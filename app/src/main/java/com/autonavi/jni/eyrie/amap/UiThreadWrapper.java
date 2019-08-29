package com.autonavi.jni.eyrie.amap;

import android.os.Handler;
import android.os.Looper;

public class UiThreadWrapper {
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    static UiThreadWrapper sInstance;
    /* access modifiers changed from: private */
    public volatile boolean mIsUiDestroyed;
    private long mPtr;

    /* access modifiers changed from: private */
    public void log(String str) {
    }

    private static native long nativeCreateInstance(UiThreadWrapper uiThreadWrapper);

    private static native void nativeDestroyInstance(long j);

    /* access modifiers changed from: private */
    public static native void nativeRun(long j);

    public static UiThreadWrapper getInstance() {
        return sInstance;
    }

    public static void init() {
        if (sInstance == null) {
            sInstance = new UiThreadWrapper();
        }
    }

    public static void uninit() {
        if (sInstance != null) {
            sInstance.release();
            sInstance = null;
        }
    }

    private UiThreadWrapper() {
        this.mPtr = 0;
        this.mIsUiDestroyed = true;
        this.mPtr = nativeCreateInstance(this);
        this.mIsUiDestroyed = false;
    }

    public void onUiDestroyed() {
        this.mIsUiDestroyed = true;
    }

    private void post(final long j, long j2) {
        log(String.format("ptr=%d,ms=%d", new Object[]{Long.valueOf(j), Long.valueOf(j2)}));
        if (!this.mIsUiDestroyed) {
            sHandler.postDelayed(new Runnable() {
                public void run() {
                    UiThreadWrapper.this.log(String.format("ptr=%d", new Object[]{Long.valueOf(j)}));
                    if (!UiThreadWrapper.this.mIsUiDestroyed) {
                        UiThreadWrapper.nativeRun(j);
                    }
                }
            }, j2);
        }
    }

    private void release() {
        nativeDestroyInstance(this.mPtr);
        this.mPtr = 0;
    }

    public long getNativePtr() {
        return this.mPtr;
    }
}
