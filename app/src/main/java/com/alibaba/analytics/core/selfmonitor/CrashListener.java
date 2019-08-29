package com.alibaba.analytics.core.selfmonitor;

public interface CrashListener {
    void onCrash(Thread thread, Throwable th);
}
