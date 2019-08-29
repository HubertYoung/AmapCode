package com.amap.location.protocol;

import android.content.Context;
import com.amap.location.protocol.b.b;
import com.amap.location.protocol.e.c;

/* compiled from: AosLocator */
public class a extends c {
    private boolean c;

    private static String a(String str) {
        return str == null ? "" : str;
    }

    public a(Context context, boy boy) {
        super(context, boy);
    }

    /* access modifiers changed from: protected */
    public byte[] a(LocationRequest locationRequest) throws Exception {
        byte[] bArr;
        locationRequest.addHeader("et", "101");
        boolean e = locationRequest.e();
        boolean f = locationRequest.f();
        boolean g = locationRequest.g();
        d d = locationRequest.d();
        String q = d.q();
        String f2 = d.f();
        c cVar = new c();
        b a = c.a(this.a, locationRequest.a(), locationRequest.b(), locationRequest.c(), locationRequest.k(), q, f2, e, f, g, locationRequest.h());
        locationRequest.a(a);
        byte[] a2 = cVar.a(a, true);
        a(a2);
        if (!this.c) {
            return a2;
        }
        try {
            bArr = d.a(a2);
        } catch (Exception e2) {
            com.amap.location.common.d.a.b("aosloc", "aos body error", e2);
            bArr = null;
        }
        return bArr != null ? bArr : a2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0216  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(com.amap.location.protocol.LocationRequest r6, boolean r7, boolean r8) {
        /*
            r5 = this;
            r0 = 0
            r5.c = r0
            com.amap.location.common.model.FPS r1 = r6.a()
            com.amap.location.common.model.CellStatus r2 = r1.cellStatus
            if (r2 == 0) goto L_0x001b
            com.amap.location.common.model.CellStatus r2 = r1.cellStatus
            com.amap.location.common.model.CellState r2 = r2.mainCell
            if (r2 == 0) goto L_0x001b
            com.amap.location.common.model.CellStatus r0 = r1.cellStatus
            com.amap.location.common.model.CellState r0 = r0.mainCell
            int r0 = r0.mcc
            boolean r0 = com.amap.location.protocol.e.a.a(r0)
        L_0x001b:
            if (r0 == 0) goto L_0x003c
            boolean r0 = com.amap.location.protocol.b.a.a
            if (r0 == 0) goto L_0x003c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = com.amap.location.protocol.b.a.o
            r7.append(r8)
            java.lang.String r8 = "&csid="
            r7.append(r8)
            java.lang.String r6 = r6.j()
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            return r6
        L_0x003c:
            com.amap.location.protocol.d r0 = r6.d()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            if (r7 == 0) goto L_0x004d
            java.lang.String r7 = com.amap.location.protocol.b.a.l
            r1.append(r7)
            goto L_0x0076
        L_0x004d:
            if (r8 == 0) goto L_0x0063
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "https://"
            r7.<init>(r8)
            java.lang.String r8 = com.amap.location.protocol.b.a.k
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r1.append(r7)
            goto L_0x0076
        L_0x0063:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "http://"
            r7.<init>(r8)
            java.lang.String r8 = com.amap.location.protocol.b.a.k
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r1.append(r7)
        L_0x0076:
            java.lang.String r7 = "?"
            r1.append(r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "dip="
            r7.append(r8)
            java.lang.String r8 = r0.c()
            r7.append(r8)
            java.lang.String r8 = "&div="
            r7.append(r8)
            java.lang.String r8 = r0.d()
            r7.append(r8)
            java.lang.String r8 = r0.e()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x00ae
            java.lang.String r8 = "&autodiv="
            r7.append(r8)
            java.lang.String r8 = r0.e()
            r7.append(r8)
        L_0x00ae:
            java.lang.String r8 = "&adiu="
            r7.append(r8)
            java.lang.String r8 = r0.f()
            r7.append(r8)
            java.lang.String r8 = "&dibv="
            r7.append(r8)
            java.lang.String r8 = r0.g()
            r7.append(r8)
            java.lang.String r8 = "&die="
            r7.append(r8)
            java.lang.String r8 = r0.h()
            r7.append(r8)
            java.lang.String r8 = "&did="
            r7.append(r8)
            java.lang.String r8 = r0.i()
            r7.append(r8)
            java.lang.String r8 = "&dic="
            r7.append(r8)
            java.lang.String r8 = r0.j()
            r7.append(r8)
            java.lang.String r8 = "&diu="
            r7.append(r8)
            java.lang.String r8 = r0.k()
            r7.append(r8)
            java.lang.String r8 = "&diu2="
            r7.append(r8)
            java.lang.String r8 = r0.l()
            r7.append(r8)
            java.lang.String r8 = "&diu3="
            r7.append(r8)
            java.lang.String r8 = r0.m()
            r7.append(r8)
            java.lang.String r8 = "&channel="
            r7.append(r8)
            java.lang.String r8 = r0.n()
            r7.append(r8)
            java.lang.String r8 = "&cifa="
            r7.append(r8)
            java.lang.String r8 = r0.o()
            r7.append(r8)
            java.lang.String r8 = "&from="
            r7.append(r8)
            java.lang.String r8 = r0.p()
            r7.append(r8)
            java.lang.String r8 = "&session="
            r7.append(r8)
            java.lang.String r8 = r0.a()
            r7.append(r8)
            java.lang.String r8 = "&spm="
            r7.append(r8)
            java.lang.String r8 = r0.b()
            r7.append(r8)
            java.lang.String r8 = r0.q()
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            if (r2 != 0) goto L_0x0163
            java.lang.String r2 = "&tid="
            r7.append(r2)
            java.lang.String r2 = "utf-8"
            java.lang.String r2 = android.net.Uri.encode(r8, r2)
            r7.append(r2)
        L_0x0163:
            java.lang.String r2 = "&stepid="
            r7.append(r2)
            java.lang.String r2 = r0.r()
            r7.append(r2)
            java.lang.String r2 = "&client_network_class="
            r7.append(r2)
            android.content.Context r2 = r5.a
            int r2 = com.amap.location.protocol.e.e.a(r2)
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r7.append(r2)
            java.lang.String r2 = r0.s()
            java.lang.String r2 = a(r2)
            java.lang.String r3 = r0.n()
            java.lang.String r8 = com.amap.location.security.Core.saos(r3, r8, r2)
            java.lang.String r2 = "&sign="
            r7.append(r2)
            r7.append(r8)
            java.util.Map r8 = r0.u()
            if (r8 == 0) goto L_0x01d4
            java.util.Set r8 = r8.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x01a7:
            boolean r2 = r8.hasNext()
            if (r2 == 0) goto L_0x01d4
            java.lang.Object r2 = r8.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.String r3 = "&"
            r7.append(r3)
            java.lang.Object r3 = r2.getKey()
            java.lang.String r3 = (java.lang.String) r3
            r7.append(r3)
            java.lang.String r3 = "="
            r7.append(r3)
            java.lang.Object r2 = r2.getValue()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r2 = android.net.Uri.encode(r2)
            r7.append(r2)
            goto L_0x01a7
        L_0x01d4:
            java.lang.String r8 = "0"
            java.lang.String r2 = r0.t()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x01e4
            java.lang.String r8 = r0.t()
        L_0x01e4:
            java.lang.String r2 = "&loc_scene="
            r7.append(r2)
            r7.append(r8)
            java.lang.String r8 = "&ver=5.2"
            r7.append(r8)
            r8 = 0
            java.lang.String r2 = r7.toString()     // Catch:{ Exception -> 0x0204 }
            java.lang.String r0 = r0.a(r2)     // Catch:{ Exception -> 0x0204 }
            r8 = 1
            r5.c = r8     // Catch:{ Exception -> 0x01ff }
            r8 = r0
            goto L_0x020c
        L_0x01ff:
            r8 = move-exception
            r4 = r0
            r0 = r8
            r8 = r4
            goto L_0x0205
        L_0x0204:
            r0 = move-exception
        L_0x0205:
            java.lang.String r2 = "aosloc"
            java.lang.String r3 = "aos url error"
            com.amap.location.common.d.a.b(r2, r3, r0)
        L_0x020c:
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0216
            r1.append(r7)
            goto L_0x022c
        L_0x0216:
            java.lang.String r7 = android.net.Uri.encode(r8)
            java.lang.String r8 = "in="
            r1.append(r8)
            r1.append(r7)
            java.lang.String r7 = "&ent=2"
            r1.append(r7)
            java.lang.String r7 = "&is_bin=1"
            r1.append(r7)
        L_0x022c:
            java.lang.String r7 = "&csid="
            r1.append(r7)
            java.lang.String r6 = r6.j()
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.protocol.a.a(com.amap.location.protocol.LocationRequest, boolean, boolean):java.lang.String");
    }
}
