package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class PhotoGridView extends GridView {
    public static final String TAG = "PhotoGridView";
    private OnOverScrolledListener listener;

    public interface OnOverScrolledListener {
        void onOverScrolled(int i, int i2);
    }

    public PhotoGridView(Context context) {
        this(context, null);
    }

    public PhotoGridView(Context context, AttributeSet as) {
        super(context, as);
    }

    public void setOnOverScrolledListener(OnOverScrolledListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (this.listener != null) {
            this.listener.onOverScrolled(scrollX, scrollY);
        }
    }
}
