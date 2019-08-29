package com.alipay.inside.jsoncodec.codec;

import com.alipay.inside.jsoncodec.util.ClassUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import org.json.alipay.inside.JSONArray;

public class CollectionCodec implements ObjectDeserializer, ObjectSerializer {
    public Object serialize(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (Object serializeToSimpleObject : (Iterable) obj) {
            arrayList.add(JSONSerializer.serializeToSimpleObject(serializeToSimpleObject));
        }
        return arrayList;
    }

    public Object deserialize(Object obj, Type type) throws Exception {
        if (!obj.getClass().equals(JSONArray.class)) {
            return null;
        }
        JSONArray jSONArray = (JSONArray) obj;
        Collection<Object> createCollection = createCollection(ClassUtil.getRawClass(type), type);
        if (type instanceof ParameterizedType) {
            Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            for (int i = 0; i < jSONArray.length(); i++) {
                createCollection.add(JSONDeserializer.deserialize0(jSONArray.get(i), type2));
            }
            return createCollection;
        }
        throw new IllegalArgumentException("Does not support the implement for generics.");
    }

    private static Collection<Object> createCollection(Class<?> cls, Type type) {
        Type type2;
        if (cls == AbstractCollection.class) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSet();
        }
        if (cls.isAssignableFrom(TreeSet.class)) {
            return new TreeSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (cls.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                type2 = Object.class;
            }
            return EnumSet.noneOf((Class) type2);
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("create instane error, class ");
            sb.append(cls.getName());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean match(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }
}
