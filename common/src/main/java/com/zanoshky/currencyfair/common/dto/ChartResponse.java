package com.zanoshky.currencyfair.common.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartResponse {

    private String chartName;
    private List<String> labels;
    private List<Dataset> datasets;

    protected ChartResponse() {
    }

    public ChartResponse(final String chartName, final List<String> labels) {
        this.chartName = chartName;
        this.labels = labels;
        datasets = new ArrayList<>();
    }

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
