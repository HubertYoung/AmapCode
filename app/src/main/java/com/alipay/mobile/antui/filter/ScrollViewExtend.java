package com.alipay.mobile.antui.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

public class ScrollViewExtend extends ScrollView {
    /* access modifiers changed from: private */
    public boolean canScroll = true;
    private GestureDetector mGestureDetector = new GestureDetector(new a(this));
    OnTouchListener mGestureListener;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            this.canScroll = true;
        }
        if (!super.onInterceptTouchEvent(ev) || !this.mGestureDetector.onTouchEvent(ev)) {
            return false;
        }
        return true;
    }
}
