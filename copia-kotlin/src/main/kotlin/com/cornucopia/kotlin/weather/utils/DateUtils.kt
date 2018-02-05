package com.cornucopia.kotlin.weather.utils

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by thom on 4/2/2018.
 */
class DateUtils {

    companion object {

        val DAY_IN_MMILLIS = TimeUnit.DAYS.toMillis(1);

        fun getNormalizedUtcMsFroToday(): Long {

            var utcNowMillis = System.currentTimeMillis()

            var currentTimeZone = TimeZone.getDefault()

            var gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis)

            var timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis;

            var daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis)

            return TimeUnit.DAYS.toMillis(daysSinceEpochLocal)
        }

        fun getNormalizedUtcDateForToday() : Date {
            var normalizedMilli = getNormalizedUtcMsFroToday();
            return Date(normalizedMilli);
        }
    }


}