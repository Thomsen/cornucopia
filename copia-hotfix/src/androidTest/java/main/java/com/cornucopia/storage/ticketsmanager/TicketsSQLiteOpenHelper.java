package com.cornucopia.storage.ticketsmanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TicketsSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "tickets_db.sqlite3";
	private static final int VERSION = 1;
	
	public static final String TICKETS_TABLE = "tickets";
	public static final String TICKETS_ID = "id";
	public static final String TICKETS_NAME = "name";
	public static final String TICKETS_COMPLETE = "complete";
	
	public TicketsSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(
				"create table " + TICKETS_TABLE + "(" +
			 	TICKETS_ID + " integer primary key autoincrement not null, " +
			 	TICKETS_NAME + " text," + 
			 	TICKETS_COMPLETE + " text" + ")");
			 	
	}
	
	public ArrayList<Tickets> loadTickets() {
		ArrayList<Tickets> currentTickets = new ArrayList<Tickets>();
		// 为了能够使用TICKETS_TABLE，可以使用import static package;
		Cursor ticketsCursor = getReadableDatabase().query(TICKETS_TABLE, new String[] {
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
		return currentTickets;
	}

	
	/**
	 * 将tickets插入到数据库中
	 * @param ticket
	 */
	public void addTicketToDatabase(Tickets ticket) {
		ContentValues values = new ContentValues();
		values.put(TICKETS_NAME, ticket.getName());
		values.put(TICKETS_COMPLETE, Boolean.toString(ticket.isComplete()));
		
		// id为long型，因为返回的是long
		ticket.setId(getWritableDatabase().insert(TICKETS_TABLE, null, values));
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
		getWritableDatabase().update(TICKETS_TABLE, values, where, new String[] {id+""});
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
		getWritableDatabase().delete(TICKETS_TABLE, where, null);
	}
}
