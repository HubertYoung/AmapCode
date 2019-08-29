package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e6 A[LOOP:0: B:1:0x001e->B:59:0x00e6, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00c5 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.zxing.common.DecoderResult decode(byte[] r18, com.google.zxing.qrcode.decoder.Version r19, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r20, java.util.Map<com.google.zxing.DecodeHintType, ?> r21) throws com.google.zxing.FormatException {
        /*
            r0 = r19
            com.google.zxing.common.BitSource r7 = new com.google.zxing.common.BitSource
            r8 = r18
            r7.<init>(r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r1 = 50
            r9.<init>(r1)
            java.util.ArrayList r10 = new java.util.ArrayList
            r11 = 1
            r10.<init>(r11)
            r1 = 0
            r2 = -1
            r12 = 0
            r14 = r12
            r13 = 0
            r15 = -1
            r16 = -1
        L_0x001e:
            int r1 = r7.available()     // Catch:{ IllegalArgumentException -> 0x00e9 }
            r2 = 4
            if (r1 >= r2) goto L_0x0029
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.TERMINATOR     // Catch:{ IllegalArgumentException -> 0x00e9 }
        L_0x0027:
            r6 = r1
            goto L_0x0032
        L_0x0029:
            int r1 = r7.readBits(r2)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.forBits(r1)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x0027
        L_0x0032:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.TERMINATOR     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 == r1) goto L_0x005e
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.FNC1_FIRST_POSITION     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 == r1) goto L_0x00bf
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.FNC1_SECOND_POSITION     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x0040
            goto L_0x00bf
        L_0x0040:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.STRUCTURED_APPEND     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x0061
            int r1 = r7.available()     // Catch:{ IllegalArgumentException -> 0x00e9 }
            r2 = 16
            if (r1 >= r2) goto L_0x0051
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x00e9 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x00e9 }
        L_0x0051:
            r1 = 8
            int r2 = r7.readBits(r1)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            int r1 = r7.readBits(r1)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            r16 = r1
            r15 = r2
        L_0x005e:
            r11 = r6
            goto L_0x00c1
        L_0x0061:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.ECI     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x0074
            int r1 = parseECIValue(r7)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            com.google.zxing.common.CharacterSetECI r14 = com.google.zxing.common.CharacterSetECI.getCharacterSetECIByValue(r1)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r14 != 0) goto L_0x005e
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x00e9 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x00e9 }
        L_0x0074:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.HANZI     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x008a
            int r1 = r7.readBits(r2)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            int r2 = r6.getCharacterCountBits(r0)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            int r2 = r7.readBits(r2)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r1 != r11) goto L_0x005e
            decodeHanziSegment(r7, r9, r2)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x005e
        L_0x008a:
            int r1 = r6.getCharacterCountBits(r0)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            int r3 = r7.readBits(r1)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.NUMERIC     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x009a
            decodeNumericSegment(r7, r9, r3)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x005e
        L_0x009a:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.ALPHANUMERIC     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x00a2
            decodeAlphanumericSegment(r7, r9, r3, r13)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x005e
        L_0x00a2:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.BYTE     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r6 != r1) goto L_0x00b1
            r1 = r7
            r2 = r9
            r4 = r14
            r5 = r10
            r11 = r6
            r6 = r21
            decodeByteSegment(r1, r2, r3, r4, r5, r6)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x00c1
        L_0x00b1:
            r11 = r6
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.KANJI     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r11 != r1) goto L_0x00ba
            decodeKanjiSegment(r7, r9, r3)     // Catch:{ IllegalArgumentException -> 0x00e9 }
            goto L_0x00c1
        L_0x00ba:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x00e9 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x00e9 }
        L_0x00bf:
            r11 = r6
            r13 = 1
        L_0x00c1:
            com.google.zxing.qrcode.decoder.Mode r1 = com.google.zxing.qrcode.decoder.Mode.TERMINATOR     // Catch:{ IllegalArgumentException -> 0x00e9 }
            if (r11 != r1) goto L_0x00e6
            com.google.zxing.common.DecoderResult r7 = new com.google.zxing.common.DecoderResult
            java.lang.String r2 = r9.toString()
            boolean r0 = r10.isEmpty()
            if (r0 == 0) goto L_0x00d3
            r3 = r12
            goto L_0x00d4
        L_0x00d3:
            r3 = r10
        L_0x00d4:
            if (r20 != 0) goto L_0x00d8
            r4 = r12
            goto L_0x00dd
        L_0x00d8:
            java.lang.String r0 = r20.toString()
            r4 = r0
        L_0x00dd:
            r0 = r7
            r1 = r8
            r5 = r15
            r6 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return r7
        L_0x00e6:
            r11 = 1
            goto L_0x001e
        L_0x00e9:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.decoder.DecodedBitStreamParser.decode(byte[], com.google.zxing.qrcode.decoder.Version, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, java.util.Map):com.google.zxing.common.DecoderResult");
    }

    private static void decodeHanziSegment(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[(i * 2)];
        int i2 = 0;
        while (i > 0) {
            int readBits = bitSource.readBits(13);
            int i3 = (readBits % 96) | ((readBits / 96) << 8);
            int i4 = i3 + (i3 < 959 ? 41377 : 42657);
            bArr[i2] = (byte) ((i4 >> 8) & 255);
            bArr[i2 + 1] = (byte) (i4 & 255);
            i2 += 2;
            i--;
        }
        try {
            sb.append(new String(bArr, StringUtils.GB2312));
        } catch (UnsupportedEncodingException unused) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeKanjiSegment(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[(i * 2)];
        int i2 = 0;
        while (i > 0) {
            int readBits = bitSource.readBits(13);
            int i3 = (readBits % 192) | ((readBits / 192) << 8);
            int i4 = i3 + (i3 < 7936 ? 33088 : 49472);
            bArr[i2] = (byte) (i4 >> 8);
            bArr[i2 + 1] = (byte) i4;
            i2 += 2;
            i--;
        }
        try {
            sb.append(new String(bArr, StringUtils.SHIFT_JIS));
        } catch (UnsupportedEncodingException unused) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeByteSegment(BitSource bitSource, StringBuilder sb, int i, CharacterSetECI characterSetECI, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        String str;
        if (i * 8 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) bitSource.readBits(8);
        }
        if (characterSetECI == null) {
            str = StringUtils.guessEncoding(bArr, map);
        } else {
            str = characterSetECI.name();
        }
        try {
            sb.append(new String(bArr, str));
            collection.add(bArr);
        } catch (UnsupportedEncodingException unused) {
            throw FormatException.getFormatInstance();
        }
    }

    private static char toAlphaNumericChar(int i) throws FormatException {
        if (i < ALPHANUMERIC_CHARS.length) {
            return ALPHANUMERIC_CHARS[i];
        }
        throw FormatException.getFormatInstance();
    }

    private static void decodeAlphanumericSegment(BitSource bitSource, StringBuilder sb, int i, boolean z) throws FormatException {
        while (i > 1) {
            if (bitSource.available() < 11) {
                throw FormatException.getFormatInstance();
            }
            int readBits = bitSource.readBits(11);
            sb.append(toAlphaNumericChar(readBits / 45));
            sb.append(toAlphaNumericChar(readBits % 45));
            i -= 2;
        }
        if (i == 1) {
            if (bitSource.available() < 6) {
                throw FormatException.getFormatInstance();
            }
            sb.append(toAlphaNumericChar(bitSource.readBits(6)));
        }
        if (z) {
            for (int length = sb.length(); length < sb.length(); length++) {
                if (sb.charAt(length) == '%') {
                    if (length < sb.length() - 1) {
                        int i2 = length + 1;
                        if (sb.charAt(i2) == '%') {
                            sb.deleteCharAt(i2);
                        }
                    }
                    sb.setCharAt(length, 29);
                }
            }
        }
    }

    private static void decodeNumericSegment(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        while (i >= 3) {
            if (bitSource.available() < 10) {
                throw FormatException.getFormatInstance();
            }
            int readBits = bitSource.readBits(10);
            if (readBits >= 1000) {
                throw FormatException.getFormatInstance();
            }
            sb.append(toAlphaNumericChar(readBits / 100));
            sb.append(toAlphaNumericChar((readBits / 10) % 10));
            sb.append(toAlphaNumericChar(readBits % 10));
            i -= 3;
        }
        if (i != 2) {
            if (i == 1) {
                if (bitSource.available() < 4) {
                    throw FormatException.getFormatInstance();
                }
                int readBits2 = bitSource.readBits(4);
                if (readBits2 >= 10) {
                    throw FormatException.getFormatInstance();
                }
                sb.append(toAlphaNumericChar(readBits2));
            }
        } else if (bitSource.available() < 7) {
            throw FormatException.getFormatInstance();
        } else {
            int readBits3 = bitSource.readBits(7);
            if (readBits3 >= 100) {
                throw FormatException.getFormatInstance();
            }
            sb.append(toAlphaNumericChar(readBits3 / 10));
            sb.append(toAlphaNumericChar(readBits3 % 10));
        }
    }

    private static int parseECIValue(BitSource bitSource) throws FormatException {
        int readBits = bitSource.readBits(8);
        if ((readBits & 128) == 0) {
            return readBits & 127;
        }
        if ((readBits & 192) == 128) {
            return bitSource.readBits(8) | ((readBits & 63) << 8);
        }
        if ((readBits & 224) == 192) {
            return bitSource.readBits(16) | ((readBits & 31) << 16);
        }
        throw FormatException.getFormatInstance();
    }
}
