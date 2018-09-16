package com.zanoshky.currencyfair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "currency_pair_detail")
public class CurrencyPairDetail implements Serializable {

    @EmbeddedId
    private CurrencyPairDetailIdentity currencyPairDetailIdentity;

    @Column(name = "count")
    private Long count;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "currency_from", referencedColumnName = "currency_from", insertable = false, updatable = false),
            @JoinColumn(name = "currency_to", referencedColumnName = "currency_to", insertable = false, updatable = false),
    })
    @JsonIgnore
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