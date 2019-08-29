package com.autonavi.bundle.amaphome.vui;

import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.uc.webview.export.internal.SDKFactory;

public class VoiceOperationManagerImpl$1 implements Callback<Status> {
    final /* synthetic */ int a;
    final /* synthetic */ arv b;

    public VoiceOperationManagerImpl$1(arv arv, int i) {
        this.b = arv;
        this.a = i;
    }

    public void callback(Status status) {
        if (status == Status.ON_LOCATION_OK) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                this.b.a(this.a, latestPosition, false);
                return;
            }
            ku.a().a((String) "voiceoperation", (String) "getMyLocation callback success but gpPoint == null");
            arv.b(this.a, (int) SDKFactory.getCoreType);
            return;
        }
        if (status == Status.ON_LOCATION_FAIL) {
            ku.a().a((String) "voiceoperation", (String) "getMyLocation callback fail");
            arv.b(this.a, (int) SDKFactory.getCoreType);
        }
    }

    public void error(Throwable th, boolean z) {
        ku a2 = ku.a();
        StringBuilder sb = new StringBuilder("getMyLocation callback error  = ");
        sb.append(th.toString());
        a2.a((String) "voiceoperation", sb.toString());
        arv.b(this.a, (int) SDKFactory.getCoreType);
    }
}
