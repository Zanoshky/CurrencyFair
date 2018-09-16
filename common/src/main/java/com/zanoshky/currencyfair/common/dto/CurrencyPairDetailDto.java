package com.zanoshky.currencyfair.common.dto;

import java.time.LocalDateTime;

public class CurrencyPairDetailDto {

    private LocalDateTime time;
    private String currencyFrom;
    private String currencyTo;
    private Long count;

    protected CurrencyPairDetailDto() {
    }

    public CurrencyPairDetailDto(final LocalDateTime time, final String currencyFrom, final String currencyTo, final Long count) {
        this.time = time;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.count = count;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "CurrencyPairDetailDto{" +
                "time=" + time +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", count=" + count +
                '}';
    }
}
