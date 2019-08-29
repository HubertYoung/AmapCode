package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APFileTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.DiskCache;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUpMMTask extends FileMMTask {
    public static final String PREFIX_FILE_UPLOAD = "file_up_";
    private static final Logger a = Logger.getLogger((String) "FileUpMMTask");
    protected final Set<APFileUploadCallback> callbacks = Collections.synchronizedSet(new HashSet());

    protected FileUpMMTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo) {
        super(context, reqList, taskInfo);
    }

    public static String generateTaskId(String savePath) {
        return MD5Util.getMD5String(new StringBuilder(PREFIX_FILE_UPLOAD).append(savePath).toString());
    }

    public static String generateTaskId(byte[] data) {
        return MD5Util.getMD5String(data);
    }

    public void onMergeTask(MMTask task) {
        if (task != null) {
            FileUpMMTask mergeTask = (FileUpMMTask) task;
            synchronized (this.callbacks) {
                this.callbacks.addAll(mergeTask.callbacks);
            }
            mergeTask.taskInfo = this.taskInfo;
        }
    }

    public void onAddTask() {
        APFileTaskManager.getInstance(this.mContext).addTaskRecord(this.taskInfo);
    }

    /* access modifiers changed from: protected */
    public void copyToCache(String cloudId, String path, String businessId) {
        if (!TextUtils.isEmpty(cloudId) && FileUtils.checkFile(path)) {
            DiskCache cache = CacheContext.get().getDiskCache();
            File dstFile = new File(cache.genPathByKey(cloudId));
            boolean needCopy = true;
            try {
                needCopy = !FileUtils.checkFile(dstFile) || !MD5Util.getFileMD5String(new File(path)).equals(MD5Util.getFileMD5String(dstFile));
            } catch (Exception e) {
                a.e(e, "copyToCache cloudId: " + cloudId + ", path: " + path + ", biz: " + businessId, new Object[0]);
            }
            boolean copied = false;
            if (needCopy) {
                copied = FileUtils.copyFile(new File(path), dstFile);
                if (copied) {
                    cache.save(cloudId, 4, 2048, businessId, Long.MAX_VALUE);
                }
            }
            a.d("copyToCache needCopy: " + needCopy + ", cloudId: " + cloudId + ", path: " + path + ", biz: " + businessId + ", copied: " + copied, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyUploadStart(APMultimediaTaskModel taskInfo) {
        a.d("FileUploadListener onUploadStart " + taskInfo, new Object[0]);
        if (2 == taskInfo.getStatus()) {
            a.d("onUploadStart cancel return ", new Object[0]);
            return;
        }
        updateTaskModelStatus(taskInfo, 1);
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                a.d("notifyUploadStart callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileUploadCallback onUploadStart : this.callbacks) {
                    onUploadStart.onUploadStart(taskInfo);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyUploadProgress(APMultimediaTaskModel taskInfo, int progress, long hasUploadSize, long total) {
        if (progress <= 1 || progress >= 99) {
            a.d("FileUploadListener onUploadProgress=" + taskInfo + ";progress=" + progress + ";hasUploadSize=" + hasUploadSize + ";total=" + total + ";cbs=" + this.callbacks.size(), new Object[0]);
        } else {
            a.d("FileUploadListener onUploadProgress=" + taskInfo + ";progress=" + progress + ";hasUploadSize=" + hasUploadSize + ";total=" + total + ";cbs=" + this.callbacks.size(), new Object[0]);
        }
        if (2 == taskInfo.getStatus()) {
            a.d("onUploadProgress cancel return ", new Object[0]);
            return;
        }
        taskInfo.setCurrentSize(hasUploadSize);
        taskInfo.setTotalSize(total);
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                a.d("notifyUploadProgress callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileUploadCallback onUploadProgress : this.callbacks) {
                    onUploadProgress.onUploadProgress(taskInfo, progress, hasUploadSize, total);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyUploadError(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
        a.d("FileUploadListener onUploadError " + taskInfo + ", " + rsp, new Object[0]);
        if (5 == rsp.getRetCode() || 2 == taskInfo.getStatus()) {
            a.d("onUploadError cancel return ", new Object[0]);
            return;
        }
        updateTaskModelStatus(taskInfo, 3);
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                a.d("notifyUploadError callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileUploadCallback onUploadError : this.callbacks) {
                    onUploadError.onUploadError(taskInfo, rsp);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void notifyUploadFinish(APMultimediaTaskModel taskInfo, APFileUploadRsp rsp) {
        a.d("FileUploadListener onUploadFinished " + taskInfo + ", " + rsp, new Object[0]);
        if (2 == taskInfo.getStatus()) {
            a.d("onUploadFinished cancel return ", new Object[0]);
            return;
        }
        taskInfo.setCloudId(rsp.getFileReq().getCloudId());
        if (rsp.getFileReq().isNeedCache()) {
            taskInfo.cLock = false;
            updateTaskModelStatus(taskInfo, 4);
        } else {
            removeTaskRecord(taskInfo.getTaskId());
        }
        synchronized (this.callbacks) {
            if (!this.callbacks.isEmpty()) {
                a.d("notifyUploadFinish callbacks " + this.callbacks.size(), new Object[0]);
                for (APFileUploadCallback onUploadFinished : this.callbacks) {
                    onUploadFinished.onUploadFinished(taskInfo, rsp);
                }
            }
        }
    }

    public void registeFileUploadCallback(APFileUploadCallback callback) {
        if (callback != null) {
            synchronized (this.callbacks) {
                this.callbacks.add(callback);
            }
        }
    }

    public void unregisteFileUploadCallback(APFileUploadCallback callback) {
        synchronized (this.callbacks) {
            if (callback == null) {
                this.callbacks.clear();
            } else {
                this.callbacks.remove(callback);
            }
        }
    }
}
