package com.autonavi.minimap.ajx3;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxErrorUtil {
    public static final int ERR_CODE_INTERNAL_ERROR = 2;
    public static final int ERR_CODE_INVALID_PARAM = 1;
    public static final String ERR_MSG_INTERNAL_ERROR = "internal error";
    public static final String ERR_MSG_INVALID_PARAM = "invalid param";

    public static JSONObject getError(int i, String str, String... strArr) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            if (str != null) {
                jSONObject.put("msg", str);
            }
            if (strArr != null) {
                StringBuilder sb = new StringBuilder();
                for (String append : strArr) {
                    sb.append(append);
                    sb.append(MergeUtil.SEPARATOR_KV);
                }
                String sb2 = sb.toString();
                if (!TextUtils.isEmpty(sb2)) {
                    jSONObject.put("extra", sb2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
