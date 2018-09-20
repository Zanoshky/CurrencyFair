package com.zanoshky.currencyfair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.repository.CurrencyPairRepository;

@RestController
@RequestMapping("/api")
public class CurrencyPairController {

    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    @GetMapping("/currency-pair/{currencyPairId:[\\d]+}")
    public CurrencyPair getCurrencyPairById(@PathVariable("currencyPairId") final long currencyPairId) {
        return currencyPairRepository.findById(currencyPairId).orElse(null);
    }

    @GetMapping("/currency-pairs")
    public List<CurrencyPair> getAllCurrencyPairs() {
        return currencyPairRepository.findAll();
    }

}
