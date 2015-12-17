package com.cornucopia.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
	
	private Context mContext;
	
	public WebAppInterface(Context c) {
		mContext = c;
	}
	
	@JavascriptInterface // targetSdkVersion >= 17
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}

}
