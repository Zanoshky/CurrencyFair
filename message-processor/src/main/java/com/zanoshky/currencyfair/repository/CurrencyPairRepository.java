package com.zanoshky.currencyfair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zanoshky.currencyfair.model.CurrencyPair;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

}
