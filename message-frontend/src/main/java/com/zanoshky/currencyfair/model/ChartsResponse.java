package com.zanoshky.currencyfair.model;

import java.util.List;

public class ChartsResponse {

    private String chartName;
    private List<String> labels;
    private List<Dataset> datasets;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(final String chartName) {
        this.chartName = chartName;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(final List<Dataset> datasets) {
        this.datasets = datasets;
    }

}
