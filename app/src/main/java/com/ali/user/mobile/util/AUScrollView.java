package com.ali.user.mobile.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class AUScrollView extends ScrollView {
    private AUScrollViewListener a;

    public AUScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AUScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AUScrollView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }

    public void setScrollViewListener(AUScrollViewListener aUScrollViewListener) {
        this.a = aUScrollViewListener;
    }
}
