package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.business.a.d;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import com.uc.webview.export.internal.setup.l;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.SetupTask;

/* compiled from: ProGuard */
final class i implements ValueCallback<BaseSetupTask> {
    final /* synthetic */ a a;

    i(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        BaseSetupTask baseSetupTask = (BaseSetupTask) obj;
        String a2 = a.a;
        StringBuilder sb = new StringBuilder("mExceptionCallback ");
        sb.append(baseSetupTask.getException().errCode());
        sb.append(":");
        sb.append(baseSetupTask.getException().getMessage());
        Log.d(a2, sb.toString());
        this.a.c.a(d.g);
        baseSetupTask.getException().errCode();
        if (l.a(baseSetupTask.getException().errCode())) {
            Log.d(a.a, "mExceptionCallback isErrorCodeShouldCleanup.");
            ((l) ((l) ((l) new l().invoke(10001, SetupTask.getRoot())).setup(UCCore.OPTION_CONTEXT, this.a.getContext())).setup(UCCore.OPTION_DECOMPRESS_ROOT_DIR, this.a.mOptions.get(UCCore.OPTION_BUSINESS_DECOMPRESS_ROOT_PATH))).start();
        }
        a.a(this.a, LogCategory.CATEGORY_EXCEPTION, baseSetupTask);
    }
}
