package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tools.AmapBatteryManager$1;
import com.amap.bundle.tools.AmapBatteryManager$2;
import com.amap.bundle.tools.AmapBatteryManager$3;
import com.amap.bundle.tools.AmapBatteryManager$4;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: afq reason: default package */
/* compiled from: AmapBatteryManager */
public class afq {
    private static volatile afq e;
    private static BroadcastReceiver k = new AmapBatteryManager$1();
    public Context a;
    public final CopyOnWriteArrayList<b> b = new CopyOnWriteArrayList<>();
    public BroadcastReceiver c = new AmapBatteryManager$2(this);
    private final String d = "AmapBatteryManager";
    /* access modifiers changed from: private */
    public Intent f = null;
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock g = new ReentrantReadWriteLock();
    private BatteryManager h;
    private final CopyOnWriteArrayList<Object> i = new CopyOnWriteArrayList<>();
    /* access modifiers changed from: private */
    public final CopyOnWriteArrayList<a> j = new CopyOnWriteArrayList<>();
    private BroadcastReceiver l = new AmapBatteryManager$3(this);
    private BroadcastReceiver m = new AmapBatteryManager$4(this);

    /* renamed from: afq$a */
    /* compiled from: AmapBatteryManager */
    public interface a {
        void a();
    }

    /* renamed from: afq$b */
    /* compiled from: AmapBatteryManager */
    public interface b {
        void a(boolean z);
    }

    public static afq a(@NonNull Context context) {
        if (e == null) {
            synchronized (afq.class) {
                try {
                    if (e == null) {
                        e = new afq(context.getApplicationContext());
                    }
                }
            }
        }
        return e;
    }

