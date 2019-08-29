package com.uc.webview.export.utility.download;

/* compiled from: ProGuard */
final class j implements Runnable {
    final /* synthetic */ DownloadTask a;
    final /* synthetic */ i b;

    j(i iVar, DownloadTask downloadTask) {
        this.b = iVar;
        this.a = downloadTask;
    }

    public final void run() {
        this.a.start();
    }
}
