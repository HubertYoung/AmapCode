package com.ta.audid.store;

import com.ta.utdid2.android.utils.StringUtils;
import java.util.HashMap;
import org.json.JSONObject;

public class RSModle {
    private static final String BODY = "{\"src\":%s,\"target\":%s}";

    public static String buildJsonString(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format(BODY, new Object[]{buildSortJsonString(str, str2, str3), buildSortJsonString(str4, str5, str6)});
    }

    private static String buildSortJsonString(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put("utdid", str);
        hashMap.put("appkey", str2);
        hashMap.put("appName", str3);
        return new JSONObject(StringUtils.sortMapByKey(hashMap)).toString();
    }
}
