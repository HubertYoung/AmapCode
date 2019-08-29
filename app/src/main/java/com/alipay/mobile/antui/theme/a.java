package com.alipay.mobile.antui.theme;

import com.alipay.mobile.antui.excutor.ConfigCallback;

/* compiled from: ThemeInfoProcessor */
final class a implements ConfigCallback {
    final /* synthetic */ String a;
    final /* synthetic */ ThemeCallback b;

    a(String str, ThemeCallback themeCallback) {
        this.a = str;
        this.b = themeCallback;
    }

    public final void onKeyBack(String key, String config) {
        ThemeInfoProcessor.dealConfig(this.a, config, this.b);
    }
}
