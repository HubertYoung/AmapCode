package com.alipay.mobile.nebulacore.plugin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5ShakePlugin extends H5SimplePlugin {
    /* access modifiers changed from: private */
    public static int e = 100;
    /* access modifiers changed from: private */
    public static int i = SecExceptionCode.SEC_ERROR_SECURITYBODY;
    /* access modifiers changed from: private */
    public static int k = 4;
    /* access modifiers changed from: private */
    public H5BridgeContext a;
    private boolean b;
    private SensorEventListener c = null;
    /* access modifiers changed from: private */
    public long d;
    /* access modifiers changed from: private */
    public float f;
    /* access modifiers changed from: private */
    public float g;
    /* access modifiers changed from: private */
    public float h;
    /* access modifiers changed from: private */
    public int j = 0;

    private SensorEventListener d() {
        if (this.c == null) {
            this.c = new SensorEventListener() {
                public void onSensorChanged(SensorEvent intent) {
                    long currentUpdateTime = System.currentTimeMillis();
                    long timeInterval = currentUpdateTime - H5ShakePlugin.this.d;
                    if (timeInterval >= ((long) H5ShakePlugin.e)) {
                        H5ShakePlugin.this.d = currentUpdateTime;
                        float x = intent.values[0];
                        float y = intent.values[1];
                        float z = intent.values[2];
                        float deltaX = x - H5ShakePlugin.this.f;
                        float deltaY = y - H5ShakePlugin.this.g;
                        float deltaZ = z - H5ShakePlugin.this.h;
                        H5ShakePlugin.this.f = x;
                        H5ShakePlugin.this.g = y;
                        H5ShakePlugin.this.h = z;
                        double speed = (Math.sqrt((double) (((deltaX * deltaX) + (deltaY * deltaY)) + (deltaZ * deltaZ))) / ((double) timeInterval)) * 10000.0d;
                        H5Log.d("H5ShakePlugin", "onSensorChanged x " + x + " y " + y + " z " + z);
                        H5Log.d("H5ShakePlugin", "speed " + speed);
                        if (speed >= ((double) H5ShakePlugin.i)) {
                            H5Log.d("H5ShakePlugin", "counts" + H5ShakePlugin.this.j);
                            if (H5ShakePlugin.this.j < H5ShakePlugin.k) {
                                H5ShakePlugin.this.j = H5ShakePlugin.this.j + 1;
                                return;
                            }
                            H5ShakePlugin.this.j = 0;
                            H5ShakePlugin.this.g();
                            if (H5ShakePlugin.this.a != null) {
                                if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_use_watchShake"))) {
                                    H5ShakePlugin.this.a.sendSuccess();
                                } else {
                                    H5ShakePlugin.this.a.sendBridgeResult(null);
                                }
                                H5ShakePlugin.this.a = null;
                            }
                        }
                    }
                }

                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
        return this.c;
    }

    private SensorEventListener e() {
        if (this.c == null) {
            this.c = new SensorEventListener() {
                public void onSensorChanged(SensorEvent intent) {
                    boolean z = false;
                    float[] values = intent.values;
                    float x = values[0];
                    float y = values[1];
                    float z2 = values[2];
                    H5Log.d("H5ShakePlugin", "onSensorChanged x " + x + " y " + y + " z " + z2);
                    if (Math.abs(x) > 12.0f || Math.abs(y) > 12.0f || Math.abs(z2) > 12.0f) {
                        z = true;
                    }
                    if (z) {
                        H5ShakePlugin.this.g();
                        if (H5ShakePlugin.this.a != null) {
                            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_use_watchShake"))) {
                                H5ShakePlugin.this.a.sendSuccess();
                            } else {
                                H5ShakePlugin.this.a.sendBridgeResult(null);
                            }
                            H5ShakePlugin.this.a = null;
                        }
                    }
                }

                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
        return this.c;
    }

    public void onInitialize(H5CoreNode coreNode) {
        this.b = false;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.VIBRATE);
        filter.addAction("watchShake");
    }

    public void onRelease() {
        g();
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.VIBRATE.equals(action)) {
            a(bridgeContext, event);
            return true;
        } else if (!"watchShake".equals(action) || (event.getParam() != null && !event.getParam().isEmpty() && event.getParam().size() != 1)) {
            return false;
        } else {
            g();
            f();
            this.a = bridgeContext;
            return true;
        }
    }

    private static void a(H5BridgeContext bridgeContext, H5Event event) {
        try {
            Vibrator vibrator = (Vibrator) H5Environment.getContext().getSystemService("vibrator");
            if (vibrator != null) {
                vibrator.vibrate(400);
            }
            bridgeContext.sendSuccess();
        } catch (Exception e2) {
            H5Log.e((String) "H5ShakePlugin", "vibrate exception: " + e2.getMessage());
            bridgeContext.sendError(event, Error.UNKNOWN_ERROR);
        }
    }

    private void f() {
        SensorEventListener eventListener;
        if (!this.b) {
            SensorManager sensorManager = (SensorManager) H5Environment.getContext().getSystemService("sensor");
            Sensor sensor = sensorManager.getDefaultSensor(1);
            JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_use_watchShake_delayed"));
            String isDelayed = H5Utils.getString(jsonObject, (String) "h5_use_watchShake_is_delayed");
            String isAdjust = H5Utils.getString(jsonObject, (String) "h5_use_watchShake_is_adjust");
            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(isDelayed)) {
                if ("yes".equalsIgnoreCase(isAdjust)) {
                    e = H5Utils.parseInt(H5Utils.getString(jsonObject, (String) "h5_use_watchShake_interval_time"));
                    i = H5Utils.parseInt(H5Utils.getString(jsonObject, (String) "h5_use_watchShake_speed_threshold"));
                    k = H5Utils.parseInt(H5Utils.getString(jsonObject, (String) "h5_use_watchShake_counts_limited"));
                }
                eventListener = d();
            } else {
                eventListener = e();
            }
            sensorManager.registerListener(eventListener, sensor, 3);
            this.b = true;
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        SensorEventListener eventListener;
        if (this.b) {
            SensorManager sensorManager = (SensorManager) H5Environment.getContext().getSystemService("sensor");
            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_use_watchShake_delayed"))) {
                eventListener = d();
            } else {
                eventListener = e();
            }
            sensorManager.unregisterListener(eventListener);
            this.b = false;
        }
    }
}
