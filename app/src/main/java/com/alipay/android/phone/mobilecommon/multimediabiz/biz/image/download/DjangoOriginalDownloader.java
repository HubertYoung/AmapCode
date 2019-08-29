package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class DjangoOriginalDownloader implements APFileDownCallback, ImageDownloader<String> {
    private static final Logger logger = Logger.getLogger((String) "DjangoOriginalDownloader");
    private String bizType;
    private String cloudId;
    private boolean hasNetwork = true;
    private ImageLoadReq loadReq;
    private APFileDownCallback mCallback;
    private MultimediaFileService mService;
    private APMultimediaTaskModel mTask;
    private String savePath;
    private long size;
    private long start;

    public DjangoOriginalDownloader(ImageLoadReq loadReq2, String savePath2, APFileDownCallback cb) {
        this.savePath = savePath2;
        this.mCallback = cb;
        this.mService = AppUtils.getFileService();
        this.loadReq = loadReq2;
    }

    public String download(ImageLoadReq req, Bundle extra) {
        this.start = System.currentTimeMillis();
        this.hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        this.loadReq = req;
        this.size = 0;
        APFileReq fileReq = new APFileReq();
        fileReq.setCloudId(req.path);
        this.cloudId = req.path;
        fileReq.setSavePath(this.savePath);
        fileReq.setCallGroup(1001);
        fileReq.setPriority(req.options.getPriority());
        fileReq.setHttps(req.options.isHttps());
        fileReq.setMd5(req.options.getMd5());
        this.bizType = req.options.getBizType();
        this.mTask = this.mService.downLoad(fileReq, (APFileDownCallback) this, this.bizType);
        return this.savePath;
    }

    public void cancel() {
        if (this.mTask != null && !TextUtils.isEmpty(this.mTask.getTaskId())) {
            this.mService.cancelLoad(this.mTask.getTaskId());
        }
    }

    public void onDownloadStart(APMultimediaTaskModel taskInfo) {
        if (this.mCallback != null) {
            this.mCallback.onDownloadStart(taskInfo);
        }
    }

    public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
        this.size = total;
        if (this.mCallback != null) {
            this.mCallback.onDownloadProgress(taskInfo, progress, hasDownSize, total);
        }
    }

    public void onDownloadBatchProgress(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
        if (this.mCallback != null) {
            this.mCallback.onDownloadBatchProgress(taskInfo, progress, curIndex, hasDownSize, total);
        }
    }

    public void onDownloadFinished(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        if (this.mCallback != null) {
            this.mCallback.onDownloadFinished(taskInfo, rsp);
        }
        logger.d("saveFile downloadOriginal success, path: " + rsp.getFileReq().getCloudId() + ", saveFile: " + rsp.getFileReq().getSavePath(), new Object[0]);
        UCLogUtil.UC_MM_C04("0", this.size, (int) (System.currentTimeMillis() - this.start), "original", 2, false, rsp.getMsg(), rsp.getTraceId(), taskInfo != null ? taskInfo.getCloudId() : "", null, this.bizType, false, isMdnWay() ? "3" : "");
        Logger.TIME("DjangoOriginalDownloader onDownloadFinished costTime: " + (System.currentTimeMillis() - this.start), System.currentTimeMillis() - this.start, new Object[0]);
        if (isMdnWay()) {
            this.loadReq.netPerf.netMethod = 3;
        }
        this.loadReq.netPerf.id = rsp.getFileReq().getCloudId();
        this.loadReq.netPerf.traceId = rsp.getTraceId();
    }

    public void onDownloadError(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        logger.e("onDownloadError rsp: " + rsp, new Object[0]);
        if (this.mCallback != null) {
            this.mCallback.onDownloadError(taskInfo, rsp);
        }
        UCLogUtil.UC_MM_C04(String.valueOf(rsp.getRetCode()), 0, (int) (System.currentTimeMillis() - this.start), "original", 2, false, rsp.getMsg(), rsp.getTraceId(), this.cloudId, null, this.bizType, !this.hasNetwork, isMdnWay() ? "3" : "");
        Logger.TIME("DjangoOriginalDownloader onDownloadError costTime: " + (System.currentTimeMillis() - this.start), System.currentTimeMillis() - this.start, new Object[0]);
        this.loadReq.netPerf.id = this.cloudId;
        if (isMdnWay()) {
            this.loadReq.netPerf.netMethod = 3;
        }
        this.loadReq.netPerf.traceId = rsp.getTraceId();
        this.loadReq.netPerf.exception = rsp.getMsg();
        this.loadReq.netPerf.hasNetwork = this.hasNetwork;
    }

    private boolean isMdnWay() {
        return this.loadReq != null && this.loadReq.getTransportWay() == 3;
    }
}
