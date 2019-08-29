package com.amap.location.g.b.a.b;

import android.location.GpsStatus.NmeaListener;
import android.location.OnNmeaMessageListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.location.g.b.b;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: NmeaManager */
public class a {
    private final List<C0022a> a = new CopyOnWriteArrayList();
    private com.amap.location.g.b.a.c.a b;
    private OnNmeaMessageListener c;
    private NmeaListener d;

    /* renamed from: com.amap.location.g.b.a.b.a$a reason: collision with other inner class name */
    /* compiled from: NmeaManager */
    static class C0022a {
        b a;
        private Handler b;

        /* renamed from: com.amap.location.g.b.a.b.a$a$a reason: collision with other inner class name */
        /* compiled from: NmeaManager */
        static class C0023a extends Handler {
            private b a;

            C0023a(b bVar, Looper looper) {
                super(looper);
                this.a = bVar;
            }

            public void handleMessage(Message message) {
                Bundle data = message.getData();
                this.a.onNmeaReceived(data.getLong("timestamp"), data.getString("nmea"));
            }
        }

        C0022a(b bVar, Looper looper) {
            this.a = bVar;
            this.b = new C0023a(this.a, looper == null ? Looper.getMainLooper() : looper);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(b bVar, Looper looper) {
            if (looper == null) {
                looper = Looper.getMainLooper();
            }
            return this.a == bVar && this.b.getLooper() == looper;
        }

        /* access modifiers changed from: 0000 */
        public void a(long j, String str) {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.getData().putLong("timestamp", j);
            obtainMessage.getData().putString("nmea", str);
            obtainMessage.sendToTarget();
        }
    }

    public a(com.amap.location.g.b.a.c.a aVar) {
        this.b = aVar;
        if (VERSION.SDK_INT >= 24) {
            this.c = new OnNmeaMessageListener() {
                public void onNmeaMessage(String str, long j) {
                    a.this.a(j, str);
                }
            };
        } else {
            this.d = new NmeaListener() {
                public void onNmeaReceived(long j, String str) {
                    a.this.a(j, str);
                }
            };
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        return r0;
     */
    @android.support.annotation.RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(com.amap.location.g.b.b r5, android.os.Looper r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.util.List<com.amap.location.g.b.a.b.a$a> r1 = r4.a
            monitor-enter(r1)
            com.amap.location.g.b.a.b.a$a r2 = r4.b(r5)     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x0013
            boolean r5 = r2.a(r5, r6)     // Catch:{ all -> 0x0050 }
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            return r5
        L_0x0013:
            com.amap.location.g.b.a.b.a$a r2 = new com.amap.location.g.b.a.b.a$a     // Catch:{ all -> 0x0050 }
            r2.<init>(r5, r6)     // Catch:{ all -> 0x0050 }
            java.util.List<com.amap.location.g.b.a.b.a$a> r5 = r4.a     // Catch:{ all -> 0x0050 }
            r5.add(r2)     // Catch:{ all -> 0x0050 }
            java.util.List<com.amap.location.g.b.a.b.a$a> r5 = r4.a     // Catch:{ all -> 0x0050 }
            int r5 = r5.size()     // Catch:{ all -> 0x0050 }
            r3 = 1
            if (r5 != r3) goto L_0x004e
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0050 }
            r3 = 24
            if (r5 < r3) goto L_0x0039
            android.location.OnNmeaMessageListener r5 = r4.c     // Catch:{ all -> 0x0050 }
            if (r5 == 0) goto L_0x0045
            com.amap.location.g.b.a.c.a r5 = r4.b     // Catch:{ all -> 0x0050 }
            android.location.OnNmeaMessageListener r0 = r4.c     // Catch:{ all -> 0x0050 }
            boolean r0 = r5.a(r0, r6)     // Catch:{ all -> 0x0050 }
            goto L_0x0045
        L_0x0039:
            android.location.GpsStatus$NmeaListener r5 = r4.d     // Catch:{ all -> 0x0050 }
            if (r5 == 0) goto L_0x0045
            com.amap.location.g.b.a.c.a r5 = r4.b     // Catch:{ all -> 0x0050 }
            android.location.GpsStatus$NmeaListener r0 = r4.d     // Catch:{ all -> 0x0050 }
            boolean r0 = r5.a(r0, r6)     // Catch:{ all -> 0x0050 }
        L_0x0045:
            if (r0 != 0) goto L_0x004c
            java.util.List<com.amap.location.g.b.a.b.a$a> r5 = r4.a     // Catch:{ all -> 0x0050 }
            r5.remove(r2)     // Catch:{ all -> 0x0050 }
        L_0x004c:
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            return r0
        L_0x004e:
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            return r3
        L_0x0050:
            r5 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.g.b.a.b.a.a(com.amap.location.g.b.b, android.os.Looper):boolean");
    }

    public void a(b bVar) {
        if (bVar != null) {
            synchronized (this.a) {
                C0022a b2 = b(bVar);
                if (b2 != null) {
                    this.a.remove(b2);
                    if (this.a.size() == 0) {
                        if (VERSION.SDK_INT >= 24) {
                            if (this.c != null) {
                                this.b.a(this.c);
                            }
                        } else if (this.d != null) {
                            this.b.a(this.d);
                        }
                    }
                }
            }
        }
    }

    private C0022a b(b bVar) {
        for (C0022a next : this.a) {
            if (next.a == bVar) {
                return next;
            }
        }
        return null;
    }

    public void a(long j, String str) {
        synchronized (this.a) {
            for (C0022a a2 : this.a) {
                a2.a(j, str);
            }
        }
    }
}
