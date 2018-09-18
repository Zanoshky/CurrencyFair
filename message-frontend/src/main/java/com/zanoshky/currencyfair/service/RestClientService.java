package com.zanoshky.currencyfair.service;

import com.zanoshky.currencyfair.common.dto.ChartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestClientService {

    private static final String GET_ALL_URL = "http://localhost:8002/api/currency-pair-charts-last-15-minutes";
    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Contacts message-processor service API to GET all {@link ChartResponse} which has been processed to visual represents transaction volume of each currency pair.
     *
     * @return {@link List} of {@link ChartResponse} which are ready for presenting to the Frontend
     */
    public List<ChartResponse> getAllCurrencyStatCharts() {
        return Arrays.stream(restTemplate.getForObject(GET_ALL_URL, ChartResponse[].class)).collect(Collectors.toList());
    }
}