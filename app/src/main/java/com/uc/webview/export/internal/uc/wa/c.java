package com.uc.webview.export.internal.uc.wa;

import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
public final class c implements Runnable {
    final /* synthetic */ a a;

    public c(a aVar) {
        this.a = aVar;
    }

    public final void run() {
        try {
            a.b(this.a.a(this.a.k.getSharedPreferences("UC_WA_STAT", 0)));
        } catch (Throwable th) {
            Log.i("SDKWaStat", "generateUUID", th);
        }
    }
}
