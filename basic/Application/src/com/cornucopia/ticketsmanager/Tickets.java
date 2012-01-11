package com.cornucopia.ticketsmanager;

public class Tickets {
	private String name;

	public Tickets(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// 将会返回类的信息
//		return super.toString(); 
	
		// 正确返回
		return name;
	}
	
	
}
