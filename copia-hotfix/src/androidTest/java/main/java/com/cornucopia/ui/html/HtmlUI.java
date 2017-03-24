package com.cornucopia.ui.html;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.WebView;
import android.widget.Toast;

public class HtmlUI extends Activity {

    private WebView mWebView;

    private final String JAVASCRIPT_INTERFACE = "hu";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AddUserPlugin.GET_USERS: {
                    String param = (String) msg.obj;
                    // addUserShow方法是在add_table.js中定义的
                    mWebView.loadUrl(param);  // must be ui thread
                    break;
                }
                default: {
                    break;
                }
            }
        }
    };

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mWebView = new WebView(this);

		setWebViewAttribute();

		mWebView.loadUrl("file:///android_asset/index.html");

		setContentView(mWebView);
	}

	private void setWebViewAttribute() {
		mWebView.getSettings().setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new DefineChromeClient(this));

		// 设置javascript插件，并将其命名为addUser，在html中的onload加载并调用getUsers()
		mWebView.addJavascriptInterface(new AddUserPlugin(mHandler), "addUser");

		// 添加插件
		mWebView.addJavascriptInterface(this, JAVASCRIPT_INTERFACE);
	}

	public void startFunction() {
	    // js case sensitive, java must public
	    Toast.makeText(this, "js interface " + JAVASCRIPT_INTERFACE, Toast.LENGTH_SHORT).show();
	}

	public void startFunction(String param) {
        Toast.makeText(this, "js interface " + JAVASCRIPT_INTERFACE
                + " with " + param, Toast.LENGTH_SHORT).show();
	}


	private final CharSequence MENU_ONE = "invoke js";
	private final CharSequence MENU_TWO = "invoke js with param";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(MENU_ONE);
	    menu.add(MENU_TWO);

	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (MENU_ONE.equals(item.getTitle())) {
	        mWebView.loadUrl("javascript:callJs()");
	    }
	    if (MENU_TWO.equals(item.getTitle())) {
	        mWebView.loadUrl("javascript:callJsWithParam('param')");
	    }
	    return super.onOptionsItemSelected(item);
	}

}
