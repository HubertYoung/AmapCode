package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateCodec implements ObjectDeserializer, ObjectSerializer {
    public static final DateCodec instance = new DateCodec();

    private DateCodec() {
    }

    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException {
        Date date;
        char[] cArr;
        JSONSerializer jSONSerializer2 = jSONSerializer;
        Object obj3 = obj;
        SerializeWriter serializeWriter = jSONSerializer2.out;
        if (obj3 == null) {
            serializeWriter.writeNull();
        } else if ((serializeWriter.features & SerializerFeature.WriteClassName.mask) == 0 || obj.getClass() == type) {
            if (obj3 instanceof Calendar) {
                date = ((Calendar) obj3).getTime();
            } else {
                date = (Date) obj3;
            }
            if ((serializeWriter.features & SerializerFeature.WriteDateUseDateFormat.mask) != 0) {
                DateFormat dateFormat = jSONSerializer.getDateFormat();
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, jSONSerializer2.locale);
                    dateFormat.setTimeZone(jSONSerializer2.timeZone);
                }
                serializeWriter.writeString(dateFormat.format(date));
                return;
            }
            long time = date.getTime();
            if ((serializeWriter.features & SerializerFeature.UseISO8601DateFormat.mask) != 0) {
                if ((serializeWriter.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                    serializeWriter.write(39);
                } else {
                    serializeWriter.write(34);
                }
                Calendar instance2 = Calendar.getInstance(jSONSerializer2.timeZone, jSONSerializer2.locale);
                instance2.setTimeInMillis(time);
                int i = instance2.get(1);
                int i2 = instance2.get(2) + 1;
                int i3 = instance2.get(5);
                int i4 = instance2.get(11);
                int i5 = instance2.get(12);
                int i6 = instance2.get(13);
                int i7 = instance2.get(14);
                if (i7 != 0) {
                    cArr = "0000-00-00T00:00:00.000".toCharArray();
                    SerializeWriter.getChars((long) i7, 23, cArr);
                    SerializeWriter.getChars((long) i6, 19, cArr);
                    SerializeWriter.getChars((long) i5, 16, cArr);
                    SerializeWriter.getChars((long) i4, 13, cArr);
                    SerializeWriter.getChars((long) i3, 10, cArr);
                    SerializeWriter.getChars((long) i2, 7, cArr);
                    SerializeWriter.getChars((long) i, 4, cArr);
                } else if (i6 == 0 && i5 == 0 && i4 == 0) {
                    cArr = "0000-00-00".toCharArray();
                    SerializeWriter.getChars((long) i3, 10, cArr);
                    SerializeWriter.getChars((long) i2, 7, cArr);
                    SerializeWriter.getChars((long) i, 4, cArr);
                } else {
                    cArr = "0000-00-00T00:00:00".toCharArray();
                    SerializeWriter.getChars((long) i6, 19, cArr);
                    SerializeWriter.getChars((long) i5, 16, cArr);
                    SerializeWriter.getChars((long) i4, 13, cArr);
                    SerializeWriter.getChars((long) i3, 10, cArr);
                    SerializeWriter.getChars((long) i2, 7, cArr);
                    SerializeWriter.getChars((long) i, 4, cArr);
                }
                serializeWriter.write(cArr);
                if ((serializeWriter.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
                    serializeWriter.write(39);
                } else {
                    serializeWriter.write(34);
                }
            } else {
                serializeWriter.writeLong(time);
            }
        } else if (obj.getClass() == Date.class) {
            serializeWriter.write((String) "new Date(");
            serializeWriter.writeLong(((Date) obj3).getTime());
            serializeWriter.write(41);
        } else {
            serializeWriter.write(123);
            serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY, false);
            jSONSerializer2.write(obj.getClass().getName());
            serializeWriter.write(44);
            serializeWriter.writeFieldName("val", false);
            serializeWriter.writeLong(((Date) obj3).getTime());
            serializeWriter.write(125);
        }
    }

    public final <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return deserialze(defaultJSONParser, type, obj, null);
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v13, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r1v16, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: type inference failed for: r1v19, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r1v21, types: [java.lang.Long] */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v16, types: [java.lang.String]
      assigns: [java.lang.String, java.lang.Long, java.util.Date, java.lang.Object]
      uses: [java.lang.String, ?[OBJECT, ARRAY]]
      mth insns count: 104
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
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r10, java.lang.reflect.Type r11, java.lang.Object r12, java.lang.String r13) {
        /*
            r9 = this;
            com.alibaba.fastjson.parser.JSONLexer r0 = r10.lexer
            int r1 = r0.token()
            r2 = 0
            r3 = 2
            r4 = 16
            if (r1 != r3) goto L_0x001a
            long r5 = r0.longValue()
            java.lang.Long r1 = java.lang.Long.valueOf(r5)
            r0.nextToken(r4)
        L_0x0017:
            r7 = r1
            goto L_0x010e
        L_0x001a:
            r5 = 4
            if (r1 != r5) goto L_0x004b
            java.lang.String r1 = r0.stringVal()
            r0.nextToken(r4)
            int r3 = r0.features
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat
            int r4 = r4.mask
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0017
            com.alibaba.fastjson.parser.JSONLexer r3 = new com.alibaba.fastjson.parser.JSONLexer
            r3.<init>(r1)
            r4 = 1
            boolean r4 = r3.scanISO8601DateIfMatch(r4)
            if (r4 == 0) goto L_0x0047
            java.util.Calendar r1 = r3.calendar
            java.lang.Class<java.util.Calendar> r4 = java.util.Calendar.class
            if (r11 != r4) goto L_0x0043
            r3.close()
            return r1
        L_0x0043:
            java.util.Date r1 = r1.getTime()
        L_0x0047:
            r3.close()
            goto L_0x0017
        L_0x004b:
            r6 = 8
            if (r1 != r6) goto L_0x0055
            r0.nextToken()
            r7 = r2
            goto L_0x010e
        L_0x0055:
            r6 = 12
            r7 = 13
            r8 = 17
            if (r1 != r6) goto L_0x00ca
            r0.nextToken()
            int r1 = r0.token()
            if (r1 != r5) goto L_0x00c1
            java.lang.String r1 = r0.stringVal()
            java.lang.String r6 = "@type"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x008d
            r0.nextToken()
            r10.accept(r8)
            java.lang.String r1 = r0.stringVal()
            com.alibaba.fastjson.parser.ParserConfig r6 = r10.config
            int r8 = r0.features
            java.lang.Class r1 = r6.checkAutoType(r1, r2, r8)
            if (r1 == 0) goto L_0x0087
            r11 = r1
        L_0x0087:
            r10.accept(r5)
            r10.accept(r4)
        L_0x008d:
            r1 = 58
            r0.nextTokenWithChar(r1)
            int r1 = r0.token()
            if (r1 != r3) goto L_0x00a8
            long r3 = r0.longValue()
            r0.nextToken()
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            r10.accept(r7)
            goto L_0x0017
        L_0x00a8:
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "syntax error : "
            r11.<init>(r12)
            java.lang.String r12 = com.alibaba.fastjson.parser.JSONToken.name(r1)
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x00c1:
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.String r11 = "syntax error"
            r10.<init>(r11)
            throw r10
        L_0x00ca:
            int r1 = r10.resolveStatus
            if (r1 != r3) goto L_0x0108
            r1 = 0
            r10.resolveStatus = r1
            r10.accept(r4)
            int r1 = r0.token()
            if (r1 != r5) goto L_0x00ff
            java.lang.String r1 = "val"
            java.lang.String r3 = r0.stringVal()
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x00f0
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.String r11 = "syntax error"
            r10.<init>(r11)
            throw r10
        L_0x00f0:
            r0.nextToken()
            r10.accept(r8)
            java.lang.Object r1 = r10.parse()
            r10.accept(r7)
            goto L_0x0017
        L_0x00ff:
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.String r11 = "syntax error"
            r10.<init>(r11)
            throw r10
        L_0x0108:
            java.lang.Object r1 = r10.parse()
            goto L_0x0017
        L_0x010e:
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r8 = r13
            java.lang.Object r10 = r3.cast(r4, r5, r6, r7, r8)
            java.lang.Class<java.util.Calendar> r12 = java.util.Calendar.class
            if (r11 != r12) goto L_0x0131
            boolean r11 = r10 instanceof java.util.Calendar
            if (r11 == 0) goto L_0x0120
            return r10
        L_0x0120:
            java.util.Date r10 = (java.util.Date) r10
            if (r10 != 0) goto L_0x0125
            return r2
        L_0x0125:
            java.util.TimeZone r11 = r0.timeZone
            java.util.Locale r12 = r0.locale
            java.util.Calendar r11 = java.util.Calendar.getInstance(r11, r12)
            r11.setTime(r10)
            return r11
        L_0x0131:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.DateCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.String):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public final <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, String str) {
        DateFormat dateFormat;
        if (obj2 == null) {
            return null;
        }
        if (obj2 instanceof Date) {
            return obj2;
        }
        if (obj2 instanceof BigDecimal) {
            return new Date(((BigDecimal) obj2).longValueExact());
        }
        if (obj2 instanceof Number) {
            return new Date(((Number) obj2).longValue());
        }
        if (obj2 instanceof String) {
            String str2 = (String) obj2;
            if (str2.length() == 0) {
                return null;
            }
            JSONLexer jSONLexer = new JSONLexer(str2);
            try {
                if (jSONLexer.scanISO8601DateIfMatch(false)) {
                    T t = jSONLexer.calendar;
                    if (type == Calendar.class) {
                        return t;
                    }
                    T time = t.getTime();
                    jSONLexer.close();
                    return time;
                }
                jSONLexer.close();
                if ("0000-00-00".equals(str2) || "0000-00-00T00:00:00".equalsIgnoreCase(str2) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(str2)) {
                    return null;
                }
                if (str != null) {
                    dateFormat = new SimpleDateFormat(str);
                } else {
                    dateFormat = defaultJSONParser.getDateFormat();
                }
                try {
                    return dateFormat.parse(str2);
                } catch (ParseException unused) {
                    return new Date(Long.parseLong(str2));
                }
            } finally {
                jSONLexer.close();
            }
        } else {
            throw new JSONException("parse error");
        }
    }
}
