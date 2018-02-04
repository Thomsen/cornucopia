package com.cornucopia.kotlin.weather.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.cornucopia.kotlin.weather.viewmodel.model.Weather
import java.util.*

/**
 * Created by thom on 3/2/2018.
 */
@Dao
public interface WeatherDao {

    @Query("select * from weather where date = :date")
    fun findWeatherByDate(date: Date): LiveData<Weather>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(vararg weather: Weather);

    @Query("delete from weather date < :date")
    fun deleteOldWeather(date: Date);

}