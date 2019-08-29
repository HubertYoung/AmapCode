package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$1 implements ValueCallback<SetupTask> {
    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("downloadException: ");
        sb.append(setupTask.getExtraException() != null ? setupTask.getExtraException().getMessage() : "");
        AMapLog.i("UCCore.init", sb.toString());
    }
}
