package com.alipay.mobile.common.patch;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.patch.dir.FileDirPatcher;
import com.alipay.mobile.common.task.AsyncTaskExecutor;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadManager;
import com.dodola.patcher.utils.AppUtils;
import java.io.File;

public abstract class BasePatcher {
    public static final String TAG = "BasePatcher";
    protected static boolean isLoadLibrary = true;
    protected PatchCallBack mCallBack;
    protected Context mContext;
    protected DownloadManager mDownloadEngine = null;
    protected AsyncTaskExecutor mExecutor;
    protected Handler mHandler = null;
    protected String mNewFileMD5;
    protected String mNewFilePath;
    protected String mOldFilePath;
    protected String mPatchFileMD5;
    protected String mPatchFilePath;
    protected String mPatchFileUrl;

    public abstract void CancelTask();

    /* access modifiers changed from: protected */
    public abstract void downloadPatch(TransportCallback transportCallback);

    /* access modifiers changed from: protected */
    public abstract Runnable getPatchTask();

    public abstract TransportCallback getPatcherDownloadListener();

    public abstract void startPatch();

    static {
        initLibrary();
    }

    protected static void initLibrary() {
        try {
            isLoadLibrary = true;
            System.loadLibrary("patcher");
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "load so success");
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "load so fail!!");
            isLoadLibrary = false;
            LoggerUtils.writePatchLog("loadLibrary-Fail-BasePatcher");
            LoggerFactory.getTraceLogger().error((String) TAG, e);
        }
    }

    public BasePatcher(Context context, String newFilePath, String oldFilePath, String patchFileUrl, String newFileMD5, String patchFileMD5, PatchCallBack callBack) {
        this.mNewFilePath = newFilePath;
        this.mOldFilePath = oldFilePath;
        this.mPatchFileUrl = patchFileUrl;
        this.mNewFileMD5 = newFileMD5;
        this.mPatchFileMD5 = patchFileMD5;
        this.mCallBack = callBack;
        this.mContext = context;
        if (this.mContext != null) {
            this.mHandler = new Handler(this.mContext.getMainLooper());
        }
        this.mDownloadEngine = new DownloadManager(this.mContext);
        this.mExecutor = AsyncTaskExecutor.getInstance();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void applyPatch() {
        this.mExecutor.execute(getPatchTask(), "applyPatch");
    }

    /* access modifiers changed from: protected */
    public boolean verifyPatchBeforeApply() {
        String patchPath = PatchUtils.getPatchFile(this.mContext, this.mPatchFileUrl);
        if (TextUtils.isEmpty(patchPath)) {
            return false;
        }
        this.mPatchFilePath = patchPath;
        File patchFile = new File(patchPath);
        if (!verifyPatchMD5(patchFile)) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "verifyPatchMD5 fail");
            LoggerUtils.writePatchLog("verifyPatchMD5-Fail-BasePatcher");
            return false;
        } else if (!PatchUtils.IsCanUseSdCard()) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "IsCanUseSdCard false");
            return false;
        } else if (!PatchUtils.isFileExists(this.mOldFilePath)) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "mOldFilePath is not exists");
            return false;
        } else if (!PatchUtils.isEnoughSpaceDoPatch(patchFile, new File(this.mOldFilePath))) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "space is not enough to patch");
            return false;
        } else if (PatchUtils.creatFileDir(this.mNewFilePath)) {
            return true;
        } else {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "mNewFilePath can not creat");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyPatchMD5(File patchFile) {
        return PatchUtils.checkFileInMd5(this.mPatchFileMD5, patchFile);
    }

    /* access modifiers changed from: protected */
    public void delPatcherFile() {
        PatchUtils.deleteFileByPath(this.mPatchFilePath);
    }

    /* access modifiers changed from: protected */
    public void delNewFile() {
        PatchUtils.deleteFileByPath(this.mNewFilePath);
    }

    /* access modifiers changed from: protected */
    public void destory() {
        if (this.mDownloadEngine != null) {
            this.mDownloadEngine.close();
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccess(String path) {
        LoggerFactory.getTraceLogger().warn((String) TAG, "onSuccess " + path);
        if (this.mCallBack != null) {
            this.mCallBack.onSuccess(path);
        }
    }

    /* access modifiers changed from: protected */
    public void onFail(int errCode) {
        LoggerFactory.getTraceLogger().warn((String) TAG, "onFail " + errCode);
        if (this.mCallBack != null) {
            this.mCallBack.onFail(errCode);
        }
    }

    /* access modifiers changed from: protected */
    public void updateDownloadPatchProgress(double percent) {
        if (this.mCallBack != null) {
            this.mCallBack.onDownloadPatchProgressUpdate(percent);
        }
    }

    /* access modifiers changed from: protected */
    public void updateDownloadNewFileProgress(double percent) {
        if (this.mCallBack != null) {
            this.mCallBack.onDownloadNewFileProgressUpdate(percent);
        }
    }

    public static boolean patcherDir(Context context, String newFileFolder, String oldFileFolder, String patchFilePath, String oldFileMD5, String patchFileMD5) {
        if (!isLoadLibrary) {
            return false;
        }
        return FileDirPatcher.patchDir(context, newFileFolder, oldFileFolder, patchFilePath, oldFileMD5, patchFileMD5);
    }

    public static boolean patcher(String newFilePath, String oldFilePath, String patchFilePath, String newFileMD5, String patchFileMD5) {
        if (TextUtils.isEmpty(newFilePath) || TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(patchFilePath) || TextUtils.isEmpty(newFileMD5) || TextUtils.isEmpty(patchFileMD5)) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (String) "param is empty");
            return false;
        }
        try {
            File patchFile = new File(patchFilePath);
            if (!PatchUtils.checkFileInMd5(patchFileMD5, patchFile)) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "verifyPatchMD5 fail");
                return false;
            } else if (!PatchUtils.IsCanUseSdCard()) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "IsCanUseSdCard false");
                return false;
            } else if (!PatchUtils.isFileExists(oldFilePath)) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "mOldFilePath is not exists");
                return false;
            } else if (!PatchUtils.isEnoughSpaceDoPatch(patchFile, new File(oldFilePath))) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "space is not enough to patch");
                return false;
            } else if (!PatchUtils.creatFileDir(newFilePath)) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "mNewFilePath can not creat");
                return false;
            } else {
                LoggerUtils.writePatchLogStart(TAG);
                if (AppUtils.patcher(oldFilePath, newFilePath, patchFilePath) == 0) {
                    LoggerUtils.writePatchLogSuccess(TAG);
                    if (PatchUtils.checkFileInMd5(newFileMD5, new File(newFilePath))) {
                        LoggerUtils.logVerifyNewFileMD5Success(TAG);
                        return true;
                    }
                    LoggerUtils.logVerifyNewFileMD5Fail(TAG);
                    return false;
                }
                LoggerUtils.writePatchLogFail(TAG);
                return false;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
            return false;
        }
    }
}
