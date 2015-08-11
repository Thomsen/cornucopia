package com.cornucopia.ui.dynamic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cornucopia.R;
import com.cornucopia.ui.dynamic.DynamicLayoutWrapper.OnRadioCheckedListener;
import com.google.gson.Gson;

public class SearchbarDemo extends Activity {

	private RelativeLayout mHeaderBar; // include
	private ActionBar mActionBar;
	private PopupWindow mPopupWindow;
	
//	DynamicLayoutUtil layoutUtil = null;
	DynamicLayoutWrapper layoutUtil = null;
	LinearLayout convertView = null;
	ArrayList<LayoutDto> listLayout = null;
	
	ArrayList<LayoutDto> listLayoutDto = null; // radiobutton关联的查询条件
	
	ArrayList<RadioButton> listRadio = new ArrayList<RadioButton>();
//	HashMap<RadioButton, Boolean> 
	int indexChecked = 0;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_searchbar);
		
		mHeaderBar = (RelativeLayout) findViewById(R.id.header);
		mActionBar = (ActionBar) findViewById(R.id.header_action);
		
//		layoutUtil = new DynamicLayoutUtil(this);
		layoutUtil = new DynamicLayoutWrapper(this);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = (LinearLayout) inflater.inflate(R.layout.panel_content, null);
		
		initPopupWindow();
		
//		mHeaderBar.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "header click", Toast.LENGTH_SHORT).show();
//			}
//		});
		
		mActionBar.setTitle("test title");
		mActionBar.hideMenu();
		
		mHeaderBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mActionBar.showMenu();
				if (mPopupWindow.isShowing()) {
//					Toast.makeText(getApplicationContext(), "正在查询", Toast.LENGTH_SHORT).show();
//					closePopupWindow();
//					
//					ArrayList<LayoutDataDto> queryData = layoutUtil.getLayoutValue(convertView, listLayoutDto);
//					Gson gson = new Gson();
//					Log.d("thom", "search: " + gson.toJson(queryData));
				} else {
					Toast.makeText(getApplicationContext(), "请选择查询条件", Toast.LENGTH_SHORT).show();
					showPopupWindow();
				}
				
			}
		});
		
		mActionBar.setOnActionMenuClickedListener(new ActionBar.OnActionMenuClickedListener() {
			
			@Override
			public void eventOccured(int id) {
//				Toast.makeText(getApplicationContext(), "view id: " + id, Toast.LENGTH_SHORT).show();
				
//				ImageView iSearch = (ImageView) mActionBar.findViewById(id);
//				iSearch.setBackgroundColor(Color.TRANSPARENT);
				
//				mActionBar.showProgressBar();
				
//				initPopupWindow(); // 每次init会创建新的对象，只需要一次 ， 如果close时设置了null，那么还需要在这里进行初始化，初始化时判断。 在这里初始化后对convertView有影响，重新定义初始化的位置即可。
				if (mPopupWindow.isShowing()) {
					Toast.makeText(getApplicationContext(), "正在查询", Toast.LENGTH_SHORT).show();
					closePopupWindow();
					mActionBar.hideMenu();
					
					ArrayList<LayoutDataDto> queryData = layoutUtil.getLayoutValue(convertView, listLayoutDto);
					Gson gson = new Gson();
					Log.d("thom", "search: " + gson.toJson(queryData));
				} 
//				else {
//					Toast.makeText(getApplicationContext(), "请选择查询条件", Toast.LENGTH_SHORT).show();
//					showPopupWindow();
//				}
				
			}
		});
	}
	
	protected void onDestroy() { // funk(onDestory), error again
		super.onDestroy();
		
		closePopupWindow();
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			closePopupWindow();
			mActionBar.hideMenu();
			break;
		}
		default:
			break;
		}
		
		return super.onTouchEvent(event);
	}

	protected void showPopupWindow() {
//		PopupWindow pw = new PopupWindow(this);
		
//		PopupWindow pw = new PopupWindow(200, 300);
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.activity_main, null);
//		pw.setContentView(rl);
		
