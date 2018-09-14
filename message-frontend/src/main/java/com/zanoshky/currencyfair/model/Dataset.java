package com.zanoshky.currencyfair.model;

import java.util.List;

public class Dataset {

    private String name;
    private List<Integer> value;

    protected Dataset() {
    }

    public Dataset(final String name, final List<Integer> value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getValue() {
        return value;
    }
}
