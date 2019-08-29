package org.xidea.el.json;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.xidea.el.ExpressionSyntaxException;

public class JSONTokenizer {
    protected String a;
    protected int b;
    protected final int c;
    protected boolean d = false;

    public JSONTokenizer(String str, boolean z) {
        this.a = str.trim();
        if (this.a.startsWith("﻿")) {
            this.a = this.a.substring(1);
        }
        this.c = this.a.length();
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public final Object a() {
        g();
        char charAt = this.a.charAt(this.b);
        if (charAt >= 65281 && charAt <= 65374) {
            charAt = (char) (charAt - 65248);
        }
        if (charAt != '\"') {
            if (charAt != '\'') {
                if (charAt == '[') {
                    ArrayList arrayList = new ArrayList();
                    this.b++;
                    g();
                    if (this.a.charAt(this.b) == ']') {
                        this.b++;
                        return arrayList;
                    }
                    char c2 = ',';
                    while (c2 != ']') {
                        if (c2 == ',') {
                            if (!this.d) {
                                g();
                                if (this.a.charAt(this.b) == ']') {
                                    this.b++;
                                    return arrayList;
                                } else if (this.a.charAt(this.b) == ',') {
                                    this.b++;
                                    g();
                                }
                            }
                            arrayList.add(a());
                            g();
                            String str = this.a;
                            int i = this.b;
                            this.b = i + 1;
                            c2 = str.charAt(i);
                        } else {
                            StringBuilder sb = new StringBuilder("无效数组语法:");
                            sb.append(this.a.substring(Math.max(0, c2 - 2), Math.min(c2 + 10, this.a.length())));
                            throw a(sb.toString());
                        }
                    }
                    return arrayList;
                } else if (charAt == '{') {
                    return b();
                } else {
                    if (charAt >= '0' && charAt <= '9') {
                        return d();
                    }
                    if (charAt != '-') {
                        String e = e();
                        if ("true".equals(e)) {
                            return Boolean.TRUE;
                        }
                        if ("false".equals(e)) {
                            return Boolean.FALSE;
                        }
                        if ("null".equals(e)) {
                            return null;
                        }
                        if (!this.d) {
                            if ("NaN".equals(e)) {
                                return Double.valueOf(Double.NaN);
                            }
                            if ("Infinit".equals(e)) {
                                return Double.valueOf(Double.POSITIVE_INFINITY);
                            }
                        }
                        throw a((String) "");
                    } else if (this.d) {
                        return d();
                    } else {
                        int i2 = this.b;
                        g();
                        char charAt2 = this.a.charAt(this.b);
                        if (charAt2 >= 0 || charAt2 <= 9) {
                            this.b = i2;
                            return d();
                        }
                        String e2 = e();
                        if ("Infinity".equals(e2)) {
                            return Double.valueOf(Double.NEGATIVE_INFINITY);
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(e2);
                        sb2.append(" is  not a valid number!!");
                        throw a(sb2.toString());
                    }
                }
            } else if (this.d) {
                throw a((String) "JSON标准 字符串应该是双引号\"...\")");
            }
        }
        return f();
    }

    private ExpressionSyntaxException a(String str) {
        StringBuilder sb = new StringBuilder("语法错误:");
        sb.append(str);
        sb.append("\n");
        sb.append(this.a.substring(Math.max(0, this.b - 2), Math.min(this.b + 10, this.a.length())));
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.b);
        return new ExpressionSyntaxException(sb.toString());
    }

    private Map<String, Object> b() {
        String str;
        this.b++;
        g();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (this.a.charAt(this.b) == '}') {
            this.b++;
            return linkedHashMap;
        }
        while (true) {
            char charAt = this.a.charAt(this.b);
            if (charAt == '\"') {
                str = f();
            } else if (this.d) {
                throw a((String) "JSON 标准Object Key 必须为标准JSON 字符串,如:{\"key\":\"value\"}");
            } else if (charAt == '\'') {
                str = f();
            } else if (Character.isJavaIdentifierPart(charAt)) {
                str = e();
            } else if (charAt == '}') {
                this.b++;
                return linkedHashMap;
            } else if (charAt == ',') {
                this.b++;
                g();
            } else {
                throw a((String) "无效对象语法");
            }
            g();
            String str2 = this.a;
            int i = this.b;
            this.b = i + 1;
            if (str2.charAt(i) != ':') {
                throw a((String) "无效对象语法");
            }
            Object a2 = a();
            g();
            String str3 = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            char charAt2 = str3.charAt(i2);
            if (charAt2 == ',') {
                linkedHashMap.put(str, a2);
                g();
            } else if (charAt2 == '}') {
                linkedHashMap.put(str, a2);
                return linkedHashMap;
            } else {
                throw a((String) "无效对象语法");
            }
        }
    }

    private void c() {
        while (this.b < this.c) {
            String str = this.a;
            int i = this.b;
            this.b = i + 1;
            char charAt = str.charAt(i);
            if (charAt >= '0') {
                if (charAt > '9') {
                }
            }
            this.b--;
            return;
        }
    }

    private Number a(int i) {
        char charAt = this.a.charAt(this.b);
        boolean z = false;
        if (charAt == '.') {
            this.b++;
            int i2 = this.b;
            c();
            if (this.b == i2) {
                this.b--;
                return Long.valueOf(Long.parseLong(this.a.substring(i, this.b)));
            }
            charAt = this.b < this.c ? this.a.charAt(this.b) : 0;
            z = true;
        }
        if (charAt == 'E' || charAt == 'e') {
            this.b++;
            String str = this.a;
            int i3 = this.b;
            this.b = i3 + 1;
            char charAt2 = str.charAt(i3);
            if (!(charAt2 == '-' || charAt2 == '+')) {
                this.b--;
            }
            c();
            z = true;
        }
        String substring = this.a.substring(i, this.b);
        if (z) {
            return Double.valueOf(Double.parseDouble(substring));
        }
        return Long.valueOf(Long.parseLong(substring));
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Number d() {
        /*
            r11 = this;
            int r0 = r11.b
            java.lang.String r1 = r11.a
            int r2 = r11.b
            int r3 = r2 + 1
            r11.b = r3
            char r1 = r1.charAt(r2)
            r2 = 0
            r3 = 1
            r4 = 43
            if (r1 != r4) goto L_0x0022
            java.lang.String r1 = r11.a
            int r4 = r11.b
            int r5 = r4 + 1
            r11.b = r5
            char r1 = r1.charAt(r4)
        L_0x0020:
            r4 = 0
            goto L_0x0033
        L_0x0022:
            r4 = 45
            if (r1 != r4) goto L_0x0020
            java.lang.String r1 = r11.a
            int r4 = r11.b
            int r5 = r4 + 1
            r11.b = r5
            char r1 = r1.charAt(r4)
            r4 = 1
        L_0x0033:
            r5 = 57
            r6 = 46
            r7 = 48
            if (r1 != r7) goto L_0x0109
            int r0 = r11.b
            int r1 = r11.c
            if (r0 >= r1) goto L_0x0104
            java.lang.String r0 = r11.a
            int r1 = r11.b
            int r8 = r1 + 1
            r11.b = r8
            char r0 = r0.charAt(r1)
            r1 = 120(0x78, float:1.68E-43)
            if (r0 == r1) goto L_0x00ad
            r1 = 88
            if (r0 != r1) goto L_0x0056
            goto L_0x00ad
        L_0x0056:
            if (r0 <= r7) goto L_0x0098
            r1 = 55
            if (r0 > r1) goto L_0x0098
            boolean r0 = r11.d
            if (r0 == 0) goto L_0x0067
            java.lang.String r0 = "JSON未定义8进制数字"
            org.xidea.el.ExpressionSyntaxException r0 = r11.a(r0)
            throw r0
        L_0x0067:
            int r0 = r11.b
            int r0 = r0 - r3
            r11.b = r0
        L_0x006c:
            int r0 = r11.b
            int r1 = r11.c
            if (r0 >= r1) goto L_0x0090
            java.lang.String r0 = r11.a
            int r1 = r11.b
            int r5 = r1 + 1
            r11.b = r5
            char r0 = r0.charAt(r1)
            if (r0 < r7) goto L_0x008b
            r1 = 56
            if (r0 >= r1) goto L_0x008b
            int r1 = r2 << 3
            int r0 = r0 + -48
            int r2 = r1 + r0
            goto L_0x006c
        L_0x008b:
            int r0 = r11.b
            int r0 = r0 - r3
            r11.b = r0
        L_0x0090:
            if (r4 == 0) goto L_0x0093
            int r2 = -r2
        L_0x0093:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)
            return r0
        L_0x0098:
            if (r0 != r6) goto L_0x00a7
            int r0 = r11.b
            int r0 = r0 - r3
            r11.b = r0
            int r0 = r11.b
            int r0 = r0 - r3
            java.lang.Number r0 = r11.a(r0)
            return r0
        L_0x00a7:
            int r0 = r11.b
            int r0 = r0 - r3
            r11.b = r0
            goto L_0x0104
        L_0x00ad:
            boolean r0 = r11.d
            if (r0 == 0) goto L_0x00b8
            java.lang.String r0 = "JSON未定义16进制数字"
            org.xidea.el.ExpressionSyntaxException r0 = r11.a(r0)
            throw r0
        L_0x00b8:
            r0 = 0
        L_0x00ba:
            int r2 = r11.b
            int r6 = r11.c
            if (r2 >= r6) goto L_0x00fc
            java.lang.String r2 = r11.a
            int r6 = r11.b
            int r8 = r6 + 1
            r11.b = r8
            char r2 = r2.charAt(r6)
            r6 = 4
            if (r2 < r7) goto L_0x00d7
            if (r2 > r5) goto L_0x00d7
            long r0 = r0 << r6
            int r2 = r2 + -48
            long r8 = (long) r2
            long r0 = r0 + r8
            goto L_0x00ba
        L_0x00d7:
            r8 = 65
            if (r2 < r8) goto L_0x00e7
            r8 = 70
            if (r2 > r8) goto L_0x00e7
            long r0 = r0 << r6
            int r2 = r2 + -65
            int r2 = r2 + 10
            long r8 = (long) r2
            long r0 = r0 + r8
            goto L_0x00ba
        L_0x00e7:
            r8 = 97
            if (r2 < r8) goto L_0x00f7
            r8 = 102(0x66, float:1.43E-43)
            if (r2 > r8) goto L_0x00f7
            long r0 = r0 << r6
            int r2 = r2 + -97
            int r2 = r2 + 10
            long r8 = (long) r2
            long r0 = r0 + r8
            goto L_0x00ba
        L_0x00f7:
            int r2 = r11.b
            int r2 = r2 - r3
            r11.b = r2
        L_0x00fc:
            if (r4 == 0) goto L_0x00ff
            long r0 = -r0
        L_0x00ff:
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            return r0
        L_0x0104:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)
            return r0
        L_0x0109:
            int r1 = r1 - r7
            long r1 = (long) r1
        L_0x010b:
            int r8 = r11.b
            int r9 = r11.c
            if (r8 >= r9) goto L_0x0141
            java.lang.String r8 = r11.a
            int r9 = r11.b
            int r10 = r9 + 1
            r11.b = r10
            char r8 = r8.charAt(r9)
            if (r8 < r7) goto L_0x012a
            if (r8 > r5) goto L_0x012a
            r9 = 10
            long r1 = r1 * r9
            int r8 = r8 + -48
            long r8 = (long) r8
            long r1 = r1 + r8
            goto L_0x010b
        L_0x012a:
            if (r8 == r6) goto L_0x0137
            r5 = 69
            if (r8 != r5) goto L_0x0131
            goto L_0x0137
        L_0x0131:
            int r0 = r11.b
            int r0 = r0 - r3
            r11.b = r0
            goto L_0x0141
        L_0x0137:
            int r1 = r11.b
            int r1 = r1 - r3
            r11.b = r1
            java.lang.Number r0 = r11.a(r0)
            return r0
        L_0x0141:
            if (r4 == 0) goto L_0x0144
            long r1 = -r1
        L_0x0144:
            java.lang.Long r0 = java.lang.Long.valueOf(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xidea.el.json.JSONTokenizer.d():java.lang.Number");
    }

    private String e() {
        int i = this.b;
        int i2 = i + 1;
        char charAt = this.a.charAt(i);
        if (Character.isJavaIdentifierPart(charAt)) {
            while (i2 < this.c && Character.isJavaIdentifierPart(this.a.charAt(i2))) {
                i2++;
            }
            String str = this.a;
            int i3 = this.b;
            this.b = i2;
            return str.substring(i3, i2);
        }
        throw a("无效id:".concat(String.valueOf(charAt)));
    }

    private String f() {
        String str = this.a;
        int i = this.b;
        this.b = i + 1;
        char charAt = str.charAt(i);
        StringBuilder sb = new StringBuilder();
        while (this.b < this.c) {
            String str2 = this.a;
            int i2 = this.b;
            this.b = i2 + 1;
            char charAt2 = str2.charAt(i2);
            if (charAt2 != 10 && charAt2 != 13) {
                if (charAt2 != '\"' && charAt2 != '\'') {
                    if (charAt2 == '\\') {
                        String str3 = this.a;
                        int i3 = this.b;
                        this.b = i3 + 1;
                        char charAt3 = str3.charAt(i3);
                        switch (charAt3) {
                            case ' ':
                                sb.append(' ');
                                break;
                            case '\"':
                                sb.append('\"');
                                break;
                            case '\'':
                                sb.append('\'');
                                break;
                            case '/':
                            case '\\':
                                sb.append(charAt3);
                                break;
                            case 'b':
                                sb.append(8);
                                break;
                            case 'f':
                                sb.append(12);
                                break;
                            case 'n':
                                sb.append(10);
                                break;
                            case 'r':
                                sb.append(13);
                                break;
                            case 't':
                                sb.append(9);
                                break;
                            case 'u':
                                sb.append((char) Integer.parseInt(this.a.substring(this.b, this.b + 4), 16));
                                this.b += 4;
                                break;
                            case 'v':
                                sb.append(11);
                                break;
                            case MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ /*120*/:
                                sb.append((char) Integer.parseInt(this.a.substring(this.b, this.b + 2), 16));
                                this.b += 2;
                                break;
                            default:
                                if (!this.d) {
                                    sb.append(charAt2);
                                    sb.append(charAt3);
                                    break;
                                } else {
                                    throw a((String) "发现JSON 标准未定义转义字符");
                                }
                        }
                    } else {
                        sb.append(charAt2);
                    }
                } else if (charAt2 == charAt) {
                    return sb.toString();
                } else {
                    sb.append(charAt2);
                }
            } else {
                throw a((String) "JSON 标准字符串不能换行");
            }
        }
        throw a((String) "未结束字符串");
    }

    private void g() {
        while (true) {
            if (this.b < this.c && Character.isWhitespace(this.a.charAt(this.b))) {
                this.b++;
            } else if (this.b < this.c && this.a.charAt(this.b) == '/') {
                if (this.d) {
                    throw a((String) "JSON 标准未定义注释");
                }
                this.b++;
                String str = this.a;
                int i = this.b;
                this.b = i + 1;
                char charAt = str.charAt(i);
                if (charAt == '/') {
                    int indexOf = this.a.indexOf(10, this.b);
                    int indexOf2 = this.a.indexOf(13, this.b);
                    int min = Math.min(indexOf, indexOf2);
                    if (min < 0) {
                        min = Math.max(indexOf, indexOf2);
                    }
                    if (min > 0) {
                        this.b = min;
                    } else {
                        this.b = this.c;
                    }
                } else if (charAt == '*') {
                    int i2 = this.b;
                    while (true) {
                        i2 = this.a.indexOf(47, i2 + 1);
                        if (i2 <= 0) {
                            throw a((String) "未結束注釋");
                        } else if (this.a.charAt(i2 - 1) == '*') {
                            this.b = i2 + 1;
                            break;
                        }
                    }
                } else {
                    continue;
                }
            } else {
                return;
            }
        }
    }
}
