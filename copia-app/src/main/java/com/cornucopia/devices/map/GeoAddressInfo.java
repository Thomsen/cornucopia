package com.cornucopia.devices.map;

import java.io.Serializable;

public class GeoAddressInfo implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -6003358794518347593L;

    /**
	 * 街道号
	 */
	private String street_number;
	
	/**
	 * 街道名称
	 */
	private String street;
	
	/**
	 * 邮政编码
	 */
	private String postal_code;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 县
	 */
	private String county;
	
	/**
	 * 区域(省)
	 */
	private String region;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * 国家编码
	 */
	private String country_code;

	public String getStreetNumber() {
		return street_number;
	}

	public void setStreetNumber(String street_number) {
		this.street_number = street_number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postal_code;
	}

	public void setPostalCode(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return country_code;
	}

	public void setCountryCode(String country_code) {
		this.country_code = country_code;
	}
	
	public String toString() {
	    return getCountry() + "(" + getCountryCode() + ")," +
	            getRegion() + getCity() + getCounty() +
	                getStreet() + "(" + getStreetNumber() + ")," +
	                "邮政编码 " + getPostalCode();
	}
	
}
