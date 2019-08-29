package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.uc.wa.a;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.SetupTask;

/* compiled from: ProGuard */
final class e implements ValueCallback<SetupTask> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ BrowserSetupTask b;

    e(BrowserSetupTask browserSetupTask, ValueCallback valueCallback) {
        this.b = browserSetupTask;
        this.a = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        SetupTask setupTask = (SetupTask) obj;
        if (setupTask.getStat().second != null && this.a != null) {
            this.a.onReceiveValue(setupTask);
            if (Log.sPrintLog) {
                a.a(setupTask.getStat());
            }
        }
    }
}
