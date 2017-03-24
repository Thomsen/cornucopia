package com.cornucopia.ui.dynamic;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cornucopia.R;
import com.google.gson.Gson;

public class ExpandlistDemo extends Activity {
	
	private ExpandableListView expListView; // 该对象不做传递使用，而是包装传递的数据
	
	private Button btnSubmit;
	
	// 做一个HaspMap对应（group， child）
	HashMap<String, ArrayList<LayoutDto>> groupChild = 
			new HashMap<String, ArrayList<LayoutDto>>();
	
	HashMap<String, ArrayList<LayoutDataDto>> groupChildData = 
			new HashMap<String, ArrayList<LayoutDataDto>>(); 
	
	ArrayList<String> listGroup = new ArrayList<String>();
	
//	ArrayList<LayoutDto> listLayout = new ArrayList<LayoutDto>();
	
	ArrayList<LayoutDataDto> listData = null;
	
	ArrayList<ExpandableLayout> listLayout = new ArrayList<ExpandableLayout>();
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		setContentView(R.layout.activity_expandlist);
	
		expListView = (ExpandableListView) findViewById(R.id.explist_panel);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		
		initTestData();
		
		final ExpAdapter expAdapter = new ExpAdapter(this);
		expListView.setAdapter(expAdapter);
		expListView.setGroupIndicator(null); // 隐藏默认的坐标图标
//		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//			@Override
//			public void onGroupExpand(int groupPosition) {
//				expAdapter.setExpandPosition(groupPosition);
//			}
//			
//		});
		int expandCount = expListView.getCount();
		for (int i=0; i<expandCount; i++) {
			expListView.expandGroup(i);
		}
		
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				expAdapter.getLayoutData();
			}
		});
	}
	
	private void initTestData() {
		
		String str = null;
		for (int i=0; i<3; i++) {
			str = new String("group " + i);
			listGroup.add(str);
		}
		
		LayoutDto lay = null;
		LayoutDataDto layData = null;
		
		ExpandableLayout layout = new ExpandableLayout();
		ArrayList<LayoutDto> listLayout1 = new ArrayList<LayoutDto>();
		listData = new ArrayList<LayoutDataDto>();
		for (int i=0; i<10; i++) {
			lay = new LayoutDto();
			layData = new LayoutDataDto();
			int type = i % 3;
			lay.setType(type);
			layData.setType(type);
			lay.setLabel("label " + i);
			layData.setLabel("label " + i);
			if (type == 2) {
				ArrayList<String> al = new ArrayList<String>();
				for (int j=0; j<5; j++) {
					al.add("value " + j);
				}
				lay.setValue(al);
				layData.setValue("value " + 2);
			} else {
				lay.setValue("value");
				layData.setValue("value");
			}
			listLayout1.add(lay);
			listData.add(layData);
		}
		groupChild.put(listGroup.get(0), listLayout1);
		groupChildData.put(listGroup.get(0), listData);
		layout.setGroupName(listGroup.get(0));
		layout.setLayout(listLayout1);
		layout.setData(listData);
		listLayout.add(layout);

		layout = new ExpandableLayout();
//		listLayout.clear();
//		listLayout = new ArrayList<LayoutDto>();
		ArrayList<LayoutDto> listLayout2 = new ArrayList<LayoutDto>(); //3 、重新创建栈指针执行堆，因为，栈指针指向堆中的栈指针引用
		listData = new ArrayList<LayoutDataDto>();
		for (int i=0; i<3; i++) {
			lay = new LayoutDto();
			layData = new LayoutDataDto();
			int type = i % 3;
			lay.setType(type);
			layData.setType(type);
			lay.setLabel("label " + i);
			layData.setLabel("label " + i);
			if (type == 2) {
				ArrayList<String> al = new ArrayList<String>();
				for (int j=0; j<5; j++) {
					al.add("value " + j);
				}
				lay.setValue(al);
				layData.setValue("value " + 3);
			} else {
				lay.setValue("value");
				layData.setValue("value");
			}
			listLayout2.add(lay);
			listData.add(layData);
		}
		groupChild.put(listGroup.get(1), listLayout2);
		groupChildData.put(listGroup.get(1), listData);
		layout.setGroupName(listGroup.get(1));
		layout.setLayout(listLayout2);
		layout.setData(listData);
		listLayout.add(layout);
		
		layout = new ExpandableLayout();
//		listLayout.clear();
//		listLayout = new ArrayList<LayoutDto>(); // 2、在堆中创建一个内存区
//		ArrayList<LayoutDto> listLayout3 = new ArrayList<LayoutDto>();
//		listLayout2.clear(); // 4、不行，指向了统一堆区
		listLayout2 = new ArrayList<LayoutDto>(); // 5、新建一个堆区，可以。这样第3条的结论就是错的
		for (int i=0; i<7; i++) {
			lay = new LayoutDto();
			int type = i % 3;
			lay.setType(type);
			lay.setLabel("label " + i);
			if (type == 2) {
				ArrayList<String> al = new ArrayList<String>();
				for (int j=0; j<5; j++) {
					al.add("value " + j);
				}
				lay.setValue(al);
			} else {
				lay.setValue("value");
			}
			listLayout2.add(lay);
		}
		groupChild.put(listGroup.get(2), listLayout2); // 1、由于listLayout指向相同的堆，groupChild的栈指针不同，改变listLayout后，所有指向的引用都会改变
		layout.setGroupName(listGroup.get(2));
		layout.setLayout(listLayout2);
		layout.setData(null);
		listLayout.add(layout);
	}

	class ExpAdapter implements ExpandableListAdapter {

		private Context mContext;
		
//		private HolderView mHolder;
		
		private int mExpandPosition;
		
//		DynamicLayoutUtil mDynamicLayout;
		DynamicLayoutWrapper mDynamicLayout;
		
		HashMap<String, LinearLayout> mLayoutGroup;
		
		HashMap<String, View> mLayoutView;
		
		public ExpAdapter(Context context) {
			mContext = context;
			
//			mDynamicLayout = new DynamicLayoutUtil(mContext);
			mDynamicLayout = new DynamicLayoutWrapper(mContext);
			mLayoutGroup = new HashMap<String, LinearLayout>();
			mLayoutView = new HashMap<String, View>();
			
//			mHolder = new HolderView(); // 会导致整个group title错乱的问题
		}
		
		@Override
		public boolean areAllItemsEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			
			
			// group position 会一直到最后一个展开的position
//			Toast.makeText(mContext.getApplicationContext(), "group pos: " + groupPosition, Toast.LENGTH_SHORT).show();

//			HolderView holder = new HolderView();
//			if (convertView == null) {
//				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView = inflater.inflate(R.layout.panel_content, null);
//				holder.layoutPanel = (LinearLayout) convertView.findViewById(R.id.child_layout_panel);
//				
//				if (groupPosition == mExpandPosition) { 
//					layoutUtil.addLinearView(holder.layoutPanel, groupChild.get(listGroup.get(groupPosition))); // 放在下面，每次展开都会add, 但是针对不同的group，需放在下面
//				
//					convertView.setTag(R.id.child_layout_panel, holder.layoutPanel);
//				} else {
//					holder.layoutPanel = (LinearLayout) convertView.getTag(R.id.child_layout_panel);
//				}
//				
//				convertView.setTag(holder);
//			} else {
//				holder = (HolderView) convertView.getTag();
//			}
//			
//				layoutUtil.setLayoutValue(holder.layoutPanel, listData);
			
//			HolderView holder = new HolderView();
//			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = inflater.inflate(R.layout.panel_content, null);
//			holder.layoutPanel = (LinearLayout) convertView.findViewById(R.id.child_layout_panel);
//			mDynamicLayout.addLinearView(holder.layoutPanel, groupChild.get(listGroup.get(groupPosition))); // TODO 动态加载，对于输入如何是好
//			mLayoutGroup.put(listGroup.get(groupPosition), holder.layoutPanel);
//			mDynamicLayout.setLayoutValue(holder.layoutPanel, groupChildData.get(listGroup.get(groupPosition)));
	
			HolderView holder = new HolderView();
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.panel_content, null);
				holder.layoutPanel = (LinearLayout) convertView.findViewById(R.id.child_layout_panel);
