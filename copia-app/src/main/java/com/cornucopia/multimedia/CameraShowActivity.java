package com.cornucopia.multimedia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cornucopia.R;
import com.cornucopia.multimedia.camera2.Camera2BasicFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CameraShowActivity extends Activity implements OnClickListener {
    

    private static final int INTENT_IMAGE = 100;
    
    private static final int CAPTURE_IMAGE = 101;

    private Uri fileUri;

    public static final String URI_FILE = "uri_file";
    
    public static final String CAMERA_TWO = "camera_two";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_camera_show);
        
        Button btnIntentCamera = (Button) findViewById(R.id.btn_intent_camera);
        btnIntentCamera.setOnClickListener(this);
        
        Button btnCaptureCamera = (Button) findViewById(R.id.btn_capture_camera);
        btnCaptureCamera.setOnClickListener(this);
        
        Button btnCaptureCamera2 = (Button) findViewById(R.id.btn_capture_camera2);
        btnCaptureCamera2.setOnClickListener(this);
        

    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_intent_camera == v.getId()) {
            intentCamera();
        }
        if (R.id.btn_capture_camera == v.getId()) {
            captureCamera(false);
        }
        if (R.id.btn_capture_camera2 == v.getId()) {
            captureCamera(true);
        }
    }

    
    private void captureCamera(boolean isCameraTwo) {
        Intent intent = new Intent(this, CameraActivity.class);
        fileUri = getOutputMediaFileUri(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(URI_FILE, fileUri);
        intent.putExtra(CAMERA_TWO, isCameraTwo);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
            if (requestCode == INTENT_IMAGE) {
                if (null != data) {
                    Toast.makeText(this, "image saved to: \n" + data.getData(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void intentCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        fileUri = getOutputMediaFileUri(MediaStore.ACTION_IMAGE_CAPTURE);
        if (null != fileUri) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        
        startActivityForResult(intent, INTENT_IMAGE);
    }
    
    private Uri getOutputMediaFileUri(String type) {
        File file = getOutputMediaFile(type);
        if (null != file) {
            return Uri.fromFile(getOutputMediaFile(type));
        } else {
            return null;
        }
    }
    
    private File getOutputMediaFile(String type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "camera");
        
        if (! mediaStorageDir.exists()) {
            Log.d("cornucopia", "failed to create public directory");
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "camera");
            
            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    Log.d("cornucopia", "failed to create directory");
                    return null;
                }
            }
        }
        
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (MediaStore.ACTION_IMAGE_CAPTURE.equals(type)){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(MediaStore.ACTION_VIDEO_CAPTURE.equals(type)) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;

    }
}
