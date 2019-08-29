package com.uc.webview.export.business.setup;

import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.HashMap;

/* compiled from: ProGuard */
final class g extends HashMap<String, String> {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    g(a aVar, String str) {
        this.b = aVar;
        this.a = str;
        put(IWaStat.BUSINESS_ELAPSE_INIT_TYPE, this.a);
        put(IWaStat.BUSINESS_ELAPSE_INIT_CHECK, this.b.g.b);
        put(IWaStat.BUSINESS_ELAPSE_INIT_CHECK_CPU, this.b.g.c);
        put(IWaStat.BUSINESS_ELAPSE_SETUP_CALLBACK, this.b.g.d);
        put(IWaStat.BUSINESS_ELAPSE_SETUP_CALLBACK_CPU, this.b.g.e);
        put(IWaStat.BUSINESS_ELAPSE_SUCCESS_CALLBACK, this.b.g.f);
        put(IWaStat.BUSINESS_ELAPSE_SUCCESS_CALLBACK_CPU, this.b.g.g);
    }
}
