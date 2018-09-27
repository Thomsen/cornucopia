package com.cornucopia.kotlin.weather.viewmodel.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.weather.repository.ListWeather
import kotlinx.android.synthetic.main.layout_forecast_item.view.*
import java.util.Date;

/**
 * Created by thom on 4/2/2018.
 */
class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastHolder> {

    var mWeathers: List<ListWeather> = listOf()

    var mContext: Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val inflate = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflate.inflate(R.layout.layout_forecast_item, parent, false)
        return ForecastHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mWeathers!!.count()
    }

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        var weather = mWeathers.get(position)

        holder.itemDate.setText("" + weather.date)
        holder.itemInterval.setText("" + weather.min + " ~ " + weather.max)
    }


    constructor(context: Context, weathers: List<ListWeather>,
                clickHandler: ForecastAdapterOnItemClickHandler) {
        mContext = context
        mWeathers = weathers
    }

    class ForecastHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var itemDate : TextView
        var itemInterval : TextView

        init {
            itemDate = itemView!!.findViewById(R.id.tv_forecast_date)
            itemInterval = itemView!!.tv_forecast_interval
        }
    }

    interface ForecastAdapterOnItemClickHandler {
        fun onItemClick(date: Date);
    }
}

