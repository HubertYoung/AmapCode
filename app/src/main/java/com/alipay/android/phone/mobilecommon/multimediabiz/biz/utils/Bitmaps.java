package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class Bitmaps {
    public static final Config BITMAP_CONFIG = Config.ARGB_8888;
    public static final int BYTES_PER_PIXEL = 4;

    private static native void nativeCopyBitmap(Bitmap bitmap, int i, Bitmap bitmap2, int i2, int i3);

    private static native void nativePinBitmap(Bitmap bitmap);

    static {
        AppUtils.loadLibrary("bitmaps");
    }

    public static void pinBitmap(Bitmap bitmap) {
        checkNotNull(bitmap);
        nativePinBitmap(bitmap);
    }

    public static void copyBitmap(Bitmap dest, Bitmap src) {
        boolean z;
        boolean z2 = true;
        checkArgument(dest.getConfig() == src.getConfig());
        checkArgument(dest.isMutable());
        if (dest.getWidth() == src.getWidth()) {
            z = true;
        } else {
            z = false;
        }
        checkArgument(z);
        if (dest.getHeight() != src.getHeight()) {
            z2 = false;
        }
        checkArgument(z2);
        nativeCopyBitmap(dest, dest.getRowBytes(), src, src.getRowBytes(), dest.getHeight());
    }

    @TargetApi(19)
    public static void reconfigureBitmap(Bitmap bitmap, int width, int height) {
        checkArgument(bitmap.getAllocationByteCount() >= (width * height) * 4);
        bitmap.reconfigure(width, height, BITMAP_CONFIG);
    }

    public static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new IllegalArgumentException();
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}
