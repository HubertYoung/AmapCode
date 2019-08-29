package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.business.a.d;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class k implements ValueCallback<BaseSetupTask> {
    final /* synthetic */ a a;

    k(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        BaseSetupTask baseSetupTask = (BaseSetupTask) obj;
        String a2 = a.a;
        StringBuilder sb = new StringBuilder("mNewCoreDecompressAndODex ");
        sb.append(baseSetupTask.toString());
        Log.d(a2, sb.toString());
        a.a(this.a, "setup", baseSetupTask);
        this.a.g.d = String.valueOf(this.a.g.a.getMilis());
        this.a.g.e = String.valueOf(this.a.g.a.getMilisCpu());
        this.a.c.a(d.e);
    }
}
