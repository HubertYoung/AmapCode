package com.alipay.mobile.nebula.view;

import android.graphics.drawable.Drawable;

public class H5NavMenuItem {
    public Drawable icon;
    public boolean iconDownloading;
    public String iconUrl;
    public String name;
    public String redDotNum;
    public boolean selected;
    public String tag;
    public boolean temp;

    public H5NavMenuItem(String name2, String tag2, Drawable icon2, boolean temp2, boolean selected2) {
        this.name = name2;
        this.tag = tag2;
        this.icon = icon2;
        this.temp = temp2;
        this.selected = selected2;
        this.iconDownloading = false;
    }

    public H5NavMenuItem(String name2, String tag2, Drawable icon2, boolean temp2) {
        this(name2, tag2, icon2, temp2, false);
    }

    public H5NavMenuItem(Drawable icon2, String tag2, boolean selected2) {
        this(null, tag2, icon2, false, selected2);
    }

    public void setRedDotNum(String redDotNum2) {
        this.redDotNum = redDotNum2;
    }
}
