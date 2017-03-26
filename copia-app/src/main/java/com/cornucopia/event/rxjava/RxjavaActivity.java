package com.cornucopia.event.rxjava;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cornucopia.R;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class RxjavaActivity extends Activity implements OnClickListener {

    private static final String TAG = "RxjavaActivity";

    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rxjava);

        Button btnRx = (Button) findViewById(R.id.btn_rxjava);
        btnRx.setOnClickListener(this);


    }

    private void basicOb() {


    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_rxjava == v.getId()) {
            Toast.makeText(this, "rxjava", Toast.LENGTH_SHORT).show();
            new SleepTask().execute();

            basicOb();

            onRunSchedulerExampleButtonClicked();

        }
    }

    private class SleepTask extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RxjavaActivity.this, "sleep start...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                new Thread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(RxjavaActivity.this, "sleep end", Toast.LENGTH_SHORT).show();
        }
    }

    void onRunSchedulerExampleButtonClicked() {

    }



    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("rxjava background", THREAD_PRIORITY_BACKGROUND);
        }
    }

}
