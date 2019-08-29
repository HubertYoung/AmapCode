package defpackage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;

/* renamed from: ecc reason: default package */
/* compiled from: NaviSensorHelper */
public final class ecc {
    public a a;
    private Context b;
    private Sensor c;
    private SensorManager d;
    private HandlerThread e;
    private float[] f = new float[3];
    private float[] g = new float[3];
    private SensorEventListener h = new SensorEventListener() {
        public final void onSensorChanged(SensorEvent sensorEvent) {
        }

        public final void onAccuracyChanged(final Sensor sensor, final int i) {
            if (ahr.a()) {
                if (!(sensor.getType() == 2 || sensor.getType() == 1)) {
                    return;
                }
            } else if (sensor.getType() != 3) {
                return;
            }
            aho.a(new Runnable() {
                public final void run() {
                    if (ecc.this.a != null) {
                        ecc.this.a.a(sensor.getType(), i);
                    }
                }
            });
        }
    };

    /* renamed from: ecc$a */
    /* compiled from: NaviSensorHelper */
    public interface a {
        void a(int i, int i2);
    }

    public ecc(Context context) {
        this.b = context;
    }

    public final void a() {
        this.d = (SensorManager) this.b.getSystemService("sensor");
        this.c = this.d.getDefaultSensor(3);
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append("_NaviSensorThread");
        this.e = new HandlerThread(sb.toString());
        this.e.start();
        Handler handler = new Handler(this.e.getLooper());
        if (!ahr.a() || !ahr.b()) {
            this.d.registerListener(this.h, this.c, 1, handler);
            return;
        }
        this.d.registerListener(this.h, this.d.getDefaultSensor(2), 1, handler);
        this.d.registerListener(this.h, this.d.getDefaultSensor(1), 1, handler);
    }

    public final void b() {
        if (this.d != null) {
            this.d.unregisterListener(this.h);
            this.d = null;
        }
        if (this.e != null) {
            if (VERSION.SDK_INT >= 18) {
                this.e.quitSafely();
            } else {
                this.e.quit();
            }
            this.e = null;
        }
        this.c = null;
        this.a = null;
    }
}
