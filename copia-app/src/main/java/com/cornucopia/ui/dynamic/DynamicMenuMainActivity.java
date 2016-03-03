package com.cornucopia.ui.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.cornucopia.R;

public class DynamicMenuMainActivity extends Activity implements OnItemClickListener {

	private GridView mGridView;
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		setContentView(R.layout.activity_dynamicmenu);
		
		mGridView = (GridView) findViewById(R.id.gv_dynamic_view);
		mGridView.setAdapter(new DynamicMenuAdapter(this));
		
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(DynamicMenuMainActivity.this, 
				"pos: " + position + " id: " + id, Toast.LENGTH_SHORT).show();	
	}
	
}
