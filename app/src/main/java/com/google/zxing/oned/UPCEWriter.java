package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class UPCEWriter extends UPCEANWriter {
    private static final int CODE_WIDTH = 51;

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.UPC_E) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode UPC_E, but got ".concat(String.valueOf(barcodeFormat)));
    }

    public final boolean[] encode(String str) {
        if (str.length() != 8) {
            StringBuilder sb = new StringBuilder("Requested contents should be 8 digits long, but got ");
            sb.append(str.length());
            throw new IllegalArgumentException(sb.toString());
        }
        int i = UPCEReader.CHECK_DIGIT_ENCODINGS[Integer.parseInt(str.substring(7, 8))];
        boolean[] zArr = new boolean[51];
        int appendPattern = appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        int i2 = 1;
        while (i2 <= 6) {
            int i3 = i2 + 1;
            int parseInt = Integer.parseInt(str.substring(i2, i3));
            if (((i >> (6 - i2)) & 1) == 1) {
                parseInt += 10;
            }
            appendPattern += appendPattern(zArr, appendPattern, UPCEANReader.L_AND_G_PATTERNS[parseInt], false);
            i2 = i3;
        }
        appendPattern(zArr, appendPattern, UPCEANReader.END_PATTERN, false);
        return zArr;
    }
}
