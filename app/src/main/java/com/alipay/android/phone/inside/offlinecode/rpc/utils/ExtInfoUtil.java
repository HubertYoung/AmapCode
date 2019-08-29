package com.alipay.android.phone.inside.offlinecode.rpc.utils;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.Map;
import org.json.JSONObject;

public class ExtInfoUtil {
    public static JSONObject buildExtInfo(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        if (map == null) {
            return jSONObject;
        }
        try {
            for (String next : map.keySet()) {
                jSONObject.put(next, map.get(next));
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }
}
