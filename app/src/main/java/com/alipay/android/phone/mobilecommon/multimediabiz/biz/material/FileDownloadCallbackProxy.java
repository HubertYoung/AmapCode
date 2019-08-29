package com.alipay.android.phone.mobilecommon.multimediabiz.biz.material;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadError;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadProgress;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;

public class FileDownloadCallbackProxy implements APFileDownCallback {
    private static final Logger a = Logger.getLogger((String) "DLCBPrx");
    private APDownloadStatus b;
    private MaterialDownloadListenerHandler c;
    private APMaterialInfo d;
    private MaterialManager e;
    private APMaterialDownloadRequest f;

    public FileDownloadCallbackProxy(MaterialManager mgr, APMaterialDownloadRequest request, APMaterialInfo materialInfo, MaterialDownloadListenerHandler handler, APDownloadStatus status) {
        this.e = mgr;
        this.f = request;
        this.d = materialInfo;
        this.c = handler;
        this.b = status;
    }

    public void onDownloadStart(APMultimediaTaskModel taskInfo) {
        a.d("onDownloadStart taskInfo: " + taskInfo, new Object[0]);
        this.b.setStatus(1);
    }

    public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
        if (progress >= 95 || progress <= 5) {
            a.d("onDownloadProgress taskInfo: " + taskInfo + ", progress: " + progress + ", hasDownSize: " + hasDownSize + ", total: " + total, new Object[0]);
        } else {
            a.v("onDownloadProgress taskInfo: " + taskInfo + ", progress: " + progress + ", hasDownSize: " + hasDownSize + ", total: " + total, new Object[0]);
        }
        this.b.setStatus(1);
        this.b.setPercent(progress);
        this.b.setProcessSize(hasDownSize);
        this.b.setTotalSize(total);
        APDownloadProgress downloadProgress = new APDownloadProgress(progress, hasDownSize, total);
        downloadProgress.mDownloadRequest = this.f;
        downloadProgress.mMaterialInfo = this.d;
        this.c.handleProgress(taskInfo.getTaskId(), downloadProgress);
    }

    public void onDownloadBatchProgress(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
    }

    public void onDownloadFinished(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
        a.d("onDownloadFinished taskInfo: " + taskInfo + ", rsp: " + rsp, new Object[0]);
        try {
            String tmpSavePath = rsp.getFileReq().getSavePath();
            boolean md5Ok = a(tmpSavePath);
            if (!md5Ok || !this.e.saveMaterialResource(this.d, this.f, tmpSavePath)) {
                this.b.setStatus(3);
                APDownloadError downloadError = new APDownloadError();
                downloadError.mDownloadRequest = this.f;
                downloadError.mMaterialInfo = this.d;
                if (md5Ok) {
                    downloadError.errorCode = 10002;
                    downloadError.msg = "check by falcon failed";
                } else {
                    downloadError.errorCode = 10000;
                    downloadError.msg = "check md5 error";
                }
                this.c.handleError(taskInfo.getTaskId(), downloadError);
            } else {
                this.b.setStatus(4);
                APDownloadComplete downloadComplete = new APDownloadComplete();
                downloadComplete.mDownloadRequest = this.f;
                downloadComplete.mMaterialInfo = this.d;
                this.c.handleComplete(taskInfo.getTaskId(), downloadComplete);
            }
        } finally {
            this.e.removeDownloadTask(this.f);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0067 A[SYNTHETIC, Splitter:B:16:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0039 A[Catch:{ all -> 0x005e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDownloadError(com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel r7, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp r8) {
        /*
            r6 = this;
            r5 = 2
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "onDownloadError taskInfo: "
            r3.<init>(r4)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r4 = ", rsp: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r3 = r3.toString()
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r2.e(r3, r4)
            if (r8 == 0) goto L_0x0057
            int r2 = r8.getRetCode()     // Catch:{ all -> 0x005e }
            r3 = 5
            if (r2 != r3) goto L_0x0057
            com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus r2 = r6.b     // Catch:{ all -> 0x005e }
            r3 = 2
            r2.setStatus(r3)     // Catch:{ all -> 0x005e }
        L_0x0031:
            com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus r2 = r6.b     // Catch:{ all -> 0x005e }
            int r2 = r2.getStatus()     // Catch:{ all -> 0x005e }
            if (r5 != r2) goto L_0x0067
            com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadCancel r0 = new com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadCancel     // Catch:{ all -> 0x005e }
            r0.<init>()     // Catch:{ all -> 0x005e }
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest r2 = r6.f     // Catch:{ all -> 0x005e }
            r0.mDownloadRequest = r2     // Catch:{ all -> 0x005e }
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo r2 = r6.d     // Catch:{ all -> 0x005e }
            r0.mMaterialInfo = r2     // Catch:{ all -> 0x005e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.MaterialDownloadListenerHandler r2 = r6.c     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r7.getTaskId()     // Catch:{ all -> 0x005e }
            r2.handleCancel(r3, r0)     // Catch:{ all -> 0x005e }
        L_0x004f:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.MaterialManager r2 = r6.e
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest r3 = r6.f
            r2.removeDownloadTask(r3)
            return
        L_0x0057:
            com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus r2 = r6.b     // Catch:{ all -> 0x005e }
            r3 = 3
            r2.setStatus(r3)     // Catch:{ all -> 0x005e }
            goto L_0x0031
        L_0x005e:
            r2 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.MaterialManager r3 = r6.e
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest r4 = r6.f
            r3.removeDownloadTask(r4)
            throw r2
        L_0x0067:
            com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadError r1 = new com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadError     // Catch:{ all -> 0x005e }
            r1.<init>()     // Catch:{ all -> 0x005e }
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest r2 = r6.f     // Catch:{ all -> 0x005e }
            r1.mDownloadRequest = r2     // Catch:{ all -> 0x005e }
            com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo r2 = r6.d     // Catch:{ all -> 0x005e }
            r1.mMaterialInfo = r2     // Catch:{ all -> 0x005e }
            if (r8 == 0) goto L_0x0082
            int r2 = r8.getRetCode()     // Catch:{ all -> 0x005e }
            r1.errorCode = r2     // Catch:{ all -> 0x005e }
            java.lang.String r2 = r8.getMsg()     // Catch:{ all -> 0x005e }
            r1.msg = r2     // Catch:{ all -> 0x005e }
        L_0x0082:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.MaterialDownloadListenerHandler r2 = r6.c     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r7.getTaskId()     // Catch:{ all -> 0x005e }
            r2.handleError(r3, r1)     // Catch:{ all -> 0x005e }
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.material.FileDownloadCallbackProxy.onDownloadError(com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp):void");
    }

    private boolean a(String path) {
        try {
            if (TextUtils.isEmpty(this.d.md5) || this.d.md5.equalsIgnoreCase(MD5Util.getFileMD5String(new File(path)))) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            a.e(e2, "checkMd5 fail, md5: " + this.d.md5 + ", path: " + path + ", fileMd5: " + null, new Object[0]);
            return false;
        }
    }
}
