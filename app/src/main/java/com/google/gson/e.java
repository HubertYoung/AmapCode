package com.google.gson;

import com.google.gson.b.d;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: GsonBuilder */
public final class e {
    private d a = d.a;
    private s b = s.DEFAULT;
    private d c = c.IDENTITY;
    private final Map<Type, f<?>> d = new HashMap();
    private final List<u> e = new ArrayList();
    private final List<u> f = new ArrayList();
    private boolean g = false;
    private String h;
    private int i = 2;
    private int j = 2;
    private boolean k = false;
    private boolean l = false;
    private boolean m = true;
    private boolean n = false;
    private boolean o = false;
    private boolean p = false;

    public e() {
    }

    e(Gson gson) {
        this.a = gson.excluder;
        this.c = gson.fieldNamingStrategy;
        this.d.putAll(gson.instanceCreators);
        this.g = gson.serializeNulls;
        this.k = gson.complexMapKeySerialization;
        this.o = gson.generateNonExecutableJson;
        this.m = gson.htmlSafe;
        this.n = gson.prettyPrinting;
        this.p = gson.lenient;
        this.l = gson.serializeSpecialFloatingPointValues;
        this.b = gson.longSerializationPolicy;
        this.h = gson.datePattern;
        this.i = gson.dateStyle;
        this.j = gson.timeStyle;
        this.e.addAll(gson.builderFactories);
        this.f.addAll(gson.builderHierarchyFactories);
    }
}
