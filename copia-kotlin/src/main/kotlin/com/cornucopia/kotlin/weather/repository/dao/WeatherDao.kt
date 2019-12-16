package com.cornucopia.kotlin.weather.repository.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cornucopia.kotlin.weather.repository.ListWeather
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
    fun bulkInsert(vararg weather: Weather);  // vararg 可变参数

    @Query("delete from weather where date < :date")
    fun deleteOldWeather(date: Date?);  // date: () -> Date, no viable alternative at input 'date'

    @Query("select count(id) from weather where date >= :date")
    fun countAllFutureWeather(date: Date): Int;

    @Query("select id, weatherIconId, date, min, max from weather where date >= :date")
    fun getCurrentWeatherForecasts(date: Date?): LiveData<List<ListWeather>>;

}