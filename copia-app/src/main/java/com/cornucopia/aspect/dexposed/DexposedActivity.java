package com.cornucopia.aspect.dexposed;

import com.cornucopia.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DexposedActivity extends Activity implements OnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_dexposed);
        
        Button btnDexposed = (Button) findViewById(R.id.btn_dexposed);
        btnDexposed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dexposed) {
            Toast.makeText(this, "dexposed", Toast.LENGTH_SHORT).show();
        }
    }
    
    
}
