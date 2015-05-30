package com.cornucopia.application;

import java.util.ArrayList;

import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.TicketsSQLiteOpenHelper;

import static com.cornucopia.storage.ticketsmanager.TicketsSQLiteOpenHelper.*;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class CornucopiaApplication extends Application {

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
