package com.cornucopia.kotlin.nav

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.R.id.tv_second
import kotlinx.android.synthetic.main.activity_nav_second.*

class NavigationSecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nav_second)

        var message = intent.getStringExtra("message")

        Snackbar.make((tv_second as TextView), "second: " + message, Snackbar.LENGTH_SHORT).show()
    }

}