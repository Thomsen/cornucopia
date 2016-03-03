package com.cornucopia.devices.map;

import java.util.ArrayList;

public class GoogleCellIdRequest {

	/**
	 * 版本
	 */
	private String version;
	
	/**
	 * 主机
	 */
	private String host;
	
	/**
	 * 访问令牌
	 */
	private String access_token;
	
	/**
	 * 国家代码
	 */
	private int home_mobile_country_code;
	
	/**
	 * 移动运营商代码
	 */
	private int home_mobile_network_code;
	
	/**
	 * 位置区域代码
	 */
	private String address_language;
	
	/**
	 * 信号类型
	 */
	private String radio_type;
	
	/**
	 * 请求地址
	 */
	private boolean request_address;
	
	/**
	 * 基站信息
	 */
	private ArrayList<CellTowerInfo> cell_towers;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public int getHomeMobileCountryCode() {
		return home_mobile_country_code;
	}

	public void setHomeMobileCountryCode(int home_mobile_country_code) {
		this.home_mobile_country_code = home_mobile_country_code;
	}

	public int getHomeMobileNetworkCode() {
		return home_mobile_network_code;
	}

	public void setHomeMobileNetworkCode(int home_mobile_network_code) {
		this.home_mobile_network_code = home_mobile_network_code;
	}

	public String getAddressLanguage() {
		return address_language;
	}

	public void setAddressLanguage(String address_language) {
		this.address_language = address_language;
	}

	public String getRadioType() {
		return radio_type;
	}

	public void setRadioType(String radio_type) {
		this.radio_type = radio_type;
	}

	public boolean isRequestAddress() {
		return request_address;
	}

	public void setRequestAddress(boolean request_address) {
		this.request_address = request_address;
	}

	public ArrayList<CellTowerInfo> getCellTowers() {
		return cell_towers;
	}

	public void setCellTowers(ArrayList<CellTowerInfo> cell_towers) {
		this.cell_towers = cell_towers;
	}
	
	public void addCellTower(CellTowerInfo cellTower) {
		if (cell_towers == null) {
			cell_towers = new ArrayList<CellTowerInfo>();
		}
		
		cell_towers.add(cellTower);
	}
	
}
