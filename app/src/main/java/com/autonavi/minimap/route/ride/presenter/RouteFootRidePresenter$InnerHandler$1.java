package com.autonavi.minimap.route.ride.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.ride.page.RouteFootRideMapPage;

class RouteFootRidePresenter$InnerHandler$1 implements Callback<ctm> {
    final /* synthetic */ RouteFootRidePresenter a;
    final /* synthetic */ b b;

    public void error(Throwable th, boolean z) {
    }

    RouteFootRidePresenter$InnerHandler$1(b bVar, RouteFootRidePresenter routeFootRidePresenter) {
        this.b = bVar;
        this.a = routeFootRidePresenter;
    }

    public void callback(ctm ctm) {
        if (ctm != null && this.a.mPage != null && ((RouteFootRideMapPage) this.a.mPage).isStarted()) {
            this.a.E = false;
            this.a.D.a((AbstractBasePage) this.a.mPage, "9", ctm.c);
        }
    }
}
