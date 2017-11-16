package com.cornucoppia.component;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ComponentActivity extends FragmentActivity {

    // extends AppCompatActivity
    // AppCompat does not support the current theme features


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_container, new UserProfileFragment());

        fragmentTransaction.commit();

    }
}
