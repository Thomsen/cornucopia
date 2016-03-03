package com.cornucopia.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.cornucopia.R;
import com.cornucopia.transmit.ActivityTest;

public class ActivityTestCase extends ActivityInstrumentationTestCase2<ActivityTest> {

	private Activity mActivity;
	private View mView;
	private TextView mTextView;
	
	public ActivityTestCase() {
		super("com.cornucopia", ActivityTest.class);
	}
	
	public ActivityTestCase(String pkg, Class<ActivityTest> activityClass) {
		super(pkg, activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mActivity = this.getActivity();
		mView = mActivity.findViewById(R.id.text_activity_test);
		
		mTextView = (TextView) mActivity.findViewById(R.id.text_activity_test);
	}
	
	// 测试先决条件，检查初始化应用程序状态
	public void testPreconditions() {
		// 断言是否为空
		assertNotNull(mView);
	}
	
	public void testPreconditions2() {
		assertNotNull(mTextView);
	}

	// 测试单元
	public void testTextView() {
		String str = "Activity Test";
		assertEquals(str, mTextView.getText().toString());
	}
}
