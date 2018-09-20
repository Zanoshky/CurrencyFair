package com.zanoshky.currencyfair.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zanoshky.currencyfair.dto.VolumeMessageDto;
import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;

@Service
public class CacheService {
    private static final Map<String, Map<String, CurrencyPair>> CURRENCY_PAIR_MAP = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
    private static String lastProcessedId = "0";

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private RestClientService restClientService;

    private static synchronized void updateLastProcessedId(final String id) {
        lastProcessedId = id;
    }

    /**
     * Method to connect to internal database and get all existing values of {@link CurrencyPair}. Retrieved values are loaded into
     * {@link CacheService} which represents cache memory in form of {@link Map}.
     */
    public void loadCurrencyPairsFromDbIntoMap() {
        LOGGER.info("Loading existing CurrencyPairs into cache");
        final List<CurrencyPair> currencyPairs = currencyService.findAllExistingCurrencyPairs();

        for (final CurrencyPair pair : currencyPairs) {
            final Map<String, CurrencyPair> existingMap = CURRENCY_PAIR_MAP.get(pair.getCurrencyFrom());

            if (existingMap == null) {
                createNewMapInCacheAndAddPair(pair.getCurrencyFrom(), pair.getCurrencyTo(), pair);
            } else {
                final CurrencyPair existingPair = CURRENCY_PAIR_MAP.get(pair.getCurrencyFrom()).get(pair.getCurrencyTo());

                if (existingPair == null) {
                    createNewEntryForCacheAndAddPair(pair.getCurrencyFrom(), pair.getCurrencyTo(), pair);
                }
            }
        }

        LOGGER.info("Loading existing CurrencyPairs into cache finished");
    }

    /**
     * Method to trigger API call towards message-consumption service to gather all new {@link VolumeMessageDto} since last processed message.
     */
    public void triggerStatisticalSync() {
        LOGGER.info("Requesting message-consumption to GET all new messages since ID: {}", lastProcessedId);

        final List<VolumeMessageDto> volumeMessages = restClientService.getAllUnprocessedVolumeMessages(lastProcessedId);

        if (volumeMessages.isEmpty()) {
            LOGGER.info("No new messages to process");
        } else {
            final Long newLastId = processVolumeMessages(volumeMessages);

            if (newLastId != null) {
                updateLastProcessedId(newLastId.toString());
                LOGGER.info("Last processed message is with ID: {}", lastProcessedId);
            }
        }
    }

    /**
     * Method goes trues {@link Map} in-memory cache and gets each instance of {@link CurrencyPair}.
     *
     * @return {@link List} interface of {@link ArrayList} contains each value of {@link CurrencyPair} loaded in-memory cache.
     */
    public List<CurrencyPair> listOfAllCurrencyPairs() {
        final List<CurrencyPair> list = new ArrayList<>();

        for (final String from : CURRENCY_PAIR_MAP.keySet()) {
            for (final String to : CURRENCY_PAIR_MAP.get(from).keySet()) {
                list.add(CURRENCY_PAIR_MAP.get(from).get(to));
            }
        }

        return list;
    }

    private Long processVolumeMessages(final List<VolumeMessageDto> volumeMessages) {
        Long processedId = null;

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

            processedId = message.getId();
        }

        return processedId;
    }

    private CurrencyPair processCurrencyPair(final String currencyFrom, final String currencyTo) {
        final Map<String, CurrencyPair> fromMap = CURRENCY_PAIR_MAP.get(currencyFrom);

        if (fromMap == null) {
            final CurrencyPair currencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
            createNewMapInCacheAndAddPair(currencyFrom, currencyTo, currencyPair);
        } else {
            final CurrencyPair currencyPair = CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);

            if (currencyPair == null) {
                final CurrencyPair createdCurrencyPair = currencyService.createNewCurrencyPair(currencyFrom, currencyTo);
                createNewEntryForCacheAndAddPair(currencyFrom, currencyTo, createdCurrencyPair);
            }
        }

        return CURRENCY_PAIR_MAP.get(currencyFrom).get(currencyTo);
    }

    private void createNewMapInCacheAndAddPair(final String currencyFrom, final String currencyTo, final CurrencyPair currencyPair) {
        CURRENCY_PAIR_MAP.put(currencyFrom, new HashMap<>());
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, currencyPair);
        LOGGER.info("Added {} {} into cache", currencyFrom, currencyTo);
    }

    private void createNewEntryForCacheAndAddPair(final String currencyFrom, final String currencyTo, final CurrencyPair createdCurrencyPair) {
        CURRENCY_PAIR_MAP.get(currencyFrom).put(currencyTo, createdCurrencyPair);
        LOGGER.info("Added {} {} into cache", currencyFrom, currencyTo);
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
