package com.autonavi.minimap.route.ride.dest.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.Ajx3Page;

public class AjxRideMapPresenter$3 implements Callback<ctm> {
    final /* synthetic */ edm a;

    public void error(Throwable th, boolean z) {
    }

    public AjxRideMapPresenter$3(edm edm) {
        this.a = edm;
    }

    public void callback(ctm ctm) {
        if (ctm != null && this.a.mPage != null && ((Ajx3Page) this.a.mPage).isStarted()) {
            this.a.d.a((AbstractBasePage) this.a.mPage, "7", ctm.c);
            eba.a().a = false;
        }
    }
}
