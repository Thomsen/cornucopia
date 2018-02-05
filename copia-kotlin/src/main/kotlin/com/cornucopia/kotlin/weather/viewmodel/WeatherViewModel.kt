package com.cornucopia.kotlin.weather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.cornucopia.kotlin.weather.repository.ListWeather
import com.cornucopia.kotlin.weather.repository.WeatherRepository

/**
 * Created by thom on 3/2/2018.
 */
class WeatherViewModel: ViewModel {

    private lateinit var mRepository: WeatherRepository

    var mForecast: LiveData<List<ListWeather>>
        get() = mForecast


    constructor(repository: WeatherRepository) {
        mRepository = repository;
        mForecast = mRepository.getCurrentWeatherForecasts();
    }

}