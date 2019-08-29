package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class MpaasPropertiesUtil {
    public static final String APP_KEY_DEBUG = "rpc-sdk";
    public static final String APP_KEY_ONLINE = "rpc-sdk-online";
    private static Map<String, String> a = null;

    public static final Map<String, String> getMpaasProperties(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (MpaasPropertiesUtil.class) {
            try {
                if (a == null) {
                    a = a(context);
                }
            }
        }
        return a;
    }

    public static final String getWorkspaceId(Context context) {
        try {
            String wsid = getMpaasProperties(context).get("WorkspaceId");
            if (wsid != null) {
                return wsid;
            }
            return "";
        } catch (Throwable e) {
            LogCatUtil.error((String) "MpaasPropertiesUtil", "getWorkspaceId. error: " + e.toString());
            return "";
        }
    }

    public static final String getAppId(Context context) {
        return getAppId(context, getAppKeyFromMetaData(TransportEnvUtil.getContext()));
    }

    public static final String getAppId(Context context, String appKey) {
        String appIdFromMetaData = getAppIdFromMetaData(context);
        if (!TextUtils.isEmpty(appIdFromMetaData)) {
            return appIdFromMetaData;
        }
        if (!TextUtils.isEmpty(appKey)) {
            return appKey;
        }
        return getProductId(context);
    }

    public static final String getAppIdFromMetaData(Context context) {
        if (context == null) {
            return "";
        }
        try {
            Object appidObj = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get("mobilegw.appid");
            String mobilegwAppId = appidObj != null ? appidObj.toString() : null;
            if (TextUtils.isEmpty(mobilegwAppId)) {
                return "";
            }
            LogCatUtil.info("MpaasPropertiesUtil", "getAppIdFromMetaData. config appId=[" + mobilegwAppId + "]");
            if (!mobilegwAppId.equals("[product_id]")) {
                return mobilegwAppId;
            }
            String mobilegwAppId2 = getProductId(context);
            LogCatUtil.info("MpaasPropertiesUtil", "getAppIdFromMetaData. getAppIdFromProductID appId=[" + mobilegwAppId2 + "]");
            return mobilegwAppId2;
        } catch (Throwable e) {
            LogCatUtil.warn("MpaasPropertiesUtil", "getAppIdFromMetaData get mobilegw.appid fail", e);
            return "";
        }
    }

    public static final String getAppkey(String externalAppKey, boolean isReq2Online, Context context) {
        appKey = "";
        try {
            if (!TextUtils.isEmpty(externalAppKey)) {
                appKey = externalAppKey;
            } else if (isReq2Online) {
                if (MiscUtils.isDebugger(context)) {
                    LogCatUtil.debug("MpaasPropertiesUtil", "[getAppkey] appKey:" + "rpc-sdk-online");
                }
                r5 = "rpc-sdk-online";
                return "rpc-sdk-online";
            } else {
                if (MiscUtils.isDebugger(context)) {
                    LogCatUtil.debug("MpaasPropertiesUtil", "[getAppkey] appKey:" + "rpc-sdk");
                }
                r5 = "rpc-sdk";
                return "rpc-sdk";
            }
        } finally {
            if (MiscUtils.isDebugger(context)) {
                r2 = "MpaasPropertiesUtil";
                r4 = "[getAppkey] appKey:";
                LogCatUtil.debug(r2, appKey);
            }
        }
    }

    public static String getAppKeyFromMetaData(Context pContext) {
        try {
            Object appkeyObj = pContext.getPackageManager().getApplicationInfo(pContext.getPackageName(), 128).metaData.get("appkey");
            String appkey = appkeyObj != null ? appkeyObj.toString() : null;
            if (!TextUtils.isEmpty(appkey)) {
                LogCatUtil.info("MpaasPropertiesUtil", "getAppKeyFromMetaData. appkey=[" + appkey + "]");
                return appkey;
            }
        } catch (Throwable e) {
            LogCatUtil.warn("MpaasPropertiesUtil", "getAppKeyFromMetaData get appkey fail", e);
        }
        return "";
    }

    public static String getProductId(Context context) {
        HttpContextExtend httpContextExtend = HttpContextExtend.createInstance(context);
        if (httpContextExtend != null) {
            return httpContextExtend.getProductId();
        }
        return AppInfoUtil.getProductId();
    }

    private static final Map<String, String> a(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("mpaas.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            if (properties.size() <= 0) {
                return Collections.emptyMap();
            }
            Map map = new HashMap(properties.size());
            for (Entry entry : properties.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
            LogCatUtil.info("MpaasPropertiesUtil", "Mpaas properties loaded， size: " + map.size());
            return map;
        } catch (IOException e) {
            LogCatUtil.warn((String) "MpaasPropertiesUtil", "Mpaas properties load fail. " + e.toString());
        } catch (Throwable e2) {
            LogCatUtil.error("MpaasPropertiesUtil", "Mpaas properties load fail. ", e2);
        }
        return Collections.emptyMap();
    }
}
