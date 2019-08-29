package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class o implements ValueCallback<UpdateTask> {
    o() {
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        WaStat.stat((String) IWaStat.VIDEO_UNZIP_SUCCESS);
    }
}
