package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo implements Comparable<FieldInfo> {
    public final String[] alternateNames;
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final long nameHashCode;
    private int ordinal = 0;
    public final int serialzeFeatures;

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r7 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FieldInfo(java.lang.String r2, java.lang.Class<?> r3, java.lang.Class<?> r4, java.lang.reflect.Type r5, java.lang.reflect.Field r6, int r7, int r8) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.ordinal = r0
            if (r7 >= 0) goto L_0x0009
            r7 = 0
        L_0x0009:
            r1.name = r2
            r1.declaringClass = r3
            r1.fieldClass = r4
            r1.fieldType = r5
            r3 = 0
            r1.method = r3
            r1.field = r6
            r1.ordinal = r7
            r1.serialzeFeatures = r8
            boolean r5 = r4.isEnum()
            r7 = 1
            if (r5 == 0) goto L_0x002b
            java.lang.Class<com.alibaba.fastjson.JSONAware> r5 = com.alibaba.fastjson.JSONAware.class
            boolean r4 = r5.isAssignableFrom(r4)
            if (r4 != 0) goto L_0x002b
            r4 = 1
            goto L_0x002c
        L_0x002b:
            r4 = 0
        L_0x002c:
            r1.isEnum = r4
            r1.fieldAnnotation = r3
            r1.methodAnnotation = r3
            if (r6 == 0) goto L_0x004b
            int r4 = r6.getModifiers()
            r5 = r4 & 1
            if (r5 != 0) goto L_0x0042
            java.lang.reflect.Method r5 = r1.method
            if (r5 != 0) goto L_0x0041
            goto L_0x0042
        L_0x0041:
            r7 = 0
        L_0x0042:
            r1.fieldAccess = r7
            boolean r4 = java.lang.reflect.Modifier.isTransient(r4)
            r1.fieldTransient = r4
            goto L_0x004f
        L_0x004b:
            r1.fieldAccess = r0
            r1.fieldTransient = r0
        L_0x004f:
            r1.getOnly = r0
            r4 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r5 = r4
            r4 = 0
        L_0x0058:
            int r7 = r2.length()
            if (r4 >= r7) goto L_0x006e
            char r7 = r2.charAt(r4)
            long r7 = (long) r7
            long r5 = r5 ^ r7
            r7 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r5 = r5 * r7
            int r4 = r4 + 1
            goto L_0x0058
        L_0x006e:
            r1.nameHashCode = r5
            r1.format = r3
            java.lang.String[] r2 = new java.lang.String[r0]
            r1.alternateNames = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.FieldInfo.<init>(java.lang.String, java.lang.Class, java.lang.Class, java.lang.reflect.Type, java.lang.reflect.Field, int, int):void");
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: type inference failed for: r10v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r7v1, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r8v2, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r6v2, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r8v4, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r6v4, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r6v5, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r13v8, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r13v11, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r13v13 */
    /* JADX WARNING: type inference failed for: r6v7, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r7v11 */
    /* JADX WARNING: type inference failed for: r7v12 */
    /* JADX WARNING: type inference failed for: r7v15, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r6v8, types: [java.lang.Class[]] */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: type inference failed for: r7v17 */
    /* JADX WARNING: type inference failed for: r6v10, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r8v14 */
    /* JADX WARNING: type inference failed for: r8v15 */
    /* JADX WARNING: type inference failed for: r8v16, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r6v11, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r8v18 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* JADX WARNING: type inference failed for: r8v22, types: [java.lang.reflect.Type[]] */
    /* JADX WARNING: type inference failed for: r8v23 */
    /* JADX WARNING: type inference failed for: r6v12 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r13v27 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r7v18 */
    /* JADX WARNING: type inference failed for: r7v19 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r8v24 */
    /* JADX WARNING: type inference failed for: r8v25 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r8v26 */
    /* JADX WARNING: type inference failed for: r8v27 */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0186, code lost:
        r6 = com.alibaba.fastjson.util.TypeUtils.getClass(r8);
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v11
      assigns: []
      uses: []
      mth insns count: 185
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 23 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FieldInfo(java.lang.String r6, java.lang.reflect.Method r7, java.lang.reflect.Field r8, java.lang.Class<?> r9, java.lang.reflect.Type r10, int r11, int r12, com.alibaba.fastjson.annotation.JSONField r13, com.alibaba.fastjson.annotation.JSONField r14, boolean r15) {
        /*
            r5 = this;
            r5.<init>()
            r0 = 0
            r5.ordinal = r0
            if (r11 >= 0) goto L_0x0009
            r11 = 0
        L_0x0009:
            r5.name = r6
            r5.method = r7
            r5.field = r8
            r5.ordinal = r11
            r5.methodAnnotation = r13
            r5.fieldAnnotation = r14
            r5.serialzeFeatures = r12
            com.alibaba.fastjson.annotation.JSONField r11 = r5.getAnnotation()
            r12 = 0
            if (r11 == 0) goto L_0x0034
            java.lang.String r13 = r11.format()
            java.lang.String r14 = r13.trim()
            int r14 = r14.length()
            if (r14 != 0) goto L_0x002d
            r13 = r12
        L_0x002d:
            java.lang.String[] r11 = r11.alternateNames()
            r5.alternateNames = r11
            goto L_0x0039
        L_0x0034:
            java.lang.String[] r11 = new java.lang.String[r0]
            r5.alternateNames = r11
            r13 = r12
        L_0x0039:
            r5.format = r13
            r11 = 1
            if (r8 == 0) goto L_0x0062
            int r13 = r8.getModifiers()
            if (r7 == 0) goto L_0x0055
            r14 = r13 & 1
            if (r14 == 0) goto L_0x0053
            java.lang.Class r14 = r7.getReturnType()
            java.lang.Class r1 = r8.getType()
            if (r14 != r1) goto L_0x0053
            goto L_0x0055
        L_0x0053:
            r14 = 0
            goto L_0x0056
        L_0x0055:
            r14 = 1
        L_0x0056:
            r5.fieldAccess = r14
            r13 = r13 & 128(0x80, float:1.794E-43)
            if (r13 == 0) goto L_0x005e
            r13 = 1
            goto L_0x005f
        L_0x005e:
            r13 = 0
        L_0x005f:
            r5.fieldTransient = r13
            goto L_0x0066
        L_0x0062:
            r5.fieldAccess = r0
            r5.fieldTransient = r0
        L_0x0066:
            r13 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r1 = r13
            r13 = 0
        L_0x006d:
            int r14 = r6.length()
            if (r13 >= r14) goto L_0x0083
            char r14 = r6.charAt(r13)
            long r3 = (long) r14
            long r1 = r1 ^ r3
            r3 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r1 = r1 * r3
            int r13 = r13 + 1
            goto L_0x006d
        L_0x0083:
            r5.nameHashCode = r1
            if (r7 == 0) goto L_0x00c6
            java.lang.Class[] r6 = r7.getParameterTypes()
            int r8 = r6.length
            if (r8 != r11) goto L_0x00ac
            r6 = r6[r0]
            java.lang.Class<java.lang.Class> r8 = java.lang.Class.class
            if (r6 == r8) goto L_0x00a8
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            if (r6 == r8) goto L_0x00a8
            boolean r8 = r6.isPrimitive()
            if (r8 == 0) goto L_0x009f
            goto L_0x00a8
        L_0x009f:
            if (r15 == 0) goto L_0x00a8
            java.lang.reflect.Type[] r8 = r7.getGenericParameterTypes()
            r8 = r8[r0]
            goto L_0x00a9
        L_0x00a8:
            r8 = r6
        L_0x00a9:
            r5.getOnly = r0
            goto L_0x00be
        L_0x00ac:
            java.lang.Class r6 = r7.getReturnType()
            java.lang.Class<java.lang.Class> r8 = java.lang.Class.class
            if (r6 == r8) goto L_0x00bb
            if (r15 == 0) goto L_0x00bb
            java.lang.reflect.Type r8 = r7.getGenericReturnType()
            goto L_0x00bc
        L_0x00bb:
            r8 = r6
        L_0x00bc:
            r5.getOnly = r11
        L_0x00be:
            java.lang.Class r7 = r7.getDeclaringClass()
            r5.declaringClass = r7
            r7 = r8
            goto L_0x00f3
        L_0x00c6:
            java.lang.Class r6 = r8.getType()
            boolean r7 = r6.isPrimitive()
            if (r7 != 0) goto L_0x00e2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            if (r6 == r7) goto L_0x00e2
            boolean r7 = r6.isEnum()
            if (r7 == 0) goto L_0x00db
            goto L_0x00e2
        L_0x00db:
            if (r15 == 0) goto L_0x00e2
            java.lang.reflect.Type r7 = r8.getGenericType()
            goto L_0x00e3
        L_0x00e2:
            r7 = r6
        L_0x00e3:
            java.lang.Class r13 = r8.getDeclaringClass()
            r5.declaringClass = r13
            int r8 = r8.getModifiers()
            boolean r8 = java.lang.reflect.Modifier.isFinal(r8)
            r5.getOnly = r8
        L_0x00f3:
            if (r9 == 0) goto L_0x016b
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            if (r6 != r8) goto L_0x016b
            boolean r8 = r7 instanceof java.lang.reflect.TypeVariable
            if (r8 == 0) goto L_0x016b
            r8 = r7
            java.lang.reflect.TypeVariable r8 = (java.lang.reflect.TypeVariable) r8
            boolean r13 = r10 instanceof java.lang.reflect.ParameterizedType
            if (r13 == 0) goto L_0x010c
            r13 = r10
            java.lang.reflect.ParameterizedType r13 = (java.lang.reflect.ParameterizedType) r13
            java.lang.reflect.Type[] r13 = r13.getActualTypeArguments()
            goto L_0x010d
        L_0x010c:
            r13 = r12
        L_0x010d:
            r14 = r13
            r13 = r9
        L_0x010f:
            if (r13 == 0) goto L_0x0134
            java.lang.Class<java.lang.Object> r15 = java.lang.Object.class
            if (r13 == r15) goto L_0x0134
            java.lang.Class<?> r15 = r5.declaringClass
            if (r13 == r15) goto L_0x0134
            java.lang.reflect.Type r15 = r13.getGenericSuperclass()
            boolean r1 = r15 instanceof java.lang.reflect.ParameterizedType
            if (r1 == 0) goto L_0x012f
            java.lang.reflect.ParameterizedType r15 = (java.lang.reflect.ParameterizedType) r15
            java.lang.reflect.Type[] r15 = r15.getActualTypeArguments()
            java.lang.reflect.TypeVariable[] r1 = r13.getTypeParameters()
            com.alibaba.fastjson.util.TypeUtils.getArgument(r15, r1, r14)
            r14 = r15
        L_0x012f:
            java.lang.Class r13 = r13.getSuperclass()
            goto L_0x010f
        L_0x0134:
            if (r14 == 0) goto L_0x014e
            java.lang.Class<?> r13 = r5.declaringClass
            java.lang.reflect.TypeVariable[] r13 = r13.getTypeParameters()
            r15 = 0
        L_0x013d:
            int r1 = r13.length
            if (r15 >= r1) goto L_0x014e
            r1 = r13[r15]
            boolean r1 = r8.equals(r1)
            if (r1 == 0) goto L_0x014b
            r12 = r14[r15]
            goto L_0x014e
        L_0x014b:
            int r15 = r15 + 1
            goto L_0x013d
        L_0x014e:
            if (r12 == 0) goto L_0x016b
            java.lang.Class r7 = com.alibaba.fastjson.util.TypeUtils.getClass(r12)
            r5.fieldClass = r7
            r5.fieldType = r12
            boolean r7 = r6.isEnum()
            if (r7 == 0) goto L_0x0167
            java.lang.Class<com.alibaba.fastjson.JSONAware> r7 = com.alibaba.fastjson.JSONAware.class
            boolean r6 = r7.isAssignableFrom(r6)
            if (r6 != 0) goto L_0x0167
            goto L_0x0168
        L_0x0167:
            r11 = 0
        L_0x0168:
            r5.isEnum = r11
            return
        L_0x016b:
            boolean r8 = r7 instanceof java.lang.Class
            if (r8 != 0) goto L_0x018b
            if (r10 == 0) goto L_0x0172
            goto L_0x0173
        L_0x0172:
            r10 = r9
        L_0x0173:
            java.lang.reflect.Type r8 = getFieldType(r9, r10, r7)
            if (r8 == r7) goto L_0x018c
            boolean r7 = r8 instanceof java.lang.reflect.ParameterizedType
            if (r7 == 0) goto L_0x0182
            java.lang.Class r6 = com.alibaba.fastjson.util.TypeUtils.getClass(r8)
            goto L_0x018c
        L_0x0182:
            boolean r7 = r8 instanceof java.lang.Class
            if (r7 == 0) goto L_0x018c
            java.lang.Class r6 = com.alibaba.fastjson.util.TypeUtils.getClass(r8)
            goto L_0x018c
        L_0x018b:
            r8 = r7
        L_0x018c:
            r5.fieldType = r8
            r5.fieldClass = r6
            boolean r7 = r6.isArray()
            if (r7 != 0) goto L_0x01a5
            boolean r7 = r6.isEnum()
            if (r7 == 0) goto L_0x01a5
            java.lang.Class<com.alibaba.fastjson.JSONAware> r7 = com.alibaba.fastjson.JSONAware.class
            boolean r6 = r7.isAssignableFrom(r6)
            if (r6 != 0) goto L_0x01a5
            goto L_0x01a6
        L_0x01a5:
            r11 = 0
        L_0x01a6:
            r5.isEnum = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.FieldInfo.<init>(java.lang.String, java.lang.reflect.Method, java.lang.reflect.Field, java.lang.Class, java.lang.reflect.Type, int, int, com.alibaba.fastjson.annotation.JSONField, com.alibaba.fastjson.annotation.JSONField, boolean):void");
    }

    public static Type getFieldType(Class<?> cls, Type type, Type type2) {
        ParameterizedType parameterizedType;
        TypeVariable[] typeVariableArr;
        if (cls == null || type == null) {
            return type2;
        }
        if (type2 instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type2).getGenericComponentType();
            Type fieldType2 = getFieldType(cls, type, genericComponentType);
            return genericComponentType != fieldType2 ? Array.newInstance(TypeUtils.getClass(fieldType2), 0).getClass() : type2;
        } else if (!TypeUtils.isGenericParamType(type)) {
            return type2;
        } else {
            if (type2 instanceof TypeVariable) {
                ParameterizedType parameterizedType2 = (ParameterizedType) TypeUtils.getGenericParamType(type);
                Class<?> cls2 = TypeUtils.getClass(parameterizedType2);
                TypeVariable typeVariable = (TypeVariable) type2;
                for (int i = 0; i < cls2.getTypeParameters().length; i++) {
                    if (cls2.getTypeParameters()[i].getName().equals(typeVariable.getName())) {
                        return parameterizedType2.getActualTypeArguments()[i];
                    }
                }
            }
            if (type2 instanceof ParameterizedType) {
                ParameterizedType parameterizedType3 = (ParameterizedType) type2;
                Type[] actualTypeArguments = parameterizedType3.getActualTypeArguments();
                if (type instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) type;
                    typeVariableArr = cls.getTypeParameters();
                } else if (cls.getGenericSuperclass() instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
                    typeVariableArr = cls.getSuperclass().getTypeParameters();
                } else {
                    typeVariableArr = null;
                    parameterizedType = null;
                }
                Type[] typeArr = null;
                boolean z = false;
                for (int i2 = 0; i2 < actualTypeArguments.length && parameterizedType != null; i2++) {
                    Type type3 = actualTypeArguments[i2];
                    if (type3 instanceof TypeVariable) {
                        TypeVariable typeVariable2 = (TypeVariable) type3;
                        Type[] typeArr2 = typeArr;
                        boolean z2 = z;
                        for (int i3 = 0; i3 < typeVariableArr.length; i3++) {
                            if (typeVariableArr[i3].getName().equals(typeVariable2.getName())) {
                                if (typeArr2 == null) {
                                    typeArr2 = parameterizedType.getActualTypeArguments();
                                }
                                actualTypeArguments[i2] = typeArr2[i3];
                                z2 = true;
                            }
                        }
                        z = z2;
                        typeArr = typeArr2;
                    }
                }
                if (z) {
                    return new ParameterizedTypeImpl(actualTypeArguments, parameterizedType3.getOwnerType(), parameterizedType3.getRawType());
                }
            }
            return type2;
        }
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(FieldInfo fieldInfo) {
        if (this.ordinal < fieldInfo.ordinal) {
            return -1;
        }
        if (this.ordinal > fieldInfo.ordinal) {
            return 1;
        }
        return this.name.compareTo(fieldInfo.name);
    }

    public boolean equals(FieldInfo fieldInfo) {
        return fieldInfo == this || compareTo(fieldInfo) == 0;
    }

    public JSONField getAnnotation() {
        if (this.fieldAnnotation != null) {
            return this.fieldAnnotation;
        }
        return this.methodAnnotation;
    }

    public Object get(Object obj) throws IllegalAccessException, InvocationTargetException {
        if (this.fieldAccess) {
            return this.field.get(obj);
        }
        return this.method.invoke(obj, new Object[0]);
    }

    public void set(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            this.method.invoke(obj, new Object[]{obj2});
            return;
        }
        this.field.set(obj, obj2);
    }
}
