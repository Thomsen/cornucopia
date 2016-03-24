package com.cornucopia.storage.realm;

import java.util.ArrayList;

import com.cornucopia.R;
import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.TicketsListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RealmTicketsListAdapter extends RealmBaseAdapter<RealmTickets> implements ListAdapter {

    public RealmTicketsListAdapter(Context context,
            RealmResults<RealmTickets> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TicketsListItem sTicketsListItem;
        
        if (null == convertView) {
            sTicketsListItem = (TicketsListItem) View.inflate(context, R.layout.application_tickets_list_item, null);
        } else {
            sTicketsListItem = (TicketsListItem) convertView;
        }
        
        sTicketsListItem.setRealmTicket(realmResults.get(position));
        
        return sTicketsListItem;
    }

    public void toggleTicketCompleteAtPosition(int position) {
        RealmTickets ticket = realmResults.get(position);
        ticket.toggleComplete();
        
        // 数据改变后更新View
        notifyDataSetChanged();
    }

    public Long[] removeCompleteTickets() {
        ArrayList<RealmTickets> completedTickets = new ArrayList<RealmTickets>();
        ArrayList<Long> completedTicketsId = new ArrayList<Long>();

        for (RealmTickets ticket : realmResults) {
            if (ticket.isComplete()) {
                completedTickets.add(ticket);
                completedTicketsId.add(ticket.getId());
            }
        }

        realmResults.removeAll(completedTickets);

        notifyDataSetChanged();

        return completedTicketsId.toArray(new Long[] {});
    }
}
