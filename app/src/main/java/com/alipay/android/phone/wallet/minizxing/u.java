package com.alipay.android.phone.wallet.minizxing;

import java.util.Map;

public interface u {
    BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map);
}
