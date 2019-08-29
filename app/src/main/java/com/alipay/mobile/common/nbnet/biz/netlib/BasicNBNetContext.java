package com.alipay.mobile.common.nbnet.biz.netlib;

import android.content.Context;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.NBNetInterceptor;
import com.alipay.mobile.common.nbnet.biz.NBNetInterceptorManager;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BasicNBNetContext implements NBNetContext {
    private final NBNetContext a;
    private Map b;

    public BasicNBNetContext() {
        this(0);
    }

    private BasicNBNetContext(byte b2) {
        this.b = null;
        this.a = null;
    }

    public Context getApplicationContext() {
        return NBNetEnvUtils.a();
    }

    public void addNBNetInterceptor(NBNetInterceptor interceptor) {
        NBNetInterceptorManager.a().a(interceptor);
    }

    public Object getAttribute(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        Object obj = null;
        if (this.b != null) {
            obj = this.b.get(id);
        }
        if (obj != null || this.a == null) {
            return obj;
        }
        return this.a.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        a();
        synchronized (this) {
            this.b.put(id, obj);
        }
    }

    public Object removeAttribute(String id) {
        Object obj;
        if (id == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        synchronized (this) {
            if (this.b != null) {
                obj = this.b.remove(id);
            } else {
                obj = null;
            }
        }
        return obj;
    }

    public Set<String> keySet() {
        if (this.b == null) {
            return Collections.emptySet();
        }
        return this.b.keySet();
    }

    public Set<Entry<String, Object>> entrySet() {
        if (this.b == null) {
            return Collections.emptySet();
        }
        return this.b.entrySet();
    }

    public int size() {
        if (this.b == null) {
            return 0;
        }
        return this.b.size();
    }

    public void copyOverTo(NBNetContext nbNetContext) {
        if (nbNetContext != null && this != nbNetContext && size() > 0) {
            synchronized (this) {
                for (Entry entry : entrySet()) {
                    nbNetContext.setAttribute((String) entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void clear() {
        if (this.b != null && !this.b.isEmpty()) {
            synchronized (this) {
                this.b.clear();
            }
        }
    }

    private void a() {
        if (this.b == null) {
            synchronized (this) {
                if (this.b == null) {
                    this.b = new HashMap();
                }
            }
        }
    }
}
