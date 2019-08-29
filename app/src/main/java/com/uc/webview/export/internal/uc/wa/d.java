package com.uc.webview.export.internal.uc.wa;

import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
public final class d implements Runnable {
    final /* synthetic */ a a;

    public d(a aVar) {
        this.a = aVar;
    }

    public final void run() {
        try {
            a.c(this.a);
        } catch (Throwable th) {
            Log.i("SDKWaStat", "update", th);
        }
    }
}
