package com.alipay.mobile.quinox.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.commonbizservice.BuildConfig;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import java.util.HashMap;

public class LiteProcessInfo {
    private static LiteProcessInfo INSTANCE = null;
    public static final int LITE_PROCESS_NUM = 5;
    public static final String TAG = "LiteProcessInfo";
    public static final String[] liteBundles = {"merged-slink-bundles-res", "android-phone-wallet-nebulaconfig", "android-phone-wallet-nebula", "android-phone-wallet-nebulaappcenter", "android-phone-wallet-nebulaappproxy", H5BaseProviderInfo.nebulabiz, "android-phone-wallet-nebulauc", "android-phone-wallet-nebulaconfig", "android-phone-wallet-openplatform", BuildConfig.BUNDLE_NAME, "android-phone-businesscommon-commonbiz", "android-phone-mobilecommon-share", "android-phone-mobilecommon-multimediabiz", "android-phone-wallet-socialchatsdk", "android-phone-mobilesdk-liteprocess", com.alipay.multimedia.apxmmusic.BuildConfig.BUNDLE_NAME, com.alipay.android.phone.bluetoothsdk.BuildConfig.BUNDLE_NAME, "android-phone-mobilesdk-tianyanadapter", com.alipay.android.phone.mobilesdk.permission.BuildConfig.BUNDLE_NAME, "android-phone-wallet-openplatformadapter", H5BaseProviderInfo.tinyappservice, "android-phone-wallet-tinyappcommon", "android-phone-mobilesdk-tinybootloader", "android-phone-mobilesdk-tiny", "android-phone-wallet-advertisement"};
    private Context context;
    private String currentProcessName;
    private HashMap<String, String> liteProcessNames = new HashMap<>();

    public static LiteProcessInfo g(Context context2) {
        if (INSTANCE == null) {
            synchronized (LiteProcessInfo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LiteProcessInfo(context2);
                }
            }
        }
        return INSTANCE;
    }

    public LiteProcessInfo(Context context2) {
        if (context2 != null) {
            if (context2 instanceof Application) {
                this.context = context2;
            } else {
                this.context = context2.getApplicationContext();
                if (this.context == null) {
                    this.context = context2;
                }
            }
            String packageName = this.context.getPackageName();
            for (int i = 1; i <= 5; i++) {
                HashMap<String, String> hashMap = this.liteProcessNames;
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append(":lite");
                sb.append(i);
                hashMap.put(sb.toString(), ProcessInfo.ALIAS_LITE.concat(String.valueOf(i)));
            }
            this.currentProcessName = getCurrentProcessName();
        }
    }

    public boolean isLiteProcess(String str) {
        return this.liteProcessNames.containsKey(str);
    }

    public boolean isCurrentProcessALiteProcess() {
        return this.liteProcessNames.containsKey(this.currentProcessName);
    }

    public int getCurrentLiteProcessId() {
        if (isCurrentProcessALiteProcess()) {
            return Integer.valueOf(this.currentProcessName.substring(this.currentProcessName.length() - 1)).intValue();
        }
        return 0;
    }

    public String getProcessAlias() {
        return this.liteProcessNames.get(this.currentProcessName);
    }

    public String getCurrentProcessName() {
        if (!TextUtils.isEmpty(this.currentProcessName)) {
            return this.currentProcessName;
        }
        try {
            return (String) ReflectUtil.invokeMethod(ReflectUtil.invokeMethod((String) "android.app.ActivityThread", (String) "currentActivityThread"), (String) "getProcessName");
        } catch (Throwable th) {
            TraceLogger.w((String) TAG, th);
        }
        return "";
    }

    public static String[] getLiteBundleList() {
        new StringBuilder("getLiteBundleList: ").append(liteBundles.length);
        return liteBundles;
    }
}
