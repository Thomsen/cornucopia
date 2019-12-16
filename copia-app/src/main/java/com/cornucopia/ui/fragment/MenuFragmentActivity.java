package com.cornucopia.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;

import com.cornucopia.R;

public class MenuFragmentActivity extends FragmentActivity {

	// 一些基本的信息进行逐层嵌套如ActionBar继承FragmentActivity，其他的类似继承ActionBar，Tab
	
	Fragment listFragment;
	Fragment filterFragment;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		Log.i("thom", "on create");
		
		setContentView(R.layout.activity_menu_fragment);
		
	}
	
//	@Override
//	public View onCreateView(String name, Context context, AttributeSet attrs) {
//		
//		Log.i("thom", "on create view");
//		
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View localView = inflater.inflate(R.layout.activity_menu_fragment, null);
//		
//		return localView;
//		
////		return super.onCreateView(name, context, attrs);
//	}
	
	
}
