package com.google.zxing.datamatrix.decoder;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

final class DecodedBitStreamParser {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;
    private static final char[] C40_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = {'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', DjangoUtils.EXTENSION_SEPARATOR, '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT2_SET_CHARS = C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS = {'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', 127};

    enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(17:3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0030 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0039 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0042 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode() {
        /*
            int[] r0 = $SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode[] r0 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ANSIX12_ENCODE     // Catch:{ NoSuchFieldError -> 0x0015 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0015 }
            r2 = 5
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0015 }
        L_0x0015:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE     // Catch:{ NoSuchFieldError -> 0x001e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001e }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001e }
        L_0x001e:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.BASE256_ENCODE     // Catch:{ NoSuchFieldError -> 0x0027 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 7
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.C40_ENCODE     // Catch:{ NoSuchFieldError -> 0x0030 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0030 }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0030 }
        L_0x0030:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.EDIFACT_ENCODE     // Catch:{ NoSuchFieldError -> 0x0039 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0039 }
            r2 = 6
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0039 }
        L_0x0039:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.PAD_ENCODE     // Catch:{ NoSuchFieldError -> 0x0042 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0042 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0042 }
        L_0x0042:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.TEXT_ENCODE     // Catch:{ NoSuchFieldError -> 0x004b }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            $SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.$SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode():int[]");
    }

    private DecodedBitStreamParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.zxing.common.DecoderResult decode(byte[] r6) throws com.google.zxing.FormatException {
        /*
            com.google.zxing.common.BitSource r0 = new com.google.zxing.common.BitSource
            r0.<init>(r6)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = 100
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 0
            r2.<init>(r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r4 = 1
            r3.<init>(r4)
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
        L_0x001a:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r5 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
            if (r4 != r5) goto L_0x0023
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = decodeAsciiSegment(r0, r1, r2)
            goto L_0x004a
        L_0x0023:
            int[] r5 = $SWITCH_TABLE$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode()
            int r4 = r4.ordinal()
            r4 = r5[r4]
            switch(r4) {
                case 3: goto L_0x0045;
                case 4: goto L_0x0041;
                case 5: goto L_0x003d;
                case 6: goto L_0x0039;
                case 7: goto L_0x0035;
                default: goto L_0x0030;
            }
        L_0x0030:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        L_0x0035:
            decodeBase256Segment(r0, r1, r3)
            goto L_0x0048
        L_0x0039:
            decodeEdifactSegment(r0, r1)
            goto L_0x0048
        L_0x003d:
            decodeAnsiX12Segment(r0, r1)
            goto L_0x0048
        L_0x0041:
            decodeTextSegment(r0, r1)
            goto L_0x0048
        L_0x0045:
            decodeC40Segment(r0, r1)
        L_0x0048:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
        L_0x004a:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r5 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.PAD_ENCODE
            if (r4 == r5) goto L_0x0054
            int r5 = r0.available()
            if (r5 > 0) goto L_0x001a
        L_0x0054:
            int r0 = r2.length()
            if (r0 <= 0) goto L_0x005d
            r1.append(r2)
        L_0x005d:
            com.google.zxing.common.DecoderResult r0 = new com.google.zxing.common.DecoderResult
            java.lang.String r1 = r1.toString()
            boolean r2 = r3.isEmpty()
            r4 = 0
            if (r2 == 0) goto L_0x006b
            r3 = r4
        L_0x006b:
            r0.<init>(r6, r1, r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.decode(byte[]):com.google.zxing.common.DecoderResult");
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder sb, StringBuilder sb2) throws FormatException {
        boolean z = false;
        do {
            int readBits = bitSource.readBits(8);
            if (readBits == 0) {
                throw FormatException.getFormatInstance();
            } else if (readBits <= 128) {
                if (z) {
                    readBits += 128;
                }
                sb.append((char) (readBits - 1));
                return Mode.ASCII_ENCODE;
            } else if (readBits == 129) {
                return Mode.PAD_ENCODE;
            } else {
                if (readBits <= 229) {
                    int i = readBits - 130;
                    if (i < 10) {
                        sb.append('0');
                    }
                    sb.append(i);
                } else if (readBits == 230) {
                    return Mode.C40_ENCODE;
                } else {
                    if (readBits == 231) {
                        return Mode.BASE256_ENCODE;
                    }
                    if (readBits == 232) {
                        sb.append(29);
                    } else if (!(readBits == 233 || readBits == 234)) {
                        if (readBits == 235) {
                            z = true;
                        } else if (readBits == 236) {
                            sb.append("[)>\u001e05\u001d");
                            sb2.insert(0, "\u001e\u0004");
                        } else if (readBits == 237) {
                            sb.append("[)>\u001e06\u001d");
                            sb2.insert(0, "\u001e\u0004");
                        } else if (readBits == 238) {
                            return Mode.ANSIX12_ENCODE;
                        } else {
                            if (readBits == 239) {
                                return Mode.TEXT_ENCODE;
                            }
                            if (readBits == 240) {
                                return Mode.EDIFACT_ENCODE;
                            }
                            if (!(readBits == 241 || readBits < 242 || (readBits == 254 && bitSource.available() == 0))) {
                                throw FormatException.getFormatInstance();
                            }
                        }
                    }
                }
            }
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
        r5 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void decodeC40Segment(com.google.zxing.common.BitSource r8, java.lang.StringBuilder r9) throws com.google.zxing.FormatException {
        /*
            r0 = 3
            int[] r1 = new int[r0]
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0006:
            int r5 = r8.available()
            r6 = 8
            if (r5 != r6) goto L_0x000f
            return
        L_0x000f:
            int r5 = r8.readBits(r6)
            r7 = 254(0xfe, float:3.56E-43)
            if (r5 != r7) goto L_0x0018
            return
        L_0x0018:
            int r6 = r8.readBits(r6)
            parseTwoBytes(r5, r6, r1)
            r5 = r3
            r3 = 0
        L_0x0021:
            if (r3 < r0) goto L_0x002c
            int r3 = r8.available()
            if (r3 > 0) goto L_0x002a
            return
        L_0x002a:
            r3 = r5
            goto L_0x0006
        L_0x002c:
            r6 = r1[r3]
            switch(r4) {
                case 0: goto L_0x0084;
                case 1: goto L_0x0075;
                case 2: goto L_0x0047;
                case 3: goto L_0x0036;
                default: goto L_0x0031;
            }
        L_0x0031:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x0036:
            if (r5 == 0) goto L_0x0040
            int r6 = r6 + 224
            char r4 = (char) r6
            r9.append(r4)
        L_0x003e:
            r5 = 0
            goto L_0x0082
        L_0x0040:
            int r6 = r6 + 96
            char r4 = (char) r6
            r9.append(r4)
            goto L_0x0082
        L_0x0047:
            char[] r4 = C40_SHIFT2_SET_CHARS
            int r4 = r4.length
            if (r6 >= r4) goto L_0x005e
            char[] r4 = C40_SHIFT2_SET_CHARS
            char r4 = r4[r6]
            if (r5 == 0) goto L_0x005a
            int r4 = r4 + 128
            char r4 = (char) r4
            r9.append(r4)
            r4 = 0
            goto L_0x006e
        L_0x005a:
            r9.append(r4)
            goto L_0x0067
        L_0x005e:
            r4 = 27
            if (r6 != r4) goto L_0x0069
            r4 = 29
            r9.append(r4)
        L_0x0067:
            r4 = r5
            goto L_0x006e
        L_0x0069:
            r4 = 30
            if (r6 != r4) goto L_0x0070
            r4 = 1
        L_0x006e:
            r5 = r4
            goto L_0x0082
        L_0x0070:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x0075:
            if (r5 == 0) goto L_0x007e
            int r6 = r6 + 128
            char r4 = (char) r6
            r9.append(r4)
            goto L_0x003e
        L_0x007e:
            char r4 = (char) r6
            r9.append(r4)
        L_0x0082:
            r4 = 0
            goto L_0x009f
        L_0x0084:
            if (r6 >= r0) goto L_0x0089
            int r4 = r6 + 1
            goto L_0x009f
        L_0x0089:
            char[] r7 = C40_BASIC_SET_CHARS
            int r7 = r7.length
            if (r6 >= r7) goto L_0x00a3
            char[] r7 = C40_BASIC_SET_CHARS
            char r6 = r7[r6]
            if (r5 == 0) goto L_0x009c
            int r6 = r6 + 128
            char r5 = (char) r6
            r9.append(r5)
            r5 = 0
            goto L_0x009f
        L_0x009c:
            r9.append(r6)
        L_0x009f:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x00a3:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.decodeC40Segment(com.google.zxing.common.BitSource, java.lang.StringBuilder):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0047, code lost:
        r5 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void decodeTextSegment(com.google.zxing.common.BitSource r8, java.lang.StringBuilder r9) throws com.google.zxing.FormatException {
        /*
            r0 = 3
            int[] r1 = new int[r0]
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0006:
            int r5 = r8.available()
            r6 = 8
            if (r5 != r6) goto L_0x000f
            return
        L_0x000f:
            int r5 = r8.readBits(r6)
            r7 = 254(0xfe, float:3.56E-43)
            if (r5 != r7) goto L_0x0018
            return
        L_0x0018:
            int r6 = r8.readBits(r6)
            parseTwoBytes(r5, r6, r1)
            r5 = r3
            r3 = 0
        L_0x0021:
            if (r3 < r0) goto L_0x002c
            int r3 = r8.available()
            if (r3 > 0) goto L_0x002a
            return
        L_0x002a:
            r3 = r5
            goto L_0x0006
        L_0x002c:
            r6 = r1[r3]
            switch(r4) {
                case 0: goto L_0x008f;
                case 1: goto L_0x0080;
                case 2: goto L_0x0052;
                case 3: goto L_0x0036;
                default: goto L_0x0031;
            }
        L_0x0031:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x0036:
            char[] r4 = TEXT_SHIFT3_SET_CHARS
            int r4 = r4.length
            if (r6 >= r4) goto L_0x004d
            char[] r4 = TEXT_SHIFT3_SET_CHARS
            char r4 = r4[r6]
            if (r5 == 0) goto L_0x0049
            int r4 = r4 + 128
            char r4 = (char) r4
            r9.append(r4)
        L_0x0047:
            r5 = 0
            goto L_0x008d
        L_0x0049:
            r9.append(r4)
            goto L_0x008d
        L_0x004d:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x0052:
            char[] r4 = TEXT_SHIFT2_SET_CHARS
            int r4 = r4.length
            if (r6 >= r4) goto L_0x0069
            char[] r4 = TEXT_SHIFT2_SET_CHARS
            char r4 = r4[r6]
            if (r5 == 0) goto L_0x0065
            int r4 = r4 + 128
            char r4 = (char) r4
            r9.append(r4)
            r4 = 0
            goto L_0x0079
        L_0x0065:
            r9.append(r4)
            goto L_0x0072
        L_0x0069:
            r4 = 27
            if (r6 != r4) goto L_0x0074
            r4 = 29
            r9.append(r4)
        L_0x0072:
            r4 = r5
            goto L_0x0079
        L_0x0074:
            r4 = 30
            if (r6 != r4) goto L_0x007b
            r4 = 1
        L_0x0079:
            r5 = r4
            goto L_0x008d
        L_0x007b:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        L_0x0080:
            if (r5 == 0) goto L_0x0089
            int r6 = r6 + 128
            char r4 = (char) r6
            r9.append(r4)
            goto L_0x0047
        L_0x0089:
            char r4 = (char) r6
            r9.append(r4)
        L_0x008d:
            r4 = 0
            goto L_0x00aa
        L_0x008f:
            if (r6 >= r0) goto L_0x0094
            int r4 = r6 + 1
            goto L_0x00aa
        L_0x0094:
            char[] r7 = TEXT_BASIC_SET_CHARS
            int r7 = r7.length
            if (r6 >= r7) goto L_0x00ae
            char[] r7 = TEXT_BASIC_SET_CHARS
            char r6 = r7[r6]
            if (r5 == 0) goto L_0x00a7
            int r6 = r6 + 128
            char r5 = (char) r6
            r9.append(r5)
            r5 = 0
            goto L_0x00aa
        L_0x00a7:
            r9.append(r6)
        L_0x00aa:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x00ae:
            com.google.zxing.FormatException r8 = com.google.zxing.FormatException.getFormatInstance()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.decodeTextSegment(com.google.zxing.common.BitSource, java.lang.StringBuilder):void");
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int[] iArr = new int[3];
        while (bitSource.available() != 8) {
            int readBits = bitSource.readBits(8);
            if (readBits != 254) {
                parseTwoBytes(readBits, bitSource.readBits(8), iArr);
                for (int i = 0; i < 3; i++) {
                    int i2 = iArr[i];
                    if (i2 == 0) {
                        sb.append(13);
                    } else if (i2 == 1) {
                        sb.append('*');
                    } else if (i2 == 2) {
                        sb.append('>');
                    } else if (i2 == 3) {
                        sb.append(' ');
                    } else if (i2 < 14) {
                        sb.append((char) (i2 + 44));
                    } else if (i2 < 40) {
                        sb.append((char) (i2 + 51));
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                }
                if (bitSource.available() <= 0) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private static void parseTwoBytes(int i, int i2, int[] iArr) {
        int i3 = ((i << 8) + i2) - 1;
        int i4 = i3 / SecExceptionCode.SEC_ERROR_SAFETOKEN;
        iArr[0] = i4;
        int i5 = i3 - (i4 * SecExceptionCode.SEC_ERROR_SAFETOKEN);
        int i6 = i5 / 40;
        iArr[1] = i6;
        iArr[2] = i5 - (i6 * 40);
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder sb) {
        while (bitSource.available() > 16) {
            for (int i = 0; i < 4; i++) {
                int readBits = bitSource.readBits(6);
                if (readBits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                    }
                    return;
                }
                if ((readBits & 32) == 0) {
                    readBits |= 64;
                }
                sb.append((char) readBits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeBase256Segment(BitSource bitSource, StringBuilder sb, Collection<byte[]> collection) throws FormatException {
        int byteOffset = bitSource.getByteOffset() + 1;
        int i = byteOffset + 1;
        int unrandomize255State = unrandomize255State(bitSource.readBits(8), byteOffset);
        if (unrandomize255State == 0) {
            unrandomize255State = bitSource.available() / 8;
        } else if (unrandomize255State >= 250) {
            unrandomize255State = ((unrandomize255State - 249) * Callback.DEFAULT_SWIPE_ANIMATION_DURATION) + unrandomize255State(bitSource.readBits(8), i);
            i++;
        }
        if (unrandomize255State < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[unrandomize255State];
        int i2 = 0;
        while (i2 < unrandomize255State) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            bArr[i2] = (byte) unrandomize255State(bitSource.readBits(8), i);
            i2++;
            i++;
        }
        collection.add(bArr);
        try {
            sb.append(new String(bArr, "ISO8859_1"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Platform does not support required encoding: ".concat(String.valueOf(e)));
        }
    }

    private static int unrandomize255State(int i, int i2) {
        int i3 = i - (((i2 * 149) % 255) + 1);
        return i3 >= 0 ? i3 : i3 + 256;
    }
}
