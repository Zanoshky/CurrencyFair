package com.zanoshky.currencyfair.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "currency_pair_detail")
public class CurrencyPairDetail implements Serializable {

    @Id
    @Column(name = "time_id")
    private LocalDateTime id;

    @Column(name = "currency_pair_id")
    private CurrencyPair currencyPair;

    @Column(name = "count")
    private String count;

    protected CurrencyPairDetail() {
    }

    public CurrencyPairDetail(LocalDateTime id, CurrencyPair currencyPair, String count) {
        this.id = id;
        this.currencyPair = currencyPair;
        this.count = count;
    }
}
