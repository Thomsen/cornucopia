package com.cornucopia.ui.fragment;

import com.cornucopia.R;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlineFragment extends ListFragment {
	
	private String[] headlines = new String[] {
			"headline one",
			"headline two"
	};
	
	
	OnHeadlineSelectedListener mCallback;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int layout = Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ?
				android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
		
		setListAdapter(new ArrayAdapter<String> (getActivity(), layout, headlines) );
				
	}
	
	public void onStart() {
		super.onStart();
		
		if (getFragmentManager().findFragmentById(R.id.fragment_article) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
//		mCallback = (OnHeadlineSelectedListener) activity;
		
		try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}
	
	public void onListItemClick(ListView lv, View v, int position, long id) {
		super.onListItemClick(lv, v, position, id);
		
		mCallback.onArticleSelected(position);
		
		getListView().setItemChecked(position, true);
	}

	public interface OnHeadlineSelectedListener {
		public void onArticleSelected(int position);
	}
}
