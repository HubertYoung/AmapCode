package org.nanohttpd.protocols.http.request;

public enum Method {
    GET,
    PUT,
    POST,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    CONNECT,
    PATCH,
    PROPFIND,
    PROPPATCH,
    MKCOL,
    MOVE,
    COPY,
    LOCK,
    UNLOCK;

    public static Method lookup(String method) {
        Method method2 = null;
        if (method == null) {
            return method2;
        }
        try {
            return valueOf(method);
        } catch (IllegalArgumentException e) {
            return method2;
        }
    }
}
