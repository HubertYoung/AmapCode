package com.ant.phone.imu.sensor;

import android.hardware.Sensor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/* compiled from: DeviceSensorLooper */
final class b extends HandlerThread {
    final /* synthetic */ DeviceSensorLooper a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    b(DeviceSensorLooper this$0, String x0) {
        // this.a = this$0;
        super(x0);
    }

    /* access modifiers changed from: protected */
    public final void onLooperPrepared() {
        int[] c;
        Handler handler = new Handler(Looper.myLooper());
        for (int sensorType : DeviceSensorLooper.f) {
            Sensor sensor = this.a.b.getDefaultSensor(sensorType);
            Log.i("DeviceSensorLooper", "getDefaultSensor type:" + sensorType + ", result:" + sensor);
            boolean result = this.a.b.registerListener(this.a.d, sensor, 1, handler);
            Log.i("DeviceSensorLooper", "registerListener result:" + result);
            if (sensor == null || !result) {
                Log.w("DeviceSensorLooper", "sensor not supported on this device!");
                this.a.b.unregisterListener(this.a.d);
                Looper.myLooper().quit();
                return;
            }
        }
    }
}
