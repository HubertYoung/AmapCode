package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    static {
        int[][] iArr = new int[107][];
        iArr[0] = new int[]{2, 1, 2, 2, 2, 2};
        iArr[1] = new int[]{2, 2, 2, 1, 2, 2};
        iArr[2] = new int[]{2, 2, 2, 2, 2, 1};
        iArr[3] = new int[]{1, 2, 1, 2, 2, 3};
        iArr[4] = new int[]{1, 2, 1, 3, 2, 2};
        iArr[5] = new int[]{1, 3, 1, 2, 2, 2};
        iArr[6] = new int[]{1, 2, 2, 2, 1, 3};
        iArr[7] = new int[]{1, 2, 2, 3, 1, 2};
        iArr[8] = new int[]{1, 3, 2, 2, 1, 2};
        iArr[9] = new int[]{2, 2, 1, 2, 1, 3};
        iArr[10] = new int[]{2, 2, 1, 3, 1, 2};
        iArr[11] = new int[]{2, 3, 1, 2, 1, 2};
        iArr[12] = new int[]{1, 1, 2, 2, 3, 2};
        iArr[13] = new int[]{1, 2, 2, 1, 3, 2};
        iArr[14] = new int[]{1, 2, 2, 2, 3, 1};
        iArr[15] = new int[]{1, 1, 3, 2, 2, 2};
        iArr[16] = new int[]{1, 2, 3, 1, 2, 2};
        iArr[17] = new int[]{1, 2, 3, 2, 2, 1};
        iArr[18] = new int[]{2, 2, 3, 2, 1, 1};
        iArr[19] = new int[]{2, 2, 1, 1, 3, 2};
        iArr[20] = new int[]{2, 2, 1, 2, 3, 1};
        iArr[21] = new int[]{2, 1, 3, 2, 1, 2};
        iArr[22] = new int[]{2, 2, 3, 1, 1, 2};
        iArr[23] = new int[]{3, 1, 2, 1, 3, 1};
        iArr[24] = new int[]{3, 1, 1, 2, 2, 2};
        iArr[25] = new int[]{3, 2, 1, 1, 2, 2};
        iArr[26] = new int[]{3, 2, 1, 2, 2, 1};
        iArr[27] = new int[]{3, 1, 2, 2, 1, 2};
        iArr[28] = new int[]{3, 2, 2, 1, 1, 2};
        iArr[29] = new int[]{3, 2, 2, 2, 1, 1};
        iArr[30] = new int[]{2, 1, 2, 1, 2, 3};
        iArr[31] = new int[]{2, 1, 2, 3, 2, 1};
        iArr[32] = new int[]{2, 3, 2, 1, 2, 1};
        iArr[33] = new int[]{1, 1, 1, 3, 2, 3};
        iArr[34] = new int[]{1, 3, 1, 1, 2, 3};
        iArr[35] = new int[]{1, 3, 1, 3, 2, 1};
        iArr[36] = new int[]{1, 1, 2, 3, 1, 3};
        iArr[37] = new int[]{1, 3, 2, 1, 1, 3};
        iArr[38] = new int[]{1, 3, 2, 3, 1, 1};
        iArr[39] = new int[]{2, 1, 1, 3, 1, 3};
        iArr[40] = new int[]{2, 3, 1, 1, 1, 3};
        iArr[41] = new int[]{2, 3, 1, 3, 1, 1};
        iArr[42] = new int[]{1, 1, 2, 1, 3, 3};
        iArr[43] = new int[]{1, 1, 2, 3, 3, 1};
        iArr[44] = new int[]{1, 3, 2, 1, 3, 1};
        iArr[45] = new int[]{1, 1, 3, 1, 2, 3};
        iArr[46] = new int[]{1, 1, 3, 3, 2, 1};
        iArr[47] = new int[]{1, 3, 3, 1, 2, 1};
        iArr[48] = new int[]{3, 1, 3, 1, 2, 1};
        iArr[49] = new int[]{2, 1, 1, 3, 3, 1};
        iArr[50] = new int[]{2, 3, 1, 1, 3, 1};
        iArr[51] = new int[]{2, 1, 3, 1, 1, 3};
        iArr[52] = new int[]{2, 1, 3, 3, 1, 1};
        iArr[53] = new int[]{2, 1, 3, 1, 3, 1};
        iArr[54] = new int[]{3, 1, 1, 1, 2, 3};
        iArr[55] = new int[]{3, 1, 1, 3, 2, 1};
        iArr[56] = new int[]{3, 3, 1, 1, 2, 1};
        iArr[57] = new int[]{3, 1, 2, 1, 1, 3};
        iArr[58] = new int[]{3, 1, 2, 3, 1, 1};
        iArr[59] = new int[]{3, 3, 2, 1, 1, 1};
        iArr[60] = new int[]{3, 1, 4, 1, 1, 1};
        iArr[61] = new int[]{2, 2, 1, 4, 1, 1};
        iArr[62] = new int[]{4, 3, 1, 1, 1, 1};
        iArr[63] = new int[]{1, 1, 1, 2, 2, 4};
        iArr[64] = new int[]{1, 1, 1, 4, 2, 2};
        iArr[65] = new int[]{1, 2, 1, 1, 2, 4};
        iArr[66] = new int[]{1, 2, 1, 4, 2, 1};
        iArr[67] = new int[]{1, 4, 1, 1, 2, 2};
        iArr[68] = new int[]{1, 4, 1, 2, 2, 1};
        iArr[69] = new int[]{1, 1, 2, 2, 1, 4};
        iArr[70] = new int[]{1, 1, 2, 4, 1, 2};
        iArr[71] = new int[]{1, 2, 2, 1, 1, 4};
        iArr[72] = new int[]{1, 2, 2, 4, 1, 1};
        iArr[73] = new int[]{1, 4, 2, 1, 1, 2};
        iArr[74] = new int[]{1, 4, 2, 2, 1, 1};
        iArr[75] = new int[]{2, 4, 1, 2, 1, 1};
        iArr[76] = new int[]{2, 2, 1, 1, 1, 4};
        iArr[77] = new int[]{4, 1, 3, 1, 1, 1};
        iArr[78] = new int[]{2, 4, 1, 1, 1, 2};
        iArr[79] = new int[]{1, 3, 4, 1, 1, 1};
        iArr[80] = new int[]{1, 1, 1, 2, 4, 2};
        iArr[81] = new int[]{1, 2, 1, 1, 4, 2};
        iArr[82] = new int[]{1, 2, 1, 2, 4, 1};
        iArr[83] = new int[]{1, 1, 4, 2, 1, 2};
        iArr[84] = new int[]{1, 2, 4, 1, 1, 2};
        iArr[85] = new int[]{1, 2, 4, 2, 1, 1};
        iArr[86] = new int[]{4, 1, 1, 2, 1, 2};
        iArr[87] = new int[]{4, 2, 1, 1, 1, 2};
        iArr[88] = new int[]{4, 2, 1, 2, 1, 1};
        iArr[89] = new int[]{2, 1, 2, 1, 4, 1};
        iArr[90] = new int[]{2, 1, 4, 1, 2, 1};
        iArr[91] = new int[]{4, 1, 2, 1, 2, 1};
        iArr[92] = new int[]{1, 1, 1, 1, 4, 3};
        iArr[93] = new int[]{1, 1, 1, 3, 4, 1};
        iArr[94] = new int[]{1, 3, 1, 1, 4, 1};
        iArr[95] = new int[]{1, 1, 4, 1, 1, 3};
        iArr[CODE_FNC_3] = new int[]{1, 1, 4, 3, 1, 1};
        iArr[CODE_FNC_2] = new int[]{4, 1, 1, 1, 1, 3};
        iArr[CODE_SHIFT] = new int[]{4, 1, 1, 3, 1, 1};
        iArr[99] = new int[]{1, 1, 3, 1, 4, 1};
        iArr[100] = new int[]{1, 1, 4, 1, 3, 1};
        iArr[101] = new int[]{3, 1, 1, 1, 4, 1};
        iArr[102] = new int[]{4, 1, 1, 1, 3, 1};
        iArr[103] = new int[]{2, 1, 1, 4, 1, 2};
        iArr[104] = new int[]{2, 1, 1, 2, 1, 4};
        iArr[105] = new int[]{2, 1, 1, 2, 3, 2};
        iArr[106] = new int[]{2, 3, 3, 1, 1, 1, 2};
        CODE_PATTERNS = iArr;
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int[] iArr = new int[6];
        int i = nextSet;
        boolean z = false;
        int i2 = 0;
        while (nextSet < size) {
            if (bitArray.get(nextSet) ^ z) {
                iArr[i2] = iArr[i2] + 1;
            } else {
                if (i2 == 5) {
                    float f = MAX_AVG_VARIANCE;
                    int i3 = -1;
                    for (int i4 = 103; i4 <= 105; i4++) {
                        float patternMatchVariance = patternMatchVariance(iArr, CODE_PATTERNS[i4], MAX_INDIVIDUAL_VARIANCE);
                        if (patternMatchVariance < f) {
                            i3 = i4;
                            f = patternMatchVariance;
                        }
                    }
                    if (i3 < 0 || !bitArray.isRange(Math.max(0, i - ((nextSet - i) / 2)), i, false)) {
                        i += iArr[0] + iArr[1];
                        System.arraycopy(iArr, 2, iArr, 0, 4);
                        iArr[4] = 0;
                        iArr[5] = 0;
                        i2--;
                    } else {
                        return new int[]{i, nextSet, i3};
                    }
                } else {
                    i2++;
                }
                iArr[i2] = 1;
                z = !z;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int decodeCode(BitArray bitArray, int[] iArr, int i) throws NotFoundException {
        recordPattern(bitArray, i, iArr);
        float f = MAX_AVG_VARIANCE;
        int i2 = -1;
        for (int i3 = 0; i3 < CODE_PATTERNS.length; i3++) {
            float patternMatchVariance = patternMatchVariance(iArr, CODE_PATTERNS[i3], MAX_INDIVIDUAL_VARIANCE);
            if (patternMatchVariance < f) {
                i2 = i3;
                f = patternMatchVariance;
            }
        }
        if (i2 >= 0) {
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* JADX WARNING: type inference failed for: r3v27 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01f5, code lost:
        r23 = r5;
        r24 = r9;
        r3 = false;
        r15 = 'c';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0208, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0209, code lost:
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x020a, code lost:
        r23 = r5;
        r24 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0246, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0247, code lost:
        r15 = 'e';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x024e, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x024f, code lost:
        r15 = 'd';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0259, code lost:
        r23 = r5;
        r24 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x025d, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x025e, code lost:
        if (r14 == false) goto L_0x0269;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0262, code lost:
        if (r15 != 'e') goto L_0x0267;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0264, code lost:
        r15 = 'd';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0267, code lost:
        r15 = 'e';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0269, code lost:
        r14 = r3;
        r12 = r8;
        r8 = r22;
        r2 = 1;
        r5 = 2;
        r9 = 'c';
        r27 = r17;
        r17 = r11;
        r11 = r27;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0178, code lost:
        if (r5 != false) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01a4, code lost:
        r24 = r9;
        r3 = false;
        r23 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01b6, code lost:
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01d6, code lost:
        r3 = false;
        r23 = false;
        r24 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01e1, code lost:
        if (r5 != false) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01e3, code lost:
        r3 = false;
        r23 = false;
        r24 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01ec, code lost:
        r24 = r9;
        r3 = false;
        r23 = true;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1, types: [boolean]
      assigns: []
      uses: [boolean, ?[int, short, byte, char]]
      mth insns count: 260
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.zxing.Result decodeRow(int r29, com.google.zxing.common.BitArray r30, java.util.Map<com.google.zxing.DecodeHintType, ?> r31) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
            r28 = this;
            r0 = r30
            r1 = r31
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0012
            com.google.zxing.DecodeHintType r4 = com.google.zxing.DecodeHintType.ASSUME_GS1
            boolean r1 = r1.containsKey(r4)
            if (r1 == 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            int[] r4 = findStartPattern(r30)
            r5 = 2
            r6 = r4[r5]
            java.util.ArrayList r7 = new java.util.ArrayList
            r8 = 20
            r7.<init>(r8)
            byte r9 = (byte) r6
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)
            r7.add(r9)
            r9 = 99
            switch(r6) {
                case 103: goto L_0x0039;
                case 104: goto L_0x0036;
                case 105: goto L_0x0033;
                default: goto L_0x002e;
            }
        L_0x002e:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()
            throw r0
        L_0x0033:
            r12 = 99
            goto L_0x003b
        L_0x0036:
            r12 = 100
            goto L_0x003b
        L_0x0039:
            r12 = 101(0x65, float:1.42E-43)
        L_0x003b:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>(r8)
            r8 = r4[r3]
            r14 = r4[r2]
            r15 = 6
            int[] r10 = new int[r15]
            r18 = r6
            r15 = r12
            r6 = 0
            r11 = 0
            r17 = 0
            r19 = 0
            r21 = 1
            r23 = 0
            r24 = 0
            r12 = r8
            r8 = r14
            r14 = 0
        L_0x0059:
            if (r6 == 0) goto L_0x00e9
            int r1 = r8 - r12
            int r6 = r0.getNextUnset(r8)
            int r8 = r30.getSize()
            int r10 = r6 - r12
            int r10 = r10 / r5
            int r10 = r10 + r6
            int r8 = java.lang.Math.min(r8, r10)
            boolean r0 = r0.isRange(r6, r8, r3)
            if (r0 != 0) goto L_0x0078
            com.google.zxing.NotFoundException r0 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r0
        L_0x0078:
            int r19 = r19 * r11
            int r18 = r18 - r19
            int r0 = r18 % 103
            if (r0 == r11) goto L_0x0085
            com.google.zxing.ChecksumException r0 = com.google.zxing.ChecksumException.getChecksumInstance()
            throw r0
        L_0x0085:
            int r0 = r13.length()
            if (r0 != 0) goto L_0x0090
            com.google.zxing.NotFoundException r0 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r0
        L_0x0090:
            if (r0 <= 0) goto L_0x00a1
            if (r21 == 0) goto L_0x00a1
            if (r15 != r9) goto L_0x009c
            int r6 = r0 + -2
            r13.delete(r6, r0)
            goto L_0x00a1
        L_0x009c:
            int r6 = r0 + -1
            r13.delete(r6, r0)
        L_0x00a1:
            r0 = r4[r2]
            r4 = r4[r3]
            int r0 = r0 + r4
            float r0 = (float) r0
            r4 = 1073741824(0x40000000, float:2.0)
            float r11 = r0 / r4
            float r0 = (float) r12
            float r1 = (float) r1
            float r1 = r1 / r4
            float r12 = r0 + r1
            int r0 = r7.size()
            byte[] r1 = new byte[r0]
            r4 = 0
        L_0x00b7:
            if (r4 < r0) goto L_0x00d8
            com.google.zxing.Result r0 = new com.google.zxing.Result
            java.lang.String r4 = r13.toString()
            com.google.zxing.ResultPoint[] r5 = new com.google.zxing.ResultPoint[r5]
            com.google.zxing.ResultPoint r6 = new com.google.zxing.ResultPoint
            r8 = r29
            float r7 = (float) r8
            r6.<init>(r11, r7)
            r5[r3] = r6
            com.google.zxing.ResultPoint r3 = new com.google.zxing.ResultPoint
            r3.<init>(r12, r7)
            r5[r2] = r3
            com.google.zxing.BarcodeFormat r2 = com.google.zxing.BarcodeFormat.CODE_128
            r0.<init>(r4, r1, r5, r2)
            return r0
        L_0x00d8:
            r8 = r29
            java.lang.Object r6 = r7.get(r4)
            java.lang.Byte r6 = (java.lang.Byte) r6
            byte r6 = r6.byteValue()
            r1[r4] = r6
            int r4 = r4 + 1
            goto L_0x00b7
        L_0x00e9:
            int r11 = decodeCode(r0, r10, r8)
            byte r12 = (byte) r11
            java.lang.Byte r12 = java.lang.Byte.valueOf(r12)
            r7.add(r12)
            r12 = 106(0x6a, float:1.49E-43)
            if (r11 == r12) goto L_0x00fb
            r21 = 1
        L_0x00fb:
            if (r11 == r12) goto L_0x0103
            int r19 = r19 + 1
            int r25 = r19 * r11
            int r18 = r18 + r25
        L_0x0103:
            r22 = r8
            r2 = 0
            r3 = 6
        L_0x0107:
            if (r2 < r3) goto L_0x027a
            switch(r11) {
                case 103: goto L_0x011b;
                case 104: goto L_0x011b;
                case 105: goto L_0x011b;
                default: goto L_0x010c;
            }
        L_0x010c:
            r2 = 96
            r3 = 29
            switch(r15) {
                case 99: goto L_0x0211;
                case 100: goto L_0x018d;
                case 101: goto L_0x0120;
                default: goto L_0x0113;
            }
        L_0x0113:
            r5 = r23
            r9 = r24
            r2 = 100
            goto L_0x0259
        L_0x011b:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()
            throw r0
        L_0x0120:
            r5 = 64
            if (r11 >= r5) goto L_0x013c
            r5 = r23
            r9 = r24
            if (r5 != r9) goto L_0x0132
            int r2 = r11 + 32
            char r2 = (char) r2
            r13.append(r2)
            goto L_0x01a4
        L_0x0132:
            int r2 = r11 + 32
            int r2 = r2 + 128
            char r2 = (char) r2
            r13.append(r2)
            goto L_0x01a4
        L_0x013c:
            r5 = r23
            r9 = r24
            if (r11 >= r2) goto L_0x0152
            if (r5 != r9) goto L_0x014b
            int r2 = r11 + -64
            char r2 = (char) r2
            r13.append(r2)
            goto L_0x01a4
        L_0x014b:
            int r2 = r11 + 64
            char r2 = (char) r2
            r13.append(r2)
            goto L_0x01a4
        L_0x0152:
            if (r11 == r12) goto L_0x0156
            r21 = 0
        L_0x0156:
            if (r11 == r12) goto L_0x0208
            switch(r11) {
                case 96: goto L_0x020a;
                case 97: goto L_0x020a;
                case 98: goto L_0x0184;
                case 99: goto L_0x01f5;
                case 100: goto L_0x017c;
                case 101: goto L_0x0170;
                case 102: goto L_0x015c;
                default: goto L_0x015b;
            }
        L_0x015b:
            goto L_0x01b6
        L_0x015c:
            if (r1 == 0) goto L_0x020a
            int r2 = r13.length()
            if (r2 != 0) goto L_0x016b
            java.lang.String r2 = "]C1"
            r13.append(r2)
            goto L_0x020a
        L_0x016b:
            r13.append(r3)
            goto L_0x020a
        L_0x0170:
            if (r9 != 0) goto L_0x0176
            if (r5 == 0) goto L_0x0176
            goto L_0x01d6
        L_0x0176:
            if (r9 == 0) goto L_0x01ec
            if (r5 == 0) goto L_0x01ec
            goto L_0x01e3
        L_0x017c:
            r23 = r5
            r24 = r9
            r2 = 100
            goto L_0x024e
        L_0x0184:
            r23 = r5
            r24 = r9
            r2 = 100
            r3 = 1
            goto L_0x024f
        L_0x018d:
            r5 = r23
            r9 = r24
            if (r11 >= r2) goto L_0x01ad
            if (r5 != r9) goto L_0x019c
            int r2 = r11 + 32
            char r2 = (char) r2
            r13.append(r2)
            goto L_0x01a4
        L_0x019c:
            int r2 = r11 + 32
            int r2 = r2 + 128
            char r2 = (char) r2
            r13.append(r2)
        L_0x01a4:
            r24 = r9
            r2 = 100
            r3 = 0
            r23 = 0
            goto L_0x025e
        L_0x01ad:
            if (r11 == r12) goto L_0x01b1
            r21 = 0
        L_0x01b1:
            if (r11 == r12) goto L_0x0208
            switch(r11) {
                case 96: goto L_0x020a;
                case 97: goto L_0x020a;
                case 98: goto L_0x0200;
                case 99: goto L_0x01f5;
                case 100: goto L_0x01d2;
                case 101: goto L_0x01ca;
                case 102: goto L_0x01b8;
                default: goto L_0x01b6;
            }
        L_0x01b6:
            r2 = r6
            goto L_0x0209
        L_0x01b8:
            if (r1 == 0) goto L_0x020a
            int r2 = r13.length()
            if (r2 != 0) goto L_0x01c6
            java.lang.String r2 = "]C1"
            r13.append(r2)
            goto L_0x020a
        L_0x01c6:
            r13.append(r3)
            goto L_0x020a
        L_0x01ca:
            r23 = r5
            r24 = r9
            r2 = 100
            goto L_0x0246
        L_0x01d2:
            if (r9 != 0) goto L_0x01df
            if (r5 == 0) goto L_0x01df
        L_0x01d6:
            r2 = 100
            r3 = 0
            r23 = 0
            r24 = 1
            goto L_0x025e
        L_0x01df:
            if (r9 == 0) goto L_0x01ec
            if (r5 == 0) goto L_0x01ec
        L_0x01e3:
            r2 = 100
            r3 = 0
            r23 = 0
            r24 = 0
            goto L_0x025e
        L_0x01ec:
            r24 = r9
            r2 = 100
            r3 = 0
            r23 = 1
            goto L_0x025e
        L_0x01f5:
            r23 = r5
            r24 = r9
            r2 = 100
            r3 = 0
            r15 = 99
            goto L_0x025e
        L_0x0200:
            r23 = r5
            r24 = r9
            r2 = 100
            r3 = 1
            goto L_0x0247
        L_0x0208:
            r2 = 1
        L_0x0209:
            r6 = r2
        L_0x020a:
            r23 = r5
            r24 = r9
            r2 = 100
            goto L_0x025d
        L_0x0211:
            r5 = r23
            r9 = r24
            r2 = 100
            if (r11 >= r2) goto L_0x0226
            r3 = 10
            if (r11 >= r3) goto L_0x0222
            r3 = 48
            r13.append(r3)
        L_0x0222:
            r13.append(r11)
            goto L_0x0259
        L_0x0226:
            if (r11 == r12) goto L_0x022a
            r21 = 0
        L_0x022a:
            if (r11 == r12) goto L_0x0252
            switch(r11) {
                case 100: goto L_0x024a;
                case 101: goto L_0x0242;
                case 102: goto L_0x0230;
                default: goto L_0x022f;
            }
        L_0x022f:
            goto L_0x0259
        L_0x0230:
            if (r1 == 0) goto L_0x0259
            int r12 = r13.length()
            if (r12 != 0) goto L_0x023e
            java.lang.String r3 = "]C1"
            r13.append(r3)
            goto L_0x0259
        L_0x023e:
            r13.append(r3)
            goto L_0x0259
        L_0x0242:
            r23 = r5
            r24 = r9
        L_0x0246:
            r3 = 0
        L_0x0247:
            r15 = 101(0x65, float:1.42E-43)
            goto L_0x025e
        L_0x024a:
            r23 = r5
            r24 = r9
        L_0x024e:
            r3 = 0
        L_0x024f:
            r15 = 100
            goto L_0x025e
        L_0x0252:
            r23 = r5
            r24 = r9
            r3 = 0
            r6 = 1
            goto L_0x025e
        L_0x0259:
            r23 = r5
            r24 = r9
        L_0x025d:
            r3 = 0
        L_0x025e:
            if (r14 == 0) goto L_0x0269
            r5 = 101(0x65, float:1.42E-43)
            if (r15 != r5) goto L_0x0267
            r15 = 100
            goto L_0x0269
        L_0x0267:
            r15 = 101(0x65, float:1.42E-43)
        L_0x0269:
            r14 = r3
            r12 = r8
            r8 = r22
            r2 = 1
            r3 = 0
            r5 = 2
            r9 = 99
            r27 = r17
            r17 = r11
            r11 = r27
            goto L_0x0059
        L_0x027a:
            r5 = r23
            r9 = r24
            r3 = 100
            r16 = 101(0x65, float:1.42E-43)
            r20 = r10[r2]
            int r22 = r22 + r20
            int r2 = r2 + 1
            r3 = 6
            r5 = 2
            r9 = 99
            goto L_0x0107
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
