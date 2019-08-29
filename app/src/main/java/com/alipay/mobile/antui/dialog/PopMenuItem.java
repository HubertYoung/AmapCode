package com.alipay.mobile.antui.dialog;

import android.graphics.drawable.Drawable;
import java.util.HashMap;

public class PopMenuItem {
    private Drawable drawable;
    private HashMap<String, Object> externParam;
    private CharSequence name;
    private int resId = 0;
    private int type;

    public PopMenuItem(CharSequence name2, int resId2) {
        this.name = name2;
        this.resId = resId2;
    }

    public PopMenuItem(CharSequence name2) {
        this.name = name2;
    }

    public PopMenuItem(CharSequence name2, Drawable drawable2) {
        this.name = name2;
        this.drawable = drawable2;
    }

    public PopMenuItem(String name2, int resId2) {
        this.name = name2;
        this.resId = resId2;
    }

    public PopMenuItem(String name2, Drawable drawable2) {
        this.name = name2;
        this.drawable = drawable2;
    }

    public CharSequence getName() {
        return this.name;
    }

    public void setName(CharSequence name2) {
        this.name = name2;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int resId2) {
        this.resId = resId2;
    }

    public void setDrawable(Drawable drawable2) {
        this.drawable = drawable2;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public HashMap<String, Object> getExternParam() {
        return this.externParam;
    }

    public void setExternParam(HashMap<String, Object> externParam2) {
        this.externParam = externParam2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
