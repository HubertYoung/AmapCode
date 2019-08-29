package defpackage;

/* renamed from: avf reason: default package */
/* compiled from: PoiResultWrapperUtil */
public final class avf {
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00bb, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.ArrayList<com.autonavi.common.model.POI> a(defpackage.aud r6) {
        /*
            java.lang.Class<avf> r0 = defpackage.avf.class
            monitor-enter(r0)
            r1 = 0
            if (r6 == 0) goto L_0x00ba
            auc r2 = r6.b     // Catch:{ all -> 0x00b7 }
            if (r2 == 0) goto L_0x00ba
            auc r2 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00b7 }
            if (r2 == 0) goto L_0x00ba
            auc r2 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00b7 }
            int r2 = r2.size()     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x001c
            goto L_0x00ba
        L_0x001c:
            r2 = 0
            if (r6 == 0) goto L_0x0060
            auc r3 = r6.b     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x0060
            auc r3 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00b7 }
            if (r3 != 0) goto L_0x002a
            goto L_0x0060
        L_0x002a:
            r3 = 0
        L_0x002b:
            auc r4 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b7 }
            int r4 = r4.size()     // Catch:{ all -> 0x00b7 }
            if (r3 >= r4) goto L_0x0056
            auc r4 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b7 }
            java.lang.Object r4 = r4.get(r3)     // Catch:{ all -> 0x00b7 }
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4     // Catch:{ all -> 0x00b7 }
            if (r4 == 0) goto L_0x0053
            java.lang.String r4 = r4.getId()     // Catch:{ all -> 0x00b7 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x00b7 }
            if (r4 == 0) goto L_0x0053
            auc r4 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b7 }
            r4.remove(r3)     // Catch:{ all -> 0x00b7 }
            goto L_0x0056
        L_0x0053:
            int r3 = r3 + 1
            goto L_0x002b
        L_0x0056:
            auc r3 = r6.b     // Catch:{ all -> 0x00b7 }
            boolean r3 = r3.g     // Catch:{ all -> 0x00b7 }
            if (r3 != 0) goto L_0x0060
            auc r3 = r6.b     // Catch:{ all -> 0x00b7 }
            r3.f = r1     // Catch:{ all -> 0x00b7 }
        L_0x0060:
            auc r3 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00b7 }
            int r3 = r3.size()     // Catch:{ all -> 0x00b7 }
            r4 = 1
            if (r6 == 0) goto L_0x008d
            auc r5 = r6.b     // Catch:{ all -> 0x00b7 }
            if (r5 == 0) goto L_0x008d
            auc r5 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00b7 }
            if (r5 == 0) goto L_0x008d
            auc r5 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00b7 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b7 }
            if (r5 <= 0) goto L_0x008d
            auc r5 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r5 = r5.d     // Catch:{ all -> 0x00b7 }
            int r5 = r5.size()     // Catch:{ all -> 0x00b7 }
            int r5 = r5 + 10
            int r5 = r5 - r4
            int r5 = r5 / 10
            goto L_0x008e
        L_0x008d:
            r5 = 1
        L_0x008e:
            if (r4 <= r5) goto L_0x0092
            monitor-exit(r0)
            return r1
        L_0x0092:
            if (r3 > 0) goto L_0x0096
            monitor-exit(r0)
            return r1
        L_0x0096:
            int r3 = r3 - r4
            r1 = 9
            if (r1 <= r3) goto L_0x009c
            r1 = r3
        L_0x009c:
            int r1 = r1 - r2
            int r1 = r1 + r4
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x00b7 }
            r3.<init>()     // Catch:{ all -> 0x00b7 }
        L_0x00a3:
            if (r2 >= r1) goto L_0x00b5
            auc r4 = r6.b     // Catch:{ all -> 0x00b7 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b7 }
            int r5 = r2 + 0
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x00b7 }
            r3.add(r4)     // Catch:{ all -> 0x00b7 }
            int r2 = r2 + 1
            goto L_0x00a3
        L_0x00b5:
            monitor-exit(r0)
            return r3
        L_0x00b7:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        L_0x00ba:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.avf.a(aud):java.util.ArrayList");
    }

    public static int b(aud aud) {
        if (aud == null) {
            return 0;
        }
        return ((aud.b.e + 10) - 1) / 10;
    }
}
