package com.alipay.android.phone.wallet.minizxing;

import java.util.Map;

public final class MultiFormatWriter implements u {
    public final BitMatrix encode(String contents, BarcodeFormat format, int width, int height) {
        return encode(contents, format, width, height, null);
    }

    public final BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        u writer;
        switch (format) {
            case QR_CODE:
                writer = new q();
                break;
            case CODE_128:
                writer = new g();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + format);
        }
        return writer.encode(contents, format, width, height, hints);
    }
}
