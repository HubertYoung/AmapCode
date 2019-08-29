package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class ck implements ValueCallback<Object> {
    final /* synthetic */ UCSetupTask a;

    ck(UCSetupTask uCSetupTask) {
        this.a = uCSetupTask;
    }

    public final void onReceiveValue(Object obj) {
        Log.d("UCSetupTask", "print_log onReceiveValue value: ".concat(String.valueOf(obj)));
        if (obj != null) {
            this.a.a(true, ((Boolean) obj).booleanValue());
        } else {
            this.a.a(false, true);
        }
        this.a.j = true;
    }
}
