package com.zanoshky.currencyfair;

import com.zanoshky.currencyfair.dto.VolumeMessageDto;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;
import com.zanoshky.currencyfair.service.CurrencyService;
import com.zanoshky.currencyfair.service.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class ProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorApplication.class);
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, CurrencyPair>> CURRENCY_PAIR_MAP = new ConcurrentHashMap<>();

    private static String lastProcessedId = "0";

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RestClientService restClientService;

    public static void main(final String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

    // By defining a @Bean of RestTemplate, we make it available to the application context
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    private void init() {
        loadCurrencyPairsFromDbIntoMap();
    }

    private void loadCurrencyPairsFromDbIntoMap() {
        LOGGER.info("LOADING existing CurrencyPairs into cache");

        final List<CurrencyPair> currencyPairs = currencyService.findAllExistingCurrencyPairs();

        for (final CurrencyPair pair : currencyPairs) {
            final Map<String, CurrencyPair> existingMap = CURRENCY_PAIR_MAP.get(pair.getCurrencyFrom());

            if (existingMap == null) {
                createNewHashMapInMapAndAdd(pair.getCurrencyFrom(), pair.getCurrencyTo(), pair);
            } else {
                final CurrencyPair existingPair = CURRENCY_PAIR_MAP.get(pair.getCurrencyFrom()).get(pair.getCurrencyTo());

                if (existingPair == null) {
                    createNewEntryForMapAndAdd(pair.getCurrencyFrom(), pair.getCurrencyTo(), pair);
                }
            }
        }

        LOGGER.info("LOADING existing CurrencyPairs into cache finished");
    }

    @Scheduled(fixedRate = 60000, initialDelay = 10000)
    private void downloadLatestVolumeMessages() {
        LOGGER.info("Requesting message-consumption to GET all new messages since ID: " + lastProcessedId);
        final List<VolumeMessageDto> volumeMessages = restClientService.getAllUnprocessedVolumeMessages(lastProcessedId);

        if (volumeMessages.isEmpty()) {
            LOGGER.info("No new messages to process");
        } else {
            final Long newLastId = processVolumeMessages(volumeMessages);

            if (newLastId != null) {
                lastProcessedId = newLastId.toString();
                LOGGER.info("Last processed message is with ID: " + ProcessorApplication.lastProcessedId);
            }
        }
    }

    private CurrencyPair processCurrencyPair(final String currencyFrom, final String currencyTo) {
        final Map<String, CurrencyPair> fromMap = CURRENCY_PAIR_MAP.get(currencyFrom);

        if (fromMap == null) {
            final CurrencyPair currencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
            createNewHashMapInMapAndAdd(currencyFrom, currencyTo, currencyPair);
        } else {
            final CurrencyPair currencyPair = CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);

            if (currencyPair == null) {
                final CurrencyPair createdCurrencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
                createNewEntryForMapAndAdd(currencyFrom, currencyTo, createdCurrencyPair);
            }
        }

        return CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);
    }

    private void createNewHashMapInMapAndAdd(final String currencyFrom, final String currencyTo, final CurrencyPair currencyPair) {
        CURRENCY_PAIR_MAP.put(currencyFrom, new ConcurrentHashMap<>());
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, currencyPair);

        LOGGER.info("Added " + currencyFrom + " - " + currencyTo + " into cache");
    }

    private void createNewEntryForMapAndAdd(final String currencyFrom, final String currencyTo, final CurrencyPair createdCurrencyPair) {
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, createdCurrencyPair);

        LOGGER.info("Added " + currencyFrom + " - " + currencyTo + " into cache");
    }

    private Long processVolumeMessages(final List<VolumeMessageDto> volumeMessages) {
        Long lastProcessedId = null;

        for (final VolumeMessageDto message : volumeMessages) {
            // Transform the message's time to floored value which will be used as key
            final LocalDateTime timeId = message.getTimePlaced().truncatedTo(ChronoUnit.MINUTES);
            final CurrencyPair currencyPair = processCurrencyPair(message.getCurrencyFrom(), message.getCurrencyTo());

            final CurrencyPairDetailIdentity detailIdentity = new CurrencyPairDetailIdentity(timeId, currencyPair.getId());
            final Optional<CurrencyPairDetail> currencyPairDetail = currencyService.getCurrencyPairDetail(detailIdentity);

            if (currencyPairDetail.isPresent()) {
                incrementExistingDetail(currencyPairDetail.get());
            } else {
                createNewDetail(detailIdentity);
            }

            lastProcessedId = message.getId();
        }

        return lastProcessedId;
    }

    private void createNewDetail(final CurrencyPairDetailIdentity detailIdentity) {
        final CurrencyPairDetail detail = new CurrencyPairDetail(detailIdentity, 1L);
        currencyService.createNewCurrencyPairDetail(detail);
    }

    private void incrementExistingDetail(final CurrencyPairDetail detail) {
        detail.incrementCount();
        currencyService.updateCurrencyPairDetail(detail);
    }

}
