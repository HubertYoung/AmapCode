package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class Size {
    private final int mHeight;
    private final int mWidth;

    public Size(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size other = (Size) obj;
        if (this.mWidth == other.mWidth && this.mHeight == other.mHeight) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.mWidth + DictionaryKeys.CTRLXY_X + this.mHeight;
    }

    public int hashCode() {
        return this.mHeight ^ ((this.mWidth << 16) | (this.mWidth >>> 16));
    }
}
