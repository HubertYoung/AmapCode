package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] smartMatchHashArray;
    private transient int[] smartMatchHashArrayMapping;
    private final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, cls, type, JavaBeanInfo.build(cls, cls.getModifiers(), type, false, true, true, true, parserConfig.propertyNamingStrategy));
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type, JavaBeanInfo javaBeanInfo) {
        String[] strArr;
        this.clazz = cls;
        this.beanInfo = javaBeanInfo;
        this.sortedFieldDeserializers = new FieldDeserializer[javaBeanInfo.sortedFields.length];
        int length = javaBeanInfo.sortedFields.length;
        Map<String, FieldDeserializer> map = null;
        int i = 0;
        while (i < length) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i];
            FieldDeserializer createFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, cls, fieldInfo);
            this.sortedFieldDeserializers[i] = createFieldDeserializer;
            Map<String, FieldDeserializer> map2 = map;
            for (String str : fieldInfo.alternateNames) {
                if (map2 == null) {
                    map2 = new HashMap<>();
                }
                map2.put(str, createFieldDeserializer);
            }
            i++;
            map = map2;
        }
        this.alterNameFieldDeserializers = map;
        this.fieldDeserializers = new FieldDeserializer[javaBeanInfo.fields.length];
        int length2 = javaBeanInfo.fields.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(javaBeanInfo.fields[i2].name);
        }
    }

    /* access modifiers changed from: protected */
    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) {
        Object obj;
        FieldInfo[] fieldInfoArr;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject((defaultJSONParser.lexer.features & Feature.OrderedField.mask) != 0));
        } else if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        } else {
            if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
                return null;
            }
            try {
                Constructor<?> constructor = this.beanInfo.defaultConstructor;
                if (this.beanInfo.defaultConstructorParameterSize != 0) {
                    obj = constructor.newInstance(new Object[]{defaultJSONParser.contex.object});
                } else if (constructor != null) {
                    obj = constructor.newInstance(new Object[0]);
                } else {
                    obj = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
                }
                if (!(defaultJSONParser == null || (defaultJSONParser.lexer.features & Feature.InitStringFieldAsEmpty.mask) == 0)) {
                    for (FieldInfo fieldInfo : this.beanInfo.fields) {
                        if (fieldInfo.fieldClass == String.class) {
                            fieldInfo.set(obj, "");
                        }
                    }
                }
                return obj;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("create instance error, class ");
                sb.append(this.clazz.getName());
                throw new JSONException(sb.toString(), e);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return deserialze(defaultJSONParser, type, obj, null);
    }

    private <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        Enum enumR;
        char c6;
        char c7;
        char c8;
        char c9;
        char c10;
        char c11;
        String str;
        char c12;
        char c13;
        char c14;
        char c15;
        char c16;
        DefaultJSONParser defaultJSONParser2 = defaultJSONParser;
        JSONLexer jSONLexer = defaultJSONParser2.lexer;
        T createInstance = createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i = 0;
        while (i < length) {
            char c17 = i == length + -1 ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
            Class<?> cls = fieldInfo.fieldClass;
            try {
                if (cls == Integer.TYPE) {
                    int scanLongValue = (int) jSONLexer.scanLongValue();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setInt(createInstance, scanLongValue);
                    } else {
                        fieldDeserializer.setValue((Object) createInstance, (Object) Integer.valueOf(scanLongValue));
                    }
                    if (jSONLexer.ch == ',') {
                        int i2 = jSONLexer.bp + 1;
                        jSONLexer.bp = i2;
                        if (i2 >= jSONLexer.len) {
                            c16 = JSONLexer.EOI;
                        } else {
                            c16 = jSONLexer.text.charAt(i2);
                        }
                        jSONLexer.ch = c16;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i3 = jSONLexer.bp + 1;
                        jSONLexer.bp = i3;
                        if (i3 >= jSONLexer.len) {
                            c15 = JSONLexer.EOI;
                        } else {
                            c15 = jSONLexer.text.charAt(i3);
                        }
                        jSONLexer.ch = c15;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls == String.class) {
                    if (jSONLexer.ch == '\"') {
                        str = jSONLexer.scanStringValue('\"');
                    } else if (jSONLexer.ch != 'n' || !jSONLexer.text.startsWith("null", jSONLexer.bp)) {
                        throw new JSONException("not match string. feild : ".concat(String.valueOf(obj)));
                    } else {
                        jSONLexer.bp += 4;
                        int i4 = jSONLexer.bp;
                        if (jSONLexer.bp >= jSONLexer.len) {
                            c14 = JSONLexer.EOI;
                        } else {
                            c14 = jSONLexer.text.charAt(i4);
                        }
                        jSONLexer.ch = c14;
                        str = null;
                    }
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.set(createInstance, str);
                    } else {
                        fieldDeserializer.setValue((Object) createInstance, (Object) str);
                    }
                    if (jSONLexer.ch == ',') {
                        int i5 = jSONLexer.bp + 1;
                        jSONLexer.bp = i5;
                        if (i5 >= jSONLexer.len) {
                            c13 = JSONLexer.EOI;
                        } else {
                            c13 = jSONLexer.text.charAt(i5);
                        }
                        jSONLexer.ch = c13;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i6 = jSONLexer.bp + 1;
                        jSONLexer.bp = i6;
                        if (i6 >= jSONLexer.len) {
                            c12 = JSONLexer.EOI;
                        } else {
                            c12 = jSONLexer.text.charAt(i6);
                        }
                        jSONLexer.ch = c12;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls == Long.TYPE) {
                    long scanLongValue2 = jSONLexer.scanLongValue();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setLong(createInstance, scanLongValue2);
                    } else {
                        fieldDeserializer.setValue((Object) createInstance, (Object) new Long(scanLongValue2));
                    }
                    if (jSONLexer.ch == ',') {
                        int i7 = jSONLexer.bp + 1;
                        jSONLexer.bp = i7;
                        if (i7 >= jSONLexer.len) {
                            c11 = JSONLexer.EOI;
                        } else {
                            c11 = jSONLexer.text.charAt(i7);
                        }
                        jSONLexer.ch = c11;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i8 = jSONLexer.bp + 1;
                        jSONLexer.bp = i8;
                        if (i8 >= jSONLexer.len) {
                            c10 = JSONLexer.EOI;
                        } else {
                            c10 = jSONLexer.text.charAt(i8);
                        }
                        jSONLexer.ch = c10;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls == Boolean.TYPE) {
                    boolean scanBoolean = jSONLexer.scanBoolean();
                    if (fieldInfo.fieldAccess) {
                        fieldInfo.field.setBoolean(createInstance, scanBoolean);
                    } else {
                        fieldDeserializer.setValue((Object) createInstance, (Object) Boolean.valueOf(scanBoolean));
                    }
                    if (jSONLexer.ch == ',') {
                        int i9 = jSONLexer.bp + 1;
                        jSONLexer.bp = i9;
                        if (i9 >= jSONLexer.len) {
                            c9 = JSONLexer.EOI;
                        } else {
                            c9 = jSONLexer.text.charAt(i9);
                        }
                        jSONLexer.ch = c9;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i10 = jSONLexer.bp + 1;
                        jSONLexer.bp = i10;
                        if (i10 >= jSONLexer.len) {
                            c8 = JSONLexer.EOI;
                        } else {
                            c8 = jSONLexer.text.charAt(i10);
                        }
                        jSONLexer.ch = c8;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls.isEnum()) {
                    char c18 = jSONLexer.ch;
                    if (c18 == '\"') {
                        String scanSymbol = jSONLexer.scanSymbol(defaultJSONParser2.symbolTable);
                        if (scanSymbol == null) {
                            enumR = null;
                        } else {
                            enumR = Enum.valueOf(cls, scanSymbol);
                        }
                    } else if (c18 < '0' || c18 > '9') {
                        StringBuilder sb = new StringBuilder("illegal enum.");
                        sb.append(jSONLexer.info());
                        throw new JSONException(sb.toString());
                    } else {
                        enumR = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser2.config)).ordinalEnums[(int) jSONLexer.scanLongValue()];
                    }
                    fieldDeserializer.setValue((Object) createInstance, (Object) enumR);
                    if (jSONLexer.ch == ',') {
                        int i11 = jSONLexer.bp + 1;
                        jSONLexer.bp = i11;
                        if (i11 >= jSONLexer.len) {
                            c7 = JSONLexer.EOI;
                        } else {
                            c7 = jSONLexer.text.charAt(i11);
                        }
                        jSONLexer.ch = c7;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i12 = jSONLexer.bp + 1;
                        jSONLexer.bp = i12;
                        if (i12 >= jSONLexer.len) {
                            c6 = JSONLexer.EOI;
                        } else {
                            c6 = jSONLexer.text.charAt(i12);
                        }
                        jSONLexer.ch = c6;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else if (cls == Date.class && jSONLexer.ch == '1') {
                    fieldDeserializer.setValue((Object) createInstance, (Object) new Date(jSONLexer.scanLongValue()));
                    if (jSONLexer.ch == ',') {
                        int i13 = jSONLexer.bp + 1;
                        jSONLexer.bp = i13;
                        if (i13 >= jSONLexer.len) {
                            c5 = JSONLexer.EOI;
                        } else {
                            c5 = jSONLexer.text.charAt(i13);
                        }
                        jSONLexer.ch = c5;
                        jSONLexer.token = 16;
                    } else if (jSONLexer.ch == ']') {
                        int i14 = jSONLexer.bp + 1;
                        jSONLexer.bp = i14;
                        if (i14 >= jSONLexer.len) {
                            c4 = JSONLexer.EOI;
                        } else {
                            c4 = jSONLexer.text.charAt(i14);
                        }
                        jSONLexer.ch = c4;
                        jSONLexer.token = 15;
                    } else {
                        jSONLexer.nextToken();
                    }
                } else {
                    if (jSONLexer.ch == '[') {
                        int i15 = jSONLexer.bp + 1;
                        jSONLexer.bp = i15;
                        if (i15 >= jSONLexer.len) {
                            c3 = JSONLexer.EOI;
                        } else {
                            c3 = jSONLexer.text.charAt(i15);
                        }
                        jSONLexer.ch = c3;
                        jSONLexer.token = 14;
                    } else if (jSONLexer.ch == '{') {
                        int i16 = jSONLexer.bp + 1;
                        jSONLexer.bp = i16;
                        if (i16 >= jSONLexer.len) {
                            c2 = JSONLexer.EOI;
                        } else {
                            c2 = jSONLexer.text.charAt(i16);
                        }
                        jSONLexer.ch = c2;
                        jSONLexer.token = 12;
                    } else {
                        jSONLexer.nextToken();
                    }
                    fieldDeserializer.parseField(defaultJSONParser2, createInstance, fieldInfo.fieldType, null);
                    if (c17 == ']') {
                        if (jSONLexer.token != 15) {
                            throw new JSONException("syntax error");
                        }
                    } else if (c17 == ',' && jSONLexer.token != 16) {
                        throw new JSONException("syntax error");
                    }
                }
                i++;
            } catch (IllegalAccessException e) {
                StringBuilder sb2 = new StringBuilder("set ");
                sb2.append(fieldInfo.name);
                sb2.append("error");
                throw new JSONException(sb2.toString(), e);
            }
        }
        if (jSONLexer.ch == ',') {
            int i17 = jSONLexer.bp + 1;
            jSONLexer.bp = i17;
            if (i17 >= jSONLexer.len) {
                c = JSONLexer.EOI;
            } else {
                c = jSONLexer.text.charAt(i17);
            }
            jSONLexer.ch = c;
            jSONLexer.token = 16;
        } else {
            jSONLexer.nextToken();
        }
        return createInstance;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:238:0x02cd, code lost:
        if (r12 != 16) goto L_0x02da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:?, code lost:
        r11.nextTokenWithChar(':');
        r1 = r11.token;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x02e8, code lost:
        if (r1 != 4) goto L_0x0376;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x02ea, code lost:
        r1 = r11.stringVal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x02f4, code lost:
        if (com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool.PREFIX_ID.equals(r1) == false) goto L_0x02fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:?, code lost:
        r1 = r15.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x0301, code lost:
        if ("..".equals(r1) == false) goto L_0x0318;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:?, code lost:
        r2 = r15.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:0x0307, code lost:
        if (r2.object == null) goto L_0x030c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x0309, code lost:
        r1 = r2.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x030c, code lost:
        r8.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r2, r1));
        r8.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x031e, code lost:
        if ("$".equals(r1) == false) goto L_0x033b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x0320, code lost:
        r2 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x0323, code lost:
        if (r2.parent == null) goto L_0x0328;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x0325, code lost:
        r2 = r2.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x032a, code lost:
        if (r2.object == null) goto L_0x032f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:0x032c, code lost:
        r1 = r2.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x032f, code lost:
        r8.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r2, r1));
        r8.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:?, code lost:
        r8.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r15, r1));
        r8.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:0x0346, code lost:
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:278:?, code lost:
        r11.nextToken(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:279:0x034d, code lost:
        if (r11.token == 13) goto L_0x035d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:0x0356, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal ref");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x0357, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x0358, code lost:
        r5 = r1;
        r13 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:?, code lost:
        r11.nextToken(16);
        r8.setContext(r15, r1, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:288:0x0365, code lost:
        r2 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x0367, code lost:
        if (r2 == null) goto L_0x036b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x0369, code lost:
        r2.object = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:291:0x036b, code lost:
        r8.setContext(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x036e, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x036f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:294:0x0370, code lost:
        r5 = r1;
        r13 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x0376, code lost:
        r2 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:297:?, code lost:
        r4 = new java.lang.StringBuilder("illegal ref, ");
        r4.append(com.alibaba.fastjson.parser.JSONToken.name(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x038f, code lost:
        throw new com.alibaba.fastjson.JSONException(r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x039e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:319:0x03d1, code lost:
        r13 = r2;
        r12 = r5;
        r1 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:323:0x03f0, code lost:
        if (r4 != null) goto L_0x041b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x03f2, code lost:
        r13 = r8.config.checkAutoType(r1, r7.clazz, r11.features);
        r4 = com.alibaba.fastjson.util.TypeUtils.getClass(r42);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:325:0x0400, code lost:
        if (r4 == null) goto L_0x0414;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:326:0x0402, code lost:
        if (r13 == null) goto L_0x040b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:328:0x0408, code lost:
        if (r4.isAssignableFrom(r13) == false) goto L_0x040b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:331:0x0413, code lost:
        throw new com.alibaba.fastjson.JSONException("type not match");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:332:0x0414, code lost:
        r4 = r8.config.getDeserializer(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:333:0x041b, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:335:0x041e, code lost:
        if ((r4 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer) == false) goto L_0x0433;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:336:0x0420, code lost:
        r4 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r4;
        r6 = r4.deserialze(r8, r13, r10, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:337:0x0427, code lost:
        if (r3 == null) goto L_0x0437;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x0429, code lost:
        r3 = r4.getFieldDeserializer(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:339:0x042d, code lost:
        if (r3 == null) goto L_0x0437;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:340:0x042f, code lost:
        r3.setValue((java.lang.Object) r6, (java.lang.Object) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:341:0x0433, code lost:
        r6 = r4.deserialze(r8, r13, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:342:0x0437, code lost:
        if (r2 == null) goto L_0x043b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:343:0x0439, code lost:
        r2.object = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:344:0x043b, code lost:
        r8.setContext(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:345:0x043e, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:500:0x0623, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:501:0x0624, code lost:
        r1 = r0;
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:531:0x0699, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:532:0x069a, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:534:?, code lost:
        r3 = new java.lang.StringBuilder("create instance error, ");
        r3.append(r7.beanInfo.creatorConstructor.toGenericString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:535:0x06b6, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:541:0x06c8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:542:0x06c9, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:544:?, code lost:
        r3 = new java.lang.StringBuilder("create factory method error, ");
        r3.append(r7.beanInfo.factoryMethod.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:545:0x06e5, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:557:0x0715, code lost:
        throw new com.alibaba.fastjson.JSONException(r2.toString());
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:487:0x0609, B:518:0x0660, B:538:0x06bd] */
    /* JADX WARNING: Removed duplicated region for block: B:227:0x02ae  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x03ae A[Catch:{ all -> 0x039e }] */
    /* JADX WARNING: Removed duplicated region for block: B:346:0x043f A[SYNTHETIC, Splitter:B:346:0x043f] */
    /* JADX WARNING: Removed duplicated region for block: B:353:0x0452  */
    /* JADX WARNING: Removed duplicated region for block: B:355:0x045f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0061 A[Catch:{ all -> 0x0041 }] */
    /* JADX WARNING: Removed duplicated region for block: B:379:0x0498  */
    /* JADX WARNING: Removed duplicated region for block: B:468:0x05b6 A[Catch:{ IllegalAccessException -> 0x0584, all -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:481:0x05fa A[Catch:{ IllegalAccessException -> 0x0584, all -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:564:0x0736  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r41, java.lang.reflect.Type r42, java.lang.Object r43, java.lang.Object r44) {
        /*
            r40 = this;
            r7 = r40
            r8 = r41
            r9 = r42
            r10 = r43
            java.lang.Class<com.alibaba.fastjson.JSON> r2 = com.alibaba.fastjson.JSON.class
            if (r9 == r2) goto L_0x073c
            java.lang.Class<com.alibaba.fastjson.JSONObject> r2 = com.alibaba.fastjson.JSONObject.class
            if (r9 != r2) goto L_0x0012
            goto L_0x073c
        L_0x0012:
            com.alibaba.fastjson.parser.JSONLexer r11 = r8.lexer
            int r2 = r11.token
            r3 = 8
            r12 = 16
            r13 = 0
            if (r2 != r3) goto L_0x0021
            r11.nextToken(r12)
            return r13
        L_0x0021:
            boolean r14 = r11.disableCircularReferenceDetect
            com.alibaba.fastjson.parser.ParseContext r3 = r8.contex
            if (r44 == 0) goto L_0x002b
            if (r3 == 0) goto L_0x002b
            com.alibaba.fastjson.parser.ParseContext r3 = r3.parent
        L_0x002b:
            r15 = r3
            r6 = 13
            if (r2 != r6) goto L_0x0047
            r11.nextToken(r12)     // Catch:{ all -> 0x0041 }
            if (r44 != 0) goto L_0x003b
            java.lang.Object r2 = r40.createInstance(r41, r42)     // Catch:{ all -> 0x0041 }
            r1 = r2
            goto L_0x003d
        L_0x003b:
            r1 = r44
        L_0x003d:
            r8.setContext(r15)
            return r1
        L_0x0041:
            r0 = move-exception
            r5 = r44
        L_0x0044:
            r1 = r0
            goto L_0x0734
        L_0x0047:
            r3 = 14
            r5 = 0
            if (r2 != r3) goto L_0x0069
            com.alibaba.fastjson.parser.JavaBeanInfo r3 = r7.beanInfo     // Catch:{ all -> 0x0041 }
            boolean r3 = r3.supportBeanToArray     // Catch:{ all -> 0x0041 }
            if (r3 != 0) goto L_0x005e
            int r3 = r11.features     // Catch:{ all -> 0x0041 }
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.SupportArrayToBean     // Catch:{ all -> 0x0041 }
            int r4 = r4.mask     // Catch:{ all -> 0x0041 }
            r3 = r3 & r4
            if (r3 == 0) goto L_0x005c
            goto L_0x005e
        L_0x005c:
            r3 = 0
            goto L_0x005f
        L_0x005e:
            r3 = 1
        L_0x005f:
            if (r3 == 0) goto L_0x0069
            java.lang.Object r2 = r40.deserialzeArrayMapping(r41, r42, r43, r44)     // Catch:{ all -> 0x0041 }
            r8.setContext(r15)
            return r2
        L_0x0069:
            r3 = 12
            r4 = 4
            if (r2 == r3) goto L_0x00b2
            if (r2 == r12) goto L_0x00b2
            boolean r3 = r11.isBlankInput()     // Catch:{ all -> 0x0041 }
            if (r3 == 0) goto L_0x007a
            r8.setContext(r15)
            return r13
        L_0x007a:
            if (r2 != r4) goto L_0x008d
            java.lang.String r2 = r11.stringVal()     // Catch:{ all -> 0x0041 }
            int r2 = r2.length()     // Catch:{ all -> 0x0041 }
            if (r2 != 0) goto L_0x008d
            r11.nextToken()     // Catch:{ all -> 0x0041 }
            r8.setContext(r15)
            return r13
        L_0x008d:
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch:{ all -> 0x0041 }
            java.lang.String r3 = "syntax error, expect {, actual "
            r2.<init>(r3)     // Catch:{ all -> 0x0041 }
            java.lang.String r3 = r11.info()     // Catch:{ all -> 0x0041 }
            r2.append(r3)     // Catch:{ all -> 0x0041 }
            boolean r3 = r10 instanceof java.lang.String     // Catch:{ all -> 0x0041 }
            if (r3 == 0) goto L_0x00a8
            java.lang.String r3 = ", fieldName "
            r2.append(r3)     // Catch:{ all -> 0x0041 }
            r2.append(r10)     // Catch:{ all -> 0x0041 }
        L_0x00a8:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0041 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0041 }
            r3.<init>(r2)     // Catch:{ all -> 0x0041 }
            throw r3     // Catch:{ all -> 0x0041 }
        L_0x00b2:
            int r2 = r8.resolveStatus     // Catch:{ all -> 0x072f }
            r3 = 2
            if (r2 != r3) goto L_0x00b9
            r8.resolveStatus = r5     // Catch:{ all -> 0x0041 }
        L_0x00b9:
            com.alibaba.fastjson.parser.JavaBeanInfo r2 = r7.beanInfo     // Catch:{ all -> 0x072f }
            java.lang.String r3 = r2.typeKey     // Catch:{ all -> 0x072f }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r2 = r7.sortedFieldDeserializers     // Catch:{ all -> 0x072f }
            int r2 = r2.length     // Catch:{ all -> 0x072f }
            r17 = 0
            r5 = r44
            r20 = r13
            r21 = r20
            r19 = r14
            r13 = r17
            r1 = 0
        L_0x00cd:
            int r22 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r22 == 0) goto L_0x00e7
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r13 = r7.getFieldDeserializerByHash(r13)     // Catch:{ all -> 0x00e1 }
            if (r13 == 0) goto L_0x00dc
            com.alibaba.fastjson.util.FieldInfo r14 = r13.fieldInfo     // Catch:{ all -> 0x00e1 }
            java.lang.Class<?> r4 = r14.fieldClass     // Catch:{ all -> 0x00e1 }
            goto L_0x00de
        L_0x00dc:
            r4 = 0
            r14 = 0
        L_0x00de:
            r23 = r17
            goto L_0x00ec
        L_0x00e1:
            r0 = move-exception
            r1 = r0
        L_0x00e3:
            r13 = r20
            goto L_0x0734
        L_0x00e7:
            r23 = r13
            r4 = 0
            r13 = 0
            r14 = 0
        L_0x00ec:
            if (r13 != 0) goto L_0x0100
            if (r1 >= r2) goto L_0x00fe
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r4 = r7.sortedFieldDeserializers     // Catch:{ all -> 0x00e1 }
            r4 = r4[r1]     // Catch:{ all -> 0x00e1 }
            com.alibaba.fastjson.util.FieldInfo r13 = r4.fieldInfo     // Catch:{ all -> 0x00e1 }
            java.lang.Class<?> r14 = r13.fieldClass     // Catch:{ all -> 0x00e1 }
            r39 = r13
            r13 = r4
            r4 = r14
            r14 = r39
        L_0x00fe:
            int r1 = r1 + 1
        L_0x0100:
            r22 = r1
            r25 = 0
            if (r13 == 0) goto L_0x02a0
            r27 = r2
            long r1 = r14.nameHashCode     // Catch:{ all -> 0x00e1 }
            java.lang.Class r12 = java.lang.Integer.TYPE     // Catch:{ all -> 0x00e1 }
            r6 = -2
            if (r4 == r12) goto L_0x027c
            java.lang.Class<java.lang.Integer> r12 = java.lang.Integer.class
            if (r4 != r12) goto L_0x0115
            goto L_0x027c
        L_0x0115:
            java.lang.Class r12 = java.lang.Long.TYPE     // Catch:{ all -> 0x00e1 }
            if (r4 == r12) goto L_0x0262
            java.lang.Class<java.lang.Long> r12 = java.lang.Long.class
            if (r4 != r12) goto L_0x011f
            goto L_0x0262
        L_0x011f:
            java.lang.Class<java.lang.String> r12 = java.lang.String.class
            if (r4 != r12) goto L_0x0149
            java.lang.String r1 = r11.scanFieldString(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x0137
        L_0x012b:
            r30 = r1
            r28 = r17
            r31 = r25
            r1 = 1
            r2 = 0
            r6 = 1
        L_0x0134:
            r12 = 0
            goto L_0x02ac
        L_0x0137:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x013f:
            r30 = r1
            r28 = r17
            r31 = r25
            r1 = 0
            r2 = 0
            r6 = 0
            goto L_0x0134
        L_0x0149:
            java.lang.Class<java.util.Date> r12 = java.util.Date.class
            if (r4 != r12) goto L_0x015e
            java.util.Date r1 = r11.scanFieldDate(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x0156
            goto L_0x012b
        L_0x0156:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x015e:
            java.lang.Class r12 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x00e1 }
            if (r4 == r12) goto L_0x024d
            java.lang.Class<java.lang.Boolean> r12 = java.lang.Boolean.class
            if (r4 != r12) goto L_0x0168
            goto L_0x024d
        L_0x0168:
            java.lang.Class r12 = java.lang.Float.TYPE     // Catch:{ all -> 0x00e1 }
            if (r4 == r12) goto L_0x022f
            java.lang.Class<java.lang.Float> r12 = java.lang.Float.class
            if (r4 != r12) goto L_0x0172
            goto L_0x022f
        L_0x0172:
            java.lang.Class r12 = java.lang.Double.TYPE     // Catch:{ all -> 0x00e1 }
            if (r4 == r12) goto L_0x021c
            java.lang.Class<java.lang.Double> r12 = java.lang.Double.class
            if (r4 != r12) goto L_0x017c
            goto L_0x021c
        L_0x017c:
            boolean r12 = r14.isEnum     // Catch:{ all -> 0x00e1 }
            if (r12 == 0) goto L_0x019f
            com.alibaba.fastjson.parser.ParserConfig r12 = r8.config     // Catch:{ all -> 0x00e1 }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r12 = r12.getDeserializer(r4)     // Catch:{ all -> 0x00e1 }
            boolean r12 = r12 instanceof com.alibaba.fastjson.parser.EnumDeserializer     // Catch:{ all -> 0x00e1 }
            if (r12 == 0) goto L_0x019f
            long r1 = r11.scanFieldSymbol(r1)     // Catch:{ all -> 0x00e1 }
            int r12 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r12 <= 0) goto L_0x0197
            java.lang.Enum r1 = r13.getEnumByHashCode(r1)     // Catch:{ all -> 0x00e1 }
            goto L_0x012b
        L_0x0197:
            int r1 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r1 != r6) goto L_0x02a2
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x019f:
            java.lang.Class<int[]> r12 = int[].class
            if (r4 != r12) goto L_0x01b5
            int[] r1 = r11.scanFieldIntArray(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x01ad
            goto L_0x012b
        L_0x01ad:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x01b5:
            java.lang.Class<float[]> r12 = float[].class
            if (r4 != r12) goto L_0x01cb
            float[] r1 = r11.scanFieldFloatArray(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x01c3
            goto L_0x012b
        L_0x01c3:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x01cb:
            java.lang.Class<double[]> r12 = double[].class
            if (r4 != r12) goto L_0x01e1
            double[] r1 = r11.scanFieldDoubleArray(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x01d9
            goto L_0x012b
        L_0x01d9:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x01e1:
            java.lang.Class<float[][]> r12 = float[][].class
            if (r4 != r12) goto L_0x01f7
            float[][] r1 = r11.scanFieldFloatArray2(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x01ef
            goto L_0x012b
        L_0x01ef:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x01f7:
            java.lang.Class<double[][]> r12 = double[][].class
            if (r4 != r12) goto L_0x020d
            double[][] r1 = r11.scanFieldDoubleArray2(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x0205
            goto L_0x012b
        L_0x0205:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x020d:
            long r1 = r14.nameHashCode     // Catch:{ all -> 0x00e1 }
            boolean r1 = r11.matchField(r1)     // Catch:{ all -> 0x00e1 }
            if (r1 == 0) goto L_0x02d0
            r28 = r17
            r31 = r25
            r1 = 1
            goto L_0x02a7
        L_0x021c:
            double r25 = r11.scanFieldDouble(r1)     // Catch:{ all -> 0x00e1 }
            int r1 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r1 <= 0) goto L_0x0227
            r28 = r17
            goto L_0x026c
        L_0x0227:
            int r1 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r1 != r6) goto L_0x02a2
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x022f:
            float r1 = r11.scanFieldFloat(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x023e
            r2 = r1
            r28 = r17
            r31 = r25
            r1 = 1
            goto L_0x0270
        L_0x023e:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x0245
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x0245:
            r2 = r1
            r28 = r17
            r31 = r25
            r1 = 0
            goto L_0x02a8
        L_0x024d:
            boolean r1 = r11.scanFieldBoolean(r1)     // Catch:{ all -> 0x00e1 }
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x025b
            goto L_0x012b
        L_0x025b:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x013f
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x0262:
            long r1 = r11.scanFieldLong(r1)     // Catch:{ all -> 0x00e1 }
            int r12 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r12 <= 0) goto L_0x0272
            r28 = r1
        L_0x026c:
            r31 = r25
            r1 = 1
            r2 = 0
        L_0x0270:
            r6 = 1
            goto L_0x02a9
        L_0x0272:
            int r12 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r12 != r6) goto L_0x0279
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
            goto L_0x0293
        L_0x0279:
            r28 = r1
            goto L_0x02a4
        L_0x027c:
            int r1 = r11.scanFieldInt(r1)     // Catch:{ all -> 0x00e1 }
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 <= 0) goto L_0x028d
            r12 = r1
            r28 = r17
            r31 = r25
            r1 = 1
            r2 = 0
            r6 = 1
            goto L_0x02aa
        L_0x028d:
            int r2 = r11.matchStat     // Catch:{ all -> 0x00e1 }
            if (r2 != r6) goto L_0x0297
            long r13 = r11.fieldHash     // Catch:{ all -> 0x00e1 }
        L_0x0293:
            r1 = r22
            goto L_0x03df
        L_0x0297:
            r12 = r1
            r28 = r17
            r31 = r25
            r1 = 0
            r2 = 0
            r6 = 0
            goto L_0x02aa
        L_0x02a0:
            r27 = r2
        L_0x02a2:
            r28 = r17
        L_0x02a4:
            r31 = r25
            r1 = 0
        L_0x02a7:
            r2 = 0
        L_0x02a8:
            r6 = 0
        L_0x02a9:
            r12 = 0
        L_0x02aa:
            r30 = 0
        L_0x02ac:
            if (r1 != 0) goto L_0x0452
            r33 = r14
            com.alibaba.fastjson.parser.SymbolTable r14 = r8.symbolTable     // Catch:{ all -> 0x044b }
            java.lang.String r14 = r11.scanSymbol(r14)     // Catch:{ all -> 0x044b }
            if (r14 != 0) goto L_0x02d6
            r34 = r12
            int r12 = r11.token     // Catch:{ all -> 0x00e1 }
            r35 = r2
            r2 = 13
            if (r12 != r2) goto L_0x02cb
            r2 = 16
            r11.nextToken(r2)     // Catch:{ all -> 0x00e1 }
            r2 = r20
            goto L_0x03d1
        L_0x02cb:
            r2 = 16
            if (r12 == r2) goto L_0x02d0
            goto L_0x02da
        L_0x02d0:
            r2 = r20
            r12 = 13
            goto L_0x03d9
        L_0x02d6:
            r35 = r2
            r34 = r12
        L_0x02da:
            java.lang.String r2 = "$ref"
            r12 = 58
            if (r2 != r14) goto L_0x0390
            if (r15 == 0) goto L_0x0390
            r11.nextTokenWithChar(r12)     // Catch:{ all -> 0x044b }
            int r1 = r11.token     // Catch:{ all -> 0x044b }
            r2 = 4
            if (r1 != r2) goto L_0x0376
            java.lang.String r1 = r11.stringVal()     // Catch:{ all -> 0x044b }
            java.lang.String r2 = "@"
            boolean r2 = r2.equals(r1)     // Catch:{ all -> 0x044b }
            if (r2 == 0) goto L_0x02fb
            java.lang.Object r1 = r15.object     // Catch:{ all -> 0x00e1 }
        L_0x02f8:
            r2 = 13
            goto L_0x0348
        L_0x02fb:
            java.lang.String r2 = ".."
            boolean r2 = r2.equals(r1)     // Catch:{ all -> 0x044b }
            if (r2 == 0) goto L_0x0318
            com.alibaba.fastjson.parser.ParseContext r2 = r15.parent     // Catch:{ all -> 0x00e1 }
            java.lang.Object r3 = r2.object     // Catch:{ all -> 0x00e1 }
            if (r3 == 0) goto L_0x030c
            java.lang.Object r1 = r2.object     // Catch:{ all -> 0x00e1 }
            goto L_0x02f8
        L_0x030c:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r3 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x00e1 }
            r3.<init>(r2, r1)     // Catch:{ all -> 0x00e1 }
            r8.addResolveTask(r3)     // Catch:{ all -> 0x00e1 }
            r1 = 1
            r8.resolveStatus = r1     // Catch:{ all -> 0x00e1 }
            goto L_0x0346
        L_0x0318:
            java.lang.String r2 = "$"
            boolean r2 = r2.equals(r1)     // Catch:{ all -> 0x044b }
            if (r2 == 0) goto L_0x033b
            r2 = r15
        L_0x0321:
            com.alibaba.fastjson.parser.ParseContext r3 = r2.parent     // Catch:{ all -> 0x00e1 }
            if (r3 == 0) goto L_0x0328
            com.alibaba.fastjson.parser.ParseContext r2 = r2.parent     // Catch:{ all -> 0x00e1 }
            goto L_0x0321
        L_0x0328:
            java.lang.Object r3 = r2.object     // Catch:{ all -> 0x00e1 }
            if (r3 == 0) goto L_0x032f
            java.lang.Object r1 = r2.object     // Catch:{ all -> 0x00e1 }
            goto L_0x02f8
        L_0x032f:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r3 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x00e1 }
            r3.<init>(r2, r1)     // Catch:{ all -> 0x00e1 }
            r8.addResolveTask(r3)     // Catch:{ all -> 0x00e1 }
            r1 = 1
            r8.resolveStatus = r1     // Catch:{ all -> 0x00e1 }
            goto L_0x0346
        L_0x033b:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r2 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x044b }
            r2.<init>(r15, r1)     // Catch:{ all -> 0x044b }
            r8.addResolveTask(r2)     // Catch:{ all -> 0x044b }
            r2 = 1
            r8.resolveStatus = r2     // Catch:{ all -> 0x044b }
        L_0x0346:
            r1 = r5
            goto L_0x02f8
        L_0x0348:
            r11.nextToken(r2)     // Catch:{ all -> 0x036f }
            int r3 = r11.token     // Catch:{ all -> 0x036f }
            if (r3 == r2) goto L_0x035d
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0357 }
            java.lang.String r3 = "illegal ref"
            r2.<init>(r3)     // Catch:{ all -> 0x0357 }
            throw r2     // Catch:{ all -> 0x0357 }
        L_0x0357:
            r0 = move-exception
            r5 = r1
            r13 = r20
            goto L_0x0044
        L_0x035d:
            r2 = 16
            r11.nextToken(r2)     // Catch:{ all -> 0x036f }
            r8.setContext(r15, r1, r10)     // Catch:{ all -> 0x036f }
            r2 = r20
            if (r2 == 0) goto L_0x036b
            r2.object = r1
        L_0x036b:
            r8.setContext(r15)
            return r1
        L_0x036f:
            r0 = move-exception
            r2 = r20
            r5 = r1
            r13 = r2
            goto L_0x0044
        L_0x0376:
            r2 = r20
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x039e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x039e }
            java.lang.String r6 = "illegal ref, "
            r4.<init>(r6)     // Catch:{ all -> 0x039e }
            java.lang.String r1 = com.alibaba.fastjson.parser.JSONToken.name(r1)     // Catch:{ all -> 0x039e }
            r4.append(r1)     // Catch:{ all -> 0x039e }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x039e }
            r3.<init>(r1)     // Catch:{ all -> 0x039e }
            throw r3     // Catch:{ all -> 0x039e }
        L_0x0390:
            r2 = r20
            if (r3 == 0) goto L_0x03a1
            boolean r16 = r3.equals(r14)     // Catch:{ all -> 0x039e }
            if (r16 != 0) goto L_0x039b
            goto L_0x03a1
        L_0x039b:
            r1 = 58
            goto L_0x03a6
        L_0x039e:
            r0 = move-exception
            goto L_0x044e
        L_0x03a1:
            java.lang.String r12 = "@type"
            if (r12 != r14) goto L_0x0448
            goto L_0x039b
        L_0x03a6:
            r11.nextTokenWithChar(r1)     // Catch:{ all -> 0x039e }
            int r1 = r11.token     // Catch:{ all -> 0x039e }
            r4 = 4
            if (r1 != r4) goto L_0x043f
            java.lang.String r1 = r11.stringVal()     // Catch:{ all -> 0x039e }
            r4 = 16
            r11.nextToken(r4)     // Catch:{ all -> 0x039e }
            boolean r4 = r9 instanceof java.lang.Class     // Catch:{ all -> 0x039e }
            if (r4 == 0) goto L_0x03e8
            r4 = r9
            java.lang.Class r4 = (java.lang.Class) r4     // Catch:{ all -> 0x039e }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x039e }
            boolean r4 = r1.equals(r4)     // Catch:{ all -> 0x039e }
            if (r4 == 0) goto L_0x03e8
            int r1 = r11.token     // Catch:{ all -> 0x039e }
            r12 = 13
            if (r1 != r12) goto L_0x03d9
            r11.nextToken()     // Catch:{ all -> 0x039e }
        L_0x03d1:
            r13 = r2
            r12 = r5
            r1 = r21
            r16 = 0
            goto L_0x0605
        L_0x03d9:
            r20 = r2
            r1 = r22
            r13 = r23
        L_0x03df:
            r2 = r27
            r4 = 4
            r6 = 13
            r12 = 16
            goto L_0x00cd
        L_0x03e8:
            com.alibaba.fastjson.parser.ParserConfig r4 = r8.config     // Catch:{ all -> 0x039e }
            com.alibaba.fastjson.parser.JavaBeanInfo r6 = r7.beanInfo     // Catch:{ all -> 0x039e }
            com.alibaba.fastjson.parser.JavaBeanDeserializer r4 = r7.getSeeAlso(r4, r6, r1)     // Catch:{ all -> 0x039e }
            if (r4 != 0) goto L_0x041b
            com.alibaba.fastjson.parser.ParserConfig r4 = r8.config     // Catch:{ all -> 0x039e }
            java.lang.Class<?> r6 = r7.clazz     // Catch:{ all -> 0x039e }
            int r11 = r11.features     // Catch:{ all -> 0x039e }
            java.lang.Class r13 = r4.checkAutoType(r1, r6, r11)     // Catch:{ all -> 0x039e }
            java.lang.Class r4 = com.alibaba.fastjson.util.TypeUtils.getClass(r42)     // Catch:{ all -> 0x039e }
            if (r4 == 0) goto L_0x0414
            if (r13 == 0) goto L_0x040b
            boolean r4 = r4.isAssignableFrom(r13)     // Catch:{ all -> 0x039e }
            if (r4 == 0) goto L_0x040b
            goto L_0x0414
        L_0x040b:
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x039e }
            java.lang.String r3 = "type not match"
            r1.<init>(r3)     // Catch:{ all -> 0x039e }
            throw r1     // Catch:{ all -> 0x039e }
        L_0x0414:
            com.alibaba.fastjson.parser.ParserConfig r4 = r8.config     // Catch:{ all -> 0x039e }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r4.getDeserializer(r13)     // Catch:{ all -> 0x039e }
            goto L_0x041c
        L_0x041b:
            r13 = 0
        L_0x041c:
            boolean r6 = r4 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer     // Catch:{ all -> 0x039e }
            if (r6 == 0) goto L_0x0433
            com.alibaba.fastjson.parser.JavaBeanDeserializer r4 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r4     // Catch:{ all -> 0x039e }
            r6 = 0
            java.lang.Object r6 = r4.deserialze(r8, r13, r10, r6)     // Catch:{ all -> 0x039e }
            if (r3 == 0) goto L_0x0437
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r3 = r4.getFieldDeserializer(r3)     // Catch:{ all -> 0x039e }
            if (r3 == 0) goto L_0x0437
            r3.setValue(r6, r1)     // Catch:{ all -> 0x039e }
            goto L_0x0437
        L_0x0433:
            java.lang.Object r6 = r4.deserialze(r8, r13, r10)     // Catch:{ all -> 0x039e }
        L_0x0437:
            if (r2 == 0) goto L_0x043b
            r2.object = r5
        L_0x043b:
            r8.setContext(r15)
            return r6
        L_0x043f:
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x039e }
            java.lang.String r3 = "syntax error"
            r1.<init>(r3)     // Catch:{ all -> 0x039e }
            throw r1     // Catch:{ all -> 0x039e }
        L_0x0448:
            r12 = 13
            goto L_0x045d
        L_0x044b:
            r0 = move-exception
            r2 = r20
        L_0x044e:
            r1 = r0
            r13 = r2
            goto L_0x0734
        L_0x0452:
            r35 = r2
            r34 = r12
            r33 = r14
            r2 = r20
            r12 = 13
            r14 = 0
        L_0x045d:
            if (r5 != 0) goto L_0x048f
            if (r21 != 0) goto L_0x048f
            java.lang.Object r12 = r40.createInstance(r41, r42)     // Catch:{ all -> 0x0487 }
            if (r12 != 0) goto L_0x047a
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ all -> 0x0474 }
            r36 = r2
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r2 = r7.fieldDeserializers     // Catch:{ all -> 0x0472 }
            int r2 = r2.length     // Catch:{ all -> 0x0472 }
            r5.<init>(r2)     // Catch:{ all -> 0x0472 }
            goto L_0x047e
        L_0x0472:
            r0 = move-exception
            goto L_0x0477
        L_0x0474:
            r0 = move-exception
            r36 = r2
        L_0x0477:
            r1 = r0
            r5 = r12
            goto L_0x048b
        L_0x047a:
            r36 = r2
            r5 = r21
        L_0x047e:
            if (r19 != 0) goto L_0x0494
            com.alibaba.fastjson.parser.ParseContext r2 = r8.setContext(r15, r12, r10)     // Catch:{ all -> 0x0472 }
            r20 = r2
            goto L_0x0496
        L_0x0487:
            r0 = move-exception
            r36 = r2
            r1 = r0
        L_0x048b:
            r13 = r36
            goto L_0x0734
        L_0x048f:
            r36 = r2
            r12 = r5
            r5 = r21
        L_0x0494:
            r20 = r36
        L_0x0496:
            if (r1 == 0) goto L_0x05b6
            if (r6 != 0) goto L_0x04a9
            r13.parseField(r8, r12, r9, r5)     // Catch:{ all -> 0x072a }
            r10 = r3
            r21 = r5
            r9 = r27
        L_0x04a2:
            r13 = 1
            r14 = 13
            r16 = 0
            goto L_0x05f4
        L_0x04a9:
            if (r12 != 0) goto L_0x04f8
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ all -> 0x072a }
            if (r4 == r1) goto L_0x04e3
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            if (r4 != r1) goto L_0x04b4
            goto L_0x04e3
        L_0x04b4:
            java.lang.Class r1 = java.lang.Long.TYPE     // Catch:{ all -> 0x072a }
            if (r4 == r1) goto L_0x04dc
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            if (r4 != r1) goto L_0x04bd
            goto L_0x04dc
        L_0x04bd:
            java.lang.Class r1 = java.lang.Float.TYPE     // Catch:{ all -> 0x072a }
            if (r4 == r1) goto L_0x04d5
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            if (r4 != r1) goto L_0x04c6
            goto L_0x04d5
        L_0x04c6:
            java.lang.Class r1 = java.lang.Double.TYPE     // Catch:{ all -> 0x072a }
            if (r4 == r1) goto L_0x04ce
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            if (r4 != r1) goto L_0x04e9
        L_0x04ce:
            r1 = r31
            java.lang.Double r30 = java.lang.Double.valueOf(r1)     // Catch:{ all -> 0x072a }
            goto L_0x04e9
        L_0x04d5:
            r6 = r35
            java.lang.Float r30 = java.lang.Float.valueOf(r6)     // Catch:{ all -> 0x072a }
            goto L_0x04e9
        L_0x04dc:
            r1 = r28
            java.lang.Long r30 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x072a }
            goto L_0x04e9
        L_0x04e3:
            r14 = r34
            java.lang.Integer r30 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x072a }
        L_0x04e9:
            r2 = r30
            r1 = r33
            java.lang.String r1 = r1.name     // Catch:{ all -> 0x072a }
            r5.put(r1, r2)     // Catch:{ all -> 0x072a }
            r37 = r3
            r38 = r5
            goto L_0x05a4
        L_0x04f8:
            r37 = r3
            r9 = r28
            r1 = r31
            r3 = r33
            r14 = r34
            r6 = r35
            if (r30 != 0) goto L_0x059d
            r38 = r5
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 == r5) goto L_0x0570
            java.lang.Class<java.lang.Integer> r5 = java.lang.Integer.class
            if (r4 != r5) goto L_0x0512
            goto L_0x0570
        L_0x0512:
            java.lang.Class r5 = java.lang.Long.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 == r5) goto L_0x055c
            java.lang.Class<java.lang.Long> r5 = java.lang.Long.class
            if (r4 != r5) goto L_0x051b
            goto L_0x055c
        L_0x051b:
            java.lang.Class r5 = java.lang.Float.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 == r5) goto L_0x0548
            java.lang.Class<java.lang.Float> r5 = java.lang.Float.class
            if (r4 != r5) goto L_0x0524
            goto L_0x0548
        L_0x0524:
            java.lang.Class r5 = java.lang.Double.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 == r5) goto L_0x0534
            java.lang.Class<java.lang.Double> r5 = java.lang.Double.class
            if (r4 != r5) goto L_0x052d
            goto L_0x0534
        L_0x052d:
            r5 = r30
            r13.setValue(r12, r5)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0534:
            boolean r5 = r3.fieldAccess     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r5 == 0) goto L_0x0540
            java.lang.Class r5 = java.lang.Double.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 != r5) goto L_0x0540
            r13.setValue(r12, r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0540:
            java.lang.Double r1 = java.lang.Double.valueOf(r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            r13.setValue(r12, r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0548:
            boolean r1 = r3.fieldAccess     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r1 == 0) goto L_0x0554
            java.lang.Class r1 = java.lang.Float.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 != r1) goto L_0x0554
            r13.setValue(r12, r6)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0554:
            java.lang.Float r1 = java.lang.Float.valueOf(r6)     // Catch:{ IllegalAccessException -> 0x0584 }
            r13.setValue(r12, r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x055c:
            boolean r1 = r3.fieldAccess     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r1 == 0) goto L_0x0568
            java.lang.Class r1 = java.lang.Long.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 != r1) goto L_0x0568
            r13.setValue(r12, r9)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0568:
            java.lang.Long r1 = java.lang.Long.valueOf(r9)     // Catch:{ IllegalAccessException -> 0x0584 }
            r13.setValue(r12, r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0570:
            boolean r1 = r3.fieldAccess     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r1 == 0) goto L_0x057c
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x0584 }
            if (r4 != r1) goto L_0x057c
            r13.setValue(r12, r14)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x057c:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r14)     // Catch:{ IllegalAccessException -> 0x0584 }
            r13.setValue(r12, r1)     // Catch:{ IllegalAccessException -> 0x0584 }
            goto L_0x05a4
        L_0x0584:
            r0 = move-exception
            r1 = r0
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x072a }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x072a }
            java.lang.String r5 = "set property error, "
            r4.<init>(r5)     // Catch:{ all -> 0x072a }
            java.lang.String r3 = r3.name     // Catch:{ all -> 0x072a }
            r4.append(r3)     // Catch:{ all -> 0x072a }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x072a }
            r2.<init>(r3, r1)     // Catch:{ all -> 0x072a }
            throw r2     // Catch:{ all -> 0x072a }
        L_0x059d:
            r38 = r5
            r5 = r30
            r13.setValue(r12, r5)     // Catch:{ all -> 0x072a }
        L_0x05a4:
            int r1 = r11.matchStat     // Catch:{ all -> 0x072a }
            r4 = 4
            if (r1 != r4) goto L_0x05ae
            r21 = r38
            r16 = 0
            goto L_0x0601
        L_0x05ae:
            r9 = r27
            r10 = r37
            r21 = r38
            goto L_0x04a2
        L_0x05b6:
            r37 = r3
            r38 = r5
            r4 = 4
            r1 = r7
            r9 = r27
            r5 = 1
            r2 = r8
            r10 = r37
            r3 = r14
            r13 = 1
            r14 = 4
            r4 = r12
            r21 = r38
            r16 = 0
            r5 = r42
            r14 = 13
            r6 = r21
            boolean r1 = r1.parseField(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x072a }
            if (r1 != 0) goto L_0x05e5
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            if (r1 != r14) goto L_0x05de
            r11.nextToken()     // Catch:{ all -> 0x072a }
            goto L_0x0601
        L_0x05de:
            r2 = 16
        L_0x05e0:
            r3 = r43
            r5 = 0
            goto L_0x0716
        L_0x05e5:
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            r2 = 17
            if (r1 != r2) goto L_0x05f4
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x072a }
            java.lang.String r2 = "syntax error, unexpect token ':'"
            r1.<init>(r2)     // Catch:{ all -> 0x072a }
            throw r1     // Catch:{ all -> 0x072a }
        L_0x05f4:
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            r2 = 16
            if (r1 == r2) goto L_0x05e0
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            if (r1 != r14) goto L_0x06ee
            r11.nextToken(r2)     // Catch:{ all -> 0x072a }
        L_0x0601:
            r13 = r20
            r1 = r21
        L_0x0605:
            if (r12 != 0) goto L_0x06e6
            if (r1 != 0) goto L_0x0628
            java.lang.Object r1 = r40.createInstance(r41, r42)     // Catch:{ all -> 0x0623 }
            if (r13 != 0) goto L_0x061b
            r3 = r43
            com.alibaba.fastjson.parser.ParseContext r2 = r8.setContext(r15, r1, r3)     // Catch:{ all -> 0x0617 }
            r13 = r2
            goto L_0x061b
        L_0x0617:
            r0 = move-exception
            r5 = r1
            goto L_0x0044
        L_0x061b:
            if (r13 == 0) goto L_0x061f
            r13.object = r1
        L_0x061f:
            r8.setContext(r15)
            return r1
        L_0x0623:
            r0 = move-exception
            r1 = r0
            r5 = r12
            goto L_0x0734
        L_0x0628:
            com.alibaba.fastjson.parser.JavaBeanInfo r2 = r7.beanInfo     // Catch:{ all -> 0x0623 }
            java.lang.String[] r2 = r2.creatorConstructorParameters     // Catch:{ all -> 0x0623 }
            if (r2 == 0) goto L_0x0630
            int r3 = r2.length     // Catch:{ all -> 0x0623 }
            goto L_0x0633
        L_0x0630:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r3 = r7.fieldDeserializers     // Catch:{ all -> 0x0623 }
            int r3 = r3.length     // Catch:{ all -> 0x0623 }
        L_0x0633:
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x0623 }
            r5 = 0
        L_0x0636:
            if (r5 >= r3) goto L_0x065a
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r6 = r7.fieldDeserializers     // Catch:{ all -> 0x0623 }
            r6 = r6[r5]     // Catch:{ all -> 0x0623 }
            com.alibaba.fastjson.util.FieldInfo r6 = r6.fieldInfo     // Catch:{ all -> 0x0623 }
            if (r2 == 0) goto L_0x0647
            java.lang.String r9 = r6.name     // Catch:{ all -> 0x0623 }
            java.lang.Object r9 = r1.remove(r9)     // Catch:{ all -> 0x0623 }
            goto L_0x064d
        L_0x0647:
            java.lang.String r9 = r6.name     // Catch:{ all -> 0x0623 }
            java.lang.Object r9 = r1.get(r9)     // Catch:{ all -> 0x0623 }
        L_0x064d:
            if (r9 != 0) goto L_0x0655
            java.lang.Class<?> r6 = r6.fieldClass     // Catch:{ all -> 0x0623 }
            java.lang.Object r9 = com.alibaba.fastjson.util.TypeUtils.defaultValue(r6)     // Catch:{ all -> 0x0623 }
        L_0x0655:
            r4[r5] = r9     // Catch:{ all -> 0x0623 }
            int r5 = r5 + 1
            goto L_0x0636
        L_0x065a:
            com.alibaba.fastjson.parser.JavaBeanInfo r3 = r7.beanInfo     // Catch:{ all -> 0x0623 }
            java.lang.reflect.Constructor<?> r3 = r3.creatorConstructor     // Catch:{ all -> 0x0623 }
            if (r3 == 0) goto L_0x06b7
            com.alibaba.fastjson.parser.JavaBeanInfo r3 = r7.beanInfo     // Catch:{ Exception -> 0x0699 }
            java.lang.reflect.Constructor<?> r3 = r3.creatorConstructor     // Catch:{ Exception -> 0x0699 }
            java.lang.Object r3 = r3.newInstance(r4)     // Catch:{ Exception -> 0x0699 }
            if (r2 == 0) goto L_0x0697
            java.util.Set r1 = r1.entrySet()     // Catch:{ all -> 0x0692 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0692 }
        L_0x0672:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0692 }
            if (r2 == 0) goto L_0x0697
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0692 }
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch:{ all -> 0x0692 }
            java.lang.Object r4 = r2.getKey()     // Catch:{ all -> 0x0692 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x0692 }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r4 = r7.getFieldDeserializer(r4)     // Catch:{ all -> 0x0692 }
            if (r4 == 0) goto L_0x0672
            java.lang.Object r2 = r2.getValue()     // Catch:{ all -> 0x0692 }
            r4.setValue(r3, r2)     // Catch:{ all -> 0x0692 }
            goto L_0x0672
        L_0x0692:
            r0 = move-exception
            r1 = r0
            r5 = r3
            goto L_0x0734
        L_0x0697:
            r12 = r3
            goto L_0x06e6
        L_0x0699:
            r0 = move-exception
            r1 = r0
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0623 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0623 }
            java.lang.String r4 = "create instance error, "
            r3.<init>(r4)     // Catch:{ all -> 0x0623 }
            com.alibaba.fastjson.parser.JavaBeanInfo r4 = r7.beanInfo     // Catch:{ all -> 0x0623 }
            java.lang.reflect.Constructor<?> r4 = r4.creatorConstructor     // Catch:{ all -> 0x0623 }
            java.lang.String r4 = r4.toGenericString()     // Catch:{ all -> 0x0623 }
            r3.append(r4)     // Catch:{ all -> 0x0623 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0623 }
            r2.<init>(r3, r1)     // Catch:{ all -> 0x0623 }
            throw r2     // Catch:{ all -> 0x0623 }
        L_0x06b7:
            com.alibaba.fastjson.parser.JavaBeanInfo r1 = r7.beanInfo     // Catch:{ all -> 0x0623 }
            java.lang.reflect.Method r1 = r1.factoryMethod     // Catch:{ all -> 0x0623 }
            if (r1 == 0) goto L_0x06e6
            com.alibaba.fastjson.parser.JavaBeanInfo r1 = r7.beanInfo     // Catch:{ Exception -> 0x06c8 }
            java.lang.reflect.Method r1 = r1.factoryMethod     // Catch:{ Exception -> 0x06c8 }
            r5 = 0
            java.lang.Object r1 = r1.invoke(r5, r4)     // Catch:{ Exception -> 0x06c8 }
            r12 = r1
            goto L_0x06e6
        L_0x06c8:
            r0 = move-exception
            r1 = r0
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0623 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0623 }
            java.lang.String r4 = "create factory method error, "
            r3.<init>(r4)     // Catch:{ all -> 0x0623 }
            com.alibaba.fastjson.parser.JavaBeanInfo r4 = r7.beanInfo     // Catch:{ all -> 0x0623 }
            java.lang.reflect.Method r4 = r4.factoryMethod     // Catch:{ all -> 0x0623 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0623 }
            r3.append(r4)     // Catch:{ all -> 0x0623 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0623 }
            r2.<init>(r3, r1)     // Catch:{ all -> 0x0623 }
            throw r2     // Catch:{ all -> 0x0623 }
        L_0x06e6:
            if (r13 == 0) goto L_0x06ea
            r13.object = r12
        L_0x06ea:
            r8.setContext(r15)
            return r12
        L_0x06ee:
            r3 = r43
            r5 = 0
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            r4 = 18
            if (r1 == r4) goto L_0x06fb
            int r1 = r11.token     // Catch:{ all -> 0x072a }
            if (r1 != r13) goto L_0x0716
        L_0x06fb:
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x072a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x072a }
            java.lang.String r3 = "syntax error, unexpect token "
            r2.<init>(r3)     // Catch:{ all -> 0x072a }
            int r3 = r11.token     // Catch:{ all -> 0x072a }
            java.lang.String r3 = com.alibaba.fastjson.parser.JSONToken.name(r3)     // Catch:{ all -> 0x072a }
            r2.append(r3)     // Catch:{ all -> 0x072a }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x072a }
            r1.<init>(r2)     // Catch:{ all -> 0x072a }
            throw r1     // Catch:{ all -> 0x072a }
        L_0x0716:
            r2 = r9
            r5 = r12
            r1 = r22
            r13 = r23
            r4 = 4
            r6 = 13
            r12 = 16
            r9 = r42
            r39 = r10
            r10 = r3
            r3 = r39
            goto L_0x00cd
        L_0x072a:
            r0 = move-exception
            r1 = r0
            r5 = r12
            goto L_0x00e3
        L_0x072f:
            r0 = move-exception
            r5 = r13
            r1 = r0
            r5 = r44
        L_0x0734:
            if (r13 == 0) goto L_0x0738
            r13.object = r5
        L_0x0738:
            r8.setContext(r15)
            throw r1
        L_0x073c:
            java.lang.Object r1 = r41.parse()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public FieldDeserializer getFieldDeserializerByHash(long j) {
        for (FieldDeserializer fieldDeserializer : this.sortedFieldDeserializers) {
            if (fieldDeserializer.fieldInfo.nameHashCode == j) {
                return fieldDeserializer;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public FieldDeserializer getFieldDeserializer(String str) {
        if (str == null) {
            return null;
        }
        int i = 0;
        if (this.beanInfo.ordered) {
            while (i < this.sortedFieldDeserializers.length) {
                FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
                if (fieldDeserializer.fieldInfo.name.equalsIgnoreCase(str)) {
                    return fieldDeserializer;
                }
                i++;
            }
            return null;
        }
        int length = this.sortedFieldDeserializers.length - 1;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            int compareTo = this.sortedFieldDeserializers[i2].fieldInfo.name.compareTo(str);
            if (compareTo < 0) {
                i = i2 + 1;
            } else if (compareTo <= 0) {
                return this.sortedFieldDeserializers[i2];
            } else {
                length = i2 - 1;
            }
        }
        if (this.alterNameFieldDeserializers != null) {
            return this.alterNameFieldDeserializers.get(str);
        }
        return null;
    }

    private boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        Field[] declaredFields;
        boolean z;
        DefaultJSONParser defaultJSONParser2 = defaultJSONParser;
        String str2 = str;
        Object obj2 = obj;
        JSONLexer jSONLexer = defaultJSONParser2.lexer;
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str2);
        if (fieldDeserializer == null) {
            long fnv_64_lower = TypeUtils.fnv_64_lower(str);
            if (this.smartMatchHashArray == null) {
                long[] jArr = new long[this.sortedFieldDeserializers.length];
                for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                    jArr[i] = TypeUtils.fnv_64_lower(this.sortedFieldDeserializers[i].fieldInfo.name);
                }
                Arrays.sort(jArr);
                this.smartMatchHashArray = jArr;
            }
            int binarySearch = Arrays.binarySearch(this.smartMatchHashArray, fnv_64_lower);
            if (binarySearch < 0) {
                z = str2.startsWith("is");
                if (z) {
                    binarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv_64_lower(str2.substring(2)));
                }
            } else {
                z = false;
            }
            if (binarySearch >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    int[] iArr = new int[this.smartMatchHashArray.length];
                    Arrays.fill(iArr, -1);
                    for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                        int binarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv_64_lower(this.sortedFieldDeserializers[i2].fieldInfo.name));
                        if (binarySearch2 >= 0) {
                            iArr[binarySearch2] = i2;
                        }
                    }
                    this.smartMatchHashArrayMapping = iArr;
                }
                int i3 = this.smartMatchHashArrayMapping[binarySearch];
                if (i3 != -1) {
                    fieldDeserializer = this.sortedFieldDeserializers[i3];
                    Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
                    if (!(!z || cls == Boolean.TYPE || cls == Boolean.class)) {
                        fieldDeserializer = null;
                    }
                }
            }
        }
        int i4 = Feature.SupportNonPublicField.mask;
        if (fieldDeserializer == null && !((defaultJSONParser2.lexer.features & i4) == 0 && (i4 & this.beanInfo.parserFeatures) == 0)) {
            if (this.extraFieldDeserializers == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1, 0.75f, 1);
                Class cls2 = this.clazz;
                while (cls2 != null && cls2 != Object.class) {
                    for (Field field : cls2.getDeclaredFields()) {
                        String name = field.getName();
                        if (getFieldDeserializer(name) == null) {
                            int modifiers = field.getModifiers();
                            if ((modifiers & 16) == 0 && (modifiers & 8) == 0) {
                                concurrentHashMap.put(name, field);
                            }
                        }
                    }
                    cls2 = cls2.getSuperclass();
                }
                this.extraFieldDeserializers = concurrentHashMap;
            }
            Object obj3 = this.extraFieldDeserializers.get(str2);
            if (obj3 != null) {
                if (obj3 instanceof FieldDeserializer) {
                    fieldDeserializer = (FieldDeserializer) obj3;
                } else {
                    Field field2 = (Field) obj3;
                    field2.setAccessible(true);
                    FieldInfo fieldInfo = new FieldInfo(str2, field2.getDeclaringClass(), field2.getType(), field2.getGenericType(), field2, 0, 0);
                    fieldDeserializer = new DefaultFieldDeserializer(defaultJSONParser2.config, this.clazz, fieldInfo);
                    this.extraFieldDeserializers.put(str2, fieldDeserializer);
                }
            }
        }
        if (fieldDeserializer == null) {
            parseExtra(defaultJSONParser2, obj2, str2);
            return false;
        }
        jSONLexer.nextTokenWithChar(':');
        fieldDeserializer.parseField(defaultJSONParser2, obj2, type, map);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void parseExtra(DefaultJSONParser defaultJSONParser, Object obj, String str) {
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if ((defaultJSONParser.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
            StringBuilder sb = new StringBuilder("setter not found, class ");
            sb.append(this.clazz.getName());
            sb.append(", property ");
            sb.append(str);
            throw new JSONException(sb.toString());
        }
        jSONLexer.nextTokenWithChar(':');
        Type type = null;
        List<ExtraTypeProvider> list = defaultJSONParser.extraTypeProviders;
        if (list != null) {
            for (ExtraTypeProvider extraType : list) {
                type = extraType.getExtraType(obj, str);
            }
        }
        if (type == null) {
            obj2 = defaultJSONParser.parse();
        } else {
            obj2 = defaultJSONParser.parseObject(type);
        }
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, obj2);
            return;
        }
        List<ExtraProcessor> list2 = defaultJSONParser.extraProcessors;
        if (list2 != null) {
            for (ExtraProcessor processExtra : list2) {
                processExtra.processExtra(obj, str, obj2);
            }
        }
    }

    public Object createInstance(Map<String, Object> map, ParserConfig parserConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj;
        double d;
        float f;
        Object obj2 = null;
        if (this.beanInfo.creatorConstructor == null) {
            Object createInstance = createInstance((DefaultJSONParser) null, (Type) this.clazz);
            for (Entry next : map.entrySet()) {
                FieldDeserializer fieldDeserializer = getFieldDeserializer((String) next.getKey());
                if (fieldDeserializer != null) {
                    Object value = next.getValue();
                    Method method = fieldDeserializer.fieldInfo.method;
                    if (method != null) {
                        method.invoke(createInstance, new Object[]{TypeUtils.cast(value, method.getGenericParameterTypes()[0], parserConfig)});
                    } else {
                        Field field = fieldDeserializer.fieldInfo.field;
                        Type type = fieldDeserializer.fieldInfo.fieldType;
                        if (type == Boolean.TYPE) {
                            if (value == Boolean.FALSE) {
                                field.setBoolean(createInstance, false);
                            } else if (value == Boolean.TRUE) {
                                field.setBoolean(createInstance, true);
                            }
                        } else if (type == Integer.TYPE) {
                            if (value instanceof Number) {
                                field.setInt(createInstance, ((Number) value).intValue());
                            }
                        } else if (type == Long.TYPE) {
                            if (value instanceof Number) {
                                field.setLong(createInstance, ((Number) value).longValue());
                            }
                        } else if (type == Float.TYPE) {
                            if (value instanceof Number) {
                                field.setFloat(createInstance, ((Number) value).floatValue());
                            } else if (value instanceof String) {
                                String str = (String) value;
                                if (str.length() <= 10) {
                                    f = TypeUtils.parseFloat(str);
                                } else {
                                    f = Float.parseFloat(str);
                                }
                                field.setFloat(createInstance, f);
                            }
                        } else if (type == Double.TYPE) {
                            if (value instanceof Number) {
                                field.setDouble(createInstance, ((Number) value).doubleValue());
                            } else if (value instanceof String) {
                                String str2 = (String) value;
                                if (str2.length() <= 10) {
                                    d = TypeUtils.parseDouble(str2);
                                } else {
                                    d = Double.parseDouble(str2);
                                }
                                field.setDouble(createInstance, d);
                            }
                        } else if (value != null && type == value.getClass()) {
                            field.set(createInstance, value);
                        }
                        String str3 = fieldDeserializer.fieldInfo.format;
                        if (str3 == null || type != Date.class || !(value instanceof String)) {
                            obj = type instanceof ParameterizedType ? TypeUtils.cast(value, (ParameterizedType) type, parserConfig) : TypeUtils.cast(value, type, parserConfig);
                        } else {
                            try {
                                obj = new SimpleDateFormat(str3).parse((String) value);
                            } catch (ParseException unused) {
                                obj = null;
                            }
                        }
                        field.set(createInstance, obj);
                    }
                }
            }
            return createInstance;
        }
        FieldInfo[] fieldInfoArr = this.beanInfo.fields;
        int length = fieldInfoArr.length;
        Object[] objArr = new Object[length];
        for (int i = 0; i < length; i++) {
            FieldInfo fieldInfo = fieldInfoArr[i];
            Object obj3 = map.get(fieldInfo.name);
            if (obj3 == null) {
                obj3 = TypeUtils.defaultValue(fieldInfo.fieldClass);
            }
            objArr[i] = obj3;
        }
        if (this.beanInfo.creatorConstructor != null) {
            try {
                obj2 = this.beanInfo.creatorConstructor.newInstance(objArr);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("create instance error, ");
                sb.append(this.beanInfo.creatorConstructor.toGenericString());
                throw new JSONException(sb.toString(), e);
            }
        }
        return obj2;
    }

    /* access modifiers changed from: protected */
    public JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        if (javaBeanInfo.jsonType == null) {
            return null;
        }
        for (Class deserializer : javaBeanInfo.jsonType.seeAlso()) {
            ObjectDeserializer deserializer2 = parserConfig.getDeserializer(deserializer);
            if (deserializer2 instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer2;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }
}
