package com.alipay.ma.util;

import com.alipay.mobile.bqcscanservice.Logger;
import com.google.zxing.common.StringUtils;

/* compiled from: StringEncodeUtils */
public final class b {
    private static final String a = System.getProperty("file.encoding");
    private static final boolean b = (StringUtils.SHIFT_JIS.equalsIgnoreCase(a) || "EUC_JP".equalsIgnoreCase(a));

    private static String b(byte[] bytes, boolean useOldEncodeGuess) {
        boolean canBeGB2312;
        int length = bytes.length;
        int sjisBytesLeft = 0;
        int sjisKatakanaChars = 0;
        int sjisCurKatakanaWordLength = 0;
        int sjisCurDoubleBytesWordLength = 0;
        int sjisMaxKatakanaWordLength = 0;
        int sjisMaxDoubleBytesWordLength = 0;
        int isoHighOther = 0;
        boolean canBeISO88591 = true;
        boolean canBeShiftJIS = true;
        boolean canBeUTF8 = b(bytes);
        if (!useOldEncodeGuess) {
            canBeGB2312 = a(bytes);
        } else {
            canBeGB2312 = true;
        }
        Logger.d("StringEncodeUtil", "The value of useOldEncodeGuess is " + useOldEncodeGuess + ", The value is canBeGB2312 is " + canBeGB2312);
        for (int i = 0; i < length && ((useOldEncodeGuess && canBeGB2312) || canBeISO88591 || canBeShiftJIS); i++) {
            int value = bytes[i] & 255;
            if (useOldEncodeGuess && canBeGB2312 && value > 127 && value > 176 && value <= 247 && i + 1 < length) {
                byte b2 = bytes[i + 1] & 255;
                if (b2 <= 160 || b2 > 247) {
                    canBeGB2312 = false;
                } else {
                    canBeGB2312 = true;
                }
            }
            if (canBeISO88591) {
                if (value > 127 && value < 160) {
                    canBeISO88591 = false;
                } else if (value > 159 && (value < 192 || value == 215 || value == 247)) {
                    isoHighOther++;
                }
            }
            if (canBeShiftJIS) {
                if (sjisBytesLeft > 0) {
                    if (value < 64 || value == 127 || value > 252) {
                        canBeShiftJIS = false;
                    } else {
                        sjisBytesLeft--;
                    }
                } else if (value == 128 || value == 160 || value > 239) {
                    canBeShiftJIS = false;
                } else if (value > 160 && value < 224) {
                    sjisKatakanaChars++;
                    sjisCurDoubleBytesWordLength = 0;
                    sjisCurKatakanaWordLength++;
                    if (sjisCurKatakanaWordLength > sjisMaxKatakanaWordLength) {
                        sjisMaxKatakanaWordLength = sjisCurKatakanaWordLength;
                    }
                } else if (value > 127) {
                    sjisBytesLeft++;
                    sjisCurKatakanaWordLength = 0;
                    sjisCurDoubleBytesWordLength++;
                    if (sjisCurDoubleBytesWordLength > sjisMaxDoubleBytesWordLength) {
                        sjisMaxDoubleBytesWordLength = sjisCurDoubleBytesWordLength;
                    }
                } else {
                    sjisCurKatakanaWordLength = 0;
                    sjisCurDoubleBytesWordLength = 0;
                }
            }
        }
        if (canBeUTF8) {
            return "UTF8";
        }
        if (canBeShiftJIS && sjisBytesLeft > 0) {
            canBeShiftJIS = false;
        }
        if (canBeGB2312) {
            return StringUtils.GB2312;
        }
        if (canBeShiftJIS && (b || sjisMaxKatakanaWordLength >= 3 || sjisMaxDoubleBytesWordLength >= 3)) {
            return StringUtils.SHIFT_JIS;
        }
        if (canBeISO88591 && canBeShiftJIS) {
            return (!(sjisMaxKatakanaWordLength == 2 && sjisKatakanaChars == 2) && isoHighOther * 10 < length) ? "ISO8859_1" : StringUtils.SHIFT_JIS;
        }
        if (canBeISO88591) {
            return "ISO8859_1";
        }
        if (canBeShiftJIS) {
            return StringUtils.SHIFT_JIS;
        }
        return a;
    }

    public static String a(byte[] needGuessEncodeData, boolean useOldEncodeGuess) {
        if (needGuessEncodeData == null) {
            return null;
        }
        try {
            if (needGuessEncodeData.length > 0) {
                return b(needGuessEncodeData, useOldEncodeGuess);
            }
            return null;
        } catch (Exception e) {
            Logger.e("StringEncodeUtil", e.getMessage());
            return null;
        }
    }

    private static boolean a(byte[] bytes) {
        int value0 = 0;
        boolean whether2312 = true;
        boolean candidateChinese = false;
        int i = 0;
        while (true) {
            if (i >= bytes.length) {
                break;
            }
            int value1 = bytes[i] & 255;
            if (candidateChinese) {
                int value = ((value0 & 255) << 8) | (value1 & 255);
                if (value < 41377 || value > 65278) {
                    whether2312 = false;
                } else {
                    candidateChinese = false;
                    value0 = 0;
                }
            } else if ((value1 & 128) != 0) {
                value0 = value1;
                candidateChinese = true;
            }
            i++;
        }
        whether2312 = false;
        if (value0 != 0) {
            return false;
        }
        return whether2312;
    }

    private static boolean b(byte[] bytes) {
        int length;
        byte b2;
        byte b3;
        byte b4;
        boolean utf8 = true;
        if (bytes != null) {
            length = bytes.length;
        } else {
            length = 0;
        }
        int i = 0;
        while (i < length && utf8) {
            int avaLen = length - i;
            int value0 = bytes[i] & 255;
            if (avaLen > 1) {
                b2 = bytes[i + 1] & 255;
            } else {
                b2 = 0;
            }
            if (avaLen > 2) {
                b3 = bytes[i + 2] & 255;
            } else {
                b3 = 0;
            }
            if (avaLen > 3) {
                b4 = bytes[i + 3] & 255;
            } else {
                b4 = 0;
            }
            if ((value0 & 248) == 240 && (b2 & 192) == 128 && (b3 & 192) == 128 && (b4 & 192) == 128) {
                i += 4;
            } else if ((value0 & 240) == 224 && (b2 & 192) == 128 && (b3 & 192) == 128) {
                i += 3;
            } else if ((value0 & 224) == 192 && (b2 & 192) == 128) {
                i += 2;
            } else if ((value0 & 128) == 0) {
                i++;
            } else {
                utf8 = false;
            }
        }
        return utf8;
    }
}
