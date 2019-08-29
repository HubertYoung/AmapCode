package com.autonavi.link.protocol.http;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.link.protocol.http.NanoHTTPD.IHTTPSession;
import com.autonavi.link.protocol.http.NanoHTTPD.Response;
import com.autonavi.link.protocol.http.NanoHTTPD.Response.Status;
import java.util.HashMap;
import java.util.Map;

public class DefaultHttpServer extends NanoHTTPD {
    private Map<String, HttpServe> a = new HashMap();

    public DefaultHttpServer(int i) {
        super(i);
    }

    public DefaultHttpServer(String str, int i) {
        super(str, i);
    }

    public void registerServe(String str, HttpServe httpServe) {
        if (!TextUtils.isEmpty(str) && httpServe != null) {
            synchronized (this.a) {
                this.a.put(str, httpServe);
            }
        }
    }

    public void unregisterServe(String str) {
        synchronized (this.a) {
            this.a.remove(str);
        }
    }

    public Response serve(IHTTPSession iHTTPSession) {
        HttpServe httpServe;
        Uri parse = Uri.parse(iHTTPSession.getUri());
        if (parse == null) {
            return null;
        }
        String path = parse.getPath();
        int indexOf = path.indexOf("/");
        if (indexOf >= 0) {
            int i = indexOf + 1;
            int indexOf2 = path.substring(i).indexOf("/");
            if (indexOf2 > 0) {
                String substring = path.substring(i, indexOf2 + indexOf + 1);
                if (!TextUtils.isEmpty(substring)) {
                    synchronized (this.a) {
                        httpServe = this.a.get(substring);
                    }
                    if (httpServe != null) {
                        Response invoke = httpServe.invoke(iHTTPSession);
                        if (invoke == null) {
                            invoke = newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "SERVICE NOT FOUND");
                        }
                        return invoke;
                    }
                }
            }
        }
        return null;
    }
}
