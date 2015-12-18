package com.cornucopia.devices.map;

public class CellIdInfo {
	/**
	 * 基站编号
	 */
	private int cellId;
	
	/**
	 * 国家代码
	 */
	private int mcc;
	
	/**
	 * 网络代码
	 */
	private int mnc;
	
	/**
	 * 区域代码
	 */
	private int lac;
	
	/**
	 * 信号类型
	 */
	private String radioType;

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}
	
}
