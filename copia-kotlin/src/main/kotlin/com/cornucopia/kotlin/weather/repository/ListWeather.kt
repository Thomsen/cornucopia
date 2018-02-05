package com.cornucopia.kotlin.weather.repository

import java.util.*

/**
 * Created by thom on 4/2/2018.
 */
class ListWeather {

    var id = 0;
    var weatherIconId = 0;
    var date: Date? = null;

    var min = 0.0;
    var max = 0.0;

    constructor(id: Int, weatherIconId: Int, date: Date?, min: Double, max: Double) {
        this.id = id
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
    }
}