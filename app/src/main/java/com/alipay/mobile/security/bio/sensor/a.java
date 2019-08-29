package com.alipay.mobile.security.bio.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.alipay.mobile.security.bio.sensor.SensorCollectors.SensorType;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: SensorCollectWorker */
final class a implements SensorEventListener {
    Sensor a = null;
    SensorManager b;
    String c;
    int d;
    private Object e = new Object();
    private String f = "[,,]";

    public a(SensorManager sensorManager, SensorType sensorType) {
        if (sensorManager != null && sensorType != null) {
            this.b = sensorManager;
            this.a = sensorManager.getDefaultSensor(sensorType.getmSensorType());
            this.c = sensorType.getSensorName();
            this.d = sensorType.getmSensorType();
            if (this.a == null) {
                BioLog.i("SensorCollectWorker: " + sensorType.getSensorName() + " 注册失败.［" + System.currentTimeMillis() + "]");
            } else {
                BioLog.i("SensorCollectWorker: " + sensorType.getSensorName() + " 注册成功.［" + System.currentTimeMillis() + "]");
            }
        }
    }

    public final int a() {
        if (this.a == null) {
            return -1;
        }
        return this.d;
    }

    public final String b() {
        String str;
        synchronized (this.e) {
            try {
                str = this.f;
            }
        }
        return str;
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent != null && sensorEvent.values != null) {
            StringBuilder sb = new StringBuilder();
            try {
                int length = sensorEvent.values.length;
                sb.append("[");
                for (int i = 0; i < length; i++) {
                    sb.append((int) (sensorEvent.values[i] * 100.0f));
                    if (i + 1 < length) {
                        sb.append(",");
                    }
                }
                sb.append("]");
            } catch (Throwable th) {
            }
            synchronized (this.e) {
                this.f = sb.toString();
            }
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
