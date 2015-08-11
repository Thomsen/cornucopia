package com.cornucopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cornucopia.R;

public class MenuListFragment extends BaseFragment {
	
	ListView mMainMenu;
	
	ListView mSubMenu;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		Log.i("thom", "Menu List Fragment onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.i("thom", "Menu List Fragment onCreateView");

//		View view = inflater.inflate(R.layout.activity_list_item, null);
//		View view = inflater.inflate(R.layout.activity_list_item, container);
//		View view = inflater.inflate(R.layout.activity_list_item, container, false);
//		TextView tv = (TextView) view.findViewById(R.id.text1);
//		tv.setText("menu list fragment");
//		tv.setBackgroundColor(Color.BLUE);
		
//		View view = inflater.inflate(R.layout.list_content, null);
//		ListView lv = (ListView) view.findViewById(R.id.list);
//		ListAdapter adapter = new ArrayAdapter<String>(getActivity(), 
//				R.layout.activity_list_item, R.id.text1, new String[] {"menu1", "menu2"});
//		lv.setAdapter(adapter);
		
		View view = inflater.inflate(R.layout.fragment_menu_list, null);
		mMainMenu = (ListView) view.findViewById(R.id.menu_main);
		mSubMenu = (ListView) view.findViewById(R.id.menu_sub);
		
		ListAdapter adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.activity_list_item, android.R.id.text1, new String[] {"menu1", "menu2"});
		mMainMenu.setAdapter(adapter);
		
		ListAdapter adapter2 = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.activity_list_item, android.R.id.text1, new String[] {"sub1", "sub2"});
		mSubMenu.setAdapter(adapter2);
		
		return view;
		
//		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		Log.i("thom", "Menu List Fragment onAttach");
	}
	
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		
		Log.i("thom", "Menu List Fragment onActivityCreated");
	}
	
	public void onDestroyView() {
		super.onDestroyView();
	
		Log.i("thom", "Menu List Fragment onDestroyView");
	}
	
	public void onDestroy() {
		super.onDestroy();
		
		Log.i("thom", "Menu List Fragment onDestroy");
	}
	
	public void onDetach() {
		super.onDetach();
		
		Log.i("thom", "Menu List Fragment onDetach");
	}
}
