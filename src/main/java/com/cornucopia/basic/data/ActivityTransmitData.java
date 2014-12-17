package com.cornucopia.basic.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.cornucopia.R;

public class ActivityTransmitData extends Activity {

	
	private EditText mEditText;
	private EditText mEditTextPersonName;
	private EditText mEditTextPassword;
	private EditText mEditTextEmailAddress;
	private EditText mEditPhone;
	private EditText mEditPostalAddress;
	private EditText mEditTextMultiLine;
	private EditText mEditTime;
	private EditText mEditDate;
	private EditText mEditNumber;
	private EditText mEditNumberSigned;
	private EditText mEditNumberDecimal;
	private AutoCompleteTextView mAutoCompleteTextView;
	private MultiAutoCompleteTextView mMultiAutoCompleteTextView;
	private Button mButtonTransmit;
	
	TextFieldsParcel textFieldsParcel;
	
	private final static int TRANSMIT_DATA = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_transmit_data);
		
		setupViews();
		
		setupDatas();
		
		mButtonTransmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityTransmitData.this, ActivityObtainData.class);
				
//				 1、不使用bundle
				intent.putExtra("test", textFieldsParcel.getPlainText());
				
				// 2、使用bundle
				Bundle bundle = new Bundle();
				bundle.putString("autoCompleteText", textFieldsParcel.getAutoCompleteText());
				bundle.putString("date", textFieldsParcel.getDate());
				bundle.putString("email", textFieldsParcel.getEmail());
				bundle.putString("multiAutoCompleteText", textFieldsParcel.getMultiAutoCompleteText());
				
				intent.putExtra("bundle", bundle);
				
				// 3、使用Parcelable接口，传递对象
				
				intent.putExtra("parcelable", textFieldsParcel);
				
//				startActivity(intent);
				startActivityForResult(intent, TRANSMIT_DATA);
			}
		});
	
	}
	
	private void setupDatas() {
		textFieldsParcel = new TextFieldsParcel();
		
		// 设置数据
		textFieldsParcel.setAutoCompleteText(mAutoCompleteTextView.getText().toString());
		textFieldsParcel.setDate(mEditDate.getText().toString());
		textFieldsParcel.setEmail(mEditTextEmailAddress.getText().toString());
		textFieldsParcel.setMultiAutoCompleteText(mMultiAutoCompleteTextView.getText().toString());
		textFieldsParcel.setMultilineText(mEditTextMultiLine.getText().toString());
		textFieldsParcel.setNumber(mEditNumber.getText().toString());
		textFieldsParcel.setNumberDecimal(mEditNumberDecimal.getText().toString());
		textFieldsParcel.setNumberSigned(mEditNumberSigned.getText().toString());
		textFieldsParcel.setPassword(mEditTextPassword.getText().toString());
		textFieldsParcel.setPasswordNumeric("");
		textFieldsParcel.setPersonName(mEditTextPersonName.getText().toString());
		textFieldsParcel.setPhone(mEditPhone.getText().toString());
		textFieldsParcel.setPlainText(mEditText.getText().toString());
		textFieldsParcel.setPostalAddress(mEditPostalAddress.getText().toString());
		textFieldsParcel.setTime(mEditTime.getText().toString());
		
		
	}

	private void setupViews() {
		mEditText = (EditText) findViewById(R.id.editText);
		mEditTextPersonName = (EditText) findViewById(R.id.editTextPersonName);
		mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
		mEditTextEmailAddress = (EditText) findViewById(R.id.editTextEmailAddress);
		mEditPhone = (EditText) findViewById(R.id.editPhone);
		mEditPostalAddress = (EditText) findViewById(R.id.editTextPostalAddress);
		mEditTextMultiLine = (EditText) findViewById(R.id.editTextMultiLine);
		mEditTime = (EditText) findViewById(R.id.editTime);
		mEditDate = (EditText) findViewById(R.id.editDate);
		mEditNumber = (EditText) findViewById(R.id.editNumber);
		mEditNumberSigned = (EditText) findViewById(R.id.editNumberSigned);
		mEditNumberDecimal = (EditText) findViewById(R.id.editNumberDecimal);
		mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		mMultiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
		
		mButtonTransmit = (Button) findViewById(R.id.buttonTransmit);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// requestCode 自定义
		if (requestCode == TRANSMIT_DATA) {
			// resultCode 系统
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "result: " + data.getStringExtra("test") , Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "result is null", Toast.LENGTH_SHORT).show();
			}
		}
		
		
	}

}
