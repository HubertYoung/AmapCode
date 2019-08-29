package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.utility.UCMPackageInfo;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ProGuard */
public final class at extends UCSetupTask<at, at> {
    public final void run() {
        Context context = (Context) getOption(UCCore.OPTION_CONTEXT);
        String str = (String) getOption(UCCore.OPTION_UCM_ZIP_FILE);
        ValueCallback valueCallback = (ValueCallback) getOption("stat");
        File file = (File) UCMPackageInfo.invoke(10003, context);
        if (file.list().length <= 0 || UCMPackageInfo.checkNeedDecompress(context, file.getAbsolutePath(), str)) {
            ((l) ((l) ((l) ((l) ((l) new l().invoke(10001, this)).setup(UCCore.OPTION_CONTEXT, context)).setup(UCCore.OPTION_DECOMPRESS_ROOT_DIR, getOption(UCCore.OPTION_DECOMPRESS_ROOT_DIR))).onEvent("stat", new ax(this, valueCallback))).onEvent("success", new au(this, context, str, valueCallback))).start();
        }
    }

    public static t a(ConcurrentHashMap<String, Object> concurrentHashMap) {
        n nVar = new n();
        if (concurrentHashMap != null) {
            nVar.setOptions(concurrentHashMap);
        }
        ((t) nVar.setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_DIR, (Object) null);
        return nVar;
    }
}
