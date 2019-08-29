package com.alipay.mobile.antui.iconfont.model;

import android.support.annotation.ColorInt;

public class IconPaintBuilder {
    @ColorInt
    public int color;
    public boolean isBold;
    public int resId;
    public String resString;
    public int size;

    public IconPaintBuilder() {
    }

    public IconPaintBuilder(@ColorInt int color2, int size2, int resId2) {
        this.color = color2;
        this.size = size2;
        this.resId = resId2;
    }

    public IconPaintBuilder(@ColorInt int color2, int size2, String resString2) {
        this.color = color2;
        this.size = size2;
        this.resString = resString2;
    }
}
