package com.alipay.android.phone.wallet.minizxing;

final class c {
    private final byte[] a;
    private final byte[] b;

    c(byte[] data, byte[] errorCorrection) {
        this.a = data;
        this.b = errorCorrection;
    }

    public final byte[] a() {
        return this.a;
    }

    public final byte[] b() {
        return this.b;
    }
}
