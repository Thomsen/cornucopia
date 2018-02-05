package com.cornucopia.kotlin.weather.repository.network

import android.app.IntentService
import android.content.Intent
import com.cornucopia.kotlin.weather.utils.InjectorUtils
import javax.inject.Inject

/**
 * Created by thom on 4/2/2018.
 */
class WeatherSyncIntentService: IntentService("WeatherSyncIntentService") {


    override fun onHandleIntent(intent: Intent?) {
        var dataSource = InjectorUtils.provideNetworkDataSource(this.applicationContext);
        dataSource.fetchWeather()

    }

}