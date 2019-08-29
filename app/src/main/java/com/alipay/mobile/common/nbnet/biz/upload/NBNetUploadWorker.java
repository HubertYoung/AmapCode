package com.alipay.mobile.common.nbnet.biz.upload;

import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.NBNetActionController;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDo;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetCancelException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetFuseException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerLimitFlowException;
import com.alipay.mobile.common.nbnet.biz.log.FrameworkMonitorFactory;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.netlib.BasicNBNetContext;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.util.IOUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.NBNetStatusUtil;
import com.alipay.mobile.common.nbnet.biz.util.ServerLimitedFlowHelper;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NBNetUploadWorker implements NBNetActionController, Callable<NBNetUploadResponse> {
    private static ThreadPoolExecutor a;
    private NBNetUploadRequest b;
    private UploadTransport c = null;
    private BasicNBNetContext d = new BasicNBNetContext();
    private UploadActionSession e = null;
    private ContentDescription f = null;
    private UploadRetryHandler g;
    private boolean h = false;
    private boolean i = false;
    private String j;
    private long k = System.currentTimeMillis();
    private int l;
    private long m = -1;
    private long n = -1;
    private int o;
    private DeviceTrafficStateInfo p;
    private int q = 0;
    private int r = 0;

    class WriteTask implements Callable<Void> {
        UploadRequestEntity a;
        UploadTransport b;

        public WriteTask(UploadTransport uploadTransport, UploadRequestEntity uploadRequestEntity) {
            this.b = uploadTransport;
            this.a = uploadRequestEntity;
            this.a.j();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void call() {
            NBNetLogCat.a((String) "NBNetUploadWorker", (String) "WriteRunnable. execute aync write request body");
            this.b.a(this.a);
            return null;
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 15, 5, TimeUnit.SECONDS, new LinkedBlockingQueue(), NBNetCommonUtil.c("UploadThread"));
        a = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public NBNetUploadWorker(NBNetUploadRequest uploadRequest) {
        if (uploadRequest == null) {
            throw new IllegalArgumentException("NBNetUploadRequest may not be null");
        }
        this.b = uploadRequest;
        ((NBNetUploadActionController) uploadRequest.getNBNetActionController()).a(this);
        this.o = NBNetConfigUtil.p();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public NBNetUploadResponse call() {
        NBNetUploadResponse uploadResponse;
        k();
        NBNetUploadResponse uploadResponse2 = null;
        try {
            uploadResponse2 = m();
            if (uploadResponse2 != null) {
                c(uploadResponse2);
                return uploadResponse2;
            }
            do {
                uploadResponse2 = b();
            } while (uploadResponse2 == null);
            c(uploadResponse2);
            return uploadResponse2;
        } catch (Throwable th) {
            th = th;
        }
        c(uploadResponse2);
        throw th;
    }

    private NBNetUploadResponse b() {
        UploadResponseEntity responseEntity = c();
        if (responseEntity == null || responseEntity.d == null) {
            NBNetUploadResponse uploadResponse = a(responseEntity);
            this.b.getCallbackWrapper().onUploadFinished(this.b, uploadResponse);
            return uploadResponse;
        }
        if (!isStop() || responseEntity.d.getErrorCode() == -8) {
            boolean isRetry = false;
            try {
                isRetry = j().a((Throwable) responseEntity.d, a(g()));
            } catch (Throwable e2) {
                NBNetLogCat.b("NBNetUploadWorker", "[execute] retryUpload exception: " + e2.toString(), e2);
            }
            if (isRetry) {
                NBNetLogCat.b("NBNetUploadWorker", " doCall exception: ", responseEntity.d);
                this.l++;
                return null;
            }
        } else {
            responseEntity.d = new NBNetCancelException(responseEntity.d.toString());
        }
        MonitorErrorLogHelper.log("NBNetUploadWorker", responseEntity.d);
        NBNetException nbNetException = responseEntity.d;
        nbNetException.setErrorCode(NBNetStatusUtil.a(nbNetException));
        NBNetUploadResponse uploadResponse2 = a(responseEntity);
        uploadResponse2.setNbNetException(nbNetException);
        this.b.getCallbackWrapper().onUploadError(this.b, nbNetException.getErrorCode(), nbNetException.getMessage());
        return uploadResponse2;
    }

    private NBNetUploadResponse a(UploadResponseEntity responseEntity) {
        NBNetUploadResponse uploadResponse = new NBNetUploadResponse();
        uploadResponse.setFileId(g().f());
        uploadResponse.setContent(responseEntity.b);
        uploadResponse.setMd5(g().a());
        uploadResponse.setTraceId(h());
        uploadResponse.setQuic(responseEntity.e);
        uploadResponse.setLimitedSleep(responseEntity.f);
        uploadResponse.setRespHeader(responseEntity.g);
        return uploadResponse;
    }

    private UploadResponseEntity c() {
        UploadResponseEntity nbNetUploadResponseEntity;
        MonitorLogUtil.b((NBNetContext) this.d, NBNetEnvUtils.a());
        UploadResponseEntity nbNetUploadResponseEntity2 = null;
        try {
            UploadResponseEntity nbNetUploadResponseEntity3 = d();
            if (this.c == null) {
                return nbNetUploadResponseEntity3;
            }
            this.q += this.c.h();
            this.r += this.c.i();
            try {
                b(nbNetUploadResponseEntity3);
                return nbNetUploadResponseEntity3;
            } catch (Throwable th) {
                return nbNetUploadResponseEntity3;
            }
        } catch (Throwable th2) {
            return nbNetUploadResponseEntity;
        }
        if (this.c != null) {
            this.q += this.c.h();
            this.r += this.c.i();
            try {
                b(nbNetUploadResponseEntity2);
            } catch (Throwable th3) {
            }
        }
        throw th;
        throw th;
    }

    public void cancel() {
        stop();
    }

    public void stop() {
        this.i = true;
        if (this.c != null) {
            this.c.e();
        }
    }

    public boolean isStop() {
        return this.i;
    }

    private UploadResponseEntity d() {
        i();
        if (!FrameworkMonitorFactory.a().a(g().a())) {
            l();
        }
        this.c = f();
        UploadResponseEntity responseEntity = null;
        int resumeCount = 1;
        UploadActionSession uploadActionSession = a(g());
        while (uploadActionSession.a != 3) {
            i();
            if (resumeCount >= this.o + 1) {
                throw new NBNetException("Upload single task resume run count limit: " + this.o, -12);
            }
            NBNetLogCat.a((String) "NBNetUploadWorker", "uploadProcessor.  start uploadAction=" + this.e.a + ", resumeCount: " + resumeCount);
            e();
            UploadRequestEntity uploadRequestEntity = a(uploadActionSession);
            if (uploadActionSession.d) {
                this.c.j();
            }
            this.c.a();
            if (!this.h) {
                this.h = true;
                this.b.getCallbackWrapper().onUploadStart(this.b);
            }
            if (!uploadRequestEntity.a()) {
                this.c.a(uploadRequestEntity);
                return a((Future<?>) null);
            }
            long transferLength = uploadRequestEntity.f();
            NBNetLogCat.a((String) "NBNetUploadWorker", "uploadProcessor. transferLength=" + transferLength + ", sendBuffSize=" + this.c.f());
            if (transferLength <= ((long) this.c.f())) {
                this.c.a(uploadRequestEntity);
                responseEntity = a((Future<?>) null);
            } else {
                Future writeFutureTask = a.submit(new WriteTask(this.c, uploadRequestEntity));
                ((NBNetUploadActionController) this.b.getNBNetActionController()).a(writeFutureTask);
                responseEntity = a(writeFutureTask);
            }
            resumeCount++;
        }
        return responseEntity;
    }

    private void e() {
        if (this.c == null) {
            this.c = f();
        } else if (this.c.g()) {
            this.c = f();
        }
    }

    @NonNull
    private UploadTransport f() {
        UploadTransport uploadTransport = new UploadTransport();
        uploadTransport.a(this.d);
        NBNetLogCat.d("NBNetUploadWorker", "rebuilding UploadTransport");
        return uploadTransport;
    }

    @NonNull
    private UploadRequestEntity a(UploadActionSession uploadActionSession) {
        UploadRequestEntity uploadRequestEntity = new UploadRequestEntity(this.b);
        uploadRequestEntity.a(uploadActionSession);
        uploadRequestEntity.a(g());
        uploadRequestEntity.a(this.d);
        a(uploadActionSession, uploadRequestEntity);
        return uploadRequestEntity;
    }

    private void a(UploadActionSession uploadActionSession, UploadRequestEntity uploadRequestEntity) {
        if (uploadActionSession.c()) {
            uploadRequestEntity.a(a(true));
            return;
        }
        UploadRecordDo uploadRecordDo = uploadActionSession.j();
        if (uploadRecordDo != null && !TextUtils.isEmpty(uploadRecordDo.traceId)) {
            NBNetLogCat.a((String) "NBNetUploadWorker", "reuse traceId=" + uploadRecordDo.traceId);
            this.j = uploadRecordDo.traceId;
        }
        uploadRequestEntity.a(h());
    }

    private UploadResponseEntity a(Future<?> writeFutureTask) {
        try {
            return this.c.b();
        } finally {
            IOUtils.a(writeFutureTask);
        }
    }

    private ContentDescription g() {
        if (this.f != null) {
            return this.f;
        }
        synchronized (this) {
            if (this.f != null) {
                ContentDescription contentDescription = this.f;
                return contentDescription;
            }
            this.f = new ContentDescription(this.b);
            ContentDescription contentDescription2 = this.f;
            return contentDescription2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return r1.e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession a(com.alipay.mobile.common.nbnet.biz.upload.ContentDescription r2) {
        /*
            r1 = this;
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = r1.e
            if (r0 == 0) goto L_0x0007
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = r1.e
        L_0x0006:
            return r0
        L_0x0007:
            monitor-enter(r1)
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = r1.e     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0013
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = r1.e     // Catch:{ all -> 0x0010 }
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            boolean r0 = r2.c()     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0027
            java.lang.String r0 = r2.a()     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = com.alipay.mobile.common.nbnet.biz.upload.UploadActionHelper.a(r0)     // Catch:{ all -> 0x0010 }
            r1.e = r0     // Catch:{ all -> 0x0010 }
        L_0x0023:
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = r1.e
            goto L_0x0006
        L_0x0027:
            com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession r0 = com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession.a()     // Catch:{ all -> 0x0010 }
            r1.e = r0     // Catch:{ all -> 0x0010 }
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nbnet.biz.upload.NBNetUploadWorker.a(com.alipay.mobile.common.nbnet.biz.upload.ContentDescription):com.alipay.mobile.common.nbnet.biz.upload.UploadActionSession");
    }

    private void b(UploadResponseEntity nbNetUploadResponseEntity) {
        if (this.c != null) {
            if (nbNetUploadResponseEntity == null || nbNetUploadResponseEntity.d == null) {
                this.c.d();
                this.c = null;
                return;
            }
            this.c.e();
        }
    }

    private String h() {
        return a(false);
    }

    private String a(boolean reGen) {
        if (TextUtils.isEmpty(this.j) || reGen) {
            this.j = NBNetEnvUtils.i();
            NBNetLogCat.a((String) "NBNetUploadWorker", "getTraceId: " + this.j + ", reGen: " + reGen);
        }
        return this.j;
    }

    private void i() {
        if (isStop()) {
            throw new NBNetCancelException("Cancel upload.");
        }
    }

    private UploadRetryHandler j() {
        if (this.g == null) {
            this.g = new UploadRetryHandler();
        }
        return this.g;
    }

    private void a(NBNetUploadResponse uploadResponse) {
        try {
            b(uploadResponse);
        } catch (Throwable e2) {
            NBNetLogCat.d("NBNetUploadWorker", "processFinishMonitorLog exception: " + e2.toString());
        }
    }

    private void b(NBNetUploadResponse uploadResponse) {
        long allTime = System.currentTimeMillis() - this.m;
        NBNetLogCat.a((String) "NBNetUploadWorker", "upload timing: " + allTime);
        MonitorLogUtil.i((NBNetContext) this.d, allTime);
        MonitorLogUtil.i(this.d);
        MonitorLogUtil.c(this.d);
        MonitorLogUtil.h((NBNetContext) this.d, g().b());
        String bizId = this.b.getBizId();
        if (!TextUtils.isEmpty(bizId)) {
            MonitorLogUtil.h((NBNetContext) this.d, bizId);
        }
        if (!TextUtils.isEmpty(uploadResponse.getFileId())) {
            MonitorLogUtil.a((NBNetContext) this.d, uploadResponse.getFileId());
        }
        if (uploadResponse.isSuccess()) {
            MonitorLogUtil.e(this.d);
            MonitorLogUtil.a((NBNetContext) this.d, uploadResponse.isQuic());
        } else {
            MonitorLogUtil.f(this.d);
            MonitorLogUtil.a((NBNetContext) this.d, uploadResponse.getErrorCode());
            MonitorLogUtil.e((NBNetContext) this.d, uploadResponse.getErrorMsg());
        }
        if (g() != null) {
            ContentDescription contentDescription = g();
            MonitorLogUtil.b((NBNetContext) this.d, contentDescription.h());
            StringBuilder fileNameBuilder = new StringBuilder(contentDescription.h());
            if (!TextUtils.isEmpty(contentDescription.g())) {
                fileNameBuilder.append(".").append(contentDescription.g());
            }
            MonitorLogUtil.c((NBNetContext) this.d, fileNameBuilder.toString());
        }
        if (!TextUtils.isEmpty(uploadResponse.getTraceId())) {
            MonitorLogUtil.d((NBNetContext) this.d, uploadResponse.getTraceId());
        }
        if (this.l > 0) {
            MonitorLogUtil.b((NBNetContext) this.d, this.l);
        }
        MonitorLogUtil.a((NBNetContext) this.d);
    }

    private void c(NBNetUploadResponse uploadResponse) {
        if (this.p != null) {
            NetworkQoeManagerFactory.a().a(this.p);
        }
        FrameworkMonitorFactory.a().a(this.r, this.q, this.b, uploadResponse);
        a(uploadResponse);
        MonitorLogUtil.a(uploadResponse);
        UploadDBHelper.a(uploadResponse, a(g()), g());
    }

    private void k() {
        Thread.currentThread().setPriority(10);
        Process.setThreadPriority(-4);
        this.p = NetworkQoeManagerFactory.a().b();
        this.n = System.currentTimeMillis() - this.k;
        MonitorLogUtil.a((NBNetContext) this.d, this.n);
        this.m = System.currentTimeMillis();
        MonitorLogUtil.a((NBNetContext) this.d, NBNetEnvUtils.a());
        if (!NetworkUtils.isNetworkAvailable(NBNetEnvUtils.a())) {
            MonitorLogUtil.b(this.d);
        }
    }

    private boolean l() {
        StringBuilder stringBuilder = new StringBuilder("Upload trafic beyond limit. ");
        stringBuilder.append("bizId = " + this.b.getBizId());
        stringBuilder.append(", md5 = " + g().a());
        String logMark = this.b.getExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK);
        if (!TextUtils.isEmpty(logMark)) {
            stringBuilder.append(", multiLogMark = " + logMark);
        }
        throw new NBNetFuseException(stringBuilder.toString());
    }

    private NBNetUploadResponse m() {
        if (isStop()) {
            NBNetCancelException nbNetCancelException = new NBNetCancelException("upload cancel");
            NBNetUploadResponse uploadResponse = new NBNetUploadResponse();
            uploadResponse.setNbNetException(nbNetCancelException);
            this.b.getCallbackWrapper().onUploadError(this.b, nbNetCancelException.getErrorCode(), nbNetCancelException.getMessage());
            NBNetLogCat.d("NBNetUploadWorker", "[preCheck] Task it's stop.");
            return uploadResponse;
        } else if (!ServerLimitedFlowHelper.a()) {
            return null;
        } else {
            NBNetServerLimitFlowException nbNetServerLimitFlowException = ServerLimitedFlowHelper.b();
            if (nbNetServerLimitFlowException == null) {
                nbNetServerLimitFlowException = new NBNetServerLimitFlowException("Server limit flow");
            }
            NBNetUploadResponse uploadResponse2 = new NBNetUploadResponse();
            uploadResponse2.setNbNetException(nbNetServerLimitFlowException);
            this.b.getCallbackWrapper().onUploadError(this.b, nbNetServerLimitFlowException.getErrorCode(), nbNetServerLimitFlowException.getMessage());
            NBNetLogCat.d("NBNetUploadWorker", "[preCheck] Currently in the current limit state.");
            return uploadResponse2;
        }
    }
}
