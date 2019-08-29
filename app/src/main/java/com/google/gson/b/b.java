package com.google.gson.b;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* compiled from: $Gson$Types */
public final class b {
    static final Type[] a = new Type[0];

    /* compiled from: $Gson$Types */
    static final class a implements Serializable, GenericArrayType {
        private final Type a;

        public a(Type type) {
            this.a = b.d(type);
        }

        public final Type getGenericComponentType() {
            return this.a;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && b.a((Type) this, (Type) (GenericArrayType) obj);
        }

        public final int hashCode() {
            return this.a.hashCode();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(b.f(this.a));
            sb.append("[]");
            return sb.toString();
        }
    }

    /* renamed from: com.google.gson.b.b$b reason: collision with other inner class name */
    /* compiled from: $Gson$Types */
    static final class C0055b implements Serializable, ParameterizedType {
        private final Type a;
        private final Type b;
        private final Type[] c;

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
            r3 = false;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public C0055b(java.lang.reflect.Type r5, java.lang.reflect.Type r6, java.lang.reflect.Type... r7) {
            /*
                r4 = this;
                r4.<init>()
                boolean r0 = r6 instanceof java.lang.Class
                r1 = 0
                if (r0 == 0) goto L_0x0029
                r0 = r6
                java.lang.Class r0 = (java.lang.Class) r0
                int r2 = r0.getModifiers()
                boolean r2 = java.lang.reflect.Modifier.isStatic(r2)
                r3 = 1
                if (r2 != 0) goto L_0x001f
                java.lang.Class r0 = r0.getEnclosingClass()
                if (r0 != 0) goto L_0x001d
                goto L_0x001f
            L_0x001d:
                r0 = 0
                goto L_0x0020
            L_0x001f:
                r0 = 1
            L_0x0020:
                if (r5 != 0) goto L_0x0026
                if (r0 == 0) goto L_0x0025
                goto L_0x0026
            L_0x0025:
                r3 = 0
            L_0x0026:
                com.google.gson.b.a.a(r3)
            L_0x0029:
                if (r5 != 0) goto L_0x002d
                r5 = 0
                goto L_0x0031
            L_0x002d:
                java.lang.reflect.Type r5 = com.google.gson.b.b.d(r5)
            L_0x0031:
                r4.a = r5
                java.lang.reflect.Type r5 = com.google.gson.b.b.d(r6)
                r4.b = r5
                java.lang.Object r5 = r7.clone()
                java.lang.reflect.Type[] r5 = (java.lang.reflect.Type[]) r5
                r4.c = r5
                java.lang.reflect.Type[] r5 = r4.c
                int r5 = r5.length
            L_0x0044:
                if (r1 >= r5) goto L_0x0061
                java.lang.reflect.Type[] r6 = r4.c
                r6 = r6[r1]
                com.google.gson.b.a.a(r6)
                java.lang.reflect.Type[] r6 = r4.c
                r6 = r6[r1]
                com.google.gson.b.b.h(r6)
                java.lang.reflect.Type[] r6 = r4.c
                r7 = r6[r1]
                java.lang.reflect.Type r7 = com.google.gson.b.b.d(r7)
                r6[r1] = r7
                int r1 = r1 + 1
                goto L_0x0044
            L_0x0061:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.b.C0055b.<init>(java.lang.reflect.Type, java.lang.reflect.Type, java.lang.reflect.Type[]):void");
        }

        public final Type[] getActualTypeArguments() {
            return (Type[]) this.c.clone();
        }

        public final Type getRawType() {
            return this.b;
        }

        public final Type getOwnerType() {
            return this.a;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && b.a((Type) this, (Type) (ParameterizedType) obj);
        }

        public final int hashCode() {
            return (Arrays.hashCode(this.c) ^ this.b.hashCode()) ^ b.a((Object) this.a);
        }

        public final String toString() {
            int length = this.c.length;
            if (length == 0) {
                return b.f(this.b);
            }
            StringBuilder sb = new StringBuilder((length + 1) * 30);
            sb.append(b.f(this.b));
            sb.append(SimpleComparison.LESS_THAN_OPERATION);
            sb.append(b.f(this.c[0]));
            for (int i = 1; i < length; i++) {
                sb.append(", ");
                sb.append(b.f(this.c[i]));
            }
            sb.append(SimpleComparison.GREATER_THAN_OPERATION);
            return sb.toString();
        }
    }

    /* compiled from: $Gson$Types */
    static final class c implements Serializable, WildcardType {
        private final Type a;
        private final Type b;

        public c(Type[] typeArr, Type[] typeArr2) {
            boolean z = true;
            a.a(typeArr2.length <= 1);
            a.a(typeArr.length == 1);
            if (typeArr2.length == 1) {
                a.a(typeArr2[0]);
                b.h(typeArr2[0]);
                a.a(typeArr[0] != Object.class ? false : z);
                this.b = b.d(typeArr2[0]);
                this.a = Object.class;
                return;
            }
            a.a(typeArr[0]);
            b.h(typeArr[0]);
            this.b = null;
            this.a = b.d(typeArr[0]);
        }

        public final Type[] getUpperBounds() {
            return new Type[]{this.a};
        }

        public final Type[] getLowerBounds() {
            if (this.b == null) {
                return b.a;
            }
            return new Type[]{this.b};
        }

        public final boolean equals(Object obj) {
            return (obj instanceof WildcardType) && b.a((Type) this, (Type) (WildcardType) obj);
        }

        public final int hashCode() {
            return (this.b != null ? this.b.hashCode() + 31 : 1) ^ (this.a.hashCode() + 31);
        }

        public final String toString() {
            if (this.b != null) {
                StringBuilder sb = new StringBuilder("? super ");
                sb.append(b.f(this.b));
                return sb.toString();
            } else if (this.a == Object.class) {
                return "?";
            } else {
                StringBuilder sb2 = new StringBuilder("? extends ");
                sb2.append(b.f(this.a));
                return sb2.toString();
            }
        }
    }

    public static ParameterizedType a(Type type, Type type2, Type... typeArr) {
        return new C0055b(type, type2, typeArr);
    }

    public static GenericArrayType a(Type type) {
        return new a(type);
    }

    public static WildcardType b(Type type) {
        Type[] typeArr;
        if (type instanceof WildcardType) {
            typeArr = ((WildcardType) type).getUpperBounds();
        } else {
            typeArr = new Type[]{type};
        }
        return new c(typeArr, a);
    }

    public static WildcardType c(Type type) {
        Type[] typeArr;
        if (type instanceof WildcardType) {
            typeArr = ((WildcardType) type).getLowerBounds();
        } else {
            typeArr = new Type[]{type};
        }
        return new c(new Type[]{Object.class}, typeArr);
    }

    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r0v8, types: [com.google.gson.b.b$a] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.reflect.Type d(java.lang.reflect.Type r3) {
        /*
            boolean r0 = r3 instanceof java.lang.Class
            if (r0 == 0) goto L_0x001d
            java.lang.Class r3 = (java.lang.Class) r3
            boolean r0 = r3.isArray()
            if (r0 == 0) goto L_0x001a
            com.google.gson.b.b$a r0 = new com.google.gson.b.b$a
            java.lang.Class r3 = r3.getComponentType()
            java.lang.reflect.Type r3 = d(r3)
            r0.<init>(r3)
            r3 = r0
        L_0x001a:
            java.lang.reflect.Type r3 = (java.lang.reflect.Type) r3
            return r3
        L_0x001d:
            boolean r0 = r3 instanceof java.lang.reflect.ParameterizedType
            if (r0 == 0) goto L_0x0035
            java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
            com.google.gson.b.b$b r0 = new com.google.gson.b.b$b
            java.lang.reflect.Type r1 = r3.getOwnerType()
            java.lang.reflect.Type r2 = r3.getRawType()
            java.lang.reflect.Type[] r3 = r3.getActualTypeArguments()
            r0.<init>(r1, r2, r3)
            return r0
        L_0x0035:
            boolean r0 = r3 instanceof java.lang.reflect.GenericArrayType
            if (r0 == 0) goto L_0x0045
            java.lang.reflect.GenericArrayType r3 = (java.lang.reflect.GenericArrayType) r3
            com.google.gson.b.b$a r0 = new com.google.gson.b.b$a
            java.lang.reflect.Type r3 = r3.getGenericComponentType()
            r0.<init>(r3)
            return r0
        L_0x0045:
            boolean r0 = r3 instanceof java.lang.reflect.WildcardType
            if (r0 == 0) goto L_0x0059
            java.lang.reflect.WildcardType r3 = (java.lang.reflect.WildcardType) r3
            com.google.gson.b.b$c r0 = new com.google.gson.b.b$c
            java.lang.reflect.Type[] r1 = r3.getUpperBounds()
            java.lang.reflect.Type[] r3 = r3.getLowerBounds()
            r0.<init>(r1, r3)
            return r0
        L_0x0059:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.b.d(java.lang.reflect.Type):java.lang.reflect.Type");
    }

    public static Class<?> e(Type type) {
        String str;
        while (!(type instanceof Class)) {
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                a.a(rawType instanceof Class);
                return (Class) rawType;
            } else if (type instanceof GenericArrayType) {
                return Array.newInstance(e(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
            } else {
                if (type instanceof TypeVariable) {
                    return Object.class;
                }
                if (type instanceof WildcardType) {
                    type = ((WildcardType) type).getUpperBounds()[0];
                } else {
                    if (type == null) {
                        str = "null";
                    } else {
                        str = type.getClass().getName();
                    }
                    StringBuilder sb = new StringBuilder("Expected a Class, ParameterizedType, or GenericArrayType, but <");
                    sb.append(type);
                    sb.append("> is of type ");
                    sb.append(str);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
        }
        return (Class) type;
    }

    static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static boolean a(Type type, Type type2) {
        while (type != type2) {
            if (type instanceof Class) {
                return type.equals(type2);
            }
            if (type instanceof ParameterizedType) {
                if (!(type2 instanceof ParameterizedType)) {
                    return false;
                }
                ParameterizedType parameterizedType = (ParameterizedType) type;
                ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                if (!a((Object) parameterizedType.getOwnerType(), (Object) parameterizedType2.getOwnerType()) || !parameterizedType.getRawType().equals(parameterizedType2.getRawType()) || !Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments())) {
                    return false;
                }
                return true;
            } else if (type instanceof GenericArrayType) {
                if (!(type2 instanceof GenericArrayType)) {
                    return false;
                }
                type = ((GenericArrayType) type).getGenericComponentType();
                type2 = ((GenericArrayType) type2).getGenericComponentType();
            } else if (type instanceof WildcardType) {
                if (!(type2 instanceof WildcardType)) {
                    return false;
                }
                WildcardType wildcardType = (WildcardType) type;
                WildcardType wildcardType2 = (WildcardType) type2;
                if (!Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) || !Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds())) {
                    return false;
                }
                return true;
            } else if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable typeVariable = (TypeVariable) type;
                TypeVariable typeVariable2 = (TypeVariable) type2;
                if (typeVariable.getGenericDeclaration() != typeVariable2.getGenericDeclaration() || !typeVariable.getName().equals(typeVariable2.getName())) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    static int a(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static String f(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r5v0, types: [java.lang.Class<?>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.reflect.Type a(java.lang.reflect.Type r4, java.lang.Class r5, java.lang.Class<?> r6) {
        /*
        L_0x0000:
            if (r6 != r5) goto L_0x0003
            return r4
        L_0x0003:
            boolean r4 = r6.isInterface()
            if (r4 == 0) goto L_0x0033
            java.lang.Class[] r4 = r5.getInterfaces()
            r0 = 0
            int r1 = r4.length
        L_0x000f:
            if (r0 >= r1) goto L_0x0033
            r2 = r4[r0]
            if (r2 != r6) goto L_0x001c
            java.lang.reflect.Type[] r4 = r5.getGenericInterfaces()
            r4 = r4[r0]
            return r4
        L_0x001c:
            r2 = r4[r0]
            boolean r2 = r6.isAssignableFrom(r2)
            if (r2 == 0) goto L_0x0030
            java.lang.reflect.Type[] r5 = r5.getGenericInterfaces()
            r5 = r5[r0]
            r4 = r4[r0]
        L_0x002c:
            r3 = r5
            r5 = r4
            r4 = r3
            goto L_0x0000
        L_0x0030:
            int r0 = r0 + 1
            goto L_0x000f
        L_0x0033:
            boolean r4 = r5.isInterface()
            if (r4 != 0) goto L_0x0055
        L_0x0039:
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            if (r5 == r4) goto L_0x0055
            java.lang.Class r4 = r5.getSuperclass()
            if (r4 != r6) goto L_0x0048
            java.lang.reflect.Type r4 = r5.getGenericSuperclass()
            return r4
        L_0x0048:
            boolean r0 = r6.isAssignableFrom(r4)
            if (r0 == 0) goto L_0x0053
            java.lang.reflect.Type r5 = r5.getGenericSuperclass()
            goto L_0x002c
        L_0x0053:
            r5 = r4
            goto L_0x0039
        L_0x0055:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.b.a(java.lang.reflect.Type, java.lang.Class, java.lang.Class):java.lang.reflect.Type");
    }

    static Type b(Type type, Class<?> cls, Class<?> cls2) {
        if (type instanceof WildcardType) {
            type = ((WildcardType) type).getUpperBounds()[0];
        }
        a.a(cls2.isAssignableFrom(cls));
        return a(type, cls, a(type, cls, cls2));
    }

    public static Type g(Type type) {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        }
        return ((Class) type).getComponentType();
    }

    public static Type a(Type type, Class<?> cls) {
        Type b = b(type, cls, Collection.class);
        if (b instanceof WildcardType) {
            b = ((WildcardType) b).getUpperBounds()[0];
        }
        if (b instanceof ParameterizedType) {
            return ((ParameterizedType) b).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    public static Type[] b(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type b = b(type, cls, Map.class);
        if (b instanceof ParameterizedType) {
            return ((ParameterizedType) b).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static Type a(Type type, Class<?> cls, Type type2) {
        return a(type, cls, type2, new HashSet());
    }

    private static Type a(Type type, Class<?> cls, Type type2, Collection<TypeVariable> collection) {
        while (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            if (collection.contains(typeVariable)) {
                return type2;
            }
            collection.add(typeVariable);
            type2 = a(type, cls, typeVariable);
            if (type2 == typeVariable) {
                return type2;
            }
        }
        if (type2 instanceof Class) {
            Class cls2 = (Class) type2;
            if (cls2.isArray()) {
                Class<?> componentType = cls2.getComponentType();
                Type a2 = a(type, cls, componentType, collection);
                if (componentType == a2) {
                    return cls2;
                }
                return a(a2);
            }
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type2;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            Type a3 = a(type, cls, genericComponentType, collection);
            if (genericComponentType == a3) {
                return genericArrayType;
            }
            return a(a3);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            Type ownerType = parameterizedType.getOwnerType();
            Type a4 = a(type, cls, ownerType, collection);
            boolean z = a4 != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int length = actualTypeArguments.length;
            for (int i = 0; i < length; i++) {
                Type a5 = a(type, cls, actualTypeArguments[i], collection);
                if (a5 != actualTypeArguments[i]) {
                    if (!z) {
                        actualTypeArguments = (Type[]) actualTypeArguments.clone();
                        z = true;
                    }
                    actualTypeArguments[i] = a5;
                }
            }
            return z ? a(a4, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
        } else if (!(type2 instanceof WildcardType)) {
            return type2;
        } else {
            WildcardType wildcardType = (WildcardType) type2;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                Type a6 = a(type, cls, lowerBounds[0], collection);
                if (a6 != lowerBounds[0]) {
                    return c(a6);
                }
            } else if (upperBounds.length == 1) {
                Type a7 = a(type, cls, upperBounds[0], collection);
                if (a7 != upperBounds[0]) {
                    return b(a7);
                }
            }
            return wildcardType;
        }
    }

    static Type a(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class<?> a2 = a(typeVariable);
        if (a2 == null) {
            return typeVariable;
        }
        Type a3 = a(type, cls, a2);
        if (!(a3 instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) a3).getActualTypeArguments()[a((Object[]) a2.getTypeParameters(), (Object) typeVariable)];
    }

    private static int a(Object[] objArr, Object obj) {
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> a(TypeVariable<?> typeVariable) {
        Object genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    static void h(Type type) {
        a.a(!(type instanceof Class) || !((Class) type).isPrimitive());
    }
}
