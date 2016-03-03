package com.cornucopia.ui.fragment;

import android.R;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MenuFilterFragment extends BaseFragment {
	
	// must, did not create a view
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
//		return super.onCreateView(inflater, container, bundle);
		
		View view = inflater.inflate(R.layout.list_content, null);
		ListView lv = (ListView) view.findViewById(R.id.list);
//		lv.setBackgroundColor(Color.GREEN);
		ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, 
				R.id.text1, new String[]{"hehe", "haha"});
		lv.setAdapter(adapter);
		
		return view;
	}

}
