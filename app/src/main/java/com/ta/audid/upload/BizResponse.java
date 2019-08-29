package com.ta.audid.upload;

import android.text.TextUtils;
import com.ta.audid.Variables;
import com.ta.audid.utils.UtdidLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class BizResponse {
    private static final String TAG_AUDID = "audid";
    private static final String TAG_CODE = "code";
    private static final String TAG_DATA = "data";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_UTDID = "utdid";
    public int code = -1;
    public String message = "";

    public static boolean isSuccess(int i) {
        return i >= 0 && i != 10012;
    }

    public static BizResponse parseResult(String str) {
        BizResponse bizResponse = new BizResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("code")) {
                bizResponse.code = jSONObject.getInt("code");
            }
            if (jSONObject.has("message")) {
                bizResponse.message = jSONObject.getString("message");
            }
            if (jSONObject.has("data")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (jSONObject2 != null) {
                    if (jSONObject2.has("audid")) {
                        String string = jSONObject2.getString("audid");
                        if (!TextUtils.isEmpty(string)) {
                            UtdidKeyFile.writeAudidFile(string);
                        }
                    }
                    if (jSONObject2.has("utdid")) {
                        String string2 = jSONObject2.getString("utdid");
                        if (!TextUtils.isEmpty(string2)) {
                            UtdidKeyFile.writeUtdidToSettings(Variables.getInstance().getContext(), string2);
                            UtdidKeyFile.writeSdcardUtdidFile(string2);
                            UtdidKeyFile.writeAppUtdidFile(string2);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            UtdidLogger.d((String) "", e.toString());
        }
        return bizResponse;
    }
}
