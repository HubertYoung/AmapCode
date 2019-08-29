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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

public class FileTask implements Callable<APFileRsp> {
    protected static final String TASK_CANCELED = "multimedia_file_task_canceled";
    private static final Logger d = Logger.getLogger((String) "FileTask");
    private final APRequestParam a = new APRequestParam("ACL", "UID");
    private DjangoClient b;
    protected String bizType;
    private volatile boolean c = false;
    private boolean e = true;
    protected List fileReqList;
    protected Context mContext;
    protected String name;
    protected APMultimediaTaskModel taskInfo;

    protected FileTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo2) {
        this.mContext = context;
        this.fileReqList = reqList;
        this.taskInfo = taskInfo2;
        this.bizType = (reqList == null || reqList.isEmpty()) ? APConstants.DEFAULT_BUSINESS : reqList.get(0).getBizType();
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

    /* access modifiers changed from: protected */
    public boolean removeCacheFile(APFileReq req) {
        d.d("removeCacheFile req: " + req, new Object[0]);
        if (req != null && !TextUtils.isEmpty(req.getCloudId())) {
            CacheContext.get().getDiskCache().remove(req.getCloudId());
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public String addCacheFile(APFileReq req) {
        d.d("addCacheFile req: " + req, new Object[0]);
        if (req != null && !TextUtils.isEmpty(req.getCloudId()) && !TextUtils.isEmpty(req.getSavePath()) && new File(req.getSavePath()).exists()) {
            clearOldFileIfNotEnough();
            String cachePath = getCachePathByCloudId(req);
            if ((cachePath != null && cachePath.equalsIgnoreCase(req.getSavePath())) || copyFile(req.getSavePath(), cachePath)) {
                CacheContext.get().getDiskCache().save(req.getCloudId(), 4, 2048, req.getBusinessId(), req.getExpiredTime());
                return cachePath;
            }
        }
        return "";
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
        d.i("checkCacheFile return true ", new Object[0]);
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
            r1 = "";
            return "";
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
        return this.b;
    }

    /* access modifiers changed from: protected */
    public boolean isCanceled() {
        boolean ret = this.c || Thread.currentThread().isInterrupted();
        if (ret) {
            cancel();
        }
        return ret;
    }

    /* access modifiers changed from: protected */
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

    public APFileRsp call() {
        this.e = CommonUtils.isActiveNetwork(this.mContext);
        if (this.e || !CommonUtils.isNeedCheckActiveNet(true)) {
            return null;
        }
        APFileRsp rsp = new APFileRsp();
        rsp.setRetCode(9);
        rsp.setMsg("has no network");
        return rsp;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    /* access modifiers changed from: protected */
    public boolean isNeedUCLog(APFileReq req, int ret) {
        return (req == null || req.getCallGroup() == 1000) && (this.e || ret == 0);
    }
}
