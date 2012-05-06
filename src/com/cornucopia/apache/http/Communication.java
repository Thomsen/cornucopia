package com.cornucopia.apache.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cornucopia.R;

public class Communication extends Activity implements OnClickListener {

	TextView mTextView;
	
	Button mButtonUrlGet;
	Button mButtonUrlPost;
	Button mButtonHttpGet;
	Button mButtonHttpPost;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.apache_communication);
		
		setupViews();
	}
	
	@Override
	public void onClick(View v) {
		String url = "http://www.baidu.com";
		
		switch (v.getId()) {
		case R.id.conn_url_get: {
			mTextView.setText(getResultWithUrlGet(url));
			break;
		}
		case R.id.conn_url_post: {
			mTextView.setText(getResultWithUrlPost(url));
			break;
		}
		case R.id.conn_http_get: {
//			url = "http://www.baidu.com";
			mTextView.setText(getResultWithHttpGet(url));
			break;
		}
		case R.id.conn_http_post: {
			mTextView.setText(getResultWithHttpPost(url));
			break;
		}
		}
		
	}
	
	private void setupViews() {
		mButtonUrlGet = (Button) findViewById(R.id.conn_url_get);
		mButtonUrlPost = (Button) findViewById(R.id.conn_url_post);
		mButtonHttpGet = (Button) findViewById(R.id.conn_http_get);
		mButtonHttpPost = (Button) findViewById(R.id.conn_http_post);
		
		mButtonUrlGet.setOnClickListener(this);
		mButtonUrlPost.setOnClickListener(this);
		mButtonHttpGet.setOnClickListener(this);
		mButtonHttpPost.setOnClickListener(this);
		
		mTextView = (TextView) findViewById(R.id.conn_content);
	}

	private String getResultWithUrlGet(String strUrl) {
		String result = "communication url get";

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

	private String getResultWithHttpGet(String strUrl) {

		String result = "communication http get";

		HttpClient httpClient = new DefaultHttpClient();

		// get方式
		 HttpGet httpGet = new HttpGet(strUrl);

		// post方式
//		HttpPost httpPost = new HttpPost(strUrl);

		// 设置连接和读取参数
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
		HttpConnectionParams.setSoTimeout(params, 1 * 1000);

		try {
			 HttpResponse httpResponse = httpClient.execute(httpGet);
//			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			// 处理获取的数据
			if (httpEntity != null) {
				InputStream inStream = httpEntity.getContent();
				// 流转字符串
				result = convertStreamToString(inStream);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private CharSequence getResultWithHttpPost(String url) {
		
		String result = "communication http post";

		
		return result;
	}

	private String convertStreamToString(InputStream inStream) {
		String result = "result";

		try {
			InputStreamReader inReader = new InputStreamReader(inStream,
					"UTF-8");
			BufferedReader bufferReader = new BufferedReader(inReader);
			StringBuffer strBuffer = new StringBuffer();
			String s;
			while ((s = bufferReader.readLine()) != null) {
				strBuffer.append(s);
			}

			result = strBuffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private CharSequence getResultWithUrlPost(String url) {
		String result = "communication url post";

		return result;
	}

	public void testWebViewToServer() {
		WebView webView = new WebView(this);
		webView.loadUrl("http://www.baidu.com");

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
