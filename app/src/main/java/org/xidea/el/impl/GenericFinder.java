package org.xidea.el.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface GenericFinder {

    public static class Default {
        static GenericFinder a;

        static Type a(Field field) {
            return a == null ? field.getGenericType() : a.b();
        }

        static Type a(Method method) {
            return a == null ? method.getGenericReturnType() : a.c();
        }

        static Type a(Type type, Class<?> cls, int i) {
            TypeVariable[] typeVariableArr;
            Class<?> cls2;
            Type[] typeArr = null;
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> cls3 = (Class) parameterizedType.getRawType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                typeVariableArr = cls3.getTypeParameters();
                Class<?> cls4 = cls3;
                typeArr = actualTypeArguments;
                cls2 = cls4;
            } else {
                cls2 = (Class) type;
                typeVariableArr = null;
            }
            if (cls != cls2) {
                Type[] genericInterfaces = cls2.getGenericInterfaces();
                if (genericInterfaces != null) {
                    for (Type type2 : genericInterfaces) {
                        if ((type2 instanceof ParameterizedType) && cls.equals((Class) ((ParameterizedType) type2).getRawType())) {
                            return a(a(type2, cls, i), (TypeVariable<?>[]) typeVariableArr, typeArr);
                        }
                    }
                }
                Class<? super T> superclass = cls2.getSuperclass();
                if (superclass == null || !cls.isAssignableFrom(superclass)) {
                    StringBuilder sb = new StringBuilder("FindGenericType:");
                    sb.append(type);
                    sb.append(", declaredClass: ");
                    sb.append(cls);
                    sb.append(", index: ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                }
                return a(a(a == null ? cls2.getGenericSuperclass() : a.a(), cls, i), (TypeVariable<?>[]) typeVariableArr, typeArr);
            } else if (typeArr != null) {
                return typeArr[i];
            } else {
                return Object.class;
            }
        }

        private static Type a(Type type, TypeVariable<?>[] typeVariableArr, Type[] typeArr) {
            if (type instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type;
                String name = typeVariable.getName();
                if (typeArr != null) {
                    for (int i = 0; i < typeVariableArr.length; i++) {
                        if (name.equals(typeVariableArr[i].getName())) {
                            return typeArr[i];
                        }
                    }
                }
                return typeVariable;
            }
            if (type instanceof GenericArrayType) {
                Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                if (genericComponentType instanceof Class) {
                    return Array.newInstance((Class) genericComponentType, 0).getClass();
                }
            }
            return type;
        }

        static Type a(Type type, Class<?> cls, Type type2) {
            int i = 0;
            if (type2 instanceof TypeVariable) {
                String name = ((TypeVariable) type2).getName();
                TypeVariable[] typeParameters = cls.getTypeParameters();
                if (typeParameters != null) {
                    while (i < typeParameters.length) {
                        if (name.equals(typeParameters[i].getName())) {
                            return a(type, cls, i);
                        }
                        i++;
                    }
                }
                return type2;
            }
            if (type2 instanceof ParameterizedType) {
                final ParameterizedType parameterizedType = (ParameterizedType) type2;
                final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                boolean z = false;
                while (i < actualTypeArguments.length) {
                    Type type3 = actualTypeArguments[i];
                    Type a2 = a(type, cls, type3);
                    if (type3 != a2) {
                        actualTypeArguments[i] = a2;
                        z = true;
                    }
                    i++;
                }
                if (z) {
                    return new ParameterizedType() {
                        public final Type getRawType() {
                            return parameterizedType.getRawType();
                        }

                        public final Type getOwnerType() {
                            return parameterizedType.getOwnerType();
                        }

                        public final Type[] getActualTypeArguments() {
                            return actualTypeArguments;
                        }
                    };
                }
            }
            return type2;
        }
    }

    Type a();

    Type b();

    Type c();
}
