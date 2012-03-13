package com.cornucopia.application;

import java.util.ArrayList;

import com.cornucopia.application.ticketsmanager.Tickets;
import com.cornucopia.application.ticketsmanager.db.TicketsSQLiteOpenHelper;
import static com.cornucopia.application.ticketsmanager.db.TicketsSQLiteOpenHelper.*;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class CornucopiaApplication extends Application {

	private ArrayList<Tickets> currentTickets;
	private SQLiteDatabase database;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// 数据库操作
		TicketsSQLiteOpenHelper dbHelper = new TicketsSQLiteOpenHelper(this);
		database = dbHelper.getWritableDatabase();
		
		if (null == currentTickets) {
//			currentTickets = new ArrayList<Tickets>();
			
			// 使用数据库
			loadTickets();
			
		}
	}

	private void loadTickets() {
		currentTickets = new ArrayList<Tickets>();
		// 为了能够使用TICKETS_TABLE，可以使用import static package;
		Cursor ticketsCursor = database.query(TICKETS_TABLE, new String[] {
				TICKETS_ID, 
				TICKETS_NAME, 
				TICKETS_COMPLETE}, 
				null, null, null, null, String.format("%s, %s", TICKETS_COMPLETE, TICKETS_NAME));
		ticketsCursor.moveToFirst();
		Tickets ticket;
		if (! ticketsCursor.isAfterLast()) {
			do {
				long id = ticketsCursor.getInt(0);
				String name = ticketsCursor.getString(1);
				String boolValue = ticketsCursor.getString(2);
				boolean complete = Boolean.parseBoolean(boolValue);
				
				ticket = new Tickets(name);
				ticket.setId(id);
				ticket.setComplete(complete);
				
				currentTickets.add(ticket);
				
			} while(ticketsCursor.moveToNext());
		}
		
		ticketsCursor.close();
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
	
	/**
	 * 将tickets插入到数据库中
	 * @param ticket
	 */
	public void addTicketToDatabase(Tickets ticket) {
		ContentValues values = new ContentValues();
		values.put(TICKETS_NAME, ticket.getName());
		values.put(TICKETS_COMPLETE, Boolean.toString(ticket.isComplete()));
		
		// id为long型，因为insert返回的是long
		ticket.setId(database.insert(TICKETS_TABLE, null, values));
		
		currentTickets.add(ticket);
	}
	
	/**
	 * 更新数据库
	 * @param ticket
	 */
	public void saveTicketToDatabase(Tickets ticket) {
		ContentValues values = new ContentValues();
		values.put(TICKETS_NAME, ticket.getName());
		values.put(TICKETS_COMPLETE, Boolean.toString(ticket.isComplete()));
		
		long id = ticket.getId();
//		String where = String.format("%s = %s", TICKETS_ID, id);
		String where = String.format("%s = ?", TICKETS_ID);
		
//		database.update(TICKETS_TABLE, values, where, null);
		database.update(TICKETS_TABLE, values, where, new String[] {id+""});
	}
	
	/**
	 * 删除tickets，可以传多个id
	 * @param ids
	 */
	public void deleteTickets(Long[] ids) {
		StringBuffer idList = new StringBuffer();
		for (int i=0; i<ids.length; i++) {
			idList.append(ids[i]);
			if (i < ids.length - 1) {
				idList.append(",");
			}
		}
		// idList (1,2,3)
		String where = String.format("%s in (%s)", TICKETS_ID, idList);
		database.delete(TICKETS_TABLE, where, null);
	}

}
