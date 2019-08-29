package com.google.zxing.qrcode.encoder;

final class BlockPair {
    private final byte[] dataBytes;
    private final byte[] errorCorrectionBytes;

    BlockPair(byte[] bArr, byte[] bArr2) {
        this.dataBytes = bArr;
        this.errorCorrectionBytes = bArr2;
    }

    public final byte[] getDataBytes() {
        return this.dataBytes;
    }

    public final byte[] getErrorCorrectionBytes() {
        return this.errorCorrectionBytes;
    }
}
