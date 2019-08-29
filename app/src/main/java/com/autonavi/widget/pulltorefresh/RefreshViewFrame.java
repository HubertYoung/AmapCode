package com.autonavi.widget.pulltorefresh;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class RefreshViewFrame extends FrameLayout {
    private OnTouchListener mOnListViewDispatchTouchEvent;
    private OnTouchListener mOnListViewInterceptTouchEvent;

    public void setOnListViewInterceptTouchEvent(OnTouchListener onTouchListener) {
        this.mOnListViewInterceptTouchEvent = onTouchListener;
    }

    public void setOnListViewDispatchTouchEvent(OnTouchListener onTouchListener) {
        this.mOnListViewDispatchTouchEvent = onTouchListener;
    }

    public RefreshViewFrame(Context context) {
        super(context);
    }

    public void addView(View view) {
        if (!(view instanceof FrameLayout)) {
            super.addView(view);
        }
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        if (!(view instanceof FrameLayout)) {
            super.addView(view, i, layoutParams);
        }
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (!(view instanceof FrameLayout)) {
            super.addView(view, layoutParams);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mOnListViewDispatchTouchEvent != null) {
            this.mOnListViewDispatchTouchEvent.onTouch(this, motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mOnListViewInterceptTouchEvent != null) {
            return this.mOnListViewInterceptTouchEvent.onTouch(this, motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
