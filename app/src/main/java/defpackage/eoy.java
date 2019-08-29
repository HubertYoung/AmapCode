package defpackage;

import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.amap.api.service.IndoorLocationProvider;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: eoy reason: default package */
/* compiled from: LocationMonitor */
public class eoy {
    private static volatile eoy a;
    private volatile boolean b = false;
    private b c;
    /* access modifiers changed from: private */
    public Handler d;
    /* access modifiers changed from: private */
    public Context e;
    /* access modifiers changed from: private */
    public eox f;
    /* access modifiers changed from: private */
    public volatile boolean g = false;
    /* access modifiers changed from: private */
    public volatile boolean h = false;
    /* access modifiers changed from: private */
    public volatile boolean i = false;

    /* renamed from: eoy$a */
    /* compiled from: LocationMonitor */
    class a extends Handler {
        private Runnable b = new Runnable() {
            public final void run() {
                if (eoy.this.i) {
                    if (epk.a) {
                        StringBuilder sb = new StringBuilder("定位出现超时，之前是否有引擎位置：");
                        sb.append(eoy.this.g);
                        epk.a("LMLocationMonitor", sb.toString());
                    }
                    a.this.a(eoy.this.g ? (byte) 3 : 1);
                    eoy.this.h = true;
                    a.this.removeCallbacks(a.this.c);
                    a.this.postDelayed(a.this.c, 60000);
                }
            }
        };
        /* access modifiers changed from: private */
        public Runnable c = new Runnable() {
            public final void run() {
                if (eoy.this.i && eoy.this.h) {
                    if (epk.a) {
                        epk.a("LMLocationMonitor", "定位持续超时");
                    }
                    a.this.a(0);
                    a.this.removeCallbacks(a.this.c);
                    a.this.postDelayed(a.this.c, 60000);
                }
            }
        };

