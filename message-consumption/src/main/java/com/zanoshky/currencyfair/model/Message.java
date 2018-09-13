package com.zanoshky.currencyfair.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "currency_from")
    private String currencyFrom;

    @Column(name = "currency_to")
    private String currencyTo;

    @Column(name = "amount_sell")
    private Double amountSell;

    @Column(name = "amount_buy")
    private Double amountBuy;

    @Column(name = "rate")
    private Double rate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-YY HH:mm:ss")
    @Column(name = "time_placed")
    private Date timePlaced;

    @Column(name = "originating_country")
    private String originatingCountry;

    protected Message() {
    }

    public Message(
            final String userId, final String currencyFrom, final String currencyTo, final Double amountSell, final Double amountBuy,
            final Double rate, final Date timePlaced,
            final String originatingCountry) {
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountSell = amountSell;
        this.amountBuy = amountBuy;
        this.rate = rate;
        this.timePlaced = timePlaced;
        this.originatingCountry = originatingCountry;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public Double getAmountSell() {
        return amountSell;
    }

    public Double getAmountBuy() {
        return amountBuy;
    }

    public Double getRate() {
        return rate;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", userId='" + userId + '\'' + ", originatedCountry='" + originatingCountry + '\'' + ", currencyFrom='"
                + currencyFrom + '\'' + ", currencyTo='" + currencyTo + '\'' + ", amountSell=" + amountSell + ", amountBuy=" + amountBuy + ", rate="
                + rate + ", timePlaced=" + timePlaced + '}';
    }
}
