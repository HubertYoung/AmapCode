package com.alipay.ma.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import java.io.File;

/* compiled from: ImageTool */
public final class a {
    public static Bitmap a(File srcImageFile) {
        return b(srcImageFile);
    }

    private static Bitmap b(File srcImageFile) {
        if (srcImageFile == null || !srcImageFile.exists()) {
            return null;
        }
        Bitmap resultBitmap = null;
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcImageFile.getPath(), opts);
        opts.inSampleSize = a(opts, Math.min(1024, 1024));
        opts.inJustDecodeBounds = false;
        opts.inInputShareable = true;
        opts.inPurgeable = true;
        try {
            return BitmapFactory.decodeFile(srcImageFile.getPath(), opts);
        } catch (OutOfMemoryError e) {
            Log.d(RPCDataItems.SWITCH_TAG_LOG, "e" + e.getLocalizedMessage());
            return resultBitmap;
        }
    }

    private static int a(Options options, int minSideLength) {
        int initialSize = b(options, minSideLength);
        if (initialSize > 8) {
            return ((initialSize + 7) / 8) * 8;
        }
        int roundedSize = 1;
        while (roundedSize < initialSize) {
            roundedSize <<= 1;
        }
        return roundedSize;
    }

    private static int b(Options options, int minSideLength) {
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        int lowerBound = (int) Math.floor(Math.sqrt((w * h) / 1048576.0d));
        int upperBound = minSideLength == -1 ? 128 : (int) Math.min(Math.floor(w / ((double) minSideLength)), Math.floor(h / ((double) minSideLength)));
        if (upperBound >= lowerBound && minSideLength != -1) {
            return upperBound;
        }
        return lowerBound;
    }
}
