package com.ant.phone.imu.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.Iterator;

/* compiled from: DeviceSensorLooper */
final class a implements SensorEventListener {
    final /* synthetic */ DeviceSensorLooper a;

    a(DeviceSensorLooper this$0) {
        this.a = this$0;
    }

    public final void onSensorChanged(SensorEvent event) {
        synchronized (this.a.e) {
            for (int i = 0; i < this.a.e.size(); i++) {
                SensorEventListener listener = (SensorEventListener) this.a.e.get(i);
                synchronized (listener) {
                    if (listener != null) {
                        listener.onSensorChanged(event);
                    }
                }
            }
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        synchronized (this.a.e) {
            Iterator it = this.a.e.iterator();
            while (it.hasNext()) {
                SensorEventListener listener = (SensorEventListener) it.next();
                synchronized (listener) {
                    listener.onAccuracyChanged(sensor, accuracy);
                }
            }
        }
    }
}
