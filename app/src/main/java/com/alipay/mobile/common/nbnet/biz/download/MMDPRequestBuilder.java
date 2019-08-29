package com.alipay.mobile.common.nbnet.biz.download;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPReq;
import com.alipay.mobile.common.nbnet.biz.db.DownloadTaskModel;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import java.io.File;
import java.util.List;
import okio.ByteString;

public class MMDPRequestBuilder {
    private static final String a = MMDPRequestBuilder.class.getSimpleName();
    private final NBNetDownloadRequest b;
    private final DownloadTaskModel c;

    public MMDPRequestBuilder(NBNetDownloadRequest downloadRequest, DownloadTaskModel cacheTaskModel) {
        this.b = downloadRequest;
        this.c = cacheTaskModel;
    }

    public final MMDPReq a() {
        MMDPReq downloadReq = new MMDPReq();
        downloadReq.fileid = this.b.getFileId();
        downloadReq.restype = this.b.getResType();
        downloadReq.traceid = this.b.getTraceId();
        downloadReq.timestamp = Long.valueOf(System.currentTimeMillis());
        downloadReq.command = this.b.getCmdType();
        downloadReq.biztype = this.b.getBizType();
        downloadReq.srctype = this.b.getSourceType();
        downloadReq.userid = NBNetEnvUtils.e();
        byte[] bizParams = this.b.getBizParams();
        if (bizParams != null && bizParams.length > 0) {
            downloadReq.paramdata = ByteString.of(bizParams);
        }
        List extList = this.b.getExtList();
        if (extList != null && !extList.isEmpty()) {
            downloadReq.extra = extList;
        }
        File cacheFile = DownloadCacheManager.a().a(this.b.getRequestId());
        if (cacheFile.exists()) {
            downloadReq.rangestart = Integer.valueOf((int) cacheFile.length());
        }
        if (this.c != null) {
            if (!TextUtils.isEmpty(this.c.fileMD5)) {
                downloadReq.filemd5 = this.c.fileMD5;
            }
            if (this.c.fileLength > 0) {
                downloadReq.filelength = Integer.valueOf(this.c.fileLength);
            }
            if (!this.c.fileId.equals(this.b.getFileId())) {
                NBNetLogCat.d(a, "file id not mapping:" + this.c.fileId);
            }
        }
        a(downloadReq);
        return downloadReq;
    }

    private static void a(MMDPReq downloadReq) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("fileid=").append(downloadReq.fileid);
        if (downloadReq.rangestart != null) {
            logBuilder.append(",rangestart=").append(downloadReq.rangestart);
        }
        if (downloadReq.rangeend != null) {
            logBuilder.append(",rangeend=").append(downloadReq.rangeend);
        }
        if (downloadReq.restype != null) {
            logBuilder.append(",restype=").append(downloadReq.restype);
        }
        if (downloadReq.filelength != null) {
            logBuilder.append(",filelength=").append(downloadReq.filelength);
        }
        if (downloadReq.filemd5 != null) {
            logBuilder.append(",filemd5=").append(downloadReq.filemd5);
        }
        if (downloadReq.traceid != null) {
            logBuilder.append(",traceid=").append(downloadReq.traceid);
        }
        if (downloadReq.srctype != null) {
            logBuilder.append(",sourcetype=").append(downloadReq.srctype);
        }
        if (downloadReq.biztype != null) {
            logBuilder.append(",biztype=").append(downloadReq.biztype);
        }
        if (downloadReq.timestamp != null) {
            logBuilder.append(",timestamp=").append(downloadReq.timestamp);
        }
        if (downloadReq.userid != null) {
            logBuilder.append(",utdid=").append(downloadReq.userid);
        }
        if (downloadReq.command != null) {
            logBuilder.append(",command=").append(downloadReq.command);
        }
        NBNetLogCat.a(a, "downloadRequest======" + logBuilder);
    }
}
