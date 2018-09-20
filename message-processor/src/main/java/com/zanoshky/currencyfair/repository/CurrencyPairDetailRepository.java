package com.zanoshky.currencyfair.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zanoshky.currencyfair.model.CurrencyPairDetail;
import com.zanoshky.currencyfair.model.CurrencyPairDetailIdentity;

public interface CurrencyPairDetailRepository extends JpaRepository<CurrencyPairDetail, CurrencyPairDetailIdentity> {

    /**
     * Custom query which gathers {@link CurrencyPairDetail} between two specified dates from internal database and performs custom sorting by time_id
     * in ascending order and currency_pair_id in ascending order.
     *
     * @param from
     *            {@link LocalDateTime} value from when the query will start to return data
     * @param to
     *            {@link LocalDateTime} value up to when the query will end to return data
     * @return @return {@link List} of {@link CurrencyPairDetail} in custom order, between specified dates, ordered by time_id ASC and
     *         currency_pair_id ASC.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM currency_pair_detail AS cpd WHERE cpd.time_id BETWEEN :from AND :to ORDER BY cpd.time_id ASC, cpd.currency_pair_id ASC")
    List<CurrencyPairDetail> findAllAndSortForLastXMinutes(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
