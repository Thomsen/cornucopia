package com.cornucopia.ticketsmanager;

import com.cornucopia.R;

import android.app.Activity;
import android.os.Bundle;

public class AddTicketsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 做到这里，先建立一个layout，然后在建立布局文件，不过这里需要先建立布局文件，而不能直接建立变量
		setContentView(R.layout.application_activity_addtickets);
	}

}
