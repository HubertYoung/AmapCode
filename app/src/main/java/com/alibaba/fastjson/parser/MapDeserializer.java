package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class MapDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    MapDeserializer() {
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == JSONObject.class && defaultJSONParser.fieldTypeResolver == null) {
            return defaultJSONParser.parseObject();
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        Map<?, ?> createMap = createMap(type);
        ParseContext parseContext = defaultJSONParser.contex;
        try {
            defaultJSONParser.setContext(parseContext, createMap, obj);
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type type2 = parameterizedType.getActualTypeArguments()[0];
                Type type3 = parameterizedType.getActualTypeArguments()[1];
                if (String.class == type2) {
                    return parseMap(defaultJSONParser, createMap, type3, obj);
                }
                T parseMap = parseMap(defaultJSONParser, createMap, type2, type3, obj);
                defaultJSONParser.setContext(parseContext);
                return parseMap;
            }
            T parseObject = defaultJSONParser.parseObject((Map) createMap, obj);
            defaultJSONParser.setContext(parseContext);
            return parseObject;
        } finally {
            defaultJSONParser.setContext(parseContext);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r9 = r8.config.getDeserializer(r2);
        r0.nextToken(16);
        r8.resolveStatus = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x010c, code lost:
        if (r1 == null) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0110, code lost:
        if ((r11 instanceof java.lang.Integer) != false) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0112, code lost:
        r8.popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0115, code lost:
        r9 = (java.util.Map) r9.deserialze(r8, r2, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x011b, code lost:
        r8.setContext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x011e, code lost:
        return r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map parseMap(com.alibaba.fastjson.parser.DefaultJSONParser r8, java.util.Map<java.lang.String, java.lang.Object> r9, java.lang.reflect.Type r10, java.lang.Object r11) {
        /*
            com.alibaba.fastjson.parser.JSONLexer r0 = r8.lexer
            int r1 = r0.token
            r2 = 12
            if (r1 == r2) goto L_0x001f
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "syntax error, expect {, actual "
            r9.<init>(r10)
            int r10 = r0.token
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x001f:
            com.alibaba.fastjson.parser.ParseContext r1 = r8.contex
        L_0x0021:
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            char r2 = r0.ch     // Catch:{ all -> 0x0159 }
        L_0x0026:
            r3 = 44
            if (r2 != r3) goto L_0x0033
            r0.next()     // Catch:{ all -> 0x0159 }
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            char r2 = r0.ch     // Catch:{ all -> 0x0159 }
            goto L_0x0026
        L_0x0033:
            r3 = 0
            r4 = 58
            r5 = 34
            r6 = 16
            if (r2 != r5) goto L_0x0062
            com.alibaba.fastjson.parser.SymbolTable r2 = r8.symbolTable     // Catch:{ all -> 0x0159 }
            java.lang.String r2 = r0.scanSymbol(r2, r5)     // Catch:{ all -> 0x0159 }
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            char r7 = r0.ch     // Catch:{ all -> 0x0159 }
            if (r7 == r4) goto L_0x00c7
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0159 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = "syntax error, "
            r10.<init>(r11)     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = r0.info()     // Catch:{ all -> 0x0159 }
            r10.append(r11)     // Catch:{ all -> 0x0159 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0159 }
            r9.<init>(r10)     // Catch:{ all -> 0x0159 }
            throw r9     // Catch:{ all -> 0x0159 }
        L_0x0062:
            r7 = 125(0x7d, float:1.75E-43)
            if (r2 != r7) goto L_0x0072
            r0.next()     // Catch:{ all -> 0x0159 }
            r0.sp = r3     // Catch:{ all -> 0x0159 }
            r0.nextToken(r6)     // Catch:{ all -> 0x0159 }
            r8.setContext(r1)
            return r9
        L_0x0072:
            r7 = 39
            if (r2 != r7) goto L_0x009c
            com.alibaba.fastjson.parser.SymbolTable r2 = r8.symbolTable     // Catch:{ all -> 0x0159 }
            java.lang.String r2 = r0.scanSymbol(r2, r7)     // Catch:{ all -> 0x0159 }
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            char r7 = r0.ch     // Catch:{ all -> 0x0159 }
            if (r7 == r4) goto L_0x00c7
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0159 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = "syntax error, "
            r10.<init>(r11)     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = r0.info()     // Catch:{ all -> 0x0159 }
            r10.append(r11)     // Catch:{ all -> 0x0159 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0159 }
            r9.<init>(r10)     // Catch:{ all -> 0x0159 }
            throw r9     // Catch:{ all -> 0x0159 }
        L_0x009c:
            com.alibaba.fastjson.parser.SymbolTable r2 = r8.symbolTable     // Catch:{ all -> 0x0159 }
            java.lang.String r2 = r0.scanSymbolUnQuoted(r2)     // Catch:{ all -> 0x0159 }
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            char r7 = r0.ch     // Catch:{ all -> 0x0159 }
            if (r7 == r4) goto L_0x00c7
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0159 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = "expect ':' at "
            r10.<init>(r11)     // Catch:{ all -> 0x0159 }
            int r11 = r0.pos     // Catch:{ all -> 0x0159 }
            r10.append(r11)     // Catch:{ all -> 0x0159 }
            java.lang.String r11 = ", actual "
            r10.append(r11)     // Catch:{ all -> 0x0159 }
            r10.append(r7)     // Catch:{ all -> 0x0159 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0159 }
            r9.<init>(r10)     // Catch:{ all -> 0x0159 }
            throw r9     // Catch:{ all -> 0x0159 }
        L_0x00c7:
            r0.next()     // Catch:{ all -> 0x0159 }
            r0.skipWhitespace()     // Catch:{ all -> 0x0159 }
            r0.sp = r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = "@type"
            r4 = 13
            r7 = 0
            if (r2 != r3) goto L_0x011f
            com.alibaba.fastjson.parser.Feature r3 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0159 }
            boolean r3 = r0.isEnabled(r3)     // Catch:{ all -> 0x0159 }
            if (r3 != 0) goto L_0x011f
            com.alibaba.fastjson.parser.SymbolTable r2 = r8.symbolTable     // Catch:{ all -> 0x0159 }
            java.lang.String r2 = r0.scanSymbol(r2, r5)     // Catch:{ all -> 0x0159 }
            com.alibaba.fastjson.parser.ParserConfig r3 = r8.config     // Catch:{ all -> 0x0159 }
            int r5 = r0.features     // Catch:{ all -> 0x0159 }
            java.lang.Class r2 = r3.checkAutoType(r2, r7, r5)     // Catch:{ all -> 0x0159 }
            java.lang.Class r3 = r9.getClass()     // Catch:{ all -> 0x0159 }
            if (r2 != r3) goto L_0x0100
            r0.nextToken(r6)     // Catch:{ all -> 0x0159 }
            int r2 = r0.token     // Catch:{ all -> 0x0159 }
            if (r2 != r4) goto L_0x0021
            r0.nextToken(r6)     // Catch:{ all -> 0x0159 }
            r8.setContext(r1)
            return r9
        L_0x0100:
            com.alibaba.fastjson.parser.ParserConfig r9 = r8.config     // Catch:{ all -> 0x0159 }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r9 = r9.getDeserializer(r2)     // Catch:{ all -> 0x0159 }
            r0.nextToken(r6)     // Catch:{ all -> 0x0159 }
            r10 = 2
            r8.resolveStatus = r10     // Catch:{ all -> 0x0159 }
            if (r1 == 0) goto L_0x0115
            boolean r10 = r11 instanceof java.lang.Integer     // Catch:{ all -> 0x0159 }
            if (r10 != 0) goto L_0x0115
            r8.popContext()     // Catch:{ all -> 0x0159 }
        L_0x0115:
            java.lang.Object r9 = r9.deserialze(r8, r2, r11)     // Catch:{ all -> 0x0159 }
            java.util.Map r9 = (java.util.Map) r9     // Catch:{ all -> 0x0159 }
            r8.setContext(r1)
            return r9
        L_0x011f:
            r0.nextToken()     // Catch:{ all -> 0x0159 }
            r8.setContext(r1)     // Catch:{ all -> 0x0159 }
            int r3 = r0.token     // Catch:{ all -> 0x0159 }
            r5 = 8
            if (r3 != r5) goto L_0x012f
            r0.nextToken()     // Catch:{ all -> 0x0159 }
            goto L_0x0133
        L_0x012f:
            java.lang.Object r7 = r8.parseObject(r10, r2)     // Catch:{ all -> 0x0159 }
        L_0x0133:
            r9.put(r2, r7)     // Catch:{ all -> 0x0159 }
            int r3 = r8.resolveStatus     // Catch:{ all -> 0x0159 }
            r5 = 1
            if (r3 != r5) goto L_0x013e
            r8.checkMapResolve(r9, r2)     // Catch:{ all -> 0x0159 }
        L_0x013e:
            r8.setContext(r1, r7, r2)     // Catch:{ all -> 0x0159 }
            int r2 = r0.token     // Catch:{ all -> 0x0159 }
            r3 = 20
            if (r2 == r3) goto L_0x0155
            r3 = 15
            if (r2 != r3) goto L_0x014c
            goto L_0x0155
        L_0x014c:
            if (r2 != r4) goto L_0x0021
            r0.nextToken()     // Catch:{ all -> 0x0159 }
            r8.setContext(r1)
            return r9
        L_0x0155:
            r8.setContext(r1)
            return r9
        L_0x0159:
            r9 = move-exception
            r8.setContext(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.MapDeserializer.parseMap(com.alibaba.fastjson.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.Object):java.util.Map");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006a, code lost:
        r5.nextTokenWithChar(':');
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006f, code lost:
        if (r5.token != 4) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0071, code lost:
        r2 = r5.stringVal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007b, code lost:
        if ("..".equals(r2) == false) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007d, code lost:
        r13 = r9.parent.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0081, code lost:
        r2 = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008a, code lost:
        if ("$".equals(r2) == false) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008c, code lost:
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008f, code lost:
        if (r2.parent == null) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0091, code lost:
        r2 = r2.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0094, code lost:
        r13 = r2.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0097, code lost:
        r1.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r9, r2));
        r1.resolveStatus = 1;
        r2 = 13;
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a4, code lost:
        r5.nextToken(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a9, code lost:
        if (r5.token == r2) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b2, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal ref");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b3, code lost:
        r5.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b8, code lost:
        r1.setContext(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bb, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r3 = new java.lang.StringBuilder("illegal ref, ");
        r3.append(com.alibaba.fastjson.parser.JSONToken.name(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d3, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object parseMap(com.alibaba.fastjson.parser.DefaultJSONParser r17, java.util.Map<java.lang.Object, java.lang.Object> r18, java.lang.reflect.Type r19, java.lang.reflect.Type r20, java.lang.Object r21) {
        /*
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            com.alibaba.fastjson.parser.JSONLexer r5 = r1.lexer
            int r6 = r5.token
            r7 = 16
            r8 = 12
            if (r6 == r8) goto L_0x002d
            if (r6 == r7) goto L_0x002d
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "syntax error, expect {, actual "
            r2.<init>(r3)
            java.lang.String r3 = com.alibaba.fastjson.parser.JSONToken.name(r6)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x002d:
            com.alibaba.fastjson.parser.ParserConfig r6 = r1.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r6.getDeserializer(r3)
            com.alibaba.fastjson.parser.ParserConfig r8 = r1.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r8 = r8.getDeserializer(r4)
            r5.nextToken()
            com.alibaba.fastjson.parser.ParseContext r9 = r1.contex
        L_0x003e:
            int r10 = r5.token     // Catch:{ all -> 0x0148 }
            r11 = 13
            if (r10 != r11) goto L_0x004b
            r5.nextToken(r7)     // Catch:{ all -> 0x0148 }
            r1.setContext(r9)
            return r2
        L_0x004b:
            r12 = 58
            r14 = 1
            r15 = 4
            if (r10 != r15) goto L_0x00d4
            int r13 = r5.sp     // Catch:{ all -> 0x0148 }
            if (r13 != r15) goto L_0x00d4
            java.lang.String r13 = r5.text     // Catch:{ all -> 0x0148 }
            java.lang.String r7 = "$ref"
            int r11 = r5.np     // Catch:{ all -> 0x0148 }
            int r11 = r11 + r14
            boolean r7 = r13.startsWith(r7, r11)     // Catch:{ all -> 0x0148 }
            if (r7 == 0) goto L_0x00d4
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0148 }
            boolean r7 = r5.isEnabled(r7)     // Catch:{ all -> 0x0148 }
            if (r7 != 0) goto L_0x00d4
            r5.nextTokenWithChar(r12)     // Catch:{ all -> 0x0148 }
            int r2 = r5.token     // Catch:{ all -> 0x0148 }
            if (r2 != r15) goto L_0x00bc
            java.lang.String r2 = r5.stringVal()     // Catch:{ all -> 0x0148 }
            java.lang.String r3 = ".."
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x0148 }
            if (r3 == 0) goto L_0x0084
            com.alibaba.fastjson.parser.ParseContext r2 = r9.parent     // Catch:{ all -> 0x0148 }
            java.lang.Object r13 = r2.object     // Catch:{ all -> 0x0148 }
        L_0x0081:
            r2 = 13
            goto L_0x00a4
        L_0x0084:
            java.lang.String r3 = "$"
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x0148 }
            if (r3 == 0) goto L_0x0097
            r2 = r9
        L_0x008d:
            com.alibaba.fastjson.parser.ParseContext r3 = r2.parent     // Catch:{ all -> 0x0148 }
            if (r3 == 0) goto L_0x0094
            com.alibaba.fastjson.parser.ParseContext r2 = r2.parent     // Catch:{ all -> 0x0148 }
            goto L_0x008d
        L_0x0094:
            java.lang.Object r13 = r2.object     // Catch:{ all -> 0x0148 }
            goto L_0x0081
        L_0x0097:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r3 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x0148 }
            r3.<init>(r9, r2)     // Catch:{ all -> 0x0148 }
            r1.addResolveTask(r3)     // Catch:{ all -> 0x0148 }
            r1.resolveStatus = r14     // Catch:{ all -> 0x0148 }
            r2 = 13
            r13 = 0
        L_0x00a4:
            r5.nextToken(r2)     // Catch:{ all -> 0x0148 }
            int r3 = r5.token     // Catch:{ all -> 0x0148 }
            if (r3 == r2) goto L_0x00b3
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0148 }
            java.lang.String r3 = "illegal ref"
            r2.<init>(r3)     // Catch:{ all -> 0x0148 }
            throw r2     // Catch:{ all -> 0x0148 }
        L_0x00b3:
            r2 = 16
            r5.nextToken(r2)     // Catch:{ all -> 0x0148 }
            r1.setContext(r9)
            return r13
        L_0x00bc:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0148 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = "illegal ref, "
            r3.<init>(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = com.alibaba.fastjson.parser.JSONToken.name(r10)     // Catch:{ all -> 0x0148 }
            r3.append(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0148 }
            r2.<init>(r3)     // Catch:{ all -> 0x0148 }
            throw r2     // Catch:{ all -> 0x0148 }
        L_0x00d4:
            int r7 = r18.size()     // Catch:{ all -> 0x0148 }
            if (r7 != 0) goto L_0x0108
            if (r10 != r15) goto L_0x0108
            java.lang.String r7 = "@type"
            java.lang.String r10 = r5.stringVal()     // Catch:{ all -> 0x0148 }
            boolean r7 = r7.equals(r10)     // Catch:{ all -> 0x0148 }
            if (r7 == 0) goto L_0x0108
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x0148 }
            boolean r7 = r5.isEnabled(r7)     // Catch:{ all -> 0x0148 }
            if (r7 != 0) goto L_0x0108
            r5.nextTokenWithChar(r12)     // Catch:{ all -> 0x0148 }
            r7 = 16
            r5.nextToken(r7)     // Catch:{ all -> 0x0148 }
            int r7 = r5.token     // Catch:{ all -> 0x0148 }
            r10 = 13
            if (r7 != r10) goto L_0x0105
            r5.nextToken()     // Catch:{ all -> 0x0148 }
            r1.setContext(r9)
            return r2
        L_0x0105:
            r5.nextToken()     // Catch:{ all -> 0x0148 }
        L_0x0108:
            r7 = 0
            java.lang.Object r7 = r6.deserialze(r1, r3, r7)     // Catch:{ all -> 0x0148 }
            int r10 = r5.token     // Catch:{ all -> 0x0148 }
            r11 = 17
            if (r10 == r11) goto L_0x012a
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0148 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = "syntax error, expect :, actual "
            r3.<init>(r4)     // Catch:{ all -> 0x0148 }
            int r4 = r5.token     // Catch:{ all -> 0x0148 }
            r3.append(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0148 }
            r2.<init>(r3)     // Catch:{ all -> 0x0148 }
            throw r2     // Catch:{ all -> 0x0148 }
        L_0x012a:
            r5.nextToken()     // Catch:{ all -> 0x0148 }
            java.lang.Object r10 = r8.deserialze(r1, r4, r7)     // Catch:{ all -> 0x0148 }
            int r11 = r1.resolveStatus     // Catch:{ all -> 0x0148 }
            if (r11 != r14) goto L_0x0138
            r1.checkMapResolve(r2, r7)     // Catch:{ all -> 0x0148 }
        L_0x0138:
            r2.put(r7, r10)     // Catch:{ all -> 0x0148 }
            int r7 = r5.token     // Catch:{ all -> 0x0148 }
            r10 = 16
            if (r7 != r10) goto L_0x0144
            r5.nextToken()     // Catch:{ all -> 0x0148 }
        L_0x0144:
            r7 = 16
            goto L_0x003e
        L_0x0148:
            r0 = move-exception
            r2 = r0
            r1.setContext(r9)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.MapDeserializer.parseMap(com.alibaba.fastjson.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.reflect.Type, java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public Map<?, ?> createMap(Type type) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class || type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type == JSONObject.class) {
            return new JSONObject();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (EnumMap.class.equals(rawType)) {
                return new EnumMap((Class) parameterizedType.getActualTypeArguments()[0]);
            }
            return createMap(rawType);
        }
        Class cls = (Class) type;
        if (cls.isInterface()) {
            throw new JSONException("unsupport type ".concat(String.valueOf(type)));
        }
        try {
            return (Map) cls.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type ".concat(String.valueOf(type)), e);
        }
    }
}
