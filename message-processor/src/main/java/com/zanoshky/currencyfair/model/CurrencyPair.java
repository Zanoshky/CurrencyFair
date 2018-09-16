package com.zanoshky.currencyfair.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    public CurrencyPair(final String currencyFrom, final String currencyTo) {
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyPair that = (CurrencyPair) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(currencyFrom, that.currencyFrom) &&
                Objects.equals(currencyTo, that.currencyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyFrom, currencyTo);
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
