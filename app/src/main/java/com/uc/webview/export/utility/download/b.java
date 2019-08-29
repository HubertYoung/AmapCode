package com.uc.webview.export.utility.download;

/* compiled from: ProGuard */
final class b implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ DownloadTask b;

    b(DownloadTask downloadTask, Runnable runnable) {
        this.b = downloadTask;
        this.a = runnable;
    }

    public final void run() {
        try {
            synchronized (this.b) {
                this.a.run();
            }
        } catch (Throwable unused) {
        }
    }
}
