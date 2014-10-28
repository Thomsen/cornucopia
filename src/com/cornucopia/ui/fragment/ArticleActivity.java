package com.cornucopia.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.cornucopia.R;

public class ArticleActivity extends FragmentActivity implements HeadlineFragment.OnHeadlineSelectedListener {
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ui_new_article);
		
		
		if (findViewById(R.id.fragment_container) != null) {
			
			if (savedInstanceState != null) {
				return ;
			}
			
			HeadlineFragment headline = new HeadlineFragment();
			
			headline.setArguments(getIntent().getExtras());
			
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, headline).commit();
		}
	}

	@Override
	public void onArticleSelected(int position) {
		
		ArticleFragment articleFragment = 
		(ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_article);
			
		if (articleFragment != null) {
			articleFragment.updateArticleView(position);
		} else {
			ArticleFragment article = new ArticleFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(ArticleFragment.POSITION_STATE, position);
			article.setArguments(bundle);
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			
			transaction.add(R.id.fragment_container, article);
			transaction.addToBackStack(null);
			
			transaction.commit();
		}

	}
	
}
