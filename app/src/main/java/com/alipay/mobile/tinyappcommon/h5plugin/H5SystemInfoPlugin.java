package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.taobao.accs.common.Constants;
import java.lang.ref.WeakReference;

public class H5SystemInfoPlugin extends H5SimplePlugin {
    private static final int DEFAULT_INTERVAL = 100;
    private static final String GET_SYSTEM_INFO = "getSystemInfo";
    private static final String TAG = "H5SystemInfoPlugin";
    private static final String WATCH_SHAKE = "watchShake";
    private static final String onAccelerometerChange = "accelerometerChange";
    private static final String onCompassChange = "compassChange";
    /* access modifiers changed from: private */
    public WeakReference<Activity> activity;
    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                int level = intent.getIntExtra(H5PermissionManager.level, 0);
                H5SystemInfoPlugin.this.mCachedBatteryPercentage = (level * 100) / intent.getIntExtra(WidgetType.SCALE, 100);
                H5Log.d(H5SystemInfoPlugin.TAG, "ACTION_BATTERY_CHANGED..." + H5SystemInfoPlugin.this.mCachedBatteryPercentage);
            }
        }
    };
    /* access modifiers changed from: private */
    public H5Page h5Page;
    /* access modifiers changed from: private */
    public volatile int interval = 100;
    /* access modifiers changed from: private */
    public Long lastSendTime = Long.valueOf(System.currentTimeMillis());
    /* access modifiers changed from: private */
    public float[] mAccelerateValues;
    /* access modifiers changed from: private */
    public boolean mBatteryBroadcastRegistered = false;
    /* access modifiers changed from: private */
    public int mCachedBatteryPercentage;
    /* access modifiers changed from: private */
    public float[] mMagneticValues;
    /* access modifiers changed from: private */
    public boolean monitorAccelerometer = false;
    /* access modifiers changed from: private */
    public boolean monitorCompass = false;
    private boolean registered;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent intent) {
            if (intent != null && intent.values != null && intent.sensor != null) {
                int type = intent.sensor.getType();
                if ((type == 2 || type == 1) && H5SystemInfoPlugin.this.activity != null && H5SystemInfoPlugin.this.activity.get() != null && !((Activity) H5SystemInfoPlugin.this.activity.get()).isFinishing() && System.currentTimeMillis() - H5SystemInfoPlugin.this.lastSendTime.longValue() > ((long) H5SystemInfoPlugin.this.interval)) {
                    H5SystemInfoPlugin.this.lastSendTime = Long.valueOf(System.currentTimeMillis());
                    if (type == 2) {
                        try {
                            H5SystemInfoPlugin.this.mMagneticValues = intent.values;
                        } catch (Exception e) {
                            H5Log.e((String) H5SystemInfoPlugin.TAG, (Throwable) e);
                            return;
                        }
                    }
                    if (type == 1) {
                        H5SystemInfoPlugin.this.mAccelerateValues = intent.values;
                    }
                    if (H5SystemInfoPlugin.this.mAccelerateValues != null && H5SystemInfoPlugin.this.mMagneticValues != null) {
                        float x = H5SystemInfoPlugin.this.mAccelerateValues[0];
                        float y = H5SystemInfoPlugin.this.mAccelerateValues[1];
                        float z = H5SystemInfoPlugin.this.mAccelerateValues[2];
                        H5Log.d(H5SystemInfoPlugin.TAG, "onSensorChanged x " + x + " y " + y + " z " + z);
                        if (H5SystemInfoPlugin.this.h5Page != null) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put((String) DictionaryKeys.CTRLXY_X, (Object) Float.valueOf((-x) / 10.0f));
                            jsonObject.put((String) DictionaryKeys.CTRLXY_Y, (Object) Float.valueOf((-y) / 10.0f));
                            jsonObject.put((String) "z", (Object) Float.valueOf((-z) / 10.0f));
                            JSONObject param = new JSONObject();
                            float[] calValues = new float[3];
                            float[] R = new float[9];
                            SensorManager.getRotationMatrix(R, null, H5SystemInfoPlugin.this.mAccelerateValues, H5SystemInfoPlugin.this.mMagneticValues);
                            SensorManager.getOrientation(R, calValues);
                            calValues[0] = (float) Math.toDegrees((double) calValues[0]);
                            int direction = (int) ((calValues[0] + 360.0f) % 360.0f);
                            H5Log.d(H5SystemInfoPlugin.TAG, "direction:" + direction);
                            param.put((String) "direction", (Object) Integer.valueOf(direction));
                            if (H5SystemInfoPlugin.this.monitorAccelerometer) {
                                H5SystemInfoPlugin.this.h5Page.getBridge().sendDataWarpToWeb(H5SystemInfoPlugin.onAccelerometerChange, jsonObject, null);
                            }
                            if (H5SystemInfoPlugin.this.monitorCompass) {
                                H5SystemInfoPlugin.this.h5Page.getBridge().sendDataWarpToWeb(H5SystemInfoPlugin.onCompassChange, param, null);
                            }
                        }
                    }
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void registerListener() {
        if (!this.registered) {
            SensorManager sensorManager = (SensorManager) H5Utils.getContext().getSystemService("sensor");
            Sensor accelerometerSensor = sensorManager.getDefaultSensor(1);
            Sensor magneticSensor = sensorManager.getDefaultSensor(2);
            sensorManager.registerListener(this.sensorEventListener, accelerometerSensor, 3);
            sensorManager.registerListener(this.sensorEventListener, magneticSensor, 3);
            this.registered = true;
        }
    }

    private void unregisterListener() {
        if (this.registered) {
            ((SensorManager) H5Utils.getContext().getSystemService("sensor")).unregisterListener(this.sensorEventListener);
            this.registered = false;
            this.monitorAccelerometer = false;
            this.monitorCompass = false;
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_SYSTEM_INFO);
        filter.addAction("watchShake");
    }

    public void onRelease() {
        this.h5Page = null;
        this.activity = null;
        unregisterListener();
        unregisterBroadcastReceiver();
    }

    private void unregisterBroadcastReceiver() {
        H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
            public void run() {
                try {
                    if (H5SystemInfoPlugin.this.mBatteryBroadcastRegistered) {
                        H5Utils.getContext().unregisterReceiver(H5SystemInfoPlugin.this.broadcastReceiver);
                        H5SystemInfoPlugin.this.mBatteryBroadcastRegistered = false;
                    }
                } catch (Throwable e) {
                    H5Log.e((String) H5SystemInfoPlugin.TAG, "unregisterBroadcastReceiver...e=" + e);
                }
            }
        });
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        this.activity = new WeakReference<>(event.getActivity());
        if (GET_SYSTEM_INFO.equals(action)) {
            H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                public void run() {
                    float density = 0.0f;
                    int width = 0;
                    try {
                        DisplayMetrics displayMetrics = ((Activity) H5SystemInfoPlugin.this.activity.get()).getResources().getDisplayMetrics();
                        if (displayMetrics != null) {
                            density = displayMetrics.density;
                            width = Math.round(((float) displayMetrics.widthPixels) / density);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put((String) Constants.KEY_MODEL, (Object) Build.MANUFACTURER + Token.SEPARATOR + Build.MODEL);
                        jsonObject.put((String) "pixelRatio", (Object) Float.valueOf(density));
                        jsonObject.put((String) "windowWidth", (Object) Integer.valueOf(width));
                        jsonObject.put((String) "windowHeight", (Object) Integer.valueOf(H5SystemInfoPlugin.this.getHeight(event, H5SystemInfoPlugin.this.h5Page, density, displayMetrics)));
                        if (displayMetrics != null) {
                            jsonObject.put((String) "screenWidth", (Object) Integer.valueOf(displayMetrics.widthPixels));
                            jsonObject.put((String) "screenHeight", (Object) Integer.valueOf(displayMetrics.heightPixels));
                        }
                        jsonObject.put((String) "system", (Object) VERSION.RELEASE);
                        jsonObject.put((String) "platform", (Object) a.a);
                        jsonObject.put((String) "apiLevel", (Object) Integer.valueOf(VERSION.SDK_INT));
                        jsonObject.put((String) "storage", (Object) H5SystemInfoPlugin.getInternalMemorySize(H5Utils.getContext()));
                        jsonObject.put((String) "currentBattery", (Object) H5SystemInfoPlugin.this.getCurrentBatteryPercentage() + "%");
                        jsonObject.put((String) "brand", (Object) Build.BRAND);
                        if (H5SystemInfoPlugin.this.h5Page == null) {
                            jsonObject.put((String) H5Param.LONG_TRANSPARENT_TITLE, (Object) Boolean.valueOf(false));
                        } else {
                            String trans = H5Utils.getString(H5SystemInfoPlugin.this.h5Page.getParams(), (String) H5Param.LONG_TRANSPARENT_TITLE);
                            if (TextUtils.equals(trans, "auto") || TextUtils.equals(trans, "always") || TextUtils.equals(trans, "custom")) {
                                jsonObject.put((String) H5Param.LONG_TRANSPARENT_TITLE, (Object) Boolean.valueOf(true));
                            } else {
                                jsonObject.put((String) H5Param.LONG_TRANSPARENT_TITLE, (Object) Boolean.valueOf(false));
                            }
                        }
                        jsonObject.put((String) "titleBarHeight", (Object) Integer.valueOf(H5SystemInfoPlugin.this.getTitleBarHeight()));
                        jsonObject.put((String) "statusBarHeight", (Object) Integer.valueOf(H5SystemInfoPlugin.this.getStatusBarHeight(event)));
                        H5SystemInfoPlugin.this.appendSystemInfo(jsonObject);
                        context.sendBridgeResult(jsonObject);
                    } catch (Exception e) {
                        H5Log.e(H5SystemInfoPlugin.TAG, "exception detail: ", e);
                        context.sendError(105, (String) "获取系统信息失败");
                    }
                }
            });
            return true;
        }
        if ("watchShake".equals(event.getAction())) {
            this.monitorAccelerometer = H5Utils.getBoolean(event.getParam(), (String) "monitorAccelerometer", false);
            this.monitorCompass = H5Utils.getBoolean(event.getParam(), (String) "monitorCompass", false);
            this.interval = H5Utils.getInt(event.getParam(), (String) H5SensorPlugin.PARAM_INTERVAL, 100);
            if (this.monitorCompass || this.monitorAccelerometer) {
                if (!this.registered) {
                    registerListener();
                }
            } else if (this.registered) {
                unregisterListener();
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public int getTitleBarHeight() {
        int density = H5DimensionUtil.dip2px(H5Environment.getContext(), 1.0f);
        float titleBarHeight = 0.0f;
        if (density > 0) {
            titleBarHeight = H5Environment.getContext().getResources().getDimension(R.dimen.h5_title_height) / ((float) density);
        }
        return Math.round(titleBarHeight);
    }

    /* access modifiers changed from: private */
    public int getStatusBarHeight(H5Event h5Event) {
        if (h5Event != null) {
            int density = H5DimensionUtil.dip2px(H5Environment.getContext(), 1.0f);
            Rect frame = new Rect();
            Activity topActivity = h5Event.getActivity();
            if (topActivity != null && density > 0) {
                topActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                if (frame.top > 0) {
                    return frame.top / density;
                }
            }
        }
        return 0;
    }

    public int getHeight(H5Event event, H5Page h5Page2, float density, DisplayMetrics displayMetrics) {
        int height = displayMetrics != null ? Math.round(((float) (displayMetrics.heightPixels - getTitleAndStatusBarHeight(event.getActivity()))) / density) : 0;
        boolean getHeightFromWebView = true;
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return height;
        }
        String getHeightWebview = provider.getConfig("h5_getWebViewHeight");
        if (!TextUtils.isEmpty(getHeightWebview) && BQCCameraParam.VALUE_NO.equalsIgnoreCase(getHeightWebview)) {
            getHeightFromWebView = false;
        }
        if (getHeightFromWebView) {
            int heightOfWebView = Math.round(((float) h5Page2.getWebView().getView().getHeight()) / density);
            if (heightOfWebView > 0) {
                height = heightOfWebView;
            }
        }
        return height;
    }

    private int getTitleAndStatusBarHeight(Activity activity2) {
        try {
            Rect frame = new Rect();
            activity2.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            return ((int) activity2.getResources().getDimension(R.dimen.h5_title_height)) + frame.top;
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getTitleAndStatusBarHeight...e=" + e);
            return H5DimensionUtil.dip2px(H5Utils.getContext(), 1.0f) * 73;
        }
    }

    public static String getInternalMemorySize(Context context) {
        if (VERSION.SDK_INT < 18) {
            return "";
        }
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return Formatter.formatFileSize(context, statFs.getBlockCountLong() * statFs.getBlockSizeLong());
        } catch (Throwable e) {
            H5Log.e(TAG, "getInternalMemorySize...", e);
            return "";
        }
    }

    /* access modifiers changed from: private */
    public int getCurrentBatteryPercentage() {
        if (this.mBatteryBroadcastRegistered) {
            return this.mCachedBatteryPercentage;
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            Intent intent = H5Utils.getContext().registerReceiver(this.broadcastReceiver, intentFilter);
            this.mBatteryBroadcastRegistered = true;
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                this.mCachedBatteryPercentage = (intent.getIntExtra(H5PermissionManager.level, 0) * 100) / intent.getIntExtra(WidgetType.SCALE, 100);
                return this.mCachedBatteryPercentage;
            }
        } catch (Exception e) {
            H5Log.e("getCurrentBatteryPercentage...e=" + e);
        }
        return this.mCachedBatteryPercentage;
    }

    /* access modifiers changed from: protected */
    public void appendSystemInfo(JSONObject jsonObject) {
    }
}
