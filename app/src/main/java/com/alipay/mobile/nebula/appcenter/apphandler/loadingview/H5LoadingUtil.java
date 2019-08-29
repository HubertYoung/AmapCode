package com.alipay.mobile.nebula.appcenter.apphandler.loadingview;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;

public class H5LoadingUtil {
    private static final int defaultTime = 30;
    private static H5LoadingActivity h5LoadingActivity;
    private static H5LoadingManager h5LoadingManager;
    private static H5LoadingTypeListen h5LoadingTypeListen;
    private static H5StartAppInfo h5StartAppInfo;

    public static H5LoadingTypeListen getH5LoadingTypeListen() {
        return h5LoadingTypeListen;
    }

    public static void setH5LoadingTypeListen(H5LoadingTypeListen h5LoadingTypeListen2) {
        h5LoadingTypeListen = h5LoadingTypeListen2;
    }

    public static H5StartAppInfo getH5StartAppInfo() {
        return h5StartAppInfo;
    }

    public static void setH5StartAppInfo(H5StartAppInfo h5StartAppInfo2) {
        h5StartAppInfo = h5StartAppInfo2;
    }

    public static H5LoadingManager getH5LoadingManager() {
        return h5LoadingManager;
    }

    public static void setH5LoadingManager(H5LoadingManager h5LoadingManager2) {
        h5LoadingManager = h5LoadingManager2;
    }

    public static H5LoadingActivity getH5LoadingActivity() {
        return h5LoadingActivity;
    }

    public static void setH5LoadingActivity(H5LoadingActivity h5LoadingActivity2) {
        h5LoadingActivity = h5LoadingActivity2;
    }

    private static int getAppTimeout(String appId) {
        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (configProvider != null) {
            JSONObject jo = H5Utils.parseObject(configProvider.getConfigWithProcessCache("h5_preparetimelimit"));
            if (jo != null) {
                return H5Utils.parseInt(H5Utils.getString(jo, appId, (String) "-1"));
            }
        }
        return -1;
    }

    public static int getTimeout(String appId, String type, Bundle appStartParam, AppInfo appInfo) {
        String key;
        int configTimeOut = getAppTimeout(appId);
        if (configTimeOut > 0) {
            return configTimeOut;
        }
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            JSONObject preJson = H5Utils.parseObject(h5AppProvider.getConfigExtra(H5NebulaAppConfigs.EXPIRE_TIME));
            if (preJson != null && !preJson.isEmpty() && !"0".equals(H5Utils.getString(preJson, (String) FunctionSupportConfiger.SWITCH_TAG))) {
                String key2 = null;
                if (appInfo != null) {
                    JSONObject channelJsonObject = H5Utils.getJSONObject(preJson, String.valueOf(appInfo.app_channel), null);
                    if (channelJsonObject != null && !channelJsonObject.isEmpty()) {
                        if (H5AppHandler.isSyncType(type)) {
                            key2 = "force";
                        } else if (TextUtils.equals(type, "synctry")) {
                            key2 = "tryup";
                        } else if (TextUtils.equals(type, "try")) {
                            key2 = "tryoff";
                        }
                    }
                    String value = H5Utils.getString(channelJsonObject, key2);
                    if (!TextUtils.isEmpty(value)) {
                        return H5Utils.parseInt(value);
                    }
                }
            }
        }
        if (appStartParam != null) {
            JSONObject data = H5Utils.parseObject(H5Utils.getString(appStartParam, (String) "nbprepareTime"));
            if (data != null && !data.isEmpty()) {
                if (H5AppHandler.isSyncType(type)) {
                    key = "force";
                } else {
                    key = "try";
                }
                String time = H5Utils.getString(data, key);
                if (!TextUtils.isEmpty(time)) {
                    return H5Utils.parseInt(time);
                }
            }
        }
        return 30;
    }
}
