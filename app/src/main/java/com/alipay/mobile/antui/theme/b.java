package com.alipay.mobile.antui.theme;

import com.alipay.mobile.antui.excutor.FileLoadCallback;
import com.alipay.mobile.antui.theme.model.AUThemeModel;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ThemeInfoProcessor */
final class b implements FileLoadCallback {
    final /* synthetic */ String a;
    final /* synthetic */ AUThemeModel b;
    final /* synthetic */ ThemeCallback c;

    b(String str, AUThemeModel aUThemeModel, ThemeCallback themeCallback) {
        this.a = str;
        this.b = aUThemeModel;
        this.c = themeCallback;
    }

    public final void onFinished(String fileName, String fileInfo) {
        AuiLogger.eventBehavor("ChangeSkin", "ReadSuccess", this.a, this.b.version, null);
        this.c.updateTheme(this.a, this.b);
    }

    public final void onError(String fileName, String errorMessage) {
        AuiLogger.eventBehavor("ChangeSkin", "ReadError", this.a, this.b.version, null);
    }
}
