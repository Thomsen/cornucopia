package lifecycle;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityLifecycle extends Activity {
	
	public final static String TAG_ACTIVITY_LIFECYCLE = "tag_activity_lifecycle";

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onContentChanged run...");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout linearLayout01 = new LinearLayout(this);
		
		TextView textView = new TextView(this);
		textView.setText("Activity Lifecycle");
		
		linearLayout01.addView(textView);
		
		setContentView(linearLayout01);
		
		// The activity is being created.
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onCreate run...");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onRestart run...");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// The activity is about to become visible.
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onStart run...");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onRestoreInstanceState run...");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onPostCreate run...");
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// The activity has become visible (it is now "resumed").

		Log.i(TAG_ACTIVITY_LIFECYCLE, "onResume run...");
	}
	

	@Override
	protected void onPostResume() {
		super.onPostResume();
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onPostResume run...");
		Log.i(TAG_ACTIVITY_LIFECYCLE, "--------------------------------");
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		// Another activity is taking focus (this activity is about to be "paused").
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onPause run...");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onSaveInstanceState run...");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// The activity is no longer visible (it is now "stopped")

		Log.i(TAG_ACTIVITY_LIFECYCLE, "onStop run...");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// The activity is about to be destroyed.

		Log.i(TAG_ACTIVITY_LIFECYCLE, "onDestory run...");
		Log.i(TAG_ACTIVITY_LIFECYCLE, "--------------------------------");
	}

	
	/*
	 *  横屏切换
	 */

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "onConfigurationChanged run...");
	
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.d(TAG_ACTIVITY_LIFECYCLE, "orientation landcape");
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.d(TAG_ACTIVITY_LIFECYCLE, "orientation portrait");
		}
	}

}
