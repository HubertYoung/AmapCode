package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ChunkTransferredListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.BaseUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnCommitReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnOpenReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnProcessReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.InputStreamUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnCommitResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnOpenResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnProcessResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HttpFileUpMMTask extends FileUpMMTask {
    public static final long BIG_FILE_SIZE_THRESHOLD = 10485760;
    /* access modifiers changed from: private */
    public static final Logger d = Logger.getLogger((String) "HttpFileUpMMTask");
    /* access modifiers changed from: private */
    public AtomicInteger a = new AtomicInteger(-1);
    /* access modifiers changed from: private */
    public AtomicLong b = new AtomicLong(0);
    private String c;
    private boolean e = false;
    private int f;

    private class ChunkTransListener implements ChunkTransferredListener {
        private DjangoClient b;
        private long c;
        private long d = 0;
        private ChunkUpTxnProcessReq e;

        public ChunkTransListener(DjangoClient djangoClient, ChunkUpTxnProcessReq req, long totalBytes) {
            this.b = djangoClient;
            this.e = req;
            this.c = totalBytes;
        }

        public void onChunkTransferred(int chunkSequence, long transferredCount) {
            if (!(!HttpFileUpMMTask.this.isCanceled() || this.b == null || this.b.getConnectionManager() == null)) {
                this.e.abort();
            }
            HttpFileUpMMTask.this.checkCanceled();
            int progress = (int) ((((float) HttpFileUpMMTask.this.b.addAndGet(transferredCount - this.d)) * 100.0f) / ((float) this.c));
            this.d = transferredCount;
            if (HttpFileUpMMTask.this.a.get() != progress) {
                HttpFileUpMMTask.d.d("onChunkTransferred progress:  " + progress + ", name: " + HttpFileUpMMTask.this.name, new Object[0]);
                HttpFileUpMMTask.this.a.set(progress);
                HttpFileUpMMTask.this.notifyUploadProgress(HttpFileUpMMTask.this.taskInfo, progress, HttpFileUpMMTask.this.b.get(), this.c);
            }
        }
    }

    public HttpFileUpMMTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo, APFileUploadCallback callback) {
        super(context, reqList, taskInfo);
        this.callbacks.add(callback);
        setTag("HttpFileUpMMTask");
    }

    private APFileUploadRsp a(List reqList) {
        d.d("uploadSync start reqList size = " + reqList.size(), new Object[0]);
        notifyUploadStart(this.taskInfo);
        APFileUploadRsp rsp = new APFileUploadRsp();
        try {
            a(reqList, rsp);
        } catch (RuntimeException e1) {
            if ("multimedia_file_task_canceled".equals(e1.getMessage())) {
                rsp.setRetCode(5);
                rsp.setMsg(e1.getMessage());
            } else {
                d.e(e1, "", new Object[0]);
                rsp.setRetCode(1);
                rsp.setMsg(e1.getMessage());
            }
        } catch (Exception e2) {
            d.e(e2, "", new Object[0]);
            rsp.setRetCode(1);
            rsp.setMsg(e2.getMessage());
        } finally {
            rsp.setTraceId(this.c);
        }
        if (isCanceled() || 5 == this.taskInfo.getStatus()) {
            rsp.setRetCode(5);
            rsp.setMsg("multimedia_file_task_canceled");
        }
        if (!this.callbacks.isEmpty()) {
            if (rsp.getRetCode() == 0) {
                if (isNeedUCLog(rsp.getFileReq())) {
                    copyToCache(rsp.getFileReq().getCloudId(), rsp.getFileReq().getSavePath(), rsp.getFileReq().businessId);
                }
                notifyUploadFinish(this.taskInfo, rsp);
            } else {
                rsp.getRetCode();
                notifyUploadError(this.taskInfo, rsp);
            }
        }
        return rsp;
    }

    private void a(List reqList, APFileUploadRsp rsp) {
        APFileReq info = (APFileReq) reqList.get(0);
        rsp.setFileReq(info);
        a(info, rsp);
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01f1  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01f8  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x020d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq r24, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp r25) {
        /*
            r23 = this;
            long r18 = java.lang.System.currentTimeMillis()
            r4 = 0
            r16 = 0
            r15 = 0
            byte[] r22 = r24.getUploadData()     // Catch:{ Exception -> 0x01ca }
            r14 = 0
            if (r22 != 0) goto L_0x00a4
            java.io.File r14 = new java.io.File     // Catch:{ Exception -> 0x01ca }
            java.lang.String r3 = r24.getSavePath()     // Catch:{ Exception -> 0x01ca }
            java.lang.String r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils.extractPath(r3)     // Catch:{ Exception -> 0x01ca }
            r14.<init>(r3)     // Catch:{ Exception -> 0x01ca }
            long r20 = r14.length()     // Catch:{ Exception -> 0x01ca }
        L_0x0021:
            r4 = r20
            r10 = 0
            int r3 = (r20 > r10 ? 1 : (r20 == r10 ? 0 : -1))
            if (r3 <= 0) goto L_0x0241
            if (r22 != 0) goto L_0x00ac
            java.lang.String r15 = com.alipay.mobile.common.utils.MD5Util.getFileMD5String(r14)     // Catch:{ Exception -> 0x00bb }
        L_0x002f:
            boolean r3 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x00bb }
            if (r3 == 0) goto L_0x013f
            r3 = 4
            r0 = r25
            r0.setRetCode(r3)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r3 = "fileUpResp calc md5 fail"
            r0 = r25
            r0.setMsg(r3)     // Catch:{ Exception -> 0x00bb }
            int r3 = r25.getRetCode()
            java.lang.String r17 = java.lang.String.valueOf(r3)
            r9 = 0
            int r3 = r25.getRetCode()
            if (r3 == 0) goto L_0x00b2
            r9 = r15
        L_0x0052:
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x0060
            r0 = r23
            int r3 = r0.f
            java.lang.String r17 = java.lang.String.valueOf(r3)
        L_0x0060:
            java.lang.String r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.KEY_MD5
            r0 = r25
            r0.addExtra(r3, r15)
            boolean r3 = r23.isNeedUCLog(r24)
            if (r3 == 0) goto L_0x00a1
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x00b8
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "s"
            r3.<init>(r6)
            r0 = r17
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
        L_0x0084:
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r18
            int r6 = (int) r10
            r7 = 0
            r0 = r23
            java.lang.String r8 = r0.c
            r0 = r23
            java.lang.String r10 = r0.bizType
            int r11 = r25.getRetCode()
            r0 = r23
            boolean r11 = r0.isNoNetwork(r11)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C03(r3, r4, r6, r7, r8, r9, r10, r11)
        L_0x00a1:
            r7 = r16
        L_0x00a3:
            return
        L_0x00a4:
            r0 = r22
            int r3 = r0.length     // Catch:{ Exception -> 0x01ca }
            long r0 = (long) r3
            r20 = r0
            goto L_0x0021
        L_0x00ac:
            java.lang.String r15 = com.alipay.mobile.common.utils.MD5Util.getMD5String(r22)     // Catch:{ Exception -> 0x00bb }
            goto L_0x002f
        L_0x00b2:
            r3 = 0
            r0 = r23
            r0.e = r3
            goto L_0x0052
        L_0x00b8:
            r3 = r17
            goto L_0x0084
        L_0x00bb:
            r2 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = d     // Catch:{ Exception -> 0x01ca }
            java.lang.String r6 = ""
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x01ca }
            r3.e(r2, r6, r8)     // Catch:{ Exception -> 0x01ca }
            r3 = 4
            r0 = r25
            r0.setRetCode(r3)     // Catch:{ Exception -> 0x01ca }
            java.lang.String r3 = "fileUpResp calc md5 exception"
            r0 = r25
            r0.setMsg(r3)     // Catch:{ Exception -> 0x01ca }
            int r3 = r25.getRetCode()
            java.lang.String r17 = java.lang.String.valueOf(r3)
            r9 = 0
            int r3 = r25.getRetCode()
            if (r3 == 0) goto L_0x0136
            r9 = r15
        L_0x00e3:
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x00f1
            r0 = r23
            int r3 = r0.f
            java.lang.String r17 = java.lang.String.valueOf(r3)
        L_0x00f1:
            java.lang.String r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.KEY_MD5
            r0 = r25
            r0.addExtra(r3, r15)
            boolean r3 = r23.isNeedUCLog(r24)
            if (r3 == 0) goto L_0x0132
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x013c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "s"
            r3.<init>(r6)
            r0 = r17
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
        L_0x0115:
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r18
            int r6 = (int) r10
            r7 = 0
            r0 = r23
            java.lang.String r8 = r0.c
            r0 = r23
            java.lang.String r10 = r0.bizType
            int r11 = r25.getRetCode()
            r0 = r23
            boolean r11 = r0.isNoNetwork(r11)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C03(r3, r4, r6, r7, r8, r9, r10, r11)
        L_0x0132:
            r7 = r16
            goto L_0x00a3
        L_0x0136:
            r3 = 0
            r0 = r23
            r0.e = r3
            goto L_0x00e3
        L_0x013c:
            r3 = r17
            goto L_0x0115
        L_0x013f:
            r23.checkCanceled()     // Catch:{ Exception -> 0x01ca }
            com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam r3 = r24.getRequestParam()     // Catch:{ Exception -> 0x01ca }
            r0 = r23
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient r13 = r0.getDjangoClient(r3)     // Catch:{ Exception -> 0x01ca }
            r10 = 10485760(0xa00000, double:5.180654E-317)
            int r3 = (r20 > r10 ? 1 : (r20 == r10 ? 0 : -1))
            if (r3 < 0) goto L_0x01c0
            r10 = r23
            r11 = r24
            r12 = r25
            r10.b(r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x01ca }
        L_0x015c:
            java.lang.String r7 = r25.getMsg()     // Catch:{ Exception -> 0x01ca }
        L_0x0160:
            int r3 = r25.getRetCode()
            java.lang.String r17 = java.lang.String.valueOf(r3)
            r9 = 0
            int r3 = r25.getRetCode()
            if (r3 == 0) goto L_0x0252
            r9 = r15
        L_0x0170:
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x017e
            r0 = r23
            int r3 = r0.f
            java.lang.String r17 = java.lang.String.valueOf(r3)
        L_0x017e:
            java.lang.String r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.KEY_MD5
            r0 = r25
            r0.addExtra(r3, r15)
            boolean r3 = r23.isNeedUCLog(r24)
            if (r3 == 0) goto L_0x00a3
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x025a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "s"
            r3.<init>(r6)
            r0 = r17
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
        L_0x01a2:
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r18
            int r6 = (int) r10
            r0 = r23
            java.lang.String r8 = r0.c
            r0 = r23
            java.lang.String r10 = r0.bizType
            int r11 = r25.getRetCode()
            r0 = r23
            boolean r11 = r0.isNoNetwork(r11)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C03(r3, r4, r6, r7, r8, r9, r10, r11)
            goto L_0x00a3
        L_0x01c0:
            r10 = r23
            r11 = r24
            r12 = r25
            r10.a(r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x01ca }
            goto L_0x015c
        L_0x01ca:
            r2 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = d     // Catch:{ all -> 0x0268 }
            java.lang.String r6 = ""
            r8 = 0
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0268 }
            r3.e(r2, r6, r8)     // Catch:{ all -> 0x0268 }
            java.lang.String r7 = r2.getMessage()     // Catch:{ all -> 0x0268 }
            r3 = 1
            r0 = r25
            r0.setRetCode(r3)     // Catch:{ all -> 0x01e0 }
            throw r2     // Catch:{ all -> 0x01e0 }
        L_0x01e0:
            r3 = move-exception
            r12 = r3
        L_0x01e2:
            int r3 = r25.getRetCode()
            java.lang.String r17 = java.lang.String.valueOf(r3)
            r9 = 0
            int r3 = r25.getRetCode()
            if (r3 == 0) goto L_0x025e
            r9 = r15
        L_0x01f2:
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x0200
            r0 = r23
            int r3 = r0.f
            java.lang.String r17 = java.lang.String.valueOf(r3)
        L_0x0200:
            java.lang.String r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.KEY_MD5
            r0 = r25
            r0.addExtra(r3, r15)
            boolean r3 = r23.isNeedUCLog(r24)
            if (r3 == 0) goto L_0x0240
            r0 = r23
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x0265
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "s"
            r3.<init>(r6)
            r0 = r17
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
        L_0x0224:
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r18
            int r6 = (int) r10
            r0 = r23
            java.lang.String r8 = r0.c
            r0 = r23
            java.lang.String r10 = r0.bizType
            int r11 = r25.getRetCode()
            r0 = r23
            boolean r11 = r0.isNoNetwork(r11)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_C03(r3, r4, r6, r7, r8, r9, r10, r11)
        L_0x0240:
            throw r12
        L_0x0241:
            r3 = 3
            r0 = r25
            r0.setRetCode(r3)     // Catch:{ Exception -> 0x01ca }
            java.lang.String r3 = "local file size is zero"
            r0 = r25
            r0.setMsg(r3)     // Catch:{ Exception -> 0x01ca }
            r7 = r16
            goto L_0x0160
        L_0x0252:
            r7 = 0
            r3 = 0
            r0 = r23
            r0.e = r3
            goto L_0x0170
        L_0x025a:
            r3 = r17
            goto L_0x01a2
        L_0x025e:
            r7 = 0
            r3 = 0
            r0 = r23
            r0.e = r3
            goto L_0x01f2
        L_0x0265:
            r3 = r17
            goto L_0x0224
        L_0x0268:
            r3 = move-exception
            r12 = r3
            r7 = r16
            goto L_0x01e2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask.a(com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq, com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp):void");
    }

    private void a(APFileReq info, APFileUploadRsp rsp, DjangoClient djangoClient, File file, String md5) {
        byte[] uploadData = info.getUploadData();
        final long totalBytes = uploadData != null ? (long) uploadData.length : file.length();
        final BaseUpReq upReq = uploadData != null ? new InputStreamUpReq(new ByteArrayInputStream(uploadData), md5, null, totalBytes) : new FileUpReq(file, null);
        final DjangoClient djangoClient2 = djangoClient;
        upReq.setTransferedListener(new TransferredListener() {
            public void onTransferred(long transferredCount) {
                if (!(!HttpFileUpMMTask.this.isCanceled() || djangoClient2 == null || djangoClient2.getConnectionManager() == null)) {
                    upReq.abort();
                }
                HttpFileUpMMTask.this.checkCanceled();
                int progress = (int) ((((float) transferredCount) * 100.0f) / ((float) totalBytes));
                if (HttpFileUpMMTask.this.a.get() != progress) {
                    HttpFileUpMMTask.d.d("onTransferred progress:  " + progress + ", name: " + HttpFileUpMMTask.this.name, new Object[0]);
                    HttpFileUpMMTask.this.a.set(progress);
                    HttpFileUpMMTask.this.notifyUploadProgress(HttpFileUpMMTask.this.taskInfo, progress, transferredCount, totalBytes);
                }
            }
        });
        upReq.setMd5(md5);
        if (!TextUtils.isEmpty(info.getAliasFileName())) {
            upReq.setExt(DjangoUtils.getExtension(info.getAliasFileName()));
        }
        if (file != null && TextUtils.isEmpty(upReq.getExt())) {
            upReq.setExt(DjangoUtils.getExtension(file.getName()));
        }
        upReq.setPublic(info.getPublic());
        d.d("uploadSmallFile ext: " + upReq.getExt() + ", public: " + upReq.getPublic(), new Object[0]);
        checkCanceled();
        upReq.addExtra(TransportConstants.KEY_UP_MEDIA_TYPE, a(info), true);
        upReq.addExtra("bizId", info.getBizType(), true);
        FileApi fileApi = djangoClient.getFileApi();
        FileUpResp fileUpResp = upReq instanceof FileUpReq ? fileApi.uploadDirect((FileUpReq) upReq) : fileApi.uploadDirect((InputStreamUpReq) upReq);
        d.d("uploadSmallFile fileUpResp: " + fileUpResp, new Object[0]);
        if (fileUpResp == null) {
            rsp.setRetCode(2);
        } else if (fileUpResp.isSuccess()) {
            this.c = fileUpResp.getTraceId();
            if (fileUpResp.isRapid() || (fileUpResp.getFileInfo() != null && upReq.getMd5().equalsIgnoreCase(fileUpResp.getFileInfo().getMd5()))) {
                rsp.setRetCode(fileUpResp.getCode());
                rsp.setMsg(fileUpResp.getMsg());
                info.setCloudId(fileUpResp.getFileInfo().getId());
                rsp.setFileReq(info);
                this.taskInfo.setDestPath(addCacheFile(info));
            } else {
                rsp.setRetCode(6);
                rsp.setMsg("size not match");
            }
        } else if (429 == fileUpResp.getCode()) {
            d.d("upload django file fail by net limit, resp: " + fileUpResp, new Object[0]);
            this.c = fileUpResp.getTraceId();
            this.e = true;
            this.f = fileUpResp.getCode();
            rsp.setRetCode(2000);
            rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
        } else if (DjangoConstant.DJANGO_TIMEOUT == fileUpResp.getCode()) {
            d.d("upload django file fail by timeout, resp: " + fileUpResp, new Object[0]);
            this.c = fileUpResp.getTraceId();
            this.e = true;
            this.f = fileUpResp.getCode();
            rsp.setRetCode(14);
            rsp.setMsg("upload file timeout");
        } else {
            this.c = fileUpResp.getTraceId();
            this.e = true;
            this.f = fileUpResp.getCode();
            rsp.setRetCode(10);
            rsp.setMsg(fileUpResp.getMsg());
        }
        if (rsp.getRetCode() != 0) {
            rsp.addExtra(UCLogUtil.KEY_MD5, md5);
            d.d("uploadSmallFile " + file + ", md5: " + md5 + ", length: " + totalBytes, new Object[0]);
        }
    }

    private static Object a(APFileReq info) {
        switch (info.getCallGroup()) {
            case 1001:
                return "img";
            case 1002:
                return "audio";
            case 1003:
                return "video";
            default:
                return "file";
        }
    }

    private void b(APFileReq info, APFileUploadRsp rsp, DjangoClient djangoClient, File file, String md5) {
        byte[] uploadData = info.getUploadData();
        long totalBytes = uploadData != null ? (long) uploadData.length : file.length();
        ChunkUpTxnOpenReq openReq = new ChunkUpTxnOpenReq(totalBytes);
        openReq.setMd5(md5);
        if (!TextUtils.isEmpty(info.getAliasFileName())) {
            openReq.setExtension(DjangoUtils.getExtension(info.getAliasFileName()));
        }
        if (file != null && TextUtils.isEmpty(openReq.getExtension())) {
            openReq.setExtension(DjangoUtils.getExtension(file.getName()));
        }
        long chunkSize = openReq.getChunkSize();
        ChunkUpTxnOpenResp openResp = djangoClient.getFileApi().uploadChunkOpen(openReq);
        d.d("uploadBigFile openResp: " + openResp, new Object[0]);
        if (openResp == null) {
            rsp.setRetCode(2);
            rsp.setMsg("openResp null");
        } else if (openResp.isSuccess()) {
            String fileId = openResp.getFileInfo().getId();
            if (TextUtils.isEmpty(fileId)) {
                rsp.setRetCode(3);
                rsp.setMsg("fileId empty");
                return;
            } else if (openResp.getFileInfo().getStatus() == 20) {
                rsp.setRetCode(0);
                rsp.setMsg(openResp.getMsg());
                info.setCloudId(fileId);
                rsp.setFileReq(info);
                this.taskInfo.setDestPath(addCacheFile(info));
                return;
            } else {
                if (!a(rsp, djangoClient, info, chunkSize, openResp.getFileInfo().getChunkNumber(), fileId)) {
                    d.d("uploadBigFile chunk " + file + ", md5: " + md5 + ", length: " + totalBytes, new Object[0]);
                    return;
                }
                ChunkUpTxnCommitReq commitReq = new ChunkUpTxnCommitReq(fileId);
                commitReq.addExtra(TransportConstants.KEY_UP_MEDIA_TYPE, a(info), true);
                commitReq.addExtra("bizId", info.getBizType(), true);
                ChunkUpTxnCommitResp commitResp = djangoClient.getFileApi().uploadChunkCommit(commitReq);
                d.d("uploadBigFile commitResp: " + commitResp, new Object[0]);
                if (commitResp == null) {
                    rsp.setRetCode(2);
                    rsp.setMsg("commitResp null");
                } else if (commitResp.isSuccess()) {
                    if (commitResp.getFileInfo() != null) {
                        rsp.setRetCode(0);
                        rsp.setMsg(openResp.getMsg());
                        info.setCloudId(fileId);
                        rsp.setFileReq(info);
                        this.taskInfo.setDestPath(addCacheFile(info));
                    } else {
                        rsp.setRetCode(6);
                        rsp.setMsg("commit fileInfo null");
                    }
                } else if (429 == commitResp.getCode()) {
                    this.e = true;
                    this.f = commitResp.getCode();
                    rsp.setRetCode(2000);
                    rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
                } else if (DjangoConstant.DJANGO_TIMEOUT == openResp.getCode()) {
                    this.e = true;
                    this.f = openResp.getCode();
                    rsp.setRetCode(14);
                    rsp.setMsg("upload big file timeout");
                } else {
                    this.e = true;
                    this.f = commitResp.getCode();
                    rsp.setRetCode(10);
                    rsp.setMsg(commitResp.getMsg());
                }
            }
        } else if (429 == openResp.getCode()) {
            d.d("upload django file fail by net limit, resp: " + openResp, new Object[0]);
            this.c = openResp.getTraceId();
            this.e = true;
            this.f = openResp.getCode();
            rsp.setRetCode(2000);
            rsp.setMsg(ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG);
        } else if (DjangoConstant.DJANGO_TIMEOUT == openResp.getCode()) {
            d.d("upload django big file fail by timeout, resp: " + openResp, new Object[0]);
            this.c = openResp.getTraceId();
            this.e = true;
            this.f = openResp.getCode();
            rsp.setRetCode(14);
            rsp.setMsg("upload big file timeout");
        } else {
            this.e = true;
            this.f = openResp.getCode();
            rsp.setRetCode(10);
            rsp.setMsg(openResp.getMsg());
        }
        if (rsp.getRetCode() != 0) {
            d.d("uploadBigFile " + file + ", md5: " + md5 + ", length: " + totalBytes, new Object[0]);
        }
    }

    private boolean a(APFileUploadRsp rsp, DjangoClient djangoClient, APFileReq info, long chunkSize, int chunkNum, String fileId) {
        ChunkUpTxnProcessReq processReq;
        String chunkMd5;
        byte[] uploadData = info.getUploadData();
        File file = uploadData == null ? new File(info.getSavePath()) : null;
        long totalBytes = uploadData == null ? file.length() : (long) uploadData.length;
        Map chunkUpMd5Map = new ConcurrentHashMap();
        for (int seq = 1; seq <= chunkNum; seq++) {
            if (file != null) {
                try {
                    chunkMd5 = MD5Util.getFileChunkMD5String(file, seq, chunkSize);
                } catch (IOException e2) {
                    d.e(e2, "", new Object[0]);
                }
            } else {
                chunkMd5 = MD5Util.getByteArrayChunkMD5String(uploadData, seq, (int) chunkSize);
            }
            if (TextUtils.isEmpty(chunkMd5)) {
                break;
            }
            chunkUpMd5Map.put(String.valueOf(seq), chunkMd5);
        }
        if (chunkUpMd5Map.size() != chunkNum) {
            rsp.setRetCode(4);
            rsp.setMsg("TxnPro md5 error");
            return false;
        }
        List failList = new ArrayList();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int seq2 = 1; seq2 <= chunkNum; seq2++) {
            if (file != null) {
                processReq = new ChunkUpTxnProcessReq(fileId, file, seq2);
            } else {
                processReq = new ChunkUpTxnProcessReq(fileId, uploadData, seq2);
            }
            processReq.setMd5((String) chunkUpMd5Map.get(String.valueOf(seq2)));
            processReq.setChunkNumber((long) chunkNum);
            processReq.setChunkSize(chunkSize);
            processReq.addExtra(TransportConstants.KEY_UP_MEDIA_TYPE, a(rsp.getFileReq()), true);
            processReq.addExtra("bizId", rsp.getFileReq().getBizType(), true);
            if (seq2 == chunkNum) {
                processReq.setRealChunkSize(totalBytes - (((long) (chunkNum - 1)) * chunkSize));
            } else {
                processReq.setRealChunkSize(chunkSize);
            }
            processReq.setChunkTransListener(new ChunkTransListener(djangoClient, processReq, totalBytes));
            ChunkUpTxnProcessResp processResp = djangoClient.getFileApi().uploadChunkProcess(processReq);
            concurrentHashMap.put(String.valueOf(seq2), processResp);
            if (!processResp.isSuccess()) {
                break;
            }
        }
        if (isCanceled()) {
            rsp.setRetCode(5);
            rsp.setMsg("task canceled");
            return false;
        }
        int errCode = 0;
        Iterator it = concurrentHashMap.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String seqString = (String) it.next();
            ChunkUpTxnProcessResp processResp2 = (ChunkUpTxnProcessResp) concurrentHashMap.get(seqString);
            d.d("uploadBigFile seq: " + seqString + ", processResp: " + processResp2, new Object[0]);
            if (processResp2.isSuccess()) {
                if (!((String) chunkUpMd5Map.get(seqString)).equalsIgnoreCase(processResp2.getData().getMd5())) {
                    failList.add(seqString);
                    errCode = 4;
                    break;
                }
            } else {
                d.d("seq: " + seqString + ", processResp: " + processResp2.getCode() + ", " + processResp2.getMsg(), new Object[0]);
                failList.add(seqString);
                this.e = true;
                this.f = processResp2.getCode();
                if (429 == this.f) {
                    errCode = 2000;
                } else if (DjangoConstant.DJANGO_TIMEOUT == this.f) {
                    errCode = 14;
                } else {
                    errCode = 10;
                }
            }
        }
        if (failList.isEmpty()) {
            return true;
        }
        rsp.setRetCode(errCode);
        rsp.setMsg("some chunk fail, " + failList);
        return false;
    }

    public APFileUploadRsp taskRun() {
        APFileRsp rsp = super.taskRun();
        if (rsp == null) {
            return a(this.fileReqList);
        }
        APFileUploadRsp uploadRsp = new APFileUploadRsp();
        uploadRsp.setRetCode(rsp.getRetCode());
        uploadRsp.setMsg(rsp.getMsg());
        uploadRsp.setFileReq((APFileReq) this.fileReqList.get(0));
        notifyUploadError(this.taskInfo, uploadRsp);
        return uploadRsp;
    }
}
