package com.cornucopia.devices.map;

public class CellTowerInfo {

	/**
	 * 移动基站
	 */
	private long cell_id;
	
	/**
	 * 地区区域码
	 */
	private int location_area_code;
	
	/**
	 * 移动用户所属国家代号
	 */
	private int mobile_country_code;
	
	/**
	 * 移动网号码
	 */
	private int mobile_network_code;
	
	/**
	 * 小区的时间提前量
	 */
	private int timing_advance;
	
	private int age;

	public long getCellId() {
		return cell_id;
	}

	public void setCellId(long cell_id) {
		this.cell_id = cell_id;
	}

	public int getLocationAreaCode() {
		return location_area_code;
	}

	public void setLocationAreaCode(int location_area_code) {
		this.location_area_code = location_area_code;
	}

	public int getMobilCountryCode() {
		return mobile_country_code;
	}

	public void setMobileCountryCode(int mobile_country_code) {
		this.mobile_country_code = mobile_country_code;
	}

	public int getMobileNetworkCode() {
		return mobile_network_code;
	}

	public void setMobileNetworkCode(int mobile_network_code) {
		this.mobile_network_code = mobile_network_code;
	}

	public int getTimingAdvance() {
		return timing_advance;
	}

	public void setTimingAdvance(int timing_advance) {
		this.timing_advance = timing_advance;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
