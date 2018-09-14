package com.zanoshky.currencyfair.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getCurrencyFrom() {
        return this.currencyFrom;
    }

    public String getCurrencyTo() {
        return this.currencyTo;
    }

    public Double getAmountSell() {
        return this.amountSell;
    }

    public Double getAmountBuy() {
        return this.amountBuy;
    }

    public Double getRate() {
        return this.rate;
    }

    public Date getTimePlaced() {
        return this.timePlaced;
    }

    public String getOriginatingCountry() {
        return this.originatingCountry;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + this.id + ", userId='" + this.userId + '\'' + ", originatedCountry='" + this.originatingCountry + '\'' + ", currencyFrom='"
                + this.currencyFrom + '\'' + ", currencyTo='" + this.currencyTo + '\'' + ", amountSell=" + this.amountSell + ", amountBuy=" + this.amountBuy + ", rate="
                + this.rate + ", timePlaced=" + this.timePlaced + '}';
    }
}
