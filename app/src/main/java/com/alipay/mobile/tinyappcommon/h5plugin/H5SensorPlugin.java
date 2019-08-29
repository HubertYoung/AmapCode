package com.alipay.mobile.tinyappcommon.h5plugin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class H5SensorPlugin extends H5SimplePlugin {
    public static final String ACTION_WATCH_SHAKE = "watchShake";
    private static final int DELAY_DEFAULT = 50;
    private static final int DELAY_GAME = 20;
    private static final int DELAY_NORMAL = 200;
    private static final int DELAY_UI = 60;
    public static final String GYROSCOPE_CHANGE = "gyroscopeChange";
    public static final String INTERVAL_GAME = "game";
    public static final String INTERVAL_NORMAL = "normal";
    public static final String INTERVAL_UI = "ui";
    public static final String PARAM_INTERVAL = "interval";
    public static final String PARAM_MONITOR_GYROSCOPE = "monitorGyroscope";
    private static final String TAG = "H5SensorPlugin";
    private SensorEventListener accelerometerListener = new SensorChangedListener();
    /* access modifiers changed from: private */
    public float[] accelerometerValues;
    private volatile int delay = 50;
    private SensorEventListener gyroscopeListener = new SensorChangedListener();
    /* access modifiers changed from: private */
    public float[] gyroscopeValues;
    private H5Page h5Page;
    private boolean hasRegisterGyroscope;
    private long lastSendTime = System.currentTimeMillis();
    private SensorEventListener magneticFieldListener = new SensorChangedListener();
    /* access modifiers changed from: private */
    public float[] magneticFieldValues;

    class SensorChangedListener implements SensorEventListener {
        SensorChangedListener() {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event != null && event.values != null && event.sensor != null) {
                int eventType = event.sensor.getType();
                if (eventType == 1) {
                    H5SensorPlugin.this.accelerometerValues = event.values;
                } else if (eventType == 2) {
                    H5SensorPlugin.this.magneticFieldValues = event.values;
                } else if (eventType == 4) {
                    H5SensorPlugin.this.gyroscopeValues = event.values;
                }
                H5SensorPlugin.this.sendDataIfNeed();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("watchShake");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if ("watchShake".equals(event.getAction())) {
            if (H5Utils.getBoolean(event.getParam(), (String) PARAM_MONITOR_GYROSCOPE, false)) {
                unregisterGyroscopeListener();
                registerGyroscopeListener(event);
            } else {
                unregisterGyroscopeListener();
            }
        }
        return false;
    }

    public void onInitialize(H5CoreNode coreNode) {
        if (coreNode instanceof H5Page) {
            this.h5Page = (H5Page) coreNode;
        }
    }

    public void onRelease() {
        unregisterGyroscopeListener();
        this.h5Page = null;
    }

    private boolean registerGyroscopeListener(H5Event event) {
        if (this.hasRegisterGyroscope) {
            return true;
        }
        SensorManager sensorManager = (SensorManager) H5Utils.getContext().getSystemService("sensor");
        if (sensorManager == null) {
            return false;
        }
        Sensor sensor = sensorManager.getDefaultSensor(4);
        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(1);
        Sensor sensorMagneticField = sensorManager.getDefaultSensor(2);
        this.delay = 50;
        if (H5Utils.contains(event.getParam(), (String) PARAM_INTERVAL)) {
            this.delay = (int) (1000.0f * H5Utils.getFloat(event.getParam(), PARAM_INTERVAL, 0.5f));
        }
        int samplingPeriodUs = 3;
        if (this.delay >= 0 && this.delay < 20) {
            samplingPeriodUs = 1;
        } else if (this.delay >= 20 && this.delay < 60) {
            samplingPeriodUs = 1;
        } else if (this.delay >= 60 && this.delay < 200) {
            samplingPeriodUs = 2;
        }
        sensorManager.registerListener(this.gyroscopeListener, sensor, samplingPeriodUs);
        sensorManager.registerListener(this.accelerometerListener, sensorAccelerometer, samplingPeriodUs);
        sensorManager.registerListener(this.magneticFieldListener, sensorMagneticField, samplingPeriodUs);
        this.hasRegisterGyroscope = true;
        return true;
    }

    private void unregisterGyroscopeListener() {
        if (this.hasRegisterGyroscope) {
            SensorManager sensorManager = (SensorManager) H5Utils.getContext().getSystemService("sensor");
            if (sensorManager != null) {
                sensorManager.unregisterListener(this.gyroscopeListener);
                sensorManager.unregisterListener(this.accelerometerListener);
                sensorManager.unregisterListener(this.magneticFieldListener);
                this.hasRegisterGyroscope = false;
                this.accelerometerValues = null;
                this.magneticFieldValues = null;
                this.gyroscopeValues = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void sendDataIfNeed() {
        if (this.accelerometerValues != null && this.magneticFieldValues != null && this.gyroscopeValues != null) {
            long now = System.currentTimeMillis();
            if (now - this.lastSendTime >= ((long) this.delay)) {
                this.lastSendTime = now;
                float x = this.gyroscopeValues[0];
                float y = this.gyroscopeValues[1];
                float z = this.gyroscopeValues[2];
                H5Log.d(TAG, "onSensorChanged x " + x + " y " + y + " z " + z);
                if (this.h5Page == null) {
                    H5Log.w(TAG, "onSensorChanged for null bridge context");
                    return;
                }
                JSONObject result = new JSONObject();
                result.put((String) DictionaryKeys.CTRLXY_X, (Object) Float.valueOf(x));
                result.put((String) DictionaryKeys.CTRLXY_Y, (Object) Float.valueOf(y));
                result.put((String) "z", (Object) Float.valueOf(z));
                this.h5Page.getBridge().sendDataWarpToWeb(GYROSCOPE_CHANGE, result, null);
            }
        }
    }
}
