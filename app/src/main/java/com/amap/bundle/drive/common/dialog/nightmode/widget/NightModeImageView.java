package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NightModeImageView extends ImageView {
    public nd mNightModeWrapper;

    public NightModeImageView(Context context) {
        this(context, null);
    }

    public NightModeImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new nd(context, attributeSet, i, this);
    }

    public void processNightMode(boolean z) {
        this.mNightModeWrapper.a(z);
    }

    public void setDayNightModeImageResource(int i, int i2) {
        this.mNightModeWrapper.b(i, i2);
    }

    public void setDayNightModeImageBackGround(int i, int i2) {
        this.mNightModeWrapper.a(i, i2);
    }
}
