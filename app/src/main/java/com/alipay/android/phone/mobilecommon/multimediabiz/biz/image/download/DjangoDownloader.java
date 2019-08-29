package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailsDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageMarkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MarkUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class DjangoDownloader implements ImageDownloader<ThumbnailsDownResp> {
    private static final String APP_KEY = "aliwallet";
    private static final int TYPE_BIG = 1;
    private static final int TYPE_ORIGINAL = 2;
    private static final int TYPE_SMALL = 0;
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger((String) "DjangoDownloader");
    final AtomicBoolean bFinish = new AtomicBoolean(false);
    private boolean cancelled = false;
    private DjangoClient djangoClient;
    private boolean hasNetwork = true;
    private DownloadListener listener;
    /* access modifiers changed from: private */
    public ImageLoadReq loadReq;
    private RETCODE mCode;
    private Context mContext;
    int mProgress = -1;
    private String savePath;
    /* access modifiers changed from: private */
    public long start;
    private File storeFile = null;

    public interface DownloadListener {
        void onDownloadError(DjangoDownloader djangoDownloader, ImageLoadReq imageLoadReq, Exception exc);

        void onDownloadFinish(DjangoDownloader djangoDownloader, ImageLoadReq imageLoadReq, ThumbnailsDownResp thumbnailsDownResp);

        void onDownloadProgress(DjangoDownloader djangoDownloader, ImageLoadReq imageLoadReq, long j, long j2, int i, File file);

        void onDownloadStart(DjangoDownloader djangoDownloader, ImageLoadReq imageLoadReq);
    }

    public DjangoDownloader(String savePath2, ImageLoadReq req, DownloadListener listener2) {
        this.savePath = savePath2;
        this.loadReq = req;
        this.listener = listener2;
        this.mContext = AppUtils.getApplicationContext();
    }

    public ThumbnailsDownResp download(ImageLoadReq req, Bundle extra) {
        this.loadReq = req;
        this.hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        this.djangoClient = getDjangoClient();
        ThumbnailsDownReq downReq = createDownReq(req);
        downReq.setbHttps(req.options.isHttps());
        downReq.setBizId(this.loadReq.options.getBusinessId());
        downReq.mTimeout = req.mTimeout;
        ThumbnailsDownResp rsp = null;
        try {
            this.start = System.currentTimeMillis();
            rsp = this.djangoClient.getImageApi().downloadThumbnails(downReq);
            long cost = System.currentTimeMillis() - this.start;
            Logger.TIME("downloadThumbnails get response costTime: " + cost, cost, new Object[0]);
            handleDownloadRsp(downReq, rsp);
            if (this.loadReq.netPerf.netTime == -1) {
                this.loadReq.netPerf.netTime = System.currentTimeMillis() - this.start;
            }
        } catch (Exception e) {
            if (this.listener != null) {
                this.listener.onDownloadError(this, this.loadReq, e);
            }
            if (TextUtils.isEmpty(this.loadReq.netPerf.exception)) {
                this.loadReq.netPerf.exception = CommonUtils.getExceptionMsg(e);
            }
            logger.e(e, "downloadThumbnails exp", new Object[0]);
            if (this.loadReq.netPerf.netTime == -1) {
                this.loadReq.netPerf.netTime = System.currentTimeMillis() - this.start;
            }
        } catch (Throwable th) {
            if (this.loadReq.netPerf.netTime == -1) {
                this.loadReq.netPerf.netTime = System.currentTimeMillis() - this.start;
            }
            throw th;
        }
        return rsp;
    }

    private ThumbnailsDownReq createDownReq(ImageLoadReq req) {
        ThumbnailsDownReq downReq;
        String zoom = getZoom(req);
        if (req.options.getImageMarkRequest() != null) {
            APImageMarkRequest markRequest = req.options.getImageMarkRequest();
            downReq = new ThumbnailMarkDownReq(req.urlToDjango ? req.fileId : req.path, zoom);
            fillMarkParams((ThumbnailMarkDownReq) downReq, markRequest);
        } else {
            downReq = new ThumbnailsDownReq(req.urlToDjango ? req.fileId : req.path, zoom);
            downReq.setZoom2(ZoomHelper.getSecondaryZoom(req));
        }
        downReq.mTimeout = req.mTimeout;
        req.netPerf.zoom = zoom;
        req.netPerf.id = req.urlToDjango ? req.fileId : req.path;
        this.loadReq.netPerf.hasNetwork = this.hasNetwork;
        if (!TextUtils.isEmpty(downReq.getZoom2())) {
            StringBuilder sb = new StringBuilder();
            LoadImageFromNetworkPerf loadImageFromNetworkPerf = req.netPerf;
            loadImageFromNetworkPerf.zoom = sb.append(loadImageFromNetworkPerf.zoom).append('&').append(downReq.getZoom2()).toString();
        }
        downReq.addExtra("bizId", req.options.getBizType(), true);
        return downReq;
    }

    private void fillMarkParams(ThumbnailMarkDownReq downReq, APImageMarkRequest markRequest) {
        MarkUtil.fillMarkParams(downReq, markRequest);
        if (this.loadReq.netPerf instanceof LoadImageMarkPerf) {
            LoadImageMarkPerf markPerf = (LoadImageMarkPerf) this.loadReq.netPerf;
            markPerf.markId = markRequest.getMarkId();
            markPerf.markWidth = markRequest.getMarkWidth().intValue();
            markPerf.markHeight = markRequest.getMarkHeight().intValue();
            markPerf.paddingX = markRequest.getPaddingX();
            markPerf.paddingY = markRequest.getPaddingY();
            markPerf.position = markRequest.getPosition().intValue();
            markPerf.transparency = markRequest.getTransparency().intValue();
            markPerf.percent = markRequest.getPercent();
        }
    }

    private String getZoom(ImageLoadReq req) {
        return ZoomHelper.getZoom(req);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x04f6 A[Catch:{ Exception -> 0x058a }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x050c A[Catch:{ Exception -> 0x058a }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0510 A[Catch:{ Exception -> 0x058a }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0584  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0587  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleDownloadRsp(com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailsDownReq r50, com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp r51) {
        /*
            r49 = this;
            r45 = 0
            r47 = 0
            long r22 = r50.getRange()
            r6 = 0
            r41 = -1
            java.lang.String r12 = ""
            r4 = 0
            r13 = 0
            r43 = -1
            java.lang.String r13 = r51.getTraceId()     // Catch:{ Exception -> 0x0494 }
            boolean r9 = r51.isSuccess()     // Catch:{ Exception -> 0x0494 }
            if (r9 == 0) goto L_0x040d
            org.apache.http.HttpResponse r44 = r51.getResp()     // Catch:{ Exception -> 0x0494 }
            org.apache.http.HttpEntity r9 = r44.getEntity()     // Catch:{ Exception -> 0x0494 }
            long r6 = r9.getContentLength()     // Catch:{ Exception -> 0x0494 }
            long r24 = r6 + r22
            boolean r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpClientUtils.checkRspContentSizeAndType(r44)     // Catch:{ Exception -> 0x0494 }
            if (r9 != 0) goto L_0x0147
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.INCONSISTENT_SIZE     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            r0.mCode = r9     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = r0.mCode     // Catch:{ Exception -> 0x0494 }
            int r9 = r9.value()     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            r0.setCode(r9)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r9 = "content size and type not match"
            r0 = r51
            r0.setMsg(r9)     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = "handleDownloadRsp checkRspContentSizeAndType fail size="
            r10.<init>(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = r10.append(r6)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = ";id="
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = r50.getFileIds()     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = ";biz="
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = r50.getBizId()     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0494 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0494 }
            r9.d(r10, r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x0139 }
            r9.traceId = r13     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x0139 }
            java.lang.String r10 = r50.getFileIds()     // Catch:{ Exception -> 0x0139 }
            r9.id = r10     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x0139 }
            r9.exception = r12     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            boolean r10 = r0.hasNetwork     // Catch:{ Exception -> 0x0139 }
            r9.hasNetwork = r10     // Catch:{ Exception -> 0x0139 }
            r9 = 0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)     // Catch:{ Exception -> 0x0139 }
            r9 = 0
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r9)     // Catch:{ Exception -> 0x0139 }
            java.lang.String r5 = "-1"
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            long r0 = r0.start     // Catch:{ Exception -> 0x0139 }
            r18 = r0
            long r10 = r10 - r18
            int r8 = (int) r10     // Catch:{ Exception -> 0x0139 }
            r15 = 0
            r9 = 5000(0x1388, float:7.006E-42)
            if (r8 < r9) goto L_0x00cd
            org.apache.http.client.methods.HttpRequestBase r9 = r50.getHttpRequestBase()     // Catch:{ Exception -> 0x0139 }
            java.net.URI r9 = r9.getURI()     // Catch:{ Exception -> 0x0139 }
            java.lang.String r15 = r9.getHost()     // Catch:{ Exception -> 0x0139 }
        L_0x00cd:
            r9 = -1
            r0 = r49
            boolean r9 = r0.isNeedUcLog(r9)     // Catch:{ Exception -> 0x0139 }
            if (r9 != 0) goto L_0x0136
            r17 = 1
        L_0x00d8:
            java.lang.String r9 = r50.getZoom()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x0139 }
            java.lang.Integer r10 = r10.getWidth()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r11 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r11 = r11.options     // Catch:{ Exception -> 0x0139 }
            java.lang.Integer r11 = r11.getHeight()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            int r10 = r0.getImageType(r10, r11)     // Catch:{ Exception -> 0x0139 }
            r11 = 0
            java.lang.String r14 = r50.getFileIds()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r0 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            r16 = r0
            r0 = r16
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r0 = r0.options     // Catch:{ Exception -> 0x0139 }
            r16 = r0
            java.lang.String r16 = r16.getBizType()     // Catch:{ Exception -> 0x0139 }
            java.lang.String r18 = "1"
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ Exception -> 0x0139 }
            r20 = -1
            r21 = -1
            java.lang.String r24 = r50.getFileIds()     // Catch:{ Exception -> 0x0139 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x0139 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x0139 }
            java.lang.String r25 = r9.getBizType()     // Catch:{ Exception -> 0x0139 }
            java.lang.String r27 = r50.getZoom()     // Catch:{ Exception -> 0x0139 }
            boolean r28 = r50.isbHttps()     // Catch:{ Exception -> 0x0139 }
            r19 = r49
            r22 = r6
            r26 = r12
            r29 = r17
            r19.UC_MM_47(r20, r21, r22, r24, r25, r26, r27, r28, r29)     // Catch:{ Exception -> 0x0139 }
        L_0x0135:
            return
        L_0x0136:
            r17 = 0
            goto L_0x00d8
        L_0x0139:
            r42 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger
            java.lang.String r10 = "handleDownloadRsp finally exp"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r0 = r42
            r9.e(r0, r10, r11)
            goto L_0x0135
        L_0x0147:
            org.apache.http.HttpEntity r9 = r44.getEntity()     // Catch:{ Exception -> 0x0494 }
            java.io.InputStream r45 = r9.getContent()     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = "getFromDjango, ts: "
            r10.<init>(r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r24
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = ", range: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r22
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = ", mSourcePath: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r11 = r0.loadReq     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = r11.path     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0494 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0494 }
            r9.d(r10, r11)     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream r48 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.io.ProgressInputStream     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoDownloader$1 r19 = new com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoDownloader$1     // Catch:{ Exception -> 0x0494 }
            r20 = r49
            r21 = r50
            r19.<init>(r21, r22, r24)     // Catch:{ Exception -> 0x0494 }
            r0 = r48
            r1 = r45
            r2 = r19
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            java.lang.String r0 = r0.savePath     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r21 = r0
            r19 = r49
            r20 = r48
            java.lang.String r46 = r19.toFile(r20, r21, r22, r24)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r49
            long r0 = r0.start     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r18 = r0
            long r10 = r10 - r18
            r9.netTime = r10     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            boolean r9 = android.text.TextUtils.isEmpty(r46)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            if (r9 == 0) goto L_0x02db
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.STORE_FAILED     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r49
            r0.mCode = r9     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = r0.mCode     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            int r9 = r9.value()     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r51
            r0.setCode(r9)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            java.lang.String r9 = "to file fail"
            r0 = r51
            r0.setMsg(r9)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            java.lang.String r11 = "get from django toFile fail, resp: "
            r10.<init>(r11)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r51
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r9.d(r10, r11)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
        L_0x01f3:
            int r41 = r51.getCode()     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r47 = r48
        L_0x01f9:
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x02cc }
            r9.traceId = r13     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x02cc }
            java.lang.String r10 = r50.getFileIds()     // Catch:{ Exception -> 0x02cc }
            r9.id = r10     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x02cc }
            r9.exception = r12     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            boolean r10 = r0.hasNetwork     // Catch:{ Exception -> 0x02cc }
            r9.hasNetwork = r10     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r45)     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r47)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r5 = java.lang.String.valueOf(r41)     // Catch:{ Exception -> 0x02cc }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            long r0 = r0.start     // Catch:{ Exception -> 0x02cc }
            r18 = r0
            long r10 = r10 - r18
            int r8 = (int) r10     // Catch:{ Exception -> 0x02cc }
            r15 = 0
            r9 = 5000(0x1388, float:7.006E-42)
            if (r8 < r9) goto L_0x0249
            org.apache.http.client.methods.HttpRequestBase r9 = r50.getHttpRequestBase()     // Catch:{ Exception -> 0x02cc }
            java.net.URI r9 = r9.getURI()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r15 = r9.getHost()     // Catch:{ Exception -> 0x02cc }
        L_0x0249:
            r0 = r49
            r1 = r41
            boolean r9 = r0.isNeedUcLog(r1)     // Catch:{ Exception -> 0x02cc }
            if (r9 != 0) goto L_0x0497
            r17 = 1
        L_0x0255:
            if (r4 == 0) goto L_0x049b
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02cc }
            java.lang.String r10 = "s"
            r9.<init>(r10)     // Catch:{ Exception -> 0x02cc }
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ Exception -> 0x02cc }
            java.lang.String r27 = r9.toString()     // Catch:{ Exception -> 0x02cc }
        L_0x0266:
            java.lang.String r31 = r50.getZoom()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x02cc }
            java.lang.Integer r9 = r9.getWidth()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x02cc }
            java.lang.Integer r10 = r10.getHeight()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            int r32 = r0.getImageType(r9, r10)     // Catch:{ Exception -> 0x02cc }
            r33 = 0
            java.lang.String r36 = r50.getFileIds()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x02cc }
            java.lang.String r38 = r9.getBizType()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r40 = "1"
            r28 = r6
            r30 = r8
            r34 = r12
            r35 = r13
            r37 = r15
            r39 = r17
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r27, r28, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40)     // Catch:{ Exception -> 0x02cc }
            r29 = -1
            java.lang.String r32 = r50.getFileIds()     // Catch:{ Exception -> 0x02cc }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x02cc }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x02cc }
            java.lang.String r33 = r9.getBizType()     // Catch:{ Exception -> 0x02cc }
            java.lang.String r35 = r50.getZoom()     // Catch:{ Exception -> 0x02cc }
            boolean r36 = r50.isbHttps()     // Catch:{ Exception -> 0x02cc }
            r27 = r49
            r28 = r41
            r30 = r6
            r34 = r12
            r37 = r17
            r27.UC_MM_47(r28, r29, r30, r32, r33, r34, r35, r36, r37)     // Catch:{ Exception -> 0x02cc }
            goto L_0x0135
        L_0x02cc:
            r42 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger
            java.lang.String r10 = "handleDownloadRsp finally exp"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r0 = r42
            r9.e(r0, r10, r11)
            goto L_0x0135
        L_0x02db:
            r0 = r51
            r1 = r46
            r0.setSavePath(r1)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            r0 = r49
            r1 = r46
            r2 = r50
            r3 = r51
            r0.copyToCache(r1, r2, r3)     // Catch:{ Exception -> 0x02ef, all -> 0x0598 }
            goto L_0x01f3
        L_0x02ef:
            r42 = move-exception
            r47 = r48
        L_0x02f2:
            r0 = r42
            boolean r9 = r0 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x04b1 }
            if (r9 != 0) goto L_0x02fe
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.DOWNLOAD_FAILED     // Catch:{ all -> 0x04b1 }
            r0 = r49
            r0.mCode = r9     // Catch:{ all -> 0x04b1 }
        L_0x02fe:
            r4 = 1
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ all -> 0x04b1 }
            java.lang.String r10 = ""
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x04b1 }
            r0 = r42
            r9.e(r0, r10, r11)     // Catch:{ all -> 0x04b1 }
            int r43 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils.parseException(r42)     // Catch:{ all -> 0x04b1 }
            r0 = r49
            boolean r9 = r0.cancelled     // Catch:{ all -> 0x04b1 }
            if (r9 == 0) goto L_0x049f
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CANCEL     // Catch:{ all -> 0x04b1 }
            r0 = r49
            r0.mCode = r9     // Catch:{ all -> 0x04b1 }
            int r41 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_CANCEL     // Catch:{ all -> 0x04b1 }
        L_0x031d:
            java.lang.String r12 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils.getExceptionMsg(r42)     // Catch:{ all -> 0x04b1 }
            r0 = r51
            r1 = r41
            r0.setCode(r1)     // Catch:{ all -> 0x04b1 }
            r0 = r51
            r0.setMsg(r12)     // Catch:{ all -> 0x04b1 }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x03fe }
            r9.traceId = r13     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x03fe }
            java.lang.String r10 = r50.getFileIds()     // Catch:{ Exception -> 0x03fe }
            r9.id = r10     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x03fe }
            r9.exception = r12     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r9 = r9.netPerf     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            boolean r10 = r0.hasNetwork     // Catch:{ Exception -> 0x03fe }
            r9.hasNetwork = r10     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r45)     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r47)     // Catch:{ Exception -> 0x03fe }
            java.lang.String r5 = java.lang.String.valueOf(r41)     // Catch:{ Exception -> 0x03fe }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            long r0 = r0.start     // Catch:{ Exception -> 0x03fe }
            r18 = r0
            long r10 = r10 - r18
            int r8 = (int) r10     // Catch:{ Exception -> 0x03fe }
            r15 = 0
            r9 = 5000(0x1388, float:7.006E-42)
            if (r8 < r9) goto L_0x037d
            org.apache.http.client.methods.HttpRequestBase r9 = r50.getHttpRequestBase()     // Catch:{ Exception -> 0x03fe }
            java.net.URI r9 = r9.getURI()     // Catch:{ Exception -> 0x03fe }
            java.lang.String r15 = r9.getHost()     // Catch:{ Exception -> 0x03fe }
        L_0x037d:
            r0 = r49
            r1 = r41
            boolean r9 = r0.isNeedUcLog(r1)     // Catch:{ Exception -> 0x03fe }
            if (r9 != 0) goto L_0x04ad
            r17 = 1
        L_0x0389:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03fe }
            java.lang.String r10 = "s"
            r9.<init>(r10)     // Catch:{ Exception -> 0x03fe }
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ Exception -> 0x03fe }
            java.lang.String r27 = r9.toString()     // Catch:{ Exception -> 0x03fe }
            java.lang.String r31 = r50.getZoom()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x03fe }
            java.lang.Integer r9 = r9.getWidth()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x03fe }
            java.lang.Integer r10 = r10.getHeight()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            int r32 = r0.getImageType(r9, r10)     // Catch:{ Exception -> 0x03fe }
            r33 = 0
            java.lang.String r36 = r50.getFileIds()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x03fe }
            java.lang.String r38 = r9.getBizType()     // Catch:{ Exception -> 0x03fe }
            java.lang.String r40 = "1"
            r28 = r6
            r30 = r8
            r34 = r12
            r35 = r13
            r37 = r15
            r39 = r17
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r27, r28, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40)     // Catch:{ Exception -> 0x03fe }
            java.lang.String r32 = r50.getFileIds()     // Catch:{ Exception -> 0x03fe }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r9 = r0.loadReq     // Catch:{ Exception -> 0x03fe }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r9 = r9.options     // Catch:{ Exception -> 0x03fe }
            java.lang.String r33 = r9.getBizType()     // Catch:{ Exception -> 0x03fe }
            java.lang.String r35 = r50.getZoom()     // Catch:{ Exception -> 0x03fe }
            boolean r36 = r50.isbHttps()     // Catch:{ Exception -> 0x03fe }
            r27 = r49
            r28 = r41
            r29 = r43
            r30 = r6
            r34 = r12
            r37 = r17
            r27.UC_MM_47(r28, r29, r30, r32, r33, r34, r35, r36, r37)     // Catch:{ Exception -> 0x03fe }
            goto L_0x0135
        L_0x03fe:
            r42 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger
            java.lang.String r10 = "handleDownloadRsp finally exp"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            r0 = r42
            r9.e(r0, r10, r11)
            goto L_0x0135
        L_0x040d:
            r9 = 429(0x1ad, float:6.01E-43)
            int r10 = r51.getCode()     // Catch:{ Exception -> 0x0494 }
            if (r9 != r10) goto L_0x044b
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = "get from django fail by net limit, resp: "
            r10.<init>(r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0494 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0494 }
            r9.d(r10, r11)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r12 = "download fail for limited current"
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CURRENT_LIMIT     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            r0.mCode = r9     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.CURRENT_LIMIT     // Catch:{ Exception -> 0x0494 }
            int r41 = r9.value()     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            r1 = r41
            r0.setCode(r1)     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            r0.setMsg(r12)     // Catch:{ Exception -> 0x0494 }
            r4 = 1
            goto L_0x01f9
        L_0x044b:
            int r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_TIMEOUT     // Catch:{ Exception -> 0x0494 }
            int r10 = r51.getCode()     // Catch:{ Exception -> 0x0494 }
            if (r9 != r10) goto L_0x0470
            java.lang.String r12 = "download image timeout"
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Exception -> 0x0494 }
            r0 = r49
            r0.mCode = r9     // Catch:{ Exception -> 0x0494 }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg$RETCODE r9 = com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE.TIME_OUT     // Catch:{ Exception -> 0x0494 }
            int r41 = r9.value()     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            r1 = r41
            r0.setCode(r1)     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            r0.setMsg(r12)     // Catch:{ Exception -> 0x0494 }
            r4 = 1
            goto L_0x01f9
        L_0x0470:
            java.lang.String r12 = r51.getMsg()     // Catch:{ Exception -> 0x0494 }
            int r41 = r51.getCode()     // Catch:{ Exception -> 0x0494 }
            r4 = 1
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = logger     // Catch:{ Exception -> 0x0494 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0494 }
            java.lang.String r11 = "get from django fail, resp: "
            r10.<init>(r11)     // Catch:{ Exception -> 0x0494 }
            r0 = r51
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Exception -> 0x0494 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0494 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0494 }
            r9.d(r10, r11)     // Catch:{ Exception -> 0x0494 }
            goto L_0x01f9
        L_0x0494:
            r42 = move-exception
            goto L_0x02f2
        L_0x0497:
            r17 = 0
            goto L_0x0255
        L_0x049b:
            r27 = r5
            goto L_0x0266
        L_0x049f:
            int r9 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_TIMEOUT     // Catch:{ all -> 0x04b1 }
            r0 = r43
            if (r0 != r9) goto L_0x04a9
            int r41 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_TIMEOUT     // Catch:{ all -> 0x04b1 }
            goto L_0x031d
        L_0x04a9:
            int r41 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant.DJANGO_400     // Catch:{ all -> 0x04b1 }
            goto L_0x031d
        L_0x04ad:
            r17 = 0
            goto L_0x0389
        L_0x04b1:
            r9 = move-exception
        L_0x04b2:
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r10 = r10.netPerf     // Catch:{ Exception -> 0x058a }
            r10.traceId = r13     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r10 = r10.netPerf     // Catch:{ Exception -> 0x058a }
            java.lang.String r11 = r50.getFileIds()     // Catch:{ Exception -> 0x058a }
            r10.id = r11     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r10 = r10.netPerf     // Catch:{ Exception -> 0x058a }
            r10.exception = r12     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf r10 = r10.netPerf     // Catch:{ Exception -> 0x058a }
            r0 = r49
            boolean r11 = r0.hasNetwork     // Catch:{ Exception -> 0x058a }
            r10.hasNetwork = r11     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r45)     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r47)     // Catch:{ Exception -> 0x058a }
            java.lang.String r5 = java.lang.String.valueOf(r41)     // Catch:{ Exception -> 0x058a }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            long r0 = r0.start     // Catch:{ Exception -> 0x058a }
            r18 = r0
            long r10 = r10 - r18
            int r8 = (int) r10     // Catch:{ Exception -> 0x058a }
            r15 = 0
            r10 = 5000(0x1388, float:7.006E-42)
            if (r8 < r10) goto L_0x0502
            org.apache.http.client.methods.HttpRequestBase r10 = r50.getHttpRequestBase()     // Catch:{ Exception -> 0x058a }
            java.net.URI r10 = r10.getURI()     // Catch:{ Exception -> 0x058a }
            java.lang.String r15 = r10.getHost()     // Catch:{ Exception -> 0x058a }
        L_0x0502:
            r0 = r49
            r1 = r41
            boolean r10 = r0.isNeedUcLog(r1)     // Catch:{ Exception -> 0x058a }
            if (r10 != 0) goto L_0x0584
            r17 = 1
        L_0x050e:
            if (r4 == 0) goto L_0x0587
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x058a }
            java.lang.String r11 = "s"
            r10.<init>(r11)     // Catch:{ Exception -> 0x058a }
            java.lang.StringBuilder r10 = r10.append(r5)     // Catch:{ Exception -> 0x058a }
            java.lang.String r27 = r10.toString()     // Catch:{ Exception -> 0x058a }
        L_0x051f:
            java.lang.String r31 = r50.getZoom()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x058a }
            java.lang.Integer r10 = r10.getWidth()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r11 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r11 = r11.options     // Catch:{ Exception -> 0x058a }
            java.lang.Integer r11 = r11.getHeight()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            int r32 = r0.getImageType(r10, r11)     // Catch:{ Exception -> 0x058a }
            r33 = 0
            java.lang.String r36 = r50.getFileIds()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x058a }
            java.lang.String r38 = r10.getBizType()     // Catch:{ Exception -> 0x058a }
            java.lang.String r40 = "1"
            r28 = r6
            r30 = r8
            r34 = r12
            r35 = r13
            r37 = r15
            r39 = r17
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C04(r27, r28, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40)     // Catch:{ Exception -> 0x058a }
            java.lang.String r32 = r50.getFileIds()     // Catch:{ Exception -> 0x058a }
            r0 = r49
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq r10 = r0.loadReq     // Catch:{ Exception -> 0x058a }
            com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions r10 = r10.options     // Catch:{ Exception -> 0x058a }
            java.lang.String r33 = r10.getBizType()     // Catch:{ Exception -> 0x058a }
            java.lang.String r35 = r50.getZoom()     // Catch:{ Exception -> 0x058a }
            boolean r36 = r50.isbHttps()     // Catch:{ Exception -> 0x058a }
            r27 = r49
            r28 = r41
            r29 = r43
            r30 = r6
            r34 = r12
            r37 = r17
            r27.UC_MM_47(r28, r29, r30, r32, r33, r34, r35, r36, r37)     // Catch:{ Exception -> 0x058a }
        L_0x0583:
            throw r9
        L_0x0584:
            r17 = 0
            goto L_0x050e
        L_0x0587:
            r27 = r5
            goto L_0x051f
        L_0x058a:
            r42 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r10 = logger
            java.lang.String r11 = "handleDownloadRsp finally exp"
            r14 = 0
            java.lang.Object[] r14 = new java.lang.Object[r14]
            r0 = r42
            r10.e(r0, r11, r14)
            goto L_0x0583
        L_0x0598:
            r9 = move-exception
            r47 = r48
            goto L_0x04b2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoDownloader.handleDownloadRsp(com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailsDownReq, com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp):void");
    }

    private void copyToCache(String path, ThumbnailsDownReq req, ThumbnailsDownResp rsp) {
        boolean ret = false;
        boolean ret2 = false;
        Bundle extra = new Bundle();
        try {
            if (couldSaveCache(path)) {
                ret2 = ImageDiskCache.get().savePath(this.loadReq.cacheKey, path, this.loadReq.cacheKey.tag, this.loadReq.options.getBusinessId(), this.loadReq.getExpiredTime());
            }
        } catch (Exception e) {
            logger.e(e, "copyToCache error, req: " + req, new Object[0]);
        } finally {
            r2 = "saveDisk";
            extra.putBoolean(r2, ret);
            rsp.setExtra(extra);
        }
    }

    private boolean couldSaveCache(String path) {
        return ImageDiskCache.couldSaveIntoCacheDirectly(path, this.loadReq.options.getProcessor() != null, this.loadReq.options.isDetectedGif());
    }

    private int getImageType(Integer width, Integer height) {
        if (width == null || height == null) {
            return 2;
        }
        if ((width.intValue() == 0 && height.intValue() == 0) || (width.intValue() == 1280 && height.intValue() == 1280)) {
            return 1;
        }
        if (width.intValue() == Integer.MAX_VALUE && height.intValue() == Integer.MAX_VALUE) {
            return 2;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void checkCancel(ThumbnailsDownReq req) {
        if (this.cancelled || this.loadReq.taskModel.getStatus() == 2) {
            this.mCode = RETCODE.CANCEL;
            if (req != null) {
                req.abort();
            }
            logger.d("checkCancel task cancel, key: " + this.loadReq.cacheKey, new Object[0]);
            throw new RuntimeException("cancel");
        }
    }

    /* access modifiers changed from: private */
    public int onProcess(long cSize, long tSize) {
        int progressValue = 0;
        if (tSize > 0) {
            progressValue = (int) ((((float) cSize) * 100.0f) / ((float) tSize));
        }
        if (this.mProgress != progressValue) {
            this.mProgress = progressValue;
            this.loadReq.taskModel.setTotalSize(tSize);
            this.loadReq.taskModel.setCurrentSize(cSize);
            APMultimediaTaskManager.getInstance(this.mContext).updateTaskRecord(this.loadReq.taskModel);
            if (this.listener != null) {
                this.listener.onDownloadProgress(this, this.loadReq, cSize, tSize, this.mProgress, this.storeFile);
            }
        }
        return progressValue;
    }

    /* JADX INFO: finally extract failed */
    private String toFile(InputStream in, String savePath2, long start2, long totalSize) {
        DownloadResponseHelper helper = new DownloadResponseHelper();
        long startTime = System.currentTimeMillis();
        this.storeFile = new File(savePath2 + FilePathHelper.SUFFIX_DOT_TMP);
        File finalFile = new File(savePath2);
        String path = null;
        boolean flag = false;
        try {
            if (!finalFile.exists() || finalFile.length() <= 0 || finalFile.length() != totalSize) {
                if (finalFile.exists() && (finalFile.length() == 0 || (totalSize > 0 && finalFile.length() != totalSize))) {
                    logger.d("toFile for finalFile.exists() && finalFile.length(), del: " + this.storeFile + ";finalFile.length()=" + finalFile.length() + ";totalSize=" + totalSize, new Object[0]);
                    finalFile.delete();
                }
                logger.d(" toFile  offset :" + start2 + ",path: " + this.loadReq.path + ", store path: " + this.storeFile.getAbsolutePath(), new Object[0]);
                helper.writeSingleFile(in, this.storeFile, start2, new TransferredListener() {
                    public void onTransferred(long transferredCount) {
                    }
                });
                logger.d(" onTransferred bFinish :" + this.bFinish + ", storeFile len: " + this.storeFile.length() + ", store path: " + this.storeFile.getAbsolutePath(), new Object[0]);
                if (this.bFinish.get()) {
                    flag = this.storeFile.renameTo(finalFile);
                    logger.d("saveFile key: " + this.loadReq.path + this.loadReq.options + ", renameFrom: " + this.storeFile + ", finalFile: " + finalFile + ", len: " + finalFile.length() + ", ret: " + flag, new Object[0]);
                    path = finalFile.getAbsolutePath();
                }
            } else {
                path = finalFile.getAbsolutePath();
                this.bFinish.set(true);
                if (this.storeFile.exists()) {
                    logger.d("toFile for finalFile.exists() && finalFile.length() > 0, del: " + this.storeFile, new Object[0]);
                    this.storeFile.delete();
                }
            }
            if ((!this.bFinish.get() || TextUtils.isEmpty(path)) && this.loadReq.taskModel.getStatus() != 2) {
                logger.d("toFile for !bFinish.get() || TextUtils.isEmpty(path), del: " + this.storeFile, new Object[0]);
                this.storeFile.delete();
                logger.d("toFile delete path=" + path, new Object[0]);
                path = null;
            }
            logger.d("toFile usedTime=" + (System.currentTimeMillis() - startTime) + ";bFinish=" + this.bFinish.get() + ";flag=" + flag, new Object[0]);
            return path;
        } catch (Throwable th) {
            if ((!this.bFinish.get() || TextUtils.isEmpty(null)) && this.loadReq.taskModel.getStatus() != 2) {
                logger.d("toFile for !bFinish.get() || TextUtils.isEmpty(path), del: " + this.storeFile, new Object[0]);
                this.storeFile.delete();
                logger.d("toFile delete path=" + null, new Object[0]);
            }
            logger.d("toFile usedTime=" + (System.currentTimeMillis() - startTime) + ";bFinish=" + this.bFinish.get() + ";flag=" + false, new Object[0]);
            throw th;
        }
    }

    public void cancel() {
        this.cancelled = true;
    }

    /* access modifiers changed from: protected */
    public synchronized DjangoClient getDjangoClient() {
        if (this.djangoClient == null) {
            ConnectionManager conMgr = new HttpConnectionManager();
            conMgr.setAppKey(APP_KEY);
            this.djangoClient = new HttpDjangoClient(AppUtils.getApplicationContext(), conMgr);
        }
        return this.djangoClient;
    }

    private void UC_MM_47(int ret, int expcode, long size, String id, String biz, String exp, String zoom, boolean bHttps, boolean noNetwork) {
        if (ret == 0 || expcode > 0) {
            UCLogUtil.UC_MM_C47(ret == 0 ? "0" : String.valueOf(expcode), size, 0, id, "im", biz, "1", exp, zoom, bHttps ? "1" : "0", noNetwork);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isNeedUcLog(int ret) {
        return this.hasNetwork || ret == 0;
    }
}
