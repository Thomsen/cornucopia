package com.cornucopia.ui.list;

import com.cornucopia.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RecyclerListActivity extends Activity {
    
    private RecyclerView mRecyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_recycler_list);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        
//        java.lang.NullPointerException: Attempt to invoke virtual method 'void android.support.v7.widget.RecyclerView$LayoutManager.onMeasure(
//                android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, int, int)' on a null object reference
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        
        String[] datas = new String[1000];
        int len = datas.length;
        for (int i=0; i<len; i++) {
            datas[i] = "recycler data " + i;
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                android.R.id.text1, datas);  // SimpleAdapter
        
        mRecyclerView.setAdapter(new RecAdapter(datas));
    }
    
    class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
        
        String[] datas;
        
        final class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
            
            TextView tv;
            
        }
        
        public RecAdapter(String[] datas) {
            this.datas = datas;
        }

        @Override
        public int getItemCount() {
            if (null == datas) {
                return 0;
            } else {
                return datas.length;
            }           
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(datas[position]);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.tv = (TextView) v.findViewById(android.R.id.text1);
            holder.tv.setTextColor(Color.BLACK);
            return holder;
        }
    }

}
