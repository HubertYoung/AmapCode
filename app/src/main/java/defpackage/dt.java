package defpackage;

import android.webkit.CookieManager;
import anetwork.channel.http.NetworkSdkSetting;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: dt reason: default package */
/* compiled from: CookieManager */
public final class dt {
    private static volatile boolean a = false;
    private static CookieManager b = null;
    private static boolean c = true;

    /* JADX WARNING: Can't wrap try/catch for region: R(2:16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        c = false;
        defpackage.cl.e("anet.CookieManager", "Cookie Manager setup failed!!!", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0047 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r10) {
        /*
            java.lang.Class<dt> r0 = defpackage.dt.class
            monitor-enter(r0)
            boolean r1 = a     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            r1 = 0
            r2 = 1
            r3 = 0
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0047 }
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0047 }
            r7 = 21
            if (r6 >= r7) goto L_0x0019
            android.webkit.CookieSyncManager.createInstance(r10)     // Catch:{ Throwable -> 0x0047 }
        L_0x0019:
            android.webkit.CookieManager r10 = android.webkit.CookieManager.getInstance()     // Catch:{ Throwable -> 0x0047 }
            b = r10     // Catch:{ Throwable -> 0x0047 }
            r10.setAcceptCookie(r2)     // Catch:{ Throwable -> 0x0047 }
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0047 }
            if (r10 >= r7) goto L_0x002b
            android.webkit.CookieManager r10 = b     // Catch:{ Throwable -> 0x0047 }
            r10.removeExpiredCookie()     // Catch:{ Throwable -> 0x0047 }
        L_0x002b:
            java.lang.String r10 = "anet.CookieManager"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0047 }
            java.lang.String r7 = "CookieManager setup. cost:"
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0047 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0047 }
            r9 = 0
            long r7 = r7 - r4
            r6.append(r7)     // Catch:{ Throwable -> 0x0047 }
            java.lang.String r4 = r6.toString()     // Catch:{ Throwable -> 0x0047 }
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0047 }
            defpackage.cl.d(r10, r4, r1, r5)     // Catch:{ Throwable -> 0x0047 }
            goto L_0x0052
        L_0x0047:
            c = r3     // Catch:{ all -> 0x0056 }
            java.lang.String r10 = "anet.CookieManager"
            java.lang.String r4 = "Cookie Manager setup failed!!!"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0056 }
            defpackage.cl.e(r10, r4, r1, r3)     // Catch:{ all -> 0x0056 }
        L_0x0052:
            a = r2     // Catch:{ all -> 0x0056 }
            monitor-exit(r0)
            return
        L_0x0056:
            r10 = move-exception
            monitor-exit(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dt.a(android.content.Context):void");
    }

    private static boolean a() {
        if (!a && NetworkSdkSetting.getContext() != null) {
            a(NetworkSdkSetting.getContext());
        }
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<dt> r0 = defpackage.dt.class
            monitor-enter(r0)
            boolean r1 = a()     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x004a
            boolean r1 = c     // Catch:{ all -> 0x004c }
            if (r1 != 0) goto L_0x000e
            goto L_0x004a
        L_0x000e:
            android.webkit.CookieManager r1 = b     // Catch:{ Throwable -> 0x0029 }
            r1.setCookie(r4, r5)     // Catch:{ Throwable -> 0x0029 }
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0029 }
            r2 = 21
            if (r1 >= r2) goto L_0x0022
            android.webkit.CookieSyncManager r1 = android.webkit.CookieSyncManager.getInstance()     // Catch:{ Throwable -> 0x0029 }
            r1.sync()     // Catch:{ Throwable -> 0x0029 }
            monitor-exit(r0)
            return
        L_0x0022:
            android.webkit.CookieManager r1 = b     // Catch:{ Throwable -> 0x0029 }
            r1.flush()     // Catch:{ Throwable -> 0x0029 }
            monitor-exit(r0)
            return
        L_0x0029:
            java.lang.String r1 = "anet.CookieManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x004c }
            java.lang.String r3 = "set cookie failed. url="
            r2.<init>(r3)     // Catch:{ all -> 0x004c }
            r2.append(r4)     // Catch:{ all -> 0x004c }
            java.lang.String r4 = " cookies="
            r2.append(r4)     // Catch:{ all -> 0x004c }
            r2.append(r5)     // Catch:{ all -> 0x004c }
            java.lang.String r4 = r2.toString()     // Catch:{ all -> 0x004c }
            r5 = 0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x004c }
            defpackage.cl.e(r1, r4, r5, r2)     // Catch:{ all -> 0x004c }
            monitor-exit(r0)
            return
        L_0x004a:
            monitor-exit(r0)
            return
        L_0x004c:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dt.a(java.lang.String, java.lang.String):void");
    }

    public static void a(String str, Map<String, List<String>> map) {
        if (str != null && map != null) {
            try {
                for (Entry next : map.entrySet()) {
                    String str2 = (String) next.getKey();
                    if (str2 != null && (str2.equalsIgnoreCase("Set-Cookie") || str2.equalsIgnoreCase("Set-Cookie2"))) {
                        for (String a2 : (List) next.getValue()) {
                            a(str, a2);
                        }
                    }
                }
            } catch (Exception unused) {
                cl.e("anet.CookieManager", "set cookie failed", null, "url", str, "\nheaders", map);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String a(java.lang.String r4) {
        /*
            java.lang.Class<dt> r0 = defpackage.dt.class
            monitor-enter(r0)
            boolean r1 = a()     // Catch:{ all -> 0x002d }
            r2 = 0
            if (r1 == 0) goto L_0x002b
            boolean r1 = c     // Catch:{ all -> 0x002d }
            if (r1 != 0) goto L_0x000f
            goto L_0x002b
        L_0x000f:
            android.webkit.CookieManager r1 = b     // Catch:{ Throwable -> 0x0016 }
            java.lang.String r1 = r1.getCookie(r4)     // Catch:{ Throwable -> 0x0016 }
            goto L_0x0029
        L_0x0016:
            java.lang.String r1 = "anet.CookieManager"
            java.lang.String r3 = "get cookie failed. url="
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x002d }
            java.lang.String r4 = r3.concat(r4)     // Catch:{ all -> 0x002d }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x002d }
            defpackage.cl.e(r1, r4, r2, r3)     // Catch:{ all -> 0x002d }
            r1 = r2
        L_0x0029:
            monitor-exit(r0)
            return r1
        L_0x002b:
            monitor-exit(r0)
            return r2
        L_0x002d:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dt.a(java.lang.String):java.lang.String");
    }
}
