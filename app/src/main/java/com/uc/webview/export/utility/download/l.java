package com.uc.webview.export.utility.download;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;

/* compiled from: ProGuard */
final class l implements ValueCallback<DownloadTask> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ UpdateTask b;

    l(UpdateTask updateTask, ValueCallback valueCallback) {
        this.b = updateTask;
        this.a = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        DownloadTask downloadTask = (DownloadTask) obj;
        downloadTask.delete();
        StringBuilder sb = new StringBuilder();
        sb.append(new StringBuilder("DownloadTask failed with:"));
        sb.append(downloadTask.getException() != null ? downloadTask.getException().getMessage() : "");
        String sb2 = sb.toString();
        WaStat.stat((String) IWaStat.UCM_FAILED_DOWNLOAD);
        this.b.e[1] = new RuntimeException(sb2);
        try {
            if (this.a != null) {
                this.a.onReceiveValue(this.b);
            }
        } catch (Throwable unused) {
        }
    }
}
