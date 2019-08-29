package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheRecord;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheStorageManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APFileTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.TaskConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.manager.TaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.schedule.TaskScheduler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.diskcache.model.FileCacheModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FileWorker {
    private static FileWorker a = null;
    private static final Logger b = Logger.getLogger((String) "FileWorker");
    /* access modifiers changed from: private */
    public Context c;
    private TaskScheduler d = null;
    private APFileTaskManager e = null;

    private FileWorker(Context context) {
        this.c = context;
        this.d = TaskManager.getInstance().createTaskScheduler(TaskConstants.FILE_NET_TASKSERVICE, null);
        this.e = APFileTaskManager.getInstance(this.c);
    }

    public static FileWorker getInstance(Context context) {
        if (a == null) {
            synchronized (FileWorker.class) {
                if (a == null) {
                    a = new FileWorker(context);
                }
            }
        }
        return a;
    }

    public int saveToCache(APCacheReq req) {
        boolean result;
        b.w("saveToCache " + req, new Object[0]);
        if (TextUtils.isEmpty(req.cloudId) || TextUtils.isEmpty(req.srcPath)) {
            return -1;
        }
        String cachePath = CacheContext.get().getDiskCache().genPathByKey(req.cloudId);
        if (TextUtils.isEmpty(cachePath)) {
            return -1;
        }
        File dstFile = new File(cachePath);
        if (dstFile.exists()) {
            b.w("saveToCache cache file already exist", new Object[0]);
            return -1;
        }
        try {
            result = FileUtils.copyFile(new File(req.srcPath), dstFile);
        } catch (Exception e2) {
            result = false;
            try {
                File cacheFile = new File(cachePath);
                if (cacheFile.exists()) {
                    cacheFile.delete();
                }
            } catch (Exception delE) {
                b.w("saveToCache delete file error " + delE.getLocalizedMessage(), new Object[0]);
            }
        }
        if (result) {
            result = CacheContext.get().getDiskCache().save(req.cloudId, cachePath, 4, 2048, TextUtils.isEmpty(req.extra) ? "" : req.extra, req.getBusinessId(), req.getExpiredTime());
            b.w("saveToCache database result " + result, new Object[0]);
        }
        if (result) {
            return 0;
        }
        return -1;
    }

    public APMultimediaTaskModel downLoad(APFileReq info, APFileDownCallback callback) {
        b.d("downLoad " + info + ", callback: " + callback, new Object[0]);
        if (b(info, callback)) {
            return null;
        }
        FileMMTask task = a(info, callback);
        this.d.addTask(task);
        return task.getTaskInfo();
    }

    public APFileDownloadRsp downLoadSync(APFileReq info, APFileDownCallback callback) {
        b.d("downLoadSync " + info + ", callback: " + callback, new Object[0]);
        if (b(info, callback)) {
            return null;
        }
        APFileDownloadRsp rsp = new APFileDownloadRsp();
        try {
            return (APFileDownloadRsp) this.d.addTask(a(info, callback)).get();
        } catch (InterruptedException e2) {
            b.e(e2, "downLoadSync InterruptedException", new Object[0]);
            rsp.setRetCode(5);
            rsp.setMsg(e2.getMessage());
            return rsp;
        } catch (Exception e3) {
            b.e(e3, "downLoadSync ExecutionException", new Object[0]);
            rsp.setRetCode(1);
            rsp.setMsg(e3.getMessage());
            return rsp;
        }
    }

    public void cancelLoad(String taskId) {
        b.d("cancelLoad " + taskId, new Object[0]);
        a(taskId);
    }

    private APMultimediaTaskModel a(String taskId) {
        APMultimediaTaskModel taskInfo = null;
        if (TextUtils.isEmpty(taskId)) {
            b.d("cancelTask id=null", new Object[0]);
            return null;
        }
        b.d("cancelTask id=" + taskId, new Object[0]);
        FileMMTask task = (FileMMTask) this.d.cancelTask(taskId);
        if (task != null) {
            taskInfo = task.getTaskInfo();
        }
        return b(taskInfo);
    }

    private FileMMTask a(APFileReq info, APFileDownCallback cb) {
        b.d("createFileDownloadTask info: " + info + ", cb: " + cb, new Object[0]);
        List reqList = new ArrayList();
        reqList.add(info);
        APMultimediaTaskModel taskInfo = a((String) FileDownloadMMTask.PREFIX_FILE_DOWNLOAD, info);
        taskInfo.setSourcePath(info.getCloudId());
        taskInfo.setCloudId(info.getCloudId());
        FileMMTask task = new FileDownloadMMTask(this.c, reqList, taskInfo, cb);
        task.setPriority(info.getPriority());
        return task;
    }

    private boolean b(APFileReq info, APFileDownCallback callback) {
        if (info == null || TextUtils.isEmpty(info.getCloudId())) {
            a(info, 7, "path is empty", callback);
            return true;
        }
        b.d("checkPreDownload info: " + info + ", callback: " + callback, new Object[0]);
        if (TextUtils.isEmpty(info.getSavePath())) {
            if (a(info)) {
                if (PathUtils.isPreloadNeedReport(info.businessId, info.getCloudId())) {
                    UCLogUtil.UC_MM_48("0", info.getCloudId(), UCLogUtil.getTypeByCallGroup(info.getCallGroup()));
                }
                if (!PathUtils.checkIsResourcePreDownload(info.businessId)) {
                    if (info.getCallGroup() == 1001) {
                        CacheHitManager.getInstance().imageCacheHit();
                    } else if (info.getCallGroup() == 1003) {
                        CacheHitManager.getInstance().videoCacheHit();
                    } else if (info.getCallGroup() == 1002) {
                        CacheHitManager.getInstance().audioCacheHit();
                    } else {
                        CacheHitManager.getInstance().fileCacheHit();
                    }
                }
                if (PathUtils.isEncryptedMusic(info.getCloudId())) {
                    List reqList = new ArrayList();
                    reqList.add(info);
                    APMultimediaTaskModel taskInfo = a((String) "encrypted file move", info);
                    taskInfo.setSourcePath(info.getCloudId());
                    taskInfo.setCloudId(info.getCloudId());
                    this.d.addTask(new EncryptedFileMoveTask(this.c, reqList, taskInfo, callback));
                    return true;
                }
                a(info, "get from cache file", callback);
                return true;
            } else if (!PathUtils.checkIsResourcePreDownload(info.businessId)) {
                if (info.getCallGroup() == 1001) {
                    CacheHitManager.getInstance().imageCacheMissed();
                } else if (info.getCallGroup() == 1003) {
                    CacheHitManager.getInstance().videoCacheMissed();
                } else if (info.getCallGroup() == 1002) {
                    CacheHitManager.getInstance().audioCacheMissed();
                } else {
                    CacheHitManager.getInstance().fileCacheMissed();
                }
            }
        }
        if (CommonUtils.isSatisfyDownloadSpace(info.getBizType())) {
            return false;
        }
        b.e("checkPreDownload sdcard space is not enough to download... current: " + FileUtils.getSdAvailableSize(), new Object[0]);
        a(info, 12, "sdcard space is not enough to download...", callback);
        a(info, info.getCloudId(), info.businessId, "space not enough", info.isHttps());
        return true;
    }

    private boolean a(final APFileReq info) {
        boolean result;
        boolean result2 = false;
        if (QueryCacheManager.getInstance().getConf().getFileTimeoutSwitch()) {
            try {
                return ((Boolean) TaskScheduleManager.get().commonExecutor().submit(new Callable<Boolean>() {
                    public Boolean call() {
                        boolean ret = true;
                        String cachePath = new FileTask(FileWorker.this.c, null, null).checkCacheFile(info);
                        if (TextUtils.isEmpty(cachePath) || !FileUtils.checkFileByMd5(info.getMd5(), cachePath, true)) {
                            ret = false;
                        }
                        if (ret) {
                            info.setSavePath(cachePath);
                        }
                        return Boolean.valueOf(ret);
                    }
                }).get((long) QueryCacheManager.getInstance().getConf().queryTimeout, TimeUnit.MILLISECONDS)).booleanValue();
            } catch (Exception e2) {
                b.e(e2, "checkCacheFile in main thread exp", new Object[0]);
                return result2;
            }
        } else {
            String cachePath = new FileTask(this.c, null, null).checkCacheFile(info);
            if (TextUtils.isEmpty(cachePath) || !FileUtils.checkFileByMd5(info.getMd5(), cachePath, true)) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return result;
            }
            info.setSavePath(cachePath);
            return result;
        }
    }

    private static void a(APFileReq req, String id, String biz, String exp, boolean bHttps) {
        String type = "file";
        if (req.getCallGroup() == 1001) {
            type = "im";
        } else if (req.getCallGroup() == 1002) {
            type = "ad";
        } else if (req.getCallGroup() == 1003) {
            type = LogItem.MM_C18_K4_VD;
        }
        UCLogUtil.UC_MM_C47("2100", 0, 0, id, type, biz, "1", exp, "", bHttps ? "1" : "0", false);
    }

    private static void a(APFileReq info, String msg, APFileDownCallback callback) {
        b.d("notifyDownloadFinish info: " + info + ", code: 0, msg: " + msg + ", callback: " + callback, new Object[0]);
        if (callback != null) {
            APFileDownloadRsp rsp = new APFileDownloadRsp();
            rsp.setRetCode(0);
            rsp.setMsg(msg);
            rsp.setFileReq(info);
            callback.onDownloadFinished(null, rsp);
        }
    }

    private static void a(APFileReq info, int code, String msg, APFileDownCallback callback) {
        b.d("notifyDownloadError info: " + info + ", code: " + code + ", msg: " + msg + ", callback: " + callback, new Object[0]);
        if (callback != null) {
            APFileDownloadRsp rsp = new APFileDownloadRsp();
            rsp.setRetCode(code);
            rsp.setMsg(msg);
            rsp.setFileReq(info);
            callback.onDownloadError(null, rsp);
        }
    }

    private static APMultimediaTaskModel a(String taskPrefix, APFileReq req) {
        String taskId;
        APMultimediaTaskModel task = new APMultimediaTaskModel();
        if (req == null) {
            task.setTaskId(taskPrefix + System.currentTimeMillis());
        } else if (FileDownloadMMTask.PREFIX_FILE_DOWNLOAD.equals(taskPrefix)) {
            task.setTaskId(FileDownloadMMTask.generateTaskId(req.getCloudId()));
        } else if (FileUpMMTask.PREFIX_FILE_UPLOAD.equals(taskPrefix)) {
            if (req.getUploadData() != null) {
                taskId = FileUpMMTask.generateTaskId(req.getUploadData());
            } else {
                taskId = FileUpMMTask.generateTaskId(req.getSavePath());
            }
            task.setTaskId(taskId);
        } else {
            task.setTaskId(taskPrefix + System.currentTimeMillis());
        }
        task.cBusinessId = req == null ? APConstants.DEFAULT_BUSINESS : req.businessId;
        return task;
    }

    private APMultimediaTaskModel a(APMultimediaTaskModel taskInfo) {
        return this.e.updateTaskRecord(taskInfo);
    }

    public APMultimediaTaskModel getTaskStatusByCloudId(String cloudId) {
        String taskId = FileDownloadMMTask.generateTaskId(cloudId);
        if (TextUtils.isEmpty(taskId)) {
            return null;
        }
        MMTask task = this.d.getTask(taskId);
        if (task instanceof FileDownloadMMTask) {
            return ((FileDownloadMMTask) task).getTaskInfo();
        }
        return null;
    }

    public APMultimediaTaskModel getTaskRecord(String taskId) {
        return this.e.getTaskRecord(taskId);
    }

    public APMultimediaTaskModel removeTaskRecord(String taskId) {
        return this.e.delTaskRecord(taskId);
    }

    private APMultimediaTaskModel b(APMultimediaTaskModel taskInfo) {
        if (taskInfo == null) {
            return null;
        }
        taskInfo.setStatus(2);
        return a(taskInfo);
    }

    public APMultimediaTaskModel getLoadTaskStatusByCloudId(String cloudId) {
        b.d("getLoadTaskStatusByCloudId " + cloudId, new Object[0]);
        return getTaskRecord(FileDownloadMMTask.generateTaskId(cloudId));
    }

    public void registeLoadCallback(String taskId, APFileDownCallback callBack) {
        if (taskId == null || callBack == null) {
            b.d("registeLoadCallback " + taskId, new Object[0]);
            return;
        }
        MMTask task = this.d.getTask(taskId);
        if (task == null) {
            return;
        }
        if (task.getState() == 2 || task.getState() == 3) {
            throw new IllegalStateException("MMTask is already canceled or finished");
        } else if (task instanceof FileDownloadMMTask) {
            ((FileDownloadMMTask) task).registeFileDownloadCallback(callBack);
        }
    }

    public void unregisteLoadCallback(String taskId, APFileDownCallback callBack) {
        if (taskId == null) {
            b.d("registeLoadCallback is null", new Object[0]);
            return;
        }
        MMTask task = this.d.getTask(taskId);
        if (task != null && (task instanceof FileDownloadMMTask)) {
            ((FileDownloadMMTask) task).unregisteFileDownloadCallback(callBack);
        }
    }

    public APFileDownloadRsp downloadOffline(APFileReq info) {
        List reqList = new ArrayList();
        reqList.add(info);
        try {
            return new OfflineDownloadTask(this.c, reqList, null).taskRun();
        } catch (Exception e2) {
            b.e(e2, "downloadOffline exception", new Object[0]);
            APFileDownloadRsp rsp = new APFileDownloadRsp();
            rsp.setRetCode(1);
            rsp.setMsg(e2.getMessage());
            return rsp;
        }
    }

    public boolean deleteFileCache(String path) {
        boolean z = false;
        if (TextUtils.isEmpty(path)) {
            return z;
        }
        try {
            String downloadTaskId = FileDownloadMMTask.generateTaskId(path);
            String uploadTaskId = FileUpMMTask.generateTaskId(path);
            a(downloadTaskId);
            a(uploadTaskId);
            removeTaskRecord(downloadTaskId);
            removeTaskRecord(uploadTaskId);
            APFileReq req = new APFileReq();
            req.setCloudId(path);
            return FileMMTask.removeCacheFile(req);
        } catch (Exception e2) {
            b.e(e2, "deleteFileCache exception", new Object[z]);
            return z;
        }
    }

    public APMultimediaTaskModel upLoad(APFileReq info, Options options, APFileUploadCallback callback) {
        b.d("upLoad " + info, new Object[0]);
        b(info);
        if (a(info, callback)) {
            return null;
        }
        FileMMTask task = FileTaskFactory.createUploadMMTask(this.c, info, options, a((String) FileUpMMTask.PREFIX_FILE_UPLOAD, info), callback);
        this.d.addTask(task);
        return task.getTaskInfo();
    }

    public APMultimediaTaskModel upLoad(APFileReq info, APFileUploadCallback callback) {
        return upLoad(info, null, callback);
    }

    public APFileUploadRsp upLoadSync(APFileReq info, APFileUploadCallback callback) {
        b.d("upLoadSync " + info, new Object[0]);
        b(info);
        if (a(info, callback)) {
            return null;
        }
        APFileUploadRsp rsp = new APFileUploadRsp();
        APMultimediaTaskModel taskInfo = a((String) FileUpMMTask.PREFIX_FILE_UPLOAD, info);
        taskInfo.cLock = true;
        try {
            return (APFileUploadRsp) this.d.addTask(FileTaskFactory.createUploadMMTask(this.c, info, null, taskInfo, callback)).get();
        } catch (InterruptedException e2) {
            b.e(e2, "upLoadSync InterruptedException", new Object[0]);
            rsp.setRetCode(5);
            rsp.setMsg(e2.getMessage());
            return rsp;
        } catch (Exception e3) {
            b.e(e3, "upLoadSync ExecutionException", new Object[0]);
            rsp.setRetCode(1);
            rsp.setMsg(e3.getMessage());
            return rsp;
        }
    }

    public void cancelUp(String taskId) {
        b.d("cancelUp " + taskId, new Object[0]);
        a(taskId);
    }

    private static boolean a(APFileReq info, APFileUploadCallback callback) {
        if (info != null && (!TextUtils.isEmpty(info.getSavePath()) || info.getUploadData() != null)) {
            return false;
        }
        if (callback != null) {
            APFileUploadRsp rsp = new APFileUploadRsp();
            rsp.setRetCode(7);
            rsp.setMsg("save path empty!");
            rsp.setFileReq(info);
            callback.onUploadError(null, rsp);
        }
        return true;
    }

    private static void b(APFileReq info) {
        if (info != null && !TextUtils.isEmpty(info.getUploadIdentifier())) {
            info.setSavePath(CacheStorageManager.queryCacheInfoPath(info.getUploadIdentifier()));
        }
        b.d("preFillUploadRequest info: " + info, new Object[0]);
    }

    public APMultimediaTaskModel getUpTaskStatusByCloudId(String cloudId) {
        b.d("getLoadTaskStatusByCloudId " + cloudId, new Object[0]);
        return getTaskRecord(FileUpMMTask.generateTaskId(cloudId));
    }

    public void registeUpCallback(String taskId, APFileUploadCallback callBack) {
        if (TextUtils.isEmpty(taskId) || callBack == null) {
            b.d("registeUpCallBack id=" + taskId, new Object[0]);
            return;
        }
        MMTask task = this.d.getTask(taskId);
        if (task == null) {
            return;
        }
        if (task.getState() == 2 || task.getState() == 3) {
            throw new IllegalStateException("MMTask is already canceled or finished");
        } else if (task instanceof HttpFileUpMMTask) {
            ((HttpFileUpMMTask) task).registeFileUploadCallback(callBack);
        } else if (task instanceof NBNetFileUpMMTask) {
            ((NBNetFileUpMMTask) task).registeFileUploadCallback(callBack);
        }
    }

    public void unregisteUpCallback(String taskId, APFileUploadCallback callBack) {
        if (TextUtils.isEmpty(taskId)) {
            b.d("unregisteUpCallBack id=null", new Object[0]);
            return;
        }
        MMTask task = this.d.getTask(taskId);
        if (task == null) {
            return;
        }
        if (task instanceof HttpFileUpMMTask) {
            ((HttpFileUpMMTask) task).unregisteFileUploadCallback(callBack);
        } else if (task instanceof NBNetFileUpMMTask) {
            ((NBNetFileUpMMTask) task).unregisteFileUploadCallback(callBack);
        }
    }

    public APCacheRecord queryCacheRecord(String key) {
        FileCacheModel model = CacheContext.get().getDiskCache().get(key);
        if (model == null) {
            return null;
        }
        APCacheRecord record = new APCacheRecord();
        record.id = model.id;
        record.cacheKey = model.cacheKey;
        record.aliasKey = model.aliasKey;
        record.path = model.path;
        record.fileSize = model.fileSize;
        record.modifyTime = model.modifyTime;
        record.accessTime = model.accessTime;
        record.businessId = model.businessId;
        record.tag = model.tag;
        record.type = model.type;
        record.expiredTime = model.expiredTime;
        record.extra = model.extra;
        record.multiAliasKeys = model.multiAliasKeys;
        return record;
    }
}
