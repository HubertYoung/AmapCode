package com.alipay.mobile.tinyappcommon.ws;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: NewH5WSSessionManager */
public final class d {
    private static d a;
    private HashMap<String, Map<String, e>> b = new HashMap<>();

    public static final d a() {
        if (a != null) {
            return a;
        }
        synchronized (a.class) {
            try {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    public final synchronized void a(String appId, String socketTaskID, e webSocketSession) {
        Map sessions = this.b.get(appId);
        if (sessions == null) {
            sessions = new HashMap();
            this.b.put(appId, sessions);
        }
        sessions.put(socketTaskID, webSocketSession);
    }

    public final synchronized e a(String appId, String socketTaskID) {
        e eVar;
        try {
            Map sessions = this.b.get(appId);
            if (sessions != null) {
                eVar = (e) sessions.get(socketTaskID);
            } else {
                eVar = null;
            }
        }
        return eVar;
    }

    public final synchronized int a(String appId) {
        int i;
        Map sessions = this.b.get(appId);
        if (sessions != null) {
            i = sessions.size();
        } else {
            i = 0;
        }
        return i;
    }

    public final synchronized boolean a(String appId, String socketTaskID, boolean isCloseSocket) {
        boolean z;
        Map sessions = this.b.get(appId);
        if (sessions != null) {
            e defaultWebSocketSession = (e) sessions.remove(socketTaskID);
            if (defaultWebSocketSession == null) {
                z = false;
            } else {
                if (isCloseSocket) {
                    defaultWebSocketSession.a();
                }
                if (sessions.size() == 0) {
                    this.b.remove(appId);
                }
            }
        }
        z = true;
        return z;
    }

    public final synchronized boolean b(String appId) {
        boolean z;
        Map sessions = this.b.get(appId);
        if (sessions != null) {
            for (Entry value : sessions.entrySet()) {
                e webSocketSession = (e) value.getValue();
                if (webSocketSession != null) {
                    webSocketSession.a();
                }
            }
            sessions.clear();
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
