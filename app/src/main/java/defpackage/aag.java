package defpackage;

/* renamed from: aag reason: default package */
/* compiled from: NetworkABTestTools */
public final class aag {
    private static volatile a a;

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        if (r5 == null) goto L_0x000a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0085 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0086  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map<java.lang.String, java.lang.String> a(java.lang.String r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            if (r5 != 0) goto L_0x000d
        L_0x000a:
            r5 = r1
            goto L_0x0074
        L_0x000d:
            java.lang.String r0 = "://"
            java.lang.String[] r0 = r5.split(r0)
            r2 = 1
            if (r0 == 0) goto L_0x0040
            int r3 = r0.length
            if (r3 <= r2) goto L_0x0040
            r5 = r0[r2]
        L_0x001b:
            if (r5 == 0) goto L_0x002a
            java.lang.String r0 = "/"
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x002a
            java.lang.String r5 = r5.substring(r2)
            goto L_0x001b
        L_0x002a:
            if (r5 != 0) goto L_0x002d
            goto L_0x000a
        L_0x002d:
            java.lang.String r0 = "/"
            int r0 = r5.indexOf(r0)
            if (r0 < 0) goto L_0x003d
            int r3 = r5.length()
            java.lang.String r5 = r5.substring(r0, r3)
        L_0x003d:
            if (r5 != 0) goto L_0x0040
            goto L_0x000a
        L_0x0040:
            if (r5 != 0) goto L_0x0043
            goto L_0x000a
        L_0x0043:
            if (r5 == 0) goto L_0x0052
            java.lang.String r0 = "/"
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0052
            java.lang.String r5 = r5.substring(r2)
            goto L_0x0043
        L_0x0052:
            java.lang.String r0 = "\\?"
            java.lang.String[] r0 = r5.split(r0)
            r3 = 0
            if (r0 == 0) goto L_0x0060
            int r4 = r0.length
            if (r4 <= 0) goto L_0x0060
            r5 = r0[r3]
        L_0x0060:
            if (r5 != 0) goto L_0x0063
            goto L_0x000a
        L_0x0063:
            java.lang.String r0 = "/"
            boolean r0 = r5.endsWith(r0)
            if (r0 == 0) goto L_0x0074
            int r0 = r5.length()
            int r0 = r0 - r2
            java.lang.String r5 = r5.substring(r3, r0)
        L_0x0074:
            java.lang.String r0 = "network.NetworkABTestTools"
            java.lang.String r2 = "getABTestParamMapByUrl path = "
            java.lang.String r3 = java.lang.String.valueOf(r5)
            java.lang.String r2 = r2.concat(r3)
            com.amap.bundle.logs.AMapLog.d(r0, r2)
            if (r5 != 0) goto L_0x0086
            return r1
        L_0x0086:
            java.lang.String r0 = "ws/shield/dsp/app/route/navigation"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0090
            java.lang.String r5 = "ws/mapapi/navigation/auto"
        L_0x0090:
            aae$a r0 = a
            if (r0 != 0) goto L_0x00a2
            aae r0 = defpackage.aaf.a
            if (r0 != 0) goto L_0x009a
            r0 = r1
            goto L_0x00a0
        L_0x009a:
            aae r0 = defpackage.aaf.a
            aae$a r0 = r0.n()
        L_0x00a0:
            a = r0
        L_0x00a2:
            aae$a r0 = a
            if (r0 != 0) goto L_0x00a8
            r0 = r1
            goto L_0x00ae
        L_0x00a8:
            aae$a r0 = a
            java.util.Map r0 = r0.a()
        L_0x00ae:
            if (r0 == 0) goto L_0x00df
            java.util.Set r2 = r0.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x00b8:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00df
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto L_0x00b8
            boolean r4 = r5.equals(r3)
            if (r4 == 0) goto L_0x00b8
            java.lang.Object r3 = r0.get(r3)
            java.util.HashMap r3 = (java.util.HashMap) r3
            if (r3 == 0) goto L_0x00b8
            int r4 = r3.size()
            if (r4 <= 0) goto L_0x00b8
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>(r3)
        L_0x00df:
            java.lang.String r5 = "network.NetworkABTestTools"
            java.lang.String r0 = "getABTestParamMapByUrl result = "
            java.lang.String r2 = java.lang.String.valueOf(r1)
            java.lang.String r0 = r0.concat(r2)
            com.amap.bundle.logs.AMapLog.d(r5, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aag.a(java.lang.String):java.util.Map");
    }
}
