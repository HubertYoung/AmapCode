package com.autonavi.bundle.amaphome.vui;

import android.text.TextUtils;
import android.util.Pair;
import com.autonavi.common.Callback;
import com.uc.webview.export.internal.SDKFactory;

public class VoiceOperationManagerImpl$3 implements Callback<awg> {
    final /* synthetic */ int a;
    final /* synthetic */ boolean b;
    final /* synthetic */ arv c;

    public VoiceOperationManagerImpl$3(arv arv, int i, boolean z) {
        this.c = arv;
        this.a = i;
        this.b = z;
    }

    public void error(Throwable th, boolean z) {
        ku a2 = ku.a();
        StringBuilder sb = new StringBuilder("getReverseGeocodeDesc callback error  = ");
        sb.append(th.toString());
        a2.a((String) "voiceoperation", sb.toString());
        arv.b(this.a, (int) SDKFactory.getCoreType);
    }

    public void callback(awg awg) {
        String str = "";
        if (awg != null) {
            str = awg.c;
        }
        if (!TextUtils.isEmpty(str)) {
            ku.a().a((String) "voiceoperation", "getReverseGeocodeDesc callback success desc = ".concat(String.valueOf(str)));
            arv.a(this.a, new Pair("message", str));
            if (this.b) {
                arv.a();
            }
            return;
        }
        ku.a().a((String) "voiceoperation", (String) "getReverseGeocodeDesc callback success but desc == null");
        arv.b(this.a, (int) SDKFactory.getCoreType);
    }
}
