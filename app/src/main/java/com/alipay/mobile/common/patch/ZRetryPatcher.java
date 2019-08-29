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

public class ZRetryPatcher extends BasePatcher {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public TransportCallback b = new TransportCallback() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onProgressUpdate(Request request, double percent) {
            ZRetryPatcher.this.updateDownloadNewFileProgress(percent);
        }

        public void onPreExecute(Request request) {
        }

        public void onPostExecute(Request request, Response response) {
            LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "download newFile completed url=" + ZRetryPatcher.this.a);
            if (ZRetryPatcher.this.a()) {
                ZRetryPatcher.this.onSuccess(ZRetryPatcher.this.mNewFilePath);
            } else {
                ZRetryPatcher.this.onFail(108);
            }
        }

        public void onFailed(Request request, int code, String msg) {
            LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "Failed download newFile." + msg + " error code = " + code + "url= " + ZRetryPatcher.this.a);
            ZRetryPatcher.this.onFail(102);
        }

        public void onCancelled(Request request) {
            ZRetryPatcher.this.delNewFile();
            ZRetryPatcher.this.onFail(110);
        }
    };
    private TransportCallback c = new TransportCallback() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onProgressUpdate(Request request, double percent) {
            ZRetryPatcher.this.updateDownloadPatchProgress(percent);
        }

        public void onPreExecute(Request request) {
        }

        public void onPostExecute(Request request, Response response) {
            LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "download patch completed url=" + ZRetryPatcher.this.mPatchFileUrl);
            ZRetryPatcher.this.applyPatch();
        }

        public void onFailed(Request request, int code, String msg) {
            LoggerFactory.getTraceLogger().error((String) "ZRetryPatcher", "download patch error code = " + code + "msg = " + msg + "url = " + ZRetryPatcher.this.mPatchFileUrl);
            LoggerUtils.writePatchLog("DownloadPatchFile-Fail-ZRetryPatcher");
            ZRetryPatcher.this.a(ZRetryPatcher.this.b);
        }

        public void onCancelled(Request request) {
            LoggerFactory.getTraceLogger().error((String) "ZRetryPatcher", "download patch cancel url = " + ZRetryPatcher.this.mPatchFileUrl);
            ZRetryPatcher.this.delPatcherFile();
            ZRetryPatcher.this.onFail(110);
        }
    };
    protected Future<?> mNewFileDownloadTask = null;
    protected Future<?> mPatchDownloadTask = null;
    Runnable patchTask = new Runnable() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void run() {
            int result = 1;
            try {
                if (!ZRetryPatcher.this.verifyPatchBeforeApply()) {
                    ZRetryPatcher.this.delPatcherFile();
                    ZRetryPatcher.this.a(ZRetryPatcher.this.b);
                    return;
                }
                LoggerUtils.writePatchLogStart("ZRetryPatcher");
                result = AppUtils.patcher(ZRetryPatcher.this.mOldFilePath, ZRetryPatcher.this.mNewFilePath, ZRetryPatcher.this.mPatchFilePath);
                ZRetryPatcher.this.delPatcherFile();
                if (result == 0) {
                    LoggerUtils.writePatchLogSuccess("ZRetryPatcher");
                    if (ZRetryPatcher.this.a()) {
                        LoggerUtils.logVerifyNewFileMD5Success("ZRetryPatcher");
                        ZRetryPatcher.this.onSuccess(ZRetryPatcher.this.mNewFilePath);
                        return;
                    }
                    LoggerUtils.logVerifyNewFileMD5Fail("ZRetryPatcher");
                    ZRetryPatcher.this.a(ZRetryPatcher.this.b);
                    return;
                }
                LoggerUtils.writePatchLogFail("ZRetryPatcher");
                ZRetryPatcher.this.a(ZRetryPatcher.this.b);
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "ZRetryPatcher", e);
            }
        }
    };

    public ZRetryPatcher(Context context, String newFileUrl, String newFilePath, String oldFilePath, String patchFileUrl, String newFileMD5, String patchFileMD5, PatchCallBack callBack) {
        super(context, newFilePath, oldFilePath, patchFileUrl, newFileMD5, patchFileMD5, callBack);
        this.a = newFileUrl;
        LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "ZRetryPatcher init");
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void startPatch() {
        if (isLoadLibrary) {
            LoggerUtils.logDoPatchServiceStart("ZRetryPatcher");
            downloadPatch(getPatcherDownloadListener());
            return;
        }
        a(this.b);
    }

    public TransportCallback getPatcherDownloadListener() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void downloadPatch(TransportCallback callBack) {
        String patchFile = PatchUtils.getPatchFile(this.mContext, this.mPatchFileUrl);
        this.mPatchFilePath = patchFile;
        delPatcherFile();
        if (TextUtils.isEmpty(patchFile) || !PatchUtils.isFileExists(this.mOldFilePath)) {
            a(this.b);
            return;
        }
        LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "start downloadPatch...");
        DownloadRequest request = new DownloadRequest(this.mPatchFileUrl, patchFile, null, null);
        request.setTransportCallback(callBack);
        request.setUseEtag(false);
        this.mPatchDownloadTask = this.mDownloadEngine.addDownload(request);
    }

    /* access modifiers changed from: private */
    public void a(TransportCallback callBack) {
        LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "downloadNewFile url = " + this.mNewFilePath);
        if (TextUtils.isEmpty(this.a)) {
            onFail(101);
            return;
        }
        LoggerUtils.writePatchLog("downloadNewFile-Start-ZRetryPatcher");
        LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "start downloadNewFile...");
        DownloadRequest request = new DownloadRequest(this.a, this.mNewFilePath, null, null);
        request.setTransportCallback(callBack);
        request.setUseEtag(false);
        this.mNewFileDownloadTask = this.mDownloadEngine.addDownload(request);
    }

    /* access modifiers changed from: protected */
    public Runnable getPatchTask() {
        return this.patchTask;
    }

    /* access modifiers changed from: private */
    public boolean a() {
        LoggerFactory.getTraceLogger().debug("ZRetryPatcher", "verifyNewFileMD5...");
        if (TextUtils.isEmpty(this.mNewFilePath)) {
            return false;
        }
        if (PatchUtils.checkFileInMd5(this.mNewFileMD5, new File(this.mNewFilePath))) {
            return true;
        }
        PatchUtils.deleteFileByPath(this.mNewFilePath);
        return false;
    }

    public void CancelTask() {
        if (this.mPatchDownloadTask != null && !this.mPatchDownloadTask.isDone()) {
            this.mPatchDownloadTask.cancel(false);
        } else if (this.mNewFileDownloadTask != null && !this.mNewFileDownloadTask.isDone()) {
            this.mNewFileDownloadTask.cancel(false);
        }
    }
}
