package com.alipay.android.phone.inside.cashier.utils;

import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InsideEnvBuilder {
    public String getOutTradePayEnv() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put(H5AppUtil.scene, "INSIDEOUTPAY");
        return getInsideEnv(hashMap);
    }

    public String getBusCodeAuthEnv() throws Exception {
        String a = RunningConfig.a(false);
        if (TextUtils.isEmpty(a)) {
            throw new Exception("tid is empty!!");
        }
        HashMap hashMap = new HashMap();
        hashMap.put("insideSdkTid", a);
        hashMap.put(H5AppUtil.scene, "INSIDETRANSPORT");
        return getInsideEnv(hashMap);
    }

    public String getQrcodeAuthEnv() throws Exception {
        String a = RunningConfig.a(false);
        if (TextUtils.isEmpty(a)) {
            throw new Exception("tid is empty!!");
        }
        HashMap hashMap = new HashMap();
        hashMap.put("insideSdkTid", a);
        hashMap.put("rpcSeed", Boolean.TRUE);
        hashMap.put(H5AppUtil.scene, "INSIDEBARPAY");
        return getInsideEnv(hashMap);
    }

    public String getInnerQrcodeAuthEnv() throws Exception {
        return getInsideEnv(getQrcodeSupportParams());
    }

    public String getInnerQrCodeToOnlineEnv(boolean z) throws JSONException {
        HashMap hashMap = new HashMap();
        if (z) {
            hashMap.put("insideAttachAction", "initOtp");
            hashMap.putAll(getQrcodeSupportParams());
        }
        return getInsideEnv(hashMap);
    }

    private Map<String, Object> getQrcodeSupportParams() {
        HashMap hashMap = new HashMap();
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("CODE_18");
        jSONArray.put("CODE_19");
        hashMap.put("supportedCodeTypes", jSONArray);
        hashMap.put("requiredCodeType", TextUtils.equals(StaticConfig.a((String) "requiredCode19"), "true") ? "CODE_19" : "CODE_18");
        return hashMap;
    }

    public String getInsideEnv() throws JSONException {
        return getInsideEnv(null);
    }

    private String getInsideEnv(Map<String, Object> map) throws JSONException {
        JSONObject baseInsideEnv = getBaseInsideEnv();
        if (map != null) {
            for (String next : map.keySet()) {
                baseInsideEnv.put(next, map.get(next));
            }
        }
        return baseInsideEnv.toString();
    }

    private JSONObject getBaseInsideEnv() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("insideModel", StaticConfig.b());
        jSONObject.put("insidePushKey", OutsideConfig.g());
        jSONObject.put("insideProductId", RunningConfig.c());
        jSONObject.put("currentOperateMobile", OutsideConfig.h());
        jSONObject.put("isTrojan", OutsideConfig.j());
        jSONObject.put("isPrisonBreak", OutsideConfig.i());
        jSONObject.put("appKey", OutsideConfig.n());
        jSONObject.put("clientTime", String.valueOf(System.currentTimeMillis() / 1000));
        jSONObject.put("version", StaticConfig.c());
        return jSONObject;
    }
}
