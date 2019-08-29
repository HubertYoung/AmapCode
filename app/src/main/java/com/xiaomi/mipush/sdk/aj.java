package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.channel.commonutils.string.c;
import com.xiaomi.push.service.an;
import com.xiaomi.push.service.at;
import com.xiaomi.push.service.av;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.g;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class aj {
    private static aj b = null;
    private static boolean f = false;
    private static final ArrayList<a> g = new ArrayList<>();
    private boolean a = false;
    /* access modifiers changed from: private */
    public Context c;
    private String d;
    /* access modifiers changed from: private */
    public Messenger e;
    private Handler h = null;
    /* access modifiers changed from: private */
    public List<Message> i = new ArrayList();
    /* access modifiers changed from: private */
    public boolean j = false;
    private Intent k = null;
    /* access modifiers changed from: private */
    public Integer l = null;

    static class a<T extends org.apache.thrift.a<T, ?>> {
        T a;
        com.xiaomi.xmpush.thrift.a b;
        boolean c;

        a() {
        }
    }

    private aj(Context context) {
        this.c = context.getApplicationContext();
        this.d = null;
        this.a = i();
        f = r();
        this.h = new ak(this, Looper.getMainLooper());
        Intent k2 = k();
        if (k2 != null) {
            a(k2);
        }
    }

    public static synchronized aj a(Context context) {
        aj ajVar;
        synchronized (aj.class) {
            try {
                if (b == null) {
                    b = new aj(context);
                }
                ajVar = b;
            }
        }
        return ajVar;
    }

    private void a(Intent intent) {
        try {
            this.c.startService(intent);
        } catch (Exception e2) {
            b.a((Throwable) e2);
        }
    }

    /* JADX INFO: used method not loaded: com.xiaomi.xmpush.thrift.ai.b(java.lang.String):null, types can be incorrect */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a1, code lost:
        r2.setAction(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a4, code lost:
        r0.b(com.xiaomi.mipush.sdk.c.a(r11.c).c());
        r0.d(r11.c.getPackageName());
        a((T) r0, com.xiaomi.xmpush.thrift.a.i, false, (com.xiaomi.xmpush.thrift.u) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c0, code lost:
        if (r14 == false) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c2, code lost:
        r6.b(com.xiaomi.mipush.sdk.c.a(r11.c).c());
        r6.d(r11.c.getPackageName());
        r14 = com.xiaomi.xmpush.thrift.au.a(com.xiaomi.mipush.sdk.ae.a(r11.c, r6, com.xiaomi.xmpush.thrift.a.i, false, r11.c.getPackageName(), com.xiaomi.mipush.sdk.c.a(r11.c).c()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f5, code lost:
        if (r14 == null) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00f7, code lost:
        r2.putExtra("mipush_payload", r14);
        r2.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", true);
        r2.putExtra("mipush_app_id", com.xiaomi.mipush.sdk.c.a(r11.c).c());
        r2.putExtra("mipush_app_token", com.xiaomi.mipush.sdk.c.a(r11.c).d());
        b(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0122, code lost:
        r14 = android.os.Message.obtain();
        r14.what = 19;
        r13 = r13.ordinal();
        r14.obj = r12;
        r14.arg1 = r13;
        r11.h.sendMessageDelayed(r14, 5000);
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r12, com.xiaomi.mipush.sdk.ao r13, boolean r14, java.util.HashMap<java.lang.String, java.lang.String> r15) {
        /*
            r11 = this;
            android.content.Context r0 = r11.c
            com.xiaomi.mipush.sdk.c r0 = com.xiaomi.mipush.sdk.c.a(r0)
            boolean r0 = r0.b()
            if (r0 == 0) goto L_0x0139
            android.content.Context r0 = r11.c
            boolean r0 = com.xiaomi.channel.commonutils.network.d.c(r0)
            if (r0 != 0) goto L_0x0015
            return
        L_0x0015:
            com.xiaomi.xmpush.thrift.ai r0 = new com.xiaomi.xmpush.thrift.ai
            r0.<init>()
            r1 = 1
            r0.a(r1)
            android.content.Intent r2 = r11.j()
            boolean r3 = android.text.TextUtils.isEmpty(r12)
            r4 = 0
            if (r3 == 0) goto L_0x004a
            java.lang.String r12 = com.xiaomi.mipush.sdk.MiPushClient.generatePacketID()
            r0.a(r12)
            if (r14 == 0) goto L_0x0038
            com.xiaomi.xmpush.thrift.ai r3 = new com.xiaomi.xmpush.thrift.ai
            r3.<init>(r12, r1)
            goto L_0x0039
        L_0x0038:
            r3 = r4
        L_0x0039:
            java.lang.Class<com.xiaomi.mipush.sdk.ab> r5 = com.xiaomi.mipush.sdk.ab.class
            monitor-enter(r5)
            android.content.Context r6 = r11.c     // Catch:{ all -> 0x0047 }
            com.xiaomi.mipush.sdk.ab r6 = com.xiaomi.mipush.sdk.ab.a(r6)     // Catch:{ all -> 0x0047 }
            r6.a(r12)     // Catch:{ all -> 0x0047 }
            monitor-exit(r5)     // Catch:{ all -> 0x0047 }
            goto L_0x0054
        L_0x0047:
            r12 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0047 }
            throw r12
        L_0x004a:
            r0.a(r12)
            if (r14 == 0) goto L_0x0056
            com.xiaomi.xmpush.thrift.ai r3 = new com.xiaomi.xmpush.thrift.ai
            r3.<init>(r12, r1)
        L_0x0054:
            r6 = r3
            goto L_0x0057
        L_0x0056:
            r6 = r4
        L_0x0057:
            int[] r3 = com.xiaomi.mipush.sdk.an.a
            int r5 = r13.ordinal()
            r3 = r3[r5]
            switch(r3) {
                case 1: goto L_0x0089;
                case 2: goto L_0x0070;
                case 3: goto L_0x0063;
                case 4: goto L_0x0063;
                case 5: goto L_0x0063;
                default: goto L_0x0062;
            }
        L_0x0062:
            goto L_0x00a4
        L_0x0063:
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.ThirdPartyRegUpdate
            java.lang.String r3 = r3.W
            r0.c(r3)
            if (r15 == 0) goto L_0x00a4
            r0.a(r15)
            goto L_0x00a4
        L_0x0070:
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.EnablePushMessage
            java.lang.String r3 = r3.W
            r0.c(r3)
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.EnablePushMessage
            java.lang.String r3 = r3.W
            r6.c(r3)
            if (r15 == 0) goto L_0x0086
            r0.a(r15)
            r6.a(r15)
        L_0x0086:
            java.lang.String r15 = "com.xiaomi.mipush.ENABLE_PUSH_MESSAGE"
            goto L_0x00a1
        L_0x0089:
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.DisablePushMessage
            java.lang.String r3 = r3.W
            r0.c(r3)
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.DisablePushMessage
            java.lang.String r3 = r3.W
            r6.c(r3)
            if (r15 == 0) goto L_0x009f
            r0.a(r15)
            r6.a(r15)
        L_0x009f:
            java.lang.String r15 = "com.xiaomi.mipush.DISABLE_PUSH_MESSAGE"
        L_0x00a1:
            r2.setAction(r15)
        L_0x00a4:
            android.content.Context r15 = r11.c
            com.xiaomi.mipush.sdk.c r15 = com.xiaomi.mipush.sdk.c.a(r15)
            java.lang.String r15 = r15.c()
            r0.b(r15)
            android.content.Context r15 = r11.c
            java.lang.String r15 = r15.getPackageName()
            r0.d(r15)
            com.xiaomi.xmpush.thrift.a r15 = com.xiaomi.xmpush.thrift.a.Notification
            r3 = 0
            r11.a((T) r0, r15, r3, r4)
            if (r14 == 0) goto L_0x0122
            android.content.Context r14 = r11.c
            com.xiaomi.mipush.sdk.c r14 = com.xiaomi.mipush.sdk.c.a(r14)
            java.lang.String r14 = r14.c()
            r6.b(r14)
            android.content.Context r14 = r11.c
            java.lang.String r14 = r14.getPackageName()
            r6.d(r14)
            android.content.Context r5 = r11.c
            com.xiaomi.xmpush.thrift.a r7 = com.xiaomi.xmpush.thrift.a.Notification
            r8 = 0
            android.content.Context r14 = r11.c
            java.lang.String r9 = r14.getPackageName()
            android.content.Context r14 = r11.c
            com.xiaomi.mipush.sdk.c r14 = com.xiaomi.mipush.sdk.c.a(r14)
            java.lang.String r10 = r14.c()
            com.xiaomi.xmpush.thrift.af r14 = com.xiaomi.mipush.sdk.ae.a(r5, r6, r7, r8, r9, r10)
            byte[] r14 = com.xiaomi.xmpush.thrift.au.a(r14)
            if (r14 == 0) goto L_0x0122
            java.lang.String r15 = "mipush_payload"
            r2.putExtra(r15, r14)
            java.lang.String r14 = "com.xiaomi.mipush.MESSAGE_CACHE"
            r2.putExtra(r14, r1)
            java.lang.String r14 = "mipush_app_id"
            android.content.Context r15 = r11.c
            com.xiaomi.mipush.sdk.c r15 = com.xiaomi.mipush.sdk.c.a(r15)
            java.lang.String r15 = r15.c()
            r2.putExtra(r14, r15)
            java.lang.String r14 = "mipush_app_token"
            android.content.Context r15 = r11.c
            com.xiaomi.mipush.sdk.c r15 = com.xiaomi.mipush.sdk.c.a(r15)
            java.lang.String r15 = r15.d()
            r2.putExtra(r14, r15)
            r11.b(r2)
        L_0x0122:
            android.os.Message r14 = android.os.Message.obtain()
            r15 = 19
            r14.what = r15
            int r13 = r13.ordinal()
            r14.obj = r12
            r14.arg1 = r13
            android.os.Handler r12 = r11.h
            r0 = 5000(0x1388, double:2.4703E-320)
            r12.sendMessageDelayed(r14, r0)
        L_0x0139:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.aj.a(java.lang.String, com.xiaomi.mipush.sdk.ao, boolean, java.util.HashMap):void");
    }

    private void b(Intent intent) {
        int a2 = an.a(this.c).a(g.ServiceBootMode.a(), com.xiaomi.xmpush.thrift.b.START.a());
        int h2 = h();
        boolean z = a2 == com.xiaomi.xmpush.thrift.b.BIND.a() && f;
        int a3 = (z ? com.xiaomi.xmpush.thrift.b.BIND : com.xiaomi.xmpush.thrift.b.START).a();
        if (a3 != h2) {
            c(a3);
        }
        if (z) {
            c(intent);
        } else {
            a(intent);
        }
    }

    private synchronized void c(Intent intent) {
        if (this.j) {
            Message d2 = d(intent);
            if (this.i.size() >= 50) {
                this.i.remove(0);
            }
            this.i.add(d2);
        } else if (this.e == null) {
            this.c.bindService(intent, new am(this), 1);
            this.j = true;
            this.i.clear();
            this.i.add(d(intent));
        } else {
            try {
                this.e.send(d(intent));
            } catch (RemoteException e2) {
                b.a((Throwable) e2);
            }
        }
    }

    private Message d(Intent intent) {
        Message obtain = Message.obtain();
        obtain.what = 17;
        obtain.obj = intent;
        return obtain;
    }

    private synchronized void d(int i2) {
        this.c.getSharedPreferences("mipush_extra", 0).edit().putInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, i2).commit();
    }

    private synchronized int h() {
        return this.c.getSharedPreferences("mipush_extra", 0).getInt(Constants.EXTRA_KEY_BOOT_SERVICE_MODE, -1);
    }

    private boolean i() {
        try {
            PackageInfo packageInfo = this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            return packageInfo != null && packageInfo.versionCode >= 105;
        } catch (Throwable unused) {
            return false;
        }
    }

    private Intent j() {
        return (!c() || "com.xiaomi.xmsf".equals(this.c.getPackageName())) ? n() : m();
    }

    private Intent k() {
        if (!"com.xiaomi.xmsf".equals(this.c.getPackageName())) {
            return l();
        }
        b.c("pushChannel xmsf create own channel");
        return n();
    }

    private Intent l() {
        if (c()) {
            b.c("pushChannel app start miui china channel");
            return m();
        }
        b.c("pushChannel app start  own channel");
        return n();
    }

    private Intent m() {
        Intent intent = new Intent();
        String packageName = this.c.getPackageName();
        intent.setPackage("com.xiaomi.xmsf");
        intent.setClassName("com.xiaomi.xmsf", o());
        intent.putExtra("mipush_app_package", packageName);
        p();
        return intent;
    }

    private Intent n() {
        Intent intent = new Intent();
        String packageName = this.c.getPackageName();
        q();
        intent.setComponent(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"));
        intent.putExtra("mipush_app_package", packageName);
        return intent;
    }

    private String o() {
        try {
            if (this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 106) {
                return "com.xiaomi.push.service.XMPushService";
            }
        } catch (Exception unused) {
        }
        return "com.xiaomi.xmsf.push.service.XMPushService";
    }

    private void p() {
        try {
            this.c.getPackageManager().setComponentEnabledSetting(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"), 2, 1);
        } catch (Throwable unused) {
        }
    }

    private void q() {
        try {
            this.c.getPackageManager().setComponentEnabledSetting(new ComponentName(this.c, "com.xiaomi.push.service.XMPushService"), 1, 1);
        } catch (Throwable unused) {
        }
    }

    private boolean r() {
        if (c()) {
            try {
                return this.c.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4).versionCode >= 108;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    private boolean s() {
        String packageName = this.c.getPackageName();
        return packageName.contains("miui") || packageName.contains("xiaomi") || (this.c.getApplicationInfo().flags & 1) != 0;
    }

    public void a() {
        a(j());
    }

    public void a(int i2) {
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        j2.putExtra(at.y, this.c.getPackageName());
        j2.putExtra(at.z, i2);
        b(j2);
    }

    public final void a(com.xiaomi.xmpush.thrift.aj ajVar, boolean z) {
        this.k = null;
        c.a(this.c).a = ajVar.c();
        Intent j2 = j();
        byte[] a2 = au.a(ae.a(this.c, ajVar, com.xiaomi.xmpush.thrift.a.Registration));
        if (a2 == null) {
            b.a((String) "register fail, because msgBytes is null.");
            return;
        }
        j2.setAction("com.xiaomi.mipush.REGISTER_APP");
        j2.putExtra("mipush_app_id", c.a(this.c).c());
        j2.putExtra("mipush_payload", a2);
        j2.putExtra("mipush_session", this.d);
        j2.putExtra("mipush_env_chanage", z);
        j2.putExtra("mipush_env_type", c.a(this.c).l());
        if (!d.c(this.c) || !g()) {
            this.k = j2;
        } else {
            b(j2);
        }
    }

    public final void a(aq aqVar) {
        byte[] a2 = au.a(ae.a(this.c, aqVar, com.xiaomi.xmpush.thrift.a.UnRegistration));
        if (a2 == null) {
            b.a((String) "unregister fail, because msgBytes is null.");
            return;
        }
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.UNREGISTER_APP");
        j2.putExtra("mipush_app_id", c.a(this.c).c());
        j2.putExtra("mipush_payload", a2);
        b(j2);
    }

    public final void a(f fVar) {
        Intent j2 = j();
        byte[] a2 = au.a(fVar);
        if (a2 == null) {
            b.a((String) "send TinyData failed, because tinyDataBytes is null.");
            return;
        }
        j2.setAction("com.xiaomi.mipush.SEND_TINYDATA");
        j2.putExtra("mipush_payload", a2);
        a(j2);
    }

    public final void a(String str, ao aoVar, d dVar) {
        ab.a(this.c).a(aoVar, "syncing");
        a(str, aoVar, false, g.c(this.c, dVar));
    }

    public void a(String str, String str2) {
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.CLEAR_NOTIFICATION");
        j2.putExtra(at.y, this.c.getPackageName());
        j2.putExtra(at.D, str);
        j2.putExtra(at.E, str2);
        b(j2);
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, u uVar) {
        a(t, aVar, !aVar.equals(com.xiaomi.xmpush.thrift.a.Registration), uVar);
    }

    public <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z) {
        a aVar2 = new a();
        aVar2.a = t;
        aVar2.b = aVar;
        aVar2.c = z;
        synchronized (g) {
            g.add(aVar2);
            if (g.size() > 10) {
                g.remove(0);
            }
        }
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, u uVar) {
        a(t, aVar, z, true, uVar, true);
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, u uVar, boolean z2) {
        a(t, aVar, z, true, uVar, z2);
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, boolean z2, u uVar, boolean z3) {
        a(t, aVar, z, z2, uVar, z3, this.c.getPackageName(), c.a(this.c).c());
    }

    public final <T extends org.apache.thrift.a<T, ?>> void a(T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, boolean z2, u uVar, boolean z3, String str, String str2) {
        if (c.a(this.c).i()) {
            af a2 = ae.a(this.c, t, aVar, z, str, str2);
            if (uVar != null) {
                a2.a(uVar);
            }
            byte[] a3 = au.a(a2);
            if (a3 == null) {
                b.a((String) "send message fail, because msgBytes is null.");
                return;
            }
            Intent j2 = j();
            j2.setAction("com.xiaomi.mipush.SEND_MESSAGE");
            j2.putExtra("mipush_payload", a3);
            j2.putExtra("com.xiaomi.mipush.MESSAGE_CACHE", z3);
            b(j2);
        } else if (z2) {
            a(t, aVar, z);
        } else {
            b.a((String) "drop the message before initialization.");
        }
    }

    public final void a(boolean z) {
        a(z, (String) null);
    }

    public final void a(boolean z, String str) {
        ao aoVar;
        if (z) {
            ab.a(this.c).a(ao.DISABLE_PUSH, "syncing");
            ab.a(this.c).a(ao.ENABLE_PUSH, "");
            aoVar = ao.DISABLE_PUSH;
        } else {
            ab.a(this.c).a(ao.ENABLE_PUSH, "syncing");
            ab.a(this.c).a(ao.DISABLE_PUSH, "");
            aoVar = ao.ENABLE_PUSH;
        }
        a(str, aoVar, true, (HashMap<String, String>) null);
    }

    public final void b() {
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.DISABLE_PUSH");
        b(j2);
    }

    public void b(int i2) {
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        j2.putExtra(at.y, this.c.getPackageName());
        j2.putExtra(at.A, i2);
        String str = at.C;
        StringBuilder sb = new StringBuilder();
        sb.append(this.c.getPackageName());
        sb.append(i2);
        j2.putExtra(str, c.b(sb.toString()));
        b(j2);
    }

    public boolean c() {
        return this.a && 1 == c.a(this.c).l();
    }

    public boolean c(int i2) {
        if (!c.a(this.c).b()) {
            return false;
        }
        d(i2);
        ai aiVar = new ai();
        aiVar.a(MiPushClient.generatePacketID());
        aiVar.b(c.a(this.c).c());
        aiVar.d(this.c.getPackageName());
        aiVar.c(r.ClientABTest.W);
        aiVar.h = new HashMap();
        aiVar.h.put("boot_mode", String.valueOf(i2));
        a(this.c).a((T) aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, (u) null);
        return true;
    }

    public void d() {
        if (this.k != null) {
            b(this.k);
            this.k = null;
        }
    }

    public void e() {
        synchronized (g) {
            Iterator<a> it = g.iterator();
            while (it.hasNext()) {
                a next = it.next();
                a(next.a, next.b, next.c, false, null, true);
            }
            g.clear();
        }
    }

    public void f() {
        Intent j2 = j();
        j2.setAction("com.xiaomi.mipush.SET_NOTIFICATION_TYPE");
        j2.putExtra(at.y, this.c.getPackageName());
        j2.putExtra(at.C, c.b(this.c.getPackageName()));
        b(j2);
    }

    public boolean g() {
        if (!c() || !s()) {
            return true;
        }
        if (this.l == null) {
            this.l = Integer.valueOf(av.a(this.c).b());
            if (this.l.intValue() == 0) {
                this.c.getContentResolver().registerContentObserver(av.a(this.c).c(), false, new al(this, new Handler(Looper.getMainLooper())));
            }
        }
        return this.l.intValue() != 0;
    }
}
