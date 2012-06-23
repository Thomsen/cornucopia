package com.cornucopia.application.ticketsmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cornucopia.R;
import com.cornucopia.application.CornucopiaApplication;

public class ViewTicketsListActivity extends ListActivity {

	private Button mButtonAdd;
	private Button mButtonRemove;
	private CornucopiaApplication app;
	private TicketsListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.application_activity_viewtickets2);
	
		setUpViews();
		
		app = (CornucopiaApplication) getApplication();
		adapter = new TicketsListAdapter(app.getCurrentTickets(), this);
		setListAdapter(adapter);
		
		// 使用ArrayAdapter显示数据 
//		boundDataWithArrayAdapter();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// 在这里进行修改adapter数据，位置关键
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		adapter.toggleTicketCompleteAtPosition(position);
		
		Tickets ticket = adapter.getItem(position);
		app.saveTicketToDatabase(ticket);
	}

	private void setUpViews() {
		mButtonAdd = (Button) findViewById(R.id.add_button);
		mButtonRemove = (Button) findViewById(R.id.remove_button);
		
		mButtonAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ViewTicketsListActivity.this, AddTicketsActivity.class);
			
				startActivity(intent);
			}
		});
		
		
		mButtonRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				removeCompleteTickets();
			}
		});
	}

	protected void removeCompleteTickets() {
		// 修改方法返回id
		Long[] ids = adapter.removeCompleteTickets();
		
		// 根据id从数据库中删除
		app.deleteTickets(ids);
	}


	private void boundDataWithArrayAdapter() {
		List<String> strings = new ArrayList<String>();
		strings.add("ticket one");
		strings.add("ticket two");
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings));
	}
}
