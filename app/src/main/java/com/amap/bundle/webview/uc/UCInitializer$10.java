package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$10 implements ValueCallback<SetupTask> {
    final /* synthetic */ long val$begin;

    public UCInitializer$10(long j) {
        this.val$begin = j;
    }

    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("UCCore setup U3 dex files finished:");
        sb.append(System.currentTimeMillis() - this.val$begin);
        AMapLog.d("UCCore.init", sb.toString());
    }
}
