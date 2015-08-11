package com.cornucopia.ui.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cornucopia.R;

public class ActionBar extends RelativeLayout {
	
	private Context mContext;

	private TextView mActionTitle;
	
	private ImageButton mActionSearch;
	
	private ProgressBar mActionProgress;
	
	private OnActionMenuClickedListener onActionMenuClickedListener;

	public ActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		
		LayoutInflater infalter = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout = (RelativeLayout) infalter.inflate(R.layout.action_bar, this); // null -> this or addView()
//		addView(relativeLayout);
		
		mActionTitle = (TextView) relativeLayout.findViewById(R.id.tv_action_title);
		mActionSearch = (ImageButton) relativeLayout.findViewById(R.id.ib_action_search); // 动态加载，就是动态布局ImabeButton，超出的隐藏实现
		// 自定义一个Action接口，多态实现不同的action
		mActionProgress = (ProgressBar) relativeLayout.findViewById(R.id.pb_action_search);
		
		mActionSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 外部监听， 此处触发
				if (onActionMenuClickedListener != null) {
					onActionMenuClickedListener.eventOccured(v.getId());
				}
			}
		});
	}

	public ActionBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ActionBar(Context context) {
		this(context, null);
	}
	
	public void setTitle(CharSequence title) {
		mActionTitle.setText(title);
	}
	
	public void setTitle(int resId) {
		mActionTitle.setText(resId);
	}
	
//	public void setSearchViewGone(boolean isGone) {
//		if (isGone) {
//			mActionSearch.setVisibility(View.GONE);
//		}
//	}
//	to
	
	public void hideMenu() {
		mActionSearch.setVisibility(View.GONE);
	}
	
	public void showMenu() {
		mActionSearch.setVisibility(View.VISIBLE);
	}
	
	public void hideProgressBar() {
		mActionProgress.setVisibility(View.GONE);
	}
	
	public void showProgressBar() {
		mActionProgress.setVisibility(View.VISIBLE);
	}
	
	public boolean isProgressBarVisiable() {
		return mActionProgress.isShown();
	}
	
	public void setOnActionMenuClickedListener(OnActionMenuClickedListener listener) {
		onActionMenuClickedListener = listener;
	}
	
	interface OnActionMenuClickedListener {
		public void eventOccured(int id);
	}
}
