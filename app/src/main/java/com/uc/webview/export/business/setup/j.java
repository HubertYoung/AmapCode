package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class j implements ValueCallback<BaseSetupTask> {
    final /* synthetic */ a a;

    j(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        BaseSetupTask baseSetupTask = (BaseSetupTask) obj;
        String a2 = a.a;
        StringBuilder sb = new StringBuilder("mDieDelegateCallback ");
        sb.append(baseSetupTask.toString());
        sb.append(" init type: ");
        sb.append(baseSetupTask.getInitType());
        Log.d(a2, sb.toString());
        a.j(this.a);
        a.c(this.a, baseSetupTask.getInitType());
        if (com.uc.webview.export.internal.utility.j.e()) {
            WaStat.saveData(true);
            WaStat.upload();
        }
        String a3 = a.a;
        StringBuilder sb2 = new StringBuilder("options: ");
        sb2.append(this.a.mOptions);
        Log.d(a3, sb2.toString());
    }
}
