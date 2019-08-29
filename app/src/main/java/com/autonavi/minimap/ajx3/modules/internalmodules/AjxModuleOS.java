package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.FeatureInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.AjxOrientationHelper;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.bundle.qrscan.scanner.AjxScanManager;
import com.autonavi.minimap.bundle.qrscan.scanner.AjxScanManager.OnFetchTorchStatusCallback;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.os")
public class AjxModuleOS extends AbstractModule {
    public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";
    public static final String MODULE_NAME = "ajx.os";
    @AjxField("device")
    public String device;
    @AjxField("hardwareHeight")
    public Integer hardwareHeight;
    @AjxField("hardwareWidth")
    public Integer hardwareWidth;
    @AjxField("isSupportFlashlight")
    public boolean isSupportFlashlight;
    /* access modifiers changed from: private */
    public JsFunctionCallback mBatterStateCallback;
    private BatteryStateReceiver mBatteryStateReceiver = null;
    @AjxField("name")
    public String name = "android";
    @AjxField("platform")
    public String platform;
    @AjxField("realHeight")
    public Integer realHeight;
    @AjxField("realWidth")
    public Integer realWidth;
    @AjxField("density")
    public Float screenDensity;
    @AjxField("height")
    public Integer screenHeight;
    @AjxField("screenSafeAreaBottom")
    public Integer screenSafeAreaBottom;
    @AjxField("screenSafeAreaLeft")
    public Integer screenSafeAreaLeft;
    @AjxField("screenSafeAreaRight")
    public Integer screenSafeAreaRight;
    @AjxField("screenSafeAreaTop")
    public Integer screenSafeAreaTop;
    @AjxField("width")
    public Integer screenWidth;
    @AjxField("android_sdk_int")
    public Integer sdk_int = Integer.valueOf(VERSION.SDK_INT);
    @AjxField("statusBarHeight")
    public Integer statusBarHeight;
    @AjxField("version")
    public String version = VERSION.RELEASE;

