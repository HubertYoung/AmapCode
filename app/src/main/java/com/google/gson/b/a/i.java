package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.b.c;
import com.google.gson.b.k;
import com.google.gson.d;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: ReflectiveTypeAdapterFactory */
public final class i implements u {
    private final c a;
    private final d b;
    private final com.google.gson.b.d c;
    private final d d;
    private final com.google.gson.b.b.b e = com.google.gson.b.b.b.a();

    /* compiled from: ReflectiveTypeAdapterFactory */
    public static final class a<T> extends t<T> {
        private final com.google.gson.b.i<T> a;
        private final Map<String, b> b;

        a(com.google.gson.b.i<T> iVar, Map<String, b> map) {
            this.a = iVar;
            this.b = map;
        }

        public final T b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == com.google.gson.d.b.NULL) {
                aVar.j();
                return null;
            }
            T a2 = this.a.a();
            try {
                aVar.c();
                while (aVar.e()) {
                    b bVar = this.b.get(aVar.g());
                    if (bVar != null) {
                        if (bVar.j) {
                            bVar.a(aVar, (Object) a2);
                        }
                    }
                    aVar.n();
                }
                aVar.d();
                return a2;
            } catch (IllegalStateException e) {
                throw new r((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public final void a(com.google.gson.d.c cVar, T t) throws IOException {
            if (t == null) {
                cVar.f();
                return;
            }
            cVar.d();
            try {
                for (b next : this.b.values()) {
                    if (next.a(t)) {
                        cVar.a(next.h);
                        next.a(cVar, (Object) t);
                    }
                }
                cVar.e();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    /* compiled from: ReflectiveTypeAdapterFactory */
    static abstract class b {
        final String h;
        final boolean i;
        final boolean j;

        /* access modifiers changed from: 0000 */
        public abstract void a(com.google.gson.d.a aVar, Object obj) throws IOException, IllegalAccessException;

        /* access modifiers changed from: 0000 */
        public abstract void a(com.google.gson.d.c cVar, Object obj) throws IOException, IllegalAccessException;

        /* access modifiers changed from: 0000 */
        public abstract boolean a(Object obj) throws IOException, IllegalAccessException;

        protected b(String str, boolean z, boolean z2) {
            this.h = str;
            this.i = z;
            this.j = z2;
        }
    }

    public i(c cVar, d dVar, com.google.gson.b.d dVar2, d dVar3) {
        this.a = cVar;
        this.b = dVar;
        this.c = dVar2;
        this.d = dVar3;
    }

    public final boolean a(Field field, boolean z) {
        return a(field, z, this.c);
    }

    static boolean a(Field field, boolean z, com.google.gson.b.d dVar) {
        return !dVar.a(field.getType(), z) && !dVar.a(field, z);
    }

    private List<String> a(Field field) {
        com.google.gson.a.c cVar = (com.google.gson.a.c) field.getAnnotation(com.google.gson.a.c.class);
        if (cVar == null) {
            return Collections.singletonList(this.b.a(field));
        }
        String a2 = cVar.a();
        String[] b2 = cVar.b();
        if (b2.length == 0) {
            return Collections.singletonList(a2);
        }
        ArrayList arrayList = new ArrayList(b2.length + 1);
        arrayList.add(a2);
        for (String add : b2) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        if (!Object.class.isAssignableFrom(a2)) {
            return null;
        }
        return new a(this.a.a(aVar), a(gson, aVar, a2));
    }

    private b a(Gson gson, Field field, String str, com.google.gson.c.a<?> aVar, boolean z, boolean z2) {
        final Gson gson2 = gson;
        final com.google.gson.c.a<?> aVar2 = aVar;
        final boolean a2 = k.a((Type) aVar.a());
        final Field field2 = field;
        com.google.gson.a.b bVar = (com.google.gson.a.b) field2.getAnnotation(com.google.gson.a.b.class);
        t<?> a3 = bVar != null ? this.d.a(this.a, gson2, aVar2, bVar) : null;
        final boolean z3 = a3 != null;
        if (a3 == null) {
            a3 = gson2.getAdapter(aVar2);
        }
        final t tVar = a3;
        AnonymousClass1 r0 = new b(str, z, z2) {
            /* access modifiers changed from: 0000 */
            public void a(com.google.gson.d.c cVar, Object obj) throws IOException, IllegalAccessException {
                t tVar;
                Object obj2 = field2.get(obj);
                if (z3) {
                    tVar = tVar;
                } else {
                    tVar = new m(gson2, tVar, aVar2.b());
                }
                tVar.a(cVar, obj2);
            }

            /* access modifiers changed from: 0000 */
            public void a(com.google.gson.d.a aVar, Object obj) throws IOException, IllegalAccessException {
                Object b2 = tVar.b(aVar);
                if (b2 != null || !a2) {
                    field2.set(obj, b2);
                }
            }

            public boolean a(Object obj) throws IOException, IllegalAccessException {
                if (this.i && field2.get(obj) != obj) {
                    return true;
                }
                return false;
            }
        };
        return r0;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r26v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<java.lang.String, com.google.gson.b.a.i.b> a(com.google.gson.Gson r24, com.google.gson.c.a<?> r25, java.lang.Class r26) {
        /*
            r23 = this;
            r7 = r23
            java.util.LinkedHashMap r8 = new java.util.LinkedHashMap
            r8.<init>()
            boolean r0 = r26.isInterface()
            if (r0 == 0) goto L_0x000e
            return r8
        L_0x000e:
            java.lang.reflect.Type r9 = r25.b()
            r11 = r25
            r10 = r26
        L_0x0016:
            java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
            if (r10 == r0) goto L_0x00d5
            java.lang.reflect.Field[] r12 = r10.getDeclaredFields()
            int r13 = r12.length
            r14 = 0
            r15 = 0
        L_0x0021:
            if (r15 >= r13) goto L_0x00bd
            r6 = r12[r15]
            r0 = 1
            boolean r0 = r7.a(r6, r0)
            boolean r16 = r7.a(r6, r14)
            if (r0 != 0) goto L_0x0032
            if (r16 == 0) goto L_0x00b6
        L_0x0032:
            com.google.gson.b.b.b r1 = r7.e
            r1.a(r6)
            java.lang.reflect.Type r1 = r11.b()
            java.lang.reflect.Type r2 = r6.getGenericType()
            java.lang.reflect.Type r5 = com.google.gson.b.b.a(r1, r10, r2)
            java.util.List r4 = r7.a(r6)
            r1 = 0
            int r3 = r4.size()
            r2 = 0
        L_0x004d:
            if (r2 >= r3) goto L_0x0097
            java.lang.Object r17 = r4.get(r2)
            r14 = r17
            java.lang.String r14 = (java.lang.String) r14
            if (r2 == 0) goto L_0x005c
            r17 = 0
            goto L_0x005e
        L_0x005c:
            r17 = r0
        L_0x005e:
            com.google.gson.c.a r18 = com.google.gson.c.a.a(r5)
            r0 = r7
            r7 = r1
            r1 = r24
            r19 = r2
            r2 = r6
            r20 = r3
            r3 = r14
            r21 = r4
            r4 = r18
            r18 = r5
            r5 = r17
            r22 = r6
            r6 = r16
            com.google.gson.b.a.i$b r0 = r0.a(r1, r2, r3, r4, r5, r6)
            java.lang.Object r0 = r8.put(r14, r0)
            com.google.gson.b.a.i$b r0 = (com.google.gson.b.a.i.b) r0
            if (r7 != 0) goto L_0x0086
            r1 = r0
            goto L_0x0087
        L_0x0086:
            r1 = r7
        L_0x0087:
            int r2 = r19 + 1
            r0 = r17
            r5 = r18
            r3 = r20
            r4 = r21
            r6 = r22
            r7 = r23
            r14 = 0
            goto L_0x004d
        L_0x0097:
            r7 = r1
            if (r7 == 0) goto L_0x00b6
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r9)
            java.lang.String r2 = " declares multiple JSON fields named "
            r1.append(r2)
            java.lang.String r2 = r7.h
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00b6:
            int r15 = r15 + 1
            r7 = r23
            r14 = 0
            goto L_0x0021
        L_0x00bd:
            java.lang.reflect.Type r0 = r11.b()
            java.lang.reflect.Type r1 = r10.getGenericSuperclass()
            java.lang.reflect.Type r0 = com.google.gson.b.b.a(r0, r10, r1)
            com.google.gson.c.a r11 = com.google.gson.c.a.a(r0)
            java.lang.Class r10 = r11.a()
            r7 = r23
            goto L_0x0016
        L_0x00d5:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.a.i.a(com.google.gson.Gson, com.google.gson.c.a, java.lang.Class):java.util.Map");
    }
}
