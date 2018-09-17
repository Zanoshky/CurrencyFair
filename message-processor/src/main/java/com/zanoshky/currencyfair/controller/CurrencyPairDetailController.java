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
    @GetMapping("/currency-pair-charts")
    public List<ChartResponse> getAllCurrencyStatCharts() {
        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSort();
        final List<ChartResponse> dtoList = new ArrayList<>();

        Dataset dataset = null;
        CurrencyPair currentPair = null;
        ChartResponse currentChart = null;

        for (final CurrencyPairDetail detail : detailList) {
            if (currentPair != null && detail.getCurrencyPairDetailIdentity().getCurrencyPair().equals(currentPair.getId())) {
                currentChart.getLabels().add(detail.getCurrencyPairDetailIdentity().getTimeId().toString());
                dataset.getValue().add(detail.getCount());
            } else {
                if (currentChart != null) {
                    // Add current chart to the response list before overwriting with new chart value
                    dtoList.add(currentChart);
                }

                currentPair = detail.getCurrencyPair();

                // Create new object empty chart response and set name
                currentChart = new ChartResponse(formChartName(detail));
                currentChart.getLabels().add(detail.getCurrencyPairDetailIdentity().getTimeId().toString());
                dataset = new Dataset("First");
                dataset.getValue().add(detail.getCount());
                currentChart.getDatasets().add(dataset);
            }
        }

        // Add current chart to the response list
        dtoList.add(currentChart);
        return dtoList;
    }

    @GetMapping("/currency-pair-charts-last-15-minutes")
    public List<ChartResponse> getAllCurrencyStatChartsLast15Minutes() {
        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSortForLastXMinutes();
        final List<ChartResponse> dtoList = new ArrayList<>();
        final List<String> list = generateLastXWhereTimeIds(15L);
        return dtoList;
    }

    private String formChartName(final CurrencyPairDetail detail) {
        return "Message volume of: " + detail.getCurrencyPair().getCurrencyFrom() + " -> "
                + detail.getCurrencyPair().getCurrencyTo();
    }

    private List<String> generateLastXWhereTimeIds(final long fromMinutes) {
        final LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(fromMinutes);
        final List<String> minutesInBetween = new ArrayList<>();

        for (long i = 0; i < fromMinutes; i++) {
            minutesInBetween.add("'" + startTime.plusMinutes(i).toString().replace('T', ' ') + "'");
        }

        return minutesInBetween;
    }
}
