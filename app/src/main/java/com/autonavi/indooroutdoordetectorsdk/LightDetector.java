package com.autonavi.indooroutdoordetectorsdk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.util.L;

class LightDetector {
    Configuration mConfiguration = null;
    Handler mHandler = null;
    SensorEventListener mSensorListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            try {
                if (sensorEvent.sensor.getType() == 5) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(sensorEvent.values[0]);
                    GeoFenceHelper.logFile("ligt", sb.toString());
                    JNIWrapper.jniSetLightData(System.currentTimeMillis(), (int) sensorEvent.values[0]);
                    LightDetector.this.mHandler.sendEmptyMessage(802);
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    };
    SensorManager mSensorManager = null;

    LightDetector() {
    }

    public void initDetect(Configuration configuration) {
        this.mConfiguration = configuration;
    }

    public void startDetect() {
        if (L.isLogging) {
            L.d((String) "startDetect");
        }
        try {
            if (this.mSensorManager == null) {
                GeoFenceHelper.logFile("SensorStart");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "SensorStart");
                this.mSensorManager = this.mConfiguration.mSensorManager;
                Sensor defaultSensor = this.mConfiguration.mSensorManager.getDefaultSensor(5);
                if (defaultSensor != null) {
                    this.mConfiguration.mSensorManager.registerListener(this.mSensorListener, defaultSensor, 3, this.mHandler);
                    GeoFenceHelper.logFile("SensorStarted");
                }
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public void stopDetect() {
        try {
            if (this.mSensorManager != null) {
                GeoFenceHelper.logFile("SensorStop");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "SensorStop");
                this.mConfiguration.mSensorManager.unregisterListener(this.mSensorListener);
                this.mSensorManager = null;
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("光");
        sb.append(this.mSensorManager != null ? "开启" : "关闭");
        return sb.toString();
    }
}
