package com.uc.webview.export.extension;

import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class k implements Runnable {
    final /* synthetic */ UpdateTask a;
    final /* synthetic */ j b;

    k(j jVar, UpdateTask updateTask) {
        this.b = jVar;
        this.a = updateTask;
    }

    public final void run() {
        this.a.start();
    }
}
