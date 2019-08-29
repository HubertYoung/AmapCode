package com.uc.webview.export.extension;

import android.webkit.ValueCallback;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.utility.download.UpdateTask;
import java.io.File;
import java.util.Map;

/* compiled from: ProGuard */
final class i implements ValueCallback<UpdateTask> {
    final /* synthetic */ Map a;
    final /* synthetic */ File b;

    i(Map map, File file) {
        this.a = map;
        this.b = file;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UpdateTask updateTask = (UpdateTask) obj;
        if (this.a != null) {
            ValueCallback valueCallback = (ValueCallback) this.a.get("exists");
            if (valueCallback != null) {
                try {
                    valueCallback.onReceiveValue(null);
                } catch (Throwable unused) {
                }
            }
        }
        try {
            Thread.sleep(10000);
            StringBuilder sb = new StringBuilder();
            sb.append(updateTask.getUpdateDir().getAbsolutePath());
            sb.append("/");
            if (sb.toString().equals(SDKFactory.y)) {
                UCCyclone.recursiveDelete(this.b, true, updateTask.getUpdateDir());
            }
        } catch (Exception unused2) {
        }
    }
}
