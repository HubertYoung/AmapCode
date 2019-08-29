package com.uc.webview.export.internal.setup;

import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.HashMap;

/* compiled from: ProGuard */
final class ba extends HashMap<String, String> {
    final /* synthetic */ long a;
    final /* synthetic */ long b;
    final /* synthetic */ ay c;

    ba(ay ayVar, long j, long j2) {
        this.c = ayVar;
        this.a = j;
        this.b = j2;
        put(IWaStat.SHARE_CORE_TASK_PROCESS, Long.toString(this.a));
        put(IWaStat.SHARE_CORE_TASK_BEGIN_PROCESS, Long.toString(this.b));
    }
}
