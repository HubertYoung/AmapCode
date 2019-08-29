package com.amap.location.c;

import com.amap.location.common.model.AmapLoc;

/* compiled from: LocFilter */
public class a {
    private AmapLoc a = null;
    private long b = 0;
    private long c = 0;

    /* JADX WARNING: Unknown top exception splitter block from list: {B:91:0x01a0=Splitter:B:91:0x01a0, B:18:0x0035=Splitter:B:18:0x0035, B:122:0x025a=Splitter:B:122:0x025a, B:38:0x008b=Splitter:B:38:0x008b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.amap.location.common.model.AmapLoc a(com.amap.location.common.model.AmapLoc r21) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            monitor-enter(r20)
            com.amap.location.common.model.AmapLoc r3 = r1.a     // Catch:{ all -> 0x0266 }
            if (r3 == 0) goto L_0x025a
            com.amap.location.common.model.AmapLoc r3 = r1.a     // Catch:{ all -> 0x0266 }
            boolean r3 = r3.isLocationCorrect()     // Catch:{ all -> 0x0266 }
            if (r3 == 0) goto L_0x025a
            if (r2 == 0) goto L_0x025a
            boolean r3 = r21.isLocationCorrect()     // Catch:{ all -> 0x0266 }
            if (r3 != 0) goto L_0x001b
            goto L_0x025a
        L_0x001b:
            long r3 = r21.getTime()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r5 = r1.a     // Catch:{ all -> 0x0266 }
            long r5 = r5.getTime()     // Catch:{ all -> 0x0266 }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r4 = 1133903872(0x43960000, float:300.0)
            if (r3 != 0) goto L_0x0035
            float r3 = r21.getAccuracy()     // Catch:{ all -> 0x0266 }
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x0035
            monitor-exit(r20)
            return r2
        L_0x0035:
            java.lang.String r3 = r21.getProvider()     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = "gps"
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x0266 }
            if (r3 == 0) goto L_0x004d
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0266 }
            r1.b = r3     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x004d:
            int r3 = r21.getCoord()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r5 = r1.a     // Catch:{ all -> 0x0266 }
            int r5 = r5.getCoord()     // Catch:{ all -> 0x0266 }
            if (r3 == r5) goto L_0x0065
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0266 }
            r1.b = r3     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0065:
            java.lang.String r3 = r21.getPoiid()     // Catch:{ all -> 0x0266 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0266 }
            if (r3 != 0) goto L_0x008b
            java.lang.String r3 = r21.getPoiid()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r5 = r1.a     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = r5.getPoiid()     // Catch:{ all -> 0x0266 }
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x0266 }
            if (r3 != 0) goto L_0x008b
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0266 }
            r1.b = r3     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x008b:
            com.amap.location.common.model.AmapLoc r3 = r1.a     // Catch:{ all -> 0x0266 }
            float r3 = com.amap.location.common.f.d.a(r2, r3)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r5 = r1.a     // Catch:{ all -> 0x0266 }
            float r5 = r5.getAccuracy()     // Catch:{ all -> 0x0266 }
            float r6 = r21.getAccuracy()     // Catch:{ all -> 0x0266 }
            float r7 = r6 - r5
            long r8 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0266 }
            long r10 = r1.b     // Catch:{ all -> 0x0266 }
            r12 = 0
            long r10 = r8 - r10
            r12 = 1120403456(0x42c80000, float:100.0)
            int r12 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            r13 = 0
            r15 = 3
            if (r12 > 0) goto L_0x00b3
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x00bb
        L_0x00b3:
            int r12 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r12 < 0) goto L_0x0142
            int r16 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r16 < 0) goto L_0x0142
        L_0x00bb:
            long r3 = r1.c     // Catch:{ all -> 0x0266 }
            int r3 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r3 != 0) goto L_0x00f1
            java.lang.String r3 = "locfilter"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = "acc become low or continue low"
            r4.<init>(r5)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r5 = r1.a     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = r5.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = com.amap.location.common.d.a.a(r5)     // Catch:{ all -> 0x0266 }
            r4.append(r5)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = ",new:"
            r4.append(r5)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = r2.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = com.amap.location.common.d.a.a(r5)     // Catch:{ all -> 0x0266 }
            r4.append(r5)     // Catch:{ all -> 0x0266 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r3, r4)     // Catch:{ all -> 0x0266 }
            r1.c = r8     // Catch:{ all -> 0x0266 }
            goto L_0x0121
        L_0x00f1:
            long r3 = r1.c     // Catch:{ all -> 0x0266 }
            r5 = 0
            long r3 = r8 - r3
            r5 = 30000(0x7530, double:1.4822E-319)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x0121
            java.lang.String r3 = "locfilter"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = "delay report new loc"
            r4.<init>(r5)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = r2.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r5 = com.amap.location.common.d.a.a(r5)     // Catch:{ all -> 0x0266 }
            r4.append(r5)     // Catch:{ all -> 0x0266 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r3, r4)     // Catch:{ all -> 0x0266 }
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            r1.c = r13     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0121:
            r3 = 30000(0x7530, double:1.4822E-319)
            int r3 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r3 < 0) goto L_0x012e
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            r1.c = r13     // Catch:{ all -> 0x0266 }
            goto L_0x013e
        L_0x012e:
            java.lang.String r2 = "locfilter"
            java.lang.String r3 = "time invaild:"
            java.lang.String r4 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r2, r3)     // Catch:{ all -> 0x0266 }
        L_0x013e:
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0142:
            r17 = 1125515264(0x43160000, float:150.0)
            int r17 = (r6 > r17 ? 1 : (r6 == r17 ? 0 : -1))
            if (r17 >= 0) goto L_0x0154
            if (r12 < 0) goto L_0x0154
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            r1.c = r13     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0154:
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 >= 0) goto L_0x015a
            r1.c = r13     // Catch:{ all -> 0x0266 }
        L_0x015a:
            r12 = 1092616192(0x41200000, float:10.0)
            int r12 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r12 >= 0) goto L_0x01a4
            double r13 = (double) r3     // Catch:{ all -> 0x0266 }
            r18 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            int r12 = (r13 > r18 ? 1 : (r13 == r18 ? 0 : -1))
            if (r12 <= 0) goto L_0x01a4
            java.lang.String r4 = "locfilter"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0266 }
            java.lang.String r11 = "little shake:"
            r10.<init>(r11)     // Catch:{ all -> 0x0266 }
            r10.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = "m"
            r10.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = r10.toString()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r4, r3)     // Catch:{ all -> 0x0266 }
            r3 = -1013579776(0xffffffffc3960000, float:-300.0)
            int r3 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r3 < 0) goto L_0x018c
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x018c:
            r3 = 0
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x01a0
            float r5 = r5 / r6
            r3 = 1073741824(0x40000000, float:2.0)
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 < 0) goto L_0x01a0
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x01a0:
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x01a4:
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 < 0) goto L_0x01ef
            r4 = 30000(0x7530, double:1.4822E-319)
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 < 0) goto L_0x01b6
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x01b6:
            java.lang.String r4 = "locfilter"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0266 }
            java.lang.String r6 = "big shake in short time"
            r5.<init>(r6)     // Catch:{ all -> 0x0266 }
            r5.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = ",old:"
            r5.append(r3)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r3 = r1.a     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = r3.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = com.amap.location.common.d.a.a(r3)     // Catch:{ all -> 0x0266 }
            r5.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = ",new:"
            r5.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = r2.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x0266 }
            r5.append(r2)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r4, r2)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x01ef:
            long r4 = r1.b     // Catch:{ all -> 0x0266 }
            r6 = 0
            long r4 = r8 - r4
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x020a
            double r6 = (double) r3     // Catch:{ all -> 0x0266 }
            r10 = 4615288898129284301(0x400ccccccccccccd, double:3.6)
            double r6 = r6 * r10
            r10 = 1000(0x3e8, double:4.94E-321)
            long r10 = r4 / r10
            double r10 = (double) r10     // Catch:{ all -> 0x0266 }
            double r6 = r6 / r10
            int r3 = (int) r6     // Catch:{ all -> 0x0266 }
            goto L_0x020d
        L_0x020a:
            r3 = 2147483647(0x7fffffff, float:NaN)
        L_0x020d:
            r6 = 130(0x82, float:1.82E-43)
            if (r3 < r6) goto L_0x0252
            java.lang.String r6 = "locfilter"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0266 }
            java.lang.String r8 = "fast move speed:"
            r7.<init>(r8)     // Catch:{ all -> 0x0266 }
            r7.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = ",dt:"
            r7.append(r3)     // Catch:{ all -> 0x0266 }
            r7.append(r4)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = ",old:"
            r7.append(r3)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r3 = r1.a     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = r3.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = com.amap.location.common.d.a.a(r3)     // Catch:{ all -> 0x0266 }
            r7.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r3 = ",new:"
            r7.append(r3)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = r2.toJSONStr(r15)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = com.amap.location.common.d.a.a(r2)     // Catch:{ all -> 0x0266 }
            r7.append(r2)     // Catch:{ all -> 0x0266 }
            java.lang.String r2 = r7.toString()     // Catch:{ all -> 0x0266 }
            com.amap.location.common.d.a.b(r6, r2)     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0252:
            r1.b = r8     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x025a:
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0266 }
            r1.b = r3     // Catch:{ all -> 0x0266 }
            r1.a = r2     // Catch:{ all -> 0x0266 }
            com.amap.location.common.model.AmapLoc r2 = r1.a     // Catch:{ all -> 0x0266 }
            monitor-exit(r20)
            return r2
        L_0x0266:
            r0 = move-exception
            r2 = r0
            monitor-exit(r20)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.c.a.a(com.amap.location.common.model.AmapLoc):com.amap.location.common.model.AmapLoc");
    }
}
