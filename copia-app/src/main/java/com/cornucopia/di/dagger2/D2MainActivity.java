package com.cornucopia.di.dagger2;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.cornucopia.R;
import com.cornucopia.application.CornucopiaApplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class D2MainActivity extends Activity {
    
//    @Bind(R.id.text_activity_test)
    TextView tvTest;
    
    private D2CollectionUtils d2Collection;
    
    @Inject
    D2Service d2Service;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        
        tvTest = (TextView) findViewById(R.id.text_activity_test);
        
        tvTest.setText("dragger2 butterknife bind view");
        
        CornucopiaApplication.component().inject(this);
        
        tvTest.setText(d2Service.greet("test"));
        
    }

}
