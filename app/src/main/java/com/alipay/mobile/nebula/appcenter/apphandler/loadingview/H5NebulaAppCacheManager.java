package com.alipay.mobile.nebula.appcenter.apphandler.loadingview;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5NebulaAppCacheManager {
    public static final String NEBULA_H5INNERTINY_APP = "nebulaH5InnerTinyApp";
    public static final String NEBULA_H5TINY_APP = "nebulaH5TinyApp";
    public static final String NEBULA_H5_APP = "nebulaH5App";
    public static final String NEBULA_NATIVE_TINY_APP = "nebulaNativeTinyApp";
    private static final String TAG = "H5NebulaAppCacheManager";
    private static Map<String, H5StartAppInfo> nebulaAppType = new ConcurrentHashMap();

    public static void putAppType(String appId, H5StartAppInfo type) {
        nebulaAppType.put(appId, type);
    }

    private static boolean enableUseCache() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache(TAG))) {
            return true;
        }
        return false;
    }

    public static String getAppType(String appId) {
        if (TextUtils.isEmpty(appId) || !enableUseCache()) {
            return null;
        }
        H5StartAppInfo type = nebulaAppType.get(appId);
        if (type == null) {
            return null;
        }
        H5Log.d(TAG, appId + " getAppType " + type);
        return type.nebulaAppType;
    }

    public static void setAppType(H5StartAppInfo startAppInfo, AppInfo appInfo, Bundle bundle) {
        if (appInfo != null) {
            if (H5Utils.isTinyApp(appInfo)) {
                startAppInfo.nebulaAppType = NEBULA_H5TINY_APP;
            } else if (H5AppUtil.isRNApp(appInfo)) {
                startAppInfo.nebulaAppType = NEBULA_NATIVE_TINY_APP;
            } else if (H5Utils.isInnerTinyApp(appInfo)) {
                startAppInfo.nebulaAppType = NEBULA_H5INNERTINY_APP;
            } else {
                startAppInfo.nebulaAppType = "nebulaH5App";
            }
            startAppInfo.enableMultiProcess = openMultiFromParam(bundle, NEBULA_H5TINY_APP.equals(startAppInfo.nebulaAppType));
            if (NEBULA_H5TINY_APP.equals(startAppInfo.nebulaAppType)) {
                startAppInfo.tinyType = 0;
                startAppInfo.useAppX = true;
                return;
            }
            startAppInfo.tinyType = 1;
            startAppInfo.useAppX = "yes".equalsIgnoreCase(H5Utils.getString(bundle, (String) H5Param.ENABLE_DSL));
            if (!TextUtils.isEmpty(appInfo.extend_info_jo)) {
                JSONObject extraJo = H5Utils.parseObject(appInfo.extend_info_jo);
                if (extraJo != null && !extraJo.isEmpty()) {
                    startAppInfo.isUsePresetPopmenu = TextUtils.equals("YES", extraJo.getString("usePresetPopmenu"));
                }
            }
        }
    }

    private static boolean openMultiFromParam(Bundle bundle, boolean H5Tiny) {
        String openMultiFromParam = H5Utils.getString(bundle, (String) H5Param.ENABLE_MULTI_PROCESS);
        if (TextUtils.isEmpty(openMultiFromParam)) {
            return H5Tiny;
        }
        if ("yes".equalsIgnoreCase(openMultiFromParam)) {
            return true;
        }
        return false;
    }

    public static boolean isH5TinyApp(String type) {
        return NEBULA_H5TINY_APP.equals(type);
    }

    public static boolean useAppX(String appId) {
        if (TextUtils.isEmpty(appId) || !enableUseCache()) {
            return false;
        }
        H5StartAppInfo type = nebulaAppType.get(appId);
        if (type == null) {
            H5Log.d(TAG, "useAppX type == null");
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null) {
                AppInfo appInfo = h5AppProvider.getAppInfo(appId);
                if (H5Utils.isTinyApp(appInfo)) {
                    return true;
                }
                Bundle copy = new Bundle();
                H5AppUtil.mergeConmonStartParam(copy, appInfo);
                return "yes".equalsIgnoreCase(H5Utils.getString(copy, (String) H5Param.ENABLE_DSL));
            }
        }
        H5Log.d(TAG, appId + " useAppX " + type);
        if (type != null) {
            return type.useAppX;
        }
        return false;
    }

    public static boolean enableMulti(String appId, Bundle bundle) {
        H5StartAppInfo h5StartAppInfo = nebulaAppType.get(appId);
        if (h5StartAppInfo != null) {
            return h5StartAppInfo.enableMultiProcess;
        }
        H5Log.d(TAG, "enableMulti h5StartAppInfo == null");
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider == null) {
            return false;
        }
        AppInfo appInfo = h5AppProvider.getAppInfo(appId);
        Bundle copy = H5AppUtil.copyBundle(bundle);
        H5AppUtil.mergeConmonStartParam(copy, appInfo);
        return openMultiFromParam(copy, H5Utils.isTinyApp(appInfo));
    }
}
