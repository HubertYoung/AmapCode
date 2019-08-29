package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class d implements ValueCallback<UpdateTask> {
    d() {
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        WaStat.stat((String) IWaStat.VIDEO_UNZIP);
        SDKFactory.invoke(10001, Long.valueOf(8192));
    }
}
