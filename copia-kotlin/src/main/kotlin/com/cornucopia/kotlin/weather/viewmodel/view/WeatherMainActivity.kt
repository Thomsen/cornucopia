package com.cornucopia.kotlin.weather.viewmodel.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.cornucopia.kotlin.R
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

    lateinit  var mModelView: WeatherViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather_main)

        recy_forecast.layoutManager = LinearLayoutManager(this)

        mForecastAdapter = ForecastAdapter(this, this)

        var factory = InjectorUtils.provideViewModelFactory(this);

        mModelView = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)

        mModelView.mForecast.observe(this, Observer {
            weatherEntries ->
            if (null != weatherEntries) {

            }
        });

    }

    override fun onItemClick(date: Date) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}