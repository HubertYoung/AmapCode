package com.autonavi.map.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

@SuppressLint({"AppCompatCustomView"})
public class AmapButton extends Button {
    private float mDensity;
    private float mScaledDensity;

    public AmapButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public AmapButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AmapButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mScaledDensity = displayMetrics.scaledDensity;
        this.mDensity = displayMetrics.density;
    }

    private float getScaleSpSize(float f) {
        if (this.mScaledDensity <= 0.0f) {
            return f;
        }
        float f2 = f / this.mScaledDensity;
        return this.mScaledDensity != this.mDensity ? f2 / (this.mScaledDensity / this.mDensity) : f2;
    }
}
