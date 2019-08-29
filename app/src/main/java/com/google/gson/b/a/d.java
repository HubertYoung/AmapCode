package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.a.b;
import com.google.gson.b.c;
import com.google.gson.c.a;
import com.google.gson.t;
import com.google.gson.u;

/* compiled from: JsonAdapterAnnotationTypeAdapterFactory */
public final class d implements u {
    private final c a;

    public d(c cVar) {
        this.a = cVar;
    }

    public final <T> t<T> a(Gson gson, a<T> aVar) {
        b bVar = (b) aVar.a().getAnnotation(b.class);
        if (bVar == null) {
            return null;
        }
        return a(this.a, gson, aVar, bVar);
    }

    /* JADX WARNING: type inference failed for: r9v3, types: [com.google.gson.t, com.google.gson.t<?>] */
    /* JADX WARNING: type inference failed for: r9v13, types: [com.google.gson.t] */
    /* JADX WARNING: type inference failed for: r9v14, types: [com.google.gson.t] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.gson.t<?> a(com.google.gson.b.c r9, com.google.gson.Gson r10, com.google.gson.c.a<?> r11, com.google.gson.a.b r12) {
        /*
            r8 = this;
            java.lang.Class r0 = r12.a()
            com.google.gson.c.a r0 = com.google.gson.c.a.b(r0)
            com.google.gson.b.i r9 = r9.a(r0)
            java.lang.Object r9 = r9.a()
            boolean r0 = r9 instanceof com.google.gson.t
            if (r0 == 0) goto L_0x0017
            com.google.gson.t r9 = (com.google.gson.t) r9
            goto L_0x0072
        L_0x0017:
            boolean r0 = r9 instanceof com.google.gson.u
            if (r0 == 0) goto L_0x0022
            com.google.gson.u r9 = (com.google.gson.u) r9
            com.google.gson.t r9 = r9.a(r10, r11)
            goto L_0x0072
        L_0x0022:
            boolean r0 = r9 instanceof com.google.gson.q
            if (r0 != 0) goto L_0x0058
            boolean r1 = r9 instanceof com.google.gson.i
            if (r1 == 0) goto L_0x002b
            goto L_0x0058
        L_0x002b:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r0 = "Invalid attempt to bind an instance of "
            r12.<init>(r0)
            java.lang.Class r9 = r9.getClass()
            java.lang.String r9 = r9.getName()
            r12.append(r9)
            java.lang.String r9 = " as a @JsonAdapter for "
            r12.append(r9)
            java.lang.String r9 = r11.toString()
            r12.append(r9)
            java.lang.String r9 = ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer."
            r12.append(r9)
            java.lang.String r9 = r12.toString()
            r10.<init>(r9)
            throw r10
        L_0x0058:
            r1 = 0
            if (r0 == 0) goto L_0x0060
            r0 = r9
            com.google.gson.q r0 = (com.google.gson.q) r0
            r3 = r0
            goto L_0x0061
        L_0x0060:
            r3 = r1
        L_0x0061:
            boolean r0 = r9 instanceof com.google.gson.i
            if (r0 == 0) goto L_0x0068
            r1 = r9
            com.google.gson.i r1 = (com.google.gson.i) r1
        L_0x0068:
            r4 = r1
            com.google.gson.b.a.l r9 = new com.google.gson.b.a.l
            r7 = 0
            r2 = r9
            r5 = r10
            r6 = r11
            r2.<init>(r3, r4, r5, r6, r7)
        L_0x0072:
            if (r9 == 0) goto L_0x007e
            boolean r10 = r12.b()
            if (r10 == 0) goto L_0x007e
            com.google.gson.t r9 = r9.a()
        L_0x007e:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.a.d.a(com.google.gson.b.c, com.google.gson.Gson, com.google.gson.c.a, com.google.gson.a.b):com.google.gson.t");
    }
}
