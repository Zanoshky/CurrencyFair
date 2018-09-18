package com.zanoshky.currencyfair.repository;

import com.zanoshky.currencyfair.common.dto.VolumeMessage;
import com.zanoshky.currencyfair.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Custom query which connects to internal DB and gathers filtered information about {@link Message} where the value of Primary Key identifier
     * must be larger then lastProcessedId parameter.
     *
     * @param lastProcessedId Value which defines last processed ID, so the method will only return new unprocessed information.
     * @return {@link List} of {@link VolumeMessage} which contains filtered information of {@link Message}.
     */
    @Query(nativeQuery = true, value = "SELECT id, currency_from AS currencyFrom, currency_to AS currencyTo, time_placed AS timePlaced FROM message m WHERE id > :lastProcessedId")
    List<VolumeMessage> findAllOnlyVolumeInfo(@Param("lastProcessedId") final Long lastProcessedId);

}
