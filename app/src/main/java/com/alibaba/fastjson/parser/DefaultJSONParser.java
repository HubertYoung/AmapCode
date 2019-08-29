package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    public ParserConfig config;
    protected ParseContext contex;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    protected List<ExtraProcessor> extraProcessors;
    protected List<ExtraTypeProvider> extraTypeProviders;
    public FieldTypeResolver fieldTypeResolver;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public static class ResolveTask {
        /* access modifiers changed from: private */
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        /* access modifiers changed from: private */
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.locale);
            this.dateFormat.setTimeZone(this.lexer.timeZone);
        }
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat2) {
        this.dateFormat = dateFormat2;
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(new JSONLexer(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i) {
        this(new JSONLexer(str, i), parserConfig);
    }

    public DefaultJSONParser(char[] cArr, int i, ParserConfig parserConfig, int i2) {
        this(new JSONLexer(cArr, i, i2), parserConfig);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.global);
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.lexer = jSONLexer;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char c = jSONLexer.ch;
        char c2 = JSONLexer.EOI;
        if (c == '{') {
            int i = jSONLexer.bp + 1;
            jSONLexer.bp = i;
            jSONLexer.ch = i < jSONLexer.len ? jSONLexer.text.charAt(i) : c2;
            jSONLexer.token = 12;
        } else if (jSONLexer.ch == '[') {
            int i2 = jSONLexer.bp + 1;
            jSONLexer.bp = i2;
            jSONLexer.ch = i2 < jSONLexer.len ? jSONLexer.text.charAt(i2) : c2;
            jSONLexer.token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    /* JADX WARNING: type inference failed for: r6v39, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r6v40, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v41 */
    /* JADX WARNING: type inference failed for: r6v43, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r6v46 */
    /* JADX WARNING: type inference failed for: r6v47 */
    /* JADX INFO: used method not loaded: com.alibaba.fastjson.parser.deserializer.FieldDeserializer.setValue(java.lang.Object, java.lang.Object):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0250, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0270, code lost:
        r4.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0277, code lost:
        if (r4.token != 13) goto L_0x02e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0279, code lost:
        r4.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:?, code lost:
        r3 = r1.config.getDeserializer(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0284, code lost:
        if ((r3 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer) == false) goto L_0x02b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0286, code lost:
        r3 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r3;
        r4 = r3.createInstance(r1, (java.lang.reflect.Type) r7);
        r2 = r19.entrySet().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0298, code lost:
        if (r2.hasNext() == false) goto L_0x02b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x029a, code lost:
        r5 = (java.util.Map.Entry) r2.next();
        r8 = r5.getKey();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02a6, code lost:
        if ((r8 instanceof java.lang.String) == false) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02a8, code lost:
        r8 = r3.getFieldDeserializer((java.lang.String) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02ae, code lost:
        if (r8 == null) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02b0, code lost:
        r8.setValue(r4, r5.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02b8, code lost:
        r4 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02b9, code lost:
        if (r4 != null) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02bd, code lost:
        if (r7 != java.lang.Cloneable.class) goto L_0x02c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02bf, code lost:
        r4 = new java.util.HashMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02cb, code lost:
        if ("java.util.Collections$EmptyMap".equals(r6) == false) goto L_0x02d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x02cd, code lost:
        r4 = java.util.Collections.emptyMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02d2, code lost:
        r4 = r7.newInstance();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x02d6, code lost:
        if (r14 != false) goto L_0x02da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x02d8, code lost:
        r1.contex = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02da, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x02e5, code lost:
        r1.resolveStatus = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x02ea, code lost:
        if (r1.contex == null) goto L_0x02f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x02ee, code lost:
        if ((r3 instanceof java.lang.Integer) != false) goto L_0x02f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x02f0, code lost:
        popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x02f7, code lost:
        if (r19.size() <= 0) goto L_0x0307;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x02f9, code lost:
        r2 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r2, r7, r1.config);
        parseObject(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0302, code lost:
        if (r14 != false) goto L_0x0306;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0304, code lost:
        r1.contex = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x0306, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:?, code lost:
        r2 = r1.config.getDeserializer(r7);
        r3 = r2.deserialze(r1, r7, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x0313, code lost:
        if ((r2 instanceof com.alibaba.fastjson.parser.MapDeserializer) == false) goto L_0x0318;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x0315, code lost:
        r1.resolveStatus = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x0318, code lost:
        if (r14 != false) goto L_0x031c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x031a, code lost:
        r1.contex = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x031c, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x032c, code lost:
        r4.nextToken(4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0331, code lost:
        if (r4.token != 4) goto L_0x03c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0333, code lost:
        r2 = r4.stringVal();
        r4.nextToken(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x0340, code lost:
        if (com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool.PREFIX_ID.equals(r2) == false) goto L_0x0358;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0342, code lost:
        r2 = r1.contex;
        r7 = r2.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x0348, code lost:
        if ((r7 instanceof java.lang.Object[]) != false) goto L_0x039f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x034c, code lost:
        if ((r7 instanceof java.util.Collection) == false) goto L_0x034f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x0351, code lost:
        if (r2.parent == null) goto L_0x039e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x0353, code lost:
        r7 = r2.parent.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x035e, code lost:
        if ("..".equals(r2) == false) goto L_0x0372;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x0362, code lost:
        if (r15.object == null) goto L_0x0367;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x0364, code lost:
        r7 = r15.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0367, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r15, r2));
        r1.resolveStatus = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x0378, code lost:
        if ("$".equals(r2) == false) goto L_0x0394;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x037a, code lost:
        r3 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x037d, code lost:
        if (r3.parent == null) goto L_0x0382;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x037f, code lost:
        r3 = r3.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x0384, code lost:
        if (r3.object == null) goto L_0x0389;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x0386, code lost:
        r7 = r3.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x0389, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r3, r2));
        r1.resolveStatus = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x0394, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r15, r2));
        r1.resolveStatus = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x039e, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x03a1, code lost:
        if (r4.token == 13) goto L_0x03bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x03a3, code lost:
        r3 = new java.lang.StringBuilder("syntax error, ");
        r3.append(r4.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x03bb, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x03bc, code lost:
        r4.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x03c1, code lost:
        if (r14 != false) goto L_0x03c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x03c3, code lost:
        r1.contex = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x03c5, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:?, code lost:
        r3 = new java.lang.StringBuilder("illegal ref, ");
        r3.append(com.alibaba.fastjson.parser.JSONToken.name(r4.token));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x03df, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:365:0x05c8, code lost:
        if (r6 != '}') goto L_0x064b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:366:0x05ca, code lost:
        r5 = r4.bp + 1;
        r4.bp = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:367:0x05d2, code lost:
        if (r5 < r4.len) goto L_0x05d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:0x05d4, code lost:
        r10 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:369:0x05d7, code lost:
        r10 = r4.text.charAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:0x05dd, code lost:
        r4.ch = r10;
        r4.sp = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:371:0x05e4, code lost:
        if (r10 != ',') goto L_0x0600;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:372:0x05e6, code lost:
        r5 = r4.bp + 1;
        r4.bp = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:373:0x05ee, code lost:
        if (r5 < r4.len) goto L_0x05f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:374:0x05f0, code lost:
        r10 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:375:0x05f3, code lost:
        r10 = r4.text.charAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:376:0x05f9, code lost:
        r4.ch = r10;
        r4.token = 16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:378:0x0602, code lost:
        if (r10 != '}') goto L_0x061e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:0x0604, code lost:
        r5 = r4.bp + 1;
        r4.bp = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:0x060c, code lost:
        if (r5 < r4.len) goto L_0x0611;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:0x060e, code lost:
        r10 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:382:0x0611, code lost:
        r10 = r4.text.charAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:383:0x0617, code lost:
        r4.ch = r10;
        r4.token = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:385:0x0620, code lost:
        if (r10 != ']') goto L_0x063c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:0x0622, code lost:
        r5 = r4.bp + 1;
        r4.bp = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:387:0x062a, code lost:
        if (r5 < r4.len) goto L_0x062f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:0x062c, code lost:
        r10 = com.alibaba.fastjson.parser.JSONLexer.EOI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:0x062f, code lost:
        r10 = r4.text.charAt(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:0x0635, code lost:
        r4.ch = r10;
        r4.token = 15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:0x063c, code lost:
        r4.nextToken();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:392:0x063f, code lost:
        if (r14 != false) goto L_0x0646;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:0x0641, code lost:
        setContext(r1.contex, r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:394:0x0646, code lost:
        if (r14 != false) goto L_0x064a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:395:0x0648, code lost:
        r1.contex = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:396:0x064a, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:398:?, code lost:
        r3 = new java.lang.StringBuilder("syntax error, ");
        r3.append(r4.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:399:0x0663, code lost:
        throw new com.alibaba.fastjson.JSONException(r3.toString());
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v39, types: [java.lang.String]
      assigns: [java.lang.String, java.util.Date]
      uses: [java.lang.String, java.lang.Object]
      mth insns count: 636
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
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01e4 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x021e A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x03f6 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x041c A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x0506 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x0515 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x051e A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x0522 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x0527 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:336:0x053e  */
    /* JADX WARNING: Removed duplicated region for block: B:358:0x05ab A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:431:0x032c A[EDGE_INSN: B:431:0x032c->B:198:0x032c ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:432:0x0530 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:433:0x016b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:434:0x05c6 A[EDGE_INSN: B:434:0x05c6->B:364:0x05c6 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0158 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x015d A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0163 A[Catch:{ Exception -> 0x02db, NumberFormatException -> 0x0183, all -> 0x06b4 }] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object parseObject(java.util.Map r19, java.lang.Object r20) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer
            int r5 = r4.token
            r6 = 8
            r7 = 0
            if (r5 != r6) goto L_0x0013
            r4.nextToken()
            return r7
        L_0x0013:
            r8 = 12
            r9 = 16
            if (r5 == r8) goto L_0x0040
            if (r5 == r9) goto L_0x0040
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "syntax error, expect {, actual "
            r3.<init>(r6)
            java.lang.String r5 = com.alibaba.fastjson.parser.JSONToken.name(r5)
            r3.append(r5)
            java.lang.String r5 = ", "
            r3.append(r5)
            java.lang.String r4 = r4.info()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0040:
            boolean r5 = r2 instanceof com.alibaba.fastjson.JSONObject
            r11 = 1
            if (r5 == 0) goto L_0x004f
            r5 = r2
            com.alibaba.fastjson.JSONObject r5 = (com.alibaba.fastjson.JSONObject) r5
            java.util.Map r5 = r5.getInnerMap()
            r12 = r5
            r5 = 1
            goto L_0x0051
        L_0x004f:
            r12 = r2
            r5 = 0
        L_0x0051:
            int r13 = r4.features
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat
            int r14 = r14.mask
            r13 = r13 & r14
            if (r13 == 0) goto L_0x005c
            r13 = 1
            goto L_0x005d
        L_0x005c:
            r13 = 0
        L_0x005d:
            boolean r14 = r4.disableCircularReferenceDetect
            com.alibaba.fastjson.parser.ParseContext r15 = r1.contex
            r16 = 0
        L_0x0063:
            char r7 = r4.ch     // Catch:{ all -> 0x06b4 }
            r6 = 125(0x7d, float:1.75E-43)
            r8 = 34
            if (r7 == r8) goto L_0x0072
            if (r7 == r6) goto L_0x0072
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
            char r7 = r4.ch     // Catch:{ all -> 0x06b4 }
        L_0x0072:
            r9 = 44
            if (r7 != r9) goto L_0x007f
            r4.next()     // Catch:{ all -> 0x06b4 }
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
            char r7 = r4.ch     // Catch:{ all -> 0x06b4 }
            goto L_0x0072
        L_0x007f:
            r9 = 58
            r10 = 26
            if (r7 != r8) goto L_0x00b8
            com.alibaba.fastjson.parser.SymbolTable r7 = r1.symbolTable     // Catch:{ all -> 0x06b4 }
            java.lang.String r7 = r4.scanSymbol(r7, r8)     // Catch:{ all -> 0x06b4 }
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x00b4
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x00b4
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "expect ':' at "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            int r4 = r4.pos     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = ", name "
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            r3.append(r7)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x00b4:
            r8 = r7
            r7 = 0
            goto L_0x01e0
        L_0x00b8:
            if (r7 != r6) goto L_0x00d9
            int r3 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r3 = r3 + r11
            r4.bp = r3     // Catch:{ all -> 0x06b4 }
            int r5 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r3 < r5) goto L_0x00c4
            goto L_0x00ca
        L_0x00c4:
            java.lang.String r5 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r5.charAt(r3)     // Catch:{ all -> 0x06b4 }
        L_0x00ca:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r3 = 0
            r4.sp = r3     // Catch:{ all -> 0x06b4 }
            r3 = 16
            r4.nextToken(r3)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x00d8
            r1.contex = r15
        L_0x00d8:
            return r2
        L_0x00d9:
            r8 = 39
            if (r7 != r8) goto L_0x0106
            com.alibaba.fastjson.parser.SymbolTable r7 = r1.symbolTable     // Catch:{ all -> 0x06b4 }
            r8 = 39
            java.lang.String r7 = r4.scanSymbol(r7, r8)     // Catch:{ all -> 0x06b4 }
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x00ec
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
        L_0x00ec:
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x00b4
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "expect ':' at "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            int r4 = r4.pos     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x0106:
            if (r7 != r10) goto L_0x0121
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x0121:
            r8 = 44
            if (r7 != r8) goto L_0x013e
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x013e:
            r8 = 48
            if (r7 < r8) goto L_0x0149
            r8 = 57
            if (r7 <= r8) goto L_0x0147
            goto L_0x0149
        L_0x0147:
            r7 = 0
            goto L_0x014e
        L_0x0149:
            r8 = 45
            if (r7 != r8) goto L_0x019b
            goto L_0x0147
        L_0x014e:
            r4.sp = r7     // Catch:{ all -> 0x06b4 }
            r4.scanNumber()     // Catch:{ all -> 0x06b4 }
            int r7 = r4.token     // Catch:{ NumberFormatException -> 0x0183 }
            r8 = 2
            if (r7 != r8) goto L_0x015d
            java.lang.Number r7 = r4.integerValue()     // Catch:{ NumberFormatException -> 0x0183 }
            goto L_0x0161
        L_0x015d:
            java.lang.Number r7 = r4.decimalValue(r11)     // Catch:{ NumberFormatException -> 0x0183 }
        L_0x0161:
            if (r5 == 0) goto L_0x0167
            java.lang.String r7 = r7.toString()     // Catch:{ NumberFormatException -> 0x0183 }
        L_0x0167:
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x00b4
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "parse number key error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x0183:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "parse number key error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x019b:
            r8 = 123(0x7b, float:1.72E-43)
            if (r7 == r8) goto L_0x01d7
            r8 = 91
            if (r7 != r8) goto L_0x01a4
            goto L_0x01d7
        L_0x01a4:
            com.alibaba.fastjson.parser.SymbolTable r7 = r1.symbolTable     // Catch:{ all -> 0x06b4 }
            java.lang.String r7 = r4.scanSymbolUnQuoted(r7)     // Catch:{ all -> 0x06b4 }
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
            char r8 = r4.ch     // Catch:{ all -> 0x06b4 }
            if (r8 == r9) goto L_0x01cf
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "expect ':' at "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            int r4 = r4.bp     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = ", actual "
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            r3.append(r8)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x01cf:
            if (r5 == 0) goto L_0x00b4
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x06b4 }
            goto L_0x00b4
        L_0x01d7:
            r4.nextToken()     // Catch:{ all -> 0x06b4 }
            java.lang.Object r7 = r18.parse()     // Catch:{ all -> 0x06b4 }
            r8 = r7
            r7 = 1
        L_0x01e0:
            r9 = 13
            if (r7 != 0) goto L_0x021e
            int r7 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r7 = r7 + r11
            r4.bp = r7     // Catch:{ all -> 0x06b4 }
            int r10 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r7 < r10) goto L_0x01f0
            r10 = 26
            goto L_0x01f6
        L_0x01f0:
            java.lang.String r10 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r10.charAt(r7)     // Catch:{ all -> 0x06b4 }
        L_0x01f6:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
        L_0x01f8:
            r7 = 32
            if (r10 > r7) goto L_0x021b
            r7 = 32
            if (r10 == r7) goto L_0x0213
            r7 = 10
            if (r10 == r7) goto L_0x0213
            if (r10 == r9) goto L_0x0213
            r7 = 9
            if (r10 == r7) goto L_0x0213
            r7 = 12
            if (r10 == r7) goto L_0x0213
            r7 = 8
            if (r10 != r7) goto L_0x0222
            goto L_0x0215
        L_0x0213:
            r7 = 8
        L_0x0215:
            r4.next()     // Catch:{ all -> 0x06b4 }
            char r10 = r4.ch     // Catch:{ all -> 0x06b4 }
            goto L_0x01f8
        L_0x021b:
            r7 = 8
            goto L_0x0222
        L_0x021e:
            r7 = 8
            char r10 = r4.ch     // Catch:{ all -> 0x06b4 }
        L_0x0222:
            r7 = 0
            r4.sp = r7     // Catch:{ all -> 0x06b4 }
            java.lang.String r7 = "@type"
            if (r8 != r7) goto L_0x031d
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x06b4 }
            boolean r7 = r4.isEnabled(r7)     // Catch:{ all -> 0x06b4 }
            if (r7 != 0) goto L_0x031d
            com.alibaba.fastjson.parser.SymbolTable r6 = r1.symbolTable     // Catch:{ all -> 0x06b4 }
            r7 = 34
            java.lang.String r6 = r4.scanSymbol(r6, r7)     // Catch:{ all -> 0x06b4 }
            r7 = 0
        L_0x023a:
            int r8 = r6.length()     // Catch:{ all -> 0x06b4 }
            if (r7 >= r8) goto L_0x0252
            char r8 = r6.charAt(r7)     // Catch:{ all -> 0x06b4 }
            r10 = 48
            if (r8 < r10) goto L_0x0250
            r10 = 57
            if (r8 <= r10) goto L_0x024d
            goto L_0x0250
        L_0x024d:
            int r7 = r7 + 1
            goto L_0x023a
        L_0x0250:
            r7 = 0
            goto L_0x0253
        L_0x0252:
            r7 = 1
        L_0x0253:
            if (r7 != 0) goto L_0x025f
            com.alibaba.fastjson.parser.ParserConfig r7 = r1.config     // Catch:{ all -> 0x06b4 }
            int r8 = r4.features     // Catch:{ all -> 0x06b4 }
            r10 = 0
            java.lang.Class r7 = r7.checkAutoType(r6, r10, r8)     // Catch:{ all -> 0x06b4 }
            goto L_0x0261
        L_0x025f:
            r10 = 0
            r7 = r10
        L_0x0261:
            if (r7 != 0) goto L_0x0270
            java.lang.String r7 = "@type"
            r2.put(r7, r6)     // Catch:{ all -> 0x06b4 }
            r6 = 8
            r8 = 12
            r9 = 16
            goto L_0x0063
        L_0x0270:
            r5 = 16
            r4.nextToken(r5)     // Catch:{ all -> 0x06b4 }
            int r8 = r4.token     // Catch:{ all -> 0x06b4 }
            if (r8 != r9) goto L_0x02e5
            r4.nextToken(r5)     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.ParserConfig r3 = r1.config     // Catch:{ Exception -> 0x02db }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r3 = r3.getDeserializer(r7)     // Catch:{ Exception -> 0x02db }
            boolean r4 = r3 instanceof com.alibaba.fastjson.parser.JavaBeanDeserializer     // Catch:{ Exception -> 0x02db }
            if (r4 == 0) goto L_0x02b8
            com.alibaba.fastjson.parser.JavaBeanDeserializer r3 = (com.alibaba.fastjson.parser.JavaBeanDeserializer) r3     // Catch:{ Exception -> 0x02db }
            java.lang.Object r4 = r3.createInstance(r1, r7)     // Catch:{ Exception -> 0x02db }
            java.util.Set r2 = r19.entrySet()     // Catch:{ Exception -> 0x02db }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Exception -> 0x02db }
        L_0x0294:
            boolean r5 = r2.hasNext()     // Catch:{ Exception -> 0x02db }
            if (r5 == 0) goto L_0x02b9
            java.lang.Object r5 = r2.next()     // Catch:{ Exception -> 0x02db }
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ Exception -> 0x02db }
            java.lang.Object r8 = r5.getKey()     // Catch:{ Exception -> 0x02db }
            boolean r9 = r8 instanceof java.lang.String     // Catch:{ Exception -> 0x02db }
            if (r9 == 0) goto L_0x0294
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x02db }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r8 = r3.getFieldDeserializer(r8)     // Catch:{ Exception -> 0x02db }
            if (r8 == 0) goto L_0x0294
            java.lang.Object r5 = r5.getValue()     // Catch:{ Exception -> 0x02db }
            r8.setValue(r4, r5)     // Catch:{ Exception -> 0x02db }
            goto L_0x0294
        L_0x02b8:
            r4 = r10
        L_0x02b9:
            if (r4 != 0) goto L_0x02d6
            java.lang.Class<java.lang.Cloneable> r2 = java.lang.Cloneable.class
            if (r7 != r2) goto L_0x02c5
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ Exception -> 0x02db }
            r4.<init>()     // Catch:{ Exception -> 0x02db }
            goto L_0x02d6
        L_0x02c5:
            java.lang.String r2 = "java.util.Collections$EmptyMap"
            boolean r2 = r2.equals(r6)     // Catch:{ Exception -> 0x02db }
            if (r2 == 0) goto L_0x02d2
            java.util.Map r4 = java.util.Collections.emptyMap()     // Catch:{ Exception -> 0x02db }
            goto L_0x02d6
        L_0x02d2:
            java.lang.Object r4 = r7.newInstance()     // Catch:{ Exception -> 0x02db }
        L_0x02d6:
            if (r14 != 0) goto L_0x02da
            r1.contex = r15
        L_0x02da:
            return r4
        L_0x02db:
            r0 = move-exception
            r2 = r0
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = "create instance error"
            r3.<init>(r4, r2)     // Catch:{ all -> 0x06b4 }
            throw r3     // Catch:{ all -> 0x06b4 }
        L_0x02e5:
            r4 = 2
            r1.resolveStatus = r4     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.ParseContext r4 = r1.contex     // Catch:{ all -> 0x06b4 }
            if (r4 == 0) goto L_0x02f3
            boolean r4 = r3 instanceof java.lang.Integer     // Catch:{ all -> 0x06b4 }
            if (r4 != 0) goto L_0x02f3
            r18.popContext()     // Catch:{ all -> 0x06b4 }
        L_0x02f3:
            int r4 = r19.size()     // Catch:{ all -> 0x06b4 }
            if (r4 <= 0) goto L_0x0307
            com.alibaba.fastjson.parser.ParserConfig r3 = r1.config     // Catch:{ all -> 0x06b4 }
            java.lang.Object r2 = com.alibaba.fastjson.util.TypeUtils.cast(r2, r7, r3)     // Catch:{ all -> 0x06b4 }
            r1.parseObject(r2)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x0306
            r1.contex = r15
        L_0x0306:
            return r2
        L_0x0307:
            com.alibaba.fastjson.parser.ParserConfig r2 = r1.config     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r2 = r2.getDeserializer(r7)     // Catch:{ all -> 0x06b4 }
            java.lang.Object r3 = r2.deserialze(r1, r7, r3)     // Catch:{ all -> 0x06b4 }
            boolean r2 = r2 instanceof com.alibaba.fastjson.parser.MapDeserializer     // Catch:{ all -> 0x06b4 }
            if (r2 == 0) goto L_0x0318
            r2 = 0
            r1.resolveStatus = r2     // Catch:{ all -> 0x06b4 }
        L_0x0318:
            if (r14 != 0) goto L_0x031c
            r1.contex = r15
        L_0x031c:
            return r3
        L_0x031d:
            java.lang.String r7 = "$ref"
            r6 = 4
            if (r8 != r7) goto L_0x03e0
            if (r15 == 0) goto L_0x03e0
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x06b4 }
            boolean r7 = r4.isEnabled(r7)     // Catch:{ all -> 0x06b4 }
            if (r7 != 0) goto L_0x03e0
            r4.nextToken(r6)     // Catch:{ all -> 0x06b4 }
            int r2 = r4.token     // Catch:{ all -> 0x06b4 }
            if (r2 != r6) goto L_0x03c6
            java.lang.String r2 = r4.stringVal()     // Catch:{ all -> 0x06b4 }
            r4.nextToken(r9)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = "@"
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x0358
            com.alibaba.fastjson.parser.ParseContext r2 = r1.contex     // Catch:{ all -> 0x06b4 }
            java.lang.Object r7 = r2.object     // Catch:{ all -> 0x06b4 }
            boolean r3 = r7 instanceof java.lang.Object[]     // Catch:{ all -> 0x06b4 }
            if (r3 != 0) goto L_0x039f
            boolean r3 = r7 instanceof java.util.Collection     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x034f
            goto L_0x039f
        L_0x034f:
            com.alibaba.fastjson.parser.ParseContext r3 = r2.parent     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x039e
            com.alibaba.fastjson.parser.ParseContext r2 = r2.parent     // Catch:{ all -> 0x06b4 }
            java.lang.Object r7 = r2.object     // Catch:{ all -> 0x06b4 }
            goto L_0x039f
        L_0x0358:
            java.lang.String r3 = ".."
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x0372
            java.lang.Object r3 = r15.object     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x0367
            java.lang.Object r7 = r15.object     // Catch:{ all -> 0x06b4 }
            goto L_0x039f
        L_0x0367:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r3 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x06b4 }
            r3.<init>(r15, r2)     // Catch:{ all -> 0x06b4 }
            r1.addResolveTask(r3)     // Catch:{ all -> 0x06b4 }
            r1.resolveStatus = r11     // Catch:{ all -> 0x06b4 }
            goto L_0x039e
        L_0x0372:
            java.lang.String r3 = "$"
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x0394
            r3 = r15
        L_0x037b:
            com.alibaba.fastjson.parser.ParseContext r5 = r3.parent     // Catch:{ all -> 0x06b4 }
            if (r5 == 0) goto L_0x0382
            com.alibaba.fastjson.parser.ParseContext r3 = r3.parent     // Catch:{ all -> 0x06b4 }
            goto L_0x037b
        L_0x0382:
            java.lang.Object r5 = r3.object     // Catch:{ all -> 0x06b4 }
            if (r5 == 0) goto L_0x0389
            java.lang.Object r7 = r3.object     // Catch:{ all -> 0x06b4 }
            goto L_0x039f
        L_0x0389:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r5 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x06b4 }
            r5.<init>(r3, r2)     // Catch:{ all -> 0x06b4 }
            r1.addResolveTask(r5)     // Catch:{ all -> 0x06b4 }
            r1.resolveStatus = r11     // Catch:{ all -> 0x06b4 }
            goto L_0x039e
        L_0x0394:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r3 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x06b4 }
            r3.<init>(r15, r2)     // Catch:{ all -> 0x06b4 }
            r1.addResolveTask(r3)     // Catch:{ all -> 0x06b4 }
            r1.resolveStatus = r11     // Catch:{ all -> 0x06b4 }
        L_0x039e:
            r7 = 0
        L_0x039f:
            int r2 = r4.token     // Catch:{ all -> 0x06b4 }
            if (r2 == r9) goto L_0x03bc
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x03bc:
            r2 = 16
            r4.nextToken(r2)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x03c5
            r1.contex = r15
        L_0x03c5:
            return r7
        L_0x03c6:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "illegal ref, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            int r4 = r4.token     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = com.alibaba.fastjson.parser.JSONToken.name(r4)     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x03e0:
            if (r14 != 0) goto L_0x03f2
            if (r16 != 0) goto L_0x03f2
            com.alibaba.fastjson.parser.ParseContext r7 = r1.contex     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.ParseContext r7 = r1.setContext(r7, r2, r3)     // Catch:{ all -> 0x06b4 }
            if (r15 != 0) goto L_0x03ed
            r15 = r7
        L_0x03ed:
            r7 = 34
            r16 = 1
            goto L_0x03f4
        L_0x03f2:
            r7 = 34
        L_0x03f4:
            if (r10 != r7) goto L_0x041c
            java.lang.String r6 = r4.scanStringValue(r7)     // Catch:{ all -> 0x06b4 }
            if (r13 == 0) goto L_0x0410
            com.alibaba.fastjson.parser.JSONLexer r7 = new com.alibaba.fastjson.parser.JSONLexer     // Catch:{ all -> 0x06b4 }
            r7.<init>(r6)     // Catch:{ all -> 0x06b4 }
            boolean r10 = r7.scanISO8601DateIfMatch(r11)     // Catch:{ all -> 0x06b4 }
            if (r10 == 0) goto L_0x040d
            java.util.Calendar r6 = r7.calendar     // Catch:{ all -> 0x06b4 }
            java.util.Date r6 = r6.getTime()     // Catch:{ all -> 0x06b4 }
        L_0x040d:
            r7.close()     // Catch:{ all -> 0x06b4 }
        L_0x0410:
            if (r12 == 0) goto L_0x0417
            r12.put(r8, r6)     // Catch:{ all -> 0x06b4 }
            goto L_0x0598
        L_0x0417:
            r2.put(r8, r6)     // Catch:{ all -> 0x06b4 }
            goto L_0x0598
        L_0x041c:
            r7 = 48
            if (r10 < r7) goto L_0x0424
            r7 = 57
            if (r10 <= r7) goto L_0x0428
        L_0x0424:
            r7 = 45
            if (r10 != r7) goto L_0x0431
        L_0x0428:
            java.lang.Number r6 = r4.scanNumberValue()     // Catch:{ all -> 0x06b4 }
            r12.put(r8, r6)     // Catch:{ all -> 0x06b4 }
            goto L_0x0598
        L_0x0431:
            r7 = 91
            if (r10 != r7) goto L_0x04a0
            r6 = 14
            r4.token = r6     // Catch:{ all -> 0x06b4 }
            int r6 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r6 = r6 + r11
            r4.bp = r6     // Catch:{ all -> 0x06b4 }
            int r7 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r6 < r7) goto L_0x0445
            r10 = 26
            goto L_0x044b
        L_0x0445:
            java.lang.String r7 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r7.charAt(r6)     // Catch:{ all -> 0x06b4 }
        L_0x044b:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x06b4 }
            r6.<init>()     // Catch:{ all -> 0x06b4 }
            if (r3 == 0) goto L_0x045e
            java.lang.Class r7 = r20.getClass()     // Catch:{ all -> 0x06b4 }
            java.lang.Class<java.lang.Integer> r10 = java.lang.Integer.class
            if (r7 != r10) goto L_0x045e
            r7 = 1
            goto L_0x045f
        L_0x045e:
            r7 = 0
        L_0x045f:
            if (r7 != 0) goto L_0x0464
            r1.setContext(r15)     // Catch:{ all -> 0x06b4 }
        L_0x0464:
            r1.parseArray(r6, r8)     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.JSONArray r7 = new com.alibaba.fastjson.JSONArray     // Catch:{ all -> 0x06b4 }
            r7.<init>(r6)     // Catch:{ all -> 0x06b4 }
            if (r12 == 0) goto L_0x0472
            r12.put(r8, r7)     // Catch:{ all -> 0x06b4 }
            goto L_0x0475
        L_0x0472:
            r2.put(r8, r7)     // Catch:{ all -> 0x06b4 }
        L_0x0475:
            int r6 = r4.token     // Catch:{ all -> 0x06b4 }
            if (r6 != r9) goto L_0x0483
            r7 = 16
            r4.nextToken(r7)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x0482
            r1.contex = r15
        L_0x0482:
            return r2
        L_0x0483:
            r7 = 16
            if (r6 == r7) goto L_0x05c0
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x04a0:
            r7 = 123(0x7b, float:1.72E-43)
            if (r10 != r7) goto L_0x055b
            int r6 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r6 = r6 + r11
            r4.bp = r6     // Catch:{ all -> 0x06b4 }
            int r7 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r6 < r7) goto L_0x04b0
            r10 = 26
            goto L_0x04b6
        L_0x04b0:
            java.lang.String r7 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r7.charAt(r6)     // Catch:{ all -> 0x06b4 }
        L_0x04b6:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r7 = 12
            r4.token = r7     // Catch:{ all -> 0x06b4 }
            boolean r6 = r3 instanceof java.lang.Integer     // Catch:{ all -> 0x06b4 }
            int r10 = r4.features     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x06b4 }
            int r7 = r7.mask     // Catch:{ all -> 0x06b4 }
            r7 = r7 & r10
            if (r7 == 0) goto L_0x04d2
            com.alibaba.fastjson.JSONObject r7 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x06b4 }
            java.util.LinkedHashMap r10 = new java.util.LinkedHashMap     // Catch:{ all -> 0x06b4 }
            r10.<init>()     // Catch:{ all -> 0x06b4 }
            r7.<init>(r10)     // Catch:{ all -> 0x06b4 }
            goto L_0x04d7
        L_0x04d2:
            com.alibaba.fastjson.JSONObject r7 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x06b4 }
            r7.<init>()     // Catch:{ all -> 0x06b4 }
        L_0x04d7:
            if (r14 != 0) goto L_0x04e0
            if (r6 != 0) goto L_0x04e0
            com.alibaba.fastjson.parser.ParseContext r10 = r1.setContext(r15, r7, r8)     // Catch:{ all -> 0x06b4 }
            goto L_0x04e1
        L_0x04e0:
            r10 = 0
        L_0x04e1:
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r9 = r1.fieldTypeResolver     // Catch:{ all -> 0x06b4 }
            if (r9 == 0) goto L_0x0502
            if (r8 == 0) goto L_0x04ec
            java.lang.String r9 = r8.toString()     // Catch:{ all -> 0x06b4 }
            goto L_0x04ed
        L_0x04ec:
            r9 = 0
        L_0x04ed:
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r11 = r1.fieldTypeResolver     // Catch:{ all -> 0x06b4 }
            java.lang.reflect.Type r9 = r11.resolve(r2, r9)     // Catch:{ all -> 0x06b4 }
            if (r9 == 0) goto L_0x0502
            com.alibaba.fastjson.parser.ParserConfig r11 = r1.config     // Catch:{ all -> 0x06b4 }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r11 = r11.getDeserializer(r9)     // Catch:{ all -> 0x06b4 }
            java.lang.Object r9 = r11.deserialze(r1, r9, r8)     // Catch:{ all -> 0x06b4 }
            r11 = r9
            r9 = 1
            goto L_0x0504
        L_0x0502:
            r9 = 0
            r11 = 0
        L_0x0504:
            if (r9 != 0) goto L_0x050a
            java.lang.Object r11 = r1.parseObject(r7, r8)     // Catch:{ all -> 0x06b4 }
        L_0x050a:
            if (r10 == 0) goto L_0x0510
            if (r7 == r11) goto L_0x0510
            r10.object = r2     // Catch:{ all -> 0x06b4 }
        L_0x0510:
            int r7 = r1.resolveStatus     // Catch:{ all -> 0x06b4 }
            r9 = 1
            if (r7 != r9) goto L_0x051c
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x06b4 }
            r1.checkMapResolve(r2, r7)     // Catch:{ all -> 0x06b4 }
        L_0x051c:
            if (r12 == 0) goto L_0x0522
            r12.put(r8, r11)     // Catch:{ all -> 0x06b4 }
            goto L_0x0525
        L_0x0522:
            r2.put(r8, r11)     // Catch:{ all -> 0x06b4 }
        L_0x0525:
            if (r6 == 0) goto L_0x052a
            r1.setContext(r15, r11, r8)     // Catch:{ all -> 0x06b4 }
        L_0x052a:
            int r6 = r4.token     // Catch:{ all -> 0x06b4 }
            r7 = 13
            if (r6 != r7) goto L_0x053e
            r7 = 16
            r4.nextToken(r7)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x0539
            r1.contex = r15     // Catch:{ all -> 0x06b4 }
        L_0x0539:
            if (r14 != 0) goto L_0x053d
            r1.contex = r15
        L_0x053d:
            return r2
        L_0x053e:
            r7 = 16
            if (r6 == r7) goto L_0x05c0
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x055b:
            r7 = 116(0x74, float:1.63E-43)
            if (r10 != r7) goto L_0x057b
            java.lang.String r6 = r4.text     // Catch:{ all -> 0x06b4 }
            java.lang.String r7 = "true"
            int r9 = r4.bp     // Catch:{ all -> 0x06b4 }
            boolean r6 = r6.startsWith(r7, r9)     // Catch:{ all -> 0x06b4 }
            if (r6 == 0) goto L_0x0598
            int r6 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r6 = r6 + 3
            r4.bp = r6     // Catch:{ all -> 0x06b4 }
            r4.next()     // Catch:{ all -> 0x06b4 }
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x06b4 }
            r2.put(r8, r6)     // Catch:{ all -> 0x06b4 }
            goto L_0x0598
        L_0x057b:
            r7 = 102(0x66, float:1.43E-43)
            if (r10 != r7) goto L_0x0664
            java.lang.String r7 = r4.text     // Catch:{ all -> 0x06b4 }
            java.lang.String r9 = "false"
            int r10 = r4.bp     // Catch:{ all -> 0x06b4 }
            boolean r7 = r7.startsWith(r9, r10)     // Catch:{ all -> 0x06b4 }
            if (r7 == 0) goto L_0x0598
            int r7 = r4.bp     // Catch:{ all -> 0x06b4 }
            int r7 = r7 + r6
            r4.bp = r7     // Catch:{ all -> 0x06b4 }
            r4.next()     // Catch:{ all -> 0x06b4 }
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x06b4 }
            r2.put(r8, r6)     // Catch:{ all -> 0x06b4 }
        L_0x0598:
            char r6 = r4.ch     // Catch:{ all -> 0x06b4 }
            r7 = 44
            if (r6 == r7) goto L_0x05a7
            r7 = 125(0x7d, float:1.75E-43)
            if (r6 == r7) goto L_0x05a7
            r4.skipWhitespace()     // Catch:{ all -> 0x06b4 }
            char r6 = r4.ch     // Catch:{ all -> 0x06b4 }
        L_0x05a7:
            r7 = 44
            if (r6 != r7) goto L_0x05c6
            int r6 = r4.bp     // Catch:{ all -> 0x06b4 }
            r7 = 1
            int r6 = r6 + r7
            r4.bp = r6     // Catch:{ all -> 0x06b4 }
            int r7 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r6 < r7) goto L_0x05b8
            r10 = 26
            goto L_0x05be
        L_0x05b8:
            java.lang.String r7 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r7.charAt(r6)     // Catch:{ all -> 0x06b4 }
        L_0x05be:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
        L_0x05c0:
            r6 = 0
            r7 = 1
            r8 = 16
            goto L_0x06ab
        L_0x05c6:
            r5 = 125(0x7d, float:1.75E-43)
            if (r6 != r5) goto L_0x064b
            int r5 = r4.bp     // Catch:{ all -> 0x06b4 }
            r6 = 1
            int r5 = r5 + r6
            r4.bp = r5     // Catch:{ all -> 0x06b4 }
            int r6 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r5 < r6) goto L_0x05d7
            r10 = 26
            goto L_0x05dd
        L_0x05d7:
            java.lang.String r6 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r6.charAt(r5)     // Catch:{ all -> 0x06b4 }
        L_0x05dd:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r6 = 0
            r4.sp = r6     // Catch:{ all -> 0x06b4 }
            r5 = 44
            if (r10 != r5) goto L_0x0600
            int r5 = r4.bp     // Catch:{ all -> 0x06b4 }
            r6 = 1
            int r5 = r5 + r6
            r4.bp = r5     // Catch:{ all -> 0x06b4 }
            int r6 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r5 < r6) goto L_0x05f3
            r10 = 26
            goto L_0x05f9
        L_0x05f3:
            java.lang.String r6 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r6.charAt(r5)     // Catch:{ all -> 0x06b4 }
        L_0x05f9:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r5 = 16
            r4.token = r5     // Catch:{ all -> 0x06b4 }
            goto L_0x063f
        L_0x0600:
            r5 = 125(0x7d, float:1.75E-43)
            if (r10 != r5) goto L_0x061e
            int r5 = r4.bp     // Catch:{ all -> 0x06b4 }
            r6 = 1
            int r5 = r5 + r6
            r4.bp = r5     // Catch:{ all -> 0x06b4 }
            int r6 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r5 < r6) goto L_0x0611
            r10 = 26
            goto L_0x0617
        L_0x0611:
            java.lang.String r6 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r6.charAt(r5)     // Catch:{ all -> 0x06b4 }
        L_0x0617:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r5 = 13
            r4.token = r5     // Catch:{ all -> 0x06b4 }
            goto L_0x063f
        L_0x061e:
            r5 = 93
            if (r10 != r5) goto L_0x063c
            int r5 = r4.bp     // Catch:{ all -> 0x06b4 }
            r7 = 1
            int r5 = r5 + r7
            r4.bp = r5     // Catch:{ all -> 0x06b4 }
            int r6 = r4.len     // Catch:{ all -> 0x06b4 }
            if (r5 < r6) goto L_0x062f
            r10 = 26
            goto L_0x0635
        L_0x062f:
            java.lang.String r6 = r4.text     // Catch:{ all -> 0x06b4 }
            char r10 = r6.charAt(r5)     // Catch:{ all -> 0x06b4 }
        L_0x0635:
            r4.ch = r10     // Catch:{ all -> 0x06b4 }
            r5 = 15
            r4.token = r5     // Catch:{ all -> 0x06b4 }
            goto L_0x063f
        L_0x063c:
            r4.nextToken()     // Catch:{ all -> 0x06b4 }
        L_0x063f:
            if (r14 != 0) goto L_0x0646
            com.alibaba.fastjson.parser.ParseContext r4 = r1.contex     // Catch:{ all -> 0x06b4 }
            r1.setContext(r4, r2, r3)     // Catch:{ all -> 0x06b4 }
        L_0x0646:
            if (r14 != 0) goto L_0x064a
            r1.contex = r15
        L_0x064a:
            return r2
        L_0x064b:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x0664:
            r6 = 0
            r7 = 1
            r4.nextToken()     // Catch:{ all -> 0x06b4 }
            java.lang.Object r9 = r18.parse()     // Catch:{ all -> 0x06b4 }
            java.lang.Class r10 = r19.getClass()     // Catch:{ all -> 0x06b4 }
            java.lang.Class<com.alibaba.fastjson.JSONObject> r11 = com.alibaba.fastjson.JSONObject.class
            if (r10 != r11) goto L_0x0679
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x06b4 }
        L_0x0679:
            r2.put(r8, r9)     // Catch:{ all -> 0x06b4 }
            int r8 = r4.token     // Catch:{ all -> 0x06b4 }
            r9 = 13
            if (r8 != r9) goto L_0x068c
            r8 = 16
            r4.nextToken(r8)     // Catch:{ all -> 0x06b4 }
            if (r14 != 0) goto L_0x068b
            r1.contex = r15
        L_0x068b:
            return r2
        L_0x068c:
            r8 = 16
            int r9 = r4.token     // Catch:{ all -> 0x06b4 }
            if (r9 == r8) goto L_0x06ab
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x06b4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x06b4 }
            java.lang.String r5 = "syntax error, "
            r3.<init>(r5)     // Catch:{ all -> 0x06b4 }
            java.lang.String r4 = r4.info()     // Catch:{ all -> 0x06b4 }
            r3.append(r4)     // Catch:{ all -> 0x06b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x06b4 }
            r2.<init>(r3)     // Catch:{ all -> 0x06b4 }
            throw r2     // Catch:{ all -> 0x06b4 }
        L_0x06ab:
            r6 = 8
            r8 = 12
            r9 = 16
            r11 = 1
            goto L_0x0063
        L_0x06b4:
            r0 = move-exception
            r2 = r0
            if (r14 != 0) goto L_0x06ba
            r1.contex = r15
        L_0x06ba:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public <T> T parseObject(Class<T> cls) {
        return parseObject((Type) cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return parseObject(type, (Object) null);
    }

    public <T> T parseObject(Type type, Object obj) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (this.lexer.token == 4) {
            if (type == byte[].class) {
                Object bytesValue = this.lexer.bytesValue();
                this.lexer.nextToken();
                return bytesValue;
            } else if (type == char[].class) {
                String stringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return stringVal.toCharArray();
            }
        }
        try {
            return this.config.getDeserializer(type).deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray(cls, (Collection) arrayList);
        return arrayList;
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    /* JADX INFO: finally extract failed */
    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer objectDeserializer;
        Object obj2;
        if (this.lexer.token == 21 || this.lexer.token == 22) {
            this.lexer.nextToken();
        }
        if (this.lexer.token != 14) {
            StringBuilder sb = new StringBuilder("exepct '[', but ");
            sb.append(JSONToken.name(this.lexer.token));
            sb.append(", ");
            sb.append(this.lexer.info());
            throw new JSONException(sb.toString());
        }
        if (Integer.TYPE == type) {
            objectDeserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            objectDeserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            objectDeserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(12);
        }
        ParseContext parseContext = this.contex;
        if (!this.lexer.disableCircularReferenceDetect) {
            setContext(this.contex, collection, obj);
        }
        int i = 0;
        while (true) {
            try {
                if (this.lexer.token == 16) {
                    this.lexer.nextToken();
                } else if (this.lexer.token != 15) {
                    Object obj3 = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token == 4) {
                            obj2 = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object parse = parse();
                            if (parse != null) {
                                obj3 = parse.toString();
                            }
                            obj2 = obj3;
                        }
                        collection.add(obj2);
                    } else {
                        if (this.lexer.token == 8) {
                            this.lexer.nextToken();
                        } else {
                            obj3 = objectDeserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        collection.add(obj3);
                        if (this.resolveStatus == 1) {
                            checkListResolve(collection);
                        }
                    }
                    if (this.lexer.token == 16) {
                        this.lexer.nextToken();
                    }
                    i++;
                } else {
                    this.contex = parseContext;
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                this.contex = parseContext;
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object obj;
        boolean z;
        Type type;
        if (this.lexer.token == 8) {
            this.lexer.nextToken(16);
            return null;
        } else if (this.lexer.token != 14) {
            StringBuilder sb = new StringBuilder("syntax error, ");
            sb.append(this.lexer.info());
            throw new JSONException(sb.toString());
        } else {
            Object[] objArr = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token != 15) {
                    StringBuilder sb2 = new StringBuilder("syntax error, ");
                    sb2.append(this.lexer.info());
                    throw new JSONException(sb2.toString());
                }
                this.lexer.nextToken(16);
                return new Object[0];
            }
            this.lexer.nextToken(2);
            int i = 0;
            while (i < typeArr.length) {
                if (this.lexer.token == 8) {
                    this.lexer.nextToken(16);
                    obj = null;
                } else {
                    Class<String> cls = typeArr[i];
                    if (cls == Integer.TYPE || cls == Integer.class) {
                        if (this.lexer.token == 2) {
                            obj = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                        } else {
                            obj = TypeUtils.cast(parse(), (Type) cls, this.config);
                        }
                    } else if (cls != String.class) {
                        if (i != typeArr.length - 1 || !(cls instanceof Class)) {
                            type = null;
                            z = false;
                        } else {
                            Class cls2 = cls;
                            z = cls2.isArray();
                            type = cls2.getComponentType();
                        }
                        if (!z || this.lexer.token == 14) {
                            obj = this.config.getDeserializer(cls).deserialze(this, cls, null);
                        } else {
                            ArrayList arrayList = new ArrayList();
                            ObjectDeserializer deserializer = this.config.getDeserializer(type);
                            if (this.lexer.token != 15) {
                                while (true) {
                                    arrayList.add(deserializer.deserialze(this, cls, null));
                                    if (this.lexer.token != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(12);
                                }
                                if (this.lexer.token != 15) {
                                    StringBuilder sb3 = new StringBuilder("syntax error, ");
                                    sb3.append(this.lexer.info());
                                    throw new JSONException(sb3.toString());
                                }
                            }
                            obj = TypeUtils.cast((Object) arrayList, (Type) cls, this.config);
                        }
                    } else if (this.lexer.token == 4) {
                        obj = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        obj = TypeUtils.cast(parse(), (Type) cls, this.config);
                    }
                }
                objArr[i] = obj;
                if (this.lexer.token == 15) {
                    break;
                } else if (this.lexer.token != 16) {
                    StringBuilder sb4 = new StringBuilder("syntax error, ");
                    sb4.append(this.lexer.info());
                    throw new JSONException(sb4.toString());
                } else {
                    if (i == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i++;
                }
            }
            if (this.lexer.token != 15) {
                StringBuilder sb5 = new StringBuilder("syntax error, ");
                sb5.append(this.lexer.info());
                throw new JSONException(sb5.toString());
            }
            this.lexer.nextToken(16);
            return objArr;
        }
    }

    public void parseObject(Object obj) {
        Object obj2;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        int i = this.lexer.token;
        if (i == 12 || i == 16) {
            while (true) {
                String scanSymbol = this.lexer.scanSymbol(this.symbolTable);
                if (scanSymbol == null) {
                    if (this.lexer.token == 13) {
                        this.lexer.nextToken(16);
                        return;
                    } else if (this.lexer.token == 16) {
                        continue;
                    }
                }
                FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(scanSymbol) : null;
                if (fieldDeserializer != null) {
                    Class<?> cls2 = fieldDeserializer.fieldInfo.fieldClass;
                    Type type = fieldDeserializer.fieldInfo.fieldType;
                    if (cls2 == Integer.TYPE) {
                        this.lexer.nextTokenWithChar(':');
                        obj2 = IntegerCodec.instance.deserialze(this, type, null);
                    } else if (cls2 == String.class) {
                        this.lexer.nextTokenWithChar(':');
                        obj2 = parseString();
                    } else if (cls2 == Long.TYPE) {
                        this.lexer.nextTokenWithChar(':');
                        obj2 = IntegerCodec.instance.deserialze(this, type, null);
                    } else {
                        ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                        this.lexer.nextTokenWithChar(':');
                        obj2 = deserializer2.deserialze(this, type, null);
                    }
                    fieldDeserializer.setValue(obj, obj2);
                    if (this.lexer.token != 16 && this.lexer.token == 13) {
                        this.lexer.nextToken(16);
                        return;
                    }
                } else if ((this.lexer.features & Feature.IgnoreNotMatch.mask) == 0) {
                    StringBuilder sb = new StringBuilder("setter not found, class ");
                    sb.append(cls.getName());
                    sb.append(", property ");
                    sb.append(scanSymbol);
                    throw new JSONException(sb.toString());
                } else {
                    this.lexer.nextTokenWithChar(':');
                    parse();
                    if (this.lexer.token == 13) {
                        this.lexer.nextToken();
                        return;
                    }
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder("syntax error, expect {, actual ");
            sb2.append(JSONToken.name(i));
            throw new JSONException(sb2.toString());
        }
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type ".concat(String.valueOf(type)));
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class) type2, (Collection) arrayList);
            return arrayList;
        } else if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (!Object.class.equals(type3)) {
                ArrayList arrayList2 = new ArrayList();
                parseArray((Class) type3, (Collection) arrayList2);
                return arrayList2;
            } else if (wildcardType.getLowerBounds().length == 0) {
                return parse();
            } else {
                throw new JSONException("not support type : ".concat(String.valueOf(type)));
            }
        } else {
            if (type2 instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) type2;
                Type[] bounds = typeVariable.getBounds();
                if (bounds.length != 1) {
                    throw new JSONException("not support : ".concat(String.valueOf(typeVariable)));
                }
                Type type4 = bounds[0];
                if (type4 instanceof Class) {
                    ArrayList arrayList3 = new ArrayList();
                    parseArray((Class) type4, (Collection) arrayList3);
                    return arrayList3;
                }
            }
            if (type2 instanceof ParameterizedType) {
                ArrayList arrayList4 = new ArrayList();
                parseArray((Type) (ParameterizedType) type2, (Collection) arrayList4);
                return arrayList4;
            }
            throw new JSONException("TODO : ".concat(String.valueOf(type)));
        }
    }

    /* access modifiers changed from: protected */
    public void checkListResolve(Collection collection) {
        if (collection instanceof List) {
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, collection.size() - 1);
            lastResolveTask.ownerContext = this.contex;
            this.resolveStatus = 0;
            return;
        }
        ResolveTask lastResolveTask2 = getLastResolveTask();
        lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(collection);
        lastResolveTask2.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    /* access modifiers changed from: protected */
    public void checkMapResolve(Map map, Object obj) {
        ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
        ResolveTask lastResolveTask = getLastResolveTask();
        lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
        lastResolveTask.ownerContext = this.contex;
        this.resolveStatus = 0;
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        return (JSONObject) parseObject((Map) (this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject((Map<String, Object>) new LinkedHashMap<String,Object>()) : new JSONObject(), (Object) null);
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r4v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v25, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r4v29 */
    /* JADX WARNING: type inference failed for: r4v31, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r4v33, types: [java.lang.Number] */
    /* JADX WARNING: type inference failed for: r6v14, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v17, types: [java.util.Date] */
    /* JADX WARNING: type inference failed for: r6v18, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r6v19, types: [java.lang.Boolean] */
    /* JADX WARNING: type inference failed for: r6v20 */
    /* JADX WARNING: type inference failed for: r4v42 */
    /* JADX WARNING: type inference failed for: r6v25, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v27, types: [com.alibaba.fastjson.JSONArray, java.util.Collection] */
    /* JADX WARNING: type inference failed for: r6v28 */
    /* JADX WARNING: type inference failed for: r4v51 */
    /* JADX WARNING: type inference failed for: r4v53, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v40 */
    /* JADX WARNING: type inference failed for: r6v41 */
    /* JADX WARNING: type inference failed for: r4v73 */
    /* JADX WARNING: type inference failed for: r4v74 */
    /* JADX WARNING: type inference failed for: r4v75 */
    /* JADX WARNING: type inference failed for: r4v76 */
    /* JADX WARNING: type inference failed for: r6v42 */
    /* JADX WARNING: type inference failed for: r6v43 */
    /* JADX WARNING: type inference failed for: r6v44 */
    /* JADX WARNING: type inference failed for: r6v45 */
    /* JADX WARNING: type inference failed for: r6v46 */
    /* JADX WARNING: type inference failed for: r6v47 */
    /* JADX WARNING: type inference failed for: r4v77 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
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
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v29
      assigns: []
      uses: []
      mth insns count: 291
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
    /* JADX WARNING: Removed duplicated region for block: B:71:0x013e A[Catch:{ all -> 0x0132 }] */
    public final void parseArray(java.util.Collection r18, java.lang.Object r19) {
        /*
            r17 = this;
            r1 = r17
            com.alibaba.fastjson.parser.JSONLexer r2 = r1.lexer
            int r2 = r2.token
            r3 = 21
            if (r2 == r3) goto L_0x000e
            r3 = 22
            if (r2 != r3) goto L_0x0017
        L_0x000e:
            com.alibaba.fastjson.parser.JSONLexer r2 = r1.lexer
            r2.nextToken()
            com.alibaba.fastjson.parser.JSONLexer r2 = r1.lexer
            int r2 = r2.token
        L_0x0017:
            r3 = 14
            if (r2 == r3) goto L_0x0040
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "syntax error, expect [, actual "
            r4.<init>(r5)
            java.lang.String r2 = com.alibaba.fastjson.parser.JSONToken.name(r2)
            r4.append(r2)
            java.lang.String r2 = ", pos "
            r4.append(r2)
            com.alibaba.fastjson.parser.JSONLexer r2 = r1.lexer
            int r2 = r2.pos
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r3.<init>(r2)
            throw r3
        L_0x0040:
            com.alibaba.fastjson.parser.JSONLexer r2 = r1.lexer
            boolean r2 = r2.disableCircularReferenceDetect
            com.alibaba.fastjson.parser.ParseContext r3 = r1.contex
            if (r2 != 0) goto L_0x0052
            com.alibaba.fastjson.parser.ParseContext r4 = r1.contex
            r5 = r18
            r6 = r19
            r1.setContext(r4, r5, r6)
            goto L_0x0054
        L_0x0052:
            r5 = r18
        L_0x0054:
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            char r4 = r4.ch     // Catch:{ all -> 0x028b }
            r6 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x028b }
            r7 = 93     // Catch:{ all -> 0x028b }
            r8 = 4     // Catch:{ all -> 0x028b }
            r9 = 12     // Catch:{ all -> 0x028b }
            r12 = 34     // Catch:{ all -> 0x028b }
            r13 = 16     // Catch:{ all -> 0x028b }
            r14 = 1     // Catch:{ all -> 0x028b }
            if (r4 == r12) goto L_0x009f     // Catch:{ all -> 0x028b }
            if (r4 != r7) goto L_0x0077     // Catch:{ all -> 0x028b }
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            r4.next()     // Catch:{ all -> 0x028b }
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            r4.nextToken(r13)     // Catch:{ all -> 0x028b }
            if (r2 != 0) goto L_0x0076
            r1.contex = r3
        L_0x0076:
            return
        L_0x0077:
            if (r4 != r6) goto L_0x0098
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            int r15 = r4.bp     // Catch:{ all -> 0x028b }
            int r15 = r15 + r14     // Catch:{ all -> 0x028b }
            r4.bp = r15     // Catch:{ all -> 0x028b }
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            int r10 = r4.len     // Catch:{ all -> 0x028b }
            if (r15 < r10) goto L_0x0089     // Catch:{ all -> 0x028b }
            r10 = 26     // Catch:{ all -> 0x028b }
            goto L_0x0091     // Catch:{ all -> 0x028b }
        L_0x0089:
            com.alibaba.fastjson.parser.JSONLexer r10 = r1.lexer     // Catch:{ all -> 0x028b }
            java.lang.String r10 = r10.text     // Catch:{ all -> 0x028b }
            char r10 = r10.charAt(r15)     // Catch:{ all -> 0x028b }
        L_0x0091:
            r4.ch = r10     // Catch:{ all -> 0x028b }
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            r4.token = r9     // Catch:{ all -> 0x028b }
            goto L_0x009d     // Catch:{ all -> 0x028b }
        L_0x0098:
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            r4.nextToken(r9)     // Catch:{ all -> 0x028b }
        L_0x009d:
            r4 = 0     // Catch:{ all -> 0x028b }
            goto L_0x00b2     // Catch:{ all -> 0x028b }
        L_0x009f:
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            int r4 = r4.features     // Catch:{ all -> 0x028b }
            com.alibaba.fastjson.parser.Feature r10 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x028b }
            int r10 = r10.mask     // Catch:{ all -> 0x028b }
            r4 = r4 & r10     // Catch:{ all -> 0x028b }
            if (r4 != 0) goto L_0x00ac     // Catch:{ all -> 0x028b }
            r4 = 1     // Catch:{ all -> 0x028b }
            goto L_0x00b2     // Catch:{ all -> 0x028b }
        L_0x00ac:
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer     // Catch:{ all -> 0x028b }
            r4.nextToken(r8)     // Catch:{ all -> 0x028b }
            goto L_0x009d
        L_0x00b2:
            r10 = r3
            r15 = r5
            r3 = 0
            r5 = r2
            r2 = r1
        L_0x00b7:
            if (r4 == 0) goto L_0x0137
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            char r9 = r9.ch     // Catch:{ all -> 0x0132 }
            if (r9 != r12) goto L_0x0137     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.String r9 = r9.scanStringValue(r12)     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r6 = r2.lexer     // Catch:{ all -> 0x0132 }
            char r6 = r6.ch     // Catch:{ all -> 0x0132 }
            r11 = 44     // Catch:{ all -> 0x0132 }
            if (r6 != r11) goto L_0x00fe     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r6 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r11 = r6.bp     // Catch:{ all -> 0x0132 }
            int r11 = r11 + r14     // Catch:{ all -> 0x0132 }
            r6.bp = r11     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r6 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r8 = r6.len     // Catch:{ all -> 0x0132 }
            if (r11 < r8) goto L_0x00dd     // Catch:{ all -> 0x0132 }
            r8 = 26     // Catch:{ all -> 0x0132 }
            goto L_0x00e5     // Catch:{ all -> 0x0132 }
        L_0x00dd:
            com.alibaba.fastjson.parser.JSONLexer r8 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.String r8 = r8.text     // Catch:{ all -> 0x0132 }
            char r8 = r8.charAt(r11)     // Catch:{ all -> 0x0132 }
        L_0x00e5:
            r6.ch = r8     // Catch:{ all -> 0x0132 }
            r15.add(r9)     // Catch:{ all -> 0x0132 }
            int r6 = r2.resolveStatus     // Catch:{ all -> 0x0132 }
            if (r6 != r14) goto L_0x00f1     // Catch:{ all -> 0x0132 }
            r2.checkListResolve(r15)     // Catch:{ all -> 0x0132 }
        L_0x00f1:
            if (r8 == r12) goto L_0x00f9     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            r9 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
            r11 = 0     // Catch:{ all -> 0x0132 }
            goto L_0x0282     // Catch:{ all -> 0x0132 }
        L_0x00f9:
            r8 = 4     // Catch:{ all -> 0x0132 }
            r9 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
            goto L_0x0276     // Catch:{ all -> 0x0132 }
        L_0x00fe:
            if (r6 != r7) goto L_0x012f     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r3 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r3.bp     // Catch:{ all -> 0x0132 }
            int r4 = r4 + r14     // Catch:{ all -> 0x0132 }
            r3.bp = r4     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r3 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r6 = r3.len     // Catch:{ all -> 0x0132 }
            if (r4 < r6) goto L_0x0110     // Catch:{ all -> 0x0132 }
            r4 = 26     // Catch:{ all -> 0x0132 }
            goto L_0x0118     // Catch:{ all -> 0x0132 }
        L_0x0110:
            com.alibaba.fastjson.parser.JSONLexer r6 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r6.text     // Catch:{ all -> 0x0132 }
            char r4 = r6.charAt(r4)     // Catch:{ all -> 0x0132 }
        L_0x0118:
            r3.ch = r4     // Catch:{ all -> 0x0132 }
            r15.add(r9)     // Catch:{ all -> 0x0132 }
            int r3 = r2.resolveStatus     // Catch:{ all -> 0x0132 }
            if (r3 != r14) goto L_0x0124     // Catch:{ all -> 0x0132 }
            r2.checkListResolve(r15)     // Catch:{ all -> 0x0132 }
        L_0x0124:
            com.alibaba.fastjson.parser.JSONLexer r3 = r2.lexer     // Catch:{ all -> 0x0132 }
            r3.nextToken(r13)     // Catch:{ all -> 0x0132 }
            if (r5 != 0) goto L_0x012e
            r2.contex = r10
            return
        L_0x012e:
            return
        L_0x012f:
            r11 = r4
            goto L_0x027f
        L_0x0132:
            r0 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0290
        L_0x0137:
            r11 = r4
        L_0x0138:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r4.token     // Catch:{ all -> 0x0132 }
            if (r4 == r13) goto L_0x027f     // Catch:{ all -> 0x0132 }
            r6 = 0     // Catch:{ all -> 0x0132 }
            switch(r4) {
                case 2: goto L_0x0201;
                case 3: goto L_0x01e0;
                case 4: goto L_0x01b4;
                case 6: goto L_0x01ab;
                case 7: goto L_0x01a2;
                case 8: goto L_0x0199;
                case 12: goto L_0x0175;
                case 14: goto L_0x0168;
                case 15: goto L_0x015e;
                case 20: goto L_0x0155;
                case 23: goto L_0x014a;
                default: goto L_0x0142;
            }     // Catch:{ all -> 0x0132 }
        L_0x0142:
            r6 = 0     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            java.lang.Object r4 = r2.parse()     // Catch:{ all -> 0x0132 }
            goto L_0x020e     // Catch:{ all -> 0x0132 }
        L_0x014a:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            r4.nextToken(r8)     // Catch:{ all -> 0x0132 }
        L_0x0150:
            r4 = r6     // Catch:{ all -> 0x0132 }
            r6 = 0     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            goto L_0x020e     // Catch:{ all -> 0x0132 }
        L_0x0155:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0132 }
            java.lang.String r4 = "unclosed jsonArray"     // Catch:{ all -> 0x0132 }
            r3.<init>(r4)     // Catch:{ all -> 0x0132 }
            throw r3     // Catch:{ all -> 0x0132 }
        L_0x015e:
            com.alibaba.fastjson.parser.JSONLexer r3 = r2.lexer     // Catch:{ all -> 0x0132 }
            r3.nextToken(r13)     // Catch:{ all -> 0x0132 }
            if (r5 != 0) goto L_0x0167
            r2.contex = r10
        L_0x0167:
            return
        L_0x0168:
            com.alibaba.fastjson.JSONArray r6 = new com.alibaba.fastjson.JSONArray     // Catch:{ all -> 0x0132 }
            r6.<init>()     // Catch:{ all -> 0x0132 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0132 }
            r2.parseArray(r6, r4)     // Catch:{ all -> 0x0132 }
            goto L_0x0150     // Catch:{ all -> 0x0132 }
        L_0x0175:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r4.features     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.Feature r6 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x0132 }
            int r6 = r6.mask     // Catch:{ all -> 0x0132 }
            r4 = r4 & r6     // Catch:{ all -> 0x0132 }
            if (r4 == 0) goto L_0x018b     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0132 }
            java.util.LinkedHashMap r6 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0132 }
            r6.<init>()     // Catch:{ all -> 0x0132 }
            r4.<init>(r6)     // Catch:{ all -> 0x0132 }
            goto L_0x0190     // Catch:{ all -> 0x0132 }
        L_0x018b:
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0132 }
            r4.<init>()     // Catch:{ all -> 0x0132 }
        L_0x0190:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0132 }
            java.lang.Object r6 = r2.parseObject(r4, r6)     // Catch:{ all -> 0x0132 }
            goto L_0x0150     // Catch:{ all -> 0x0132 }
        L_0x0199:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            r4.nextToken(r8)     // Catch:{ all -> 0x0132 }
        L_0x019f:
            r4 = r6     // Catch:{ all -> 0x0132 }
            r6 = 0     // Catch:{ all -> 0x0132 }
            goto L_0x020e     // Catch:{ all -> 0x0132 }
        L_0x01a2:
            r8 = 4     // Catch:{ all -> 0x0132 }
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.nextToken(r13)     // Catch:{ all -> 0x0132 }
            goto L_0x019f     // Catch:{ all -> 0x0132 }
        L_0x01ab:
            r8 = 4     // Catch:{ all -> 0x0132 }
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.nextToken(r13)     // Catch:{ all -> 0x0132 }
            goto L_0x019f     // Catch:{ all -> 0x0132 }
        L_0x01b4:
            r8 = 4     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.String r6 = r4.stringVal()     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.nextToken(r13)     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r4.features     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.Feature r9 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x0132 }
            int r9 = r9.mask     // Catch:{ all -> 0x0132 }
            r4 = r4 & r9     // Catch:{ all -> 0x0132 }
            if (r4 == 0) goto L_0x019f     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = new com.alibaba.fastjson.parser.JSONLexer     // Catch:{ all -> 0x0132 }
            r4.<init>(r6)     // Catch:{ all -> 0x0132 }
            boolean r9 = r4.scanISO8601DateIfMatch(r14)     // Catch:{ all -> 0x0132 }
            if (r9 == 0) goto L_0x01dc     // Catch:{ all -> 0x0132 }
            java.util.Calendar r6 = r4.calendar     // Catch:{ all -> 0x0132 }
            java.util.Date r6 = r6.getTime()     // Catch:{ all -> 0x0132 }
        L_0x01dc:
            r4.close()     // Catch:{ all -> 0x0132 }
            goto L_0x019f     // Catch:{ all -> 0x0132 }
        L_0x01e0:
            r8 = 4     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r4.features     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.Feature r6 = com.alibaba.fastjson.parser.Feature.UseBigDecimal     // Catch:{ all -> 0x0132 }
            int r6 = r6.mask     // Catch:{ all -> 0x0132 }
            r4 = r4 & r6     // Catch:{ all -> 0x0132 }
            if (r4 == 0) goto L_0x01f4     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.Number r4 = r4.decimalValue(r14)     // Catch:{ all -> 0x0132 }
            r6 = 0     // Catch:{ all -> 0x0132 }
            goto L_0x01fb     // Catch:{ all -> 0x0132 }
        L_0x01f4:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r6 = 0     // Catch:{ all -> 0x0132 }
            java.lang.Number r4 = r4.decimalValue(r6)     // Catch:{ all -> 0x0132 }
        L_0x01fb:
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            r9.nextToken(r13)     // Catch:{ all -> 0x0132 }
            goto L_0x020e     // Catch:{ all -> 0x0132 }
        L_0x0201:
            r6 = 0     // Catch:{ all -> 0x0132 }
            r8 = 4     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.Number r4 = r4.integerValue()     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            r9.nextToken(r13)     // Catch:{ all -> 0x0132 }
        L_0x020e:
            r15.add(r4)     // Catch:{ all -> 0x0132 }
            int r4 = r2.resolveStatus     // Catch:{ all -> 0x0132 }
            if (r4 != r14) goto L_0x0218     // Catch:{ all -> 0x0132 }
            r2.checkListResolve(r15)     // Catch:{ all -> 0x0132 }
        L_0x0218:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r4 = r4.token     // Catch:{ all -> 0x0132 }
            if (r4 != r13) goto L_0x0273     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            char r4 = r4.ch     // Catch:{ all -> 0x0132 }
            if (r4 != r12) goto L_0x0232     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r9 = r9.bp     // Catch:{ all -> 0x0132 }
            r4.pos = r9     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.scanString()     // Catch:{ all -> 0x0132 }
            goto L_0x0273     // Catch:{ all -> 0x0132 }
        L_0x0232:
            r9 = 48     // Catch:{ all -> 0x0132 }
            if (r4 < r9) goto L_0x0248     // Catch:{ all -> 0x0132 }
            r9 = 57     // Catch:{ all -> 0x0132 }
            if (r4 > r9) goto L_0x0248     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r9 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r9 = r9.bp     // Catch:{ all -> 0x0132 }
            r4.pos = r9     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.scanNumber()     // Catch:{ all -> 0x0132 }
            goto L_0x0273     // Catch:{ all -> 0x0132 }
        L_0x0248:
            r9 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
            if (r4 != r9) goto L_0x026d     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r6 = 12     // Catch:{ all -> 0x0132 }
            r4.token = r6     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r6 = r4.bp     // Catch:{ all -> 0x0132 }
            int r6 = r6 + r14     // Catch:{ all -> 0x0132 }
            r4.bp = r6     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            int r7 = r4.len     // Catch:{ all -> 0x0132 }
            if (r6 < r7) goto L_0x0262     // Catch:{ all -> 0x0132 }
            r6 = 26     // Catch:{ all -> 0x0132 }
            goto L_0x026a     // Catch:{ all -> 0x0132 }
        L_0x0262:
            com.alibaba.fastjson.parser.JSONLexer r7 = r2.lexer     // Catch:{ all -> 0x0132 }
            java.lang.String r7 = r7.text     // Catch:{ all -> 0x0132 }
            char r6 = r7.charAt(r6)     // Catch:{ all -> 0x0132 }
        L_0x026a:
            r4.ch = r6     // Catch:{ all -> 0x0132 }
            goto L_0x0275     // Catch:{ all -> 0x0132 }
        L_0x026d:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.nextToken()     // Catch:{ all -> 0x0132 }
            goto L_0x0275     // Catch:{ all -> 0x0132 }
        L_0x0273:
            r9 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
        L_0x0275:
            r4 = r11     // Catch:{ all -> 0x0132 }
        L_0x0276:
            int r3 = r3 + r14     // Catch:{ all -> 0x0132 }
            r6 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
            r7 = 93     // Catch:{ all -> 0x0132 }
            r9 = 12     // Catch:{ all -> 0x0132 }
            goto L_0x00b7     // Catch:{ all -> 0x0132 }
        L_0x027f:
            r8 = 4     // Catch:{ all -> 0x0132 }
            r9 = 123(0x7b, float:1.72E-43)     // Catch:{ all -> 0x0132 }
        L_0x0282:
            com.alibaba.fastjson.parser.JSONLexer r4 = r2.lexer     // Catch:{ all -> 0x0132 }
            r4.nextToken()     // Catch:{ all -> 0x0132 }
            r7 = 93
            goto L_0x0138
        L_0x028b:
            r0 = move-exception
            r5 = r2
            r10 = r3
            r2 = r0
            r3 = r1
        L_0x0290:
            if (r5 != 0) goto L_0x0294
            r3.contex = r10
        L_0x0294:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseArray(java.util.Collection, java.lang.Object):void");
    }

    /* access modifiers changed from: protected */
    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    /* access modifiers changed from: protected */
    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public void setContext(ParseContext parseContext) {
        if (!this.lexer.disableCircularReferenceDetect) {
            this.contex = parseContext;
        }
    }

    /* access modifiers changed from: protected */
    public void popContext() {
        this.contex = this.contex.parent;
        this.contextArray[this.contextArrayIndex - 1] = null;
        this.contextArrayIndex--;
    }

    /* access modifiers changed from: protected */
    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.disableCircularReferenceDetect) {
            return null;
        }
        this.contex = new ParseContext(parseContext, obj, obj2);
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        if (this.contextArray == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= this.contextArray.length) {
            ParseContext[] parseContextArr = new ParseContext[((this.contextArray.length * 3) / 2)];
            System.arraycopy(this.contextArray, 0, parseContextArr, 0, this.contextArray.length);
            this.contextArray = parseContextArr;
        }
        this.contextArray[i] = this.contex;
        return this.contex;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parse(Object obj) {
        boolean z = true;
        switch (this.lexer.token) {
            case 2:
                Number integerValue = this.lexer.integerValue();
                this.lexer.nextToken();
                return integerValue;
            case 3:
                if ((this.lexer.features & Feature.UseBigDecimal.mask) == 0) {
                    z = false;
                }
                Number decimalValue = this.lexer.decimalValue(z);
                this.lexer.nextToken();
                return decimalValue;
            case 4:
                String stringVal = this.lexer.stringVal();
                this.lexer.nextToken(16);
                if ((this.lexer.features & Feature.AllowISO8601DateFormat.mask) != 0) {
                    JSONLexer jSONLexer = new JSONLexer(stringVal);
                    try {
                        if (jSONLexer.scanISO8601DateIfMatch(true)) {
                            return jSONLexer.calendar.getTime();
                        }
                        jSONLexer.close();
                    } finally {
                        jSONLexer.close();
                    }
                }
                return stringVal;
            case 6:
                this.lexer.nextToken(16);
                return Boolean.TRUE;
            case 7:
                this.lexer.nextToken(16);
                return Boolean.FALSE;
            case 8:
            case 23:
                this.lexer.nextToken();
                return null;
            case 9:
                this.lexer.nextToken(18);
                if (this.lexer.token != 18) {
                    StringBuilder sb = new StringBuilder("syntax error, ");
                    sb.append(this.lexer.info());
                    throw new JSONException(sb.toString());
                }
                this.lexer.nextToken(10);
                accept(10);
                long longValue = this.lexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(longValue);
            case 12:
                return parseObject((Map) (this.lexer.features & Feature.OrderedField.mask) != 0 ? new JSONObject((Map<String, Object>) new LinkedHashMap<String,Object>()) : new JSONObject(), obj);
            case 14:
                JSONArray jSONArray = new JSONArray();
                parseArray((Collection) jSONArray, obj);
                return jSONArray;
            case 20:
                if (this.lexer.isBlankInput()) {
                    return null;
                }
                StringBuilder sb2 = new StringBuilder("syntax error, ");
                sb2.append(this.lexer.info());
                throw new JSONException(sb2.toString());
            case 21:
                this.lexer.nextToken();
                HashSet hashSet = new HashSet();
                parseArray((Collection) hashSet, obj);
                return hashSet;
            case 22:
                this.lexer.nextToken();
                TreeSet treeSet = new TreeSet();
                parseArray((Collection) treeSet, obj);
                return treeSet;
            default:
                StringBuilder sb3 = new StringBuilder("syntax error, ");
                sb3.append(this.lexer.info());
                throw new JSONException(sb3.toString());
        }
    }

    public void config(Feature feature, boolean z) {
        this.lexer.config(feature, z);
    }

    public final void accept(int i) {
        if (this.lexer.token == i) {
            this.lexer.nextToken();
            return;
        }
        StringBuilder sb = new StringBuilder("syntax error, expect ");
        sb.append(JSONToken.name(i));
        sb.append(", actual ");
        sb.append(JSONToken.name(this.lexer.token));
        throw new JSONException(sb.toString());
    }

    public void close() {
        try {
            if (this.lexer.token != 20) {
                StringBuilder sb = new StringBuilder("not close json text, token : ");
                sb.append(JSONToken.name(this.lexer.token));
                throw new JSONException(sb.toString());
            }
        } finally {
            this.lexer.close();
        }
    }

    public void handleResovleTask(Object obj) {
        if (this.resolveTaskList != null) {
            int size = this.resolveTaskList.size();
            for (int i = 0; i < size; i++) {
                ResolveTask resolveTask = this.resolveTaskList.get(i);
                FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
                if (fieldDeserializer != null) {
                    Object obj2 = null;
                    Object obj3 = resolveTask.ownerContext != null ? resolveTask.ownerContext.object : null;
                    String access$000 = resolveTask.referenceValue;
                    if (access$000.startsWith("$")) {
                        for (int i2 = 0; i2 < this.contextArrayIndex; i2++) {
                            if (access$000.equals(this.contextArray[i2].toString())) {
                                obj2 = this.contextArray[i2].object;
                            }
                        }
                    } else {
                        obj2 = resolveTask.context.object;
                    }
                    fieldDeserializer.setValue(obj3, obj2);
                }
            }
        }
    }

    public String parseString() {
        int i = this.lexer.token;
        if (i == 4) {
            String stringVal = this.lexer.stringVal();
            char c = this.lexer.ch;
            char c2 = JSONLexer.EOI;
            if (c == ',') {
                JSONLexer jSONLexer = this.lexer;
                int i2 = jSONLexer.bp + 1;
                jSONLexer.bp = i2;
                JSONLexer jSONLexer2 = this.lexer;
                if (i2 < jSONLexer2.len) {
                    c2 = this.lexer.text.charAt(i2);
                }
                jSONLexer2.ch = c2;
                this.lexer.token = 16;
            } else if (this.lexer.ch == ']') {
                JSONLexer jSONLexer3 = this.lexer;
                int i3 = jSONLexer3.bp + 1;
                jSONLexer3.bp = i3;
                JSONLexer jSONLexer4 = this.lexer;
                if (i3 < jSONLexer4.len) {
                    c2 = this.lexer.text.charAt(i3);
                }
                jSONLexer4.ch = c2;
                this.lexer.token = 15;
            } else if (this.lexer.ch == '}') {
                JSONLexer jSONLexer5 = this.lexer;
                int i4 = jSONLexer5.bp + 1;
                jSONLexer5.bp = i4;
                JSONLexer jSONLexer6 = this.lexer;
                if (i4 < jSONLexer6.len) {
                    c2 = this.lexer.text.charAt(i4);
                }
                jSONLexer6.ch = c2;
                this.lexer.token = 13;
            } else {
                this.lexer.nextToken();
            }
            return stringVal;
        } else if (i == 2) {
            String numberString = this.lexer.numberString();
            this.lexer.nextToken(16);
            return numberString;
        } else {
            Object parse = parse();
            if (parse == null) {
                return null;
            }
            return parse.toString();
        }
    }
}
