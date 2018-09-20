package com.zanoshky.currencyfair.utilities;

import java.time.LocalDateTime;
import java.util.List;

import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;

public final class ChartHelper {

    /**
     * Method compares each {@link CurrencyPair} and the {@link LocalDateTime} with all {@link CurrencyPairDetail}'s in order to get a message count.
     * 
     * @param details
     *            {@link List} of {@link CurrencyPairDetail} on which the search will be performed.
     * @param pair
     *            {@link CurrencyPair} is the targeted currency which is being searched for.
     * @param time
     *            {@link LocalDateTime} is the targeted time which is being searched for.
     * @return Number of messages received for specific currency in specif minute as primitive long value
     */
    public static long countsPerMinute(final List<CurrencyPairDetail> details, final CurrencyPair pair, final LocalDateTime time) {
        for (final CurrencyPairDetail detail : details) {
            if (detail.getCurrencyPair().equals(pair) && detail.getCurrencyPairDetailIdentity().getTimeId().equals(time)) {
                return detail.getCount();
            }
        }

        return 0;
    }

    /**
     * Method forms a Chart Name which will decorate the front end Chart, by adding prefix and suffix from given currency pair.
     * 
     * @param pair
     *            {@link CurrencyPair} from which currencies names are extracted to form a Chart Name.
     * @return Formatted {@link String} to represent a specific Currency Chart.
     */
    public static String formChartName(final CurrencyPair pair) {
        return "Message volume of: " + pair.getCurrencyFrom() + " -> "
                + pair.getCurrencyTo();
    }
}
