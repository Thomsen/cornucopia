package com.cornucopia.ui.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cornucopia.R;

public class OrgTreeMainActivity extends Activity implements OnItemClickListener {
	
	/**
	 * 处于展开中的组织
	 */
	List<PdaOrgDto> listExpandOrgnization;
	
	/**
	 * 所有获取的组织
	 */
	List<PdaOrgDto> listAllOrgnization;
	
	private ListView mListView;
	
	private TreeViewAdapter mTreeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_tree_main);
        
        mListView = (ListView) findViewById(R.id.lv_orgnization_tree);
        
        initTreeData();
        
        mTreeAdapter = new TreeViewAdapter(this, listExpandOrgnization);
        mListView.setAdapter(mTreeAdapter);
        
//        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_org_tree_main, menu);
        return true;
    }
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

    	switch (item.getItemId()) {
    	case R.id.menu_settings: {
//    		Toast.makeText(this, "hehe", Toast.LENGTH_SHORT).show();
    		List<String> ls = mTreeAdapter.getOrgTypeDataId(mTreeAdapter.getOrgSelected());
    		Log.i("thom", "" + ls);
    		break;
    	}
    	default: {
    		break;
    	}
    	}
    	
    	return super.onMenuItemSelected(featureId, item);
	}

	public void initTreeData() {
    	listExpandOrgnization = new ArrayList<PdaOrgDto>();
    	listAllOrgnization = new ArrayList<PdaOrgDto>();
    	
    	PdaOrgDto org1 = new PdaOrgDto((long)99, "组织结构", false, 1, null);
    	PdaOrgDto org2 = new PdaOrgDto((long)11, "父组织1", false, 2, (long)99);
    	PdaOrgDto org3 = new PdaOrgDto((long)12, "父组织2", false, 2, (long)99);
    	PdaOrgDto org4 = new PdaOrgDto((long)13, "父组织3", true, 2, (long)99);
    	PdaOrgDto org5 = new PdaOrgDto((long)111, "父组织1-子组织1", true, 3, (long)11);
    	PdaOrgDto org6 = new PdaOrgDto((long)112, "父组织1-子组织2", true, 3, (long)11);
    	PdaOrgDto org7 = new PdaOrgDto((long)113, "父组织1-子组织3", true, 3, (long)11);
    	PdaOrgDto org8 = new PdaOrgDto((long)114, "父组织1-子组织4", true, 3, (long)11);
    	PdaOrgDto org9 = new PdaOrgDto((long)121, "父组织2-子组织4", true, 3, (long)12);
    	PdaOrgDto org11 = new PdaOrgDto((long)123, "父组织2-子组织4", true, 3, (long)12);
    	PdaOrgDto org12 = new PdaOrgDto((long)124, "父组织2-子组织4", true, 3, (long)12);
    	PdaOrgDto org13 = new PdaOrgDto((long)125, "父组织2-子组织4", true, 3, (long)12);
    	PdaOrgDto org14 = new PdaOrgDto((long)126, "父组织2-子组织4", true, 3, (long)12);
    	PdaOrgDto org10 = new PdaOrgDto((long)122, "父组织222222222222222222222-子组织4", true,3, (long)12);
    	
    	org1.setTypeDataId("1");
    	org2.setTypeDataId("2");
    	org3.setTypeDataId("3");
    	org4.setTypeDataId("4");
    	org5.setTypeDataId("5");
    	org6.setTypeDataId("6");
    	org7.setTypeDataId("7");
    	org8.setTypeDataId("8");
    	org9.setTypeDataId("9");
    	org10.setTypeDataId("10");
    	org11.setTypeDataId("11");
    	org12.setTypeDataId("12");
    	org13.setTypeDataId("13");
    	org14.setTypeDataId("14");
    	
    	org1.setExpanded(false);
    	org1.setLevel(0);
    	listExpandOrgnization.add(org1);
    	listExpandOrgnization.add(new PdaOrgDto((long)100, "ffff", false, 1, null));
    	
    	listAllOrgnization.add(org1);
    	listAllOrgnization.add(org2);
    	listAllOrgnization.add(org3);
    	listAllOrgnization.add(org4);
    	listAllOrgnization.add(org5);
    	listAllOrgnization.add(org6);
    	listAllOrgnization.add(org7);
    	listAllOrgnization.add(org8);
    	listAllOrgnization.add(org9);
    	listAllOrgnization.add(org10);
    	listAllOrgnization.add(org11);
    	listAllOrgnization.add(org12);
    	listAllOrgnization.add(org13);
    	listAllOrgnization.add(org14);
    	
    }

    class TreeViewAdapter extends BaseAdapter {

    	private Context mContext;
    	
    	private List<PdaOrgDto> listOrgs;
    	
    	/**
    	 * 已选择的组织，组织的id，组织的keyid
    	 */
//    	private Map<Long, Boolean> orgSelected;
    	private Map<Long, String> orgSelected;

		public TreeViewAdapter(Context context, List<PdaOrgDto> listOrgs) {
    		mContext = context;
    		this.listOrgs = listOrgs;
    	
//    		orgSelected = new HashMap<Long, Boolean>();
    		orgSelected = new HashMap<Long, String>();
    	}
    	
		@Override
		public int getCount() {
			if (listOrgs != null) {
				return listOrgs.size();
			} else {
				return 0;
			}
			
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
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder = new HolderView();
			
			// 此处优化方式会出现icon显示异常
//			if (convertView == null) {
//				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView = inflater.inflate(R.layout.tree_org_content, null);
//				
//				holder.ivExpandIcon = (ImageView) convertView.findViewById(R.id.iv_tree_expand_icon);
//				holder.tvNodeName = (TextView) convertView.findViewById(R.id.tv_tree_node_name);
//				
//				convertView.setTag(holder);
//			} else {
//				holder = (HolderView) convertView.getTag();
//			}
			
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.tree_org_content, null);
			
			holder.ivExpandIcon = (ImageView) convertView.findViewById(R.id.iv_tree_expand_icon);
			holder.tvNodeName = (TextView) convertView.findViewById(R.id.tv_tree_node_name);
			holder.cbNodeSelect = (CheckBox) convertView.findViewById(R.id.cb_tree_node_select);
			
			convertView.setTag(holder);
			
			final PdaOrgDto org = listOrgs.get(position);
			holder.tvNodeName.setText(org.getText());
//			int level = org.getOrgLevel();
			int level = org.getOrgLevel();
			holder.ivExpandIcon.setPadding(25 * (level+1), holder.ivExpandIcon.getPaddingTop(),
					0, holder.ivExpandIcon.getPaddingBottom());
			if (!org.isLeaf() && !org.isExpanded()) {
				holder.ivExpandIcon.setImageResource(R.drawable.ic_list_collapse);
			} else if (!org.isLeaf() && org.isExpanded()) {
				holder.ivExpandIcon.setImageResource(R.drawable.ic_list_expand);
			} else if (org.isLeaf()) {
				holder.ivExpandIcon.setImageResource(R.drawable.ic_list_collapse);
				holder.ivExpandIcon.setVisibility(View.INVISIBLE);
			}
			final int pos = position;
			// 点击展开收缩事件
			holder.ivExpandIcon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					orgClickEvent(pos);
					
					// 关联父组织与子组织的选择
//					for (PdaOrgDto orgDto : listOrgs) {
//						if (org.getDataId() == orgDto.getParentId()) {
//							if (orgSelected.get(org.getDataId()) != null) {
//								orgSelected.put(orgDto.getDataId(), orgDto.getTypeDataId());							
//							} else {
//								orgSelected.remove(orgDto.getDataId());
//							}
//						}
//
//					}
					
					// 考虑不用递归
//					recursionOrgSelect(org);
				}
			});
			
			holder.cbNodeSelect.setChecked((orgSelected.get(org.getDataId()) == null) ? false : true);
			
			// 点击勾选按钮
			holder.cbNodeSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						orgSelected.put(org.getDataId(), org.getTypeDataId());
						
					} else {
						orgSelected.remove(org.getDataId());
					}
					
