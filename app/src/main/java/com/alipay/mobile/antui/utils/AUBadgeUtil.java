package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AUBadgeUtil {
    public static void addBadgeView(Context context, RelativeLayout container, View targetView, View flagView) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(1, targetView.getId());
        layoutParams.addRule(6, targetView.getId());
        AuiLogger.info("BadgeViewHelper", "layoutParams topMargin:" + layoutParams.topMargin + "  leftMargin" + layoutParams.leftMargin);
        flagView.setLayoutParams(layoutParams);
        container.addView(flagView);
    }

    public static void addBadgeView(RelativeLayout container, View flagView, LayoutParams params) {
        flagView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        flagView.setLayoutParams(params);
        container.addView(flagView);
    }
}
