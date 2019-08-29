package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$9 implements ValueCallback<SetupTask> {
    final /* synthetic */ long val$begin;

    public UCInitializer$9(long j) {
        this.val$begin = j;
    }

    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("UCCore load U3 so files finished:");
        sb.append(System.currentTimeMillis() - this.val$begin);
        AMapLog.d("UCCore.init", sb.toString());
    }
}
