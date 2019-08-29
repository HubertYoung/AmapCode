package defpackage;

import org.json.JSONObject;

/* renamed from: bsr reason: default package */
/* compiled from: SaveBeanJsonUtils */
public final class bsr {
    public static JSONObject a(bth bth, String str) {
        if (bth == null || bth.a() == null) {
            return null;
        }
        coo coo = (coo) ank.a(coo.class);
        if (coo == null) {
            return null;
        }
        JSONObject a = coo.a(bth.a());
        if (a == null) {
            return null;
        }
        agd.a(a, (String) "item_id", str);
        agd.a(a, (String) "type", 0);
        Long valueOf = Long.valueOf(0);
        if (bth.f != null) {
            valueOf = bth.f;
        }
        agd.a(a, (String) "create_time", String.valueOf(((double) valueOf.longValue()) / 1000.0d));
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0015  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0014 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.bth a(java.lang.String r2, java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 0
            boolean r1 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x000d }
            if (r1 != 0) goto L_0x0011
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x000d }
            r1.<init>(r2)     // Catch:{ JSONException -> 0x000d }
            goto L_0x0012
        L_0x000d:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0011:
            r1 = r0
        L_0x0012:
            if (r1 != 0) goto L_0x0015
            return r0
        L_0x0015:
            bth r2 = new bth
            r2.<init>()
            r2.a = r3
            r2.b = r4
            java.lang.String r3 = r1.toString()
            r2.c = r3
            java.lang.String r3 = "common_name"
            java.lang.String r3 = defpackage.agd.e(r1, r3)
            r2.d = r3
            java.lang.String r3 = "poiid"
            java.lang.String r3 = defpackage.agd.e(r1, r3)
            r2.e = r3
            r3 = 0
            java.lang.String r0 = "create_time"
            double r0 = defpackage.agd.b(r1, r0)
            double r3 = java.lang.Math.max(r3, r0)
            r0 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r3 = r3 * r0
            long r3 = (long) r3
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r2.f = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Long r4 = r2.f
            r3.append(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bsr.a(java.lang.String, java.lang.String, java.lang.String):bth");
    }
}
