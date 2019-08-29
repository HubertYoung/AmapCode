package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: ehl reason: default package */
/* compiled from: TrackRecordImpl */
public class ehl implements ehn {
    /* access modifiers changed from: private */
    public static volatile ehl n;
    public volatile boolean a;
    private Context b;
    private HandlerThread c;
    private FootNaviLocation d;
    /* access modifiers changed from: private */
    public a e;
    /* access modifiers changed from: private */
    public List<HealthPoint> f;
    /* access modifiers changed from: private */
    public SharedPreferences g;
    private Editor h;
    private b i;
    /* access modifiers changed from: private */
    public RandomAccessFile j;
    private File k;
    /* access modifiers changed from: private */
    public long l;
    private volatile boolean m;
    private com.autonavi.minimap.route.foot.footnavi.FootNaviLocation.a o;

    /* renamed from: ehl$a */
    /* compiled from: TrackRecordImpl */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 4) {
                switch (i) {
                    case 1:
                        ehl.a(ehl.this, (HealthPoint) message.obj);
                        return;
                    case 2:
                        ehl.a(ehl.this, (defpackage.ehn.a) message.obj);
                        removeMessages(2);
                        return;
                }
            } else if (ehl.n != null) {
                ehl.n.a();
            }
        }
    }

    /* renamed from: ehl$b */
    /* compiled from: TrackRecordImpl */
    class b extends Thread {
        private long b;

        private b() {
            this.b = 0;
        }

        /* synthetic */ b(ehl ehl, byte b2) {
            this();
        }

        public final void run() {
            synchronized (ehl.this.f) {
                while (ehl.this.a && ehl.this.f != null) {
                    if (ehl.this.f.size() > 0) {
                        long j = 0;
                        this.b = 0;
                        try {
                            ehl ehl = ehl.this;
                            if (ehl.this.j != null) {
                                j = ehl.this.j.getFilePointer();
                            }
                            ehl.l = j;
                            long a2 = ehl.a(ehl.this.l);
                            StringBuilder sb = new StringBuilder("position 6 =");
                            sb.append(ehl.this.l);
                            sb.append(",fixed=");
                            sb.append(a2);
                            eao.d("wbsw", sb.toString());
                            if (a2 != ehl.this.l) {
                                ehl.this.l = a2;
                                ehl.this.j.seek(ehl.this.l);
                            }
                            ehl.this.f();
                            for (HealthPoint healthPoint : ehl.this.f) {
                                if (ehl.this.j != null) {
                                    StringBuilder sb2 = new StringBuilder("real write >>gps_time=");
                                    sb2.append(healthPoint.gps_time);
                                    sb2.append(",accuracy=");
                                    sb2.append(healthPoint.accuracy);
                                    sb2.append(",latitude=");
                                    sb2.append(healthPoint.latitude);
                                    sb2.append(",longitude=");
                                    sb2.append(healthPoint.longitude);
                                    sb2.append(",angle=");
                                    sb2.append(healthPoint.angle);
                                    sb2.append(",speed=");
                                    sb2.append(healthPoint.speed);
                                    eao.d("wbsw", sb2.toString());
                                    ehl.this.j.writeLong(healthPoint.gps_time);
                                    ehl.this.j.writeDouble(healthPoint.accuracy);
                                    ehl.this.j.writeDouble(healthPoint.latitude);
                                    ehl.this.j.writeDouble(healthPoint.longitude);
                                    ehl.this.j.writeInt(healthPoint.angle);
                                    ehl.this.j.writeDouble(healthPoint.speed);
                                    ehl.this.f.remove(healthPoint);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.b++;
                        try {
                            ehl.this.f.wait(this.b * 1000 * 10);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static ehl a(Context context) {
        if (n == null) {
            synchronized (ehl.class) {
                try {
                    n = new ehl(context);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    n = new ehl();
                }
            }
        }
        return n;
    }

    private void a(boolean z) throws FileNotFoundException {
        if (!z) {
            if (this.k.exists()) {
                this.k.delete();
            }
            try {
                this.k.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
                throw new IllegalStateException(e2);
            }
        } else if (!this.k.exists()) {
            try {
                this.k.createNewFile();
            } catch (IOException e3) {
                e3.printStackTrace();
                throw new IllegalStateException(e3);
            }
        }
        this.j = new RandomAccessFile(this.k, "rws");
    }

    private ehl() {
        this.f = new ArrayList();
        this.i = new b(this, 0);
        this.l = 0;
        this.a = false;
        this.m = false;
        this.o = new com.autonavi.minimap.route.foot.footnavi.FootNaviLocation.a() {
            public final void a(int i) {
            }

            public final void a(Location location) {
                if (location != null) {
                    StringBuilder sb = new StringBuilder("NoGPS, write >>gps_time>>|=");
                    sb.append(location.getTime());
                    sb.append(",accuracy=");
                    sb.append(location.getAccuracy());
                    sb.append(",latitude=");
                    sb.append(location.getLatitude());
                    sb.append(",longitude=");
                    sb.append(location.getLongitude());
                    sb.append(",angle=");
                    sb.append(location.getBearing());
                    sb.append(",speed=");
                    sb.append(location.getSpeed());
                    eao.d("wbsw", sb.toString());
                    String string = ehl.this.g.getString("order_id", "");
                    eao.d("wbsw", "current order id ".concat(String.valueOf(string)));
                    if (!"".equals(string)) {
                        long currentTimeMillis = System.currentTimeMillis() - ehl.this.g.getLong(string, 0);
                        StringBuilder sb2 = new StringBuilder("Current order id =");
                        sb2.append(string);
                        sb2.append(", interval=");
                        sb2.append(currentTimeMillis);
                        eao.d("wbsw", sb2.toString());
                        if (currentTimeMillis >= 43200000) {
                            ehl.this.e.obtainMessage(4).sendToTarget();
                            return;
                        }
                    }
                    if (WidgetType.GPS.equals(location.getProvider())) {
                        HealthPoint healthPoint = new HealthPoint();
                        healthPoint.latitude = location.getLatitude();
                        healthPoint.longitude = location.getLongitude();
                        healthPoint.accuracy = (double) location.getAccuracy();
                        healthPoint.angle = (int) location.getBearing();
                        healthPoint.speed = (double) location.getSpeed();
                        healthPoint.gps_time = location.getTime() / 1000;
                        StringBuilder sb3 = new StringBuilder("GPS write >>gps_time=");
                        sb3.append(healthPoint.gps_time);
                        sb3.append(",accuracy=");
                        sb3.append(healthPoint.accuracy);
                        sb3.append(",latitude=");
                        sb3.append(healthPoint.latitude);
                        sb3.append("longitude=");
                        sb3.append(healthPoint.longitude);
                        sb3.append(",angle=");
                        sb3.append(healthPoint.angle);
                        sb3.append(",speed=");
                        sb3.append(healthPoint.speed);
                        eao.d("wbsw", sb3.toString());
                        ehl.this.e.obtainMessage(1, healthPoint).sendToTarget();
                        return;
                    }
                    StringBuilder sb4 = new StringBuilder("wrong provider ");
                    sb4.append(location.getProvider());
                    eao.d("tr", sb4.toString());
                }
            }
        };
        this.b = null;
    }

    private ehl(Context context) {
        this.f = new ArrayList();
        this.i = new b(this, 0);
        this.l = 0;
        this.a = false;
        this.m = false;
        this.o = new com.autonavi.minimap.route.foot.footnavi.FootNaviLocation.a() {
            public final void a(int i) {
            }

            public final void a(Location location) {
                if (location != null) {
                    StringBuilder sb = new StringBuilder("NoGPS, write >>gps_time>>|=");
                    sb.append(location.getTime());
                    sb.append(",accuracy=");
                    sb.append(location.getAccuracy());
                    sb.append(",latitude=");
                    sb.append(location.getLatitude());
                    sb.append(",longitude=");
                    sb.append(location.getLongitude());
                    sb.append(",angle=");
                    sb.append(location.getBearing());
                    sb.append(",speed=");
                    sb.append(location.getSpeed());
                    eao.d("wbsw", sb.toString());
                    String string = ehl.this.g.getString("order_id", "");
                    eao.d("wbsw", "current order id ".concat(String.valueOf(string)));
                    if (!"".equals(string)) {
                        long currentTimeMillis = System.currentTimeMillis() - ehl.this.g.getLong(string, 0);
                        StringBuilder sb2 = new StringBuilder("Current order id =");
                        sb2.append(string);
                        sb2.append(", interval=");
                        sb2.append(currentTimeMillis);
                        eao.d("wbsw", sb2.toString());
                        if (currentTimeMillis >= 43200000) {
                            ehl.this.e.obtainMessage(4).sendToTarget();
                            return;
                        }
                    }
                    if (WidgetType.GPS.equals(location.getProvider())) {
                        HealthPoint healthPoint = new HealthPoint();
                        healthPoint.latitude = location.getLatitude();
                        healthPoint.longitude = location.getLongitude();
                        healthPoint.accuracy = (double) location.getAccuracy();
                        healthPoint.angle = (int) location.getBearing();
                        healthPoint.speed = (double) location.getSpeed();
                        healthPoint.gps_time = location.getTime() / 1000;
                        StringBuilder sb3 = new StringBuilder("GPS write >>gps_time=");
                        sb3.append(healthPoint.gps_time);
                        sb3.append(",accuracy=");
                        sb3.append(healthPoint.accuracy);
                        sb3.append(",latitude=");
                        sb3.append(healthPoint.latitude);
                        sb3.append("longitude=");
                        sb3.append(healthPoint.longitude);
                        sb3.append(",angle=");
                        sb3.append(healthPoint.angle);
                        sb3.append(",speed=");
                        sb3.append(healthPoint.speed);
                        eao.d("wbsw", sb3.toString());
                        ehl.this.e.obtainMessage(1, healthPoint).sendToTarget();
                        return;
                    }
                    StringBuilder sb4 = new StringBuilder("wrong provider ");
                    sb4.append(location.getProvider());
                    eao.d("tr", sb4.toString());
                }
            }
        };
        if (context == null) {
            eao.d("tr", "Context is null, Failed to create track record ");
            return;
        }
        this.b = context;
        this.g = this.b.getSharedPreferences("tr_preference", 0);
        this.h = this.g.edit();
        this.d = new FootNaviLocation();
        this.c = new HandlerThread("tr");
        this.c.start();
        this.e = new a(this.c.getLooper());
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getFilesDir());
        sb.append("/track_record");
        this.k = new File(sb.toString());
    }

    private void d() {
        this.h.apply();
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b1 A[SYNTHETIC, Splitter:B:47:0x00b1] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x013e A[SYNTHETIC, Splitter:B:83:0x013e] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0164  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = "tr"
            java.lang.String r1 = "start"
            defpackage.eao.f(r0, r1)     // Catch:{ all -> 0x017c }
            android.content.Context r0 = r7.b     // Catch:{ all -> 0x017c }
            r1 = 0
            if (r0 != 0) goto L_0x0016
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Failed to create trackrecord in start"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x0016:
            boolean r0 = r7.m     // Catch:{ all -> 0x017c }
            if (r0 == 0) goto L_0x0023
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Cannot restore state of trackrecord during getting data"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x0023:
            r0 = 0
            if (r8 == 0) goto L_0x0033
            java.lang.String r2 = ""
            java.lang.String r3 = r8.trim()     // Catch:{ all -> 0x017c }
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x017c }
            if (r2 != 0) goto L_0x0033
            goto L_0x0034
        L_0x0033:
            r8 = r0
        L_0x0034:
            java.lang.String r0 = "tr"
            java.lang.String r2 = "start track with OrderId  "
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x017c }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x017c }
            defpackage.eao.e(r0, r2)     // Catch:{ all -> 0x017c }
            java.lang.String r0 = ""
            android.content.SharedPreferences r2 = r7.g     // Catch:{ all -> 0x017c }
            java.lang.String r3 = "order_id"
            java.lang.String r4 = ""
            java.lang.String r2 = r2.getString(r3, r4)     // Catch:{ all -> 0x017c }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x017c }
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x00b1
            if (r8 == 0) goto L_0x00a8
            r7.a(r1)     // Catch:{ Exception -> 0x00a2 }
            r7.l = r3     // Catch:{ Exception -> 0x00a2 }
            r7.f()     // Catch:{ Exception -> 0x00a2 }
            android.content.SharedPreferences$Editor r0 = r7.h     // Catch:{ all -> 0x017c }
            java.lang.String r3 = "order_id"
            r0.putString(r3, r8)     // Catch:{ all -> 0x017c }
            r7.d()     // Catch:{ all -> 0x017c }
            ehl$b r0 = r7.i     // Catch:{ all -> 0x017c }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x017c }
            if (r0 != 0) goto L_0x0092
            ehl$b r0 = r7.i     // Catch:{ Exception -> 0x007a }
            r0.start()     // Catch:{ Exception -> 0x007a }
            goto L_0x0092
        L_0x007a:
            java.lang.String r0 = "wbsww"
            java.lang.String r3 = "Object of thread has not been destory"
            defpackage.eao.d(r0, r3)     // Catch:{ all -> 0x017c }
            ehl$b r0 = r7.i     // Catch:{ all -> 0x017c }
            r0.interrupt()     // Catch:{ all -> 0x017c }
            ehl$b r0 = new ehl$b     // Catch:{ all -> 0x017c }
            r0.<init>(r7, r1)     // Catch:{ all -> 0x017c }
            r7.i = r0     // Catch:{ all -> 0x017c }
            ehl$b r0 = r7.i     // Catch:{ all -> 0x017c }
            r0.start()     // Catch:{ all -> 0x017c }
        L_0x0092:
            android.content.SharedPreferences$Editor r0 = r7.h     // Catch:{ all -> 0x017c }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x017c }
            r0.putLong(r8, r3)     // Catch:{ all -> 0x017c }
            r7.d()     // Catch:{ all -> 0x017c }
            r7.a = r2     // Catch:{ all -> 0x017c }
            goto L_0x013a
        L_0x00a2:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x00a8:
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Order ID is null  "
            defpackage.eao.e(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x00b1:
            android.content.SharedPreferences r0 = r7.g     // Catch:{ all -> 0x017c }
            java.lang.String r5 = "order_id"
            java.lang.String r6 = ""
            java.lang.String r0 = r0.getString(r5, r6)     // Catch:{ all -> 0x017c }
            boolean r0 = r0.equals(r8)     // Catch:{ all -> 0x017c }
            if (r0 == 0) goto L_0x0173
            boolean r0 = r7.a     // Catch:{ all -> 0x017c }
            if (r0 == 0) goto L_0x00ce
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Track record module has came back, dont resume again"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x00ce:
            r7.a(r2)     // Catch:{ Exception -> 0x016d }
            android.content.SharedPreferences r0 = r7.g     // Catch:{ Exception -> 0x016d }
            java.lang.String r5 = "file_offset"
            long r5 = r0.getLong(r5, r3)     // Catch:{ Exception -> 0x016d }
            r7.l = r5     // Catch:{ Exception -> 0x016d }
            r7.f()     // Catch:{ Exception -> 0x016d }
            java.io.RandomAccessFile r0 = r7.j     // Catch:{ Exception -> 0x016d }
            if (r0 == 0) goto L_0x00e9
            java.io.RandomAccessFile r0 = r7.j     // Catch:{ Exception -> 0x016d }
            long r5 = r7.l     // Catch:{ Exception -> 0x016d }
            r0.seek(r5)     // Catch:{ Exception -> 0x016d }
        L_0x00e9:
            android.content.SharedPreferences r0 = r7.g     // Catch:{ all -> 0x017c }
            long r5 = r0.getLong(r8, r3)     // Catch:{ all -> 0x017c }
            int r8 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r8 != 0) goto L_0x00fc
            java.lang.String r8 = "wbsww"
            java.lang.String r0 = "Stored time is invalid, I think time is expired"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x00fc:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x017c }
            r8 = 0
            long r3 = r3 - r5
            r5 = 43200000(0x2932e00, double:2.1343636E-316)
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 < 0) goto L_0x0112
            java.lang.String r8 = "wbsww"
            java.lang.String r0 = "Time is expired"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x0112:
            ehl$b r8 = r7.i     // Catch:{ all -> 0x017c }
            boolean r8 = r8.isAlive()     // Catch:{ all -> 0x017c }
            if (r8 != 0) goto L_0x0138
            ehl$b r8 = r7.i     // Catch:{ Exception -> 0x0120 }
            r8.start()     // Catch:{ Exception -> 0x0120 }
            goto L_0x0138
        L_0x0120:
            java.lang.String r8 = "wbsww"
            java.lang.String r0 = "Object of thread has not been destory"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x017c }
            ehl$b r8 = r7.i     // Catch:{ all -> 0x017c }
            r8.interrupt()     // Catch:{ all -> 0x017c }
            ehl$b r8 = new ehl$b     // Catch:{ all -> 0x017c }
            r8.<init>(r7, r1)     // Catch:{ all -> 0x017c }
            r7.i = r8     // Catch:{ all -> 0x017c }
            ehl$b r8 = r7.i     // Catch:{ all -> 0x017c }
            r8.start()     // Catch:{ all -> 0x017c }
        L_0x0138:
            r7.a = r2     // Catch:{ all -> 0x017c }
        L_0x013a:
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x017c }
            if (r8 == 0) goto L_0x0164
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ SecurityException -> 0x0147 }
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation$a r0 = r7.o     // Catch:{ SecurityException -> 0x0147 }
            r8.startLocation(r0)     // Catch:{ SecurityException -> 0x0147 }
            monitor-exit(r7)
            return r2
        L_0x0147:
            r8 = move-exception
            java.lang.String r0 = "tr"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x017c }
            java.lang.String r3 = "No GPS location permission "
            r2.<init>(r3)     // Catch:{ all -> 0x017c }
            java.lang.String r3 = r8.getMessage()     // Catch:{ all -> 0x017c }
            r2.append(r3)     // Catch:{ all -> 0x017c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x017c }
            defpackage.eao.e(r0, r2)     // Catch:{ all -> 0x017c }
            r8.printStackTrace()     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x0164:
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Failed to get GPS location manager "
            defpackage.eao.e(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x016d:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x0173:
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Invalid order ID  "
            defpackage.eao.e(r8, r0)     // Catch:{ all -> 0x017c }
            monitor-exit(r7)
            return r1
        L_0x017c:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehl.a(java.lang.String):boolean");
    }

    public final synchronized void a() {
        eao.f("tr", "flush");
        if (this.b == null) {
            eao.d("tr", "Failed to create trackrecord in flush");
            return;
        }
        if (this.d != null) {
            this.d.stopLocation();
        }
        e();
        if (this.f != null) {
            synchronized (this.f) {
                this.a = false;
                if (this.f != null) {
                    this.f.notifyAll();
                }
            }
        }
    }

    public final String b() {
        if (this.b != null) {
            return this.g != null ? this.g.getString("order_id", "") : "";
        }
        eao.d("tr", "Failed to create trackrecord in getCurrentOrderId");
        return "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:128:0x021f, code lost:
        if (r7.d != null) goto L_0x0221;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0221, code lost:
        r7.d.stopLocation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0228, code lost:
        if (r7.j != null) goto L_0x022a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
        r7.j.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0230, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        r3.printStackTrace();
        r5 = new java.lang.StringBuilder("stop trackOrderId  ");
        r5.append(r3.getMessage());
        defpackage.eao.e("tr", r5.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x024d, code lost:
        if (r7.k != null) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x024f, code lost:
        r7.k.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0256, code lost:
        if (r7.h != null) goto L_0x0258;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0258, code lost:
        r7.h.clear();
        d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0260, code lost:
        r7.l = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0264, code lost:
        if (r7.e != null) goto L_0x0266;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0266, code lost:
        r7.e.removeCallbacksAndMessages(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x026d, code lost:
        if (r7.c != null) goto L_0x026f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x026f, code lost:
        r7.c.quit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0274, code lost:
        r7.f = null;
        n = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0278, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00dd, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r8.printStackTrace();
        r4 = new java.lang.StringBuilder("stop trackOrderId  ");
        r4.append(r8.getMessage());
        defpackage.eao.e("tr", r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x012a, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:42:0x00d7, B:69:0x012e] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e A[Catch:{ all -> 0x012a, Exception -> 0x00dd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b(java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.String r0 = "tr"
            java.lang.String r1 = "stop"
            defpackage.eao.f(r0, r1)     // Catch:{ all -> 0x0282 }
            android.content.Context r0 = r7.b     // Catch:{ all -> 0x0282 }
            if (r0 != 0) goto L_0x0015
            java.lang.String r8 = "tr"
            java.lang.String r0 = "Failed to create trackrecord in stop"
            defpackage.eao.d(r8, r0)     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)
            return
        L_0x0015:
            r0 = 0
            if (r8 == 0) goto L_0x0026
            java.lang.String r1 = ""
            java.lang.String r2 = r8.trim()     // Catch:{ all -> 0x0282 }
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0282 }
            if (r1 != 0) goto L_0x0026
            r1 = r8
            goto L_0x0027
        L_0x0026:
            r1 = r0
        L_0x0027:
            java.lang.String r2 = "share_bike_cp_source"
            java.lang.String r2 = defpackage.ehs.b(r2)     // Catch:{ all -> 0x0282 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0282 }
            java.lang.String r4 = "TrackRecordImpl stop, upload sharebike: "
            r3.<init>(r4)     // Catch:{ all -> 0x0282 }
            r3.append(r2)     // Catch:{ all -> 0x0282 }
            java.lang.String r4 = ", orderid:"
            r3.append(r4)     // Catch:{ all -> 0x0282 }
            r3.append(r8)     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0282 }
            com.autonavi.minimap.route.logs.track.TrackPostUtils.a(r3)     // Catch:{ all -> 0x0282 }
            com.autonavi.jni.eyrie.amap.tracker.TrackType r3 = com.autonavi.jni.eyrie.amap.tracker.TrackType.SHAREBIKE     // Catch:{ all -> 0x0282 }
            com.autonavi.jni.eyrie.amap.tracker.TrackInfo r3 = com.autonavi.jni.eyrie.amap.tracker.TrackInfo.createTrackInfo(r3)     // Catch:{ all -> 0x0282 }
            if (r3 == 0) goto L_0x005d
            com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType r4 = com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType.ShareBikeSource     // Catch:{ all -> 0x0282 }
            r3.set(r4, r2)     // Catch:{ all -> 0x0282 }
            com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType r2 = com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType.ShareBikeOrderId     // Catch:{ all -> 0x0282 }
            r3.set(r2, r8)     // Catch:{ all -> 0x0282 }
            com.autonavi.jni.eyrie.amap.tracker.TrackType r8 = com.autonavi.jni.eyrie.amap.tracker.TrackType.SHAREBIKE     // Catch:{ all -> 0x0282 }
            com.autonavi.jni.eyrie.amap.tracker.TrackPoster.upload(r8)     // Catch:{ all -> 0x0282 }
        L_0x005d:
            java.lang.String r8 = "tr"
            java.lang.String r2 = "stop trackOrderId  "
            java.lang.String r3 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0282 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x0282 }
            defpackage.eao.e(r8, r2)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = ""
            android.content.SharedPreferences r2 = r7.g     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = "order_id"
            java.lang.String r4 = ""
            java.lang.String r2 = r2.getString(r3, r4)     // Catch:{ all -> 0x0282 }
            boolean r8 = r8.equals(r2)     // Catch:{ all -> 0x0282 }
            if (r8 != 0) goto L_0x0279
            android.content.SharedPreferences r8 = r7.g     // Catch:{ all -> 0x0282 }
            java.lang.String r2 = "order_id"
            java.lang.String r3 = ""
            java.lang.String r8 = r8.getString(r2, r3)     // Catch:{ all -> 0x0282 }
            boolean r8 = r8.equals(r1)     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0279
            r1 = 0
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            if (r8 == 0) goto L_0x0099
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.stopLocation()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
        L_0x0099:
            r7.e()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            java.util.List<com.autonavi.jni.route.health.HealthPoint> r8 = r7.f     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            monitor-enter(r8)     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r3 = 0
            r7.a = r3     // Catch:{ all -> 0x0127 }
            java.util.List<com.autonavi.jni.route.health.HealthPoint> r3 = r7.f     // Catch:{ all -> 0x0127 }
            r3.notifyAll()     // Catch:{ all -> 0x0127 }
            monitor-exit(r8)     // Catch:{ all -> 0x0127 }
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.close()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            java.io.File r8 = r7.k     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.delete()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.clear()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r7.d()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r7.l = r1     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            ehl$a r8 = r7.e     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.removeCallbacksAndMessages(r0)     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            android.os.HandlerThread r8 = r7.c     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r8.quit()     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            r7.f = r0     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            n = r0     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x00d3
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            r8.stopLocation()     // Catch:{ all -> 0x0282 }
        L_0x00d3:
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x00f8
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ Exception -> 0x00dd }
            r8.close()     // Catch:{ Exception -> 0x00dd }
            goto L_0x00f8
        L_0x00dd:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = "tr"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0282 }
            java.lang.String r5 = "stop trackOrderId  "
            r4.<init>(r5)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0282 }
            r4.append(r8)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x0282 }
            defpackage.eao.e(r3, r8)     // Catch:{ all -> 0x0282 }
        L_0x00f8:
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0101
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            r8.delete()     // Catch:{ all -> 0x0282 }
        L_0x0101:
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x010d
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            r8.clear()     // Catch:{ all -> 0x0282 }
            r7.d()     // Catch:{ all -> 0x0282 }
        L_0x010d:
            r7.l = r1     // Catch:{ all -> 0x0282 }
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0118
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            r8.removeCallbacksAndMessages(r0)     // Catch:{ all -> 0x0282 }
        L_0x0118:
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0121
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            r8.quit()     // Catch:{ all -> 0x0282 }
        L_0x0121:
            r7.f = r0     // Catch:{ all -> 0x0282 }
            n = r0     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)
            return
        L_0x0127:
            r3 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0127 }
            throw r3     // Catch:{ IOException -> 0x01a5, NullPointerException -> 0x012d }
        L_0x012a:
            r8 = move-exception
            goto L_0x021d
        L_0x012d:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x012a }
            java.lang.String r3 = "tr"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x012a }
            java.lang.String r5 = "stop trackOrderId  "
            r4.<init>(r5)     // Catch:{ all -> 0x012a }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x012a }
            r4.append(r8)     // Catch:{ all -> 0x012a }
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x012a }
            defpackage.eao.e(r3, r8)     // Catch:{ all -> 0x012a }
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0151
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            r8.stopLocation()     // Catch:{ all -> 0x0282 }
        L_0x0151:
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0176
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ Exception -> 0x015b }
            r8.close()     // Catch:{ Exception -> 0x015b }
            goto L_0x0176
        L_0x015b:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = "tr"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0282 }
            java.lang.String r5 = "stop trackOrderId  "
            r4.<init>(r5)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0282 }
            r4.append(r8)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x0282 }
            defpackage.eao.e(r3, r8)     // Catch:{ all -> 0x0282 }
        L_0x0176:
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x017f
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            r8.delete()     // Catch:{ all -> 0x0282 }
        L_0x017f:
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x018b
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            r8.clear()     // Catch:{ all -> 0x0282 }
            r7.d()     // Catch:{ all -> 0x0282 }
        L_0x018b:
            r7.l = r1     // Catch:{ all -> 0x0282 }
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0196
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            r8.removeCallbacksAndMessages(r0)     // Catch:{ all -> 0x0282 }
        L_0x0196:
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x019f
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            r8.quit()     // Catch:{ all -> 0x0282 }
        L_0x019f:
            r7.f = r0     // Catch:{ all -> 0x0282 }
            n = r0     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)
            return
        L_0x01a5:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x012a }
            java.lang.String r3 = "tr"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x012a }
            java.lang.String r5 = "stop trackOrderId  "
            r4.<init>(r5)     // Catch:{ all -> 0x012a }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x012a }
            r4.append(r8)     // Catch:{ all -> 0x012a }
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x012a }
            defpackage.eao.e(r3, r8)     // Catch:{ all -> 0x012a }
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x01c9
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r8 = r7.d     // Catch:{ all -> 0x0282 }
            r8.stopLocation()     // Catch:{ all -> 0x0282 }
        L_0x01c9:
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x01ee
            java.io.RandomAccessFile r8 = r7.j     // Catch:{ Exception -> 0x01d3 }
            r8.close()     // Catch:{ Exception -> 0x01d3 }
            goto L_0x01ee
        L_0x01d3:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = "tr"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0282 }
            java.lang.String r5 = "stop trackOrderId  "
            r4.<init>(r5)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0282 }
            r4.append(r8)     // Catch:{ all -> 0x0282 }
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x0282 }
            defpackage.eao.e(r3, r8)     // Catch:{ all -> 0x0282 }
        L_0x01ee:
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x01f7
            java.io.File r8 = r7.k     // Catch:{ all -> 0x0282 }
            r8.delete()     // Catch:{ all -> 0x0282 }
        L_0x01f7:
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0203
            android.content.SharedPreferences$Editor r8 = r7.h     // Catch:{ all -> 0x0282 }
            r8.clear()     // Catch:{ all -> 0x0282 }
            r7.d()     // Catch:{ all -> 0x0282 }
        L_0x0203:
            r7.l = r1     // Catch:{ all -> 0x0282 }
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x020e
            ehl$a r8 = r7.e     // Catch:{ all -> 0x0282 }
            r8.removeCallbacksAndMessages(r0)     // Catch:{ all -> 0x0282 }
        L_0x020e:
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            if (r8 == 0) goto L_0x0217
            android.os.HandlerThread r8 = r7.c     // Catch:{ all -> 0x0282 }
            r8.quit()     // Catch:{ all -> 0x0282 }
        L_0x0217:
            r7.f = r0     // Catch:{ all -> 0x0282 }
            n = r0     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)
            return
        L_0x021d:
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r3 = r7.d     // Catch:{ all -> 0x0282 }
            if (r3 == 0) goto L_0x0226
            com.autonavi.minimap.route.foot.footnavi.FootNaviLocation r3 = r7.d     // Catch:{ all -> 0x0282 }
            r3.stopLocation()     // Catch:{ all -> 0x0282 }
        L_0x0226:
            java.io.RandomAccessFile r3 = r7.j     // Catch:{ all -> 0x0282 }
            if (r3 == 0) goto L_0x024b
            java.io.RandomAccessFile r3 = r7.j     // Catch:{ Exception -> 0x0230 }
            r3.close()     // Catch:{ Exception -> 0x0230 }
            goto L_0x024b
        L_0x0230:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ all -> 0x0282 }
            java.lang.String r4 = "tr"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0282 }
            java.lang.String r6 = "stop trackOrderId  "
            r5.<init>(r6)     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = r3.getMessage()     // Catch:{ all -> 0x0282 }
            r5.append(r3)     // Catch:{ all -> 0x0282 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x0282 }
            defpackage.eao.e(r4, r3)     // Catch:{ all -> 0x0282 }
        L_0x024b:
            java.io.File r3 = r7.k     // Catch:{ all -> 0x0282 }
            if (r3 == 0) goto L_0x0254
            java.io.File r3 = r7.k     // Catch:{ all -> 0x0282 }
            r3.delete()     // Catch:{ all -> 0x0282 }
        L_0x0254:
            android.content.SharedPreferences$Editor r3 = r7.h     // Catch:{ all -> 0x0282 }
            if (r3 == 0) goto L_0x0260
            android.content.SharedPreferences$Editor r3 = r7.h     // Catch:{ all -> 0x0282 }
            r3.clear()     // Catch:{ all -> 0x0282 }
            r7.d()     // Catch:{ all -> 0x0282 }
        L_0x0260:
            r7.l = r1     // Catch:{ all -> 0x0282 }
            ehl$a r1 = r7.e     // Catch:{ all -> 0x0282 }
            if (r1 == 0) goto L_0x026b
            ehl$a r1 = r7.e     // Catch:{ all -> 0x0282 }
            r1.removeCallbacksAndMessages(r0)     // Catch:{ all -> 0x0282 }
        L_0x026b:
            android.os.HandlerThread r1 = r7.c     // Catch:{ all -> 0x0282 }
            if (r1 == 0) goto L_0x0274
            android.os.HandlerThread r1 = r7.c     // Catch:{ all -> 0x0282 }
            r1.quit()     // Catch:{ all -> 0x0282 }
        L_0x0274:
            r7.f = r0     // Catch:{ all -> 0x0282 }
            n = r0     // Catch:{ all -> 0x0282 }
            throw r8     // Catch:{ all -> 0x0282 }
        L_0x0279:
            java.lang.String r8 = "tr"
            java.lang.String r0 = " Track record happen error "
            defpackage.eao.e(r8, r0)     // Catch:{ all -> 0x0282 }
            monitor-exit(r7)
            return
        L_0x0282:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehl.b(java.lang.String):void");
    }

    private void e() {
        if (this.f != null) {
            synchronized (this.f) {
                if (this.f == null) {
                    eao.d("wbsww", "Invoke of trackrecord is incorrect");
                    return;
                }
                try {
                    Iterator<HealthPoint> it = this.f.iterator();
                    while (it.hasNext()) {
                        HealthPoint next = it.next();
                        if (this.j != null) {
                            this.j.writeLong(next.gps_time);
                            this.j.writeDouble(next.accuracy);
                            this.j.writeDouble(next.latitude);
                            this.j.writeDouble(next.longitude);
                            this.j.writeInt(next.angle);
                            this.j.writeDouble(next.speed);
                            it.remove();
                        }
                    }
                    this.l = this.j != null ? this.j.getFilePointer() : 0;
                    StringBuilder sb = new StringBuilder("position=");
                    sb.append(this.l);
                    eao.d("wbsw", sb.toString());
                    f();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public final synchronized void a(defpackage.ehn.a aVar) {
        if (this.b == null) {
            eao.d("tr", "Failed to create trackrecord in getTrack");
            return;
        }
        this.e.obtainMessage(2, aVar).sendToTarget();
        this.m = true;
    }

    /* access modifiers changed from: private */
    public void f() {
        this.h.putLong("file_offset", this.l);
        d();
    }

    static /* synthetic */ void a(ehl ehl, HealthPoint healthPoint) {
        synchronized (ehl.f) {
            if (ehl.f != null) {
                ehl.f.add(healthPoint);
                ehl.f.notifyAll();
            } else {
                eao.d("wbsww", "Cache has been clean , order is deleted");
            }
        }
    }

    static /* synthetic */ void a(ehl ehl, defpackage.ehn.a aVar) {
        HealthPoint[] healthPointArr;
        synchronized (ehl.f) {
            if (ehl.f == null) {
                eao.d("wbsww", "Invoke of tracerecord is incorrect");
                return;
            }
            long j2 = ehl.l / 44;
            StringBuilder sb = new StringBuilder("pSize=");
            sb.append(j2);
            sb.append(",mPosition=");
            sb.append(ehl.l);
            eao.d("wbsw", sb.toString());
            int i2 = (int) j2;
            long size = j2 + ((long) ehl.f.size());
            healthPointArr = new HealthPoint[((int) size)];
            StringBuilder sb2 = new StringBuilder("pSize=");
            sb2.append(size);
            sb2.append(",index=");
            sb2.append(i2);
            eao.d("wbsw", sb2.toString());
            for (HealthPoint healthPoint : ehl.f) {
                healthPointArr[i2] = healthPoint;
                i2++;
            }
            if (!(size == 0 || ehl.j == null)) {
                try {
                    ehl.j.seek(0);
                    int size2 = (int) (size - ((long) ehl.f.size()));
                    int i3 = 0;
                    while (i3 < size2) {
                        HealthPoint healthPoint2 = new HealthPoint();
                        healthPoint2.gps_time = ehl.j.readLong();
                        healthPoint2.accuracy = ehl.j.readDouble();
                        healthPoint2.latitude = ehl.j.readDouble();
                        healthPoint2.longitude = ehl.j.readDouble();
                        healthPoint2.angle = ehl.j.readInt();
                        healthPoint2.speed = ehl.j.readDouble();
                        healthPoint2.status = HealthPointStatus.HPS_VALID;
                        StringBuilder sb3 = new StringBuilder("1gps_time=");
                        sb3.append(healthPoint2.gps_time);
                        sb3.append(",accuracy=");
                        sb3.append(healthPoint2.accuracy);
                        sb3.append(",latitude=");
                        sb3.append(healthPoint2.latitude);
                        sb3.append(",longitude=");
                        sb3.append(healthPoint2.longitude);
                        sb3.append(",angle=");
                        sb3.append(healthPoint2.angle);
                        sb3.append(",speed=");
                        sb3.append(healthPoint2.speed);
                        eao.d("wbsw", sb3.toString());
                        int i4 = i3 + 1;
                        healthPointArr[i3] = healthPoint2;
                        i3 = i4;
                    }
                } catch (Exception e2) {
                    eao.a((String) "tr", (Throwable) e2);
                    e2.printStackTrace();
                }
            }
        }
        aVar.a(healthPointArr);
        ehl.m = false;
    }

    static /* synthetic */ long a(long j2) {
        long j3 = j2 % 44;
        if (j3 >= 0) {
            return j2 - j3;
        }
        return 0;
    }
}
