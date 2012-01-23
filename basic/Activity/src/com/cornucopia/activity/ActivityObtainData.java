package com.cornucopia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cornucopia.activity.data.TextFieldsParcel;

public class ActivityObtainData extends Activity {

	@Override
	public Intent getIntent() {
		return super.getIntent();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
//		Log.i("thom", "1 " + getIntent().getExtras().getString("test"));
//		Log.i("thom", "2 " + getIntent().getStringExtra("test"));
		
//		Bundle bundle = getIntent().getExtras();
		Bundle bundle = getIntent().getBundleExtra("bundle");
		Log.i("thom", "3 " + bundle.getString("date"));
		
		TextFieldsParcel textParcel = getIntent().getParcelableExtra("parcelable");
		
		// Parcel的初始化对象
//		Parcel dest = Parcel.obtain();
//		textParcel.writeToParcel(dest, 0);
		
		Log.i("thom", "4 " + textParcel.getPlainText());
		
	}

}
