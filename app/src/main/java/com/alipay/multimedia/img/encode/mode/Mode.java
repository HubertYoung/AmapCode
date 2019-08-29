package com.alipay.multimedia.img.encode.mode;

public abstract class Mode {
    public static final int TYPE_CENTER_CROP = 2;
    public static final int TYPE_MAX_LEN = 0;
    public static final int TYPE_MIN_LEN = 1;
    public static final int TYPE_NONE_SCALE = 4;
    public static final int TYPE_SPECIFIC_CROP = 3;
    public final int type;

    public Mode(int type2) {
        this.type = type2;
    }
}
