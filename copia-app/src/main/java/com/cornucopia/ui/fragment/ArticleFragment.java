package com.cornucopia.ui.fragment;

import com.cornucopia.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {

	int mCurrentPosition = 0;

	public static final String POSITION_STATE = "position";

	public ArticleFragment() {
    }

	public ArticleFragment(int position) {
	    mCurrentPosition = position;
    }

	private String[] articles = new String[] {
			"article one",
			"article two",
			"article three",
			"article four",
			"article five"
	};

	private int[] colors = new int[] {
	        Color.RED,
	        Color.BLUE,
	        Color.WHITE,
	        Color.YELLOW,
	        Color.GRAY
	};

	private ViewGroup rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(POSITION_STATE);
		}

		rootView =  (ViewGroup) inflater.inflate(R.layout.ui_article_view, container, false);
		return rootView;
	}

	public void onStart() {
		super.onStart();

		Bundle bundle = getArguments();

//		if (bundle != null) {
//			updateArticleView(bundle.getInt(POSITION_STATE));
//		} else if (mCurrentPosition != -1)  {
			updateArticleView(mCurrentPosition);
//		}

	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(POSITION_STATE, mCurrentPosition);
	}

	public void updateArticleView(int position) {
//		TextView article = (TextView) getActivity().findViewById(R.id.article); // error
	    TextView article = (TextView) rootView.findViewById(R.id.article);
		article.setText(articles[position]);
		article.invalidate();
//		mCurrentPosition = position;

		rootView.setBackgroundColor(colors[position]);
	}
}
