package com.cornucopia.devices.map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.TextView;

import com.cornucopia.R;
import com.cornucopia.devices.map.location.LocationManager;

public class LocMainActivity extends Activity {
    
    private TextView mTextLatitude;

    private TextView mTextLongitude;
    
    private TextView mTextAlitude;
    
    private TextView mTextAccuracy;
    
    private TextView mTextAlitudeAccuracy;
    
    private TextView mTextAddress;
    
    private Activity mActivity;
    
    GeoLocationInfo locInfo;
    
    LocationManager locationManager;
    
    private static final int REFRESH_LOCATION = 0;
    
    private Handler mHandler = new Handler() {
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case REFRESH_LOCATION: {
    			mTextLatitude.setText(String.valueOf(locInfo.getLatitude()));
                mTextLongitude.setText(String.valueOf(locInfo.getLongitude()));
                mTextAlitude.setText(String.valueOf(locInfo.getAltitude()));
                mTextAccuracy.setText(String.valueOf(locInfo.getAccuracy()));
                mTextAlitudeAccuracy.setText(String.valueOf(locInfo.getAltitudeAccuracy()));
                
                if (locInfo.getAddress() == null) {
                	mTextAddress.setText(locInfo.getStrAddress());
                } else {
                	mTextAddress.setText(locInfo.getAddress().toString());
                }
                
    			break;
    		}
    		}
    	}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);
        
        mActivity = this;
        
        if (savedInstanceState != null) {
            locInfo = (GeoLocationInfo) savedInstanceState.getSerializable("loc_info");
        }
        
        setupView();
        initView();
        
//        Message.obtain(h, what, obj).sendToTarget(); // 在handler中通过msg.obj得到对象
    }
    
    public void onDestroy() {
    	super.onDestroy();
    	if (locationManager != null) {
            locationManager.stop();
        }
    }

    /** 
     * description: 初始化界面，设置数据
     * 
     * @author Thomsen
     * @date Sep 16, 2012 12:41:55 PM
     */
    private void initView() {
    	// google geolocation 耗时放在子线程中
//    	new Thread() {
//    		public void run() {
//    			GoogleLocationManager nlm = new GoogleLocationManager();
//    	        CellIdInfoManager m = new CellIdInfoManager(mActivity);
//    	        List<CellIdInfo> listCellIdInfo = m.getCellIdInfo();
//    	        locInfo = nlm.getBaseStationLocation(listCellIdInfo);
//    	        
//    	        if (locInfo != null) {
//    	            mHandler.sendEmptyMessage(REFRESH_LOCATION);
//    	        }
//    		}
//    	}.start();
        
    	

    }

    /** 
     * description: 设置界面属性
     * 
     * @author Thomsen
     * @date Sep 16, 2012 12:36:39 PM
     */
    private void setupView() {
        mTextLatitude = (TextView) findViewById(R.id.geo_latitude);
        mTextLongitude = (TextView) findViewById(R.id.geo_longitude);
        mTextAlitude = (TextView) findViewById(R.id.geo_altitude);
        mTextAccuracy = (TextView) findViewById(R.id.geo_accuracy);
        mTextAlitudeAccuracy = (TextView) findViewById(R.id.geo_altitude_accuracy);
        
        mTextAddress = (TextView) findViewById(R.id.geo_address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (locInfo != null) {
            outState.putSerializable("loc_info", locInfo);
        }
    }
}
