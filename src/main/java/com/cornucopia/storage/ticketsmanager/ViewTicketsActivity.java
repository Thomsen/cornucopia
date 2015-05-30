package com.cornucopia.storage.ticketsmanager;

import java.util.ArrayList;

import com.cornucopia.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewTicketsActivity extends TicketsManagerActivity {

	private Button mAddButton;
	private TextView mTicketList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.application_activity_viewtickets);
		
		// 整体上把握，在此处引用布局的控件
		setUpViews();
		
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		showTickets();
	}



	private void showTickets() {
		// 显示添加的tickets
		ArrayList<Tickets> tickets = getTicketsManagerApplication().getCurrentTickets();
	
		StringBuffer sb = new StringBuffer();
		// 对tickets进行format
		for(Tickets t:tickets) {
			sb.append(String.format("* %s\n", t.toString()));
		}
		mTicketList.setText(sb.toString());
	}

	private void setUpViews() {
		mAddButton = (Button) findViewById(R.id.button_add_ticket);
		mTicketList = (TextView) findViewById(R.id.ticket_list_text);
	
		// 添加监听事件
		mAddButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewTicketsActivity.this, AddTicketsActivity.class);
				startActivity(intent);
			}
		});
	}

}
