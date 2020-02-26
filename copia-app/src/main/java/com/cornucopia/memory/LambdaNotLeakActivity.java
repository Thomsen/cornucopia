package com.cornucopia.memory;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.cornucopia.R;

import androidx.annotation.Nullable;

public class LambdaNotLeakActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        Button btnLeak = findViewById(R.id.btn_leak);

        btnLeak.setOnClickListener((View view) -> startAsyncWork());

    }

    private void startAsyncWork() {
        Runnable work = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(20000);
            }
        };
        new Thread(work).start();
    }
}
