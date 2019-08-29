package com.alipay.mobile.common.patch;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.dodola.patcher.utils.AppUtils;
import java.io.File;
import java.util.concurrent.Future;

public class ZPatcher extends BasePatcher {
    public static final String TAG = "ZPatcher";
    private TransportCallback a = new TransportCallback() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onProgressUpdate(Request request, double percent) {
            ZPatcher.this.updateDownloadPatchProgress(percent);
        }

        public void onPreExecute(Request request) {
            LoggerFactory.getTraceLogger().debug(ZPatcher.TAG, "onPreExecute");
        }

        public void onPostExecute(Request request, Response response) {
            LoggerFactory.getTraceLogger().debug(ZPatcher.TAG, "download patch completed..");
            ZPatcher.this.applyPatch();
        }

        public void onFailed(Request request, int code, String msg) {
            LoggerFactory.getTraceLogger().error((String) ZPatcher.TAG, "download patch error code = " + code + "msg = " + msg + "url = " + ZPatcher.this.mPatchFileUrl);
            LoggerUtils.writePatchLog("DownloadPatchFile-Fail-ZPatcher");
            ZPatcher.this.onFail(102);
        }

        public void onCancelled(Request request) {
            LoggerFactory.getTraceLogger().error((String) ZPatcher.TAG, "download patch cancel url = " + ZPatcher.this.mPatchFileUrl);
            ZPatcher.this.delPatcherFile();
            ZPatcher.this.onFail(110);
        }
    };
    protected Future<?> mDownloadTask = null;
    Runnable patchTask = new Runnable() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void run() {
            int result = 1;
            try {
                if (!ZPatcher.this.verifyPatchBeforeApply()) {
                    ZPatcher.this.delPatcherFile();
                    ZPatcher.this.onFail(108);
                    return;
                }
                LoggerUtils.writePatchLogStart(ZPatcher.TAG);
                result = AppUtils.patcher(ZPatcher.this.mOldFilePath, ZPatcher.this.mNewFilePath, ZPatcher.this.mPatchFilePath);
                ZPatcher.this.delPatcherFile();
                if (result == 0) {
                    LoggerUtils.writePatchLogSuccess(ZPatcher.TAG);
                    ZPatcher.this.a();
                    return;
                }
                LoggerUtils.writePatchLogFail(ZPatcher.TAG);
                ZPatcher.this.onFail(104);
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) ZPatcher.TAG, e);
            }
        }
    };

    public ZPatcher(Context context, String newFilePath, String oldFilePath, String patchFileUrl, String newFileMD5, String patchFileMD5, PatchCallBack callBack) {
        super(context, newFilePath, oldFilePath, patchFileUrl, newFileMD5, patchFileMD5, callBack);
        LoggerFactory.getTraceLogger().debug(TAG, "ZPatcher init");
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void startPatch() {
        if (isLoadLibrary) {
            LoggerUtils.logDoPatchServiceStart(TAG);
            downloadPatch(getPatcherDownloadListener());
            return;
        }
        onFail(109);
    }

    public TransportCallback getPatcherDownloadListener() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void downloadPatch(TransportCallback callBack) {
        String patchFile = PatchUtils.getPatchFile(this.mContext, this.mPatchFileUrl);
        this.mPatchFilePath = patchFile;
        delPatcherFile();
        if (TextUtils.isEmpty(patchFile) || !PatchUtils.isFileExists(this.mOldFilePath)) {
            onFail(101);
            return;
        }
        LoggerFactory.getTraceLogger().debug(TAG, "start downloadPatch...");
        DownloadRequest request = new DownloadRequest(this.mPatchFileUrl, patchFile, null, null);
        request.setTransportCallback(callBack);
        request.setUseEtag(false);
        this.mDownloadTask = this.mDownloadEngine.addDownload(request);
    }

    /* access modifiers changed from: protected */
    public Runnable getPatchTask() {
        return this.patchTask;
    }

    /* access modifiers changed from: private */
    public void a() {
        LoggerFactory.getTraceLogger().debug(TAG, "verifyNewFileMD5...");
        if (TextUtils.isEmpty(this.mNewFilePath)) {
            onFail(102);
        }
        if (PatchUtils.checkFileInMd5(this.mNewFileMD5, new File(this.mNewFilePath))) {
            LoggerUtils.logVerifyNewFileMD5Success(TAG);
            onSuccess(this.mNewFilePath);
            return;
        }
        LoggerUtils.logVerifyNewFileMD5Fail(TAG);
        onFail(108);
        PatchUtils.deleteFileByPath(this.mNewFilePath);
    }

    public void CancelTask() {
        if (this.mDownloadTask != null && !this.mDownloadTask.isDone()) {
            this.mDownloadTask.cancel(false);
        }
    }
}
