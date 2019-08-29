package com.standardar.common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IMUReader implements SensorEventListener, AutoCloseable {
    public static final int IMU_ACC_TAG = 0;
    public static final int IMU_GRAVITY_TAG = 2;
    public static final int IMU_GYRO_TAG = 1;
    public static final int IMU_RV_TAG = 3;
    public static final long NS_PER_SECOND = 1000000000;
    private static IMUReader mInstance;
    private static Object mInstanceLock = new Object();
    private Object mCallbackLock = new Object();
    private Context mContext;
    long mEnginePtr = 0;
    private Sensor mGravitySensor;
    private Sensor mGyroscopeSensor;
    private Sensor mLinearAccelerationSensor;
    private Sensor mRotationVectorSensor;
    private Set<ISensorNotifyCallback> mSensorNotifiers = new HashSet();
    private Map<Sensor, Integer> mSensorTag = new HashMap();
    long mSessionPtr = 0;
    public boolean mbSensorTimestamp = true;

    public interface ISensorNotifyCallback {
        void onSensorChanged(float[] fArr, int i, long j);
    }

    private native void arSetImuData(long j, int i, float[] fArr, double d);

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void registerCallback(ISensorNotifyCallback iSensorNotifyCallback) {
        synchronized (this.mCallbackLock) {
            if (iSensorNotifyCallback != null) {
                try {
                    this.mSensorNotifiers.add(iSensorNotifyCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void unregisterCallback(ISensorNotifyCallback iSensorNotifyCallback) {
        synchronized (this.mCallbackLock) {
            if (iSensorNotifyCallback != null) {
                try {
                    this.mSensorNotifiers.remove(iSensorNotifyCallback);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static IMUReader getInstance(Context context, long j, long j2) {
        IMUReader iMUReader;
        synchronized (mInstanceLock) {
            if (mInstance == null) {
                IMUReader iMUReader2 = new IMUReader(context, j, j2);
                mInstance = iMUReader2;
            } else {
                mInstance.setContext(context);
                mInstance.setSessionPtr(j);
                mInstance.setEnginePtr(j2);
            }
            iMUReader = mInstance;
        }
        return iMUReader;
    }

    public IMUReader(Context context, long j, long j2) {
        this.mContext = context;
        this.mSessionPtr = j;
        this.mEnginePtr = j2;
    }

    private Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setEnginePtr(long j) {
        this.mEnginePtr = j;
    }

    public void setSessionPtr(long j) {
        this.mSessionPtr = j;
    }

    public void start() {
        RegisterListener();
    }

    public void stop() {
        UnregisterListener();
    }

    public void close() {
        stop();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr = sensorEvent.values;
        long elapsedRealtimeNanos = this.mbSensorTimestamp ? sensorEvent.timestamp : SystemClock.elapsedRealtimeNanos();
        Sensor sensor = sensorEvent.sensor;
        synchronized (this.mCallbackLock) {
            if (this.mSensorNotifiers.size() != 0) {
                StringBuilder sb = new StringBuilder("sensor timestamp ");
                sb.append(sensorEvent.timestamp);
                sb.append(" tag:");
                sb.append(this.mSensorTag.get(sensor));
                Util.LOGD(sb.toString());
                for (ISensorNotifyCallback onSensorChanged : this.mSensorNotifiers) {
                    onSensorChanged.onSensorChanged(fArr, this.mSensorTag.get(sensor).intValue(), elapsedRealtimeNanos);
                }
            }
        }
    }

    private void RegisterListener() {
        SensorManager sensorManager = (SensorManager) getContext().getSystemService("sensor");
        this.mLinearAccelerationSensor = sensorManager.getDefaultSensor(35);
        if (this.mLinearAccelerationSensor == null) {
            Util.LOGE("Do not support linear acceleration uncalibrated sensor");
            this.mLinearAccelerationSensor = sensorManager.getDefaultSensor(10);
            if (this.mLinearAccelerationSensor == null) {
                Util.LOGE("Do not support linear acceleration sensor");
                return;
            }
        }
        this.mGyroscopeSensor = sensorManager.getDefaultSensor(16);
        if (this.mGyroscopeSensor == null) {
            Util.LOGE("Do not support gyroscope sensor");
            return;
        }
        this.mRotationVectorSensor = sensorManager.getDefaultSensor(11);
        if (this.mRotationVectorSensor == null) {
            Util.LOGE("Do not support rotation vector sensor");
            return;
        }
        this.mGravitySensor = sensorManager.getDefaultSensor(9);
        if (this.mGravitySensor == null) {
            Util.LOGE("Do not support gravity sensor");
            return;
        }
        sensorManager.registerListener(this, this.mLinearAccelerationSensor, 2500);
        sensorManager.registerListener(this, this.mGyroscopeSensor, 2500);
        sensorManager.registerListener(this, this.mRotationVectorSensor, 0);
        sensorManager.registerListener(this, this.mGravitySensor, 0);
        this.mSensorTag.put(this.mLinearAccelerationSensor, Integer.valueOf(0));
        this.mSensorTag.put(this.mGyroscopeSensor, Integer.valueOf(1));
        this.mSensorTag.put(this.mRotationVectorSensor, Integer.valueOf(3));
        this.mSensorTag.put(this.mGravitySensor, Integer.valueOf(2));
    }

    private void UnregisterListener() {
        ((SensorManager) getContext().getSystemService("sensor")).unregisterListener(this);
    }
}
