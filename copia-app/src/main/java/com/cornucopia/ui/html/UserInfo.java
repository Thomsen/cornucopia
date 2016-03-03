package com.cornucopia.ui.html;

class UserInfo {
	/**
     *
     */
    private String name;
	private String sex;
	private String date;
	private String contact;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public UserInfo(String name, String sex, String date, String contact) {
		super();
		this.name = name;
		this.sex = sex;
		this.date = date;
		this.contact = contact;
	}

}