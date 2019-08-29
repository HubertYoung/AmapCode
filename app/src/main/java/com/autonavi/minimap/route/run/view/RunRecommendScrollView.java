package com.autonavi.minimap.route.run.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class RunRecommendScrollView extends HorizontalScrollView {
    /* access modifiers changed from: private */
    public int currentX;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public boolean mIsScrolled;
    /* access modifiers changed from: private */
    public a mScrollViewListener;
    private Runnable scrollRunnable;
    /* access modifiers changed from: private */
    public ScrollType scrollType;

    enum ScrollType {
        IDLE,
        TOUCH_SCROLL,
        FLING
    }

    public interface a {
        void a(ScrollType scrollType, int i);
    }

    public RunRecommendScrollView(Context context) {
        super(context);
        this.currentX = 0;
        this.scrollType = ScrollType.IDLE;
        this.scrollRunnable = new Runnable() {
            public final void run() {
                if (RunRecommendScrollView.this.getScrollX() == RunRecommendScrollView.this.currentX) {
                    RunRecommendScrollView.this.scrollType = ScrollType.IDLE;
                    if (RunRecommendScrollView.this.mScrollViewListener != null && RunRecommendScrollView.this.mIsScrolled) {
                        a access$200 = RunRecommendScrollView.this.mScrollViewListener;
                        ScrollType access$100 = RunRecommendScrollView.this.scrollType;
                        int access$000 = RunRecommendScrollView.this.currentX;
                        RunRecommendScrollView.this.getScrollY();
                        access$200.a(access$100, access$000);
                        RunRecommendScrollView.this.mIsScrolled = false;
                    }
                    RunRecommendScrollView.this.mHandler.removeCallbacks(this);
                    return;
                }
                RunRecommendScrollView.this.scrollType = ScrollType.FLING;
                if (RunRecommendScrollView.this.mScrollViewListener != null) {
                    a access$2002 = RunRecommendScrollView.this.mScrollViewListener;
                    ScrollType access$1002 = RunRecommendScrollView.this.scrollType;
                    int scrollX = RunRecommendScrollView.this.getScrollX();
                    RunRecommendScrollView.this.getScrollY();
                    access$2002.a(access$1002, scrollX);
                }
                RunRecommendScrollView.this.currentX = RunRecommendScrollView.this.getScrollX();
                RunRecommendScrollView.this.mHandler.postDelayed(this, 50);
            }
        };
    }

    public RunRecommendScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentX = 0;
        this.scrollType = ScrollType.IDLE;
        this.scrollRunnable = new Runnable() {
            public final void run() {
                if (RunRecommendScrollView.this.getScrollX() == RunRecommendScrollView.this.currentX) {
                    RunRecommendScrollView.this.scrollType = ScrollType.IDLE;
                    if (RunRecommendScrollView.this.mScrollViewListener != null && RunRecommendScrollView.this.mIsScrolled) {
                        a access$200 = RunRecommendScrollView.this.mScrollViewListener;
                        ScrollType access$100 = RunRecommendScrollView.this.scrollType;
                        int access$000 = RunRecommendScrollView.this.currentX;
                        RunRecommendScrollView.this.getScrollY();
                        access$200.a(access$100, access$000);
                        RunRecommendScrollView.this.mIsScrolled = false;
                    }
                    RunRecommendScrollView.this.mHandler.removeCallbacks(this);
                    return;
                }
                RunRecommendScrollView.this.scrollType = ScrollType.FLING;
                if (RunRecommendScrollView.this.mScrollViewListener != null) {
                    a access$2002 = RunRecommendScrollView.this.mScrollViewListener;
                    ScrollType access$1002 = RunRecommendScrollView.this.scrollType;
                    int scrollX = RunRecommendScrollView.this.getScrollX();
                    RunRecommendScrollView.this.getScrollY();
                    access$2002.a(access$1002, scrollX);
                }
                RunRecommendScrollView.this.currentX = RunRecommendScrollView.this.getScrollX();
                RunRecommendScrollView.this.mHandler.postDelayed(this, 50);
            }
        };
        this.mHandler = new Handler();
    }

    public RunRecommendScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentX = 0;
        this.scrollType = ScrollType.IDLE;
        this.scrollRunnable = new Runnable() {
            public final void run() {
                if (RunRecommendScrollView.this.getScrollX() == RunRecommendScrollView.this.currentX) {
                    RunRecommendScrollView.this.scrollType = ScrollType.IDLE;
                    if (RunRecommendScrollView.this.mScrollViewListener != null && RunRecommendScrollView.this.mIsScrolled) {
                        a access$200 = RunRecommendScrollView.this.mScrollViewListener;
                        ScrollType access$100 = RunRecommendScrollView.this.scrollType;
                        int access$000 = RunRecommendScrollView.this.currentX;
                        RunRecommendScrollView.this.getScrollY();
                        access$200.a(access$100, access$000);
                        RunRecommendScrollView.this.mIsScrolled = false;
                    }
                    RunRecommendScrollView.this.mHandler.removeCallbacks(this);
                    return;
                }
                RunRecommendScrollView.this.scrollType = ScrollType.FLING;
                if (RunRecommendScrollView.this.mScrollViewListener != null) {
                    a access$2002 = RunRecommendScrollView.this.mScrollViewListener;
                    ScrollType access$1002 = RunRecommendScrollView.this.scrollType;
                    int scrollX = RunRecommendScrollView.this.getScrollX();
                    RunRecommendScrollView.this.getScrollY();
                    access$2002.a(access$1002, scrollX);
                }
                RunRecommendScrollView.this.currentX = RunRecommendScrollView.this.getScrollX();
                RunRecommendScrollView.this.mHandler.postDelayed(this, 50);
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mIsScrolled = true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
                this.mHandler.post(this.scrollRunnable);
                break;
            case 2:
                this.scrollType = ScrollType.TOUCH_SCROLL;
                a aVar = this.mScrollViewListener;
                ScrollType scrollType2 = this.scrollType;
                int scrollX = getScrollX();
                getScrollY();
                aVar.a(scrollType2, scrollX);
                this.mHandler.removeCallbacks(this.scrollRunnable);
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setmScrollViewListener(a aVar) {
        this.mScrollViewListener = aVar;
    }
}
