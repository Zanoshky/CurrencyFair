package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyPairDetailRepository extends JpaRepository<CurrencyPairDetail, CurrencyPairDetailIdentity> {

}
