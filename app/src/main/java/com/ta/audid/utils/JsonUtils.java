package com.ta.audid.utils;

import com.ta.utdid2.android.utils.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class JsonUtils {
    public static Map<String, String> jsonToMap(String str) throws Exception {
        JSONObject jSONObject = new JSONObject(str);
        Iterator<String> keys = jSONObject.keys();
        HashMap hashMap = new HashMap();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, jSONObject.getString(next));
        }
        return hashMap;
    }

    public String jsonToSortString(String str) throws Exception {
        return new JSONObject(StringUtils.sortMapByKey(jsonToMap(str))).toString();
    }
}
