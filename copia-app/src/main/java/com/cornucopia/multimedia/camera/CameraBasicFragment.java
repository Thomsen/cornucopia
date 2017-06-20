package com.cornucopia.multimedia.camera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cornucopia.R;

public class CameraBasicFragment extends Fragment implements OnClickListener {
    
    private Context mContext;
    
    private Camera mCamera;
    
    private CameraPreview mPreview;
    
    private Uri fileUri;
    
    private int mCameraId;

    public static CameraBasicFragment newInstance(Uri fileUri) {
        CameraBasicFragment fragment = new CameraBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("fileUri", fileUri);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    public CameraBasicFragment() {
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        mContext = activity;
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(activity, mCamera);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        
        View view = inflater.inflate(R.layout.layout_camera_preview, null);
        
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
        Button capture = (Button) view.findViewById(R.id.btn_capture);
        capture.setOnClickListener(this);
        
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private Camera getCameraInstance() {
        int numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                mCameraId = i;
                break;
            }
        }
        Camera c;
        if (null == mCamera) {
            c = Camera.open(mCameraId);
        } else {
            c = mCamera;
        }
        return c;
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_capture == v.getId()) {
            captureCamera();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    private void captureCamera() {
        mCamera.takePicture(null, null, new CameraPictureCallback(mContext, fileUri));
    }

    private void releaseCamera() {
        if (null != mCamera) {
            mCamera.release();
            mCamera = null;
        }
    }    
}
