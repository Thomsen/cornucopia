package com.cornucopia.ticketsmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cornucopia.R;

public class AddTicketsActivity extends TicketsManagerActivity {

	private Button mButtonOk;
	private Button mButtonCancel;
	private EditText mEditTicketName;
	protected boolean isEditChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 做到这里，先建立一个layout，然后在建立布局文件，不过这里需要先建立布局文件，而不能直接建立变量
		setContentView(R.layout.application_activity_addtickets);
		
		mEditTicketName = (EditText) findViewById(R.id.edit_ticket_name);
		mButtonOk = (Button) findViewById(R.id.button_add_tickets_ok);
		mButtonCancel = (Button) findViewById(R.id.button_add_tickets_cancel);
		
		// 判断EditText是否已改变
		mEditTicketName.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// text改变后的操作
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// text改变前的操作
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				 isEditChange = true;
			}
			
		});
		
		mButtonOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 添加ticket
				addTicket();
				
			}
		});
		
		mButtonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 取消添加
				cancel();
				
			}
		});
	}

	protected void cancel() {
		if (isEditChange) {
			final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
									.setTitle("是否添加ticket")  // setCustomTitle(view)可自定义dialog title
									.setMessage("对于已编辑的ticket name，确定是否添加到tickets显示?");
			dialogBuilder.setNegativeButton("Negative", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 结束dialog
//					finish();
					// 调用AlertDialog的cancel方法， 解决掉dilaog，当AddTicketsActivity仍在
					dialogBuilder.create().cancel();
					
				}
			});
			
			dialogBuilder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 结束dialog和AddTicketsActivity
					finish();
					
				}
			});
			
			dialogBuilder.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// 添加ticket
					addTicket();
					
				}
			});
			
			
			dialogBuilder.show();
			
		} else {
			finish();
		}
	}

	protected void addTicket() {
		// 获取ticket的name
		String ticketName = mEditTicketName.getText().toString();
		
		// 创建Tickets对象
		Tickets ticket = new Tickets(ticketName);
		
		// 通过Application添加ticket
		getTicketsManagerApplication().addTicket(ticket);
		
		finish();
	}

}
