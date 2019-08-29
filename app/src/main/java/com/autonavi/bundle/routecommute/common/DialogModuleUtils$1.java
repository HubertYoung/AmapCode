package com.autonavi.bundle.routecommute.common;

import android.text.TextUtils;
import com.autonavi.common.Callback;

public class DialogModuleUtils$1 implements Callback<ctm> {
    final /* synthetic */ ctl a;
    final /* synthetic */ azr b;
    final /* synthetic */ String c;

    public DialogModuleUtils$1(ctl ctl, azr azr, String str) {
        this.a = ctl;
        this.b = azr;
        this.c = str;
    }

    public final void error(Throwable th, boolean z) {
        azb.a("DialogModuleUtils", th.getMessage());
    }

    public final void callback(ctm ctm) {
        if (ctm != null && ctm.a == 1 && !TextUtils.isEmpty(ctm.c)) {
            this.a.a(this.b.a(), this.c, ctm.c);
            this.b.b();
        }
    }
}
