package com.zanoshky.currencyfair.common.dto;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

    private String name;
    private List<Long> value;

    protected Dataset() {
    }

    public Dataset(final String name) {
        this.name = name;
        value = new ArrayList<>();
    }

    public Dataset(final String name, final List<Long> value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Long> getValue() {
        return value;
    }

    public void setValue(final List<Long> value) {
        this.value = value;
    }
}
