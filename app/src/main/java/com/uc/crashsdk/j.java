package com.uc.crashsdk;

import com.uc.crashsdk.a.a;

/* compiled from: ProGuard */
final class j implements Runnable {
    final /* synthetic */ CrashLogFilesUploader a;

    j(CrashLogFilesUploader crashLogFilesUploader) {
        this.a = crashLogFilesUploader;
    }

    public final void run() {
        try {
            CrashLogFilesUploader.a();
        } catch (Throwable th) {
            a.a(th, false);
        }
        this.a.a.sendEmptyMessage(0);
    }
}
