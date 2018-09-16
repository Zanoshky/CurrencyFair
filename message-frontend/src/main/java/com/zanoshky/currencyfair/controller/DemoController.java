package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.model.ChartResponse;
import com.zanoshky.currencyfair.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    RestClientService restClientService;

    @RequestMapping("/view-charts")
    public List<ChartResponse> getAllCurrecnyPairsDetails() {
        final List<ChartResponse> chartsResponse = restClientService.getAllCurrencyPairDetails();
        return chartsResponse;
    }
}
