package defpackage;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.List;

/* renamed from: yr reason: default package */
/* compiled from: AccsConfig */
public final class yr {
    private static final String[] a = {"m5.amap.com", "sns.amap.com", "poi.amap.com", "mps.amap.com", "oss.amap.com", "indoor.amap.com", "passport.amap.com", "wb.amap.com", "sync.amap.com", "comment.amap.com", "cmg-ws-mit.amap.com"};
    private static final String[] b = {"indooreditor.amap.com", "maps.testing.amap.com", "sns.testing.amap.com", "poi.testing.amap.com", "oss.testing.amap.com", "passport.testing.amap.com", "wb.testing.amap.com", "sync.testing.amap.com", "comment.testing.amap.com", "pre-cmg-ws.amap.com"};
    private static final String[] c = {"ws/shield/frogserver/rd/displist", "ws/shield/frogserver/rd/feedback", "ws/shield/frogserver/aocs/updatable/1"};
    private static final boolean d = ahs.a(aaf.a());
    private static b e;

    public static boolean a() {
        b c2 = aaf.c();
        if (c2 == null) {
            return false;
        }
        return c2.c();
    }

    public static final String[] b() {
        String str;
        b c2 = aaf.c();
        if (c2 == null) {
            str = "test";
        } else {
            str = c2.b();
        }
        if (str.equalsIgnoreCase("test")) {
            return b;
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r8) {
        /*
            boolean r0 = d
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000c
            boolean r0 = defpackage.yv.a
            if (r0 == 0) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            if (r0 == 0) goto L_0x0020
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r3 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r3)
            java.lang.String r3 = "ACCS_SWITCH"
            boolean r0 = r0.getBooleanValue(r3, r1)
            if (r0 == 0) goto L_0x0020
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            r3 = 3
            if (r0 == 0) goto L_0x00a9
            if (r8 != 0) goto L_0x0029
        L_0x0026:
            r0 = 0
            goto L_0x00a5
        L_0x0029:
            java.lang.String[] r0 = c
            int r4 = r0.length
            r5 = 0
        L_0x002d:
            if (r5 >= r4) goto L_0x0050
            r6 = r0[r5]
            boolean r6 = r8.contains(r6)
            if (r6 == 0) goto L_0x004d
            boolean r0 = defpackage.bpv.a(r3)
            if (r0 == 0) goto L_0x0026
            java.lang.String r0 = "Network-AccsConfig"
            java.lang.String r4 = "hit local black list, url: "
            java.lang.String r5 = java.lang.String.valueOf(r8)
            java.lang.String r4 = r4.concat(r5)
            defpackage.bpv.b(r0, r4)
            goto L_0x0026
        L_0x004d:
            int r5 = r5 + 1
            goto L_0x002d
        L_0x0050:
            java.util.List r0 = c()
            java.lang.String r4 = "://"
            java.lang.String[] r4 = r8.split(r4)
            if (r4 == 0) goto L_0x0086
            int r5 = r4.length
            if (r5 <= r1) goto L_0x0086
            r4 = r4[r1]
            java.lang.String r5 = "\\?"
            java.lang.String[] r4 = r4.split(r5)
            if (r4 == 0) goto L_0x0086
            int r5 = r4.length
            if (r5 <= 0) goto L_0x0086
            r5 = 0
        L_0x006d:
            int r6 = r0.size()
            if (r5 >= r6) goto L_0x0086
            java.lang.Object r6 = r0.get(r5)
            java.lang.String r6 = (java.lang.String) r6
            r7 = r4[r2]
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L_0x0083
            r0 = 1
            goto L_0x0087
        L_0x0083:
            int r5 = r5 + 1
            goto L_0x006d
        L_0x0086:
            r0 = 0
        L_0x0087:
            if (r0 != 0) goto L_0x0026
            java.util.List r0 = d()
            r4 = 0
        L_0x008e:
            int r5 = r0.size()
            if (r4 >= r5) goto L_0x0026
            java.lang.Object r5 = r0.get(r4)
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = r8.contains(r5)
            if (r5 == 0) goto L_0x00a2
            r0 = 1
            goto L_0x00a5
        L_0x00a2:
            int r4 = r4 + 1
            goto L_0x008e
        L_0x00a5:
            if (r0 == 0) goto L_0x00a9
            r0 = 1
            goto L_0x00aa
        L_0x00a9:
            r0 = 0
        L_0x00aa:
            boolean r4 = defpackage.bno.a
            if (r4 == 0) goto L_0x00ca
            if (r0 == 0) goto L_0x00c9
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            android.content.SharedPreferences r0 = r0.sharedPrefs()
            java.lang.String r4 = "accs_switch_off"
            boolean r0 = r0.getBoolean(r4, r2)
            if (r0 != 0) goto L_0x00c9
            r0 = 1
            goto L_0x00ca
        L_0x00c9:
            r0 = 0
        L_0x00ca:
            boolean r1 = defpackage.bno.a
            if (r1 == 0) goto L_0x00f3
            boolean r1 = defpackage.bpv.a(r3)
            if (r1 == 0) goto L_0x00f3
            java.lang.String r1 = "Network-AccsConfig"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "shouldRequestByAccs: "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r3 = ", url:"
            r2.append(r3)
            if (r8 != 0) goto L_0x00e9
            java.lang.String r8 = ""
        L_0x00e9:
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            defpackage.bpv.b(r1, r8)
        L_0x00f3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yr.a(java.lang.String):boolean");
    }

    public static void b(String str) {
        for (String a2 : b()) {
            dt.a(a2, str);
        }
    }

    @NonNull
    private static synchronized List<String> c() {
        synchronized (yr.class) {
            b e2 = e();
            List<String> d2 = e2 == null ? Collections.EMPTY_LIST : e2.d();
            if (d2 != null) {
                return d2;
            }
            List<String> list = Collections.EMPTY_LIST;
            return list;
        }
    }

    @NonNull
    private static synchronized List<String> d() {
        synchronized (yr.class) {
            b e2 = e();
            List<String> e3 = e2 == null ? Collections.EMPTY_LIST : e2.e();
            if (e3 != null) {
                return e3;
            }
            List<String> list = Collections.EMPTY_LIST;
            return list;
        }
    }

    private static b e() {
        if (e == null) {
            e = aaf.c();
        }
        return e;
    }
}
