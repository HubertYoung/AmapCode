package defpackage;

import android.os.RemoteException;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;
import java.util.List;
import java.util.Map;

/* renamed from: dx reason: default package */
/* compiled from: Repeater */
public final class dx implements eb {
    public String a;
    ParcelableInputStreamImpl b = null;
    boolean c = false;
    dy d = null;
    private ParcelableNetworkListener e;

    public dx(ParcelableNetworkListener parcelableNetworkListener, dy dyVar) {
        this.e = parcelableNetworkListener;
        this.d = dyVar;
        if (parcelableNetworkListener != null) {
            try {
                if ((parcelableNetworkListener.getListenerState() & 8) != 0) {
                    this.c = true;
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final void a(final int i, final Map<String, List<String>> map) {
        if (cl.a(2)) {
            cl.b("anet.Repeater", "[onResponseCode]", this.a, new Object[0]);
        }
        if (this.e != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.e;
            a((Runnable) new Runnable() {
                public final void run() {
                    try {
                        parcelableNetworkListener.onResponseCode(i, new ParcelableHeader(i, map));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    public final void a(int i, int i2, aa aaVar) {
        if (this.e != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.e;
            final int i3 = i;
            final aa aaVar2 = aaVar;
            final int i4 = i2;
            AnonymousClass2 r1 = new Runnable() {
                public final void run() {
                    if (!dx.this.c) {
                        try {
                            parcelableNetworkListener.onDataReceived(new DefaultProgressEvent(i3, aaVar2.c, i4, aaVar2.a));
                        } catch (RemoteException unused) {
                        }
                    } else {
                        try {
                            if (dx.this.b == null) {
                                dx.this.b = new ParcelableInputStreamImpl();
                                dx.this.b.init(dx.this.d, i4);
                                dx.this.b.write(aaVar2);
                                parcelableNetworkListener.onInputStreamGet(dx.this.b);
                                return;
                            }
                            dx.this.b.write(aaVar2);
                        } catch (Exception unused2) {
                            if (dx.this.b != null) {
                                try {
                                    dx.this.b.close();
                                } catch (RemoteException unused3) {
                                }
                            }
                        }
                    }
                }
            };
            a((Runnable) r1);
        }
    }

    public final void a(final DefaultFinishEvent defaultFinishEvent) {
        if (cl.a(2)) {
            cl.b("anet.Repeater", "[onFinish] ", this.a, new Object[0]);
        }
        if (this.e != null) {
            final ParcelableNetworkListener parcelableNetworkListener = this.e;
            a((Runnable) new Runnable() {
                /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
                /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00e6 */
                /* JADX WARNING: Removed duplicated region for block: B:22:0x0080 A[Catch:{ Throwable -> 0x00fa }] */
                /* JADX WARNING: Removed duplicated region for block: B:28:0x00b3 A[Catch:{ Throwable -> 0x00fa }] */
                /* JADX WARNING: Removed duplicated region for block: B:32:0x00c5 A[Catch:{ Exception -> 0x00e6 }] */
                /* JADX WARNING: Removed duplicated region for block: B:33:0x00c6 A[Catch:{ Exception -> 0x00e6 }] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r9 = this;
                        anetwork.channel.aidl.DefaultFinishEvent r0 = r5
                        r1 = 0
                        if (r0 == 0) goto L_0x0009
                        anetwork.channel.aidl.DefaultFinishEvent r0 = r5
                        r0.a = r1
                    L_0x0009:
                        long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.DefaultFinishEvent r0 = r5     // Catch:{ Throwable -> 0x00fa }
                        anet.channel.statist.RequestStatistic r0 = r0.e     // Catch:{ Throwable -> 0x00fa }
                        if (r0 == 0) goto L_0x002b
                        long r4 = r0.rspEnd     // Catch:{ Throwable -> 0x00fa }
                        r6 = 0
                        long r4 = r2 - r4
                        r0.lastProcessTime = r4     // Catch:{ Throwable -> 0x00fa }
                        long r4 = r0.retryCostTime     // Catch:{ Throwable -> 0x00fa }
                        long r6 = r0.start     // Catch:{ Throwable -> 0x00fa }
                        r8 = 0
                        long r6 = r2 - r6
                        long r4 = r4 + r6
                        r0.oneWayTime = r4     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.DefaultFinishEvent r4 = r5     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.statist.StatisticData r4 = r4.d     // Catch:{ Throwable -> 0x00fa }
                        r4.filledBy(r0)     // Catch:{ Throwable -> 0x00fa }
                    L_0x002b:
                        anetwork.channel.aidl.ParcelableNetworkListener r4 = r0     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.DefaultFinishEvent r5 = r5     // Catch:{ Throwable -> 0x00fa }
                        r4.onFinished(r5)     // Catch:{ Throwable -> 0x00fa }
                        dx r4 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.adapter.ParcelableInputStreamImpl r4 = r4.b     // Catch:{ Throwable -> 0x00fa }
                        if (r4 == 0) goto L_0x003f
                        dx r4 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.adapter.ParcelableInputStreamImpl r4 = r4.b     // Catch:{ Throwable -> 0x00fa }
                        r4.writeEnd()     // Catch:{ Throwable -> 0x00fa }
                    L_0x003f:
                        if (r0 == 0) goto L_0x00f9
                        long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00fa }
                        r6 = 0
                        long r4 = r4 - r2
                        r0.callbackTime = r4     // Catch:{ Throwable -> 0x00fa }
                        int r2 = r0.ret     // Catch:{ Throwable -> 0x00fa }
                        r3 = 0
                        r4 = 1
                        if (r2 != r4) goto L_0x006b
                        int r2 = r0.statusCode     // Catch:{ Throwable -> 0x00fa }
                        if (r2 > 0) goto L_0x0054
                        goto L_0x006b
                    L_0x0054:
                        r2 = 2
                        boolean r2 = defpackage.cl.a(r2)     // Catch:{ Throwable -> 0x00fa }
                        if (r2 == 0) goto L_0x007a
                        java.lang.String r2 = "anet.Repeater"
                        java.lang.String r5 = r0.toString()     // Catch:{ Throwable -> 0x00fa }
                        dx r6 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        java.lang.String r6 = r6.a     // Catch:{ Throwable -> 0x00fa }
                        java.lang.Object[] r7 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00fa }
                        defpackage.cl.b(r2, r5, r6, r7)     // Catch:{ Throwable -> 0x00fa }
                        goto L_0x007a
                    L_0x006b:
                        java.lang.String r2 = "anet.Repeater"
                        java.lang.String r5 = r0.toString()     // Catch:{ Throwable -> 0x00fa }
                        dx r6 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        java.lang.String r6 = r6.a     // Catch:{ Throwable -> 0x00fa }
                        java.lang.Object[] r7 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00fa }
                        defpackage.cl.d(r2, r5, r6, r7)     // Catch:{ Throwable -> 0x00fa }
                    L_0x007a:
                        java.util.concurrent.CopyOnWriteArrayList r2 = defpackage.m.i()     // Catch:{ Throwable -> 0x00fa }
                        if (r2 == 0) goto L_0x009a
                        int r5 = r2.size()     // Catch:{ Throwable -> 0x00fa }
                    L_0x0084:
                        int r6 = r5 + -1
                        if (r3 >= r6) goto L_0x009a
                        java.lang.Object r6 = r2.get(r3)     // Catch:{ Throwable -> 0x00fa }
                        java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x00fa }
                        int r7 = r3 + 1
                        java.lang.Object r7 = r2.get(r7)     // Catch:{ Throwable -> 0x00fa }
                        r0.putExtra(r6, r7)     // Catch:{ Throwable -> 0x00fa }
                        int r3 = r3 + 2
                        goto L_0x0084
                    L_0x009a:
                        dx r2 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        dy r2 = r2.d     // Catch:{ Throwable -> 0x00fa }
                        java.lang.String r3 = "RequestUserInfo"
                        java.lang.String r2 = r2.a(r3)     // Catch:{ Throwable -> 0x00fa }
                        r0.userInfo = r2     // Catch:{ Throwable -> 0x00fa }
                        z r2 = defpackage.x.a()     // Catch:{ Throwable -> 0x00fa }
                        r2.a(r0)     // Catch:{ Throwable -> 0x00fa }
                        boolean r2 = defpackage.ds.a(r0)     // Catch:{ Throwable -> 0x00fa }
                        if (r2 == 0) goto L_0x00bf
                        anet.channel.statist.RequestMonitor r2 = new anet.channel.statist.RequestMonitor     // Catch:{ Throwable -> 0x00fa }
                        r2.<init>(r0)     // Catch:{ Throwable -> 0x00fa }
                        z r3 = defpackage.x.a()     // Catch:{ Throwable -> 0x00fa }
                        r3.a(r2)     // Catch:{ Throwable -> 0x00fa }
                    L_0x00bf:
                        java.lang.String r2 = r0.ip     // Catch:{ Exception -> 0x00e6 }
                        org.json.JSONObject r3 = r0.extra     // Catch:{ Exception -> 0x00e6 }
                        if (r3 != 0) goto L_0x00c6
                        goto L_0x00ce
                    L_0x00c6:
                        org.json.JSONObject r1 = r0.extra     // Catch:{ Exception -> 0x00e6 }
                        java.lang.String r3 = "firstIp"
                        java.lang.String r1 = r1.optString(r3)     // Catch:{ Exception -> 0x00e6 }
                    L_0x00ce:
                        boolean r2 = defpackage.ci.b(r2)     // Catch:{ Exception -> 0x00e6 }
                        if (r2 != 0) goto L_0x00da
                        boolean r1 = defpackage.ci.b(r1)     // Catch:{ Exception -> 0x00e6 }
                        if (r1 == 0) goto L_0x00e6
                    L_0x00da:
                        anet.channel.statist.RequestMonitor r1 = new anet.channel.statist.RequestMonitor     // Catch:{ Exception -> 0x00e6 }
                        r1.<init>(r0)     // Catch:{ Exception -> 0x00e6 }
                        z r0 = defpackage.x.a()     // Catch:{ Exception -> 0x00e6 }
                        r0.a(r1)     // Catch:{ Exception -> 0x00e6 }
                    L_0x00e6:
                        eg r0 = defpackage.eg.a.a     // Catch:{ Throwable -> 0x00fa }
                        dx r1 = defpackage.dx.this     // Catch:{ Throwable -> 0x00fa }
                        dy r1 = r1.d     // Catch:{ Throwable -> 0x00fa }
                        ay r1 = r1.b     // Catch:{ Throwable -> 0x00fa }
                        cs r1 = r1.a     // Catch:{ Throwable -> 0x00fa }
                        java.lang.String r1 = r1.e     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.aidl.DefaultFinishEvent r2 = r5     // Catch:{ Throwable -> 0x00fa }
                        anetwork.channel.statist.StatisticData r2 = r2.d     // Catch:{ Throwable -> 0x00fa }
                        r0.a(r1, r2)     // Catch:{ Throwable -> 0x00fa }
                    L_0x00f9:
                        return
                    L_0x00fa:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.dx.AnonymousClass3.run():void");
                }
            });
        }
        this.e = null;
    }

    private void a(Runnable runnable) {
        if (this.d.k) {
            runnable.run();
        } else {
            dw.a(this.a != null ? this.a.hashCode() : hashCode(), runnable);
        }
    }
}
