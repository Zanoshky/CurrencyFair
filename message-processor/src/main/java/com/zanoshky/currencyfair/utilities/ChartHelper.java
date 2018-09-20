package com.zanoshky.currencyfair.utilities;

import java.time.LocalDateTime;
import java.util.List;

import com.zanoshky.currencyfair.model.CurrencyPair;
import com.zanoshky.currencyfair.model.CurrencyPairDetail;

public final class ChartHelper {

    public static long countsPerMinute(final List<CurrencyPairDetail> detailList, final CurrencyPair pair, final LocalDateTime time) {
        for (final CurrencyPairDetail detail : detailList) {
            if (detail.getCurrencyPair().equals(pair) && detail.getCurrencyPairDetailIdentity().getTimeId().equals(time)) {
                return detail.getCount();
            }
        }

        return 0;
    }

    public static String formChartName(final CurrencyPair pair) {
        return "Message volume of: " + pair.getCurrencyFrom() + " -> "
                + pair.getCurrencyTo();
    }
}
