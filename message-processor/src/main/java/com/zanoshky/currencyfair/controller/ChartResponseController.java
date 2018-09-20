package com.zanoshky.currencyfair.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.common.dto.ChartResponse;
import com.zanoshky.currencyfair.common.dto.Dataset;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import com.zanoshky.currencyfair.service.CacheService;
import com.zanoshky.currencyfair.utilities.ChartHelper;
import com.zanoshky.currencyfair.utilities.TimeIdHelper;

@RestController
@RequestMapping("/api")
public class ChartResponseController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CurrencyPairDetailRepository currencyPairDetailRepository;

    /**
     * Method processes values of {@link CurrencyPairDetail} into statistical information about transactions volume per minute.
     *
     * @return {@link List} of {@link ChartResponse} which presents statistical information about transactions volume per minute for specific
     *         {@link CurrencyPair}.
     */
    @GetMapping("/charts-for-last-minutes/{currencyPairId:[\\d]+}")
    public List<ChartResponse> getAllCurrencyStatChartsLast15Minutes() {
        // This is template for generic method for any period
        final long selectedMinutes = 15L;

        final LocalDateTime endTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final LocalDateTime startTime = endTime.minusMinutes(selectedMinutes);

        final List<LocalDateTime> timeIds = TimeIdHelper.generateTimeIdsToCompare(startTime, selectedMinutes);
        final List<String> timeIdLabels = TimeIdHelper.generateTimeIdLabels(timeIds);

        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSortForLastXMinutes(startTime, endTime);
        final List<ChartResponse> dtoResponse = new ArrayList<>();

        for (final CurrencyPair pair : cacheService.listOfAllCurrencyPairs()) {
            final ChartResponse currentChart = new ChartResponse(ChartHelper.formChartName(pair), timeIdLabels);
            final Dataset dataset = new Dataset("Dataset");

            for (final LocalDateTime time : timeIds) {
                final long count = ChartHelper.countsPerMinute(detailList, pair, time);
                dataset.getValue().add(count);
            }

            currentChart.getDatasets().add(dataset);
            dtoResponse.add(currentChart);
        }

        return dtoResponse;
    }

}
