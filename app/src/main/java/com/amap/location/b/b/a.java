package com.amap.location.b.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import com.amap.location.b.c.b;
import com.amap.location.b.c.c;
import com.amap.location.b.c.h;
import com.amap.location.b.c.i;
import com.amap.location.b.c.k;
import com.amap.location.common.model.CellStatus.HistoryCell;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: CellCollector */
public class a {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public Handler b;
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock c;
    private TelephonyManager d;
    /* access modifiers changed from: private */
    public CellLocation e;
    /* access modifiers changed from: private */
    public long f;
    /* access modifiers changed from: private */
    public SignalStrength g;
    /* access modifiers changed from: private */
    public boolean h;
    private CellLocation i;
    private CellInfo j;
    private Location k;
    private b l = new b();
    private b m = new b();
    private final List<HistoryCell> n = new ArrayList(3);
    private BroadcastReceiver o = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null) {
                    char c = 65535;
                    if (action.hashCode() == -1076576821 && action.equals("android.intent.action.AIRPLANE_MODE")) {
                        c = 0;
                    }
                    if (c == 0) {
                        a.this.h = !com.amap.location.b.f.b.a(a.this.a);
                        if (!a.this.h) {
                            a.this.e = null;
                            a.this.f = 0;
                        }
                    }
                }
            }
        }
    };
    private PhoneStateListener p = new PhoneStateListener() {
        public void onCellLocationChanged(final CellLocation cellLocation) {
            a.this.c.readLock().lock();
            try {
                if (a.this.b != null) {
                    a.this.b.post(new Runnable() {
                        public void run() {
                            a.this.e = cellLocation;
                            a.this.f = SystemClock.elapsedRealtime();
                            a.this.e();
                        }
                    });
                }
            } finally {
                a.this.c.readLock().unlock();
            }
        }

        public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
            a.this.c.readLock().lock();
            try {
                if (a.this.b != null) {
                    a.this.b.post(new Runnable() {
                        public void run() {
                            a.this.g = signalStrength;
                            a.this.e();
                        }
                    });
                }
            } finally {
                a.this.c.readLock().unlock();
            }
        }

        public void onCellInfoChanged(List<CellInfo> list) {
            a.this.c.readLock().lock();
            try {
                if (a.this.b != null) {
                    a.this.b.post(new Runnable() {
                        public void run() {
                            a.this.e();
                        }
                    });
                }
            } finally {
                a.this.c.readLock().unlock();
            }
        }
    };

    public a(Context context, Looper looper) {
        this.a = context;
        this.d = (TelephonyManager) this.a.getSystemService("phone");
        this.b = new Handler(looper);
        this.c = new ReentrantReadWriteLock();
    }

    public void a() {
        this.h = !com.amap.location.b.f.b.a(this.a);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        try {
            this.a.registerReceiver(this.o, intentFilter, null, this.b);
            if (this.d != null) {
                int i2 = LZMA_Base.kNumLenSymbols;
                if (VERSION.SDK_INT >= 17) {
                    i2 = 1296;
                }
                this.d.listen(this.p, i2);
            }
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    public void b() {
        try {
            this.a.unregisterReceiver(this.o);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.d != null) {
            this.d.listen(this.p, 0);
        }
        this.c.writeLock().lock();
        try {
            this.b.removeCallbacksAndMessages(null);
            this.b = null;
        } finally {
            this.c.writeLock().unlock();
        }
    }

    public b a(Location location) {
        if (!this.h) {
            return null;
        }
        CellLocation c2 = c();
        if ((c2 instanceof CdmaCellLocation) && -1 == ((CdmaCellLocation) c2).getNetworkId()) {
            c2 = null;
        }
        List<CellInfo> d2 = d();
        CellInfo a2 = d2 != null ? com.amap.location.b.f.b.a(d2) : null;
        if (c2 == null && a2 == null) {
            return null;
        }
        if (!(this.k == null || b(location) || !com.amap.location.b.f.b.a(c2, this.i) || !com.amap.location.b.f.b.a(a2, this.j))) {
            return null;
        }
        com.amap.location.b.f.b.a(this.a, this.l, c2, this.g, d2);
        this.i = c2;
        this.j = a2;
        this.k = location;
        com.amap.location.b.d.a.a((List<c>) this.l.c);
        a(this.l);
        return this.l;
    }

    private CellLocation c() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (!((this.e == null || this.f == 0 || elapsedRealtime - this.f > 1500) ? false : true)) {
            try {
                this.e = this.d != null ? this.d.getCellLocation() : null;
                this.f = elapsedRealtime;
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
                this.e = null;
                this.f = 0;
            }
        }
        return this.e;
    }

    private List<CellInfo> d() {
        try {
            if (this.d == null || VERSION.SDK_INT < 17) {
                return null;
            }
            return this.d.getAllCellInfo();
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
            return null;
        }
    }

    private boolean b(Location location) {
        float f2 = location.getSpeed() > 10.0f ? 2000.0f : location.getSpeed() > 2.0f ? 500.0f : 100.0f;
        return location.distanceTo(this.k) > f2;
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.h) {
            try {
                CellLocation c2 = c();
                CellInfo cellInfo = null;
                if ((c2 instanceof CdmaCellLocation) && -1 == ((CdmaCellLocation) c2).getNetworkId()) {
                    c2 = null;
                }
                List<CellInfo> d2 = d();
                if (d2 != null) {
                    cellInfo = com.amap.location.b.f.b.a(d2);
                }
                if (c2 != null || cellInfo != null) {
                    com.amap.location.b.f.b.a(this.a, this.m, c2, this.g, d2);
                    com.amap.location.b.d.a.a((List<c>) this.m.c);
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    private void a(b bVar) {
        synchronized (this.n) {
            Iterator<c> it = bVar.c.iterator();
            while (it.hasNext()) {
                c next = it.next();
                if (1 == next.b) {
                    HistoryCell historyCell = new HistoryCell();
                    historyCell.lastUpdateTimeMills = SystemClock.elapsedRealtime();
                    historyCell.type = next.a;
                    switch (next.a) {
                        case 1:
                            if (next.f == null) {
                                break;
                            } else {
                                h hVar = (h) next.f;
                                if (com.amap.location.common.f.h.a(hVar.c) && com.amap.location.common.f.h.b(hVar.d)) {
                                    historyCell.lac = hVar.c;
                                    historyCell.cid = hVar.d;
                                    historyCell.rssi = hVar.e;
                                    com.amap.location.common.f.h.a(historyCell, this.n, 3);
                                    break;
                                }
                            }
                        case 2:
                            if (next.f == null) {
                                break;
                            } else {
                                com.amap.location.b.c.a aVar = (com.amap.location.b.c.a) next.f;
                                if (com.amap.location.common.f.h.c(aVar.a) && com.amap.location.common.f.h.d(aVar.b) && com.amap.location.common.f.h.e(aVar.c)) {
                                    historyCell.sid = aVar.a;
                                    historyCell.nid = aVar.b;
                                    historyCell.bid = aVar.c;
                                    historyCell.rssi = aVar.f;
                                    com.amap.location.common.f.h.a(historyCell, this.n, 3);
                                    break;
                                }
                            }
                        case 3:
                            if (next.f == null) {
                                break;
                            } else {
                                i iVar = (i) next.f;
                                if (com.amap.location.common.f.h.a(iVar.c) && com.amap.location.common.f.h.b(iVar.d)) {
                                    historyCell.lac = iVar.c;
                                    historyCell.cid = iVar.d;
                                    historyCell.rssi = iVar.f;
                                    com.amap.location.common.f.h.a(historyCell, this.n, 3);
                                    break;
                                }
                            }
                        case 4:
                            if (next.f == null) {
                                break;
                            } else {
                                k kVar = (k) next.f;
                                if (com.amap.location.common.f.h.a(kVar.c) && com.amap.location.common.f.h.b(kVar.d)) {
                                    historyCell.lac = kVar.c;
                                    historyCell.cid = kVar.d;
                                    historyCell.rssi = kVar.f;
                                    com.amap.location.common.f.h.a(historyCell, this.n, 3);
                                    break;
                                }
                            }
                    }
                }
            }
            this.l.d.clear();
            this.l.d.addAll(this.n);
        }
    }
}
