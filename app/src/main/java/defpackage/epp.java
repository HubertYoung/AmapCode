package defpackage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.pos.LocManager;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: epp reason: default package */
/* compiled from: DataRecorder */
public class epp {
    /* access modifiers changed from: private */
    public static long B = 0;
    /* access modifiers changed from: private */
    public static long D = 0;
    /* access modifiers changed from: private */
    public static float E = 0.0f;
    private static final String a = "epp";
    private boolean A;
    private final SensorEventListener C;
    private final SensorEventListener F;
    private long G;
    private Sensor H;
    private final SensorEventListener I;
    private epq b;
    private final SensorManager c;
    /* access modifiers changed from: private */
    public epr d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public epr f;
    /* access modifiers changed from: private */
    public epr g;
    /* access modifiers changed from: private */
    public epr h;
    /* access modifiers changed from: private */
    public int i;
    /* access modifiers changed from: private */
    public long j;
    private float k;
    /* access modifiers changed from: private */
    public long l;
    private long m;
    private Context n;
    private HandlerThread o;
    private final AtomicInteger p;
    private int q;
    /* access modifiers changed from: private */
    public boolean r;
    /* access modifiers changed from: private */
    public boolean s;
    private boolean t;
    private final ahn u;
    private Handler v;
    private boolean w;
    /* access modifiers changed from: private */
    public float[] x;
    /* access modifiers changed from: private */
    public float[] y;
    private final SensorEventListener z;

    /* renamed from: epp$a */
    /* compiled from: DataRecorder */
    static class a {
        public static epp a = new epp(AMapAppGlobal.getApplication(), 0);
    }

    /* synthetic */ epp(Context context, byte b2) {
        this(context);
    }

    public static epp a() {
        return a.a;
    }

