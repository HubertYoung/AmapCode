package com.uc.webview.export.extension;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.utility.download.UpdateTask;
import java.util.Map;

/* compiled from: ProGuard */
final class m implements ValueCallback<UpdateTask> {
    final /* synthetic */ Context a;
    final /* synthetic */ Map b;

    m(Context context, Map map) {
        this.a = context;
        this.b = map;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        WaStat.stat((String) IWaStat.VIDEO_DOWNLOAD_SUCCESS);
        SDKFactory.invoke(10034, this.a);
        if (this.b != null) {
            ValueCallback valueCallback = (ValueCallback) this.b.get("success");
            if (valueCallback != null) {
                try {
                    valueCallback.onReceiveValue(null);
                } catch (Throwable unused) {
                }
            }
        }
    }
}
