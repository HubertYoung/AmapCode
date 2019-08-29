package com.uc.webview.export.internal.setup;

import android.os.Handler;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cy implements ValueCallback<UpdateTask> {
    final /* synthetic */ cn a;
    private int b = 3;

    cy(cn cnVar) {
        this.a = cnVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        UpdateTask updateTask = (UpdateTask) obj;
        Log.d("UpdateSetupTask", LogCategory.CATEGORY_EXCEPTION);
        synchronized (this.a) {
            this.a.c = true;
        }
        int i = this.b;
        this.b = i - 1;
        if (i > 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new cz(this, updateTask), 60000);
        }
    }
}
