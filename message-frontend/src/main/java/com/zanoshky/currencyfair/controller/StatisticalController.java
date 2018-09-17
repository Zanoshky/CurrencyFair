package com.zanoshky.currencyfair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.common.dto.ChartResponse;
import com.zanoshky.currencyfair.service.RestClientService;

@RestController
public class StatisticalController {

    @Autowired
    RestClientService restClientService;

    @RequestMapping("/view-all-currency-stat-charts")
    public List<ChartResponse> getAllCurrencyStatCharts() {
        return restClientService.getAllCurrencyStatCharts();
    }
}
