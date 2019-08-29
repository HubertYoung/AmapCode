package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = null;
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:3|4|5|6|7|8|9|10|11|12|13|(2:14|15)|16|18) */
    /* JADX WARNING: Can't wrap try/catch for region: R(15:3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0027 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0030 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0039 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ int[] $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode() {
        /*
            int[] r0 = $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode[] r0 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA     // Catch:{ NoSuchFieldError -> 0x0015 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0015 }
            r2 = 1
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0015 }
        L_0x0015:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA_SHIFT     // Catch:{ NoSuchFieldError -> 0x001e }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001e }
            r2 = 5
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001e }
        L_0x001e:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER     // Catch:{ NoSuchFieldError -> 0x0027 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
            r2 = 2
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0027 }
        L_0x0027:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED     // Catch:{ NoSuchFieldError -> 0x0030 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0030 }
            r2 = 3
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0030 }
        L_0x0030:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT     // Catch:{ NoSuchFieldError -> 0x0039 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0039 }
            r2 = 4
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0039 }
        L_0x0039:
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT     // Catch:{ NoSuchFieldError -> 0x0042 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0042 }
            r2 = 6
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0042 }
        L_0x0042:
            $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.$SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode():int[]");
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900);
        EXP900[1] = valueOf;
        for (int i = 2; i < EXP900.length; i++) {
            BigInteger[] bigIntegerArr2 = EXP900;
            bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
        }
    }

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(int[] iArr, String str) throws FormatException {
        int i;
        int i2 = 2;
        StringBuilder sb = new StringBuilder(iArr.length * 2);
        Charset charset = DEFAULT_ENCODING;
        int i3 = iArr[1];
        PDF417ResultMetadata pDF417ResultMetadata = new PDF417ResultMetadata();
        while (i2 < iArr[0]) {
            if (i3 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                switch (i3) {
                    case 900:
                        i = textCompaction(iArr, i2, sb);
                        break;
                    case 901:
                        i = byteCompaction(i3, iArr, charset, i2, sb);
                        break;
                    case 902:
                        i = numericCompaction(iArr, i2, sb);
                        break;
                    default:
                        switch (i3) {
                            case MACRO_PDF417_TERMINATOR /*922*/:
                            case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                throw FormatException.getFormatInstance();
                            case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                break;
                            case ECI_USER_DEFINED /*925*/:
                                i = i2 + 1;
                                break;
                            case ECI_GENERAL_PURPOSE /*926*/:
                                i = i2 + 2;
                                break;
                            case ECI_CHARSET /*927*/:
                                Charset forName = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(iArr[i2]).name());
                                i = i2 + 1;
                                charset = forName;
                                break;
                            case 928:
                                i = decodeMacroBlock(iArr, i2, pDF417ResultMetadata);
                                break;
                            default:
                                i = textCompaction(iArr, i2 - 1, sb);
                                break;
                        }
                        i = byteCompaction(i3, iArr, charset, i2, sb);
                        break;
                }
            } else {
                sb.append((char) iArr[i2]);
                i = i2 + 1;
            }
            if (i < iArr.length) {
                int i4 = i + 1;
                i3 = iArr[i];
                i2 = i4;
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (sb.length() == 0) {
            throw FormatException.getFormatInstance();
        }
        DecoderResult decoderResult = new DecoderResult(null, sb.toString(), null, str);
        decoderResult.setOther(pDF417ResultMetadata);
        return decoderResult;
    }

    private static int decodeMacroBlock(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i + 2 > iArr[0]) {
            throw FormatException.getFormatInstance();
        }
        int[] iArr2 = new int[2];
        int i2 = i;
        int i3 = 0;
        while (i3 < 2) {
            iArr2[i3] = iArr[i2];
            i3++;
            i2++;
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(iArr2, 2)));
        StringBuilder sb = new StringBuilder();
        int textCompaction = textCompaction(iArr, i2, sb);
        pDF417ResultMetadata.setFileId(sb.toString());
        if (iArr[textCompaction] == BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
            int i4 = textCompaction + 1;
            int[] iArr3 = new int[(iArr[0] - i4)];
            boolean z = false;
            int i5 = 0;
            while (i4 < iArr[0] && !z) {
                int i6 = i4 + 1;
                int i7 = iArr[i4];
                if (i7 < 900) {
                    iArr3[i5] = i7;
                    i4 = i6;
                    i5++;
                } else if (i7 != MACRO_PDF417_TERMINATOR) {
                    throw FormatException.getFormatInstance();
                } else {
                    pDF417ResultMetadata.setLastSegment(true);
                    i4 = i6 + 1;
                    z = true;
                }
            }
            pDF417ResultMetadata.setOptionalData(Arrays.copyOf(iArr3, i5));
            return i4;
        } else if (iArr[textCompaction] != MACRO_PDF417_TERMINATOR) {
            return textCompaction;
        } else {
            pDF417ResultMetadata.setLastSegment(true);
            return textCompaction + 1;
        }
    }

    private static int textCompaction(int[] iArr, int i, StringBuilder sb) {
        int[] iArr2 = new int[((iArr[0] - i) * 2)];
        int[] iArr3 = new int[((iArr[0] - i) * 2)];
        boolean z = false;
        int i2 = 0;
        while (i < iArr[0] && !z) {
            int i3 = i + 1;
            int i4 = iArr[i];
            if (i4 < 900) {
                iArr2[i2] = i4 / 30;
                iArr2[i2 + 1] = i4 % 30;
                i2 += 2;
            } else if (i4 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                if (i4 != 928) {
                    switch (i4) {
                        case 900:
                            iArr2[i2] = 900;
                            i2++;
                            break;
                        case 901:
                        case 902:
                            break;
                        default:
                            switch (i4) {
                                case MACRO_PDF417_TERMINATOR /*922*/:
                                case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                    break;
                            }
                    }
                }
                i = i3 - 1;
                z = true;
            } else {
                iArr2[i2] = MODE_SHIFT_TO_BYTE_COMPACTION_MODE;
                i = i3 + 1;
                iArr3[i2] = iArr[i3];
                i2++;
            }
            i = i3;
        }
        decodeTextCompaction(iArr2, iArr3, i2, sb);
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004c, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0059, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0086, code lost:
        r4 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b7, code lost:
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00d9, code lost:
        r3 = ' ';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00f9, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00fa, code lost:
        if (r3 == 0) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00fc, code lost:
        r0.append(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00ff, code lost:
        r2 = r2 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void decodeTextCompaction(int[] r15, int[] r16, int r17, java.lang.StringBuilder r18) {
        /*
            r0 = r18
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r2 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            r4 = r1
            r5 = r2
            r2 = 0
            r1 = r17
        L_0x000b:
            if (r2 < r1) goto L_0x000e
            return
        L_0x000e:
            r6 = r15[r2]
            int[] r7 = $SWITCH_TABLE$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode()
            int r8 = r4.ordinal()
            r7 = r7[r8]
            r8 = 28
            r9 = 27
            r10 = 32
            r11 = 913(0x391, float:1.28E-42)
            r12 = 900(0x384, float:1.261E-42)
            r13 = 26
            r3 = 29
            switch(r7) {
                case 1: goto L_0x00d1;
                case 2: goto L_0x00aa;
                case 3: goto L_0x0078;
                case 4: goto L_0x005c;
                case 5: goto L_0x0047;
                case 6: goto L_0x002d;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x00f9
        L_0x002d:
            if (r6 >= r3) goto L_0x0034
            char[] r3 = PUNCT_CHARS
            char r3 = r3[r6]
            goto L_0x004c
        L_0x0034:
            if (r6 != r3) goto L_0x0039
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0039:
            if (r6 != r11) goto L_0x0042
            r3 = r16[r2]
            char r3 = (char) r3
            r0.append(r3)
            goto L_0x0059
        L_0x0042:
            if (r6 != r12) goto L_0x0059
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0047:
            if (r6 >= r13) goto L_0x004f
            int r6 = r6 + 65
            char r3 = (char) r6
        L_0x004c:
            r4 = r5
            goto L_0x00fa
        L_0x004f:
            if (r6 != r13) goto L_0x0054
            r4 = r5
            goto L_0x00d9
        L_0x0054:
            if (r6 != r12) goto L_0x0059
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0059:
            r4 = r5
            goto L_0x00f9
        L_0x005c:
            if (r6 >= r3) goto L_0x0064
            char[] r3 = PUNCT_CHARS
            char r3 = r3[r6]
            goto L_0x00fa
        L_0x0064:
            if (r6 != r3) goto L_0x0069
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0069:
            if (r6 != r11) goto L_0x0073
            r3 = r16[r2]
            char r3 = (char) r3
            r0.append(r3)
            goto L_0x00f9
        L_0x0073:
            if (r6 != r12) goto L_0x00f9
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0078:
            r7 = 25
            if (r6 >= r7) goto L_0x0082
            char[] r3 = MIXED_CHARS
            char r3 = r3[r6]
            goto L_0x00fa
        L_0x0082:
            if (r6 != r7) goto L_0x0089
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT
        L_0x0086:
            r4 = r3
            goto L_0x00f9
        L_0x0089:
            if (r6 != r13) goto L_0x008c
            goto L_0x00d9
        L_0x008c:
            if (r6 != r9) goto L_0x0091
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER
            goto L_0x0086
        L_0x0091:
            if (r6 != r8) goto L_0x0096
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x0096:
            if (r6 != r3) goto L_0x009b
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT
            goto L_0x00b7
        L_0x009b:
            if (r6 != r11) goto L_0x00a5
            r3 = r16[r2]
            char r3 = (char) r3
            r0.append(r3)
            goto L_0x00f9
        L_0x00a5:
            if (r6 != r12) goto L_0x00f9
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x00aa:
            if (r6 >= r13) goto L_0x00b0
            int r6 = r6 + 97
            char r3 = (char) r6
            goto L_0x00fa
        L_0x00b0:
            if (r6 != r13) goto L_0x00b3
            goto L_0x00d9
        L_0x00b3:
            if (r6 != r9) goto L_0x00b9
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA_SHIFT
        L_0x00b7:
            r5 = r4
            goto L_0x0086
        L_0x00b9:
            if (r6 != r8) goto L_0x00be
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED
            goto L_0x0086
        L_0x00be:
            if (r6 != r3) goto L_0x00c3
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT
            goto L_0x00b7
        L_0x00c3:
            if (r6 != r11) goto L_0x00cc
            r3 = r16[r2]
            char r3 = (char) r3
            r0.append(r3)
            goto L_0x00f9
        L_0x00cc:
            if (r6 != r12) goto L_0x00f9
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x00d1:
            if (r6 >= r13) goto L_0x00d7
            int r6 = r6 + 65
            char r3 = (char) r6
            goto L_0x00fa
        L_0x00d7:
            if (r6 != r13) goto L_0x00dc
        L_0x00d9:
            r3 = 32
            goto L_0x00fa
        L_0x00dc:
            if (r6 != r9) goto L_0x00e1
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER
            goto L_0x0086
        L_0x00e1:
            if (r6 != r8) goto L_0x00e6
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED
            goto L_0x0086
        L_0x00e6:
            if (r6 != r3) goto L_0x00eb
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT
            goto L_0x00b7
        L_0x00eb:
            if (r6 != r11) goto L_0x00f4
            r3 = r16[r2]
            char r3 = (char) r3
            r0.append(r3)
            goto L_0x00f9
        L_0x00f4:
            if (r6 != r12) goto L_0x00f9
            com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA
            goto L_0x0086
        L_0x00f9:
            r3 = 0
        L_0x00fa:
            if (r3 == 0) goto L_0x00ff
            r0.append(r3)
        L_0x00ff:
            int r2 = r2 + 1
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decodeTextCompaction(int[], int[], int, java.lang.StringBuilder):void");
    }

    private static int byteCompaction(int i, int[] iArr, Charset charset, int i2, StringBuilder sb) {
        int i3;
        long j;
        int i4;
        int i5;
        int i6 = i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i7 = MACRO_PDF417_TERMINATOR;
        int i8 = BEGIN_MACRO_PDF417_OPTIONAL_FIELD;
        int i9 = 928;
        int i10 = 902;
        long j2 = 900;
        if (i6 == 901) {
            int[] iArr2 = new int[6];
            int i11 = iArr[i2];
            int i12 = i2 + 1;
            boolean z = false;
            loop0:
            while (true) {
                i4 = 0;
                long j3 = 0;
                while (i3 < iArr[0] && !z) {
                    int i13 = i4 + 1;
                    iArr2[i4] = i11;
                    j3 = (j3 * j) + ((long) i11);
                    int i14 = i3 + 1;
                    i11 = iArr[i3];
                    if (i11 == 900 || i11 == 901 || i11 == 902 || i11 == BYTE_COMPACTION_MODE_LATCH_6 || i11 == 928 || i11 == i8 || i11 == i7) {
                        i3 = i14 - 1;
                        i4 = i13;
                        i7 = MACRO_PDF417_TERMINATOR;
                        i8 = BEGIN_MACRO_PDF417_OPTIONAL_FIELD;
                        j = 900;
                        z = true;
                    } else if (i13 % 5 != 0 || i13 <= 0) {
                        i3 = i14;
                        i4 = i13;
                        i7 = MACRO_PDF417_TERMINATOR;
                        i8 = BEGIN_MACRO_PDF417_OPTIONAL_FIELD;
                        j = 900;
                    } else {
                        int i15 = 0;
                        while (i15 < 6) {
                            byteArrayOutputStream.write((byte) ((int) (j3 >> ((5 - i15) * 8))));
                            i15++;
                            i7 = MACRO_PDF417_TERMINATOR;
                            i8 = BEGIN_MACRO_PDF417_OPTIONAL_FIELD;
                        }
                        i12 = i14;
                        j2 = 900;
                    }
                }
            }
            if (i3 != iArr[0] || i11 >= 900) {
                i5 = i4;
            } else {
                iArr2[i4] = i11;
                i5 = i4 + 1;
            }
            for (int i16 = 0; i16 < i5; i16++) {
                byteArrayOutputStream.write((byte) iArr2[i16]);
            }
        } else if (i6 == BYTE_COMPACTION_MODE_LATCH_6) {
            int i17 = i2;
            boolean z2 = false;
            loop4:
            while (true) {
                int i18 = 0;
                long j4 = 0;
                while (i3 < iArr[0] && !z2) {
                    int i19 = i3 + 1;
                    int i20 = iArr[i3];
                    if (i20 < 900) {
                        i18++;
                        j4 = (j4 * 900) + ((long) i20);
                        i17 = i19;
                    } else {
                        if (i20 != 900 && i20 != 901 && i20 != i10 && i20 != BYTE_COMPACTION_MODE_LATCH_6 && i20 != i9) {
                            if (i20 != BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
                                if (i20 != MACRO_PDF417_TERMINATOR) {
                                    i17 = i19;
                                }
                                i17 = i19 - 1;
                                z2 = true;
                            }
                        }
                        i17 = i19 - 1;
                        z2 = true;
                    }
                    if (i18 % 5 != 0 || i18 <= 0) {
                        i9 = 928;
                        i10 = 902;
                    } else {
                        int i21 = 0;
                        while (i21 < 6) {
                            byteArrayOutputStream.write((byte) ((int) (j4 >> ((5 - i21) * 8))));
                            i21++;
                            i9 = 928;
                            i10 = 902;
                        }
                    }
                }
            }
        } else {
            i3 = i2;
        }
        sb.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return i3;
    }

    private static int numericCompaction(int[] iArr, int i, StringBuilder sb) throws FormatException {
        int[] iArr2 = new int[15];
        boolean z = false;
        loop0:
        while (true) {
            int i2 = 0;
            while (i < iArr[0] && !z) {
                int i3 = i + 1;
                int i4 = iArr[i];
                if (i3 == iArr[0]) {
                    z = true;
                }
                if (i4 < 900) {
                    iArr2[i2] = i4;
                    i2++;
                } else if (i4 == 900 || i4 == 901 || i4 == BYTE_COMPACTION_MODE_LATCH_6 || i4 == 928 || i4 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD || i4 == MACRO_PDF417_TERMINATOR) {
                    i3--;
                    z = true;
                }
                if ((i2 % 15 == 0 || i4 == 902 || z) && i2 > 0) {
                    sb.append(decodeBase900toBase10(iArr2, i2));
                    i = i3;
                } else {
                    i = i3;
                }
            }
        }
        return i;
    }

    private static String decodeBase900toBase10(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf((long) iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }
}
