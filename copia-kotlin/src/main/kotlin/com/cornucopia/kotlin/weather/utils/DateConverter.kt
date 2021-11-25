package com.cornucopia.kotlin.weather.utils

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by thom on 4/2/2018.
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {  // not in companion
        return if (timestamp == null)  null else Date(timestamp);
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return if (date == null) null else date.time;
    }

}