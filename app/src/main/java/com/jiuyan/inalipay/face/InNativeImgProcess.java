package com.jiuyan.inalipay.face;

import android.graphics.Bitmap;

public class InNativeImgProcess {
    public static native int getMeasure(Bitmap bitmap, int[] iArr);

    static {
        System.loadLibrary("InNativeProcess");
    }
}
