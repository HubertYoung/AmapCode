package com.uc.webview.export.internal.setup;

import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
final class cz implements Runnable {
    final /* synthetic */ UpdateTask a;
    final /* synthetic */ cy b;

    cz(cy cyVar, UpdateTask updateTask) {
        this.b = cyVar;
        this.a = updateTask;
    }

    public final void run() {
        this.a.start();
    }
}
