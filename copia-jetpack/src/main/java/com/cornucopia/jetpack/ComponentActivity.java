package com.cornucopia.jetpack;

import android.os.Bundle;
import androidx.core.app.FragmentActivity;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentTransaction;

import com.cornucopia.jetpack.lifecycle.ComponentObserver;

public class ComponentActivity extends FragmentActivity {

    // extends AppCompatActivity
    // AppCompat does not support the current theme features


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        getLifecycle().addObserver(new ComponentObserver());

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_container, new UserProfileFragment());

        fragmentTransaction.commit();

    }
}
