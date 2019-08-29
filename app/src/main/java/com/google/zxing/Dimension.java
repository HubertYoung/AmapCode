package com.google.zxing;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public final class Dimension {
    private final int height;
    private final int width;

    public Dimension(int i, int i2) {
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException();
        }
        this.width = i;
        this.height = i2;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Dimension)) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        if (this.width == dimension.width && this.height == dimension.height) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (this.width * 32713) + this.height;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(String.valueOf(this.width));
        sb.append(DictionaryKeys.CTRLXY_X);
        sb.append(this.height);
        return sb.toString();
    }
}
