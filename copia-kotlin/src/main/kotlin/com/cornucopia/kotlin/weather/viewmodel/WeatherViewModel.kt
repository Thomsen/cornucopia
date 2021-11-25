package com.cornucopia.kotlin.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cornucopia.kotlin.weather.repository.ListWeather
import com.cornucopia.kotlin.weather.repository.WeatherRepository

/**
 * Created by thom on 3/2/2018.
 */
class WeatherViewModel: ViewModel {

    private var mRepository: WeatherRepository

    // get() = mForecast  dead cycle
    var mForecast: LiveData<List<ListWeather>>

    init {
        // with val

    }

    constructor(repository: WeatherRepository) {
        mRepository = repository;
        mForecast = mRepository.getCurrentWeatherForecasts();
    }

}