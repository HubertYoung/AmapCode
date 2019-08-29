package com.uc.webview.export.internal.setup;

import android.os.HandlerThread;

/* compiled from: ProGuard */
final class by extends HandlerThread {
    final /* synthetic */ UCAsyncTask a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    by(UCAsyncTask uCAsyncTask, String str, int i) {
        // this.a = uCAsyncTask;
        super(str, i);
    }

    /* access modifiers changed from: protected */
    public final void onLooperPrepared() {
        this.a.j = new bz(this, getLooper()).post(this.a);
    }
}
