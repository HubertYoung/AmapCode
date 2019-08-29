package com.alipay.mobile.nebula.appcenter.config;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import java.util.HashMap;
import java.util.Map;

public class H5NebulaAppConfigManager {
    public static final int DEFAULT_OUT_DATE_SECOND = 2592000;
    public static final int DEFAULT_RES_INVALID_SECOND = 259200;
    private static final String TAG = "H5NebulaAppConfigManager";
    /* access modifiers changed from: private */
    public static boolean canUseClientConfig = false;

    public static boolean enableNewConfig() {
        return canUseClientConfig;
    }

    public static void initConfig() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            applyConfig(false, provider.getConfigWithNotifyChange("h5_nbmngconfig", new OnConfigChangeListener() {
                public final void onChange(String newValue) {
                    H5NebulaAppConfigManager.applyConfig(true, newValue);
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public static void applyConfig(boolean fromConfigChange, final String configStr) {
        if (!TextUtils.isEmpty(configStr)) {
            (fromConfigChange ? H5ThreadPoolFactory.getDefaultExecutor() : H5Utils.getExecutor(H5ThreadType.IO)).execute(new Runnable() {
                public final void run() {
                    Map configMap;
                    JSONObject jsonObject = H5Utils.parseObject(configStr);
                    if (jsonObject != null) {
                        H5Log.d(H5NebulaAppConfigManager.TAG, "applyConfig: " + configStr);
                        H5NebulaAppConfigManager.canUseClientConfig = "yes".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) FunctionSupportConfiger.SWITCH_TAG, (String) null));
                        if (H5NebulaAppConfigManager.canUseClientConfig) {
                            H5AppCenterService h5AppCenterService = H5ServiceUtils.getAppCenterService();
                            if (h5AppCenterService != null) {
                                AppRes appRes = new AppRes();
                                appRes.fromNewConfig = true;
                                JSONObject configObj = H5Utils.getJSONObject(jsonObject, "config", null);
                                if (configObj == null || configObj.size() == 0) {
                                    configMap = new HashMap();
                                } else {
                                    configMap = H5Utils.jsonToMap(configObj);
                                }
                                appRes.config = configMap;
                                H5Log.d(H5NebulaAppConfigManager.TAG, "updateConfig: " + appRes.config);
                                h5AppCenterService.setUpInfo(appRes, true);
                            }
                        }
                    }
                }
            });
        }
    }

    public static boolean isNeedForceUpdate(AppInfo appInfo) {
        if (appInfo == null) {
            return true;
        }
        H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (provider == null) {
            return false;
        }
        int strictReqRate = parseConfig(provider.getConfigExtra(H5NebulaAppConfigs.PRE_FORCE_REQ_RATE), appInfo.app_channel, 0);
        if (strictReqRate != 0 && !TextUtils.isEmpty(appInfo.update_app_time)) {
            long updateTime = H5Utils.parseLong(appInfo.update_app_time);
            long timeDiff = System.currentTimeMillis() - updateTime;
            H5Log.d(TAG, "[isNeedForceUpdate] updateTime:" + updateTime + " timeDiff:" + (timeDiff / 1000) + " strictReqRat:" + strictReqRate);
            if (timeDiff / 1000 > ((long) strictReqRate)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOutOfReqRate(Bundle copyParam, AppInfo appInfo) {
        if (appInfo == null) {
            return true;
        }
        H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (provider == null) {
            return false;
        }
        if (!"yes".equals(appInfo.fromPreset) || H5DevConfig.getBooleanConfig(H5DevConfig.H5_USE_PRESET_PKG_INFO, false)) {
            int strictReqRate = H5Utils.parseInt(H5Utils.getString(copyParam, (String) H5NebulaAppConfigs.STRICT_REQ_RATE));
            if (strictReqRate == 0) {
                strictReqRate = parseConfig(provider.getConfigExtra(H5NebulaAppConfigs.PRE_REQ_RATE), appInfo.app_channel, DEFAULT_OUT_DATE_SECOND);
            }
            if (strictReqRate != 0 && !TextUtils.isEmpty(appInfo.update_app_time)) {
                long updateTime = H5Utils.parseLong(appInfo.update_app_time);
                long timeDiff = System.currentTimeMillis() - updateTime;
                H5Log.d(TAG, "[isOutOfReqRate] updateTime:" + updateTime + " timeDiff:" + (timeDiff / 1000) + " strictReqRat:" + strictReqRate);
                if (timeDiff / 1000 > ((long) strictReqRate)) {
                    return true;
                }
            }
            return false;
        }
        H5Log.w(TAG, " appInfo is fromPreset set Timeout");
        return true;
    }

    private static int parseConfig(String configObj, int channel, int defaultTime) {
        String time;
        JSONObject preJson = H5Utils.parseObject(configObj);
        if (preJson == null || preJson.isEmpty()) {
            return 0;
        }
        if (preJson.containsKey(String.valueOf(channel))) {
            time = H5Utils.getString(preJson, String.valueOf(channel));
        } else {
            time = H5Utils.getString(preJson, (String) IPreloadManager.SIR_COMMON_TYPE);
        }
        if (!TextUtils.isEmpty(time)) {
            return H5Utils.parseInt(time);
        }
        return defaultTime;
    }
}
