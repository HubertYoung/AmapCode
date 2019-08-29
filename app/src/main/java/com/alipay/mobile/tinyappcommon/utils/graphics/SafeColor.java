package com.alipay.mobile.tinyappcommon.utils.graphics;

public class SafeColor {
    public static final SafeColor INVALID_COLOR = new SafeColor(false);
    public final boolean valid;
    public final int value;

    public SafeColor(boolean valid2) {
        this.valid = valid2;
        this.value = 0;
    }

    public SafeColor(int value2) {
        this.valid = true;
        this.value = value2;
    }

    public SafeColor(boolean valid2, int value2) {
        this.valid = valid2;
        this.value = value2;
    }
}
