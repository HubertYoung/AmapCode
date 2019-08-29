package com.amap.location.icecream;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: IcecreamFreezer */
public class g {
    private List<h> a = new ArrayList();
    private List<h> b = new ArrayList();
    /* access modifiers changed from: private */
    public i c = null;
    /* access modifiers changed from: private */
    public e d = null;
    private b e = null;
    private Context f = null;
    private JSONObject g = null;
    /* access modifiers changed from: private */
    public volatile boolean h = false;
    /* access modifiers changed from: private */
    public volatile Handler i = null;

    /* compiled from: IcecreamFreezer */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    g.this.b();
                    return;
                case 2:
                    if (g.this.c != null) {
                        g.this.c.a();
                    }
                    if (g.this.d != null) {
                        g.this.d.a();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: IcecreamFreezer */
    final class b extends HandlerThread {
        public b(String str, int i) {
            super(str, i);
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            g.this.i = new a(Looper.myLooper());
            synchronized (g.this) {
                if (!g.this.h) {
                    g.this.b();
                }
            }
        }
    }

    protected g() {
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r2, org.json.JSONObject r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            r1.f = r2     // Catch:{ all -> 0x002e }
            r1.g = r3     // Catch:{ all -> 0x002e }
            r2 = 0
            r1.h = r2     // Catch:{ all -> 0x002e }
            com.amap.location.icecream.g$b r2 = r1.e     // Catch:{ all -> 0x002e }
            if (r2 != 0) goto L_0x001e
            com.amap.location.icecream.g$b r2 = new com.amap.location.icecream.g$b     // Catch:{ all -> 0x002e }
            java.lang.String r3 = "IcecreamFreezer"
            r0 = 10
            r2.<init>(r3, r0)     // Catch:{ all -> 0x002e }
            r1.e = r2     // Catch:{ all -> 0x002e }
            com.amap.location.icecream.g$b r2 = r1.e     // Catch:{ all -> 0x002e }
            r2.start()     // Catch:{ all -> 0x002e }
            monitor-exit(r1)
            return
        L_0x001e:
            android.os.Handler r2 = r1.i     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x002c
            android.os.Handler r2 = r1.i     // Catch:{ all -> 0x002e }
            r3 = 1
            android.os.Message r2 = r2.obtainMessage(r3)     // Catch:{ all -> 0x002e }
            r2.sendToTarget()     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r1)
            return
        L_0x002e:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.g.a(android.content.Context, org.json.JSONObject):void");
    }

    /* access modifiers changed from: protected */
    public int a(int i2) {
        return IcecreamBaseLibFactory.getIcecreamBaseLib().getAmapBroadcastInstance().getListenerCountByAction(i2);
    }

    public synchronized void a(String str) {
        IcecreamBaseLibFactory.getIcecreamBaseLib().getAmapBroadcastInstance().handleMessage(1, 0, 0, str);
    }

    /* access modifiers changed from: protected */
    public void a(Location location) {
        IcecreamBaseLibFactory.getIcecreamBaseLib().getAmapLocationInstance().onLocationChanged(location);
    }

    /* access modifiers changed from: protected */
    public void b(String str) {
        IcecreamBaseLibFactory.getIcecreamBaseLib().getAmapParamInstance().onParamChanged(str);
    }

    /* access modifiers changed from: protected */
    public synchronized void a() {
        this.h = true;
        if (this.i != null) {
            this.i.obtainMessage(2).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.c = new i(this.f, this.g);
        this.d = new e(this.f);
        this.a.clear();
        this.b.clear();
        f.a(this.f, this.g, this.a, this.b);
        this.c.a(this.a);
        this.d.a(this.b);
    }
}
