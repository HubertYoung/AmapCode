package com.google.gson.b.a.a;

import java.util.TimeZone;

/* compiled from: ISO8601Utils */
public class a {
    private static final TimeZone a = TimeZone.getTimeZone("UTC");

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c7 A[Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00cf A[Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Date a(java.lang.String r18, java.text.ParsePosition r19) throws java.text.ParseException {
        /*
            r1 = r18
            r2 = r19
            int r3 = r19.getIndex()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r4 = r3 + 4
            int r3 = a(r1, r3, r4)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r5 = 45
            boolean r6 = a(r1, r4, r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r6 == 0) goto L_0x0018
            int r4 = r4 + 1
        L_0x0018:
            int r6 = r4 + 2
            int r4 = a(r1, r4, r6)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            boolean r7 = a(r1, r6, r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r7 == 0) goto L_0x0026
            int r6 = r6 + 1
        L_0x0026:
            int r7 = r6 + 2
            int r6 = a(r1, r6, r7)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r8 = 84
            boolean r8 = a(r1, r7, r8)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r9 = 1
            if (r8 != 0) goto L_0x0049
            int r10 = r18.length()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r10 > r7) goto L_0x0049
            java.util.GregorianCalendar r5 = new java.util.GregorianCalendar     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r4 = r4 - r9
            r5.<init>(r3, r4, r6)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r2.setIndex(r7)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.util.Date r3 = r5.getTime()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            return r3
        L_0x0049:
            r10 = 43
            r11 = 90
            if (r8 == 0) goto L_0x00bc
            int r7 = r7 + 1
            int r8 = r7 + 2
            int r7 = a(r1, r7, r8)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r13 = 58
            boolean r14 = a(r1, r8, r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r14 == 0) goto L_0x0061
            int r8 = r8 + 1
        L_0x0061:
            int r14 = r8 + 2
            int r8 = a(r1, r8, r14)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            boolean r13 = a(r1, r14, r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r13 == 0) goto L_0x006f
            int r14 = r14 + 1
        L_0x006f:
            int r13 = r18.length()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r13 <= r14) goto L_0x00ba
            char r13 = r1.charAt(r14)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r13 == r11) goto L_0x00ba
            if (r13 == r10) goto L_0x00ba
            if (r13 == r5) goto L_0x00ba
            int r13 = r14 + 2
            int r14 = a(r1, r14, r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r15 = 59
            if (r14 <= r15) goto L_0x008f
            r15 = 63
            if (r14 >= r15) goto L_0x008f
            r14 = 59
        L_0x008f:
            r15 = 46
            boolean r15 = a(r1, r13, r15)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r15 == 0) goto L_0x00b8
            int r13 = r13 + 1
            int r15 = r13 + 1
            int r15 = a(r1, r15)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r12 = r13 + 3
            int r12 = java.lang.Math.min(r15, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r16 = a(r1, r13, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r12 = r12 - r13
            switch(r12) {
                case 1: goto L_0x00b3;
                case 2: goto L_0x00b0;
                default: goto L_0x00ad;
            }     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x00ad:
            r12 = r16
            goto L_0x00b6
        L_0x00b0:
            int r16 = r16 * 10
            goto L_0x00ad
        L_0x00b3:
            int r16 = r16 * 100
            goto L_0x00ad
        L_0x00b6:
            r13 = r15
            goto L_0x00c1
        L_0x00b8:
            r12 = 0
            goto L_0x00c1
        L_0x00ba:
            r13 = r14
            goto L_0x00bf
        L_0x00bc:
            r13 = r7
            r7 = 0
            r8 = 0
        L_0x00bf:
            r12 = 0
            r14 = 0
        L_0x00c1:
            int r15 = r18.length()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r15 > r13) goto L_0x00cf
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r4 = "No time zone indicator"
            r3.<init>(r4)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            throw r3     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x00cf:
            char r15 = r1.charAt(r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r5 = 5
            if (r15 != r11) goto L_0x00db
            java.util.TimeZone r10 = a     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r13 = r13 + r9
            goto L_0x0177
        L_0x00db:
            if (r15 == r10) goto L_0x00fb
            r10 = 45
            if (r15 != r10) goto L_0x00e2
            goto L_0x00fb
        L_0x00e2:
            java.lang.IndexOutOfBoundsException r3 = new java.lang.IndexOutOfBoundsException     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r5 = "Invalid time zone indicator '"
            r4.<init>(r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r4.append(r15)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r5 = "'"
            r4.append(r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r4 = r4.toString()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3.<init>(r4)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            throw r3     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x00fb:
            java.lang.String r10 = r1.substring(r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r11 = r10.length()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r11 < r5) goto L_0x0106
            goto L_0x0117
        L_0x0106:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r11.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r11.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r10 = "00"
            r11.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r10 = r11.toString()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x0117:
            int r11 = r10.length()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r13 = r13 + r11
            java.lang.String r11 = "+0000"
            boolean r11 = r11.equals(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r11 != 0) goto L_0x0175
            java.lang.String r11 = "+00:00"
            boolean r11 = r11.equals(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r11 == 0) goto L_0x012d
            goto L_0x0175
        L_0x012d:
            java.lang.String r11 = "GMT"
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r10 = r11.concat(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.util.TimeZone r11 = java.util.TimeZone.getTimeZone(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r15 = r11.getID()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            boolean r16 = r15.equals(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r16 != 0) goto L_0x0173
            java.lang.String r5 = ":"
            java.lang.String r9 = ""
            java.lang.String r5 = r15.replace(r5, r9)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            boolean r5 = r5.equals(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            if (r5 != 0) goto L_0x0173
            java.lang.IndexOutOfBoundsException r3 = new java.lang.IndexOutOfBoundsException     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r5 = "Mismatching time zone indicator: "
            r4.<init>(r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r4.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r5 = " given, resolves to "
            r4.append(r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r5 = r11.getID()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r4.append(r5)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.lang.String r4 = r4.toString()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3.<init>(r4)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            throw r3     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x0173:
            r10 = r11
            goto L_0x0177
        L_0x0175:
            java.util.TimeZone r10 = a     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
        L_0x0177:
            java.util.GregorianCalendar r5 = new java.util.GregorianCalendar     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r5.<init>(r10)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r9 = 0
            r5.setLenient(r9)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r9 = 1
            r5.set(r9, r3)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            int r4 = r4 - r9
            r3 = 2
            r5.set(r3, r4)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3 = 5
            r5.set(r3, r6)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3 = 11
            r5.set(r3, r7)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3 = 12
            r5.set(r3, r8)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3 = 13
            r5.set(r3, r14)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r3 = 14
            r5.set(r3, r12)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            r2.setIndex(r13)     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            java.util.Date r3 = r5.getTime()     // Catch:{ IndexOutOfBoundsException -> 0x01af, NumberFormatException -> 0x01ac, IllegalArgumentException -> 0x01a9 }
            return r3
        L_0x01a9:
            r0 = move-exception
            r3 = r0
            goto L_0x01b1
        L_0x01ac:
            r0 = move-exception
            r3 = r0
            goto L_0x01b1
        L_0x01af:
            r0 = move-exception
            r3 = r0
        L_0x01b1:
            if (r1 != 0) goto L_0x01b5
            r1 = 0
            goto L_0x01c8
        L_0x01b5:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "\""
            r4.<init>(r5)
            r4.append(r1)
            r1 = 34
            r4.append(r1)
            java.lang.String r1 = r4.toString()
        L_0x01c8:
            java.lang.String r4 = r3.getMessage()
            if (r4 == 0) goto L_0x01d4
            boolean r5 = r4.isEmpty()
            if (r5 == 0) goto L_0x01ef
        L_0x01d4:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "("
            r4.<init>(r5)
            java.lang.Class r5 = r3.getClass()
            java.lang.String r5 = r5.getName()
            r4.append(r5)
            java.lang.String r5 = ")"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
        L_0x01ef:
            java.text.ParseException r5 = new java.text.ParseException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Failed to parse date ["
            r6.<init>(r7)
            r6.append(r1)
            java.lang.String r1 = "]: "
            r6.append(r1)
            r6.append(r4)
            java.lang.String r1 = r6.toString()
            int r2 = r19.getIndex()
            r5.<init>(r1, r2)
            r5.initCause(r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.a.a.a.a(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean a(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int a(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                StringBuilder sb = new StringBuilder("Invalid number: ");
                sb.append(str.substring(i, i2));
                throw new NumberFormatException(sb.toString());
            }
            i3 = -digit;
        } else {
            i4 = i;
            i3 = 0;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int digit2 = Character.digit(str.charAt(i4), 10);
            if (digit2 < 0) {
                StringBuilder sb2 = new StringBuilder("Invalid number: ");
                sb2.append(str.substring(i, i2));
                throw new NumberFormatException(sb2.toString());
            }
            i3 = (i3 * 10) - digit2;
            i4 = i5;
        }
        return -i3;
    }

    private static int a(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
