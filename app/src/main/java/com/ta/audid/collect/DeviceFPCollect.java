package com.ta.audid.collect;

import android.content.Context;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class DeviceFPCollect {
    private static final String V = "V";
    private static final String VERSION = "1.0";

    private static Map<String, String> getDeviceFP(Context context) {
        HashMap hashMap = new HashMap();
        hashMap.put("V", "1.0");
        hashMap.putAll(DeviceInfoModle.getDeviceInfoModle(context));
        hashMap.putAll(SystemInfoModle.getSystemInfoModle(context));
        hashMap.putAll(AppInfoModle.getAppInfoModle(context));
        return hashMap;
    }

    public static JSONObject getFPInfo(Context context) {
        if (!UtdidLogger.isDebug()) {
            return new JSONObject(getDeviceFP(context));
        }
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject jSONObject = new JSONObject(StringUtils.sortMapByKey(getDeviceFP(context)));
        UtdidLogger.sd(jSONObject.toString(), new Object[0]);
        StringBuilder sb = new StringBuilder("getFPInfo time:");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        UtdidLogger.sd(sb.toString(), new Object[0]);
        return jSONObject;
    }
}
