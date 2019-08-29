package defpackage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: aov reason: default package */
/* compiled from: AccountStateDispatcher */
public final class aov {
    private List<anp> a;
    private List<anq> b;
    private List<anq> c;
    private List<anq> d;

    /* renamed from: aov$a */
    /* compiled from: AccountStateDispatcher */
    public static class a {
        /* access modifiers changed from: private */
        public static final aov a = new aov(0);
    }

    /* synthetic */ aov(byte b2) {
        this();
    }

    private aov() {
    }

    public final void a(boolean z, boolean z2) {
        if (z ^ z2) {
            b(z, z2);
        }
    }

    public final void a(ant ant) {
        for (anp next : this.a) {
            if (next != null) {
                next.onUserInfoUpdate(ant);
            }
        }
    }

    public final synchronized void a(anp anp) {
        if (!d().contains(anp)) {
            d().add(anp);
        }
    }

    public final synchronized void b(anp anp) {
        if (d().contains(anp)) {
            d().remove(anp);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(@android.support.annotation.Nullable defpackage.anq r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            java.util.List r0 = r1.e()     // Catch:{ all -> 0x0018 }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x0018 }
            if (r0 != 0) goto L_0x0016
            java.util.List r0 = r1.e()     // Catch:{ all -> 0x0018 }
            r0.add(r2)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r1)
            return
        L_0x0018:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aov.a(anq):void");
    }

    public final void a(boolean z) {
        for (anq onComplete : e()) {
            onComplete.onComplete(z);
        }
        e().clear();
    }

    public final void a() {
        for (anq loginOrBindCancel : e()) {
            loginOrBindCancel.loginOrBindCancel();
        }
        e().clear();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(@android.support.annotation.Nullable defpackage.anq r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            java.util.List r0 = r1.f()     // Catch:{ all -> 0x0018 }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x0018 }
            if (r0 != 0) goto L_0x0016
            java.util.List r0 = r1.f()     // Catch:{ all -> 0x0018 }
            r0.add(r2)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r1)
            return
        L_0x0018:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aov.b(anq):void");
    }

    public final void b(boolean z) {
        for (anq onComplete : f()) {
            onComplete.onComplete(z);
        }
        f().clear();
    }

    public final void b() {
        for (anq loginOrBindCancel : f()) {
            loginOrBindCancel.loginOrBindCancel();
        }
        f().clear();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c(@android.support.annotation.Nullable defpackage.anq r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            java.util.List r0 = r1.c()     // Catch:{ all -> 0x0018 }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x0018 }
            if (r0 != 0) goto L_0x0016
            java.util.List r0 = r1.c()     // Catch:{ all -> 0x0018 }
            r0.add(r2)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r1)
            return
        L_0x0018:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aov.c(anq):void");
    }

    private void b(boolean z, boolean z2) {
        for (anp next : this.a) {
            if (next != null) {
                next.onLoginStateChanged(z, z2);
            }
        }
    }

    private List<anp> d() {
        if (this.a == null) {
            this.a = new CopyOnWriteArrayList();
        }
        return this.a;
    }

    private List<anq> e() {
        if (this.b == null) {
            this.b = new CopyOnWriteArrayList();
        }
        return this.b;
    }

    private List<anq> f() {
        if (this.c == null) {
            this.c = new CopyOnWriteArrayList();
        }
        return this.c;
    }

    public final List<anq> c() {
        if (this.d == null) {
            this.d = new CopyOnWriteArrayList();
        }
        return this.d;
    }
}
