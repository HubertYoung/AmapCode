package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.utility.SetupTask;

public class UCInitializer$8 implements ValueCallback<SetupTask> {
    final /* synthetic */ long val$begin;

    public UCInitializer$8(long j) {
        this.val$begin = j;
    }

    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("UCCore init UCMobilewebkit finished:");
        sb.append(System.currentTimeMillis() - this.val$begin);
        AMapLog.d("UCCore.init", sb.toString());
    }
}
