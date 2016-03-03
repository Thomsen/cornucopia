package com.cornucopia.web;

import com.cornucopia.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowserDemo extends Activity {

	WebView mWebBrowser;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		setContentView(R.layout.activity_webbrowser);
	
		mWebBrowser = (WebView) findViewById(R.id.web_browser);
		
		initSettings(mWebBrowser);
		
//		mWebBrowser.loadUrl("http://192.168.1.248:7003/webgisams/");
//		mWebBrowser.loadUrl("http://weibo.com/");
		mWebBrowser.loadUrl("http://192.168.1.239:28080/jira");
		
	}

	private void initSettings(WebView webBrowser) {
		webBrowser.getSettings().setJavaScriptEnabled(true);
		webBrowser.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				return super.shouldOverrideUrlLoading(view, url);
				view.loadUrl(url);
				return true;  // 表示点击页面链接还在当前webview跳转，不跳转到浏览器
			}
			
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebBrowser.canGoBack()) {
			mWebBrowser.goBack();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
