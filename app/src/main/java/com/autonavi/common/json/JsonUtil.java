package com.autonavi.common.json;

import com.autonavi.common.reflect.ReflectUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
    public static <T> T fromJson(Object obj, Class<T> cls) throws Exception {
        return fromJson(obj, cls, (Type) null);
    }

    protected static <T> T fromJson(Object obj, Class<T> cls, Type type) throws Exception {
        if (JSONObject.NULL.equals(obj)) {
            return null;
        }
        if (cls == String.class) {
            return obj instanceof String ? obj : obj.toString();
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return obj instanceof Integer ? obj : Integer.valueOf(obj.toString());
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return obj instanceof Long ? obj : Long.valueOf(obj.toString());
        }
        if (cls == Double.TYPE || cls == Double.class) {
            return obj instanceof Double ? obj : Double.valueOf(obj.toString());
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return obj instanceof Float ? obj : Float.valueOf(obj.toString());
        }
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            return obj instanceof Boolean ? obj : Boolean.valueOf(obj.toString());
        }
        if (!Enum.class.isAssignableFrom(cls)) {
            return (cls == JSONObject.class || cls == JSONArray.class) ? obj : fromJson(obj, (T) createInstance(cls), type);
        }
        return ReflectUtil.valueOfEnum(cls, obj instanceof String ? (String) obj : obj.toString());
    }

    private static Object createInstance(Class cls) throws IllegalAccessException, InstantiationException {
        if (cls.getName().equals("java.util.List")) {
            return new ArrayList();
        }
        if (cls.getName().equals("java.util.Map")) {
            return new HashMap();
        }
        if (cls.getName().equals("java.util.Set")) {
            return new HashSet();
        }
        return cls.newInstance();
    }

    public static <T> T fromJson(Object obj, T t) throws Exception {
        return fromJson(obj, t, (Type) null);
    }

    protected static <T> T fromJson(Object obj, T t, Type type) throws Exception {
        big[] a;
        Object obj2;
        String str;
        if (JSONObject.NULL.equals(obj)) {
            return null;
        }
        if (t instanceof String) {
            return obj instanceof String ? obj : obj.toString();
        }
        if (t instanceof Integer) {
            return obj instanceof Integer ? obj : Integer.valueOf(obj.toString());
        }
        if (t instanceof Long) {
            return obj instanceof Long ? obj : Long.valueOf(obj.toString());
        }
        if (t instanceof Double) {
            return obj instanceof Double ? obj : Double.valueOf(obj.toString());
        }
        if (t instanceof Float) {
            return obj instanceof Float ? obj : Float.valueOf(obj.toString());
        }
        if (t instanceof Boolean) {
            return obj instanceof Boolean ? obj : Boolean.valueOf(obj.toString());
        }
        if (t instanceof Enum) {
            Class<?> cls = t.getClass();
            if (obj instanceof String) {
                str = (String) obj;
            } else {
                str = obj.toString();
            }
            return ReflectUtil.valueOfEnum(cls, str);
        } else if ((t instanceof JSONObject) || (t instanceof JSONArray)) {
            return obj;
        } else {
            if (t instanceof Collection) {
                return convertCollectionFromJson(obj, (Collection) t, type);
            }
            if (t instanceof Map) {
                return convertMapFromJson(obj, (Map) t, type);
            }
            if (t instanceof IJsonItem) {
                return ((IJsonItem) t).fromJson(obj, null);
            }
            JSONObject jSONObject = (JSONObject) obj;
            for (big big : big.a(t.getClass())) {
                Object opt = jSONObject.opt(big.a());
                if (opt != null) {
                    Class<?> a2 = big.a(type);
                    if (IJsonItem.class.isAssignableFrom(a2)) {
                        obj2 = ((IJsonItem) a2.newInstance()).fromJson(opt, big);
                    } else {
                        obj2 = fromJson(opt, a2, big.b(type));
                    }
                    big.a((Object) t, (Object) obj2);
                }
            }
            return t;
        }
    }

    protected static <T extends Collection> T convertCollectionFromJson(Object obj, T t, Type type) throws Exception {
        Class<?> cls = t.getClass();
        Class<?> collectionElementRawType = ReflectUtil.getCollectionElementRawType(cls, type);
        Type collectionElementGenericType = ReflectUtil.getCollectionElementGenericType(cls, type);
        JSONArray jSONArray = (JSONArray) obj;
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            t.add(fromJson(jSONArray.opt(i), collectionElementRawType, collectionElementGenericType));
        }
        return t;
    }

    protected static <T extends Map> T convertMapFromJson(Object obj, T t, Type type) throws Exception {
        Class<?> cls = t.getClass();
        if (!String.class.isAssignableFrom(ReflectUtil.getMapKeyRawType(cls, type))) {
            throw new Exception();
        }
        Class<?> mapValueRawType = ReflectUtil.getMapValueRawType(cls, type);
        Type mapValueGenericType = ReflectUtil.getMapValueGenericType(cls, type);
        JSONObject jSONObject = (JSONObject) obj;
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            t.put(next, fromJson(jSONObject.opt(next), mapValueRawType, mapValueGenericType));
        }
        return t;
    }

    public static <T> T fromString(String str, Class<T> cls) throws Exception {
        return fromString(str, cls, null);
    }

    public static <T> T fromString(String str, Class<T> cls, Type type) throws Exception {
        if (str == null) {
            return null;
        }
        if (cls == String.class) {
            return str;
        }
        return fromJson(fromString(str), cls, type);
    }

    public static <T> T fromString(String str, T t) throws Exception {
        if (t instanceof String) {
            return str;
        }
        return fromJson(fromString(str), t, (Type) null);
    }

    public static <T> String toString(T t) throws Exception {
        if (t == null) {
            return null;
        }
        return toJson(t).toString();
    }

    public static <T> Object toJson(T t) throws Exception {
        if (t == null) {
            return JSONObject.NULL;
        }
        if (t instanceof Collection) {
            return convertCollectionToJson(t);
        }
        if (t instanceof Map) {
            return convertMapToJson(t);
        }
        if (t instanceof IJsonItem) {
            return ((IJsonItem) t).toJson(null);
        }
        if ((t instanceof JSONObject) || (t instanceof JSONArray) || (t instanceof String) || (t instanceof Integer) || (t instanceof Long) || (t instanceof Double) || (t instanceof Float) || (t instanceof Boolean)) {
            return t;
        }
        if (t instanceof Enum) {
            return t.toString();
        }
        JSONObject jSONObject = new JSONObject();
        big[] a = big.a(t.getClass());
        int length = a.length;
        for (int i = 0; i < length; i++) {
            big big = a[i];
            Object a2 = big.a((Object) t);
            if (a2 != null) {
                jSONObject.put(big.a(), a2 instanceof IJsonItem ? ((IJsonItem) a2).toJson(big) : toJson(a2));
            }
        }
        return jSONObject;
    }

    protected static <T> Object convertCollectionToJson(T t) throws Exception {
        JSONArray jSONArray = new JSONArray();
        for (Object json : (Collection) t) {
            jSONArray.put(toJson(json));
        }
        return jSONArray;
    }

    protected static <T> Object convertMapToJson(T t) throws Exception {
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : ((Map) t).entrySet()) {
            Object key = entry.getKey();
            if (!(key instanceof String)) {
                break;
            }
            Object value = entry.getValue();
            if (value != null) {
                jSONObject.put((String) key, toJson(value));
            }
        }
        return jSONObject;
    }

    public static Object fromString(String str) throws Exception {
        return new JSONTokener(str).nextValue();
    }
}
