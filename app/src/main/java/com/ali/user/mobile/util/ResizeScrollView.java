package com.ali.user.mobile.util;

import android.content.Context;
import android.util.AttributeSet;

public class ResizeScrollView extends AUScrollView {
    private OnSizeChangedListener a;

    public interface OnSizeChangedListener {
        void a(int i, int i2);
    }

    public ResizeScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ResizeScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ResizeScrollView(Context context) {
        super(context);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {
        this.a = onSizeChangedListener;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (this.a != null) {
            this.a.a(i2, i4);
        }
        super.onSizeChanged(i, i2, i3, i4);
    }
}
