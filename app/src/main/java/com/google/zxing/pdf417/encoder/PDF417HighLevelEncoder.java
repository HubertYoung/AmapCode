package com.google.zxing.pdf417.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED = new byte[128];
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION = new byte[128];
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW;
    private static final byte[] TEXT_PUNCTUATION_RAW;

    private static boolean isAlphaLower(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    private static boolean isAlphaUpper(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isText(char c) {
        return c == 9 || c == 10 || c == 13 || (c >= ' ' && c <= '~');
    }

    static {
        byte[] bArr = new byte[30];
        bArr[0] = 48;
        bArr[1] = 49;
        bArr[2] = 50;
        bArr[3] = 51;
        bArr[4] = 52;
        bArr[5] = 53;
        bArr[6] = 54;
        bArr[7] = 55;
        bArr[8] = 56;
        bArr[9] = 57;
        bArr[10] = 38;
        bArr[11] = 13;
        bArr[12] = 9;
        bArr[13] = 44;
        bArr[14] = 58;
        bArr[15] = 35;
        bArr[16] = 45;
        bArr[17] = 46;
        bArr[18] = 36;
        bArr[19] = 47;
        bArr[20] = 43;
        bArr[21] = 37;
        bArr[22] = 42;
        bArr[23] = 61;
        bArr[24] = 94;
        bArr[26] = 32;
        TEXT_MIXED_RAW = bArr;
        byte[] bArr2 = new byte[30];
        bArr2[0] = 59;
        bArr2[1] = 60;
        bArr2[2] = 62;
        bArr2[3] = 64;
        bArr2[4] = 91;
        bArr2[5] = 92;
        bArr2[6] = 93;
        bArr2[7] = 95;
        bArr2[8] = 96;
        bArr2[9] = 126;
        bArr2[10] = 33;
        bArr2[11] = 13;
        bArr2[12] = 9;
        bArr2[13] = 44;
        bArr2[14] = 58;
        bArr2[15] = 10;
        bArr2[16] = 45;
        bArr2[17] = 46;
        bArr2[18] = 36;
        bArr2[19] = 47;
        bArr2[20] = 34;
        bArr2[21] = 124;
        bArr2[22] = 42;
        bArr2[23] = 40;
        bArr2[24] = 41;
        bArr2[25] = 63;
        bArr2[26] = 123;
        bArr2[27] = 125;
        bArr2[28] = 39;
        TEXT_PUNCTUATION_RAW = bArr2;
        Arrays.fill(MIXED, -1);
        for (int i = 0; i < TEXT_MIXED_RAW.length; i++) {
            byte b = TEXT_MIXED_RAW[i];
            if (b > 0) {
                MIXED[b] = (byte) i;
            }
        }
        Arrays.fill(PUNCTUATION, -1);
        for (int i2 = 0; i2 < TEXT_PUNCTUATION_RAW.length; i2++) {
            byte b2 = TEXT_PUNCTUATION_RAW[i2];
            if (b2 > 0) {
                PUNCTUATION[b2] = (byte) i2;
            }
        }
    }

    private PDF417HighLevelEncoder() {
    }

    static String encodeHighLevel(String str, Compaction compaction, Charset charset) throws WriterException {
        int i;
        StringBuilder sb = new StringBuilder(str.length());
        if (charset == null) {
            charset = DEFAULT_ENCODING;
        } else if (!DEFAULT_ENCODING.equals(charset)) {
            CharacterSetECI characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name());
            if (characterSetECIByName != null) {
                encodingECI(characterSetECIByName.getValue(), sb);
            }
        }
        int length = str.length();
        if (compaction != Compaction.TEXT) {
            if (compaction != Compaction.BYTE) {
                if (compaction != Compaction.NUMERIC) {
                    int i2 = 0;
                    int i3 = 0;
                    loop0:
                    while (true) {
                        int i4 = 0;
                        while (i < length) {
                            int determineConsecutiveDigitCount = determineConsecutiveDigitCount(str, i);
                            if (determineConsecutiveDigitCount >= 13) {
                                sb.append(902);
                                i3 = 2;
                                encodeNumeric(str, i, determineConsecutiveDigitCount, sb);
                                i2 = i + determineConsecutiveDigitCount;
                            } else {
                                int determineConsecutiveTextCount = determineConsecutiveTextCount(str, i);
                                if (determineConsecutiveTextCount >= 5 || determineConsecutiveDigitCount == length) {
                                    if (i3 != 0) {
                                        sb.append(900);
                                        i3 = 0;
                                        i4 = 0;
                                    }
                                    i4 = encodeText(str, i, determineConsecutiveTextCount, sb, i4);
                                    i += determineConsecutiveTextCount;
                                } else {
                                    int determineConsecutiveBinaryCount = determineConsecutiveBinaryCount(str, i, charset);
                                    if (determineConsecutiveBinaryCount == 0) {
                                        determineConsecutiveBinaryCount = 1;
                                    }
                                    int i5 = determineConsecutiveBinaryCount + i;
                                    byte[] bytes = str.substring(i, i5).getBytes(charset);
                                    if (bytes.length == 1 && i3 == 0) {
                                        encodeBinary(bytes, 0, 1, 0, sb);
                                    } else {
                                        encodeBinary(bytes, 0, bytes.length, i3, sb);
                                        i3 = 1;
                                        i4 = 0;
                                    }
                                    i = i5;
                                }
                            }
                        }
                        break loop0;
                    }
                } else {
                    sb.append(902);
                    encodeNumeric(str, 0, length, sb);
                }
            } else {
                byte[] bytes2 = str.getBytes(charset);
                encodeBinary(bytes2, 0, bytes2.length, 1, sb);
            }
        } else {
            encodeText(str, 0, length, sb, 0);
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d0, code lost:
        r9 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00dc, code lost:
        r9 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ea, code lost:
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ec, code lost:
        if (r8 < r1) goto L_0x0010;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ee, code lost:
        r10 = r3.length();
        r0 = 0;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f4, code lost:
        if (r0 < r10) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f7, code lost:
        if ((r10 % 2) == 0) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f9, code lost:
        r2.append((char) ((r1 * 30) + 29));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0100, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0103, code lost:
        if ((r0 % 2) == 0) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0105, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0107, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0108, code lost:
        if (r7 == false) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x010a, code lost:
        r1 = (char) ((r1 * 30) + r3.charAt(r0));
        r2.append(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0116, code lost:
        r1 = r3.charAt(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x011a, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0120, code lost:
        r9 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int encodeText(java.lang.CharSequence r17, int r18, int r19, java.lang.StringBuilder r20, int r21) {
        /*
            r0 = r17
            r1 = r19
            r2 = r20
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            r4 = 2
            r6 = 0
            r9 = r21
            r8 = 0
        L_0x0010:
            int r10 = r18 + r8
            char r11 = r0.charAt(r10)
            r12 = 26
            r13 = 32
            r14 = 27
            r15 = 28
            r5 = 29
            switch(r9) {
                case 0: goto L_0x00b4;
                case 1: goto L_0x007b;
                case 2: goto L_0x0033;
                default: goto L_0x0023;
            }
        L_0x0023:
            boolean r10 = isPunctuation(r11)
            if (r10 == 0) goto L_0x011d
            byte[] r10 = PUNCTUATION
            byte r10 = r10[r11]
            char r10 = (char) r10
            r3.append(r10)
            goto L_0x00ea
        L_0x0033:
            boolean r12 = isMixed(r11)
            if (r12 == 0) goto L_0x0043
            byte[] r10 = MIXED
            byte r10 = r10[r11]
            char r10 = (char) r10
            r3.append(r10)
            goto L_0x00ea
        L_0x0043:
            boolean r12 = isAlphaUpper(r11)
            if (r12 == 0) goto L_0x004e
            r3.append(r15)
            goto L_0x0120
        L_0x004e:
            boolean r12 = isAlphaLower(r11)
            if (r12 == 0) goto L_0x0059
            r3.append(r14)
            goto L_0x00d0
        L_0x0059:
            int r10 = r10 + 1
            if (r10 >= r1) goto L_0x006e
            char r10 = r0.charAt(r10)
            boolean r10 = isPunctuation(r10)
            if (r10 == 0) goto L_0x006e
            r9 = 3
            r5 = 25
            r3.append(r5)
            goto L_0x0010
        L_0x006e:
            r3.append(r5)
            byte[] r10 = PUNCTUATION
            byte r10 = r10[r11]
            char r10 = (char) r10
            r3.append(r10)
            goto L_0x00ea
        L_0x007b:
            boolean r10 = isAlphaLower(r11)
            if (r10 == 0) goto L_0x008e
            if (r11 != r13) goto L_0x0087
            r3.append(r12)
            goto L_0x00ea
        L_0x0087:
            int r11 = r11 + -97
            char r10 = (char) r11
            r3.append(r10)
            goto L_0x00ea
        L_0x008e:
            boolean r10 = isAlphaUpper(r11)
            if (r10 == 0) goto L_0x009e
            r3.append(r14)
            int r11 = r11 + -65
            char r10 = (char) r11
            r3.append(r10)
            goto L_0x00ea
        L_0x009e:
            boolean r10 = isMixed(r11)
            if (r10 == 0) goto L_0x00a8
            r3.append(r15)
            goto L_0x00dc
        L_0x00a8:
            r3.append(r5)
            byte[] r10 = PUNCTUATION
            byte r10 = r10[r11]
            char r10 = (char) r10
            r3.append(r10)
            goto L_0x00ea
        L_0x00b4:
            boolean r10 = isAlphaUpper(r11)
            if (r10 == 0) goto L_0x00c7
            if (r11 != r13) goto L_0x00c0
            r3.append(r12)
            goto L_0x00ea
        L_0x00c0:
            int r11 = r11 + -65
            char r10 = (char) r11
            r3.append(r10)
            goto L_0x00ea
        L_0x00c7:
            boolean r10 = isAlphaLower(r11)
            if (r10 == 0) goto L_0x00d3
            r3.append(r14)
        L_0x00d0:
            r9 = 1
            goto L_0x0010
        L_0x00d3:
            boolean r10 = isMixed(r11)
            if (r10 == 0) goto L_0x00df
            r3.append(r15)
        L_0x00dc:
            r9 = 2
            goto L_0x0010
        L_0x00df:
            r3.append(r5)
            byte[] r10 = PUNCTUATION
            byte r10 = r10[r11]
            char r10 = (char) r10
            r3.append(r10)
        L_0x00ea:
            int r8 = r8 + 1
            if (r8 < r1) goto L_0x0010
            int r10 = r3.length()
            r0 = 0
            r1 = 0
        L_0x00f4:
            if (r0 < r10) goto L_0x0101
            int r10 = r10 % r4
            if (r10 == 0) goto L_0x0100
            int r1 = r1 * 30
            int r1 = r1 + r5
            char r0 = (char) r1
            r2.append(r0)
        L_0x0100:
            return r9
        L_0x0101:
            int r7 = r0 % 2
            if (r7 == 0) goto L_0x0107
            r7 = 1
            goto L_0x0108
        L_0x0107:
            r7 = 0
        L_0x0108:
            if (r7 == 0) goto L_0x0116
            int r1 = r1 * 30
            char r7 = r3.charAt(r0)
            int r1 = r1 + r7
            char r1 = (char) r1
            r2.append(r1)
            goto L_0x011a
        L_0x0116:
            char r1 = r3.charAt(r0)
        L_0x011a:
            int r0 = r0 + 1
            goto L_0x00f4
        L_0x011d:
            r3.append(r5)
        L_0x0120:
            r9 = 0
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    private static void encodeBinary(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append(913);
        } else if (i2 % 6 == 0) {
            sb.append(924);
        } else {
            sb.append(901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + ((long) (bArr[i4 + i5] & 255));
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) ((int) (j % 900));
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    private static void encodeNumeric(String str, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900);
        BigInteger valueOf2 = BigInteger.valueOf(0);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append(str.substring(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += min;
        }
    }

    private static boolean isMixed(char c) {
        return MIXED[c] != -1;
    }

    private static boolean isPunctuation(char c) {
        return PUNCTUATION[c] != -1;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            char charAt = charSequence.charAt(i);
            while (isDigit(charAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    charAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(charAt) && i2 < length) {
                i3++;
                i2++;
                if (i2 < length) {
                    charAt = charSequence.charAt(i2);
                }
            }
            if (i3 < 13) {
                if (i3 <= 0) {
                    if (!isText(charSequence.charAt(i2))) {
                        break;
                    }
                    i2++;
                }
            } else {
                return (i2 - i) - i3;
            }
        }
        return i2 - i;
    }

    private static int determineConsecutiveBinaryCount(String str, int i, Charset charset) throws WriterException {
        CharsetEncoder newEncoder = charset.newEncoder();
        int length = str.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(charAt)) {
                i3++;
                int i4 = i2 + i3;
                if (i4 >= length) {
                    break;
                }
                charAt = str.charAt(i4);
            }
            if (i3 >= 13) {
                return i2 - i;
            }
            char charAt2 = str.charAt(i2);
            if (!newEncoder.canEncode(charAt2)) {
                StringBuilder sb = new StringBuilder("Non-encodable character detected: ");
                sb.append(charAt2);
                sb.append(" (Unicode: ");
                sb.append(charAt2);
                sb.append(')');
                throw new WriterException(sb.toString());
            }
            i2++;
        }
        return i2 - i;
    }

    private static void encodingECI(int i, StringBuilder sb) throws WriterException {
        if (i >= 0 && i < 900) {
            sb.append(927);
            sb.append((char) i);
        } else if (i < 810900) {
            sb.append(926);
            sb.append((char) ((i / 900) - 1));
            sb.append((char) (i % 900));
        } else if (i < 811800) {
            sb.append(925);
            sb.append((char) (810900 - i));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was ".concat(String.valueOf(i)));
        }
    }
}
