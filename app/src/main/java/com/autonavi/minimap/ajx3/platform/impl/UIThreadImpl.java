package com.autonavi.minimap.ajx3.platform.impl;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.minimap.ajx3.platform.ackor.IUIThread;

public class UIThreadImpl extends IUIThread {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public void post(final long j, long j2) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                UIThreadImpl.this.nativePost(j);
            }
        }, j2);
    }
}
