package org.xidea.el.json;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xidea.el.impl.ReflectUtil;
import org.xidea.el.json.JSONDecoder.TypeTransformer;

class JSONTransformer {
    static Map<Class<?>, Class<?>> a;
    private static final Pattern c = Pattern.compile("^(\\d{4})(?:\\-(\\d{1,2})(?:\\-(\\d{1,2})(?:T(\\d{2})\\:(\\d{2})(?:\\:(\\d{2}(?:\\.\\d+)?))?(Z|[+\\-]\\d{2}\\:?\\d{2})?)?)?)?$");
    private static final Object d = new Object();
    Map<Type, TypeTransformer<? extends Object>> b = new HashMap();
    private ClassLoader e = JSONTransformer.class.getClassLoader();

    static final class TransformException extends RuntimeException {
        private static final long serialVersionUID = -7860716983754523610L;
        String a;
        Type b;
        Object c;

        public TransformException(String str, Type type, Object obj) {
            this.a = str;
            this.b = type;
            this.c = obj;
        }
    }

    JSONTransformer() {
    }

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(Map.class, HashMap.class);
        a.put(List.class, ArrayList.class);
        a.put(Collection.class, ArrayList.class);
        a.put(Set.class, HashSet.class);
    }

    public Date a(String str) {
        Matcher matcher = c.matcher(str);
        if (!matcher.find()) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.clear();
        String group = matcher.group(7);
        if (group != null) {
            instance.setTimeZone(TimeZone.getTimeZone("GMT".concat(String.valueOf(group))));
        }
        instance.set(1, Integer.parseInt(matcher.group(1)));
        String group2 = matcher.group(2);
        if (group2 != null) {
            instance.set(2, Integer.parseInt(group2) - 1);
            String group3 = matcher.group(3);
            if (group3 != null) {
                instance.set(5, Integer.parseInt(group3));
                String group4 = matcher.group(4);
                if (group4 != null) {
                    String group5 = matcher.group(5);
                    instance.set(10, Integer.parseInt(group4));
                    instance.set(12, Integer.parseInt(group5));
                    String group6 = matcher.group(6);
                    if (group6 != null) {
                        if (group6.length() > 2) {
                            float parseFloat = Float.parseFloat(group6);
                            instance.set(13, (int) parseFloat);
                            instance.set(14, ((int) (parseFloat * 1000.0f)) % 1000);
                        } else {
                            instance.set(13, Integer.parseInt(group6));
                        }
                    }
                }
            }
        }
        return instance.getTime();
    }

    public Date b(String str) {
        Date a2 = a(str);
        if (a2 != null) {
            return a2;
        }
        throw new TransformException("日期解析失败", Date.class, str);
    }

    private Object a(Map map, Class cls) throws InstantiationException, IllegalAccessException {
        String str = (String) map.get("class");
        if (str != null) {
            try {
                cls = c(str);
            } catch (ClassNotFoundException unused) {
            }
        }
        TypeTransformer typeTransformer = this.b.get(cls);
        if (typeTransformer != null) {
            return typeTransformer.a();
        }
        if (cls.isInterface()) {
            Class cls2 = a.get(cls);
            if (cls2 == null) {
                StringBuilder sb = new StringBuilder("接口找不到默认实现类");
                sb.append(a);
                throw new TransformException(sb.toString(), cls, map);
            }
            cls = cls2;
        }
        Object d2 = ReflectUtil.d(cls);
        if (d2 != null) {
            return d2;
        }
        if (cls == null || !cls.isMemberClass() || (cls.getModifiers() & 8) != 0) {
            throw new TransformException("JavaBean 创建异常", cls, map);
        }
        throw new TransformException("请尽量避免用非静态的内部类存储数据", cls, map);
    }

    public TypeTransformer<? extends Object> a(TypeTransformer<? extends Object> typeTransformer) {
        return this.b.put(ReflectUtil.a((Type) typeTransformer.getClass(), TypeTransformer.class), typeTransformer);
    }

    /* access modifiers changed from: protected */
    public Object a(Object obj, Type type) throws Exception {
        Class<? extends Object> cls;
        Object obj2;
        Object date;
        Number number;
        boolean z = type instanceof Class;
        if (z && ((Class) type).isInstance(obj)) {
            return obj;
        }
        if (z) {
            cls = (Class) type;
            boolean isPrimitive = cls.isPrimitive();
            Class<? extends Object> e2 = ReflectUtil.e(cls);
            boolean z2 = false;
            if (obj == null) {
                if (!isPrimitive) {
                    obj2 = null;
                } else if (e2 == Boolean.class) {
                    obj2 = Boolean.FALSE;
                } else if (e2 == Character.class) {
                    obj2 = Character.valueOf(0);
                } else if (Number.class.isAssignableFrom(e2)) {
                    obj2 = ReflectUtil.a((Number) Integer.valueOf(0), e2);
                } else {
                    throw new IllegalArgumentException("class is not primitive type:".concat(String.valueOf(e2)));
                }
            } else if (String.class == e2) {
                obj2 = obj.toString();
            } else {
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (Date.class == e2) {
                        obj2 = b(str);
                    } else if (java.sql.Date.class == e2) {
                        date = new java.sql.Date(b(str).getTime());
                    } else if (e2 == Class.class) {
                        obj2 = c(str);
                    } else if (Boolean.class == e2) {
                        obj2 = Boolean.valueOf(str);
                    } else {
                        if (Number.class.isAssignableFrom(e2)) {
                            if (Long.class == e2) {
                                obj2 = Long.valueOf(Long.parseLong(str));
                            } else {
                                obj2 = ReflectUtil.a((Number) Double.valueOf(Double.parseDouble(String.valueOf(obj))), e2);
                            }
                        }
                        obj2 = d;
                    }
                } else if (Number.class.isAssignableFrom(e2)) {
                    if (obj instanceof Number) {
                        number = (Number) obj;
                    } else {
                        number = Double.valueOf(Double.parseDouble(String.valueOf(obj)));
                    }
                    obj2 = ReflectUtil.a(number, e2);
                } else if (obj instanceof Boolean) {
                    if (Boolean.class == e2) {
                        obj2 = obj;
                    }
                    obj2 = d;
                } else {
                    if (obj instanceof Number) {
                        Number number2 = (Number) obj;
                        if (Date.class == e2) {
                            date = new Date(number2.longValue());
                        } else if (java.sql.Date.class == e2) {
                            date = new java.sql.Date(number2.longValue());
                        } else if (Boolean.class == e2) {
                            if (number2.floatValue() != 0.0f) {
                                z2 = true;
                            }
                            obj2 = Boolean.valueOf(z2);
                        }
                    }
                    obj2 = d;
                }
                obj2 = date;
            }
            if (obj2 != d) {
                return obj2;
            }
        } else {
            cls = ReflectUtil.a(type);
        }
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            return b(obj, type, cls);
        }
        a(cls);
        this.b.get(type);
        if (obj instanceof Map) {
            return a(obj, type, cls);
        }
        return a(obj, cls);
    }

    private final Class<?> c(String str) throws ClassNotFoundException {
        return Class.forName(str, true, this.e);
    }

    private static void a(Class<?> cls) throws ClassNotFoundException {
        if (!cls.isPrimitive()) {
            Class.forName(cls.getName(), true, cls.getClassLoader());
        }
    }

    public Object b(Object obj, Type type) {
        try {
            return a(obj, type);
        } catch (Exception | TransformException unused) {
            return null;
        }
    }

    private Object a(Object obj, Type type, Class<?> cls) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        a(cls);
        Map map = (Map) obj;
        Object a2 = a(map, (Class) cls);
        for (Object next : map.keySet()) {
            Type b2 = ReflectUtil.b(type, next);
            Object obj2 = map.get(next);
            if (b2 != null) {
                try {
                    obj2 = a(obj2, b2);
                } catch (Exception | TransformException unused) {
                }
            }
            ReflectUtil.a(a2, next, obj2);
        }
        return a2;
    }

    private Object b(Object obj, Type type, Class<?> cls) throws ClassNotFoundException {
        int i = 0;
        if (cls.isArray()) {
            List list = (List) obj;
            Object newInstance = Array.newInstance(cls.getComponentType(), list.size());
            int size = list.size();
            while (i < size) {
                Array.set(newInstance, i, b(list.get(i), cls.getComponentType()));
                i++;
            }
            return newInstance;
        }
        if (!List.class.isAssignableFrom(cls) || !(type instanceof ParameterizedType)) {
            a(cls);
            this.b.get(type);
            if (!cls.isInstance(obj)) {
                throw new TransformException("List类型转换失败", cls, obj);
            }
        } else {
            List list2 = (List) obj;
            Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            int size2 = list2.size();
            while (i < size2) {
                list2.set(i, b(list2.get(i), ReflectUtil.a(type2)));
                i++;
            }
        }
        return obj;
    }

    private static Object a(Object obj, Class<?> cls) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (Enum.class.isAssignableFrom(cls)) {
            return ReflectUtil.a(obj, (Class) cls);
        }
        if (Character.class != cls) {
            try {
                return cls.getConstructor(new Class[]{obj.getClass()}).newInstance(new Object[]{obj});
            } catch (Exception unused) {
                throw new TransformException("不支持数据类型", cls, obj);
            }
        } else if (obj instanceof CharSequence) {
            return Character.valueOf(((CharSequence) obj).charAt(0));
        } else {
            if (obj instanceof Number) {
                return Character.valueOf((char) ((Number) obj).intValue());
            }
            throw new TransformException("Char类型转换失败(只支持CharSequence和Number 到Char的转换)", cls, obj);
        }
    }

    public void a(ClassLoader classLoader) {
        this.e = classLoader;
    }
}
