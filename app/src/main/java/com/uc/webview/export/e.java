package com.uc.webview.export;

import android.util.Pair;
import java.lang.reflect.Constructor;

/* compiled from: ProGuard */
final class e implements Runnable {
    final /* synthetic */ Constructor a;
    final /* synthetic */ d b;

    e(d dVar, Constructor constructor) {
        this.b = dVar;
        this.a = constructor;
    }

    public final void run() {
        try {
            this.b.d.onReceiveValue(new Pair((WebView) this.a.newInstance(this.b.c), null));
        } catch (Throwable th) {
            this.b.d.onReceiveValue(new Pair(null, th));
        }
    }
}
