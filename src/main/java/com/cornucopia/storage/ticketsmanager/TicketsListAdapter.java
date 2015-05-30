package com.cornucopia.storage.ticketsmanager;

import java.util.ArrayList;

import com.cornucopia.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TicketsListAdapter extends BaseAdapter {

	private ArrayList<Tickets> tickets;
	private Context mContext;
	
	public TicketsListAdapter(ArrayList<Tickets> tickets, Context mContext) {
		super();
		this.tickets = tickets;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return tickets.size();
	}

	@Override
	public Tickets getItem(int arg0) {
		return ((null == tickets) ? null : tickets.get(arg0));
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		TicketsListItem sTicketsListItem;
		
		if (null == arg1) {
			sTicketsListItem = (TicketsListItem) View.inflate(mContext, R.layout.application_tickets_list_item, null);
		} else {
			sTicketsListItem = (TicketsListItem) arg1;
		}
		
		sTicketsListItem.setTicket(tickets.get(arg0));
		
		return sTicketsListItem;
	}

	public void toggleTicketCompleteAtPosition(int position) {
		  Tickets ticket = tickets.get(position);
		  ticket.toggleComplete();
		  
		  // 数据改变后更新View
		  notifyDataSetChanged();
	}

	public Long[] removeCompleteTickets() {
		ArrayList<Tickets> completedTickets = new ArrayList<Tickets>();
		ArrayList<Long> completedTicketsId = new ArrayList<Long>();
		
		for (Tickets ticket : tickets) {
			if (ticket.isComplete()) {
				completedTickets.add(ticket);
				completedTicketsId.add(ticket.getId());
			}
		}
		
		tickets.removeAll(completedTickets);
		
		notifyDataSetChanged();
		
		return completedTicketsId.toArray(new Long[] {});
	}
}
