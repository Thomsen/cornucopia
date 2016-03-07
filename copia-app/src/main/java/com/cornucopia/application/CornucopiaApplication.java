package com.cornucopia.application;

import java.util.ArrayList;

import android.support.multidex.MultiDexApplication;

import com.cornucopia.aspect.dexposed.DexposedHook;
import com.cornucopia.hotfix.Hotfix;
import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.TicketsSQLiteOpenHelper;


public class CornucopiaApplication extends MultiDexApplication {

	private ArrayList<Tickets> currentTickets;

	private TicketsSQLiteOpenHelper ticketDBHelper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// 数据库操作
		ticketDBHelper = new TicketsSQLiteOpenHelper(this);
		
		if (null == currentTickets) {
//			currentTickets = new ArrayList<Tickets>();
			
			// 使用数据库
			currentTickets = ticketDBHelper.loadTickets();
		}
		
		// 异常捕获
		initCrashHandler();
		
		Hotfix hotfix = new Hotfix();
		hotfix.loadBugfix(this, "patch_dex.jar", "com.cornucopia.hotfix.HotfixBug");
		
		DexposedHook dexposed = new DexposedHook();
		dexposed.hook(this);
		
	}

	private void initCrashHandler() {
		// TODO Auto-generated method stub
		
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

	public TicketsSQLiteOpenHelper getTicketDBHelper() {
		return ticketDBHelper;
	}

}
