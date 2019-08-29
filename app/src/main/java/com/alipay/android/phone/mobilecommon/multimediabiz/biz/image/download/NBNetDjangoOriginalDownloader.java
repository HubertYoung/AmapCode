package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPCmdType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPExtraData;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPSourceType;
import java.util.ArrayList;
import java.util.List;

public class NBNetDjangoOriginalDownloader implements ImageDownloader<String> {
    private static final int TYPE_ORIGINAL = 2;
    private static final Logger logger = Logger.getLogger((String) "NBNetDjangoDl");
    private boolean bCancel = false;
    private int code = -1;
    private NBNetDownloadClient downloadClient;
    private String fileId;
    private boolean hasNetwork = true;
    private ImageLoadReq loadReq;
    private NBNetDownloadCallback mCallback;
    private NBNetDownloadRequest nbReq;
    private String savePath;
    private long size;
    private long start;
    private String traceId;

    public NBNetDjangoOriginalDownloader(ImageLoadReq loadReq2, String savePath2, NBNetDownloadCallback cb) {
        this.savePath = savePath2;
        this.fileId = loadReq2.path;
        this.mCallback = cb;
        this.loadReq = loadReq2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x007e A[Catch:{ all -> 0x034c }] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x038a  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0438  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x043c  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x043f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String download(com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r24, android.os.Bundle r25) {
        /*
            r23 = this;
            android.content.Context r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getApplicationContext()
            boolean r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.isActiveNetwork(r3)
            r0 = r23
            r0.hasNetwork = r3
            r0 = r24
            r1 = r23
            r1.loadReq = r0
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest r3 = r23.createDownReq(r24)
            r0 = r23
            r0.nbReq = r3
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "NBNetDjangoOriginalDownloader download start req="
            r4.<init>(r5)
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest r5 = r0.nbReq
            java.lang.String r5 = r5.toString()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r3.d(r4, r5)
            r19 = 0
            r10 = 0
            r3 = 0
            r0 = r23
            r0.bCancel = r3
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils.getNBNetDownloadClient()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.downloadClient = r3     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient r3 = r0.downloadClient     // Catch:{ Throwable -> 0x0057 }
            if (r3 != 0) goto L_0x0153
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r4 = "downloadClient can not be null"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0057 }
            throw r3     // Catch:{ Throwable -> 0x0057 }
        L_0x0057:
            r15 = move-exception
        L_0x0058:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger     // Catch:{ all -> 0x034c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x034c }
            java.lang.String r5 = "download original image exp costTime: "
            r4.<init>(r5)     // Catch:{ all -> 0x034c }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x034c }
            r0 = r23
            long r8 = r0.start     // Catch:{ all -> 0x034c }
            long r6 = r6 - r8
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ all -> 0x034c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x034c }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x034c }
            r3.e(r15, r4, r5)     // Catch:{ all -> 0x034c }
            r0 = r23
            boolean r3 = r0.bCancel     // Catch:{ all -> 0x034c }
            if (r3 == 0) goto L_0x0438
            int r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_CANCEL     // Catch:{ all -> 0x034c }
            r0 = r23
            r0.code = r3     // Catch:{ all -> 0x034c }
            java.lang.String r10 = "NBNetDjangoOriginalDownloader canceled"
        L_0x0086:
            long r4 = java.lang.System.currentTimeMillis()
            r0 = r23
            long r6 = r0.start
            long r16 = r4 - r6
            r0 = r23
            int r3 = r0.code
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0 = r23
            long r4 = r0.size
            r0 = r16
            int r6 = (int) r0
            java.lang.String r7 = r23.getZoom(r24)
            r8 = 2
            r9 = 0
            r0 = r23
            java.lang.String r11 = r0.traceId
            r0 = r23
            java.lang.String r12 = r0.fileId
            r0 = r24
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r13 = r0.options
            java.lang.String r13 = r13.getBizType()
            r0 = r23
            int r14 = r0.code
            r0 = r23
            boolean r14 = r0.isNeedUcLog(r14)
            if (r14 != 0) goto L_0x043c
            r14 = 1
        L_0x00c2:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r3, r4, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "download end code="
            r4.<init>(r5)
            r0 = r23
            int r5 = r0.code
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";msg="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r5 = ";size="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            long r6 = r0.size
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r5 = ";fileid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.fileId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";traceid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.traceId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";savePath="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.savePath
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r3.d(r4, r5)
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.fileId
            r3.id = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.traceId
            r3.traceId = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r3.exception = r10
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            boolean r4 = r0.hasNetwork
            r3.hasNetwork = r4
        L_0x014e:
            r0 = r23
            java.lang.String r3 = r0.savePath
            return r3
        L_0x0153:
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.start = r4     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient r3 = r0.downloadClient     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest r4 = r0.nbReq     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback r5 = r0.mCallback     // Catch:{ Throwable -> 0x0057 }
            java.util.concurrent.Future r18 = r3.requestDownload(r4, r5)     // Catch:{ Throwable -> 0x0057 }
            r0 = r24
            int r3 = r0.mTimeout     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            if (r3 <= 0) goto L_0x02a2
            r0 = r24
            int r3 = r0.mTimeout     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            long r4 = (long) r3     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            r0 = r18
            java.lang.Object r3 = r0.get(r4, r3)     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            r0 = r3
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse r0 = (com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse) r0     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            r19 = r0
        L_0x0183:
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            long r6 = r0.start     // Catch:{ Throwable -> 0x0057 }
            long r16 = r4 - r6
            if (r19 == 0) goto L_0x0418
            long r4 = r19.getDataLength()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.size = r4     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r3 = r19.getTraceId()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.traceId = r3     // Catch:{ Throwable -> 0x0057 }
            r2 = 1
            boolean r3 = r19.isSuccess()     // Catch:{ Throwable -> 0x0057 }
            if (r3 == 0) goto L_0x0319
            r0 = r24
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r3 = r0.options     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r3 = r3.getMd5()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            java.lang.String r4 = r0.savePath     // Catch:{ Throwable -> 0x0057 }
            r5 = 1
            boolean r2 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFileByMd5(r3, r4, r5)     // Catch:{ Throwable -> 0x0057 }
            if (r2 == 0) goto L_0x0319
            int r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_OK     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.code = r3     // Catch:{ Throwable -> 0x0057 }
        L_0x01bf:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r4 = "download original image get response costTime: "
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0057 }
            r0 = r16
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0057 }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0057 }
            r0 = r16
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.TIME(r3, r0, r4)     // Catch:{ Throwable -> 0x0057 }
            long r4 = java.lang.System.currentTimeMillis()
            r0 = r23
            long r6 = r0.start
            long r16 = r4 - r6
            r0 = r23
            int r3 = r0.code
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0 = r23
            long r4 = r0.size
            r0 = r16
            int r6 = (int) r0
            java.lang.String r7 = r23.getZoom(r24)
            r8 = 2
            r9 = 0
            r0 = r23
            java.lang.String r11 = r0.traceId
            r0 = r23
            java.lang.String r12 = r0.fileId
            r0 = r24
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r13 = r0.options
            java.lang.String r13 = r13.getBizType()
            r0 = r23
            int r14 = r0.code
            r0 = r23
            boolean r14 = r0.isNeedUcLog(r14)
            if (r14 != 0) goto L_0x0435
            r14 = 1
        L_0x0214:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r3, r4, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "download end code="
            r4.<init>(r5)
            r0 = r23
            int r5 = r0.code
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";msg="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r5 = ";size="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            long r6 = r0.size
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r5 = ";fileid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.fileId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";traceid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.traceId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";savePath="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.savePath
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r3.d(r4, r5)
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.fileId
            r3.id = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.traceId
            r3.traceId = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r3.exception = r10
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            boolean r4 = r0.hasNetwork
            r3.hasNetwork = r4
            goto L_0x014e
        L_0x02a2:
            java.lang.Object r3 = r18.get()     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            r0 = r3
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse r0 = (com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse) r0     // Catch:{ TimeoutException -> 0x02ad, InterruptedException -> 0x02f9 }
            r19 = r0
            goto L_0x0183
        L_0x02ad:
            r21 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger     // Catch:{ Throwable -> 0x0057 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r5 = "future.get time out error: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r5 = r21.getMessage()     // Catch:{ Throwable -> 0x0057 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0057 }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0057 }
            r0 = r21
            r3.e(r0, r4, r5)     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback r3 = r0.mCallback     // Catch:{ Throwable -> 0x0057 }
            if (r3 == 0) goto L_0x0183
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse r20 = new com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse     // Catch:{ Throwable -> 0x0057 }
            r20.<init>()     // Catch:{ Throwable -> 0x0057 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r3 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            int r3 = r3.value()     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            r0 = r20
            r0.setErrorCode(r3)     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            java.lang.String r3 = "NBNetDjangoOriginalDownloader timeout "
            r0 = r20
            r0.setErrorMsg(r3)     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback r3 = r0.mCallback     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            r0 = r23
            com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest r4 = r0.nbReq     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            r0 = r20
            r3.onDownloadError(r4, r0)     // Catch:{ Throwable -> 0x0449, all -> 0x0442 }
            r19 = r20
            goto L_0x0183
        L_0x02f9:
            r15 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger     // Catch:{ Throwable -> 0x0057 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r5 = "future.get error: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r5 = r15.getMessage()     // Catch:{ Throwable -> 0x0057 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0057 }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0057 }
            r3.e(r15, r4, r5)     // Catch:{ Throwable -> 0x0057 }
            r19 = 0
            goto L_0x0183
        L_0x0319:
            if (r2 != 0) goto L_0x0329
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r3 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.MD5_FAILED     // Catch:{ Throwable -> 0x0057 }
            int r3 = r3.value()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.code = r3     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r10 = "md5 not match"
        L_0x0327:
            goto L_0x01bf
        L_0x0329:
            r3 = 429(0x1ad, float:6.01E-43)
            int r4 = r19.getErrorCode()     // Catch:{ Throwable -> 0x0057 }
            if (r3 != r4) goto L_0x033e
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r3 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CURRENT_LIMIT     // Catch:{ Throwable -> 0x0057 }
            int r3 = r3.value()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.code = r3     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r10 = "download fail for limited current"
            goto L_0x0327
        L_0x033e:
            java.lang.String r10 = r19.getErrorMsg()     // Catch:{ Throwable -> 0x0057 }
            int r3 = r19.getErrorCode()     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            r0.code = r3     // Catch:{ Throwable -> 0x0057 }
            goto L_0x01bf
        L_0x034c:
            r3 = move-exception
            r22 = r3
        L_0x034f:
            long r4 = java.lang.System.currentTimeMillis()
            r0 = r23
            long r6 = r0.start
            long r16 = r4 - r6
            r0 = r23
            int r3 = r0.code
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0 = r23
            long r4 = r0.size
            r0 = r16
            int r6 = (int) r0
            java.lang.String r7 = r23.getZoom(r24)
            r8 = 2
            r9 = 0
            r0 = r23
            java.lang.String r11 = r0.traceId
            r0 = r23
            java.lang.String r12 = r0.fileId
            r0 = r24
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r13 = r0.options
            java.lang.String r13 = r13.getBizType()
            r0 = r23
            int r14 = r0.code
            r0 = r23
            boolean r14 = r0.isNeedUcLog(r14)
            if (r14 != 0) goto L_0x043f
            r14 = 1
        L_0x038b:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r3, r4, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "download end code="
            r4.<init>(r5)
            r0 = r23
            int r5 = r0.code
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";msg="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r5 = ";size="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            long r6 = r0.size
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r5 = ";fileid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.fileId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";traceid="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.traceId
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ";savePath="
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r23
            java.lang.String r5 = r0.savePath
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r3.d(r4, r5)
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.fileId
            r3.id = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            java.lang.String r4 = r0.traceId
            r3.traceId = r4
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r3.exception = r10
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r3 = r0.loadReq
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r3 = r3.netPerf
            r0 = r23
            boolean r4 = r0.hasNetwork
            r3.hasNetwork = r4
            throw r22
        L_0x0418:
            r0 = r23
            boolean r3 = r0.bCancel     // Catch:{ Throwable -> 0x0057 }
            if (r3 == 0) goto L_0x042e
            int r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_CANCEL     // Catch:{ Throwable -> 0x0057 }
        L_0x0420:
            r0 = r23
            r0.code = r3     // Catch:{ Throwable -> 0x0057 }
            r0 = r23
            boolean r3 = r0.bCancel     // Catch:{ Throwable -> 0x0057 }
            if (r3 == 0) goto L_0x0431
            java.lang.String r10 = "NBNetDjangoOriginalDownloader canceled"
            goto L_0x0327
        L_0x042e:
            int r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ Throwable -> 0x0057 }
            goto L_0x0420
        L_0x0431:
            java.lang.String r10 = "NBNetDjangoOriginalDownloader rsp is null"
            goto L_0x0327
        L_0x0435:
            r14 = 0
            goto L_0x0214
        L_0x0438:
            java.lang.String r10 = "NBNetDjangoOriginalDownloader exception"
            goto L_0x0086
        L_0x043c:
            r14 = 0
            goto L_0x00c2
        L_0x043f:
            r14 = 0
            goto L_0x038b
        L_0x0442:
            r3 = move-exception
            r22 = r3
            r19 = r20
            goto L_0x034f
        L_0x0449:
            r15 = move-exception
            r19 = r20
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NBNetDjangoOriginalDownloader.download(com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq, android.os.Bundle):java.lang.String");
    }

    private String getZoom(ImageLoadReq req) {
        return ZoomHelper.getZoom(req);
    }

    public void cancel() {
        this.bCancel = true;
        if (this.downloadClient != null) {
            logger.d("cancel nbReq=" + this.nbReq.toString(), new Object[0]);
            this.downloadClient.cancelDownload(this.nbReq);
        }
    }

    private NBNetDownloadRequest createDownReq(ImageLoadReq req) {
        this.nbReq = new NBNetDownloadRequest();
        this.nbReq.setCmdType(MMDPCmdType.FILE_DOWNLOAD);
        this.nbReq.setFileId(req.path);
        this.nbReq.setSavePath(this.savePath);
        this.nbReq.setSourceType(MMDPSourceType.FILEID);
        this.nbReq.setBizType(req.options.getBizType());
        this.nbReq.setResType(MMDPResType.FILE);
        if (req.mTimeout > 0) {
            this.nbReq.setReqTimeOut(req.mTimeout * 1000);
        } else {
            this.nbReq.setReqTimeOut(NBNetUtils.getDownloadImageConfigTimeout());
        }
        if (req.downLoadCallback != null) {
            logger.d("add monitor log: " + req.downLoadCallback.getClass().getName(), new Object[0]);
            this.nbReq.setExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK, req.downLoadCallback.getClass().getName());
        }
        if (!TextUtils.isEmpty(this.loadReq.options.fileKey) && this.loadReq.options.bundle != null) {
            List extList = new ArrayList(2);
            if (!TextUtils.isEmpty(this.loadReq.options.bundle.getString("ssid"))) {
                MMDPExtraData data = new MMDPExtraData();
                data.name = "ssid";
                data.value = this.loadReq.options.bundle.getString("ssid");
                extList.add(data);
            }
            if (!TextUtils.isEmpty(this.loadReq.options.bundle.getString("refid"))) {
                MMDPExtraData data2 = new MMDPExtraData();
                data2.name = "refid";
                data2.value = this.loadReq.options.bundle.getString("refid");
                extList.add(data2);
            }
            if (extList.size() > 0) {
                this.nbReq.setExtList(extList);
            }
            logger.d("createDownReq bizSession=" + this.loadReq.options.bundle.getString("ssid") + ";refID=" + this.loadReq.options.bundle.getString("refid"), new Object[0]);
        }
        return this.nbReq;
    }

    private boolean isNeedUcLog(int ret) {
        return this.hasNetwork || ret == 0;
    }
}
