package com.alipay.inside.jsoncodec.codec;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import org.json.alipay.inside.JSONArray;

public class SetDeserializer implements ObjectDeserializer {
    public Object deserialize(Object obj, Type type) throws Exception {
        Type type2;
        if (!obj.getClass().equals(JSONArray.class)) {
            return null;
        }
        JSONArray jSONArray = (JSONArray) obj;
        HashSet hashSet = new HashSet();
        if (type instanceof ParameterizedType) {
            type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            type2 = Object.class;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            hashSet.add(JSONDeserializer.deserialize0(jSONArray.get(i), type2));
        }
        return hashSet;
    }

    public boolean match(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }
}
