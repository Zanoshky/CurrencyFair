package com.zanoshky.currencyfair.model;

import javax.persistence.*;

@Entity
@Table(name = "currency_pair")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_from")
    private String currencyFrom;

    @Column(name = "currency_to")
    private String currencyTo;

    protected CurrencyPair() {
    }

    public CurrencyPair(String currencyFrom, String currencyTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public Long getId() {
        return id;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "id=" + id +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                '}';
    }
}