package com.cornucopia.kotlin.recy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.cornucopia.kotlin.R
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_recy_flexbox.*

class RecyFlexboxActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recy_flexbox);

        var layoutManager = FlexboxLayoutManager(this, FlexDirection.COLUMN)
        layoutManager.flexDirection = FlexDirection.ROW  // row vertical scroll， column horizontal scroll
        layoutManager.justifyContent = JustifyContent.FLEX_END
        layoutManager.flexWrap = FlexWrap.WRAP

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

//        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//            var imageView = ImageView(parent!!.context)
//            return ViewHolder(imageView)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//            holder?.imageView!!.setImageResource(items.get(position))
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var imageView = ImageView(parent.context)
            imageView.setPadding(10, 10, 10, 10)
            return ViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imageView.setImageResource(items.get(position))
        }

        inner class ViewHolder(val imageView : ImageView) : RecyclerView.ViewHolder(imageView) {

        }
    }
}