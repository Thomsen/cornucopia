package com.cornucopia.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cornucopia.R;

import androidx.annotation.Nullable;

public class LambdaNotLeakActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        Button btnLeak = findViewById(R.id.btn_leak);

        btnLeak.setOnClickListener((View view) -> startAsyncWork());

        Button btnLeakRunnable = findViewById(R.id.btn_leak_runnable);
        btnLeakRunnable.setVisibility(View.VISIBLE);
        btnLeakRunnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncWorkLambda();
            }
        });

        Button btnLeakListenerRunnable = findViewById(R.id.btn_leak_listener_runnable);
        btnLeakListenerRunnable.setVisibility(View.VISIBLE);
        btnLeakListenerRunnable.setOnClickListener((View view) -> startAsyncWorkLambda());

    }

    private void startAsyncWork() {
        Runnable work = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(20000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "leak listener lambda work done",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        new Thread(work).start();
    }

    private void startAsyncWorkLambda() {
        Runnable work = () -> {
            SystemClock.sleep(20000);
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), "leak runnable lambda work done",
                        Toast.LENGTH_SHORT).show();
            });
        };
        new Thread(work).start();
    }
}
