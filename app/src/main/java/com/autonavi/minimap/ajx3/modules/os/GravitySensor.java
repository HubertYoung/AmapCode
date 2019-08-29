package com.autonavi.minimap.ajx3.modules.os;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class GravitySensor implements SensorEventListener {
    public static final int FILTER_COUNT_VALUE = 2;
    private static final float FILTER_LAND = 0.2f;
    private static final float FILTER_PORT = 0.08f;
    public static final String LANDSCAPE_PRIMARY = "landscape-primary";
    public static final String LANDSCAPE_SECONDARY = "landscape-secondary";
    public static final String PORTRAIT_PRIMARY = "portrait-primary";
    public static final String PORTRAIT_SECONDARY = "portrait-secondary";
    private String mCurrentOrientation = PORTRAIT_PRIMARY;
    private int mFilterCount = 0;
    private GravityListener mListener;
    private boolean mSensorEnable;
    private SensorManager mSensorManager;
    private float[] mTempXYZOffset = null;

    public interface GravityListener {
        void onOrientationChanged(String str);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public GravitySensor(@NonNull Context context) {
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        if (this.mSensorManager != null) {
            Sensor defaultSensor = this.mSensorManager.getDefaultSensor(9);
            if (defaultSensor != null) {
                this.mSensorEnable = this.mSensorManager.registerListener(this, defaultSensor, 2);
            }
        }
    }

    public void setGravityListener(GravityListener gravityListener) {
        if (this.mSensorEnable) {
            this.mListener = gravityListener;
            this.mListener.onOrientationChanged(this.mCurrentOrientation);
        }
    }

    public void destroy() {
        if (this.mSensorEnable) {
            this.mSensorManager.unregisterListener(this);
            this.mSensorEnable = false;
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        int i;
        float[] fArr = sensorEvent.values;
        float f = -fArr[0];
        float f2 = -fArr[1];
        float f3 = -fArr[2];
        if (((f * f) + (f2 * f2)) * 4.0f >= f3 * f3) {
            i = 90 - Math.round(((float) Math.atan2((double) (-f2), (double) f)) * 57.29578f);
            while (i >= 360) {
                i -= 360;
            }
            while (i < 0) {
                i += 360;
            }
        } else {
            i = -1;
        }
        String str = this.mCurrentOrientation;
        double d = (double) i;
        if (d > 67.5d && d < 112.5d) {
            str = LANDSCAPE_PRIMARY;
        } else if (d > 157.5d && d < 202.5d) {
            str = PORTRAIT_SECONDARY;
        } else if (d > 247.5d && d < 292.5d) {
            str = LANDSCAPE_SECONDARY;
        } else if ((d > 337.5d && i < 360) || (i > 0 && d < 22.5d)) {
            str = PORTRAIT_PRIMARY;
        }
        if (TextUtils.equals(this.mCurrentOrientation, str)) {
            this.mFilterCount = 0;
            return;
        }
        if (this.mTempXYZOffset == null) {
            this.mTempXYZOffset = new float[]{f, f2, f3};
        }
        float abs = Math.abs(this.mTempXYZOffset[0] - f);
        float abs2 = Math.abs(this.mTempXYZOffset[1] - f2);
        float abs3 = Math.abs(this.mTempXYZOffset[2] - f3);
        this.mTempXYZOffset[0] = f;
        this.mTempXYZOffset[1] = f2;
        this.mTempXYZOffset[2] = f3;
        double d2 = 0.07999999821186066d;
        if (this.mCurrentOrientation.equals(LANDSCAPE_PRIMARY) || this.mCurrentOrientation.equals(LANDSCAPE_PRIMARY)) {
            d2 = 0.20000000298023224d;
        }
        if (((double) (abs + abs2 + abs3)) > d2) {
            this.mFilterCount = 0;
            return;
        }
        this.mFilterCount++;
        if (this.mFilterCount >= 2) {
            if (this.mListener != null) {
                this.mListener.onOrientationChanged(str);
            }
            this.mCurrentOrientation = str;
        }
    }
}
