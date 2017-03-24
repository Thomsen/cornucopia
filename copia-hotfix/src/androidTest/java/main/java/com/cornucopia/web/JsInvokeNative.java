package com.cornucopia.web;

import com.cornucopia.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class JsInvokeNative extends Activity {
	
	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_web_invoke);
			
		mWebView = (WebView) findViewById(R.id.wv_ionvoke);
		mWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidNative"); // AndroidNative is js object, can invoke native method
		
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);  // true is must, otherwise js function no invoke
		
		mWebView.loadUrl("file:////android_asset/invoke.html");
	}

}
