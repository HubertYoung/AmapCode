package com.uc.webview.export.business.setup;

import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.HashMap;

/* compiled from: ProGuard */
final class b extends HashMap<String, String> {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
        put(IWaStat.BUSINESS_INIT_START, Long.toString(this.a.b.a));
        put(IWaStat.BUSINESS_CHECK_INPUT_CONDITIONS, Long.toString(this.a.d.a));
        put(IWaStat.BUSINESS_CHECK_NEW_CORE, Long.toString(this.a.e.a));
        put(IWaStat.BUSINESS_CHECK_OLD_CORE, Long.toString(this.a.f.a));
        put(IWaStat.BUSINESS_INIT_PROCESS, Long.toString(this.a.c.a));
    }
}
