package com.jiuyan.inimage.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import java.io.File;

/* compiled from: BitmapUtil */
public final class c {
    public static final boolean a(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }

    public static final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
            return false;
        }
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            if (options.outWidth <= 0 || options.outHeight <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Bitmap a(View view, Config config) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap = null;
        if (drawingCache != null) {
            bitmap = drawingCache.copy(config, false);
        }
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
