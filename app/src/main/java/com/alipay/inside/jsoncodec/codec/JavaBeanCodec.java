package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.TreeMap;
import org.json.alipay.inside.JSONObject;

public class JavaBeanCodec implements ObjectDeserializer, ObjectSerializer {
    public boolean match(Class<?> cls) {
        return true;
    }

    public Object serialize(Object obj) throws Exception {
        TreeMap treeMap = new TreeMap();
        Class cls = obj.getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        while (!cls.equals(Object.class)) {
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    Object parseField = parseField(field, obj);
                    if (parseField != null) {
                        treeMap.put(field.getName(), parseField);
                    }
                }
            }
            cls = cls.getSuperclass();
            declaredFields = cls.getDeclaredFields();
        }
        return treeMap;
    }

    public Object deserialize(Object obj, Type type) throws Exception {
        if (!obj.getClass().equals(JSONObject.class)) {
            return null;
        }
        JSONObject jSONObject = (JSONObject) obj;
        Class<? super T> cls = (Class) type;
        Object newInstance = cls.newInstance();
        while (!cls.equals(Object.class)) {
            Field[] declaredFields = cls.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    Type genericType = field.getGenericType();
                    if (jSONObject.has(name)) {
                        field.setAccessible(true);
                        field.set(newInstance, JSONDeserializer.deserialize0(jSONObject.get(name), genericType));
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return newInstance;
    }

    private static Object parseField(Field field, Object obj) throws Exception {
        if (field == null || obj == null || "this$0".equals(field.getName())) {
            return null;
        }
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Object obj2 = field.get(obj);
        if (obj2 == null) {
            return null;
        }
        field.setAccessible(isAccessible);
        return JSONSerializer.serializeToSimpleObject(obj2);
    }
}
