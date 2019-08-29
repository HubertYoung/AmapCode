package com.uc.webview.export.utility.download;

import com.uc.webview.export.cyclone.UCCyclone;

/* compiled from: ProGuard */
final class f implements Runnable {
    final /* synthetic */ UpdateTask a;

    f(UpdateTask updateTask) {
        this.a = updateTask;
    }

    public final void run() {
        try {
            synchronized (this.a) {
                UCCyclone.recursiveDelete(this.a.getUpdateDir(), false, null);
            }
        } catch (Throwable unused) {
        }
    }
}
