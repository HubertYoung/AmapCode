package com.alipay.mobile.beehive.compositeui.banner.model;

import android.content.Context;

public class BannerResConst {
    public static final boolean DEFAULT_CIRCLE_INDICATOR_CENTERED = true;
    public static final int DEFAULT_CIRCLE_INDICATOR_FILL_COLOR = -16733441;
    public static final int DEFAULT_CIRCLE_INDICATOR_ORIENTATION = 0;
    public static final int DEFAULT_CIRCLE_INDICATOR_PAGE_COLOR = -1644826;
    public static final int DEFAULT_CIRCLE_INDICATOR_RADIUS = 3;
    public static final boolean DEFAULT_CIRCLE_INDICATOR_SNAP = true;
    public static final int DEFAULT_CIRCLE_INDICATOR_STROKE_COLOR = -2236963;
    public static final int DEFAULT_CIRCLE_INDICATOR_STROKE_WIDTH = 0;
    public static final int GRAY_COLOR = 14803425;

    public static int dip2px(Context context, double d) {
        return (int) ((((double) context.getResources().getDisplayMetrics().density) * d) + 0.5d);
    }

    public static int px2dip(Context context, double pxValue) {
        return (int) ((pxValue / ((double) context.getResources().getDisplayMetrics().density)) + 0.5d);
    }
}
