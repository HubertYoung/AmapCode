package com.autonavi.minimap.route.ride.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.ride.page.RideFinishMapPage;

public class RideFinishMapPresenter$2 implements Callback<ctm> {
    final /* synthetic */ String a;
    final /* synthetic */ eem b;

    public void error(Throwable th, boolean z) {
    }

    public RideFinishMapPresenter$2(eem eem, String str) {
        this.b = eem;
        this.a = str;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && RideFinishMapPresenter$2.this.b.mPage != null && ((RideFinishMapPage) RideFinishMapPresenter$2.this.b.mPage).isStarted()) {
                    RideFinishMapPresenter$2.this.b.q = true;
                    RideFinishMapPresenter$2.this.b.o.a((AbstractBasePage) RideFinishMapPresenter$2.this.b.mPage, RideFinishMapPresenter$2.this.a, ctm.c);
                }
            }
        }, 1000);
    }
}
