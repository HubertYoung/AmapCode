package com.amap.bundle.pay.payment;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class PayInfo {
    public static final int CODE_CANCEL = 100010;
    public static final int CODE_FAIL = 100020;
    public static final int CODE_FAIL_UNKNOWN = 100021;
    public static final int CODE_PARAM_ERROR = 100101;
    public static final int CODE_PAYMENT_UNSUPPORT = 100102;
    public static final int CODE_SUCCESS = 100000;
    private static final String KEY_REQUEST = "request";
    private static final String KEY_RESULT = "result";
    private static final String KEY_RESULT_CODE = "result_code";
    private static final String KEY_RESULT_TEXT = "result_text";
    public String request;
    public JSONObject requestJson;
    public String result = "";
    public int resultCode;
    @Nullable
    public String resultText;

    public PayInfo(String str, int i, String str2) {
        this.request = str;
        this.resultCode = i;
        this.resultText = str2;
    }

    public PayInfo(String str) {
        this.request = str;
        if (!TextUtils.isEmpty(str)) {
            try {
                this.requestJson = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.request);
    }

    public void setResult(String str, int i, String str2) {
        this.result = str;
        this.resultCode = i;
        this.resultText = str2;
    }

    public String toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result", this.result);
            jSONObject.put(KEY_RESULT_CODE, this.resultCode);
            jSONObject.put(KEY_RESULT_TEXT, this.resultText);
            jSONObject.put("request", this.request);
            return jSONObject.toString();
        } catch (JSONException e) {
            AMapLog.e(getClass().getSimpleName(), e.toString());
            return "";
        }
    }

    public String toString() {
        return toJson();
    }
}
