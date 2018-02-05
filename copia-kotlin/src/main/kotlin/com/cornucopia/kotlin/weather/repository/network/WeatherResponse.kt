package com.cornucopia.kotlin.weather.repository.network

import com.cornucopia.kotlin.weather.viewmodel.model.Weather
import org.jetbrains.annotations.NotNull

/**
 * Created by thom on 4/2/2018.
 */
class WeatherResponse {

    @NotNull
    lateinit var mWeatherForecast: Array<Weather>;

    constructor(weatherForecast: Array<Weather>) {
        mWeatherForecast = weatherForecast;
    }


}