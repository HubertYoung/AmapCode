package com.alibaba.analytics.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MapUtils {
    public static Map<String, String> convertStringAToMap(String... strArr) {
        return null;
    }

    public static Map<String, String> convertObjectMapToStringMap(Map<String, Object> map) {
        if (map != null) {
            HashMap hashMap = new HashMap();
            if (map != null && map.size() > 0) {
                Iterator<String> it = map.keySet().iterator();
                if (it != null) {
                    while (it.hasNext()) {
                        String next = it.next();
                        String convertObjectToString = StringUtils.convertObjectToString(map.get(next));
                        if (next != null) {
                            hashMap.put(next, convertObjectToString);
                        }
                    }
                    return hashMap;
                }
            }
        }
        return null;
    }

    public static Map<String, String> convertToUrlEncodedMap(Map<String, String> map) {
        if (map == null) {
            return map;
        }
        HashMap hashMap = new HashMap();
        for (String next : map.keySet()) {
            if (next instanceof String) {
                String str = map.get(next);
                if (!StringUtils.isEmpty(next) && !StringUtils.isEmpty(str)) {
                    try {
                        hashMap.put(URLEncoder.encode(next, "UTF-8"), URLEncoder.encode(str, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return hashMap;
    }

    public static Map<String, String> convertPropertiesToMap(Properties properties) {
        if (properties == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (Object next : properties.keySet()) {
            if (next instanceof String) {
                String convertObjectToString = StringUtils.convertObjectToString(next);
                String convertObjectToString2 = StringUtils.convertObjectToString(properties.get(next));
                if (!StringUtils.isEmpty(convertObjectToString) && !StringUtils.isEmpty(convertObjectToString2)) {
                    hashMap.put(convertObjectToString, convertObjectToString2);
                }
            }
        }
        return hashMap;
    }
}
