package com.alipay.mobile.tinyappcommon.c;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: TinyAppStartupInfo */
public final class a {
    public static final Map<String, Boolean> a = new ConcurrentHashMap();
    public static final Map<String, String> b = new ConcurrentHashMap();
    private static final JSONObject c;

    static {
        JSONObject jSONObject = new JSONObject();
        c = jSONObject;
        jSONObject.put((String) "ch_tinycenter", (Object) "1001");
        c.put((String) "ch_alipaysearch", (Object) "1005");
        c.put((String) "ch_share", (Object) "1007");
        c.put((String) "ch_scan", (Object) "1011");
        c.put((String) "ch_messageservice", (Object) "1014");
        c.put((String) "ch_life", (Object) "1020");
        c.put((String) "ch_desktop", (Object) "1023");
        c.put((String) "ch_othertinyapp", (Object) "1037");
        c.put((String) "ch_backfromtinyapp", (Object) "1038");
        c.put((String) "ch_tinylongpress", (Object) "1090");
        c.put((String) "ch_cityservice", (Object) "1200");
        c.put((String) "ch_zhima", (Object) "1201");
        c.put((String) "ch_carservice", (Object) "1202");
        c.put((String) "ch_medicalservice", (Object) "1203");
        c.put((String) "ch_college", (Object) "1204");
        c.put((String) "ch_school", (Object) "1205");
        c.put((String) "ch_sharebike", (Object) "1206");
        c.put((String) "ch_insurance", (Object) "1207");
        c.put((String) "ch_ttyl", (Object) "1208");
        c.put((String) "ch_membership", (Object) "1209");
        c.put((String) "ch_outerUrl", (Object) "1300");
        c.put((String) "ch_miniService", (Object) "miniService");
    }

    private static String a(String channel) {
        if (TextUtils.isEmpty(channel)) {
            return "0000";
        }
        String mainChannel = channel;
        int indexOfSubChannel = channel.indexOf(95, 3);
        if (indexOfSubChannel != -1) {
            mainChannel = channel.substring(0, indexOfSubChannel);
        }
        String value = H5Utils.getString(TinyAppConfig.getInstance().getSceneConfig(), mainChannel);
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        String value2 = H5Utils.getString(c, mainChannel);
        if (!TextUtils.isEmpty(value2)) {
            return value2;
        }
        return "0000";
    }

    public static void a(H5Page h5Page, Bundle bundle, boolean sureTinyApp) {
        if (bundle == null || TinyAppConfig.getInstance().getSceneTransformShutdown()) {
            return;
        }
        if (!TextUtils.isEmpty(H5Utils.getString(bundle, (String) "MINI-PROGRAM-WEB-VIEW-TAG")) || !sureTinyApp || H5Utils.getBoolean(bundle, (String) "isTinyApp", false)) {
            String channel = H5Utils.getString(bundle, (String) "chInfo");
            if (TextUtils.isEmpty(channel)) {
                try {
                    channel = TinyAppParamUtils.getChannelFromSceneParams(h5Page);
                    if (TextUtils.isEmpty(channel)) {
                        channel = b(bundle);
                    } else {
                        JSONArray injectList = TinyAppConfig.getInstance().getInjectChInfoList();
                        if (injectList != null && (injectList.contains("all") || injectList.contains(TinyAppParamUtils.getAppId(bundle)))) {
                            bundle.putString("chInfo", channel);
                        }
                    }
                } catch (Exception e) {
                    H5Log.e((String) "TinyAppStartupInfo", (Throwable) e);
                }
                if (TextUtils.isEmpty(channel)) {
                    if (!bundle.containsKey(MicroApplication.KEY_APP_SCENE_ID)) {
                        bundle.putString(MicroApplication.KEY_APP_SCENE_ID, "0000");
                        return;
                    }
                    return;
                }
            }
            bundle.putString(MicroApplication.KEY_APP_SCENE_ID, a(channel));
        }
    }

    private static String b(Bundle startParams) {
        String customParams = H5Utils.getString(startParams, (String) H5Param.CUSTOM_PARAMS);
        if (!TextUtils.isEmpty(customParams)) {
            String[] customParamsArray = customParams.split("&");
            if (!(customParams == null || customParams.length() == 0)) {
                for (String customParam : customParamsArray) {
                    if (customParam.startsWith("chInfo=")) {
                        return customParam.substring(7);
                    }
                }
            }
        }
        return "";
    }

    public static void a(Bundle bundle) {
        if (bundle != null) {
            try {
                String appId = TinyAppParamUtils.getHostAppId(bundle);
                String sceneId = H5Utils.getString(bundle, (String) MicroApplication.KEY_APP_SCENE_ID);
                if (!TextUtils.isEmpty(appId)) {
                    if (TextUtils.equals(sceneId, "1300")) {
                        String thirdPartSchema = bundle.getString("thirdPartSchema");
                        if (!TextUtils.isEmpty(thirdPartSchema)) {
                            a.put(appId, Boolean.valueOf(true));
                            b.put(appId, thirdPartSchema);
                            return;
                        }
                        return;
                    }
                    a.put(appId, Boolean.valueOf(false));
                    b.remove(appId);
                }
            } catch (Throwable e) {
                H5Log.e((String) "TinyAppStartupInfo", e);
            }
        }
    }
}
