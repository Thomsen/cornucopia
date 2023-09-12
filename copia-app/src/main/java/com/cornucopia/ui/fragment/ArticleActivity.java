package com.cornucopia.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cornucopia.R;
import com.cornucopia.ui.animation.ZoomOutPageTransformer;

public class ArticleActivity extends FragmentActivity implements HeadlineFragment.OnHeadlineSelectedListener {

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ui_new_article);


//		if (findViewById(R.id.fragment_container) != null) {
//
//			if (savedInstanceState != null) {
//				return ;
//			}
//
//			HeadlineFragment headline = new HeadlineFragment();
//
//			headline.setArguments(getIntent().getExtras());
//
//			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, headline).commit();
//		}

		View rootView = findViewById(R.id.ui_container);
		if (rootView instanceof ViewPager) {

			mPager = (ViewPager) rootView;

			mPager.setPageTransformer(true, new ZoomOutPageTransformer());
//		mPager.setPageTransformer(true, new DephPageTransformer());

			mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
			mPager.setAdapter(mPagerAdapter);
		} else if (rootView instanceof LinearLayout) {
//			onArticleSelected(0);
			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_headline);
			if (fragment instanceof HeadlineFragment) {
				((HeadlineFragment) fragment).performClick(0);
			}
		}

	}

//	Unable to start activity ComponentInfo{com.cornucopia/com.cornucopia.ui.fragment.ArticleActivity}:
//	android.view.InflateException: Binary XML file line #14 in com.cornucopia:layout/ui_new_article:
//	Binary XML file line #14 in com.cornucopia:layout/ui_new_article: Error inflating class fragment
	// screen switch to LinearLayout crash (layout-land)


	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
//		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPagerAdapter != null) {
			mPagerAdapter.saveState();
		}
		if (mPager != null) {

			mPager.clearOnPageChangeListeners();
			mPager = null;
		}
	}

	@Override
	public void onArticleSelected(int position) {

//		ArticleFragment articleFragment =
//		(ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_article);
//
//		if (articleFragment != null) {
//			articleFragment.updateArticleView(position);
//		} else {
		ArticleFragment article = new ArticleFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ArticleFragment.POSITION_STATE, position);
		article.setArguments(bundle);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		transaction.add(R.id.fragment_article, article);
		// click always back event
		transaction.addToBackStack(null);

		transaction.commit();
//		}

	}

}
