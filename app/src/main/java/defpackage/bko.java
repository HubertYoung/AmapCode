package defpackage;

/* renamed from: bko reason: default package */
/* compiled from: AMapLogAction */
public class bko extends vz {
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006e, code lost:
        if (r9.equals("fatal") != false) goto L_0x0090;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r8, defpackage.wa r9) throws org.json.JSONException {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            java.lang.String r9 = "level"
            java.lang.String r0 = ""
            java.lang.String r9 = r8.optString(r9, r0)
            java.lang.String r0 = "group"
            java.lang.String r1 = ""
            java.lang.String r0 = r8.optString(r0, r1)
            java.lang.String r1 = "tag"
            java.lang.String r2 = ""
            java.lang.String r1 = r8.optString(r1, r2)
            java.lang.String r2 = "msg"
            java.lang.String r3 = ""
            java.lang.String r2 = r8.optString(r2, r3)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            r4 = 0
            r5 = 1
            if (r3 != 0) goto L_0x0034
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r3 = 1
            goto L_0x0035
        L_0x0034:
            r3 = 0
        L_0x0035:
            if (r3 != 0) goto L_0x0055
            boolean r9 = defpackage.bno.a
            if (r9 == 0) goto L_0x0054
            java.lang.String r9 = "paas.webview"
            java.lang.String r0 = "AMapLogAction"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "invalid log:"
            r1.<init>(r2)
            java.lang.String r8 = r8.toString()
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            com.amap.bundle.logs.AMapLog.error(r9, r0, r8)
        L_0x0054:
            return
        L_0x0055:
            r3 = -1
            int r6 = r9.hashCode()
            switch(r6) {
                case 3237038: goto L_0x0085;
                case 95458899: goto L_0x007b;
                case 96784904: goto L_0x0071;
                case 97203460: goto L_0x0068;
                case 1124446108: goto L_0x005e;
                default: goto L_0x005d;
            }
        L_0x005d:
            goto L_0x008f
        L_0x005e:
            java.lang.String r4 = "warning"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x008f
            r4 = 2
            goto L_0x0090
        L_0x0068:
            java.lang.String r5 = "fatal"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x008f
            goto L_0x0090
        L_0x0071:
            java.lang.String r4 = "error"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x008f
            r4 = 1
            goto L_0x0090
        L_0x007b:
            java.lang.String r4 = "debug"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x008f
            r4 = 4
            goto L_0x0090
        L_0x0085:
            java.lang.String r4 = "info"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x008f
            r4 = 3
            goto L_0x0090
        L_0x008f:
            r4 = -1
        L_0x0090:
            switch(r4) {
                case 0: goto L_0x00c1;
                case 1: goto L_0x00bd;
                case 2: goto L_0x00b9;
                case 3: goto L_0x00b5;
                case 4: goto L_0x00b1;
                default: goto L_0x0093;
            }
        L_0x0093:
            boolean r9 = defpackage.bno.a
            if (r9 == 0) goto L_0x00c5
            java.lang.String r9 = "paas.webview"
            java.lang.String r0 = "AMapLogAction"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "invalid log level:"
            r1.<init>(r2)
            java.lang.String r8 = r8.toString()
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            com.amap.bundle.logs.AMapLog.error(r9, r0, r8)
            goto L_0x00c5
        L_0x00b1:
            com.amap.bundle.logs.AMapLog.debug(r0, r1, r2)
            return
        L_0x00b5:
            com.amap.bundle.logs.AMapLog.info(r0, r1, r2)
            return
        L_0x00b9:
            com.amap.bundle.logs.AMapLog.warning(r0, r1, r2)
            return
        L_0x00bd:
            com.amap.bundle.logs.AMapLog.error(r0, r1, r2)
            return
        L_0x00c1:
            com.amap.bundle.logs.AMapLog.fatal(r0, r1, r2)
            return
        L_0x00c5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bko.a(org.json.JSONObject, wa):void");
    }
}
