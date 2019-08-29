package com.amap.bundle.drive.setting.quicknaviwidget.main.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

public class CarPlateTextView extends TextView {
    private CharSequence mOriginalPlate = "";
    private CharSequence mShownPlate = "";

    public CarPlateTextView(Context context) {
        super(context);
    }

    public CarPlateTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CarPlateTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setCarPlate(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mOriginalPlate = charSequence;
            StringBuilder sb = new StringBuilder(charSequence);
            if (sb.length() == 7) {
                this.mShownPlate = sb.replace(3, 6, "***");
            } else {
                this.mShownPlate = this.mOriginalPlate;
            }
            setText(this.mShownPlate);
        }
    }

    public void setText(String str) {
        setCarPlate(str);
    }

    public CharSequence getText() {
        return this.mOriginalPlate;
    }
}
