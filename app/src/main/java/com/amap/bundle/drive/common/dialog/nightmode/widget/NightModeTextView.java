package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.autonavi.minimap.widget.AmapTextView;

public class NightModeTextView extends AmapTextView {
    public ng mNightModeWrapper;

    public NightModeTextView(Context context) {
        this(context, null);
    }

    public NightModeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new ng(context, attributeSet, i, this);
    }

    public void setDayNightModeTextColor(int i, int i2) {
        this.mNightModeWrapper.b(i, i2);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }
}
