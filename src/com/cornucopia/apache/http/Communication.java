package com.cornucopia.apache.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Communication extends Activity {

	TextView mTextView;

	// webview测试
	private String url01 = "192.168.56.1"; // no
	private String url02 = "http://192.168.56.1:8089"; // yes
	private String url03 = "http://192.168.56.1:3001"; // yes
	private String url04 = "http://192.168.56.1:3003"; // yes

	// android的内置ip
	private String url05 = "http://10.0.2.2"; // yes
	private String url06 = "http://10.0.2.2:8089"; // yes

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// testWebViewToServer();

		ScrollView sv = new ScrollView(this);

		mTextView = new TextView(this);

		// mTextView.setText(getResultWithHttpConnection(url05));
		mTextView.setText(getResultWithHttpClient(url06));

		sv.addView(mTextView);

		setContentView(sv);
	}

	private String getResultWithHttpConnection(String strUrl) {
		String result = "communication http connection";

		try {
			URL url = new URL(strUrl);

			// 请求连接
			// URLConnection httpRequest = url.openConnection();
			HttpURLConnection httpRequest = (HttpURLConnection) url
					.openConnection();

			// 设置连接参数
			httpRequest.setConnectTimeout(6 * 1000);
			httpRequest.setReadTimeout(3 * 1000);

			// 设置请求方式
			// httpRequest.setRequestMethod("POST");

			// 此处抛出IO异常，由于没有网络权限
			httpRequest.connect();

			InputStream inStream = httpRequest.getInputStream();

			// 将数据流转换成字符串
			result = convertStreamToString(inStream);

			httpRequest.disconnect();
			inStream.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private String getResultWithHttpClient(String strUrl) {

		String result = "communication http client";

		HttpClient httpClient = new DefaultHttpClient();

		// get方式
		// HttpGet httpGet = new HttpGet(strUrl);

		// post方式
		HttpPost httpPost = new HttpPost(strUrl);

		// 设置连接和读取参数
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
		HttpConnectionParams.setSoTimeout(params, 1 * 1000);

		try {
			// HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			// 处理获取的数据
			if (httpEntity != null) {
				InputStream inStream = httpEntity.getContent();
				// 流转字符串
				result = convertStreamToString(inStream);

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private String convertStreamToString(InputStream inStream) {
		String str = "";

		try {
			InputStreamReader inReader = new InputStreamReader(inStream,
					"UTF-8");
			BufferedReader bufferReader = new BufferedReader(inReader);
			StringBuffer strBuffer = new StringBuffer();
			String s;
			while ((s = bufferReader.readLine()) != null) {
				strBuffer.append(s);
			}

			str = strBuffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	private void testWebViewToServer() {
		WebView webView = new WebView(this);
		webView.loadUrl(url04);

		// 一种方式
		final Activity activity = this;

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				activity.setProgress(progress * 1000);
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(Communication.this, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}
		});

		setContentView(webView);
	}

}
