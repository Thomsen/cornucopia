package com.cornucopia.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cornucopia.R;

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

        RecyclerAdapter adapter = new RecyclerAdapter(this, datas);
//        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                if (null != tv) {
                    Snackbar.make(mRecyclerView, "item: " + tv.getText(), Snackbar.LENGTH_LONG)
                            .setAction("action", new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(RecyclerListActivity.this, "snackbar action", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else {
                    Toast.makeText(RecyclerListActivity.this, "click", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecyclerView.setAdapter(adapter);

    }


}
