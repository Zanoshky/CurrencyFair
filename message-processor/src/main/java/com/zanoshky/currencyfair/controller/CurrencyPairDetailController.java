package com.zanoshky.currencyfair.controller;


import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    @Autowired
    CurrencyPairDetailRepository currencyPairDetailRepository;

    @GetMapping("/currency-pair-details")
    public List<CurrencyPairDetail> getAllTrades() {
        return currencyPairDetailRepository.findAll();
    }

}
