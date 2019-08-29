package com.xiaomi.push.service;

import android.content.SharedPreferences;
import com.google.protobuf.micro.c;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.channel.commonutils.misc.k.b;
import com.xiaomi.push.protobuf.a.C0081a;
import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.smack.util.e;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ba {
    private static String a;
    private static ba e = new ba();
    /* access modifiers changed from: private */
    public List<a> b = new ArrayList();
    /* access modifiers changed from: private */
    public C0081a c;
    /* access modifiers changed from: private */
    public b d;

    public static abstract class a {
        public void a(C0081a aVar) {
        }

        public void a(C0082b bVar) {
        }
    }

    private ba() {
    }

    public static ba a() {
        return e;
    }

    public static synchronized String e() {
        String str;
        synchronized (ba.class) {
            try {
                if (a == null) {
                    SharedPreferences sharedPreferences = j.a().getSharedPreferences("XMPushServiceConfig", 0);
                    String string = sharedPreferences.getString("DeviceUUID", null);
                    a = string;
                    if (string == null) {
                        String b2 = j.b();
                        a = b2;
                        if (b2 != null) {
                            sharedPreferences.edit().putString("DeviceUUID", a).commit();
                        }
                    }
                }
                str = a;
            }
        }
        return str;
    }

    private void f() {
        if (this.c == null) {
            h();
        }
    }

    private void g() {
        if (this.d == null) {
            this.d = new bb(this);
            e.a(this.d);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.xiaomi.channel.commonutils.android.j.a()     // Catch:{ Exception -> 0x002b }
            java.lang.String r2 = "XMCloudCfg"
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch:{ Exception -> 0x002b }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x002b }
            r2.<init>(r1)     // Catch:{ Exception -> 0x002b }
            com.google.protobuf.micro.b r0 = com.google.protobuf.micro.b.a(r2)     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            com.xiaomi.push.protobuf.a$a r0 = com.xiaomi.push.protobuf.a.C0081a.c(r0)     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            r4.c = r0     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            r2.close()     // Catch:{ Exception -> 0x0025, all -> 0x0021 }
            com.xiaomi.channel.commonutils.file.a.a(r2)
            goto L_0x0044
        L_0x0021:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x0050
        L_0x0025:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x002c
        L_0x0029:
            r1 = move-exception
            goto L_0x0050
        L_0x002b:
            r1 = move-exception
        L_0x002c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0029 }
            java.lang.String r3 = "load config failure: "
            r2.<init>(r3)     // Catch:{ all -> 0x0029 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0029 }
            r2.append(r1)     // Catch:{ all -> 0x0029 }
            java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x0029 }
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0029 }
            com.xiaomi.channel.commonutils.file.a.a(r0)
        L_0x0044:
            com.xiaomi.push.protobuf.a$a r0 = r4.c
            if (r0 != 0) goto L_0x004f
            com.xiaomi.push.protobuf.a$a r0 = new com.xiaomi.push.protobuf.a$a
            r0.<init>()
            r4.c = r0
        L_0x004f:
            return
        L_0x0050:
            com.xiaomi.channel.commonutils.file.a.a(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ba.h():void");
    }

    /* access modifiers changed from: private */
    public void i() {
        try {
            if (this.c != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(j.a().openFileOutput("XMCloudCfg", 0));
                c a2 = c.a((OutputStream) bufferedOutputStream);
                this.c.a(a2);
                a2.a();
                bufferedOutputStream.close();
            }
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("save config failure: ");
            sb.append(e2.getMessage());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(C0082b bVar) {
        a[] aVarArr;
        if (bVar.i() && bVar.h() > c()) {
            g();
        }
        synchronized (this) {
            aVarArr = (a[]) this.b.toArray(new a[this.b.size()]);
        }
        for (a a2 : aVarArr) {
            a2.a(bVar);
        }
    }

    public synchronized void a(a aVar) {
        this.b.add(aVar);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void b() {
        this.b.clear();
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        f();
        if (this.c != null) {
            return this.c.d();
        }
        return 0;
    }

    public C0081a d() {
        f();
        return this.c;
    }
}
