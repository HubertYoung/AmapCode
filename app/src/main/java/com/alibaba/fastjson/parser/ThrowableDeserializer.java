package com.alibaba.fastjson.parser;

public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig parserConfig, Class<?> cls) {
        super(parserConfig, cls, cls);
    }

    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r11v0 */
    /* JADX WARNING: type inference failed for: r11v1 */
    /* JADX WARNING: type inference failed for: r7v1, types: [java.lang.reflect.Type] */
    /* JADX WARNING: type inference failed for: r11v2, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v1, types: [com.alibaba.fastjson.parser.JavaBeanDeserializer] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v5, types: [com.alibaba.fastjson.parser.JavaBeanDeserializer] */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r4v3, types: [java.lang.reflect.Constructor[]] */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r14v1 */
    /* JADX WARNING: type inference failed for: r15v1 */
    /* JADX WARNING: type inference failed for: r15v2, types: [java.lang.reflect.Constructor] */
    /* JADX WARNING: type inference failed for: r14v2, types: [java.lang.reflect.Constructor] */
    /* JADX WARNING: type inference failed for: r13v3, types: [java.lang.reflect.Constructor] */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v10, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r4v12, types: [java.lang.Object[]] */
    /* JADX WARNING: type inference failed for: r9v1, types: [java.lang.reflect.Constructor] */
    /* JADX WARNING: type inference failed for: r16v0 */
    /* JADX WARNING: type inference failed for: r15v3 */
    /* JADX WARNING: type inference failed for: r14v3 */
    /* JADX WARNING: type inference failed for: r13v4 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r16v1 */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: type inference failed for: r14v4 */
    /* JADX WARNING: type inference failed for: r16v2 */
    /* JADX WARNING: type inference failed for: r15v4 */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r7v22 */
    /* JADX WARNING: type inference failed for: r8v20 */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r8v22, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r8v23 */
    /* JADX WARNING: type inference failed for: r11v5 */
    /* JADX WARNING: type inference failed for: r7v23 */
    /* JADX WARNING: type inference failed for: r7v24 */
    /* JADX WARNING: type inference failed for: r11v6 */
    /* JADX WARNING: type inference failed for: r11v7 */
    /* JADX WARNING: type inference failed for: r11v8 */
    /* JADX WARNING: type inference failed for: r11v9 */
    /* JADX WARNING: type inference failed for: r11v10 */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* JADX WARNING: type inference failed for: r7v25 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r4v25 */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r14v5 */
    /* JADX WARNING: type inference failed for: r15v5 */
    /* JADX WARNING: type inference failed for: r15v6 */
    /* JADX WARNING: type inference failed for: r15v7 */
    /* JADX WARNING: type inference failed for: r15v8 */
    /* JADX WARNING: type inference failed for: r15v9 */
    /* JADX WARNING: type inference failed for: r14v6 */
    /* JADX WARNING: type inference failed for: r14v7 */
    /* JADX WARNING: type inference failed for: r14v8 */
    /* JADX WARNING: type inference failed for: r14v9 */
    /* JADX WARNING: type inference failed for: r14v10 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r13v8 */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: type inference failed for: r13v10 */
    /* JADX WARNING: type inference failed for: r13v11 */
    /* JADX WARNING: type inference failed for: r15v10 */
    /* JADX WARNING: type inference failed for: r14v11 */
    /* JADX WARNING: type inference failed for: r13v12 */
    /* JADX WARNING: type inference failed for: r16v3 */
    /* JADX WARNING: type inference failed for: r16v4 */
    /* JADX WARNING: type inference failed for: r16v5 */
    /* JADX WARNING: type inference failed for: r16v6 */
    /* JADX WARNING: type inference failed for: r16v7 */
    /* JADX WARNING: type inference failed for: r11v12 */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r7v26 */
    /* JADX WARNING: type inference failed for: r8v30 */
    /* JADX WARNING: type inference failed for: r11v14 */
    /* JADX WARNING: type inference failed for: r7v27 */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0140, code lost:
        r15 = r15;
        r14 = r14;
        r13 = r13;
        r16 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0140, code lost:
        r15 = r15;
        r14 = r14;
        r13 = r13;
        r16 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0039, code lost:
        if (java.lang.Throwable.class.isAssignableFrom(r3) != false) goto L_0x003d;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r11v1
      assigns: []
      uses: []
      mth insns count: 219
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
    /* JADX WARNING: Removed duplicated region for block: B:99:0x019f  */
    /* JADX WARNING: Unknown variable types count: 29 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r18, java.lang.reflect.Type r19, java.lang.Object r20) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer
            int r5 = r4.token
            r6 = 8
            r7 = 0
            if (r5 != r6) goto L_0x0013
            r4.nextToken()
            return r7
        L_0x0013:
            int r5 = r2.resolveStatus
            r8 = 2
            r9 = 0
            if (r5 != r8) goto L_0x001c
            r2.resolveStatus = r9
            goto L_0x002b
        L_0x001c:
            int r5 = r4.token
            r10 = 12
            if (r5 == r10) goto L_0x002b
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x002b:
            if (r3 == 0) goto L_0x003c
            boolean r5 = r3 instanceof java.lang.Class
            if (r5 == 0) goto L_0x003c
            java.lang.Class r3 = (java.lang.Class) r3
            java.lang.Class<java.lang.Throwable> r5 = java.lang.Throwable.class
            boolean r5 = r5.isAssignableFrom(r3)
            if (r5 == 0) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r3 = r7
        L_0x003d:
            r10 = r3
            r3 = r7
            r5 = r3
            r11 = r5
            r12 = r11
        L_0x0042:
            com.alibaba.fastjson.parser.SymbolTable r13 = r2.symbolTable
            java.lang.String r13 = r4.scanSymbol(r13)
            r14 = 13
            r15 = 16
            if (r13 != 0) goto L_0x005e
            int r8 = r4.token
            if (r8 != r14) goto L_0x0057
            r4.nextToken(r15)
            goto L_0x00e9
        L_0x0057:
            int r8 = r4.token
            if (r8 == r15) goto L_0x005c
            goto L_0x005e
        L_0x005c:
            r8 = 2
            goto L_0x0042
        L_0x005e:
            r8 = 58
            r4.nextTokenWithChar(r8)
            java.lang.String r8 = "@type"
            boolean r8 = r8.equals(r13)
            r14 = 4
            if (r8 == 0) goto L_0x008a
            int r8 = r4.token
            if (r8 != r14) goto L_0x0081
            java.lang.String r8 = r4.stringVal()
            com.alibaba.fastjson.parser.ParserConfig r10 = r2.config
            java.lang.ClassLoader r10 = r10.defaultClassLoader
            java.lang.Class r8 = com.alibaba.fastjson.util.TypeUtils.loadClass(r8, r10, r9)
            r4.nextToken(r15)
            r10 = r8
            goto L_0x00e0
        L_0x0081:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x008a:
            java.lang.String r8 = "message"
            boolean r8 = r8.equals(r13)
            if (r8 == 0) goto L_0x00ae
            int r8 = r4.token
            if (r8 != r6) goto L_0x0098
            r8 = r7
            goto L_0x00a0
        L_0x0098:
            int r8 = r4.token
            if (r8 != r14) goto L_0x00a5
            java.lang.String r8 = r4.stringVal()
        L_0x00a0:
            r4.nextToken()
            r11 = r8
            goto L_0x00e0
        L_0x00a5:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.String r3 = "syntax error"
            r2.<init>(r3)
            throw r2
        L_0x00ae:
            java.lang.String r8 = "cause"
            boolean r8 = r8.equals(r13)
            if (r8 == 0) goto L_0x00bf
            java.lang.String r5 = "cause"
            java.lang.Object r5 = r1.deserialze(r2, r7, r5)
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            goto L_0x00e0
        L_0x00bf:
            java.lang.String r8 = "stackTrace"
            boolean r8 = r8.equals(r13)
            if (r8 == 0) goto L_0x00d2
            java.lang.Class<java.lang.StackTraceElement[]> r8 = java.lang.StackTraceElement[].class
            java.lang.Object r8 = r2.parseObject(r8)
            java.lang.StackTraceElement[] r8 = (java.lang.StackTraceElement[]) r8
            r12 = r8
            goto L_0x00e0
        L_0x00d2:
            if (r3 != 0) goto L_0x00d9
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
        L_0x00d9:
            java.lang.Object r8 = r18.parse()
            r3.put(r13, r8)
        L_0x00e0:
            int r8 = r4.token
            r13 = 13
            if (r8 != r13) goto L_0x01d2
            r4.nextToken(r15)
        L_0x00e9:
            if (r10 != 0) goto L_0x00f2
            java.lang.Exception r4 = new java.lang.Exception
            r4.<init>(r11, r5)
            goto L_0x0180
        L_0x00f2:
            java.lang.reflect.Constructor[] r4 = r10.getConstructors()     // Catch:{ Exception -> 0x01c8 }
            int r6 = r4.length     // Catch:{ Exception -> 0x01c8 }
            r13 = r7
            r14 = r13
            r15 = r14
            r8 = 0
        L_0x00fb:
            if (r8 >= r6) goto L_0x0146
            r9 = r4[r8]     // Catch:{ Exception -> 0x01c8 }
            java.lang.Class[] r7 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            int r7 = r7.length     // Catch:{ Exception -> 0x01c8 }
            if (r7 != 0) goto L_0x010a
            r16 = r4
            r15 = r9
            goto L_0x0140
        L_0x010a:
            java.lang.Class[] r7 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            int r7 = r7.length     // Catch:{ Exception -> 0x01c8 }
            r16 = r4
            r4 = 1
            if (r7 != r4) goto L_0x0121
            java.lang.Class[] r4 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            r7 = 0
            r4 = r4[r7]     // Catch:{ Exception -> 0x01c8 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            if (r4 != r7) goto L_0x0121
            r14 = r9
            goto L_0x0140
        L_0x0121:
            java.lang.Class[] r4 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            int r4 = r4.length     // Catch:{ Exception -> 0x01c8 }
            r7 = 2
            if (r4 != r7) goto L_0x0140
            java.lang.Class[] r4 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            r7 = 0
            r4 = r4[r7]     // Catch:{ Exception -> 0x01c8 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            if (r4 != r7) goto L_0x0140
            java.lang.Class[] r4 = r9.getParameterTypes()     // Catch:{ Exception -> 0x01c8 }
            r7 = 1
            r4 = r4[r7]     // Catch:{ Exception -> 0x01c8 }
            java.lang.Class<java.lang.Throwable> r7 = java.lang.Throwable.class
            if (r4 != r7) goto L_0x0140
            r13 = r9
        L_0x0140:
            int r8 = r8 + 1
            r4 = r16
            r9 = 0
            goto L_0x00fb
        L_0x0146:
            if (r13 == 0) goto L_0x0159
            r7 = 2
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x01c8 }
            r6 = 0
            r4[r6] = r11     // Catch:{ Exception -> 0x01c8 }
            r6 = 1
            r4[r6] = r5     // Catch:{ Exception -> 0x01c8 }
            java.lang.Object r4 = r13.newInstance(r4)     // Catch:{ Exception -> 0x01c8 }
            r7 = r4
            java.lang.Throwable r7 = (java.lang.Throwable) r7     // Catch:{ Exception -> 0x01c8 }
            goto L_0x0177
        L_0x0159:
            if (r14 == 0) goto L_0x0169
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x01c8 }
            r6 = 0
            r4[r6] = r11     // Catch:{ Exception -> 0x01c8 }
            java.lang.Object r4 = r14.newInstance(r4)     // Catch:{ Exception -> 0x01c8 }
            r7 = r4
            java.lang.Throwable r7 = (java.lang.Throwable) r7     // Catch:{ Exception -> 0x01c8 }
            goto L_0x0177
        L_0x0169:
            if (r15 == 0) goto L_0x0176
            r8 = 0
            java.lang.Object[] r4 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x01c8 }
            java.lang.Object r4 = r15.newInstance(r4)     // Catch:{ Exception -> 0x01c8 }
            r7 = r4
            java.lang.Throwable r7 = (java.lang.Throwable) r7     // Catch:{ Exception -> 0x01c8 }
            goto L_0x0177
        L_0x0176:
            r7 = 0
        L_0x0177:
            if (r7 != 0) goto L_0x017f
            java.lang.Exception r4 = new java.lang.Exception     // Catch:{ Exception -> 0x01c8 }
            r4.<init>(r11, r5)     // Catch:{ Exception -> 0x01c8 }
            goto L_0x0180
        L_0x017f:
            r4 = r7
        L_0x0180:
            if (r12 == 0) goto L_0x0185
            r4.setStackTrace(r12)
        L_0x0185:
            if (r3 == 0) goto L_0x01c7
            if (r10 == 0) goto L_0x019c
            java.lang.Class r5 = r1.clazz
            if (r10 != r5) goto L_0x018f
            r2 = r1
            goto L_0x019d
        L_0x018f:
            com.alibaba.fastjson.parser.ParserConfig r2 = r2.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r2 = r2.getDeserializer(r10)
            boolean r5 = r2 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer
            if (r5 == 0) goto L_0x019c
            com.alibaba.fastjson.parser.JavaBeanDeserializer r2 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r2
            goto L_0x019d
        L_0x019c:
            r2 = 0
        L_0x019d:
            if (r2 == 0) goto L_0x01c7
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x01a7:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x01c7
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getKey()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r5 = r5.getValue()
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r6 = r2.getFieldDeserializer(r6)
            if (r6 == 0) goto L_0x01a7
            r6.setValue(r4, r5)
            goto L_0x01a7
        L_0x01c7:
            return r4
        L_0x01c8:
            r0 = move-exception
            r2 = r0
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.String r4 = "create instance error"
            r3.<init>(r4, r2)
            throw r3
        L_0x01d2:
            r7 = 0
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.ThrowableDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }
}
