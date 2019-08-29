package com.alipay.android.phone.mobilesdk.permission.b;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: PipelineRunnablePool */
final class g extends com.alipay.android.phone.mobilesdk.permission.utils.g<f> {
    private final AtomicInteger d = new AtomicInteger(1);

    public final synchronized f a(Runnable runnable, String threadName) {
        return a(runnable, threadName, false);
    }

    public final synchronized f b(Runnable runnable, String threadName) {
        try {
        }
        return a(runnable, threadName, true);
    }

    private synchronized f a(Runnable runnable, String threadName, boolean serial) {
        f pipeLineRunnable;
        if (serial) {
            runnable = new b(runnable);
        }
        if (this.c.size() == 0) {
            String prefix = String.valueOf(this.d.getAndIncrement());
            if (threadName == null || threadName.length() <= 0) {
                threadName = prefix;
            } else {
                threadName = prefix + "_" + threadName;
            }
            pipeLineRunnable = new f();
        } else {
            pipeLineRunnable = (f) this.c.pop();
        }
        pipeLineRunnable.a(runnable, threadName);
        return pipeLineRunnable;
    }

    public final synchronized void a(f object) {
        super.a(object);
    }
}
