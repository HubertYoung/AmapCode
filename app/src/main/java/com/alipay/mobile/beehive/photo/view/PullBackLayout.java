package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class PullBackLayout extends FrameLayout {
    /* access modifiers changed from: private */
    @Nullable
    public Callback callback;
    /* access modifiers changed from: private */
    public final ViewDragHelper dragger;
    private boolean mEnablePullGuesture;
    GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public final int minimumFlingVelocity;

    public interface Callback {
        void onPull(float f);

        void onPullCancel();

        void onPullComplete();

        void onPullStart();

        boolean tryCaptureView(View view, int i);

        boolean tryCaptureViewWhenPullDown();
    }

    private class a extends android.support.v4.widget.ViewDragHelper.Callback {
        private a() {
        }

        /* synthetic */ a(PullBackLayout x0, byte b) {
            this();
        }

        public final boolean tryCaptureView(View child, int pointerId) {
            if (PullBackLayout.this.callback != null) {
                return PullBackLayout.this.callback.tryCaptureView(child, pointerId);
            }
            return true;
        }

        public final int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left < 0) {
                return Math.max((-PullBackLayout.this.getWidth()) / 2, left);
            }
            return Math.min(PullBackLayout.this.getWidth() / 2, left);
        }

        public final int clampViewPositionVertical(View child, int top, int dy) {
            return Math.max(0, top);
        }

        public final int getViewHorizontalDragRange(View child) {
            return PullBackLayout.this.getWidth();
        }

        public final int getViewVerticalDragRange(View child) {
            return PullBackLayout.this.getHeight();
        }

        public final void onViewCaptured(View capturedChild, int activePointerId) {
            if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPullStart();
            }
        }

        public final void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPull(((float) top) / ((float) PullBackLayout.this.getHeight()));
            }
        }

        public final void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild.getTop() <= (yvel > ((float) PullBackLayout.this.minimumFlingVelocity) ? PullBackLayout.this.getHeight() / 6 : PullBackLayout.this.getHeight() / 3)) {
                if (PullBackLayout.this.callback != null) {
                    PullBackLayout.this.callback.onPullCancel();
                }
                PullBackLayout.this.dragger.settleCapturedViewAt(0, 0);
                PullBackLayout.this.invalidate();
            } else if (PullBackLayout.this.callback != null) {
                PullBackLayout.this.callback.onPullComplete();
            }
        }
    }

    public void setEnablePullGuesture(boolean isEnable) {
        this.mEnablePullGuesture = isEnable;
    }

    public PullBackLayout(Context context) {
        this(context, null);
    }

    public PullBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dragger = ViewDragHelper.create(this, 0.125f, new a(this, 0));
        this.minimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            public final boolean onDown(MotionEvent e) {
                return true;
            }

            public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (distanceY >= 0.0f) {
                    return false;
                }
                if (PullBackLayout.this.callback != null) {
                    return PullBackLayout.this.callback.tryCaptureViewWhenPullDown();
                }
                return true;
            }
        });
    }

    public void setCallback(@Nullable Callback callback2) {
        this.callback = callback2;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mEnablePullGuesture || ev.getPointerCount() > 1 || !this.mGestureDetector.onTouchEvent(ev)) {
            return false;
        }
        return this.dragger.shouldInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (!this.mEnablePullGuesture || event.getPointerCount() > 1) {
            return false;
        }
        this.dragger.processTouchEvent(event);
        return true;
    }

    public void computeScroll() {
        if (this.dragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
