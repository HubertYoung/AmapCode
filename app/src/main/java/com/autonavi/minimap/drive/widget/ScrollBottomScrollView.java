package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollBottomScrollView extends ScrollView {
    private a mScrollBottomListener;

    public interface a {
    }

    public ScrollBottomScrollView(Context context) {
        super(context);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        if (this.mScrollBottomListener != null && i2 + getHeight() >= computeVerticalScrollRange()) {
            this.mScrollBottomListener = null;
        }
    }

    public void setScrollBottomListener(a aVar) {
        this.mScrollBottomListener = aVar;
    }
}
