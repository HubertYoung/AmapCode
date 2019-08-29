package com.alipay.mobile.beehive.compositeui.wheelview.picker;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.View;
import com.alipay.mobile.beehive.compositeui.wheelview.widget.ConfirmPopup;

public abstract class WheelPicker extends ConfirmPopup<View> {
    protected int lineColor = -8139290;
    protected boolean lineVisible = true;
    protected int offset = 1;
    protected int textColorFocus = -16611122;
    protected int textColorNormal = -4473925;
    protected int textSize = 16;

    public WheelPicker(Activity activity) {
        super(activity);
    }

    public void setTextSize(int textSize2) {
        this.textSize = textSize2;
    }

    public void setTextColor(@ColorInt int textColorFocus2, @ColorInt int textColorNormal2) {
        this.textColorFocus = textColorFocus2;
        this.textColorNormal = textColorNormal2;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColorFocus = textColor;
    }

    public void setLineVisible(boolean lineVisible2) {
        this.lineVisible = lineVisible2;
    }

    public void setLineColor(@ColorInt int lineColor2) {
        this.lineColor = lineColor2;
    }

    public void setOffset(@IntRange(from = 1, to = 4) int offset2) {
        this.offset = offset2;
    }
}