//		pw.showAsDropDown(mActionBar);
//		pw.showAtLocation(mActionBar, Gravity.BOTTOM | Gravity.LEFT, 0, 0);

		if (mPopupWindow != null && !mPopupWindow.isShowing()) {
			mPopupWindow.showAsDropDown(mActionBar);
		}
	}
	
	protected void closePopupWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
//			mPopupWindow = null;
		}
	}
	
	int popupHeight = 0;
	
	private void initPopupWindow() {
//		mPopupWindow = new PopupWindow(200, 300); // android2.3 空指针
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		final RelativeLayout convertView = (RelativeLayout) inflater.inflate(R.layout.activity_main, null);
////		mPopupWindow.setContentView(rl);
//		convertView.setBackgroundColor(Color.GREEN);
		
//		convertView = (LinearLayout) inflater.inflate(R.layout.panel_content, null);
		HorizontalScrollView hsv = (HorizontalScrollView) convertView.findViewById(R.id.panel_horizontal_scrollview);
		final RadioGroup rg = new RadioGroup(this);
//		rg.setOrientation(RadioGroup.HORIZONTAL);
		rg.setOrientation(RadioGroup.VERTICAL);
		rg.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
				RadioGroup.LayoutParams.WRAP_CONTENT));
		listLayout = new ArrayList<LayoutDto>();
		LayoutDto layout = null;
		for (int i=0; i<5; i++) {
			layout = new LayoutDto();
			layout.setType(3);
			layout.setLabel("单选选项" + i);
			ArrayList<LayoutDto> inlineLayout = new ArrayList<LayoutDto>();
			LayoutDto lay = new LayoutDto();
			lay.setType(2);
			lay.setLabel("子图 " + i);
			ArrayList<String> listValue = new ArrayList<String>();
			listValue.add("值 " + i);
			lay.setValue(listValue);
			inlineLayout.add(lay);
			layout.setInlineLayout(inlineLayout);
			listLayout.add(layout);
		}
//		layoutUtil.addLinearView(rg, listLayout);
//		layoutUtil.addGroupView(rg, listLayout);
		
//		layoutUtil.addLinearGroupView(rg, listLayout.subList(0, 2));
//		layoutUtil.addLinearGroupView(rg, listLayout.subList(2, 4));
		
