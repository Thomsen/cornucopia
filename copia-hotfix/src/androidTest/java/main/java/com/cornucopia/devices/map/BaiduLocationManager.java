package com.cornucopia.devices.map;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduLocationManager {
	
	private BDLocationListener locListener;
	
	private LocationClient locClient;
	
	public BaiduLocationManager(Context context) {
		locClient = new LocationClient(context);
		setLocationOption();
	}
	
	/**
	 * 注册baidu获取坐标监听器
	 * @author Thomsen
	 * @date 2012-11-30 上午11:11:11
	 * @param listener 不设置时，使用默认的监听器
	 */
	public void registerLocationListener(BDLocationListener listener) {
		if (listener == null) {
			locListener = new BaiduLocationListener();
		} else {
			locListener = listener;
		}
		locClient.registerLocationListener(locListener);
	}
	
	/**
	 * 配置
	 * @author Thomsen
	 * @date 2012-11-30 下午2:28:07
	 */
	public void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("gcj02"); // bd09 bd09ll gcj02
		
		locClient.setLocOption(option);
	}
	
	
	/**
	 * 启动定位
	 * @author Thomsen
	 * @date 2012-11-30 上午11:19:27
	 */
	public void onStart() {
		locClient.start();
	}
	
	/**
	 * 停止定位
	 * @author Thomsen
	 * @date 2012-11-30 上午11:47:55
	 */
	public void onStop() {
		locClient.stop();
	}
	
	class BaiduLocationListener implements BDLocationListener {

		// 混合方式获取坐标（gps，wifi，基站）
		@Override
		public void onReceiveLocation(BDLocation location) {

			Log.i("thom", location.getLongitude() + "");
		}
		
	}

}
