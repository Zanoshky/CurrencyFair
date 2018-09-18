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

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    @Autowired
    CacheService cacheService;

    @Autowired
    CurrencyPairDetailRepository currencyPairDetailRepository;

    /**
     * Method which returns processed {@link CurrencyPairDetail} into statistical information about volume of transactions per
     * {@link java.time.LocalDateTime}.
     *
     * @return {@link List} of {@link ChartResponse} which statisticaly information about {@link CurrencyPairDetail}.
     */
    @GetMapping("/currency-pair-charts-last-15-minutes")
    public List<ChartResponse> getAllCurrencyStatChartsLast15Minutes() {
        final long selectedMinutes = 15L;
        final LocalDateTime endTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final LocalDateTime startTime = endTime.minusMinutes(selectedMinutes);

        final List<LocalDateTime> timeIds = generateTimeIdsToCompare(startTime, selectedMinutes);
        final List<String> timeIdLabels = generateTimeIdLabels(timeIds);

        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSortForLastXMinutes(startTime, endTime);
        final List<ChartResponse> dtoResponse = new ArrayList<>();

        for (final CurrencyPair pair : cacheService.listOfAllCurrencyPairs()) {
            final ChartResponse currentChart = new ChartResponse(formChartName(pair));
            currentChart.setLabels(timeIdLabels);
            final Dataset dataset = new Dataset("First");

            for (final LocalDateTime time : timeIds) {
                long count = 0;

                for (final CurrencyPairDetail detail : detailList) {
                    if (detail.getCurrencyPair().equals(pair) && detail.getCurrencyPairDetailIdentity().getTimeId().equals(time)) {
                        count = detail.getCount();
                        break;
                    }
                }

                dataset.getValue().add(count);
            }

            currentChart.getDatasets().add(dataset);
            dtoResponse.add(currentChart);
        }

        return dtoResponse;
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
            labelList.add(minute.toString().replace('T', ' ').substring(11));
        }

        return labelList;
    }
}
