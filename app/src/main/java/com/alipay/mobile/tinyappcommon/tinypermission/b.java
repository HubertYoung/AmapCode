package com.alipay.mobile.tinyappcommon.tinypermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: H5ApiPermissionInfo */
public final class b {
    private boolean a = false;
    private Map<String, List<String>> b = new HashMap();
    private Map<String, String> c = new HashMap();

    public final boolean a() {
        return this.a;
    }

    public final void a(boolean hasPermissionFile) {
        this.a = hasPermissionFile;
    }

    public final List<String> a(String level) {
        return this.b.get(level);
    }

    public final void a(String level, List<String> list) {
        this.b.put(level, list);
    }

    public final String b(String level) {
        return this.c.get(level);
    }

    public final void a(String level, String value) {
        this.c.put(level, value);
    }

    public final void b() {
        a(false);
        this.b.clear();
        this.b = null;
        this.c.clear();
        this.c = null;
    }
}
