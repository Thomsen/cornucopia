package com.cornucopia.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cornucopia.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeRefreshActivity extends Activity implements OnRefreshListener, OnItemClickListener {
    
    private SwipeRefreshLayout mSrfLayout;
    
    private ListView mLvContent;
    
    private ListAdapter mAdapter;
    
    private ArrayList<String> listData;
    
    private static final int REFRESH_COMPLETED = 1;
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETED: {
                    listData.add("More");
                    mLvContent.setAdapter(mAdapter);
                    mSrfLayout.setRefreshing(false);
                    break;
                }
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_swipe_refresh);
        
        mSrfLayout = (SwipeRefreshLayout) findViewById(R.id.srl_layout);
        mLvContent = (ListView) findViewById(R.id.lv_content);
        
        listData = new ArrayList<String>(Arrays.asList("Application", "Basic", "Datastorage", "Devices", "Grahpics"));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        mLvContent.setAdapter(mAdapter);
        mLvContent.setOnItemClickListener(this);
        
        mSrfLayout.setOnRefreshListener(this);
        mSrfLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        
    }

    @Override
    public void onRefresh() {
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(REFRESH_COMPLETED);
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        listData.remove(arg2);
        mLvContent.setAdapter(mAdapter);
    }

}
