package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class NightModeEditText extends EditText {
    public nb mNightModeWrapper;

    public NightModeEditText(Context context) {
        this(context, null);
    }

    public NightModeEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new nb(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
