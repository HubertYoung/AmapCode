package com.autonavi.common.cloudsync.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowCompat extends PopupWindow {
    Context mContext;

    public PopupWindowCompat() {
    }

    public PopupWindowCompat(Context context) {
        super(context);
    }

    public PopupWindowCompat(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PopupWindowCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public PopupWindowCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public PopupWindowCompat(View view) {
        super(view);
        this.mContext = view.getContext();
    }

    public PopupWindowCompat(int i, int i2) {
        super(i, i2);
    }

    public PopupWindowCompat(View view, int i, int i2) {
        super(view, i, i2);
        this.mContext = view.getContext();
    }

    public PopupWindowCompat(View view, int i, int i2, boolean z) {
        super(view, i, i2, z);
        this.mContext = view.getContext();
    }

    public void showAsDropDown(View view) {
        showAsDropDown(view, 0, 0);
    }

    public void showAsDropDown(View view, int i, int i2) {
        if (VERSION.SDK_INT > 23) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            if (this.mContext != null) {
                setHeight(this.mContext.getResources().getDisplayMetrics().heightPixels - (iArr[1] + view.getHeight()));
            }
            showAtLocation(view, 0, i, iArr[1] + view.getHeight() + i2);
            return;
        }
        super.showAsDropDown(view, i, i2);
    }
}