//		for (int i=0; i<listLayout.size(); i++ ) {  // 清楚有多少次的循环
//			layoutUtil.addLinearGroupView(rg, listLayout.subList(i, 
//					(i+2 < listLayout.size()) ? i+2 : listLayout.size()));
//			i++;
//		}
		int i = 0;
		while (i < listLayout.size()) {
			int j = ((i+2) > listLayout.size() ? listLayout.size() : (i+2));
			layoutUtil.addLinearGroupView(rg, listLayout.subList(i, j));
			i = j;
		}
		
		listRadio = layoutUtil.getListRadio();
		
		layoutUtil.setOnRadioCheckedListener(new OnRadioCheckedListener() {
			ArrayList<LayoutDto> listTmpDto = null;
			@Override
			public void onChecked(CompoundButton buttonView, boolean isChecked,
					ArrayList<LayoutDto> inlineLayout) {
				
//				Toast.makeText(getApplicationContext(), "" + buttonView.getTag(), Toast.LENGTH_SHORT).show();
				
				if (isChecked) {
					indexChecked = listRadio.indexOf(buttonView);
//					Toast.makeText(getApplicationContext(), "" + listRadio.indexOf(buttonView), Toast.LENGTH_SHORT).show();
					for (int i=0; i<listRadio.size(); i++) {
						listRadio.get(i).setChecked(false);
					}
					listRadio.get(indexChecked).setChecked(true);
				}
				
				if (isChecked) {
					listLayoutDto = new ArrayList<LayoutDto>();
					for (LayoutDto layDto : inlineLayout) {
						layDto.setIsLabelVisiable(false);  // 不显示label
						listLayoutDto.add(layDto);
					}
//					layoutUtil.addGroupView((LinearLayout) rg.getParent(), listLayoutDto);
					layoutUtil.addGroupView(convertView, listLayoutDto); // 2、原本用成员list，改为临时list，防止监听事件顺序
				} else {
					layoutUtil.removeView(convertView, listLayoutDto); // 5、另外一种解决方式，通过手动设置单选的状态
				}
				
				
				// 4、最后解决方式，对监听事件unchecked和checked采用不同的方式
//				else {
//					layoutUtil.removeView(convertView, listLayoutDto);  // 1、listener监听事件顺序问题
//					
//					listLayoutDto = listTmpDto;
//					if (listTmpDto != null) { // 不能要，但是第一个View没有被remove
//						listTmpDto.clear();
//						listTmpDto = null;
//					}
//				}
//				layoutUtil.addGroupView(convertView, listLayoutDto);
//				convertView.invalidate();
			
//				if (isChecked) {  // 3、在外部进行监听的时候，on checked listener有问题，应该是全局监听和单个监听
//					// 显示关联的查询条件
//					listLayoutDto = new ArrayList<LayoutDto>();
//					for (LayoutDto layDto : inlineLayout) {
//						layDto.setIsLabelVisiable(false);
//						listLayoutDto.add(layDto);
//					}
//					layoutUtil.addGroupView(convertView, listLayoutDto);
//				} else {
//					layoutUtil.removeView(convertView , listLayoutDto);
//					if (listLayoutDto != null) {
//						listLayoutDto.clear();
//						listLayoutDto = null;
//					}
//				}
				
			}

//			@Override
//			public void unChecked() {
//				layoutUtil.removeView(convertView , listLayoutDto);
//				if (listLayoutDto != null) {
//					listLayoutDto.clear();
//					listLayoutDto = null;
//				}
//			}
//
//			@Override
//			public void checked(ArrayList<LayoutDto> inlineLayout) {
//				listLayoutDto = new ArrayList<LayoutDto>();
//				for (LayoutDto layDto : inlineLayout) {
//					layDto.setIsLabelVisiable(false);
//					listLayoutDto.add(layDto);
//				}
//				layoutUtil.addGroupView(convertView, listLayoutDto);
//				
//			}
		});
		
		hsv.addView(rg);  // 横向滚动
//		convertView.addView(rg);
		
//		layoutUtil.addQueryView(convertView);
//		convertView.setBackgroundColor(Color.GREEN);
		
//		rl.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
		
//		rl.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), // 空指针
//				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		
//		int height = convertView.getHeight(); // 0 ，实际高度, 是因为view还没有画好就调用了
//		height = rl.getMeasuredHeight(); // 0，最后一次调用measure方法的高度
		
//		rl.post(new Runnable() {
//
//			@Override
//			public void run() {
////				height = rl.getHeight();  // 局部变量中，纠结的final
//				popupHeight = rl.getHeight(); // 没有成功
//				
//				Toast.makeText(getApplicationContext(), "rl height: " + popupHeight, Toast.LENGTH_SHORT).show();
//				
//				if (mPopupWindow == null) {
//					mPopupWindow = new PopupWindow(rl, ViewGroup.LayoutParams.WRAP_CONTENT, popupHeight); // android2.3 没有报空指针
//					
//					mPopupWindow.setOutsideTouchable(true);
//				}
//			}
//			
//		});

//		Toast.makeText(getApplicationContext(), "rl height: " + height, Toast.LENGTH_SHORT).show();
		
		if (mPopupWindow == null) {
//			mPopupWindow = new PopupWindow(rl, ViewGroup.LayoutParams.WRAP_CONTENT, height); // android2.3 没有报空指针
			
			mPopupWindow = new PopupWindow(convertView, WindowManager.LayoutParams.MATCH_PARENT, 
					WindowManager.LayoutParams.WRAP_CONTENT);
			
//			mPopupWindow.setOutsideTouchable(true);
		}
		
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}
	
}
