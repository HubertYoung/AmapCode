package com.autonavi.minimap.ajx3.exception;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import org.json.JSONObject;

public class JsException extends JSONObject {
    public static final int ERR_CODE_INTERNAL_ERROR = 2;
    public static final int ERR_CODE_INVALID_PARAM = 1;
    public static final String ERR_MSG_INTERNAL_ERROR = "internal error";
    public static final String ERR_MSG_INVALID_PARAM = "invalid param";

    public JsException(int i, String str, String... strArr) {
        try {
            put("code", i);
            put("msg", str);
            StringBuilder sb = new StringBuilder();
            if (strArr != null) {
                for (String append : strArr) {
                    sb.append(append);
                    sb.append(MergeUtil.SEPARATOR_KV);
                }
            }
            put("extra", sb.toString());
        } catch (Exception unused) {
        }
    }
}
