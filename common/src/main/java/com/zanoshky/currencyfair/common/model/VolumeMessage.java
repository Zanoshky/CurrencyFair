package com.zanoshky.currencyfair.common.model;

import java.time.LocalDateTime;

public interface VolumeMessage {

    Long getId();

    String getCurrencyFrom();

    String getCurrencyTo();

    LocalDateTime getTimePlaced();

}
