package com.cornucopia.kotlin.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornucopia.kotlin.R

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation);
    }
}