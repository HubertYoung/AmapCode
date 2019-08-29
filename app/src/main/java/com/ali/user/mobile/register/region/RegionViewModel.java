package com.ali.user.mobile.register.region;

import java.util.List;
import java.util.Map;

public class RegionViewModel {
    private List<RegionInfo> a;
    private Map<String, Integer> b;
    private List<String> c;

    public final boolean a() {
        return this.a == null || this.b == null || this.c == null || this.c.isEmpty();
    }

    public final List<RegionInfo> b() {
        return this.a;
    }

    public final Map<String, Integer> c() {
        return this.b;
    }

    public final List<String> d() {
        return this.c;
    }

    public final void a(List<RegionInfo> list) {
        this.a = list;
    }

    public final void a(Map<String, Integer> map) {
        this.b = map;
    }

    public final void b(List<String> list) {
        this.c = list;
    }
}
