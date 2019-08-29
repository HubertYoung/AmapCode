package com.alipay.mobile.common.nbnet.biz.download;

import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallbackAdapter;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.mobile.common.nbnet.biz.log.FrameworkMonitorFactory;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.task.Job;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetStatusUtil;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class DownloadJob extends Job<NBNetDownloadResponse> implements ProgressObserver {
    private static final String a = DownloadJob.class.getSimpleName();
    private final DownloadRetryPolicy b = new DownloadRetryPolicy();
    private final NBNetDownloadRequest c;
    private final NBNetDownloadCallback d;
    private final NBNetDownloadResponse e;
    private final Map<NBNetDownloadRequest, NBNetDownloadCallback> f;
    private Future<NBNetDownloadResponse> g;
    private DownloadEngine h;
    private NBNetContext i = new BasicNBNetContext();
    private long j = System.currentTimeMillis();
    private long k = -1;
    private long l = -1;
    private int m = 0;
    private boolean n = false;
    private int o;
    private DeviceTrafficStateInfo p;
    private boolean q = false;
    private boolean r = false;
    private int s;

    public DownloadJob(NBNetDownloadRequest downloadReq, NBNetDownloadCallback callback) {
        this.c = downloadReq;
        this.d = callback != null ? callback : NBNetDownloadCallbackAdapter.getDefault();
        this.f = new ConcurrentHashMap();
        this.e = new NBNetDownloadResponse();
        if (downloadReq != null && callback != null) {
            this.f.put(downloadReq, callback);
        }
    }

    public final void a(Future<NBNetDownloadResponse> downloadFuture) {
        this.g = downloadFuture;
    }

    public final Future<NBNetDownloadResponse> a(NBNetDownloadRequest downloadReq, NBNetDownloadCallback callback) {
        if (!(downloadReq == null || callback == null)) {
            this.f.put(downloadReq, callback);
        }
        NBNetLogCat.c(a, this.f.size() + ", reuseJob:" + this.c.getRequestId());
        return this.g;
    }

    public final void a(NBNetDownloadRequest downloadReq) {
        a(downloadReq, false);
    }

    public final synchronized void a(NBNetDownloadRequest downloadReq, boolean isTimeout) {
        if (!downloadReq.isCancel()) {
            downloadReq.setCancel(true);
            NBNetDownloadCallback downloadCallback = this.f.remove(downloadReq);
            if (downloadCallback != null) {
                downloadCallback.onCancled(downloadReq);
            }
            StringBuilder logStrBuilder = new StringBuilder("cancelJob. downloadReq=" + downloadReq.toString() + ", isTimeout=" + isTimeout + (isTimeout ? ", taskTimeOut=" + downloadReq.getReqTimeOut() : ""));
            int remainingReqCount = this.f.size();
            if (remainingReqCount > 0) {
                logStrBuilder.append(", remaining task count=" + remainingReqCount);
                NBNetLogCat.d(a, logStrBuilder.toString());
            } else {
                NBNetLogCat.d(a, logStrBuilder.toString());
                this.q = true;
                this.r = isTimeout;
                if (this.b != null) {
                    this.b.a(isTimeout);
                }
                if (this.h != null) {
                    this.h.c();
                }
                NBNetDownloadClientImpl.a().a(downloadReq);
                if (this.g != null && !this.g.isDone()) {
                    this.g.cancel(false);
                }
                NBNetLogCat.d(a, "cancelJob.  Task has been cancelled.");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public NBNetDownloadResponse a() {
        boolean isFinished;
        try {
            f();
            isFinished = true;
            while (this.m < 15) {
                this.m++;
                if (d()) {
                    break;
                }
            }
        } catch (Throwable th) {
            NBNetDownloadClientImpl.a().a(this.c);
            c();
            NBNetLogCat.c(a, this.c.getFileId() + ",downloadResponse:" + this.e);
            throw th;
        }
        if (isFinished) {
            a(DownloadCacheManager.a().a(this.c.getRequestId()));
        }
        NBNetDownloadClientImpl.a().a(this.c);
        c();
        NBNetLogCat.c(a, this.c.getFileId() + ",downloadResponse:" + this.e);
        return this.e;
    }

    private void c() {
        NetworkQoeManagerFactory.a().a(this.p);
        g();
        FrameworkMonitorFactory.a().a(this.h, this.c);
    }

    private boolean d() {
        Throwable throwable;
        e();
        try {
            if (this.h == null) {
                this.h = new DownloadEngine(this.c, this, this.i);
            }
            this.h.a();
            this.h.a(this.e);
            this.h.b();
            this.h.d();
            return true;
        } catch (Throwable th) {
            if (throwable != null) {
                this.h.e();
            } else {
                this.h.d();
            }
            throw th;
        }
    }

    private void e() {
        if (this.r) {
            throw new NBNetException((String) "Download time out", -23);
        } else if (this.q) {
            throw new NBNetException((String) "Download cancelled", -8);
        }
    }

    private void f() {
        NBNetLogCat.c(a, this.c.getFileId() + ",start onDownloadStart, callback count:" + this.f.size());
        Thread.currentThread().setPriority(10);
        Process.setThreadPriority(-4);
        this.c.setSessionId(NBNetEnvUtils.i());
        this.p = NetworkQoeManagerFactory.a().b();
        this.l = System.currentTimeMillis() - this.j;
        MonitorLogUtil.a(this.i, this.l);
        this.k = System.currentTimeMillis();
        if (!NetworkUtils.isNetworkAvailable(NBNetEnvUtils.a())) {
            MonitorLogUtil.b(this.i);
        }
        MonitorLogUtil.a(this.i, NBNetEnvUtils.a());
        for (NBNetDownloadRequest request : this.f.keySet()) {
            try {
                this.f.get(request).onDownloadStart(request);
            } catch (Throwable throwable) {
                NBNetLogCat.b(a, throwable);
            }
        }
        NBNetLogCat.c(a, "end onDownloadStart");
    }

    private void a(Throwable throwable) {
        int errorCode;
        MonitorErrorLogHelper.log(a, throwable);
        if (this.r) {
            errorCode = -23;
        } else if (this.q) {
            errorCode = -8;
        } else {
            errorCode = NBNetStatusUtil.a(throwable);
        }
        Throwable rootCause = MiscUtils.getRootCause(throwable);
        String msg = rootCause.toString();
        if (TextUtils.isEmpty(msg)) {
            msg = throwable.toString();
        }
        this.e.setErrorMsg(msg);
        this.e.setErrorCode(errorCode);
        this.e.setException(rootCause);
        for (Entry entry : this.f.entrySet()) {
            try {
                ((NBNetDownloadCallback) entry.getValue()).onDownloadError((NBNetDownloadRequest) entry.getKey(), this.e);
            } catch (Throwable e2) {
                NBNetLogCat.b(a, e2);
            }
        }
        NBNetLogCat.c(a, "end onDownloadFail");
    }

    /* JADX INFO: finally extract failed */
    private void a(File cacheFile) {
        NBNetLogCat.c(a, this.c.getFileId() + ",start onDownloadFinish");
        long startTime = System.currentTimeMillis();
        try {
            int size = this.f.size();
            if (size == 1) {
                a(cacheFile, this.c, this.d);
            } else if (size > 1) {
                NBNetLogCat.d(a, "batchCopyCacheFile2TargetFile.");
                b(cacheFile);
            } else {
                NBNetLogCat.d(a, "My God, actually no task.");
            }
            if (cacheFile.exists()) {
                cacheFile.delete();
            }
            long copyCost = System.currentTimeMillis() - startTime;
            MonitorLogUtil.b(this.i, copyCost);
            NBNetLogCat.a(a, "monitor_perf: copyCacheFile2TargetFile. cost_time: " + copyCost);
            DownloadCacheManager.a().b(this.c.getRequestId());
            NBNetLogCat.c(a, "end onDownloadFinish");
        } catch (Throwable th) {
            if (cacheFile.exists()) {
                cacheFile.delete();
            }
            long copyCost2 = System.currentTimeMillis() - startTime;
            MonitorLogUtil.b(this.i, copyCost2);
            NBNetLogCat.a(a, "monitor_perf: copyCacheFile2TargetFile. cost_time: " + copyCost2);
            throw th;
        }
    }

    private void b(File cacheFile) {
        Set copiedTargetPath = new HashSet();
        for (NBNetDownloadRequest request : this.f.keySet()) {
            NBNetDownloadCallback callback = this.f.get(request);
            if (copiedTargetPath.contains(request.getSavePath())) {
                callback.onDownloadFinished(request, this.e);
            } else {
                a(cacheFile, request, callback);
                copiedTargetPath.add(request.getSavePath());
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r7, com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest r8, com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback r9) {
        /*
            r6 = this;
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r3 = r8.getSavePath()     // Catch:{ Throwable -> 0x0055 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r3 = a     // Catch:{ Throwable -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x002b }
            java.lang.String r5 = " file copy to "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x002b }
            java.lang.String r5 = r2.getAbsolutePath()     // Catch:{ Throwable -> 0x002b }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x002b }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x002b }
            com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat.a(r3, r4)     // Catch:{ Throwable -> 0x002b }
            com.alipay.mobile.common.nbnet.biz.util.IOUtils.a(r7, r2)     // Catch:{ Throwable -> 0x002b }
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse r3 = r6.e     // Catch:{ Throwable -> 0x0055 }
            r9.onDownloadFinished(r8, r3)     // Catch:{ Throwable -> 0x0055 }
        L_0x0029:
            r3 = 1
        L_0x002a:
            return r3
        L_0x002b:
            r0 = move-exception
            java.lang.String r3 = a     // Catch:{ Throwable -> 0x0055 }
            com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat.b(r3, r0)     // Catch:{ Throwable -> 0x0055 }
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse r1 = new com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse     // Catch:{ Throwable -> 0x0055 }
            r1.<init>()     // Catch:{ Throwable -> 0x0055 }
            r3 = -1
            r1.setErrorCode(r3)     // Catch:{ Throwable -> 0x0055 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r4 = "copy file fail:"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r4 = r2.getAbsolutePath()     // Catch:{ Throwable -> 0x0055 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0055 }
            r1.setErrorMsg(r3)     // Catch:{ Throwable -> 0x0055 }
            r9.onDownloadError(r8, r1)     // Catch:{ Throwable -> 0x0055 }
            r3 = 0
            goto L_0x002a
        L_0x0055:
            r0 = move-exception
            java.lang.String r3 = a
            com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat.b(r3, r0)
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nbnet.biz.download.DownloadJob.a(java.io.File, com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest, com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback):boolean");
    }

    public final void a(File cacheFile, int received, int total) {
        int progress = (int) (total > 0 ? (((double) received) / ((double) total)) * 100.0d : 0.0d);
        if (this.s != progress) {
            this.s = progress;
            boolean isLogging = !this.n || (progress != this.o && progress > 95);
            this.n = true;
            if (isLogging) {
                this.o = progress;
                NBNetLogCat.c(a, this.c.getFileId() + ",start onDownloadProgress:" + progress);
            }
            for (NBNetDownloadRequest request : this.f.keySet()) {
                try {
                    this.f.get(request).onDownloadProgress(request, progress, (long) received, (long) total, cacheFile);
                } catch (Throwable e2) {
                    NBNetLogCat.b(a, e2);
                }
            }
            if (isLogging) {
                NBNetLogCat.c(a, "end onDownloadProgress");
            }
        }
    }

    private void g() {
        MonitorLogUtil.j(this.i);
        MonitorLogUtil.d(this.i);
        MonitorLogUtil.j(this.i, System.currentTimeMillis() - this.k);
        MonitorLogUtil.g(this.i);
        MonitorLogUtil.a(this.i, this.c.getFileId());
        MonitorLogUtil.b(this.i, this.h.f());
        MonitorLogUtil.h(this.i);
        if (!TextUtils.isEmpty(this.c.getBizType())) {
            MonitorLogUtil.h(this.i, this.c.getBizType());
        }
        int retryCount = this.m - 1;
        if (retryCount > 0) {
            MonitorLogUtil.b(this.i, retryCount);
        }
        MonitorLogUtil.c(this.i, this.c.getRequestId());
        if (this.e.isSuccess()) {
            MonitorLogUtil.e(this.i);
            MonitorLogUtil.k(this.i, this.e.getDataLength());
        } else {
            MonitorLogUtil.f(this.i);
            MonitorLogUtil.a(this.i, this.e.getErrorCode());
            MonitorLogUtil.e(this.i, this.e.getErrorMsg());
        }
        MonitorLogUtil.a(this.i);
    }
}
