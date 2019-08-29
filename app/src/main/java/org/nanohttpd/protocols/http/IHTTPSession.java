package org.nanohttpd.protocols.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.nanohttpd.protocols.http.content.CookieHandler;
import org.nanohttpd.protocols.http.request.Method;

public interface IHTTPSession {
    void execute();

    CookieHandler getCookies();

    Map<String, String> getHeaders();

    InputStream getInputStream();

    Method getMethod();

    Map<String, List<String>> getParameters();

    @Deprecated
    Map<String, String> getParms();

    String getQueryParameterString();

    String getRemoteHostName();

    String getRemoteIpAddress();

    String getUri();

    void parseBody(Map<String, String> map);
}
