package com.alipay.inside.jsoncodec.codec;

import com.alipay.inside.jsoncodec.util.ClassUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.alipay.inside.JSONObject;

public class MapCodec implements ObjectDeserializer, ObjectSerializer {
    public Object serialize(Object obj) throws Exception {
        TreeMap treeMap = new TreeMap();
        for (Entry entry : ((Map) obj).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("Map key must be String!");
            }
            treeMap.put((String) entry.getKey(), JSONSerializer.serializeToSimpleObject(entry.getValue()));
        }
        return treeMap;
    }

    public Object deserialize(Object obj, Type type) throws Exception {
        if (!obj.getClass().equals(JSONObject.class)) {
            return null;
        }
        JSONObject jSONObject = (JSONObject) obj;
        Map<Object, Object> createMap = createMap(type);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            Type type3 = parameterizedType.getActualTypeArguments()[1];
            if (String.class == type2) {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    if (ClassUtil.isSimpleType((Class) type3)) {
                        createMap.put(str, jSONObject.get(str));
                    } else {
                        createMap.put(str, JSONDeserializer.deserialize0(jSONObject.get(str), type3));
                    }
                }
                return createMap;
            }
            throw new IllegalArgumentException("Deserialize Map Key must be String.class");
        }
        throw new IllegalArgumentException("Deserialize Map must be Generics!");
    }

    /* access modifiers changed from: protected */
    public Map<Object, Object> createMap(Type type) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class || type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            return createMap(((ParameterizedType) type).getRawType());
        }
        Class cls = (Class) type;
        if (cls.isInterface()) {
            throw new IllegalArgumentException("unsupport type ".concat(String.valueOf(type)));
        }
        try {
            return (Map) cls.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("unsupport type ".concat(String.valueOf(type)), e);
        }
    }

    public boolean match(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }
}
