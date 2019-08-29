package com.alipay.android.phone.inside.main.action.utils;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthParamsChecker {
    public static boolean a(JSONObject jSONObject) {
        String optString = jSONObject.optString("action");
        boolean optBoolean = jSONObject.optBoolean("isOpenAuthLogin", false);
        String optString2 = jSONObject.optString("alipayUserId");
        String optString3 = jSONObject.optString("authToken");
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("[");
        sb.append(optString);
        sb.append("] isOpenAuthLogin=");
        sb.append(optBoolean);
        sb.append(", alipayUserId=");
        sb.append(optString2);
        sb.append(", authToken=");
        sb.append(optString3);
        f.b((String) "buscode|AuthParamsChecker", sb.toString());
        if (!optBoolean || TextUtils.isEmpty(optString2) || TextUtils.isEmpty(optString3)) {
            return true;
        }
        return false;
    }

    private static String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("errorCode", "INSIDE_PARAMS_ILLEGAL");
            jSONObject2.put(ModulePoi.TIPS, "参数非法");
            jSONObject.put("indicator", jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static OperationResult a(OperationResult operationResult, ResultCode resultCode) {
        operationResult.setCode(resultCode);
        operationResult.setResult(a());
        return operationResult;
    }
}
