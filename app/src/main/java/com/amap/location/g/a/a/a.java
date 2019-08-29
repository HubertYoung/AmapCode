package com.amap.location.g.a.a;

import android.os.Build.VERSION;
import android.os.Looper;
import android.os.SystemClock;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: PhoneListener */
public class a extends PhoneStateListener {
    private com.amap.location.g.a.a.a.a a;
    private final SparseArray<List<b>> b = new SparseArray<>();
    private final SparseArray<C0019a> c = new SparseArray<>();
    private int d = 0;

    /* renamed from: com.amap.location.g.a.a.a$a reason: collision with other inner class name */
    /* compiled from: PhoneListener */
    static class C0019a {
        public long a;
        public Object b;
        public Object c;

        private C0019a() {
        }
    }

    public a(com.amap.location.g.a.a.a.a aVar) {
        this.a = aVar;
    }

    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        a(1, serviceState, null);
    }

    public void onSignalStrengthChanged(int i) {
        super.onSignalStrengthChanged(i);
        a(2, Integer.valueOf(i), null);
    }

    public void onMessageWaitingIndicatorChanged(boolean z) {
        super.onMessageWaitingIndicatorChanged(z);
        a(4, Boolean.valueOf(z), null);
    }

    public void onCallForwardingIndicatorChanged(boolean z) {
        super.onCallForwardingIndicatorChanged(z);
        a(8, Boolean.valueOf(z), null);
    }

    public void onCellLocationChanged(CellLocation cellLocation) {
        super.onCellLocationChanged(cellLocation);
        a(16, cellLocation, null);
    }

    public void onCallStateChanged(int i, String str) {
        super.onCallStateChanged(i, str);
        a(32, Integer.valueOf(i), str);
    }

    public void onDataConnectionStateChanged(int i) {
        super.onDataConnectionStateChanged(i);
        a(64, Integer.valueOf(i), null);
    }

    public void onDataConnectionStateChanged(int i, int i2) {
        super.onDataConnectionStateChanged(i, i2);
        a(64, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void onDataActivity(int i) {
        super.onDataActivity(i);
        a(128, Integer.valueOf(i), null);
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        a(256, signalStrength, null);
    }

    public void onCellInfoChanged(List<CellInfo> list) {
        super.onCellInfoChanged(list);
        if (VERSION.SDK_INT >= 17) {
            a(1024, list, null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        r1 = r4.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0 = r4.c.get(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        if (r0 != null) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r0 = new com.amap.location.g.a.a.a.C0019a(null);
        r4.c.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        r0.a = android.os.SystemClock.elapsedRealtime();
        r0.b = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0047, code lost:
        if (r7 == null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
        r0.c = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r5, java.lang.Object r6, java.lang.Object r7) {
        /*
            r4 = this;
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            android.util.SparseArray<java.util.List<com.amap.location.g.a.a.b>> r0 = r4.b
            monitor-enter(r0)
            android.util.SparseArray<java.util.List<com.amap.location.g.a.a.b>> r1 = r4.b     // Catch:{ all -> 0x0050 }
            java.lang.Object r1 = r1.get(r5)     // Catch:{ all -> 0x0050 }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x0050 }
            if (r1 != 0) goto L_0x0012
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            return
        L_0x0012:
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0050 }
        L_0x0016:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x0026
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0050 }
            com.amap.location.g.a.a.b r2 = (com.amap.location.g.a.a.b) r2     // Catch:{ all -> 0x0050 }
            r2.a(r5, r6, r7)     // Catch:{ all -> 0x0050 }
            goto L_0x0016
        L_0x0026:
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            android.util.SparseArray<com.amap.location.g.a.a.a$a> r1 = r4.c
            monitor-enter(r1)
            android.util.SparseArray<com.amap.location.g.a.a.a$a> r0 = r4.c     // Catch:{ all -> 0x004d }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x004d }
            com.amap.location.g.a.a.a$a r0 = (com.amap.location.g.a.a.a.C0019a) r0     // Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x003f
            com.amap.location.g.a.a.a$a r0 = new com.amap.location.g.a.a.a$a     // Catch:{ all -> 0x004d }
            r2 = 0
            r0.<init>()     // Catch:{ all -> 0x004d }
            android.util.SparseArray<com.amap.location.g.a.a.a$a> r2 = r4.c     // Catch:{ all -> 0x004d }
            r2.put(r5, r0)     // Catch:{ all -> 0x004d }
        L_0x003f:
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x004d }
            r0.a = r2     // Catch:{ all -> 0x004d }
            r0.b = r6     // Catch:{ all -> 0x004d }
            if (r7 == 0) goto L_0x004b
            r0.c = r7     // Catch:{ all -> 0x004d }
        L_0x004b:
            monitor-exit(r1)     // Catch:{ all -> 0x004d }
            return
        L_0x004d:
            r5 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004d }
            throw r5
        L_0x0050:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.g.a.a.a.a(int, java.lang.Object, java.lang.Object):void");
    }

    public void a(PhoneStateListener phoneStateListener, int i, long j, Looper looper) {
        if (i == 0) {
            synchronized (this.b) {
                int size = this.b.size();
                boolean z = false;
                for (int i2 = 0; i2 < size; i2++) {
                    List valueAt = this.b.valueAt(i2);
                    Iterator it = valueAt.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        b bVar = (b) it.next();
                        if (bVar.a == phoneStateListener) {
                            valueAt.remove(bVar);
                            if (valueAt.isEmpty()) {
                                this.d &= this.b.keyAt(i2) ^ -1;
                                z = true;
                            }
                        }
                    }
                }
                if (z) {
                    this.a.a(this, 0);
                    if (this.d != 0) {
                        this.a.a(this, this.d);
                    }
                }
            }
            return;
        }
        synchronized (this.b) {
            int i3 = this.d;
            if ((i & 1) != 0) {
                a(1, phoneStateListener, j, looper);
            }
            if ((i & 2) != 0) {
                a(2, phoneStateListener, j, looper);
            }
            if ((i & 4) != 0) {
                a(4, phoneStateListener, j, looper);
            }
            if ((i & 8) != 0) {
                a(8, phoneStateListener, j, looper);
            }
            if ((i & 16) != 0) {
                a(16, phoneStateListener, j, looper);
            }
            if ((i & 32) != 0) {
                a(32, phoneStateListener, j, looper);
            }
            if ((i & 64) != 0) {
                a(64, phoneStateListener, j, looper);
            }
            if ((i & 128) != 0) {
                a(128, phoneStateListener, j, looper);
            }
            if ((i & 256) != 0) {
                a(256, phoneStateListener, j, looper);
            }
            if (VERSION.SDK_INT >= 17 && (i & 1024) != 0) {
                a(1024, phoneStateListener, j, looper);
            }
            if (i3 != this.d) {
                if (i3 != 0) {
                    this.a.a(this, 0);
                }
                this.a.a(this, this.d);
            }
        }
    }

    private void a(int i, PhoneStateListener phoneStateListener, long j, Looper looper) {
        List<b> list = this.b.get(i);
        if (list == null) {
            list = new ArrayList<>();
            this.b.put(i, list);
        }
        if (list.isEmpty()) {
            list.add(new b(phoneStateListener, looper));
            this.d = i | this.d;
            return;
        }
        for (b bVar : list) {
            if (bVar.a == phoneStateListener) {
                return;
            }
        }
        b bVar2 = new b(phoneStateListener, looper);
        list.add(bVar2);
        synchronized (this.c) {
            C0019a aVar = this.c.get(i);
            if (aVar != null && SystemClock.elapsedRealtime() - aVar.a <= j) {
                bVar2.a(i, aVar.b, aVar.c);
            }
        }
    }
}
