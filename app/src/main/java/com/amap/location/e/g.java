package com.amap.location.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import com.amap.location.common.f.h;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.CellStatus;
import com.amap.location.common.model.CellStatus.HistoryCell;
import com.amap.location.common.model.FPS;
import com.amap.location.common.model.WifiStatus;
import com.amap.location.e.d.e;
import com.amap.location.g.d.a.C0029a;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: LocationContext */
public class g {
    private ReentrantReadWriteLock A = new ReentrantReadWriteLock();
    private com.amap.location.e.c.b B;
    private com.amap.location.e.c.c C;
    private com.amap.location.e.d.d D;
    private Runnable E = new Runnable() {
        public void run() {
            g.this.a(20);
        }
    };
    private c a;
    /* access modifiers changed from: private */
    public Context b;
    private h c;
    private boolean d;
    /* access modifiers changed from: private */
    public FPS e = new FPS();
    private final List<HistoryCell> f = new ArrayList(3);
    private boolean g = true;
    private boolean h = true;
    private boolean i;
    private boolean j;
    private boolean k;
    /* access modifiers changed from: private */
    public boolean l;
    private com.amap.location.e.d.a m;
    private com.amap.location.g.a.a n;
    private LocationManager o;
    private Object p;
    /* access modifiers changed from: private */
    public com.amap.location.g.d.a q;
    private a r;
    private c s;
    private d t;
    private b u;
    private long v;
    /* access modifiers changed from: private */
    public long w;
    private long x;
    /* access modifiers changed from: private */
    public long y;
    private long z;

