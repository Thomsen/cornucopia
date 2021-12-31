package com.cornucopia.kotlin.ml;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.Nullable;

import com.cornucopia.kotlin.databinding.ActivityLeakBinding;

public class LeakActivity extends Activity {

    private ActivityLeakBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeakBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLeak.setOnClickListener(new View.OnClickListener() {
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
            }
        };
        new Thread(work).start();
    }
}
