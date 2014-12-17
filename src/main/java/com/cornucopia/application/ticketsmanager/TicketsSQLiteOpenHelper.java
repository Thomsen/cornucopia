package com.cornucopia.application.ticketsmanager;

import android.content.Context;
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
}
