package com.uc.webview.export.internal.setup;

import android.util.Pair;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import java.util.HashMap;

/* compiled from: ProGuard */
public final class z extends UCSubSetupTask<z, z> implements ValueCallback<Pair<String, HashMap<String, String>>> {
    public final /* synthetic */ void onReceiveValue(Object obj) {
        callbackStat((Pair) obj);
    }

    public final void run() {
        SDKFactory.b = this;
    }
}
