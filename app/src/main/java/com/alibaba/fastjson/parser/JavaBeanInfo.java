package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

class JavaBeanInfo {
    final Constructor<?> creatorConstructor;
    public final String[] creatorConstructorParameters;
    final Constructor<?> defaultConstructor;
    final int defaultConstructorParameterSize;
    final Method factoryMethod;
    final FieldInfo[] fields;
    final JSONType jsonType;
    boolean ordered = false;
    public final int parserFeatures;
    final FieldInfo[] sortedFields;
    final boolean supportBeanToArray;
    public final String typeKey;
    public final long typeKeyHashCode;
    public final String typeName;

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0099, code lost:
        r0 = r8.getParameterTypes().length;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    JavaBeanInfo(java.lang.Class<?> r5, java.lang.reflect.Constructor<?> r6, java.lang.reflect.Constructor<?> r7, java.lang.reflect.Method r8, com.alibaba.fastjson.util.FieldInfo[] r9, com.alibaba.fastjson.util.FieldInfo[] r10, com.alibaba.fastjson.annotation.JSONType r11, java.lang.String[] r12) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 0
            r4.ordered = r0
            r4.defaultConstructor = r6
            r4.creatorConstructor = r7
            r4.factoryMethod = r8
            r4.fields = r9
            r4.jsonType = r11
            r7 = 0
            if (r12 == 0) goto L_0x001a
            int r1 = r12.length
            int r2 = r9.length
            if (r1 != r2) goto L_0x001a
            r4.creatorConstructorParameters = r7
            goto L_0x001c
        L_0x001a:
            r4.creatorConstructorParameters = r12
        L_0x001c:
            if (r11 == 0) goto L_0x004e
            java.lang.String r12 = r11.typeName()
            int r1 = r12.length()
            if (r1 <= 0) goto L_0x0029
            goto L_0x002d
        L_0x0029:
            java.lang.String r12 = r5.getName()
        L_0x002d:
            r4.typeName = r12
            java.lang.String r5 = r11.typeKey()
            int r12 = r5.length()
            if (r12 <= 0) goto L_0x003a
            goto L_0x003b
        L_0x003a:
            r5 = r7
        L_0x003b:
            r4.typeKey = r5
            com.alibaba.fastjson.parser.Feature[] r5 = r11.parseFeatures()
            int r7 = r5.length
            r12 = 0
            r1 = 0
        L_0x0044:
            if (r12 >= r7) goto L_0x0057
            r2 = r5[r12]
            int r2 = r2.mask
            r1 = r1 | r2
            int r12 = r12 + 1
            goto L_0x0044
        L_0x004e:
            java.lang.String r5 = r5.getName()
            r4.typeName = r5
            r4.typeKey = r7
            r1 = 0
        L_0x0057:
            java.lang.String r5 = r4.typeKey
            if (r5 != 0) goto L_0x0060
            r2 = 0
            r4.typeKeyHashCode = r2
            goto L_0x0068
        L_0x0060:
            java.lang.String r5 = r4.typeKey
            long r2 = com.alibaba.fastjson.util.TypeUtils.fnv_64_lower(r5)
            r4.typeKeyHashCode = r2
        L_0x0068:
            r4.parserFeatures = r1
            if (r11 == 0) goto L_0x007f
            com.alibaba.fastjson.parser.Feature[] r5 = r11.parseFeatures()
            int r7 = r5.length
            r11 = 0
            r12 = 0
        L_0x0073:
            if (r11 >= r7) goto L_0x0080
            r1 = r5[r11]
            com.alibaba.fastjson.parser.Feature r2 = com.alibaba.fastjson.parser.Feature.SupportArrayToBean
            if (r1 != r2) goto L_0x007c
            r12 = 1
        L_0x007c:
            int r11 = r11 + 1
            goto L_0x0073
        L_0x007f:
            r12 = 0
        L_0x0080:
            r4.supportBeanToArray = r12
            com.alibaba.fastjson.util.FieldInfo[] r5 = r4.computeSortedFields(r9, r10)
            boolean r7 = java.util.Arrays.equals(r9, r5)
            if (r7 == 0) goto L_0x008d
            r5 = r9
        L_0x008d:
            r4.sortedFields = r5
            if (r6 == 0) goto L_0x0097
            java.lang.Class[] r5 = r6.getParameterTypes()
            int r0 = r5.length
            goto L_0x009e
        L_0x0097:
            if (r8 == 0) goto L_0x009e
            java.lang.Class[] r5 = r8.getParameterTypes()
            int r0 = r5.length
        L_0x009e:
            r4.defaultConstructorParameterSize = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JavaBeanInfo.<init>(java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Constructor, java.lang.reflect.Method, com.alibaba.fastjson.util.FieldInfo[], com.alibaba.fastjson.util.FieldInfo[], com.alibaba.fastjson.annotation.JSONType, java.lang.String[]):void");
    }

    private FieldInfo[] computeSortedFields(FieldInfo[] fieldInfoArr, FieldInfo[] fieldInfoArr2) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        if (this.jsonType == null) {
            return fieldInfoArr2;
        }
        String[] orders = this.jsonType.orders();
        if (!(orders == null || orders.length == 0)) {
            int i = 0;
            while (true) {
                if (i >= orders.length) {
                    z = true;
                    break;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= fieldInfoArr2.length) {
                        z4 = false;
                        break;
                    } else if (fieldInfoArr2[i2].name.equals(orders[i])) {
                        z4 = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z4) {
                    z = false;
                    break;
                }
                i++;
            }
            if (!z) {
                return fieldInfoArr2;
            }
            if (orders.length == fieldInfoArr.length) {
                int i3 = 0;
                while (true) {
                    if (i3 >= orders.length) {
                        z3 = true;
                        break;
                    } else if (!fieldInfoArr2[i3].name.equals(orders[i3])) {
                        z3 = false;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (z3) {
                    return fieldInfoArr2;
                }
                FieldInfo[] fieldInfoArr3 = new FieldInfo[fieldInfoArr2.length];
                for (int i4 = 0; i4 < orders.length; i4++) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= fieldInfoArr2.length) {
                            break;
                        } else if (fieldInfoArr2[i5].name.equals(orders[i4])) {
                            fieldInfoArr3[i4] = fieldInfoArr2[i5];
                            break;
                        } else {
                            i5++;
                        }
                    }
                }
                this.ordered = true;
                return fieldInfoArr3;
            }
            FieldInfo[] fieldInfoArr4 = new FieldInfo[fieldInfoArr2.length];
            for (int i6 = 0; i6 < orders.length; i6++) {
                int i7 = 0;
                while (true) {
                    if (i7 >= fieldInfoArr2.length) {
                        break;
                    } else if (fieldInfoArr2[i7].name.equals(orders[i6])) {
                        fieldInfoArr4[i6] = fieldInfoArr2[i7];
                        break;
                    } else {
                        i7++;
                    }
                }
            }
            int length = orders.length;
            for (int i8 = 0; i8 < fieldInfoArr2.length; i8++) {
                int i9 = 0;
                while (true) {
                    if (i9 >= fieldInfoArr4.length || i9 >= length) {
                        z2 = false;
                    } else if (fieldInfoArr4[i8].equals(fieldInfoArr2[i9])) {
                        z2 = true;
                        break;
                    } else {
                        i9++;
                    }
                }
                if (!z2) {
                    fieldInfoArr4[length] = fieldInfoArr2[i8];
                    length++;
                }
            }
            this.ordered = true;
        }
        return fieldInfoArr2;
    }

    static boolean addField(List<FieldInfo> list, FieldInfo fieldInfo, boolean z) {
        if (!z) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                FieldInfo fieldInfo2 = list.get(i);
                if (fieldInfo2.name.equals(fieldInfo.name) && (!fieldInfo2.getOnly || fieldInfo.getOnly)) {
                    return false;
                }
            }
        }
        list.add(fieldInfo);
        return true;
    }

    /* JADX WARNING: type inference failed for: r6v24 */
    /* JADX WARNING: type inference failed for: r37v0 */
    /* JADX WARNING: type inference failed for: r4v25, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r25v4 */
    /* JADX WARNING: type inference failed for: r22v15 */
    /* JADX WARNING: type inference failed for: r24v6 */
    /* JADX WARNING: type inference failed for: r4v34, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x0668, code lost:
        if ((java.util.Map.class.isAssignableFrom(r5) || java.util.Collection.class.isAssignableFrom(r5)) != false) goto L_0x066a;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r51v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x05e1  */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.fastjson.parser.JavaBeanInfo build(java.lang.Class r51, int r52, java.lang.reflect.Type r53, boolean r54, boolean r55, boolean r56, boolean r57, com.alibaba.fastjson.PropertyNamingStrategy r58) {
        /*
            r11 = r51
            r12 = r52
            r13 = r54
            r10 = r58
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            java.lang.reflect.Constructor[] r0 = r51.getDeclaredConstructors()
            boolean r1 = com.alibaba.fastjson.util.TypeUtils.isKotlin(r51)
            r2 = r12 & 1024(0x400, float:1.435E-42)
            r17 = 0
            r7 = 1
            r6 = 0
            if (r2 != 0) goto L_0x006d
            int r3 = r0.length
            if (r3 == r7) goto L_0x0027
            if (r1 != 0) goto L_0x006d
        L_0x0027:
            java.lang.Class[] r3 = new java.lang.Class[r6]     // Catch:{ Exception -> 0x002e }
            java.lang.reflect.Constructor r3 = r11.getDeclaredConstructor(r3)     // Catch:{ Exception -> 0x002e }
            goto L_0x0030
        L_0x002e:
            r3 = r17
        L_0x0030:
            if (r3 != 0) goto L_0x0066
            boolean r4 = r51.isMemberClass()
            if (r4 == 0) goto L_0x0066
            r4 = r12 & 8
            if (r4 != 0) goto L_0x0066
            int r4 = r0.length
            r5 = 0
        L_0x003e:
            if (r5 >= r4) goto L_0x0066
            r6 = r0[r5]
            r19 = r3
            java.lang.Class[] r3 = r6.getParameterTypes()
            r20 = r4
            int r4 = r3.length
            if (r4 != r7) goto L_0x005c
            r18 = 0
            r3 = r3[r18]
            java.lang.Class r4 = r51.getDeclaringClass()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x005e
            goto L_0x0071
        L_0x005c:
            r18 = 0
        L_0x005e:
            int r5 = r5 + 1
            r3 = r19
            r4 = r20
            r6 = 0
            goto L_0x003e
        L_0x0066:
            r19 = r3
            r18 = 0
            r6 = r19
            goto L_0x0071
        L_0x006d:
            r18 = 0
            r6 = r17
        L_0x0071:
            if (r13 == 0) goto L_0x0078
            r10 = r17
            r15 = r10
            goto L_0x00e1
        L_0x0078:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r4 = r11
            r5 = r17
        L_0x0080:
            if (r4 == 0) goto L_0x00d3
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            if (r4 == r7) goto L_0x00d3
            java.lang.reflect.Method[] r7 = r4.getDeclaredMethods()
            r22 = r5
            int r5 = r7.length
            r10 = 0
        L_0x008e:
            if (r10 >= r5) goto L_0x00c9
            r24 = r5
            r5 = r7[r10]
            r25 = r7
            int r7 = r5.getModifiers()
            r19 = r7 & 8
            if (r19 == 0) goto L_0x00b3
            java.lang.Class<com.alibaba.fastjson.annotation.JSONCreator> r7 = com.alibaba.fastjson.annotation.JSONCreator.class
            boolean r7 = r5.isAnnotationPresent(r7)
            if (r7 == 0) goto L_0x00c2
            if (r22 == 0) goto L_0x00b0
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r1 = "multi-json creator"
            r0.<init>(r1)
            throw r0
        L_0x00b0:
            r22 = r5
            goto L_0x00c2
        L_0x00b3:
            r19 = r7 & 2
            if (r19 != 0) goto L_0x00c2
            r15 = r7 & 256(0x100, float:3.59E-43)
            if (r15 != 0) goto L_0x00c2
            r7 = r7 & 4
            if (r7 != 0) goto L_0x00c2
            r3.add(r5)
        L_0x00c2:
            int r10 = r10 + 1
            r5 = r24
            r7 = r25
            goto L_0x008e
        L_0x00c9:
            java.lang.Class r4 = r4.getSuperclass()
            r5 = r22
            r7 = 1
            r10 = r58
            goto L_0x0080
        L_0x00d3:
            r22 = r5
            int r4 = r3.size()
            java.lang.reflect.Method[] r4 = new java.lang.reflect.Method[r4]
            r3.toArray(r4)
            r10 = r4
            r15 = r22
        L_0x00e1:
            java.lang.reflect.Field[] r7 = r51.getDeclaredFields()
            boolean r3 = r51.isInterface()
            if (r3 != 0) goto L_0x00f0
            if (r2 == 0) goto L_0x00ee
            goto L_0x00f0
        L_0x00ee:
            r2 = 0
            goto L_0x00f1
        L_0x00f0:
            r2 = 1
        L_0x00f1:
            if (r6 == 0) goto L_0x0106
            if (r2 == 0) goto L_0x00f6
            goto L_0x0106
        L_0x00f6:
            r22 = r7
            r30 = r10
            r40 = r15
            r18 = r17
            r24 = r18
            r15 = 1
            r7 = r6
            r10 = r8
            r8 = 0
            goto L_0x0405
        L_0x0106:
            int r3 = r0.length
            r4 = 0
        L_0x0108:
            if (r4 >= r3) goto L_0x011e
            r5 = r0[r4]
            r27 = r3
            java.lang.Class<com.alibaba.fastjson.annotation.JSONCreator> r3 = com.alibaba.fastjson.annotation.JSONCreator.class
            java.lang.annotation.Annotation r3 = r5.getAnnotation(r3)
            com.alibaba.fastjson.annotation.JSONCreator r3 = (com.alibaba.fastjson.annotation.JSONCreator) r3
            if (r3 == 0) goto L_0x0119
            goto L_0x0120
        L_0x0119:
            int r4 = r4 + 1
            r3 = r27
            goto L_0x0108
        L_0x011e:
            r5 = r17
        L_0x0120:
            if (r5 == 0) goto L_0x0200
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r5, r12)
            java.lang.Class[] r4 = r5.getParameterTypes()
            if (r57 == 0) goto L_0x0132
            java.lang.reflect.Type[] r0 = r5.getGenericParameterTypes()
            r19 = r0
            goto L_0x0134
        L_0x0132:
            r19 = r4
        L_0x0134:
            java.lang.annotation.Annotation[][] r20 = r5.getParameterAnnotations()
            r3 = 0
        L_0x0139:
            int r0 = r4.length
            if (r3 >= r0) goto L_0x01be
            r0 = r20[r3]
            int r1 = r0.length
            r2 = 0
        L_0x0140:
            if (r2 >= r1) goto L_0x0157
            r28 = r1
            r1 = r0[r2]
            r29 = r0
            boolean r0 = r1 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r0 == 0) goto L_0x0150
            r0 = r1
            com.alibaba.fastjson.annotation.JSONField r0 = (com.alibaba.fastjson.annotation.JSONField) r0
            goto L_0x0159
        L_0x0150:
            int r2 = r2 + 1
            r1 = r28
            r0 = r29
            goto L_0x0140
        L_0x0157:
            r0 = r17
        L_0x0159:
            if (r0 != 0) goto L_0x0163
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r1 = "illegal json creator"
            r0.<init>(r1)
            throw r0
        L_0x0163:
            r22 = r4[r3]
            r24 = r19[r3]
            java.lang.String r1 = r0.name()
            java.lang.reflect.Field r2 = com.alibaba.fastjson.util.TypeUtils.getField(r11, r1, r7, r8)
            if (r2 == 0) goto L_0x0174
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r2, r12)
        L_0x0174:
            int r25 = r0.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r0.serialzeFeatures()
            int r27 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.util.FieldInfo r1 = new com.alibaba.fastjson.util.FieldInfo
            java.lang.String r28 = r0.name()
            r0 = r1
            r30 = r10
            r10 = r1
            r1 = r28
            r28 = r2
            r2 = r11
            r29 = r3
            r3 = r22
            r22 = r4
            r4 = r24
            r24 = r5
            r5 = r28
            r31 = r6
            r32 = r8
            r8 = 0
            r6 = r25
            r33 = r7
            r7 = r27
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            addField(r9, r10, r13)
            int r3 = r29 + 1
            r4 = r22
            r5 = r24
            r10 = r30
            r6 = r31
            r8 = r32
            r7 = r33
            r18 = 0
            goto L_0x0139
        L_0x01be:
            r24 = r5
            r31 = r6
            r33 = r7
            r32 = r8
            r30 = r10
            r8 = 0
            int r0 = r9.size()
            com.alibaba.fastjson.util.FieldInfo[] r0 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r9.toArray(r0)
            int r1 = r0.length
            com.alibaba.fastjson.util.FieldInfo[] r1 = new com.alibaba.fastjson.util.FieldInfo[r1]
            int r2 = r0.length
            java.lang.System.arraycopy(r0, r8, r1, r8, r2)
            java.util.Arrays.sort(r1)
            if (r55 == 0) goto L_0x01e3
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r1 = com.alibaba.fastjson.annotation.JSONType.class
            r11.getAnnotation(r1)
        L_0x01e3:
            int r1 = r0.length
            java.lang.String[] r1 = new java.lang.String[r1]
            r2 = 0
        L_0x01e7:
            int r3 = r0.length
            if (r2 >= r3) goto L_0x01f3
            r3 = r0[r2]
            java.lang.String r3 = r3.name
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x01e7
        L_0x01f3:
            r18 = r1
            r40 = r15
            r7 = r31
            r10 = r32
            r22 = r33
            r15 = 1
            goto L_0x0405
        L_0x0200:
            r24 = r5
            r31 = r6
            r33 = r7
            r32 = r8
            r30 = r10
            r8 = 0
            if (r15 == 0) goto L_0x02c9
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r15, r12)
            java.lang.Class[] r10 = r15.getParameterTypes()
            int r0 = r10.length
            if (r0 <= 0) goto L_0x02c1
            if (r57 == 0) goto L_0x021f
            java.lang.reflect.Type[] r0 = r15.getGenericParameterTypes()
            r12 = r0
            goto L_0x0220
        L_0x021f:
            r12 = r10
        L_0x0220:
            java.lang.annotation.Annotation[][] r16 = r15.getParameterAnnotations()
            r7 = 0
        L_0x0225:
            int r0 = r10.length
            if (r7 >= r0) goto L_0x028a
            r0 = r16[r7]
            int r1 = r0.length
            r2 = 0
        L_0x022c:
            if (r2 >= r1) goto L_0x023b
            r3 = r0[r2]
            boolean r4 = r3 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r4 == 0) goto L_0x0238
            r0 = r3
            com.alibaba.fastjson.annotation.JSONField r0 = (com.alibaba.fastjson.annotation.JSONField) r0
            goto L_0x023d
        L_0x0238:
            int r2 = r2 + 1
            goto L_0x022c
        L_0x023b:
            r0 = r17
        L_0x023d:
            if (r0 != 0) goto L_0x0247
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r1 = "illegal json creator"
            r0.<init>(r1)
            throw r0
        L_0x0247:
            r3 = r10[r7]
            r4 = r12[r7]
            java.lang.String r1 = r0.name()
            r6 = r32
            r5 = r33
            java.lang.reflect.Field r18 = com.alibaba.fastjson.util.TypeUtils.getField(r11, r1, r5, r6)
            int r19 = r0.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r0.serialzeFeatures()
            int r20 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            com.alibaba.fastjson.util.FieldInfo r2 = new com.alibaba.fastjson.util.FieldInfo
            java.lang.String r1 = r0.name()
            r0 = r2
            r8 = r2
            r2 = r11
            r34 = r10
            r10 = r5
            r5 = r18
            r35 = r10
            r10 = r6
            r6 = r19
            r18 = r7
            r7 = r20
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            addField(r9, r8, r13)
            int r7 = r18 + 1
            r32 = r10
            r10 = r34
            r33 = r35
            r8 = 0
            goto L_0x0225
        L_0x028a:
            int r0 = r9.size()
            com.alibaba.fastjson.util.FieldInfo[] r5 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r9.toArray(r5)
            int r0 = r5.length
            com.alibaba.fastjson.util.FieldInfo[] r0 = new com.alibaba.fastjson.util.FieldInfo[r0]
            int r1 = r5.length
            r2 = 0
            java.lang.System.arraycopy(r5, r2, r0, r2, r1)
            java.util.Arrays.sort(r0)
            boolean r1 = java.util.Arrays.equals(r5, r0)
            if (r1 == 0) goto L_0x02a6
            r6 = r5
            goto L_0x02a7
        L_0x02a6:
            r6 = r0
        L_0x02a7:
            if (r55 == 0) goto L_0x02b3
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r0 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r0 = r11.getAnnotation(r0)
            com.alibaba.fastjson.annotation.JSONType r0 = (com.alibaba.fastjson.annotation.JSONType) r0
            r7 = r0
            goto L_0x02b5
        L_0x02b3:
            r7 = r17
        L_0x02b5:
            com.alibaba.fastjson.parser.JavaBeanInfo r9 = new com.alibaba.fastjson.parser.JavaBeanInfo
            r2 = 0
            r3 = 0
            r8 = 0
            r0 = r9
            r1 = r11
            r4 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x02c1:
            r10 = r32
            r40 = r15
            r22 = r33
            goto L_0x0400
        L_0x02c9:
            r10 = r32
            r35 = r33
            if (r2 != 0) goto L_0x03fb
            if (r1 == 0) goto L_0x03eb
            int r1 = r0.length
            if (r1 <= 0) goto L_0x03eb
            java.lang.String[] r8 = com.alibaba.fastjson.util.TypeUtils.getKoltinConstructorParameters(r51)
            if (r8 == 0) goto L_0x03db
            int r1 = r0.length
            r7 = r24
            r2 = 0
        L_0x02de:
            if (r2 >= r1) goto L_0x0308
            r3 = r0[r2]
            java.lang.Class[] r4 = r3.getParameterTypes()
            int r5 = r4.length
            if (r5 <= 0) goto L_0x02fa
            int r5 = r4.length
            r6 = 1
            int r5 = r5 - r6
            r5 = r4[r5]
            java.lang.String r5 = r5.getName()
            java.lang.String r6 = "kotlin.jvm.internal.DefaultConstructorMarker"
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x0305
        L_0x02fa:
            if (r7 == 0) goto L_0x0304
            java.lang.Class[] r5 = r7.getParameterTypes()
            int r5 = r5.length
            int r4 = r4.length
            if (r5 >= r4) goto L_0x0305
        L_0x0304:
            r7 = r3
        L_0x0305:
            int r2 = r2 + 1
            goto L_0x02de
        L_0x0308:
            r6 = 1
            r7.setAccessible(r6)
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r7, r12)
            java.lang.Class[] r5 = r7.getParameterTypes()
            if (r57 == 0) goto L_0x031c
            java.lang.reflect.Type[] r0 = r7.getGenericParameterTypes()
            r18 = r0
            goto L_0x031e
        L_0x031c:
            r18 = r5
        L_0x031e:
            java.lang.annotation.Annotation[][] r19 = r7.getParameterAnnotations()
            r4 = 0
        L_0x0323:
            int r0 = r5.length
            if (r4 >= r0) goto L_0x03ab
            r0 = r8[r4]
            r1 = r19[r4]
            int r2 = r1.length
            r3 = 0
        L_0x032c:
            if (r3 >= r2) goto L_0x0340
            r6 = r1[r3]
            r36 = r1
            boolean r1 = r6 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r1 == 0) goto L_0x033a
            r1 = r6
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            goto L_0x0342
        L_0x033a:
            int r3 = r3 + 1
            r1 = r36
            r6 = 1
            goto L_0x032c
        L_0x0340:
            r1 = r17
        L_0x0342:
            r3 = r5[r4]
            r6 = r18[r4]
            r37 = r5
            r2 = r35
            java.lang.reflect.Field r5 = com.alibaba.fastjson.util.TypeUtils.getField(r11, r0, r2, r10)
            if (r5 == 0) goto L_0x035a
            if (r1 != 0) goto L_0x035a
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r5.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
        L_0x035a:
            if (r1 == 0) goto L_0x037b
            int r20 = r1.ordinal()
            r38 = r0
            com.alibaba.fastjson.serializer.SerializerFeature[] r0 = r1.serialzeFeatures()
            int r0 = com.alibaba.fastjson.serializer.SerializerFeature.of(r0)
            java.lang.String r1 = r1.name()
            int r21 = r1.length()
            if (r21 == 0) goto L_0x0376
            r38 = r1
        L_0x0376:
            r21 = r0
            r1 = r38
            goto L_0x0383
        L_0x037b:
            r38 = r0
            r1 = r38
            r20 = 0
            r21 = 0
        L_0x0383:
            com.alibaba.fastjson.util.FieldInfo r0 = new com.alibaba.fastjson.util.FieldInfo
            r39 = r0
            r22 = r2
            r2 = r11
            r24 = r4
            r4 = r6
            r25 = r37
            r40 = r15
            r15 = 1
            r6 = r20
            r20 = r7
            r7 = r21
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            addField(r9, r0, r13)
            int r4 = r24 + 1
            r7 = r20
            r35 = r22
            r5 = r25
            r15 = r40
            r6 = 1
            goto L_0x0323
        L_0x03ab:
            r20 = r7
            r40 = r15
            r22 = r35
            r15 = 1
            int r0 = r9.size()
            com.alibaba.fastjson.util.FieldInfo[] r0 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r9.toArray(r0)
            int r1 = r0.length
            com.alibaba.fastjson.util.FieldInfo[] r1 = new com.alibaba.fastjson.util.FieldInfo[r1]
            int r2 = r0.length
            r8 = 0
            java.lang.System.arraycopy(r0, r8, r1, r8, r2)
            java.util.Arrays.sort(r1)
            int r1 = r0.length
            java.lang.String[] r1 = new java.lang.String[r1]
            r2 = 0
        L_0x03ca:
            int r3 = r0.length
            if (r2 >= r3) goto L_0x03d6
            r3 = r0[r2]
            java.lang.String r3 = r3.name
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x03ca
        L_0x03d6:
            r18 = r1
            r24 = r20
            goto L_0x0403
        L_0x03db:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r1 = "default constructor not found. "
            java.lang.String r2 = java.lang.String.valueOf(r51)
            java.lang.String r1 = r1.concat(r2)
            r0.<init>(r1)
            throw r0
        L_0x03eb:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.String r1 = "default constructor not found. "
            java.lang.String r2 = java.lang.String.valueOf(r51)
            java.lang.String r1 = r1.concat(r2)
            r0.<init>(r1)
            throw r0
        L_0x03fb:
            r40 = r15
            r22 = r35
            r8 = 0
        L_0x0400:
            r15 = 1
            r18 = r17
        L_0x0403:
            r7 = r31
        L_0x0405:
            if (r7 == 0) goto L_0x040a
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r7, r12)
        L_0x040a:
            r5 = 4
            if (r13 != 0) goto L_0x062f
            r4 = r30
            int r3 = r4.length
            r2 = 0
        L_0x0411:
            if (r2 >= r3) goto L_0x0627
            r1 = r4[r2]
            java.lang.String r0 = r1.getName()
            int r6 = r0.length()
            if (r6 < r5) goto L_0x0608
            java.lang.Class r6 = r1.getReturnType()
            java.lang.Class r5 = java.lang.Void.TYPE
            if (r6 == r5) goto L_0x042d
            java.lang.Class r5 = r1.getDeclaringClass()
            if (r6 != r5) goto L_0x0608
        L_0x042d:
            java.lang.Class[] r5 = r1.getParameterTypes()
            int r5 = r5.length
            if (r5 != r15) goto L_0x0608
            if (r56 == 0) goto L_0x043f
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r5 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r5 = r1.getAnnotation(r5)
            com.alibaba.fastjson.annotation.JSONField r5 = (com.alibaba.fastjson.annotation.JSONField) r5
            goto L_0x0441
        L_0x043f:
            r5 = r17
        L_0x0441:
            if (r5 != 0) goto L_0x0449
            if (r56 == 0) goto L_0x0449
            com.alibaba.fastjson.annotation.JSONField r5 = com.alibaba.fastjson.util.TypeUtils.getSupperMethodAnnotation(r11, r1)
        L_0x0449:
            r6 = r5
            if (r6 == 0) goto L_0x04b9
            boolean r5 = r6.deserialize()
            if (r5 == 0) goto L_0x0608
            int r20 = r6.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r5 = r6.serialzeFeatures()
            int r21 = com.alibaba.fastjson.serializer.SerializerFeature.of(r5)
            java.lang.String r5 = r6.name()
            int r5 = r5.length()
            if (r5 == 0) goto L_0x04a8
            java.lang.String r5 = r6.name()
            com.alibaba.fastjson.util.FieldInfo r0 = new com.alibaba.fastjson.util.FieldInfo
            r25 = 0
            r26 = 0
            r41 = r0
            r42 = r1
            r1 = r5
            r27 = r2
            r2 = r42
            r28 = r3
            r3 = r25
            r25 = r4
            r4 = r11
            r15 = 4
            r5 = r53
            r29 = r6
            r15 = 3
            r6 = r20
            r30 = r7
            r7 = r21
            r8 = r29
            r15 = r9
            r9 = r26
            r44 = r10
            r14 = r22
            r45 = r25
            r10 = r57
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            addField(r15, r0, r13)
            r10 = r42
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r10, r12)
            goto L_0x0603
        L_0x04a8:
            r27 = r2
            r28 = r3
            r45 = r4
            r29 = r6
            r30 = r7
            r15 = r9
            r44 = r10
            r14 = r22
            r10 = r1
            goto L_0x04cd
        L_0x04b9:
            r27 = r2
            r28 = r3
            r45 = r4
            r29 = r6
            r30 = r7
            r15 = r9
            r44 = r10
            r14 = r22
            r10 = r1
            r20 = 0
            r21 = 0
        L_0x04cd:
            java.lang.String r1 = "set"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x0603
            r1 = 3
            char r2 = r0.charAt(r1)
            boolean r3 = java.lang.Character.isUpperCase(r2)
            if (r3 == 0) goto L_0x050d
            boolean r2 = com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean
            if (r2 == 0) goto L_0x04f0
            java.lang.String r0 = r0.substring(r1)
            java.lang.String r0 = com.alibaba.fastjson.util.TypeUtils.decapitalize(r0)
        L_0x04ed:
            r9 = r44
            goto L_0x053c
        L_0x04f0:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            char r3 = r0.charAt(r1)
            char r1 = java.lang.Character.toLowerCase(r3)
            r2.append(r1)
            r1 = 4
            java.lang.String r0 = r0.substring(r1)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            goto L_0x04ed
        L_0x050d:
            r1 = 4
            r3 = 95
            if (r2 != r3) goto L_0x0517
            java.lang.String r0 = r0.substring(r1)
            goto L_0x04ed
        L_0x0517:
            r3 = 102(0x66, float:1.43E-43)
            if (r2 != r3) goto L_0x0521
            r2 = 3
            java.lang.String r0 = r0.substring(r2)
            goto L_0x04ed
        L_0x0521:
            r2 = 3
            int r3 = r0.length()
            r4 = 5
            if (r3 < r4) goto L_0x0603
            char r3 = r0.charAt(r1)
            boolean r1 = java.lang.Character.isUpperCase(r3)
            if (r1 == 0) goto L_0x0603
            java.lang.String r0 = r0.substring(r2)
            java.lang.String r0 = com.alibaba.fastjson.util.TypeUtils.decapitalize(r0)
            goto L_0x04ed
        L_0x053c:
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r11, r0, r14, r9)
            if (r1 != 0) goto L_0x0570
            java.lang.Class[] r2 = r10.getParameterTypes()
            r8 = 0
            r2 = r2[r8]
            java.lang.Class r3 = java.lang.Boolean.TYPE
            if (r2 != r3) goto L_0x0571
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "is"
            r1.<init>(r2)
            char r2 = r0.charAt(r8)
            char r2 = java.lang.Character.toUpperCase(r2)
            r1.append(r2)
            r2 = 1
            java.lang.String r3 = r0.substring(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.reflect.Field r1 = com.alibaba.fastjson.util.TypeUtils.getField(r11, r1, r14, r9)
            goto L_0x0571
        L_0x0570:
            r8 = 0
        L_0x0571:
            r3 = r1
            if (r3 == 0) goto L_0x05d1
            if (r56 == 0) goto L_0x0580
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r3.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            r7 = r1
            goto L_0x0582
        L_0x0580:
            r7 = r17
        L_0x0582:
            if (r7 == 0) goto L_0x05d1
            int r6 = r7.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r1 = r7.serialzeFeatures()
            int r20 = com.alibaba.fastjson.serializer.SerializerFeature.of(r1)
            java.lang.String r1 = r7.name()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x05bf
            java.lang.String r1 = r7.name()
            com.alibaba.fastjson.util.FieldInfo r5 = new com.alibaba.fastjson.util.FieldInfo
            r0 = r5
            r2 = r10
            r4 = r11
            r10 = r5
            r5 = r53
            r21 = r7
            r7 = r20
            r8 = r29
            r22 = r9
            r9 = r21
            r46 = r14
            r14 = r10
            r10 = r57
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            addField(r15, r14, r13)
            r20 = r22
            goto L_0x0615
        L_0x05bf:
            r21 = r7
            r22 = r9
            r46 = r14
            if (r29 != 0) goto L_0x05ce
            r7 = r20
            r8 = r21
            r20 = r22
            goto L_0x05dd
        L_0x05ce:
            r7 = r20
            goto L_0x05d9
        L_0x05d1:
            r22 = r9
            r46 = r14
            r6 = r20
            r7 = r21
        L_0x05d9:
            r20 = r22
            r8 = r29
        L_0x05dd:
            r14 = r58
            if (r14 == 0) goto L_0x05e5
            java.lang.String r0 = r14.translate(r0)
        L_0x05e5:
            r1 = r0
            com.alibaba.fastjson.util.FieldInfo r9 = new com.alibaba.fastjson.util.FieldInfo
            r3 = 0
            r21 = 0
            r0 = r9
            r2 = r10
            r4 = r11
            r5 = r53
            r47 = r9
            r9 = r21
            r14 = r10
            r10 = r57
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r0 = r47
            addField(r15, r0, r13)
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r14, r12)
            goto L_0x0615
        L_0x0603:
            r46 = r14
            r20 = r44
            goto L_0x0615
        L_0x0608:
            r27 = r2
            r28 = r3
            r45 = r4
            r30 = r7
            r15 = r9
            r20 = r10
            r46 = r22
        L_0x0615:
            int r2 = r27 + 1
            r9 = r15
            r10 = r20
            r3 = r28
            r7 = r30
            r4 = r45
            r22 = r46
            r5 = 4
            r8 = 0
            r15 = 1
            goto L_0x0411
        L_0x0627:
            r45 = r4
            r30 = r7
            r15 = r9
            r46 = r22
            goto L_0x0636
        L_0x062f:
            r15 = r9
            r46 = r22
            r45 = r30
            r30 = r7
        L_0x0636:
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r46
            int r2 = r1.length
            r0.<init>(r2)
            int r2 = r1.length
            r3 = 0
        L_0x0640:
            if (r3 >= r2) goto L_0x067d
            r4 = r1[r3]
            int r5 = r4.getModifiers()
            r6 = r5 & 8
            if (r6 != 0) goto L_0x0678
            r5 = r5 & 16
            if (r5 == 0) goto L_0x066a
            java.lang.Class r5 = r4.getType()
            java.lang.Class<java.util.Map> r6 = java.util.Map.class
            boolean r6 = r6.isAssignableFrom(r5)
            if (r6 != 0) goto L_0x0667
            java.lang.Class<java.util.Collection> r6 = java.util.Collection.class
            boolean r5 = r6.isAssignableFrom(r5)
            if (r5 == 0) goto L_0x0665
            goto L_0x0667
        L_0x0665:
            r5 = 0
            goto L_0x0668
        L_0x0667:
            r5 = 1
        L_0x0668:
            if (r5 == 0) goto L_0x0678
        L_0x066a:
            int r5 = r4.getModifiers()
            r43 = 1
            r5 = r5 & 1
            if (r5 == 0) goto L_0x067a
            r0.add(r4)
            goto L_0x067a
        L_0x0678:
            r43 = 1
        L_0x067a:
            int r3 = r3 + 1
            goto L_0x0640
        L_0x067d:
            r43 = 1
            java.lang.Class r1 = r51.getSuperclass()
        L_0x0683:
            if (r1 == 0) goto L_0x06c8
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r1 == r2) goto L_0x06c8
            java.lang.reflect.Field[] r2 = r1.getDeclaredFields()
            int r3 = r2.length
            r4 = 0
        L_0x068f:
            if (r4 >= r3) goto L_0x06c3
            r5 = r2[r4]
            int r6 = r5.getModifiers()
            r7 = r6 & 8
            if (r7 != 0) goto L_0x06c0
            r7 = r6 & 16
            if (r7 == 0) goto L_0x06b9
            java.lang.Class r7 = r5.getType()
            java.lang.Class<java.util.Map> r8 = java.util.Map.class
            boolean r8 = r8.isAssignableFrom(r7)
            if (r8 != 0) goto L_0x06b6
            java.lang.Class<java.util.Collection> r8 = java.util.Collection.class
            boolean r7 = r8.isAssignableFrom(r7)
            if (r7 == 0) goto L_0x06b4
            goto L_0x06b6
        L_0x06b4:
            r7 = 0
            goto L_0x06b7
        L_0x06b6:
            r7 = 1
        L_0x06b7:
            if (r7 == 0) goto L_0x06c0
        L_0x06b9:
            r6 = r6 & 1
            if (r6 == 0) goto L_0x06c0
            r0.add(r5)
        L_0x06c0:
            int r4 = r4 + 1
            goto L_0x068f
        L_0x06c3:
            java.lang.Class r1 = r1.getSuperclass()
            goto L_0x0683
        L_0x06c8:
            java.util.Iterator r14 = r0.iterator()
        L_0x06cc:
            boolean r0 = r14.hasNext()
            if (r0 == 0) goto L_0x074c
            java.lang.Object r0 = r14.next()
            r3 = r0
            java.lang.reflect.Field r3 = (java.lang.reflect.Field) r3
            java.lang.String r0 = r3.getName()
            int r1 = r15.size()
            r2 = 0
            r7 = 0
        L_0x06e3:
            if (r2 >= r1) goto L_0x06f7
            java.lang.Object r4 = r15.get(r2)
            com.alibaba.fastjson.util.FieldInfo r4 = (com.alibaba.fastjson.util.FieldInfo) r4
            java.lang.String r4 = r4.name
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x06f4
            r7 = 1
        L_0x06f4:
            int r2 = r2 + 1
            goto L_0x06e3
        L_0x06f7:
            if (r7 != 0) goto L_0x06cc
            if (r56 == 0) goto L_0x0705
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r3.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            r9 = r1
            goto L_0x0707
        L_0x0705:
            r9 = r17
        L_0x0707:
            if (r9 == 0) goto L_0x0726
            int r1 = r9.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r2 = r9.serialzeFeatures()
            int r2 = com.alibaba.fastjson.serializer.SerializerFeature.of(r2)
            java.lang.String r4 = r9.name()
            int r4 = r4.length()
            if (r4 == 0) goto L_0x0723
            java.lang.String r0 = r9.name()
        L_0x0723:
            r6 = r1
            r7 = r2
            goto L_0x0728
        L_0x0726:
            r6 = 0
            r7 = 0
        L_0x0728:
            r10 = r58
            if (r10 == 0) goto L_0x0730
            java.lang.String r0 = r10.translate(r0)
        L_0x0730:
            r1 = r0
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r3, r12)
            com.alibaba.fastjson.util.FieldInfo r8 = new com.alibaba.fastjson.util.FieldInfo
            r2 = 0
            r20 = 0
            r0 = r8
            r4 = r11
            r5 = r53
            r48 = r8
            r8 = r20
            r10 = r57
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r0 = r48
            addField(r15, r0, r13)
            goto L_0x06cc
        L_0x074c:
            if (r13 != 0) goto L_0x080c
            r14 = r45
            int r10 = r14.length
            r9 = 0
        L_0x0752:
            if (r9 >= r10) goto L_0x080c
            r8 = r14[r9]
            java.lang.String r0 = r8.getName()
            int r1 = r0.length()
            r2 = 4
            if (r1 < r2) goto L_0x07fe
            java.lang.String r1 = "get"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x07fe
            r1 = 3
            char r2 = r0.charAt(r1)
            boolean r1 = java.lang.Character.isUpperCase(r2)
            if (r1 == 0) goto L_0x07fe
            java.lang.Class[] r1 = r8.getParameterTypes()
            int r1 = r1.length
            if (r1 != 0) goto L_0x07fe
            java.lang.Class r1 = r8.getReturnType()
            java.lang.Class<java.util.Collection> r2 = java.util.Collection.class
            boolean r2 = r2.isAssignableFrom(r1)
            if (r2 != 0) goto L_0x078f
            java.lang.Class<java.util.Map> r2 = java.util.Map.class
            boolean r1 = r2.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x07fe
        L_0x078f:
            if (r56 == 0) goto L_0x079b
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = r8.getAnnotation(r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            r7 = r1
            goto L_0x079d
        L_0x079b:
            r7 = r17
        L_0x079d:
            if (r7 == 0) goto L_0x07ac
            java.lang.String r1 = r7.name()
            int r2 = r1.length()
            if (r2 <= 0) goto L_0x07ac
            r5 = 4
            r6 = 3
            goto L_0x07ca
        L_0x07ac:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r6 = 3
            char r2 = r0.charAt(r6)
            char r2 = java.lang.Character.toLowerCase(r2)
            r1.append(r2)
            r5 = 4
            java.lang.String r0 = r0.substring(r5)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = r0
        L_0x07ca:
            com.alibaba.fastjson.util.FieldInfo r4 = new com.alibaba.fastjson.util.FieldInfo
            r3 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r0 = r4
            r2 = r8
            r49 = r4
            r4 = r11
            r23 = 4
            r5 = r53
            r25 = 3
            r6 = r20
            r20 = r7
            r7 = r21
            r50 = r8
            r8 = r20
            r20 = r9
            r9 = r22
            r21 = r10
            r10 = r57
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r0 = r49
            addField(r15, r0, r13)
            r0 = r50
            com.alibaba.fastjson.util.TypeUtils.setAccessible(r11, r0, r12)
            goto L_0x0806
        L_0x07fe:
            r20 = r9
            r21 = r10
            r23 = 4
            r25 = 3
        L_0x0806:
            int r9 = r20 + 1
            r10 = r21
            goto L_0x0752
        L_0x080c:
            int r0 = r15.size()
            com.alibaba.fastjson.util.FieldInfo[] r5 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r15.toArray(r5)
            int r0 = r5.length
            com.alibaba.fastjson.util.FieldInfo[] r6 = new com.alibaba.fastjson.util.FieldInfo[r0]
            int r0 = r5.length
            r1 = 0
            java.lang.System.arraycopy(r5, r1, r6, r1, r0)
            java.util.Arrays.sort(r6)
            if (r55 == 0) goto L_0x082c
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r0 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r0 = r11.getAnnotation(r0)
            com.alibaba.fastjson.annotation.JSONType r0 = (com.alibaba.fastjson.annotation.JSONType) r0
            r7 = r0
            goto L_0x082e
        L_0x082c:
            r7 = r17
        L_0x082e:
            com.alibaba.fastjson.parser.JavaBeanInfo r9 = new com.alibaba.fastjson.parser.JavaBeanInfo
            r0 = r9
            r1 = r11
            r2 = r30
            r3 = r24
            r4 = r40
            r8 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JavaBeanInfo.build(java.lang.Class, int, java.lang.reflect.Type, boolean, boolean, boolean, boolean, com.alibaba.fastjson.PropertyNamingStrategy):com.alibaba.fastjson.parser.JavaBeanInfo");
    }
}
