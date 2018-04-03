package com.cornucopia.kotlin.weather.utils

import android.net.Uri
import androidx.net.toUri
import com.cornucopia.kotlin.weather.repository.network.WeatherNetworkDataSource
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by thom on 4/2/2018.
 */
class NetworkUtils {

    companion object {

        val URL_DYNAMIC_WEATHER = "https://andfun-weather.udacity.com/weather";

        val URL_STATIC_WEATHER = "https://andfun-weather.udacity.com/staticweather";

        var URL_BASE_FORECAST = URL_DYNAMIC_WEATHER;

        val format = "json";

        val units = "metric";

        val PARAM_QUERY_ = "q"

        val PARAM_FORMAT_ = "mode"

        val PARAM_UNITS_ = "units";

        val PARAM_DAYS = "cnt";

        fun getUrl(): URL {
            var locationQuery = "Mountain View, CA";
            return buildUrlWithLocationQuery(locationQuery);
        }

        private fun buildUrlWithLocationQuery(locationQuery: String): URL {
//            var weatherQueryUri = Uri.parse(URL_BASE_FORECAST).buildUpon()
            var weatherQueryUri = URL_BASE_FORECAST.toUri().buildUpon()  // toUri with android-ktx
                    .appendQueryParameter(PARAM_QUERY_, locationQuery)
                    .appendQueryParameter(PARAM_FORMAT_, format)
                    .appendQueryParameter(PARAM_UNITS_, units)
                    .appendQueryParameter(PARAM_DAYS, Integer.toString(WeatherNetworkDataSource.NUM_DAYS))
                    .build();


            var weatherQueryUrl = URL(weatherQueryUri.toString());
            return weatherQueryUrl;

        }

       fun getResponseFromHttpUrl(url: URL): String {
           var connection: HttpURLConnection = url.openConnection() as HttpURLConnection;

           try {
               var instream = connection.getInputStream();

               var scanner = Scanner(instream);

               scanner.useDelimiter("\\A");

               var hasInput = scanner.hasNext();

               var response: String = "";

               if (hasInput) {
                   response = scanner.next();
               }

               return response;
           } finally {
               connection.disconnect();
           }
       }

    }
}