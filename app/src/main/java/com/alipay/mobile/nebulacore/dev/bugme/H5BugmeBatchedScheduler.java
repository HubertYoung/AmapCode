package com.alipay.mobile.nebulacore.dev.bugme;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest.Builder;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.batched.BatchedScheduler;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.dev.provider.H5DebugConsolePool;
import com.alipay.mobile.nebulacore.dev.provider.H5WalletDevDebugProvider;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.List;

public class H5BugmeBatchedScheduler extends BatchedScheduler<JSONObject> {
    private String a = null;

    public H5BugmeBatchedScheduler() {
        super(H5Utils.getExecutor("RPC"));
    }

    private String a() {
        String uid = "";
        if (H5Utils.isInTinyProcess()) {
            uid = this.a;
        }
        if (!TextUtils.isEmpty(uid)) {
            return uid;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) Nebula.getProviderManager().getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider == null) {
            return uid;
        }
        String uid2 = h5LoginProvider.getUserId();
        this.a = uid2;
        return uid2;
    }

    /* access modifiers changed from: protected */
    public void onSchedule(List<JSONObject> list) {
        StringBuilder bodyBuilder = new StringBuilder();
        for (JSONObject jsonObject : list) {
            boolean bugmeSwitchOpen = H5Utils.getBoolean(jsonObject, (String) "bugmeSwitchOpen", false);
            jsonObject.remove("bugmeSwitchOpen");
            H5EnvProvider h5EnvProvider = (H5EnvProvider) Nebula.getProviderManager().getProvider(H5EnvProvider.class.getName());
            if (h5EnvProvider != null) {
                jsonObject.put((String) LocationParams.PARA_COMMON_DID, (Object) h5EnvProvider.getmDid());
            }
            jsonObject.put((String) Oauth2AccessToken.KEY_UID, (Object) a());
            JSONObject extra = H5Utils.getJSONObject(jsonObject, "extra", null);
            if (extra == null) {
                extra = new JSONObject();
            }
            extra.put((String) "vorlon", (Object) Boolean.valueOf(H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DOM_DEBUG, false)));
            extra.put((String) AjxDebugConfig.PERFORMANCE, (Object) Boolean.valueOf(bugmeSwitchOpen));
            jsonObject.put((String) "extra", (Object) extra);
            JSONObject appInfo = H5Utils.parseObject(H5Utils.getString(jsonObject, (String) "app"));
            if (appInfo != null) {
                jsonObject.put((String) "app", (Object) appInfo);
            }
            if (!AjxDebugConfig.PERFORMANCE.equals(H5Utils.getString(jsonObject, (String) "type")) && H5BugmeLogCollector.enabled()) {
                H5BugmeLogCollector.enqueueLog(a(jsonObject, 1024));
            }
            if (bugmeSwitchOpen) {
                H5BugmeConsole console = H5DebugConsolePool.getInstance().getConsole(H5Utils.getString(jsonObject, (String) "viewId"));
                if (console != null) {
                    console.recordLog(jsonObject);
                }
                JSONObject objectToSend = a(jsonObject, 5000);
                String logData = objectToSend.toString();
                H5Log.d(H5WalletDevDebugProvider.TAG, "send data:" + objectToSend);
                bodyBuilder.append(logData).append("\n");
            }
        }
        if (bodyBuilder.length() > 0) {
            new Builder().url(H5DevConfig.BATCHED_SERVER).addHeader("Content-Type", "text/plain").addHeader(H5AppHttpRequest.HEADER_UA, "").body(bodyBuilder.toString()).build().execute();
        }
    }

    private static JSONObject a(JSONObject jsonObject, int singleValueLengthLimit) {
        JSONObject clippedObject = new JSONObject();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if ("extra".equals(key) || "app".equals(key) || "img".equals(key) || !(value instanceof String)) {
                clippedObject.put(key, value);
            } else {
                String strValue = (String) value;
                if (!TextUtils.isEmpty(strValue)) {
                    if (strValue.length() > singleValueLengthLimit) {
                        strValue = strValue.substring(0, singleValueLengthLimit) + "...";
                    }
                    clippedObject.put(key, (Object) strValue);
                }
            }
        }
        return clippedObject;
    }
}
