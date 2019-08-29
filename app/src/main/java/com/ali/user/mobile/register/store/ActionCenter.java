package com.ali.user.mobile.register.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;

public class ActionCenter {
    /* access modifiers changed from: private */
    public State a;
    private IStorageCallback[] b;
    private IStorageCallback c = new IStorageCallback() {
        public final void a(State state) {
            AliUserLog.d("Reg_actionCenter", "state handled by mock callback ".concat(String.valueOf(state)));
        }
    };
    private IStore[] d = new IStore[2];
    private Handler e = new Handler(Looper.getMainLooper());

    public ActionCenter() {
        this.d[0] = new RPCStore();
        this.d[1] = new RPCStore();
        this.a = new State();
        this.b = new IStorageCallback[2];
    }

    public final synchronized void a(final SimpleRequest simpleRequest, final BaseActivity baseActivity) {
        AliUserLog.c("Reg_actionCenter", "start handle remote request ".concat(String.valueOf(simpleRequest)));
        final IStore iStore = this.d[0];
        if (this.a == null) {
            this.a = new State();
        }
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                State c2 = ActionCenter.this.a.c();
                c2.b = -2;
                try {
                    State a2 = iStore.a(simpleRequest, c2, baseActivity);
                    ActionCenter.this.b(a2);
                    if (a2.e != null) {
                        throw a2.e;
                    }
                } catch (Throwable th) {
                    ActionCenter.this.b(c2);
                    if (c2.e != null) {
                        throw c2.e;
                    }
                    throw th;
                }
            }
        });
    }

    public final synchronized State a() {
        try {
        }
        return this.a;
    }

    public final synchronized void a(Account account) {
        AliUserLog.c("Reg_actionCenter", "update account");
        this.a.a(account);
    }

    public final synchronized void a(String str, String str2, String str3) {
        AliUserLog.c("Reg_actionCenter", "update account");
        this.a.a(str, str2, str3);
    }

    public final synchronized void a(String str) {
        a(str, (Bundle) null);
    }

    private synchronized void a(String str, Bundle bundle) {
        AliUserLog.c("Reg_actionCenter", "start handle local request ".concat(String.valueOf(str)));
        if (this.a == null) {
            this.a = new State();
        }
        State c2 = this.a.c();
        c2.b = -1;
        c2.g = str;
        c2.h = bundle;
        b(c2);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(final com.ali.user.mobile.register.model.State r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "Reg_actionCenter"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x004e }
            java.lang.String r2 = "update state old "
            r1.<init>(r2)     // Catch:{ all -> 0x004e }
            com.ali.user.mobile.register.model.State r2 = r3.a     // Catch:{ all -> 0x004e }
            r1.append(r2)     // Catch:{ all -> 0x004e }
            java.lang.String r2 = " new "
            r1.append(r2)     // Catch:{ all -> 0x004e }
            r1.append(r4)     // Catch:{ all -> 0x004e }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x004e }
            com.ali.user.mobile.log.AliUserLog.c(r0, r1)     // Catch:{ all -> 0x004e }
            if (r4 == 0) goto L_0x004c
            r0 = -1
            int r1 = r4.b     // Catch:{ all -> 0x004e }
            if (r0 == r1) goto L_0x0032
            r0 = -2
            int r1 = r4.b     // Catch:{ all -> 0x004e }
            if (r0 == r1) goto L_0x0032
            boolean r0 = r4.b()     // Catch:{ all -> 0x004e }
            if (r0 != 0) goto L_0x0032
            goto L_0x004c
        L_0x0032:
            r3.a = r4     // Catch:{ all -> 0x004e }
            com.ali.user.mobile.register.store.IStorageCallback[] r0 = r3.b     // Catch:{ all -> 0x004e }
            int r1 = r4.b     // Catch:{ all -> 0x004e }
            int r1 = r1 + 2
            r0 = r0[r1]     // Catch:{ all -> 0x004e }
            if (r0 != 0) goto L_0x0040
            com.ali.user.mobile.register.store.IStorageCallback r0 = r3.c     // Catch:{ all -> 0x004e }
        L_0x0040:
            android.os.Handler r1 = r3.e     // Catch:{ all -> 0x004e }
            com.ali.user.mobile.register.store.ActionCenter$3 r2 = new com.ali.user.mobile.register.store.ActionCenter$3     // Catch:{ all -> 0x004e }
            r2.<init>(r0, r4)     // Catch:{ all -> 0x004e }
            r1.post(r2)     // Catch:{ all -> 0x004e }
            monitor-exit(r3)
            return
        L_0x004c:
            monitor-exit(r3)
            return
        L_0x004e:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.register.store.ActionCenter.b(com.ali.user.mobile.register.model.State):void");
    }

    public final void a(IStorageCallback iStorageCallback) {
        this.b[1] = iStorageCallback;
    }

    public final void b(IStorageCallback iStorageCallback) {
        this.b[0] = iStorageCallback;
    }

    public final synchronized void a(boolean z) {
        StringBuilder sb = new StringBuilder("reset state ");
        sb.append(this.a);
        sb.append(" forced ");
        sb.append(z);
        AliUserLog.c("Reg_actionCenter", sb.toString());
        if (!z) {
            if (this.a != null) {
                this.a.f = null;
                return;
            }
        }
        this.a = new State();
    }

    public final synchronized void a(State state) {
        this.a = state;
    }
}
