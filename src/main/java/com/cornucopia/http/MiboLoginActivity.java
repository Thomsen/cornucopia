package com.cornucopia.http;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cornucopia.R;
import com.cornucopia.http.volley.GsonRequest;
import com.cornucopia.http.volley.MiboUser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiboLoginActivity extends Activity implements OnClickListener {
    
    private EditText mEtServerAddress;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnConfirm;
    
    private String serverAddress = "http://192.168.1.116:3000";

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
            if (!TextUtils.isEmpty(mEtServerAddress.getText())) {
                String addr = mEtServerAddress.getText().toString();
                if (addr.startsWith("http://")) {
                    serverAddress = addr;
                } else {
                    serverAddress = "http://" + addr;
                }
                
            }
//            new Thread() {
//                public void run() {
//                	login();
//                }
//            }.start();
            
            volleyLogin();
        }
    }
    
    private void show() {
    	String showUrl = serverAddress + "/users/17";
    	HttpParams params = new BasicHttpParams();
//    	params.setIntParameter("id", 17);
    	httpGet(showUrl, params);
    }
    
    private void login() {
    	String loginUrl = serverAddress + "/users/login";
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("username", mEtUsername.getText().toString()));
    	params.add(new BasicNameValuePair("password", mEtPassword.getText().toString()));
    	httpPost(loginUrl, params);
    }
    
    private void volleyShow() {
        String showUrl = serverAddress + "/users/1";
        JSONObject jsonRequest = new JSONObject();
        volleyGet(showUrl, jsonRequest);
    }
    
    private void volleyLogin() {
        String loginUrl = serverAddress + "/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", mEtUsername.getText().toString());
        params.put("password", mEtPassword.getText().toString());
        JSONObject jsonRequest = new JSONObject(params);
        TypeToken<MiboUser> typeOf = new TypeToken<MiboUser>(){};
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(new GsonRequest<>(Method.POST, loginUrl, typeOf, null, jsonRequest, new Response.Listener<MiboUser>() {

            @Override
            public void onResponse(MiboUser response) {
                Log.i("thom", "username " + response.getUsername());
                Toast.makeText(getApplicationContext(), "username " + response.getUsername(),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("thom", "error response " + error.getMessage());
            }
        }));
        queue.start();
        
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
        
        try {
            HttpResponse httpResp = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResp.getEntity();
            if (null != httpEntity) {
                InputStream inStream = httpEntity.getContent();
                final String response = convertStreamToString(inStream);
                
                Log.i("thom", "response: " + response);
                runOnUiThread(new Runnable() {

        			@Override
        			public void run() {
        				Toast.makeText(MiboLoginActivity.this, response, Toast.LENGTH_SHORT).show();
        			}
                	
                });
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private void volleyGet(String url, JSONObject jsonRequest) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(new JsonObjectRequest(Method.GET, url, jsonRequest,
          new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("thom", "volley login " + response);
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
            
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("thom", "message " + error.getMessage());
            }
        }));
        queue.start();
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
