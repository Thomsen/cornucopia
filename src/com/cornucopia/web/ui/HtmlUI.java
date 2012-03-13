package com.cornucopia.web.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class HtmlUI extends Activity {
	
	WebView mWebView;

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
		
		// 设置javascript插件，并将其命名为addUser，在html中的onload加载并调用getUsers()
		mWebView.addJavascriptInterface(new AddUserPlugin(), "addUser");
	}
	
	class AddUserPlugin {
		public void getUsers() {
			List<UserInfo> uList = getUserList();
			
			// 转化为json格式数据 [{name, sex, date, contact}, {name, sex, date, contact}]
			
			JSONArray jsonArray = new JSONArray();
			
			for (UserInfo uInfo : uList) {
				JSONObject jsonObject = new JSONObject();
				
				try {
					jsonObject.put("name", uInfo.getName());
					jsonObject.put("sex", uInfo.getSex());
					jsonObject.put("date", uInfo.getDate());
					jsonObject.put("contact", uInfo.getContact());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				jsonArray.put(jsonObject);
				
				
				
			}
			
			// addUserShow方法是在add_table.js中定义的
			mWebView.loadUrl("javascript:addUserShow('" + jsonArray.toString() + "')");
		}
	}
	
	private List getUserList() {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		
		userList.add(new UserInfo("汪长森", "男", "16/10/1989", "androidfoross@gmail.com"));
		userList.add(new UserInfo("thom", "male", "16/10/1989", "androidfoross@gmail.com"));
		
		
		return userList;
		
	}
	
	class UserInfo {
		private String name;
		private String sex;
		private String date;
		private String contact;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		
		public UserInfo(String name, String sex, String date, String contact) {
			super();
			this.name = name;
			this.sex = sex;
			this.date = date;
			this.contact = contact;
		}
		
		
	}

}
