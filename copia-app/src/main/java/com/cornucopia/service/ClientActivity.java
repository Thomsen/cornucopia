package com.cornucopia.service;

import com.bugsnag.android.Client;
import com.cornucopia.R;
import com.cornucopia.kotlin.service.ILocalService;
import com.cornucopia.kotlin.service.LocalService;
import com.cornucopia.utils.IntentUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends Activity {
	
	private Activity mActivity;
	private IRemoteService mIRemoteService;

	private ILocalService mLocalService;

//	private boolean isBoundLocal = false;
	private boolean isBoundRemote = false;
	
	private TextView mTextViewRemote;
	private Button mBtnRemoteService;
	private Button mBtnCloseRemoteService;

	private TextView mTextViewLocal;
	private Button mBtnLocalService;

	private ServiceConnection mConnectionLocalService = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// remote process, binder must local service
			// java.lang.ClassCastException: android.os.BinderProxy cannot be cast to com.cornucopia.kotlin.service.LocalService$LocalBinder
			LocalService.LocalBinder binder = (LocalService.LocalBinder) service;

			mLocalService = binder.getService();

			mTextViewLocal.setText("本地服务： " + mLocalService.getName());
			mTextViewLocal.setVisibility(View.VISIBLE);
			mBtnLocalService.setText(R.string.unbind_local_service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// 不是onServiceConnected的逆过程
			// 是在服务崩溃或杀死的时候被调用，unbind主要是清理bind的对象
			// 被动状态会触发
			mLocalService = null;
		}

	};

	private ServiceConnection mConnectionRemoteService = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mIRemoteService = IRemoteService.Stub.asInterface(service);
			
			if (mIRemoteService == null) {
				mBtnRemoteService.setEnabled(false);
			} else {
				mBtnRemoteService.setEnabled(true);
			}

			isBoundRemote = true;

			// 显示进程ID
			setTextViewContent();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(mActivity, name + " disconnected", Toast.LENGTH_SHORT).show();
			
			// 只有发现异常服务中断时，才会调用该方法
			mIRemoteService = null;
			isBoundRemote = false;
			setTextViewContent();
		}
		
	};
	
	Messenger mMessenger = null;
	
	private ServiceConnection mMessengerService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
        }
	    
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity = this;
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		mTextViewRemote = new TextView(this);
		mBtnRemoteService = new Button(this);
		mBtnCloseRemoteService = new Button(this);

		mTextViewLocal = new TextView(this);
		mTextViewLocal.setVisibility(View.GONE);
		mBtnLocalService = new Button(this);

		mBtnLocalService.setText(R.string.bind_local_service);
		mBtnRemoteService.setText(R.string.bind_remote_service);
		// show/hide
		mBtnCloseRemoteService.setText(R.string.unbind_remote_service);

		Button btnSendLocal = new Button(this);
		btnSendLocal.setText(R.string.send_local_service);

		linearLayout.addView(mTextViewLocal);

		linearLayout.addView(mBtnLocalService);
		linearLayout.addView(btnSendLocal);
		linearLayout.addView(mTextViewRemote);
		linearLayout.addView(mBtnRemoteService);
		linearLayout.addView(mBtnCloseRemoteService);
		visibleCloseRemote();

		mBtnLocalService.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mLocalService && mLocalService.isBound()) {
					// 怎样停止绑定的服务
					mActivity.unbindService(mConnectionLocalService);  // execute onBind
					mActivity.stopService(new Intent(mActivity, LocalService.class)); // onDestroy

					mTextViewLocal.setText("");
					mTextViewLocal.setVisibility(View.GONE);
					mBtnLocalService.setText(R.string.bind_local_service);
				} else {
					Intent intent = new Intent();
					intent.setClass(mActivity, LocalService.class);
					mActivity.bindService(intent, mConnectionLocalService, Context.BIND_AUTO_CREATE);
					// 启动后台服务，页面去绑定他
				}
			}
		});

		btnSendLocal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mLocalService) {
					mLocalService.sendMessage("client");
				} else {
					Toast.makeText(ClientActivity.this, R.string.bind_local_service,
							Toast.LENGTH_LONG).show();
				}
			}
		});
		
		mBtnRemoteService.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent implictIntent = new Intent();
//				implictIntent.setAction("com.cornucopia.basic.service.IRemoteService");
				implictIntent.setAction(IRemoteService.class.getName());
				implictIntent.setPackage(getPackageName());
				Intent intent = IntentUtils.getExplicitIntent(mActivity, implictIntent);
//				Intent intent = new Intent(mActivity, IRemoteService.class);
			    mActivity.bindService(intent, mConnectionRemoteService, Context.BIND_AUTO_CREATE);
			}
		});
		
		mBtnCloseRemoteService.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // 结束服务的进程
                try {
                    // 服务与进程关联了，结束了自己。在manifest配置
                	if (null != mIRemoteService) {
                		android.os.Process.killProcess(mIRemoteService.getPid());
                	}
                } catch (RemoteException e) {
                    Toast.makeText(mActivity, "服务已停止", Toast.LENGTH_SHORT).show();
                }
            }
        });
		
		Button btnMessenger = new Button(this);
		btnMessenger.setText(R.string.bind_messenger);
		
		btnMessenger.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain(null, MessengerService.HANDLE_MESSENGER, 0, 0);
				msg.obj = "client activity";
                try {
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
		linearLayout.addView(btnMessenger);
		
		setContentView(linearLayout);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		// 通过string的action，需要manifest的action定义，在多应用中最好采用这种方式
//		Intent intent = new Intent("com.cornucopia.service.server.RemoteExService");
		
//		Intent intent = new Intent(mActivity, RemoteExService.class);
		// 需要在manifest配置IRemoteService，正确的配置成action
//		Intent intent = new Intent(IRemoteService.class.getName()); // android 5.0
		
		// 启动服务。不启动，在系统-正在运行的服务中就看不到，但进程依然存在，是一个缓存应用程序(cached process)。
//		startService(intent); 

		// 启动服务，并没有调用onBind方法，需要绑定服务
//		bindService(IntentUtils.getExplicitIntent(this, intent), mConnectionRemoteService, Context.BIND_AUTO_CREATE);
		
		// messenger bound service
		bindService(new Intent(this, MessengerService.class), mMessengerService, Context.BIND_AUTO_CREATE);

	}
	
	@Override
	protected void onStop() {
		super.onStop();

		if (isBoundRemote) {
			unbindService(mConnectionRemoteService);
		}

		unbindService(mMessengerService);

		if (null != mLocalService && mLocalService.isBound()) {
			unbindService(mConnectionLocalService);
		}
	}

	private void setTextViewContent() {
		try {
			// 调用mIRemoteService前需要绑定服务
		    if (null != mIRemoteService) {
		        mTextViewRemote.setText("远程服务：" + String.valueOf(mIRemoteService.getPid()));
		    } else {
				mTextViewRemote.setText("远程服务：无");
		    }
		    visibleCloseRemote();
		} catch (RemoteException e) {
			mTextViewRemote.setText("exception");
		}
	}
	
	private void visibleCloseRemote() {
	    if (null == mIRemoteService) {
	    	mTextViewRemote.setVisibility(View.GONE);
	        mBtnCloseRemoteService.setVisibility(View.GONE);
	    } else {
			mTextViewRemote.setVisibility(View.VISIBLE);
			mBtnCloseRemoteService.setVisibility(View.VISIBLE);
	    }
	}

}
