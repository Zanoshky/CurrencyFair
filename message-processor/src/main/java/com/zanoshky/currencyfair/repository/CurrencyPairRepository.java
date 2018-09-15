package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.model.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

}
