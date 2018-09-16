package com.zanoshky.currencyfair.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyPairDetailIdentity that = (CurrencyPairDetailIdentity) o;
        return Objects.equals(timeId, that.timeId) &&
                Objects.equals(currencyPairId, that.currencyPairId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeId, currencyPairId);
    }

    @Override
    public String toString() {
        return "CurrencyPairDetailIdentity{" +
                "timeId=" + timeId +
                ", currencyPairId=" + currencyPairId +
                '}';
    }
}