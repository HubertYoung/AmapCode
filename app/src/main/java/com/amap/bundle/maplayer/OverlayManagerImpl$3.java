package com.amap.bundle.maplayer;

import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;

public class OverlayManagerImpl$3 implements Callback<Integer> {
    final /* synthetic */ POI a;
    final /* synthetic */ xn b;

    public void error(Throwable th, boolean z) {
    }

    public OverlayManagerImpl$3(xn xnVar, POI poi) {
        this.b = xnVar;
        this.a = poi;
    }

    public void callback(Integer num) {
        if (this.b.m == null || this.b.m.isFooterMapPointRequestOutter()) {
            xn.a(this.b, this.a, num.intValue());
        }
    }
}
