package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cs implements ValueCallback<UpdateTask> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, "updateProgress"));
    final /* synthetic */ cn b;

    cs(cn cnVar) {
        this.b = cnVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        this.b.mPercent = ((UpdateTask) obj).getPercent();
        if (this.a != null) {
            this.a.onReceiveValue(this.b);
        }
    }
}
