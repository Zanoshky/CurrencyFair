package com.zanoshky.currencyfair.service;

import com.zanoshky.currencyfair.dto.VolumeMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestClientService {

    private static final String GET_URL_BY_ID = "http://localhost:8001/api/volume-messages/";
    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Contacts message-consumption service by consuming API to GET all {@link com.zanoshky.currencyfair.common.model.VolumeMessage} which has not been processed.
     *
     * @param lastProcessedId {@link String} value of ID from last processed ID by message-processor service
     * @return {@link List} of {@link VolumeMessageDto} which have not been processed by message-processor service.
     */
    public List<VolumeMessageDto> getAllUnprocessedVolumeMessages(final String lastProcessedId) {
        return Arrays.stream(restTemplate.getForObject(GET_URL_BY_ID + lastProcessedId, VolumeMessageDto[].class)).collect(Collectors.toList());
    }

}