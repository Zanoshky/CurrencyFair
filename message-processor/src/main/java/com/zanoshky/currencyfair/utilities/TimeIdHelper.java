package com.zanoshky.currencyfair.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class TimeIdHelper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Method generates timeId's in form of {@link LocalDateTime} for every minutes from given start date.
     * 
     * @param startTime
     *            {@link LocalDateTime} value from where the time id's will be generated from.
     * @param numberOfMinutes
     *            Primitive long value how many minutes / time id's needs to be generated.
     * @return {@link List} of {@link LocalDateTime} containing every minute from given startDate.
     */
    public static List<LocalDateTime> generateTimeIdsToCompare(final LocalDateTime startTime, final long numberOfMinutes) {
        final List<LocalDateTime> minuteList = new ArrayList<>();

        for (long i = 0; i < numberOfMinutes; i++) {
            minuteList.add(startTime.plusMinutes(i));
        }

        return minuteList;
    }

    /**
     * Methods formates given {@link List} of {@link LocalDateTime} into HH:mm time format.
     * 
     * @param minuteList
     *            {@link List} of {@link LocalDateTime} which will be processed.
     * @return {@link List} of {@link String} containing processed {@link LocalDateTime} into HH:mm format.
     */
    public static List<String> generateTimeIdLabels(final List<LocalDateTime> minuteList) {
        final List<String> labelList = new ArrayList<>();

        for (final LocalDateTime minute : minuteList) {
            labelList.add(minute.format(TIME_FORMATTER));
        }

        return labelList;
    }

}
