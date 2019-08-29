package com.autonavi.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ReflectUtil {
    public static boolean isAssignable(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type == null || type2 == null) {
            return false;
        }
        if (type.equals(type2)) {
            return true;
        }
        if (!(type instanceof ParameterizedType) || !(type2 instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        ParameterizedType parameterizedType2 = (ParameterizedType) type2;
        if (!parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
            return false;
        }
        Type ownerType = parameterizedType.getOwnerType();
        Type ownerType2 = parameterizedType2.getOwnerType();
        if (ownerType != null) {
            if (!ownerType.equals(ownerType2)) {
                return false;
            }
        } else if (ownerType2 != null) {
            return false;
        }
        return true;
    }

    public static Class<?> getClass(Type type) {
        while (type.getClass() != Class.class) {
            if (!(type instanceof ParameterizedType)) {
                return Object.class;
            }
            type = ((ParameterizedType) type).getRawType();
        }
        return (Class) type;
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.reflect.TypeVariable, java.lang.reflect.TypeVariable<?>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Type getInheritGenericType(java.lang.Class<?> r5, java.lang.reflect.TypeVariable<?> r6) {
        /*
            java.lang.reflect.GenericDeclaration r0 = r6.getGenericDeclaration()
        L_0x0004:
            java.lang.reflect.Type r5 = r5.getGenericSuperclass()
            r1 = 0
            if (r5 != 0) goto L_0x000c
            return r1
        L_0x000c:
            boolean r2 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r2 == 0) goto L_0x003c
            r2 = r5
            java.lang.reflect.ParameterizedType r2 = (java.lang.reflect.ParameterizedType) r2
            java.lang.reflect.Type r3 = r2.getRawType()
            if (r3 != r0) goto L_0x003c
            java.lang.reflect.TypeVariable[] r5 = r0.getTypeParameters()
            java.lang.reflect.Type[] r0 = r2.getActualTypeArguments()
            java.lang.String r6 = r6.getName()
            r2 = 0
            int r3 = r5.length
        L_0x0027:
            if (r2 >= r3) goto L_0x003b
            r4 = r5[r2]
            java.lang.String r4 = r4.getName()
            boolean r4 = r6.equals(r4)
            if (r4 == 0) goto L_0x0038
            r5 = r0[r2]
            return r5
        L_0x0038:
            int r2 = r2 + 1
            goto L_0x0027
        L_0x003b:
            return r1
        L_0x003c:
            java.lang.Class r2 = getClass(r5)
            if (r5 != 0) goto L_0x0043
            return r1
        L_0x0043:
            r5 = r2
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.reflect.ReflectUtil.getInheritGenericType(java.lang.Class, java.lang.reflect.TypeVariable):java.lang.reflect.Type");
    }

    public static boolean isGenericParamType(Type type) {
        while (type != null) {
            if (type instanceof ParameterizedType) {
                return true;
            }
            if (!(type instanceof Class)) {
                return false;
            }
            type = ((Class) type).getGenericSuperclass();
        }
        return false;
    }

    public static Type getGenericParamType(Type type) {
        while (!(type instanceof ParameterizedType) && (type instanceof Class)) {
            type = ((Class) type).getGenericSuperclass();
        }
        return type;
    }

    public static GenericInfo getGenericInfo(String str, Class<?> cls, ParameterizedType parameterizedType) {
        GenericInfo genericInfo = new GenericInfo();
        TypeVariable[] typeParameters = cls.getTypeParameters();
        int length = typeParameters.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            } else if (str.equals(typeParameters[i].getName())) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1 && i < parameterizedType.getActualTypeArguments().length) {
            Type type = parameterizedType.getActualTypeArguments()[i];
            if (type instanceof Class) {
                genericInfo.rawType = (Class) type;
            } else if (type instanceof ParameterizedType) {
                genericInfo.rawType = (Class) ((ParameterizedType) type).getRawType();
                genericInfo.parameterizedType = type;
            }
        }
        return genericInfo;
    }

    public static <T> Constructor<T> getConstructor(Class<T> cls, Class<?>... clsArr) {
        try {
            return cls.getConstructor(clsArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... objArr) {
        try {
            return constructor.newInstance(objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object get(Object obj, big big) {
        try {
            return big.a(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void set(Object obj, big big, Object obj2) {
        try {
            big.a(obj, obj2);
        } catch (Exception unused) {
        }
    }

    public static Object get(Object obj, Field field) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void set(Object obj, Field field, Object obj2) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(obj, obj2);
        } catch (Exception unused) {
        }
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(obj, objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Class<?> getParamRawType(Class<?> cls, Type type, int i) {
        if (cls == null && type == null) {
            return Object.class;
        }
        if (type == null) {
            type = cls.getGenericSuperclass();
        }
        if (!(type instanceof ParameterizedType)) {
            type = getGenericParamType(type);
        }
        if (type instanceof ParameterizedType) {
            Type type2 = ((ParameterizedType) type).getActualTypeArguments()[i];
            if (type2 instanceof Class) {
                return (Class) type2;
            }
            if (type2 instanceof ParameterizedType) {
                return (Class) ((ParameterizedType) type2).getRawType();
            }
        }
        return Object.class;
    }

    public static Type getParamGenericType(Class<?> cls, Type type, int i) {
        if (cls == null && type == null) {
            return Object.class;
        }
        if (type == null) {
            type = cls.getGenericSuperclass();
        }
        if (!(type instanceof ParameterizedType)) {
            type = getGenericParamType(type);
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments()[i];
        }
        return Object.class;
    }

    public static Class<?> getParamRawType(Class<?> cls, int i) {
        if (cls == null) {
            return Object.class;
        }
        return getParamRawType(cls, cls.getGenericSuperclass(), i);
    }

    public static Type getParamGenericType(Class<?> cls, int i) {
        if (cls == null) {
            return Object.class;
        }
        return getParamGenericType(cls, cls.getGenericSuperclass(), i);
    }

    public static Class<?> getCollectionElementRawType(Class<?> cls, Type type) {
        return getParamRawType(cls, type, 0);
    }

    public static Type getCollectionElementGenericType(Class<?> cls, Type type) {
        return getParamGenericType(cls, type, 0);
    }

    public static Class<?> getMapKeyRawType(Class<?> cls, Type type) {
        return getParamRawType(cls, type, 0);
    }

    public static Class<?> getMapValueRawType(Class<?> cls, Type type) {
        return getParamRawType(cls, type, 1);
    }

    public static Type getMapValueGenericType(Class<?> cls, Type type) {
        return getParamGenericType(cls, type, 1);
    }

    public static Object valueOfEnum(Class<?> cls, String str) {
        try {
            return Enum.valueOf(cls, str);
        } catch (Exception unused) {
            return null;
        }
    }
}
