package com.alipay.mobile.beehive.plugins.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;

public class H5ParamParser {
    private static H5PLogger sLogger = H5PLogger.getLogger((String) com.alipay.mobile.nebula.util.H5ParamParser.TAG);

    public static boolean getBoolean(JSONObject object, String key) {
        if (object == null) {
            return false;
        }
        Boolean v = object.getBoolean(key);
        if (v == null) {
            return false;
        }
        return v.booleanValue();
    }

    public static boolean getBoolean(JSONObject object, String key, boolean defaultVal) {
        if (object == null) {
            return false;
        }
        Boolean v = object.getBoolean(key);
        return v == null ? defaultVal : v.booleanValue();
    }

    public static int getInt(JSONObject object, String key, int defaultVal) {
        int ret = defaultVal;
        if (object == null) {
            return ret;
        }
        Integer i = object.getInteger(key);
        if (i != null) {
            return i.intValue();
        }
        return ret;
    }

    public static float getFloat(JSONObject object, String key) {
        if (object != null) {
            return object.getFloatValue(key);
        }
        return 0.0f;
    }

    public static String[] getStringArr(JSONObject object, String key, String[] defaultVal) {
        String[] ret = defaultVal;
        if (object == null) {
            return ret;
        }
        JSONArray arr = object.getJSONArray(key);
        if (arr == null || arr.size() <= 0) {
            return ret;
        }
        String[] ret2 = new String[arr.size()];
        arr.toArray(ret2);
        return ret2;
    }

    public static String getString(JSONObject object, String key) {
        if (object != null) {
            return object.getString(key);
        }
        return "";
    }

    public static JSONObject getJsonObj(JSONObject object, String key) {
        if (object != null) {
            return object.getJSONObject(key);
        }
        return null;
    }

    public static <T> T parseParams(H5Event event, Class<T> clazz) {
        if (!(event == null || event.getParam() == null || clazz == null)) {
            try {
                return JSON.parseObject(event.getParam().toJSONString(), clazz);
            } catch (Exception t) {
                sLogger.w("parse params error, param: " + event.getParam() + ", clazz: " + clazz + " exception:" + t.getMessage());
            }
        }
        return null;
    }
}
