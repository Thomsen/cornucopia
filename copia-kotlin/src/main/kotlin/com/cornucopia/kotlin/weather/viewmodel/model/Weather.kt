package com.cornucopia.kotlin.weather.viewmodel.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
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

    var id: Int = 0;

    var weatherIconId: Int = 0;

    var date: Date? = null;

    var min: Double = 0.0;

    var max: Double = 0.0;


    var humidity: Double = 0.0;

    var pressure: Double = 0.0;

    var wind: Double = 0.0;

    var degrees: Double = 0.0;


    // Constructor used by Room to create
    constructor(id: Int, weatherIconId: Int, date: Date?, min: Double, max: Double,
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


}