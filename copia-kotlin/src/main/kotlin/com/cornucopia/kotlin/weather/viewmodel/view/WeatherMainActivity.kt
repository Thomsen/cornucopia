package com.cornucopia.kotlin.weather.viewmodel.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.weather.repository.ListWeather
import com.cornucopia.kotlin.weather.utils.InjectorUtils
import com.cornucopia.kotlin.weather.viewmodel.WeatherViewModel
import com.cornucopia.kotlin.weather.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_weather_main.*
import java.util.Date;

/**
 * Created by thom on 2/2/2018.
 */
class WeatherMainActivity : AppCompatActivity(), ForecastAdapter.ForecastAdapterOnItemClickHandler {

    private lateinit var mForecastAdapter: ForecastAdapter

    lateinit var mModelView: WeatherViewModel

    var mWeathers : List<ListWeather> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather_main)

        recy_forecast.layoutManager =
            LinearLayoutManager(this)

        var factory = InjectorUtils.provideViewModelFactory(this);

        mModelView = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)

        mModelView.mForecast.observe(this, Observer {
            weatherEntries ->
            if (null != weatherEntries) {
                mForecastAdapter = ForecastAdapter(this, weatherEntries,this)
                recy_forecast.adapter = mForecastAdapter
            }
        });

    }

    override fun onItemClick(date: Date) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}