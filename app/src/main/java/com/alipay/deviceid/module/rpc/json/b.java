package com.alipay.deviceid.module.rpc.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class b {
    public static final Object a = new a(0);
    private Map b;

    static final class a {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final Object clone() {
            return this;
        }

        public final boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public final String toString() {
            return "null";
        }
    }

    public b() {
        this.b = new HashMap();
    }

    public b(c cVar) {
        this();
        if (cVar.c() != '{') {
            throw cVar.a((String) "A JSONObject text must begin with '{'");
        }
        while (true) {
            char c = cVar.c();
            if (c == 0) {
                throw cVar.a((String) "A JSONObject text must end with '}'");
            } else if (c != '}') {
                cVar.a();
                String obj = cVar.d().toString();
                char c2 = cVar.c();
                if (c2 == '=') {
                    if (cVar.b() != '>') {
                        cVar.a();
                    }
                } else if (c2 != ':') {
                    throw cVar.a((String) "Expected a ':' after a key");
                }
                Object d = cVar.d();
                if (obj == null) {
                    throw new JSONException((String) "Null key.");
                }
                if (d != null) {
                    b(d);
                    this.b.put(obj, d);
                } else {
                    this.b.remove(obj);
                }
                char c3 = cVar.c();
                if (c3 == ',' || c3 == ';') {
                    if (cVar.c() != '}') {
                        cVar.a();
                    } else {
                        return;
                    }
                } else if (c3 != '}') {
                    throw cVar.a((String) "Expected a ',' or '}'");
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public b(String str) {
        this(new c(str));
    }

    public b(Map map) {
        this.b = map == null ? new HashMap() : map;
    }

    static String a(Object obj) {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (!(obj instanceof Number)) {
            return ((obj instanceof Boolean) || (obj instanceof b) || (obj instanceof a)) ? obj.toString() : obj instanceof Map ? new b((Map) obj).toString() : obj instanceof Collection ? new a((Collection) obj).toString() : obj.getClass().isArray() ? new a(obj).toString() : c(obj.toString());
        }
        Number number = (Number) obj;
        if (number == null) {
            throw new JSONException((String) "Null pointer");
        }
        b((Object) number);
        String obj2 = number.toString();
        if (obj2.indexOf(46) > 0 && obj2.indexOf(101) < 0 && obj2.indexOf(69) < 0) {
            while (obj2.endsWith("0")) {
                obj2 = obj2.substring(0, obj2.length() - 1);
            }
            if (obj2.endsWith(".")) {
                obj2 = obj2.substring(0, obj2.length() - 1);
            }
        }
        return obj2;
    }

    private static void b(Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.isInfinite() || d.isNaN()) {
                throw new JSONException((String) "JSON does not allow non-finite numbers.");
            }
        } else if (obj instanceof Float) {
            Float f = (Float) obj;
            if (f.isInfinite() || f.isNaN()) {
                throw new JSONException((String) "JSON does not allow non-finite numbers.");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007f, code lost:
        r1.append(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0085, code lost:
        if (r4 == '<') goto L_0x0087;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String c(java.lang.String r8) {
        /*
            if (r8 == 0) goto L_0x0099
            int r0 = r8.length()
            if (r0 != 0) goto L_0x000a
            goto L_0x0099
        L_0x000a:
            int r0 = r8.length()
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            int r2 = r0 + 4
            r1.<init>(r2)
            r2 = 34
            r1.append(r2)
            r3 = 0
            r4 = 0
        L_0x001c:
            if (r3 >= r0) goto L_0x0091
            char r5 = r8.charAt(r3)
            r6 = 92
            if (r5 == r2) goto L_0x0087
            r7 = 47
            if (r5 == r7) goto L_0x0083
            if (r5 == r6) goto L_0x0087
            switch(r5) {
                case 8: goto L_0x007d;
                case 9: goto L_0x007a;
                case 10: goto L_0x0077;
                default: goto L_0x002f;
            }
        L_0x002f:
            switch(r5) {
                case 12: goto L_0x0074;
                case 13: goto L_0x0071;
                default: goto L_0x0032;
            }
        L_0x0032:
            r4 = 32
            if (r5 < r4) goto L_0x0046
            r4 = 128(0x80, float:1.794E-43)
            if (r5 < r4) goto L_0x003e
            r4 = 160(0xa0, float:2.24E-43)
            if (r5 < r4) goto L_0x0046
        L_0x003e:
            r4 = 8192(0x2000, float:1.14794E-41)
            if (r5 < r4) goto L_0x008a
            r4 = 8448(0x2100, float:1.1838E-41)
            if (r5 >= r4) goto L_0x008a
        L_0x0046:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "000"
            r4.<init>(r6)
            java.lang.String r6 = java.lang.Integer.toHexString(r5)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "\\u"
            r6.<init>(r7)
            int r7 = r4.length()
            int r7 = r7 + -4
            java.lang.String r4 = r4.substring(r7)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            goto L_0x007f
        L_0x0071:
            java.lang.String r4 = "\\r"
            goto L_0x007f
        L_0x0074:
            java.lang.String r4 = "\\f"
            goto L_0x007f
        L_0x0077:
            java.lang.String r4 = "\\n"
            goto L_0x007f
        L_0x007a:
            java.lang.String r4 = "\\t"
            goto L_0x007f
        L_0x007d:
            java.lang.String r4 = "\\b"
        L_0x007f:
            r1.append(r4)
            goto L_0x008d
        L_0x0083:
            r7 = 60
            if (r4 != r7) goto L_0x008a
        L_0x0087:
            r1.append(r6)
        L_0x008a:
            r1.append(r5)
        L_0x008d:
            int r3 = r3 + 1
            r4 = r5
            goto L_0x001c
        L_0x0091:
            r1.append(r2)
            java.lang.String r8 = r1.toString()
            return r8
        L_0x0099:
            java.lang.String r8 = "\"\""
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.rpc.json.b.c(java.lang.String):java.lang.String");
    }

    public final Object a(String str) {
        Object obj = str == null ? null : this.b.get(str);
        if (obj != null) {
            return obj;
        }
        StringBuilder sb = new StringBuilder("JSONObject[");
        sb.append(c(str));
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public final Iterator a() {
        return this.b.keySet().iterator();
    }

    public final boolean b(String str) {
        return this.b.containsKey(str);
    }

    public String toString() {
        try {
            Iterator a2 = a();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (a2.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = a2.next();
                stringBuffer.append(c(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(a(this.b.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
