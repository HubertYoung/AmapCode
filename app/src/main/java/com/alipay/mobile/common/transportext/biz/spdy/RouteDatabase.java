package com.alipay.mobile.common.transportext.biz.spdy;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.SSLHandshakeException;

public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet();

    public final synchronized void failed(Route failedRoute, IOException failure) {
        this.failedRoutes.add(failedRoute);
        if (!(failure instanceof SSLHandshakeException)) {
            this.failedRoutes.add(failedRoute.flipTlsMode());
        }
    }

    public final synchronized void connected(Route route) {
        this.failedRoutes.remove(route);
    }

    public final synchronized boolean shouldPostpone(Route route) {
        return this.failedRoutes.contains(route);
    }

    public final synchronized int failedRoutesCount() {
        return this.failedRoutes.size();
    }
}
