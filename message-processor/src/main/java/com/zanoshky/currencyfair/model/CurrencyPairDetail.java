package com.zanoshky.currencyfair.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "currency_pair_detail")
public class CurrencyPairDetail implements Serializable {

    @EmbeddedId
    private CurrencyPairDetailIdentity currencyPairDetailIdentity;

    @Column(name = "count")
    private Long count;

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
}