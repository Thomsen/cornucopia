package com.cornucopia.mvp.spi;


import com.cornucopia.jetpack.spi.Display;

public class MvpDisplay implements Display {

    @Override
    public String message() {
        return "mvp message";
    }

}
