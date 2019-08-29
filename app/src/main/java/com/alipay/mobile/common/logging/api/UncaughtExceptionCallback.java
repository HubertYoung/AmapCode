package com.alipay.mobile.common.logging.api;

public interface UncaughtExceptionCallback {
    String getExternalExceptionInfo(Thread thread, Throwable th);
}
