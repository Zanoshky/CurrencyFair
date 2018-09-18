package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.dto.ChartResponse;
import com.zanoshky.currencyfair.common.dto.Dataset;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import com.zanoshky.currencyfair.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CurrencyPairDetailRepository currencyPairDetailRepository;

    /**
     * Method processes values of {@link CurrencyPairDetail} into statistical information about transactions volume per minute.
     *
     * @return {@link List} of {@link ChartResponse} which presents statistical information about transactions volume per minute for specific {@link CurrencyPair}.
     */
    @GetMapping("/currency-pair-charts-last-15-minutes")
    public List<ChartResponse> getAllCurrencyStatChartsLast15Minutes() {
        // This is template for generic method for any period
        final long selectedMinutes = 15L;

        final LocalDateTime endTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final LocalDateTime startTime = endTime.minusMinutes(selectedMinutes);

        final List<LocalDateTime> timeIds = generateTimeIdsToCompare(startTime, selectedMinutes);
        final List<String> timeIdLabels = generateTimeIdLabels(timeIds);

        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSortForLastXMinutes(startTime, endTime);
        final List<ChartResponse> dtoResponse = new ArrayList<>();

        for (final CurrencyPair pair : cacheService.listOfAllCurrencyPairs()) {
            final ChartResponse currentChart = new ChartResponse(formChartName(pair), timeIdLabels);
            final Dataset dataset = new Dataset("Dataset");

            for (final LocalDateTime time : timeIds) {
                final long count = getCountFromTimeAndCurrencyPair(detailList, pair, time);
                dataset.getValue().add(count);
            }

            currentChart.getDatasets().add(dataset);
            dtoResponse.add(currentChart);
        }

        return dtoResponse;
    }

    private long getCountFromTimeAndCurrencyPair(final List<CurrencyPairDetail> detailList, final CurrencyPair pair, final LocalDateTime time) {
        for (final CurrencyPairDetail detail : detailList) {
            if (detail.getCurrencyPair().equals(pair) && detail.getCurrencyPairDetailIdentity().getTimeId().equals(time)) {
                return detail.getCount();
            }
        }

        return 0;
    }

    private String formChartName(final CurrencyPair pair) {
        return "Message volume of: " + pair.getCurrencyFrom() + " -> "
                + pair.getCurrencyTo();
    }

    private List<LocalDateTime> generateTimeIdsToCompare(final LocalDateTime startTime, final long numberOfMinutes) {
        final List<LocalDateTime> minuteList = new ArrayList<>();

        for (long i = 0; i < numberOfMinutes; i++) {
            minuteList.add(startTime.plusMinutes(i));
        }

        return minuteList;
    }

    private List<String> generateTimeIdLabels(final List<LocalDateTime> minuteList) {
        final List<String> labelList = new ArrayList<>();

        for (final LocalDateTime minute : minuteList) {
            labelList.add(minute.format(TIME_FORMATTER));
        }

        return labelList;
    }
}
