package com.alipay.android.phone.inside.main.action.utils;

import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ResultCode;
import org.json.JSONException;
import org.json.JSONObject;

public class IotPayConstants {
    private static String a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", "INSIDE_UNKNOWN");
            jSONObject.put("displayMessage", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static void a(OperationResult operationResult, ResultCode resultCode, String str) {
        if (operationResult != null) {
            operationResult.setCode(resultCode);
            operationResult.setResult(a(str));
        }
    }
}
