package com.autonavi.bundle.vui.model;

import com.autonavi.common.Callback;
import com.uc.webview.export.internal.SDKFactory;

public class SchemeOpenPageModel$1 implements Callback<Boolean> {
    final /* synthetic */ bgb a;
    final /* synthetic */ bgj b;

    public SchemeOpenPageModel$1(bgj bgj, bgb bgb) {
        this.b = bgj;
        this.a = bgb;
    }

    public void callback(Boolean bool) {
        bfh.a("VUI_JAVA", "SchemeOpenPageModel solveScheme callback aBoolean:".concat(String.valueOf(bool)));
        final boolean booleanValue = bool.booleanValue();
        this.b.a.postDelayed(new Runnable() {
            public final void run() {
                d.a.a(SchemeOpenPageModel$1.this.a.a, booleanValue ? 10000 : SDKFactory.getCoreType, (String) null);
            }
        }, 200);
    }

    public void error(Throwable th, boolean z) {
        bgb bgb = this.a;
        StringBuilder sb = new StringBuilder("SchemeOpenPageModel solveScheme error throwable:");
        sb.append(th.getMessage());
        bgj.a(bgb, sb.toString());
        this.b.a.postDelayed(new Runnable() {
            public final void run() {
                d.a.a(SchemeOpenPageModel$1.this.a.a, (int) SDKFactory.getCoreType, (String) null);
            }
        }, 200);
    }
}
