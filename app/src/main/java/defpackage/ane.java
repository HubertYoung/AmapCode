package defpackage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus.Listener;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.amap.bundle.logs.AMapLog;

/* renamed from: ane reason: default package */
/* compiled from: GPSStatusListener */
public final class ane implements SensorEventListener, Listener {
    public Callback a;

    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    public ane() {
        if (VERSION.SDK_INT >= 24) {
            this.a = new Callback() {
                public final void onStarted() {
                    AMapLog.i("LocWrapper", "定位启动");
                }

                public final void onStopped() {
                    AMapLog.i("LocWrapper", "定位结束");
                }

                public final void onFirstFix(int i) {
                    AMapLog.i("LocWrapper", "第一次定位");
                }

                public final void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                    anf.a(gnssStatus);
                }
            };
        }
    }

    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (!(sensorEvent == null || sensorEvent.sensor == null)) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int type = sensorEvent.sensor.getType();
            if (type != 6) {
                switch (type) {
                    case 3:
                        anf.b((double) sensorEvent.values[0], elapsedRealtime);
                        return;
                    case 4:
                        return;
                }
            } else {
                anf.a((double) (sensorEvent.values[0] * 100.0f), elapsedRealtime);
            }
        }
    }

    public final void onGpsStatusChanged(int i) {
        switch (i) {
            case 1:
                AMapLog.i("LocWrapper", "定位启动");
                return;
            case 2:
                AMapLog.i("LocWrapper", "定位结束");
                break;
            case 3:
                AMapLog.i("LocWrapper", "第一次定位");
                return;
            case 4:
                anf.c();
                return;
        }
    }
}
