package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Application;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class TinyDeviceOrientationPlugin extends H5SimplePlugin implements SensorEventListener {
    private static final String START_DEVICE_MOTION_LISTENING = "startDeviceMotionListening";
    private static final String STOP_DEVICE_MOTION_LISTENING = "stopDeviceMotionListening";
    private static final String TAG = "TinyDeviceOrientationPlugin";
    private float[] accelerometerValues;
    private boolean hasListening = false;
    private H5Service mH5Service;
    private SensorManager mSensorManager;
    private float[] magneticFieldValues;

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
        try {
            LauncherApplicationAgent launcherApplicationAgent = LauncherApplicationAgent.getInstance();
            if (launcherApplicationAgent != null) {
                Application application = launcherApplicationAgent.getApplicationContext();
                if (application != null) {
                    this.mSensorManager = (SensorManager) application.getSystemService("sensor");
                }
                MicroApplicationContext microApplicationContext = launcherApplicationAgent.getMicroApplicationContext();
                if (microApplicationContext != null) {
                    this.mH5Service = (H5Service) microApplicationContext.findServiceByInterface(H5Service.class.getName());
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(START_DEVICE_MOTION_LISTENING);
        filter.addAction(STOP_DEVICE_MOTION_LISTENING);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        int samplingPeriodUs;
        try {
            if (this.mSensorManager == null) {
                context.sendError(1001, (String) "当前系统不支持相关能力");
            } else if (START_DEVICE_MOTION_LISTENING.equals(event.getAction())) {
                if (!this.hasListening) {
                    this.hasListening = true;
                    String interval = H5Utils.getString(event.getParam(), (String) H5SensorPlugin.PARAM_INTERVAL, (String) "ui");
                    if (!TextUtils.equals(interval, "normal")) {
                        if (TextUtils.equals(interval, H5SensorPlugin.INTERVAL_GAME)) {
                            samplingPeriodUs = 1;
                        } else if (TextUtils.equals(interval, "ui")) {
                            samplingPeriodUs = 2;
                        }
                        Sensor accelerometer = this.mSensorManager.getDefaultSensor(1);
                        Sensor magnetic = this.mSensorManager.getDefaultSensor(2);
                        this.mSensorManager.registerListener(this, accelerometer, samplingPeriodUs);
                        this.mSensorManager.registerListener(this, magnetic, samplingPeriodUs);
                        JSONObject result = new JSONObject();
                        result.put((String) "success", (Object) Boolean.valueOf(true));
                        context.sendBridgeResult(result);
                    }
                    samplingPeriodUs = 3;
                    Sensor accelerometer2 = this.mSensorManager.getDefaultSensor(1);
                    Sensor magnetic2 = this.mSensorManager.getDefaultSensor(2);
                    this.mSensorManager.registerListener(this, accelerometer2, samplingPeriodUs);
                    this.mSensorManager.registerListener(this, magnetic2, samplingPeriodUs);
                    JSONObject result2 = new JSONObject();
                    result2.put((String) "success", (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(result2);
                }
            } else if (STOP_DEVICE_MOTION_LISTENING.equals(event.getAction())) {
                if (this.hasListening) {
                    Sensor accelerometer3 = this.mSensorManager.getDefaultSensor(1);
                    Sensor magnetic3 = this.mSensorManager.getDefaultSensor(2);
                    this.mSensorManager.unregisterListener(this, accelerometer3);
                    this.mSensorManager.unregisterListener(this, magnetic3);
                    this.hasListening = false;
                    JSONObject result3 = new JSONObject();
                    result3.put((String) "success", (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(result3);
                } else {
                    context.sendError(10000, (String) "设备方向监听未开启");
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
        return true;
    }

    public void onRelease() {
        super.onRelease();
        try {
            if (this.mSensorManager != null && this.hasListening) {
                Sensor accelerometer = this.mSensorManager.getDefaultSensor(1);
                Sensor magnetic = this.mSensorManager.getDefaultSensor(2);
                this.mSensorManager.unregisterListener(this, accelerometer);
                this.mSensorManager.unregisterListener(this, magnetic);
                this.hasListening = false;
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        try {
            if (event.sensor != null && event.sensor.getType() == 1) {
                this.accelerometerValues = event.values;
            } else if (event.sensor != null && event.sensor.getType() == 2) {
                this.magneticFieldValues = event.values;
            }
            if (this.accelerometerValues != null && this.magneticFieldValues != null) {
                float[] values = new float[3];
                float[] R = new float[9];
                SensorManager.getRotationMatrix(R, null, this.accelerometerValues, this.magneticFieldValues);
                SensorManager.getOrientation(R, values);
                float beta = values[1];
                float gamma = values[2];
                JSONObject result = new JSONObject();
                JSONObject data = new JSONObject();
                data.put((String) "alpha", (Object) Double.valueOf(((double) values[0]) + 3.141592653589793d));
                data.put((String) "beta", (Object) Float.valueOf(beta));
                data.put((String) "gamma", (Object) Float.valueOf(gamma));
                result.put((String) "data", (Object) data);
                if (this.mH5Service != null) {
                    H5Page h5Page = this.mH5Service.getTopH5PageForTiny();
                    if (h5Page != null && h5Page.getBridge() != null) {
                        h5Page.getBridge().sendToWeb("deviceMotionChange", result, null);
                    }
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
