package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class APListView extends ListView {
    public APListView(Context context) {
        super(context);
    }

    public APListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(APViewEventHelper.a(onItemClickListener));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        postInvalidate();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        } catch (IndexOutOfBoundsException unused) {
        }
    }
}
