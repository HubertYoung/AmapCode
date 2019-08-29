package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.j256.ormlite.stmt.query.SimpleComparison;

final class SimpleToken extends Token {
    private final short bitCount;
    private final short value;

    SimpleToken(Token token, int i, int i2) {
        super(token);
        this.value = (short) i;
        this.bitCount = (short) i2;
    }

    /* access modifiers changed from: 0000 */
    public final void appendTo(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
        sb.append(Integer.toBinaryString((this.value & ((1 << this.bitCount) - 1)) | (1 << this.bitCount) | (1 << this.bitCount)).substring(1));
        sb.append('>');
        return sb.toString();
    }
}