        a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message != null) {
                boolean z = true;
                switch (message.what) {
                    case 1:
                        eoy.this.g = false;
                        eoy.this.h = false;
                        eox c2 = eoy.this.f;
                        long longValue = ((Long) message.obj).longValue();
                        try {
                            c2.i.j = longValue;
                            c2.i.l = longValue;
                            c2.i.e = 0;
                            c2.i.b = ept.c;
                            c2.f.requestLocationUpdates("passive", 1000, 0.0f, c2.q, c2.b);
                            LocationInstrument.getInstance().addSatelliteListener(c2.r, c2.b);
                            LocationInstrument.getInstance().addNmeaListener(c2.s, c2.b);
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
                            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                            intentFilter.addAction("android.location.PROVIDERS_CHANGED");
                            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
                            c2.a.registerReceiver(c2.t, intentFilter);
                        } catch (Throwable th) {
                            if (epk.a) {
                                epk.a(th);
                            }
                        }
                        removeCallbacks(this.b);
                        postDelayed(this.b, 10000);
                        return;
                    case 2:
                        removeCallbacksAndMessages(null);
                        if (eoy.this.h) {
                            a(4);
                        }
                        eox c3 = eoy.this.f;
                        try {
                            c3.d.readLock().lock();
                            if (c3.c != null) {
                                c3.c.removeCallbacksAndMessages(null);
                            }
                            c3.d.readLock().unlock();
                            LocationInstrument.getInstance().removeNmeaListener(c3.s);
                            LocationInstrument.getInstance().removeSatelliteListener(c3.r);
                            c3.f.removeUpdates(c3.q);
                            c3.i.a();
                            c3.e.a = -1;
                            c3.k.clear();
                            c3.l.clear();
                            c3.m.clear();
                            c3.n.clear();
                            c3.o.clear();
                            c3.p.clear();
                            c3.a.unregisterReceiver(c3.t);
                        } catch (Throwable th2) {
                            if (epk.a) {
                                epk.a(th2);
                            }
                        }
                        eoy.this.g = false;
                        eoy.this.h = false;
                        return;
                    case 3:
                        Location location = (Location) message.obj;
                        if (location != null) {
                            eox c4 = eoy.this.f;
                            if (location != null) {
                                epa epa = new epa();
                                epa.a = 0;
                                epa.c = location.getTime();
                                epa.d = epl.c();
                                epa.e = location.getLatitude();
                                epa.f = location.getLongitude();
                                epa.j = location.getBearing();
                                epa.i = location.getSpeed() * 3.6f;
                                epa.k = location.getAccuracy();
                                if (WidgetType.GPS.equals(location.getProvider())) {
                                    epa.b = 1;
                                } else if ("network".equals(location.getProvider())) {
                                    epa.b = 2;
                                } else if (IndoorLocationProvider.NAME.equals(location.getProvider())) {
                                    epa.b = 3;
                                } else {
                                    epa.b = 0;
                                }
                                c4.j.add(epa);
                            }
                            return;
                        }
                        return;
                    case 4:
                        if (epk.a) {
                            StringBuilder sb = new StringBuilder("注入引擎位置 是否超时：");
                            sb.append(eoy.this.h);
                            epk.a("LMLocationMonitor", sb.toString());
                        }
                        eoy.this.g = true;
                        if (eoy.this.h) {
                            removeCallbacks(this.c);
                            a(2);
                        }
                        eoy.this.h = false;
                        removeCallbacks(this.b);
                        postDelayed(this.b, 10000);
                        eoy.this.f.a(false);
                        return;
                    case 5:
                        if (message.obj != null) {
                            if (message.arg1 <= 0) {
                                z = false;
                            }
                            long longValue2 = ((Long) message.obj).longValue();
                            eox c5 = eoy.this.f;
                            if (!z) {
                                c5.e.b(longValue2, 128);
                                break;
                            } else {
                                c5.e.a(longValue2, 128);
                                return;
                            }
                        } else {
                            return;
                        }
                }
            }
        }

        /* access modifiers changed from: private */
        public void a(byte b2) {
            if (epk.a) {
                epk.a("LMLocationMonitor", "开始上传日志 原因:%d", Byte.valueOf(b2));
            }
            byte[] a2 = eoy.this.f.a(b2);
            if (a2 != null) {
                try {
                    String encodeToString = Base64.encodeToString(a2, 2);
                    if (!TextUtils.isEmpty(encodeToString)) {
                        cjy.a(ALCLogLevel.P7, (String) "T10", (String) "D10", (String) "P0015", (String) "E010", encodeToString);
                        if (epk.a) {
                            epk.a("LMLocationMonitor", "日志上传完毕 原因:%d，原始日志大小:%d，加密日志大小:%d", Byte.valueOf(b2), Integer.valueOf(a2.length), Integer.valueOf(encodeToString.length()));
                        }
                    }
                } catch (Throwable th) {
                    if (epk.a) {
                        epk.a(th);
                    }
                }
            }
            eoy.this.f.a(true);
        }
    }

    /* renamed from: eoy$b */
    /* compiled from: LocationMonitor */
    final class b extends HandlerThread {
        volatile boolean a;
        private ReentrantReadWriteLock c = new ReentrantReadWriteLock();

        b(String str) {
            super(str, 10);
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            this.c.writeLock().lock();
            try {
                Looper looper = getLooper();
                if (this.a) {
                    if (looper != null) {
                        looper.quit();
                    }
                    return;
                }
                eoy.this.f = new eox(looper, eoy.this.e);
                eoy.this.d = new a(Looper.myLooper());
                this.c.writeLock().unlock();
            } finally {
                this.c.writeLock().unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(Message message) {
            this.c.readLock().lock();
            if (eoy.this.d != null) {
                eoy.this.d.sendMessage(message);
            }
            this.c.readLock().unlock();
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i) {
            this.c.readLock().lock();
            if (eoy.this.d != null) {
                eoy.this.d.obtainMessage(i).sendToTarget();
            }
            this.c.readLock().unlock();
        }
    }

    public static eoy a() {
        if (a == null) {
            synchronized (eoy.class) {
                try {
                    if (a == null) {
                        a = new eoy();
                    }
                }
            }
        }
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.content.Context r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            boolean r0 = r1.b     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0027
            r1.e = r2     // Catch:{ all -> 0x0029 }
            boolean r2 = defpackage.epk.a     // Catch:{ all -> 0x0029 }
            if (r2 == 0) goto L_0x0016
            java.lang.String r2 = "LMLocationMonitor"
            java.lang.String r0 = "init"
            defpackage.epk.a(r2, r0)     // Catch:{ all -> 0x0029 }
        L_0x0016:
            eoy$b r2 = new eoy$b     // Catch:{ all -> 0x0029 }
            java.lang.String r0 = "LocationMonitor"
            r2.<init>(r0)     // Catch:{ all -> 0x0029 }
            r1.c = r2     // Catch:{ all -> 0x0029 }
            eoy$b r2 = r1.c     // Catch:{ all -> 0x0029 }
            r2.start()     // Catch:{ all -> 0x0029 }
            r2 = 1
            r1.b = r2     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r1)
            return
        L_0x0029:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoy.a(android.content.Context):void");
    }

    public final synchronized void a(long j) {
        if (!this.i && this.b) {
            if (epk.a) {
                StringBuilder sb = new StringBuilder("开始导航 ");
                sb.append(epl.a(j));
                sb.append(" 云控状态：");
                sb.append(ept.a);
                epk.a("LMLocationMonitor", sb.toString());
            }
            if (ept.a) {
                Message obtain = Message.obtain();
                obtain.obj = Long.valueOf(j);
                obtain.what = 1;
                if (this.c != null) {
                    this.c.a(obtain);
                }
                this.i = true;
            }
        }
    }

    public final synchronized void b() {
        if (this.i) {
            this.i = false;
            if (epk.a) {
                StringBuilder sb = new StringBuilder("停止导航 上次是否超时 ");
                sb.append(this.h);
                epk.a("LMLocationMonitor", sb.toString());
            }
            if (this.c != null) {
                this.c.a(2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(android.location.Location r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 != 0) goto L_0x0005
            monitor-exit(r1)
            return
        L_0x0005:
            eoy$b r0 = r1.c     // Catch:{ all -> 0x0019 }
            if (r0 == 0) goto L_0x0017
            android.os.Message r0 = android.os.Message.obtain()     // Catch:{ all -> 0x0019 }
            r0.obj = r2     // Catch:{ all -> 0x0019 }
            r2 = 3
            r0.what = r2     // Catch:{ all -> 0x0019 }
            eoy$b r2 = r1.c     // Catch:{ all -> 0x0019 }
            r2.a(r0)     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r1)
            return
        L_0x0019:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoy.a(android.location.Location):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(android.location.Location r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.i     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            if (r2 != 0) goto L_0x0008
            goto L_0x001f
        L_0x0008:
            boolean r2 = defpackage.epk.a     // Catch:{ all -> 0x0021 }
            if (r2 == 0) goto L_0x0013
            java.lang.String r2 = "LMLocationMonitor"
            java.lang.String r0 = "收到定位引擎位置"
            defpackage.epk.a(r2, r0)     // Catch:{ all -> 0x0021 }
        L_0x0013:
            eoy$b r2 = r1.c     // Catch:{ all -> 0x0021 }
            if (r2 == 0) goto L_0x001d
            eoy$b r2 = r1.c     // Catch:{ all -> 0x0021 }
            r0 = 4
            r2.a(r0)     // Catch:{ all -> 0x0021 }
        L_0x001d:
            monitor-exit(r1)
            return
        L_0x001f:
            monitor-exit(r1)
            return
        L_0x0021:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoy.b(android.location.Location):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(long r4, boolean r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.i     // Catch:{ all -> 0x0034 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r3)
            return
        L_0x0007:
            boolean r0 = defpackage.epk.a     // Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = "LMLocationMonitor"
            java.lang.String r1 = "设置的SDK对于GPS是否打开:"
            java.lang.String r2 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0034 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ all -> 0x0034 }
            defpackage.epk.a(r0, r1)     // Catch:{ all -> 0x0034 }
        L_0x001a:
            eoy$b r0 = r3.c     // Catch:{ all -> 0x0034 }
            if (r0 == 0) goto L_0x0032
            android.os.Message r0 = android.os.Message.obtain()     // Catch:{ all -> 0x0034 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0034 }
            r0.obj = r4     // Catch:{ all -> 0x0034 }
            r0.arg1 = r6     // Catch:{ all -> 0x0034 }
            r4 = 5
            r0.what = r4     // Catch:{ all -> 0x0034 }
            eoy$b r4 = r3.c     // Catch:{ all -> 0x0034 }
            r4.a(r0)     // Catch:{ all -> 0x0034 }
        L_0x0032:
            monitor-exit(r3)
            return
        L_0x0034:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoy.a(long, boolean):void");
    }
}
