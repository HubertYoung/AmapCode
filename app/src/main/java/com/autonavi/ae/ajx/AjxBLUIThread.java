package com.autonavi.ae.ajx;

import android.os.Handler;
import android.os.Looper;

public class AjxBLUIThread {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public boolean isStop = false;

    /* access modifiers changed from: private */
    public native void nativePost(long j);

    public void post(final long j, long j2) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (!AjxBLUIThread.this.isStop) {
                    AjxBLUIThread.this.nativePost(j);
                }
            }
        }, j2);
    }

    public void setStop(boolean z) {
        this.isStop = z;
        if (z) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
