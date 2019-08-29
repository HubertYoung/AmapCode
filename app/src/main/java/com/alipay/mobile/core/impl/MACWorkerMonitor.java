package com.alipay.mobile.core.impl;

import android.text.TextUtils;
import android.util.Printer;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.quinox.asynctask.AsyncTaskExecutor;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.ThreadDumpUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MACWorkerMonitor implements Printer {
    private static long a = 5;
    /* access modifiers changed from: private */
    public static long b = 3;
    /* access modifiers changed from: private */
    public WeakReference<Thread> c = null;
    /* access modifiers changed from: private */
    public String d = null;
    private ScheduledFuture<?> e = null;
    public String processingAppId = "Unknown";

    public class MacWorkerMonitorRunnable implements Runnable {
        private String a;
        private long b = System.currentTimeMillis();

        public MacWorkerMonitorRunnable(String message) {
            this.a = message;
        }

        public void run() {
            if (!TextUtils.isEmpty(this.a) && this.a.equals(MACWorkerMonitor.this.d)) {
                String lastStackTrace = MACWorkerMonitor.this.getStackTraceString(MACWorkerMonitor.this.c);
                if (!TextUtils.isEmpty(lastStackTrace)) {
                    int waitCount = 0;
                    do {
                        try {
                            Thread.sleep(TimeUnit.SECONDS.toMillis(MACWorkerMonitor.b));
                        } catch (Throwable th) {
                        }
                        if (this.a.equals(MACWorkerMonitor.this.d)) {
                            String currentStackTrace = MACWorkerMonitor.this.getStackTraceString(MACWorkerMonitor.this.c);
                            if (TextUtils.isEmpty(currentStackTrace)) {
                                return;
                            }
                            if (lastStackTrace.equals(currentStackTrace)) {
                                waitCount++;
                            } else {
                                lastStackTrace = currentStackTrace;
                                waitCount = 0;
                            }
                        } else {
                            return;
                        }
                    } while (waitCount < 3);
                    if (TextUtils.isEmpty(lastStackTrace) || !lastStackTrace.contains("sycnStartGestureIfNecessary") || !lastStackTrace.contains("onCallAround") || !lastStackTrace.contains("wait")) {
                        MACWorkerMonitor.this.handleFrameWorkerPending(System.currentTimeMillis() - this.b, waitCount, lastStackTrace);
                    } else {
                        TraceLogger.i((String) "MACWorkerMonitor", (String) "MACMonitor gesture is up, no report.");
                    }
                }
            }
        }
    }

    public void setWorkerThread(Thread thread) {
        this.c = new WeakReference<>(thread);
    }

    public void println(String x) {
        if (x != null) {
            if (x.startsWith(">>>>>")) {
                this.d = x;
                this.e = AsyncTaskExecutor.getInstance().schedule(new MacWorkerMonitorRunnable(this.d), this.d, a, TimeUnit.SECONDS);
            }
            if (x.startsWith("<<<<<")) {
                this.d = null;
                if (this.e != null) {
                    this.e.cancel(true);
                    this.e = null;
                }
            }
        }
    }

    public void handleFrameWorkerPending(long waitTime, int waitCount, String stack) {
        Map params = new HashMap();
        params.put("waitTime", String.valueOf(waitTime));
        params.put("waitCount", String.valueOf(waitCount));
        if (!TextUtils.isEmpty(stack)) {
            params.put("stackFrame", stack);
        }
        if (!TextUtils.isEmpty(this.processingAppId)) {
            params.put("processingAppId", this.processingAppId);
        }
        String threadsTrace = ThreadDumpUtil.getThreadsStackTrace();
        if (!TextUtils.isEmpty(threadsTrace)) {
            params.put("threadsTrace", threadsTrace);
        }
        TraceLogger.w((String) "MACWorkerMonitor", "handleFrameWorkerPending waitTime:" + waitTime + " waitCount:" + waitCount + " stack:" + stack);
        MonitorLogger.mtBizReport(MTBizReportName.MTBIZ_FRAME, "FRAME_MAC_WORKER_STUCK", "1000", params);
        ThreadDumpUtil.logAllThreadsTraces();
        if (TextUtils.isEmpty(this.processingAppId)) {
            this.processingAppId = "Unknown";
        }
        FrameworkMonitor.getInstance(null).handleMicroAppStartupFail(this.processingAppId, FrameworkMonitor.MICROAPP_STARTUP_FAIL_MAC_STUCK);
    }

    public String getStackTraceString(WeakReference<Thread> threadReference) {
        if (threadReference != null) {
            Thread thread = (Thread) threadReference.get();
            if (thread != null) {
                StackTraceElement[] ste = thread.getStackTrace();
                if (ste != null && ste.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (StackTraceElement steItem : ste) {
                        if (steItem != null) {
                            sb.append("\tat ");
                            sb.append(steItem.toString());
                            sb.append(10);
                        }
                    }
                    return sb.toString();
                }
            }
        }
        return null;
    }
}
