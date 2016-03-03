package com.cornucopia.devices.map;

import java.io.Serializable;

public class GeoLocationInfo implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -9153565796960522760L;

    /**
	 * 纬度
	 */
	private double latitude;
	
	/**
	 * 经度
	 */
	private double longitude;
	
	/**
	 * 海拔
	 */
	private double altitude;
	
	/**
	 * 精度
	 */
	private double accuracy;
	
	/**
	 * 地址准确度
	 */
	private double altitude_accuracy;
	
	/**
	 * 地址信息
	 */
	private GeoAddressInfo address;
	
	/**
	 * 地址
	 */
	private String strAddress;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getAltitudeAccuracy() {
		return altitude_accuracy;
	}

	public void setAltitudeAccuracy(double altitude_accuracy) {
		this.altitude_accuracy = altitude_accuracy;
	}

	public GeoAddressInfo getAddress() {
		return address;
	}

	public void setAddress(GeoAddressInfo address) {
		this.address = address;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
	}
	
	
}
