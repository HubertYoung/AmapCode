package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class NightModeCheckBox extends CheckBox {
    public mz mNightModeWrapper;

    public NightModeCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new mz(context, attributeSet, i, this);
    }

    public NightModeCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeCheckBox(Context context) {
        this(context, null);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
