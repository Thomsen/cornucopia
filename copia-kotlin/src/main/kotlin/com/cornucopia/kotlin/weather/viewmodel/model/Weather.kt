package com.cornucopia.kotlin.weather.viewmodel.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by thom on 2/2/2018.
 */
@Entity(
        tableName="weather",
        indices = [Index(value = ["date"], unique = true)]  // [] kotlin 1.2+
        // an annotation can't be used as the annotations argument
        //indices = {@Index(value = {"date"}, unique=true)}
)
class Weather {

    // val read only
    // var read and write

//    val vid: Int?;

    @PrimaryKey(autoGenerate = true)
    var id = 0;

    var weatherIconId = 0;

    var date: Date? = null;

    var min = 0.0;

    var max = 0.0;

    var humidity = 0.0;

    var pressure = 0.0;

    var wind = 0.0;

    var degrees = 0.0;

    companion object {

    }

    init {

    }

    // There are multiple good constructors and Room will pick the no-arg constructor
    // add @Ignore
    @Ignore
    constructor() {

    }

    // Constructor used by Room to create
    @Ignore
    constructor(id: Int, weatherIconId: Int, date: Date, min: Double, max: Double,
                humidity: Double, pressure: Double, wind: Double, degrees: Double) {
        this.id = id
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }

    // by network parse
    constructor(weatherIconId: Int, date: Date, min: Double, max: Double,
                humidity: Double, pressure: Double, wind: Double, degrees: Double) {
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }


}