package com.zanoshky.currencyfair.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zanoshky.currencyfair.common.model.ChartResponse;

@Service
public class RestClientService {

    private static final String GET_ALL_URL = "http://localhost:8002/api/currency-pair-charts";
    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Contacts API from message-processor service to GET all {@link ChartResponse} which has been processed.
     *
     * @return {@link List} of {@link ChartResponse} which are ready for presenting to the Frontend
     */
    public List<ChartResponse> getAllCurrencyPairDetails() {
        return Arrays.stream(restTemplate.getForObject(GET_ALL_URL, ChartResponse[].class)).collect(Collectors.toList());
    }
}