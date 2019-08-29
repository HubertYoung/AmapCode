package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$4 implements ValueCallback<SetupTask> {
    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("UCCore exception: ");
        sb.append(setupTask.getException() != null ? setupTask.getException().getMessage() : "");
        AMapLog.e("UCCore.init", sb.toString());
        ajk.b();
    }
}
