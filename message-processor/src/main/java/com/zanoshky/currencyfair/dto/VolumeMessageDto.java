package com.zanoshky.currencyfair.dto;

import com.zanoshky.currencyfair.common.model.VolumeMessage;

import java.time.LocalDateTime;

public class VolumeMessageDto implements VolumeMessage {

    private Long id;
    private String currencyFrom;
    private String currencyTo;
    private LocalDateTime timePlaced;

    protected VolumeMessageDto() {
    }

    public VolumeMessageDto(final Long id, final String currencyFrom, final String currencyTo, final LocalDateTime timePlaced) {
        this.id = id;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.timePlaced = timePlaced;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    @Override
    public String getCurrencyTo() {
        return currencyTo;
    }

    @Override
    public LocalDateTime getTimePlaced() {
        return timePlaced;
    }

    @Override
    public String toString() {
        return "VolumeMessageDto{" +
                "id=" + id +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", timePlaced=" + timePlaced +
                '}';
    }
}
