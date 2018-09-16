package com.zanoshky.currencyfair.controller;


import com.zanoshky.currencyfair.common.model.ChartResponse;
import com.zanoshky.currencyfair.common.model.Dataset;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    @Autowired
    CurrencyPairDetailRepository currencyPairDetailRepository;

    @GetMapping("/currency-pair-details")
    public List<ChartResponse> getAllCurrencyPairDetails() {
        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAllAndSort();
        final List<ChartResponse> dtoList = new ArrayList<>();

        CurrencyPair currentPair = null;
        Dataset dataset = null;
        ChartResponse currentChart = null;

        for (final CurrencyPairDetail detail : detailList) {
            if (currentPair != null && detail.getCurrencyPairDetailIdentity().getCurrencyPair().equals(currentPair.getId())) {
                currentChart.getLabels().add(detail.getCurrencyPairDetailIdentity().getTimeId().toString());
                dataset.getValue().add(detail.getCount());
            } else {
                if (currentPair == null) {
                    currentPair = detail.getCurrencyPair();
                    continue;
                }

                if (currentChart != null) {
                    // Add current chart to the response list
                    dtoList.add(currentChart);
                }

                currentPair = detail.getCurrencyPair();

                // Create new object empty chart response and set name
                currentChart = new ChartResponse(detail.getCurrencyPair().getCurrencyFrom() + " -> " + detail.getCurrencyPair().getCurrencyTo());
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

}
