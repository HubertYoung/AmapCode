package com.alibaba.analytics.core.selfmonitor.exception;

public class AppMonitorException extends RuntimeException {
    public AppMonitorException() {
    }

    public AppMonitorException(String str) {
        super(str);
    }

    public AppMonitorException(String str, Throwable th) {
        super(str, th);
    }

    public AppMonitorException(Throwable th) {
        super(th);
    }
}
