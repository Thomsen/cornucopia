package com.cornucopia.ticketsmanager.listitem;

import com.cornucopia.ticketsmanager.Tickets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

public class TicketsListItem extends LinearLayout {
	
	private Tickets ticket;
	private CheckedTextView mCheckedTextView;

	public TicketsListItem(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		// 在该View的根下找到text1的id
		mCheckedTextView = (CheckedTextView) findViewById(android.R.id.text1);
	}



	public Tickets getTicket() {
		return ticket;
	}

	public void setTicket(Tickets ticket) {
		this.ticket = ticket;
		// mCheckTextView为null, 针对android.R.id.text1需要在tickets_list_item中进行设置
		mCheckedTextView.setText(ticket.getName());
		
		// 检查该项是否被选
		mCheckedTextView.setChecked(ticket.isComplete());
		
	}
	
	

}
