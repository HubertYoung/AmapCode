package com.alipay.android.phone.wallet.minizxing;

public enum ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);
    
    private static final ErrorCorrectionLevel[] a = null;
    private final int b;

    static {
        a = new ErrorCorrectionLevel[]{M, L, H, Q};
    }

    private ErrorCorrectionLevel(int bits) {
        this.b = bits;
    }

    public final int getBits() {
        return this.b;
    }

    public static ErrorCorrectionLevel forBits(int bits) {
        if (bits >= 0 && bits < a.length) {
            return a[bits];
        }
        throw new IllegalArgumentException();
    }
}
