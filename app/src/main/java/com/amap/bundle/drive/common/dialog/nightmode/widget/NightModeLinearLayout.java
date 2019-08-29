package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class NightModeLinearLayout extends LinearLayout {
    public ne mNightModeWrapper;

    public NightModeLinearLayout(Context context) {
        this(context, null);
    }

    public NightModeLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new ne(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
