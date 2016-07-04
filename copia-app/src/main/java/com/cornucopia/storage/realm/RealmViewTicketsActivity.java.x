package com.cornucopia.storage.realm;

import io.realm.Realm;
import io.realm.RealmResults;

import com.cornucopia.R;
import com.cornucopia.storage.ticketsmanager.AddTicketsActivity;
import com.cornucopia.storage.ticketsmanager.Tickets;
import com.cornucopia.storage.ticketsmanager.ViewTicketsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RealmViewTicketsActivity extends Activity {

    private Button mAddButton;
    private TextView mTicketList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.application_activity_viewtickets);
        setUpViews();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        showTickets();
    }

    private void setUpViews() {
        mAddButton = (Button) findViewById(R.id.button_add_ticket);
        mTicketList = (TextView) findViewById(R.id.ticket_list_text);
    
        // 添加监听事件
        mAddButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RealmViewTicketsActivity.this, AddTicketsActivity.class);
                startActivity(intent);
            }
        });
    }
    
    private void showTickets() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmTickets> tickets = realm.where(RealmTickets.class).findAll();
        
        StringBuffer sb = new StringBuffer();
        for(RealmTickets t : tickets) {
            sb.append(String.format("* %s\n", t.toString()));
        }
        mTicketList.setText(sb.toString());
    }
}
