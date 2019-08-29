package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.offline.preference.OfflinePreference;

/* renamed from: aey reason: default package */
/* compiled from: OfflineFirstUtils */
public final class aey {
    private static boolean a = false;

    public static void a(boolean z) {
        a = z;
    }

    public static boolean a() {
        return a;
    }

    public static boolean b() {
        if (a) {
            return false;
        }
        return c();
    }

    public static boolean a(int i) {
        if (a) {
            return false;
        }
        return b(i);
    }

    public static boolean c() {
        Context context = DoNotUseTool.getContext();
        bty mapView = DoNotUseTool.getMapManager() != null ? DoNotUseTool.getMapManager().getMapView() : null;
        if (context == null || mapView == null) {
            return false;
        }
        return b((int) li.a().a(mapView.p(), mapView.q()));
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0068 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(int r7) {
        /*
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getContext()
            com.autonavi.map.core.MapManager r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            if (r1 == 0) goto L_0x0013
            com.autonavi.map.core.MapManager r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            bty r1 = r1.getMapView()
            goto L_0x0014
        L_0x0013:
            r1 = 0
        L_0x0014:
            r2 = 0
            if (r0 == 0) goto L_0x006d
            if (r1 != 0) goto L_0x001a
            goto L_0x006d
        L_0x001a:
            int r3 = defpackage.aaw.b(r0)
            r4 = 4
            if (r3 != r4) goto L_0x0022
            return r2
        L_0x0022:
            boolean r3 = e()
            if (r3 != 0) goto L_0x0029
            return r2
        L_0x0029:
            java.lang.Class<com.autonavi.minimap.offline.map.inter.IOfflineManager> r3 = com.autonavi.minimap.offline.map.inter.IOfflineManager.class
            java.lang.Object r3 = defpackage.ank.a(r3)
            com.autonavi.minimap.offline.map.inter.IOfflineManager r3 = (com.autonavi.minimap.offline.map.inter.IOfflineManager) r3
            if (r3 == 0) goto L_0x0061
            boolean r4 = r3.isOfflineDataReady()     // Catch:{ Exception -> 0x005a }
            if (r4 != 0) goto L_0x003a
            return r2
        L_0x003a:
            boolean r4 = r3.checkCityDownload(r7)     // Catch:{ Exception -> 0x005a }
            if (r4 != 0) goto L_0x005f
            li r5 = defpackage.li.a()     // Catch:{ Exception -> 0x0058 }
            int r6 = r1.p()     // Catch:{ Exception -> 0x0058 }
            int r1 = r1.q()     // Catch:{ Exception -> 0x0058 }
            long r5 = r5.a(r6, r1)     // Catch:{ Exception -> 0x0058 }
            int r1 = (int) r5     // Catch:{ Exception -> 0x0058 }
            if (r1 == r7) goto L_0x005f
            boolean r7 = r3.checkCityDownload(r1)     // Catch:{ Exception -> 0x0058 }
            goto L_0x0062
        L_0x0058:
            r7 = move-exception
            goto L_0x005c
        L_0x005a:
            r7 = move-exception
            r4 = 0
        L_0x005c:
            r7.printStackTrace()
        L_0x005f:
            r7 = r4
            goto L_0x0062
        L_0x0061:
            r7 = 0
        L_0x0062:
            boolean r0 = defpackage.aaw.c(r0)
            if (r7 == 0) goto L_0x006c
            if (r0 == 0) goto L_0x006c
            r7 = 1
            return r7
        L_0x006c:
            return r2
        L_0x006d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aey.b(int):boolean");
    }

    private static boolean e() {
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED, false);
        boolean booleanValue2 = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED_OLD, false);
        if (booleanValue || booleanValue2) {
            return true;
        }
        return false;
    }

    public static boolean d() {
        Context context = DoNotUseTool.getContext();
        if (context == null || aaw.b(context) == 4) {
            return false;
        }
        boolean e = e();
        boolean c = aaw.c(context);
        if (!e || !c) {
            return false;
        }
        return true;
    }
}
