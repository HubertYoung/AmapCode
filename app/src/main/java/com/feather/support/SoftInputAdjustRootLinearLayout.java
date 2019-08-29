package com.feather.support;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

public class SoftInputAdjustRootLinearLayout extends LinearLayout {
    public SoftInputAdjustRootLinearLayout(Context context) {
        super(context);
    }

    public SoftInputAdjustRootLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SoftInputAdjustRootLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SoftInputAdjustRootLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public final boolean fitSystemWindows(Rect rect) {
        if (VERSION.SDK_INT >= 19) {
            rect.left = 0;
            rect.top = 0;
            rect.right = 0;
        }
        return super.fitSystemWindows(rect);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        return VERSION.SDK_INT >= 20 ? super.onApplyWindowInsets(windowInsets.replaceSystemWindowInsets(0, 0, 0, windowInsets.getSystemWindowInsetBottom())) : windowInsets;
    }
}
