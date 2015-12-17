package com.cornucopia.web;

import com.cornucopia.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NativeInvokeJs extends Activity {
	
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_web_invoke);
		
		mWebView = (WebView) findViewById(R.id.wv_ionvoke);
		
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true); // enable js function
		webSettings.setUseWideViewPort(true); // 
		
		mWebView.loadUrl("file:////android_asset/invoke.html");
		
		mWebView.setWebViewClient(new NativeWebViewClient());
		
		mWebView.setWebChromeClient(new NativeWebChromeClient()); // alert dialog
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class NativeWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			view.loadUrl(url);
			
			return true;
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		
			view.loadUrl("javascript:showJsToast('shadow');");  // js function showJsToast invoke, but alert not show
		}
		
	}
	
	class NativeWebChromeClient extends WebChromeClient {
		
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			
			final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
			
			builder.setTitle("chrome title dialog");
			builder.setMessage(message);
			builder.setPositiveButton("ok", null);
			
			builder.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					return false;
				}
			});
			
			builder.setCancelable(false);
			
			builder.create().show();
			
			result.confirm();
			
//			return super.onJsAlert(view, url, message, result); // two dialog
			return true;
		}
	}
}
