package com.cornucopia.transmit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cornucopia.R;

public class DialogIntentActivity extends Activity implements View.OnClickListener {

	 private static final int REQUEST_CODE_START = 1;
	    private static final int REQUEST_CODE_DIALOG_START = 2;


	    TextView tv;

	    EditText mEt;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_dialog_main);

	        findViewById(R.id.button).setOnClickListener(this);
	        findViewById(R.id.button2).setOnClickListener(this);

	        tv = (TextView) findViewById(R.id.textView);
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        return true;
	    }

	    @Override
	    public void onClick(View view) {
			// case R.id.button: will error: constant expression required
			if (view.getId() == R.id.button) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("TZ");

					LinearLayout ll = new LinearLayout(this);

					EditText et = new EditText(this);

					Button btn = new Button(this);

					et.setId(R.id.dialog_et_one);
					btn.setId(R.id.dialog_button_one);
					btn.setText("intent");

					mEt = et;

					btn.setOnClickListener(this);

					ll.addView(et);
					ll.addView(btn);

					builder.setView(ll);

					builder.create();
					builder.show();

				}
			if (view.getId() == R.id.button2)  {
				Intent intent = new Intent(this, DialogResultActivity.class);
				startActivityForResult(intent, REQUEST_CODE_START);
			}
			if (view.getId() == R.id.dialog_button_one) {
				Intent intent = new Intent(this, DialogResultActivity.class);
				startActivityForResult(intent, REQUEST_CODE_DIALOG_START);
			}
		}

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        if (resultCode == RESULT_OK) {
	            switch (requestCode) {
	                case REQUEST_CODE_START: {
	                    String da = data.getStringExtra("result");
	                    Log.i("thom", da);

	                    tv.setText(da);
	                    break;
	                }
	                case REQUEST_CODE_DIALOG_START: {
	                    String da = data.getStringExtra("result");
	                    Log.i("thom", da);

	                    mEt.setText(da);
	                    break;
	                }
	            }
	        }
	    }
	    
}
