package com.zanoshky.currencyfair.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zanoshky.currencyfair.common.dto.ChartResponse;

@Service
public class RestClientService {

    private static final String SYSTEM_VAR = System.getenv("message_processor_api");
    private static final String API_URL = "/api/charts-for-last-minutes/15";
    private static final String HTTP = "http://";
    private static final String GET_ALL_URL;

    static {
        if (SYSTEM_VAR == null) {
            GET_ALL_URL = HTTP + "localhost:8002" + API_URL;
        } else {
            GET_ALL_URL = HTTP + SYSTEM_VAR + API_URL;
        }
    }

    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Contacts message-processor service API to GET all {@link ChartResponse} which has been processed to visual represents transaction volume of
     * each currency pair.
     *
     * @return {@link List} of {@link ChartResponse} which are ready for presenting to the Frontend
     */
    public List<ChartResponse> getAllCurrencyStatCharts() {
        return Arrays.stream(restTemplate.getForObject(GET_ALL_URL, ChartResponse[].class)).collect(Collectors.toList());
    }
}