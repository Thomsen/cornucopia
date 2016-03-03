package com.cornucopia.widgets.popupmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomsen
 * @version 1.0
 * @since 13-4-25 下午9:47
 */
public class PopupMenu {

	private Context mContext;

	private PopupWindow mPopupWindow;

	private Drawable mWindowBackground;

	private View mAnchorView;

	private WindowManager mWindowManager;

	private LayoutInflater mInflater;
	
    /**
     * 菜单列表
     */
    List<MenuItem> listItem;

    /**
     * 菜单以列表形式展示
     */
    ListView mListView;

    public PopupMenu(Context context) {
        mContext = context;
        mPopupWindow = new PopupWindow(mContext);
        listItem = new ArrayList<MenuItem>();
    }

    /**
     * 添加菜单
     * @param itemId
     * @param title
     * @return
     */
    public MenuItem addItem(int itemId, String title) {
        MenuItem item = new MenuItem(itemId, title);

        listItem.add(item);

        return item;
    }

    /**
     * 添加子菜单
     * @param parentItem
     * @param itemId
     * @param title
     * @return
     */
    public MenuItem addSubItem(MenuItem parentItem, int itemId, String title) {
        MenuItem subItem = new MenuItem(itemId, title);

        return subItem;
    }


    /**
     * 显示菜单
     */
    public void show(View anchorView) {
    	mPopupWindow.showAsDropDown(anchorView);
    }
}
