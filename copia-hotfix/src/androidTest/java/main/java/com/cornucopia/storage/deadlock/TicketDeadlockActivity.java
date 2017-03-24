package com.cornucopia.storage.deadlock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import com.cornucopia.R;
import com.cornucopia.R.id;
import com.cornucopia.R.layout;
import com.cornucopia.R.menu;
import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.TicketsSQLiteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TicketDeadlockActivity extends Activity implements OnClickListener {
    
    private AtomicInteger allCount = new AtomicInteger();
    
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_deadlock);
        
        uiHandler = new Handler();
        
        findViewById(R.id.btn_one_helper).setOnClickListener(this);
        findViewById(R.id.btn_many_helper).setOnClickListener(this);
        findViewById(R.id.btn_trans_isolation).setOnClickListener(this);
        findViewById(R.id.btn_trans_isolation_multi).setOnClickListener(this);
        findViewById(R.id.btn_trans).setOnClickListener(this);
        findViewById(R.id.btn_no_trans).setOnClickListener(this);
        findViewById(R.id.btn_single_countdown).setOnClickListener(this);
        findViewById(R.id.btn_single_semaphore).setOnClickListener(this);
        findViewById(R.id.btn_single_semaphore_nojoin).setOnClickListener(this);
        findViewById(R.id.btn_single_semaphore_notrans).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tickets_deadlock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_one_helper == v.getId()) {
            allCount.set(0);
            final List<TicketInsertThread> allThreads = new ArrayList<TicketInsertThread>();
            TicketSQLiteOpenHelper helper = new TicketSQLiteOpenHelper(this);
            
            for (int i=0; i<4; i++) {
                allThreads.add(new TicketInsertThread(helper, 100));
            }
            
            runAllThread(allThreads);
        }
        if (R.id.btn_many_helper == v.getId()) {
            allCount.set(0);
            final List<TicketInsertThread> allThreads = new ArrayList<TicketInsertThread>();

            for (int i=0; i<4; i++) {
                TicketSQLiteOpenHelper helper = new TicketSQLiteOpenHelper(this);
                allThreads.add(new TicketInsertThread(helper, 100));
            }
            
            runAllThread(allThreads);  // db is locked
        }
        if (R.id.btn_trans_isolation == v.getId()) {
            TicketSQLiteOpenHelper helper = new TicketSQLiteOpenHelper(this);
            new TicketTransInsertThread(helper).start();
            new TicketFastSelectThread(helper).start();
        }
        if (R.id.btn_trans_isolation_multi == v.getId()) {
            // db is locked
            TicketSQLiteOpenHelper helper = new TicketSQLiteOpenHelper(this);
            new TicketTransInsertThread(helper).start();
            TicketSQLiteOpenHelper helper2 = new TicketSQLiteOpenHelper(this);
            new TicketFastSelectThread(helper2).start();
        }
        if (R.id.btn_trans == v.getId()) {
            runTrans();  // quickly
        }
        if (R.id.btn_no_trans == v.getId()) {
            runNoTrans();
        }
        
        if (R.id.btn_single_countdown == v.getId()) {
            
            final CountDownLatch countDownLatch = new CountDownLatch(1);  // shared lock
            TicketSingleInsertThread single = new TicketSingleInsertThread(countDownLatch,
                    getApplicationContext().getFilesDir(), true, true);
            
            new Thread(single).start();  // s1
            
            try {
                Log.i("SingleThread", "ticket single insert started");  // s2
                countDownLatch.wait(); // AQS.acquireSharedInterruptibly   s3
                Log.i("SingleThread", "ticket single insert wait end. " + single.getResult());  // s11
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if (R.id.btn_single_semaphore == v.getId()) {
            
            final Semaphore semaphore = new Semaphore(0);  // 
            TicketSingleInsertThread single = new TicketSingleInsertThread(semaphore,
                    getApplicationContext().getFilesDir(), true, true);
            
            new Thread(single).start();  // s1
            
            try {
                Log.i("SingleThread", "ticket single insert started");  // s2
                semaphore.acquire(); //    s3
                Log.i("SingleThread", "ticket single insert wait end. " + single.getResult());  // s11
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if (R.id.btn_single_semaphore_nojoin == v.getId()) {
            
            final Semaphore semaphore = new Semaphore(0);  // 
            TicketSingleInsertThread single = new TicketSingleInsertThread(semaphore,
                    getApplicationContext().getFilesDir(), false, true);
            
            new Thread(single).start();  // s1
            
            try {
                Log.i("SingleThread", "ticket single insert started");  // s2
                semaphore.acquire(); //    s3
                Log.i("SingleThread", "ticket single insert wait end. " + single.getResult());  // s11
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if (R.id.btn_single_semaphore_notrans == v.getId()) {
            
            final Semaphore semaphore = new Semaphore(0);  // 
            TicketSingleInsertThread single = new TicketSingleInsertThread(semaphore,
                    getApplicationContext().getFilesDir(), true, false);
            
            new Thread(single).start();  // s1
            
            try {
                Log.i("SingleThread", "ticket single insert started");  // s2
                semaphore.acquire(); //    s3
                Log.i("SingleThread", "ticket single insert wait end. " + single.getResult());  // s11
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void runTrans() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object... objects) {
                TicketSQLiteOpenHelper instance = TicketSQLiteOpenHelper
                        .getInstance(TicketDeadlockActivity.this);

                SQLiteDatabase writableDatabase = instance
                        .getWritableDatabase();

                long start = System.currentTimeMillis();
                writableDatabase.beginTransaction();
                insertTest(instance);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();

                return start;
            }

            @Override
            protected void onPostExecute(Object o) {
                showTime((Long) o,
                        TicketSQLiteOpenHelper
                                .getInstance(TicketDeadlockActivity.this), "trans");
            }
        }.execute();

    }

    @SuppressWarnings("unchecked")
    private void runNoTrans() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object... objects) {
                TicketSQLiteOpenHelper instance = TicketSQLiteOpenHelper
                        .getInstance(TicketDeadlockActivity.this);
                long start = System.currentTimeMillis();
                insertTest(instance);

                return start;
            }

            @Override
            protected void onPostExecute(Object o) {
                showTime((Long) o,
                        TicketSQLiteOpenHelper
                                .getInstance(TicketDeadlockActivity.this), "notrans");
            }
        }.execute();
    }

    private void showTime(long start, TicketSQLiteOpenHelper instance, String label) {
        long time = System.currentTimeMillis() - start;
        ((TextView) findViewById(R.id.tv_tickets_timeout))
                .setText("Total rows: " + instance.countickets() +  " " + label + " time: "
                        + Long.toString(time));
    }

    private void insertTest(TicketSQLiteOpenHelper helper) {

        int numberOfInserts = 100;
        Tickets ticket;
        while (numberOfInserts > 0) {
            ticket = new Tickets("Tickets " + numberOfInserts);
            helper.addTicketToDatabase(ticket);
            numberOfInserts--;
        }

    }
    
    private void runAllThread(final List<TicketInsertThread> allThreads) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (TicketInsertThread thred : allThreads) {
                    thred.start();
                }
                
                for (TicketInsertThread thread : allThreads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                uiHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        ((TextView)  findViewById(R.id.tv_tickets_amount)).setText("Inserted "+ allCount.get());
                    }
                    
                });
            }
            
        }).start();
    }
    
    
    class TicketInsertThread extends Thread {
        private TicketSQLiteOpenHelper ticketHelper;
        private int runCount;
        
        TicketInsertThread(TicketSQLiteOpenHelper ticketHelper, int runCount) {
            this.ticketHelper = ticketHelper;
            this.runCount = runCount;
        }
        
        @Override
        public void run() {
            super.run();
            
            Random random = new Random();
            
            for (int i=0; i<runCount; i++) {
                if (i % 10 == 0) {
                    Log.i("TicketDeadlock", "Ticket Writing");
                }
                
                Tickets ticket = new Tickets("ticket " + random.nextInt(456789));
                
                try {
                    ticketHelper.addTicketToDatabase(ticket);
                    allCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                    
                    Log.e("TicketDeadlock", "Ticket Writing Failed!"  + " -- helper -- " + ticketHelper.hashCode());
                }
            }
        }
    }
    
    class TicketTransInsertThread extends Thread {
        private TicketSQLiteOpenHelper ticketHelper;
        private Handler handler;
        
        public TicketTransInsertThread(TicketSQLiteOpenHelper ticketHelper) {
            this.ticketHelper = ticketHelper;
            this.handler = new Handler();
        }
        
        @Override
        public void run() {
            super.run();
            
            int count = 0;
            while (count < 10) {
                SQLiteDatabase db = ticketHelper.getWritableDatabase();
                db.beginTransaction();
                
                final ContentValues contentValues = new ContentValues();
                contentValues.put(TicketSQLiteOpenHelper.TICKETS_NAME, "ticket " + count);
                contentValues.put(TicketSQLiteOpenHelper.TICKETS_COMPLETE, Boolean.toString(false));
                
                Log.i("TicketDeadlock", "Ticket Trans insert");
                db.insertOrThrow(TicketSQLiteOpenHelper.TICKETS_TABLE, null, contentValues);
                
                Log.i("TicketDeadlock", "Ticket Trans start wait");
                
                try {
                    Thread.sleep(300);  // not appear with long interval
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                Log.i("TicketDeadlock", "Ticket Trans end wait");
                
                db.endTransaction();
                count ++;
            }
        }
        
    }
    
    class TicketFastSelectThread extends Thread {
        private TicketSQLiteOpenHelper ticketHelper;
        private Handler handler;
        
        public TicketFastSelectThread(TicketSQLiteOpenHelper ticketHelper) {
            this.ticketHelper = ticketHelper;
            this.handler = new Handler();
        }
        
        @Override
        public void run() {
            super.run();
            
            int count = 0;
            while (count < 100) {
                
                ticketHelper.loadTickets();
                
                Log.i("TicketDeadlock", "Ticket select start wait");
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                Log.i("TicketDeadlock", "Ticket select end wait");
                
                count++;
            }
        }
    }
    
    static class TicketSingleInsertThread implements Runnable {
        
        protected CountDownLatch countDownLatch;
        
        protected Semaphore semaphore;
        
        protected File baseDirectory;
        
        private String threadResult;
        
        private boolean isJoin;
        
        private boolean isTrans;
        
        public TicketSingleInsertThread(CountDownLatch countDownLatch, File baseDirectory, boolean isJoin, boolean isTrans) {
            this.countDownLatch = countDownLatch;
            this.baseDirectory = baseDirectory;
            this.isJoin = isJoin;
            this.isTrans = isTrans;
        }
        
        public TicketSingleInsertThread(Semaphore semaphore, File baseDirectory, boolean isJoin, boolean isTrans) {
            this.semaphore = semaphore;
            this.baseDirectory = baseDirectory;
            this.isJoin = isJoin;
            this.isTrans = isTrans;
        }
        
        public String getResult() {
            return threadResult;
        }

        @Override
        public void run() {

            SQLiteDatabase sqdb = TicketSQLiteDatabase.openRandomDatabase(baseDirectory);
            TicketSQLiteDatabase.createTestTables(sqdb);
            
            Thread readThread = null;
            if (isJoin) {
                ReaderThreadRunnable rtr = new ReaderThreadRunnable(sqdb);
                readThread = new Thread(rtr);
            }
            
            WriterThreadRunnable wtr = new WriterThreadRunnable(sqdb, readThread, isTrans);
            Thread writerThread = new Thread(wtr);
            
            writerThread.start();  // s4
            
            try {
                writerThread.join();  // wait writer thread completed  s5
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
            
            if (null != countDownLatch) {
                threadResult = "thread countDownLatch end";
                countDownLatch.countDown();  // releaseShared(1)  s10
            }
            if (null != semaphore) {
                threadResult = "thread semaphore end";
                semaphore.release();
            }
            
        }
        
        static class ReaderThreadRunnable implements Runnable {

            private SQLiteDatabase sqliteDatabase;

            ReaderThreadRunnable(SQLiteDatabase sqliteDatabase) {
                this.sqliteDatabase = sqliteDatabase;
            }

            @Override
            public void run() {
                TicketSQLiteDatabase.runFakeQuery(sqliteDatabase);   // s8
            }

        }

        static class WriterThreadRunnable implements Runnable {

            private SQLiteDatabase sqliteDatabase;
            private Thread readerThread;
            
            private boolean isTrans;

            WriterThreadRunnable(SQLiteDatabase sqliteDatabase, Thread readerThread, boolean isTrans) {
                this.sqliteDatabase = sqliteDatabase;
                this.readerThread = readerThread;
                this.isTrans = isTrans;
            }

            @Override
            public void run() {

                boolean shouldCommit = false;
                try {
                    if (isTrans) {
                        TicketSQLiteDatabase.beginTransaction(sqliteDatabase);
                    }
                    TicketSQLiteDatabase.doFakeInsert(sqliteDatabase);
                    shouldCommit = true;
                    if (null != readerThread) {
                        readerThread.start();  // s6
                        readerThread.join(); // The join method allows one thread to wait for the completion of another.  s7    
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (isTrans) {
                        TicketSQLiteDatabase.endTransaction(sqliteDatabase, shouldCommit);   // s9  (not execute, because s3 object not locked by thread before wait)
                    }
                }

            }
        }
        
    }

}
