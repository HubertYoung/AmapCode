package com.autonavi.minimap.operation.inter;

import com.autonavi.common.Callback;
import com.autonavi.sdk.location.LocationInstrument;

public class PicPhotoDialog$1$1 implements Callback<Object> {
    final /* synthetic */ AnonymousClass1 a;

    public void error(Throwable th, boolean z) {
    }

    public PicPhotoDialog$1$1(AnonymousClass1 r1) {
        this.a = r1;
    }

    public void callback(Object obj) {
        dtd.this.m = LocationInstrument.getInstance().getLatestPosition();
    }
}
