package com.alipay.mobile.base.stepdetect.impl;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.alipay.mobile.framework.service.StepDetectService;

public class StepDetectServiceImpl extends StepDetectService {
    private PowerManager mPowerManager;
    private WakeLock mWakeLock;
    private StepDetector sdsi = null;
    private Sensor sensor;
    private SensorManager sensorManager;

    public void resetSteps(Context context) {
        Editor editor = context.getSharedPreferences(context.getPackageName() + "_stepsCount", 0).edit();
        editor.putLong("stepsCount", 0);
        editor.commit();
    }

    public long getSteps(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_stepsCount", 0).getLong("stepsCount", 0);
    }

    public void stepDetectStart(Context context) {
        if (this.sdsi == null) {
            this.sensorManager = (SensorManager) context.getSystemService("sensor");
            this.sensor = this.sensorManager.getDefaultSensor(1);
            this.mPowerManager = (PowerManager) context.getSystemService("power");
            this.mWakeLock = this.mPowerManager.newWakeLock(268435457, "Jackie");
            this.mWakeLock.acquire();
            this.sdsi = new StepDetector(context);
            this.sensorManager.registerListener(this.sdsi, this.sensor, 0);
        }
    }

    public void stepDetectStop(Context context) {
        if (this.sdsi != null) {
            this.sensorManager.unregisterListener(this.sdsi);
            this.sdsi = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
        if (this.sdsi != null) {
            this.sensorManager.unregisterListener(this.sdsi);
            this.sdsi = null;
        }
    }
}
