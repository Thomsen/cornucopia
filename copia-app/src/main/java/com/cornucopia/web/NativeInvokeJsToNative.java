package com.cornucopia.web;

import com.cornucopia.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class NativeInvokeJsToNative extends Activity implements OnClickListener {
	
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_web_invoke);
		
		mWebView = (WebView) findViewById(R.id.wv_ionvoke);
		
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true); // enable js function
		webSettings.setUseWideViewPort(true); // 
		webSettings.setLoadWithOverviewMode(true); // adapter screen
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		
		mWebView.loadUrl("file:////android_asset/invoke.html");
		
		mWebView.setWebViewClient(new NativeWebViewClient());
		
		mWebView.setWebChromeClient(new NativeWebChromeClient()); // alert dialog
		
		mWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidNative");
		
		
		Button btn = (Button) findViewById(R.id.btn_invoke_js);
		btn.setOnClickListener(this);
		findViewById(R.id.btn_invoke_online).setOnClickListener(this);
		findViewById(R.id.btn_invoke_offline).setOnClickListener(this);
		
		mWebView.requestFocus();
		
		mWebView.setWebContentsDebuggingEnabled(true);
	}
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
	    if (v.getId() == R.id.btn_invoke_js) {
	        mWebView.loadUrl("javascript:showJsToast('shadow');");
	    }
	    if (v.getId() == R.id.btn_invoke_online) {
	        mWebView.setNetworkAvailable(true);
	    }
	    if (v.getId() == R.id.btn_invoke_offline) {
            mWebView.setNetworkAvailable(false);
        }
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
		
//			view.loadUrl("javascript:showJsToast('shadow');");  // js function showJsToast invoke, but alert not show
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
		
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
		        String defaultValue, JsPromptResult result) {
		    Toast.makeText(NativeInvokeJsToNative.this, message, Toast.LENGTH_SHORT).show();
//		    return super.onJsPrompt(view, url, message, defaultValue, result);  // default  false also show dialog
	        
	        if (message.startsWith("online")) {
	            result.confirm("native return online");
	        } else {
	            result.cancel();  // 会出现点击后无法再次响应的问题, resolve: cancel or confirm and return true
	        }
	        
		    return true;
		}

	}
}
