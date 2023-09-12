package com.cornucopia.di.dagger2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cornucopia.R;
import com.cornucopia.application.CornucopiaApplication;
import com.cornucopia.databinding.ActivityTestBinding;
import com.cornucopia.transmit.ActivityTest;

import javax.inject.Inject;

public class D2MainActivity extends Activity {

    TextView tvTest;
    
    private D2CollectionUtils d2Collection;
    
    @Inject
    D2Service d2Service;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTestBinding binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvTest = binding.textActivityTest;
        
        tvTest.setText("dragger2 butterknife bind view");
        
        CornucopiaApplication.component().inject(this);
        
        tvTest.setText(d2Service.greet("test"));
        
    }

}
