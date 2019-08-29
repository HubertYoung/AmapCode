package com.uc.webview.export.extension;

import android.os.Handler;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.utility.download.UpdateTask;
import java.util.Map;

/* compiled from: ProGuard */
final class j implements ValueCallback<UpdateTask> {
    final /* synthetic */ Map a;
    private int b = 3;

    j(Map map) {
        this.a = map;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UpdateTask updateTask = (UpdateTask) obj;
        if (this.a != null) {
            ValueCallback valueCallback = (ValueCallback) this.a.get(LogCategory.CATEGORY_EXCEPTION);
            if (valueCallback != null) {
                try {
                    valueCallback.onReceiveValue(null);
                } catch (Throwable unused) {
                }
            }
        }
        int i = this.b;
        this.b = i - 1;
        if (i > 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new k(this, updateTask), 60000);
        }
    }
}
