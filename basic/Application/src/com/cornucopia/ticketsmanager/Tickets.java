package com.cornucopia.ticketsmanager;

import java.io.Serializable;

public class Tickets implements Serializable {
	
	/**
	 * Eclipse generate uid
	 */
	private static final long serialVersionUID = -1222642980393012745L;
	
	private String name;
	private boolean complete = false;
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public boolean isComplete() {
		return complete;
	}
	
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public void toggleComplete() {
		complete = ! complete;
	}
	
	
}
