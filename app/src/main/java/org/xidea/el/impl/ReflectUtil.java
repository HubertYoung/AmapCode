package org.xidea.el.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xidea.el.impl.GenericFinder.Default;

public abstract class ReflectUtil {
    private static final Map<Class<?>, Map<String, Method>> a = new HashMap();
    private static final Map<Class<?>, Map<String, Method>> b = new HashMap();
    private static final Map<Class<?>, Map<String, Type[]>> c = new HashMap();
    private static final Map<Class<?>, Map<String, Field>> d = new HashMap();
    private static final Map<Class<?>, Constructor<?>> e = new HashMap();
    private static final Map<Class<? extends Enum<?>>, Enum<?>[]> f = new HashMap();
    private static Object g = new Object();

    public static Map<String, Method> a(Class<?> cls) {
        Map<String, Method> map = a.get(cls);
        if (map != null) {
            return map;
        }
        h(cls);
        return a.get(cls);
    }

    private static Map<String, Method> f(Class<?> cls) {
        Map<String, Method> map = b.get(cls);
        if (map != null) {
            return map;
        }
        h(cls);
        return b.get(cls);
    }

    private static Map<String, Type[]> g(Class<?> cls) {
        Map<String, Type[]> map = c.get(cls);
        if (map != null) {
            return map;
        }
        h(cls);
        return c.get(cls);
    }

    public static Map<String, Field> b(Class<?> cls) {
        Map<String, Field> map = d.get(cls);
        if (map != null) {
            return map;
        }
        h(cls);
        return d.get(cls);
    }

