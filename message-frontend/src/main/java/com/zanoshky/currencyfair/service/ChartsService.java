package com.zanoshky.currencyfair.service;

import com.zanoshky.currencyfair.model.ChartsResponse;
import com.zanoshky.currencyfair.model.Dataset;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class ChartsService {

    private List<String> createMonthsLabels() {
        final List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        return labels;
    }

    private Dataset createRandomDataset() {
        final UUID uuid = UUID.randomUUID();

        final List<Integer> values = new ArrayList<>();
        final IntStream ints = new Random().ints(12, 0, 10);
        ints.forEach(i -> values.add(i));

        return new Dataset(uuid.toString(), values);
    }

    public ChartsResponse generateDummyData() {
        final ChartsResponse chartsResponse = new ChartsResponse();
        chartsResponse.setChartName("Dummy Test " + new Random().nextInt());

        final List<String> monthsLabels = createMonthsLabels();
        chartsResponse.setLabels(monthsLabels);

        final List<Dataset> datasets = new ArrayList<>();
        datasets.add(createRandomDataset());
        datasets.add(createRandomDataset());

        chartsResponse.setDatasets(datasets);

        return chartsResponse;
    }
}