//					recursionOrgSelect(org);
					
					notifyDataSetChanged();
				}

				
			});
			
			return convertView;
		}
		
		private void recursionOrgSelect(final PdaOrgDto org) {
			for (PdaOrgDto orgDto : listOrgs) {
				if (org.getDataId() == orgDto.getParentId()) {
					if (orgSelected.get(org.getDataId()) != null) {
						orgSelected.put(orgDto.getDataId(), orgDto.getTypeDataId());								
					} else {
						orgSelected.remove(orgDto.getDataId());
					}
//					notifyDataSetChanged();
//					notifyDataSetInvalidated();
			
					if (!orgDto.isLeaf()) {
						// 递归
						recursionOrgSelect(orgDto);
					}
				}
			}
			
			
		}
		
		private List<String> getOrgTypeDataId(Map<Long, String> orgSelected) {
			List<String> listTypeData = new ArrayList<String>();
			if (orgSelected != null) {
				Set<Entry<Long, String>> entry = orgSelected.entrySet();
				Iterator<Entry<Long, String>> iter = entry.iterator();
				while (iter.hasNext()) {
					Map.Entry<Long, String> mapEntry = (Entry<Long, String>) iter.next();
					listTypeData.add(mapEntry.getValue());
				}
			}
			
			return listTypeData;
		}
		
    	public Map<Long, String> getOrgSelected() {
			return orgSelected;
		}

		public void setOrgSelected(Map<Long, String> orgSelected) {
			this.orgSelected = orgSelected;
		}
		
		class HolderView {
			ImageView ivExpandIcon;
			TextView tvNodeName;
			CheckBox cbNodeSelect;
		}
    	
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		orgClickEvent(position);
	}

	private void orgClickEvent(int position) {
		PdaOrgDto org = listExpandOrgnization.get(position);
		if (org.isExpanded()) {
			// 进行收缩
			org.setExpanded(false);
			
			List<PdaOrgDto> tempOrg = new ArrayList<PdaOrgDto>();
			for (int i=position+1; i<listExpandOrgnization.size(); i++) {
				if (org.getOrgLevel() >= listExpandOrgnization.get(i).getOrgLevel()) {
					break;
				}
				tempOrg.add(listExpandOrgnization.get(i));
			}
			
			listExpandOrgnization.removeAll(tempOrg);
			
			mTreeAdapter.notifyDataSetChanged();
			
		} else {
			// 进行展开
			org.setExpanded(true);
			
			int nextLevel = org.getLevel() + 1;
			int j=1;
			for (PdaOrgDto og : listAllOrgnization) {
				if (org.getDataId() == og.getParentId()) {
					// 寻找子节点
					// 放在下一级位置，构成树				
					og.setExpanded(false);
					og.setLevel(nextLevel);
					listExpandOrgnization.add(position+j, og);
					j++;
				}
//				mTreeAdapter.notifyDataSetChanged(); // 会出现莫名的错误
			}
			mTreeAdapter.notifyDataSetChanged();
		}
	}
}
