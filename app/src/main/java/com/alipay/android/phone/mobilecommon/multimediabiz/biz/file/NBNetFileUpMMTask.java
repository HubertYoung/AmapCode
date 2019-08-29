package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db.UpCacheHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.nbnet.api.ExtInfoConstans;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadCallback;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadRequest;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NBNetFileUpMMTask extends FileUpMMTask implements NBNetUploadCallback {
    private static final Logger a = Logger.getLogger((String) "NBNetFileUpMMTask");
    private APFileUploadRsp b = new APFileUploadRsp();
    private APMultimediaTaskModel c;
    private long d = 0;
    private String e = "";
    private NBNetUploadClient f;
    private NBNetUploadRequest g;
    private FutureTask<NBNetUploadResponse> h;

    protected NBNetFileUpMMTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo, APFileUploadCallback callback) {
        super(context, reqList, taskInfo);
        this.c = taskInfo;
        this.callbacks.add(callback);
        setTag("NBNetFileUpMMTask");
    }

    public APFileRsp taskRun() {
        APFileRsp rsp = super.taskRun();
        if (rsp == null) {
            return a(this.fileReqList);
        }
        this.b.setRetCode(rsp.getRetCode());
        this.b.setMsg(rsp.getMsg());
        this.b.setFileReq((APFileReq) this.fileReqList.get(0));
        notifyUploadError(this.taskInfo, this.b);
        return this.b;
    }

    private void a(APFileUploadRsp uploadRsp) {
        if (uploadRsp != null && uploadRsp.getRetCode() == 0) {
            FileUpResp rsp = new FileUpResp();
            rsp.setTraceId(uploadRsp.getTraceId());
            DjangoFileInfoResp info = new DjangoFileInfoResp();
            info.setId(uploadRsp.getFileReq().getCloudId());
            info.setMd5(this.e);
            rsp.setFileInfo(info);
            UpCacheHelper.saveToLocal(rsp, this.e);
        }
    }

    private static APFileUploadRsp a(APFileReq req) {
        try {
            String md5 = MD5Util.getFileMD5String(new File(req.getSavePath()));
            if (req.getPublic() != null && req.getPublic().booleanValue()) {
                md5 = md5 + "_pub";
            }
            FileUpResp rsp = UpCacheHelper.loadExistsResult(FileUpResp.class, md5);
            if (rsp != null) {
                APFileUploadRsp uploadRsp = new APFileUploadRsp();
                uploadRsp.setFileReq(req);
                req.setCloudId(rsp.getFileInfo().getId());
                uploadRsp.setRetCode(0);
                uploadRsp.setTraceId(rsp.getTraceId());
                uploadRsp.setMsg("from cache");
                return uploadRsp;
            }
        } catch (Exception e2) {
            a.e(e2, "getFromCache error, " + req, new Object[0]);
        }
        return null;
    }

    private APFileUploadRsp a(List reqList) {
        APFileReq req = (APFileReq) reqList.get(0);
        a.d("uploadSync start req=" + req, new Object[0]);
        notifyUploadStart(this.taskInfo);
        APFileUploadRsp cache = a(req);
        a.d("uploadSync getFromCache: " + cache + ", req: " + req, new Object[0]);
        if (cache != null) {
            this.b = cache;
        }
        if (cache == null) {
            NBNetUploadResponse nbnetRsp = null;
            long start = System.currentTimeMillis();
            boolean bTimeout = false;
            try {
                this.f = NBNetUtils.getNBNetUploadClient();
                if (this.f == null) {
                    throw new RuntimeException("uploadClient can not be null");
                }
                b(req);
                this.h = this.f.addUploadTask(this.g);
                try {
                    if (req.getTimeout() > 0) {
                        nbnetRsp = this.h.get((long) req.getTimeout(), TimeUnit.SECONDS);
                    } else {
                        nbnetRsp = this.h.get();
                    }
                } catch (Throwable th) {
                    if (th instanceof TimeoutException) {
                        bTimeout = true;
                    }
                    nbnetRsp = null;
                }
                a(req, nbnetRsp);
                a(this.b);
                if (isCanceled() || 5 == this.taskInfo.getStatus()) {
                    this.b.setRetCode(5);
                    this.b.setMsg("multimedia_file_task_canceled");
                } else if (nbnetRsp != null && nbnetRsp.getErrorCode() == 429) {
                    this.b.setRetCode(2000);
                    this.b.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                } else if (bTimeout) {
                    this.b.setMsg("nbnet upload timeout after " + req.getTimeout() + " s");
                    this.b.setRetCode(14);
                }
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C03(String.valueOf(this.b.getRetCode()), this.d, (int) (System.currentTimeMillis() - start), this.b.getMsg(), this.b.getTraceId(), this.e, this.bizType, isNoNetwork(this.b.getRetCode()));
                }
                a.d("uploadSync end mRsp=" + this.b + ";traceid=" + this.b.getTraceId() + ";size=" + this.d, new Object[0]);
                this.h = null;
            } catch (Exception e2) {
                a.e(e2, "NBNetFileUpTask exp", new Object[0]);
                this.b.setMsg(e2.getMessage());
                this.b.setRetCode(1);
                this.b.setFileReq(req);
                if (isCanceled() || 5 == this.taskInfo.getStatus()) {
                    this.b.setRetCode(5);
                    this.b.setMsg("multimedia_file_task_canceled");
                } else if (nbnetRsp != null && nbnetRsp.getErrorCode() == 429) {
                    this.b.setRetCode(2000);
                    this.b.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                } else if (bTimeout) {
                    this.b.setMsg("nbnet upload timeout after " + req.getTimeout() + " s");
                    this.b.setRetCode(14);
                }
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C03(String.valueOf(this.b.getRetCode()), this.d, (int) (System.currentTimeMillis() - start), this.b.getMsg(), this.b.getTraceId(), this.e, this.bizType, isNoNetwork(this.b.getRetCode()));
                }
                a.d("uploadSync end mRsp=" + this.b + ";traceid=" + this.b.getTraceId() + ";size=" + this.d, new Object[0]);
                this.h = null;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                if (isCanceled() || 5 == this.taskInfo.getStatus()) {
                    this.b.setRetCode(5);
                    this.b.setMsg("multimedia_file_task_canceled");
                } else if (nbnetRsp != null && nbnetRsp.getErrorCode() == 429) {
                    this.b.setRetCode(2000);
                    this.b.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                } else if (bTimeout) {
                    this.b.setMsg("nbnet upload timeout after " + req.getTimeout() + " s");
                    this.b.setRetCode(14);
                }
                if (isNeedUCLog(req)) {
                    UCLogUtil.UC_MM_C03(String.valueOf(this.b.getRetCode()), this.d, (int) (System.currentTimeMillis() - start), this.b.getMsg(), this.b.getTraceId(), this.e, this.bizType, isNoNetwork(this.b.getRetCode()));
                }
                a.d("uploadSync end mRsp=" + this.b + ";traceid=" + this.b.getTraceId() + ";size=" + this.d, new Object[0]);
                this.h = null;
                throw th3;
            }
        }
        if (!this.callbacks.isEmpty()) {
            if (this.b.getRetCode() == 0) {
                if (isNeedUCLog(this.b.getFileReq())) {
                    copyToCache(this.b.getFileReq().getCloudId(), this.b.getFileReq().getSavePath(), this.b.getFileReq().businessId);
                }
                notifyUploadFinish(this.taskInfo, this.b);
            } else {
                notifyUploadError(this.taskInfo, this.b);
            }
        }
        return this.b;
    }

    private void b(APFileReq req) {
        String suffix = "";
        if (req.getUploadData() != null) {
            this.d = (long) req.getUploadData().length;
            this.g = new NBNetUploadRequest(req.getUploadData(), this.bizType, (NBNetUploadCallback) this);
        } else {
            File file = new File(PathUtils.extractPath(req.getSavePath()));
            this.d = file.length();
            this.g = new NBNetUploadRequest(file, this.bizType, (NBNetUploadCallback) this);
            suffix = FileUtils.getSuffix(req.getAliasFileName());
            if (TextUtils.isEmpty(suffix) && !TextUtils.isEmpty(req.getSavePath())) {
                suffix = FileUtils.getSuffix(req.getSavePath());
            }
        }
        if (APFileReq.FILE_TYPE_COMPRESS_IMAGE.equals(req.getType())) {
            suffix = ".jpg";
        }
        a.d("createNBNetUpReq suffix: " + suffix, new Object[0]);
        this.g.setFileNameExt(FileUtils.getSuffixWithoutSeparator(suffix));
        if (req.getPublic() != null) {
            this.g.setPublicScope(req.getPublic().booleanValue());
        }
        if (this.callbacks != null && !this.callbacks.isEmpty()) {
            Object o = this.callbacks.iterator().next();
            this.requestCallBackClassName = o == null ? null : o.getClass().getName();
            if (this.requestCallBackClassName != null) {
                a.d("add monitor log: " + this.requestCallBackClassName, new Object[0]);
                this.g.setExtInfo(ExtInfoConstans.KEY_MULTIMEDIA_LOG_MARK, this.requestCallBackClassName);
            }
        }
    }

    private void a(APFileReq req, NBNetUploadResponse rsp) {
        this.b.setFileReq(req);
        if (rsp == null) {
            this.b.setRetCode(2);
            this.b.setMsg("nbnet response is null");
            this.b.setTraceId("unknown");
            return;
        }
        if (rsp.isSuccess()) {
            this.b.setRetCode(0);
            req.setCloudId(rsp.getFileId());
        } else if (429 == rsp.getErrorCode()) {
            this.b.setRetCode(2000);
            this.b.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
        } else {
            this.b.setRetCode(rsp.getErrorCode());
            this.b.setMsg(rsp.getErrorMsg());
        }
        this.e = rsp.getMd5();
        this.b.setTraceId(rsp.getTraceId());
    }

    public void onUploadStart(NBNetUploadRequest req) {
        a.d("onUploadStart req=" + req.toString(), new Object[0]);
    }

    public void onUploadProgress(NBNetUploadRequest req, int progress, int fileLength, int uploadedFileLength) {
        if (!this.callbacks.isEmpty()) {
            this.d = (long) fileLength;
            notifyUploadProgress(this.c, progress, (long) uploadedFileLength, (long) fileLength);
        }
    }

    public void onUploadFinished(NBNetUploadRequest req, NBNetUploadResponse rsp) {
        a.d("onUploadFinished rsp=" + rsp, new Object[0]);
    }

    public void onUploadError(NBNetUploadRequest req, int code, String errorMessage) {
        a.d("onUploadError code=" + code + ";errorMessage=" + errorMessage, new Object[0]);
    }

    public void cancel() {
        if (this.h != null) {
            this.h.cancel(true);
        }
        super.cancel();
    }
}
