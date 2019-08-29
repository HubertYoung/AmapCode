package com.alipay.mobile.common.transport.http.inner;

import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

public class HttpClientPlannerHelper {
    public static HttpRoute determineRoute(AndroidHttpClient androidHttpClient, HttpHost target, HttpRequest request, HttpContext context) {
        if (request == null) {
            throw new IllegalStateException("Request must not be null.");
        } else if (target == null) {
            throw new IllegalStateException("Target host must not be null.");
        } else {
            InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
            HttpHost proxy = ConnRouteParams.getDefaultProxy(request.getParams());
            boolean secure = androidHttpClient.getConnectionManager().getSchemeRegistry().getScheme(target.getSchemeName()).isLayered();
            if (proxy == null) {
                return new HttpRoute(target, local, secure);
            }
            return new HttpRoute(target, local, proxy, secure);
        }
    }
}
