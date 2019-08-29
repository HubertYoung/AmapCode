package defpackage;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: fgg reason: default package */
/* compiled from: CookieManager */
public final class fgg {
    public static CookieManager a = null;
    private static volatile boolean b = false;

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r2) {
        /*
            java.lang.Class<fgg> r0 = defpackage.fgg.class
            monitor-enter(r0)
            boolean r1 = b     // Catch:{ all -> 0x0022 }
            if (r1 != 0) goto L_0x0020
            if (r2 != 0) goto L_0x000a
            goto L_0x0020
        L_0x000a:
            android.webkit.CookieSyncManager.createInstance(r2)     // Catch:{ all -> 0x0022 }
            android.webkit.CookieManager r2 = android.webkit.CookieManager.getInstance()     // Catch:{ all -> 0x0022 }
            a = r2     // Catch:{ all -> 0x0022 }
            r1 = 1
            r2.setAcceptCookie(r1)     // Catch:{ all -> 0x0022 }
            android.webkit.CookieManager r2 = a     // Catch:{ all -> 0x0022 }
            r2.removeExpiredCookie()     // Catch:{ all -> 0x0022 }
            b = r1     // Catch:{ all -> 0x0022 }
            monitor-exit(r0)
            return
        L_0x0020:
            monitor-exit(r0)
            return
        L_0x0022:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgg.a(android.content.Context):void");
    }

    public static synchronized void a(String str, String str2) {
        synchronized (fgg.class) {
            if (b) {
                try {
                    a.setCookie(str, str2);
                    CookieSyncManager.getInstance().sync();
                } catch (Throwable th) {
                    StringBuilder sb = new StringBuilder("set cookie failed. url=");
                    sb.append(str);
                    sb.append(" cookies=");
                    sb.append(str2);
                    TBSdkLog.b((String) "mtopsdk.CookieManager", sb.toString(), th);
                }
            }
        }
    }

    public static synchronized String a(String str) {
        String str2;
        synchronized (fgg.class) {
            if (!b) {
                return null;
            }
            try {
                str2 = a.getCookie(str);
            } catch (Throwable th) {
                TBSdkLog.b((String) "mtopsdk.CookieManager", "get cookie failed. url=".concat(String.valueOf(str)), th);
                str2 = null;
            }
        }
        return str2;
    }
}
