package com.tencent.open.utils;

import android.support.v4.view.MotionEventCompat;

/* compiled from: ProGuard */
public final class ZipShort implements Cloneable {
    private int a;

    public ZipShort(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipShort(byte[] bArr, int i) {
        this.a = (bArr[i + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        this.a += bArr[i] & 255;
    }

    public ZipShort(int i) {
        this.a = i;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ZipShort) || this.a != ((ZipShort) obj).getValue()) {
            return false;
        }
        return true;
    }

    public final byte[] getBytes() {
        return new byte[]{(byte) (this.a & 255), (byte) ((this.a & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)};
    }

    public final int getValue() {
        return this.a;
    }

    public final int hashCode() {
        return this.a;
    }
}
