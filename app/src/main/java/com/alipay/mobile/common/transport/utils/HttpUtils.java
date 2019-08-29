package com.alipay.mobile.common.transport.utils;

import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.protocol.HttpContext;

public class HttpUtils {
    public static final void extractCookiesFromResponse(HttpHost target, HttpRequest request, HttpResponse response, HttpContext context) {
        CookieStore cookieStore = (CookieStore) context.getAttribute("http.cookie-store");
        if (cookieStore != null) {
            CookieOrigin cookieOrigin = new CookieOrigin(target.getHostName(), MiscUtils.getEffectivePort(target.getSchemeName(), target.getPort()), getRequestURI(request).getPath(), true);
            HeaderIterator cookieIt = response.headerIterator("Set-Cookie");
            CookieSpec cookieSpec = new BestMatchSpec();
            add2CookieStore(cookieIt, cookieSpec, cookieOrigin, cookieStore);
            if (cookieSpec.getVersion() > 0) {
                add2CookieStore(response.headerIterator("Set-Cookie2"), cookieSpec, cookieOrigin, cookieStore);
            }
        }
    }

    public static final void add2CookieStore(HeaderIterator iterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore) {
        while (iterator.hasNext()) {
            try {
                for (Cookie cookie : cookieSpec.parse(iterator.nextHeader(), cookieOrigin)) {
                    try {
                        cookieSpec.validate(cookie, cookieOrigin);
                        cookieStore.addCookie(cookie);
                    } catch (MalformedCookieException ex) {
                        LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "add2CookieStore1 exception : " + ex.toString());
                    }
                }
            } catch (MalformedCookieException ex2) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "add2CookieStore2 exception : " + ex2.toString());
            }
        }
    }

    public static final URI getRequestURI(HttpRequest request) {
        if (request instanceof HttpUriRequest) {
            return ((HttpUriRequest) request).getURI();
        }
        try {
            return new URI(request.getRequestLine().getUri());
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid request URI: " + request.getRequestLine().getUri(), ex);
        }
    }
}
