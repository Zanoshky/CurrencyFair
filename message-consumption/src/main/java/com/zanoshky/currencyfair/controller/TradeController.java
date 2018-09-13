package com.zanoshky.currencyfair.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zanoshky.currencyfair.model.Trade;
import com.zanoshky.currencyfair.repository.TradeRepository;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    TradeRepository tradeRepository;

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @PostMapping("/trade")
    public Trade createTrade(@Valid @RequestBody final Trade trade) {
        return tradeRepository.save(trade);
    }

}
