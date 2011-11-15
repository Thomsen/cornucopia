package com.csit.cornucopia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Cornucopia extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * 使用SimpleAdapter进行布局
		 */
		// 单层
		setListAdapter(new SimpleAdapter(this, getIntentData(),
				android.R.layout.simple_list_item_1, new String[] { "item" },
				new int[] { android.R.id.text1 }));

		// 多层
		String path = "";
	}
	
	/**
	 * @return List结构，通过Map将键值对应
	 * 
	 */
	private List<? extends Map<String, ?>> getIntentData() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		/*
		 * 为了提供查询所有的Activity，需要提供Intent参数
		 */
		Intent intent = new Intent(Intent.ACTION_MAIN);

		/*
		 * 获取当前应用程序的包管理，在这里可以通过包管理类得到应用程序的相关值
		 */
		PackageManager pkgManager = this.getPackageManager();

		/*
		 *  Retrieve all activities that can be performed for the given intent.
		 *  第二参数使用MATCH_DEFAULT_ONLY，则不能够显示item
		 */
		List<ResolveInfo> activityList = pkgManager.queryIntentActivities(
				intent, PackageManager.GET_INTENT_FILTERS);

		/*
		 * 对得到的所有Activity列表进行处理
		 */
		if (activityList == null) {
			return result;
		}

		int listLength = activityList.size();

		for (int i = 0; i < listLength; i++) {
			ResolveInfo resolveInfo = activityList.get(i);

			/*
			 * resolveInfo.activityInfo.applicationInfo.packageName：系统中得到的所有activity的包路径
			 * this.getClass().getPackage().getName()：该应用程序的包路径
			 * resolveInfo.activityInfo.name：系统中所有activity的类名
			 * this.getClass().getName()：该应用程序activity的类名
			 */
			if (resolveInfo.activityInfo.applicationInfo.packageName
					.startsWith(this.getClass().getPackage().getName())
					&& !resolveInfo.activityInfo.name.equals(this.getClass()
							.getName())) {
				/*
				 *  Retrieve the current textual label associated with this resolution.
				 *  infoLabel: 是在Manifest的android:label属性中设置
				 */
				CharSequence infoLabel = resolveInfo.loadLabel(pkgManager);

				/*
				 * 获取当前item的标签
				 */
				String label = ((infoLabel != null) ? infoLabel.toString()
						: resolveInfo.activityInfo.name);

				addItem(result,
						label,
						getActivityIntent(
								resolveInfo.activityInfo.applicationInfo.packageName,
								resolveInfo.activityInfo.name));
			}

		}

		return result;
	}
	
	/**
	 * 通过哈希对象来设置键值对应
	 * 
	 * @param result
	 * @param label
	 * @param activityIntent
	 */
	private void addItem(List<Map<String, Object>> result, String label,
			Intent activityIntent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("item", label);
		map.put("intent", activityIntent);
		result.add(map);
	}

	/**
	 * startActivity是通过package name/package class name里启动一个Activity
	 * 
	 * @param packageName: 例如com.csit.package
	 * @param name: 例如com.csit.package.class
	 * @return intent
	 */
	private Intent getActivityIntent(String packageName, String name) {
		Intent intent = new Intent();
		intent.setClassName(packageName, name);
		return intent;
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/*
		 *  得到item对应的inent
		 */
		Map map = (Map) l.getItemAtPosition(position);
		Intent intent = (Intent) map.get("intent");
		
		startActivity(intent);
	}

}
