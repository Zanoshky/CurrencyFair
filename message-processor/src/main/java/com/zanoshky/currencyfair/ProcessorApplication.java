package com.zanoshky.currencyfair;

import com.zanoshky.currencyfair.dto.VolumeMessageDto;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;
import com.zanoshky.currencyfair.service.CurrencyService;
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

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@SpringBootApplication
@EnableScheduling
public class ProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorApplication.class);

    private static final String API_VOLUME_MESSAGES = "http://localhost:8001/api/volume-messages";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static final Map<String, Map<String, CurrencyPair>> CURRENCY_PAIR_MAP = new HashMap<>();


    @Autowired
    private CurrencyService currencyService;

    public static void main(final String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

    @PostConstruct
    private void init() {
        loadCurrencyPairsFromDatabase();
    }

    private void loadCurrencyPairsFromDatabase() {

    }

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    private void downloadLatestVolumeMessages() {
        final List<VolumeMessageDto> volumeMessages = getLatestVolumeMessages();
        final List<CurrencyPairDetail> processedMessages = processVolumeMessages(volumeMessages);

        // TODO: Save Stats
        // TODO: Return response that they have been processed
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
            createNewHashMapInMapAndAdd(currencyFrom, currencyTo);
        } else {
            final CurrencyPair currencyPair = CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);

            if (currencyPair == null) {
                createNewEntryForMapAndAdd(currencyFrom, currencyTo);
            }
        }

        return CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);
    }

    private void createNewHashMapInMapAndAdd(final String currencyFrom, final String currencyTo) {
        final CurrencyPair currencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
        CURRENCY_PAIR_MAP.put(currencyFrom, new HashMap<>());
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, currencyPair);
    }

    private void createNewEntryForMapAndAdd(final String currencyFrom, final String currencyTo) {
        final CurrencyPair createdCurrencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, createdCurrencyPair);
    }

    private List<CurrencyPairDetail> processVolumeMessages(final List<VolumeMessageDto> volumeMessages) {
        final List<CurrencyPairDetail> processedMessages = new ArrayList<>();

        for (final VolumeMessageDto message : volumeMessages) {
            // Transform the message's time to floored value which will be used as key
            final LocalDateTime timeId = message.getTimePlaced().truncatedTo(ChronoUnit.MINUTES);
            final CurrencyPair currencyPair = processCurrencyPair(message.getCurrencyFrom(), message.getCurrencyTo());

            final CurrencyPairDetailIdentity detailIdentity = new CurrencyPairDetailIdentity(timeId, currencyPair.getId());
            final Optional<CurrencyPairDetail> currencyPairDetail = currencyService.getCurrencyPairDetail(detailIdentity);

            if (currencyPairDetail.isPresent()) {
                incrementExistingDetail(processedMessages, currencyPairDetail.get());
            } else {
                createNewDetail(processedMessages, detailIdentity);
            }
        }

        return processedMessages;
    }

    private void createNewDetail(final List<CurrencyPairDetail> processedMessages, final CurrencyPairDetailIdentity detailIdentity) {
        final CurrencyPairDetail detail = new CurrencyPairDetail(detailIdentity, 1L);
        processedMessages.add(currencyService.createNewCurrencyPairDetail(detail));
    }

    private void incrementExistingDetail(final List<CurrencyPairDetail> processedMessages, final CurrencyPairDetail currencyPairDetail) {
        currencyPairDetail.incrementCount();
        currencyService.updateCurrencyPairDetail(currencyPairDetail);
        processedMessages.add(currencyPairDetail);
    }

}
