package com.cornucopia.kotlin.weather.repository

import android.arch.persistence.room.*
import android.content.Context
import com.cornucopia.kotlin.weather.repository.dao.WeatherDao
import com.cornucopia.kotlin.weather.utils.DateConverter
import com.cornucopia.kotlin.weather.viewmodel.model.Weather

/**
 * Created by thom on 3/2/2018.
 */
@Database(
        entities = [(Weather::class)],
        version = 1
)
@TypeConverters(
    DateConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context) = Resource(context).INSTANCE;
    };


    abstract fun weatherDao() : WeatherDao;

    // no params can use object expression

    private class Resource(val context: Context) {

        val DATABASE_NAME: String = "weather";

        val INSTANCE = Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, DATABASE_NAME).build();

    }
}