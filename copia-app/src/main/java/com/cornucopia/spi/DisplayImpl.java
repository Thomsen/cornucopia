package com.cornucopia.spi;

import com.cornucopia.component.spi.Display;

public class DisplayImpl implements Display {

    @Override
    public String message() {
        return "app display";
    }

}
