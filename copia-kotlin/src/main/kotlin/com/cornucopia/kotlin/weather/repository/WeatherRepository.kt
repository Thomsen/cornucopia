package com.cornucopia.kotlin.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cornucopia.kotlin.weather.WeatherExecutors
import com.cornucopia.kotlin.weather.repository.dao.WeatherDao
import com.cornucopia.kotlin.weather.repository.network.WeatherNetworkDataSource
import com.cornucopia.kotlin.weather.utils.DateUtils
import com.cornucopia.kotlin.weather.viewmodel.model.Weather

/**
 * Created by thom on 3/2/2018.
 */
class WeatherRepository {

    // lateinit is unnecessary: definitely initialized in constructors
    private var mExecutors: WeatherExecutors

    private var mDataSource: WeatherNetworkDataSource

    private var mWeatherDao: WeatherDao

    constructor(weatherDao: WeatherDao, dataSource: WeatherNetworkDataSource,
                executors: WeatherExecutors) {
        mWeatherDao = weatherDao;
        mDataSource = dataSource;
        mExecutors = executors;

        var networkData = mDataSource.getCurrentWeatherForecasts();

        // as long as
        networkData.observeForever (Observer<Array<Weather>> {
            newForecastsFromNetwork ->
            mExecutors.diskIO!!.execute({

                deleteOldData();

                mWeatherDao.bulkInsert(*newForecastsFromNetwork!!); // * vararg params ; !! null check

            })
        })
    }

    companion object {

        private var sInstance: WeatherRepository? = null; // lateinit property has not been initialized

        fun getInstance(dao: WeatherDao, dataSrouce: WeatherNetworkDataSource,
                        executors: WeatherExecutors): WeatherRepository {
            if (null == sInstance) {
                synchronized(this) {
                    sInstance = WeatherRepository(dao, dataSrouce, executors);
                }
            }

            return sInstance!!;
        }
    }

    private var isInitialized: Boolean = false;

    fun initializeData() {
        if (isInitialized ) {
            return ;
        }
        isInitialized = true;

        mDataSource.scheduleRecurringFetchWeatherSync()

        mExecutors.diskIO!!.execute({
            if (isFetchNeeded()) {
                startFetchWeatherService();
            }
        });
    }

    private fun isFetchNeeded(): Boolean {
        var today = DateUtils.getNormalizedUtcDateForToday();
        var count = mWeatherDao.countAllFutureWeather(today)
        return (count < WeatherNetworkDataSource.NUM_DAYS)
    }

    private fun startFetchWeatherService() {
        mDataSource.startFetchWeatherService();
    }

    private fun deleteOldData() {
        var today = com.cornucopia.kotlin.weather.utils.DateUtils.getNormalizedUtcDateForToday()

        mWeatherDao.deleteOldWeather(today)
    }

    fun getCurrentWeatherForecasts(): LiveData<List<ListWeather>> {
        initializeData();

        var today = DateUtils.getNormalizedUtcDateForToday();
        return mWeatherDao.getCurrentWeatherForecasts(today);
    }


}