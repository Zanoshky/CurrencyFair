package com.zanoshky.currencyfair.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CurrencyPairDetailIdentity implements Serializable {

    @NotNull
    @Size(min = 3, max = 3)
    private String currencyFrom;

    @NotNull
    @Size(min = 3, max = 3)
    private String currencyTo;

    protected CurrencyPairDetailIdentity() {
    }

    public CurrencyPairDetailIdentity(@NotNull @Size(max = 3) String currencyFrom, @NotNull @Size(max = 3) String currencyTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }
}