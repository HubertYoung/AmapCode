package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.framework.service.common.DownloadService;
import java.util.concurrent.Future;

public class NetDownloader implements ImageDownloader<ThumbnailsDownResp>, TransportCallback {
    private static final Logger logger = Logger.getLogger((String) "NetDownloader");
    private String bizType;
    private DownloadService downloadService;
    private boolean hasNetwork = true;
    private ImageLoadReq loadReq;
    private boolean mCancelled = false;
    private Future mDownloadFuture;
    private String path;
    private long size;
    private long timeout = 0;
    private TransportCallback transportCallback;

    public NetDownloader(ImageLoadReq req, String savePath, TransportCallback transportCallback2) {
        this.loadReq = req;
        this.path = savePath;
        this.transportCallback = transportCallback2;
        if (req.mTimeout > 0) {
            this.timeout = (long) (req.mTimeout * 1000);
        } else {
            this.timeout = (long) ConfigManager.getInstance().getCommonConfigItem().net.dsImageDownloadTimeOut;
        }
    }

    /* JADX WARNING: type inference failed for: r40v0 */
    /* JADX WARNING: type inference failed for: r0v107, types: [com.alipay.mobile.common.transport.Response] */
    /* JADX WARNING: type inference failed for: r40v5 */
    /* JADX WARNING: type inference failed for: r0v110, types: [com.alipay.mobile.common.transport.Response] */
    /* JADX WARNING: type inference failed for: r40v6 */
    /* JADX WARNING: type inference failed for: r40v7 */
    /* JADX WARNING: type inference failed for: r40v8 */
    /* JADX WARNING: type inference failed for: r40v9 */
    /* JADX WARNING: type inference failed for: r0v125, types: [com.alipay.mobile.common.transport.Response] */
    /* JADX WARNING: type inference failed for: r40v10 */
    /* JADX WARNING: type inference failed for: r0v128, types: [com.alipay.mobile.common.transport.Response] */
    /* JADX WARNING: type inference failed for: r40v11 */
    /* JADX WARNING: type inference failed for: r40v12 */
    /* JADX WARNING: type inference failed for: r40v13 */
    /* JADX WARNING: type inference failed for: r40v14 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp download(com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r49, android.os.Bundle r50) {
        /*
            r48 = this;
            android.content.Context r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getApplicationContext()
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.isActiveNetwork(r5)
            r0 = r48
            r0.hasNetwork = r5
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp r39 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp
            r39.<init>()
            r0 = r49
            java.lang.String r13 = r0.path
            r32 = 0
            java.lang.String r38 = r48.getImageMdnUrl(r49)
            r4 = 0
            boolean r5 = android.text.TextUtils.isEmpty(r38)
            if (r5 != 0) goto L_0x002a
            boolean r4 = r48.isMdnWay()
            if (r4 == 0) goto L_0x002a
            r13 = r38
        L_0x002a:
            java.lang.String r34 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils.extractDomain(r13)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager.getInstance()
            r0 = r34
            java.lang.String r44 = r5.getConvergeTargetDomain(r0)
            boolean r5 = android.text.TextUtils.isEmpty(r44)
            if (r5 != 0) goto L_0x0046
            r0 = r34
            r1 = r44
            java.lang.String r32 = r13.replace(r0, r1)
        L_0x0046:
            r40 = 0
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r5 = r0.options
            java.lang.String r5 = r5.getBizType()
            r0 = r48
            r0.bizType = r5
            r37 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r0 = r48
            java.lang.String r6 = r0.path
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "."
            java.lang.StringBuilder r5 = r5.append(r6)
            long r6 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r46 = r5.toString()
            java.io.File r45 = new java.io.File
            r45.<init>(r46)
            r42 = 0
            r21 = -1
            r29 = 0
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils.isBlank(r13)     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x0198
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException r5 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = "url can not be null"
            r5.<init>(r6)     // Catch:{ Exception -> 0x008e }
            throw r5     // Catch:{ Exception -> 0x008e }
        L_0x008e:
            r36 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger     // Catch:{ all -> 0x01ae }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ae }
            java.lang.String r7 = "download error: "
            r6.<init>(r7)     // Catch:{ all -> 0x01ae }
            java.lang.String r7 = r36.getMessage()     // Catch:{ all -> 0x01ae }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x01ae }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x01ae }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x01ae }
            r0 = r36
            r5.e(r0, r6, r7)     // Catch:{ all -> 0x01ae }
            r0 = r48
            boolean r5 = r0.mCancelled     // Catch:{ all -> 0x01ae }
            if (r5 == 0) goto L_0x0636
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_CANCEL     // Catch:{ all -> 0x01ae }
            r0 = r39
            r0.setCode(r5)     // Catch:{ all -> 0x01ae }
        L_0x00b9:
            java.lang.String r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.getExceptionMsg(r36)     // Catch:{ all -> 0x01ae }
            r0 = r39
            r0.setMsg(r5)     // Catch:{ all -> 0x01ae }
            int r21 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils.parseException(r36)     // Catch:{ all -> 0x01ae }
            r5 = 429(0x1ad, float:6.01E-43)
            r0 = r21
            if (r5 != r0) goto L_0x00de
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r5 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CURRENT_LIMIT     // Catch:{ all -> 0x01ae }
            int r5 = r5.value()     // Catch:{ all -> 0x01ae }
            r0 = r39
            r0.setCode(r5)     // Catch:{ all -> 0x01ae }
            java.lang.String r5 = "download fail for limited current"
            r0 = r39
            r0.setMsg(r5)     // Catch:{ all -> 0x01ae }
        L_0x00de:
            if (r29 == 0) goto L_0x063f
            r33 = 1
        L_0x00e2:
            long r6 = java.lang.System.currentTimeMillis()
            long r30 = r6 - r42
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "downloadImage finally delete tmpFile: "
            r6.<init>(r7)
            r0 = r45
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", deleted? "
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r33
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", size:"
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r48
            long r8 = r0.size
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r5.d(r6, r7)
            int r5 = r39.getCode()
            r0 = r48
            boolean r5 = r0.isNeedUcLog(r5)
            if (r5 != 0) goto L_0x0645
            r17 = 1
        L_0x0129:
            int r5 = r39.getCode()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r0 = r48
            long r6 = r0.size
            r0 = r30
            int r8 = (int) r0
            java.lang.String r9 = "url"
            r10 = 2
            r11 = 0
            java.lang.String r12 = r39.getMsg()
            r14 = 0
            r15 = 0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r16 = r0
            boolean r18 = r48.isMdnWay()
            if (r18 == 0) goto L_0x0649
            java.lang.String r18 = "3"
        L_0x0150:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            int r20 = r39.getCode()
            r0 = r48
            long r0 = r0.size
            r22 = r0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r25 = r0
            java.lang.String r26 = r39.getMsg()
            boolean r28 = r48.isMdnWay()
            r19 = r48
            r24 = r13
            r27 = r17
            r19.UC_MM_47(r20, r21, r22, r24, r25, r26, r27, r28)
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r0.netPerf
            r0 = r48
            boolean r6 = r0.hasNetwork
            r5.hasNetwork = r6
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "NetDownloader costTime "
            r5.<init>(r6)
            r0 = r30
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r0 = r30
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.TIME(r5, r0, r6)
        L_0x0197:
            return r39
        L_0x0198:
            com.alipay.mobile.framework.service.common.DownloadService r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.getDownloadService()     // Catch:{ Exception -> 0x008e }
            r0 = r48
            r0.downloadService = r5     // Catch:{ Exception -> 0x008e }
            r0 = r48
            com.alipay.mobile.framework.service.common.DownloadService r5 = r0.downloadService     // Catch:{ Exception -> 0x008e }
            if (r5 != 0) goto L_0x026b
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = "DownloadService can not be null"
            r5.<init>(r6)     // Catch:{ Exception -> 0x008e }
            throw r5     // Catch:{ Exception -> 0x008e }
        L_0x01ae:
            r5 = move-exception
            r47 = r5
            if (r29 == 0) goto L_0x064d
            r33 = 1
        L_0x01b5:
            long r6 = java.lang.System.currentTimeMillis()
            long r30 = r6 - r42
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "downloadImage finally delete tmpFile: "
            r6.<init>(r7)
            r0 = r45
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", deleted? "
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r33
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", size:"
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r48
            long r8 = r0.size
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r5.d(r6, r7)
            int r5 = r39.getCode()
            r0 = r48
            boolean r5 = r0.isNeedUcLog(r5)
            if (r5 != 0) goto L_0x0653
            r17 = 1
        L_0x01fc:
            int r5 = r39.getCode()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r0 = r48
            long r6 = r0.size
            r0 = r30
            int r8 = (int) r0
            java.lang.String r9 = "url"
            r10 = 2
            r11 = 0
            java.lang.String r12 = r39.getMsg()
            r14 = 0
            r15 = 0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r16 = r0
            boolean r18 = r48.isMdnWay()
            if (r18 == 0) goto L_0x0657
            java.lang.String r18 = "3"
        L_0x0223:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            int r20 = r39.getCode()
            r0 = r48
            long r0 = r0.size
            r22 = r0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r25 = r0
            java.lang.String r26 = r39.getMsg()
            boolean r28 = r48.isMdnWay()
            r19 = r48
            r24 = r13
            r27 = r17
            r19.UC_MM_47(r20, r21, r22, r24, r25, r26, r27, r28)
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r0.netPerf
            r0 = r48
            boolean r6 = r0.hasNetwork
            r5.hasNetwork = r6
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "NetDownloader costTime "
            r5.<init>(r6)
            r0 = r30
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r0 = r30
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.TIME(r5, r0, r6)
            throw r47
        L_0x026b:
            long r42 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x008e }
            boolean r5 = android.text.TextUtils.isEmpty(r32)     // Catch:{ Exception -> 0x008e }
            if (r5 != 0) goto L_0x02e5
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "converge domain.download with convergeUrl="
            r6.<init>(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r32
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ",originalUrl="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r13)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ";timeout="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            long r8 = r0.timeout     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008e }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x008e }
            r5.d(r6, r7)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r5 = r0.loadReq     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r6 = r5.netPerf     // Catch:{ Exception -> 0x008e }
            boolean r5 = r48.isMdnWay()     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x04ad
            r5 = 3
        L_0x02b3:
            r6.downloadType = r5     // Catch:{ Exception -> 0x008e }
            r5 = 0
            r0 = r48
            r1 = r32
            r2 = r46
            com.alipay.mobile.common.transport.download.DownloadRequest r35 = r0.createDownloadRequest(r1, r2, r5)     // Catch:{ Throwable -> 0x04d5 }
            r0 = r48
            com.alipay.mobile.framework.service.common.DownloadService r5 = r0.downloadService     // Catch:{ Throwable -> 0x04d5 }
            r0 = r35
            java.util.concurrent.Future r5 = r5.addDownload(r0)     // Catch:{ Throwable -> 0x04d5 }
            r0 = r48
            r0.mDownloadFuture = r5     // Catch:{ Throwable -> 0x04d5 }
            r0 = r48
            long r6 = r0.timeout     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r8 = 0
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 > 0) goto L_0x04b0
            r0 = r48
            java.util.concurrent.Future r5 = r0.mDownloadFuture     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            java.lang.Object r5 = r5.get()     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r0 = r5
            com.alipay.mobile.common.transport.Response r0 = (com.alipay.mobile.common.transport.Response) r0     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r40 = r0
        L_0x02e5:
            if (r40 == 0) goto L_0x04de
            r0 = r40
            com.alipay.mobile.common.transport.http.HttpUrlResponse r0 = (com.alipay.mobile.common.transport.http.HttpUrlResponse) r0     // Catch:{ Exception -> 0x008e }
            r37 = r0
            r0 = r48
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r5 = r0.loadReq     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r5.netPerf     // Catch:{ Exception -> 0x008e }
            int r6 = r37.getCode()     // Catch:{ Exception -> 0x008e }
            r5.convergeResultCode = r6     // Catch:{ Exception -> 0x008e }
        L_0x02f9:
            if (r40 == 0) goto L_0x0305
            int r5 = r37.getCode()     // Catch:{ Exception -> 0x008e }
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.checkDownloadServiceHttpCode(r5)     // Catch:{ Exception -> 0x008e }
            if (r5 != 0) goto L_0x0338
        L_0x0305:
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils.isHttp(r13)     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x0338
            r0 = r48
            r1 = r46
            com.alipay.mobile.common.transport.download.DownloadRequest r35 = r0.createDownloadRequest(r13, r1, r4)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            com.alipay.mobile.framework.service.common.DownloadService r5 = r0.downloadService     // Catch:{ Exception -> 0x008e }
            r0 = r35
            java.util.concurrent.Future r5 = r5.addDownload(r0)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            r0.mDownloadFuture = r5     // Catch:{ Exception -> 0x008e }
            r0 = r48
            long r6 = r0.timeout     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r8 = 0
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 > 0) goto L_0x04f4
            r0 = r48
            java.util.concurrent.Future r5 = r0.mDownloadFuture     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            java.lang.Object r5 = r5.get()     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r0 = r5
            com.alipay.mobile.common.transport.Response r0 = (com.alipay.mobile.common.transport.Response) r0     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r40 = r0
        L_0x0338:
            if (r40 == 0) goto L_0x0581
            r0 = r40
            com.alipay.mobile.common.transport.http.HttpUrlResponse r0 = (com.alipay.mobile.common.transport.http.HttpUrlResponse) r0     // Catch:{ Exception -> 0x008e }
            r37 = r0
            java.io.File r41 = new java.io.File     // Catch:{ Exception -> 0x008e }
            r0 = r48
            java.lang.String r5 = r0.path     // Catch:{ Exception -> 0x008e }
            r0 = r41
            r0.<init>(r5)     // Catch:{ Exception -> 0x008e }
            long r6 = r45.length()     // Catch:{ Exception -> 0x008e }
            r0 = r48
            r0.size = r6     // Catch:{ Exception -> 0x008e }
            int r5 = r37.getCode()     // Catch:{ Exception -> 0x008e }
            boolean r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.checkDownloadServiceHttpCode(r5)     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x0522
            boolean r5 = r41.exists()     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x0392
            boolean r5 = r41.isFile()     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x0392
            boolean r33 = r41.delete()     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "downloadImage success, delete exists file: "
            r6.<init>(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r41
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", deleted: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r33
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008e }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x008e }
            r5.d(r6, r7)     // Catch:{ Exception -> 0x008e }
        L_0x0392:
            r0 = r45
            r1 = r41
            boolean r29 = r0.renameTo(r1)     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "saveFile source:"
            r6.<init>(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r13)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", dst: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r41
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", len: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            long r8 = r41.length()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", ret? "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r29
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008e }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x008e }
            r5.d(r6, r7)     // Catch:{ Exception -> 0x008e }
            if (r29 == 0) goto L_0x0519
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_OK     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            java.lang.String r5 = r0.path     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setSavePath(r5)     // Catch:{ Exception -> 0x008e }
        L_0x03e9:
            byte[] r5 = r40.getResData()     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setData(r5)     // Catch:{ Exception -> 0x008e }
        L_0x03f2:
            if (r29 == 0) goto L_0x0628
            r33 = 1
        L_0x03f6:
            long r6 = java.lang.System.currentTimeMillis()
            long r30 = r6 - r42
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "downloadImage finally delete tmpFile: "
            r6.<init>(r7)
            r0 = r45
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", deleted? "
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r33
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r7 = ", size:"
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r48
            long r8 = r0.size
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r5.d(r6, r7)
            int r5 = r39.getCode()
            r0 = r48
            boolean r5 = r0.isNeedUcLog(r5)
            if (r5 != 0) goto L_0x062e
            r17 = 1
        L_0x043d:
            int r5 = r39.getCode()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r0 = r48
            long r6 = r0.size
            r0 = r30
            int r8 = (int) r0
            java.lang.String r9 = "url"
            r10 = 2
            r11 = 0
            java.lang.String r12 = r39.getMsg()
            r14 = 0
            r15 = 0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r16 = r0
            boolean r18 = r48.isMdnWay()
            if (r18 == 0) goto L_0x0632
            java.lang.String r18 = "3"
        L_0x0464:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            int r20 = r39.getCode()
            r0 = r48
            long r0 = r0.size
            r22 = r0
            r0 = r48
            java.lang.String r0 = r0.bizType
            r25 = r0
            java.lang.String r26 = r39.getMsg()
            boolean r28 = r48.isMdnWay()
            r19 = r48
            r24 = r13
            r27 = r17
            r19.UC_MM_47(r20, r21, r22, r24, r25, r26, r27, r28)
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r0.netPerf
            r0 = r48
            boolean r6 = r0.hasNetwork
            r5.hasNetwork = r6
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "NetDownloader costTime "
            r5.<init>(r6)
            r0 = r30
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r0 = r30
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.TIME(r5, r0, r6)
            goto L_0x0197
        L_0x04ad:
            r5 = 1
            goto L_0x02b3
        L_0x04b0:
            r0 = r48
            java.util.concurrent.Future r5 = r0.mDownloadFuture     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r0 = r48
            long r6 = r0.timeout     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            java.lang.Object r5 = r5.get(r6, r8)     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r0 = r5
            com.alipay.mobile.common.transport.Response r0 = (com.alipay.mobile.common.transport.Response) r0     // Catch:{ InterruptedException -> 0x04c5, TimeoutException -> 0x04ca }
            r40 = r0
            goto L_0x02e5
        L_0x04c5:
            r5 = move-exception
            r40 = 0
            goto L_0x02e5
        L_0x04ca:
            r5 = move-exception
            r40 = 0
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r5 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Throwable -> 0x04d5 }
            int r21 = r5.value()     // Catch:{ Throwable -> 0x04d5 }
            goto L_0x02e5
        L_0x04d5:
            r36 = move-exception
            r40 = 0
            int r21 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils.parseException(r36)     // Catch:{ Exception -> 0x008e }
            goto L_0x02e5
        L_0x04de:
            r0 = r48
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r5 = r0.loadReq     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r5.netPerf     // Catch:{ Exception -> 0x008e }
            int r5 = r5.downloadType     // Catch:{ Exception -> 0x008e }
            r6 = 1
            if (r5 != r6) goto L_0x02f9
            r0 = r48
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r5 = r0.loadReq     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r5 = r5.netPerf     // Catch:{ Exception -> 0x008e }
            r6 = -1
            r5.convergeResultCode = r6     // Catch:{ Exception -> 0x008e }
            goto L_0x02f9
        L_0x04f4:
            r0 = r48
            java.util.concurrent.Future r5 = r0.mDownloadFuture     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r0 = r48
            long r6 = r0.timeout     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            java.lang.Object r5 = r5.get(r6, r8)     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r0 = r5
            com.alipay.mobile.common.transport.Response r0 = (com.alipay.mobile.common.transport.Response) r0     // Catch:{ InterruptedException -> 0x0509, TimeoutException -> 0x050e }
            r40 = r0
            goto L_0x0338
        L_0x0509:
            r5 = move-exception
            r40 = 0
            goto L_0x0338
        L_0x050e:
            r5 = move-exception
            r40 = 0
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r5 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Exception -> 0x008e }
            int r21 = r5.value()     // Catch:{ Exception -> 0x008e }
            goto L_0x0338
        L_0x0519:
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            goto L_0x03e9
        L_0x0522:
            int r5 = r37.getCode()     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = "Http invoker error :"
            r5.<init>(r6)     // Catch:{ Exception -> 0x008e }
            int r6 = r39.getCode()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setMsg(r5)     // Catch:{ Exception -> 0x008e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r6 = logger     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "download err, path: "
            r5.<init>(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r49
            java.lang.String r7 = r0.path     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", code: "
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x008e }
            int r7 = r39.getCode()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", msg: "
            java.lang.StringBuilder r7 = r5.append(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r40
            com.alipay.mobile.common.transport.http.HttpUrlResponse r0 = (com.alipay.mobile.common.transport.http.HttpUrlResponse) r0     // Catch:{ Exception -> 0x008e }
            r5 = r0
            java.lang.String r5 = r5.getMsg()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = r7.append(r5)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x008e }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x008e }
            r6.e(r5, r7)     // Catch:{ Exception -> 0x008e }
            goto L_0x03f2
        L_0x0581:
            r0 = r48
            boolean r5 = r0.mCancelled     // Catch:{ Exception -> 0x008e }
            if (r5 == 0) goto L_0x05ce
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_CANCEL     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = "download cancelled~~"
            r0 = r39
            r0.setMsg(r5)     // Catch:{ Exception -> 0x008e }
        L_0x0595:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r5 = logger     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = "download err, path: "
            r6.<init>(r7)     // Catch:{ Exception -> 0x008e }
            r0 = r49
            java.lang.String r7 = r0.path     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", code: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            int r7 = r39.getCode()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = ", msg: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r7 = r39.getMsg()     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x008e }
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x008e }
            r5.e(r6, r7)     // Catch:{ Exception -> 0x008e }
            goto L_0x03f2
        L_0x05ce:
            r5 = 429(0x1ad, float:6.01E-43)
            r0 = r21
            if (r5 != r0) goto L_0x05e7
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r5 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CURRENT_LIMIT     // Catch:{ Exception -> 0x008e }
            int r5 = r5.value()     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = "download fail for limited current"
            r0 = r39
            r0.setMsg(r5)     // Catch:{ Exception -> 0x008e }
            goto L_0x0595
        L_0x05e7:
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r5 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Exception -> 0x008e }
            int r5 = r5.value()     // Catch:{ Exception -> 0x008e }
            r0 = r21
            if (r5 != r0) goto L_0x0618
            r0 = r39
            r1 = r21
            r0.setCode(r1)     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = "timeout exp after "
            r5.<init>(r6)     // Catch:{ Exception -> 0x008e }
            r0 = r48
            long r6 = r0.timeout     // Catch:{ Exception -> 0x008e }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x008e }
            java.lang.String r6 = "ms"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setMsg(r5)     // Catch:{ Exception -> 0x008e }
            goto L_0x0595
        L_0x0618:
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ Exception -> 0x008e }
            r0 = r39
            r0.setCode(r5)     // Catch:{ Exception -> 0x008e }
            java.lang.String r5 = "httpManager execute return null"
            r0 = r39
            r0.setMsg(r5)     // Catch:{ Exception -> 0x008e }
            goto L_0x0595
        L_0x0628:
            boolean r33 = r45.delete()
            goto L_0x03f6
        L_0x062e:
            r17 = 0
            goto L_0x043d
        L_0x0632:
            java.lang.String r18 = ""
            goto L_0x0464
        L_0x0636:
            int r5 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ all -> 0x01ae }
            r0 = r39
            r0.setCode(r5)     // Catch:{ all -> 0x01ae }
            goto L_0x00b9
        L_0x063f:
            boolean r33 = r45.delete()
            goto L_0x00e2
        L_0x0645:
            r17 = 0
            goto L_0x0129
        L_0x0649:
            java.lang.String r18 = ""
            goto L_0x0150
        L_0x064d:
            boolean r33 = r45.delete()
            goto L_0x01b5
        L_0x0653:
            r17 = 0
            goto L_0x01fc
        L_0x0657:
            java.lang.String r18 = ""
            goto L_0x0223
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NetDownloader.download(com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq, android.os.Bundle):com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp");
    }

    private DownloadRequest createDownloadRequest(String url, String tmpPath, boolean bMdn) {
        DownloadRequest downloadReq = new DownloadRequest(url, tmpPath, null, null);
        downloadReq.setTransportCallback(this);
        downloadReq.addTags("bizId", this.bizType);
        if (bMdn) {
            logger.p("createDownloadRequest url=" + url + ";bMdn=" + bMdn, new Object[0]);
            downloadReq.addTags(TransportConstants.KEY_TARGET_SPI, TransportConstants.VALUE_TARGET_SPI);
            downloadReq.addTags(TransportConstants.KEY_OPERATION_TYPE, DjgHttpUrlRequest.OPERATION_TYPE);
            if (!ConfigManager.getInstance().getAftsLinkConf().checkNetRetrySwitch()) {
                logger.p("setAllowRetryForErrorHttpStatusCode false", new Object[0]);
                downloadReq.setAllowRetryForErrorHttpStatusCode(false);
            }
        }
        return downloadReq;
    }

    public void cancel() {
        this.mCancelled = true;
        if (this.mDownloadFuture != null && !this.mDownloadFuture.isCancelled() && !this.mDownloadFuture.isDone()) {
            this.mDownloadFuture.cancel(true);
        }
    }

    public void onCancelled(Request request) {
        if (this.transportCallback != null) {
            this.transportCallback.onCancelled(request);
        }
    }

    public void onPreExecute(Request request) {
        if (this.transportCallback != null) {
            this.transportCallback.onPreExecute(request);
        }
    }

    public void onPostExecute(Request request, Response response) {
        if (this.transportCallback != null) {
            this.transportCallback.onPostExecute(request, response);
        }
    }

    public void onProgressUpdate(Request request, double v) {
        if (this.transportCallback != null) {
            this.transportCallback.onProgressUpdate(request, v);
        }
    }

    public void onFailed(Request request, int i, String s) {
        logger.e("onFailed path: " + this.loadReq.path + ", i: " + i + ", s: " + s, new Object[0]);
        if (this.transportCallback != null) {
            this.transportCallback.onFailed(request, i, s);
        }
    }

    private void UC_MM_47(int ret, int expcode, long size2, String id, String biz, String exp, boolean noNetwork, boolean bMdn) {
        String str;
        if (ret == 0 || expcode > 0) {
            String net = "0";
            if (!TextUtils.isEmpty(id)) {
                net = id.startsWith("https") ? "1" : "0";
            }
            if (bMdn) {
                net = "1";
            }
            String valueOf = ret == 0 ? "0" : String.valueOf(expcode);
            if (bMdn) {
                str = "3";
            } else {
                str = "1";
            }
            UCLogUtil.UC_MM_C47(valueOf, size2, 0, id, "im", biz, str, exp, "url", net, noNetwork);
        }
    }

    private boolean isNeedUcLog(int ret) {
        return this.hasNetwork || ret == 0;
    }

    /* access modifiers changed from: protected */
    public String getImageMdnUrl(ImageLoadReq req) {
        if (req.getTransportWay() != 3) {
            return null;
        }
        if (TextUtils.isEmpty(req.zoom)) {
            req.zoom = ZoomHelper.getZoom(req);
        }
        String zoom2 = ZoomHelper.getSecondaryZoom(req);
        if (!TextUtils.isEmpty(zoom2)) {
            req.zoom += "&zoom2=" + zoom2;
        }
        if (TextUtils.isEmpty(req.fileId)) {
            req.fileId = req.path;
        }
        return ConfigManager.getInstance().getMdnBizConfig().genImageDlMdnUrl(req.fileId, req.zoom, req.options.getBizType());
    }

    /* access modifiers changed from: protected */
    public boolean isMdnWay() {
        return this.loadReq != null && this.loadReq.getTransportWay() == 3;
    }
}
