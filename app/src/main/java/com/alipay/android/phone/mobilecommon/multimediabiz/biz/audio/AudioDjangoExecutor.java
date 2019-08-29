package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioDownloadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.APAudioUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioUploadState;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioBenchmark;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils.AudioUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FilesDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpClientUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioDjangoExecutor {
    public static final String TRACE_ID = "TRACE_ID";
    private static AudioDjangoExecutor a;
    /* access modifiers changed from: private */
    public static final Logger b = Logger.getLogger((String) "AudioDjangoExecutor");
    /* access modifiers changed from: private */
    public Context c;
    /* access modifiers changed from: private */
    public DownloadResponseHelper d;
    private ExecutorService e = Executors.newCachedThreadPool();
    /* access modifiers changed from: private */
    public Set<String> f = new HashSet();
    private MultimediaFileService g;
    private final String h = "fromCache";
    /* access modifiers changed from: private */
    public long i;

    private class APAudioFileDownloadCallback implements APFileDownCallback {
        private APAudioInfo b;
        private APAudioDownloadCallback c;
        private long d = System.currentTimeMillis();
        public boolean hasNetwork = true;

        public APAudioFileDownloadCallback(APAudioInfo audioInfo, APAudioDownloadCallback callback) {
            this.b = audioInfo;
            this.c = callback;
        }

        public void onDownloadStart(APMultimediaTaskModel taskInfo) {
            if (this.c != null) {
                this.c.onDownloadStart(this.b);
            }
        }

        public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
        }

        public void onDownloadBatchProgress(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
        }

        public void onDownloadFinished(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
            int retCode;
            String traceId;
            String bizType;
            String cloudId;
            boolean z;
            String str;
            String str2;
            String str3;
            long j;
            int i;
            int i2;
            boolean ret = true;
            this.b.getExtra().putBoolean(AudioBenchmark.KEY_DOWNLOAD_SUCCESS, true);
            this.b.getExtra().putLong(AudioBenchmark.KEY_DOWNLOAD_TIME, System.nanoTime() - AudioDjangoExecutor.this.i);
            this.b.getExtra().putString("TRACE_ID", rsp.getTraceId());
            if (this.c != null) {
                this.b.setSavePath(rsp.getFileReq().getSavePath());
                if (AudioUtils.checkSilkAudioFile(this.b.getSavePath(), this.b)) {
                    AudioDjangoExecutor.this.d(this.b);
                    this.c.onDownloadFinished(this.b);
                } else {
                    ret = false;
                    AudioDjangoExecutor.b.e("onDownloadFinished , but the file is error silk file!!" + this.b, new Object[0]);
                    FileUtils.deleteFileByPath(this.b.getSavePath());
                    this.b.setSavePath("");
                    rsp.setRetCode(-1);
                    rsp.setMsg("Download finished, but the file is error silk file!!");
                    onDownloadError(taskInfo, rsp);
                }
            }
            long size = 0;
            try {
                size = new File(rsp.getFileReq().getSavePath()).length();
            } catch (Exception e) {
                AudioDjangoExecutor.b.e(e, "", new Object[0]);
            }
            int time = (int) (System.currentTimeMillis() - this.d);
            if (ret) {
                retCode = 0;
                traceId = rsp.getTraceId();
                bizType = this.b.getBizType();
                cloudId = this.b.getCloudId();
            } else {
                retCode = rsp.getRetCode();
                traceId = rsp.getTraceId();
                bizType = this.b.getBizType();
                cloudId = this.b.getCloudId();
                if (!this.hasNetwork) {
                    z = true;
                    str = cloudId;
                    str2 = bizType;
                    str3 = traceId;
                    j = size;
                    i = retCode;
                    i2 = time;
                    UCLogUtil.UC_MM_C05(i, j, i2, str3, str2, str, z);
                }
            }
            z = false;
            str = cloudId;
            str2 = bizType;
            str3 = traceId;
            j = size;
            i = retCode;
            i2 = time;
            UCLogUtil.UC_MM_C05(i, j, i2, str3, str2, str, z);
        }

        public void onDownloadError(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
            boolean z = false;
            this.b.getExtra().putBoolean(AudioBenchmark.KEY_DOWNLOAD_SUCCESS, false);
            this.b.getExtra().putString("TRACE_ID", rsp.getTraceId());
            this.b.getExtra().putBoolean(AudioBenchmark.KEY_HAS_NETWORK, this.hasNetwork);
            AudioBenchmark.reportDownloading(this.b);
            if (this.c != null) {
                APAudioDownloadRsp downloadRsp = new APAudioDownloadRsp();
                downloadRsp.setMsg(rsp.getMsg());
                downloadRsp.setRetCode(rsp.getRetCode());
                this.c.onDownloadError(this.b, downloadRsp);
            }
            long size = 0;
            try {
                size = new File(rsp.getFileReq().getSavePath()).length();
            } catch (Exception e) {
                AudioDjangoExecutor.b.e(e, "", new Object[0]);
            }
            int retCode = rsp.getRetCode();
            int currentTimeMillis = (int) (System.currentTimeMillis() - this.d);
            String traceId = rsp.getTraceId();
            String bizType = this.b.getBizType();
            String cloudId = this.b.getCloudId();
            if (!this.hasNetwork) {
                z = true;
            }
            UCLogUtil.UC_MM_C05(retCode, size, currentTimeMillis, traceId, bizType, cloudId, z);
        }
    }

    private class DownloadTask implements Runnable {
        private APAudioInfo b;
        private APRequestParam c;
        private APAudioDownloadCallback d;

        public DownloadTask(APAudioInfo info, APRequestParam param, APAudioDownloadCallback listener) {
            this.b = info;
            this.c = param;
            this.d = listener;
        }

        public void run() {
            if (AudioDjangoExecutor.this.fromCache(this.b)) {
                if (this.d != null) {
                    this.b.getExtra().putBoolean("notifyDownloadFinished", false);
                    this.b.getExtra().putBoolean("fromCache", true);
                    this.d.onDownloadFinished(this.b);
                }
                if (!PathUtils.checkIsResourcePreDownload(this.b.businessId)) {
                    CacheHitManager.getInstance().audioCacheHit();
                }
                AudioDjangoExecutor.this.f.remove(this.b.getCloudId());
                return;
            }
            AudioDjangoExecutor.this.i = System.nanoTime();
            if (AudioDjangoExecutor.e()) {
                if (this.d != null) {
                    this.b.getExtra().putBoolean("fromCache", false);
                    APAudioDownloadRsp audioRsp = new APAudioDownloadRsp();
                    audioRsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                    audioRsp.setRetCode(2000);
                    this.d.onDownloadError(this.b, audioRsp);
                }
                AudioDjangoExecutor.this.f.remove(this.b.getCloudId());
                return;
            }
            FilesDownReq filesDownReq = new FilesDownReq(this.b.getCloudId());
            filesDownReq.setbHttps(this.b.isHttps());
            filesDownReq.mTimeout = this.b.getTimeout();
            AudioDjangoExecutor.b.d("DownloadTask req: " + filesDownReq + ";audioInfo: " + this.b, new Object[0]);
            if (this.d != null) {
                this.d.onDownloadStart(this.b);
            }
            FilesDownResp rsp = AudioDjangoExecutor.this.a(this.c).downloadBatch(filesDownReq);
            APAudioDownloadRsp audioDownloadRsp = new APAudioDownloadRsp();
            AudioDjangoExecutor.b.d("DownloadTask rsp: " + rsp, new Object[0]);
            if (rsp != null) {
                this.b.getExtra().putString("TRACE_ID", rsp.getTraceId());
                if (rsp.isSuccess()) {
                    String output = CacheContext.get().getDiskCache().genPathByKey(this.b.getCloudId());
                    AudioDjangoExecutor.b.d("DownloadTask cache cloud file path: " + output, new Object[0]);
                    try {
                        if (!HttpClientUtils.checkRspContentSizeAndType(rsp.getResp())) {
                            audioDownloadRsp.setRetCode(1);
                            audioDownloadRsp.setMsg("content size and type not match");
                            if (this.d != null) {
                                this.d.onDownloadError(this.b, audioDownloadRsp);
                            }
                            AudioDjangoExecutor.b.d("checkRspContentSizeAndType fail id=" + filesDownReq.getFileIds() + ";biz=" + this.b.businessId, new Object[0]);
                            return;
                        }
                        AudioDjangoExecutor.this.d.writeSingleFile(rsp.getResp().getEntity().getContent(), new FileOutputStream(output));
                        this.b.setSavePath(output);
                        boolean check = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFileByMd5(this.b.getMd5(), output, true);
                        if (this.d != null) {
                            if (check) {
                                this.d.onDownloadFinished(this.b);
                            } else {
                                audioDownloadRsp.setRetCode(1);
                                audioDownloadRsp.setMsg("md5 not match");
                                this.d.onDownloadError(this.b, audioDownloadRsp);
                            }
                        }
                    } catch (Exception e) {
                        AudioDjangoExecutor.b.e(e, "DownloadTask", new Object[0]);
                        audioDownloadRsp.setMsg(e.getMessage());
                        AudioDjangoExecutor.b(1, DiskExpUtils.parseException(e), this.b.getExtra().getBoolean("fromCache") ? -1 : 0, this.b.getCloudId(), this.b.businessId, rsp.getMsg(), this.b.isHttps());
                        if (this.d != null) {
                            this.d.onDownloadError(this.b, audioDownloadRsp);
                        }
                    }
                } else {
                    audioDownloadRsp.setRetCode(rsp.getCode());
                    audioDownloadRsp.setMsg(rsp.getMsg());
                    if (this.d != null) {
                        this.d.onDownloadError(this.b, audioDownloadRsp);
                    }
                }
            } else if (this.d != null) {
                audioDownloadRsp.setRetCode(1);
                audioDownloadRsp.setMsg("No FilesDownResp");
                this.d.onDownloadError(this.b, audioDownloadRsp);
            }
            AudioDjangoExecutor.this.f.remove(this.b.getCloudId());
        }
    }

    public interface UploadIntervalListener {
        void onUploadError(APAudioInfo aPAudioInfo, FileUpResp fileUpResp);

        boolean onUploadFinished(APAudioInfo aPAudioInfo);

        void onUploadProgress(APAudioInfo aPAudioInfo, long j);
    }

    public class UploadIntervalTask implements Runnable {
        private final AtomicBoolean b = new AtomicBoolean(false);
        /* access modifiers changed from: private */
        public APAudioInfo c;
        private APRequestParam d;
        /* access modifiers changed from: private */
        public UploadIntervalListener e;
        private PipedInputStream f;
        private OutputStream g;
        private AtomicBoolean h = new AtomicBoolean(false);
        private AtomicBoolean i = new AtomicBoolean(false);

        public UploadIntervalTask(APAudioInfo mAudioInfo, APRequestParam mReqParam, UploadIntervalListener mListener) {
            this.c = mAudioInfo;
            this.d = mReqParam;
            this.e = mListener;
            a();
        }

        public void notifyStop() {
            AudioDjangoExecutor.b.d("notifyStop, " + this.c + ", hasClosed: " + this.i, new Object[0]);
            if (!this.i.get()) {
                try {
                    if (this.b.get()) {
                        IOUtils.closeQuietly((InputStream) this.f);
                    } else if (!this.h.get()) {
                        this.g.flush();
                    }
                    if (!this.h.get()) {
                        IOUtils.closeQuietly(this.g);
                    }
                } catch (IOException e2) {
                    this.h.set(true);
                    if (!this.h.get()) {
                        IOUtils.closeQuietly(this.g);
                    }
                } catch (Throwable th) {
                    if (!this.h.get()) {
                        IOUtils.closeQuietly(this.g);
                    }
                    throw th;
                }
            }
        }

        public void cancel() {
            AudioDjangoExecutor.b.d("cancel, " + this.c + ", USE_DELAY_MIN_RECORD_TIME: true", new Object[0]);
            AudioDjangoExecutor.b.p("cancel", new Object[0]);
            this.b.set(true);
            synchronized (this.b) {
                this.b.notifyAll();
            }
        }

        public OutputStream getTaskOutput() {
            return this.g;
        }

        private void a() {
            try {
                this.f = new PipedInputStream();
                PipedOutputStream pipedOutputStream = new PipedOutputStream();
                pipedOutputStream.connect(this.f);
                this.g = new BufferedOutputStream(pipedOutputStream, 4192);
                AudioDjangoExecutor.b.d("preparedUpStream, " + this.c, new Object[0]);
            } catch (Exception e2) {
                this.h.set(true);
            }
        }

        public void copyToCacheWhileSuccess() {
            AudioDjangoExecutor.b.d("copyToCacheWhileSuccess, " + this.c, new Object[0]);
            AudioDjangoExecutor.b(this.c);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:49:0x01eb, code lost:
            r17 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x01ec, code lost:
            r7 = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x032a, code lost:
            r3 = th;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x032a A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0027] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r24 = this;
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "UploadIntervalTask start, "
                r4.<init>(r5)
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r5 = r0.c
                java.lang.StringBuilder r4 = r4.append(r5)
                java.lang.String r4 = r4.toString()
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]
                r3.d(r4, r5)
                r0 = r24
                java.io.PipedInputStream r3 = r0.f
                if (r3 == 0) goto L_0x01d6
                r20 = -1
                r18 = 1
                java.util.WeakHashMap r21 = new java.util.WeakHashMap     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r3 = 1
                r0 = r21
                r0.<init>(r3)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.InputStreamUpReq r2 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.InputStreamUpReq     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.io.PipedInputStream r3 = r0.f     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r4.<init>()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r5 = r0.c     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = r5.getLocalId()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = ".amr"
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalTask$1 r5 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalTask$1     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                r1 = r21
                r5.<init>(r1)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r6 = -1
                r2.<init>(r3, r4, r5, r6)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r3 = "InputStreamUpReq"
                r0 = r21
                r0.put(r3, r2)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r3 = ".amr"
                r2.setExt(r3)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r3 = 1
                r2.setSkipRapid(r3)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r3 = "uploadType"
                java.lang.String r4 = "1"
                r5 = 1
                r2.addExtra(r3, r4, r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.this     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam r4 = r0.d     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi r16 = r3.a(r4)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r4 = r0.b     // Catch:{ Exception -> 0x01da, all -> 0x032a }
                monitor-enter(r4)     // Catch:{ Exception -> 0x01da, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.b     // Catch:{ all -> 0x01d7 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r5 = r0.c     // Catch:{ all -> 0x01d7 }
                int r5 = r5.getRecordMinTime()     // Catch:{ all -> 0x01d7 }
                int r5 = r5 + 200
                long r8 = (long) r5     // Catch:{ all -> 0x01d7 }
                r3.wait(r8)     // Catch:{ all -> 0x01d7 }
                monitor-exit(r4)     // Catch:{ all -> 0x01d7 }
            L_0x009a:
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.this     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                android.content.Context r3 = r3.c     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                boolean r18 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.isActiveNetwork(r3)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r4 = r0.h     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                if (r18 != 0) goto L_0x0292
                r3 = 1
            L_0x00ad:
                r4.set(r3)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                long r22 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = "UploadIntervalTask start upload, "
                r4.<init>(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r5 = r0.c     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = ", req: "
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = ", hasError: "
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r5 = r0.h     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r5 = ", cancel: "
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r5 = r0.b     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r3.d(r4, r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.h     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                if (r3 != 0) goto L_0x0295
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.b     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                if (r3 != 0) goto L_0x0295
                r0 = r16
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp r19 = r0.uploadDirect(r2)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
            L_0x0111:
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                long r4 = r4 - r22
                int r7 = (int) r4
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b     // Catch:{ Exception -> 0x0309 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0309 }
                java.lang.String r5 = "UploadIntervalTask rsp: "
                r4.<init>(r5)     // Catch:{ Exception -> 0x0309 }
                r0 = r19
                java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0309 }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0309 }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0309 }
                r3.d(r4, r5)     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                r0 = r19
                a(r0, r3)     // Catch:{ Exception -> 0x0309 }
                if (r19 == 0) goto L_0x014f
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                android.os.Bundle r3 = r3.getExtra()     // Catch:{ Exception -> 0x0309 }
                java.lang.String r4 = "TRACE_ID"
                java.lang.String r5 = r19.getTraceId()     // Catch:{ Exception -> 0x0309 }
                r3.putString(r4, r5)     // Catch:{ Exception -> 0x0309 }
            L_0x014f:
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalListener r3 = r0.e     // Catch:{ Exception -> 0x0309 }
                if (r3 == 0) goto L_0x01c7
                if (r19 == 0) goto L_0x0299
                boolean r3 = r19.isSuccess()     // Catch:{ Exception -> 0x0309 }
                if (r3 == 0) goto L_0x0299
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.h     // Catch:{ Exception -> 0x0309 }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x0309 }
                if (r3 != 0) goto L_0x0299
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp r4 = r19.getFileInfo()     // Catch:{ Exception -> 0x0309 }
                java.lang.String r4 = r4.getId()     // Catch:{ Exception -> 0x0309 }
                r3.setCloudId(r4)     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp r4 = r19.getFileInfo()     // Catch:{ Exception -> 0x0309 }
                java.lang.String r4 = r4.getMd5()     // Catch:{ Exception -> 0x0309 }
                r3.setFileMD5(r4)     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.b     // Catch:{ Exception -> 0x0309 }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x0309 }
                if (r3 != 0) goto L_0x01bc
                r4 = 0
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp r3 = r19.getFileInfo()     // Catch:{ Exception -> 0x0309 }
                long r5 = r3.getSize()     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                int r3 = r3.getDuration()     // Catch:{ Exception -> 0x0309 }
                long r8 = (long) r3     // Catch:{ Exception -> 0x0309 }
                java.lang.String r10 = r19.getTraceId()     // Catch:{ Exception -> 0x0309 }
                java.lang.String r11 = ""
                r12 = 1
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                java.lang.String r13 = r3.getBizType()     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                java.lang.String r14 = r3.getCloudId()     // Catch:{ Exception -> 0x0309 }
                r15 = 0
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C02(r4, r5, r7, r8, r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0309 }
            L_0x01bc:
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalListener r3 = r0.e     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r4 = r0.c     // Catch:{ Exception -> 0x0309 }
                r3.onUploadFinished(r4)     // Catch:{ Exception -> 0x0309 }
            L_0x01c7:
                r0 = r24
                java.io.PipedInputStream r3 = r0.f
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r3)
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.i
                r4 = 1
                r3.set(r4)
            L_0x01d6:
                return
            L_0x01d7:
                r3 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x01d7 }
                throw r3     // Catch:{ Exception -> 0x01da, all -> 0x032a }
            L_0x01da:
                r17 = move-exception
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                java.lang.String r4 = "UploadIntervalTask.run"
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                r0 = r17
                r3.e(r0, r4, r5)     // Catch:{ Exception -> 0x01eb, all -> 0x032a }
                goto L_0x009a
            L_0x01eb:
                r17 = move-exception
                r7 = r20
            L_0x01ee:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b     // Catch:{ all -> 0x0314 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0314 }
                java.lang.String r5 = "UploadIntervalTask, "
                r4.<init>(r5)     // Catch:{ all -> 0x0314 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r5 = r0.c     // Catch:{ all -> 0x0314 }
                java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0314 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0314 }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0314 }
                r0 = r17
                r3.e(r0, r4, r5)     // Catch:{ all -> 0x0314 }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.b     // Catch:{ all -> 0x0314 }
                boolean r3 = r3.get()     // Catch:{ all -> 0x0314 }
                if (r3 != 0) goto L_0x023a
                int r4 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ all -> 0x0314 }
                r5 = -1
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ all -> 0x0314 }
                int r3 = r3.getDuration()     // Catch:{ all -> 0x0314 }
                long r8 = (long) r3     // Catch:{ all -> 0x0314 }
                java.lang.String r10 = ""
                java.lang.String r11 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.getExceptionMsg(r17)     // Catch:{ all -> 0x0314 }
                r12 = 1
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ all -> 0x0314 }
                java.lang.String r13 = r3.getBizType()     // Catch:{ all -> 0x0314 }
                r14 = 0
                if (r18 != 0) goto L_0x0327
                r15 = 1
            L_0x0237:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C02(r4, r5, r7, r8, r10, r11, r12, r13, r14, r15)     // Catch:{ all -> 0x0314 }
            L_0x023a:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp r19 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp     // Catch:{ all -> 0x0314 }
                r19.<init>()     // Catch:{ all -> 0x0314 }
                r3 = 100
                r0 = r19
                r0.setCode(r3)     // Catch:{ all -> 0x0314 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0314 }
                r3.<init>()     // Catch:{ all -> 0x0314 }
                java.lang.Class r4 = r17.getClass()     // Catch:{ all -> 0x0314 }
                java.lang.String r4 = r4.getSimpleName()     // Catch:{ all -> 0x0314 }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0314 }
                java.lang.String r4 = ":"
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0314 }
                java.lang.String r4 = r17.getMessage()     // Catch:{ all -> 0x0314 }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0314 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0314 }
                r0 = r19
                r0.setMsg(r3)     // Catch:{ all -> 0x0314 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalListener r3 = r0.e     // Catch:{ all -> 0x0314 }
                if (r3 == 0) goto L_0x0281
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalListener r3 = r0.e     // Catch:{ all -> 0x0314 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r4 = r0.c     // Catch:{ all -> 0x0314 }
                r0 = r19
                r3.onUploadError(r4, r0)     // Catch:{ all -> 0x0314 }
            L_0x0281:
                r0 = r24
                java.io.PipedInputStream r3 = r0.f
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r3)
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.i
                r4 = 1
                r3.set(r4)
                goto L_0x01d6
            L_0x0292:
                r3 = 0
                goto L_0x00ad
            L_0x0295:
                r19 = 0
                goto L_0x0111
            L_0x0299:
                if (r19 != 0) goto L_0x02b8
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp r19 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp     // Catch:{ Exception -> 0x0309 }
                r19.<init>()     // Catch:{ Exception -> 0x0309 }
                r3 = 100
                r0 = r19
                r0.setCode(r3)     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r3 = r0.b     // Catch:{ Exception -> 0x0309 }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x0309 }
                if (r3 == 0) goto L_0x030c
                java.lang.String r3 = "less than min record time, cancel upload!!"
                r0 = r19
                r0.setMsg(r3)     // Catch:{ Exception -> 0x0309 }
            L_0x02b8:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.b     // Catch:{ Exception -> 0x0309 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0309 }
                java.lang.String r5 = "uploadError rsp: "
                r4.<init>(r5)     // Catch:{ Exception -> 0x0309 }
                r0 = r19
                java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0309 }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0309 }
                r5 = 0
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0309 }
                r3.e(r4, r5)     // Catch:{ Exception -> 0x0309 }
                int r4 = r19.getCode()     // Catch:{ Exception -> 0x0309 }
                r5 = -1
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                int r3 = r3.getDuration()     // Catch:{ Exception -> 0x0309 }
                long r8 = (long) r3     // Catch:{ Exception -> 0x0309 }
                java.lang.String r10 = r19.getTraceId()     // Catch:{ Exception -> 0x0309 }
                java.lang.String r11 = r19.getMsg()     // Catch:{ Exception -> 0x0309 }
                r12 = 1
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r3 = r0.c     // Catch:{ Exception -> 0x0309 }
                java.lang.String r13 = r3.getBizType()     // Catch:{ Exception -> 0x0309 }
                r14 = 0
                if (r18 != 0) goto L_0x0325
                r15 = 1
            L_0x02f7:
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C02(r4, r5, r7, r8, r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor$UploadIntervalListener r3 = r0.e     // Catch:{ Exception -> 0x0309 }
                r0 = r24
                com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo r4 = r0.c     // Catch:{ Exception -> 0x0309 }
                r0 = r19
                r3.onUploadError(r4, r0)     // Catch:{ Exception -> 0x0309 }
                goto L_0x01c7
            L_0x0309:
                r17 = move-exception
                goto L_0x01ee
            L_0x030c:
                java.lang.String r3 = "network is not active!!"
                r0 = r19
                r0.setMsg(r3)     // Catch:{ Exception -> 0x0309 }
                goto L_0x02b8
            L_0x0314:
                r3 = move-exception
            L_0x0315:
                r0 = r24
                java.io.PipedInputStream r4 = r0.f
                com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r4)
                r0 = r24
                java.util.concurrent.atomic.AtomicBoolean r4 = r0.i
                r5 = 1
                r4.set(r5)
                throw r3
            L_0x0325:
                r15 = 0
                goto L_0x02f7
            L_0x0327:
                r15 = 0
                goto L_0x0237
            L_0x032a:
                r3 = move-exception
                r7 = r20
                goto L_0x0315
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioDjangoExecutor.UploadIntervalTask.run():void");
        }

        private static void a(FileUpResp rsp, APAudioInfo audioInfo) {
            if (ConfigManager.getInstance().isCheckAudioSyncMd5() && rsp != null && rsp.isSuccess() && audioInfo != null) {
                String upMd5 = null;
                String localMd5 = null;
                try {
                    upMd5 = rsp.getFileInfo().getMd5();
                    localMd5 = MD5Util.getFileMD5String(new File(CacheContext.get().getDiskCache().genPathByKey(audioInfo.getLocalId())));
                } catch (Exception e2) {
                    AudioDjangoExecutor.b.d("checkMd5 error, e: " + e2, new Object[0]);
                }
                if (!TextUtils.isEmpty(upMd5) && !TextUtils.isEmpty(localMd5) && !localMd5.equals(upMd5)) {
                    AudioDjangoExecutor.b.e("upload md5 does not match local md5, up md5: " + upMd5 + ", local md5: " + localMd5, new Object[0]);
                    throw new IllegalStateException("upload md5 does not match local md5, up md5: " + upMd5 + ", local md5: " + localMd5);
                }
            }
        }
    }

    private class UploadTask implements Runnable {
        private APAudioInfo b;
        private APRequestParam c;
        private APAudioUploadCallback d;

        public UploadTask(APAudioUploadCallback callback, APAudioInfo info, APRequestParam param) {
            this.d = callback;
            this.b = info;
            this.c = param;
        }

        public void run() {
            if (this.d != null) {
                this.d.onUploadStart(this.b);
            }
            APAudioUploadRsp rsp = AudioDjangoExecutor.this.a(this.b, this.c);
            if (rsp.getRetCode() == 0) {
                this.d.onUploadFinished(rsp);
            } else {
                this.d.onUploadError(rsp);
            }
        }
    }

    private AudioDjangoExecutor(Context context) {
        this.c = context;
        this.g = AppUtils.getFileService();
        c();
    }

    private void c() {
        this.d = new DownloadResponseHelper();
    }

    public static AudioDjangoExecutor getInstance(Context context) {
        if (a == null) {
            synchronized (AudioDjangoExecutor.class) {
                if (a == null) {
                    a = new AudioDjangoExecutor(context);
                }
            }
        }
        return a;
    }

    public void uploadDirect(APAudioInfo info, APRequestParam param, APAudioUploadCallback cb) {
        this.e.execute(new UploadTask(cb, info, param));
    }

    public UploadIntervalTask uploadAudioInterval(APAudioInfo info, APRequestParam param, UploadIntervalListener listener) {
        UploadIntervalTask task = new UploadIntervalTask(info, param, listener);
        this.e.execute(task);
        return task;
    }

    public APMultimediaTaskModel download(APAudioInfo info, APRequestParam param, APAudioDownloadCallback listener) {
        if (fromCache(info)) {
            if (listener != null) {
                info.getExtra().putBoolean("notifyDownloadFinished", false);
                listener.onDownloadFinished(info);
            }
            if (!PathUtils.checkIsResourcePreDownload(info.businessId)) {
                CacheHitManager.getInstance().audioCacheHit();
            }
            return null;
        }
        this.i = System.nanoTime();
        String savePath = AudioFileManager.getInstance(this.c).generateSavePath(info.getCloudId());
        APAudioFileDownloadCallback callback = new APAudioFileDownloadCallback(info, listener);
        APFileReq req = new APFileReq();
        req.setCloudId(info.getCloudId());
        req.setIsNeedCache(false);
        req.setCallGroup(1002);
        req.setPriority(info.getPriority());
        req.setMd5(info.getMd5());
        req.setHttps(info.isHttps());
        req.setBusinessId(info.getBusinessId());
        req.setBizType(info.getBizType());
        req.setTimeout(info.getTimeout());
        if (!TextUtils.isEmpty(savePath)) {
            req.setSavePath(savePath);
        }
        callback.hasNetwork = CommonUtils.isActiveNetwork(this.c);
        return d().downLoad(req, (APFileDownCallback) callback, info.businessId);
    }

    private MultimediaFileService d() {
        if (this.g == null) {
            synchronized (AudioDjangoExecutor.class) {
                if (this.g == null) {
                    this.g = AppUtils.getFileService();
                }
            }
        }
        return this.g;
    }

    public APAudioDownloadRsp download(APAudioInfo info, APRequestParam param) {
        final long start = System.currentTimeMillis();
        final APAudioDownloadRsp rsp = new APAudioDownloadRsp();
        final boolean hasNetwork = CommonUtils.isActiveNetwork(this.c);
        new DownloadTask(info, param, new APAudioDownloadCallback() {
            public void onDownloadStart(APAudioInfo info) {
            }

            public void onDownloadFinished(APAudioInfo info) {
                long j;
                long j2;
                info.getExtra().putBoolean(AudioBenchmark.KEY_DOWNLOAD_SUCCESS, true);
                info.getExtra().putLong(AudioBenchmark.KEY_DOWNLOAD_TIME, System.nanoTime() - AudioDjangoExecutor.this.i);
                rsp.setRetCode(0);
                rsp.setAudioInfo(info);
                boolean bLocal = info.getExtra().getBoolean("fromCache");
                long fileLength = new File(info.getSavePath()).length();
                AudioDjangoExecutor.this.d(info);
                int time = (int) (System.currentTimeMillis() - start);
                if (bLocal) {
                    j = -1;
                } else {
                    j = fileLength;
                }
                UCLogUtil.UC_MM_C05(0, j, time, info.getExtra().getString("TRACE_ID"), info.getBizType(), info.getCloudId(), false);
                if (bLocal) {
                    j2 = -1;
                } else {
                    j2 = fileLength;
                }
                AudioDjangoExecutor.b(0, -1, j2, info.getCloudId(), info.businessId, "", info.isHttps());
            }

            public void onDownloadError(APAudioInfo info, APAudioDownloadRsp rsp) {
                boolean z = false;
                info.getExtra().putBoolean(AudioBenchmark.KEY_DOWNLOAD_SUCCESS, false);
                info.getExtra().putBoolean(AudioBenchmark.KEY_HAS_NETWORK, hasNetwork);
                AudioBenchmark.reportDownloading(info);
                rsp.setAudioInfo(info);
                rsp.setRetCode(429 == rsp.getRetCode() ? 2000 : rsp.getRetCode());
                boolean bLocal = info.getExtra().getBoolean("fromCache");
                int retCode = rsp.getRetCode();
                long j = bLocal ? -1 : 0;
                int currentTimeMillis = (int) (System.currentTimeMillis() - start);
                String string = info.getExtra().getString("TRACE_ID");
                String bizType = info.getBizType();
                String cloudId = info.getCloudId();
                if (!hasNetwork) {
                    z = true;
                }
                UCLogUtil.UC_MM_C05(retCode, j, currentTimeMillis, string, bizType, cloudId, z);
            }
        }).run();
        return rsp;
    }

    public void cancelDownload(APAudioInfo info) {
        APMultimediaTaskModel task = d().getUpTaskStatusByCloudId(info.getCloudId());
        if (task != null) {
            d().cancelLoad(task.getTaskId());
        }
    }

    /* access modifiers changed from: private */
    public FileApi a(APRequestParam param) {
        ConnectionManager conMgr = new HttpConnectionManager();
        if (param != null) {
            conMgr.setAcl(param.getACL());
            conMgr.setUid(param.getUID());
        }
        return new HttpDjangoClient(this.c, conMgr).getFileApi();
    }

    /* access modifiers changed from: private */
    public static void b(APAudioInfo info) {
        b.d("copyToCache():" + info, new Object[0]);
        FileCacheModel model = CacheContext.get().getDiskCache().get(info.getLocalId());
        if (model != null) {
            CacheContext.get().getDiskCache().update(info.getLocalId(), info.getCloudId(), model.tag & -17);
        }
    }

    private static void c(APAudioInfo info) {
        String path = CacheContext.get().getDiskCache().getPath(info.getLocalId());
        if (!TextUtils.isEmpty(path)) {
            info.setSavePath(path);
        }
    }

    public APAudioUploadRsp uploadDirectSync(APAudioInfo info, APRequestParam param) {
        return a(info, param);
    }

    /* access modifiers changed from: private */
    public APAudioUploadRsp a(APAudioInfo audioInfo, APRequestParam param) {
        boolean z;
        b.d("uploadSync info: " + audioInfo + ", param: " + param, new Object[0]);
        long start = System.currentTimeMillis();
        c(audioInfo);
        APFileReq req = new APFileReq();
        long size = 0;
        try {
            size = new File(audioInfo.getSavePath()).length();
            req.setSavePath(audioInfo.getSavePath());
            if (!TextUtils.isEmpty(audioInfo.getSavePath())) {
                req.setAliasFileName(new File(audioInfo.getSavePath()).getName() + ".amr");
            }
            req.setIsNeedCache(false);
            req.setCallGroup(1002);
            req.setBizType(audioInfo.getBizType());
            req.setTimeout(audioInfo.getTimeout());
        } catch (Exception e2) {
            b.d("uploadSync get size exp: " + e2.toString(), new Object[0]);
        }
        APFileUploadRsp uploadRsp = new APFileUploadRsp();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        boolean hasNetwork = CommonUtils.isActiveNetwork(this.c);
        final CountDownLatch countDownLatch2 = countDownLatch;
        final APFileUploadRsp aPFileUploadRsp = uploadRsp;
        d().upLoad(req, (APFileUploadCallback) new APFileUploadCallback() {
            public void onUploadStart(APMultimediaTaskModel taskInfo) {
            }

            public void onUploadProgress(APMultimediaTaskModel taskInfo, int progress, long hasUploadSize, long total) {
            }

            public void onUploadFinished(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
                a(rsp);
                countDownLatch2.countDown();
            }

            private void a(APFileUploadRsp rsp) {
                aPFileUploadRsp.setFileReq(rsp.getFileReq());
                aPFileUploadRsp.setRetCode(rsp.getRetCode());
                aPFileUploadRsp.setMsg(rsp.getMsg());
            }

            public void onUploadError(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
                a(rsp);
                countDownLatch2.countDown();
            }
        }, audioInfo.businessId);
        int timeout = audioInfo.getTimeout();
        boolean ret = false;
        if (timeout > 0) {
            try {
                ret = countDownLatch.await((long) timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e3) {
                b.e(e3, "uploadSync", new Object[0]);
                return a(audioInfo, e3.getMessage(), timeout > 0 && 0 == 0);
            }
        } else {
            countDownLatch.await();
        }
        if (timeout <= 0 || ret) {
            String traceId = null;
            try {
                traceId = uploadRsp.getTraceId();
                APAudioUploadRsp cbRsp = new APAudioUploadRsp();
                cbRsp.setAudioInfo(audioInfo);
                cbRsp.setRetCode(uploadRsp.getRetCode());
                cbRsp.setMsg(uploadRsp.getMsg());
                if (uploadRsp.getRetCode() == 0) {
                    audioInfo.setCloudId(uploadRsp.getFileReq().getCloudId());
                    b(audioInfo);
                    audioInfo.setUploadState(new APAudioUploadState(0));
                } else if (2000 == uploadRsp.getRetCode()) {
                    cbRsp.extCode = cbRsp.getRetCode();
                    cbRsp.extMsg = cbRsp.getMsg();
                    audioInfo.setUploadState(new APAudioUploadState(1));
                    cbRsp.setRetCode(2000);
                } else {
                    cbRsp.extCode = cbRsp.getRetCode();
                    cbRsp.extMsg = cbRsp.getMsg();
                    audioInfo.setUploadState(new APAudioUploadState(1));
                    cbRsp.setRetCode(100);
                }
                int code = uploadRsp.getRetCode();
                UCLogUtil.UC_MM_C02(code, size, (int) (System.currentTimeMillis() - start), (long) audioInfo.getDuration(), traceId, uploadRsp.getMsg(), audioInfo.getBizType(), audioInfo.getCloudId(), !hasNetwork && code != 0);
                cbRsp.recordState = 0;
                b.d("uploadSync APAudioUploadRsp: " + cbRsp, new Object[0]);
                return cbRsp;
            } catch (Throwable th) {
                Throwable th2 = th;
                int code2 = uploadRsp.getRetCode();
                UCLogUtil.UC_MM_C02(code2, size, (int) (System.currentTimeMillis() - start), (long) audioInfo.getDuration(), traceId, uploadRsp.getMsg(), audioInfo.getBizType(), audioInfo.getCloudId(), !hasNetwork && code2 != 0);
                throw th2;
            }
        } else {
            String str = "uploadSync timeout for " + timeout + " s";
            if (timeout <= 0 || ret) {
                z = false;
            } else {
                z = true;
            }
            return a(audioInfo, str, z);
        }
    }

    private static APAudioUploadRsp a(APAudioInfo audioInfo, String msg, boolean bTimeout) {
        APAudioUploadRsp cbRsp = new APAudioUploadRsp();
        cbRsp.setAudioInfo(audioInfo);
        if (bTimeout) {
            cbRsp.setRetCode(102);
        } else {
            cbRsp.setRetCode(101);
        }
        cbRsp.setMsg(msg);
        b.d("makeInterruptUploadRsp APAudioUploadRsp=" + cbRsp + ";audioInfo=" + audioInfo, new Object[0]);
        return cbRsp;
    }

    public boolean fromCache(APAudioInfo info) {
        String path;
        long ts = System.nanoTime();
        if (com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFile(info.getCloudId())) {
            path = info.getCloudId();
        } else {
            path = CacheContext.get().getDiskCache().genPathByKey(info.getCloudId());
        }
        boolean b2 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFile(path);
        info.getExtra().putLong("cache_loading_time", System.nanoTime() - ts);
        info.getExtra().putInt(AudioBenchmark.KEY_USE_CACHE, b2 ? 2 : 0);
        AudioBenchmark.reportCacheLoading(info);
        if (b2) {
            info.setSavePath(path);
        }
        return b2;
    }

    /* access modifiers changed from: private */
    public static boolean e() {
        return ConfigManager.getInstance().getIntValue(ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, 0) >= 3;
    }

    /* access modifiers changed from: private */
    public void d(final APAudioInfo info) {
        b.d("insertRecord cloudId:" + info.getCloudId(), new Object[0]);
        if (SimpleConfigProvider.get().getIOConfig().isEnableAsyncSaveData()) {
            TaskScheduleManager.get().commonExecutor().execute(new Runnable() {
                public void run() {
                    CacheContext.get().getDiskCache().save(info.getCloudId(), 3, 1024, info.businessId, info.getExpiredTime());
                }
            });
        } else {
            CacheContext.get().getDiskCache().save(info.getCloudId(), 3, 1024, info.businessId, info.getExpiredTime());
        }
        info.getExtra().putLong("file_size", new File(info.getSavePath()).length());
        AudioBenchmark.reportDownloading(info);
    }

    /* access modifiers changed from: private */
    public static void b(int ret, int expceode, long size, String id, String biz, String exp, boolean bHttps) {
        if (ret == 0 || expceode > 0) {
            UCLogUtil.UC_MM_C47(ret == 0 ? "0" : String.valueOf(expceode), size, 0, id, "ad", biz, "1", exp, "", bHttps ? "1" : "0", false);
        }
    }
}
