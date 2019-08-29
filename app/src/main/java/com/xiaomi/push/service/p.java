package com.xiaomi.push.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.e;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.ArrayList;

public class p {
    private static volatile p d;
    /* access modifiers changed from: private */
    public Context a;
    private Object b = new Object();
    private AccountManager c;
    private ArrayList<a> e;
    private OnAccountsUpdateListener f;

    public interface a {
        void a(String str, Context context);
    }

    private p(Context context) {
        this.a = context;
        if (e.b(this.a)) {
            this.c = AccountManager.get(this.a);
            this.e = new ArrayList<>();
        }
    }

    public static p a(Context context) {
        if (d == null) {
            synchronized (p.class) {
                try {
                    if (d == null) {
                        d = new p(context);
                    }
                }
            }
        }
        return d;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        r0 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        if (r0.hasNext() == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        ((com.xiaomi.push.service.p.a) r0.next()).a(r4, r3.a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.b
            monitor-enter(r0)
            java.util.ArrayList<com.xiaomi.push.service.p$a> r1 = r3.e     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x002f
            java.util.ArrayList<com.xiaomi.push.service.p$a> r1 = r3.e     // Catch:{ all -> 0x0031 }
            int r1 = r1.size()     // Catch:{ all -> 0x0031 }
            if (r1 > 0) goto L_0x0010
            goto L_0x002f
        L_0x0010:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0031 }
            java.util.ArrayList<com.xiaomi.push.service.p$a> r2 = r3.e     // Catch:{ all -> 0x0031 }
            r1.<init>(r2)     // Catch:{ all -> 0x0031 }
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            java.util.Iterator r0 = r1.iterator()
        L_0x001c:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x002e
            java.lang.Object r1 = r0.next()
            com.xiaomi.push.service.p$a r1 = (com.xiaomi.push.service.p.a) r1
            android.content.Context r2 = r3.a
            r1.a(r4, r2)
            goto L_0x001c
        L_0x002e:
            return
        L_0x002f:
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            return
        L_0x0031:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.p.a(java.lang.String):void");
    }

    private void d() {
        if (this.f == null) {
            this.f = new q(this);
        }
    }

    private String e() {
        Account a2 = e.a(this.a);
        return a2 == null ? "" : a2.name;
    }

    public void a(a aVar) {
        synchronized (this.b) {
            if (this.e == null) {
                this.e = new ArrayList<>();
            }
            if (aVar != null) {
                int size = this.e.size();
                this.e.add(aVar);
                if (size == 0 && !a()) {
                    b.a((String) "MIIDManager startMIIDUpdateListener failed cause lack of GET_ACCOUNTS permission");
                }
            }
        }
    }

    public boolean a() {
        try {
            if (!e.b(this.a)) {
                return false;
            }
            if (this.f == null) {
                d();
            }
            this.c.addOnAccountsUpdatedListener(this.f, null, true);
            return true;
        } catch (Exception e2) {
            b.d(e2.toString());
            return false;
        }
    }

    public void b() {
        if (e.b(this.a) && this.f != null) {
            this.c.removeOnAccountsUpdatedListener(this.f);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(com.xiaomi.push.service.p.a r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.b
            monitor-enter(r0)
            java.util.ArrayList<com.xiaomi.push.service.p$a> r1 = r2.e     // Catch:{ all -> 0x001d }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            return
        L_0x0009:
            if (r3 == 0) goto L_0x001b
            java.util.ArrayList<com.xiaomi.push.service.p$a> r1 = r2.e     // Catch:{ all -> 0x001d }
            r1.remove(r3)     // Catch:{ all -> 0x001d }
            java.util.ArrayList<com.xiaomi.push.service.p$a> r3 = r2.e     // Catch:{ all -> 0x001d }
            int r3 = r3.size()     // Catch:{ all -> 0x001d }
            if (r3 != 0) goto L_0x001b
            r2.b()     // Catch:{ all -> 0x001d }
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            return
        L_0x001d:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.p.b(com.xiaomi.push.service.p$a):void");
    }

    public String c() {
        String e2 = e();
        if (!TextUtils.isEmpty(e2)) {
            r.a(this.a).a(e2);
            return e2;
        }
        r.a(this.a).a((String) "0");
        return "0";
    }
}
