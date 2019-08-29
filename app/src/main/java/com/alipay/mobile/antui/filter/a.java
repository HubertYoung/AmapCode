package com.alipay.mobile.antui.filter;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/* compiled from: ScrollViewExtend */
final class a extends SimpleOnGestureListener {
    final /* synthetic */ ScrollViewExtend a;

    a(ScrollViewExtend this$0) {
        this.a = this$0;
    }

    public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (this.a.canScroll) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                this.a.canScroll = true;
            } else {
                this.a.canScroll = false;
            }
        }
        return this.a.canScroll;
    }
}