//				mDynamicLayout.addLinearView(holder.layoutPanel, groupChild.get(listGroup.get(groupPosition)));
//				mDynamicLayout.addLinearView(holder.layoutPanel, listLayout.get(groupPosition).getLayout());
				mDynamicLayout.addGroupView(holder.layoutPanel, listLayout.get(groupPosition).getLayout());
				mLayoutGroup.put(listGroup.get(groupPosition), holder.layoutPanel); // 为了获取对应layout的数据

				// 模板映射，数据不要
//				mLayoutView.put(listGroup.get(groupPosition), convertView);
				mLayoutView.put(listLayout.get(groupPosition).getGroupName(), convertView);
				
				// 初始化数据
//				mDynamicLayout.setLayoutValue(holder.layoutPanel, groupChildData.get(listGroup.get(groupPosition)));
				mDynamicLayout.setLayoutValue(holder.layoutPanel, listLayout.get(groupPosition).getData());
				
			
			} else {
//				holder.layoutPanel = mLayoutGroup.get(listGroup.get(groupPosition));  // 由于滚动时view的位置会改变，所以要映射起来
				
//				convertView = mLayoutView.get(listGroup.get(groupPosition)); // 堪称tag优化方式的另一形式
				convertView = mLayoutView.get(listLayout.get(groupPosition).getGroupName());
			}
			
			
			// 如何填充数据，是个难题
			// 难道只能在布局时，填充数据吗
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
//			if (listLayout != null) {
//				return listLayout.size();
//			} else {
//				return 0;
//			}
			return 1; // 展开就是一个动态的布局文件
		}

		@Override
		public long getCombinedChildId(long groupId, long childId) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getCombinedGroupId(long groupId) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
