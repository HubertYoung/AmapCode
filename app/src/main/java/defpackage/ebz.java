package defpackage;

/* renamed from: ebz reason: default package */
/* compiled from: FootArConfigHelper */
public final class ebz {
    private static boolean a = false;

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0045  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a() {
        /*
            lo r0 = defpackage.lo.a()
            java.lang.String r1 = "foot_ar_access_switch"
            java.lang.String r0 = r0.a(r1)
            java.lang.String r1 = "----footar---jsonStr:"
            java.lang.String r2 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r2)
            r2 = 0
            defpackage.eau.a(r2, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L_0x003d
            com.alibaba.fastjson.JSONObject r0 = com.alibaba.fastjson.JSON.parseObject(r0)
            if (r0 == 0) goto L_0x003d
            java.lang.String r1 = "footArSwitch"
            int r0 = r0.getIntValue(r1)
            java.lang.String r1 = "----footar---switchInt---:"
            java.lang.String r5 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r5)
            defpackage.eau.a(r2, r1)
            if (r4 != r0) goto L_0x003d
            r0 = 1
            goto L_0x0043
        L_0x003d:
            java.lang.String r0 = "----footar---switchInt---false"
            defpackage.eau.a(r2, r0)
            r0 = 0
        L_0x0043:
            if (r0 == 0) goto L_0x0081
            boolean r0 = a
            if (r0 != 0) goto L_0x0050
            java.lang.String r0 = "standardar"
            java.lang.System.loadLibrary(r0)
            a = r4
        L_0x0050:
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            if (r0 == 0) goto L_0x0081
            java.lang.String r1 = r0.getPackageName()
            java.lang.String r3 = "5637"
            java.lang.String r4 = android.os.Build.MODEL
            com.autonavi.jni.eyrie.amap.ar.ArManager.initARContext(r0, r1, r3, r4)
            boolean r1 = com.autonavi.jni.eyrie.amap.ar.ArManager.isSupportAr()
            java.lang.String r3 = "-----footAr---isDeviceSupportAr:"
            java.lang.String r4 = java.lang.String.valueOf(r1)
            java.lang.String r3 = r3.concat(r4)
            defpackage.eau.a(r2, r3)
            if (r1 == 0) goto L_0x0080
            java.util.concurrent.ScheduledThreadPoolExecutor r2 = org.android.agoo.common.ThreadUtil.a()
            ebz$1 r3 = new ebz$1
            r3.<init>(r0)
            r2.execute(r3)
        L_0x0080:
            return r1
        L_0x0081:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebz.a():boolean");
    }
}
