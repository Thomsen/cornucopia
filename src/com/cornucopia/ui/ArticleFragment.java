package com.cornucopia.ui;

import com.cornucopia.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
	
	int mCurrentPosition = -1;
	
	public static final String POSITION_STATE = "position";
	
	private String[] articles = new String[] {
			"article one",
			"article two"
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(POSITION_STATE);
		}
		
		return inflater.inflate(R.layout.ui_article_view, container, false);

	}
	
	public void onStart() {
		super.onStart();
		
		Bundle bundle = getArguments();
		
		if (bundle != null) {
			updateArticleView(bundle.getInt(POSITION_STATE));
		} else if (mCurrentPosition != -1)  {
			updateArticleView(mCurrentPosition);
		}
		
	}
	
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putInt(POSITION_STATE, mCurrentPosition);
	}

	public void updateArticleView(int position) {
		TextView article = (TextView) getActivity().findViewById(R.id.article);
		article.setText(articles[position]);
		mCurrentPosition = position;
	}
}
