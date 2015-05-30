package com.cornucopia.service;

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
	private Button mBtnShowRemoteId;
	private Button mBtnCloseRemoteService;

	private ServiceConnection mConnecitonService = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			Toast.makeText(mActivity, "绑定服务", Toast.LENGTH_SHORT).show();
			
			mIRemoteService = IRemoteService.Stub.asInterface(arg1);
			
			if (mIRemoteService == null) {
				mBtnShowRemoteId.setEnabled(false);
			} else {
				mBtnShowRemoteId.setEnabled(true);
			}
			// 显示进程ID
			setTextViewContent();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Toast.makeText(mActivity, "服务中断", Toast.LENGTH_SHORT).show();
			
			// 只有发现异常服务中断时，才会调用该方法
			mIRemoteService = null;
			setTextViewContent();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity = this;
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		mTextView = new TextView(this);
		mBtnShowRemoteId = new Button(this);
		mBtnCloseRemoteService = new Button(this);
		
		mBtnShowRemoteId.setText("绑定远程服务");
		// TODO show/hide
		mBtnCloseRemoteService.setText("关闭远程服务");
		
		linearLayout.addView(mTextView);
		linearLayout.addView(mBtnShowRemoteId);
		linearLayout.addView(mBtnCloseRemoteService);
		
		mBtnShowRemoteId.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    mActivity.bindService(new Intent(IRemoteService.class.getName()),
			            mConnecitonService, Context.BIND_AUTO_CREATE);
			}
		});
		
		mBtnCloseRemoteService.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // 结束服务的进程
                try {
                    // 服务与进程关联了，结束了自己。在manifest配置
                    android.os.Process.killProcess(mIRemoteService.getPid());
                } catch (RemoteException e) {
                    Toast.makeText(mActivity, "服务已停止", Toast.LENGTH_SHORT).show();
                }
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
		
		// 启动服务。不启动，在系统-正在运行的服务中就看不到，但进程依然存在，是一个缓存应用程序(cached process)。
//		startService(intent); 

		// 启动服务，并没有调用onBind方法，需要绑定服务
		bindService(intent, mConnecitonService, Context.BIND_AUTO_CREATE);

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		unbindService(mConnecitonService);
	}

	private void setTextViewContent() {
		try {
			// 调用mIRemoteService前需要绑定服务
		    if (null != mIRemoteService) {
		        mTextView.setText(String.valueOf(mIRemoteService.getPid()));
		    } else {
		        mTextView.setText("无进程ID");
		    }
		    visibleCloseRemote();
		} catch (RemoteException e) {
			mTextView.setText("exception");
		}
	}
	
	private void visibleCloseRemote() {
	    if (null == mIRemoteService) {
	        mBtnCloseRemoteService.setVisibility(View.GONE);
	    } else {
	        mBtnCloseRemoteService.setVisibility(View.VISIBLE);
	    }
	}

}
