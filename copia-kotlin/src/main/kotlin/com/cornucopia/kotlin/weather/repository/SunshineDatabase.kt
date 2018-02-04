package com.cornucopia.kotlin.weather.repository

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.cornucopia.kotlin.weather.repository.dao.WeatherDao

/**
 * Created by thom on 3/2/2018.
 */
abstract class SunshineDatabase private constructor(): RoomDatabase() {

    companion object {
        fun getInstance(context: Context) = Resource(context).INSTANCE;
    };

    public abstract fun weatherDao() : WeatherDao;

    // no params can use object expression

    private class Resource(val context: Context) {

        val DATABASE_NAME: String = "weather";

        val INSTANCE = Room.databaseBuilder(context.applicationContext,
                SunshineDatabase::class.java, DATABASE_NAME);

    }
}