package com.alipay.mobile.tinyappcommon.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;

public final class TinyAppEventUtils {
    public static boolean containsNull(H5Event event, String key) {
        if (event != null) {
            return containsNull(event.getParam(), key);
        }
        return false;
    }

    public static boolean containsNull(JSONObject param, String key) {
        if (param != null && param.containsKey(key) && param.get(key) == null) {
            return true;
        }
        return false;
    }

    public static boolean contains(H5Event event, String key) {
        if (event != null) {
            return contains(event.getParam(), key);
        }
        return false;
    }

    public static boolean contains(JSONObject param, String key) {
        if (param != null) {
            return param.containsKey(key);
        }
        return false;
    }

    public static <T> boolean isValueAssignableFrom(H5Event event, String key, Class<T> valueType) {
        if (event != null) {
            return isValueAssignableFrom(event.getParam(), key, valueType);
        }
        return false;
    }

    public static <T> boolean isValueAssignableFrom(JSONObject param, String key, Class<T> valueType) {
        if (param == null || valueType == null) {
            return false;
        }
        Object value = param.get(key);
        if (value != null) {
            return valueType.isAssignableFrom(value.getClass());
        }
        return false;
    }
}
