package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.multimedia.js.video.H5VideoUploadPlugin.UploadVideoParams;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils {
    public static boolean compatibleWithJavaBean = false;
    private static volatile Map<Class, String[]> kotlinIgnores = null;
    private static volatile boolean kotlinIgnores_error = false;
    private static volatile boolean kotlin_class_klass_error = false;
    private static volatile boolean kotlin_error = false;
    private static volatile Constructor kotlin_kclass_constructor = null;
    private static volatile Method kotlin_kclass_getConstructors = null;
    private static volatile Method kotlin_kfunction_getParameters = null;
    private static volatile Method kotlin_kparameter_getName = null;
    private static volatile Class kotlin_metadata = null;
    private static volatile boolean kotlin_metadata_error = false;
    private static final ConcurrentMap<String, Class<?>> mappings;
    private static boolean setAccessibleEnable = true;

    public static boolean isKotlin(Class cls) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.Metadata");
            } catch (Throwable unused) {
                kotlin_metadata_error = true;
            }
        }
        if (kotlin_metadata == null) {
            return false;
        }
        return cls.isAnnotationPresent(kotlin_metadata);
    }

    private static boolean isKotlinIgnore(Class cls, String str) {
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(Class.forName("kotlin.ranges.CharRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.IntRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.LongRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.ClosedFloatRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.ClosedDoubleRange"), new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = hashMap;
            } catch (Throwable unused) {
                kotlinIgnores_error = true;
            }
        }
        if (kotlinIgnores == null) {
            return false;
        }
        String[] strArr = kotlinIgnores.get(cls);
        if (strArr != null && Arrays.binarySearch(strArr, str) >= 0) {
            return true;
        }
        return false;
    }

    public static String[] getKoltinConstructorParameters(Class cls) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                Class<?> cls2 = Class.forName("kotlin.reflect.jvm.internal.KClassImpl");
                kotlin_kclass_constructor = cls2.getConstructor(new Class[]{Class.class});
                kotlin_kclass_getConstructors = cls2.getMethod("getConstructors", new Class[0]);
                kotlin_kfunction_getParameters = Class.forName("kotlin.reflect.KFunction").getMethod("getParameters", new Class[0]);
                kotlin_kparameter_getName = Class.forName("kotlin.reflect.KParameter").getMethod("getName", new Class[0]);
            } catch (Throwable unused) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null || kotlin_error) {
            return null;
        }
        try {
            Iterator it = ((Iterable) kotlin_kclass_getConstructors.invoke(kotlin_kclass_constructor.newInstance(new Object[]{cls}), new Object[0])).iterator();
            Object obj = null;
            while (it.hasNext()) {
                Object next = it.next();
                List list = (List) kotlin_kfunction_getParameters.invoke(next, new Object[0]);
                if (obj == null || list.size() != 0) {
                    obj = next;
                }
                it.hasNext();
            }
            List list2 = (List) kotlin_kfunction_getParameters.invoke(obj, new Object[0]);
            String[] strArr = new String[list2.size()];
            for (int i = 0; i < list2.size(); i++) {
                strArr[i] = (String) kotlin_kparameter_getName.invoke(list2.get(i), new Object[0]);
            }
            return strArr;
        } catch (Throwable unused2) {
            kotlin_error = true;
            return null;
        }
    }

    public static final String castToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static final Byte castToByte(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(str));
        }
        throw new JSONException("can not cast to byte, value : ".concat(String.valueOf(obj)));
    }

    public static final Character castToChar(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Character) {
            return (Character) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0) {
                return null;
            }
            if (str.length() == 1) {
                return Character.valueOf(str.charAt(0));
            }
            throw new JSONException("can not cast to byte, value : ".concat(String.valueOf(obj)));
        }
        throw new JSONException("can not cast to byte, value : ".concat(String.valueOf(obj)));
    }

    public static final Short castToShort(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Short.valueOf(((Number) obj).shortValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(str));
        }
        throw new JSONException("can not cast to short, value : ".concat(String.valueOf(obj)));
    }

    public static final BigDecimal castToBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof BigInteger) {
            return new BigDecimal((BigInteger) obj);
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0 || "null".equals(obj2)) {
            return null;
        }
        return new BigDecimal(obj2);
    }

    public static final BigInteger castToBigInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return BigInteger.valueOf(((Number) obj).longValue());
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0 || "null".equals(obj2)) {
            return null;
        }
        return new BigInteger(obj2);
    }

    public static final Float castToFloat(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Float.valueOf(((Number) obj).floatValue());
        }
        if (obj instanceof String) {
            String obj2 = obj.toString();
            if (obj2.length() == 0 || "null".equals(obj2)) {
                return null;
            }
            return Float.valueOf(Float.parseFloat(obj2));
        }
        throw new JSONException("can not cast to float, value : ".concat(String.valueOf(obj)));
    }

    public static final Double castToDouble(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (obj instanceof String) {
            String obj2 = obj.toString();
            if (obj2.length() == 0 || "null".equals(obj2) || MiscUtil.NULL_STR.equals(obj2)) {
                return null;
            }
            return Double.valueOf(Double.parseDouble(obj2));
        }
        throw new JSONException("can not cast to double, value : ".concat(String.valueOf(obj)));
    }

    public static final Date castToDate(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Calendar) {
            return ((Calendar) obj).getTime();
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        long j = -1;
        if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            int scale = bigDecimal.scale();
            if (scale < -100 || scale > 100) {
                j = bigDecimal.longValueExact();
            } else {
                j = bigDecimal.longValue();
            }
        } else if (obj instanceof Number) {
            j = ((Number) obj).longValue();
        } else if (obj instanceof String) {
            String str = (String) obj;
            if (str.indexOf(45) != -1) {
                String str2 = str.length() == JSON.DEFFAULT_DATE_FORMAT.length() ? JSON.DEFFAULT_DATE_FORMAT : str.length() == 10 ? "yyyy-MM-dd" : str.length() == 19 ? "yyyy-MM-dd HH:mm:ss" : (str.length() == 29 && str.charAt(26) == ':' && str.charAt(28) == '0') ? "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" : "yyyy-MM-dd HH:mm:ss.SSS";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, JSON.defaultLocale);
                simpleDateFormat.setTimeZone(JSON.defaultTimeZone);
                try {
                    return simpleDateFormat.parse(str);
                } catch (ParseException unused) {
                    throw new JSONException("can not cast to Date, value : ".concat(String.valueOf(str)));
                }
            } else if (str.length() == 0 || "null".equals(str)) {
                return null;
            } else {
                j = Long.parseLong(str);
            }
        }
        if (j >= 0) {
            return new Date(j);
        }
        throw new JSONException("can not cast to Date, value : ".concat(String.valueOf(obj)));
    }

    public static final Long castToLong(Object obj) {
        Calendar calendar = null;
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            int scale = bigDecimal.scale();
            if (scale < -100 || scale > 100) {
                return Long.valueOf(bigDecimal.longValueExact());
            }
            return Long.valueOf(bigDecimal.longValue());
        } else if (obj instanceof Number) {
            return Long.valueOf(((Number) obj).longValue());
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0 || "null".equals(str)) {
                    return null;
                }
                try {
                    return Long.valueOf(Long.parseLong(str));
                } catch (NumberFormatException unused) {
                    JSONLexer jSONLexer = new JSONLexer(str);
                    if (jSONLexer.scanISO8601DateIfMatch(false)) {
                        calendar = jSONLexer.calendar;
                    }
                    jSONLexer.close();
                    if (calendar != null) {
                        return Long.valueOf(calendar.getTimeInMillis());
                    }
                }
            }
            throw new JSONException("can not cast to long, value : ".concat(String.valueOf(obj)));
        }
    }

    public static final Integer castToInt(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            int scale = bigDecimal.scale();
            if (scale < -100 || scale > 100) {
                return Integer.valueOf(bigDecimal.intValueExact());
            }
            return Integer.valueOf(bigDecimal.intValue());
        } else if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0 || "null".equals(str)) {
                    return null;
                }
                return Integer.valueOf(Integer.parseInt(str));
            }
            throw new JSONException("can not cast to int, value : ".concat(String.valueOf(obj)));
        }
    }

    public static final byte[] castToBytes(Object obj) {
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            return JSONLexer.decodeFast(str, 0, str.length());
        }
        throw new JSONException("can not cast to int, value : ".concat(String.valueOf(obj)));
    }

    public static final Boolean castToBoolean(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        boolean z = false;
        if (obj instanceof BigDecimal) {
            if (((BigDecimal) obj).intValueExact() == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else if (obj instanceof Number) {
            if (((Number) obj).intValue() == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0 || "null".equals(str)) {
                    return null;
                }
                if ("true".equalsIgnoreCase(str) || "1".equals(str)) {
                    return Boolean.TRUE;
                }
                if ("false".equalsIgnoreCase(str) || "0".equals(str)) {
                    return Boolean.FALSE;
                }
            }
            throw new JSONException("can not cast to int, value : ".concat(String.valueOf(obj)));
        }
    }

    public static final <T> T castToJavaBean(Object obj, Class<T> cls) {
        return cast(obj, cls, ParserConfig.global);
    }

    public static final <T> T cast(Object obj, Class<T> cls, ParserConfig parserConfig) {
        return cast(obj, cls, parserConfig, 0);
    }

    public static final <T> T cast(Object obj, Class<T> cls, ParserConfig parserConfig, int i) {
        T t;
        if (obj == null) {
            return null;
        }
        if (cls == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (cls == obj.getClass()) {
            return obj;
        } else {
            if (!(obj instanceof Map)) {
                int i2 = 0;
                if (cls.isArray()) {
                    if (obj instanceof Collection) {
                        Collection<Object> collection = (Collection) obj;
                        T newInstance = Array.newInstance(cls.getComponentType(), collection.size());
                        for (Object cast : collection) {
                            Array.set(newInstance, i2, cast(cast, cls.getComponentType(), parserConfig));
                            i2++;
                        }
                        return newInstance;
                    } else if (cls == byte[].class) {
                        return castToBytes(obj);
                    }
                }
                if (cls.isAssignableFrom(obj.getClass())) {
                    return obj;
                }
                if (cls == Boolean.TYPE || cls == Boolean.class) {
                    return castToBoolean(obj);
                }
                if (cls == Byte.TYPE || cls == Byte.class) {
                    return castToByte(obj);
                }
                if ((cls == Character.TYPE || cls == Character.class) && (obj instanceof String)) {
                    String str = (String) obj;
                    if (str.length() == 1) {
                        return Character.valueOf(str.charAt(0));
                    }
                }
                if (cls == Short.TYPE || cls == Short.class) {
                    return castToShort(obj);
                }
                if (cls == Integer.TYPE || cls == Integer.class) {
                    return castToInt(obj);
                }
                if (cls == Long.TYPE || cls == Long.class) {
                    return castToLong(obj);
                }
                if (cls == Float.TYPE || cls == Float.class) {
                    return castToFloat(obj);
                }
                if (cls == Double.TYPE || cls == Double.class) {
                    return castToDouble(obj);
                }
                if (cls == String.class) {
                    return castToString(obj);
                }
                if (cls == BigDecimal.class) {
                    return castToBigDecimal(obj);
                }
                if (cls == BigInteger.class) {
                    return castToBigInteger(obj);
                }
                if (cls == Date.class) {
                    return castToDate(obj);
                }
                if (cls.isEnum()) {
                    return castToEnum(obj, cls, parserConfig);
                }
                if (Calendar.class.isAssignableFrom(cls)) {
                    Date castToDate = castToDate(obj);
                    if (cls == Calendar.class) {
                        t = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    } else {
                        try {
                            t = (Calendar) cls.newInstance();
                        } catch (Exception e) {
                            StringBuilder sb = new StringBuilder("can not cast to : ");
                            sb.append(cls.getName());
                            throw new JSONException(sb.toString(), e);
                        }
                    }
                    t.setTime(castToDate);
                    return t;
                }
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (str2.length() == 0 || "null".equals(str2)) {
                        return null;
                    }
                    if (cls == Currency.class) {
                        return Currency.getInstance(str2);
                    }
                }
                StringBuilder sb2 = new StringBuilder("can not cast to : ");
                sb2.append(cls.getName());
                throw new JSONException(sb2.toString());
            } else if (cls == Map.class) {
                return obj;
            } else {
                Map map = (Map) obj;
                if (cls != Object.class || map.containsKey(JSON.DEFAULT_TYPE_KEY)) {
                    return castToJavaBean(map, cls, parserConfig, i);
                }
                return obj;
            }
        }
    }

    public static final <T> T castToEnum(Object obj, Class<T> cls, ParserConfig parserConfig) {
        try {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0) {
                    return null;
                }
                return Enum.valueOf(cls, str);
            }
            if ((obj instanceof Integer) || (obj instanceof Long)) {
                int intValue = ((Number) obj).intValue();
                T[] enumConstants = cls.getEnumConstants();
                if (intValue < enumConstants.length) {
                    return enumConstants[intValue];
                }
            }
            StringBuilder sb = new StringBuilder("can not cast to : ");
            sb.append(cls.getName());
            throw new JSONException(sb.toString());
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("can not cast to : ");
            sb2.append(cls.getName());
            throw new JSONException(sb2.toString(), e);
        }
    }

    public static final <T> T cast(Object obj, Type type, ParserConfig parserConfig) {
        if (obj == null) {
            return null;
        }
        if (type instanceof Class) {
            return cast(obj, (Class) type, parserConfig, 0);
        }
        if (type instanceof ParameterizedType) {
            return cast(obj, (ParameterizedType) type, parserConfig);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : ".concat(String.valueOf(type)));
    }

    public static final <T> T cast(Object obj, ParameterizedType parameterizedType, ParserConfig parserConfig) {
        T t;
        Object obj2;
        Object obj3;
        Type rawType = parameterizedType.getRawType();
        if (rawType == List.class || rawType == ArrayList.class) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List list = (List) obj;
                int size = list.size();
                T arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    Object obj4 = list.get(i);
                    if (!(type instanceof Class)) {
                        obj3 = cast(obj4, type, parserConfig);
                    } else if (obj4 == null || obj4.getClass() != JSONObject.class) {
                        obj3 = cast(obj4, (Class) type, parserConfig, 0);
                    } else {
                        obj3 = ((JSONObject) obj4).toJavaObject((Class) type, parserConfig, 0);
                    }
                    arrayList.add(obj3);
                }
                return arrayList;
            }
        }
        if (rawType == Set.class || rawType == HashSet.class || rawType == TreeSet.class || rawType == List.class || rawType == ArrayList.class) {
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                if (rawType == Set.class || rawType == HashSet.class) {
                    t = new HashSet();
                } else if (rawType == TreeSet.class) {
                    t = new TreeSet();
                } else {
                    t = new ArrayList();
                }
                for (Object next : (Iterable) obj) {
                    if (!(type2 instanceof Class)) {
                        obj2 = cast(next, type2, parserConfig);
                    } else if (next == null || next.getClass() != JSONObject.class) {
                        obj2 = cast(next, (Class) type2, parserConfig, 0);
                    } else {
                        obj2 = ((JSONObject) next).toJavaObject((Class) type2, parserConfig, 0);
                    }
                    t.add(obj2);
                }
                return t;
            }
        }
        if (rawType == Map.class || rawType == HashMap.class) {
            Type type3 = parameterizedType.getActualTypeArguments()[0];
            Type type4 = parameterizedType.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                T hashMap = new HashMap();
                for (Entry entry : ((Map) obj).entrySet()) {
                    hashMap.put(cast(entry.getKey(), type3, parserConfig), cast(entry.getValue(), type4, parserConfig));
                }
                return hashMap;
            }
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str)) {
                return null;
            }
        }
        if (parameterizedType.getActualTypeArguments().length == 1 && (parameterizedType.getActualTypeArguments()[0] instanceof WildcardType)) {
            return cast(obj, rawType, parserConfig);
        }
        throw new JSONException("can not cast to : ".concat(String.valueOf(parameterizedType)));
    }

    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> cls, ParserConfig parserConfig) {
        return castToJavaBean(map, cls, parserConfig, 0);
    }

    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> cls, ParserConfig parserConfig, int i) {
        JSONObject jSONObject;
        int i2 = 0;
        if (cls == StackTraceElement.class) {
            try {
                String str = (String) map.get("className");
                String str2 = (String) map.get("methodName");
                String str3 = (String) map.get("fileName");
                Number number = (Number) map.get("lineNumber");
                if (number != null) {
                    if (number instanceof BigDecimal) {
                        i2 = ((BigDecimal) number).intValueExact();
                    } else {
                        i2 = number.intValue();
                    }
                }
                return new StackTraceElement(str, str2, str3, i2);
            } catch (Exception e) {
                throw new JSONException(e.getMessage(), e);
            }
        } else {
            Object obj = map.get(JSON.DEFAULT_TYPE_KEY);
            JavaBeanDeserializer javaBeanDeserializer = null;
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (parserConfig == null) {
                    parserConfig = ParserConfig.global;
                }
                Class<?> checkAutoType = parserConfig.checkAutoType(str4, null, i);
                if (checkAutoType == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    sb.append(" not found");
                    throw new ClassNotFoundException(sb.toString());
                } else if (!checkAutoType.equals(cls)) {
                    return castToJavaBean(map, checkAutoType, parserConfig, i);
                }
            }
            if (cls.isInterface()) {
                if (map instanceof JSONObject) {
                    jSONObject = (JSONObject) map;
                } else {
                    jSONObject = new JSONObject(map);
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                if (parserConfig.getDeserializer(cls) != null) {
                    return JSON.parseObject(JSON.toJSONString(jSONObject), cls);
                }
                return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, jSONObject);
            } else if (cls == String.class && (map instanceof JSONObject)) {
                return map.toString();
            } else {
                if (parserConfig == null) {
                    parserConfig = ParserConfig.global;
                }
                ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
                if (deserializer instanceof JavaBeanDeserializer) {
                    javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                }
                if (javaBeanDeserializer != null) {
                    return javaBeanDeserializer.createInstance(map, parserConfig);
                }
                throw new JSONException("can not get javaBeanDeserializer");
            }
        }
    }

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(36, 0.75f, 1);
        mappings = concurrentHashMap;
        concurrentHashMap.put("byte", Byte.TYPE);
        mappings.put(UploadVideoParams.TYPE_SHORT, Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put("[B", byte[].class);
        mappings.put("[S", short[].class);
        mappings.put("[I", int[].class);
        mappings.put("[J", long[].class);
        mappings.put("[F", float[].class);
        mappings.put("[D", double[].class);
        mappings.put("[C", char[].class);
        mappings.put("[Z", boolean[].class);
        mappings.put("java.util.HashMap", HashMap.class);
        mappings.put("java.util.TreeMap", TreeMap.class);
        mappings.put("java.util.Date", Date.class);
        mappings.put("com.alibaba.fastjson.JSONObject", JSONObject.class);
        mappings.put("java.util.concurrent.ConcurrentHashMap", ConcurrentHashMap.class);
        mappings.put("java.text.SimpleDateFormat", SimpleDateFormat.class);
        mappings.put("java.lang.StackTraceElement", StackTraceElement.class);
        mappings.put("java.lang.RuntimeException", RuntimeException.class);
    }

    public static Class<?> getClassFromMapping(String str) {
        return (Class) mappings.get(str);
    }

    public static Class<?> loadClass(String str, ClassLoader classLoader) {
        return loadClass(str, classLoader, false);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:(4:25|26|(2:28|29)|32)|33|34|(3:37|(2:39|40)|43)|44|45|46|47|48) */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009d, code lost:
        r7 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x0078 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x0093 */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008a A[SYNTHETIC, Splitter:B:39:0x008a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Class<?> loadClass(java.lang.String r6, java.lang.ClassLoader r7, boolean r8) {
        /*
            r0 = 0
        L_0x0001:
            r1 = 0
            if (r6 == 0) goto L_0x009f
            int r2 = r6.length()
            if (r2 != 0) goto L_0x000c
            goto L_0x009f
        L_0x000c:
            int r2 = r6.length()
            r3 = 256(0x100, float:3.59E-43)
            if (r2 < r3) goto L_0x0024
            com.alibaba.fastjson.JSONException r7 = new com.alibaba.fastjson.JSONException
            java.lang.String r8 = "className too long. "
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r6 = r8.concat(r6)
            r7.<init>(r6)
            throw r7
        L_0x0024:
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Class<?>> r2 = mappings
            java.lang.Object r2 = r2.get(r6)
            java.lang.Class r2 = (java.lang.Class) r2
            if (r2 == 0) goto L_0x002f
            return r2
        L_0x002f:
            char r3 = r6.charAt(r0)
            r4 = 91
            r5 = 1
            if (r3 != r4) goto L_0x004c
            java.lang.String r6 = r6.substring(r5)
            java.lang.Class r6 = loadClass(r6, r7, r0)
            if (r6 != 0) goto L_0x0043
            return r1
        L_0x0043:
            java.lang.Object r6 = java.lang.reflect.Array.newInstance(r6, r0)
            java.lang.Class r6 = r6.getClass()
            return r6
        L_0x004c:
            java.lang.String r1 = "L"
            boolean r1 = r6.startsWith(r1)
            if (r1 == 0) goto L_0x0067
            java.lang.String r1 = ";"
            boolean r1 = r6.endsWith(r1)
            if (r1 == 0) goto L_0x0067
            int r8 = r6.length()
            int r8 = r8 - r5
            java.lang.String r6 = r6.substring(r5, r8)
            r8 = 0
            goto L_0x0001
        L_0x0067:
            if (r7 == 0) goto L_0x0078
            java.lang.Class r0 = r7.loadClass(r6)     // Catch:{ Exception -> 0x0078 }
            if (r8 == 0) goto L_0x0077
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Class<?>> r1 = mappings     // Catch:{ Exception -> 0x0075 }
            r1.put(r6, r0)     // Catch:{ Exception -> 0x0075 }
            goto L_0x0077
        L_0x0075:
            r2 = r0
            goto L_0x0078
        L_0x0077:
            return r0
        L_0x0078:
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0093 }
            java.lang.ClassLoader r0 = r0.getContextClassLoader()     // Catch:{ Exception -> 0x0093 }
            if (r0 == 0) goto L_0x0093
            if (r0 == r7) goto L_0x0093
            java.lang.Class r7 = r0.loadClass(r6)     // Catch:{ Exception -> 0x0093 }
            if (r8 == 0) goto L_0x0092
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Class<?>> r8 = mappings     // Catch:{ Exception -> 0x0090 }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x0090 }
            goto L_0x0092
        L_0x0090:
            r2 = r7
            goto L_0x0093
        L_0x0092:
            return r7
        L_0x0093:
            java.lang.Class r7 = java.lang.Class.forName(r6)     // Catch:{ Exception -> 0x009d }
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Class<?>> r8 = mappings     // Catch:{ Exception -> 0x009e }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x009e }
            return r7
        L_0x009d:
            r7 = r2
        L_0x009e:
            return r7
        L_0x009f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.loadClass(java.lang.String, java.lang.ClassLoader, boolean):java.lang.Class");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0301, code lost:
        if (r0 != null) goto L_0x0303;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x0419, code lost:
        if (r0 != null) goto L_0x041e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0448, code lost:
        if (r0 != null) goto L_0x044f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b3, code lost:
        if (r5.getReturnType().getName().equals("groovy.lang.MetaClass") == false) goto L_0x00b8;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r45v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0331  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0389  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x03b6  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x03cf  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0479  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0158  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x016b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.alibaba.fastjson.util.FieldInfo> computeGetters(java.lang.Class r45, int r46, boolean r47, com.alibaba.fastjson.annotation.JSONType r48, java.util.Map<java.lang.String, java.lang.String> r49, boolean r50, boolean r51, boolean r52, com.alibaba.fastjson.PropertyNamingStrategy r53) {
        /*
            r11 = r45
            r12 = r46
            r13 = r48
            r14 = r49
            r10 = r53
            java.util.LinkedHashMap r9 = new java.util.LinkedHashMap
            r9.<init>()
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            java.lang.reflect.Field[] r7 = r45.getDeclaredFields()
            r16 = 0
            if (r47 != 0) goto L_0x049d
            boolean r17 = isKotlin(r45)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = r11
        L_0x0026:
            if (r1 == 0) goto L_0x007f
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r1 == r2) goto L_0x007f
            java.lang.reflect.Method[] r2 = r1.getDeclaredMethods()
            int r3 = r2.length
            r4 = 0
        L_0x0032:
            if (r4 >= r3) goto L_0x007a
            r5 = r2[r4]
            int r6 = r5.getModifiers()
            r20 = r6 & 8
            if (r20 != 0) goto L_0x0073
            r20 = r6 & 2
            if (r20 != 0) goto L_0x0073
            r21 = r2
            r2 = r6 & 256(0x100, float:3.59E-43)
            if (r2 != 0) goto L_0x0075
            r2 = r6 & 4
            if (r2 != 0) goto L_0x0075
            java.lang.Class r2 = r5.getReturnType()
            java.lang.Class r6 = java.lang.Void.TYPE
            boolean r2 = r2.equals(r6)
            if (r2 != 0) goto L_0x0075
            java.lang.Class[] r2 = r5.getParameterTypes()
            int r2 = r2.length
            if (r2 != 0) goto L_0x0075
            java.lang.Class r2 = r5.getReturnType()
            java.lang.Class<java.lang.ClassLoader> r6 = java.lang.ClassLoader.class
            if (r2 == r6) goto L_0x0075
            java.lang.Class r2 = r5.getDeclaringClass()
            java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
            if (r2 == r6) goto L_0x0075
            r0.add(r5)
            goto L_0x0075
        L_0x0073:
            r21 = r2
        L_0x0075:
            int r4 = r4 + 1
            r2 = r21
            goto L_0x0032
        L_0x007a:
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x0026
        L_0x007f:
            java.util.Iterator r6 = r0.iterator()
            r0 = r16
            r1 = r0
            r2 = r1
            r3 = r2
        L_0x0088:
            boolean r4 = r6.hasNext()
            if (r4 == 0) goto L_0x049d
            java.lang.Object r4 = r6.next()
            r5 = r4
            java.lang.reflect.Method r5 = (java.lang.reflect.Method) r5
            java.lang.String r4 = r5.getName()
            r22 = r1
            java.lang.String r1 = "getMetaClass"
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L_0x00b6
            java.lang.Class r1 = r5.getReturnType()
            java.lang.String r1 = r1.getName()
            r23 = r2
            java.lang.String r2 = "groovy.lang.MetaClass"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x00d6
            goto L_0x00b8
        L_0x00b6:
            r23 = r2
        L_0x00b8:
            if (r51 == 0) goto L_0x00c3
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r5.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            goto L_0x00c5
        L_0x00c3:
            r1 = r16
        L_0x00c5:
            if (r1 != 0) goto L_0x00cd
            if (r51 == 0) goto L_0x00cd
            com.alibaba.fastjson.annotation.JSONField r1 = getSupperMethodAnnotation(r11, r5)
        L_0x00cd:
            if (r17 == 0) goto L_0x00db
            boolean r2 = isKotlinIgnore(r11, r4)
            if (r2 != 0) goto L_0x00d6
            goto L_0x00db
        L_0x00d6:
            r1 = r22
            r2 = r23
            goto L_0x0088
        L_0x00db:
            if (r1 != 0) goto L_0x0188
            if (r17 == 0) goto L_0x0188
            if (r0 != 0) goto L_0x0133
            java.lang.reflect.Constructor[] r0 = r45.getDeclaredConstructors()
            int r2 = r0.length
            r25 = r3
            r3 = 1
            if (r2 != r3) goto L_0x012c
            r2 = 0
            r3 = r0[r2]
            java.lang.annotation.Annotation[][] r3 = r3.getParameterAnnotations()
            java.lang.String[] r2 = getKoltinConstructorParameters(r45)
            if (r2 == 0) goto L_0x0120
            r28 = r0
            int r0 = r2.length
            java.lang.String[] r0 = new java.lang.String[r0]
            r29 = r1
            int r1 = r2.length
            r30 = r3
            r3 = 0
            java.lang.System.arraycopy(r2, r3, r0, r3, r1)
            java.util.Arrays.sort(r0)
            int r1 = r2.length
            short[] r1 = new short[r1]
            r31 = r6
        L_0x010e:
            int r6 = r2.length
            if (r3 >= r6) goto L_0x011d
            r6 = r2[r3]
            int r6 = java.util.Arrays.binarySearch(r0, r6)
            r1[r6] = r3
            int r3 = r3 + 1
            short r3 = (short) r3
            goto L_0x010e
        L_0x011d:
            r23 = r1
            goto L_0x0129
        L_0x0120:
            r28 = r0
            r29 = r1
            r30 = r3
            r31 = r6
            r0 = r2
        L_0x0129:
            r25 = r30
            goto L_0x013d
        L_0x012c:
            r28 = r0
            r29 = r1
            r31 = r6
            goto L_0x013b
        L_0x0133:
            r29 = r1
            r25 = r3
            r31 = r6
            r28 = r0
        L_0x013b:
            r0 = r22
        L_0x013d:
            if (r0 == 0) goto L_0x0185
            if (r23 == 0) goto L_0x0185
            java.lang.String r1 = "get"
            boolean r1 = r4.startsWith(r1)
            if (r1 == 0) goto L_0x0185
            r1 = 3
            java.lang.String r2 = r4.substring(r1)
            java.lang.String r1 = decapitalize(r2)
            int r2 = java.util.Arrays.binarySearch(r0, r1)
            if (r2 >= 0) goto L_0x0169
            r3 = 0
        L_0x0159:
            int r6 = r0.length
            if (r3 >= r6) goto L_0x0169
            r6 = r0[r3]
            boolean r6 = r1.equalsIgnoreCase(r6)
            if (r6 == 0) goto L_0x0166
            r2 = r3
            goto L_0x0169
        L_0x0166:
            int r3 = r3 + 1
            goto L_0x0159
        L_0x0169:
            if (r2 < 0) goto L_0x0185
            short r1 = r23[r2]
            r1 = r25[r1]
            if (r1 == 0) goto L_0x0185
            int r2 = r1.length
            r3 = 0
        L_0x0173:
            if (r3 >= r2) goto L_0x0185
            r6 = r1[r3]
            r32 = r0
            boolean r0 = r6 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r0 == 0) goto L_0x0180
            com.alibaba.fastjson.annotation.JSONField r6 = (com.alibaba.fastjson.annotation.JSONField) r6
            goto L_0x0194
        L_0x0180:
            int r3 = r3 + 1
            r0 = r32
            goto L_0x0173
        L_0x0185:
            r32 = r0
            goto L_0x0192
        L_0x0188:
            r29 = r1
            r25 = r3
            r31 = r6
            r28 = r0
            r32 = r22
        L_0x0192:
            r6 = r29
        L_0x0194:
            if (r6 == 0) goto L_0x0232
            boolean r0 = r6.serialize()
            if (r0 == 0) goto L_0x0225
            int r18 = r6.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r0 = r6.serialzeFeatures()
            int r19 = com.alibaba.fastjson.serializer.SerializerFeature.of(r0)
            java.lang.String r0 = r6.name()
            int r0 = r0.length()
            if (r0 == 0) goto L_0x0216
            java.lang.String r0 = r6.name()
            if (r14 == 0) goto L_0x01cd
            java.lang.Object r0 = r14.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x01c1
            goto L_0x01cd
        L_0x01c1:
            r13 = r7
            r42 = r8
            r12 = r9
            r21 = r31
            r20 = 0
            r26 = 1
            goto L_0x0485
        L_0x01cd:
            r4 = r0
            setAccessible(r11, r5, r12)
            com.alibaba.fastjson.util.FieldInfo r3 = new com.alibaba.fastjson.util.FieldInfo
            r20 = 0
            r21 = 0
            r22 = 0
            r24 = 1
            r0 = r3
            r1 = r4
            r27 = 0
            r2 = r5
            r5 = r3
            r26 = 1
            r3 = r20
            r33 = r4
            r4 = r11
            r34 = r5
            r20 = 0
            r5 = r21
            r29 = r6
            r21 = r31
            r6 = r18
            r35 = r7
            r7 = r19
            r36 = r8
            r8 = r29
            r37 = r9
            r9 = r22
            r12 = r10
            r10 = r24
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r0 = r33
            r1 = r34
            r10 = r37
            r10.put(r0, r1)
            r12 = r10
            r13 = r35
            r42 = r36
            goto L_0x0485
        L_0x0216:
            r29 = r6
            r35 = r7
            r36 = r8
            r12 = r10
            r21 = r31
            r20 = 0
            r26 = 1
            r10 = r9
            goto L_0x0244
        L_0x0225:
            r12 = r10
            r21 = r31
            r20 = 0
            r26 = 1
            r13 = r7
            r42 = r8
            r12 = r9
            goto L_0x0485
        L_0x0232:
            r29 = r6
            r35 = r7
            r36 = r8
            r12 = r10
            r21 = r31
            r20 = 0
            r26 = 1
            r10 = r9
            r18 = 0
            r19 = 0
        L_0x0244:
            java.lang.String r0 = "get"
            boolean r0 = r4.startsWith(r0)
            r9 = 102(0x66, float:1.43E-43)
            r8 = 95
            if (r0 == 0) goto L_0x0367
            int r0 = r4.length()
            r1 = 4
            if (r0 < r1) goto L_0x035e
            java.lang.String r0 = "getClass"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L_0x035e
            r2 = 3
            char r0 = r4.charAt(r2)
            boolean r3 = java.lang.Character.isUpperCase(r0)
            if (r3 == 0) goto L_0x0293
            boolean r0 = compatibleWithJavaBean
            if (r0 == 0) goto L_0x0277
            java.lang.String r0 = r4.substring(r2)
            java.lang.String r0 = decapitalize(r0)
            goto L_0x02ba
        L_0x0277:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            char r3 = r4.charAt(r2)
            char r3 = java.lang.Character.toLowerCase(r3)
            r0.append(r3)
            java.lang.String r1 = r4.substring(r1)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x02ba
        L_0x0293:
            if (r0 != r8) goto L_0x029a
            java.lang.String r0 = r4.substring(r1)
            goto L_0x02ba
        L_0x029a:
            if (r0 != r9) goto L_0x02a1
            java.lang.String r0 = r4.substring(r2)
            goto L_0x02ba
        L_0x02a1:
            int r0 = r4.length()
            r3 = 5
            if (r0 < r3) goto L_0x035e
            char r0 = r4.charAt(r1)
            boolean r0 = java.lang.Character.isUpperCase(r0)
            if (r0 == 0) goto L_0x035e
            java.lang.String r0 = r4.substring(r2)
            java.lang.String r0 = decapitalize(r0)
        L_0x02ba:
            boolean r1 = isJSONTypeIgnore(r11, r13, r0)
            if (r1 != 0) goto L_0x035e
            r6 = r35
            r7 = r36
            java.lang.reflect.Field r3 = getField(r11, r0, r6, r7)
            if (r3 == 0) goto L_0x0313
            if (r51 == 0) goto L_0x02d5
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r3.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            goto L_0x02d7
        L_0x02d5:
            r1 = r16
        L_0x02d7:
            if (r1 == 0) goto L_0x030f
            boolean r18 = r1.serialize()
            if (r18 == 0) goto L_0x032b
            int r18 = r1.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r2 = r1.serialzeFeatures()
            int r2 = com.alibaba.fastjson.serializer.SerializerFeature.of(r2)
            java.lang.String r8 = r1.name()
            int r8 = r8.length()
            if (r8 == 0) goto L_0x030a
            java.lang.String r0 = r1.name()
            if (r14 == 0) goto L_0x0303
            java.lang.Object r0 = r14.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x032b
        L_0x0303:
            r22 = r1
            r19 = r2
            r1 = r0
            r0 = 1
            goto L_0x0317
        L_0x030a:
            r22 = r1
            r19 = r2
            goto L_0x0311
        L_0x030f:
            r22 = r1
        L_0x0311:
            r1 = r0
            goto L_0x0316
        L_0x0313:
            r1 = r0
            r22 = r16
        L_0x0316:
            r0 = 0
        L_0x0317:
            if (r12 == 0) goto L_0x031f
            if (r0 != 0) goto L_0x031f
            java.lang.String r1 = r12.translate(r1)
        L_0x031f:
            if (r14 == 0) goto L_0x0331
            java.lang.Object r0 = r14.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x032b
            r2 = r0
            goto L_0x0332
        L_0x032b:
            r13 = r6
            r42 = r7
            r12 = r10
            goto L_0x0485
        L_0x0331:
            r2 = r1
        L_0x0332:
            r8 = r12
            r12 = r46
            setAccessible(r11, r5, r12)
            com.alibaba.fastjson.util.FieldInfo r1 = new com.alibaba.fastjson.util.FieldInfo
            r24 = 0
            r0 = r1
            r12 = r1
            r1 = r2
            r14 = r2
            r15 = 3
            r2 = r5
            r15 = r4
            r4 = r11
            r38 = r5
            r5 = r24
            r39 = r6
            r6 = r18
            r40 = r7
            r7 = r19
            r8 = r29
            r9 = r22
            r11 = r10
            r10 = r52
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r11.put(r14, r12)
            goto L_0x036f
        L_0x035e:
            r12 = r10
            r13 = r35
            r42 = r36
        L_0x0363:
            r11 = r45
            goto L_0x0485
        L_0x0367:
            r15 = r4
            r38 = r5
            r11 = r10
            r39 = r35
            r40 = r36
        L_0x036f:
            java.lang.String r0 = "is"
            boolean r0 = r15.startsWith(r0)
            if (r0 == 0) goto L_0x047e
            int r0 = r15.length()
            r1 = 3
            if (r0 < r1) goto L_0x047e
            r0 = 2
            char r1 = r15.charAt(r0)
            boolean r2 = java.lang.Character.isUpperCase(r1)
            if (r2 == 0) goto L_0x03b6
            boolean r1 = compatibleWithJavaBean
            if (r1 == 0) goto L_0x0399
            java.lang.String r0 = r15.substring(r0)
            java.lang.String r0 = decapitalize(r0)
        L_0x0395:
            r12 = r11
            r11 = r45
            goto L_0x03c9
        L_0x0399:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            char r0 = r15.charAt(r0)
            char r0 = java.lang.Character.toLowerCase(r0)
            r1.append(r0)
            r2 = 3
            java.lang.String r0 = r15.substring(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x0395
        L_0x03b6:
            r2 = 3
            r3 = 95
            if (r1 != r3) goto L_0x03c0
            java.lang.String r0 = r15.substring(r2)
            goto L_0x0395
        L_0x03c0:
            r2 = 102(0x66, float:1.43E-43)
            if (r1 != r2) goto L_0x047e
            java.lang.String r0 = r15.substring(r0)
            goto L_0x0395
        L_0x03c9:
            boolean r1 = isJSONTypeIgnore(r11, r13, r0)
            if (r1 != 0) goto L_0x0479
            r10 = r39
            r14 = r40
            java.lang.reflect.Field r1 = getField(r11, r0, r10, r14)
            if (r1 != 0) goto L_0x03dd
            java.lang.reflect.Field r1 = getField(r11, r15, r10, r14)
        L_0x03dd:
            r3 = r1
            if (r3 == 0) goto L_0x0430
            if (r51 == 0) goto L_0x03eb
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r3.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            goto L_0x03ed
        L_0x03eb:
            r1 = r16
        L_0x03ed:
            if (r1 == 0) goto L_0x0425
            boolean r2 = r1.serialize()
            if (r2 == 0) goto L_0x044b
            int r2 = r1.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r4 = r1.serialzeFeatures()
            int r4 = com.alibaba.fastjson.serializer.SerializerFeature.of(r4)
            java.lang.String r5 = r1.name()
            int r5 = r5.length()
            if (r5 == 0) goto L_0x041c
            java.lang.String r0 = r1.name()
            r9 = r49
            if (r9 == 0) goto L_0x041e
            java.lang.Object r0 = r9.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x044b
            goto L_0x041e
        L_0x041c:
            r9 = r49
        L_0x041e:
            r18 = r1
            r6 = r2
            r7 = r4
            r8 = r53
            goto L_0x043a
        L_0x0425:
            r9 = r49
            r6 = r18
            r7 = r19
            r8 = r53
            r18 = r1
            goto L_0x043a
        L_0x0430:
            r9 = r49
            r6 = r18
            r7 = r19
            r8 = r53
            r18 = r16
        L_0x043a:
            if (r8 == 0) goto L_0x0440
            java.lang.String r0 = r8.translate(r0)
        L_0x0440:
            if (r9 == 0) goto L_0x044f
            java.lang.Object r0 = r9.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x044b
            goto L_0x044f
        L_0x044b:
            r13 = r10
            r42 = r14
            goto L_0x0485
        L_0x044f:
            r4 = r0
            r5 = r46
            setAccessible(r11, r3, r5)
            r2 = r38
            setAccessible(r11, r2, r5)
            com.alibaba.fastjson.util.FieldInfo r1 = new com.alibaba.fastjson.util.FieldInfo
            r19 = 0
            r0 = r1
            r41 = r1
            r1 = r4
            r42 = r14
            r14 = r4
            r4 = r11
            r13 = r5
            r5 = r19
            r8 = r29
            r9 = r18
            r13 = r10
            r10 = r52
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r0 = r41
            r12.put(r14, r0)
            goto L_0x0485
        L_0x0479:
            r13 = r39
            r42 = r40
            goto L_0x0485
        L_0x047e:
            r12 = r11
            r13 = r39
            r42 = r40
            goto L_0x0363
        L_0x0485:
            r9 = r12
            r7 = r13
            r6 = r21
            r2 = r23
            r3 = r25
            r0 = r28
            r1 = r32
            r8 = r42
            r10 = r53
            r12 = r46
            r13 = r48
            r14 = r49
            goto L_0x0088
        L_0x049d:
            r13 = r7
            r12 = r9
            r20 = 0
            r26 = 1
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r13.length
            r0.<init>(r1)
            int r1 = r13.length
            r2 = 0
        L_0x04ab:
            if (r2 >= r1) goto L_0x04d2
            r3 = r13[r2]
            int r4 = r3.getModifiers()
            r4 = r4 & 8
            if (r4 != 0) goto L_0x04cf
            java.lang.String r4 = r3.getName()
            java.lang.String r5 = "this$0"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x04cf
            int r4 = r3.getModifiers()
            r4 = r4 & 1
            if (r4 == 0) goto L_0x04cf
            r0.add(r3)
        L_0x04cf:
            int r2 = r2 + 1
            goto L_0x04ab
        L_0x04d2:
            java.lang.Class r1 = r45.getSuperclass()
        L_0x04d6:
            if (r1 == 0) goto L_0x0501
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r1 == r2) goto L_0x0501
            java.lang.reflect.Field[] r2 = r1.getDeclaredFields()
            int r3 = r2.length
            r4 = 0
        L_0x04e2:
            if (r4 >= r3) goto L_0x04fc
            r5 = r2[r4]
            int r6 = r5.getModifiers()
            r6 = r6 & 8
            if (r6 != 0) goto L_0x04f9
            int r6 = r5.getModifiers()
            r6 = r6 & 1
            if (r6 == 0) goto L_0x04f9
            r0.add(r5)
        L_0x04f9:
            int r4 = r4 + 1
            goto L_0x04e2
        L_0x04fc:
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x04d6
        L_0x0501:
            java.util.Iterator r13 = r0.iterator()
        L_0x0505:
            boolean r0 = r13.hasNext()
            if (r0 == 0) goto L_0x058b
            java.lang.Object r0 = r13.next()
            r3 = r0
            java.lang.reflect.Field r3 = (java.lang.reflect.Field) r3
            if (r51 == 0) goto L_0x051e
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r0 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r0 = r3.getAnnotation(r0)
            com.alibaba.fastjson.annotation.JSONField r0 = (com.alibaba.fastjson.annotation.JSONField) r0
            r9 = r0
            goto L_0x0520
        L_0x051e:
            r9 = r16
        L_0x0520:
            java.lang.String r0 = r3.getName()
            if (r9 == 0) goto L_0x0549
            boolean r1 = r9.serialize()
            if (r1 == 0) goto L_0x0505
            int r1 = r9.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r2 = r9.serialzeFeatures()
            int r2 = com.alibaba.fastjson.serializer.SerializerFeature.of(r2)
            java.lang.String r4 = r9.name()
            int r4 = r4.length()
            if (r4 == 0) goto L_0x0546
            java.lang.String r0 = r9.name()
        L_0x0546:
            r6 = r1
            r7 = r2
            goto L_0x054b
        L_0x0549:
            r6 = 0
            r7 = 0
        L_0x054b:
            r14 = r49
            if (r14 == 0) goto L_0x0557
            java.lang.Object r0 = r14.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0505
        L_0x0557:
            r10 = r53
            if (r10 == 0) goto L_0x055f
            java.lang.String r0 = r10.translate(r0)
        L_0x055f:
            r8 = r0
            boolean r0 = r12.containsKey(r8)
            if (r0 != 0) goto L_0x0505
            r5 = r46
            setAccessible(r11, r3, r5)
            com.alibaba.fastjson.util.FieldInfo r4 = new com.alibaba.fastjson.util.FieldInfo
            r2 = 0
            r17 = 0
            r18 = 0
            r0 = r4
            r1 = r8
            r43 = r4
            r4 = r11
            r5 = r17
            r44 = r8
            r8 = r18
            r10 = r52
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r1 = r43
            r0 = r44
            r12.put(r0, r1)
            goto L_0x0505
        L_0x058b:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = r48
            if (r1 == 0) goto L_0x05b0
            java.lang.String[] r1 = r48.orders()
            if (r1 == 0) goto L_0x05b2
            int r2 = r1.length
            int r3 = r12.size()
            if (r2 != r3) goto L_0x05b2
            int r2 = r1.length
            r3 = 0
        L_0x05a3:
            if (r3 >= r2) goto L_0x05b4
            r4 = r1[r3]
            boolean r4 = r12.containsKey(r4)
            if (r4 == 0) goto L_0x05b2
            int r3 = r3 + 1
            goto L_0x05a3
        L_0x05b0:
            r1 = r16
        L_0x05b2:
            r26 = 0
        L_0x05b4:
            if (r26 == 0) goto L_0x05c8
            int r2 = r1.length
            r3 = 0
        L_0x05b8:
            if (r3 >= r2) goto L_0x05e5
            r4 = r1[r3]
            java.lang.Object r4 = r12.get(r4)
            com.alibaba.fastjson.util.FieldInfo r4 = (com.alibaba.fastjson.util.FieldInfo) r4
            r0.add(r4)
            int r3 = r3 + 1
            goto L_0x05b8
        L_0x05c8:
            java.util.Collection r1 = r12.values()
            java.util.Iterator r1 = r1.iterator()
        L_0x05d0:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x05e0
            java.lang.Object r2 = r1.next()
            com.alibaba.fastjson.util.FieldInfo r2 = (com.alibaba.fastjson.util.FieldInfo) r2
            r0.add(r2)
            goto L_0x05d0
        L_0x05e0:
            if (r50 == 0) goto L_0x05e5
            java.util.Collections.sort(r0)
        L_0x05e5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.computeGetters(java.lang.Class, int, boolean, com.alibaba.fastjson.annotation.JSONType, java.util.Map, boolean, boolean, boolean, com.alibaba.fastjson.PropertyNamingStrategy):java.util.List");
    }

    public static JSONField getSupperMethodAnnotation(Class<?> cls, Method method) {
        Method[] methods;
        boolean z;
        Method[] methods2;
        boolean z2;
        for (Class methods3 : cls.getInterfaces()) {
            for (Method method2 : methods3.getMethods()) {
                if (method2.getName().equals(method.getName())) {
                    Class[] parameterTypes = method2.getParameterTypes();
                    Class[] parameterTypes2 = method.getParameterTypes();
                    if (parameterTypes.length == parameterTypes2.length) {
                        int i = 0;
                        while (true) {
                            if (i >= parameterTypes.length) {
                                z2 = true;
                                break;
                            } else if (!parameterTypes[i].equals(parameterTypes2[i])) {
                                z2 = false;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (z2) {
                            JSONField jSONField = (JSONField) method2.getAnnotation(JSONField.class);
                            if (jSONField != null) {
                                return jSONField;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        Class<? super T> superclass = cls.getSuperclass();
        if (superclass != null && Modifier.isAbstract(superclass.getModifiers())) {
            Class[] parameterTypes3 = method.getParameterTypes();
            for (Method method3 : superclass.getMethods()) {
                Class[] parameterTypes4 = method3.getParameterTypes();
                if (parameterTypes4.length == parameterTypes3.length && method3.getName().equals(method.getName())) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= parameterTypes3.length) {
                            z = true;
                            break;
                        } else if (!parameterTypes4[i2].equals(parameterTypes3[i2])) {
                            z = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (z) {
                        JSONField jSONField2 = (JSONField) method3.getAnnotation(JSONField.class);
                        if (jSONField2 != null) {
                            return jSONField2;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> cls, JSONType jSONType, String str) {
        if (!(jSONType == null || jSONType.ignores() == null)) {
            for (String equalsIgnoreCase : jSONType.ignores()) {
                if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                    return true;
                }
            }
        }
        Class<? super T> superclass = cls.getSuperclass();
        return (superclass == Object.class || superclass == null || !isJSONTypeIgnore(superclass, (JSONType) superclass.getAnnotation(JSONType.class), str)) ? false : true;
    }

    public static boolean isGenericParamType(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Type genericSuperclass = ((Class) type).getGenericSuperclass();
        if (genericSuperclass == Object.class || !isGenericParamType(genericSuperclass)) {
            return false;
        }
        return true;
    }

    public static Type getGenericParamType(Type type) {
        while (type instanceof Class) {
            type = ((Class) type).getGenericSuperclass();
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        while (type.getClass() != Class.class) {
            if (type instanceof ParameterizedType) {
                type = ((ParameterizedType) type).getRawType();
            } else if (type instanceof TypeVariable) {
                return (Class) ((TypeVariable) type).getBounds()[0];
            } else {
                if (type instanceof WildcardType) {
                    Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                    if (upperBounds.length == 1) {
                        type = upperBounds[0];
                    }
                }
                return Object.class;
            }
        }
        return (Class) type;
    }

    public static String decapitalize(String str) {
        if (str == null || str.length() == 0 || (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0)))) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    public static boolean setAccessible(Class<?> cls, Member member, int i) {
        if (member == null || !setAccessibleEnable) {
            return false;
        }
        Class<? super T> superclass = cls.getSuperclass();
        if ((superclass == null || superclass == Object.class) && (member.getModifiers() & 1) != 0 && (i & 1) != 0) {
            return false;
        }
        try {
            ((AccessibleObject) member).setAccessible(true);
            return true;
        } catch (AccessControlException unused) {
            setAccessibleEnable = false;
            return false;
        }
    }

    public static Field getField(Class<?> cls, String str, Field[] fieldArr) {
        return getField(cls, str, fieldArr, null);
    }

    public static Field getField(Class<?> cls, String str, Field[] fieldArr, Map<Class<?>, Field[]> map) {
        Field field0 = getField0(cls, str, fieldArr, map);
        if (field0 == null) {
            field0 = getField0(cls, "_".concat(String.valueOf(str)), fieldArr, map);
        }
        if (field0 == null) {
            field0 = getField0(cls, "m_".concat(String.valueOf(str)), fieldArr, map);
        }
        if (field0 != null) {
            return field0;
        }
        StringBuilder sb = new StringBuilder("m");
        sb.append(str.substring(0, 1).toUpperCase());
        sb.append(str.substring(1));
        return getField0(cls, sb.toString(), fieldArr, map);
    }

    private static Field getField0(Class<?> cls, String str, Field[] fieldArr, Map<Class<?>, Field[]> map) {
        for (Field field : fieldArr) {
            String name = field.getName();
            if (str.equals(name)) {
                return field;
            }
            if (str.length() > 2) {
                char charAt = str.charAt(0);
                if (charAt >= 'a' && charAt <= 'z') {
                    char charAt2 = str.charAt(1);
                    if (charAt2 >= 'A' && charAt2 <= 'Z' && str.equalsIgnoreCase(name)) {
                        return field;
                    }
                }
            }
        }
        Class<? super T> superclass = cls.getSuperclass();
        Field[] fieldArr2 = null;
        if (superclass == null || superclass == Object.class) {
            return null;
        }
        if (map != null) {
            fieldArr2 = map.get(superclass);
        }
        if (fieldArr2 == null) {
            fieldArr2 = superclass.getDeclaredFields();
            if (map != null) {
                map.put(superclass, fieldArr2);
            }
        }
        return getField(superclass, str, fieldArr2, map);
    }

    public static Type getCollectionItemType(Type type) {
        Type type2;
        if (type instanceof ParameterizedType) {
            type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (type2 instanceof WildcardType) {
                Type[] upperBounds = ((WildcardType) type2).getUpperBounds();
                if (upperBounds.length == 1) {
                    type2 = upperBounds[0];
                }
            }
        } else {
            if (type instanceof Class) {
                Class cls = (Class) type;
                if (!cls.getName().startsWith("java.")) {
                    type2 = getCollectionItemType(cls.getGenericSuperclass());
                }
            }
            type2 = null;
        }
        return type2 == null ? Object.class : type2;
    }

    public static Object defaultValue(Class<?> cls) {
        if (cls == Byte.TYPE) {
            return Byte.valueOf(0);
        }
        if (cls == Short.TYPE) {
            return Short.valueOf(0);
        }
        if (cls == Integer.TYPE) {
            return Integer.valueOf(0);
        }
        if (cls == Long.TYPE) {
            return Long.valueOf(0);
        }
        if (cls == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (cls == Double.TYPE) {
            return Double.valueOf(0.0d);
        }
        if (cls == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (cls == Character.TYPE) {
            return Character.valueOf('0');
        }
        return null;
    }

    public static boolean getArgument(Type[] typeArr, TypeVariable[] typeVariableArr, Type[] typeArr2) {
        if (typeArr2 == null || typeVariableArr.length == 0) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < typeArr.length; i++) {
            ParameterizedType parameterizedType = typeArr[i];
            if (parameterizedType instanceof ParameterizedType) {
                ParameterizedType parameterizedType2 = parameterizedType;
                Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
                if (getArgument(actualTypeArguments, typeVariableArr, typeArr2)) {
                    typeArr[i] = new ParameterizedTypeImpl(actualTypeArguments, parameterizedType2.getOwnerType(), parameterizedType2.getRawType());
                    z = true;
                }
            } else if (parameterizedType instanceof TypeVariable) {
                boolean z2 = z;
                for (int i2 = 0; i2 < typeVariableArr.length; i2++) {
                    if (parameterizedType.equals(typeVariableArr[i2])) {
                        typeArr[i] = typeArr2[i2];
                        z2 = true;
                    }
                }
                z = z2;
            }
        }
        return z;
    }

    public static double parseDouble(String str) {
        int length = str.length();
        if (length > 10) {
            return Double.parseDouble(str);
        }
        long j = 0;
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '-' && i2 == 0) {
                z = true;
            } else if (charAt == '.') {
                if (i != 0) {
                    return Double.parseDouble(str);
                }
                i = (length - i2) - 1;
            } else if (charAt < '0' || charAt > '9') {
                return Double.parseDouble(str);
            } else {
                j = (j * 10) + ((long) (charAt - '0'));
            }
        }
        if (z) {
            j = -j;
        }
        switch (i) {
            case 0:
                return (double) j;
            case 1:
                return ((double) j) / 10.0d;
            case 2:
                return ((double) j) / 100.0d;
            case 3:
                return ((double) j) / 1000.0d;
            case 4:
                return ((double) j) / 10000.0d;
            case 5:
                return ((double) j) / 100000.0d;
            case 6:
                return ((double) j) / 1000000.0d;
            case 7:
                return ((double) j) / 1.0E7d;
            case 8:
                return ((double) j) / 1.0E8d;
            case 9:
                return ((double) j) / 1.0E9d;
            default:
                return Double.parseDouble(str);
        }
    }

    public static float parseFloat(String str) {
        int length = str.length();
        if (length >= 10) {
            return Float.parseFloat(str);
        }
        long j = 0;
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '-' && i2 == 0) {
                z = true;
            } else if (charAt == '.') {
                if (i != 0) {
                    return Float.parseFloat(str);
                }
                i = (length - i2) - 1;
            } else if (charAt < '0' || charAt > '9') {
                return Float.parseFloat(str);
            } else {
                j = (j * 10) + ((long) (charAt - '0'));
            }
        }
        if (z) {
            j = -j;
        }
        switch (i) {
            case 0:
                return (float) j;
            case 1:
                return ((float) j) / 10.0f;
            case 2:
                return ((float) j) / 100.0f;
            case 3:
                return ((float) j) / 1000.0f;
            case 4:
                return ((float) j) / 10000.0f;
            case 5:
                return ((float) j) / 100000.0f;
            case 6:
                return ((float) j) / 1000000.0f;
            case 7:
                return ((float) j) / 1.0E7f;
            case 8:
                return ((float) j) / 1.0E8f;
            case 9:
                return ((float) j) / 1.0E9f;
            default:
                return Float.parseFloat(str);
        }
    }

    public static long fnv_64_lower(String str) {
        if (str == null) {
            return 0;
        }
        long j = -3750763034362895579L;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!(charAt == '_' || charAt == '-')) {
                if (charAt >= 'A' && charAt <= 'Z') {
                    charAt = (char) (charAt + ' ');
                }
                j = (j ^ ((long) charAt)) * 1099511628211L;
            }
        }
        return j;
    }

    public static void addMapping(String str, Class<?> cls) {
        mappings.put(str, cls);
    }

    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r0v6, types: [java.lang.Class] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Type checkPrimitiveArray(java.lang.reflect.GenericArrayType r3) {
        /*
            java.lang.reflect.Type r0 = r3.getGenericComponentType()
            java.lang.String r1 = "["
        L_0x0006:
            boolean r2 = r0 instanceof java.lang.reflect.GenericArrayType
            if (r2 == 0) goto L_0x0020
            java.lang.reflect.GenericArrayType r0 = (java.lang.reflect.GenericArrayType) r0
            java.lang.reflect.Type r0 = r0.getGenericComponentType()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            goto L_0x0006
        L_0x0020:
            boolean r2 = r0 instanceof java.lang.Class
            if (r2 == 0) goto L_0x00ff
            java.lang.Class r0 = (java.lang.Class) r0
            boolean r2 = r0.isPrimitive()
            if (r2 == 0) goto L_0x00ff
            java.lang.Class r2 = java.lang.Boolean.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x0047
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "Z"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x0047:
            java.lang.Class r2 = java.lang.Character.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x0062
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "C"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x0062:
            java.lang.Class r2 = java.lang.Byte.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x007d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "B"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x007d:
            java.lang.Class r2 = java.lang.Short.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x0097
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "S"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x0097:
            java.lang.Class r2 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x00b1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "I"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x00b1:
            java.lang.Class r2 = java.lang.Long.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x00cb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "J"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x00cb:
            java.lang.Class r2 = java.lang.Float.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x00e5
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "F"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            goto L_0x0100
        L_0x00e5:
            java.lang.Class r2 = java.lang.Double.TYPE     // Catch:{ ClassNotFoundException -> 0x00ff }
            if (r0 != r2) goto L_0x00ff
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.<init>()     // Catch:{ ClassNotFoundException -> 0x00ff }
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r1 = "D"
            r0.append(r1)     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.String r0 = r0.toString()     // Catch:{ ClassNotFoundException -> 0x00ff }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x00ff }
            r3 = r0
        L_0x00ff:
            r0 = r3
        L_0x0100:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.checkPrimitiveArray(java.lang.reflect.GenericArrayType):java.lang.reflect.Type");
    }
}
