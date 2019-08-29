package com.uc.webview.export.business;

import android.content.Context;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.business.setup.a;
import com.uc.webview.export.business.setup.o;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.utility.SetupTask;

@Api
/* compiled from: ProGuard */
public class BusinessWrapper {
    private static final String a = "BusinessWrapper";
    private static a b;

    private static synchronized a a() {
        a aVar;
        synchronized (BusinessWrapper.class) {
            if (b == null) {
                a aVar2 = new a();
                b = aVar2;
                aVar2.setup((String) UCCore.OPTION_ROOT_TASK_KEY, (Object) "BusinessSetupTask");
            }
            aVar = b;
        }
        return aVar;
    }

    public static SetupTask setup(String str, Object obj) {
        return (a) a().setup(str, obj);
    }

    public static void decompressAndODex(Context context, String str, String str2, String str3, boolean z, String[] strArr, boolean z2, long j, ValueCallback<Bundle> valueCallback) throws UCSetupException {
        ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) decompressAndODex(UCCore.OPTION_CONTEXT, context).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) str)).setup((String) UCCore.OPTION_DECOMPRESS_ROOT_DIR, (Object) str3)).setup((String) UCCore.OPTION_ZIP_FILE_TYPE, (Object) str2)).setup((String) UCCore.OPTION_DELETE_AFTER_EXTRACT, (Object) Boolean.valueOf(z))).setup((String) "o_flag_odex_done", (Object) Boolean.valueOf(z2))).setup((String) UCCore.OPTION_PROVIDED_KEYS, (Object) strArr)).setup((String) UCCore.OPTION_DECOMPRESS_AND_ODEX_CALLBACK, (Object) valueCallback)).start(j);
    }

    public static SetupTask decompressAndODex(String str, Object obj) throws UCSetupException {
        return (SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) new o().setup((String) UCCore.OPTION_SETUP_THREAD_PRIORITY, (Object) Integer.valueOf(-20))).setup((String) UCCore.OPTION_ROOT_TASK_KEY, (Object) "decompressAndODex")).setup((String) UCCore.OPTION_CONTINUE_ODEX_ON_DECOMPRESSED, (Object) Boolean.TRUE)).setup(str, obj);
    }
}
