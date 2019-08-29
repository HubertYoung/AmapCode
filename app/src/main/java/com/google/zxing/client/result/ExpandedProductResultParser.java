package com.google.zxing.client.result;

public final class ExpandedProductResultParser extends ResultParser {
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0209, code lost:
        continue;
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007a, code lost:
        if (r1.equals("3933") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0084, code lost:
        if (r1.equals("3932") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008e, code lost:
        if (r1.equals("3931") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0098, code lost:
        if (r1.equals("3930") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a1, code lost:
        if (r2.length() >= 4) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a4, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a5, code lost:
        r15 = r2.substring(3);
        r17 = r2.substring(0, 3);
        r16 = r1.substring(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bb, code lost:
        if (r1.equals("3923") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c5, code lost:
        if (r1.equals("3922") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cf, code lost:
        if (r1.equals("3921") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d9, code lost:
        if (r1.equals("3920") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00dd, code lost:
        r16 = r1.substring(3);
        r15 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ea, code lost:
        if (r1.equals("3209") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f4, code lost:
        if (r1.equals("3208") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fe, code lost:
        if (r1.equals("3207") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0108, code lost:
        if (r1.equals("3206") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0112, code lost:
        if (r1.equals("3205") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x011c, code lost:
        if (r1.equals("3204") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0126, code lost:
        if (r1.equals("3203") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0130, code lost:
        if (r1.equals("3202") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x013a, code lost:
        if (r1.equals("3201") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0144, code lost:
        if (r1.equals("3200") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0148, code lost:
        r13 = com.google.zxing.client.result.ExpandedProductParsedResult.POUND;
        r14 = r1.substring(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0156, code lost:
        if (r1.equals("3109") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0160, code lost:
        if (r1.equals("3108") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x016a, code lost:
        if (r1.equals("3107") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0174, code lost:
        if (r1.equals("3106") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x017e, code lost:
        if (r1.equals("3105") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0188, code lost:
        if (r1.equals("3104") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0192, code lost:
        if (r1.equals("3103") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x019c, code lost:
        if (r1.equals("3102") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01a6, code lost:
        if (r1.equals("3101") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01b0, code lost:
        if (r1.equals("3100") == false) goto L_0x0206;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b4, code lost:
        r13 = com.google.zxing.client.result.ExpandedProductParsedResult.KILOGRAM;
        r14 = r1.substring(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ba, code lost:
        r12 = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.zxing.client.result.ExpandedProductParsedResult parse(com.google.zxing.Result r23) {
        /*
            r22 = this;
            com.google.zxing.BarcodeFormat r0 = r23.getBarcodeFormat()
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.RSS_EXPANDED
            r2 = 0
            if (r0 == r1) goto L_0x000a
            return r2
        L_0x000a:
            java.lang.String r4 = getMassagedText(r23)
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r5 = r2
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            r13 = r12
            r14 = r13
            r15 = r14
            r16 = r15
            r17 = r16
            r3 = 0
        L_0x0023:
            int r1 = r4.length()
            if (r3 < r1) goto L_0x0032
            com.google.zxing.client.result.ExpandedProductParsedResult r1 = new com.google.zxing.client.result.ExpandedProductParsedResult
            r3 = r1
            r18 = r0
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            return r1
        L_0x0032:
            java.lang.String r1 = findAIvalue(r3, r4)
            if (r1 != 0) goto L_0x0039
            return r2
        L_0x0039:
            int r18 = r1.length()
            int r18 = r18 + 2
            int r3 = r3 + r18
            java.lang.String r2 = findValue(r3, r4)
            int r18 = r2.length()
            int r3 = r3 + r18
            r19 = r3
            int r3 = r1.hashCode()
            r20 = r4
            r4 = 1570(0x622, float:2.2E-42)
            if (r3 == r4) goto L_0x01fe
            r4 = 1572(0x624, float:2.203E-42)
            if (r3 == r4) goto L_0x01f3
            r4 = 1574(0x626, float:2.206E-42)
            if (r3 == r4) goto L_0x01e8
            switch(r3) {
                case 1536: goto L_0x01dd;
                case 1537: goto L_0x01d2;
                default: goto L_0x0062;
            }
        L_0x0062:
            switch(r3) {
                case 1567: goto L_0x01c7;
                case 1568: goto L_0x01bc;
                default: goto L_0x0065;
            }
        L_0x0065:
            r4 = 3
            switch(r3) {
                case 1567966: goto L_0x01aa;
                case 1567967: goto L_0x01a0;
                case 1567968: goto L_0x0196;
                case 1567969: goto L_0x018c;
                case 1567970: goto L_0x0182;
                case 1567971: goto L_0x0178;
                case 1567972: goto L_0x016e;
                case 1567973: goto L_0x0164;
                case 1567974: goto L_0x015a;
                case 1567975: goto L_0x0150;
                default: goto L_0x0069;
            }
        L_0x0069:
            switch(r3) {
                case 1568927: goto L_0x013e;
                case 1568928: goto L_0x0134;
                case 1568929: goto L_0x012a;
                case 1568930: goto L_0x0120;
                case 1568931: goto L_0x0116;
                case 1568932: goto L_0x010c;
                case 1568933: goto L_0x0102;
                case 1568934: goto L_0x00f8;
                case 1568935: goto L_0x00ee;
                case 1568936: goto L_0x00e4;
                default: goto L_0x006c;
            }
        L_0x006c:
            switch(r3) {
                case 1575716: goto L_0x00d3;
                case 1575717: goto L_0x00c9;
                case 1575718: goto L_0x00bf;
                case 1575719: goto L_0x00b5;
                default: goto L_0x006f;
            }
        L_0x006f:
            switch(r3) {
                case 1575747: goto L_0x0092;
                case 1575748: goto L_0x0088;
                case 1575749: goto L_0x007e;
                case 1575750: goto L_0x0074;
                default: goto L_0x0072;
            }
        L_0x0072:
            goto L_0x0206
        L_0x0074:
            java.lang.String r3 = "3933"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x009c
            goto L_0x0206
        L_0x007e:
            java.lang.String r3 = "3932"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x009c
            goto L_0x0206
        L_0x0088:
            java.lang.String r3 = "3931"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x009c
            goto L_0x0206
        L_0x0092:
            java.lang.String r3 = "3930"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x009c
            goto L_0x0206
        L_0x009c:
            int r3 = r2.length()
            r15 = 4
            if (r3 >= r15) goto L_0x00a5
            r3 = 0
            return r3
        L_0x00a5:
            r3 = 0
            java.lang.String r15 = r2.substring(r4)
            r3 = 0
            java.lang.String r17 = r2.substring(r3, r4)
            java.lang.String r16 = r1.substring(r4)
            goto L_0x0209
        L_0x00b5:
            java.lang.String r3 = "3923"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x00dd
            goto L_0x0206
        L_0x00bf:
            java.lang.String r3 = "3922"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x00dd
            goto L_0x0206
        L_0x00c9:
            java.lang.String r3 = "3921"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x00dd
            goto L_0x0206
        L_0x00d3:
            java.lang.String r3 = "3920"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x00dd
            goto L_0x0206
        L_0x00dd:
            java.lang.String r16 = r1.substring(r4)
            r15 = r2
            goto L_0x0209
        L_0x00e4:
            java.lang.String r3 = "3209"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x00ee:
            java.lang.String r3 = "3208"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x00f8:
            java.lang.String r3 = "3207"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x0102:
            java.lang.String r3 = "3206"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x010c:
            java.lang.String r3 = "3205"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x0116:
            java.lang.String r3 = "3204"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x0120:
            java.lang.String r3 = "3203"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x012a:
            java.lang.String r3 = "3202"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x0134:
            java.lang.String r3 = "3201"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x013e:
            java.lang.String r3 = "3200"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0148
            goto L_0x0206
        L_0x0148:
            java.lang.String r13 = "LB"
            java.lang.String r14 = r1.substring(r4)
            goto L_0x01ba
        L_0x0150:
            java.lang.String r3 = "3109"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x015a:
            java.lang.String r3 = "3108"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x0164:
            java.lang.String r3 = "3107"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x016e:
            java.lang.String r3 = "3106"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x0178:
            java.lang.String r3 = "3105"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x0182:
            java.lang.String r3 = "3104"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x018c:
            java.lang.String r3 = "3103"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x0196:
            java.lang.String r3 = "3102"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x01a0:
            java.lang.String r3 = "3101"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x01aa:
            java.lang.String r3 = "3100"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01b4
            goto L_0x0206
        L_0x01b4:
            java.lang.String r13 = "KG"
            java.lang.String r14 = r1.substring(r4)
        L_0x01ba:
            r12 = r2
            goto L_0x0209
        L_0x01bc:
            java.lang.String r3 = "11"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01c5
            goto L_0x0206
        L_0x01c5:
            r8 = r2
            goto L_0x0209
        L_0x01c7:
            java.lang.String r3 = "10"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01d0
            goto L_0x0206
        L_0x01d0:
            r7 = r2
            goto L_0x0209
        L_0x01d2:
            java.lang.String r3 = "01"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01db
            goto L_0x0206
        L_0x01db:
            r5 = r2
            goto L_0x0209
        L_0x01dd:
            java.lang.String r3 = "00"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01e6
            goto L_0x0206
        L_0x01e6:
            r6 = r2
            goto L_0x0209
        L_0x01e8:
            java.lang.String r3 = "17"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01f1
            goto L_0x0206
        L_0x01f1:
            r11 = r2
            goto L_0x0209
        L_0x01f3:
            java.lang.String r3 = "15"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01fc
            goto L_0x0206
        L_0x01fc:
            r10 = r2
            goto L_0x0209
        L_0x01fe:
            java.lang.String r3 = "13"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0210
        L_0x0206:
            r0.put(r1, r2)
        L_0x0209:
            r3 = r19
            r4 = r20
            r2 = 0
            goto L_0x0023
        L_0x0210:
            r9 = r2
            goto L_0x0209
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.ExpandedProductResultParser.parse(com.google.zxing.Result):com.google.zxing.client.result.ExpandedProductParsedResult");
    }

    private static String findAIvalue(int i, String str) {
        if (str.charAt(i) != '(') {
            return null;
        }
        String substring = str.substring(i + 1);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == ')') {
                return sb.toString();
            }
            if (charAt < '0' || charAt > '9') {
                return null;
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private static String findValue(int i, String str) {
        StringBuilder sb = new StringBuilder();
        String substring = str.substring(i);
        for (int i2 = 0; i2 < substring.length(); i2++) {
            char charAt = substring.charAt(i2);
            if (charAt == '(') {
                if (findAIvalue(i2, substring) != null) {
                    break;
                }
                sb.append('(');
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
