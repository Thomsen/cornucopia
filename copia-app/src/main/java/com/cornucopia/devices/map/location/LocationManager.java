package com.cornucopia.devices.map.location;

public interface LocationManager {

    public void registerLocationListener();

    public void setLocationOption();

    public void start();

    public void stop();
}
