package com.alipay.mobile.common.logging.impl;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.Thread.UncaughtExceptionHandler;

public class BaseExceptionHandler implements UncaughtExceptionHandler {
    private static BaseExceptionHandler a;
    private UncaughtExceptionHandler b;
    private boolean c = false;
    private boolean d = false;

    public static synchronized BaseExceptionHandler createInstance() {
        BaseExceptionHandler baseExceptionHandler;
        synchronized (BaseExceptionHandler.class) {
            if (a == null) {
                a = new BaseExceptionHandler();
            }
            baseExceptionHandler = a;
        }
        return baseExceptionHandler;
    }

    public static BaseExceptionHandler getInstance() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("need createInstance befor use");
    }

    public synchronized void setup() {
        if (!this.c) {
            this.c = true;
            this.d = true;
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public synchronized void takeDown() {
        this.d = false;
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        LoggerFactory.getTraceLogger().debug("BaseExceptionHandler", "enter uncaughtException. inUse:" + this.d);
        if (this.d) {
            LoggerFactory.getMonitorLogger().crash(throwable, null);
            try {
                LoggerFactory.getLogContext().flush(true);
                LoggerFactory.getLogContext().flush("applog", true);
                LoggerFactory.getLogContext().backupCurrentFile("applog", false);
            } catch (Throwable th) {
            }
        }
        if (this.b != null) {
            try {
                this.b.uncaughtException(thread, throwable);
            } catch (Throwable th2) {
            }
        }
    }
}
