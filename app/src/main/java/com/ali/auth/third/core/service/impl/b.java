package com.ali.auth.third.core.service.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class b implements ThreadFactory {
    final /* synthetic */ a a;
    private final AtomicInteger b = new AtomicInteger(1);

    b(a aVar) {
        this.a = aVar;
    }

    public Thread newThread(Runnable runnable) {
        StringBuilder sb = new StringBuilder("ExecutorTask #");
        sb.append(this.b.getAndIncrement());
        return new Thread(runnable, sb.toString());
    }
}
