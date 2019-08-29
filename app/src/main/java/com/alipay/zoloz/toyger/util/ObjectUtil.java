package com.alipay.zoloz.toyger.util;

import com.alipay.mobile.security.bio.utils.BioLog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ObjectUtil {
    private ObjectUtil() {
    }

    public static HashMap<String, String> objectToStringMap(Object obj) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            Map<String, Object> objectToObjectMap = objectToObjectMap(obj);
            if (objectToObjectMap != null && !objectToObjectMap.isEmpty()) {
                for (Entry next : objectToObjectMap.entrySet()) {
                    hashMap.put(next.getKey(), next.getValue());
                }
            }
        } catch (Throwable th) {
            BioLog.w(th);
        }
        return hashMap;
    }

    private static Map<String, Object> objectToObjectMap(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length == 0) {
            declaredFields = obj.getClass().getFields();
        }
        for (Field field : declaredFields) {
            field.setAccessible(true);
            hashMap.put(field.getName(), field.get(obj));
        }
        return hashMap;
    }
}
