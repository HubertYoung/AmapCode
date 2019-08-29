package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.alipay.mobile.h5container.api.H5PageData;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.UCAsyncTask.a;
import com.uc.webview.export.internal.utility.j;
import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ProGuard */
public final class ae extends t {
    public final void run() {
        if (j.a((String) getOption(UCCore.OPTION_UCM_LIB_DIR))) {
            throw new UCSetupException(3009, String.format("Option [%s] expected.", new Object[]{UCCore.OPTION_UCM_LIB_DIR}));
        }
        Context context = (Context) getOption(UCCore.OPTION_CONTEXT);
        boolean z = ((File) UCMPackageInfo.invoke(10006, context)).list().length > 0;
        File expectFile = UCCyclone.expectFile(context.getApplicationInfo().sourceDir);
        File file = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(10006, context), UCCyclone.getSourceHash(expectFile.getAbsolutePath())), UCCyclone.getSourceHash(expectFile.length(), expectFile.lastModified()));
        boolean decompressIfNeeded = UCCyclone.decompressIfNeeded(context, false, expectFile, file, (FilenameFilter) UCMPackageInfo.invoke(UCMPackageInfo.getLibFilter, new Object[0]), false);
        ConcurrentHashMap concurrentHashMap = this.mCallbacks;
        this.mCallbacks = null;
        Object[] objArr = {concurrentHashMap};
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append("/lib");
        t tVar = (t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new bw().invoke(10001, this)).setOptions(this.mOptions)).invoke(10002, objArr)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) sb.toString());
        if (!decompressIfNeeded || !z) {
            tVar.onEvent((String) H5PageData.KEY_UC_START, (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>());
        } else {
            resetCrashFlag();
        }
        tVar.start();
    }
}
