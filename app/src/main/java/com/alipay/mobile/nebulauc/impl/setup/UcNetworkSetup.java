package com.alipay.mobile.nebulauc.impl.setup;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulauc.impl.network.AlipayNetwork;
import com.alipay.mobile.nebulauc.impl.network.AlipayNetworkDecider;
import com.alipay.mobile.nebulauc.impl.network.AlipayNetworkDelegate;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.taobao.accs.common.Constants;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCSettings;
import java.util.HashMap;

public class UcNetworkSetup {
    private static final String LAST_CLEAR_CACHE_SWITCH_VALUE = "lastClearCacheSwitchValue";
    private static final String TAG = "H5UcService::UcNetworkSetup";
    public static JSONArray sH2AppIdBlackList = null;
    private static Boolean sInitUcNetworkNewTiming;
    public static int sMaxRequestPerClient = 13;
    public static int sMaxRequestPerHost = 6;
    public static boolean sUcRequestSettingEnabled = true;

    public static boolean useNewInitTiming() {
        if (sInitUcNetworkNewTiming == null) {
            sInitUcNetworkNewTiming = Boolean.valueOf(!"NO".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_initUcNetworkNewTiming")));
        }
        H5Log.d(TAG, "[UcNetworkSetup] useNewInitTiming: " + sInitUcNetworkNewTiming);
        return sInitUcNetworkNewTiming.booleanValue();
    }

    public static void initNetworkConfig() {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                UcNetworkSetup.setThirdNetwork();
                UcNetworkSetup.setNetworkDelegate();
                UcNetworkSetup.initUcRequestSetting();
                UcNetworkSetup.initHttpCacheUpperSize();
                UcNetworkSetup.initUcHttpCacheSdCardSetting();
            }
        });
    }

    /* access modifiers changed from: private */
    public static void setThirdNetwork() {
        H5Log.d(TAG, "[UcNetworkSetup] setThirdNetwork");
        UcSetupTracing.beginTrace("setThirdNetwork");
        JSONObject jsonObjNetWork = H5Utils.parseObject(H5ConfigUtil.getConfig("h5_ucNetConfig"));
        boolean useAlipayNet = false;
        if (jsonObjNetWork != null) {
            useAlipayNet = "YES".equals(jsonObjNetWork.getString("useAlipayNet"));
        }
        H5Log.d(TAG, "[UcNetworkSetup] useAlipayNet " + useAlipayNet);
        if (useAlipayNet && !disableUcNetByPhoneInfo(jsonObjNetWork.getJSONArray("phoneBlacklist"))) {
            UCCore.setThirdNetwork(new AlipayNetwork(), new AlipayNetworkDecider());
        }
        UcSetupTracing.endTrace("setThirdNetwork");
    }

    /* access modifiers changed from: private */
    public static void setNetworkDelegate() {
        H5Log.d(TAG, "[UcNetworkSetup] setNetworkDelegate");
        UcSetupTracing.beginTrace("setNetworkDelegate");
        JSONObject jsonObjWebp = H5Utils.parseObject(H5ConfigUtil.getConfig(H5Utils.KEY_H5_CDN_WEBP_CONFIG));
        boolean useWebp = false;
        if (jsonObjWebp != null) {
            useWebp = "YES".equals(jsonObjWebp.getString("enable"));
        }
        H5Log.d(TAG, "[UcNetworkSetup] useWebp " + useWebp);
        if (useWebp) {
            UCCore.setNetworkDelegate(new AlipayNetworkDelegate());
        }
        UcSetupTracing.endTrace("setNetworkDelegate");
    }

    /* access modifiers changed from: private */
    public static void initUcRequestSetting() {
        H5Log.d(TAG, "[UcNetworkSetup] initUcRequestSetting");
        UcSetupTracing.beginTrace("initUcRequestSetting");
        JSONObject ucRequestSetting = H5Utils.parseObject(H5ConfigUtil.getConfig("h5_ucRequestSetting"));
        if (ucRequestSetting != null) {
            sMaxRequestPerHost = ucRequestSetting.getIntValue("per_host");
            sMaxRequestPerClient = ucRequestSetting.getIntValue("per_client");
            int minSdk = ucRequestSetting.getIntValue("min_sdk");
            if (minSdk == 0) {
                minSdk = 23;
            }
            sH2AppIdBlackList = ucRequestSetting.getJSONArray("app_blacklist");
            String h2list = ucRequestSetting.getString("list");
            sUcRequestSettingEnabled = ucRequestSetting.getBoolean("enabled").booleanValue();
            if (sUcRequestSettingEnabled) {
                if (sMaxRequestPerHost > 0 && sMaxRequestPerClient > 0) {
                    HashMap limit_info = new HashMap();
                    if (sMaxRequestPerHost > 6) {
                        limit_info.put(UCSettings.CDKEY_MAX_REQ_PER_HOST, String.valueOf(sMaxRequestPerHost));
                    }
                    if (sMaxRequestPerClient > 13) {
                        limit_info.put(UCSettings.CDKEY_MAX_REQ_PER_CLIENT, String.valueOf(sMaxRequestPerClient));
                    }
                    if (!limit_info.isEmpty()) {
                        UCCore.notifyCoreEvent(7, limit_info, null);
                    }
                }
                if (VERSION.SDK_INT >= minSdk && !TextUtils.isEmpty(h2list)) {
                    UCCore.notifyCoreEvent(8, h2list, null);
                }
            }
        }
        UcSetupTracing.endTrace("initUcRequestSetting");
    }

    /* access modifiers changed from: private */
    public static void initUcHttpCacheSdCardSetting() {
        H5Log.d(TAG, "[UcNetworkSetup] initUcHttpCacheSdCardSetting");
        UcSetupTracing.beginTrace("initUcHttpCacheSdCardSetting");
        JSONObject ucRequestSetting = H5Utils.parseObject(H5WalletWrapper.getConfig("h5_httpCacheSdcardLimit"));
        if (ucRequestSetting != null) {
            int imgSizeLimit = H5Utils.getInt(ucRequestSetting, (String) "IMG_SIZE_LIMIT");
            if (imgSizeLimit > 0) {
                H5Log.d(TAG, "[initUcHttpCacheSdCardSetting] IMG_SIZE_LIMIT: " + imgSizeLimit);
                UCSettings.setGlobalIntValue("IMG_SIZE_LIMIT", imgSizeLimit);
            }
            int jsSizeLimit = H5Utils.getInt(ucRequestSetting, (String) "JS_SIZE_LIMIT");
            if (jsSizeLimit > 0) {
                H5Log.d(TAG, "[initUcHttpCacheSdCardSetting] JS_SIZE_LIMIT: " + jsSizeLimit);
                UCSettings.setGlobalIntValue("JS_SIZE_LIMIT", jsSizeLimit);
            }
            int cssSizeLimit = H5Utils.getInt(ucRequestSetting, (String) "CSS_SIZE_LIMIT");
            if (cssSizeLimit > 0) {
                H5Log.d(TAG, "[initUcHttpCacheSdCardSetting] CSS_SIZE_LIMIT: " + cssSizeLimit);
                UCSettings.setGlobalIntValue("CSS_SIZE_LIMIT", cssSizeLimit);
            }
            H5PageData.ucCacheSdcardLimit = imgSizeLimit + "_" + jsSizeLimit + "_" + cssSizeLimit;
        }
        UcSetupTracing.endTrace("initUcHttpCacheSdCardSetting");
    }

    /* access modifiers changed from: private */
    public static void initHttpCacheUpperSize() {
        if (!H5Utils.isInTinyProcess()) {
            UcSetupTracing.beginTrace("initHttpCacheUpperSize");
            String httpCacheSizeStr = H5ConfigUtil.getConfig("uc_httpcache_dynamic_upper_limit");
            if (!TextUtils.isEmpty(httpCacheSizeStr)) {
                int httpCacheSize = Integer.parseInt(httpCacheSizeStr);
                if (httpCacheSize > 0) {
                    H5Log.d(TAG, "set max http cache size: " + httpCacheSize + "MB");
                    try {
                        NativeCrashHandlerApi.addCrashHeadInfo("httpCacheSize", httpCacheSizeStr);
                        UCCore.notifyCoreEvent(10, Integer.valueOf(httpCacheSize * 1024 * 1024), null);
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
            }
            UcSetupTracing.endTrace("initHttpCacheUpperSize");
        }
    }

    private static boolean disableUcNetByPhoneInfo(JSONArray jsonArray) {
        boolean result = false;
        if (jsonArray != null && !jsonArray.isEmpty()) {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            String release = VERSION.RELEASE;
            H5Log.d(TAG, "[UcNetworkSetup] disableUcNetByPhoneInfo jsonArray is " + jsonArray.toJSONString());
            int i = 0;
            while (true) {
                if (i >= jsonArray.size()) {
                    break;
                }
                H5Log.d(TAG, "[UcNetworkSetup] disableUcNetByPhoneInfo loop jsonArray round " + i);
                JSONObject object = jsonArray.getJSONObject(i);
                if (object != null) {
                    int objSize = object.size();
                    boolean ma = false;
                    boolean mo = false;
                    boolean re = false;
                    for (String key : object.keySet()) {
                        String value = object.getString(key);
                        if ("ma".equals(key)) {
                            ma = TextUtils.equals(manufacturer, value);
                        }
                        if (Constants.KEY_MODEL.equals(key)) {
                            mo = TextUtils.equals(model, value);
                        }
                        if ("release".equals(key)) {
                            re = TextUtils.equals(release, value);
                        }
                    }
                    if (objSize != 2 || !ma || !mo) {
                        if (objSize == 3 && ma && (mo && re)) {
                            result = true;
                            H5Log.d(TAG, "[UcNetworkSetup] disableUcNetByPhoneInfo loop object in round " + i + ", ma && mo & re break");
                            break;
                        }
                    } else {
                        result = true;
                        H5Log.d(TAG, "[UcNetworkSetup] disableUcNetByPhoneInfo loop object in round " + i + ", ma && mo break");
                        break;
                    }
                }
                i++;
            }
        }
        H5Log.d(TAG, "[UcNetworkSetup] disableUcNetByPhoneInfo result " + result);
        return result;
    }

    public static void clearUcHttpCache() {
        if (H5Utils.isMainProcess()) {
            String lastClearCacheSwitchValue = H5DevConfig.getStringConfig(LAST_CLEAR_CACHE_SWITCH_VALUE, null);
            String currentClearCacheSwitchValue = H5ConfigUtil.getConfig("h5_clearUcHttpCache");
            if (!TextUtils.isEmpty(currentClearCacheSwitchValue) && !currentClearCacheSwitchValue.equalsIgnoreCase(lastClearCacheSwitchValue)) {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        try {
                            UCCore.notifyCoreEvent(3, null);
                            H5Log.d(UcNetworkSetup.TAG, "cleanHttpCache CORE_EVENT_CLEAR_HTTP_CACHE");
                        } catch (Throwable th) {
                            H5Log.e((String) UcNetworkSetup.TAG, (String) "cleanHttpCache error!");
                        }
                    }
                });
            }
            H5DevConfig.setStringConfig(LAST_CLEAR_CACHE_SWITCH_VALUE, currentClearCacheSwitchValue);
        }
    }
}
