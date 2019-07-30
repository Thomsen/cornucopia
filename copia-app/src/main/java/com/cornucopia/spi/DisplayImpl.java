package com.cornucopia.spi;

import com.cornucopia.jetpack.spi.Display;

public class DisplayImpl implements Display {

    @Override
    public String message() {
        return "app display";
    }

}
