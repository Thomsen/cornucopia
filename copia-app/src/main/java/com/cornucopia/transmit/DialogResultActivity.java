package com.cornucopia.transmit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cornucopia.R;

public class DialogResultActivity extends Activity implements View.OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_result);

        findViewById(R.id.button).setOnClickListener(this);

    }

	@Override
	public void onClick(View view) {
		// switch will error: constant expression required
		if (view.getId() == R.id.button) {
			Intent intent = new Intent();
			intent.putExtra("result", "data");

			setResult(RESULT_OK, intent);

			finish();
		}
	}
}
