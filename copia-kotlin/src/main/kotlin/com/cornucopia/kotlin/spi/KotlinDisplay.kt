package com.cornucopia.kotlin.spi

import com.cornucopia.jetpack.spi.Display

class KotlinDisplay : Display {


    override fun message(): String {
        return "kotlin message"
    }


}