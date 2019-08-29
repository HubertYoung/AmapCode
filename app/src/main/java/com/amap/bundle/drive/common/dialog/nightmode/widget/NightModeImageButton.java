package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class NightModeImageButton extends ImageButton {
    private nc mNightModeWrapper;

    public NightModeImageButton(Context context) {
        this(context, null);
    }

    public NightModeImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new nc(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }

    public void setDayNightModeImageResource(int i, int i2) {
        this.mNightModeWrapper.b(i, i2);
    }
}
