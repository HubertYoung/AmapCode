package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eac reason: default package */
/* compiled from: NotificationServiceAdapter */
public final class eac {
    /* access modifiers changed from: private */
    public static final String a = "eac";
    private static volatile eac b;
    private czk c = ((czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class));
    private ead d = null;
    private List<ead> e = new ArrayList();
    private final a f = new a() {
        public final void a() {
            String b = eac.a;
            StringBuilder sb = new StringBuilder("onServiceConnected()");
            sb.append(eac.this.g());
            eao.a(b, sb.toString());
            eac.this.e();
        }
    };

    public static final eac a() {
        if (b == null) {
            synchronized (eac.class) {
                try {
                    if (b == null) {
                        b = new eac();
                    }
                }
            }
        }
        return b;
    }

    private eac() {
    }

    private synchronized void c() {
        String str = a;
        StringBuilder sb = new StringBuilder("startNotifyService()");
        sb.append(g());
        eao.a(str, sb.toString());
        if (f()) {
            e();
        } else {
            this.c.a(this.f);
        }
    }

    private synchronized void d() {
        String str = a;
        StringBuilder sb = new StringBuilder("stopNotifyService()");
        sb.append(g());
        eao.a(str, sb.toString());
        if (f()) {
            this.c.a();
        }
        this.d = null;
    }

    public final synchronized void a(ead ead) {
        int i;
        String str = a;
        StringBuilder sb = new StringBuilder("notify(");
        sb.append(ead);
        sb.append(")");
        sb.append(g());
        eao.a(str, sb.toString());
        if (ead != null) {
            if (!f()) {
                this.d = ead;
                c();
                return;
            }
            czk czk = this.c;
            NotificationChannelIds b2 = ead.b(ead.a);
            if (ead.b == -1) {
                i = R.drawable.ic_launcher;
            } else {
                i = ead.b;
            }
            czk.a(b2, i, ead.c, TextUtils.isEmpty(ead.d) ? eay.a(R.string.amap_app_name) : ead.d);
            if (this.d != null && this.d.a == ead.a) {
                this.d = null;
            }
            int b3 = b(ead.a);
            if (b3 >= 0) {
                this.e.remove(b3);
            }
            this.e.add(ead);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0080, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String r0 = a     // Catch:{ all -> 0x0096 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = "cancel(type:"
            r1.<init>(r2)     // Catch:{ all -> 0x0096 }
            r1.append(r5)     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = ")"
            r1.append(r2)     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r4.g()     // Catch:{ all -> 0x0096 }
            r1.append(r2)     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0096 }
            defpackage.eao.a(r0, r1)     // Catch:{ all -> 0x0096 }
            ead r0 = r4.d     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x002f
            ead r0 = r4.d     // Catch:{ all -> 0x0096 }
            int r0 = r0.a     // Catch:{ all -> 0x0096 }
            if (r0 != r5) goto L_0x002f
            r5 = 0
            r4.d = r5     // Catch:{ all -> 0x0096 }
            monitor-exit(r4)
            return
        L_0x002f:
            int r0 = r4.b(r5)     // Catch:{ all -> 0x0096 }
            if (r0 < 0) goto L_0x003a
            java.util.List<ead> r1 = r4.e     // Catch:{ all -> 0x0096 }
            r1.remove(r0)     // Catch:{ all -> 0x0096 }
        L_0x003a:
            java.util.List<ead> r0 = r4.e     // Catch:{ all -> 0x0096 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0081
            java.lang.Class<dfm> r0 = defpackage.dfm.class
            java.lang.Object r0 = defpackage.ank.a(r0)     // Catch:{ all -> 0x0096 }
            dfm r0 = (defpackage.dfm) r0     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0051
            boolean r0 = r0.b()     // Catch:{ all -> 0x0096 }
            goto L_0x0052
        L_0x0051:
            r0 = 0
        L_0x0052:
            java.lang.String r1 = a     // Catch:{ all -> 0x0096 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "cancel(type:"
            r2.<init>(r3)     // Catch:{ all -> 0x0096 }
            r2.append(r5)     // Catch:{ all -> 0x0096 }
            java.lang.String r5 = "), is drive navigating : "
            r2.append(r5)     // Catch:{ all -> 0x0096 }
            r2.append(r0)     // Catch:{ all -> 0x0096 }
            java.lang.String r5 = r4.g()     // Catch:{ all -> 0x0096 }
            r2.append(r5)     // Catch:{ all -> 0x0096 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x0096 }
            defpackage.eao.a(r1, r5)     // Catch:{ all -> 0x0096 }
            boolean r5 = r4.f()     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x007f
            if (r0 != 0) goto L_0x007f
            r4.d()     // Catch:{ all -> 0x0096 }
        L_0x007f:
            monitor-exit(r4)
            return
        L_0x0081:
            java.util.List<ead> r5 = r4.e     // Catch:{ all -> 0x0096 }
            java.util.List<ead> r0 = r4.e     // Catch:{ all -> 0x0096 }
            int r0 = r0.size()     // Catch:{ all -> 0x0096 }
            int r0 = r0 + -1
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x0096 }
            ead r5 = (defpackage.ead) r5     // Catch:{ all -> 0x0096 }
            r4.a(r5)     // Catch:{ all -> 0x0096 }
            monitor-exit(r4)
            return
        L_0x0096:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eac.a(int):void");
    }

    private int b(int i) {
        String str = a;
        StringBuilder sb = new StringBuilder("indexOfType(type:");
        sb.append(i);
        sb.append(")");
        sb.append(g());
        eao.a(str, sb.toString());
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (i == this.e.get(i2).a) {
                return i2;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public synchronized void e() {
        if (this.d != null) {
            a(this.d);
        } else {
            d();
        }
    }

    private boolean f() {
        return this.c.b();
    }

    /* access modifiers changed from: private */
    public String g() {
        StringBuilder sb = new StringBuilder(" {mNotifyService=");
        sb.append(this.c);
        sb.append(",mServiceConnected=");
        sb.append(f());
        sb.append(",mTargetNotificationToShow=");
        sb.append(this.d);
        sb.append(",mShownNotifications=");
        sb.append(this.e);
        sb.append(",}");
        return sb.toString();
    }
}
