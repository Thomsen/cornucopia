package com.cornucopia.ticketsmanager;

import java.util.ArrayList;

import android.app.Application;
import android.widget.Toast;

public class TicketsManagerApplication extends Application {

	private ArrayList<Tickets> currentTickets;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Toast.makeText(this, "tickets manager application", Toast.LENGTH_SHORT).show();
		
		if (null == currentTickets) {
			currentTickets = new ArrayList<Tickets>();
		}
	}

	// 设置和获取当前的ticket
	public ArrayList<Tickets> getCurrentTickets() {
		return currentTickets;
	}

	public void setCurrentTickets(ArrayList<Tickets> currentTicket) {
		this.currentTickets = currentTicket;
	}
	
	// 添加ticket
	public void addTicket(Tickets ticket) {
		//
		assert(null != ticket);
		
		// 使用对象前最好对对象进行非空判断
		if (null == currentTickets) {
			currentTickets = new ArrayList<Tickets>();
		}
		
		currentTickets.add(ticket);
	}
	

}
