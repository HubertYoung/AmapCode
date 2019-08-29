package org.xidea.el.fn;

import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xidea.el.Invocable;
import org.xidea.el.impl.ExpressionFactoryImpl;
import org.xidea.el.json.JSONDecoder;
import org.xidea.el.json.JSONEncoder;

class JSGlobal implements Invocable {
    static String[] a = {"abs", "acos", "asin", "atan", "ceil", "asin", "cos", "exp", "floor", ReportManager.LOG_PATH, "round", "sin", "sqrt", "tan", "random", "min", "max", "pow", "atan2"};
    private static final Pattern c = Pattern.compile("^[\\+\\-]?(0x[0-9a-f]+|0+[0-7]*|[1-9][0-9]*)", 2);
    private static final Pattern d = Pattern.compile("^[\\+\\-]?[0-9]*(?:\\.[0-9]+)?");
    private static final Pattern e = Pattern.compile("[;/?:@&=+$,#]");
    private static final Pattern f = Pattern.compile("\\+|%3B|%2F|%3F|%3A|%40|%26|%3D|%2B|%24|%2C|%23");
    final int b;

    static void a(ExpressionFactoryImpl expressionFactoryImpl) {
        HashMap hashMap = new HashMap();
        double log = Math.log(10.0d);
        double log2 = Math.log(2.0d);
        hashMap.put("E", Double.valueOf(2.718281828459045d));
        hashMap.put("PI", Double.valueOf(3.141592653589793d));
        hashMap.put("LN10", Double.valueOf(log));
        hashMap.put("LN2", Double.valueOf(log2));
        hashMap.put("LOG2E", Double.valueOf(1.0d / log2));
        hashMap.put("LOG10E", Double.valueOf(1.0d / log));
        hashMap.put("SQRT1_2", Double.valueOf(Math.sqrt(0.5d)));
        hashMap.put("SQRT2", Double.valueOf(Math.sqrt(2.0d)));
        for (int i = 0; i < a.length; i++) {
            hashMap.put(a[i], new JSGlobal(i));
        }
        expressionFactoryImpl.a((String) "Math", (Object) hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("parse", new JSGlobal(100));
        hashMap2.put("stringify", new JSGlobal(101));
        expressionFactoryImpl.a((String) "JSON", (Object) Collections.unmodifiableMap(hashMap2));
        expressionFactoryImpl.a((String) "isFinite", (Object) new JSGlobal(200));
        expressionFactoryImpl.a((String) "isNaN", (Object) new JSGlobal(201));
        expressionFactoryImpl.a((String) "parseInt", (Object) new JSGlobal(300));
        expressionFactoryImpl.a((String) "parseFloat", (Object) new JSGlobal(301));
        expressionFactoryImpl.a((String) "encodeURI", (Object) new JSGlobal(400));
        expressionFactoryImpl.a((String) "decodeURI", (Object) new JSGlobal(401));
        expressionFactoryImpl.a((String) "encodeURIComponent", (Object) new JSGlobal(402));
        expressionFactoryImpl.a((String) "decodeURIComponent", (Object) new JSGlobal(403));
        expressionFactoryImpl.a((String) "Infinity", (Object) Double.valueOf(Double.POSITIVE_INFINITY));
        expressionFactoryImpl.a((String) "NaN", (Object) Double.valueOf(Double.NaN));
    }

    private JSGlobal(int i) {
        this.b = i;
    }

    public String toString() {
        switch (this.b) {
            case 100:
                return "JSON.parse";
            case 101:
                return "JSON.stringify";
            case 200:
                return "isFinite";
            case 201:
                return "isNaN";
            case 300:
                return "parseInt";
            case 301:
                return "parseFloat";
            case 400:
                return "encodeURI";
            case 401:
                return "decodeURI";
            case 402:
                return "encodeURIConponent";
            case 403:
                return "decodeURIComponent";
            default:
                if (this.b <= 0 || this.b >= a.length) {
                    StringBuilder sb = new StringBuilder("unknow method:");
                    sb.append(this.b);
                    return sb.toString();
                }
                StringBuilder sb2 = new StringBuilder("Math.");
                sb2.append(a[this.b]);
                return sb2.toString();
        }
    }

    public final Object a(Object obj, Object... objArr) throws Exception {
        boolean z = true;
        int i = 0;
        switch (this.b) {
            case 100:
                return JSONDecoder.decode(ECMA262Impl.a((Object) JSObject.a(objArr, (String) null), String.class).toString());
            case 101:
                return JSONEncoder.encode(JSObject.a(objArr, 0, (Object) null));
            case 300:
            case 301:
                String trim = JSObject.a(objArr, (String) "").trim();
                if (this.b == 301) {
                    return a(trim);
                }
                int intValue = JSObject.a(objArr, 1, (Number) Integer.valueOf(-1)).intValue();
                String trim2 = trim.trim();
                Matcher matcher = c.matcher(trim2);
                if (!matcher.find()) {
                    return Integer.valueOf(a(trim2).intValue());
                }
                String group = matcher.group(0);
                if (intValue > 0) {
                    return Long.valueOf(Long.parseLong(group, intValue));
                }
                String group2 = matcher.group(1);
                if (group2.charAt(0) != '0') {
                    return Long.valueOf(Long.parseLong(group, 10));
                }
                if (group2.length() == 1) {
                    return Integer.valueOf(0);
                }
                char charAt = group2.charAt(1);
                if (charAt != 'x' && charAt != 'X') {
                    return Long.valueOf(Long.parseLong(group, 8));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(group.charAt(0));
                sb.append(group2.substring(2));
                return Long.valueOf(Long.parseLong(sb.toString(), 16));
            case 400:
            case 401:
            case 402:
            case 403:
                String valueOf = String.valueOf(JSObject.a(objArr, 0, (Object) "null"));
                String valueOf2 = String.valueOf(JSObject.a(objArr, 1, (Object) "utf-8"));
                boolean z2 = (this.b & 1) == 0;
                if (this.b >= 402) {
                    z = false;
                }
                if (!z) {
                    return a(z2, valueOf, valueOf2);
                }
                Matcher matcher2 = (z2 ? e : f).matcher(valueOf);
                StringBuilder sb2 = new StringBuilder();
                while (matcher2.find()) {
                    int start = matcher2.start();
                    if (start >= i) {
                        sb2.append(a(z2, valueOf.substring(i, start), valueOf2));
                    }
                    i = matcher2.end();
                    sb2.append(valueOf.substring(start, i));
                }
                sb2.append(a(z2, valueOf.substring(i), valueOf2));
                return sb2.toString();
            default:
                switch (this.b) {
                    case 200:
                        Number a2 = JSObject.a(objArr, 0, (Number) null);
                        if (a2 == null) {
                            return Boolean.TRUE;
                        }
                        if (Double.isNaN(a2.doubleValue()) || Double.isInfinite(a2.doubleValue())) {
                            z = false;
                        }
                        return Boolean.valueOf(z);
                    case 201:
                        Number a3 = JSObject.a(objArr, 0, (Number) null);
                        if (a3 == null) {
                            return Boolean.FALSE;
                        }
                        return Boolean.valueOf(Double.isNaN(a3.doubleValue()));
                    default:
                        Number a4 = JSObject.a(objArr, 0, (Number) Double.valueOf(Double.NaN));
                        switch (this.b) {
                            case 0:
                                double doubleValue = a4.doubleValue();
                                return doubleValue < 0.0d ? Double.valueOf(-doubleValue) : a4;
                            case 1:
                                return Double.valueOf(Math.acos(a4.doubleValue()));
                            case 2:
                                return a(a4);
                            case 3:
                                return Double.valueOf(Math.atan(a4.doubleValue()));
                            case 4:
                                return Double.valueOf(Math.ceil(a4.doubleValue()));
                            case 5:
                                return a(a4);
                            case 6:
                                return Double.valueOf(Math.cos(a4.doubleValue()));
                            case 7:
                                return Double.valueOf(Math.exp(a4.doubleValue()));
                            case 8:
                                return Double.valueOf(Math.floor(a4.doubleValue()));
                            case 9:
                                return Double.valueOf(Math.log(a4.doubleValue()));
                            case 10:
                                return Long.valueOf(Math.round(a4.doubleValue()));
                            case 11:
                                return Double.valueOf(Math.sin(a4.doubleValue()));
                            case 12:
                                return Double.valueOf(Math.sqrt(a4.doubleValue()));
                            case 13:
                                return Double.valueOf(Math.tan(a4.doubleValue()));
                            case 14:
                                return Double.valueOf(Math.random());
                            case 15:
                                return a(false, objArr);
                            case 16:
                                return a(true, objArr);
                            case 17:
                                return Double.valueOf(Math.pow(a4.doubleValue(), JSObject.a(objArr, 1, (Number) Double.valueOf(Double.NaN)).doubleValue()));
                            case 18:
                                return Double.valueOf(Math.atan2(Double.valueOf(a4.doubleValue()).doubleValue(), Double.valueOf(JSObject.a(objArr, 1, (Number) Double.valueOf(Double.NaN)).doubleValue()).doubleValue()));
                            default:
                                return Integer.valueOf(0);
                        }
                }
        }
    }

    private static Object a(boolean z, Object... objArr) throws Exception {
        Number number = null;
        for (int i = 0; i < objArr.length; i++) {
            Number a2 = ECMA262Impl.a(JSObject.a(objArr, i, (Object) Double.valueOf(Double.NaN)));
            double floatValue = (double) a2.floatValue();
            if (Double.isNaN(floatValue)) {
                return a2;
            }
            if (z) {
                if (Double.POSITIVE_INFINITY == floatValue) {
                    return a2;
                }
            } else if (Double.NEGATIVE_INFINITY == floatValue) {
                return a2;
            }
            if (i != 0) {
                if (floatValue > number.doubleValue()) {
                    if (!z) {
                    }
                } else if (z) {
                }
            }
            number = a2;
        }
        return number;
    }

    private static Object a(Number number) {
        return Double.valueOf(Math.asin(number.doubleValue()));
    }

    private static Number a(String str) {
        if (str.length() > 0) {
            Matcher matcher = d.matcher(str);
            if (matcher.find()) {
                return Double.valueOf(Double.parseDouble(matcher.group(0)));
            }
        }
        return Double.valueOf(Double.NaN);
    }

    private static Object a(boolean z, String str, String str2) throws UnsupportedEncodingException {
        return z ? URLEncoder.encode(str, str2) : URLDecoder.decode(str, str2);
    }
}
