package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ZoomWidgetLayout extends RelativeLayout {
    private final int STOP = 327681;
    /* access modifiers changed from: private */
    public int interval = 200;
    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 327681) {
                ZoomWidgetLayout.this.mHandler.removeCallbacks(ZoomWidgetLayout.this.run);
                ZoomWidgetLayout.this.mListener.timeIsComing(ZoomWidgetLayout.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public ZoomTouchListener mListener;
    private long mStartTime = 0;
    Message msg;
    Runnable run = new Runnable() {
        public void run() {
            ZoomWidgetLayout.this.time = ZoomWidgetLayout.this.time + 1;
            if (ZoomWidgetLayout.this.time < 5) {
                ZoomWidgetLayout.this.mHandler.postDelayed(ZoomWidgetLayout.this.run, (long) ZoomWidgetLayout.this.interval);
                return;
            }
            ZoomWidgetLayout.this.time = 0;
            ZoomWidgetLayout.this.msg = ZoomWidgetLayout.this.mHandler.obtainMessage(327681);
            ZoomWidgetLayout.this.msg.sendToTarget();
        }
    };
    /* access modifiers changed from: private */
    public int time = 0;
    private final int untilTimes = 5;

    public interface ZoomTouchListener {
        void timeIsComing(View view);

        void touchToZoom(View view);
    }

    public ZoomWidgetLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setTouchListener(ZoomTouchListener zoomTouchListener) {
        this.mListener = zoomTouchListener;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.mHandler.removeCallbacks(this.run);
            if (System.currentTimeMillis() - this.mStartTime < ((long) (this.interval * 5)) && this.mListener != null) {
                this.mListener.touchToZoom(this);
            }
            this.time = 0;
        } else if (motionEvent.getAction() == 0) {
            this.mStartTime = System.currentTimeMillis();
            this.mHandler.post(this.run);
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 23 || i == 66) {
            super.onKeyDown(i, keyEvent);
            this.mStartTime = System.currentTimeMillis();
            this.mHandler.post(this.run);
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 23 || i == 66) {
            this.mHandler.removeCallbacks(this.run);
            if (System.currentTimeMillis() - this.mStartTime < ((long) (this.interval * 5)) && this.mListener != null) {
                this.mListener.touchToZoom(this);
            }
            this.time = 0;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
