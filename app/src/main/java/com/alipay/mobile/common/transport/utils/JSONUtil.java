package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class JSONUtil {
    public static final JSONObject convertJSONObject(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return new JSONObject(json);
        } catch (Throwable e) {
            JSONException jsonException = new JSONException("Parse json error. value=" + json);
            jsonException.initCause(e);
            MonitorErrorLogHelper.log("JSONUtil", jsonException);
            return null;
        }
    }
}
