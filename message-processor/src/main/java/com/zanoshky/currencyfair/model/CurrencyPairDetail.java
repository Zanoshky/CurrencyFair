package com.zanoshky.currencyfair.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "currency_pair_detail")
public class CurrencyPairDetail implements Serializable {

    @EmbeddedId
    private CurrencyPairDetailIdentity currencyPairDetailIdentity;

    @Column(name = "count")
    private Long count;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", insertable = false, updatable = false)
    private CurrencyPair currencyPair;

    protected CurrencyPairDetail() {
    }

    public CurrencyPairDetail(final CurrencyPairDetailIdentity currencyPairDetailIdentity, final Long count) {
        this.currencyPairDetailIdentity = currencyPairDetailIdentity;
        this.count = count;
    }

    public void incrementCount() {
        count = count + 1;
    }

    public CurrencyPairDetailIdentity getCurrencyPairDetailIdentity() {
        return currencyPairDetailIdentity;
    }

    public Long getCount() {
        return count;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public String toString() {
        return "CurrencyPairDetail{" +
                "currencyPairDetailIdentity=" + currencyPairDetailIdentity +
                ", count=" + count +
                ", currencyPair=" + currencyPair +
                '}';
    }
}