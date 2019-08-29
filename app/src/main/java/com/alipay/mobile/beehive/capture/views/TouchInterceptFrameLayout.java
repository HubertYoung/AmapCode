package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class TouchInterceptFrameLayout extends FrameLayout {
    private GestureDetector mDector = new GestureDetector(new SimpleOnGestureListener() {
        public final boolean onSingleTapUp(MotionEvent e) {
            if (TouchInterceptFrameLayout.this.mListener != null) {
                TouchInterceptFrameLayout.this.mListener.onTap(TouchInterceptFrameLayout.this);
            }
            return super.onSingleTapUp(e);
        }
    });
    /* access modifiers changed from: private */
    public TapListener mListener;

    public interface TapListener {
        void onTap(View view);
    }

    public TouchInterceptFrameLayout(Context context) {
        super(context);
    }

    public TouchInterceptFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchInterceptFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.mDector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void setOnTapListener(TapListener listener) {
        this.mListener = listener;
    }
}
