package com.cornucopia.ui.dynamic;

import java.util.ArrayList;

public class LayoutDto {

	// json�� basic class to Object
	
	private Integer type;
	
	private String label;
	
	private Boolean isLabelVisiable;
	
	private Boolean isEnabled;
	
	private Boolean isChecked;
	
//	private String value;

	private Object value;
	
	/**
	 * ���ֵĹ�������
	 */
	private ArrayList<LayoutDto> inlineLayout;
	
	public LayoutDto() {
		isEnabled = true; // Ĭ�Ͽɲ���
		isLabelVisiable = true; // Ĭ����ʾLabel
		isChecked = false; // ��RadioButton��Ĭ��ûѡ��
	}
	
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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
//	public String getValue() {
//		return value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}

	public Object getValue() {
		return value;
	}

//	public void setValue(Object value) {
//		this.value = value;
//	}
	
	public <T> void setValue(T value) {
		this.value = value;
	}

	public ArrayList<LayoutDto> getInlineLayout() {
		return inlineLayout;
	}

	public void setInlineLayout(ArrayList<LayoutDto> inlineLayout) {
		this.inlineLayout = inlineLayout;
	}

	public boolean isLabelVisiable() {
		return isLabelVisiable;
	}

	public void setIsLabelVisiable(Boolean isLabelVisiable) {
		this.isLabelVisiable = isLabelVisiable;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}
