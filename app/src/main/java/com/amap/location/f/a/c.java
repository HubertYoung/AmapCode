package com.amap.location.f.a;

import android.content.Context;
import android.os.SystemClock;
import com.amap.location.common.d.a;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: LocationCache */
public class c {
    private int a = 0;
    private int b;
    private int c;
    private Map<b, List<e>> d = new HashMap();
    private d e;
    private boolean f = false;

    public c(Context context, int i, int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("cacheTimeout <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else {
            this.c = i;
            this.b = i2;
            this.e = new d(context);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:72:0x016b, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ca A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean a(com.amap.location.common.model.FPS r11, com.amap.location.common.model.AmapLoc r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            r0 = 0
            if (r11 == 0) goto L_0x016a
            if (r12 == 0) goto L_0x016a
            boolean r1 = r12.isLocationCorrect()     // Catch:{ all -> 0x0167 }
            if (r1 != 0) goto L_0x000e
            goto L_0x016a
        L_0x000e:
            r10.b()     // Catch:{ all -> 0x0167 }
            com.amap.location.f.a.b r3 = new com.amap.location.f.a.b     // Catch:{ all -> 0x0167 }
            r3.<init>()     // Catch:{ all -> 0x0167 }
            com.amap.location.f.a.g r4 = new com.amap.location.f.a.g     // Catch:{ all -> 0x0167 }
            r4.<init>()     // Catch:{ all -> 0x0167 }
            boolean r1 = r10.a(r11, r3, r4)     // Catch:{ all -> 0x0167 }
            if (r1 != 0) goto L_0x0023
            monitor-exit(r10)
            return r0
        L_0x0023:
            long r1 = r4.a     // Catch:{ all -> 0x0167 }
            r5 = 0
            int r1 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x0042
            java.util.HashSet<java.lang.Long> r1 = r4.b     // Catch:{ all -> 0x0167 }
            int r1 = r1.size()     // Catch:{ all -> 0x0167 }
            if (r1 <= 0) goto L_0x0034
            goto L_0x0042
        L_0x0034:
            java.lang.String r1 = r12.getRetype()     // Catch:{ all -> 0x0167 }
            java.lang.String r2 = "4"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0167 }
            if (r1 == 0) goto L_0x0064
            monitor-exit(r10)
            return r0
        L_0x0042:
            float r1 = r12.getAccuracy()     // Catch:{ all -> 0x0167 }
            r2 = 1133903872(0x43960000, float:300.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x0058
            java.util.HashSet<java.lang.Long> r1 = r4.b     // Catch:{ all -> 0x0167 }
            int r1 = r1.size()     // Catch:{ all -> 0x0167 }
            r2 = 8
            if (r1 < r2) goto L_0x0058
            monitor-exit(r10)
            return r0
        L_0x0058:
            float r1 = r12.getAccuracy()     // Catch:{ all -> 0x0167 }
            r2 = 1092616192(0x41200000, float:10.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x0064
            monitor-exit(r10)
            return r0
        L_0x0064:
            byte r1 = r3.b     // Catch:{ all -> 0x0167 }
            r2 = 3
            if (r1 != r2) goto L_0x008b
            java.lang.String r1 = r12.getMcell()     // Catch:{ all -> 0x0167 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0167 }
            if (r1 != 0) goto L_0x008b
            com.amap.location.common.model.AmapLoc r1 = r12.getMcellLoc()     // Catch:{ all -> 0x0167 }
            if (r1 == 0) goto L_0x008b
            boolean r5 = r1.isLocationCorrect()     // Catch:{ all -> 0x0167 }
            if (r5 == 0) goto L_0x008b
            com.amap.location.common.model.FPS r5 = new com.amap.location.common.model.FPS     // Catch:{ all -> 0x0167 }
            r5.<init>()     // Catch:{ all -> 0x0167 }
            com.amap.location.common.model.CellStatus r11 = r11.cellStatus     // Catch:{ all -> 0x0167 }
            r5.cellStatus = r11     // Catch:{ all -> 0x0167 }
            r10.a(r5, r1)     // Catch:{ all -> 0x0167 }
        L_0x008b:
            r11 = 1
            com.amap.location.f.a.e r1 = r10.a(r3, r4, r11)     // Catch:{ all -> 0x0167 }
            r5 = 0
            if (r1 == 0) goto L_0x0097
            com.amap.location.common.model.AmapLoc r5 = r1.b()     // Catch:{ all -> 0x0167 }
        L_0x0097:
            if (r5 == 0) goto L_0x00de
            boolean r6 = r5.isLocationCorrect()     // Catch:{ all -> 0x0167 }
            if (r6 == 0) goto L_0x00de
            r6 = 4
            double[] r6 = new double[r6]     // Catch:{ all -> 0x0167 }
            double r7 = r12.getLat()     // Catch:{ all -> 0x0167 }
            r6[r0] = r7     // Catch:{ all -> 0x0167 }
            double r7 = r12.getLon()     // Catch:{ all -> 0x0167 }
            r6[r11] = r7     // Catch:{ all -> 0x0167 }
            double r7 = r5.getLat()     // Catch:{ all -> 0x0167 }
            r9 = 2
            r6[r9] = r7     // Catch:{ all -> 0x0167 }
            double r7 = r5.getLon()     // Catch:{ all -> 0x0167 }
            r6[r2] = r7     // Catch:{ all -> 0x0167 }
            float r5 = com.amap.location.common.f.d.a(r6)     // Catch:{ all -> 0x0167 }
            int r5 = (int) r5     // Catch:{ all -> 0x0167 }
            r6 = 550(0x226, float:7.71E-43)
            byte r7 = r3.b     // Catch:{ all -> 0x0167 }
            if (r7 != r9) goto L_0x00c8
            r6 = 200(0xc8, float:2.8E-43)
        L_0x00c8:
            if (r5 >= r6) goto L_0x00cc
            monitor-exit(r10)
            return r0
        L_0x00cc:
            java.lang.String r6 = "loccache"
            java.lang.String r7 = "remove cached, distance:"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0167 }
            java.lang.String r5 = r7.concat(r5)     // Catch:{ all -> 0x0167 }
            com.amap.location.common.d.a.b(r6, r5)     // Catch:{ all -> 0x0167 }
            r10.a(r3, r1)     // Catch:{ all -> 0x0167 }
        L_0x00de:
            java.lang.String r1 = "loccache"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = "add cache key:"
            r5.<init>(r6)     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = com.amap.location.common.d.a.a(r6)     // Catch:{ all -> 0x0167 }
            r5.append(r6)     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = ",nearby:"
            r5.append(r6)     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = r4.toString()     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = com.amap.location.common.d.a.a(r6)     // Catch:{ all -> 0x0167 }
            r5.append(r6)     // Catch:{ all -> 0x0167 }
            java.lang.String r6 = ",loc:"
            r5.append(r6)     // Catch:{ all -> 0x0167 }
            java.lang.String r2 = r12.toJSONStr(r2)     // Catch:{ all -> 0x0167 }
            java.lang.String r2 = com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x0167 }
            r5.append(r2)     // Catch:{ all -> 0x0167 }
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x0167 }
            com.amap.location.common.d.a.b(r1, r2)     // Catch:{ all -> 0x0167 }
            com.amap.location.f.a.e r1 = new com.amap.location.f.a.e     // Catch:{ all -> 0x0167 }
            r1.<init>()     // Catch:{ all -> 0x0167 }
            r1.a(r12)     // Catch:{ all -> 0x0167 }
            r1.a(r4)     // Catch:{ all -> 0x0167 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0167 }
            r1.b(r5)     // Catch:{ all -> 0x0167 }
            r1.c(r5)     // Catch:{ all -> 0x0167 }
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r2 = r10.d     // Catch:{ all -> 0x0167 }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0167 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x0167 }
            if (r2 == 0) goto L_0x013c
            r2.add(r0, r1)     // Catch:{ all -> 0x0167 }
            goto L_0x0149
        L_0x013c:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0167 }
            r0.<init>()     // Catch:{ all -> 0x0167 }
            r0.add(r1)     // Catch:{ all -> 0x0167 }
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r2 = r10.d     // Catch:{ all -> 0x0167 }
            r2.put(r3, r0)     // Catch:{ all -> 0x0167 }
        L_0x0149:
            com.amap.location.f.a.d r2 = r10.e     // Catch:{ all -> 0x0167 }
            long r6 = r1.d()     // Catch:{ all -> 0x0167 }
            long r8 = r1.e()     // Catch:{ all -> 0x0167 }
            r5 = r12
            long r2 = r2.a(r3, r4, r5, r6, r8)     // Catch:{ all -> 0x0167 }
            r1.a(r2)     // Catch:{ all -> 0x0167 }
            int r12 = r10.a     // Catch:{ all -> 0x0167 }
            int r12 = r12 + r11
            r10.a = r12     // Catch:{ all -> 0x0167 }
            int r12 = r10.b     // Catch:{ all -> 0x0167 }
            r10.a(r12)     // Catch:{ all -> 0x0167 }
            monitor-exit(r10)
            return r11
        L_0x0167:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        L_0x016a:
            monitor-exit(r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.f.a.c.a(com.amap.location.common.model.FPS, com.amap.location.common.model.AmapLoc):boolean");
    }

