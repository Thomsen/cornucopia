package com.cornucopia.kotlin.weather.utils

import android.content.Context
import com.cornucopia.kotlin.weather.WeatherExecutors
import com.cornucopia.kotlin.weather.repository.WeatherDatabase
import com.cornucopia.kotlin.weather.repository.WeatherRepository
import com.cornucopia.kotlin.weather.repository.network.WeatherNetworkDataSource
import com.cornucopia.kotlin.weather.viewmodel.WeatherViewModelFactory

/**
 * Created by thom on 4/2/2018.
 */
class InjectorUtils {
    companion object {

        fun provideRepository(context: Context): WeatherRepository {
            var database = WeatherDatabase.getInstance(context.applicationContext);
            var executors = WeatherExecutors.INSTANCE;
            var dataSource = WeatherNetworkDataSource.getInstance(context.applicationContext, executors);
            return WeatherRepository.getInstance(database.weatherDao(), dataSource, executors);
        }


        fun provideNetworkDataSource(context: Context): WeatherNetworkDataSource {
            provideRepository(context)
            var executors = WeatherExecutors.INSTANCE;
            return WeatherNetworkDataSource.getInstance(context.applicationContext, executors);
        }

        fun provideViewModelFactory(context: Context): WeatherViewModelFactory {
            var repository = provideRepository(context.applicationContext);
            return WeatherViewModelFactory(repository)
        }

    }
}