package com.google.zxing.oned;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS = {'T', 'N', '*', 'E'};
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = {'/', ':', '+', DjangoUtils.EXTENSION_SEPARATOR};
    private static final char DEFAULT_GUARD = START_END_CHARS[0];
    private static final char[] START_END_CHARS = {'A', 'B', 'C', 'D'};

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x010c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x00ee A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean[] encode(java.lang.String r11) {
        /*
            r10 = this;
            int r0 = r11.length()
            r1 = 0
            r2 = 1
            r3 = 2
            if (r0 >= r3) goto L_0x0022
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            char r3 = DEFAULT_GUARD
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0.<init>(r3)
            r0.append(r11)
            char r11 = DEFAULT_GUARD
            r0.append(r11)
            java.lang.String r11 = r0.toString()
        L_0x0020:
            r0 = r11
            goto L_0x0095
        L_0x0022:
            char r0 = r11.charAt(r1)
            char r0 = java.lang.Character.toUpperCase(r0)
            int r3 = r11.length()
            int r3 = r3 - r2
            char r3 = r11.charAt(r3)
            char r3 = java.lang.Character.toUpperCase(r3)
            char[] r4 = START_END_CHARS
            boolean r4 = com.google.zxing.oned.CodaBarReader.arrayContains(r4, r0)
            char[] r5 = START_END_CHARS
            boolean r5 = com.google.zxing.oned.CodaBarReader.arrayContains(r5, r3)
            char[] r6 = ALT_START_END_CHARS
            boolean r0 = com.google.zxing.oned.CodaBarReader.arrayContains(r6, r0)
            char[] r6 = ALT_START_END_CHARS
            boolean r3 = com.google.zxing.oned.CodaBarReader.arrayContains(r6, r3)
            if (r4 == 0) goto L_0x0063
            if (r5 != 0) goto L_0x0020
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Invalid start/end guards: "
            java.lang.String r11 = java.lang.String.valueOf(r11)
            java.lang.String r11 = r1.concat(r11)
            r0.<init>(r11)
            throw r0
        L_0x0063:
            if (r0 == 0) goto L_0x0077
            if (r3 != 0) goto L_0x0020
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Invalid start/end guards: "
            java.lang.String r11 = java.lang.String.valueOf(r11)
            java.lang.String r11 = r1.concat(r11)
            r0.<init>(r11)
            throw r0
        L_0x0077:
            if (r5 != 0) goto L_0x0175
            if (r3 == 0) goto L_0x007d
            goto L_0x0175
        L_0x007d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            char r3 = DEFAULT_GUARD
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r0.<init>(r3)
            r0.append(r11)
            char r11 = DEFAULT_GUARD
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            goto L_0x0020
        L_0x0095:
            r11 = 20
            r11 = 1
            r3 = 20
        L_0x009a:
            int r4 = r0.length()
            int r4 = r4 - r2
            if (r11 < r4) goto L_0x0128
            int r11 = r0.length()
            int r11 = r11 - r2
            int r3 = r3 + r11
            boolean[] r4 = new boolean[r3]
            r5 = 0
            r6 = 0
        L_0x00ab:
            int r11 = r0.length()
            if (r5 < r11) goto L_0x00b2
            return r4
        L_0x00b2:
            char r11 = r0.charAt(r5)
            char r11 = java.lang.Character.toUpperCase(r11)
            if (r5 == 0) goto L_0x00c3
            int r3 = r0.length()
            int r3 = r3 - r2
            if (r5 != r3) goto L_0x00d3
        L_0x00c3:
            r3 = 42
            if (r11 == r3) goto L_0x00e4
            r3 = 69
            if (r11 == r3) goto L_0x00df
            r3 = 78
            if (r11 == r3) goto L_0x00da
            r3 = 84
            if (r11 == r3) goto L_0x00d5
        L_0x00d3:
            r7 = r11
            goto L_0x00e8
        L_0x00d5:
            r11 = 65
            r7 = 65
            goto L_0x00e8
        L_0x00da:
            r11 = 66
            r7 = 66
            goto L_0x00e8
        L_0x00df:
            r11 = 68
            r7 = 68
            goto L_0x00e8
        L_0x00e4:
            r11 = 67
            r7 = 67
        L_0x00e8:
            r11 = 0
        L_0x00e9:
            char[] r3 = com.google.zxing.oned.CodaBarReader.ALPHABET
            int r3 = r3.length
            if (r11 < r3) goto L_0x00f0
            r3 = 0
            goto L_0x00fb
        L_0x00f0:
            char[] r3 = com.google.zxing.oned.CodaBarReader.ALPHABET
            char r3 = r3[r11]
            if (r7 != r3) goto L_0x0125
            int[] r3 = com.google.zxing.oned.CodaBarReader.CHARACTER_ENCODINGS
            r11 = r3[r11]
            r3 = r11
        L_0x00fb:
            r11 = 0
            r7 = 1
        L_0x00fd:
            r8 = 0
        L_0x00fe:
            r9 = 7
            if (r11 < r9) goto L_0x010f
            int r11 = r0.length()
            int r11 = r11 - r2
            if (r5 >= r11) goto L_0x010c
            r4[r6] = r1
            int r6 = r6 + 1
        L_0x010c:
            int r5 = r5 + 1
            goto L_0x00ab
        L_0x010f:
            r4[r6] = r7
            int r6 = r6 + 1
            int r9 = 6 - r11
            int r9 = r3 >> r9
            r9 = r9 & r2
            if (r9 == 0) goto L_0x0120
            if (r8 != r2) goto L_0x011d
            goto L_0x0120
        L_0x011d:
            int r8 = r8 + 1
            goto L_0x00fe
        L_0x0120:
            r7 = r7 ^ 1
            int r11 = r11 + 1
            goto L_0x00fd
        L_0x0125:
            int r11 = r11 + 1
            goto L_0x00e9
        L_0x0128:
            char r4 = r0.charAt(r11)
            boolean r4 = java.lang.Character.isDigit(r4)
            if (r4 != 0) goto L_0x016f
            char r4 = r0.charAt(r11)
            r5 = 45
            if (r4 == r5) goto L_0x016f
            char r4 = r0.charAt(r11)
            r5 = 36
            if (r4 != r5) goto L_0x0143
            goto L_0x016f
        L_0x0143:
            char[] r4 = CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED
            char r5 = r0.charAt(r11)
            boolean r4 = com.google.zxing.oned.CodaBarReader.arrayContains(r4, r5)
            if (r4 == 0) goto L_0x0152
            int r3 = r3 + 10
            goto L_0x0171
        L_0x0152:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Cannot encode : '"
            r2.<init>(r3)
            char r11 = r0.charAt(r11)
            r2.append(r11)
            r11 = 39
            r2.append(r11)
            java.lang.String r11 = r2.toString()
            r1.<init>(r11)
            throw r1
        L_0x016f:
            int r3 = r3 + 9
        L_0x0171:
            int r11 = r11 + 1
            goto L_0x009a
        L_0x0175:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Invalid start/end guards: "
            java.lang.String r11 = java.lang.String.valueOf(r11)
            java.lang.String r11 = r1.concat(r11)
            r0.<init>(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.CodaBarWriter.encode(java.lang.String):boolean[]");
    }
}
