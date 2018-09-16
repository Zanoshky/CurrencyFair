package com.zanoshky.currencyfair.controller;

import com.zanoshky.currencyfair.model.ChartsResponse;
import com.zanoshky.currencyfair.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatisticalController {

    @Autowired
    ChartsService chartService;

    @RequestMapping("/view-charts")
    public List<ChartsResponse> demoResponse() {
        final List<ChartsResponse> chartsResponse = new ArrayList<>();
        chartsResponse.add(chartService.generateDummyData());
        chartsResponse.add(chartService.generateDummyData());
        return chartsResponse;
    }
}
