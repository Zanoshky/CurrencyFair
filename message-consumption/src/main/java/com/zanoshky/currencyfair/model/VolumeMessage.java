package com.zanoshky.currencyfair.model;

import java.util.Date;

public interface VolumeMessage {

    Long getId();

    String getCurrencyFrom();

    String getCurrencyTo();

    Date getTimePlaced();

}
