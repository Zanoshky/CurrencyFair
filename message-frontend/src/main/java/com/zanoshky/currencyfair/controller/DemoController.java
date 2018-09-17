package com.zanoshky.currencyfair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.common.model.ChartResponse;
import com.zanoshky.currencyfair.service.RestClientService;

@RestController
public class DemoController {

    @Autowired
    RestClientService restClientService;

    @RequestMapping("/view-charts")
    public List<ChartResponse> getAllCurrencyPairsDetails() {
        return restClientService.getAllCurrencyPairDetails();
    }
}
