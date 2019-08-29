package com.xiaomi.push.service.timers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import com.xiaomi.channel.commonutils.reflect.a;

class b implements C0085a {
    private PendingIntent a = null;
    private Context b = null;
    private volatile long c = 0;

    public b(Context context) {
        this.b = context;
    }

    private void a(AlarmManager alarmManager, long j, PendingIntent pendingIntent) {
        try {
            AlarmManager.class.getMethod("setExact", new Class[]{Integer.TYPE, Long.TYPE, PendingIntent.class}).invoke(alarmManager, new Object[]{Integer.valueOf(0), Long.valueOf(j), pendingIntent});
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
        }
    }

    public void a() {
        if (this.a != null) {
            ((AlarmManager) this.b.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(this.a);
            this.a = null;
            com.xiaomi.channel.commonutils.logger.b.c("unregister timer");
        }
        this.c = 0;
    }

    public void a(Intent intent, long j) {
        AlarmManager alarmManager = (AlarmManager) this.b.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.a = PendingIntent.getBroadcast(this.b, 0, intent, 0);
        if (VERSION.SDK_INT >= 23) {
            a.a((Object) alarmManager, (String) "setExactAndAllowWhileIdle", Integer.valueOf(0), Long.valueOf(j), this.a);
        } else if (VERSION.SDK_INT >= 19) {
            a(alarmManager, j, this.a);
        } else {
            alarmManager.set(0, j, this.a);
        }
        com.xiaomi.channel.commonutils.logger.b.c("register timer ".concat(String.valueOf(j)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002b, code lost:
        if (r6.c < java.lang.System.currentTimeMillis()) goto L_0x0034;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r7) {
        /*
            r6 = this;
            int r0 = com.xiaomi.smack.g.c()
            long r0 = (long) r0
            r2 = 0
            if (r7 != 0) goto L_0x0010
            long r4 = r6.c
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x0010
            return
        L_0x0010:
            if (r7 == 0) goto L_0x0015
            r6.a()
        L_0x0015:
            if (r7 != 0) goto L_0x002e
            long r4 = r6.c
            int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r7 != 0) goto L_0x001e
            goto L_0x002e
        L_0x001e:
            long r2 = r6.c
            long r2 = r2 + r0
            r6.c = r2
            long r2 = r6.c
            long r4 = java.lang.System.currentTimeMillis()
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 >= 0) goto L_0x003b
            goto L_0x0034
        L_0x002e:
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r2 = r2 % r0
            long r0 = r0 - r2
        L_0x0034:
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 + r0
            r6.c = r2
        L_0x003b:
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r0 = com.xiaomi.push.service.at.o
            r7.<init>(r0)
            android.content.Context r0 = r6.b
            java.lang.String r0 = r0.getPackageName()
            r7.setPackage(r0)
            long r0 = r6.c
            r6.a(r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.timers.b.a(boolean):void");
    }

    public boolean b() {
        return this.c != 0;
    }
}
