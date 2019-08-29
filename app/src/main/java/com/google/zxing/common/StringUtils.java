package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS = (SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING));
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        byte[] bArr2 = bArr;
        Map<DecodeHintType, ?> map2 = map;
        if (map2 != null && map2.containsKey(DecodeHintType.CHARACTER_SET)) {
            return map2.get(DecodeHintType.CHARACTER_SET).toString();
        }
        int length = bArr2.length;
        int i = 0;
        boolean z = bArr2.length > 3 && bArr2[0] == -17 && bArr2[1] == -69 && bArr2[2] == -65;
        int i2 = 0;
        int i3 = 0;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i3 < length && (z2 || z3 || z4)) {
            byte b = bArr2[i3] & 255;
            if (z4) {
                if (i4 > 0) {
                    if ((b & 128) != 0) {
                        i4--;
                    }
                } else if ((b & 128) != 0) {
                    if ((b & 64) != 0) {
                        i4++;
                        if ((b & 32) == 0) {
                            i6++;
                        } else {
                            i4++;
                            if ((b & 16) == 0) {
                                i7++;
                            } else {
                                i4++;
                                if ((b & 8) == 0) {
                                    i8++;
                                }
                            }
                        }
                    }
                }
                z4 = false;
            }
            if (z2) {
                if (b > Byte.MAX_VALUE && b < 160) {
                    z2 = false;
                } else if (b > 159 && (b < 192 || b == 215 || b == 247)) {
                    i10++;
                }
            }
            if (z3) {
                if (i5 > 0) {
                    if (b >= 64 && b != Byte.MAX_VALUE && b <= 252) {
                        i5--;
                    }
                } else if (!(b == 128 || b == 160 || b > 239)) {
                    if (b > 160 && b < 224) {
                        i2++;
                        int i13 = i11 + 1;
                        if (i13 > i9) {
                            i9 = i13;
                            i11 = i9;
                        } else {
                            i11 = i13;
                        }
                    } else if (b > Byte.MAX_VALUE) {
                        i5++;
                        int i14 = i12 + 1;
                        if (i14 > i) {
                            i = i14;
                            i12 = i;
                        } else {
                            i12 = i14;
                        }
                        i11 = 0;
                    } else {
                        i11 = 0;
                    }
                    i12 = 0;
                }
                z3 = false;
            }
            i3++;
            bArr2 = bArr;
        }
        if (z4 && i4 > 0) {
            z4 = false;
        }
        boolean z5 = (!z3 || i5 <= 0) ? z3 : false;
        if (z4 && (z || i6 + i7 + i8 > 0)) {
            return UTF8;
        }
        if (z5 && (ASSUME_SHIFT_JIS || i9 >= 3 || i >= 3)) {
            return SHIFT_JIS;
        }
        if (z2 && z5) {
            return (!(i9 == 2 && i2 == 2) && i10 * 10 < length) ? ISO88591 : SHIFT_JIS;
        }
        if (z2) {
            return ISO88591;
        }
        if (z5) {
            return SHIFT_JIS;
        }
        return z4 ? UTF8 : PLATFORM_DEFAULT_ENCODING;
    }
}
