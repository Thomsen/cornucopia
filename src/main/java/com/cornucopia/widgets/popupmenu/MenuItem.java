package com.cornucopia.widgets.popupmenu;

import android.graphics.drawable.Drawable;

/**
 * @author Thomsen
 * @version 1.0
 * @since 13-4-25 下午9:28
 */
public class MenuItem {

    /**
     * 标识
     */
    private int itemId;

    /**
     * 内容
     */
    private String title;

    /**
     * 图标
     */
    private Drawable icon;

    public MenuItem(int itemId, String title) {
        this.itemId = itemId;
        this.title = title;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
