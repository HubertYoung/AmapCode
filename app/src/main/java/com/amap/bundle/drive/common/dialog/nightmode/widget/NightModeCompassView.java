package com.amap.bundle.drive.common.dialog.nightmode.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NightModeCompassView extends ImageView {
    public na mNightModeWrapper;

    public NightModeCompassView(Context context) {
        this(context, null);
    }

    public NightModeCompassView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NightModeCompassView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNightModeWrapper = new na(context, attributeSet, i, this);
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
