package com.autonavi.jni.vcs;

import android.os.Handler;
import android.os.Looper;

public class VCSUIThreadCallback {
    private static final String TAG = "VCSUIThreadCallback";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isStop = false;
    private long mMainThreadId = -1;

    private native void nativePost(long j);

    public VCSUIThreadCallback() {
        try {
            this.mMainThreadId = Looper.getMainLooper().getThread().getId();
        } catch (Exception unused) {
        }
    }

    public void post(final long j, long j2) {
        if (j2 > 0 || !isMainThread()) {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    VCSUIThreadCallback.this.runNative(j);
                }
            }, j2);
        } else {
            runNative(j);
        }
    }

    public void setStop(boolean z) {
        this.isStop = z;
    }

    private boolean isMainThread() {
        boolean z = false;
        try {
            if (this.mMainThreadId == Thread.currentThread().getId()) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void runNative(long j) {
        if (!this.isStop) {
            nativePost(j);
        }
    }
}
