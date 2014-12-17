package com.cornucopia.basic.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends Activity {
	
	private Activity mActivity;
	private IRemoteService mIRemoteService;
	
	private TextView mTextView;
	private Button mButton;

	private ServiceConnection mConnecitonService = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			Toast.makeText(mActivity, "绑定服务", Toast.LENGTH_SHORT).show();
			
			mIRemoteService = IRemoteService.Stub.asInterface(arg1);
			
			if (mIRemoteService == null) {
				mButton.setEnabled(false);
			} else {
				mButton.setEnabled(true);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Toast.makeText(mActivity, "服务中断", Toast.LENGTH_SHORT).show();
			
			// 只有发现异常服务中断时，才会调用该方法
			mIRemoteService = null;
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity = this;
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		mTextView = new TextView(this);
		mButton = new Button(this);
		
		mButton.setText("显示远程服务的ID");
		
		linearLayout.addView(mTextView);
		linearLayout.addView(mButton);
		
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// 点击时的mIRemoteService为null， 并不是null，是因为不是String
				setTextViewContent();
				
			}

			
		});
		
		setContentView(linearLayout);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// 通过string的action，需要manifest的action定义，在多应用中最好采用这种方式
//		Intent intent = new Intent("com.cornucopia.service.server.RemoteExService");
		
//		Intent intent = new Intent(mActivity, RemoteExService.class);
		// 需要在manifest配置IRemoteService，正确的配置成action
		Intent intent = new Intent(IRemoteService.class.getName());
		
		// 启动服务
//		startService(intent);
		
		// 启动服务，并没有调用onBind方法，需要绑定服务
		bindService(intent, mConnecitonService, Context.BIND_AUTO_CREATE);
		
		// 启动时的NullPointerException
//		setTextViewContent();
		

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// 结束服务的进程
		try {
			android.os.Process.killProcess(mIRemoteService.getPid());
		} catch (RemoteException e) {
			Toast.makeText(mActivity, "服务已停止", Toast.LENGTH_SHORT).show();
		}
		
		unbindService(mConnecitonService);
	}

	private void setTextViewContent() {
		try {
			// 调用mIRemoteService前需要绑定服务
			mTextView.setText(String.valueOf(mIRemoteService.getPid()));
		} catch (RemoteException e) {
			mTextView.setText("exception");
		}
	}

}
