package com.alipay.android.phone.mobilesdk.permission.b;

import com.alipay.android.phone.mobilesdk.permission.utils.g.a;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: PipelineRunnable */
final class f implements a, Runnable {
    public static final g a = new g();
    protected static final AtomicInteger b = new AtomicInteger(0);
    protected final String c = ("Transaction_" + b.getAndIncrement());
    protected e d;
    protected Runnable e;
    protected String f;
    protected int g = 0;

    f() {
    }

    /* access modifiers changed from: 0000 */
    public final void a(Runnable runnable, String threadName) {
        this.e = runnable;
        this.f = threadName;
        this.g = 0;
    }

    public final int a() {
        return this.g;
    }

    public final void a(e pipeLine) {
        this.d = pipeLine;
    }

    public final void b() {
        LoggerFactory.getTraceLogger().info("AsyTskExecutor", "reset");
        a(null, null);
        this.d = null;
    }

    public final void run() {
        String oldThreadName = Thread.currentThread().getName();
        String threadName = oldThreadName;
        if (this.f != null && this.f.length() > 0) {
            threadName = threadName + this.f;
            Thread.currentThread().setName(threadName);
        }
        long start = System.currentTimeMillis();
        e pipeLine = this.d;
        String pipelineName = null;
        if (pipeLine != null && (pipeLine instanceof i)) {
            pipelineName = ((i) pipeLine).f;
        }
        try {
            if (this.e != null) {
                this.e.run();
            } else {
                LoggerFactory.getTraceLogger().error((String) "AsyTskExecutor", (String) "mTask is null");
            }
        } finally {
            long end = System.currentTimeMillis();
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            r10 = "AsyTskExecutor";
            r12 = "[";
            r12 = "] start at ";
            StringBuilder append = new StringBuilder(r12).append(threadName).append(r12).append(System.currentTimeMillis());
            r12 = ", pipelineName=";
            r12 = ", cost=";
            StringBuilder append2 = append.append(r12).append(pipelineName).append(r12).append(end - start);
            r12 = " ms, task=";
            traceLogger.debug(r10, append2.append(r12).append(this.e).toString());
            if (this.f != null && this.f.length() > 0) {
                Thread.currentThread().setName(oldThreadName);
            }
            if (this.d != null) {
                this.d.a();
            }
            a.a(this);
        }
    }
}
