package com.uc.crashsdk;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import com.uc.crashsdk.a.c;

/* compiled from: ProGuard */
final class i extends Handler {
    final /* synthetic */ CrashLogFilesUploader a;

    i(CrashLogFilesUploader crashLogFilesUploader) {
        this.a = crashLogFilesUploader;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                c.b("Crash log upload service done, exiting");
                this.a.stopSelf();
                Process.killProcess(Process.myPid());
                return;
            default:
                return;
        }
    }
}
