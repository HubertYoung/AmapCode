package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$2 implements ValueCallback<SetupTask> {
    public final void onReceiveValue(SetupTask setupTask) {
        AMapLog.i("UCCore.init", "update progress: ".concat(String.valueOf(((Integer) setupTask.invokeO(UCAsyncTask.getPercent, new Object[0])).intValue())));
    }
}
