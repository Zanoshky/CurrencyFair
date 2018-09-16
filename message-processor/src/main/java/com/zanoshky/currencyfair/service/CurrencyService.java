package com.zanoshky.currencyfair.service;

import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import com.zanoshky.currencyfair.repository.CurrencyPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyPairRepository currencyPairRepository;

    @Autowired
    CurrencyPairDetailRepository currencyPairDetailRepository;

    public CurrencyPair createNewCurrencyPair(final String currencyFrom, final String currencyTo) {
        return currencyPairRepository.save(new CurrencyPair(currencyFrom, currencyTo));
    }

    public Optional<CurrencyPairDetail> getCurrencyPairDetail(final CurrencyPairDetailIdentity currencyPairDetailIdentity) {
        return currencyPairDetailRepository.findById(currencyPairDetailIdentity);
    }

    public CurrencyPairDetail createNewCurrencyPairDetail(final CurrencyPairDetail currencyPairDetail) {
        return currencyPairDetailRepository.save(currencyPairDetail);
    }

    public CurrencyPairDetail updateCurrencyPairDetail(final CurrencyPairDetail currencyPairDetail) {
        return currencyPairDetailRepository.save(currencyPairDetail);
    }

    public List<CurrencyPair> findAllExistingCurrencyPairs() {
        return currencyPairRepository.findAll();
    }

}
