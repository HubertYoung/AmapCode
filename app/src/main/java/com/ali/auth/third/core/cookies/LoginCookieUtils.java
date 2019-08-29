package com.ali.auth.third.core.cookies;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import org.json.JSONObject;

public class LoginCookieUtils {
    private static final int a = 6;
    private static final int b = 8;

    public static void expiresCookies(c cVar) {
        cVar.e = 1000;
    }

    public static String getHttpDomin(c cVar) {
        String str = cVar.a;
        if (!TextUtils.isEmpty(str) && str.startsWith(".")) {
            str = str.substring(1);
        }
        return AjxHttpLoader.DOMAIN_HTTP.concat(String.valueOf(str));
    }

    public static JSONObject getKeyValues(String str) {
        String[] split;
        JSONObject jSONObject = new JSONObject();
        if (TextUtils.isEmpty(str)) {
            return jSONObject;
        }
        try {
            String cookie = a.a().getCookie(".taobao.com");
            if (!TextUtils.isEmpty(cookie)) {
                for (String str2 : cookie.split(";")) {
                    String[] split2 = str2.split("=");
                    if (split2.length >= 2 && split2[0].contains(str)) {
                        if (split2.length == 2) {
                            jSONObject.put(split2[0].trim(), split2[1].trim());
                        } else {
                            jSONObject.put(split2[0].trim(), str2.substring(str2.indexOf("=") + 1));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static String getValue(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String cookie = a.a().getCookie(".taobao.com");
            if (!TextUtils.isEmpty(cookie)) {
                String[] split = cookie.split(";");
                int length = split.length;
                int i = 0;
                while (i < length) {
                    String str2 = split[i];
                    String[] split2 = str2.split("=");
                    if (split2.length < 2 || !str.equals(split2[0].trim())) {
                        i++;
                    } else {
                        return split2.length == 2 ? split2[1].trim() : str2.substring(str2.indexOf("=") + 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0058, code lost:
        if (r4 != -1) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bb, code lost:
        if (r1.charAt(r4) == r9) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e0, code lost:
        if (r1.charAt(r4) == r9) goto L_0x00bd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.ali.auth.third.core.cookies.c parseCookie(java.lang.String r18) {
        /*
            r1 = r18
            int r2 = r18.length()
            r3 = 0
            r4 = 0
        L_0x0008:
            r5 = 0
            if (r4 < 0) goto L_0x01d4
            if (r4 >= r2) goto L_0x01d4
            char r6 = r1.charAt(r4)
            r7 = 32
            if (r6 != r7) goto L_0x0018
            int r4 = r4 + 1
            goto L_0x0008
        L_0x0018:
            r6 = 59
            int r8 = r1.indexOf(r6, r4)
            r9 = 61
            int r10 = r1.indexOf(r9, r4)
            com.ali.auth.third.core.cookies.c r11 = new com.ali.auth.third.core.cookies.c
            r11.<init>()
            r12 = 34
            r13 = -1
            r14 = 1
            if (r8 == r13) goto L_0x0031
            if (r8 < r10) goto L_0x0033
        L_0x0031:
            if (r10 != r13) goto L_0x0040
        L_0x0033:
            if (r8 != r13) goto L_0x0036
            r8 = r2
        L_0x0036:
            java.lang.String r4 = r1.substring(r4, r8)
            r11.c = r4
            r11.d = r5
            r4 = r8
            goto L_0x0080
        L_0x0040:
            java.lang.String r8 = r1.substring(r4, r10)
            r11.c = r8
            int r8 = r2 + -1
            if (r10 >= r8) goto L_0x005a
            int r8 = r10 + 1
            char r8 = r1.charAt(r8)
            if (r8 != r12) goto L_0x005a
            int r4 = r10 + 2
            int r4 = r1.indexOf(r12, r4)
            if (r4 == r13) goto L_0x01d4
        L_0x005a:
            int r4 = r1.indexOf(r6, r4)
            if (r4 != r13) goto L_0x0061
            r4 = r2
        L_0x0061:
            int r8 = r4 - r10
            r15 = 4096(0x1000, float:5.74E-42)
            if (r8 <= r15) goto L_0x0071
            int r10 = r10 + r14
            int r8 = r10 + 4096
            java.lang.String r8 = r1.substring(r10, r8)
        L_0x006e:
            r11.d = r8
            goto L_0x0080
        L_0x0071:
            int r8 = r10 + 1
            if (r8 == r4) goto L_0x007d
            if (r4 >= r10) goto L_0x0078
            goto L_0x007d
        L_0x0078:
            java.lang.String r8 = r1.substring(r8, r4)
            goto L_0x006e
        L_0x007d:
            java.lang.String r8 = ""
            goto L_0x006e
        L_0x0080:
            if (r4 < 0) goto L_0x01d3
            if (r4 >= r2) goto L_0x01d3
            char r8 = r1.charAt(r4)
            if (r8 == r7) goto L_0x01ca
            char r8 = r1.charAt(r4)
            if (r8 != r6) goto L_0x0092
            goto L_0x01ca
        L_0x0092:
            char r8 = r1.charAt(r4)
            r10 = 44
            if (r8 == r10) goto L_0x01d3
            int r8 = r2 - r4
            int r15 = a
            if (r8 < r15) goto L_0x00c2
            int r15 = a
            int r15 = r15 + r4
            java.lang.String r15 = r1.substring(r4, r15)
            java.lang.String r7 = "secure"
            boolean r7 = r15.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x00c2
            int r7 = a
            int r4 = r4 + r7
            r11.f = r14
            if (r4 == r2) goto L_0x01d3
            char r7 = r1.charAt(r4)
            if (r7 != r9) goto L_0x00bf
        L_0x00bd:
            int r4 = r4 + 1
        L_0x00bf:
            r7 = 32
            goto L_0x0080
        L_0x00c2:
            int r7 = b
            if (r8 < r7) goto L_0x00e3
            int r7 = b
            int r7 = r7 + r4
            java.lang.String r7 = r1.substring(r4, r7)
            java.lang.String r8 = "httponly"
            boolean r7 = r7.equalsIgnoreCase(r8)
            if (r7 == 0) goto L_0x00e3
            int r7 = b
            int r4 = r4 + r7
            r11.g = r14
            if (r4 == r2) goto L_0x01d3
            char r7 = r1.charAt(r4)
            if (r7 != r9) goto L_0x00bf
            goto L_0x00bd
        L_0x00e3:
            int r7 = r1.indexOf(r9, r4)
            if (r7 <= 0) goto L_0x01c7
            java.lang.String r8 = r1.substring(r4, r7)
            java.lang.String r8 = r8.toLowerCase()
            java.lang.String r15 = "expires"
            boolean r15 = r8.equals(r15)
            if (r15 == 0) goto L_0x0107
            int r15 = r1.indexOf(r10, r7)
            if (r15 == r13) goto L_0x0107
            int r9 = r15 - r7
            r5 = 10
            if (r9 > r5) goto L_0x0107
            int r4 = r15 + 1
        L_0x0107:
            int r5 = r1.indexOf(r6, r4)
            int r4 = r1.indexOf(r10, r4)
            if (r5 != r13) goto L_0x0115
            if (r4 != r13) goto L_0x0115
            r4 = r2
            goto L_0x0120
        L_0x0115:
            if (r5 != r13) goto L_0x0118
            goto L_0x0120
        L_0x0118:
            if (r4 != r13) goto L_0x011c
            r4 = r5
            goto L_0x0120
        L_0x011c:
            int r4 = java.lang.Math.min(r5, r4)
        L_0x0120:
            int r7 = r7 + 1
            java.lang.String r5 = r1.substring(r7, r4)
            int r7 = r5.length()
            r9 = 2
            if (r7 <= r9) goto L_0x013d
            char r7 = r5.charAt(r3)
            if (r7 != r12) goto L_0x013d
            int r7 = r5.indexOf(r12, r14)
            if (r7 <= 0) goto L_0x013d
            java.lang.String r5 = r5.substring(r14, r7)
        L_0x013d:
            java.lang.String r7 = "expires"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x0160
            long r7 = com.ali.auth.third.core.cookies.b.a(r5)     // Catch:{ IllegalArgumentException -> 0x014d }
            r11.e = r7     // Catch:{ IllegalArgumentException -> 0x014d }
            goto L_0x01cc
        L_0x014d:
            r0 = move-exception
            r7 = r0
            java.lang.String r8 = "login.LoginCookieUtils"
            java.lang.String r9 = "illegal format for expires: "
        L_0x0153:
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r9.concat(r5)
            com.ali.auth.third.core.trace.SDKLogger.e(r8, r5, r7)
            goto L_0x01cc
        L_0x0160:
            java.lang.String r7 = "max-age"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x0181
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ NumberFormatException -> 0x017a }
            r9 = 1000(0x3e8, double:4.94E-321)
            long r16 = java.lang.Long.parseLong(r5)     // Catch:{ NumberFormatException -> 0x017a }
            long r16 = r16 * r9
            r9 = 0
            long r7 = r7 + r16
            r11.e = r7     // Catch:{ NumberFormatException -> 0x017a }
            goto L_0x01cc
        L_0x017a:
            r0 = move-exception
            r7 = r0
            java.lang.String r8 = "login.LoginCookieUtils"
            java.lang.String r9 = "illegal format for max-age: "
            goto L_0x0153
        L_0x0181:
            java.lang.String r7 = "path"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x0192
            int r7 = r5.length()
            if (r7 <= 0) goto L_0x01cc
            r11.b = r5
            goto L_0x01cc
        L_0x0192:
            java.lang.String r7 = "domain"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x01cc
            r7 = 46
            int r8 = r5.lastIndexOf(r7)
            if (r8 != 0) goto L_0x01a6
            r9 = 0
            r11.a = r9
            goto L_0x01cc
        L_0x01a6:
            int r8 = r8 + 1
            java.lang.String r8 = r5.substring(r8)     // Catch:{ NumberFormatException -> 0x01b0 }
            java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x01b0 }
            goto L_0x01cc
        L_0x01b0:
            java.lang.String r5 = r5.toLowerCase()
            char r8 = r5.charAt(r3)
            if (r8 == r7) goto L_0x01c4
            java.lang.String r7 = "."
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r7.concat(r5)
        L_0x01c4:
            r11.a = r5
            goto L_0x01cc
        L_0x01c7:
            r4 = r2
            goto L_0x00bf
        L_0x01ca:
            int r4 = r4 + 1
        L_0x01cc:
            r5 = 0
            r7 = 32
            r9 = 61
            goto L_0x0080
        L_0x01d3:
            return r11
        L_0x01d4:
            r1 = r5
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.cookies.LoginCookieUtils.parseCookie(java.lang.String):com.ali.auth.third.core.cookies.c");
    }
}
