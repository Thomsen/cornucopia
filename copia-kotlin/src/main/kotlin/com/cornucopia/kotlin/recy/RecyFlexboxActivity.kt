package com.cornucopia.kotlin.recy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.cornucopia.kotlin.R
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_recy_flexbox.*

class RecyFlexboxActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recy_flexbox);

        var layoutManager = FlexboxLayoutManager(this, 4)
        layoutManager.flexDirection = FlexDirection.COLUMN
        layoutManager.justifyContent = JustifyContent.FLEX_END

        recy_flexbox.layoutManager = layoutManager


        var items: MutableList<Int> = mutableListOf()
        for (i in 0..20) {
            items.add(i, R.drawable.ic_launcher_background);
            if (0 == (i % 3)) {
                items.add(R.mipmap.ic_nine_table)
            }
        }
        recy_flexbox.adapter = ImageAdapter(items)

    }

    class ImageAdapter(val items : List<Int>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            var imageView = ImageView(parent!!.context)
            return ViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.imageView!!.setImageResource(items.get(position))
        }


        inner class ViewHolder(val imageView : ImageView) : RecyclerView.ViewHolder(imageView) {

        }
    }
}