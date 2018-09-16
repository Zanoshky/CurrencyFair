package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.common.model.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true, value = "SELECT id, currency_from AS currencyFrom, currency_to AS currencyTo, time_placed AS timePlaced FROM message m WHERE id > :lastProcessedId")
    List<VolumeMessage> findAllOnlyVolumeInfo(@Param("lastProcessedId") final Long lastProcessedId);

}
