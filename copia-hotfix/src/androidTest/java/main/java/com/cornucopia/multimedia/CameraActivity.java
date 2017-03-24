package com.cornucopia.multimedia;

import com.cornucopia.R;
import com.cornucopia.multimedia.camera.CameraBasicFragment;
import com.cornucopia.multimedia.camera2.Camera2BasicFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CameraActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_camera);
        
        Intent intent = getIntent();
        Uri fileUri = intent.getParcelableExtra(CameraShowActivity.URI_FILE);
        
        boolean isCameraTwo = intent.getBooleanExtra(CameraShowActivity.CAMERA_TWO, false);
        
        if (null == savedInstanceState) {
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            if (isCameraTwo) {
                trans.replace(R.id.container, Camera2BasicFragment.newInstance(fileUri));
            } else {
                trans.replace(R.id.container, CameraBasicFragment.newInstance(fileUri));
            }
            trans.commit();
        }
    };
}
