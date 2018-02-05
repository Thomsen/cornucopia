package com.cornucopia.kotlin.weather.repository.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import com.cornucopia.kotlin.weather.WeatherExecutors
import com.cornucopia.kotlin.weather.utils.DateUtils
import com.cornucopia.kotlin.weather.utils.NetworkUtils
import com.cornucopia.kotlin.weather.viewmodel.model.Weather
import org.json.JSONObject
import java.net.HttpURLConnection
import java.sql.Driver
import java.util.*

/**
 * Created by thom on 3/2/2018.
 */
class WeatherNetworkDataSource {

    private var mExecutors: WeatherExecutors? = null;

    private lateinit var mContext: Context;

    // livedata storing the latest downloaded weather forecasts
    private lateinit var mDownloadedWeatherForecasts: MutableLiveData<Array<Weather>>;


    constructor() {

    }

    constructor(context: Context, executors: WeatherExecutors) {
        mContext = context;
        mExecutors = executors;

        mDownloadedWeatherForecasts = MutableLiveData<Array<Weather>>();
    }

    companion object {

        @Volatile
        private var sDataSource: WeatherNetworkDataSource? = null;

        fun getInstance(context: Context, executors: WeatherExecutors): WeatherNetworkDataSource {
            if (null == sDataSource) {
                synchronized(WeatherNetworkDataSource::class) {
                    if (null == sDataSource) {
                        sDataSource = WeatherNetworkDataSource(context, executors);
                    }
                }
            }

            return sDataSource!!;
        }

        val INSTANCE by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WeatherNetworkDataSource()
        }

        val NUM_DAYS: Int = 14;
    }


    fun getCurrentWeatherForecasts(): LiveData<Array<Weather>> {
        return mDownloadedWeatherForecasts;
    }


    fun fetchWeather() {
        mExecutors!!.networkIO!!.execute({
            try {
                // fetch weather data
                var weatherRequestUrl = NetworkUtils.getUrl();

                var jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                var response = OpenWeatherJsonParser().parse(jsonWeatherResponse);

                if (null != response && response.mWeatherForecast.size > 0) {
                    mDownloadedWeatherForecasts.postValue(response.mWeatherForecast);
                }

            } catch (e: Exception) {
                e.printStackTrace();
            }
        });
    }

    fun scheduleRecurringFetchWeatherSync() {

    }


    fun startFetchWeatherService() {
        var intent = Intent(mContext, WeatherSyncIntentService::class.java);
        mContext.startService(intent);

    }


    class OpenWeatherJsonParser {

        val OWN_MESSAGE_CODE = "cod";

        val OWN_PRESSURE = "pressure";

        val OWN_HUMIDITY = "humidity";

        val OWN_WINDSPEED = "speed";

        val OWN_WIND_DIRECTION = "deg";

        val OWN_TEMPERATURE = "temp";

        val OWN_MAX = "max";

        val OWN_MIN = "min";

        val OWN_WEATHER = "weather";

        val OWN_WEATHER_ID = "id";

        val OWN_LIST = "list";


        fun parse(jsonWeatherResponse: String): WeatherResponse? {

            var forecastJson = JSONObject(jsonWeatherResponse);

            if (hasHttpError(forecastJson)) {
                return null;
            }

            var weatherForecast = fromJson(forecastJson);

            return WeatherResponse(weatherForecast);
        }

        private fun fromJson(forecastJson: JSONObject): Array<Weather> {
            var jsonWeatherArray = forecastJson.getJSONArray(OWN_LIST)
            var weatherEntries = Array<Weather>(jsonWeatherArray.length(), {
                Weather()
            });

            var normalizedUtcStartDay = DateUtils.getNormalizedUtcMsFroToday();

            var size = jsonWeatherArray.length();
            var i = 0;
            while (size > 0) {
                size --;
                var dayForcast = jsonWeatherArray.getJSONObject(i);

                var dateTimeMillis = normalizedUtcStartDay + DateUtils.DAY_IN_MMILLIS * i;
                i ++;

                var weather = fromJson(dayForcast, dateTimeMillis) as Weather;
                weatherEntries[i] = weather;
            }

            return weatherEntries;
        }

        fun fromJson(forecastJson: JSONObject, dateTimeMillis: Long): Weather {

            var pressure = forecastJson.getDouble(OWN_PRESSURE)
            var humidity = forecastJson.getDouble(OWN_HUMIDITY)
            var windSpeed = forecastJson.getDouble(OWN_WINDSPEED)
            var windDirection = forecastJson.getDouble(OWN_WIND_DIRECTION)

            var weatherObject = forecastJson.getJSONArray(OWN_WEATHER).getJSONObject(0)

            var weatherId = weatherObject.getInt(OWN_WEATHER_ID)

            var temperatureObject = forecastJson.getJSONObject(OWN_TEMPERATURE)

            var max = temperatureObject.getDouble(OWN_MAX)
            var min = temperatureObject.getDouble(OWN_MIN)

            return Weather(weatherId, Date(dateTimeMillis), max, min, humidity,
                    pressure, windSpeed, windDirection)

        }

        private fun hasHttpError(forecastJson: JSONObject): Boolean {
            if (forecastJson.has(OWN_MESSAGE_CODE)) {
                var errorCode = forecastJson.getInt(OWN_MESSAGE_CODE);

                when(errorCode) {
                    HttpURLConnection.HTTP_OK -> return false;
                    HttpURLConnection.HTTP_NOT_FOUND -> return true;
                    else -> return true;
                }
            }

            return false;
        }

    }
}