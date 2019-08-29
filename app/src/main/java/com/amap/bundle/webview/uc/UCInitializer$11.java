package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;
import com.amap.bundle.logs.AMapLog;
import com.uc.webview.export.WebView;
import com.uc.webview.export.utility.SetupTask;
import java.io.PrintStream;

public class UCInitializer$11 implements ValueCallback<SetupTask> {
    final /* synthetic */ long val$begin;

    public UCInitializer$11(long j) {
        this.val$begin = j;
    }

    public final void onReceiveValue(SetupTask setupTask) {
        StringBuilder sb = new StringBuilder("UCCore success init finish:");
        sb.append(System.currentTimeMillis() - this.val$begin);
        AMapLog.d("UCCore.init", sb.toString());
        StringBuilder sb2 = new StringBuilder("UC core type:");
        sb2.append(WebView.getCoreType());
        AMapLog.d("UCCore.init", sb2.toString());
        PrintStream printStream = System.out;
        new StringBuilder("UC core type:").append(WebView.getCoreType());
        ajm.d = true;
        ajk.a();
        ajm.e();
        ajm.f();
    }
}
