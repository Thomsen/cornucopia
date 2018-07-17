package com.cornucopia.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cornucopia.R;
//import com.cornucopia.tools.CopiaAnnotation;

//@CopiaAnnotation(name="loading")
public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// code进行布局
		LinearLayout.LayoutParams linearParmas = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		LinearLayout.LayoutParams textParmas = linearParmas;
		textParmas.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		
		TextView textView = new TextView(this);
		textView.setLayoutParams(textParmas);
//		textView.setText("loding...");
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setTextColor(Color.BLUE);

//		setContentView(textView);
		
		// 优化登录界面，使用splash
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setLayoutParams(linearParmas);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
//		linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		linearLayout.setBackgroundColor(Color.TRANSPARENT);

		linearLayout.addView(textView);
		
		LinearLayout.LayoutParams imageParmas = textParmas;
		
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(imageParmas);
		
		imageView.setBackgroundResource(R.drawable.loading_flag);
		
		// 设置动画效果
		final AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
		imageView.post(new Runnable() {

			@Override
			public void run() {
				frameAnimation.start();
			}
			
		});
		
		// test addView
//		linearLayout.addView(textView);  // The specified child already has a parent.
//		TextView textView2 = new TextView(this);
//		textView2.setText("test addView");
//		linearLayout.addView(textView2);
		// 明白，没有给LinearLayout设置布局走向
		
//		linearLayout.addView(imageView);
		linearLayout.addView(imageView, 1);
		
		setContentView(linearLayout);

		// 1、android中使用handler操作线程
//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				Intent intent = new Intent(LoadingActivity.this,
//						Cornucopia.class);
//				
//				// 结束登录的activity
//				finish();
//			
//				startActivity(intent);
//			}
//
//		}, 3000);
		
		// 使用一个变量来代替LoadingActivity，在Thread中使用
		final LoadingActivity sLoadingActivity = this;
		
		// 2、使用Thread
		
		new Thread() {

			@Override
			public void run() {
				super.run();
				
				try {
					// 线程的工作时长
					synchronized(this) {
						wait(3000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 关闭登录的activity
				finish();
				
				Intent intent = new Intent();
				intent.setClass(sLoadingActivity, Cornucopia.class);
				
				startActivity(intent);
				
//				stop(); // 在android中，该方法已过时
			}
			
		}.start();
		
	}

}
