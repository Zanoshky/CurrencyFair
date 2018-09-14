package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CurrencyPairDetailRepository extends JpaRepository<CurrencyPairDetail, LocalDateTime> {
}
