package com.alipay.mobile.common.share.widget;

import android.content.Context;

public final class ResUtils {
    public static final String ANIM = "anim";
    public static final String ATTR = "attr";
    public static final String COLOR = "color";
    public static final String DIMEN = "dimen";
    public static final String DRAWABLE = "drawable";
    public static final String ID = "id";
    public static final String INTEGER = "integer";
    public static final String LAYOUT = "layout";
    public static final String RAW = "raw";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";

    private ResUtils() {
    }

    public static int getResId(Context context, String type, String name) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
}
