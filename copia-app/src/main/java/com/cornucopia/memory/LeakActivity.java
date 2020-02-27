package com.cornucopia.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cornucopia.R;

import androidx.annotation.Nullable;

public class LeakActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        Button btnLeak = findViewById(R.id.btn_leak);

        btnLeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncWork();
            }
        });

    }

    private void startAsyncWork() {
        Runnable work = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(20000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "leak work done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        new Thread(work).start();
    }

}

// javap -c build\intermediates\javac\debug\classes\...