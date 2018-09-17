package com.zanoshky.currencyfair.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;

public interface CurrencyPairDetailRepository extends JpaRepository<CurrencyPairDetail, CurrencyPairDetailIdentity> {

    /**
     * Custom query which gathers all data from internal database about {@link CurrencyPairDetail} and performs custom sorting by time_id ASC and
     * currency_pair_id ASC.
     *
     * @return {@link List} of {@link CurrencyPairDetail} in custom order, ordered by time_id ASC and currency_pair_id ASC.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM currency_pair_detail AS cpd ORDER BY cpd.time_id ASC, cpd.currency_pair_id ASC")
    List<CurrencyPairDetail> findAllAndSort();

    @Query(nativeQuery = true, value = "SELECT * FROM currency_pair_detail AS cpd WHERE time_id > NOW() - INTERVAL 15 MINUTE ORDER BY cpd.time_id ASC, cpd.currency_pair_id ASC")
    List<CurrencyPairDetail> findAllAndSortForLastXMinutes();

}
