package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.transport.utils.LogCatUtil;

public abstract class SecureRunnable implements Runnable {
    public abstract void call();

    public void run() {
        try {
            call();
        } catch (Exception e) {
            LogCatUtil.error((String) "SecureRunnable", (Throwable) e);
        }
    }
}
