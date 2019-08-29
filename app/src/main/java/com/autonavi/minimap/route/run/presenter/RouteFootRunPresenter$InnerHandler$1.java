package com.autonavi.minimap.route.run.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.run.page.RouteFootRunMapPage;

class RouteFootRunPresenter$InnerHandler$1 implements Callback<ctm> {
    final /* synthetic */ RouteFootRunPresenter a;
    final /* synthetic */ c b;

    public void error(Throwable th, boolean z) {
    }

    RouteFootRunPresenter$InnerHandler$1(c cVar, RouteFootRunPresenter routeFootRunPresenter) {
        this.b = cVar;
        this.a = routeFootRunPresenter;
    }

    public void callback(ctm ctm) {
        if (ctm != null && this.a.mPage != null && ((RouteFootRunMapPage) this.a.mPage).isStarted()) {
            this.a.I = false;
            this.a.H.a((AbstractBasePage) this.a.mPage, "9", ctm.c);
        }
    }
}
