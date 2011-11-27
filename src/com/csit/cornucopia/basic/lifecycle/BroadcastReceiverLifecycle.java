package lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverLifecycle extends BroadcastReceiver {

	public final static String TAG_RECEIVER_LIFECYCLE = "tag_receiver_lifecycle";
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.i(TAG_RECEIVER_LIFECYCLE, "onReceiver run...");
		
		//test hudson
	}

}
