package com.uc.webview.export.utility.download;

import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.j;
import java.io.File;

/* compiled from: ProGuard */
final class c implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ DownloadTask b;

    c(DownloadTask downloadTask, boolean z) {
        this.b = downloadTask;
        this.a = z;
    }

    public final void run() {
        try {
            File file = new File(this.b.d[1]);
            synchronized (this.b) {
                WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_UPD_FILE_THREAD_PV);
                if (this.a) {
                    WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_UPD_FILE_THREAD_SH_PV);
                    try {
                        if (this.b.c[9] != null) {
                            WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_UPD_FILE_THREAD_CB_PV);
                            if (!j.a(this.b.getFilePath())) {
                                WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_UPD_FILE_THREAD_CALL_PV);
                                this.b.c[9].onReceiveValue(this.b);
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
                UCCyclone.recursiveDelete(file, false, null);
            }
        } catch (Throwable unused2) {
        }
    }
}
