package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.sdk.util.e;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cv implements ValueCallback<UpdateTask> {
    final /* synthetic */ bx a;
    final /* synthetic */ cn b;

    cv(cn cnVar, bx bxVar) {
        this.b = cnVar;
        this.a = bxVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UpdateTask updateTask = (UpdateTask) obj;
        Log.d("UpdateSetupTask", e.b);
        synchronized (this.b) {
            this.b.d = true;
        }
        updateTask.delete();
        this.a.a(3, updateTask.getException());
    }
}
