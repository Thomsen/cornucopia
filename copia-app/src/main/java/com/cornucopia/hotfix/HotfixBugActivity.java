package com.cornucopia.hotfix;

import android.app.Activity;
import android.os.Bundle;

import com.cornucopia.R;

public class HotfixBugActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_test);
        
        Hotfix hotfix = new Hotfix();
        hotfix.loadBugfix(this, "patch_dex.jar", "com.cornucopia.hotfix.HotfixBug");
        
        HotfixBug hotBug = new HotfixBug();
        hotBug.bug();
    }
    
    

}
