package com.xiaomi.channel.commonutils.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class h {
    private static volatile h a;
    private ScheduledThreadPoolExecutor b = new ScheduledThreadPoolExecutor(1);
    /* access modifiers changed from: private */
    public SparseArray<ScheduledFuture> c = new SparseArray<>();
    /* access modifiers changed from: private */
    public Object d = new Object();
    /* access modifiers changed from: private */
    public SharedPreferences e;

    public static abstract class a implements Runnable {
        public abstract int a();
    }

    static class b implements Runnable {
        a c;

        public b(a aVar) {
            this.c = aVar;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
        }

        /* access modifiers changed from: 0000 */
        public void b() {
        }

        public void run() {
            a();
            this.c.run();
            b();
        }
    }

    private h(Context context) {
        this.e = context.getSharedPreferences("mipush_extra", 0);
    }

    public static h a(Context context) {
        if (a == null) {
            synchronized (h.class) {
                if (a == null) {
                    a = new h(context);
                }
            }
        }
        return a;
    }

    private ScheduledFuture a(a aVar) {
        ScheduledFuture scheduledFuture;
        synchronized (this.d) {
            scheduledFuture = this.c.get(aVar.a());
        }
        return scheduledFuture;
    }

    private static String b(int i) {
        return "last_job_time".concat(String.valueOf(i));
    }

    public void a(Runnable runnable) {
        a(runnable, 0);
    }

    public void a(Runnable runnable, int i) {
        this.b.schedule(runnable, (long) i, TimeUnit.SECONDS);
    }

    public boolean a(int i) {
        synchronized (this.d) {
            ScheduledFuture scheduledFuture = this.c.get(i);
            if (scheduledFuture == null) {
                return false;
            }
            this.c.remove(i);
            return scheduledFuture.cancel(false);
        }
    }

    public boolean a(a aVar, int i) {
        return a(aVar, i, 0);
    }

    public boolean a(a aVar, int i, int i2) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        String b2 = b(aVar.a());
        i iVar = new i(this, aVar, b2);
        long abs = Math.abs(System.currentTimeMillis() - this.e.getLong(b2, 0)) / 1000;
        if (abs < ((long) (i - i2))) {
            i2 = (int) (((long) i) - abs);
        }
        ScheduledFuture<?> scheduleAtFixedRate = this.b.scheduleAtFixedRate(iVar, (long) i2, (long) i, TimeUnit.SECONDS);
        synchronized (this.d) {
            this.c.put(aVar.a(), scheduleAtFixedRate);
        }
        return true;
    }

    public boolean b(a aVar, int i) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        ScheduledFuture<?> schedule = this.b.schedule(new j(this, aVar), (long) i, TimeUnit.SECONDS);
        synchronized (this.d) {
            this.c.put(aVar.a(), schedule);
        }
        return true;
    }
}
