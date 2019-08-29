package com.autonavi.minimap.nativesupport.platform;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.minimap.ackor.ackorplatform.IUIThread;

class UIThreadImpl extends IUIThread {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    UIThreadImpl() {
    }

    public void post(final long j, long j2) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                UIThreadImpl.this.nativePost(j);
            }
        }, j2);
    }
}
