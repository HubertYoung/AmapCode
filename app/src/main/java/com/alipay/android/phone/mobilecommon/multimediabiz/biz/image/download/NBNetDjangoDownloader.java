package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb.MMDPBizParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb.MMDPImageMarkParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb.MMDPImageZoomParam;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageMarkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MarkUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPCmdType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPExtraData;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResType;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPSourceType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NBNetDjangoDownloader implements ImageDownloader<ThumbnailsDownResp> {
    private static final int TYPE_BIG = 1;
    private static final int TYPE_ORIGINAL = 2;
    private static final int TYPE_SMALL = 0;
    private static final Logger logger = Logger.getLogger((String) "NBNetDjangoDownloader");
    private boolean bCancel = false;
    private NBNetDownloadCallback callback;
    private NBNetDownloadClient downloadClient;
    private String fileId;
    private boolean hasNetwork = true;
    private ImageLoadReq loadReq;
    private String mZoom = null;
    private NBNetDownloadRequest nbReq;
    private String savePath;
    private long size;
    private long start;

    public NBNetDjangoDownloader(String savePath2, ImageLoadReq req, NBNetDownloadCallback nbDownLoadCb) {
        this.loadReq = req;
        if (req.urlToDjango) {
            this.fileId = req.fileId;
        } else {
            this.fileId = req.path;
        }
        this.callback = nbDownLoadCb;
        this.savePath = savePath2;
    }

    public ThumbnailsDownResp download(ImageLoadReq req, Bundle extra) {
        int expcode;
        int expcode2;
        this.hasNetwork = CommonUtils.isActiveNetwork(AppUtils.getApplicationContext());
        this.nbReq = createDownReq(req);
        logger.d("download start req=" + this.nbReq.toString(), new Object[0]);
        NBNetDownloadResponse rsp = null;
        ThumbnailsDownResp downloadRsp = new ThumbnailsDownResp();
        this.bCancel = false;
        try {
            this.downloadClient = NBNetUtils.getNBNetDownloadClient();
            if (this.downloadClient == null) {
                throw new RuntimeException("downloadClient can not be null");
            }
            this.start = System.currentTimeMillis();
            Future<NBNetDownloadResponse> requestDownload = this.downloadClient.requestDownload(this.nbReq, this.callback);
            try {
                if (req.mTimeout > 0) {
                    rsp = requestDownload.get((long) req.mTimeout, TimeUnit.SECONDS);
                } else {
                    rsp = requestDownload.get();
                }
            } catch (TimeoutException te) {
                logger.e(te, "future.get time out error: " + te.getMessage(), new Object[0]);
                downloadRsp.setCode(DjangoConstant.DJANGO_TIMEOUT);
                downloadRsp.setMsg("NBNetDjangoDownloader task timeout exp");
                rsp = null;
            } catch (InterruptedException e) {
                logger.e(e, "future.get error: " + e.getMessage(), new Object[0]);
                rsp = null;
            }
            long cost = System.currentTimeMillis() - this.start;
            Logger.TIME("downloadThumbnails get response costTime: " + cost, cost, new Object[0]);
            this.loadReq.netPerf.netTime = cost;
            handleDownloadRsp(rsp, downloadRsp);
            try {
                long coast = System.currentTimeMillis() - this.start;
                if (this.loadReq.netPerf.netTime == -1) {
                    this.loadReq.netPerf.netTime = coast;
                }
                this.loadReq.netPerf.id = this.nbReq.getFileId();
                this.loadReq.netPerf.traceId = downloadRsp.getTraceId();
                this.loadReq.netPerf.exception = downloadRsp.getMsg();
                this.loadReq.netPerf.hasNetwork = this.hasNetwork;
                boolean noNetwork = !isNeedUcLog(downloadRsp.getCode());
                UCLogUtil.UC_MM_C04(String.valueOf(downloadRsp.getCode()), this.size, (int) coast, getZoom(req), getImageType(req.options.getWidth(), req.options.getHeight()), false, downloadRsp.getMsg(), downloadRsp.getTraceId(), this.fileId, this.loadReq.options.getBizType(), noNetwork);
                if (rsp == null || rsp.getErrorCode() >= 0) {
                    expcode2 = -1;
                } else {
                    expcode2 = DiskExpUtils.parseException(rsp.getException());
                }
                try {
                    UC_MM_47(downloadRsp.getCode(), expcode2, this.size, this.fileId, this.loadReq.options.getBusinessId(), downloadRsp.getMsg(), getZoom(req), noNetwork);
                    Logger.TIME("NBNetDjangoDownloader costTime " + coast, coast, new Object[0]);
                    logger.d("download end code=" + downloadRsp.getCode() + ";msg=" + downloadRsp.getMsg() + ";size=" + this.size + ";fileid=" + this.fileId + ";savePath=" + this.savePath + ";traceid=" + downloadRsp.getTraceId(), new Object[0]);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                logger.e(e, "download finally exp", new Object[0]);
                return downloadRsp;
            }
            return downloadRsp;
        } catch (Exception e4) {
            logger.e(e4, "download error: " + e4.getMessage(), new Object[0]);
            downloadRsp.setCode(this.bCancel ? DjangoConstant.DJANGO_CANCEL : DjangoConstant.DJANGO_400);
            downloadRsp.setMsg(CommonUtils.getExceptionMsg(e4));
            int expcode3 = DiskExpUtils.parseException(e4);
            try {
                long coast2 = System.currentTimeMillis() - this.start;
                if (this.loadReq.netPerf.netTime == -1) {
                    this.loadReq.netPerf.netTime = coast2;
                }
                this.loadReq.netPerf.id = this.nbReq.getFileId();
                this.loadReq.netPerf.traceId = downloadRsp.getTraceId();
                this.loadReq.netPerf.exception = downloadRsp.getMsg();
                this.loadReq.netPerf.hasNetwork = this.hasNetwork;
                boolean noNetwork2 = !isNeedUcLog(downloadRsp.getCode());
                UCLogUtil.UC_MM_C04(String.valueOf(downloadRsp.getCode()), this.size, (int) coast2, getZoom(req), getImageType(req.options.getWidth(), req.options.getHeight()), false, downloadRsp.getMsg(), downloadRsp.getTraceId(), this.fileId, this.loadReq.options.getBizType(), noNetwork2);
                if (rsp != null && rsp.getErrorCode() < 0) {
                    expcode3 = DiskExpUtils.parseException(rsp.getException());
                }
                UC_MM_47(downloadRsp.getCode(), expcode3, this.size, this.fileId, this.loadReq.options.getBusinessId(), downloadRsp.getMsg(), getZoom(req), noNetwork2);
                Logger.TIME("NBNetDjangoDownloader costTime " + coast2, coast2, new Object[0]);
                logger.d("download end code=" + downloadRsp.getCode() + ";msg=" + downloadRsp.getMsg() + ";size=" + this.size + ";fileid=" + this.fileId + ";savePath=" + this.savePath + ";traceid=" + downloadRsp.getTraceId(), new Object[0]);
            } catch (Exception e5) {
                logger.e(e5, "download finally exp", new Object[0]);
            }
        } catch (Throwable th) {
            try {
                long coast3 = System.currentTimeMillis() - this.start;
                if (this.loadReq.netPerf.netTime == -1) {
                    this.loadReq.netPerf.netTime = coast3;
                }
                this.loadReq.netPerf.id = this.nbReq.getFileId();
                this.loadReq.netPerf.traceId = downloadRsp.getTraceId();
                this.loadReq.netPerf.exception = downloadRsp.getMsg();
                this.loadReq.netPerf.hasNetwork = this.hasNetwork;
                boolean noNetwork3 = !isNeedUcLog(downloadRsp.getCode());
                UCLogUtil.UC_MM_C04(String.valueOf(downloadRsp.getCode()), this.size, (int) coast3, getZoom(req), getImageType(req.options.getWidth(), req.options.getHeight()), false, downloadRsp.getMsg(), downloadRsp.getTraceId(), this.fileId, this.loadReq.options.getBizType(), noNetwork3);
                if (rsp == null || rsp.getErrorCode() >= 0) {
                    expcode = -1;
                } else {
                    expcode = DiskExpUtils.parseException(rsp.getException());
                }
                try {
                    UC_MM_47(downloadRsp.getCode(), expcode, this.size, this.fileId, this.loadReq.options.getBusinessId(), downloadRsp.getMsg(), getZoom(req), noNetwork3);
                    Logger.TIME("NBNetDjangoDownloader costTime " + coast3, coast3, new Object[0]);
                    logger.d("download end code=" + downloadRsp.getCode() + ";msg=" + downloadRsp.getMsg() + ";size=" + this.size + ";fileid=" + this.fileId + ";savePath=" + this.savePath + ";traceid=" + downloadRsp.getTraceId(), new Object[0]);
                } catch (Exception e6) {
                    e = e6;
                    logger.e(e, "download finally exp", new Object[0]);
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
                logger.e(e, "download finally exp", new Object[0]);
                throw th;
            }
            throw th;
        }
    }

    private String getZoom(ImageLoadReq req) {
        if (this.mZoom == null) {
            this.mZoom = ZoomHelper.getZoom(req);
            String zoom2 = ZoomHelper.getSecondaryZoom(req);
            if (!TextUtils.isEmpty(zoom2)) {
                this.mZoom += "&zoom2=" + zoom2;
            }
        }
        return this.mZoom;
    }

    private NBNetDownloadRequest createDownReq(ImageLoadReq req) {
        if (TextUtils.isEmpty(this.savePath)) {
            this.savePath = ImageDiskCache.get().genPathByKey(req.cacheKey.complexCacheKey());
        }
        this.nbReq = new NBNetDownloadRequest();
        this.nbReq.setCmdType(MMDPCmdType.IMAGE_ZOOM);
        if (req.urlToDjango) {
            this.nbReq.setFileId(req.fileId);
        } else {
            this.nbReq.setFileId(req.path);
        }
        this.nbReq.setSavePath(this.savePath);
        this.nbReq.setSourceType(MMDPSourceType.FILEID);
        this.nbReq.setBizType(req.options.getBizType());
        this.nbReq.setResType(MMDPResType.IMAGE);
        int timeout = NBNetUtils.getDownloadImageConfigTimeout();
        if (req.mTimeout > 0) {
            timeout = req.mTimeout * 1000;
        }
        this.nbReq.setReqTimeOut(timeout);
        MMDPImageZoomParam zoomParam = new MMDPImageZoomParam();
        zoomParam.expr = getZoom(req);
        MMDPBizParam mmdpBizParam = new MMDPBizParam();
        mmdpBizParam.zoomParam = zoomParam;
        if (req.downLoadCallback != null) {
            logger.d("add monitor log: " + req.downLoadCallback.getClass().getName(), new Object[0]);
            this.nbReq.setExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK, req.downLoadCallback.getClass().getName());
        }
        this.nbReq.setExtInfo("thumb", "true");
        if (req.options == null) {
            this.nbReq.setBizParams(mmdpBizParam.toByteArray());
            logger.d("createDownReq zoomParam=" + zoomParam.toString(), new Object[0]);
            return this.nbReq;
        } else if (req.options.getImageMarkRequest() != null) {
            MMDPImageMarkParam markParam = new MMDPImageMarkParam();
            APImageMarkRequest markRequest = req.options.getImageMarkRequest();
            MarkUtil.fillMarkParams(markParam, markRequest);
            mmdpBizParam.imageMarkParam = markParam;
            this.nbReq.setCmdType(MMDPCmdType.IMAGE_MARK);
            this.nbReq.setBizParams(mmdpBizParam.toByteArray());
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
            this.loadReq.netPerf.zoom = this.mZoom;
            logger.d("createDownReq imageParam zoom=" + this.mZoom + ";fileId=" + this.fileId + ";markRequest=" + markRequest.toString(), new Object[0]);
            return this.nbReq;
        } else {
            this.nbReq.setBizParams(mmdpBizParam.toByteArray());
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
            logger.d("createDownReq imageParam zoom=" + this.mZoom + ";fileId=" + this.fileId, new Object[0]);
            return this.nbReq;
        }
    }

    private void handleDownloadRsp(NBNetDownloadResponse rsp, ThumbnailsDownResp downloadRsp) {
        if (!checkAndHandleNullRsp(rsp, downloadRsp)) {
            if (rsp.isSuccess()) {
                File saveFile = new File(this.savePath);
                this.size = rsp.getDataLength();
                boolean bSuccess = false;
                if (saveFile.exists() && saveFile.isFile() && saveFile.length() > 0) {
                    bSuccess = true;
                }
                logger.d("saveFile source:" + this.fileId + ", dst: " + saveFile + ", len: " + saveFile.length() + ", ret: " + bSuccess, new Object[0]);
                if (bSuccess) {
                    downloadRsp.setCode(DjangoConstant.DJANGO_OK);
                    downloadRsp.setSavePath(this.savePath);
                    copyToCache(this.savePath, downloadRsp);
                } else {
                    downloadRsp.setCode(DjangoConstant.DJANGO_400);
                    downloadRsp.setMsg("saveFile not exist or length is 0");
                }
                downloadRsp.setTraceId(rsp.getTraceId());
            } else if (429 == rsp.getErrorCode()) {
                downloadRsp.setCode(RETCODE.CURRENT_LIMIT.value());
                downloadRsp.setTraceId(rsp.getTraceId());
                downloadRsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                logger.d("download err, path: " + this.fileId + ", code: " + downloadRsp.getCode() + ", msg: " + downloadRsp.getMsg(), new Object[0]);
            } else {
                downloadRsp.setCode(rsp.getErrorCode());
                downloadRsp.setTraceId(rsp.getTraceId());
                String msg = rsp.getErrorMsg();
                if (TextUtils.isEmpty(msg)) {
                    msg = "Http invoker error :" + downloadRsp.getCode();
                }
                downloadRsp.setMsg(msg);
                logger.d("download err, path: " + this.fileId + ", code: " + downloadRsp.getCode() + ", msg: " + downloadRsp.getMsg(), new Object[0]);
            }
        }
    }

    private boolean checkAndHandleNullRsp(NBNetDownloadResponse rsp, ThumbnailsDownResp downloadRsp) {
        if (rsp != null) {
            return false;
        }
        if (downloadRsp.getCode() != DjangoConstant.DJANGO_TIMEOUT) {
            downloadRsp.setCode(this.bCancel ? DjangoConstant.DJANGO_CANCEL : DjangoConstant.DJANGO_400);
            downloadRsp.setMsg(this.bCancel ? "NBNetDjangoDownloader task canceled" : "httpManager execute return null");
        }
        logger.d("download err, fileid: " + this.fileId + ", code: " + downloadRsp.getCode() + ", msg: " + downloadRsp.getMsg(), new Object[0]);
        return true;
    }

    private void copyToCache(String path, ThumbnailsDownResp rsp) {
        boolean ret = false;
        boolean ret2 = false;
        Bundle extra = new Bundle();
        try {
            if (couldSaveCacheDirectly(path) && !CutScaleType.CENTER_CROP.equals(this.loadReq.options.getCutScaleType())) {
                ret2 = ImageDiskCache.get().savePath(this.loadReq.cacheKey, path, this.loadReq.cacheKey.tag, this.loadReq.options.getBusinessId(), this.loadReq.getExpiredTime());
            }
        } catch (Exception e) {
            logger.e(e, "copyToCache error", new Object[0]);
        } finally {
            r2 = "saveDisk";
            extra.putBoolean(r2, ret);
            rsp.setExtra(extra);
        }
    }

    private boolean couldSaveCacheDirectly(String path) {
        boolean hasProcessor = true;
        if (TextUtils.isEmpty(this.mZoom)) {
            return true;
        }
        if (this.loadReq.options.getProcessor() != null) {
            hasProcessor = false;
        }
        return ImageDiskCache.couldSaveIntoCacheDirectly(path, hasProcessor, this.loadReq.options.isDetectedGif());
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

    public void cancel() {
        this.bCancel = true;
        if (this.downloadClient != null) {
            logger.d("cancel nbReq=" + this.nbReq.toString(), new Object[0]);
            this.downloadClient.cancelDownload(this.nbReq);
        }
    }

    private void UC_MM_47(int ret, int expcode, long size2, String id, String biz, String exp, String zoom, boolean noNetwork) {
        if (ret == 0 || expcode > 0) {
            UCLogUtil.UC_MM_C47(ret == 0 ? "0" : String.valueOf(expcode), size2, 0, id, "im", biz, "2", exp, zoom, "1", noNetwork);
        }
    }

    private boolean isNeedUcLog(int ret) {
        return this.hasNetwork || ret == 0;
    }
}
