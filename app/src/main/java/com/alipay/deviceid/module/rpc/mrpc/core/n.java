package com.alipay.deviceid.module.rpc.mrpc.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class n implements ThreadFactory {
    private final AtomicInteger a = new AtomicInteger(1);

    n() {
    }

    public final Thread newThread(Runnable runnable) {
        StringBuilder sb = new StringBuilder("com.alipay.mobile.common.transport.http.HttpManager.HttpWorker #");
        sb.append(this.a.getAndIncrement());
        Thread thread = new Thread(runnable, sb.toString());
        thread.setPriority(4);
        return thread;
    }
}