    private epp(Context context) {
        this.b = null;
        this.d = new epr();
        this.f = new epr();
        this.g = new epr();
        this.h = new epr();
        this.j = 0;
        this.k = 0.0f;
        this.l = 0;
        this.m = 0;
        this.p = new AtomicInteger(0);
        this.q = -1;
        this.t = false;
        this.w = false;
        this.x = new float[3];
        this.y = new float[3];
        this.z = new SensorEventListener() {
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            public final void onSensorChanged(SensorEvent sensorEvent) {
                if (ahr.a()) {
                    if (sensorEvent.sensor.getType() == 1) {
                        epp.this.x = sensorEvent.values;
                    } else if (sensorEvent.sensor.getType() == 2) {
                        epp.this.y = sensorEvent.values;
                    }
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long a2 = elapsedRealtime - epp.this.l;
                    if (epp.this.l == 0 || a2 >= 100) {
                        anf.b((double) ahr.a(epp.this.x, epp.this.y), elapsedRealtime);
                        epp.this.l = elapsedRealtime;
                    }
                    return;
                }
                if (sensorEvent.sensor.getType() == 3) {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    long a3 = elapsedRealtime2 - epp.this.l;
                    if (epp.this.l == 0 || a3 >= 100) {
                        anf.b((double) sensorEvent.values[0], elapsedRealtime2);
                        epp.this.l = elapsedRealtime2;
                        epr.a(epp.this.h, (double) sensorEvent.values[1], (double) sensorEvent.values[2], (double) sensorEvent.values[0]);
                    }
                }
            }
        };
        this.A = false;
        this.C = new SensorEventListener() {
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            public final void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null && sensorEvent.sensor != null && (((double) sensorEvent.values[0]) != 0.0d || ((double) sensorEvent.values[1]) != 0.0d || ((double) sensorEvent.values[2]) != 0.0d)) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long b = elapsedRealtime - epp.B;
                    if (epp.B == 0 || b >= 37) {
                        int type = sensorEvent.sensor.getType();
                        if (type == 1) {
                            epr.a(epp.this.d, (double) (sensorEvent.values[0] / 9.80665f), (double) (sensorEvent.values[1] / 9.80665f), (double) (sensorEvent.values[2] / 9.80665f));
                            epp.this.e = sensorEvent.accuracy;
                        } else if (type == 4) {
                            epr.a(epp.this.f, (double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2]);
                        }
                        epp.B = elapsedRealtime;
                        epp.a(epp.this, epp.this.r, epp.this.s);
                    }
                }
            }
        };
        this.F = new SensorEventListener() {
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            public final void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null && sensorEvent.sensor != null && (((double) sensorEvent.values[0]) != 0.0d || ((double) sensorEvent.values[1]) != 0.0d || ((double) sensorEvent.values[2]) != 0.0d)) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    epp.this.j = elapsedRealtime;
                    int type = sensorEvent.sensor.getType();
                    if (type != 4) {
                        switch (type) {
                            case 1:
                                epr.a(epp.this.d, (double) (sensorEvent.values[0] / 9.80665f), (double) (sensorEvent.values[1] / 9.80665f), (double) (sensorEvent.values[2] / 9.80665f));
                                epp.this.e = sensorEvent.accuracy;
                                break;
                            case 2:
                                epr.a(epp.this.g, (double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2]);
                                epp.this.i = sensorEvent.accuracy;
                                break;
                        }
                    } else {
                        epr.a(epp.this.f, (double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2]);
                    }
                    long c = elapsedRealtime - epp.D;
                    if (epp.D == 0 || c >= 37) {
                        epp.D = elapsedRealtime;
                        epp.a(epp.this, epp.this.r, epp.this.s);
                    }
                }
            }
        };
        this.G = System.currentTimeMillis();
        this.H = null;
        this.I = new SensorEventListener() {
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            public final void onSensorChanged(SensorEvent sensorEvent) {
                if (VERSION.SDK_INT >= 20) {
                    String stringType = sensorEvent.sensor.getStringType();
                    if (!TextUtils.isEmpty(stringType) && stringType.toUpperCase().indexOf("TEMP") > 0) {
                        epp.E = sensorEvent.values[0];
                    }
                }
            }
        };
        this.n = context;
        this.c = (SensorManager) context.getSystemService("sensor");
        this.b = epq.a();
        this.u = new ahn(1);
    }

    public final void a(final int i2) {
        if (i2 == 4) {
            this.u.b.clear();
            b(i2);
            return;
        }
        this.u.execute(new Runnable() {
            public final void run() {
                epp.this.b(i2);
            }
        });
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        if (this.q != i2) {
            this.q = i2;
            switch (i2) {
                case 0:
                    i();
                    k();
                    e();
                    return;
                case 1:
                    h();
                    e();
                    k();
                    return;
                case 2:
                    h();
                    e();
                    j();
                    return;
                case 3:
                    h();
                    d();
                    k();
                    return;
                case 4:
                    i();
                    k();
                    e();
                    return;
                default:
                    i();
                    k();
                    e();
                    break;
            }
        }
    }

    private void d() {
        if (!this.t) {
            this.r = true;
            this.s = false;
            this.m = System.currentTimeMillis();
            l();
            this.t = true;
        }
    }

    private void e() {
        if (this.t) {
            m();
            this.b.b();
            this.j = 0;
            this.k = 0.0f;
            this.m = 0;
            this.t = false;
        }
    }

    private void f() {
        if (this.p.intValue() <= 0 && this.o == null) {
            this.o = new HandlerThread("AchSensorThread");
            this.o.start();
            this.v = new Handler(this.o.getLooper());
        }
        this.p.incrementAndGet();
    }

    private void g() {
        this.p.decrementAndGet();
        if (this.p.intValue() <= 0) {
            this.p.set(0);
            HandlerThread handlerThread = this.o;
            if (handlerThread != null) {
                if (VERSION.SDK_INT >= 18) {
                    handlerThread.quitSafely();
                } else {
                    handlerThread.quit();
                }
                this.v = null;
                this.o = null;
            }
        }
    }

    private void h() {
        if (!this.w) {
            f();
            try {
                if (this.o != null) {
                    if (this.o.isAlive()) {
                        if (!ahr.a() || !ahr.b()) {
                            this.c.registerListener(this.z, this.c.getDefaultSensor(3), 2, this.v);
                        } else {
                            this.c.registerListener(this.z, this.c.getDefaultSensor(2), 2, this.v);
                            this.c.registerListener(this.z, this.c.getDefaultSensor(1), 2, this.v);
                        }
                        this.w = true;
                    }
                }
            } catch (Exception e2) {
                if (bno.a) {
                    AMapLog.e(a, Log.getStackTraceString(e2));
                }
            }
        }
    }

    private void i() {
        if (this.w) {
            this.c.unregisterListener(this.z);
            g();
            this.w = false;
        }
    }

    private void j() {
        if (!this.A) {
            f();
            Sensor defaultSensor = this.c.getDefaultSensor(1);
            Sensor defaultSensor2 = this.c.getDefaultSensor(4);
            if (!(defaultSensor == null || defaultSensor2 == null || this.o == null || !this.o.isAlive())) {
                try {
                    this.c.registerListener(this.C, defaultSensor, 1, this.v);
                    this.c.registerListener(this.C, defaultSensor2, 1, this.v);
                } catch (Exception e2) {
                    if (bno.a) {
                        AMapLog.e(a, Log.getStackTraceString(e2));
                    }
                }
            }
            this.A = true;
        }
    }

    private void k() {
        if (this.A) {
            this.c.unregisterListener(this.C);
            g();
            this.A = false;
        }
    }

    private void l() {
        f();
        Sensor defaultSensor = this.c.getDefaultSensor(1);
        Sensor defaultSensor2 = this.c.getDefaultSensor(4);
        Sensor defaultSensor3 = this.c.getDefaultSensor(2);
        if (!(defaultSensor == null || defaultSensor2 == null || defaultSensor3 == null || this.o == null || !this.o.isAlive())) {
            try {
                this.c.registerListener(this.F, defaultSensor, 1, this.v);
                this.c.registerListener(this.F, defaultSensor2, 1, this.v);
                this.c.registerListener(this.F, defaultSensor3, 1, this.v);
            } catch (Exception e2) {
                if (bno.a) {
                    AMapLog.e(a, Log.getStackTraceString(e2));
                }
            }
        }
        n();
    }

    private void m() {
        this.c.unregisterListener(this.F);
        g();
    }

    private void n() {
        if (this.c != null) {
            for (Sensor next : this.c.getSensorList(-1)) {
                if (VERSION.SDK_INT > 19) {
                    String stringType = next.getStringType();
                    if (!TextUtils.isEmpty(stringType) && stringType.toUpperCase().indexOf("TEMP") > 0) {
                        this.H = next;
                    }
                }
                if (this.H != null) {
                    this.c.registerListener(this.I, this.H, 1);
                }
            }
        }
    }

    static /* synthetic */ void a(epp epp, boolean z2, boolean z3) {
        epp epp2 = epp;
        epp2.j = SystemClock.elapsedRealtime();
        epr epr = epp2.d;
        epr epr2 = epp2.f;
        epr epr3 = epp2.g;
        epr epr4 = epp2.h;
        double d2 = (double) epp2.j;
        int currentTimeMillis = (int) (System.currentTimeMillis() - epp2.G);
        epp2.G = System.currentTimeMillis();
        if (!(epr == null || epr2 == null || epr3 == null)) {
            long j2 = (long) d2;
            long j3 = j2;
            LocManager.setAcce(4, (float) epr.x, (float) epr.y, (float) epr.z, currentTimeMillis, j2);
            LocManager.setGyro(4, (float) epr2.x, (float) epr2.y, (float) epr2.z, E, currentTimeMillis, j3);
            LocManager.setMagnetic(4, currentTimeMillis, (float) epr3.x, (float) epr3.y, (float) epr3.z, j3);
        }
        if (epr4 != null) {
            LocManager.setOrientation(4, currentTimeMillis, (float) epr4.x, (float) epr4.y, (float) epr4.z, (long) d2);
        }
        if (z3 && !epr.a(epp2.d)) {
            epp2.b.a(epp2.d, epp2.g, epp2.f, epp2.j, epp2.i, epp2.e, z2);
        }
    }
}
