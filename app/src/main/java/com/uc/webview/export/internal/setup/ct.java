package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class ct implements ValueCallback<UpdateTask> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, "downloadFileDelete"));
    final /* synthetic */ cn b;

    ct(cn cnVar) {
        this.b = cnVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        this.b.b = ((UpdateTask) obj).getFilePath();
        if (this.a != null) {
            this.a.onReceiveValue(this.b);
        }
    }
}
