package com.cornucopia.storage.deadlock;

import java.io.File;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TicketSQLiteDatabase {
    
    private static final String TAG = "TicketSQLiteDatabase";

    public static final String TICKETS_TABLE = "tickets";
    public static final String TICKETS_ID = "id";
    public static final String TICKETS_NAME = "name";
    public static final String TICKETS_COMPLETE = "complete";
    
    public static final String SCHEMA = "create table " + TICKETS_TABLE + "(" +
            TICKETS_ID + " integer primary key autoincrement not null, " +
            TICKETS_NAME + " text," + 
            TICKETS_COMPLETE + " text" + ")";
    
    
    public static SQLiteDatabase openRandomDatabase(File baseDirectory) {

        String randomDatabaseName;
        SQLiteDatabase sqLiteDatabase;

        randomDatabaseName = getRandomDatabaseName();
        File randomDatabasePath = new File(baseDirectory, randomDatabaseName);
        sqLiteDatabase = SQLiteDatabase.openDatabase(randomDatabasePath.getPath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return sqLiteDatabase;

    }

    public static void createTestTables(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(SCHEMA);
        } catch (SQLException e) {
            sqLiteDatabase.close();
            throw new RuntimeException(e);
        }
    }

    public static void runFakeQuery(SQLiteDatabase sqLiteDatabase) {
        String sql = "SELECT * FROM " + TICKETS_TABLE;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(sql, null);
            Log.d(TAG, "Iterating over rows");
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                Log.d(TAG, "Row: " + id);
            }
            Log.d(TAG, "Done iterating over rows");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void beginTransaction(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
    }

    public static void doFakeInsert(SQLiteDatabase sqLiteDatabase) {
        long rowId = 0;
        ContentValues args = new ContentValues();
        args.put(TICKETS_NAME, "ticket " + System.currentTimeMillis());
        args.put(TICKETS_COMPLETE, Boolean.toString(false));
        rowId = sqLiteDatabase.insert(TICKETS_TABLE, null, args);
        Log.d(TAG, "Insert fake row: " + rowId + " with name: " + args.get(TICKETS_NAME));
        if (rowId == -1) {
            throw new RuntimeException("Could not insert new row");
        }
    }

    public static void endTransaction(SQLiteDatabase sqLiteDatabase, boolean commit) {
        if (commit == true) {
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        } else {
            sqLiteDatabase.endTransaction();
        }
    }

    private static String getRandomDatabaseName() {
        return String.format("testdb-%s", Long.toString(System.currentTimeMillis()));
    }

}
