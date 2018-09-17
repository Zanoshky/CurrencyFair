package com.zanoshky.currencyfair.common.dto;

import java.time.LocalDateTime;

public interface VolumeMessage {

    /**
     * Primary key (ID) of {@link VolumeMessage} automatically generated from DB.
     * 
     * @return {@link Long} value of Primary Key
     */
    Long getId();

    /**
     * {@link String} value of Currency from which messages has been initiated.
     * 
     * @return {@link String} value of Currency From.
     */
    String getCurrencyFrom();

    /**
     * {@link String} value of Currency to which messages has been designated.
     *
     * @return {@link String} value of Currency To.
     */
    String getCurrencyTo();

    /**
     * {@link LocalDateTime} value of time when the message has been placed.
     *
     * @return {@link LocalDateTime} value when the message was placed.
     */
    LocalDateTime getTimePlaced();

}
