package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class NightModeView extends View {
    public my mNightModeWrapper;

    public NightModeView(Context context) {
        this(context, null);
    }

    public NightModeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new my(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
