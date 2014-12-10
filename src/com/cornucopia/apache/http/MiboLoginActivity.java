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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MiboLoginActivity extends Activity implements OnClickListener {
    
    private EditText mEtServerAddress;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnConfirm;
    
    private static final String SERVER_ADDRESS = "http://192.168.1.116:3000"; 

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
                    httpPost();
                }
            }.start();
        }
    }
    
    private void httpGet() {
        
    }
    
    private void httpPost() {
        HttpClient httpClient = new DefaultHttpClient();
        String loginUrl = SERVER_ADDRESS + "/users/login";
        HttpPost httpPost = new HttpPost(loginUrl);
        
        HttpParams params = httpClient.getParams();
        params.setParameter("username", mEtUsername.getText().toString());
        params.setParameter("password", mEtPassword.getText().toString());
        httpPost.setParams(params);
        
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
