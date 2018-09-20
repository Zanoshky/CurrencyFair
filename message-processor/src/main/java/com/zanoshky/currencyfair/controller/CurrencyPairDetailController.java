package com.zanoshky.currencyfair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    @Autowired
    private CurrencyPairDetailRepository currencyPairDetailRepository;

    @GetMapping("/currency-pair-details")
    public List<CurrencyPairDetail> getAllCurrencyPairs() {
        return currencyPairDetailRepository.findAll();
    }

}
