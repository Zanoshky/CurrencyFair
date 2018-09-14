package com.zanoshky.currencyfair.common.model;

import java.util.Date;

public interface VolumeMessage {

    Long getId();

    String getCurrencyFrom();

    String getCurrencyTo();

    Date getTimePlaced();

}
