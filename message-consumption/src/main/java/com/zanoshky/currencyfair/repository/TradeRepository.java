package com.zanoshky.currencyfair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zanoshky.currencyfair.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
