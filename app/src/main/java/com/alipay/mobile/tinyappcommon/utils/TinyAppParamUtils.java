package com.alipay.mobile.tinyappcommon.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.wallet.H5Application;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;

public final class TinyAppParamUtils {
    public static String getAppId(H5Event event) {
        if (event == null) {
            return "";
        }
        return getAppId(event.getH5page());
    }

    public static String getAppId(H5Page h5Page) {
        if (h5Page == null) {
            return "";
        }
        return getAppId(h5Page.getParams());
    }

    public static String getAppId(Bundle params) {
        String appId = H5Utils.getString(params, (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            return H5Utils.getString(params, (String) "MINI-PROGRAM-WEB-VIEW-TAG");
        }
        return appId;
    }

    public static String getHostAppId(H5Event event) {
        if (event == null) {
            return "";
        }
        return getHostAppId(event.getH5page());
    }

    public static String getHostAppId(H5Page h5Page) {
        if (h5Page == null) {
            return "";
        }
        return getHostAppId(h5Page.getParams());
    }

    public static String getHostAppId(Bundle params) {
        if (!TinyAppMiniServicePlugin.appIsMiniService(params)) {
            return getAppId(params);
        }
        String parentAppId = H5Utils.getString(params, (String) "parentAppId");
        if (TextUtils.isEmpty(parentAppId)) {
            return getAppId(params);
        }
        return parentAppId;
    }

    public static H5Application getApplication(H5Page h5Page) {
        if (h5Page == null || h5Page.getContext() == null) {
            return null;
        }
        Context context = h5Page.getContext().getContext();
        if (!(context instanceof H5Activity)) {
            return null;
        }
        H5Activity h5Activity = (H5Activity) context;
        if (h5Activity.getActivityApplication() instanceof H5Application) {
            return (H5Application) h5Activity.getActivityApplication();
        }
        return null;
    }

    public static String getChannelFromSceneParams(H5Page h5Page) {
        H5Application h5Application = getApplication(h5Page);
        if (h5Application == null) {
            return null;
        }
        return H5Utils.getString(h5Application.getSceneParams(), (String) "chInfo");
    }
}
