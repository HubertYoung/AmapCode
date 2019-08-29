package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.autonavi.common.model.POI;

/* renamed from: eko reason: default package */
/* compiled from: VoiceRouteUtils */
public final class eko {
    public static boolean a(double d, double d2) {
        return -180.0d <= d && d <= 180.0d && -90.0d <= d2 && d2 <= 90.0d;
    }

    public static boolean b(double d, double d2) {
        return d == -1000.0d || d2 == -1000.0d;
    }

    public static POI a() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.c();
            }
        }
        return null;
    }

    public static POI b() {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                return b.d();
            }
        }
        return null;
    }

    public static boolean a(String str) {
        int i;
        if (!TextUtils.isEmpty(str)) {
            i = Integer.parseInt(str, 10);
        } else {
            i = -3;
        }
        switch (i) {
            case -2:
            case 6:
            case 407:
            case 408:
                a(10008);
                return true;
            case -1:
                a(10008);
                return true;
            case 43:
            case 101:
                a(20042);
                return true;
            case 10000:
                a(10003);
                return true;
            case 20000:
                a(10000);
                return true;
            default:
                return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        a(20040);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0030, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(java.lang.String r3) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 == 0) goto L_0x0008
            r3 = -1
            goto L_0x000e
        L_0x0008:
            r0 = 10
            int r3 = java.lang.Integer.parseInt(r3, r0)
        L_0x000e:
            r0 = 201(0xc9, float:2.82E-43)
            r1 = 20041(0x4e49, float:2.8083E-41)
            r2 = 1
            if (r3 == r0) goto L_0x0035
            r0 = 20040(0x4e48, float:2.8082E-41)
            switch(r3) {
                case 1: goto L_0x0031;
                case 2: goto L_0x0035;
                case 3: goto L_0x002d;
                case 4: goto L_0x002d;
                case 5: goto L_0x002d;
                default: goto L_0x001a;
            }
        L_0x001a:
            switch(r3) {
                case 7: goto L_0x002d;
                case 8: goto L_0x002d;
                case 9: goto L_0x002d;
                default: goto L_0x001d;
            }
        L_0x001d:
            switch(r3) {
                case 41: goto L_0x0029;
                case 42: goto L_0x0029;
                default: goto L_0x0020;
            }
        L_0x0020:
            switch(r3) {
                case 44: goto L_0x0029;
                case 45: goto L_0x0029;
                case 46: goto L_0x0029;
                case 47: goto L_0x0029;
                case 48: goto L_0x0029;
                case 49: goto L_0x0029;
                default: goto L_0x0023;
            }
        L_0x0023:
            r3 = 10020(0x2724, float:1.4041E-41)
            a(r3)
            return r2
        L_0x0029:
            a(r1)
            return r2
        L_0x002d:
            a(r0)
            return r2
        L_0x0031:
            a(r0)
            return r2
        L_0x0035:
            a(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eko.b(java.lang.String):boolean");
    }

    public static void c(String str) {
        edr.a("bike".equals(str) ^ true ? 1 : 0);
    }

    public static void a(Intent intent) {
        if (intent != null) {
            esf.a().a(new ese(intent));
        }
    }

    public static Intent d(String str) {
        return new Intent("android.intent.action.VIEW", Uri.parse(str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(int r2) {
        /*
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            if (r0 == 0) goto L_0x001d
            java.lang.String r1 = "connectivity"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            if (r0 != 0) goto L_0x0012
            r0 = 0
            goto L_0x0016
        L_0x0012:
            android.net.NetworkInfo r0 = r0.getActiveNetworkInfo()
        L_0x0016:
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.isConnected()
            goto L_0x001e
        L_0x001d:
            r0 = 0
        L_0x001e:
            if (r0 != 0) goto L_0x0025
            r1 = 10008(0x2718, float:1.4024E-41)
            a(r2, r1)
        L_0x0025:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eko.b(int):boolean");
    }

    public static void a(int i, int i2) {
        if (i != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i, i2, (Pair<String, Object>) null);
            }
        }
    }

    public static void a(int i) {
        ekn ekn = a.a.a;
        if (ekn != null) {
            ekn.a(i);
        }
    }
}
