package com.autonavi.minimap.bundle.activities.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class InterruptableRelativeLayout extends RelativeLayout {
    private boolean mInterruptTouchEvent;

    public InterruptableRelativeLayout(Context context) {
        super(context);
    }

    public InterruptableRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InterruptableRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isInterruptTouchEvent() {
        return this.mInterruptTouchEvent;
    }

    public void setInterruptTouchEvent(boolean z) {
        this.mInterruptTouchEvent = z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mInterruptTouchEvent) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
