package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

final class AI013103decoder extends AI013x0xDecoder {
    /* access modifiers changed from: protected */
    public final int checkWeight(int i) {
        return i;
    }

    AI013103decoder(BitArray bitArray) {
        super(bitArray);
    }

    /* access modifiers changed from: protected */
    public final void addWeightCode(StringBuilder sb, int i) {
        sb.append("(3103)");
    }
}
