package com.zanoshky.currencyfair;

import javax.annotation.PostConstruct;

import com.zanoshky.currencyfair.dto.VolumeMessageDto;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class MessageProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessorApplication.class);

    private static final String API_VOLUME_MESSAGES = "http://localhost:8001/api/volume-messages";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static final Map<String, Map<String, CurrencyPair>> CURRENCY_PAIR_MAP = new HashMap<>();

    @Autowired
    CurrencyPairRepository currencyPairRepository;


    public static void main(final String[] args) {
        SpringApplication.run(MessageProcessorApplication.class, args);
    }

    @PostConstruct
    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    private void init() {
        // Load from db on init...
        
        final List<VolumeMessageDto> volumeMessages = getLatestVolumeMessages();
        final List<CurrencyPairDetail> processedMessages = processVolumeMessages(volumeMessages);

        // Save Stats

        // Return response that they have been processed
    }

    private List<VolumeMessageDto> getLatestVolumeMessages() {
        final ResponseEntity<List<VolumeMessageDto>> response = REST_TEMPLATE.exchange(
                API_VOLUME_MESSAGES,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VolumeMessageDto>>() {
                });

        return response.getBody();
    }

    private CurrencyPair processCurrencyPair(final String currencyFrom, final String currencyTo) {
        final Map<String, CurrencyPair> fromMap = CURRENCY_PAIR_MAP.get(currencyFrom);

        if (fromMap == null) {
            final CurrencyPair currencyPair = currencyPairRepository.save(new CurrencyPair(currencyFrom, currencyTo));
            CURRENCY_PAIR_MAP.put(currencyFrom, new HashMap<>());
            CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, currencyPair);
        } else {
            final CurrencyPair currencyPair = CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);

            if (currencyPair == null) {
                CurrencyPair createdCurrencyPair = currencyPairRepository.save(new CurrencyPair(currencyFrom, currencyTo));
                CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, createdCurrencyPair);
            }
        }

        return CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);
    }

    private List<CurrencyPairDetail> processVolumeMessages(final List<VolumeMessageDto> volumeMessages) {
        final List<CurrencyPairDetail> processedMessages = new ArrayList<>();

        for (VolumeMessageDto message : volumeMessages) {
            CurrencyPair currencyPair = processCurrencyPair(message.getCurrencyFrom(), message.getCurrencyTo());
            LocalDateTime timeId =  message.getTimePlaced().truncatedTo(ChronoUnit.MINUTES);

        }

        return processedMessages;
    }

}
