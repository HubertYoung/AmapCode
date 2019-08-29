package com.alipay.mobile.tinyappcommon.ws;

import java.util.HashMap;

/* compiled from: H5WSSessionManager */
public class a {
    private static a a;
    private HashMap<String, e> b = new HashMap<>();

    public static final a a() {
        if (a != null) {
            return a;
        }
        synchronized (a.class) {
            try {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public final synchronized void a(String appId, e webSocketSession) {
        this.b.put(appId, webSocketSession);
    }

    public final e a(String appId) {
        return this.b.get(appId);
    }

    public final synchronized boolean b(String appId) {
        boolean z;
        e defaultWebSocketSession = this.b.remove(appId);
        if (defaultWebSocketSession == null) {
            z = false;
        } else {
            defaultWebSocketSession.a();
            z = true;
        }
        return z;
    }
}
