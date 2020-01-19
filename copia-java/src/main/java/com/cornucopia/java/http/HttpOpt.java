package com.cornucopia.java.http;

import com.cornucopia.utils.StringUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HttpOpt {

//    private Context mContext;
//
//    public HttpOpt(Context context) {
//        mContext = context;
//    }

    public void httpGet(String url, HttpParams params) {
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
                response = StringUtils.convertStreamToString(inStream);
                System.out.println("get request " + response);
            } else {
                System.out.println("get request bad!");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void httpPost(String url, List<NameValuePair> params) {
        HttpClient httpClient = new DefaultHttpClient();
        // connect timeout
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        // socket timeout (read and write)
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

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
                final String response = StringUtils.convertStreamToString(inStream);
                System.out.println("response: " + response);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void httpConn() {
        // easy compatible
    }
}