//			if (listGroup != null) {
//				return listGroup.size();
//			} else {
//				return 0;
//			}
//			if (groupChild != null) {
//				return groupChild.size();
//			} else {
//				return 0;
//			}
			if (listLayout != null) {
				return listLayout.size();
			} else {
				return 0;
			}
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			
			HolderView holder = new HolderView();
			
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.expandlist_group, null);
				
				holder.tvGroupTitle = (TextView) convertView.findViewById(R.id.group_title);
				holder.imGroupIcon = (ImageView) convertView.findViewById(R.id.group_icon);
				
				convertView.setBackgroundColor(Color.GRAY);
				
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			
//			mHolder.tvGroupTitle.setText("title " + groupPosition);
//			holder.tvGroupTitle.setText(listGroup.get(groupPosition));
			holder.tvGroupTitle.setText(listLayout.get(groupPosition).getGroupName());
			if (isExpanded) {
				holder.imGroupIcon.setBackgroundColor(Color.BLUE);
			} else {
				holder.imGroupIcon.setBackgroundColor(Color.GREEN);
			}
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGroupExpanded(int groupPosition) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}
		
		/// 
		
		public void getLayoutData() {
			HashMap<String, ArrayList<LayoutDataDto>> data = 
					mDynamicLayout.getLayoutValue(mLayoutGroup, groupChild);
			
			Gson gson = new Gson();
			java.lang.reflect.Type type = new com.google.gson.reflect
					.TypeToken<HashMap<String, ArrayList<LayoutDataDto>>>() {}.getType();
			Log.i("ExpandlistDemo", gson.toJson(data));
			
//			return mDynamicLayout.getLayoutValue(groupChild);
		}
		
		public void setExpandPosition(int position) {
			mExpandPosition = position;
		}
		
		class HolderView {
			TextView tvGroupTitle;
			ImageView imGroupIcon;
			
			LinearLayout layoutPanel;
		}
		
	}
	
}
