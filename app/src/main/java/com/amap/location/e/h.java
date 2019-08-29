package com.amap.location.e;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Base64;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import com.amap.location.e.d.e;
import com.amap.location.f.a.c;
import com.amap.location.offline.d;
import com.amap.location.protocol.LocationRequest;
import com.amap.location.protocol.f;
import com.amap.location.protocol.g;
import com.amap.location.uptunnel.UpTunnel;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONObject;

/* compiled from: LocationRequestManager */
public class h {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public c b;
    /* access modifiers changed from: private */
    public d c;
    /* access modifiers changed from: private */
    public c d;
    /* access modifiers changed from: private */
    public g e;
    /* access modifiers changed from: private */
    public d f;
    /* access modifiers changed from: private */
    public com.amap.location.e.a.a g;
    /* access modifiers changed from: private */
    public LocationRequest h;
    /* access modifiers changed from: private */
    public AmapLoc i;
    /* access modifiers changed from: private */
    public com.amap.location.e.d.a j;
    /* access modifiers changed from: private */
    public volatile Handler k;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public int m;
    /* access modifiers changed from: private */
    public AmapLoc n;
    /* access modifiers changed from: private */
    public FPS o;
    /* access modifiers changed from: private */
    public long p;
    /* access modifiers changed from: private */
    public AmapLoc q;
    /* access modifiers changed from: private */
    public long r;
    /* access modifiers changed from: private */
    public long s;
    /* access modifiers changed from: private */
    public long t = 0;
    /* access modifiers changed from: private */
    public f u = new f() {
        public void a(com.amap.location.protocol.d.a aVar) {
            h.this.l.readLock().lock();
            try {
                if (h.this.k != null) {
                    h.this.k.obtainMessage(3, aVar).sendToTarget();
                }
            } finally {
                h.this.l.readLock().unlock();
            }
        }

        public void a(LocationRequest locationRequest, ResponseException responseException) {
            StringBuilder sb = new StringBuilder("locing-online failed：");
            sb.append(responseException != null ? Integer.valueOf(responseException.errorCode) : "null error");
            com.amap.location.common.d.a.c("nlmgr", sb.toString());
            h.this.l.readLock().lock();
            try {
                if (h.this.k != null) {
                    h.this.k.obtainMessage(2, locationRequest).sendToTarget();
                }
            } finally {
                h.this.l.readLock().unlock();
            }
        }
    };
    private Runnable v = new Runnable() {
        public void run() {
            com.amap.location.common.d.a.c("nlmgr", "locing-online timeout");
            h.this.l.readLock().lock();
            try {
                if (h.this.k != null) {
                    h.this.k.obtainMessage(2).sendToTarget();
                }
            } finally {
                h.this.l.readLock().unlock();
            }
        }
    };
    private LocationListener w = new LocationListener() {
        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onLocationChanged(Location location) {
            if (location != null && location.getProvider().equals(WidgetType.GPS)) {
                h.this.g.a(location);
            }
        }
    };

    /* compiled from: LocationRequestManager */
    class a {
        private LinkedList<Long> b = new LinkedList<>();

        a() {
        }

        public void a() {
            if (this.b.size() == 3) {
                this.b.removeFirst();
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.b.add(Long.valueOf(elapsedRealtime));
            if (this.b.size() == 3) {
                boolean z = true;
                Iterator it = this.b.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (elapsedRealtime - ((Long) it.next()).longValue() > 60000) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z) {
                    h.this.t = elapsedRealtime + 10000;
                    StringBuilder sb = new StringBuilder("enter active decelerate：");
                    sb.append(h.this.t);
                    com.amap.location.common.d.a.c("nlmgr", sb.toString());
                }
            }
        }

        public void b() {
            this.b.clear();
        }
    }

    /* compiled from: LocationRequestManager */
    class b extends Handler {
        private a b = new a();

