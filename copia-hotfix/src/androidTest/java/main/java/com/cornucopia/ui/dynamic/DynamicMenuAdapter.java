package com.cornucopia.ui.dynamic;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.cornucopia.R;

public class DynamicMenuAdapter implements ListAdapter {

	private Context mContext;
	
	private static final int TYPE_NORMAL = 0x1000;
	
	private static final int TYPE_LAST_POSITION = 0x1001; 
	
	public DynamicMenuAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			
			int type = getItemViewType(position);
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			switch (type) {
			case TYPE_LAST_POSITION: {
				convertView = inflater.inflate(R.layout.grid_last_view, null);
				convertView.setBackgroundColor(Color.GREEN);
				break;
			}
			case TYPE_NORMAL: {
				ImageView iv  = new ImageView(mContext);
				iv.setImageResource(R.drawable.ic_launcher);
				convertView = iv;
				break;
			}
			}
		}
		
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {
			return TYPE_LAST_POSITION;
		} else {
			return TYPE_NORMAL;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
//		return false;	// ���item�ĵ���ͳ��������ܳɹ�
		return true;	// ����ʹ�û��ǲ��У���ҪisEnabled
	}

	@Override
	public boolean isEnabled(int position) {
//		return false;
		return true;
	}

}
