package com.alipay.android.phone.inside.framework.service;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.perf.PerfLogger;

public class ServiceExecutor {
    /* access modifiers changed from: private */
    public static final ServiceExecutorProxy a = new ServiceExecutorProxy();

    public static <Params> void a(final String str, final Params params) {
        new Thread(new Runnable() {
            public final void run() {
                PerfLogger c;
                String str;
                StringBuilder sb;
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    ServiceExecutor.a;
                    ServiceExecutorProxy.a(str, params);
                    c = LoggerFactory.c();
                    str = "framework";
                    sb = new StringBuilder("StartServiceTime|");
                } catch (Throwable th) {
                    PerfLogger c2 = LoggerFactory.c();
                    StringBuilder sb2 = new StringBuilder("StartServiceTime|");
                    sb2.append(str);
                    c2.a("framework", sb2.toString(), Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                    throw th;
                }
                sb.append(str);
                c.a(str, sb.toString(), Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            }
        }).start();
    }

    public static <Params, Result> void a(final String str, final Params params, final IInsideServiceCallback<Result> iInsideServiceCallback) {
        new Thread(new Runnable() {
            public final void run() {
                try {
                    ServiceExecutor.a;
                    ServiceExecutorProxy.a(str, params, System.currentTimeMillis());
                } catch (Throwable th) {
                    if (iInsideServiceCallback != null) {
                        iInsideServiceCallback.onException(th);
                    }
                    LoggerFactory.e().a((String) "framework", (String) "StartServiceWithCallbackEx", th);
                }
            }
        }).start();
    }

    public static <Params, Result> Result b(String str, Params params) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            Result b = ServiceExecutorProxy.b(str, params);
            return b;
        } finally {
            r3 = "framework";
            r4 = "StartServiceForResultTime|";
            LoggerFactory.c().a(r3, r4.concat(String.valueOf(str)), Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
    }
}
