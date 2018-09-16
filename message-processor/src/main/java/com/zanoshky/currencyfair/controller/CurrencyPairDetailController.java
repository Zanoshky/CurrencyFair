package com.zanoshky.currencyfair.controller;


import com.zanoshky.currencyfair.common.dto.CurrencyPairDetailDto;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.repository.CurrencyPairDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyPairDetailController {

    @Autowired
    CurrencyPairDetailRepository currencyPairDetailRepository;

    @GetMapping("/currency-pair-details")
    public List<CurrencyPairDetailDto> getAllTrades() {
        final List<CurrencyPairDetail> detailList = currencyPairDetailRepository.findAll();
        final List<CurrencyPairDetailDto> dtoList = new ArrayList<>();

        for (final CurrencyPairDetail detail : detailList) {
            dtoList.add(new CurrencyPairDetailDto(detail.getCurrencyPairDetailIdentity().getTimeId(), detail.getCurrencyPair().getCurrencyFrom(), detail.getCurrencyPair().getCurrencyTo(), detail.getCount()));
        }

        return dtoList;
    }

}
