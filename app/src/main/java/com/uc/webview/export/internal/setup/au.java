package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class au implements ValueCallback<l> {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ ValueCallback c;
    final /* synthetic */ at d;

    au(at atVar, Context context, String str, ValueCallback valueCallback) {
        this.d = atVar;
        this.a = context;
        this.b = str;
        this.c = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        if (!l.a(this.a, (String) this.d.getOption(UCCore.OPTION_DECOMPRESS_ROOT_DIR))) {
            ((t) ((t) ((t) ((t) ((t) at.a(null).invoke(10001, this.d)).setup((String) UCCore.OPTION_CONTEXT, (Object) this.a)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) this.b)).onEvent((String) "stat", (ValueCallback<CALLBACK_TYPE>) new aw<CALLBACK_TYPE>(this))).onEvent((String) "setup", (ValueCallback<CALLBACK_TYPE>) new av<CALLBACK_TYPE>(this))).start();
        }
    }
}
