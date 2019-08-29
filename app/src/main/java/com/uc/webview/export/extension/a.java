package com.uc.webview.export.extension;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.setup.cn;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.utility.j;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
final class a extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ Map b;
    final /* synthetic */ Callable c;
    final /* synthetic */ String d;
    final /* synthetic */ Map e;

    a(Context context, Map map, Callable callable, String str, Map map2) {
        this.a = context;
        this.b = map;
        this.c = callable;
        this.d = str;
        this.e = map2;
    }

    public final void run() {
        int i = 10;
        while (true) {
            if (((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue() || SDKFactory.q) {
                break;
            }
            int i2 = i - 1;
            if (i <= 0) {
                i = i2;
                break;
            } else {
                try {
                    Thread.sleep(200);
                } catch (Exception unused) {
                }
                i = i2;
            }
        }
        if (i <= 0) {
            throw new UCSetupException((String) "Waiting timeout for UCCore initialization finish!");
        }
        try {
            UCAsyncTask uCAsyncTask = new UCAsyncTask(Integer.valueOf(0));
            cn cnVar = new cn();
            ((t) ((t) ((t) ((t) ((t) ((t) ((t) cnVar.setup((String) UCCore.OPTION_CONTEXT, (Object) this.a.getApplicationContext())).invoke(10001, uCAsyncTask)).setup((String) UCCore.OPTION_UCM_ZIP_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) null)).setup((String) UCCore.OPTION_USE_SDK_SETUP, (Object) Boolean.TRUE)).setup((String) "chkMultiCore", (Object) Boolean.TRUE)).onEvent((String) "downloadException", (ValueCallback<CALLBACK_TYPE>) new c<CALLBACK_TYPE>(this))).onEvent((String) "updateProgress", (ValueCallback<CALLBACK_TYPE>) new b<CALLBACK_TYPE>(this));
            if (this.c != null) {
                cnVar.setup((String) UCCore.OPTION_DOWNLOAD_CHECKER, (Object) this.c);
            }
            if (!j.a(this.d)) {
                cnVar.setup((String) UCCore.OPTION_UCM_UPD_URL, (Object) this.d);
            }
            if (this.e != null && !this.e.isEmpty()) {
                for (Entry entry : this.e.entrySet()) {
                    cnVar.setup((String) entry.getKey(), entry.getValue());
                }
            }
            cnVar.start(2000);
            uCAsyncTask.start();
        } catch (Throwable unused2) {
        }
    }
}
