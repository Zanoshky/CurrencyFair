package com.zanoshky.currencyfair.common.model.dto;

import java.util.Date;

public class VolumeMessage implements com.zanoshky.currencyfair.common.model.VolumeMessage {

    private Long id;
    private String currencyFrom;
    private String currencyTo;
    private Date timePlaced;

    protected VolumeMessage() {
    }

    public VolumeMessage(final Long id, final String currencyFrom, final String currencyTo, final Date timePlaced) {
        this.id = id;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.timePlaced = timePlaced;
    }

    public Long getId() {
        return null;
    }

    public String getCurrencyFrom() {
        return null;
    }

    public String getCurrencyTo() {
        return null;
    }

    public Date getTimePlaced() {
        return null;
    }
}
