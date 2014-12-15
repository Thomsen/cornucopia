package com.cornucopia.apache.http;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cornucopia.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MiboLoginActivity extends Activity implements OnClickListener {
    
    private EditText mEtServerAddress;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnConfirm;
    
    private static final String SERVER_ADDRESS = "http://192.168.0.102:3031"; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login_test);
        
        mEtServerAddress = (EditText) findViewById(R.id.et_server_address);
        mEtUsername = (EditText) findViewById(R.id.et_login_username);
        mEtPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnConfirm = (Button) findViewById(R.id.btn_login_confirm);
        
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_login_confirm == v.getId()) {
            new Thread() {
                public void run() {
                	show();
                }
            }.start();
        }
    }
    
    private void show() {
    	String showUrl = SERVER_ADDRESS + "/users/17";
    	HttpParams params = new BasicHttpParams();
//    	params.setIntParameter("id", 17);
    	httpGet(showUrl, params);
    }
    
    private void login() {
    	String loginUrl = SERVER_ADDRESS + "/users/login";
    }
    
    private void httpGet(String url, HttpParams params) {
        HttpClient httpClient = new DefaultHttpClient(params);
        HttpGet httpGet = new HttpGet(url);
        
        HttpParams getParams = httpClient.getParams();
        httpGet.setParams(getParams);
        
        String response = "";
        try {
			HttpResponse httpResp = httpClient.execute(httpGet);
			if (httpResp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResp.getEntity();
				InputStream inStream = httpEntity.getContent();
				response = convertStreamToString(inStream);
				Log.d("thom", "get request " + response);
			} else {
				Log.e("thom", "get request bad!");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    private void httpPost(String url, List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        
        HttpPost httpPost = new HttpPost(url);
        try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
        String response = "";
        
        try {
            HttpResponse httpResp = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResp.getEntity();
            if (null != httpEntity) {
                InputStream inStream = httpEntity.getContent();
                response = convertStreamToString(inStream);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("thom", "response: " + response);
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
    
}