    public class BatteryStateReceiver extends BroadcastReceiver {
        public BatteryStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "android.intent.action.BATTERY_CHANGED")) {
                int i = (intent.getExtras().getInt(H5PermissionManager.level) * 100) / intent.getExtras().getInt(WidgetType.SCALE);
                int intExtra = intent.getIntExtra("status", -1);
                if ((intExtra == 1 || intExtra == 2 || intExtra == 3 || intExtra == 4 || intExtra == 5) && AjxModuleOS.this.mBatterStateCallback != null) {
                    AjxModuleOS.this.mBatterStateCallback.callback(Integer.valueOf(i));
                }
            }
        }

        public void register(Context context) {
            if (context != null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                try {
                    context.registerReceiver(this, intentFilter);
                } catch (Throwable unused) {
                }
            }
        }

        public void unRegister(Context context) {
            if (context != null) {
                try {
                    context.unregisterReceiver(this);
                } catch (Throwable unused) {
                }
            }
        }
    }

    public AjxModuleOS(IAjxContext iAjxContext) {
        super(iAjxContext);
        updateDisplayMetrics();
        this.statusBarHeight = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) getStatusBarHeight(getNativeContext())));
        int i = 0;
        this.screenSafeAreaBottom = Integer.valueOf(0);
        this.screenSafeAreaLeft = Integer.valueOf(0);
        this.screenSafeAreaRight = Integer.valueOf(0);
        this.screenSafeAreaTop = Integer.valueOf(0);
        this.device = Build.MODEL;
        this.platform = Build.MODEL;
        AjxConfig ajxConfig = iAjxContext.getAjxConfig();
        if (ajxConfig != null && ajxConfig.isScreenCutout()) {
            this.screenSafeAreaTop = this.statusBarHeight;
        }
        FeatureInfo[] systemAvailableFeatures = iAjxContext.getNativeContext().getPackageManager().getSystemAvailableFeatures();
        if (systemAvailableFeatures != null && systemAvailableFeatures.length > 0) {
            int length = systemAvailableFeatures.length;
            while (i < length) {
                FeatureInfo featureInfo = systemAvailableFeatures[i];
                if (featureInfo == null || !"android.hardware.camera.flash".equals(featureInfo.name)) {
                    i++;
                } else {
                    this.isSupportFlashlight = true;
                    return;
                }
            }
        }
    }

    public void updateDisplayMetrics() {
        WindowManager windowManager = (WindowManager) getNativeContext().getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
        if (windowManager != null) {
            Display defaultDisplay = windowManager.getDefaultDisplay();
            if (defaultDisplay != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                defaultDisplay.getRealMetrics(displayMetrics);
                this.realWidth = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics.widthPixels));
                this.realHeight = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics.heightPixels));
            }
        }
        DisplayMetrics displayMetrics2 = getNativeContext().getResources().getDisplayMetrics();
        if (this.realWidth.intValue() == 0 || this.realHeight.intValue() == 0) {
            this.realWidth = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics2.widthPixels));
            this.realHeight = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics2.heightPixels));
        }
        this.screenDensity = Float.valueOf(displayMetrics2.density);
        this.screenWidth = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics2.widthPixels));
        this.screenHeight = Integer.valueOf((int) DimensionUtils.pixelToStandardUnit((float) displayMetrics2.heightPixels));
        this.hardwareWidth = this.realWidth;
        this.hardwareHeight = this.realHeight;
        if (this.hardwareWidth.intValue() > this.hardwareHeight.intValue()) {
            int intValue = this.hardwareWidth.intValue();
            this.hardwareWidth = this.hardwareHeight;
            this.hardwareHeight = Integer.valueOf(intValue);
        }
    }

    private int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @AjxMethod(invokeMode = "sync", value = "getScreenHeight")
    public int getCurrentScreenHeight() {
        return (int) DimensionUtils.pixelToStandardUnit((float) getNativeContext().getResources().getDisplayMetrics().heightPixels);
    }

    @AjxMethod(invokeMode = "sync", value = "getScreenWidth")
    public int getCurrentScreenWidth() {
        return (int) DimensionUtils.pixelToStandardUnit((float) getNativeContext().getResources().getDisplayMetrics().widthPixels);
    }

    @AjxMethod(invokeMode = "sync", value = "getOrientation")
    public int getOrientation() {
        return AjxOrientationHelper.getInstance().getOrientation() == 2 ? 1 : 0;
    }

    @AjxMethod("turnOnFlashlight")
    public void turnOnFlashlight() {
        AjxScanManager.getInstance().setTorch(true);
    }

    @AjxMethod("turnOffFlashlight")
    public void turnOffFlashlight() {
        AjxScanManager.getInstance().setTorch(false);
    }

    @AjxMethod("getFlashlightState")
    public void getFlashlightState(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            AjxScanManager.getInstance().getFlashlightState(new OnFetchTorchStatusCallback() {
                public void onTorchStatus(boolean z) {
                    jsFunctionCallback.callback(Boolean.valueOf(z));
                }
            });
        }
    }

    @AjxMethod("vibrate")
    public void vibrate(long j) {
        ((Vibrator) getNativeContext().getSystemService("vibrator")).vibrate(j);
    }

    @AjxMethod(invokeMode = "sync", value = "getOperatorInfo")
    public JSONObject getOperatorInfo() {
        Object obj;
        TelephonyManager telephonyManager = (TelephonyManager) getNativeContext().getSystemService("phone");
        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 5) {
            return null;
        }
        String substring = networkOperator.substring(0, 3);
        String substring2 = networkOperator.substring(3, 5);
        String networkOperatorName = telephonyManager.getNetworkOperatorName();
        int parseInt = Integer.parseInt(substring2);
        switch (parseInt) {
            case 0:
            case 2:
            case 7:
                obj = "cm";
                break;
            case 1:
            case 6:
                obj = "cu";
                break;
            case 3:
            case 5:
            case 11:
                obj = LogItem.MM_C43_K4_CAMERA_TIME;
                break;
            default:
                StringBuilder sb = new StringBuilder("NetWork code undefined. NetworkCode=");
                sb.append(parseInt);
                sb.append("OperatorName=");
                sb.append(networkOperatorName);
                obj = "";
                break;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("operatorType", obj);
            jSONObject.put("operatorCountryCode", substring);
            jSONObject.put("operatorNetworkCode", substring2);
            jSONObject.put("operatorName", networkOperatorName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @AjxMethod("setScreenOnOff")
    public void setScreenOnOff(boolean z) {
        Activity activity = (Activity) getNativeContext();
        if (z) {
            activity.getWindow().addFlags(128);
        } else {
            activity.getWindow().clearFlags(128);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mBatterStateCallback = null;
        if (this.mBatteryStateReceiver != null) {
            this.mBatteryStateReceiver.unRegister(getNativeContext());
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setBrightness")
    public boolean setBrightness(int i) {
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }
        return setScreenLightness(getNativeContext(), (i * 255) / 100);
    }

    @AjxMethod(invokeMode = "sync", value = "getBrightness")
    public int getBrightness() {
        return getScreenLightness(getNativeContext());
    }

    @AjxMethod(invokeMode = "sync", value = "isAutoAdjustLightness")
    public boolean isAutoAdjustLightness() {
        try {
            if (System.getInt(getNativeContext().getContentResolver(), "screen_brightness_mode") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AjxMethod(invokeMode = "sync", value = "stopAutoAdjustLightness")
    public boolean stopAutoBrightness() {
        try {
            Context nativeContext = getNativeContext();
            System.putInt(nativeContext.getContentResolver(), "screen_brightness_mode", 0);
            Uri uriFor = System.getUriFor("screen_brightness");
            if (uriFor != null) {
                nativeContext.getContentResolver().notifyChange(uriFor, null);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AjxMethod(invokeMode = "sync", value = "startAutoAdjustLightness")
    public boolean startAutoBrightness() {
        try {
            Context nativeContext = getNativeContext();
            System.putInt(nativeContext.getContentResolver(), "screen_brightness_mode", 1);
            Uri uriFor = System.getUriFor("screen_brightness");
            if (uriFor != null) {
                nativeContext.getContentResolver().notifyChange(uriFor, null);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getScreenLightness(Context context) {
        int i;
        try {
            i = System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Exception unused) {
            i = 0;
        }
        return (i * 100) / 255;
    }

    public static boolean setScreenLightness(Context context, int i) {
        if (hasWritingPermission(context)) {
            try {
                System.putInt(context.getContentResolver(), "screen_brightness", i);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    @TargetApi(23)
    public static boolean hasWritingPermission(Context context) {
        if (!isMNC()) {
            return true;
        }
        return System.canWrite(context);
    }

    public static boolean isMNC() {
        return VERSION.SDK_INT >= 23;
    }

    @AjxMethod(invokeMode = "sync", value = "getBatteryLevel")
    public int getBatteryState(JsFunctionCallback jsFunctionCallback) {
        Intent registerReceiver = getNativeContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (jsFunctionCallback != null) {
            this.mBatterStateCallback = jsFunctionCallback;
            if (this.mBatteryStateReceiver == null) {
                this.mBatteryStateReceiver = new BatteryStateReceiver();
                this.mBatteryStateReceiver.register(getNativeContext());
            }
        }
        if (registerReceiver == null) {
            return 0;
        }
        return (registerReceiver.getIntExtra(H5PermissionManager.level, -1) * 100) / registerReceiver.getIntExtra(WidgetType.SCALE, -1);
    }

    @AjxMethod("setWindowBrightness")
    public void setWindowBrightness(int i) {
        float f = ((float) i) / 100.0f;
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        Context nativeContext = getNativeContext();
        if (nativeContext instanceof Activity) {
            Window window = ((Activity) nativeContext).getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = f;
            window.setAttributes(attributes);
        }
    }

    @AjxMethod("getWindowBrightness")
    public void getWindowBrightness(JsFunctionCallback jsFunctionCallback) {
        Context nativeContext = getNativeContext();
        if (nativeContext instanceof Activity) {
            float f = ((Activity) nativeContext).getWindow().getAttributes().screenBrightness;
            if (f < 0.0f) {
                jsFunctionCallback.callback(Float.valueOf((float) getScreenLightness(nativeContext)));
                return;
            }
            jsFunctionCallback.callback(Float.valueOf(f * 100.0f));
        }
    }

    @AjxMethod("resetWindowBrightness")
    public void resetWindowBrightness() {
        Context nativeContext = getNativeContext();
        if (nativeContext instanceof Activity) {
            Window window = ((Activity) nativeContext).getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = -1.0f;
            window.setAttributes(attributes);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getTimeIntervalSinceOSBoot")
    public String getTimeIntervalSinceOSBoot() {
        return String.valueOf(SystemClock.elapsedRealtime() / 1000);
    }

    @AjxMethod(invokeMode = "sync", value = "is24HourFormat")
    public boolean is24HourFormat() {
        return DateFormat.is24HourFormat(getNativeContext());
    }

    @AjxMethod(invokeMode = "sync", value = "getHuaWeiDisplayNotchStatus")
    public int getHuaWeiDisplayNotchStatus() {
        try {
            return Secure.getInt(getNativeContext().getContentResolver(), DISPLAY_NOTCH_STATUS, 0);
        } catch (Exception unused) {
            return 0;
        }
    }
}
