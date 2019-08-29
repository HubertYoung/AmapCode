package com.alipay.mobile.beehive.stackblur;

import android.graphics.Bitmap;

public class StackBlur {
    public static final int BLUR_RADIUS = 20;

    public static Bitmap blurBitmap(Bitmap originalBitmap) {
        return blurBitmap(originalBitmap, 20);
    }

    public static Bitmap blurBitmap(Bitmap originalBitmap, int radius) {
        if (originalBitmap != null) {
            return new StackBlurManager(originalBitmap).process(radius);
        }
        return originalBitmap;
    }
}
