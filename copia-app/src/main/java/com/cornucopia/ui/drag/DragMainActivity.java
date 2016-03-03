package com.cornucopia.ui.drag;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.cornucopia.R;

public class DragMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
