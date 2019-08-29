package com.alipay.mobile.tinyappcustom.h5plugin.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;

public class H5OpenAuthScrollView extends ScrollView {
    private int a = 500;

    public H5OpenAuthScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(this.a, Integer.MIN_VALUE));
    }
}
