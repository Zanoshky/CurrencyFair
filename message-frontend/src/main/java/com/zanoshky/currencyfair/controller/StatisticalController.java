package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.common.dto.ChartResponse;
import com.zanoshky.currencyfair.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticalController {

    @Autowired
    private RestClientService restClientService;

    @RequestMapping("/view-all-currency-stat-charts")
    public List<ChartResponse> getAllCurrencyStatCharts() {
        return restClientService.getAllCurrencyStatCharts();
    }
}
