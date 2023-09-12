package com.cornucopia.kotlin.nav

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.R.id.tv_second

class NavigationSecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nav_second)

        var message = intent.getStringExtra("message")

        Snackbar.make((tv_second as TextView), "second: " + message, Snackbar.LENGTH_SHORT).show()
    }

}