package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APRequestParam;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool.FileSecurityReport;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APFileTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.mobile.framework.service.ext.security.AuthService;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FileMMTask extends MMTask<APFileRsp> {
    protected static final String TASK_CANCELED = "multimedia_file_task_canceled";
    private static final Logger d = Logger.getLogger((String) "FileMMTask");
    private final APRequestParam a = new APRequestParam("ACL", "UID");
    private DjangoClient b;
    protected String bizType;
    private volatile boolean c = false;
    private boolean e = true;
    protected List fileReqList;
    protected Context mContext;
    protected String md5;
    protected String name;
    protected String requestCallBackClassName;
    protected String taskId;
    protected APMultimediaTaskModel taskInfo;

    protected FileMMTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo2) {
        this.mContext = context;
        this.fileReqList = reqList;
        this.taskInfo = taskInfo2;
        this.taskId = taskInfo2.getTaskId();
        this.bizType = (reqList == null || reqList.isEmpty()) ? APConstants.DEFAULT_BUSINESS : reqList.get(0).getBizType();
        setPriority((reqList == null || reqList.isEmpty()) ? 5 : reqList.get(0).getPriority());
    }

    /* access modifiers changed from: protected */
    public void clearOldFileIfNotEnough() {
        CacheContext.get().getDiskCache().trim();
    }

    /* access modifiers changed from: protected */
    public void deleteFileInner(String savePath) {
        try {
            FileUtils.deleteFileByPath(savePath);
        } catch (Exception e2) {
            d.e(e2, "deleteFile error: " + savePath, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public boolean copyFile(String srcPath, String destPath) {
        OutputStream out;
        d.d("copyFile from " + srcPath + " to " + destPath, new Object[0]);
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(destPath) || srcPath.equals(destPath)) {
            return false;
        }
        File src = new File(srcPath);
        File dst = new File(destPath);
        if (!src.exists() || !src.isFile()) {
            return false;
        }
        if (dst.exists() && dst.isFile()) {
            return false;
        }
        InputStream input = null;
        OutputStream out2 = null;
        try {
            dst.getParentFile().mkdirs();
            InputStream input2 = new FileInputStream(src);
            try {
                out = new FileOutputStream(dst);
            } catch (IOException e2) {
                e = e2;
                input = input2;
                try {
                    d.e(e, "", new Object[0]);
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(out2);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(out2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                input = input2;
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                throw th;
            }
            try {
                IOUtils.copy(input2, out);
                IOUtils.closeQuietly(input2);
                IOUtils.closeQuietly(out);
                return true;
            } catch (IOException e3) {
                e = e3;
                out2 = out;
                input = input2;
                d.e(e, "", new Object[0]);
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                return false;
            } catch (Throwable th3) {
                th = th3;
                out2 = out;
                input = input2;
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(out2);
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            d.e(e, "", new Object[0]);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(out2);
            return false;
        }
    }

    public static boolean removeCacheFile(APFileReq req) {
        d.d("removeCacheFile req: " + req, new Object[0]);
        if (req != null && !TextUtils.isEmpty(req.getCloudId())) {
            CacheContext.get().getDiskCache().remove(req.getCloudId());
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b4, code lost:
        if (copyFile(r25.getSavePath(), r5) != false) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0127, code lost:
        if (copyFile(r25.getSavePath(), r5) != false) goto L_0x0129;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String addCacheFile(com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq r25) {
        /*
            r24 = this;
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r3 = d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "addCacheFile req: "
            r6.<init>(r7)
            r0 = r25
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r3.d(r6, r7)
            if (r25 == 0) goto L_0x0167
            boolean r3 = r25.isNeedCache()
            if (r3 == 0) goto L_0x0167
            java.lang.String r3 = r25.getCloudId()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0167
            java.lang.String r3 = r25.getSavePath()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0167
            java.io.File r3 = new java.io.File
            java.lang.String r6 = r25.getSavePath()
            r3.<init>(r6)
            boolean r3 = r3.exists()
            if (r3 == 0) goto L_0x0167
            r24.clearOldFileIfNotEnough()
            java.lang.String r3 = r25.getCloudId()
            boolean r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils.isEncryptedMusic(r3)
            if (r3 == 0) goto L_0x010d
            com.alipay.mobile.framework.MicroApplicationContext r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getMicroApplicationContext()
            java.lang.Class<com.alipay.mobile.framework.service.ext.security.AuthService> r6 = com.alipay.mobile.framework.service.ext.security.AuthService.class
            java.lang.String r6 = r6.getName()
            java.lang.Object r2 = r3.findServiceByInterface(r6)
            com.alipay.mobile.framework.service.ext.security.AuthService r2 = (com.alipay.mobile.framework.service.ext.security.AuthService) r2
            r23 = 0
            if (r2 == 0) goto L_0x0073
            com.alipay.mobile.framework.service.ext.security.bean.UserInfo r3 = r2.getUserInfo()
            if (r3 == 0) goto L_0x0073
            com.alipay.mobile.framework.service.ext.security.bean.UserInfo r3 = r2.getUserInfo()
            java.lang.String r23 = r3.getUserId()
        L_0x0073:
            boolean r3 = android.text.TextUtils.isEmpty(r23)
            if (r3 == 0) goto L_0x007b
            java.lang.String r23 = ""
        L_0x007b:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = r25.getCloudId()
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r6 = "_"
            java.lang.StringBuilder r3 = r3.append(r6)
            r0 = r23
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r4 = r3.toString()
            r0 = r24
            java.lang.String r5 = r0.getCachePathByCloudId(r4)
            if (r5 == 0) goto L_0x00aa
            java.lang.String r3 = r25.getSavePath()
            boolean r3 = r5.equalsIgnoreCase(r3)
            if (r3 != 0) goto L_0x00b6
        L_0x00aa:
            java.lang.String r3 = r25.getSavePath()
            r0 = r24
            boolean r3 = r0.copyFile(r3, r5)
            if (r3 == 0) goto L_0x0167
        L_0x00b6:
            r18 = 0
            android.content.Context r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getApplicationContext()
            com.taobao.wireless.security.sdk.SecurityGuardManager r21 = com.taobao.wireless.security.sdk.SecurityGuardManager.getInstance(r3)
            if (r21 == 0) goto L_0x00d0
            com.taobao.wireless.security.sdk.staticdatastore.IStaticDataStoreComponent r20 = r21.getStaticDataStoreComp()
            if (r20 == 0) goto L_0x00d0
            java.lang.String r3 = "mdaeskey"
            r0 = r20
            java.lang.String r18 = r0.getExtraData(r3)
        L_0x00d0:
            java.lang.String r8 = ""
            boolean r3 = android.text.TextUtils.isEmpty(r18)
            if (r3 != 0) goto L_0x00f6
            r0 = r18
            java.lang.String r22 = com.alipay.multimedia.utils.AESUtils.encryptStr(r0, r4)
            com.alibaba.fastjson.JSONObject r19 = new com.alibaba.fastjson.JSONObject
            r19.<init>()
            boolean r3 = android.text.TextUtils.isEmpty(r22)
            if (r3 != 0) goto L_0x00f2
            java.lang.String r3 = "sign"
            r0 = r19
            r1 = r22
            r0.put(r3, r1)
        L_0x00f2:
            java.lang.String r8 = r19.toJSONString()
        L_0x00f6:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext.get()
            com.alipay.diskcache.DiskCache r3 = r3.getDiskCache()
            r6 = 4
            r7 = 2048(0x800, float:2.87E-42)
            java.lang.String r9 = r25.getBusinessId()
            long r10 = r25.getExpiredTime()
            r3.save(r4, r5, r6, r7, r8, r9, r10)
        L_0x010c:
            return r5
        L_0x010d:
            java.lang.String r5 = r24.getCachePathByCloudId(r25)
            if (r5 == 0) goto L_0x011d
            java.lang.String r3 = r25.getSavePath()
            boolean r3 = r5.equalsIgnoreCase(r3)
            if (r3 != 0) goto L_0x0129
        L_0x011d:
            java.lang.String r3 = r25.getSavePath()
            r0 = r24
            boolean r3 = r0.copyFile(r3, r5)
            if (r3 == 0) goto L_0x0167
        L_0x0129:
            boolean r3 = r25.isEncrypt()
            if (r3 == 0) goto L_0x014c
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext.get()
            com.alipay.diskcache.DiskCache r9 = r3.getDiskCache()
            java.lang.String r10 = r25.getCloudId()
            r12 = 4
            r13 = 2048(0x800, float:2.87E-42)
            r14 = 0
            java.lang.String r15 = r25.getBusinessId()
            long r16 = r25.getExpiredTime()
            r11 = r5
            r9.save(r10, r11, r12, r13, r14, r15, r16)
            goto L_0x010c
        L_0x014c:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext r3 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext.get()
            com.alipay.diskcache.DiskCache r9 = r3.getDiskCache()
            java.lang.String r10 = r25.getCloudId()
            r11 = 4
            r12 = 2048(0x800, float:2.87E-42)
            java.lang.String r13 = r25.getBusinessId()
            long r14 = r25.getExpiredTime()
            r9.save(r10, r11, r12, r13, r14)
            goto L_0x010c
        L_0x0167:
            java.lang.String r5 = ""
            goto L_0x010c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileMMTask.addCacheFile(com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public boolean encryptFile(APFileReq req) {
        String sourcePath = req.getSavePath();
        if (TextUtils.isEmpty(sourcePath)) {
            return false;
        }
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            return false;
        }
        FileSecurityReport report = new FileSecurityReport();
        report.operationType = 0;
        report.url = req.getCloudId();
        report.business = req.getBusinessId();
        String tmpPath = sourcePath + FilePathHelper.SUFFIX_DOT_TMP;
        if (!FileSecurityTool.encryptFile(this.mContext, sourcePath, tmpPath, report)) {
            return false;
        }
        sourceFile.delete();
        new File(tmpPath).renameTo(sourceFile);
        return true;
    }

    /* access modifiers changed from: protected */
    public String getCachePathByCloudId(String cloudid) {
        String path = CacheContext.get().getDiskCache().genPathByKey(cloudid);
        new File(path).getParentFile().mkdirs();
        return path;
    }

    /* access modifiers changed from: protected */
    public String getCachePathByCloudId(APFileReq req) {
        if (req == null || TextUtils.isEmpty(req.getCloudId())) {
            return null;
        }
        String path = CacheContext.get().getDiskCache().genPathByKey(req.getCloudId());
        if (req.isEncrypt()) {
            path = path + ".enc";
        }
        new File(path).getParentFile().mkdirs();
        return path;
    }

    /* access modifiers changed from: protected */
    public String checkCacheFile(APFileReq req) {
        d.d("checkCacheFile req: " + req, new Object[0]);
        String cachePath = getCachePathByCloudId(req);
        if (TextUtils.isEmpty(cachePath)) {
            return null;
        }
        File dst = new File(cachePath);
        if (!dst.exists() || !dst.isFile() || dst.length() <= 0) {
            return null;
        }
        if (!com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFileByMd5(req.getMd5(), cachePath, true)) {
            d.i("checkCacheFile  md5 not match", new Object[0]);
            return null;
        }
        d.i("checkCacheFile return true req:" + req, new Object[0]);
        return cachePath;
    }

    /* access modifiers changed from: protected */
    public boolean checkFileCurrentLimit() {
        return ConfigManager.getInstance().getIntValue(ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, 0) >= 3;
    }

    /* access modifiers changed from: protected */
    public String getSavePath(APFileReq req) {
        String path;
        d.d("generateSavePath info: " + req, new Object[0]);
        if (req != null && !TextUtils.isEmpty(req.getSavePath())) {
            path = req.getSavePath();
        } else if (req == null || TextUtils.isEmpty(req.getCloudId())) {
            r3 = "";
            return "";
        } else if (PathUtils.isEncryptedMusic(req.getCloudId())) {
            AuthService authService = (AuthService) AppUtils.getMicroApplicationContext().findServiceByInterface(AuthService.class.getName());
            String uid = null;
            if (!(authService == null || authService.getUserInfo() == null)) {
                uid = authService.getUserInfo().getUserId();
            }
            if (TextUtils.isEmpty(uid)) {
                uid = "";
            }
            path = getCachePathByCloudId(req.getCloudId() + "_" + uid);
        } else {
            path = getCachePathByCloudId(req);
        }
        File pathFile = new File(path);
        if (!pathFile.getParentFile().exists()) {
            d.i("generateSavePath mkdirs return : " + pathFile.getParentFile().mkdirs(), new Object[0]);
        }
        d.d("generateSavePath path: " + path, new Object[0]);
        return path;
    }

    /* access modifiers changed from: protected */
    public DjangoClient getDjangoClient(APRequestParam param) {
        if (this.b == null) {
            synchronized (this) {
                try {
                    if (this.b == null) {
                        if (param == null) {
                            param = this.a;
                        }
                        ConnectionManager conMgr = new HttpConnectionManager();
                        if (param != null) {
                            conMgr.setAcl(param.getACL());
                            conMgr.setUid(param.getUID());
                        }
                        this.b = new HttpDjangoClient(this.mContext, conMgr);
                    }
                }
            }
        }
        return this.b;
    }

    public boolean isCanceled() {
        boolean ret = this.c || Thread.currentThread().isInterrupted();
        if (ret) {
            cancel();
        }
        return ret;
    }

    public void cancel() {
        this.c = true;
        if (this.taskInfo != null) {
            this.taskInfo.setStatus(2);
        }
    }

    /* access modifiers changed from: protected */
    public void checkCanceled() {
        if (isCanceled()) {
            throw new RuntimeException(TASK_CANCELED);
        }
    }

    public void onMergeTask(MMTask task) {
    }

    public APFileRsp taskRun() {
        this.e = CommonUtils.isActiveNetwork(this.mContext);
        d.i("FileMMTask taskRun", new Object[0]);
        if (this.e || !CommonUtils.isNeedCheckActiveNet(this instanceof FileDownloadMMTask)) {
            return null;
        }
        APFileRsp rsp = new APFileRsp();
        rsp.setRetCode(9);
        rsp.setMsg("has no network");
        return rsp;
    }

    public void onStateChange(int state) {
    }

    public String getTaskId() {
        if (!TextUtils.isEmpty(this.taskId)) {
            return this.taskId;
        }
        throw new RuntimeException("File task id can not be null!!!");
    }

    /* access modifiers changed from: protected */
    public APMultimediaTaskModel updateTaskModelStatus(APMultimediaTaskModel taskInfo2, int status) {
        if (taskInfo2 == null) {
            return null;
        }
        taskInfo2.setStatus(status);
        return updateTaskRecord(taskInfo2);
    }

    /* access modifiers changed from: protected */
    public APMultimediaTaskModel updateTaskRecord(APMultimediaTaskModel taskInfo2) {
        return APFileTaskManager.getInstance(this.mContext).updateTaskRecord(taskInfo2);
    }

    /* access modifiers changed from: protected */
    public APMultimediaTaskModel removeTaskRecord(String taskId2) {
        return APFileTaskManager.getInstance(this.mContext).delTaskRecord(taskId2);
    }

    public APMultimediaTaskModel getTaskInfo() {
        return this.taskInfo;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedUCLog(APFileReq req) {
        return req == null || req.getCallGroup() == 1000;
    }

    /* access modifiers changed from: protected */
    public boolean isNoNetwork(int ret) {
        return !this.e && ret != 0;
    }
}
