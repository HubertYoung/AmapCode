package com.autonavi.minimap.route.bus.busline.impl;

import com.autonavi.common.Callback;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;

public class BusLineRequestImpl$1 implements Callback<IBusLineSearchResult> {
    final /* synthetic */ Callback a;
    final /* synthetic */ dug b;

    public BusLineRequestImpl$1(dug dug, Callback callback) {
        this.b = dug;
        this.a = callback;
    }

    public void callback(IBusLineSearchResult iBusLineSearchResult) {
        if (this.a != null) {
            this.a.callback(iBusLineSearchResult);
        }
    }

    public void error(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.error(th, z);
        }
    }
}