    public static Set<String> c(Class<?> cls) {
        return g(cls).keySet();
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0020 */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003c A[Catch:{ Exception -> 0x012c }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0066 A[Catch:{ Exception -> 0x012c }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d4 A[Catch:{ Exception -> 0x012c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void h(java.lang.Class<?> r18) {
        /*
            r1 = r18
            java.lang.Object r2 = g
            monitor-enter(r2)
            r3 = 0
            r4 = 1
            java.lang.Class[] r5 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0020 }
            java.lang.reflect.Constructor r5 = r1.getDeclaredConstructor(r5)     // Catch:{ Exception -> 0x0020 }
            boolean r6 = r5.isAccessible()     // Catch:{ Exception -> 0x0020 }
            if (r6 != 0) goto L_0x0016
            r5.setAccessible(r4)     // Catch:{ Exception -> 0x0020 }
        L_0x0016:
            java.util.Map<java.lang.Class<?>, java.lang.reflect.Constructor<?>> r6 = e     // Catch:{ Exception -> 0x0020 }
            r6.put(r1, r5)     // Catch:{ Exception -> 0x0020 }
            goto L_0x0020
        L_0x001c:
            r0 = move-exception
            r1 = r0
            goto L_0x017a
        L_0x0020:
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ all -> 0x001c }
            r5.<init>()     // Catch:{ all -> 0x001c }
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ all -> 0x001c }
            r6.<init>()     // Catch:{ all -> 0x001c }
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ all -> 0x001c }
            r7.<init>()     // Catch:{ all -> 0x001c }
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ all -> 0x001c }
            r8.<init>()     // Catch:{ all -> 0x001c }
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            boolean r9 = r1.equals(r9)     // Catch:{ Exception -> 0x012c }
            if (r9 != 0) goto L_0x005e
            java.lang.Class r9 = r18.getSuperclass()     // Catch:{ Exception -> 0x012c }
            if (r9 == 0) goto L_0x005e
            java.util.Map r10 = a(r9)     // Catch:{ Exception -> 0x012c }
            r5.putAll(r10)     // Catch:{ Exception -> 0x012c }
            java.util.Map r10 = f(r9)     // Catch:{ Exception -> 0x012c }
            r6.putAll(r10)     // Catch:{ Exception -> 0x012c }
            java.util.Map r10 = g(r9)     // Catch:{ Exception -> 0x012c }
            r7.putAll(r10)     // Catch:{ Exception -> 0x012c }
            java.util.Map r9 = b(r9)     // Catch:{ Exception -> 0x012c }
            r8.putAll(r9)     // Catch:{ Exception -> 0x012c }
        L_0x005e:
            java.lang.reflect.Method[] r9 = r18.getDeclaredMethods()     // Catch:{ Exception -> 0x012c }
            int r10 = r9.length     // Catch:{ Exception -> 0x012c }
            r11 = 0
        L_0x0064:
            if (r11 >= r10) goto L_0x00c8
            r13 = r9[r11]     // Catch:{ Exception -> 0x012c }
            int r14 = r13.getModifiers()     // Catch:{ Exception -> 0x012c }
            r14 = r14 & r4
            if (r14 <= 0) goto L_0x00c3
            java.lang.Class r14 = r13.getReturnType()     // Catch:{ Exception -> 0x012c }
            java.lang.Class[] r15 = r13.getParameterTypes()     // Catch:{ Exception -> 0x012c }
            java.lang.String r3 = r13.getName()     // Catch:{ Exception -> 0x012c }
            java.lang.Class r12 = java.lang.Void.TYPE     // Catch:{ Exception -> 0x012c }
            r4 = 3
            if (r14 != r12) goto L_0x0094
            int r12 = r15.length     // Catch:{ Exception -> 0x012c }
            r14 = 1
            if (r12 != r14) goto L_0x00c3
            java.lang.String r12 = "set"
            boolean r12 = r3.startsWith(r12)     // Catch:{ Exception -> 0x012c }
            if (r12 == 0) goto L_0x00c3
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Exception -> 0x012c }
            a(r6, r7, r13, r3)     // Catch:{ Exception -> 0x012c }
            goto L_0x00c3
        L_0x0094:
            int r12 = r15.length     // Catch:{ Exception -> 0x012c }
            if (r12 != 0) goto L_0x00c3
            java.lang.String r12 = "get"
            boolean r12 = r3.startsWith(r12)     // Catch:{ Exception -> 0x012c }
            if (r12 == 0) goto L_0x00af
            java.lang.String r12 = "getClass"
            boolean r12 = r3.equals(r12)     // Catch:{ Exception -> 0x012c }
            if (r12 != 0) goto L_0x00af
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Exception -> 0x012c }
            a(r5, r7, r13, r3)     // Catch:{ Exception -> 0x012c }
            goto L_0x00c3
        L_0x00af:
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x012c }
            if (r14 != r4) goto L_0x00c3
            java.lang.String r4 = "is"
            boolean r4 = r3.startsWith(r4)     // Catch:{ Exception -> 0x012c }
            if (r4 == 0) goto L_0x00c3
            r4 = 2
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Exception -> 0x012c }
            a(r5, r7, r13, r3)     // Catch:{ Exception -> 0x012c }
        L_0x00c3:
            int r11 = r11 + 1
            r3 = 0
            r4 = 1
            goto L_0x0064
        L_0x00c8:
            boolean r3 = r18.isMemberClass()     // Catch:{ Exception -> 0x012c }
            java.lang.reflect.Field[] r4 = r18.getDeclaredFields()     // Catch:{ Exception -> 0x012c }
            int r9 = r4.length     // Catch:{ Exception -> 0x012c }
            r10 = 0
        L_0x00d2:
            if (r10 >= r9) goto L_0x0104
            r11 = r4[r10]     // Catch:{ Exception -> 0x012c }
            r12 = 1
            r11.setAccessible(r12)     // Catch:{ Exception -> 0x012c }
            java.lang.String r13 = r11.getName()     // Catch:{ Exception -> 0x012c }
            if (r3 != 0) goto L_0x00ed
            int r14 = r11.getModifiers()     // Catch:{ Exception -> 0x012c }
            r14 = r14 & r12
            if (r14 <= 0) goto L_0x00e8
            goto L_0x00ed
        L_0x00e8:
            r12 = 2
            r15 = 1
            r16 = 0
            goto L_0x00fe
        L_0x00ed:
            r12 = 2
            java.lang.reflect.Type[] r14 = new java.lang.reflect.Type[r12]     // Catch:{ Exception -> 0x012c }
            java.lang.reflect.Type r15 = org.xidea.el.impl.GenericFinder.Default.a(r11)     // Catch:{ Exception -> 0x012c }
            r16 = 0
            r14[r16] = r15     // Catch:{ Exception -> 0x012c }
            r15 = 1
            r14[r15] = r1     // Catch:{ Exception -> 0x012c }
            r7.put(r13, r14)     // Catch:{ Exception -> 0x012c }
        L_0x00fe:
            r8.put(r13, r11)     // Catch:{ Exception -> 0x012c }
            int r10 = r10 + 1
            goto L_0x00d2
        L_0x0104:
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r3 = a     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r5)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r3 = b     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r6)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Type[]>> r3 = c     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r7)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Field>> r3 = d     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r8)     // Catch:{ all -> 0x001c }
        L_0x0125:
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            goto L_0x0153
        L_0x0129:
            r0 = move-exception
            r3 = r0
            goto L_0x0155
        L_0x012c:
            r0 = move-exception
            r3 = r0
            r3.printStackTrace()     // Catch:{ all -> 0x0129 }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r3 = a     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r5)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r3 = b     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r6)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Type[]>> r3 = c     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r7)     // Catch:{ all -> 0x001c }
            r3.put(r1, r4)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Field>> r3 = d     // Catch:{ all -> 0x001c }
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r8)     // Catch:{ all -> 0x001c }
            goto L_0x0125
        L_0x0153:
            monitor-exit(r2)     // Catch:{ all -> 0x001c }
            return
        L_0x0155:
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r4 = a     // Catch:{ all -> 0x001c }
            java.util.Map r5 = java.util.Collections.unmodifiableMap(r5)     // Catch:{ all -> 0x001c }
            r4.put(r1, r5)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Method>> r4 = b     // Catch:{ all -> 0x001c }
            java.util.Map r5 = java.util.Collections.unmodifiableMap(r6)     // Catch:{ all -> 0x001c }
            r4.put(r1, r5)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Type[]>> r4 = c     // Catch:{ all -> 0x001c }
            java.util.Map r5 = java.util.Collections.unmodifiableMap(r7)     // Catch:{ all -> 0x001c }
            r4.put(r1, r5)     // Catch:{ all -> 0x001c }
            java.util.Map<java.lang.Class<?>, java.util.Map<java.lang.String, java.lang.reflect.Field>> r4 = d     // Catch:{ all -> 0x001c }
            java.util.Map r5 = java.util.Collections.unmodifiableMap(r8)     // Catch:{ all -> 0x001c }
            r4.put(r1, r5)     // Catch:{ all -> 0x001c }
            throw r3     // Catch:{ all -> 0x001c }
        L_0x017a:
            monitor-exit(r2)     // Catch:{ all -> 0x001c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.impl.ReflectUtil.h(java.lang.Class):void");
    }

    private static void a(Map<String, Method> map, Map<String, Type[]> map2, Method method, String str) {
        if (str.length() > 0) {
            char charAt = str.charAt(0);
            if (Character.isUpperCase(charAt)) {
                StringBuilder sb = new StringBuilder();
                sb.append(Character.toLowerCase(charAt));
                sb.append(str.substring(1));
                String sb2 = sb.toString();
                method.setAccessible(true);
                map.put(sb2, method);
                Type a2 = Default.a(method);
                if (a2 == Void.TYPE) {
                    a2 = method.getParameterTypes()[0];
                }
                map2.get(sb2);
                map2.put(sb2, new Type[]{a2, method.getDeclaringClass()});
            }
        }
    }

    private static int a(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return Integer.parseInt(String.valueOf(obj));
    }

    private static Class<? extends Object> b(Type type) {
        Class cls;
        Type type2;
        if (type instanceof ParameterizedType) {
            cls = (Class) ((ParameterizedType) type).getRawType();
        } else {
            cls = (Class) type;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            type2 = Default.a(type, Collection.class, 0);
        } else if (Map.class.isAssignableFrom(cls)) {
            type2 = Default.a(type, Map.class, 1);
        } else {
            type2 = null;
        }
        if (type2 != null) {
            return a(type2);
        }
        return Object.class;
    }

    public static Type a(Type type, Class<?> cls) {
        return Default.a(type, cls, 0);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:11|12|(2:14|15)|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r1 = r4.getDeclaredConstructor(new java.lang.Class[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002f, code lost:
        if (r1.isAccessible() == false) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        r1.setAccessible(true);
        e.put(r4, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        return r1.newInstance(new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0025 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T d(java.lang.Class<T> r4) {
        /*
            r0 = 0
            java.util.Map<java.lang.Class<?>, java.lang.reflect.Constructor<?>> r1 = e     // Catch:{ Exception -> 0x0042 }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ Exception -> 0x0042 }
            java.lang.reflect.Constructor r1 = (java.lang.reflect.Constructor) r1     // Catch:{ Exception -> 0x0042 }
            r2 = 0
            if (r1 == 0) goto L_0x0020
            h(r4)     // Catch:{ Exception -> 0x0042 }
            java.util.Map<java.lang.Class<?>, java.lang.reflect.Constructor<?>> r1 = e     // Catch:{ Exception -> 0x0042 }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ Exception -> 0x0042 }
            java.lang.reflect.Constructor r1 = (java.lang.reflect.Constructor) r1     // Catch:{ Exception -> 0x0042 }
            if (r1 == 0) goto L_0x0020
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0042 }
            java.lang.Object r4 = r1.newInstance(r4)     // Catch:{ Exception -> 0x0042 }
            return r4
        L_0x0020:
            java.lang.Object r1 = r4.newInstance()     // Catch:{ InstantiationException -> 0x0025 }
            return r1
        L_0x0025:
            java.lang.Class[] r1 = new java.lang.Class[r2]     // Catch:{ NoSuchMethodException -> 0x0041 }
            java.lang.reflect.Constructor r1 = r4.getDeclaredConstructor(r1)     // Catch:{ NoSuchMethodException -> 0x0041 }
            boolean r3 = r1.isAccessible()     // Catch:{ NoSuchMethodException -> 0x0041 }
            if (r3 != 0) goto L_0x0041
            r3 = 1
            r1.setAccessible(r3)     // Catch:{ NoSuchMethodException -> 0x0041 }
            java.util.Map<java.lang.Class<?>, java.lang.reflect.Constructor<?>> r3 = e     // Catch:{ NoSuchMethodException -> 0x0041 }
            r3.put(r4, r1)     // Catch:{ NoSuchMethodException -> 0x0041 }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ NoSuchMethodException -> 0x0041 }
            java.lang.Object r4 = r1.newInstance(r4)     // Catch:{ NoSuchMethodException -> 0x0041 }
            return r4
        L_0x0041:
            return r0
        L_0x0042:
            r4 = move-exception
            r4.printStackTrace()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.impl.ReflectUtil.d(java.lang.Class):java.lang.Object");
    }

    public static Class<? extends Object> a(Type type) {
        while (!(type instanceof Class)) {
            if (type instanceof ParameterizedType) {
                type = ((ParameterizedType) type).getRawType();
            } else if (!(type instanceof WildcardType)) {
                return null;
            } else {
                type = ((WildcardType) type).getUpperBounds()[0];
            }
        }
        return (Class) type;
    }

    public static Enum a(Object obj, Class cls) {
        if (obj instanceof String) {
            return Enum.valueOf(cls, (String) obj);
        }
        if (obj instanceof Number) {
            Enum[] enumArr = (Enum[]) f.get(cls);
            if (enumArr == null) {
                try {
                    Method method = cls.getMethod("values", new Class[0]);
                    enumArr = (Enum[]) method.invoke(null, new Object[0]);
                    f.put(cls, enumArr);
                } catch (Exception unused) {
                }
            }
            return enumArr[((Number) obj).intValue()];
        }
        return null;
    }

    public static Class<?> a(Type type, Object obj) {
        return a(b(type, obj));
    }

    public static Type b(Type type, Object obj) {
        Class<? extends Object> a2 = a(type);
        if (a2 != null) {
            if (Collection.class.isAssignableFrom(a2)) {
                return b(type);
            }
            if (Map.class.isAssignableFrom(a2)) {
                return b(type);
            }
            if (!a2.isArray()) {
                Type[] typeArr = g(a2).get(String.valueOf(obj));
                if (typeArr != null) {
                    return Default.a(type, (Class) typeArr[1], typeArr[0]);
                }
            } else if ("length".equals(obj)) {
                return Integer.TYPE;
            } else {
                if (Number.class.isInstance(obj)) {
                    return a2.getComponentType();
                }
            }
        }
        return null;
    }

    public static Object a(Object obj, Object obj2) {
        Field field;
        Class<?> cls = obj.getClass();
        if (obj != null) {
            try {
                if (obj instanceof Map) {
                    return ((Map) obj).get(obj2);
                }
                if (obj2 instanceof String) {
                    Method method = a(cls).get((String) obj2);
                    if (method != null) {
                        return method.invoke(obj, new Object[0]);
                    }
                    if (obj instanceof Class) {
                        field = b((Class) obj).get(obj2);
                    } else {
                        field = b(cls).get(obj2);
                    }
                    if (field != null) {
                        return field.get(obj);
                    }
                    if ("length".equals(obj2)) {
                        if (cls.isArray()) {
                            return Integer.valueOf(Array.getLength(obj));
                        }
                        if (obj instanceof Collection) {
                            return Integer.valueOf(((Collection) obj).size());
                        }
                        if (obj instanceof String) {
                            return Integer.valueOf(((String) obj).length());
                        }
                    }
                    if (obj instanceof Map) {
                        return ((Map) obj).get(obj2);
                    }
                    if (cls.isArray()) {
                        return Integer.valueOf(Array.getLength(obj));
                    }
                    return Array.get(obj, a(obj2));
                } else if (obj instanceof List) {
                    return ((List) obj).get(a(obj2));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static void a(Object obj, Object obj2, Object obj3) {
        if (obj != null) {
            try {
                Class<?> cls = obj.getClass();
                if (cls.isArray()) {
                    Array.set(obj, a(obj2), obj3);
                } else if (obj instanceof List) {
                    ((List) obj).set(a(obj2), obj3);
                }
                if (obj instanceof Map) {
                    ((Map) obj).put(obj2, obj3);
                }
                String valueOf = String.valueOf(obj2);
                Method method = f(cls).get(valueOf);
                if (method != null) {
                    if (obj3 != null) {
                        obj3 = b(obj3, method.getParameterTypes()[0]);
                    }
                    method.invoke(obj, new Object[]{obj3});
                    return;
                }
                Field field = (Field) d.get(cls).get(valueOf);
                if (!(obj3 == null || field == null)) {
                    field.set(obj, b(obj3, field.getType()));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static Object b(Object obj, Class<?> cls) {
        if (cls.isInstance(obj)) {
            return obj;
        }
        Class<? extends Object> e2 = e(cls);
        return Number.class.isAssignableFrom(e2) ? a((Number) obj, e2) : obj;
    }

    public static Number a(Number number, Class<? extends Object> cls) {
        while (cls != Long.class) {
            if (cls == Integer.class) {
                return Integer.valueOf(number.intValue());
            }
            if (cls == Short.class) {
                return Short.valueOf(number.shortValue());
            }
            if (cls == Byte.class) {
                return Byte.valueOf(number.byteValue());
            }
            if (cls == Double.class) {
                return Double.valueOf(number.doubleValue());
            }
            if (cls == Float.class) {
                return Float.valueOf(number.floatValue());
            }
            Class<? extends Object> e2 = e(cls);
            if (e2 == cls) {
                return null;
            }
            cls = e2;
        }
        return Long.valueOf(number.longValue());
    }

    public static final Class<? extends Object> e(Class<? extends Object> cls) {
        if (cls.isPrimitive()) {
            if (Byte.TYPE == cls) {
                return Byte.class;
            }
            if (Short.TYPE == cls) {
                return Short.class;
            }
            if (Integer.TYPE == cls) {
                return Integer.class;
            }
            if (Long.TYPE == cls) {
                return Long.class;
            }
            if (Float.TYPE == cls) {
                return Float.class;
            }
            if (Double.TYPE == cls) {
                return Double.class;
            }
            if (Character.TYPE == cls) {
                return Character.class;
            }
            if (Boolean.TYPE == cls) {
                return Boolean.class;
            }
        }
        return cls;
    }
}
