package defpackage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.IRemoteNetworkGetter.Stub;
import java.util.concurrent.CountDownLatch;

/* renamed from: dn reason: default package */
/* compiled from: RemoteGetterHelper */
public class dn {
    static volatile IRemoteNetworkGetter a = null;
    static volatile boolean b = false;
    static volatile boolean c = false;
    static volatile CountDownLatch d;
    static Handler e = new Handler(Looper.getMainLooper());
    private static ServiceConnection f = new ServiceConnection() {
        public final void onServiceDisconnected(ComponentName componentName) {
            if (cl.a(2)) {
                cl.b("anet.RemoteGetter", "ANet_Service Disconnected", null, new Object[0]);
            }
            dn.a = null;
            dn.c = false;
            if (dn.d != null) {
                dn.d.countDown();
            }
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (cl.a(2)) {
                cl.b("anet.RemoteGetter", "[onServiceConnected]ANet_Service start success. ANet run with service mode", null, new Object[0]);
            }
            synchronized (dn.class) {
                dn.a = Stub.asInterface(iBinder);
                if (dn.d != null) {
                    dn.d.countDown();
                }
            }
            dn.b = false;
            dn.c = false;
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        defpackage.cl.b("anet.RemoteGetter", "[initRemoteGetterAndWait]begin to wait", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b0, code lost:
        if (d.await((long) defpackage.ds.e(), java.util.concurrent.TimeUnit.SECONDS) == false) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b2, code lost:
        defpackage.cl.b("anet.RemoteGetter", "mServiceBindLock count down to 0", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bb, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00bc, code lost:
        defpackage.cl.b("anet.RemoteGetter", "mServiceBindLock wait timeout", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c5, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r6, boolean r7) {
        /*
            anetwork.channel.aidl.IRemoteNetworkGetter r0 = a
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            boolean r0 = b
            if (r0 != 0) goto L_0x00d2
            r0 = 2
            boolean r0 = defpackage.cl.a(r0)
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = "anet.RemoteGetter"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "[asyncBindService] mContext:"
            r3.<init>(r4)
            r3.append(r6)
            java.lang.String r4 = " bBindFailed:"
            r3.append(r4)
            boolean r4 = b
            r3.append(r4)
            java.lang.String r4 = " bBinding:"
            r3.append(r4)
            boolean r4 = c
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r2]
            defpackage.cl.b(r0, r3, r1, r4)
        L_0x003b:
            r0 = 1
            if (r6 == 0) goto L_0x007f
            boolean r3 = b
            if (r3 != 0) goto L_0x007f
            boolean r3 = c
            if (r3 != 0) goto L_0x007f
            c = r0
            android.content.Intent r3 = new android.content.Intent
            java.lang.Class<anetwork.channel.aidl.NetworkService> r4 = anetwork.channel.aidl.NetworkService.class
            r3.<init>(r6, r4)
            java.lang.Class<anetwork.channel.aidl.IRemoteNetworkGetter> r4 = anetwork.channel.aidl.IRemoteNetworkGetter.class
            java.lang.String r4 = r4.getName()
            r3.setAction(r4)
            java.lang.String r4 = "android.intent.category.DEFAULT"
            r3.addCategory(r4)
            android.content.ServiceConnection r4 = f
            boolean r6 = r6.bindService(r3, r4, r0)
            r6 = r6 ^ r0
            b = r6
            if (r6 == 0) goto L_0x0073
            c = r2
            java.lang.String r6 = "anet.RemoteGetter"
            java.lang.String r3 = "[asyncBindService]ANet_Service start not success. ANet run with local mode!"
            java.lang.Object[] r4 = new java.lang.Object[r2]
            defpackage.cl.c(r6, r3, r1, r4)
        L_0x0073:
            android.os.Handler r6 = e
            dn$2 r3 = new dn$2
            r3.<init>()
            r4 = 10000(0x2710, double:4.9407E-320)
            r6.postDelayed(r3, r4)
        L_0x007f:
            boolean r6 = b
            if (r6 != 0) goto L_0x00d2
            if (r7 == 0) goto L_0x00d2
            java.lang.Class<dn> r6 = defpackage.dn.class
            monitor-enter(r6)     // Catch:{ InterruptedException -> 0x00c9 }
            anetwork.channel.aidl.IRemoteNetworkGetter r7 = a     // Catch:{ all -> 0x00c6 }
            if (r7 == 0) goto L_0x008e
            monitor-exit(r6)     // Catch:{ all -> 0x00c6 }
            return
        L_0x008e:
            java.util.concurrent.CountDownLatch r7 = d     // Catch:{ all -> 0x00c6 }
            if (r7 != 0) goto L_0x0099
            java.util.concurrent.CountDownLatch r7 = new java.util.concurrent.CountDownLatch     // Catch:{ all -> 0x00c6 }
            r7.<init>(r0)     // Catch:{ all -> 0x00c6 }
            d = r7     // Catch:{ all -> 0x00c6 }
        L_0x0099:
            monitor-exit(r6)     // Catch:{ all -> 0x00c6 }
            java.lang.String r6 = "anet.RemoteGetter"
            java.lang.String r7 = "[initRemoteGetterAndWait]begin to wait"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x00c9 }
            defpackage.cl.b(r6, r7, r1, r0)     // Catch:{ InterruptedException -> 0x00c9 }
            java.util.concurrent.CountDownLatch r6 = d     // Catch:{ InterruptedException -> 0x00c9 }
            int r7 = defpackage.ds.e()     // Catch:{ InterruptedException -> 0x00c9 }
            long r3 = (long) r7     // Catch:{ InterruptedException -> 0x00c9 }
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x00c9 }
            boolean r6 = r6.await(r3, r7)     // Catch:{ InterruptedException -> 0x00c9 }
            if (r6 == 0) goto L_0x00bc
            java.lang.String r6 = "anet.RemoteGetter"
            java.lang.String r7 = "mServiceBindLock count down to 0"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x00c9 }
            defpackage.cl.b(r6, r7, r1, r0)     // Catch:{ InterruptedException -> 0x00c9 }
            return
        L_0x00bc:
            java.lang.String r6 = "anet.RemoteGetter"
            java.lang.String r7 = "mServiceBindLock wait timeout"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ InterruptedException -> 0x00c9 }
            defpackage.cl.b(r6, r7, r1, r0)     // Catch:{ InterruptedException -> 0x00c9 }
            return
        L_0x00c6:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00c6 }
            throw r7     // Catch:{ InterruptedException -> 0x00c9 }
        L_0x00c9:
            java.lang.String r6 = "anet.RemoteGetter"
            java.lang.String r7 = "mServiceBindLock wait interrupt"
            java.lang.Object[] r0 = new java.lang.Object[r2]
            defpackage.cl.d(r6, r7, r1, r0)
        L_0x00d2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dn.a(android.content.Context, boolean):void");
    }

    public static IRemoteNetworkGetter a() {
        return a;
    }
}
