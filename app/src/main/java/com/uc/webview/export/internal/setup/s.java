package com.uc.webview.export.internal.setup;

/* compiled from: ProGuard */
final class s implements Runnable {
    final /* synthetic */ bx a;
    final /* synthetic */ r b;

    s(r rVar, bx bxVar) {
        this.b = rVar;
        this.a = bxVar;
    }

    public final void run() {
        try {
            this.b.a();
            this.a.a(0, null);
        } catch (Throwable th) {
            this.a.a(3, th);
        }
    }
}
