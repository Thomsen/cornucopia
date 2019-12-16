package com.cornucopia.kotlin.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornucopia.kotlin.weather.repository.WeatherRepository

/**
 * Created by thom on 4/2/2018.
 */
class WeatherViewModelFactory: ViewModelProvider.NewInstanceFactory {

    private var mRepository: WeatherRepository

    constructor(repository: WeatherRepository) {
        mRepository = repository;
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(mRepository) as T; // as and is,
        // as: WeatherViewModelFactory cannot be cast to android.arch.lifecycle.ViewModel
    }

}