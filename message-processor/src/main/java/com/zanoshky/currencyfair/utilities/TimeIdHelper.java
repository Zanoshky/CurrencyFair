package com.zanoshky.currencyfair.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class TimeIdHelper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static List<LocalDateTime> generateTimeIdsToCompare(final LocalDateTime startTime, final long numberOfMinutes) {
        final List<LocalDateTime> minuteList = new ArrayList<>();

        for (long i = 0; i < numberOfMinutes; i++) {
            minuteList.add(startTime.plusMinutes(i));
        }

        return minuteList;
    }

    public static List<String> generateTimeIdLabels(final List<LocalDateTime> minuteList) {
        final List<String> labelList = new ArrayList<>();

        for (final LocalDateTime minute : minuteList) {
            labelList.add(minute.format(TIME_FORMATTER));
        }

        return labelList;
    }

}
