package com.alibaba.analytics.utils;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiResponseParse {
    private static final String TAG_ERRORMSG = "ret";
    private static final String TAG_SUCCESS = "success";

    public static class BizResponse {
        public static BizResponse defaultResponse = new BizResponse();
        public String bizCode = null;
        public String errCode;
        public boolean isSuccess = false;
        public int receiveLen = 0;
        public double rt = 0.0d;

        public boolean isSignError() {
            return "E0102".equalsIgnoreCase(this.bizCode);
        }

        public boolean isParamError() {
            return "E0101".equalsIgnoreCase(this.bizCode);
        }

        public boolean isNotFoundSecret() {
            return "E0111".equalsIgnoreCase(this.bizCode) || "E0112".equalsIgnoreCase(this.bizCode);
        }
    }

    public static BizResponse parseResult(String str) {
        BizResponse bizResponse = new BizResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("success")) {
                String string = jSONObject.getString("success");
                if (!TextUtils.isEmpty(string) && string.equals("success")) {
                    bizResponse.isSuccess = true;
                }
            }
            if (jSONObject.has(TAG_ERRORMSG)) {
                bizResponse.bizCode = jSONObject.getString(TAG_ERRORMSG);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bizResponse;
    }
}
