package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.PropertyNamingStrategy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JavaBeanSerializer implements ObjectSerializer {
    private static final char[] false_chars = {'f', 'a', 'l', 's', 'e'};
    private static final char[] true_chars = {'t', 'r', 'u', 'e'};
    protected int features;
    private final FieldSerializer[] getters;
    private final FieldSerializer[] sortedGetters;
    protected final String typeKey;
    protected final String typeName;

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (PropertyNamingStrategy) null);
    }

    public JavaBeanSerializer(Class<?> cls, PropertyNamingStrategy propertyNamingStrategy) {
        this(cls, cls.getModifiers(), null, false, true, true, true, propertyNamingStrategy);
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, cls.getModifiers(), map(strArr), false, true, true, true, null);
    }

    private static Map<String, String> map(String... strArr) {
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            hashMap.put(str, str);
        }
        return hashMap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007f, code lost:
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        r14 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public JavaBeanSerializer(java.lang.Class<?> r16, int r17, java.util.Map<java.lang.String, java.lang.String> r18, boolean r19, boolean r20, boolean r21, boolean r22, com.alibaba.fastjson.PropertyNamingStrategy r23) {
        /*
            r15 = this;
            r0 = r15
            r0.<init>()
            r1 = 0
            r0.features = r1
            r2 = 0
            if (r20 == 0) goto L_0x0015
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r3 = com.alibaba.fastjson.annotation.JSONType.class
            r13 = r16
            java.lang.annotation.Annotation r3 = r13.getAnnotation(r3)
            com.alibaba.fastjson.annotation.JSONType r3 = (com.alibaba.fastjson.annotation.JSONType) r3
            goto L_0x0018
        L_0x0015:
            r13 = r16
            r3 = r2
        L_0x0018:
            if (r3 == 0) goto L_0x0090
            com.alibaba.fastjson.serializer.SerializerFeature[] r4 = r3.serialzeFeatures()
            int r4 = com.alibaba.fastjson.serializer.SerializerFeature.of(r4)
            r0.features = r4
            java.lang.String r4 = r3.typeName()
            int r5 = r4.length()
            if (r5 != 0) goto L_0x0031
            r5 = r2
            r8 = r5
            goto L_0x0081
        L_0x0031:
            java.lang.Class r5 = r16.getSuperclass()
            r6 = r2
        L_0x0036:
            if (r5 == 0) goto L_0x0055
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            if (r5 == r7) goto L_0x0055
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r7 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r7 = r5.getAnnotation(r7)
            com.alibaba.fastjson.annotation.JSONType r7 = (com.alibaba.fastjson.annotation.JSONType) r7
            if (r7 == 0) goto L_0x0055
            java.lang.String r6 = r7.typeKey()
            int r7 = r6.length()
            if (r7 != 0) goto L_0x0055
            java.lang.Class r5 = r5.getSuperclass()
            goto L_0x0036
        L_0x0055:
            java.lang.Class[] r5 = r16.getInterfaces()
            int r7 = r5.length
            r8 = r6
            r6 = 0
        L_0x005c:
            if (r6 >= r7) goto L_0x0077
            r9 = r5[r6]
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r10 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r9 = r9.getAnnotation(r10)
            com.alibaba.fastjson.annotation.JSONType r9 = (com.alibaba.fastjson.annotation.JSONType) r9
            if (r9 == 0) goto L_0x0074
            java.lang.String r8 = r9.typeKey()
            int r9 = r8.length()
            if (r9 != 0) goto L_0x0077
        L_0x0074:
            int r6 = r6 + 1
            goto L_0x005c
        L_0x0077:
            if (r8 == 0) goto L_0x0080
            int r5 = r8.length()
            if (r5 != 0) goto L_0x0080
            r8 = r2
        L_0x0080:
            r5 = r4
        L_0x0081:
            if (r23 != 0) goto L_0x008d
            com.alibaba.fastjson.PropertyNamingStrategy r6 = r3.naming()
            com.alibaba.fastjson.PropertyNamingStrategy r7 = com.alibaba.fastjson.PropertyNamingStrategy.CamelCase
            if (r6 == r7) goto L_0x008d
            r14 = r6
            goto L_0x0094
        L_0x008d:
            r14 = r23
            goto L_0x0094
        L_0x0090:
            r14 = r23
            r5 = r2
            r8 = r5
        L_0x0094:
            r0.typeName = r5
            r0.typeKey = r8
            r9 = 0
            r4 = r13
            r5 = r17
            r6 = r19
            r7 = r3
            r8 = r18
            r10 = r21
            r11 = r22
            r12 = r14
            java.util.List r4 = com.alibaba.fastjson.util.TypeUtils.computeGetters(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r4 = r4.iterator()
        L_0x00b3:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x00c8
            java.lang.Object r6 = r4.next()
            com.alibaba.fastjson.util.FieldInfo r6 = (com.alibaba.fastjson.util.FieldInfo) r6
            com.alibaba.fastjson.serializer.FieldSerializer r7 = new com.alibaba.fastjson.serializer.FieldSerializer
            r7.<init>(r6)
            r5.add(r7)
            goto L_0x00b3
        L_0x00c8:
            int r4 = r5.size()
            com.alibaba.fastjson.serializer.FieldSerializer[] r4 = new com.alibaba.fastjson.serializer.FieldSerializer[r4]
            java.lang.Object[] r4 = r5.toArray(r4)
            com.alibaba.fastjson.serializer.FieldSerializer[] r4 = (com.alibaba.fastjson.serializer.FieldSerializer[]) r4
            r0.getters = r4
            if (r3 == 0) goto L_0x00dc
            java.lang.String[] r2 = r3.orders()
        L_0x00dc:
            if (r2 == 0) goto L_0x0120
            int r2 = r2.length
            if (r2 == 0) goto L_0x0120
            r9 = 1
            r4 = r13
            r5 = r17
            r6 = r19
            r7 = r3
            r8 = r18
            r10 = r21
            r11 = r22
            r12 = r14
            java.util.List r1 = com.alibaba.fastjson.util.TypeUtils.computeGetters(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x00fc:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0111
            java.lang.Object r3 = r1.next()
            com.alibaba.fastjson.util.FieldInfo r3 = (com.alibaba.fastjson.util.FieldInfo) r3
            com.alibaba.fastjson.serializer.FieldSerializer r4 = new com.alibaba.fastjson.serializer.FieldSerializer
            r4.<init>(r3)
            r2.add(r4)
            goto L_0x00fc
        L_0x0111:
            int r1 = r2.size()
            com.alibaba.fastjson.serializer.FieldSerializer[] r1 = new com.alibaba.fastjson.serializer.FieldSerializer[r1]
            java.lang.Object[] r1 = r2.toArray(r1)
            com.alibaba.fastjson.serializer.FieldSerializer[] r1 = (com.alibaba.fastjson.serializer.FieldSerializer[]) r1
            r0.sortedGetters = r1
            return
        L_0x0120:
            com.alibaba.fastjson.serializer.FieldSerializer[] r2 = r0.getters
            int r2 = r2.length
            com.alibaba.fastjson.serializer.FieldSerializer[] r2 = new com.alibaba.fastjson.serializer.FieldSerializer[r2]
            com.alibaba.fastjson.serializer.FieldSerializer[] r3 = r0.getters
            com.alibaba.fastjson.serializer.FieldSerializer[] r4 = r0.getters
            int r4 = r4.length
            java.lang.System.arraycopy(r3, r1, r2, r1, r4)
            java.util.Arrays.sort(r2)
            com.alibaba.fastjson.serializer.FieldSerializer[] r1 = r0.getters
            boolean r1 = java.util.Arrays.equals(r2, r1)
            if (r1 == 0) goto L_0x013d
            com.alibaba.fastjson.serializer.FieldSerializer[] r1 = r0.getters
            r0.sortedGetters = r1
            return
        L_0x013d:
            r0.sortedGetters = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.<init>(java.lang.Class, int, java.util.Map, boolean, boolean, boolean, boolean, com.alibaba.fastjson.PropertyNamingStrategy):void");
    }

    /* JADX WARNING: type inference failed for: r43v0, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r2v0, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r5v1, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r5v11, types: [java.util.List<com.alibaba.fastjson.serializer.ValueFilter>] */
    /* JADX WARNING: type inference failed for: r5v12, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r2v5, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: type inference failed for: r5v19 */
    /* JADX WARNING: type inference failed for: r5v20 */
    /* JADX WARNING: type inference failed for: r5v21 */
    /* JADX WARNING: type inference failed for: r5v22 */
    /* JADX WARNING: type inference failed for: r5v23 */
    /* JADX WARNING: type inference failed for: r36v0 */
    /* JADX WARNING: type inference failed for: r5v24 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r5v25 */
    /* JADX WARNING: type inference failed for: r36v1 */
    /* JADX WARNING: type inference failed for: r5v26 */
    /* JADX WARNING: type inference failed for: r36v2 */
    /* JADX WARNING: type inference failed for: r5v27 */
    /* JADX WARNING: type inference failed for: r36v3 */
    /* JADX WARNING: type inference failed for: r5v28 */
    /* JADX WARNING: type inference failed for: r36v4 */
    /* JADX WARNING: type inference failed for: r5v29 */
    /* JADX WARNING: type inference failed for: r5v30 */
    /* JADX WARNING: type inference failed for: r5v31, types: [com.alibaba.fastjson.serializer.JSONSerializer] */
    /* JADX WARNING: type inference failed for: r5v32 */
    /* JADX WARNING: type inference failed for: r5v33 */
    /* JADX WARNING: type inference failed for: r5v34 */
    /* JADX WARNING: type inference failed for: r5v40 */
    /* JADX WARNING: type inference failed for: r5v41 */
    /* JADX WARNING: type inference failed for: r5v44, types: [java.lang.Class] */
    /* JADX WARNING: type inference failed for: r5v45, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v48, types: [boolean] */
    /* JADX WARNING: type inference failed for: r36v5 */
    /* JADX WARNING: type inference failed for: r5v58 */
    /* JADX WARNING: type inference failed for: r36v6 */
    /* JADX WARNING: type inference failed for: r5v59 */
    /* JADX WARNING: type inference failed for: r5v60 */
    /* JADX WARNING: type inference failed for: r36v7 */
    /* JADX WARNING: type inference failed for: r5v68, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v69, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v71, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v78 */
    /* JADX WARNING: type inference failed for: r5v79 */
    /* JADX WARNING: type inference failed for: r5v81, types: [int] */
    /* JADX WARNING: type inference failed for: r5v82 */
    /* JADX WARNING: type inference failed for: r5v83 */
    /* JADX WARNING: type inference failed for: r36v8 */
    /* JADX WARNING: type inference failed for: r5v84 */
    /* JADX WARNING: type inference failed for: r2v71 */
    /* JADX WARNING: type inference failed for: r5v102 */
    /* JADX WARNING: type inference failed for: r5v103 */
    /* JADX WARNING: type inference failed for: r5v104 */
    /* JADX WARNING: type inference failed for: r5v105 */
    /* JADX WARNING: type inference failed for: r5v106 */
    /* JADX WARNING: type inference failed for: r5v107 */
    /* JADX WARNING: type inference failed for: r5v108 */
    /* JADX WARNING: type inference failed for: r5v109 */
    /* JADX WARNING: type inference failed for: r5v110 */
    /* JADX WARNING: type inference failed for: r5v111 */
    /* JADX WARNING: type inference failed for: r5v112 */
    /* JADX WARNING: type inference failed for: r5v113 */
    /* JADX WARNING: type inference failed for: r5v114 */
    /* JADX WARNING: type inference failed for: r5v115 */
    /* JADX WARNING: type inference failed for: r5v116 */
    /* JADX WARNING: type inference failed for: r5v117 */
    /* JADX WARNING: type inference failed for: r5v118 */
    /* JADX WARNING: type inference failed for: r5v119 */
    /* JADX WARNING: type inference failed for: r5v120 */
    /* JADX WARNING: type inference failed for: r36v9 */
    /* JADX WARNING: type inference failed for: r5v121 */
    /* JADX WARNING: type inference failed for: r36v10 */
    /* JADX WARNING: type inference failed for: r36v11 */
    /* JADX WARNING: type inference failed for: r5v122 */
    /* JADX WARNING: type inference failed for: r5v123 */
    /* JADX WARNING: type inference failed for: r5v124 */
    /* JADX WARNING: type inference failed for: r5v125 */
    /* JADX WARNING: type inference failed for: r5v126 */
    /* JADX WARNING: type inference failed for: r5v127 */
    /* JADX WARNING: type inference failed for: r36v12 */
    /* JADX WARNING: type inference failed for: r5v128 */
    /* JADX WARNING: type inference failed for: r36v13 */
    /* JADX WARNING: type inference failed for: r5v129 */
    /* JADX WARNING: type inference failed for: r5v130 */
    /* JADX WARNING: type inference failed for: r5v131 */
    /* JADX WARNING: type inference failed for: r5v132 */
    /* JADX WARNING: type inference failed for: r5v133 */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0194, code lost:
        if (r7.fieldTransient != false) goto L_0x0197;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01b2, code lost:
        if (r1.typeKey.equals(r14) == false) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:326:0x0450, code lost:
        if (r5 != 0) goto L_0x0452;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v2
      assigns: []
      uses: []
      mth insns count: 925
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
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0160 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0173 A[SYNTHETIC, Splitter:B:106:0x0173] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0226  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x026d A[Catch:{ Exception -> 0x0253, all -> 0x0249 }] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0282 A[Catch:{ Exception -> 0x0253, all -> 0x0249 }] */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x028b A[Catch:{ Exception -> 0x0253, all -> 0x0249 }] */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x043b A[Catch:{ Exception -> 0x03ea, all -> 0x03e8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:399:0x0547 A[Catch:{ Exception -> 0x0672, all -> 0x0670 }] */
    /* JADX WARNING: Removed duplicated region for block: B:442:0x060e  */
    /* JADX WARNING: Removed duplicated region for block: B:455:0x0650  */
    /* JADX WARNING: Removed duplicated region for block: B:468:0x0679 A[SYNTHETIC, Splitter:B:468:0x0679] */
    /* JADX WARNING: Removed duplicated region for block: B:475:0x0692 A[SYNTHETIC, Splitter:B:475:0x0692] */
    /* JADX WARNING: Removed duplicated region for block: B:501:0x06cd A[SYNTHETIC, Splitter:B:501:0x06cd] */
    /* JADX WARNING: Removed duplicated region for block: B:516:0x0288 A[EDGE_INSN: B:516:0x0288->B:179:0x0288 ?: BREAK  
    EDGE_INSN: B:516:0x0288->B:179:0x0288 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0110 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0113 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0118 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0131 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0133 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0152 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0154 A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x015e A[Catch:{ Exception -> 0x06c1, all -> 0x06bb }] */
    /* JADX WARNING: Unknown variable types count: 29 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r43, java.lang.Object r44, java.lang.Object r45, java.lang.reflect.Type r46) throws java.io.IOException {
        /*
            r42 = this;
            r1 = r42
            r2 = r43
            r3 = r44
            r4 = r45
            r5 = r46
            com.alibaba.fastjson.serializer.SerializeWriter r6 = r2.out
            if (r3 != 0) goto L_0x0012
            r6.writeNull()
            return
        L_0x0012:
            com.alibaba.fastjson.serializer.SerialContext r7 = r2.context
            if (r7 == 0) goto L_0x0021
            com.alibaba.fastjson.serializer.SerialContext r7 = r2.context
            int r7 = r7.features
            com.alibaba.fastjson.serializer.SerializerFeature r8 = com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect
            int r8 = r8.mask
            r7 = r7 & r8
            if (r7 != 0) goto L_0x0031
        L_0x0021:
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r7 = r2.references
            if (r7 == 0) goto L_0x0031
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r7 = r2.references
            boolean r7 = r7.containsKey(r3)
            if (r7 == 0) goto L_0x0031
            r43.writeReference(r44)
            return
        L_0x0031:
            int r7 = r6.features
            com.alibaba.fastjson.serializer.SerializerFeature r8 = com.alibaba.fastjson.serializer.SerializerFeature.SortField
            int r8 = r8.mask
            r7 = r7 & r8
            if (r7 == 0) goto L_0x003d
            com.alibaba.fastjson.serializer.FieldSerializer[] r7 = r1.sortedGetters
            goto L_0x003f
        L_0x003d:
            com.alibaba.fastjson.serializer.FieldSerializer[] r7 = r1.getters
        L_0x003f:
            com.alibaba.fastjson.serializer.SerialContext r8 = r2.context
            int r9 = r6.features
            com.alibaba.fastjson.serializer.SerializerFeature r10 = com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect
            int r10 = r10.mask
            r9 = r9 & r10
            if (r9 != 0) goto L_0x0065
            com.alibaba.fastjson.serializer.SerialContext r9 = new com.alibaba.fastjson.serializer.SerialContext
            int r10 = r1.features
            r9.<init>(r8, r3, r4, r10)
            r2.context = r9
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r9 = r2.references
            if (r9 != 0) goto L_0x005e
            java.util.IdentityHashMap r9 = new java.util.IdentityHashMap
            r9.<init>()
            r2.references = r9
        L_0x005e:
            java.util.IdentityHashMap<java.lang.Object, com.alibaba.fastjson.serializer.SerialContext> r9 = r2.references
            com.alibaba.fastjson.serializer.SerialContext r10 = r2.context
            r9.put(r3, r10)
        L_0x0065:
            int r9 = r1.features
            com.alibaba.fastjson.serializer.SerializerFeature r10 = com.alibaba.fastjson.serializer.SerializerFeature.BeanToArray
            int r10 = r10.mask
            r9 = r9 & r10
            r10 = 0
            r11 = 1
            if (r9 != 0) goto L_0x007c
            int r9 = r6.features
            com.alibaba.fastjson.serializer.SerializerFeature r12 = com.alibaba.fastjson.serializer.SerializerFeature.BeanToArray
            int r12 = r12.mask
            r9 = r9 & r12
            if (r9 == 0) goto L_0x007a
            goto L_0x007c
        L_0x007a:
            r9 = 0
            goto L_0x007d
        L_0x007c:
            r9 = 1
        L_0x007d:
            if (r9 == 0) goto L_0x0082
            r12 = 91
            goto L_0x0084
        L_0x0082:
            r12 = 123(0x7b, float:1.72E-43)
        L_0x0084:
            if (r9 == 0) goto L_0x0089
            r13 = 93
            goto L_0x008b
        L_0x0089:
            r13 = 125(0x7d, float:1.75E-43)
        L_0x008b:
            int r14 = r6.count     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r14 = r14 + r11
            char[] r15 = r6.buf     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r15 = r15.length     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r14 <= r15) goto L_0x009f
            java.io.Writer r15 = r6.writer     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r15 != 0) goto L_0x009b
            r6.expandCapacity(r14)     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            goto L_0x009f
        L_0x009b:
            r6.flush()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r14 = 1
        L_0x009f:
            char[] r15 = r6.buf     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r11 = r6.count     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r15[r11] = r12     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r6.count = r14     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r11 = r7.length     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r11 <= 0) goto L_0x00b9
            int r11 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r12 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r12 = r12.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r11 = r11 & r12
            if (r11 == 0) goto L_0x00b9
            r43.incrementIndent()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r43.println()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
        L_0x00b9:
            int r11 = r1.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r12 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r12 = r12.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r11 = r11 & r12
            if (r11 != 0) goto L_0x00e3
            int r11 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r12 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r12 = r12.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r11 = r11 & r12
            if (r11 == 0) goto L_0x00e1
            if (r5 != 0) goto L_0x00e3
            int r11 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r12 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteRootClassName     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r12 = r12.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r11 = r11 & r12
            if (r11 == 0) goto L_0x00e3
            com.alibaba.fastjson.serializer.SerialContext r11 = r2.context     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r11 == 0) goto L_0x00e1
            com.alibaba.fastjson.serializer.SerialContext r11 = r2.context     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerialContext r11 = r11.parent     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r11 == 0) goto L_0x00e1
            goto L_0x00e3
        L_0x00e1:
            r11 = 0
            goto L_0x00e4
        L_0x00e3:
            r11 = 1
        L_0x00e4:
            if (r11 == 0) goto L_0x010b
            java.lang.Class r11 = r44.getClass()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r11 == r5) goto L_0x010b
            java.lang.String r5 = r1.typeKey     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r5 == 0) goto L_0x00f3
            java.lang.String r5 = r1.typeKey     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            goto L_0x00f7
        L_0x00f3:
            com.alibaba.fastjson.serializer.SerializeConfig r5 = r2.config     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.lang.String r5 = r5.typeKey     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
        L_0x00f7:
            r6.writeFieldName(r5, r10)     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.lang.String r5 = r1.typeName     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r5 != 0) goto L_0x0106
            java.lang.Class r5 = r44.getClass()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.lang.String r5 = r5.getName()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
        L_0x0106:
            r2.write(r5)     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r5 = 1
            goto L_0x010c
        L_0x010b:
            r5 = 0
        L_0x010c:
            r11 = 44
            if (r5 == 0) goto L_0x0113
            r5 = 44
            goto L_0x0114
        L_0x0113:
            r5 = 0
        L_0x0114:
            java.util.List<com.alibaba.fastjson.serializer.BeforeFilter> r12 = r2.beforeFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r12 == 0) goto L_0x012f
            java.util.List<com.alibaba.fastjson.serializer.BeforeFilter> r12 = r2.beforeFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.util.Iterator r12 = r12.iterator()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
        L_0x011e:
            boolean r14 = r12.hasNext()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            if (r14 == 0) goto L_0x012f
            java.lang.Object r14 = r12.next()     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.BeforeFilter r14 = (com.alibaba.fastjson.serializer.BeforeFilter) r14     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            char r5 = r14.writeBefore(r2, r3, r5)     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            goto L_0x011e
        L_0x012f:
            if (r5 != r11) goto L_0x0133
            r5 = 1
            goto L_0x0134
        L_0x0133:
            r5 = 0
        L_0x0134:
            int r12 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r14 = com.alibaba.fastjson.serializer.SerializerFeature.QuoteFieldNames     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r14 = r14.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r12 = r12 & r14
            if (r12 == 0) goto L_0x0148
            int r12 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r14 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r14 = r14.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r12 = r12 & r14
            if (r12 != 0) goto L_0x0148
            r12 = 1
            goto L_0x0149
        L_0x0148:
            r12 = 0
        L_0x0149:
            int r14 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r15 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r15 = r15.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r14 = r14 & r15
            if (r14 == 0) goto L_0x0154
            r14 = 1
            goto L_0x0155
        L_0x0154:
            r14 = 0
        L_0x0155:
            int r15 = r6.features     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            com.alibaba.fastjson.serializer.SerializerFeature r11 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteDefaultValue     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            int r11 = r11.mask     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r11 = r11 & r15
            if (r11 == 0) goto L_0x0160
            r11 = 1
            goto L_0x0161
        L_0x0160:
            r11 = 0
        L_0x0161:
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r15 = r2.propertyFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r10 = r2.nameFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r17 = r5
            java.util.List<com.alibaba.fastjson.serializer.ValueFilter> r5 = r2.valueFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            java.util.List<com.alibaba.fastjson.serializer.PropertyPreFilter> r4 = r2.propertyPreFilters     // Catch:{ Exception -> 0x06c1, all -> 0x06bb }
            r18 = r8
            r19 = r13
            r8 = 0
        L_0x0170:
            int r13 = r7.length     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r8 >= r13) goto L_0x0645
            r13 = r7[r8]     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r20 = r7
            com.alibaba.fastjson.util.FieldInfo r7 = r13.fieldInfo     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r21 = r8
            java.lang.Class<?> r8 = r7.fieldClass     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r22 = r14
            java.lang.String r14 = r7.name     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r23 = r12
            int r12 = r6.features     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r24 = r11
            com.alibaba.fastjson.serializer.SerializerFeature r11 = com.alibaba.fastjson.serializer.SerializerFeature.SkipTransientField     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            int r11 = r11.mask     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            r11 = r11 & r12
            if (r11 == 0) goto L_0x01a8
            java.lang.reflect.Field r11 = r7.field     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r11 == 0) goto L_0x01a8
            boolean r11 = r7.fieldTransient     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r11 != 0) goto L_0x0197
            goto L_0x01a8
        L_0x0197:
            r27 = r4
            r36 = r5
            r29 = r9
            r31 = r10
            r38 = r15
            r3 = 0
            r16 = 44
            r9 = r1
            r5 = r2
            goto L_0x061b
        L_0x01a8:
            java.lang.String r11 = r1.typeKey     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            if (r11 == 0) goto L_0x01b4
            java.lang.String r11 = r1.typeKey     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            boolean r11 = r11.equals(r14)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r11 != 0) goto L_0x0197
        L_0x01b4:
            if (r4 == 0) goto L_0x01ce
            java.util.Iterator r11 = r4.iterator()     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
        L_0x01ba:
            boolean r12 = r11.hasNext()     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r12 == 0) goto L_0x01ce
            java.lang.Object r12 = r11.next()     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            com.alibaba.fastjson.serializer.PropertyPreFilter r12 = (com.alibaba.fastjson.serializer.PropertyPreFilter) r12     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            boolean r12 = r12.apply(r2, r3, r14)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r12 != 0) goto L_0x01ba
            r11 = 0
            goto L_0x01cf
        L_0x01ce:
            r11 = 1
        L_0x01cf:
            if (r11 == 0) goto L_0x0197
            r11 = 0
            r25 = 0
            boolean r12 = r7.fieldAccess     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
            if (r12 == 0) goto L_0x0215
            java.lang.Class r12 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r8 != r12) goto L_0x01ed
            java.lang.reflect.Field r12 = r7.field     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            int r12 = r12.getInt(r3)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            r27 = r4
            r1 = r25
            r4 = 0
        L_0x01e7:
            r25 = 0
            r26 = r11
            r11 = 1
            goto L_0x0224
        L_0x01ed:
            java.lang.Class r12 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r8 != r12) goto L_0x01fe
            java.lang.reflect.Field r12 = r7.field     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            long r25 = r12.getLong(r3)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            r27 = r4
            r1 = r25
            r4 = 0
        L_0x01fc:
            r12 = 0
            goto L_0x01e7
        L_0x01fe:
            java.lang.Class r12 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            if (r8 != r12) goto L_0x020e
            java.lang.reflect.Field r12 = r7.field     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            boolean r12 = r12.getBoolean(r3)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            r27 = r4
            r4 = r12
            r1 = r25
            goto L_0x01fc
        L_0x020e:
            java.lang.reflect.Field r11 = r7.field     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            java.lang.Object r11 = r11.get(r3)     // Catch:{ Exception -> 0x06b5, all -> 0x06af }
            goto L_0x0219
        L_0x0215:
            java.lang.Object r11 = r13.getPropertyValue(r3)     // Catch:{ Exception -> 0x063d, all -> 0x0635 }
        L_0x0219:
            r27 = r4
            r1 = r25
            r4 = 0
            r12 = 0
            r25 = 1
            r26 = r11
            r11 = 0
        L_0x0224:
            if (r15 == 0) goto L_0x0282
            if (r11 == 0) goto L_0x025d
            r28 = r13
            java.lang.Class r13 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r13) goto L_0x0237
            java.lang.Integer r13 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
        L_0x0232:
            r29 = r9
            r25 = 1
            goto L_0x0263
        L_0x0237:
            java.lang.Class r13 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r13) goto L_0x0240
            java.lang.Long r13 = java.lang.Long.valueOf(r1)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x0232
        L_0x0240:
            java.lang.Class r13 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r13) goto L_0x025f
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r4)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x0232
        L_0x0249:
            r0 = move-exception
            r2 = r0
            r1 = r18
            r5 = r43
            r9 = r42
            goto L_0x06e9
        L_0x0253:
            r0 = move-exception
            r2 = r0
            r1 = r18
            r5 = r43
            r9 = r42
            goto L_0x06c6
        L_0x025d:
            r28 = r13
        L_0x025f:
            r29 = r9
            r13 = r26
        L_0x0263:
            java.util.Iterator r9 = r15.iterator()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
        L_0x0267:
            boolean r26 = r9.hasNext()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r26 == 0) goto L_0x0288
            java.lang.Object r26 = r9.next()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            r30 = r9
            r9 = r26
            com.alibaba.fastjson.serializer.PropertyFilter r9 = (com.alibaba.fastjson.serializer.PropertyFilter) r9     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            boolean r9 = r9.apply(r3, r14, r13)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r9 != 0) goto L_0x027f
            r9 = 0
            goto L_0x0289
        L_0x027f:
            r9 = r30
            goto L_0x0267
        L_0x0282:
            r29 = r9
            r28 = r13
            r13 = r26
        L_0x0288:
            r9 = 1
        L_0x0289:
            if (r9 == 0) goto L_0x060e
            if (r10 == 0) goto L_0x02cd
            if (r11 == 0) goto L_0x02af
            if (r25 != 0) goto L_0x02af
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x029d
            java.lang.Integer r9 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
        L_0x0299:
            r13 = r9
            r25 = 1
            goto L_0x02af
        L_0x029d:
            java.lang.Class r9 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x02a6
            java.lang.Long r9 = java.lang.Long.valueOf(r1)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x0299
        L_0x02a6:
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x02af
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r4)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x0299
        L_0x02af:
            java.util.Iterator r9 = r10.iterator()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            r31 = r10
            r10 = r14
        L_0x02b6:
            boolean r26 = r9.hasNext()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r26 == 0) goto L_0x02d0
            java.lang.Object r26 = r9.next()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            r32 = r9
            r9 = r26
            com.alibaba.fastjson.serializer.NameFilter r9 = (com.alibaba.fastjson.serializer.NameFilter) r9     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            java.lang.String r10 = r9.process(r3, r10, r13)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            r9 = r32
            goto L_0x02b6
        L_0x02cd:
            r31 = r10
            r10 = r14
        L_0x02d0:
            if (r5 == 0) goto L_0x0311
            if (r11 == 0) goto L_0x02f3
            if (r25 != 0) goto L_0x02f3
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x02e1
            java.lang.Integer r13 = java.lang.Integer.valueOf(r12)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
        L_0x02de:
            r25 = 1
            goto L_0x02f3
        L_0x02e1:
            java.lang.Class r9 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x02ea
            java.lang.Long r13 = java.lang.Long.valueOf(r1)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x02de
        L_0x02ea:
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r8 != r9) goto L_0x02f3
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r4)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x02de
        L_0x02f3:
            java.util.Iterator r9 = r5.iterator()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            r33 = r1
            r1 = r13
        L_0x02fa:
            boolean r2 = r9.hasNext()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            if (r2 == 0) goto L_0x030b
            java.lang.Object r2 = r9.next()     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            com.alibaba.fastjson.serializer.ValueFilter r2 = (com.alibaba.fastjson.serializer.ValueFilter) r2     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            java.lang.Object r1 = r2.process(r3, r14, r1)     // Catch:{ Exception -> 0x0253, all -> 0x0249 }
            goto L_0x02fa
        L_0x030b:
            r41 = r13
            r13 = r1
            r1 = r41
            goto L_0x0314
        L_0x0311:
            r33 = r1
            r1 = r13
        L_0x0314:
            if (r25 == 0) goto L_0x0400
            if (r13 != 0) goto L_0x0400
            int r2 = r7.serialzeFeatures     // Catch:{ Exception -> 0x03f6, all -> 0x03ec }
            r35 = r4
            r36 = r5
            r3 = r33
            r9 = r42
            int r5 = r9.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 | r5
            int r5 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 | r5
            java.lang.Class<java.lang.Boolean> r5 = java.lang.Boolean.class
            if (r8 != r5) goto L_0x0359
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullBooleanAsFalse     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r37 = r13
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r13 = r13.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 | r5
            if (r29 != 0) goto L_0x0345
            r26 = r2 & r13
            if (r26 != 0) goto L_0x0345
            r38 = r15
            int r15 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 & r15
            if (r13 == 0) goto L_0x043c
            goto L_0x0347
        L_0x0345:
            r38 = r15
        L_0x0347:
            r2 = r2 & r5
            if (r2 != 0) goto L_0x0353
            int r2 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 & r5
            if (r2 == 0) goto L_0x0350
            goto L_0x0353
        L_0x0350:
            r13 = r37
            goto L_0x0355
        L_0x0353:
            java.lang.Boolean r13 = java.lang.Boolean.FALSE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
        L_0x0355:
            r2 = r28
            goto L_0x0410
        L_0x0359:
            r37 = r13
            r38 = r15
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            if (r8 != r5) goto L_0x0380
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r13 = r13.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 | r5
            if (r29 != 0) goto L_0x0375
            r15 = r2 & r13
            if (r15 != 0) goto L_0x0375
            int r15 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 & r15
            if (r13 == 0) goto L_0x043c
        L_0x0375:
            r2 = r2 & r5
            if (r2 != 0) goto L_0x037d
            int r2 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 & r5
            if (r2 == 0) goto L_0x0350
        L_0x037d:
            java.lang.String r13 = ""
            goto L_0x0355
        L_0x0380:
            java.lang.Class<java.lang.Number> r5 = java.lang.Number.class
            boolean r5 = r5.isAssignableFrom(r8)     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x03aa
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r13 = r13.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 | r5
            if (r29 != 0) goto L_0x039c
            r15 = r2 & r13
            if (r15 != 0) goto L_0x039c
            int r15 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 & r15
            if (r13 == 0) goto L_0x043c
        L_0x039c:
            r2 = r2 & r5
            if (r2 != 0) goto L_0x03a4
            int r2 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 & r5
            if (r2 == 0) goto L_0x0350
        L_0x03a4:
            r2 = 0
            java.lang.Integer r13 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            goto L_0x0355
        L_0x03aa:
            java.lang.Class<java.util.Collection> r5 = java.util.Collection.class
            boolean r5 = r5.isAssignableFrom(r8)     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x03d4
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r5 = r5.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            com.alibaba.fastjson.serializer.SerializerFeature r13 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            int r13 = r13.mask     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 | r5
            if (r29 != 0) goto L_0x03c6
            r15 = r2 & r13
            if (r15 != 0) goto L_0x03c6
            int r15 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r13 & r15
            if (r13 == 0) goto L_0x043c
        L_0x03c6:
            r2 = r2 & r5
            if (r2 != 0) goto L_0x03ce
            int r2 = r6.features     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r2 = r2 & r5
            if (r2 == 0) goto L_0x0350
        L_0x03ce:
            java.util.List r2 = java.util.Collections.emptyList()     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r13 = r2
            goto L_0x0355
        L_0x03d4:
            if (r29 != 0) goto L_0x03e5
            r2 = r28
            boolean r5 = r2.writeNull     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 != 0) goto L_0x040e
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            boolean r5 = r6.isEnabled(r5)     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x043c
            goto L_0x040e
        L_0x03e5:
            r2 = r28
            goto L_0x040e
        L_0x03e8:
            r0 = move-exception
            goto L_0x03ef
        L_0x03ea:
            r0 = move-exception
            goto L_0x03f9
        L_0x03ec:
            r0 = move-exception
            r9 = r42
        L_0x03ef:
            r2 = r0
            r1 = r18
            r5 = r43
            goto L_0x06e9
        L_0x03f6:
            r0 = move-exception
            r9 = r42
        L_0x03f9:
            r2 = r0
            r1 = r18
            r5 = r43
            goto L_0x06c6
        L_0x0400:
            r35 = r4
            r36 = r5
            r37 = r13
            r38 = r15
            r2 = r28
            r3 = r33
            r9 = r42
        L_0x040e:
            r13 = r37
        L_0x0410:
            if (r25 == 0) goto L_0x0452
            if (r13 == 0) goto L_0x0452
            if (r24 == 0) goto L_0x0452
            java.lang.Class r5 = java.lang.Byte.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 == r5) goto L_0x042e
            java.lang.Class r5 = java.lang.Short.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 == r5) goto L_0x042e
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 == r5) goto L_0x042e
            java.lang.Class r5 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 == r5) goto L_0x042e
            java.lang.Class r5 = java.lang.Float.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 == r5) goto L_0x042e
            java.lang.Class r5 = java.lang.Double.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 != r5) goto L_0x0441
        L_0x042e:
            boolean r5 = r13 instanceof java.lang.Number     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x0441
            r5 = r13
            java.lang.Number r5 = (java.lang.Number) r5     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            byte r5 = r5.byteValue()     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x043c
            goto L_0x0441
        L_0x043c:
            r3 = 0
            r5 = r43
            goto L_0x0619
        L_0x0441:
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r8 != r5) goto L_0x0452
            boolean r5 = r13 instanceof java.lang.Boolean     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x0452
            r5 = r13
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            boolean r5 = r5.booleanValue()     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r5 == 0) goto L_0x043c
        L_0x0452:
            if (r17 == 0) goto L_0x0491
            int r5 = r6.count     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            r15 = 1
            int r5 = r5 + r15
            char[] r15 = r6.buf     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            int r15 = r15.length     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            if (r5 <= r15) goto L_0x0469
            java.io.Writer r15 = r6.writer     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            if (r15 != 0) goto L_0x0465
            r6.expandCapacity(r5)     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            goto L_0x0469
        L_0x0465:
            r6.flush()     // Catch:{ Exception -> 0x03ea, all -> 0x03e8 }
            r5 = 1
        L_0x0469:
            char[] r15 = r6.buf     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            r39 = r7
            int r7 = r6.count     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            r16 = 44
            r15[r7] = r16     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            r6.count = r5     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            int r5 = r6.features     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            com.alibaba.fastjson.serializer.SerializerFeature r7 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            int r7 = r7.mask     // Catch:{ Exception -> 0x048c, all -> 0x0487 }
            r5 = r5 & r7
            if (r5 == 0) goto L_0x0484
            r5 = r43
            r43.println()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x0497
        L_0x0484:
            r5 = r43
            goto L_0x0497
        L_0x0487:
            r0 = move-exception
            r5 = r43
            goto L_0x0638
        L_0x048c:
            r0 = move-exception
            r5 = r43
            goto L_0x0640
        L_0x0491:
            r39 = r7
            r5 = r43
            r16 = 44
        L_0x0497:
            if (r10 == r14) goto L_0x04a5
            if (r29 != 0) goto L_0x049f
            r1 = 1
            r6.writeFieldName(r10, r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
        L_0x049f:
            r5.write(r13)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
        L_0x04a2:
            r3 = 0
            goto L_0x060b
        L_0x04a5:
            if (r1 == r13) goto L_0x04b0
            if (r29 != 0) goto L_0x04ac
            r2.writePrefix(r5)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
        L_0x04ac:
            r5.write(r13)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x04b0:
            if (r29 != 0) goto L_0x0501
            if (r23 == 0) goto L_0x04fb
            char[] r1 = r2.name_chars     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r7 = r1.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r10 = r6.count     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r10 = r10 + r7
            char[] r14 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r14 = r14.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r10 <= r14) goto L_0x04ec
            java.io.Writer r14 = r6.writer     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r14 != 0) goto L_0x04c7
            r6.expandCapacity(r10)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04ec
        L_0x04c7:
            r10 = r7
            r7 = 0
        L_0x04c9:
            char[] r14 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r14 = r14.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r15 = r6.count     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r14 = r14 - r15
            char[] r15 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r40 = r13
            int r13 = r6.count     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            java.lang.System.arraycopy(r1, r7, r15, r13, r14)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r13 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r13 = r13.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r6.count = r13     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r6.flush()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r10 = r10 - r14
            int r7 = r7 + r14
            char[] r13 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r13 = r13.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r10 > r13) goto L_0x04e9
            r13 = r10
            goto L_0x04f1
        L_0x04e9:
            r13 = r40
            goto L_0x04c9
        L_0x04ec:
            r40 = r13
            r13 = r10
            r10 = r7
            r7 = 0
        L_0x04f1:
            char[] r14 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r15 = r6.count     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            java.lang.System.arraycopy(r1, r7, r14, r15, r10)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r6.count = r13     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x0503
        L_0x04fb:
            r40 = r13
            r2.writePrefix(r5)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x0503
        L_0x0501:
            r40 = r13
        L_0x0503:
            if (r11 == 0) goto L_0x057f
            if (r25 != 0) goto L_0x057f
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r8 != r1) goto L_0x0554
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r12 != r1) goto L_0x0515
            java.lang.String r1 = "-2147483648"
            r6.write(r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x0515:
            if (r12 >= 0) goto L_0x0519
            int r1 = -r12
            goto L_0x051a
        L_0x0519:
            r1 = r12
        L_0x051a:
            r2 = 0
        L_0x051b:
            int[] r3 = com.alibaba.fastjson.serializer.SerializeWriter.sizeTable     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r3 = r3[r2]     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r1 > r3) goto L_0x0551
            int r2 = r2 + 1
            if (r12 >= 0) goto L_0x0527
            int r2 = r2 + 1
        L_0x0527:
            int r1 = r6.count     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r1 = r1 + r2
            char[] r3 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r3 = r3.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r1 <= r3) goto L_0x0544
            java.io.Writer r3 = r6.writer     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r3 != 0) goto L_0x0537
            r6.expandCapacity(r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x0544
        L_0x0537:
            char[] r3 = new char[r2]     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            long r7 = (long) r12     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializeWriter.getChars(r7, r2, r3)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r3.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r4 = 0
            r6.write(r3, r4, r2)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r2 = 1
            goto L_0x0545
        L_0x0544:
            r2 = 0
        L_0x0545:
            if (r2 != 0) goto L_0x04a2
            long r2 = (long) r12     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r4 = r6.buf     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializeWriter.getChars(r2, r1, r4)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r6.count = r1     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x0551:
            int r2 = r2 + 1
            goto L_0x051b
        L_0x0554:
            java.lang.Class r1 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r8 != r1) goto L_0x055f
            com.alibaba.fastjson.serializer.SerializeWriter r1 = r5.out     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r1.writeLong(r3)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x055f:
            java.lang.Class r1 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r8 != r1) goto L_0x04a2
            if (r35 == 0) goto L_0x0572
            com.alibaba.fastjson.serializer.SerializeWriter r1 = r5.out     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r2 = true_chars     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r3 = true_chars     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r3 = r3.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r4 = 0
            r1.write(r2, r4, r3)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x0572:
            com.alibaba.fastjson.serializer.SerializeWriter r1 = r5.out     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r2 = false_chars     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            char[] r3 = false_chars     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r3 = r3.length     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r4 = 0
            r1.write(r2, r4, r3)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x057f:
            if (r29 != 0) goto L_0x0605
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            if (r8 != r1) goto L_0x05bb
            int r1 = r2.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r9.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r1 = r1 | r2
            if (r40 != 0) goto L_0x05a9
            int r2 = r6.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializerFeature r3 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r3 = r3.mask     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r2 = r2 & r3
            if (r2 != 0) goto L_0x05a2
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r2.mask     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r1 = r1 & r2
            if (r1 == 0) goto L_0x059d
            goto L_0x05a2
        L_0x059d:
            r6.writeNull()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x05a2:
            java.lang.String r1 = ""
            r6.writeString(r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x05a9:
            r13 = r40
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r22 == 0) goto L_0x05b4
            r6.writeStringWithSingleQuote(r13)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x05b4:
            r1 = 1
            r2 = 0
            r6.writeStringWithDoubleQuote(r13, r2, r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x05bb:
            r1 = r39
            boolean r1 = r1.isEnum     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r1 == 0) goto L_0x05fe
            if (r40 == 0) goto L_0x05f9
            int r1 = r6.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.WriteEnumUsingToString     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r2.mask     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r1 = r1 & r2
            if (r1 == 0) goto L_0x05ec
            r13 = r40
            java.lang.Enum r13 = (java.lang.Enum) r13     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            java.lang.String r1 = r13.toString()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r6.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializerFeature r3 = com.alibaba.fastjson.serializer.SerializerFeature.UseSingleQuotes     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r3 = r3.mask     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r2 = r2 & r3
            if (r2 == 0) goto L_0x05df
            r2 = 1
            goto L_0x05e0
        L_0x05df:
            r2 = 0
        L_0x05e0:
            if (r2 == 0) goto L_0x05e7
            r6.writeStringWithSingleQuote(r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x04a2
        L_0x05e7:
            r3 = 0
            r6.writeStringWithDoubleQuote(r1, r3, r3)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x060b
        L_0x05ec:
            r3 = 0
            r13 = r40
            java.lang.Enum r13 = (java.lang.Enum) r13     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r1 = r13.ordinal()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r6.writeInt(r1)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x060b
        L_0x05f9:
            r3 = 0
            r6.writeNull()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x060b
        L_0x05fe:
            r13 = r40
            r3 = 0
            r2.writeValue(r5, r13)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x060b
        L_0x0605:
            r13 = r40
            r3 = 0
            r2.writeValue(r5, r13)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
        L_0x060b:
            r17 = 1
            goto L_0x061b
        L_0x060e:
            r36 = r5
            r31 = r10
            r38 = r15
            r3 = 0
            r5 = r43
            r9 = r42
        L_0x0619:
            r16 = 44
        L_0x061b:
            int r8 = r21 + 1
            r2 = r5
            r1 = r9
            r7 = r20
            r14 = r22
            r12 = r23
            r11 = r24
            r4 = r27
            r9 = r29
            r10 = r31
            r5 = r36
            r15 = r38
            r3 = r44
            goto L_0x0170
        L_0x0635:
            r0 = move-exception
            r9 = r1
            r5 = r2
        L_0x0638:
            r2 = r0
            r1 = r18
            goto L_0x06e9
        L_0x063d:
            r0 = move-exception
            r9 = r1
            r5 = r2
        L_0x0640:
            r2 = r0
            r1 = r18
            goto L_0x06c6
        L_0x0645:
            r9 = r1
            r5 = r2
            r20 = r7
            r3 = 0
            r16 = 44
            java.util.List<com.alibaba.fastjson.serializer.AfterFilter> r1 = r5.afterFilters     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            if (r1 == 0) goto L_0x0674
            if (r17 == 0) goto L_0x0653
            goto L_0x0655
        L_0x0653:
            r16 = 0
        L_0x0655:
            java.util.List<com.alibaba.fastjson.serializer.AfterFilter> r1 = r5.afterFilters     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r2 = r16
        L_0x065d:
            boolean r3 = r1.hasNext()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r3 == 0) goto L_0x0674
            java.lang.Object r3 = r1.next()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.AfterFilter r3 = (com.alibaba.fastjson.serializer.AfterFilter) r3     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r4 = r44
            char r2 = r3.writeAfter(r5, r4, r2)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x065d
        L_0x0670:
            r0 = move-exception
            goto L_0x0638
        L_0x0672:
            r0 = move-exception
            goto L_0x0640
        L_0x0674:
            r7 = r20
            int r1 = r7.length     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            if (r1 <= 0) goto L_0x0688
            int r1 = r6.features     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            int r2 = r2.mask     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0688
            r43.decrementIdent()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r43.println()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
        L_0x0688:
            int r1 = r6.count     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            r15 = 1
            int r11 = r1 + 1
            char[] r1 = r6.buf     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            int r1 = r1.length     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            if (r11 <= r1) goto L_0x069e
            java.io.Writer r1 = r6.writer     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            if (r1 != 0) goto L_0x069a
            r6.expandCapacity(r11)     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            goto L_0x069e
        L_0x069a:
            r6.flush()     // Catch:{ Exception -> 0x0672, all -> 0x0670 }
            r11 = 1
        L_0x069e:
            char[] r1 = r6.buf     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            int r2 = r6.count     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            r1[r2] = r19     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            r6.count = r11     // Catch:{ Exception -> 0x06ad, all -> 0x06ab }
            r1 = r18
            r5.context = r1
            return
        L_0x06ab:
            r0 = move-exception
            goto L_0x06b2
        L_0x06ad:
            r0 = move-exception
            goto L_0x06b8
        L_0x06af:
            r0 = move-exception
            r9 = r1
            r5 = r2
        L_0x06b2:
            r1 = r18
            goto L_0x06bf
        L_0x06b5:
            r0 = move-exception
            r9 = r1
            r5 = r2
        L_0x06b8:
            r1 = r18
            goto L_0x06c5
        L_0x06bb:
            r0 = move-exception
            r9 = r1
            r5 = r2
            r1 = r8
        L_0x06bf:
            r2 = r0
            goto L_0x06e9
        L_0x06c1:
            r0 = move-exception
            r9 = r1
            r5 = r2
            r1 = r8
        L_0x06c5:
            r2 = r0
        L_0x06c6:
            java.lang.String r3 = "write javaBean error, fastjson version 1.1.71"
            r4 = r45
            if (r4 == 0) goto L_0x06e1
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x06e7 }
            r6.<init>()     // Catch:{ all -> 0x06e7 }
            r6.append(r3)     // Catch:{ all -> 0x06e7 }
            java.lang.String r3 = ", fieldName : "
            r6.append(r3)     // Catch:{ all -> 0x06e7 }
            r6.append(r4)     // Catch:{ all -> 0x06e7 }
            java.lang.String r3 = r6.toString()     // Catch:{ all -> 0x06e7 }
        L_0x06e1:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06e7 }
            r4.<init>(r3, r2)     // Catch:{ all -> 0x06e7 }
            throw r4     // Catch:{ all -> 0x06e7 }
        L_0x06e7:
            r0 = move-exception
            goto L_0x06bf
        L_0x06e9:
            r5.context = r1
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type):void");
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        FieldSerializer[] fieldSerializerArr;
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
        }
        return linkedHashMap;
    }
}
