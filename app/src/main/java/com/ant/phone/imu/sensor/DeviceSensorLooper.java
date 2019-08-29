package com.ant.phone.imu.sensor;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.ArrayList;

public class DeviceSensorLooper implements SensorEventProvider {
    /* access modifiers changed from: private */
    public static final int[] f = {1, 4};
    private boolean a;
    /* access modifiers changed from: private */
    public SensorManager b;
    private Looper c;
    /* access modifiers changed from: private */
    public SensorEventListener d;
    /* access modifiers changed from: private */
    public final ArrayList<SensorEventListener> e = new ArrayList<>();

    public DeviceSensorLooper(SensorManager sensorManager) {
        this.b = sensorManager;
    }

    public final void a() {
        if (!this.a) {
            this.d = new a(this);
            HandlerThread sensorThread = new b(this, "ant3d_sensor");
            sensorThread.start();
            this.c = sensorThread.getLooper();
            this.a = true;
        }
    }

    public final void b() {
        if (this.a) {
            this.b.unregisterListener(this.d);
            this.d = null;
            this.c.quit();
            this.c = null;
            this.a = false;
        }
    }

    public final void a(SensorEventListener listener) {
        synchronized (this.e) {
            this.e.add(listener);
        }
    }

    public final void b(SensorEventListener listener) {
        synchronized (this.e) {
            this.e.remove(listener);
        }
    }
}
