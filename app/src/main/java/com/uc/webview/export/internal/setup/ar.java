package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class ar implements ValueCallback<t> {
    final /* synthetic */ af a;

    ar(af afVar) {
        this.a = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        if (((t) obj) instanceof be) {
            Log.d("SdkSetupTask", "ShareCoreSdcardSetupTask.EVENT_DELAY_SEARCH_CORE_FILE callback");
            this.a.d = af.p(this.a);
        }
    }
}