    private afq(Context context) {
        this.a = context;
        if (VERSION.SDK_INT >= 21) {
            this.h = (BatteryManager) context.getSystemService("batterymanager");
        } else {
            AMapLog.warning("paas.tools", "AmapBatteryManager", "此接口需要在API Level 21及以上环境下调用");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0032, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r3 = new java.lang.StringBuilder("isCharging():");
        r3.append(android.util.Log.getStackTraceString(r0));
        com.amap.bundle.logs.AMapLog.warning("paas.tools", "AmapBatteryManager", r3.toString());
     */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0032 A[ExcHandler: AssertionError | Exception (r0v0 'e' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a() {
        /*
            r9 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r9.g     // Catch:{ AssertionError | Exception -> 0x0032 }
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()     // Catch:{ AssertionError | Exception -> 0x0032 }
            r0.lock()     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.Intent r0 = r9.f     // Catch:{ AssertionError | Exception -> 0x0032 }
            if (r0 != 0) goto L_0x0025
            android.content.Context r0 = r9.a     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.BroadcastReceiver r1 = k     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.IntentFilter r2 = new android.content.IntentFilter     // Catch:{ AssertionError | Exception -> 0x0032 }
            java.lang.String r3 = "android.intent.action.BATTERY_CHANGED"
            r2.<init>(r3)     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.Intent r0 = r0.registerReceiver(r1, r2)     // Catch:{ AssertionError | Exception -> 0x0032 }
            r9.f = r0     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.Context r0 = r9.a     // Catch:{ AssertionError | Exception -> 0x0032 }
            android.content.BroadcastReceiver r1 = k     // Catch:{ AssertionError | Exception -> 0x0032 }
            r0.unregisterReceiver(r1)     // Catch:{ AssertionError | Exception -> 0x0032 }
        L_0x0025:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.unlock()
            goto L_0x004d
        L_0x002f:
            r0 = move-exception
            goto L_0x00d6
        L_0x0032:
            r0 = move-exception
            java.lang.String r1 = "paas.tools"
            java.lang.String r2 = "AmapBatteryManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x002f }
            java.lang.String r4 = "isCharging():"
            r3.<init>(r4)     // Catch:{ all -> 0x002f }
            java.lang.String r0 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x002f }
            r3.append(r0)     // Catch:{ all -> 0x002f }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x002f }
            com.amap.bundle.logs.AMapLog.warning(r1, r2, r0)     // Catch:{ all -> 0x002f }
            goto L_0x0025
        L_0x004d:
            r0 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g     // Catch:{ Exception -> 0x00b2 }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()     // Catch:{ Exception -> 0x00b2 }
            r1.lock()     // Catch:{ Exception -> 0x00b2 }
            android.content.Intent r1 = r9.f     // Catch:{ Exception -> 0x00b2 }
            if (r1 != 0) goto L_0x0065
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.unlock()
            return r0
        L_0x0065:
            android.content.Intent r1 = r9.f     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r2 = "plugged"
            r3 = -1
            int r1 = r1.getIntExtra(r2, r3)     // Catch:{ Exception -> 0x00b2 }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00b2 }
            r3 = 17
            r4 = 1
            if (r2 < r3) goto L_0x007a
            r2 = 4
            if (r1 != r2) goto L_0x007a
            r2 = 1
            goto L_0x007b
        L_0x007a:
            r2 = 0
        L_0x007b:
            boolean r3 = defpackage.bno.a     // Catch:{ Exception -> 0x00b2 }
            r5 = 2
            if (r3 == 0) goto L_0x009f
            java.lang.String r3 = "paas.tools"
            java.lang.String r6 = "AmapBatteryManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r8 = "isCharge:"
            r7.<init>(r8)     // Catch:{ Exception -> 0x00b2 }
            if (r1 == r4) goto L_0x0094
            if (r1 == r5) goto L_0x0094
            if (r2 == 0) goto L_0x0092
            goto L_0x0094
        L_0x0092:
            r8 = 0
            goto L_0x0095
        L_0x0094:
            r8 = 1
        L_0x0095:
            r7.append(r8)     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00b2 }
            com.amap.bundle.logs.AMapLog.debug(r3, r6, r7)     // Catch:{ Exception -> 0x00b2 }
        L_0x009f:
            if (r1 == r4) goto L_0x00a5
            if (r1 == r5) goto L_0x00a5
            if (r2 == 0) goto L_0x00a6
        L_0x00a5:
            r0 = 1
        L_0x00a6:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.unlock()
            return r0
        L_0x00b0:
            r0 = move-exception
            goto L_0x00cc
        L_0x00b2:
            r1 = move-exception
            boolean r2 = defpackage.bno.a     // Catch:{ all -> 0x00b0 }
            if (r2 == 0) goto L_0x00c2
            java.lang.String r2 = "paas.tools"
            java.lang.String r3 = "AmapBatteryManager"
            java.lang.String r1 = android.util.Log.getStackTraceString(r1)     // Catch:{ all -> 0x00b0 }
            com.amap.bundle.logs.AMapLog.debug(r2, r3, r1)     // Catch:{ all -> 0x00b0 }
        L_0x00c2:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.unlock()
            return r0
        L_0x00cc:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r1.readLock()
            r1.unlock()
            throw r0
        L_0x00d6:
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = r9.g
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.afq.a():boolean");
    }

    public final float b() {
        float longProperty = (this.h == null || VERSION.SDK_INT < 21) ? -1.0f : ((float) this.h.getLongProperty(4)) / 100.0f;
        if (longProperty < 0.0f || longProperty > 1.0f) {
            try {
                Intent registerReceiver = this.a.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                if (registerReceiver != null) {
                    longProperty = ((float) registerReceiver.getIntExtra(H5PermissionManager.level, 0)) / (((float) registerReceiver.getIntExtra(WidgetType.SCALE, 1)) * 1.0f);
                    if (bno.a) {
                        AMapLog.debug("paas.tools", "AmapBatteryManager", "getScale():".concat(String.valueOf(longProperty)));
                    }
                }
            } catch (AssertionError | Exception e2) {
                StringBuilder sb = new StringBuilder("getScale()-");
                sb.append(Log.getStackTraceString(e2));
                AMapLog.debug("paas.tools", "AmapBatteryManager", sb.toString());
            }
        }
        return longProperty;
    }

    public final void a(b bVar) {
        synchronized (this.b) {
            if (this.b.isEmpty()) {
                this.b.add(bVar);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                try {
                    this.a.registerReceiver(this.c, intentFilter);
                } catch (AssertionError | Exception e2) {
                    AMapLog.warning("paas.tools", "AmapBatteryManager", Log.getStackTraceString(e2));
                }
            } else if (!this.b.contains(bVar)) {
                this.b.add(bVar);
            }
        }
    }

    public final void a(a aVar) {
        if (aVar != null) {
            synchronized (this.j) {
                if (this.j.isEmpty()) {
                    this.j.add(aVar);
                    try {
                        this.a.registerReceiver(this.m, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                    } catch (AssertionError | Exception e2) {
                        AMapLog.warning("paas.tools", "AmapBatteryManager", Log.getStackTraceString(e2));
                    }
                } else if (!this.j.contains(aVar)) {
                    this.j.add(aVar);
                }
            }
        }
    }

    public final void b(a aVar) {
        if (aVar != null) {
            synchronized (this.j) {
                if (this.j.contains(aVar)) {
                    this.j.remove(aVar);
                }
                if (this.j.isEmpty()) {
                    try {
                        this.a.unregisterReceiver(this.m);
                    } catch (Exception e2) {
                        AMapLog.warning("paas.tools", "AmapBatteryManager", Log.getStackTraceString(e2));
                    }
                }
            }
        }
    }

    public static /* synthetic */ void a(afq afq, boolean z) {
        synchronized (afq.b) {
            Iterator<b> it = afq.b.iterator();
            while (it.hasNext()) {
                it.next().a(z);
            }
        }
    }

    public static /* synthetic */ void c(afq afq) {
        synchronized (afq.i) {
            Iterator<Object> it = afq.i.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }
}