    public AmapLoc a(FPS fps) {
        b bVar = new b();
        g gVar = new g();
        if (!a(fps, bVar, gVar)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("get key:");
        sb.append(a.a(bVar.toString()));
        sb.append(" nearby:");
        sb.append(a.a(gVar.toString()));
        sb.append(" dbsize:");
        sb.append(d());
        a.b("loccache", sb.toString());
        e a2 = a(bVar, gVar, true);
        if (a2 == null) {
            return null;
        }
        return a2.b();
    }

    private e a(b bVar, g gVar) {
        if (!bVar.a()) {
            return null;
        }
        if (gVar == null) {
            gVar = new g();
        }
        b();
        if (this.d.isEmpty()) {
            return null;
        }
        e eVar = bVar.b == 3 ? a(gVar, bVar) : bVar.b == 2 ? a(gVar, bVar) : (bVar.b != 1 || !this.d.containsKey(bVar)) ? null : (e) this.d.get(bVar).get(0);
        if (eVar != null) {
            long currentTimeMillis = System.currentTimeMillis() - eVar.e();
            if (currentTimeMillis > ((long) this.c)) {
                a.b("loccache", "cache timeout, interval:".concat(String.valueOf(currentTimeMillis)));
                a(bVar, eVar);
                return null;
            }
        }
        return eVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.amap.location.f.a.e a(com.amap.location.f.a.b r5, com.amap.location.f.a.g r6, boolean r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            com.amap.location.f.a.e r6 = r4.a(r5, r6)     // Catch:{ all -> 0x0045 }
            if (r6 == 0) goto L_0x0042
            com.amap.location.common.model.AmapLoc r0 = r6.b()     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x003f
            boolean r1 = r0.isLocationCorrect()     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x003f
            java.lang.String r1 = "mem"
            r0.setType(r1)     // Catch:{ all -> 0x0045 }
            if (r7 == 0) goto L_0x003d
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0045 }
            r6.b(r0)     // Catch:{ all -> 0x0045 }
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r7 = r4.d     // Catch:{ all -> 0x0045 }
            java.lang.Object r5 = r7.get(r5)     // Catch:{ all -> 0x0045 }
            java.util.List r5 = (java.util.List) r5     // Catch:{ all -> 0x0045 }
            r5.remove(r6)     // Catch:{ all -> 0x0045 }
            r7 = 0
            r5.add(r7, r6)     // Catch:{ all -> 0x0045 }
            com.amap.location.f.a.d r5 = r4.e     // Catch:{ all -> 0x0045 }
            long r0 = r6.a()     // Catch:{ all -> 0x0045 }
            long r2 = r6.d()     // Catch:{ all -> 0x0045 }
            r5.a(r0, r2)     // Catch:{ all -> 0x0045 }
        L_0x003d:
            monitor-exit(r4)
            return r6
        L_0x003f:
            r4.a(r5, r6)     // Catch:{ all -> 0x0045 }
        L_0x0042:
            r5 = 0
            monitor-exit(r4)
            return r5
        L_0x0045:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.f.a.c.a(com.amap.location.f.a.b, com.amap.location.f.a.g, boolean):com.amap.location.f.a.e");
    }

    private boolean a(FPS fps, b bVar, g gVar) {
        if (fps == null) {
            return false;
        }
        f.a(fps.cellStatus, bVar);
        f.a(fps.wifiStatus, gVar);
        if (bVar.a.size() > 0) {
            bVar.b = (byte) (bVar.b | 1);
        }
        if ((bVar.b > 0 && gVar.b.size() > 0) || gVar.b.size() > 1) {
            bVar.b = (byte) (bVar.b | 2);
        }
        if (bVar.b > 0) {
            return true;
        }
        return false;
    }

    private void a(int i) {
        while (this.a > i) {
            b bVar = null;
            long j = Long.MAX_VALUE;
            for (Entry next : this.d.entrySet()) {
                long d2 = ((e) ((List) next.getValue()).get(((List) next.getValue()).size() - 1)).d();
                if (d2 < j) {
                    bVar = (b) next.getKey();
                    j = d2;
                }
            }
            List list = this.d.get(bVar);
            a(bVar, (e) list.get(list.size() - 1));
        }
    }

    private void a(b bVar, e eVar) {
        if (bVar.a()) {
            boolean z = false;
            List list = this.d.get(bVar);
            if (list != null) {
                z = list.remove(eVar);
                if (list.size() == 0) {
                    this.d.remove(bVar);
                }
            }
            this.e.a(eVar);
            if (z) {
                this.a--;
            }
        }
    }

    public synchronized void a() {
        this.d.clear();
        this.a = 0;
        this.f = false;
        this.e.a();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0093, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0095, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.amap.location.f.a.e a(com.amap.location.f.a.g r12, com.amap.location.f.a.b r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r0 = r11.d     // Catch:{ all -> 0x0096 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0096 }
            r1 = 0
            if (r0 != 0) goto L_0x0094
            java.util.HashSet<java.lang.Long> r0 = r12.b     // Catch:{ all -> 0x0096 }
            int r0 = r0.size()     // Catch:{ all -> 0x0096 }
            if (r0 != 0) goto L_0x0014
            goto L_0x0094
        L_0x0014:
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r0 = r11.d     // Catch:{ all -> 0x0096 }
            boolean r0 = r0.containsKey(r13)     // Catch:{ all -> 0x0096 }
            if (r0 != 0) goto L_0x001e
            monitor-exit(r11)
            return r1
        L_0x001e:
            java.util.Map<com.amap.location.f.a.b, java.util.List<com.amap.location.f.a.e>> r0 = r11.d     // Catch:{ all -> 0x0096 }
            java.lang.Object r13 = r0.get(r13)     // Catch:{ all -> 0x0096 }
            java.util.List r13 = (java.util.List) r13     // Catch:{ all -> 0x0096 }
            r0 = 0
            r2 = 0
        L_0x0028:
            int r3 = r13.size()     // Catch:{ all -> 0x0096 }
            if (r2 >= r3) goto L_0x0092
            java.lang.Object r3 = r13.get(r2)     // Catch:{ all -> 0x0096 }
            com.amap.location.f.a.e r3 = (com.amap.location.f.a.e) r3     // Catch:{ all -> 0x0096 }
            com.amap.location.f.a.g r4 = r3.c()     // Catch:{ all -> 0x0096 }
            java.util.HashSet<java.lang.Long> r4 = r4.b     // Catch:{ all -> 0x0096 }
            int r4 = r4.size()     // Catch:{ all -> 0x0096 }
            if (r4 == 0) goto L_0x008f
            com.amap.location.f.a.g r4 = r3.c()     // Catch:{ all -> 0x0096 }
            long r4 = r4.a     // Catch:{ all -> 0x0096 }
            long r6 = r12.a     // Catch:{ all -> 0x0096 }
            boolean r4 = com.amap.location.f.a.f.a(r4, r6)     // Catch:{ all -> 0x0096 }
            r5 = 1
            if (r4 == 0) goto L_0x0060
            com.amap.location.f.a.g r4 = r3.c()     // Catch:{ all -> 0x0096 }
            java.util.HashSet<java.lang.Long> r4 = r4.b     // Catch:{ all -> 0x0096 }
            java.util.HashSet<java.lang.Long> r6 = r12.b     // Catch:{ all -> 0x0096 }
            boolean r4 = com.amap.location.f.a.f.a(r4, r6)     // Catch:{ all -> 0x0096 }
            if (r4 == 0) goto L_0x005e
            goto L_0x008d
        L_0x005e:
            r4 = 1
            goto L_0x0061
        L_0x0060:
            r4 = 0
        L_0x0061:
            com.amap.location.f.a.g r6 = r3.c()     // Catch:{ all -> 0x0096 }
            java.util.HashSet<java.lang.Long> r6 = r6.b     // Catch:{ all -> 0x0096 }
            java.util.HashSet<java.lang.Long> r7 = r12.b     // Catch:{ all -> 0x0096 }
            double[] r6 = com.amap.location.f.a.f.b(r6, r7)     // Catch:{ all -> 0x0096 }
            r7 = r6[r0]     // Catch:{ all -> 0x0096 }
            r9 = 4605380979056443392(0x3fe99999a0000000, double:0.800000011920929)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 < 0) goto L_0x0079
            goto L_0x008d
        L_0x0079:
            r7 = r6[r5]     // Catch:{ all -> 0x0096 }
            r9 = 4603741668684706349(0x3fe3c6a7ef9db22d, double:0.618)
            int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r5 < 0) goto L_0x0085
            goto L_0x008d
        L_0x0085:
            if (r4 == 0) goto L_0x008f
            r4 = r6[r0]     // Catch:{ all -> 0x0096 }
            int r4 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r4 < 0) goto L_0x008f
        L_0x008d:
            r1 = r3
            goto L_0x0092
        L_0x008f:
            int r2 = r2 + 1
            goto L_0x0028
        L_0x0092:
            monitor-exit(r11)
            return r1
        L_0x0094:
            monitor-exit(r11)
            return r1
        L_0x0096:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.f.a.c.a(com.amap.location.f.a.g, com.amap.location.f.a.b):com.amap.location.f.a.e");
    }

    private void b() {
        c();
    }

    private synchronized void c() {
        if (!this.f) {
            SystemClock.elapsedRealtime();
            Map<b, List<e>> a2 = this.e.a(this.c, this.b);
            if (a2 != null) {
                this.d = a2;
                for (Entry<b, List<e>> value : this.d.entrySet()) {
                    this.a += ((List) value.getValue()).size();
                }
            }
            SystemClock.elapsedRealtime();
            this.f = true;
        }
    }

    private int d() {
        int i = 0;
        for (List next : this.d.values()) {
            if (next != null) {
                i += next.size();
            }
        }
        return i;
    }
}
