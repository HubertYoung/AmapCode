package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class NightModeRelativeLayout extends RelativeLayout {
    public nf mNightModeWrapper;

    public NightModeRelativeLayout(Context context) {
        this(context, null);
    }

    public NightModeRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new nf(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
