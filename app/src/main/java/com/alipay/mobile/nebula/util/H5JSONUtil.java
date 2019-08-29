package com.alipay.mobile.nebula.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;

public class H5JSONUtil {
    public static JSONObject deepCopy(JSONObject origin) {
        if (origin == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        for (String key : origin.keySet()) {
            Object value = origin.get(key);
            if (value instanceof JSONArray) {
                result.put(key, (Object) deepCopy((JSONArray) value));
            } else if (value instanceof JSONObject) {
                result.put(key, (Object) deepCopy((JSONObject) value));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    public static JSONArray deepCopy(JSONArray origin) {
        if (origin == null) {
            return null;
        }
        JSONArray result = new JSONArray();
        Iterator<Object> it = origin.iterator();
        while (it.hasNext()) {
            Object item = it.next();
            if (item instanceof JSONArray) {
                result.add(deepCopy((JSONArray) item));
            } else if (item instanceof JSONObject) {
                result.add(deepCopy((JSONObject) item));
            } else {
                result.add(item);
            }
        }
        return result;
    }
}
