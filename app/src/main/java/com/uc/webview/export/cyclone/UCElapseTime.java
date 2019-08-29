package com.uc.webview.export.cyclone;

import android.os.SystemClock;

@Constant
/* compiled from: ProGuard */
public class UCElapseTime {
    private long mStart = SystemClock.elapsedRealtime();
    private long mStartCpu = SystemClock.currentThreadTimeMillis();

    public long getMilis() {
        return SystemClock.elapsedRealtime() - this.mStart;
    }

    public long getMilisCpu() {
        return SystemClock.currentThreadTimeMillis() - this.mStartCpu;
    }

    public void reset() {
        this.mStart = SystemClock.elapsedRealtime();
        this.mStartCpu = SystemClock.currentThreadTimeMillis();
    }
}
