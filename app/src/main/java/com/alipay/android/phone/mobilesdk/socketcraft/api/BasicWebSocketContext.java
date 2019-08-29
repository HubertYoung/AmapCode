package com.alipay.android.phone.mobilesdk.socketcraft.api;

import java.util.HashMap;
import java.util.Map;

public class BasicWebSocketContext implements WebSocketContext {
    private Map map;
    private final WebSocketContext parentContext;

    public BasicWebSocketContext() {
        this(null);
    }

    public BasicWebSocketContext(WebSocketContext parentContext2) {
        this.map = null;
        this.parentContext = parentContext2;
    }

    public Object getAttribute(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        Object obj = null;
        if (this.map != null) {
            obj = this.map.get(id);
        }
        if (obj != null || this.parentContext == null) {
            return obj;
        }
        return this.parentContext.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        if (this.map == null) {
            this.map = new HashMap();
        }
        this.map.put(id, obj);
    }

    public Object removeAttribute(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        } else if (this.map != null) {
            return this.map.remove(id);
        } else {
            return null;
        }
    }

    public void clear() {
        if (this.map != null) {
            this.map.clear();
        }
        if (this.parentContext != null) {
            this.parentContext.clear();
        }
    }
}
