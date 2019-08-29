package com.alipay.inside.jsoncodec.codec;

import com.alipay.inside.jsoncodec.util.ClassUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.json.alipay.inside.JSONArray;
import org.json.alipay.inside.JSONObject;

public class JSONSerializer {
    private static List<ObjectSerializer> serializers;

    static {
        ArrayList arrayList = new ArrayList();
        serializers = arrayList;
        arrayList.add(new SimpleClassCodec());
        serializers.add(new EnumCodec());
        serializers.add(new DateCodec());
        serializers.add(new MapCodec());
        serializers.add(new CollectionCodec());
        serializers.add(new ArrayCodec());
        serializers.add(new JavaBeanCodec());
    }

    public static String serialize(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Object serializeToSimpleObject = serializeToSimpleObject(obj);
        if (ClassUtil.isSimpleType(serializeToSimpleObject.getClass())) {
            return JSONObject.quote(serializeToSimpleObject.toString());
        }
        if (Collection.class.isAssignableFrom(serializeToSimpleObject.getClass())) {
            return new JSONArray((Collection) (List) serializeToSimpleObject).toString();
        }
        if (Map.class.isAssignableFrom(serializeToSimpleObject.getClass())) {
            return new JSONObject((Map) serializeToSimpleObject).toString();
        }
        StringBuilder sb = new StringBuilder("Unsupported Class : ");
        sb.append(serializeToSimpleObject.getClass());
        throw new IllegalArgumentException(sb.toString());
    }

    public static Object serializeToSimpleObject(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        for (ObjectSerializer next : serializers) {
            if (next.match(obj.getClass())) {
                try {
                    Object serialize = next.serialize(obj);
                    if (serialize != null) {
                        return serialize;
                    }
                } catch (Throwable unused) {
                    return null;
                }
            }
        }
        StringBuilder sb = new StringBuilder("Unsupported Class : ");
        sb.append(obj.getClass());
        throw new IllegalArgumentException(sb.toString());
    }

    private static List<Object> parseArrayNode(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (Object next : (List) obj) {
            if (ClassUtil.isSimpleType(next.getClass())) {
                arrayList.add(next);
            } else {
                arrayList.add(parseMapNode(next));
            }
        }
        return arrayList;
    }

    private static TreeMap<String, Object> parseMapNode(Object obj) throws Exception {
        Class cls = obj.getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        if (obj instanceof Map) {
            for (Entry entry : ((Map) obj).entrySet()) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                if (ClassUtil.isSimpleType(value.getClass())) {
                    treeMap.put(str, value);
                } else if (value.getClass().isArray()) {
                    ArrayList arrayList = new ArrayList();
                    if (ClassUtil.isSimpleType(value.getClass().getComponentType())) {
                        convertListFromArray(value, arrayList);
                    } else {
                        int length = Array.getLength(value);
                        for (int i = 0; i < length; i++) {
                            TreeMap<String, Object> parseMapNode = parseMapNode(Array.get(value, i));
                            if (parseMapNode != null && parseMapNode.size() > 0) {
                                arrayList.add(parseMapNode);
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        treeMap.put((String) entry.getKey(), arrayList);
                    }
                } else if (value instanceof Collection) {
                    treeMap.put((String) entry.getKey(), parseArrayNode(value));
                } else {
                    TreeMap<String, Object> parseMapNode2 = parseMapNode(entry.getValue());
                    if (parseMapNode2 != null && parseMapNode2.size() > 0) {
                        treeMap.put((String) entry.getKey(), parseMapNode2);
                    }
                }
            }
        } else {
            while (!cls.equals(Object.class)) {
                if (declaredFields != null && declaredFields.length > 0) {
                    for (Field field : declaredFields) {
                        if (field != null) {
                            Map<String, Object> mapByFieldAndObj = getMapByFieldAndObj(field, obj);
                            if (mapByFieldAndObj != null && mapByFieldAndObj.size() > 0) {
                                treeMap.putAll(mapByFieldAndObj);
                            }
                        }
                    }
                }
                cls = cls.getSuperclass();
                declaredFields = cls.getDeclaredFields();
            }
        }
        return treeMap;
    }

    private static Map<String, Object> getMapByFieldAndObj(Field field, Object obj) throws Exception {
        if (field == null || obj == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String name = field.getName();
        if ("this$0".equals(name)) {
            return null;
        }
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        Object obj2 = field.get(obj);
        if (obj2 == null) {
            return null;
        }
        if (Collection.class.isAssignableFrom(obj2.getClass())) {
            ArrayList arrayList = new ArrayList();
            for (Object next : (Collection) obj2) {
                if (!ClassUtil.isSimpleType(next.getClass())) {
                    arrayList.add(parseMapNode(next));
                } else {
                    arrayList.add(next);
                }
            }
            hashMap.put(name, arrayList);
        } else if (obj2.getClass().isArray()) {
            ArrayList arrayList2 = new ArrayList();
            if (ClassUtil.isSimpleType(obj2.getClass().getComponentType())) {
                convertListFromArray(obj2, arrayList2);
                hashMap.put(name, arrayList2);
            }
        } else if (Date.class.isAssignableFrom(obj2.getClass())) {
            hashMap.put(name, Long.valueOf(((Date) obj2).getTime()));
        } else if (!ClassUtil.isSimpleType(obj2.getClass())) {
            hashMap.put(name, parseMapNode(obj2));
        } else {
            hashMap.put(name, obj2);
        }
        field.setAccessible(isAccessible);
        return hashMap;
    }

    private static void convertListFromArray(Object obj, List<Object> list) {
        int length = Array.getLength(obj);
        Class<?> componentType = obj.getClass().getComponentType();
        for (int i = 0; i < length; i++) {
            if (componentType.equals(Integer.TYPE)) {
                list.add(Integer.valueOf(Array.getInt(obj, i)));
            } else if (componentType.equals(Long.TYPE)) {
                list.add(Long.valueOf(Array.getLong(obj, i)));
            } else if (componentType.equals(Double.TYPE)) {
                list.add(Double.valueOf(Array.getDouble(obj, i)));
            } else if (componentType.equals(Boolean.TYPE)) {
                list.add(Boolean.valueOf(Array.getBoolean(obj, i)));
            } else if (componentType.equals(Short.TYPE)) {
                list.add(Short.valueOf(Array.getShort(obj, i)));
            } else if (componentType.equals(Byte.TYPE)) {
                list.add(Byte.valueOf(Array.getByte(obj, i)));
            } else if (componentType.equals(Float.TYPE)) {
                list.add(Float.valueOf(Array.getFloat(obj, i)));
            } else {
                list.add(Array.get(obj, i));
            }
        }
    }
}
