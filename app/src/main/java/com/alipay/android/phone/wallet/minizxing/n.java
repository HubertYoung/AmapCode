package com.alipay.android.phone.wallet.minizxing;

import java.util.Map;

public abstract class n implements u {
    public abstract boolean[] a(String str);

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        } else if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + width + 'x' + height);
        } else {
            int sidesMargin = 10;
            if (hints != null) {
                Integer sidesMarginInt = (Integer) hints.get(EncodeHintType.MARGIN);
                if (sidesMarginInt != null) {
                    sidesMargin = sidesMarginInt.intValue();
                }
            }
            return a(a(contents), width, height, sidesMargin);
        }
    }

    private static BitMatrix a(boolean[] code, int width, int height, int sidesMargin) {
        int inputWidth = code.length;
        int fullWidth = inputWidth + sidesMargin;
        int outputWidth = Math.max(width, fullWidth);
        int outputHeight = Math.max(1, height);
        int multiple = outputWidth / fullWidth;
        BitMatrix output = new BitMatrix(outputWidth, outputHeight);
        int inputX = 0;
        int outputX = (outputWidth - (inputWidth * multiple)) / 2;
        while (inputX < inputWidth) {
            if (code[inputX]) {
                output.a(outputX, 0, multiple, outputHeight);
            }
            inputX++;
            outputX += multiple;
        }
        return output;
    }

    protected static int a(boolean[] target, int pos, int[] pattern) {
        boolean color = true;
        int numAdded = 0;
        int length = pattern.length;
        int i = 0;
        while (i < length) {
            int len = pattern[i];
            int j = 0;
            int pos2 = pos;
            while (j < len) {
                target[pos2] = color;
                j++;
                pos2++;
            }
            numAdded += len;
            color = !color;
            i++;
            pos = pos2;
        }
        return numAdded;
    }
}
