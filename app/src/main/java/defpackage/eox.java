package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.interfaces.LocationNmeaListener;
import com.amap.location.sdk.fusion.interfaces.LocationSatelliteListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.jni.ae.pos.LocGPSHistory;
import com.autonavi.jni.ae.pos.LocGPSHistoryInfo;
import com.autonavi.sdk.location.monitor.LocationContext$4;
import com.autonavi.sdk.location.monitor.tools.PriorityList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: eox reason: default package */
/* compiled from: LocationContext */
public final class eox {
    Context a;
    Looper b;
    public a c;
    public ReentrantReadWriteLock d = new ReentrantReadWriteLock();
    b e = new b(this, 0);
    LocationManager f;
    WifiManager g;
    ConnectivityManager h;
    eoz i = new eoz();
    List<epa> j = new PriorityList(60);
    List<epd> k = new PriorityList(100);
    List<epb> l = new PriorityList(60);
    List<epb> m = new PriorityList(60);
    List<epb> n = new PriorityList(60);
    List<epb> o = new PriorityList(60);
    List<epb> p = new PriorityList(60);
    LocationListener q = new LocationListener() {
        public final void onProviderDisabled(String str) {
        }

        public final void onProviderEnabled(String str) {
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public final void onLocationChanged(Location location) {
            if (location != null && location.getProvider().equals(WidgetType.GPS)) {
                eox.this.l.add(new epb(epl.c(), 1));
            }
        }
    };
    LocationSatelliteListener r = new LocationSatelliteListener() {
        public final void onFirstFix(int i) {
        }

        public final void onStarted() {
        }

        public final void onStopped() {
        }

        public final void onSatelliteChanged(int i, int i2, float f) {
            long c = epl.c();
            eox.this.m.add(new epb(c, i2));
            eox.this.n.add(new epb(c, i));
            if (epk.a) {
                StringBuilder sb = new StringBuilder("收到卫星个数：");
                sb.append(i2);
                sb.append(" fix卫星颗数：");
                sb.append(i);
                epk.a("LMLocationContext", sb.toString());
            }
        }
    };
    LocationNmeaListener s = new LocationNmeaListener() {
        private long b = 0;

        public final void onNmeaStringReceived(long j, String str) {
            if (epl.c() - this.b >= 500) {
                this.b = epl.c();
                eox.this.o.add(new epb(j, 1));
                if (epk.a) {
                    epk.a("LMLocationContext", "收到Nmea回调");
                }
            }
        }
    };
    BroadcastReceiver t = new LocationContext$4(this);
    private TelephonyManager u;

    /* renamed from: eox$a */
    /* compiled from: LocationContext */
    public class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message != null) {
                switch (message.what) {
                    case 1:
                        try {
                            List<ScanResult> scanResults = eox.this.g.getScanResults();
                            if (scanResults != null) {
                                eox.this.p.add(new epb(epl.c(), scanResults.size()));
                                if (epk.a) {
                                    StringBuilder sb = new StringBuilder("收到WIFI回调：");
                                    sb.append(scanResults.size());
                                    epk.a("LMLocationContext", sb.toString());
                                }
                            }
                            return;
                        } catch (SecurityException unused) {
                            if (bno.a) {
                                AMapLog.d("LMLocationContext", "have no location permission");
                            }
                            return;
                        }
                    case 2:
                        if (eox.this.f.isProviderEnabled(WidgetType.GPS)) {
                            eox.this.e.a(epl.c(), 1);
                        } else {
                            eox.this.e.b(epl.c(), 1);
                        }
                        if (eox.this.f.isProviderEnabled("network")) {
                            eox.this.e.a(epl.c(), 2);
                            return;
                        } else {
                            eox.this.e.b(epl.c(), 2);
                            return;
                        }
                    case 3:
                        if (3 == message.arg1) {
                            eox.this.e.a(epl.c(), 32);
                            return;
                        } else if (1 == message.arg1) {
                            eox.this.e.b(epl.c(), 32);
                            return;
                        }
                        break;
                    case 4:
                        a();
                        return;
                    case 5:
                        if (!epl.a(eox.this.a)) {
                            eox.this.e.b(epl.c(), 64);
                            break;
                        } else {
                            eox.this.e.a(epl.c(), 64);
                            return;
                        }
                }
            }
        }

        /* access modifiers changed from: private */
        public void a() {
            try {
                NetworkInfo activeNetworkInfo = eox.this.h.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    int type = activeNetworkInfo.getType();
                    if (epk.a) {
                        epk.a("LMLocationContext", "收到连接状态是 ：".concat(String.valueOf(type)));
                    }
                    if (type == 1) {
                        eox.this.e.a(epl.c(), 8);
                        eox.this.e.b(epl.c(), 16);
                    } else if (type == 0) {
                        eox.this.e.a(epl.c(), 16);
                        eox.this.e.b(epl.c(), 8);
                    }
                }
            } catch (Throwable th) {
                epk.a(th);
            }
        }
    }

    /* renamed from: eox$b */
    /* compiled from: LocationContext */
    class b {
        int a;

        private b() {
            this.a = -1;
        }

        /* synthetic */ b(eox eox, byte b2) {
            this();
        }

        /* access modifiers changed from: 0000 */
        public final void a(long j, int i) {
            if (this.a != -1) {
                int i2 = this.a;
                this.a = i | this.a;
                if (this.a != i2) {
                    a(j);
                }
                return;
            }
            if (i == 128) {
                a(j, true);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b(long j, int i) {
            if (this.a != -1) {
                int i2 = this.a;
                this.a = (~i) & this.a;
                if (this.a != i2) {
                    a(j);
                }
                return;
            }
            if (i == 128) {
                a(j, false);
            }
        }

        private void a(long j, boolean z) {
            boolean z2 = false;
            this.a = 0;
            if (z) {
                try {
                    this.a |= 128;
                } catch (Exception e) {
                    if (epk.a) {
                        epk.a(e);
                    }
                    return;
                }
            }
            if (eox.this.f.isProviderEnabled(WidgetType.GPS)) {
                this.a |= 1;
            }
            if (eox.this.f.isProviderEnabled("network")) {
                this.a |= 2;
            }
            if (epl.a(eox.this.a, "android.permission.ACCESS_FINE_LOCATION") || epl.a(eox.this.a, "android.permission.ACCESS_COARSE_LOCATION")) {
                z2 = true;
            }
            if (z2) {
                this.a |= 4;
            }
            if (eox.this.h != null) {
                NetworkInfo activeNetworkInfo = eox.this.h.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    int type = activeNetworkInfo.getType();
                    if (type == 1) {
                        this.a |= 8;
                    } else if (type == 0) {
                        this.a |= 16;
                    }
                }
            }
            if (eox.this.g != null && eox.this.g.isWifiEnabled()) {
                this.a |= 32;
            }
            if (epl.a(eox.this.a)) {
                this.a |= 64;
            }
            a(j);
        }

        private void a(long j) {
            if (this.a != -1) {
                eox.this.k.add(new epd(j, this.a));
            }
            if (epk.a) {
                StringBuilder sb = new StringBuilder("系统状态开关发生变化:");
                sb.append(Integer.toBinaryString(this.a));
                epk.a("LMLocationContext", sb.toString());
            }
        }
    }

    eox(Looper looper, Context context) {
        this.a = context.getApplicationContext();
        this.b = looper;
        this.c = new a(looper);
        this.f = (LocationManager) this.a.getSystemService("location");
        this.g = (WifiManager) this.a.getSystemService("wifi");
        this.u = (TelephonyManager) this.a.getSystemService("phone");
        this.h = (ConnectivityManager) this.a.getSystemService("connectivity");
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        if (z) {
            this.i.l = this.i.m;
        } else {
            this.i.l = epl.c();
        }
        if (epk.a) {
            epk.a("LMLocationContext", "更新日志开始时间：%s，是否为上次日志结束时间：%b", epl.a(this.i.l), Boolean.valueOf(z));
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] a(byte b2) {
        this.i.m = epl.c();
        this.i.k = b2;
        List<epa> list = this.j;
        PriorityList<epa> priorityList = new PriorityList<>(100);
        try {
            LocGPSHistory locGPSHistory = new LocGPSHistory();
            anf.a(locGPSHistory);
            this.i.f = (byte) locGPSHistory.pageType;
            this.i.g = (byte) locGPSHistory.naviType;
            this.i.i = locGPSHistory.naviID;
            this.i.h = (byte) locGPSHistory.routeSource;
            this.i.n = locGPSHistory.timestampNavi;
            this.i.o = locGPSHistory.timestampRoute;
            LocGPSHistoryInfo[] locGPSHistoryInfoArr = locGPSHistory.buffer;
            if (locGPSHistoryInfoArr != null) {
                for (LocGPSHistoryInfo locGPSHistoryInfo : locGPSHistoryInfoArr) {
                    if (locGPSHistoryInfo != null) {
                        epa epa = new epa();
                        epa.a = 1;
                        epa.c = locGPSHistoryInfo.timestamp * 1000;
                        epa.d = locGPSHistoryInfo.timestampSystem;
                        if (locGPSHistoryInfo.gpsPos != null) {
                            epa.e = ((double) locGPSHistoryInfo.gpsPos.lat) / 1000000.0d;
                            epa.f = ((double) locGPSHistoryInfo.gpsPos.lon) / 1000000.0d;
                        }
                        epa.i = locGPSHistoryInfo.speed;
                        epa.j = locGPSHistoryInfo.azi;
                        epa.k = locGPSHistoryInfo.accuracy;
                        epa.l = (byte) locGPSHistoryInfo.checkStatus;
                        epa.m = locGPSHistoryInfo.hasRoute;
                        epa.n = locGPSHistoryInfo.hasRouteMatch;
                        epa.o = locGPSHistoryInfo.matchRouteState;
                        if (locGPSHistoryInfo.matchPos != null) {
                            epa.g = ((double) locGPSHistoryInfo.matchPos.lat) / 1000000.0d;
                            epa.h = ((double) locGPSHistoryInfo.matchPos.lon) / 1000000.0d;
                        }
                        epa.p = locGPSHistoryInfo.formway;
                        epa.q = locGPSHistoryInfo.linkType;
                        epa.r = locGPSHistoryInfo.roadClass;
                        priorityList.add(epa);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        PriorityList<epa> priorityList2 = new PriorityList<>(50);
        if (b2 != 0) {
            int size = priorityList.size() <= 15 ? 0 : priorityList.size() - 15;
            for (int size2 = priorityList.size() - 1; size2 >= size; size2--) {
                priorityList2.add(priorityList.get(size2));
            }
            int size3 = list.size() <= 15 ? 0 : list.size() - 15;
            for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                priorityList2.add(list.get(size4));
            }
            Collections.reverse(priorityList2);
        }
        epc epc = new epc();
        epc.a = epl.c();
        for (epa next : list) {
            if (next.d >= this.i.l) {
                epc.b++;
                if (next.b == 1) {
                    epc.c++;
                } else if (next.b == 2) {
                    epc.d++;
                }
            }
        }
        for (epa epa2 : priorityList) {
            if (epa2.d >= this.i.l) {
                epc.e++;
                switch (epa2.l) {
                    case 1:
                        epc.f++;
                        break;
                    case 2:
                        epc.g++;
                        break;
                    case 3:
                    case 4:
                        epc.h++;
                        break;
                    case 5:
                    case 6:
                        epc.i++;
                        break;
                }
                if (epa2.o == 128) {
                    epc.j++;
                }
            }
        }
        epc.k = epb.a(this.l, this.i.l)[0];
        int[] a2 = epb.a(this.p, this.i.l);
        epc.l = a2[2];
        epc.m = a2[1];
        epc.n = a();
        int[] a3 = epb.a(this.m, this.i.l);
        epc.q = a3[2];
        epc.r = a3[1];
        int[] a4 = epb.a(this.n, this.i.l);
        epc.o = a4[2];
        epc.p = a4[1];
        epc.s = epb.a(this.o, this.i.l)[0];
        if (this.c != null) {
            this.c.a();
        }
        if (epk.a) {
            epk.a("LMLocationContext", "构造FlatBuffer\n\n");
            epk.a("LMLocationContext", ">>头部信息<<<");
            StringBuilder sb = new StringBuilder();
            sb.append(this.i);
            epk.a("LMLocationContext", sb.toString());
            epk.a("LMLocationContext", ">>>位置列表<<<");
            for (epa valueOf : priorityList2) {
                epk.a("LMLocationContext", String.valueOf(valueOf));
            }
            epk.a("LMLocationContext", ">>>开关列表<<<");
            for (epd valueOf2 : this.k) {
                epk.a("LMLocationContext", String.valueOf(valueOf2));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(epc);
            sb2.append("\n\n");
            epk.a("LMLocationContext", sb2.toString());
        }
        byte[] a5 = epe.a(this.i, priorityList2, this.k, epc);
        if (this.k.size() > 0) {
            this.k.clear();
            this.k.add(this.k.get(this.k.size() - 1));
        }
        return a5;
    }

    private boolean a() {
        try {
            if (VERSION.SDK_INT >= 17) {
                List<CellInfo> allCellInfo = this.u.getAllCellInfo();
                if (allCellInfo != null) {
                    for (CellInfo isRegistered : allCellInfo) {
                        if (isRegistered.isRegistered()) {
                            return true;
                        }
                    }
                }
            } else if (this.u.getCellLocation() != null) {
                return true;
            }
        } catch (SecurityException e2) {
            if (epk.a) {
                epk.a(e2);
            }
        }
        return false;
    }
}
