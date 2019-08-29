package com.autonavi.minimap.agroup.util;

import android.os.Looper;
import android.os.SystemClock;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BackgroundLocateManager {
    public static final String a = "BackgroundLocateManager";
    public static boolean c = false;
    public static boolean i = false;
    public static boolean j = false;
    private static volatile BackgroundLocateManager k;
    private static final Object l = new Object();
    private static final Object m = new Object();
    public agl<a> b = new agl<>();
    boolean d = false;
    long e = 0;
    long f = 0;
    ScheduledExecutorService g = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService h = Executors.newSingleThreadScheduledExecutor();
    private c n = new c(this);
    private ScheduledFuture<?> o = null;
    private b p = new b(this);
    private ScheduledFuture<?> q = null;
    private Callback<Status> r = new LocateCallback(this);

    static class LocateCallback implements Callback<Status> {
        private WeakReference<BackgroundLocateManager> mManager;

        public LocateCallback(BackgroundLocateManager backgroundLocateManager) {
            this.mManager = new WeakReference<>(backgroundLocateManager);
        }

        public void callback(Status status) {
            BackgroundLocateManager.a("BackgroundLocateManager LocateCallback. callback. staus: %s", status);
            if (status == Status.ON_LOCATION_OK && this.mManager.get() != null) {
                BackgroundLocateManager.c((BackgroundLocateManager) this.mManager.get());
                boolean d = ((BackgroundLocateManager) this.mManager.get()).h();
                if (d) {
                    ((BackgroundLocateManager) this.mManager.get()).b.a((defpackage.agl.a<T>) new defpackage.agl.a<a>() {
                        public final /* synthetic */ void onNotify(Object obj) {
                            a aVar = (a) obj;
                            BackgroundLocateManager.a("BackgroundLocateManager LocateCallback. callback. onNotify. isValid: %s.", Boolean.valueOf(aVar.a()));
                            if (aVar.a()) {
                                aVar.a(LocationInstrument.getInstance().getLatestPosition());
                            }
                        }
                    });
                    return;
                }
                this.mManager.get();
                BackgroundLocateManager.a("BackgroundLocateManager LocateCallback. callback. do no thing. isSequenceMatch: %s, isLocation: %s", Boolean.valueOf(d), Boolean.valueOf(BackgroundLocateManager.i()));
            }
        }

        public void error(Throwable th, boolean z) {
            BackgroundLocateManager.b("BackgroundLocateManager LocateCallback. error.", new Object[0]);
        }
    }

    public interface a {
        void a(GeoPoint geoPoint);

        boolean a();

        long b();

        long c();
    }

    static class b implements Runnable {
        WeakReference<BackgroundLocateManager> a;
        Runnable b = new Runnable() {
            public final void run() {
                BackgroundLocateManager.a("BackgroundLocateManager LocateFailTask. onRun()", new Object[0]);
                if (b.this.a.get() != null) {
                    BackgroundLocateManager.c((BackgroundLocateManager) b.this.a.get());
                }
            }
        };

        public b(BackgroundLocateManager backgroundLocateManager) {
            this.a = new WeakReference<>(backgroundLocateManager);
        }

        public final void run() {
            boolean c = BackgroundLocateManager.c();
            BackgroundLocateManager.a("BackgroundLocateManager LocateFailTask. run(). isUIThread: %s", Boolean.valueOf(c));
            if (c) {
                this.b.run();
                return;
            }
            aho.b(this.b);
            aho.a(this.b);
        }
    }

    static class c implements Runnable {
        WeakReference<BackgroundLocateManager> a;
        Runnable b = new Runnable() {
            public final void run() {
                BackgroundLocateManager.a("BackgroundLocateManager ScheduleTask. onRun().", new Object[0]);
                if (c.this.a.get() != null) {
                    long a2 = ((BackgroundLocateManager) c.this.a.get()).e();
                    if (a2 > 0) {
                        BackgroundLocateManager.b((BackgroundLocateManager) c.this.a.get());
                        return;
                    }
                    BackgroundLocateManager.a("BackgroundLocateManager. ScheduleTask. cancel startLocate beacause periodMilliSecs: %s", Long.valueOf(a2));
                    ((BackgroundLocateManager) c.this.a.get()).b();
                }
            }
        };

        public c(BackgroundLocateManager backgroundLocateManager) {
            this.a = new WeakReference<>(backgroundLocateManager);
        }

        public final void run() {
            boolean c = BackgroundLocateManager.c();
            BackgroundLocateManager.a("BackgroundLocateManager ScheduleTask. run(). isUIThread: %s", Boolean.valueOf(c));
            if (c) {
                this.b.run();
                return;
            }
            aho.b(this.b);
            aho.a(this.b);
        }
    }

    public static BackgroundLocateManager a() {
        if (k == null) {
            synchronized (l) {
                try {
                    if (k == null) {
                        k = new BackgroundLocateManager();
                    }
                }
            }
        }
        return k;
    }

    public final void a(boolean z) {
        a("BackgroundLocateManager start. restart: %s", Boolean.valueOf(z));
        if (i()) {
            b();
            a("BackgroundLocateManager start. return because isLocating.", new Object[0]);
            return;
        }
        if (z) {
            b();
            this.f = SystemClock.elapsedRealtime();
        }
        synchronized (m) {
            if (!this.d) {
                long e2 = e();
                if (e2 > 0) {
                    a("BackgroundLocateManager start. postDelayed mScheduleTask in %s millisecends.", Long.valueOf(e2));
                    a(e2);
                    this.d = true;
                } else {
                    a("BackgroundLocateManager start. canceled beacause periodMilliSecs: %s", Long.valueOf(e2));
                }
            } else {
                a("BackgroundLocateManager start. already started.", new Object[0]);
            }
        }
    }

    public final void b() {
        a("BackgroundLocateManager stop", new Object[0]);
        synchronized (m) {
            f();
            g();
            LocationInstrument.getInstance().removeStatusCallback(this.r);
            this.d = false;
        }
    }

    /* access modifiers changed from: private */
    public long e() {
        long j2 = 0;
        for (a aVar : this.b.a()) {
            long b2 = !c ? aVar.b() : 10000;
            if (aVar.a() && b2 > 0) {
                if (j2 == 0 || b2 < j2) {
                    j2 = b2;
                }
            }
        }
        a("BackgroundLocateManager getPeriodMilliSecs. return: %s", Long.valueOf(j2));
        return j2;
    }

    private void f() {
        Object[] objArr = new Object[1];
        objArr[0] = Boolean.valueOf(this.o != null);
        a("cancelScheduleTask. scheduleTaskFuture: %s", objArr);
        if (this.o != null) {
            this.o.cancel(true);
            this.o = null;
        }
        aho.b(this.n.b);
    }

    private void a(long j2) {
        boolean z = true;
        Object[] objArr = new Object[1];
        if (this.o == null) {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        a("startScheduleTask. scheduleTaskFuture: %s", objArr);
        f();
        this.o = this.g.schedule(this.n, j2, TimeUnit.MILLISECONDS);
    }

    private void g() {
        Object[] objArr = new Object[1];
        objArr[0] = Boolean.valueOf(this.q != null);
        a("cancelLocateFailTask. locateFailTaskFuture: %s", objArr);
        if (this.q != null) {
            this.q.cancel(true);
            this.q = null;
        }
        aho.b(this.p.b);
    }

    /* access modifiers changed from: private */
    public boolean h() {
        a("BackgroundLocateManager isSequenceMatch. mStartLocateSecquence: %s, getStartLocateSecquence: %s", Long.valueOf(this.e), Long.valueOf(LocationInstrument.getInstance().getStartLocateSequence()));
        return this.e > 0 && this.e == LocationInstrument.getInstance().getStartLocateSequence();
    }

    private static boolean j() {
        if (!i) {
            return false;
        }
        boolean equals = "test".equals(ConfigerHelper.getInstance().getNetCondition());
        if (bno.a || equals) {
            return true;
        }
        return false;
    }

    public static void a(String str, Object... objArr) {
        if (j()) {
            b(str, objArr);
        }
    }

    public static void b(String str, Object... objArr) {
        if (j()) {
            try {
                a(String.format(str, objArr));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void a(String str) {
        if (j()) {
            try {
                if (j) {
                    FileUtil.saveLogToFile(String.format("[%s]---%s", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), str}), "BackgroundLocateManagerLog.txt");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean i() {
        return LocationInstrument.getInstance().isLocating();
    }

    static /* synthetic */ boolean c() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    static /* synthetic */ void b(BackgroundLocateManager backgroundLocateManager) {
        a("BackgroundLocateManager startLocate", new Object[0]);
        if (!i()) {
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(backgroundLocateManager.q != null);
            a("startLocateFailTask. locateFailTaskFuture: %s", objArr);
            backgroundLocateManager.g();
            backgroundLocateManager.q = backgroundLocateManager.h.schedule(backgroundLocateManager.p, 5000, TimeUnit.MILLISECONDS);
            LocationInstrument.getInstance().addStatusCallback(backgroundLocateManager.r, null);
            LocationInstrument.getInstance().doStartLocate(true);
            backgroundLocateManager.e = LocationInstrument.getInstance().getStartLocateSequence();
            a("BackgroundLocateManager startLocate. doStartLocate. mStartLocateSecquence: %s", Long.valueOf(backgroundLocateManager.e));
            return;
        }
        a("BackgroundLocateManager startLocate. do no thing. because isLocating.", new Object[0]);
    }

    static /* synthetic */ void c(BackgroundLocateManager backgroundLocateManager) {
        a("BackgroundLocateManager stopLocate", new Object[0]);
        backgroundLocateManager.g();
        LocationInstrument.getInstance().removeStatusCallback(backgroundLocateManager.r);
        boolean h2 = backgroundLocateManager.h();
        if (!h2 || !i()) {
            a("BackgroundLocateManager stopLocate. do no thing. isSequenceMatch: %s, isLocation: %s", Boolean.valueOf(h2), Boolean.valueOf(i()));
        } else {
            LocationInstrument.getInstance().doStopLocate(true);
            backgroundLocateManager.e = LocationInstrument.getInstance().getStartLocateSequence();
            a("BackgroundLocateManager stopLocate. doStopLocate.", new Object[0]);
        }
        if (backgroundLocateManager.d) {
            long j2 = 0;
            for (a aVar : backgroundLocateManager.b.a()) {
                long c2 = !c ? aVar.c() : 60000;
                if (aVar.a() && c2 > 0) {
                    if (j2 == 0 || c2 > j2) {
                        j2 = c2;
                    }
                }
            }
            a("BackgroundLocateManager getContinuousMilliSecs. return: %s", Long.valueOf(j2));
            long elapsedRealtime = SystemClock.elapsedRealtime() - backgroundLocateManager.f;
            if (backgroundLocateManager.f <= 0 || j2 <= 0 || elapsedRealtime <= j2) {
                backgroundLocateManager.b();
                backgroundLocateManager.a(false);
            } else {
                a("BackgroundLocateManager stopLocate. do not start next task because over continuous time. timeSpan: %s, continuousMilliSecs: %s", Long.valueOf(elapsedRealtime), Long.valueOf(j2));
                backgroundLocateManager.b();
            }
        }
    }
}
