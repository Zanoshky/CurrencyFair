package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyPairDetailRepository extends JpaRepository<CurrencyPairDetail, CurrencyPairDetailIdentity> {

    @Query(nativeQuery = true, value = "SELECT * from currency_pair_detail as cpd order by cpd.currency_pair_id ASC, cpd.currency_pair_id ASC")
    List<CurrencyPairDetail> findAllAndSort();

}
