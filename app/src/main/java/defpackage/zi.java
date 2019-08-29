package defpackage;

import com.amap.bundle.network.util.NetworkReachability;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zi reason: default package */
/* compiled from: NetworkTracerDelegate */
public final class zi implements a {
    /* JADX WARNING: Can't wrap try/catch for region: R(8:21|22|23|24|25|26|27|(2:29|(1:31))(1:32)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0096 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(defpackage.bpp r8) {
        /*
            r7 = this;
            zh r0 = new zh
            java.lang.String r1 = r8.a
            java.lang.String r2 = r8.f
            r0.<init>(r1, r2)
            java.lang.String r1 = r8.a
            r0.a = r1
            long r1 = r8.i
            r0.m = r1
            long r1 = r8.l
            r0.c = r1
            long r1 = r8.n
            r0.d = r1
            long r1 = r8.p
            r0.e = r1
            int r1 = r8.d
            r0.i = r1
            int r1 = r8.e
            r0.j = r1
            long r1 = r8.o
            r0.k = r1
            java.lang.String r1 = r8.f
            r0.b = r1
            long r1 = r8.m
            r0.l = r1
            java.util.Map<java.lang.String, java.lang.Object> r1 = r8.q
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x0061
            java.util.Map<java.lang.String, java.lang.Object> r1 = r8.q
            java.lang.String r2 = "xsign_cost"
            java.lang.Object r1 = r1.get(r2)
            boolean r2 = r1 instanceof java.lang.Long
            if (r2 == 0) goto L_0x004d
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            r0.f = r1
        L_0x004d:
            java.util.Map<java.lang.String, java.lang.Object> r1 = r8.q
            java.lang.String r2 = "wua_cost"
            java.lang.Object r1 = r1.get(r2)
            boolean r2 = r1 instanceof java.lang.Long
            if (r2 == 0) goto L_0x0061
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            r0.g = r1
        L_0x0061:
            java.lang.String r1 = r8.b
            if (r1 == 0) goto L_0x0071
            java.lang.String r1 = r8.b
            int r1 = r1.length()
            if (r1 <= 0) goto L_0x0071
            java.lang.String r1 = r8.b
            r0.h = r1
        L_0x0071:
            java.lang.String r1 = r8.c
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0083
            java.lang.String r1 = r8.c
            java.lang.String r4 = "accs"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0083
            r1 = 1
            goto L_0x0084
        L_0x0083:
            r1 = 0
        L_0x0084:
            r0.n = r1
            boolean r1 = r0.n
            if (r1 != 0) goto L_0x00d1
            boolean r1 = defpackage.bno.a     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r1 = r0.a(r1)     // Catch:{ Exception -> 0x00b9 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0096 }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0096 }
            goto L_0x00a0
        L_0x0096:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x00b9 }
            r4.<init>()     // Catch:{ Exception -> 0x00b9 }
            java.lang.String r5 = "param"
            r4.put(r5, r1)     // Catch:{ Exception -> 0x00b9 }
        L_0x00a0:
            boolean r1 = r0.n     // Catch:{ Exception -> 0x00b9 }
            if (r1 == 0) goto L_0x00b1
            int r1 = r0.i     // Catch:{ Exception -> 0x00b9 }
            r5 = 2
            if (r1 != r5) goto L_0x00d1
            java.lang.String r1 = "B004"
            long r5 = r0.m     // Catch:{ Exception -> 0x00b9 }
            defpackage.aaf.a(r1, r5, r4)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00d1
        L_0x00b1:
            java.lang.String r1 = "0"
            long r5 = r0.m     // Catch:{ Exception -> 0x00b9 }
            defpackage.aaf.a(r1, r5, r4)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00d1
        L_0x00b9:
            r0 = move-exception
            java.lang.String r1 = "network.HttpLogRecord"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "addToLog:"
            r4.<init>(r5)
            java.lang.String r0 = r0.getLocalizedMessage()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            com.amap.bundle.logs.AMapLog.e(r1, r0)
        L_0x00d1:
            java.lang.String r0 = r8.c
            if (r0 == 0) goto L_0x00e0
            java.lang.String r0 = r8.c
            java.lang.String r1 = "accs"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x00e0
            goto L_0x00e1
        L_0x00e0:
            r2 = 0
        L_0x00e1:
            java.lang.String r0 = r8.f
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x012f
            boolean r1 = defpackage.zu.a(r0)
            if (r1 == 0) goto L_0x012f
            zv r1 = new zv
            r1.<init>()
            java.lang.String r4 = "?"
            boolean r4 = r0.contains(r4)
            if (r4 == 0) goto L_0x0109
            java.lang.String r4 = "?"
            int r4 = r0.indexOf(r4)
            java.lang.String r0 = r0.substring(r3, r4)
            r1.a = r0
            goto L_0x010b
        L_0x0109:
            r1.a = r0
        L_0x010b:
            r1.d = r2
            if (r2 == 0) goto L_0x0120
            java.lang.Object r0 = r8.r
            boolean r3 = r0 instanceof anetwork.channel.statist.StatisticData
            if (r3 == 0) goto L_0x0128
            anetwork.channel.statist.StatisticData r0 = (anetwork.channel.statist.StatisticData) r0
            long r3 = r0.sendSize
            r1.b = r3
            long r3 = r0.totalSize
            r1.c = r3
            goto L_0x0128
        L_0x0120:
            long r3 = r8.m
            r1.b = r3
            long r3 = r8.o
            r1.c = r3
        L_0x0128:
            zt r0 = defpackage.zt.a()
            r0.a(r1)
        L_0x012f:
            java.lang.String r0 = r8.f
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0152
            if (r2 == 0) goto L_0x014a
            java.lang.Object r8 = r8.r
            boolean r1 = r8 instanceof anetwork.channel.statist.StatisticData
            if (r1 == 0) goto L_0x0149
            anetwork.channel.statist.StatisticData r8 = (anetwork.channel.statist.StatisticData) r8
            long r1 = r8.sendSize
            long r3 = r8.totalSize
            long r1 = r1 + r3
            a(r1, r0)
        L_0x0149:
            return
        L_0x014a:
            long r1 = r8.m
            long r3 = r8.o
            long r1 = r1 + r3
            a(r1, r0)
        L_0x0152:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zi.a(bpp):void");
    }

    private static void a(long j, String str) {
        if (j >= 1048576) {
            JSONObject jSONObject = new JSONObject();
            if (str.contains("?")) {
                str = str.substring(0, str.indexOf("?"));
            }
            try {
                jSONObject.put("url", str);
                jSONObject.put("networkStatus", NetworkReachability.c().toString());
                jSONObject.put("flow", j);
                bpv.c("Network-LargeFlow", jSONObject.toString());
            } catch (JSONException unused) {
            }
        }
    }
}
