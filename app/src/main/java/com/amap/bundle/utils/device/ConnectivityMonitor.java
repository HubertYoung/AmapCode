package com.amap.bundle.utils.device;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.os.Build.VERSION;
import android.os.Looper;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectivityMonitor {
    private static volatile ConnectivityMonitor a;
    private int b = 0;
    private List<a> c = new CopyOnWriteArrayList();
    private ConnectivityChangeReceiver d;
    private b e;
    /* access modifiers changed from: private */
    public Context f;

    class ConnectivityChangeReceiver extends BroadcastReceiver {
        ConnectivityChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                ConnectivityMonitor.this.a(agr.a(context));
            }
        }
    }

    public static abstract class a {
        public abstract void onConnectivityChanged(int i, int i2);
    }

    @TargetApi(23)
    class b extends NetworkCallback {
        b() {
        }

        public final void onAvailable(Network network) {
            a();
        }

        public final void onLost(Network network) {
            a();
        }

        private void a() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                ConnectivityMonitor.this.a(agr.a(ConnectivityMonitor.this.f));
            } else {
                aho.a(new Runnable() {
                    public final void run() {
                        ConnectivityMonitor.this.a(agr.a(ConnectivityMonitor.this.f));
                    }
                });
            }
        }
    }

    private ConnectivityMonitor() {
    }

    public final void a(a aVar) {
        if (aVar != null) {
            this.c.add(aVar);
        }
    }

    public final void b(a aVar) {
        if (aVar != null) {
            this.c.remove(aVar);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0059, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.content.Context r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            android.content.Context r0 = r2.f     // Catch:{ all -> 0x005a }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r2.f = r3     // Catch:{ all -> 0x005a }
            int r3 = defpackage.agr.a(r3)     // Catch:{ all -> 0x005a }
            r2.b = r3     // Catch:{ all -> 0x005a }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x005a }
            r0 = 24
            if (r3 >= r0) goto L_0x0033
            com.amap.bundle.utils.device.ConnectivityMonitor$ConnectivityChangeReceiver r3 = r2.d     // Catch:{ all -> 0x005a }
            if (r3 != 0) goto L_0x0020
            com.amap.bundle.utils.device.ConnectivityMonitor$ConnectivityChangeReceiver r3 = new com.amap.bundle.utils.device.ConnectivityMonitor$ConnectivityChangeReceiver     // Catch:{ all -> 0x005a }
            r3.<init>()     // Catch:{ all -> 0x005a }
            r2.d = r3     // Catch:{ all -> 0x005a }
        L_0x0020:
            android.content.IntentFilter r3 = new android.content.IntentFilter     // Catch:{ all -> 0x005a }
            r3.<init>()     // Catch:{ all -> 0x005a }
            java.lang.String r0 = "android.net.conn.CONNECTIVITY_CHANGE"
            r3.addAction(r0)     // Catch:{ all -> 0x005a }
            android.content.Context r0 = r2.f     // Catch:{ all -> 0x005a }
            com.amap.bundle.utils.device.ConnectivityMonitor$ConnectivityChangeReceiver r1 = r2.d     // Catch:{ all -> 0x005a }
            r0.registerReceiver(r1, r3)     // Catch:{ all -> 0x005a }
            monitor-exit(r2)
            return
        L_0x0033:
            com.amap.bundle.utils.device.ConnectivityMonitor$b r3 = r2.e     // Catch:{ all -> 0x005a }
            if (r3 != 0) goto L_0x003e
            com.amap.bundle.utils.device.ConnectivityMonitor$b r3 = new com.amap.bundle.utils.device.ConnectivityMonitor$b     // Catch:{ all -> 0x005a }
            r3.<init>()     // Catch:{ all -> 0x005a }
            r2.e = r3     // Catch:{ all -> 0x005a }
        L_0x003e:
            android.content.Context r3 = r2.f     // Catch:{ all -> 0x005a }
            java.lang.String r0 = "connectivity"
            java.lang.Object r3 = r3.getSystemService(r0)     // Catch:{ all -> 0x005a }
            android.net.ConnectivityManager r3 = (android.net.ConnectivityManager) r3     // Catch:{ all -> 0x005a }
            if (r3 == 0) goto L_0x0058
            android.net.NetworkRequest$Builder r0 = new android.net.NetworkRequest$Builder     // Catch:{ all -> 0x005a }
            r0.<init>()     // Catch:{ all -> 0x005a }
            android.net.NetworkRequest r0 = r0.build()     // Catch:{ all -> 0x005a }
            com.amap.bundle.utils.device.ConnectivityMonitor$b r1 = r2.e     // Catch:{ all -> 0x005a }
            r3.registerNetworkCallback(r0, r1)     // Catch:{ all -> 0x005a }
        L_0x0058:
            monitor-exit(r2)
            return
        L_0x005a:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.utils.device.ConnectivityMonitor.a(android.content.Context):void");
    }

    public final synchronized void b(Context context) {
        if (this.f != null) {
            if (this.f == context) {
                if (VERSION.SDK_INT >= 24) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) this.f.getSystemService("connectivity");
                    if (!(connectivityManager == null || this.e == null)) {
                        connectivityManager.unregisterNetworkCallback(this.e);
                    }
                } else if (this.d != null) {
                    this.f.unregisterReceiver(this.d);
                }
                this.f = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(int i) {
        int i2 = this.b;
        this.b = i;
        for (a onConnectivityChanged : this.c) {
            onConnectivityChanged.onConnectivityChanged(i, i2);
        }
    }

    public static ConnectivityMonitor a() {
        if (a == null) {
            synchronized (ConnectivityMonitor.class) {
                if (a == null) {
                    a = new ConnectivityMonitor();
                }
            }
        }
        return a;
    }
}
