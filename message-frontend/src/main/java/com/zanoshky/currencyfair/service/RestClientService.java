package com.zanoshky.currencyfair.service;

import com.zanoshky.currencyfair.common.dto.CurrencyPairDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestClientService {

    private static final String GET_ALL_URL = "http://localhost:8002/api/currency-pair-details";
    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Contacts message-processor service by consuming API to GET all {@link com.zanoshky.currencyfair.common.dto.CurrencyPairDetailDto} which has been processed.
     *
     * @return {@link List} of {@link com.zanoshky.currencyfair.common.dto.CurrencyPairDetailDto} which have not been processed by message-processor service.
     */
    public List<CurrencyPairDetailDto> getAllCurrencyPairDetails() {
        return Arrays.stream(restTemplate.getForObject(GET_ALL_URL, CurrencyPairDetailDto[].class)).collect(Collectors.toList());
    }
}