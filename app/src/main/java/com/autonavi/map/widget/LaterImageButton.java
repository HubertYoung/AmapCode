package com.autonavi.map.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class LaterImageButton extends ImageButton {
    private final int STOP = 327681;
    /* access modifiers changed from: private */
    public int interval = 200;
    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 327681) {
                LaterImageButton.this.mHandler.removeCallbacks(LaterImageButton.this.run);
                LaterImageButton.this.mListener.timeIsComing(LaterImageButton.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public LaterTouchListener mListener;
    private long mStartTime = 0;
    Message msg;
    Runnable run = new Runnable() {
        public void run() {
            LaterImageButton.this.time = LaterImageButton.this.time + 1;
            if (LaterImageButton.this.time < 5) {
                LaterImageButton.this.mHandler.postDelayed(LaterImageButton.this.run, (long) LaterImageButton.this.interval);
                return;
            }
            LaterImageButton.this.time = 0;
            LaterImageButton.this.msg = LaterImageButton.this.mHandler.obtainMessage(327681);
            LaterImageButton.this.msg.sendToTarget();
        }
    };
    /* access modifiers changed from: private */
    public int time = 0;
    private final int untilTimes = 5;

    public LaterImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setTouchListener(LaterTouchListener laterTouchListener) {
        this.mListener = laterTouchListener;
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
