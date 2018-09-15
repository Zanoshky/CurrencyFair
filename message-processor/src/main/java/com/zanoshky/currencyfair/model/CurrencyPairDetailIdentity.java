package com.zanoshky.currencyfair.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class CurrencyPairDetailIdentity implements Serializable {

    @Column(name = "time_id")
    private LocalDateTime timeId;

    @Column(name = "currency_pair_id")
    private Long currencyPairId;

    protected CurrencyPairDetailIdentity() {
    }

    public CurrencyPairDetailIdentity(final LocalDateTime timeId, final Long currencyPair) {
        this.timeId = timeId;
        currencyPairId = currencyPair;
    }

    public LocalDateTime getTimeId() {
        return timeId;
    }

    public Long getCurrencyPair() {
        return currencyPairId;
    }

    @Override
    public String toString() {
        return "CurrencyPairDetailIdentity{" +
                "timeId=" + timeId +
                ", currencyPairId=" + currencyPairId +
                '}';
    }
}