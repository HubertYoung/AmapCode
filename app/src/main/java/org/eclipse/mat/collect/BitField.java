package org.eclipse.mat.collect;

import java.io.Serializable;

public final class BitField implements Serializable {
    private static final long serialVersionUID = 1;
    private int[] bits;

    public BitField(int i) {
        this.bits = new int[(((i - 1) >>> 5) + 1)];
    }

    public final void set(int i) {
        int[] iArr = this.bits;
        int i2 = i >>> 5;
        iArr[i2] = (1 << (i & 31)) | iArr[i2];
    }

    public final void clear(int i) {
        int[] iArr = this.bits;
        int i2 = i >>> 5;
        iArr[i2] = (~(1 << (i & 31))) & iArr[i2];
    }

    public final boolean get(int i) {
        return ((1 << (i & 31)) & this.bits[i >>> 5]) != 0;
    }
}
