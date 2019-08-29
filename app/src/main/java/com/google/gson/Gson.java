package com.google.gson;

import com.google.gson.b.a.b;
import com.google.gson.b.a.e;
import com.google.gson.b.a.f;
import com.google.gson.b.a.g;
import com.google.gson.b.a.h;
import com.google.gson.b.a.i;
import com.google.gson.b.a.j;
import com.google.gson.b.a.k;
import com.google.gson.b.a.n;
import com.google.gson.b.c;
import com.google.gson.b.d;
import com.google.gson.b.l;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private static final com.google.gson.c.a<?> NULL_KEY_SURROGATE = com.google.gson.c.a.b(Object.class);
    final List<u> builderFactories;
    final List<u> builderHierarchyFactories;
    private final ThreadLocal<Map<com.google.gson.c.a<?>, a<?>>> calls;
    final boolean complexMapKeySerialization;
    private final c constructorConstructor;
    final String datePattern;
    final int dateStyle;
    final d excluder;
    final List<u> factories;
    final d fieldNamingStrategy;
    final boolean generateNonExecutableJson;
    final boolean htmlSafe;
    final Map<Type, f<?>> instanceCreators;
    private final com.google.gson.b.a.d jsonAdapterFactory;
    final boolean lenient;
    final s longSerializationPolicy;
    final boolean prettyPrinting;
    final boolean serializeNulls;
    final boolean serializeSpecialFloatingPointValues;
    final int timeStyle;
    private final Map<com.google.gson.c.a<?>, t<?>> typeTokenCache;

    static class a<T> extends t<T> {
        private t<T> a;

        a() {
        }

        public void a(t<T> tVar) {
            if (this.a != null) {
                throw new AssertionError();
            }
            this.a = tVar;
        }

        public T b(com.google.gson.d.a aVar) throws IOException {
            if (this.a != null) {
                return this.a.b(aVar);
            }
            throw new IllegalStateException();
        }

        public void a(com.google.gson.d.c cVar, T t) throws IOException {
            if (this.a == null) {
                throw new IllegalStateException();
            }
            this.a.a(cVar, t);
        }
    }

    public Gson() {
        this(d.a, c.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, s.DEFAULT, null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    Gson(d dVar, d dVar2, Map<Type, f<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, s sVar, String str, int i, int i2, List<u> list, List<u> list2, List<u> list3) {
        d dVar3 = dVar;
        d dVar4 = dVar2;
        Map<Type, f<?>> map2 = map;
        boolean z8 = z2;
        boolean z9 = z7;
        this.calls = new ThreadLocal<>();
        this.typeTokenCache = new ConcurrentHashMap();
        this.excluder = dVar3;
        this.fieldNamingStrategy = dVar4;
        this.instanceCreators = map2;
        this.constructorConstructor = new c(map2);
        this.serializeNulls = z;
        this.complexMapKeySerialization = z8;
        this.generateNonExecutableJson = z3;
        this.htmlSafe = z4;
        this.prettyPrinting = z5;
        this.lenient = z6;
        this.serializeSpecialFloatingPointValues = z9;
        this.longSerializationPolicy = sVar;
        this.datePattern = str;
        this.dateStyle = i;
        this.timeStyle = i2;
        this.builderFactories = list;
        this.builderHierarchyFactories = list2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(n.Y);
        arrayList.add(h.a);
        arrayList.add(dVar3);
        arrayList.addAll(list3);
        arrayList.add(n.D);
        arrayList.add(n.m);
        arrayList.add(n.g);
        arrayList.add(n.i);
        arrayList.add(n.k);
        t<Number> longAdapter = longAdapter(sVar);
        arrayList.add(n.a(Long.TYPE, Long.class, longAdapter));
        arrayList.add(n.a(Double.TYPE, Double.class, doubleAdapter(z9)));
        arrayList.add(n.a(Float.TYPE, Float.class, floatAdapter(z9)));
        arrayList.add(n.x);
        arrayList.add(n.o);
        arrayList.add(n.q);
        arrayList.add(n.a(AtomicLong.class, atomicLongAdapter(longAdapter)));
        arrayList.add(n.a(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
        arrayList.add(n.s);
        arrayList.add(n.z);
        arrayList.add(n.F);
        arrayList.add(n.H);
        arrayList.add(n.a(BigDecimal.class, n.B));
        arrayList.add(n.a(BigInteger.class, n.C));
        arrayList.add(n.J);
        arrayList.add(n.L);
        arrayList.add(n.P);
        arrayList.add(n.R);
        arrayList.add(n.W);
        arrayList.add(n.N);
        arrayList.add(n.d);
        arrayList.add(com.google.gson.b.a.c.a);
        arrayList.add(n.U);
        arrayList.add(k.a);
        arrayList.add(j.a);
        arrayList.add(n.S);
        arrayList.add(com.google.gson.b.a.a.a);
        arrayList.add(n.b);
        arrayList.add(new b(this.constructorConstructor));
        arrayList.add(new g(this.constructorConstructor, z8));
        this.jsonAdapterFactory = new com.google.gson.b.a.d(this.constructorConstructor);
        arrayList.add(this.jsonAdapterFactory);
        arrayList.add(n.Z);
        arrayList.add(new i(this.constructorConstructor, dVar4, dVar3, this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList(arrayList);
    }

    public final e newBuilder() {
        return new e(this);
    }

    public final d excluder() {
        return this.excluder;
    }

    public final d fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public final boolean serializeNulls() {
        return this.serializeNulls;
    }

    public final boolean htmlSafe() {
        return this.htmlSafe;
    }

    private t<Number> doubleAdapter(boolean z) {
        if (z) {
            return n.v;
        }
        return new t<Number>() {
            /* renamed from: a */
            public Double b(com.google.gson.d.a aVar) throws IOException {
                if (aVar.f() != com.google.gson.d.b.NULL) {
                    return Double.valueOf(aVar.k());
                }
                aVar.j();
                return null;
            }

            public void a(com.google.gson.d.c cVar, Number number) throws IOException {
                if (number == null) {
                    cVar.f();
                    return;
                }
                Gson.checkValidFloatingPoint(number.doubleValue());
                cVar.a(number);
            }
        };
    }

    private t<Number> floatAdapter(boolean z) {
        if (z) {
            return n.u;
        }
        return new t<Number>() {
            /* renamed from: a */
            public Float b(com.google.gson.d.a aVar) throws IOException {
                if (aVar.f() != com.google.gson.d.b.NULL) {
                    return Float.valueOf((float) aVar.k());
                }
                aVar.j();
                return null;
            }

            public void a(com.google.gson.d.c cVar, Number number) throws IOException {
                if (number == null) {
                    cVar.f();
                    return;
                }
                Gson.checkValidFloatingPoint((double) number.floatValue());
                cVar.a(number);
            }
        };
    }

    static void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            StringBuilder sb = new StringBuilder();
            sb.append(d);
            sb.append(" is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static t<Number> longAdapter(s sVar) {
        if (sVar == s.DEFAULT) {
            return n.t;
        }
        return new t<Number>() {
            /* renamed from: a */
            public final Number b(com.google.gson.d.a aVar) throws IOException {
                if (aVar.f() != com.google.gson.d.b.NULL) {
                    return Long.valueOf(aVar.l());
                }
                aVar.j();
                return null;
            }

            public final void a(com.google.gson.d.c cVar, Number number) throws IOException {
                if (number == null) {
                    cVar.f();
                } else {
                    cVar.b(number.toString());
                }
            }
        };
    }

    private static t<AtomicLong> atomicLongAdapter(final t<Number> tVar) {
        return new t<AtomicLong>() {
            public final void a(com.google.gson.d.c cVar, AtomicLong atomicLong) throws IOException {
                tVar.a(cVar, Long.valueOf(atomicLong.get()));
            }

            /* renamed from: a */
            public final AtomicLong b(com.google.gson.d.a aVar) throws IOException {
                return new AtomicLong(((Number) tVar.b(aVar)).longValue());
            }
        }.a();
    }

    private static t<AtomicLongArray> atomicLongArrayAdapter(final t<Number> tVar) {
        return new t<AtomicLongArray>() {
            public final void a(com.google.gson.d.c cVar, AtomicLongArray atomicLongArray) throws IOException {
                cVar.b();
                int length = atomicLongArray.length();
                for (int i = 0; i < length; i++) {
                    tVar.a(cVar, Long.valueOf(atomicLongArray.get(i)));
                }
                cVar.c();
            }

            /* renamed from: a */
            public final AtomicLongArray b(com.google.gson.d.a aVar) throws IOException {
                ArrayList arrayList = new ArrayList();
                aVar.a();
                while (aVar.e()) {
                    arrayList.add(Long.valueOf(((Number) tVar.b(aVar)).longValue()));
                }
                aVar.b();
                int size = arrayList.size();
                AtomicLongArray atomicLongArray = new AtomicLongArray(size);
                for (int i = 0; i < size; i++) {
                    atomicLongArray.set(i, ((Long) arrayList.get(i)).longValue());
                }
                return atomicLongArray;
            }
        }.a();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.gson.c.a<T>, code=com.google.gson.c.a, for r6v0, types: [com.google.gson.c.a, com.google.gson.c.a<T>, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> com.google.gson.t<T> getAdapter(com.google.gson.c.a r6) {
        /*
            r5 = this;
            java.util.Map<com.google.gson.c.a<?>, com.google.gson.t<?>> r0 = r5.typeTokenCache
            if (r6 != 0) goto L_0x0007
            com.google.gson.c.a<?> r1 = NULL_KEY_SURROGATE
            goto L_0x0008
        L_0x0007:
            r1 = r6
        L_0x0008:
            java.lang.Object r0 = r0.get(r1)
            com.google.gson.t r0 = (com.google.gson.t) r0
            if (r0 == 0) goto L_0x0011
            return r0
        L_0x0011:
            java.lang.ThreadLocal<java.util.Map<com.google.gson.c.a<?>, com.google.gson.Gson$a<?>>> r0 = r5.calls
            java.lang.Object r0 = r0.get()
            java.util.Map r0 = (java.util.Map) r0
            r1 = 0
            if (r0 != 0) goto L_0x0027
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.ThreadLocal<java.util.Map<com.google.gson.c.a<?>, com.google.gson.Gson$a<?>>> r1 = r5.calls
            r1.set(r0)
            r1 = 1
        L_0x0027:
            java.lang.Object r2 = r0.get(r6)
            com.google.gson.Gson$a r2 = (com.google.gson.Gson.a) r2
            if (r2 == 0) goto L_0x0030
            return r2
        L_0x0030:
            com.google.gson.Gson$a r2 = new com.google.gson.Gson$a     // Catch:{ all -> 0x0073 }
            r2.<init>()     // Catch:{ all -> 0x0073 }
            r0.put(r6, r2)     // Catch:{ all -> 0x0073 }
            java.util.List<com.google.gson.u> r3 = r5.factories     // Catch:{ all -> 0x0073 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0073 }
        L_0x003e:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x0063
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0073 }
            com.google.gson.u r4 = (com.google.gson.u) r4     // Catch:{ all -> 0x0073 }
            com.google.gson.t r4 = r4.a(r5, r6)     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x003e
            r2.a(r4)     // Catch:{ all -> 0x0073 }
            java.util.Map<com.google.gson.c.a<?>, com.google.gson.t<?>> r2 = r5.typeTokenCache     // Catch:{ all -> 0x0073 }
            r2.put(r6, r4)     // Catch:{ all -> 0x0073 }
            r0.remove(r6)
            if (r1 == 0) goto L_0x0062
            java.lang.ThreadLocal<java.util.Map<com.google.gson.c.a<?>, com.google.gson.Gson$a<?>>> r6 = r5.calls
            r6.remove()
        L_0x0062:
            return r4
        L_0x0063:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = "GSON (2.8.5) cannot handle "
            java.lang.String r4 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x0073 }
            r2.<init>(r3)     // Catch:{ all -> 0x0073 }
            throw r2     // Catch:{ all -> 0x0073 }
        L_0x0073:
            r2 = move-exception
            r0.remove(r6)
            if (r1 == 0) goto L_0x007e
            java.lang.ThreadLocal<java.util.Map<com.google.gson.c.a<?>, com.google.gson.Gson$a<?>>> r6 = r5.calls
            r6.remove()
        L_0x007e:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.Gson.getAdapter(com.google.gson.c.a):com.google.gson.t");
    }

    public final <T> t<T> getDelegateAdapter(u uVar, com.google.gson.c.a<T> aVar) {
        if (!this.factories.contains(uVar)) {
            uVar = this.jsonAdapterFactory;
        }
        boolean z = false;
        for (u next : this.factories) {
            if (z) {
                t<T> a2 = next.a(this, aVar);
                if (a2 != null) {
                    return a2;
                }
            } else if (next == uVar) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize ".concat(String.valueOf(aVar)));
    }

    public final <T> t<T> getAdapter(Class<T> cls) {
        return getAdapter(com.google.gson.c.a.b(cls));
    }

    public final j toJsonTree(Object obj) {
        if (obj == null) {
            return l.a;
        }
        return toJsonTree(obj, obj.getClass());
    }

    public final j toJsonTree(Object obj, Type type) {
        f fVar = new f();
        toJson(obj, type, (com.google.gson.d.c) fVar);
        return fVar.a();
    }

    public final String toJson(Object obj) {
        if (obj == null) {
            return toJson((j) l.a);
        }
        return toJson(obj, (Type) obj.getClass());
    }

    public final String toJson(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        toJson(obj, type, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public final void toJson(Object obj, Appendable appendable) throws k {
        if (obj != null) {
            toJson(obj, (Type) obj.getClass(), appendable);
        } else {
            toJson((j) l.a, appendable);
        }
    }

    public final void toJson(Object obj, Type type, Appendable appendable) throws k {
        try {
            toJson(obj, type, newJsonWriter(l.a(appendable)));
        } catch (IOException e) {
            throw new k((Throwable) e);
        }
    }

    public final void toJson(Object obj, Type type, com.google.gson.d.c cVar) throws k {
        t adapter = getAdapter(com.google.gson.c.a.a(type));
        boolean g = cVar.g();
        cVar.b(true);
        boolean h = cVar.h();
        cVar.c(this.htmlSafe);
        boolean i = cVar.i();
        cVar.d(this.serializeNulls);
        try {
            adapter.a(cVar, obj);
            cVar.b(g);
            cVar.c(h);
            cVar.d(i);
        } catch (IOException e) {
            throw new k((Throwable) e);
        } catch (AssertionError e2) {
            StringBuilder sb = new StringBuilder("AssertionError (GSON 2.8.5): ");
            sb.append(e2.getMessage());
            throw new AssertionError(sb.toString(), e2);
        } catch (Throwable th) {
            cVar.b(g);
            cVar.c(h);
            cVar.d(i);
            throw th;
        }
    }

    public final String toJson(j jVar) {
        StringWriter stringWriter = new StringWriter();
        toJson(jVar, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public final void toJson(j jVar, Appendable appendable) throws k {
        try {
            toJson(jVar, newJsonWriter(l.a(appendable)));
        } catch (IOException e) {
            throw new k((Throwable) e);
        }
    }

    public final com.google.gson.d.c newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(JSON_NON_EXECUTABLE_PREFIX);
        }
        com.google.gson.d.c cVar = new com.google.gson.d.c(writer);
        if (this.prettyPrinting) {
            cVar.c((String) "  ");
        }
        cVar.d(this.serializeNulls);
        return cVar;
    }

    public final com.google.gson.d.a newJsonReader(Reader reader) {
        com.google.gson.d.a aVar = new com.google.gson.d.a(reader);
        aVar.a(this.lenient);
        return aVar;
    }

    public final void toJson(j jVar, com.google.gson.d.c cVar) throws k {
        boolean g = cVar.g();
        cVar.b(true);
        boolean h = cVar.h();
        cVar.c(this.htmlSafe);
        boolean i = cVar.i();
        cVar.d(this.serializeNulls);
        try {
            l.a(jVar, cVar);
            cVar.b(g);
            cVar.c(h);
            cVar.d(i);
        } catch (IOException e) {
            throw new k((Throwable) e);
        } catch (AssertionError e2) {
            StringBuilder sb = new StringBuilder("AssertionError (GSON 2.8.5): ");
            sb.append(e2.getMessage());
            throw new AssertionError(sb.toString(), e2);
        } catch (Throwable th) {
            cVar.b(g);
            cVar.c(h);
            cVar.d(i);
            throw th;
        }
    }

    public final <T> T fromJson(String str, Class<T> cls) throws r {
        return com.google.gson.b.k.a(cls).cast(fromJson(str, (Type) cls));
    }

    public final <T> T fromJson(String str, Type type) throws r {
        if (str == null) {
            return null;
        }
        return fromJson((Reader) new StringReader(str), type);
    }

    public final <T> T fromJson(Reader reader, Class<T> cls) throws r, k {
        com.google.gson.d.a newJsonReader = newJsonReader(reader);
        T fromJson = fromJson(newJsonReader, (Type) cls);
        assertFullConsumption(fromJson, newJsonReader);
        return com.google.gson.b.k.a(cls).cast(fromJson);
    }

    public final <T> T fromJson(Reader reader, Type type) throws k, r {
        com.google.gson.d.a newJsonReader = newJsonReader(reader);
        T fromJson = fromJson(newJsonReader, type);
        assertFullConsumption(fromJson, newJsonReader);
        return fromJson;
    }

    private static void assertFullConsumption(Object obj, com.google.gson.d.a aVar) {
        if (obj != null) {
            try {
                if (aVar.f() != com.google.gson.d.b.END_DOCUMENT) {
                    throw new k((String) "JSON document was not fully consumed.");
                }
            } catch (com.google.gson.d.d e) {
                throw new r((Throwable) e);
            } catch (IOException e2) {
                throw new k((Throwable) e2);
            }
        }
    }

    public final <T> T fromJson(com.google.gson.d.a aVar, Type type) throws k, r {
        boolean q = aVar.q();
        aVar.a(true);
        try {
            aVar.f();
            T b = getAdapter(com.google.gson.c.a.a(type)).b(aVar);
            aVar.a(q);
            return b;
        } catch (EOFException e) {
            if (1 != 0) {
                aVar.a(q);
                return null;
            }
            throw new r((Throwable) e);
        } catch (IllegalStateException e2) {
            throw new r((Throwable) e2);
        } catch (IOException e3) {
            throw new r((Throwable) e3);
        } catch (AssertionError e4) {
            StringBuilder sb = new StringBuilder("AssertionError (GSON 2.8.5): ");
            sb.append(e4.getMessage());
            throw new AssertionError(sb.toString(), e4);
        } catch (Throwable th) {
            aVar.a(q);
            throw th;
        }
    }

    public final <T> T fromJson(j jVar, Class<T> cls) throws r {
        return com.google.gson.b.k.a(cls).cast(fromJson(jVar, (Type) cls));
    }

    public final <T> T fromJson(j jVar, Type type) throws r {
        if (jVar == null) {
            return null;
        }
        return fromJson((com.google.gson.d.a) new e(jVar), type);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{serializeNulls:");
        sb.append(this.serializeNulls);
        sb.append(",factories:");
        sb.append(this.factories);
        sb.append(",instanceCreators:");
        sb.append(this.constructorConstructor);
        sb.append(com.alipay.sdk.util.h.d);
        return sb.toString();
    }
}
