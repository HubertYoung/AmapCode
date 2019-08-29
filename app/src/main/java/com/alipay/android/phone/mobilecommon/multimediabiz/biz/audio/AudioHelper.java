package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

class AudioHelper {
    private static AudioHelper a = new AudioHelper();
    /* access modifiers changed from: private */
    public static final Logger b = Logger.getLogger((String) "AudioHelper");
    private SensorManager c;
    private boolean d = false;
    /* access modifiers changed from: private */
    public CopyOnWriteArraySet<OnSensorChangeListener> e = new CopyOnWriteArraySet<>();
    private SensorEventListener f = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            boolean isDistanceInRange;
            float distance = event.values[0];
            if (((double) distance) < 0.0d || distance >= 5.0f || distance >= event.sensor.getMaximumRange()) {
                isDistanceInRange = false;
            } else {
                isDistanceInRange = true;
            }
            AudioHelper.b.d("onSensorChanged distance = " + distance + ", isDistanceInRange = " + isDistanceInRange, new Object[0]);
            if (isDistanceInRange) {
                Iterator it = AudioHelper.this.e.iterator();
                while (it.hasNext()) {
                    ((OnSensorChangeListener) it.next()).onSensorChanged(true);
                }
                return;
            }
            Iterator it2 = AudioHelper.this.e.iterator();
            while (it2.hasNext()) {
                ((OnSensorChangeListener) it2.next()).onSensorChanged(false);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public interface OnSensorChangeListener {
        void onSensorChanged(boolean z);
    }

    public void registerSensorChangeListener(OnSensorChangeListener l) {
        this.e.add(l);
    }

    public void unregisterSensorChangeListener(OnSensorChangeListener l) {
        this.e.remove(l);
    }

    private AudioHelper() {
    }

    public static AudioHelper getInstance() {
        return a;
    }

    public void registerSensorMonitor(Context context) {
        if (!this.d) {
            try {
                SensorManager sensorManager = a(context);
                sensorManager.registerListener(this.f, sensorManager.getDefaultSensor(8), 3);
                this.d = true;
            } catch (Throwable t) {
                b.e(t, "registerSensorMonitor error", new Object[0]);
            }
        }
    }

    public void unregisterSensorMonitor(Context context) {
        if (this.d) {
            try {
                a(context).unregisterListener(this.f);
                this.d = false;
                this.e.clear();
            } catch (Throwable t) {
                b.e(t, "unregisterSensorMonitor error", new Object[0]);
            }
        }
    }

    private SensorManager a(Context context) {
        if (this.c == null) {
            this.c = (SensorManager) context.getSystemService("sensor");
        }
        return this.c;
    }

    public static void setSpeakerphoneOn(Context context, boolean on) {
        if (context != null) {
            b.d("setSpeakerphoneOn on: " + on, new Object[0]);
            ((AudioManager) context.getSystemService("audio")).setSpeakerphoneOn(on);
        }
    }

    public static boolean isSpeakerphoneOn(Context context) {
        boolean on = true;
        if (context != null) {
            on = ((AudioManager) context.getSystemService("audio")).isSpeakerphoneOn();
        }
        b.d("isSpeakerphoneOn on: " + on, new Object[0]);
        return on;
    }
}