    /* compiled from: LocationContext */
    final class a extends BroadcastReceiver {
        private a() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                String action = intent.getAction();
                char c = 65535;
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1875733435:
                        if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -1454123155:
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1184851779:
                        if (action.equals("android.location.PROVIDERS_CHANGED")) {
                            c = 5;
                            break;
                        }
                        break;
                    case -1076576821:
                        if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -343630553:
                        if (action.equals("android.net.wifi.STATE_CHANGE")) {
                            c = 4;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        g.this.a(30, 0);
                        return;
                    case 1:
                        g.this.a(30, 1);
                        return;
                    case 2:
                        g.this.a(13, e.a(g.this.b) ^ true ? 1 : 0);
                        return;
                    case 3:
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            g.this.a(12, e.b(g.this.b) ? 1 : 0);
                            return;
                        }
                    case 4:
                        g.this.a(16);
                        return;
                    case 5:
                        g.this.a(17);
                        break;
                }
            }
        }
    }

    /* compiled from: LocationContext */
    final class b extends Handler {
        b(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            boolean z = false;
            if (i == 1) {
                g gVar = g.this;
                int i2 = message.arg1;
                if (message.arg2 == 1) {
                    z = true;
                }
                gVar.b(i2, z);
            } else if (i == 20) {
                g.this.h();
            } else if (i != 30) {
                switch (i) {
                    case 10:
                        g.this.a((List) message.obj);
                        return;
                    case 11:
                        CellLocation cellLocation = null;
                        if (message.arg1 == 1) {
                            cellLocation = (CellLocation) message.obj;
                        }
                        g.this.a(cellLocation);
                        return;
                    case 12:
                        g gVar2 = g.this;
                        if (message.arg1 != 0) {
                            z = true;
                        }
                        gVar2.a(z);
                        return;
                    case 13:
                        g gVar3 = g.this;
                        if (message.arg1 != 0) {
                            z = true;
                        }
                        gVar3.b(z);
                        return;
                    default:
                        switch (i) {
                            case 15:
                                g.this.a((SignalStrength) message.obj);
                                return;
                            case 16:
                                g.this.o();
                                return;
                            case 17:
                                boolean c = g.this.l;
                                g.this.l = g.this.s();
                                if (g.this.l && !c) {
                                    g.this.w = 0;
                                    g.this.y = 0;
                                    break;
                                }
                        }
                        return;
                }
            } else {
                g gVar4 = g.this;
                if (message.arg1 != 0) {
                    z = true;
                }
                gVar4.c(z);
            }
        }
    }

    /* compiled from: LocationContext */
    final class c extends PhoneStateListener {
        private c() {
        }

        public final void onCellLocationChanged(CellLocation cellLocation) {
            g.this.a(11, 1, 0, cellLocation);
        }

        public final void onDataConnectionStateChanged(int i) {
            g.this.a(11);
        }

        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (signalStrength != null && g.this.e.cellStatus.mainCell != null) {
                if (SystemClock.elapsedRealtime() - g.this.e.cellStatus.updateTime >= 1000 || g.this.e.cellStatus.mainCell.signalStrength == 99) {
                    g.this.a(15, 0, 0, signalStrength);
                }
            }
        }

        public final void onCellInfoChanged(List<CellInfo> list) {
            g.this.a(11);
        }
    }

    /* compiled from: LocationContext */
    final class d implements C0029a {
        private d() {
        }

        public final void a() {
            try {
                g.this.a(10, 0, 0, g.this.q.b());
            } catch (SecurityException unused) {
            } catch (Exception unused2) {
            }
        }
    }

    public void a(AmapLoc amapLoc) {
    }

    public g(@NonNull Context context, @NonNull c cVar, @NonNull Looper looper, @NonNull d dVar) {
        this.b = context;
        this.a = cVar;
        a(this.b, looper);
        a(looper);
        this.c = h.a(context, looper, dVar, cVar);
    }

    public void a() {
        this.c.a();
    }

    public void b() {
        this.c.b();
    }

    private void a(Context context, Looper looper) {
        this.n = com.amap.location.g.a.a.a(context);
        this.o = (LocationManager) context.getSystemService("location");
        e();
        this.q = com.amap.location.g.d.a.a(context);
        this.D = new com.amap.location.e.d.d(this.q);
        this.u = new b(looper);
        this.g = !e.a(this.b);
        this.h = e.b(this.b);
        this.k = e.c(this.b);
        this.i = false;
        this.j = false;
        this.l = s();
        this.B = new com.amap.location.e.c.b(context, "cellage", this.u);
        this.C = new com.amap.location.e.c.c(context, "wifiage", this.u);
        o();
    }

    private void e() {
        try {
            switch (e.a()) {
                case 0:
                    this.p = a(this.b, (String) "phone2");
                    break;
                case 1:
                    this.p = a(this.b, (String) "phone_msim");
                    return;
                case 2:
                    this.p = a(this.b, (String) "phone2");
                    return;
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private Object a(Context context, String str) {
        Object obj;
        if (context == null) {
            return null;
        }
        try {
            obj = context.getApplicationContext().getSystemService(str);
        } catch (Throwable unused) {
            obj = null;
        }
        return obj;
    }

    public void a(int i2, boolean z2) {
        a(1, i2, z2 ? 1 : 0, null);
    }

    public void c() {
        this.i = false;
        this.j = false;
        this.B.b();
        this.C.b();
        this.c.c();
        g();
        com.amap.location.common.d.a.b("nlcontext", "remove");
    }

    public void d() {
        this.i = false;
        this.j = false;
        this.f.clear();
        this.m.b();
        c(Looper.myLooper());
        this.A.writeLock().lock();
        b bVar = this.u;
        this.u = null;
        this.A.writeLock().unlock();
        if (bVar != null) {
            bVar.removeCallbacksAndMessages(null);
        }
        this.c.d();
        this.B.c();
        this.C.c();
        com.amap.location.common.d.a.b("nlcontext", "destory");
    }

    /* access modifiers changed from: private */
    public void b(int i2, boolean z2) {
        this.d = z2;
        this.B.a();
        this.C.a();
        if (!this.i) {
            this.i = true;
            if (i2 == 1) {
                i();
            } else if (i2 == 0) {
                d(false);
            }
        } else {
            com.amap.location.common.d.a.b("nlcontext", "req failed as on request");
        }
    }

    private void f() {
        this.m.a(this.a.j.l());
    }

    private void g() {
        this.m.a();
    }

    /* access modifiers changed from: private */
    public void a(List<ScanResult> list) {
        boolean z2;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.y = elapsedRealtime;
        if (list == null || list.size() == 0) {
            a(this.e.wifiStatus);
            if (elapsedRealtime - this.x > 60000) {
                o();
                this.x = elapsedRealtime;
            }
            z2 = false;
        } else {
            this.e.wifiStatus.setWifiList(this.e.wifiStatus.scanResultList2WifiList(list));
            this.e.wifiStatus.updateTime = elapsedRealtime;
            z2 = true;
        }
        ArrayList arrayList = new ArrayList();
        if (this.e.wifiStatus.mainWifi != null) {
            arrayList.add(this.e.wifiStatus.mainWifi);
        }
        if (this.e.wifiStatus.getWifiList() != null) {
            arrayList.addAll(this.e.wifiStatus.getWifiList());
        }
        this.C.a((List<T>) arrayList);
        if (this.j) {
            g();
            this.j = false;
            d(!z2);
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        StringBuilder sb = new StringBuilder("wifi sc timeout:");
        sb.append(this.j);
        com.amap.location.common.d.a.b("nlcontext", sb.toString());
        g();
        if (this.j) {
            this.j = false;
            p();
            d(true);
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z2) {
        com.amap.location.common.d.a.b("nlcontext", "wifi enable:".concat(String.valueOf(z2)));
        if (this.h) {
            this.h = z2;
            if (!z2) {
                d(false);
            }
        } else {
            this.h = z2;
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        com.amap.location.common.d.a.b("nlcontext", "cell enable:".concat(String.valueOf(z2)));
        this.g = z2;
    }

    /* access modifiers changed from: private */
    public void c(boolean z2) {
        this.k = z2;
    }

    /* access modifiers changed from: private */
    public void a(CellLocation cellLocation) {
        StringBuilder sb = new StringBuilder("update cell:");
        sb.append(SystemClock.elapsedRealtime());
        sb.append(",enable:");
        sb.append(this.g);
        com.amap.location.common.d.a.b("nlcontext", sb.toString());
        if (this.g) {
            CellStatus a2 = e.a(this.b, this.n, this.p, this.B, cellLocation, this.e);
            this.z = SystemClock.elapsedRealtime();
            if (!a(this.e.cellStatus.updateTime, (long) this.a.j.d()) || this.e.cellStatus.mainCell == null || a2.mainCell != null) {
                this.e.cellStatus = a2;
            }
        } else {
            this.e.cellStatus = new CellStatus();
        }
        t();
    }

    private void i() {
        if (l() || !this.h) {
            j();
            return;
        }
        q();
        j();
    }

    private void j() {
        if (!a(this.z, (long) this.a.j.d())) {
            if (this.g) {
                a((CellLocation) null);
            } else {
                com.amap.location.common.d.a.c("nlcontext", "cell not fresh and disable");
                this.e.cellStatus = new CellStatus();
            }
        }
        t();
        a(this.e);
    }

    private void d(boolean z2) {
        if (!this.i) {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-onreuqest");
        } else if (l()) {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-ok");
            j();
        } else if (z2) {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-notok,scan timeout");
            j();
        } else if (!this.h) {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-notok, closed");
            this.e.wifiStatus = new WifiStatus();
            j();
        } else if (this.j) {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-onscan");
        } else if (this.k || this.a.j.k()) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            StringBuilder sb = new StringBuilder("wifipre-notok,need scan:");
            sb.append(elapsedRealtime - this.w);
            com.amap.location.common.d.a.b("nlcontext", sb.toString());
            this.v = elapsedRealtime;
            this.w = elapsedRealtime;
            r();
        } else {
            com.amap.location.common.d.a.b("nlcontext", "wifipre-notok,forbid scan");
            q();
            j();
        }
    }

    private void k() {
        this.j = false;
        g();
    }

    private void a(FPS fps) {
        k();
        this.i = false;
        this.c.a(fps, this.d);
        this.d = false;
    }

    private boolean l() {
        if (this.j) {
            return false;
        }
        int e2 = e(m());
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (a(this.w, (long) e2) || (elapsedRealtime - this.y < ((long) this.a.j.g()) && this.y > 0)) {
            return true;
        }
        return false;
    }

    private boolean m() {
        return this.e.wifiStatus.mainWifi != null && this.e.wifiStatus.mainWifi.mac > 0;
    }

    private int e(boolean z2) {
        if (z2) {
            return this.a.j.f();
        }
        return this.a.j.e();
    }

    private boolean a(long j2, long j3) {
        return j2 > 0 && Math.abs(SystemClock.elapsedRealtime() - j2) <= j3;
    }

    private void a(WifiStatus wifiStatus) {
        wifiStatus.updateTime = -3600000;
        wifiStatus.setWifiList(Collections.emptyList());
    }

    /* access modifiers changed from: private */
    public void a(SignalStrength signalStrength) {
        CellStatus cellStatus = this.e.cellStatus;
        if (cellStatus.mainCell != null) {
            int a2 = e.a(signalStrength, this.n, cellStatus.mainCell.type);
            if (a2 != 99) {
                cellStatus.mainCell.signalStrength = a2;
            }
            cellStatus.updateTime = SystemClock.elapsedRealtime();
        }
    }

    private List<ScanResult> n() {
        if (this.q != null) {
            try {
                return this.q.b();
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            } catch (Exception e3) {
                com.amap.location.common.d.a.a((Throwable) e3);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void o() {
        e.a(this.e.wifiStatus, this.q.c(), this.b);
        if (this.e.wifiStatus.mainWifi != null) {
            this.C.a(this.e.wifiStatus.mainWifi);
        }
    }

    private void p() {
        if (SystemClock.elapsedRealtime() - this.y <= this.a.j.m() || this.y <= 0) {
            q();
            StringBuilder sb = new StringBuilder("wifi timeout read wifi:");
            sb.append(this.e.wifiStatus.numWiFis());
            com.amap.location.common.d.a.b("nlcontext", sb.toString());
            return;
        }
        com.amap.location.common.d.a.b("nlcontext", "scan timeout and clear history");
        a(this.e.wifiStatus);
    }

    private void q() {
        List<ScanResult> n2 = n();
        if (n2 == null || n2.size() == 0) {
            a(this.e.wifiStatus);
        } else {
            this.e.wifiStatus.updateTime = SystemClock.elapsedRealtime();
            this.e.wifiStatus.setWifiList(this.e.wifiStatus.scanResultList2WifiList(n2));
            ArrayList arrayList = new ArrayList();
            if (this.e.wifiStatus.mainWifi != null) {
                arrayList.add(this.e.wifiStatus.mainWifi);
            }
            if (this.e.wifiStatus.getWifiList() != null) {
                arrayList.addAll(this.e.wifiStatus.getWifiList());
            }
            this.C.a((List<T>) arrayList);
        }
        StringBuilder sb = new StringBuilder("read wifi:");
        sb.append(SystemClock.elapsedRealtime());
        sb.append(",size:");
        sb.append(this.e.wifiStatus.getWifiList());
        com.amap.location.common.d.a.b("nlcontext", sb.toString());
    }

    private void r() {
        g();
        this.j = true;
        f();
        this.q.a((C0029a) this.t, 0);
    }

    private void a(Looper looper) {
        this.m = new com.amap.location.e.d.a(looper, this.E);
        this.r = new a();
        this.s = new c();
        this.t = new d();
        b(looper);
    }

    private void b(Looper looper) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.location.PROVIDERS_CHANGED");
        this.b.registerReceiver(this.r, intentFilter);
        this.q.a((C0029a) this.t, looper);
        try {
            this.n.a(this.s, 1360, 0, looper);
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    private void c(Looper looper) {
        this.b.unregisterReceiver(this.r);
        this.q.a((C0029a) this.t);
        try {
            this.n.a(this.s, 0, 0, looper);
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    /* access modifiers changed from: private */
    public boolean s() {
        boolean z2 = true;
        try {
            boolean isProviderEnabled = this.o.isProviderEnabled(WidgetType.GPS);
            boolean isProviderEnabled2 = this.o.isProviderEnabled("network");
            if (!isProviderEnabled) {
                if (!isProviderEnabled2) {
                    z2 = false;
                }
            }
            StringBuilder sb = new StringBuilder("loc mode: gps-");
            sb.append(isProviderEnabled);
            sb.append(",network-");
            sb.append(isProviderEnabled2);
            com.amap.location.common.d.a.b("nlcontext", sb.toString());
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
        return z2;
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        this.A.readLock().lock();
        try {
            if (this.u != null) {
                this.u.obtainMessage(i2).sendToTarget();
            }
        } finally {
            this.A.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3) {
        this.A.readLock().lock();
        try {
            if (this.u != null) {
                this.u.obtainMessage(i2, i3, 0).sendToTarget();
            }
        } finally {
            this.A.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3, int i4, Object obj) {
        this.A.readLock().lock();
        try {
            if (this.u != null) {
                this.u.obtainMessage(i2, i3, i4, obj).sendToTarget();
            }
        } finally {
            this.A.readLock().unlock();
        }
    }

    private void t() {
        synchronized (this.f) {
            if (this.e.cellStatus.mainCell != null) {
                h.a(new HistoryCell(this.e.cellStatus.mainCell), this.f, 3);
            }
            if (this.e.cellStatus.mainCell2 != null) {
                h.a(new HistoryCell(this.e.cellStatus.mainCell2), this.f, 3);
            }
            this.e.cellStatus.addHistoryCell(this.f);
        }
    }
}
