package defpackage;

import android.location.LocationManager;
import android.net.wifi.WifiManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

/* renamed from: cei reason: default package */
/* compiled from: LocationAccuracyCheck */
public final class cei {
    public static boolean a = false;
    private ceh b = null;

    public static boolean a() {
        try {
            return ((WifiManager) AMapAppGlobal.getApplication().getApplicationContext().getSystemService("wifi")).isWifiEnabled();
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean b() {
        try {
            return ((LocationManager) AMapAppGlobal.getApplication().getSystemService("location")).isProviderEnabled(WidgetType.GPS);
        } catch (SecurityException unused) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.app.Activity r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = a()     // Catch:{ all -> 0x002d }
            boolean r1 = b()     // Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x000f
            if (r1 == 0) goto L_0x000f
            monitor-exit(r2)
            return
        L_0x000f:
            ceh r0 = r2.b     // Catch:{ all -> 0x002d }
            if (r0 != 0) goto L_0x001d
            java.lang.Class<ceh> r0 = defpackage.ceh.class
            java.lang.Object r0 = defpackage.ank.a(r0)     // Catch:{ all -> 0x002d }
            ceh r0 = (defpackage.ceh) r0     // Catch:{ all -> 0x002d }
            r2.b = r0     // Catch:{ all -> 0x002d }
        L_0x001d:
            ceh r0 = r2.b     // Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x002b
            ceh r0 = r2.b     // Catch:{ all -> 0x002d }
            r0.a(r3)     // Catch:{ all -> 0x002d }
            ceh r3 = r2.b     // Catch:{ all -> 0x002d }
            r3.a()     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r2)
            return
        L_0x002d:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cei.a(android.app.Activity):void");
    }
}
