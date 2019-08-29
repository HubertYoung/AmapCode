package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class e implements ValueCallback<UpdateTask> {
    e() {
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        WaStat.stat((String) IWaStat.VIDEO_DOWNLOAD);
        SDKFactory.invoke(10001, Long.valueOf(32768));
    }
}
