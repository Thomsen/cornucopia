package com.cornucopia.ui.dynamic;

import java.util.ArrayList;

public class LayoutDataDto {

    private Integer type; // Object to json�������ܲ���
	
	private String label;
	
	private String value;
	
	/**
	 * ���������е����
	 */
	private ArrayList<LayoutDataDto> inlineLayoutData;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<LayoutDataDto> getInlineLayoutData() {
		return inlineLayoutData;
	}

	public void setInlineLayoutData(ArrayList<LayoutDataDto> inlineLayoutData) {
		this.inlineLayoutData = inlineLayoutData;
	}

}