        public b(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: Removed duplicated region for block: B:36:0x013c  */
        /* JADX WARNING: Removed duplicated region for block: B:76:0x0266  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r11) {
            /*
                r10 = this;
                int r0 = r11.what
                r1 = 0
                r2 = 1
                r3 = 0
                switch(r0) {
                    case 0: goto L_0x036b;
                    case 1: goto L_0x01dc;
                    case 2: goto L_0x0190;
                    case 3: goto L_0x000a;
                    default: goto L_0x0008;
                }
            L_0x0008:
                goto L_0x0378
            L_0x000a:
                java.lang.Object r11 = r11.obj
                com.amap.location.protocol.d.a r11 = (com.amap.location.protocol.d.a) r11
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                if (r0 == 0) goto L_0x0378
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                bph r4 = r11.getRequest()
                if (r0 != r4) goto L_0x0378
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.e.d.a r0 = r0.j
                r0.a()
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                com.amap.location.common.model.FPS r0 = r0.a()
                com.amap.location.e.h r4 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r4 = r4.h
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                r5.h = r3
                java.lang.Object r3 = r11.getResult()
                com.amap.location.common.model.AmapLoc r3 = (com.amap.location.common.model.AmapLoc) r3
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                int r5 = r5.m
                r6 = 5
                if (r5 >= r6) goto L_0x00a6
                if (r3 == 0) goto L_0x00a6
                boolean r5 = r3.isServerParseRequestError()
                if (r5 == 0) goto L_0x00a6
                java.lang.String r5 = "nlmgr"
                java.lang.String r6 = "server parse error"
                com.amap.location.common.d.a.c(r5, r6)
                com.amap.location.e.b.c r5 = com.amap.location.e.b.d.d()     // Catch:{ Exception -> 0x00a2 }
                if (r5 == 0) goto L_0x00a6
                org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x00a2 }
                r6.<init>()     // Catch:{ Exception -> 0x00a2 }
                java.lang.String r7 = "csid"
                java.lang.String r8 = r4.j()     // Catch:{ Exception -> 0x00a2 }
                r6.put(r7, r8)     // Catch:{ Exception -> 0x00a2 }
                java.lang.String r7 = "loc"
                org.json.JSONObject r8 = r3.toJSONObject(r2)     // Catch:{ Exception -> 0x00a2 }
                r6.put(r7, r8)     // Catch:{ Exception -> 0x00a2 }
                java.lang.String r7 = "request"
                com.amap.location.protocol.b.b r8 = r4.i()     // Catch:{ Exception -> 0x00a2 }
                org.json.JSONObject r8 = r8.b()     // Catch:{ Exception -> 0x00a2 }
                r6.put(r7, r8)     // Catch:{ Exception -> 0x00a2 }
                java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00a2 }
                java.lang.String r7 = "UTF-8"
                byte[] r6 = r6.getBytes(r7)     // Catch:{ Exception -> 0x00a2 }
                r7 = 2
                java.lang.String r6 = android.util.Base64.encodeToString(r6, r7)     // Catch:{ Exception -> 0x00a2 }
                r5.a(r6)     // Catch:{ Exception -> 0x00a2 }
                com.amap.location.e.h r5 = com.amap.location.e.h.this     // Catch:{ Exception -> 0x00a2 }
                r5.m = r5.m + 1     // Catch:{ Exception -> 0x00a2 }
                goto L_0x00a6
            L_0x00a2:
                r5 = move-exception
                com.amap.location.common.d.a.a(r5)
            L_0x00a6:
                if (r3 == 0) goto L_0x00de
                java.lang.String r5 = r3.getRdesc()
                java.lang.String r6 = "85"
                java.lang.String r5 = r5.trim()
                boolean r5 = r6.equals(r5)
                if (r5 == 0) goto L_0x00de
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                long r6 = android.os.SystemClock.elapsedRealtime()
                r8 = 60000(0xea60, double:2.9644E-319)
                long r6 = r6 + r8
                r5.t = r6
                java.lang.String r5 = "nlmgr"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r7 = "enter passtive decelerate："
                r6.<init>(r7)
                com.amap.location.e.h r7 = com.amap.location.e.h.this
                long r7 = r7.t
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                com.amap.location.common.d.a.c(r5, r6)
            L_0x00de:
                if (r3 == 0) goto L_0x0182
                boolean r5 = r3.isLocationCorrect()
                if (r5 == 0) goto L_0x0182
                com.amap.location.e.h$a r5 = r10.b
                r5.b()
                java.lang.String r5 = r11.b()
                boolean r5 = android.text.TextUtils.isEmpty(r5)
                if (r5 != 0) goto L_0x00fc
                java.lang.String r11 = r11.b()
                r3.setServerTraceId(r11)
            L_0x00fc:
                r3.setIsLast(r1)
                java.lang.String r11 = "nlmgr"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                java.lang.String r6 = "locing-onlineloc report："
                r5.<init>(r6)
                java.lang.String r6 = com.amap.location.common.d.b.a(r3)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                com.amap.location.common.d.a.b(r11, r5)
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r11 = r11.q
                if (r11 != 0) goto L_0x0124
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                r11.a(r4, r3, r0)
                goto L_0x0139
            L_0x0124:
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r11 = r11.q
                float r11 = com.amap.location.common.f.d.a(r11, r3)
                r5 = 1092616192(0x41200000, float:10.0)
                int r11 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r11 <= 0) goto L_0x013a
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                r11.a(r4, r3, r0)
            L_0x0139:
                r1 = 1
            L_0x013a:
                if (r1 == 0) goto L_0x0145
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.offline.d r11 = r11.f
                r11.a(r0, r3)
            L_0x0145:
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                long r1 = android.os.SystemClock.elapsedRealtime()
                r11.r = r1
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.f.a.c r11 = r11.d
                r11.a(r0, r3)
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r1 = new com.amap.location.common.model.AmapLoc
                r1.<init>(r3)
                r11.n = r1
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r11 = r11.n
                java.lang.String r1 = "mem"
                r11.setType(r1)
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                r11.o = r0
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.e.d r11 = r11.c
                r11.a()
                com.amap.location.protocol.a.a r11 = com.amap.location.protocol.a.a.a()
                r11.add(r3)
                goto L_0x0185
            L_0x0182:
                r10.a(r4, r0)
            L_0x0185:
                if (r3 == 0) goto L_0x0378
                org.json.JSONObject r11 = r3.getExtra()
                com.amap.location.e.d.c.a(r11)
                goto L_0x0378
            L_0x0190:
                java.lang.Object r11 = r11.obj
                com.amap.location.protocol.LocationRequest r11 = (com.amap.location.protocol.LocationRequest) r11
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                if (r0 == 0) goto L_0x0378
                if (r11 == 0) goto L_0x01a6
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                if (r0 != r11) goto L_0x0378
            L_0x01a6:
                com.amap.location.e.h$a r11 = r10.b
                r11.a()
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.e.d.a r11 = r11.j
                r11.a()
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.protocol.g r11 = r11.e
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                r11.a(r0)
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r11 = r11.h
                com.amap.location.common.model.FPS r11 = r11.a()
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                com.amap.location.e.h r1 = com.amap.location.e.h.this
                r1.h = r3
                r10.a(r0, r11)
                return
            L_0x01dc:
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                r0.q = r3
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.protocol.LocationRequest r0 = r0.h
                if (r0 == 0) goto L_0x01f1
                java.lang.String r11 = "nlmgr"
                java.lang.String r0 = "locing-onrequest"
                com.amap.location.common.d.a.b(r11, r0)
                return
            L_0x01f1:
                java.lang.Object r11 = r11.obj
                com.amap.location.common.model.FPS r11 = (com.amap.location.common.model.FPS) r11
                boolean r0 = com.amap.location.e.d.e.a(r11)
                if (r0 != 0) goto L_0x0211
                java.lang.String r0 = "nlmgr"
                java.lang.String r1 = "locing-fps not enough"
                com.amap.location.common.d.a.c(r0, r1)
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                r0.a(r3, r3, r11)
                com.amap.location.e.h r11 = com.amap.location.e.h.this
                com.amap.location.e.d r11 = r11.c
                r11.a()
                return
            L_0x0211:
                boolean r0 = r10.a()
                if (r0 != 0) goto L_0x02fa
                boolean r4 = r10.d(r11)
                if (r4 == 0) goto L_0x0228
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r5 = r5.n
                r5.setIsLast(r2)
            L_0x0226:
                r2 = 0
                goto L_0x025c
            L_0x0228:
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                com.amap.location.e.c r5 = r5.b
                com.amap.location.e.e r5 = r5.j
                boolean r5 = r5.a()
                if (r5 == 0) goto L_0x0241
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                com.amap.location.offline.d r5 = r5.f
                com.amap.location.common.model.AmapLoc r5 = r5.a(r11, r2)
                goto L_0x0242
            L_0x0241:
                r5 = r3
            L_0x0242:
                if (r5 == 0) goto L_0x024a
                boolean r6 = r5.isLocationCorrect()
                if (r6 != 0) goto L_0x0226
            L_0x024a:
                com.amap.location.e.h r6 = com.amap.location.e.h.this
                com.amap.location.e.c r6 = r6.b
                com.amap.location.e.e r6 = r6.j
                boolean r6 = r6.b()
                if (r6 == 0) goto L_0x0226
                com.amap.location.common.model.AmapLoc r5 = r10.c(r11)
            L_0x025c:
                if (r5 == 0) goto L_0x02fb
                boolean r6 = r5.isLocationCorrect()
                if (r6 == 0) goto L_0x02fb
                if (r4 != 0) goto L_0x028f
                r5.setIsLast(r1)
                if (r2 != 0) goto L_0x0281
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r1 = new com.amap.location.common.model.AmapLoc
                r1.<init>(r5)
                r0.n = r1
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.common.model.AmapLoc r0 = r0.n
                java.lang.String r1 = "mem"
                r0.setType(r1)
                goto L_0x0286
            L_0x0281:
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                r0.n = r5
            L_0x0286:
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.common.model.FPS r1 = r11.clone()
                r0.o = r1
            L_0x028f:
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                r0.a(r3, r5, r11)
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.e.d r0 = r0.c
                r0.a()
                com.amap.location.e.b.c r0 = com.amap.location.e.b.d.d()
                if (r0 == 0) goto L_0x02aa
                if (r4 != 0) goto L_0x02aa
                if (r2 == 0) goto L_0x02aa
                r10.a(r5, r11)
            L_0x02aa:
                java.lang.String r0 = "nlmgr"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r3 = "locing-noonline last:"
                r1.<init>(r3)
                r1.append(r4)
                java.lang.String r3 = " querycache:"
                r1.append(r3)
                r1.append(r2)
                java.lang.String r2 = " offenable:"
                r1.append(r2)
                com.amap.location.e.h r2 = com.amap.location.e.h.this
                com.amap.location.e.c r2 = r2.b
                com.amap.location.e.e r2 = r2.j
                boolean r2 = r2.a()
                r1.append(r2)
                java.lang.String r2 = " cacheenable:"
                r1.append(r2)
                com.amap.location.e.h r2 = com.amap.location.e.h.this
                com.amap.location.e.c r2 = r2.b
                com.amap.location.e.e r2 = r2.j
                boolean r2 = r2.b()
                r1.append(r2)
                java.lang.String r2 = " fps:"
                r1.append(r2)
                java.lang.String r11 = r10.a(r11)
                r1.append(r11)
                java.lang.String r11 = r1.toString()
                com.amap.location.common.d.a.b(r0, r11)
                return
            L_0x02fa:
                r2 = 0
            L_0x02fb:
                java.lang.String r3 = "nlmgr"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "locing-online force:"
                r4.<init>(r5)
                r4.append(r0)
                java.lang.String r5 = " querycache:"
                r4.append(r5)
                r4.append(r2)
                java.lang.String r5 = " offenable:"
                r4.append(r5)
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                com.amap.location.e.c r5 = r5.b
                com.amap.location.e.e r5 = r5.j
                boolean r5 = r5.a()
                r4.append(r5)
                java.lang.String r5 = " cacheenable:"
                r4.append(r5)
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                com.amap.location.e.c r5 = r5.b
                com.amap.location.e.e r5 = r5.j
                boolean r5 = r5.b()
                r4.append(r5)
                java.lang.String r5 = " lasttime:"
                r4.append(r5)
                com.amap.location.e.h r5 = com.amap.location.e.h.this
                long r5 = r5.s
                r4.append(r5)
                java.lang.String r5 = " fps:"
                r4.append(r5)
                java.lang.String r5 = r10.a(r11)
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.amap.location.common.d.a.b(r3, r4)
                com.amap.location.common.model.FPS r3 = r11.clone()
                r10.a(r3, r1, r2)
                if (r0 == 0) goto L_0x0378
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                com.amap.location.offline.d r0 = r0.f
                r0.a(r11)
                return
            L_0x036b:
                com.amap.location.e.h r0 = com.amap.location.e.h.this
                r0.q = r3
                java.lang.Object r11 = r11.obj
                com.amap.location.common.model.FPS r11 = (com.amap.location.common.model.FPS) r11
                r10.b(r11)
                return
            L_0x0378:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.location.e.h.b.handleMessage(android.os.Message):void");
        }

        private String a(FPS fps) {
            if (fps == null) {
                return "";
            }
            boolean z = false;
            boolean z2 = fps.cellStatus.mainCell != null;
            int size = fps.cellStatus.neighbors.size();
            int size2 = fps.cellStatus.cellStateList2.size();
            if (fps.wifiStatus.mainWifi != null) {
                z = true;
            }
            int numWiFis = fps.wifiStatus.numWiFis();
            StringBuilder sb = new StringBuilder("[");
            sb.append(z2);
            sb.append(",");
            sb.append(size);
            sb.append(",");
            sb.append(size2);
            sb.append(",");
            sb.append(z);
            sb.append(",");
            sb.append(numWiFis);
            sb.append("]");
            return sb.toString();
        }

        private void b(FPS fps) {
            if (h.this.h != null) {
                com.amap.location.common.d.a.c("nlmgr", "first ignore: on reuqest");
            } else if (!e.a(fps)) {
                com.amap.location.common.d.a.c("nlmgr", "first fps not enough");
                h.this.a(null, null, fps);
                h.this.c.a();
            } else if (!h.this.b.j.j() || !h.this.b.j.b()) {
                com.amap.location.common.d.a.b("nlmgr", "first direct online");
                a(fps.clone(), true, true);
            } else {
                AmapLoc c = c(fps);
                if (c == null || !c.isLocationCorrect()) {
                    a(fps.clone(), true, true);
                    if (h.this.b.j.a()) {
                        c = h.this.f.a(fps, true);
                    }
                    if (c != null && c.isLocationCorrect()) {
                        h.this.a(null, c, fps);
                        h.this.q = c;
                    }
                } else {
                    h.this.a(null, c, fps);
                    h.this.c.a();
                    if (com.amap.location.e.b.d.d() != null) {
                        a(c, fps);
                    }
                }
            }
        }

        private void a(FPS fps, boolean z, boolean z2) {
            long j;
            if (!z) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                boolean z3 = false;
                boolean z4 = elapsedRealtime - h.this.s < b.h;
                if (elapsedRealtime < h.this.t) {
                    z3 = true;
                }
                if (z4 || z3) {
                    StringBuilder sb = new StringBuilder("locing-final failed as online cut:, ");
                    sb.append(z4);
                    sb.append(", ");
                    sb.append(z3);
                    sb.append(", ");
                    sb.append(elapsedRealtime);
                    sb.append(", ");
                    sb.append(h.this.s);
                    sb.append(", ");
                    sb.append(b.h);
                    sb.append(", ");
                    sb.append(h.this.t);
                    com.amap.location.common.d.a.c("nlmgr", sb.toString());
                    h.this.n = null;
                    h.this.o = null;
                    h.this.r = SystemClock.elapsedRealtime();
                    h.this.a(null, null, fps);
                    h.this.c.a();
                    return;
                }
            }
            if (a(h.this.a)) {
                j = h.this.b.j.o();
            } else {
                j = h.this.b.j.p();
            }
            com.amap.location.e.b.c d = com.amap.location.e.b.d.d();
            if (d != null) {
                d.a(fps.wifiStatus.numWiFis(), Math.max(fps.cellStatus.cellStateList2.size(), fps.cellStatus.neighbors.size()));
            }
            com.amap.location.protocol.b.a.e = com.amap.location.e.d.c.a();
            h.this.s = SystemClock.elapsedRealtime();
            if (h.this.b.k == null || h.this.b.k.a == null) {
                h.this.h = new LocationRequest(fps, null, h.this.g.a(fps), null);
                h.this.h.a(h.this.b.g);
                h.this.h.a(h.this.b.l.a);
                h.this.h.b(h.this.b.l.b);
                h.this.h.c(h.this.b.l.c);
                h.this.h.setTimeout((int) j);
                h.this.h.d(z2);
                h.this.e.a(h.this.h, h.this.u);
            } else {
                h.this.h = new LocationRequest(fps, com.amap.location.e.d.c.a(z), h.this.g.a(fps), h.this.b.k.a);
                h.this.h.a(h.this.b.g);
                h.this.h.a(com.amap.location.e.d.c.b());
                h.this.h.a(h.this.b.l.a);
                h.this.h.b(h.this.b.l.b);
                h.this.h.c(h.this.b.l.c);
                h.this.h.setTimeout((int) j);
                h.this.h.d(z2);
                h.this.e.b(h.this.h, h.this.u);
            }
            h.this.j.a(j);
        }

        private AmapLoc c(FPS fps) {
            UpTunnel.addCount(LogConstant.PAGE_ID_SAVE_MAIN);
            AmapLoc a2 = h.this.d.a(fps);
            if (a2 != null && a2.isLocationCorrect()) {
                UpTunnel.addCount(100046);
                a2.setIsLast(false);
            }
            return a2;
        }

        private void a(AmapLoc amapLoc, FPS fps) {
            try {
                if (h.this.i != amapLoc) {
                    String str = "0";
                    String str2 = h.this.b.e;
                    String str3 = h.this.b.f;
                    if (!(h.this.b.k == null || h.this.b.k.a == null)) {
                        str = h.this.b.k.a.t();
                        str2 = h.this.b.k.a.q();
                        str3 = h.this.b.k.a.f();
                    }
                    String str4 = str;
                    FPS fps2 = fps;
                    byte[] a2 = new com.amap.location.protocol.e.c().a(com.amap.location.protocol.e.c.a(h.this.a, fps2, null, null, h.this.b.g, str2, str3, h.this.b.l.a, h.this.b.l.b, h.this.b.l.c, 0), false);
                    JSONObject jSONObject = new JSONObject(amapLoc.toJSONStr(2));
                    jSONObject.put("version", "5.2");
                    byte[] encode = Base64.encode(jSONObject.toString().getBytes("UTF-8"), 2);
                    com.amap.location.protocol.e.b bVar = new com.amap.location.protocol.e.b();
                    bVar.a(encode, 2);
                    bVar.a(a2, 2);
                    bVar.a(str4.getBytes("UTF-8"), 2);
                    com.amap.location.e.d.c.a(bVar.a());
                    h.this.i = amapLoc;
                }
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            }
        }

        private boolean a() {
            return SystemClock.elapsedRealtime() - h.this.r >= h.this.b.j.n();
        }

        private boolean d(FPS fps) {
            if (h.this.n == null || !h.this.n.isLocationCorrect() || h.this.o == null || fps == null || !h.this.b.j.c()) {
                com.amap.location.common.d.a.b("nlmgr", "locing-no vaild info");
                return false;
            } else if (h.this.n.getAccuracy() > 299.0f && fps.wifiStatus != null && fps.wifiStatus.getWifiList() != null && fps.wifiStatus.getWifiList().size() > 5) {
                com.amap.location.common.d.a.b("nlmgr", "locing-we have better loc");
                return false;
            } else if (!com.amap.location.f.a.f.a(h.this.o, fps)) {
                com.amap.location.common.d.a.b("nlmgr", "locing-different cell");
                return false;
            } else {
                boolean a2 = com.amap.location.f.a.f.a(h.this.o, fps, h.this.b.j.s());
                long elapsedRealtime = SystemClock.elapsedRealtime();
                boolean z = true;
                if (a2) {
                    h.this.p = 0;
                } else {
                    if (h.this.p == 0 || elapsedRealtime - h.this.p >= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                        z = false;
                    }
                    h.this.p = elapsedRealtime;
                }
                StringBuilder sb = new StringBuilder("locing-judge simi:");
                sb.append(a2);
                sb.append(", thres:");
                sb.append(h.this.b.j.s());
                sb.append(", last:");
                sb.append(z);
                com.amap.location.common.d.a.b("nlmgr", sb.toString());
                return z;
            }
        }

        private void a(LocationRequest locationRequest, FPS fps) {
            boolean z;
            if (h.this.q != null) {
                h.this.c.a();
                com.amap.location.common.d.a.b("nlmgr", "locing-online had reported");
                return;
            }
            AmapLoc c = (locationRequest.l() || !h.this.b.j.b()) ? null : c(fps);
            if ((c == null || !c.isLocationCorrect()) && h.this.b.j.a()) {
                c = h.this.f.a(fps, false);
                z = true;
            } else {
                z = false;
            }
            if (c == null || !c.isLocationCorrect()) {
                com.amap.location.common.d.a.c("nlmgr", "locing-final failed");
                h.this.n = null;
                h.this.o = null;
            } else {
                c.setIsLast(false);
                if (z) {
                    h.this.n = new AmapLoc(c);
                    h.this.n.setType(AmapLoc.TYPE_CACHE);
                } else {
                    h.this.n = c;
                }
                h.this.o = fps;
                if (com.amap.location.e.b.d.d() != null && !z) {
                    a(c, fps);
                }
            }
            h.this.a(locationRequest, c, fps);
            h.this.c.a();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Context context) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return true;
                }
                if (activeNetworkInfo.getType() == 1) {
                    return false;
                }
                if (activeNetworkInfo.getType() == 0) {
                    try {
                        int networkType = ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
                        if (!(networkType == 4 || networkType == 7 || networkType == 11)) {
                            switch (networkType) {
                                case 1:
                                case 2:
                                    break;
                                default:
                                    return false;
                            }
                        }
                        return true;
                    } catch (Throwable unused) {
                    }
                }
                return true;
            } catch (Throwable unused2) {
                return true;
            }
        }
    }

    public void a() {
    }

    public static h a(@NonNull Context context, @NonNull Looper looper, @NonNull d dVar, @NonNull c cVar) {
        h hVar = new h(context, looper, dVar, com.amap.location.e.d.c.a(cVar), cVar, com.amap.location.e.b.d.c());
        return hVar;
    }

    public void b() {
        this.f.a(this.b.m);
    }

    private h(@NonNull Context context, @NonNull Looper looper, @NonNull d dVar, @NonNull com.amap.location.protocol.e eVar, @NonNull c cVar, com.amap.location.offline.a aVar) {
        this.a = context;
        this.b = cVar;
        this.c = dVar;
        this.k = new b(looper);
        this.j = new com.amap.location.e.d.a(looper, this.v);
        this.d = new c(context, this.b.j.r(), this.b.j.q());
        this.e = new g(context, eVar);
        this.f = d.a();
        this.f.a(this.a, this.b.m, aVar);
        this.g = new com.amap.location.e.a.a(context, this.k);
        try {
            com.amap.location.g.b.a.a(context).a("passive", 0, 0.0f, this.w, looper);
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    public void a(@NonNull FPS fps, boolean z) {
        this.l.readLock().lock();
        try {
            if (this.k != null) {
                this.k.obtainMessage(z ^ true ? 1 : 0, fps).sendToTarget();
            }
        } finally {
            this.l.readLock().unlock();
        }
    }

    public void c() {
        this.g.a();
    }

    public void d() {
        this.l.writeLock().lock();
        Handler handler = this.k;
        this.k = null;
        this.l.writeLock().unlock();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.j.b();
            this.g.b();
            this.d.a();
            this.e.a();
            this.f.b();
            com.amap.location.g.b.a.a(this.a).a(this.w);
        }
        StringBuilder sb = new StringBuilder("destory:");
        sb.append(handler != null);
        com.amap.location.common.d.a.b("nlmgr", sb.toString());
    }

    /* access modifiers changed from: private */
    public void a(LocationRequest locationRequest, AmapLoc amapLoc, FPS fps) {
        this.c.a(locationRequest, amapLoc, fps);
        if (amapLoc != null) {
            this.g.a(amapLoc);
        }
    }
}
