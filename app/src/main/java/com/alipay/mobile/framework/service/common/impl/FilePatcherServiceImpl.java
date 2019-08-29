package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.patch.BasePatcher;
import com.alipay.mobile.common.patch.PatchCallBack;
import com.alipay.mobile.common.patch.ZPatcher;
import com.alipay.mobile.common.patch.ZRetryPatcher;
import com.alipay.mobile.framework.service.common.FilePatcherService;

public class FilePatcherServiceImpl extends FilePatcherService {
    public FilePatcherServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void doPatch(Context context, String newFilePath, String oldFilePath, String patchFileUrl, String newFileMD5, String patchFileMD5, PatchCallBack callBack) {
        new ZPatcher(context, newFilePath, oldFilePath, patchFileUrl, newFileMD5, patchFileMD5, callBack).startPatch();
    }

    public void doPatch(Context context, String newFileUrl, String newFilePath, String oldFilePath, String patchFileUrl, String newFileMD5, String patchFileMD5, PatchCallBack callBack) {
        new ZRetryPatcher(context, newFileUrl, newFilePath, oldFilePath, patchFileUrl, newFileMD5, patchFileMD5, callBack).startPatch();
    }

    public boolean patcher(String newFilePath, String oldFilePath, String patchFilePath, String newFileMD5, String patchFileMD5) {
        return BasePatcher.patcher(newFilePath, oldFilePath, patchFilePath, newFileMD5, patchFileMD5);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
    }
}
