package com.cornucopia.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class TasksActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tasks);
        
        Toast.makeText(this, "tasks", Toast.LENGTH_SHORT).show();
    }
    
    public void addMethod() {
    	Toast.makeText(this, "add method", Toast.LENGTH_SHORT).show();
    }

}
