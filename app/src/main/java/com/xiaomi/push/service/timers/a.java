package com.xiaomi.push.service.timers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.channel.commonutils.logger.b;

public final class a {
    private static C0085a a;

    /* renamed from: com.xiaomi.push.service.timers.a$a reason: collision with other inner class name */
    interface C0085a {
        void a();

        void a(boolean z);

        boolean b();
    }

    public static synchronized void a() {
        synchronized (a.class) {
            if (a != null) {
                a.a();
            }
        }
    }

    public static void a(Context context) {
        b bVar;
        Context applicationContext = context.getApplicationContext();
        if ("com.xiaomi.xmsf".equals(applicationContext.getPackageName())) {
            bVar = new b(applicationContext);
        } else {
            boolean z = false;
            try {
                PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 4);
                if (packageInfo.services != null) {
                    ServiceInfo[] serviceInfoArr = packageInfo.services;
                    int length = serviceInfoArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        ServiceInfo serviceInfo = serviceInfoArr[i];
                        if ("com.xiaomi.push.service.XMJobService".equals(serviceInfo.name) && "android.permission.BIND_JOB_SERVICE".equals(serviceInfo.permission)) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("check service err : ");
                sb.append(e.getMessage());
                b.a(sb.toString());
            }
            if (z || !j.b(applicationContext)) {
                int i2 = VERSION.SDK_INT;
                bVar = new b(applicationContext);
            } else {
                throw new RuntimeException("Should export service: com.xiaomi.push.service.XMJobService with permission android.permission.BIND_JOB_SERVICE in AndroidManifest.xml file");
            }
        }
        a = bVar;
    }

    public static synchronized void a(boolean z) {
        synchronized (a.class) {
            if (a == null) {
                b.a((String) "timer is not initialized");
            } else {
                a.a(z);
            }
        }
    }

    public static synchronized boolean b() {
        synchronized (a.class) {
            try {
                if (a == null) {
                    return false;
                }
                boolean b = a.b();
                return b;
            }
        }
    }
}
