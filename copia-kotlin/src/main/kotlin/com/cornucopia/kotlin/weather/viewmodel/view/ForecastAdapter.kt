package com.cornucopia.kotlin.weather.viewmodel.view

import android.content.Context
import java.util.Date;

/**
 * Created by thom on 4/2/2018.
 */
class ForecastAdapter {

    constructor(context: Context, clickHandler: ForecastAdapterOnItemClickHandler) {

    }

    interface ForecastAdapterOnItemClickHandler {
        fun onItemClick(date: Date);
    }
}

