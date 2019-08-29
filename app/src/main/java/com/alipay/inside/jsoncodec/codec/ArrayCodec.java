package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.json.alipay.inside.JSONArray;

public class ArrayCodec implements ObjectDeserializer, ObjectSerializer {
    public Object serialize(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (Object serializeToSimpleObject : (Object[]) obj) {
            arrayList.add(JSONSerializer.serializeToSimpleObject(serializeToSimpleObject));
        }
        return arrayList;
    }

    public Object deserialize(Object obj, Type type) throws Exception {
        if (!obj.getClass().equals(JSONArray.class)) {
            return null;
        }
        JSONArray jSONArray = (JSONArray) obj;
        if (type instanceof GenericArrayType) {
            throw new IllegalArgumentException("Does not support generic array!");
        }
        Class<?> componentType = ((Class) type).getComponentType();
        int length = jSONArray.length();
        Object newInstance = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(newInstance, i, JSONDeserializer.deserialize0(jSONArray.get(i), componentType));
        }
        return newInstance;
    }

    public boolean match(Class<?> cls) {
        return cls.isArray();
    }
}
