package com.alipay.android.phone.wallet.minizxing;

import com.alipay.android.phone.wallet.minizxing.t.b;
import com.alp.o.mzx.a.c;
import com.alp.o.mzx.a.d;
import com.alp.o.mzx.a.f;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class h {
    private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};

    private static int a(d matrix) {
        return k.a(matrix) + k.b(matrix) + k.c(matrix) + k.d(matrix);
    }

    public static o a(String content, ErrorCorrectionLevel ecLevel, Map<EncodeHintType, ?> hints) {
        c mixedMode;
        String encoding = hints == null ? null : (String) hints.get(EncodeHintType.CHARACTER_SET);
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        try {
            int byteLength = content.getBytes(Charset.forName(encoding)).length;
            m modex = a(content, encoding);
            if (modex == m.KANJI) {
                mixedMode = c.a(m.KANJI, content.length(), byteLength);
            } else if (!hints.containsKey(EncodeHintType.MIXED_ENCODING) || ((Boolean) hints.get(EncodeHintType.MIXED_ENCODING)).booleanValue()) {
                mixedMode = b(content, encoding);
            } else {
                mixedMode = c.a(modex, content.length(), byteLength);
            }
            b headerBits = new b();
            if (mixedMode.a(0) == m.BYTE && !"ISO-8859-1".equals(encoding)) {
                e eci = e.a(encoding);
                if (eci != null) {
                    a(eci, headerBits);
                }
            }
            t version = a(headerBits.a() + a(mixedMode, a(headerBits.a() + a(mixedMode, t.a(1)), ecLevel)), ecLevel);
            b headerAndDataBits = new b();
            headerAndDataBits.a(headerBits);
            Iterator<f> it = mixedMode.b(version.a()).iterator();
            while (it.hasNext()) {
                f seg = it.next();
                headerAndDataBits.a(seg.c.a(), 4);
                b dataBits = new b();
                a(content.substring(seg.a, seg.a + seg.b), seg.c, dataBits, encoding);
                a(seg.c == m.BYTE ? dataBits.b() : seg.b, version, seg.c, headerAndDataBits);
                headerAndDataBits.a(dataBits);
            }
            b ecBlocks = version.a(ecLevel);
            int numDataBytes = version.b() - ecBlocks.c();
            a(numDataBytes, headerAndDataBits);
            b finalBits = a(headerAndDataBits, version.b(), numDataBytes, ecBlocks.b());
            o qrCode = new o();
            qrCode.a(ecLevel);
            qrCode.a(mixedMode.a(1));
            qrCode.a(version);
            int dimension = version.c();
            d matrix = new d(dimension, dimension);
            int maskPattern = a(finalBits, ecLevel, version, matrix);
            qrCode.a(maskPattern);
            l.a(finalBits, ecLevel, version, maskPattern, matrix);
            qrCode.a(matrix);
            return qrCode;
        } catch (Exception e) {
            throw e;
        }
    }

    private static int a(c mixedMode, t versionForNumber) {
        int baseBits = mixedMode.c(versionForNumber.a());
        Iterator<f> it = mixedMode.b(versionForNumber.a()).iterator();
        while (it.hasNext()) {
            f seg = it.next();
            baseBits += seg.c.a(versionForNumber) - seg.c.a(t.a(1));
        }
        return baseBits;
    }

    private static int a(int code) {
        if (code < a.length) {
            return a[code];
        }
        return -1;
    }

    private static m a(String content, String encoding) {
        if (!"Shift_JIS".equals(encoding)) {
            boolean hasNumeric = false;
            boolean hasAlphanumeric = false;
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c >= '0' && c <= '9') {
                    hasNumeric = true;
                } else if (a((int) c) == -1) {
                    return m.BYTE;
                } else {
                    hasAlphanumeric = true;
                }
            }
            if (hasAlphanumeric) {
                return m.ALPHANUMERIC;
            }
            if (hasNumeric) {
                return m.NUMERIC;
            }
            return m.BYTE;
        } else if (a(content)) {
            return m.KANJI;
        } else {
            return m.BYTE;
        }
    }

    private static c b(String content, String encoding) {
        if (!"Shift_JIS".equals(encoding)) {
            return d.a().a(content, encoding);
        }
        return c.a(a(content) ? m.KANJI : m.BYTE, content.length(), content.getBytes(Charset.forName("Shift_JIS")).length);
    }

    private static boolean a(String content) {
        try {
            byte[] bytes = content.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < length; i += 2) {
                int byte1 = bytes[i] & 255;
                if ((byte1 < 129 || byte1 > 159) && (byte1 < 224 || byte1 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    private static int a(b bits, ErrorCorrectionLevel ecLevel, t version, d matrix) {
        int minPenalty = Integer.MAX_VALUE;
        int bestMaskPattern = -1;
        for (int maskPattern = 0; maskPattern < 8; maskPattern++) {
            l.a(bits, ecLevel, version, maskPattern, matrix);
            int penalty = a(matrix);
            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestMaskPattern = maskPattern;
            }
        }
        return bestMaskPattern;
    }

    private static t a(int numInputBits, ErrorCorrectionLevel ecLevel) {
        for (int versionNum = 2; versionNum <= 40; versionNum++) {
            t version = t.a(versionNum);
            if (version.b() - version.a(ecLevel).c() >= (numInputBits + 7) / 8) {
                return version;
            }
        }
        throw new WriterException((String) "Data too big");
    }

    private static void a(int numDataBytes, b bits) {
        int capacity = numDataBytes * 8;
        if (bits.a() > capacity) {
            throw new WriterException("data bits cannot fit in the QR Code" + bits.a() + " > " + capacity);
        }
        for (int i = 0; i < 4 && bits.a() < capacity; i++) {
            bits.a(false);
        }
        int numBitsInLastByte = bits.a() & 7;
        if (numBitsInLastByte > 0) {
            for (int i2 = numBitsInLastByte; i2 < 8; i2++) {
                bits.a(false);
            }
        }
        int numPaddingBytes = numDataBytes - bits.b();
        for (int i3 = 0; i3 < numPaddingBytes; i3++) {
            bits.a((i3 & 1) == 0 ? 236 : 17, 8);
        }
        if (bits.a() != capacity) {
            throw new WriterException((String) "Bits size does not equal capacity");
        }
    }

    private static void a(int numTotalBytes, int numDataBytes, int numRSBlocks, int blockID, int[] numDataBytesInBlock, int[] numECBytesInBlock) {
        if (blockID >= numRSBlocks) {
            throw new WriterException((String) "Block ID too large");
        }
        int numRsBlocksInGroup2 = numTotalBytes % numRSBlocks;
        int numRsBlocksInGroup1 = numRSBlocks - numRsBlocksInGroup2;
        int numTotalBytesInGroup1 = numTotalBytes / numRSBlocks;
        int numDataBytesInGroup1 = numDataBytes / numRSBlocks;
        int numDataBytesInGroup2 = numDataBytesInGroup1 + 1;
        int numEcBytesInGroup1 = numTotalBytesInGroup1 - numDataBytesInGroup1;
        int numEcBytesInGroup2 = (numTotalBytesInGroup1 + 1) - numDataBytesInGroup2;
        if (numEcBytesInGroup1 != numEcBytesInGroup2) {
            throw new WriterException((String) "EC bytes mismatch");
        } else if (numRSBlocks != numRsBlocksInGroup1 + numRsBlocksInGroup2) {
            throw new WriterException((String) "RS blocks mismatch");
        } else if (numTotalBytes != ((numDataBytesInGroup1 + numEcBytesInGroup1) * numRsBlocksInGroup1) + ((numDataBytesInGroup2 + numEcBytesInGroup2) * numRsBlocksInGroup2)) {
            throw new WriterException((String) "Total bytes mismatch");
        } else if (blockID < numRsBlocksInGroup1) {
            numDataBytesInBlock[0] = numDataBytesInGroup1;
            numECBytesInBlock[0] = numEcBytesInGroup1;
        } else {
            numDataBytesInBlock[0] = numDataBytesInGroup2;
            numECBytesInBlock[0] = numEcBytesInGroup2;
        }
    }

    private static b a(b bits, int numTotalBytes, int numDataBytes, int numRSBlocks) {
        if (bits.b() != numDataBytes) {
            throw new WriterException((String) "Number of bits and data bytes does not match");
        }
        int dataBytesOffset = 0;
        int maxNumDataBytes = 0;
        int maxNumEcBytes = 0;
        Collection<c> blocks = new ArrayList<>(numRSBlocks);
        for (int i = 0; i < numRSBlocks; i++) {
            int[] numDataBytesInBlock = new int[1];
            int[] numEcBytesInBlock = new int[1];
            a(numTotalBytes, numDataBytes, numRSBlocks, i, numDataBytesInBlock, numEcBytesInBlock);
            int size = numDataBytesInBlock[0];
            byte[] dataBytes = new byte[size];
            bits.a(dataBytesOffset * 8, dataBytes, size);
            byte[] ecBytes = a(dataBytes, numEcBytesInBlock[0]);
            blocks.add(new c(dataBytes, ecBytes));
            maxNumDataBytes = Math.max(maxNumDataBytes, size);
            maxNumEcBytes = Math.max(maxNumEcBytes, ecBytes.length);
            dataBytesOffset += numDataBytesInBlock[0];
        }
        if (numDataBytes != dataBytesOffset) {
            throw new WriterException((String) "Data bytes does not match offset");
        }
        b result = new b();
        for (int i2 = 0; i2 < maxNumDataBytes; i2++) {
            for (c a2 : blocks) {
                byte[] dataBytes2 = a2.a();
                if (i2 < dataBytes2.length) {
                    result.a(dataBytes2[i2], 8);
                }
            }
        }
        for (int i3 = 0; i3 < maxNumEcBytes; i3++) {
            for (c b : blocks) {
                byte[] ecBytes2 = b.b();
                if (i3 < ecBytes2.length) {
                    result.a(ecBytes2[i3], 8);
                }
            }
        }
        if (numTotalBytes == result.b()) {
            return result;
        }
        throw new WriterException("Interleaving error: " + numTotalBytes + " and " + result.b() + " differ.");
    }

    private static byte[] a(byte[] dataBytes, int numEcBytesInBlock) {
        int numDataBytes = dataBytes.length;
        int[] toEncode = new int[(numDataBytes + numEcBytesInBlock)];
        for (int i = 0; i < numDataBytes; i++) {
            toEncode[i] = dataBytes[i] & 255;
        }
        new r(i.e).a(toEncode, numEcBytesInBlock);
        byte[] ecBytes = new byte[numEcBytesInBlock];
        for (int i2 = 0; i2 < numEcBytesInBlock; i2++) {
            ecBytes[i2] = (byte) toEncode[numDataBytes + i2];
        }
        return ecBytes;
    }

    private static void a(int numLetters, t version, m mode, b bits) {
        int numBits = mode.a(version);
        if (numLetters >= (1 << numBits)) {
            throw new WriterException(numLetters + " is bigger than " + ((1 << numBits) - 1));
        }
        bits.a(numLetters, numBits);
    }

    private static void a(String content, m mode, b bits, String encoding) {
        switch (mode) {
            case NUMERIC:
                a((CharSequence) content, bits);
                return;
            case ALPHANUMERIC:
                b((CharSequence) content, bits);
                return;
            case BYTE:
                a(content, bits, encoding);
                return;
            case KANJI:
                a(content, bits);
                return;
            default:
                throw new WriterException("Invalid mode: " + mode);
        }
    }

    private static void a(CharSequence content, b bits) {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int num1 = content.charAt(i) - '0';
            if (i + 2 < length) {
                int num3 = content.charAt(i + 2) - '0';
                bits.a((num1 * 100) + ((content.charAt(i + 1) - '0') * 10) + num3, 10);
                i += 3;
            } else if (i + 1 < length) {
                bits.a((num1 * 10) + (content.charAt(i + 1) - '0'), 7);
                i += 2;
            } else {
                bits.a(num1, 4);
                i++;
            }
        }
    }

    private static void b(CharSequence content, b bits) {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int code1 = a((int) content.charAt(i));
            if (code1 == -1) {
                throw new WriterException();
            } else if (i + 1 < length) {
                int code2 = a((int) content.charAt(i + 1));
                if (code2 == -1) {
                    throw new WriterException();
                }
                bits.a((code1 * 45) + code2, 11);
                i += 2;
            } else {
                bits.a(code1, 6);
                i++;
            }
        }
    }

    private static void a(String content, b bits, String encoding) {
        try {
            for (byte b : content.getBytes(encoding)) {
                bits.a(b, 8);
            }
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException((Throwable) uee);
        }
    }

    private static void a(String content, b bits) {
        try {
            byte[] bytes = content.getBytes("Shift_JIS");
            int length = bytes.length;
            for (int i = 0; i < length; i += 2) {
                int byte2 = bytes[i + 1] & 255;
                int code = ((bytes[i] & 255) << 8) | byte2;
                int subtracted = -1;
                if (code >= 33088 && code <= 40956) {
                    subtracted = code - 33088;
                } else if (code >= 57408 && code <= 60351) {
                    subtracted = code - 49472;
                }
                if (subtracted == -1) {
                    throw new WriterException((String) "Invalid byte sequence");
                }
                bits.a(((subtracted >> 8) * 192) + (subtracted & 255), 13);
            }
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException((Throwable) uee);
        }
    }

    private static void a(e eci, b bits) {
        bits.a(m.ECI.a(), 4);
        bits.a(eci.a(), 8);
    }
}
