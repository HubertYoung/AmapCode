package com.alipay.diskcache.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.OutputStream;

public class ImageUtils {
    public static boolean compressBitmap(Bitmap bitmap, OutputStream os) {
        boolean ret = false;
        try {
            if (checkBitmap(bitmap)) {
                if (bitmap.hasAlpha()) {
                    bitmap.compress(CompressFormat.PNG, 100, os);
                } else {
                    bitmap.compress(CompressFormat.JPEG, 80, os);
                }
                ret = true;
            }
        } catch (Exception e) {
            LogHelper.e("ImageUtils", "compressBitmap error", e);
        } finally {
            IOUtils.closeQuietly(os);
        }
        return ret;
    }

    public static boolean checkBitmap(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }
}
