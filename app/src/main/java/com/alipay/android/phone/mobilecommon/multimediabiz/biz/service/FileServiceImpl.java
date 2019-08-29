package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APDecryptCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheRecord;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APDecryptReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileDownloadMMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileSecurityTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.FileWorker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.naming.MultiDirFileGenerator;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FileServiceImpl extends MultimediaFileService {
    private static final String TAG = "FileServiceImpl";
    private Context mContext;
    private FileWorker mFileWorker;
    private FileSecurityTool mSecurityTool;

    public static class MMFileQueryResult {
        APFileQueryResult value = null;

        public APFileQueryResult getValue() {
            return this.value;
        }

        public void setValue(APFileQueryResult value2) {
            this.value = value2;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getMicroApplicationContext().getApplicationContext();
        this.mFileWorker = FileWorker.getInstance(this.mContext);
        this.mSecurityTool = new FileSecurityTool(this.mContext);
        FRWBroadcastReceiver.initOnce();
    }

    public void cancelLoad(String taskId) {
        this.mFileWorker.cancelLoad(taskId);
    }

    public void cancelUp(String taskId) {
        this.mFileWorker.cancelUp(taskId);
    }

    public int saveToCache(APCacheReq req) {
        return this.mFileWorker.saveToCache(req);
    }

    public APMultimediaTaskModel getTaskStatusByCloudId(String cloudId) {
        return this.mFileWorker.getTaskStatusByCloudId(cloudId);
    }

    public APMultimediaTaskModel getLoadTaskStatus(String taskId) {
        return this.mFileWorker.getTaskRecord(taskId);
    }

    public APMultimediaTaskModel getLoadTaskStatusByCloudId(String cloudId) {
        return this.mFileWorker.getLoadTaskStatusByCloudId(cloudId);
    }

    public APMultimediaTaskModel getUpTaskStatus(String taskId) {
        return this.mFileWorker.getTaskRecord(taskId);
    }

    public APMultimediaTaskModel getUpTaskStatusByCloudId(String cloudId) {
        return this.mFileWorker.getUpTaskStatusByCloudId(cloudId);
    }

    public APMultimediaTaskModel downLoad(String url, APFileDownCallback callback) {
        return downLoad(url, callback, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel downLoad(String url, String savePath, APFileDownCallback callback) {
        return downLoad(url, savePath, callback, APConstants.DEFAULT_BUSINESS);
    }

    public APMultimediaTaskModel downLoad(APFileReq info, APFileDownCallback callback) {
        if (TextUtils.isEmpty(info.businessId)) {
            info.businessId = APConstants.DEFAULT_BUSINESS;
        }
        return downLoad(info, callback, info.businessId);
    }

    public APMultimediaTaskModel upLoad(APFileReq info, APFileUploadCallback callback) {
        if (TextUtils.isEmpty(info.businessId)) {
            info.businessId = APConstants.DEFAULT_BUSINESS;
        }
        return upLoad(info, callback, info.businessId);
    }

    public APFileDownloadRsp downloadOffline(APFileReq info) {
        return this.mFileWorker.downloadOffline(info);
    }

    public APMultimediaTaskModel upLoad(String savePath, APFileUploadCallback callback) {
        return upLoad(savePath, callback, (String) APConstants.DEFAULT_BUSINESS);
    }

    public APFileDownloadRsp downLoadSync(APFileReq info, APFileDownCallback callback) {
        if (TextUtils.isEmpty(info.businessId)) {
            info.businessId = APConstants.DEFAULT_BUSINESS;
        }
        return downLoadSync(info, callback, info.businessId);
    }

    public APFileUploadRsp upLoadSync(APFileReq info, APFileUploadCallback callback) {
        if (TextUtils.isEmpty(info.businessId)) {
            info.businessId = APConstants.DEFAULT_BUSINESS;
        }
        return upLoadSync(info, callback, info.businessId);
    }

    public void registeLoadCallBack(String taskId, APFileDownCallback callBack) {
        this.mFileWorker.registeLoadCallback(taskId, callBack);
    }

    public void unregisteLoadCallBack(String taskId, APFileDownCallback callBack) {
        this.mFileWorker.unregisteLoadCallback(taskId, callBack);
    }

    public void registeUpCallBack(String taskId, APFileUploadCallback callBack) {
        this.mFileWorker.registeUpCallback(taskId, callBack);
    }

    public void unregisteUpCallBack(String taskId, APFileUploadCallback callBack) {
        this.mFileWorker.unregisteUpCallback(taskId, callBack);
    }

    public boolean deleteFileCache(String path) {
        return this.mFileWorker.deleteFileCache(path);
    }

    public APMultimediaTaskModel downLoad(String url, APFileDownCallback callback, String business) {
        APFileReq req = new APFileReq();
        req.setCloudId(url);
        req.businessId = business;
        return downLoad(req, callback);
    }

    public APMultimediaTaskModel downLoad(String url, String savePath, APFileDownCallback callback, String business) {
        APFileReq req = new APFileReq();
        req.setCloudId(url);
        req.setSavePath(savePath);
        req.businessId = business;
        return downLoad(req, callback);
    }

    public APMultimediaTaskModel downLoad(APFileReq info, APFileDownCallback callback, String business) {
        info.businessId = business;
        return this.mFileWorker.downLoad(info, callback);
    }

    public APMultimediaTaskModel upLoad(APFileReq info, APFileUploadCallback callback, String business) {
        fixFileReq(info);
        info.businessId = business;
        return this.mFileWorker.upLoad(info, callback);
    }

    public APMultimediaTaskModel upLoad(String savePath, APFileUploadCallback callback, String business) {
        APFileReq req = new APFileReq();
        req.setSavePath(savePath);
        req.businessId = business;
        fixFileReq(req);
        return upLoad(req, callback);
    }

    public APFileDownloadRsp downLoadSync(APFileReq info, APFileDownCallback callback, String business) {
        info.businessId = business;
        return this.mFileWorker.downLoadSync(info, callback);
    }

    public APFileUploadRsp upLoadSync(APFileReq info, APFileUploadCallback callback, String business) {
        fixFileReq(info);
        info.businessId = business;
        return this.mFileWorker.upLoadSync(info, callback);
    }

    public APFileQueryResult queryCacheFile(final String id) {
        APFileQueryResult result;
        String str = null;
        if (TextUtils.isEmpty(id) || QueryCacheManager.getInstance().getConf().getFileSwitch()) {
            result = new APFileQueryResult();
            result.success = false;
            result.path = null;
        } else {
            APFileQueryResult result2 = QueryCacheManager.getInstance().getFileQuery(id);
            if (result2 == null || !result2.success) {
                result = new APFileQueryResult();
                String path = "";
                if (QueryCacheManager.getInstance().getConf().getFileTimeoutSwitch()) {
                    final MMFileQueryResult temRet = new MMFileQueryResult();
                    temRet.setValue(result);
                    try {
                        TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                            public Boolean call() {
                                String path = CacheContext.get().getDiskCache().genPathByKey(id);
                                temRet.getValue().success = FileUtils.checkFile(path);
                                APFileQueryResult value = temRet.getValue();
                                if (!temRet.getValue().success) {
                                    path = null;
                                }
                                value.path = path;
                                Logger.P(FileServiceImpl.TAG, "queryCacheFile async ret=" + temRet.getValue().toString() + ";id=" + id, new Object[0]);
                                return Boolean.valueOf(true);
                            }
                        }).get((long) QueryCacheManager.getInstance().getConf().queryTimeout, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        Logger.E((String) TAG, (Throwable) e, "queryCacheFile exp url: " + id, new Object[0]);
                    }
                } else {
                    path = CacheContext.get().getDiskCache().genPathByKey(id);
                    result.success = FileUtils.checkFile(path);
                    if (result.success) {
                        str = path;
                    }
                    result.path = str;
                }
                if (!result.success) {
                    Logger.D(TAG, "queryCacheFile fail,id=" + id + ";path=" + path, new Object[0]);
                }
            } else {
                CacheHitManager.getInstance().fileCacheHit();
                return result2;
            }
        }
        if (result.success) {
            CacheHitManager.getInstance().fileCacheHit();
            QueryCacheManager.getInstance().put(id, result);
        } else {
            CacheHitManager.getInstance().fileCacheMissed();
        }
        return result;
    }

    public APFileQueryResult queryTempFile(String id) {
        String str = null;
        APFileQueryResult result = new APFileQueryResult();
        if (!TextUtils.isEmpty(id)) {
            String path = FileDownloadMMTask.generateTempFilePathByCloudId(id);
            result.success = FileUtils.checkFile(path);
            if (result.success) {
                str = path;
            }
            result.path = str;
            if (!result.success) {
                Logger.D(TAG, "queryCacheFile fail,id=" + id + ";path=" + path, new Object[0]);
            }
        } else {
            result.success = false;
            result.path = null;
        }
        return result;
    }

    private void fixFileReq(APFileReq req) {
        if (req != null && LocalIdTool.isLocalIdRes(req.getSavePath())) {
            req.setSavePath(LocalIdTool.get().decodeToPath(req.getSavePath()));
        }
    }

    public void decryptFile(APDecryptReq apDecryptReq, APDecryptCallback callback) {
        this.mSecurityTool.processDecryptReq(apDecryptReq, callback);
    }

    public APFileQueryResult queryEncryptCacheFile(String id) {
        APFileQueryResult result = new APFileQueryResult();
        if (!TextUtils.isEmpty(id)) {
            String path = CacheContext.get().getDiskCache().genPathByKey(id) + ".enc";
            result.success = FileUtils.checkFile(path);
            if (!result.success) {
                path = null;
            }
            result.path = path;
        } else {
            result.success = false;
            result.path = null;
        }
        if (result.success) {
            CacheHitManager.getInstance().fileCacheHit();
        } else {
            CacheHitManager.getInstance().fileCacheMissed();
        }
        return result;
    }

    public APCacheRecord queryCacheRecord(String key) {
        return this.mFileWorker.queryCacheRecord(key);
    }

    public String getCacheFileNameByKey(String key) {
        return MultiDirFileGenerator.getCacheFileNameByKey(key);
    }
}
