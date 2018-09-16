package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.dto.CurrencyPairDetailDto;
import com.zanoshky.currencyfair.model.ChartsResponse;
import com.zanoshky.currencyfair.service.ChartsService;
import com.zanoshky.currencyfair.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    @Autowired
    ChartsService chartService;

    @Autowired
    RestClientService restClientService;

    @RequestMapping("/view-charts")
    public List<ChartsResponse> demoResponse() {
        final List<ChartsResponse> chartsResponse = new ArrayList<>();
        chartsResponse.add(chartService.generateDummyData());
        chartsResponse.add(chartService.generateDummyData());
        return chartsResponse;
    }

    @RequestMapping("/view-currency-pairs-details")
    public List<ChartsResponse> getAllCurrecnyPairsDetails() {
        final List<CurrencyPairDetailDto> details = restClientService.getAllCurrencyPairDetails();
        final List<ChartsResponse> chartsResponse = new ArrayList<>();

//        final ChartsResponse chartsResponse = new ChartsResponse();
//        chartsResponse.setChartName("Dummy Test " + new Random().nextInt());
//
//        final List<String> monthsLabels = createMonthsLabels();
//        chartsResponse.setLabels(monthsLabels);
//
//        final List<Dataset> datasets = new ArrayList<>();
//        datasets.add(createRandomDataset());
//
//        chartsResponse.setDatasets(datasets);
//
//        return chartsResponse;
        return null;
    }
}
