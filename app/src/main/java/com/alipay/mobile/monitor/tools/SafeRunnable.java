package com.alipay.mobile.monitor.tools;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public abstract class SafeRunnable implements Runnable {
    public abstract void safeRun();

    public void run() {
        try {
            safeRun();
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().warn(getClass().getName(), th);
        }
    }
}
