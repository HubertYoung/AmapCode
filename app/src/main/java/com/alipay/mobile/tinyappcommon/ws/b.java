package com.alipay.mobile.tinyappcommon.ws;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: H5WSUtils */
public final class b {
    private static String a = null;

    public static final String a(H5Event event) {
        String appId = H5Utils.getString(((H5Page) event.getTarget()).getParams(), (String) "appId");
        if ("true".equals(H5Utils.getString(event.getParam(), (String) "fromRemoteDebug"))) {
            return "fromRemoteDebug";
        }
        return appId;
    }

    public static String b(H5Event event) {
        return H5Utils.getString(event.getParam(), (String) "socketTaskID");
    }

    public static boolean c(H5Event event) {
        return H5Utils.getBoolean(event.getParam(), (String) "multiple", false);
    }

    public static final String d(H5Event event) {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        H5Page h5page = (H5Page) event.getTarget();
        if (h5page != null) {
            String userAgentString = h5page.getWebView().getSettings().getUserAgentString();
            a = userAgentString;
            return userAgentString;
        }
        String format = String.format("Mozilla/Alipay (Linux; Android SDK %s; %s %s %s Build) AppleWebKit (KHTML, like Gecko) Mobile Safari", new Object[]{Integer.valueOf(VERSION.SDK_INT), Build.BRAND, Build.MODEL, Build.MANUFACTURER});
        a = format;
        return format;
    }

    public static final void a(H5BridgeContext bridgeContext, String message) {
        bridgeContext.sendError(WSResultEnum.UNKNOW_ERROR.getErrCode(), message);
    }

    public static final void a(H5BridgeContext bridgeContext, WSResultEnum wsResultEnum) {
        bridgeContext.sendError(wsResultEnum.getErrCode(), wsResultEnum.getErrMsg());
    }

    public static final void b(H5BridgeContext bridgeContext, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "message", (Object) message);
        bridgeContext.sendBridgeResult(jsonObject);
    }

    public static Map<String, String> a(JSONObject jSONObject) {
        Map hashMap = new HashMap();
        try {
            JSONObject headerJson = jSONObject.getJSONObject(Performance.KEY_LOG_HEADER);
            if (headerJson != null && !headerJson.isEmpty()) {
                for (Entry entry : headerJson.entrySet()) {
                    String key = (String) entry.getKey();
                    if (!TextUtils.isEmpty(key)) {
                        hashMap.put(key.toLowerCase(), String.valueOf(entry.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            H5Log.e("H5WSUtils", "get header error, exception : ", e);
        }
        return hashMap;
    }

    public static String a(String url, JSONObject dataJson) {
        if (dataJson == null) {
            return url;
        }
        try {
            if (dataJson.isEmpty()) {
                return url;
            }
            StringBuilder urlBuilder = new StringBuilder(url);
            if (url.indexOf("?") <= 0) {
                urlBuilder.append("?");
            } else if (!url.endsWith("&")) {
                urlBuilder.append("&");
            }
            for (Entry dataEntry : dataJson.entrySet()) {
                String key = (String) dataEntry.getKey();
                if (!TextUtils.isEmpty(key)) {
                    urlBuilder.append(key).append("=").append(dataEntry.getValue()).append("&");
                }
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            return urlBuilder.toString();
        } catch (Throwable e) {
            H5Log.e("H5WSUtils", "appendQueryJson2Url error.", e);
            return url;
        }
    }
}
