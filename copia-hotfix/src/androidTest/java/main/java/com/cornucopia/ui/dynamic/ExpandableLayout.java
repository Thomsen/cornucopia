package com.cornucopia.ui.dynamic;

import java.util.ArrayList;

public class ExpandableLayout {
	
	/**
	 * 防止标题名重复的标识
	 */
	private String groupId;
	
	/**
	 * 扩展的标题名
	 */
	private String groupName;
	
	/**
	 * 布局配置信息
	 */
	private ArrayList<LayoutDto> layout;
	
	/**
	 * 数据信息
	 */
	private ArrayList<LayoutDataDto> data;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<LayoutDto> getLayout() {
		return layout;
	}

	public void setLayout(ArrayList<LayoutDto> layout) {
		this.layout = layout;
	}

	public ArrayList<LayoutDataDto> getData() {
		return data;
	}

	public void setData(ArrayList<LayoutDataDto> data) {
		this.data = data;
	}

}
