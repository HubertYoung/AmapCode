package com.alipay.streammedia.mmengine.picture;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public final class NativeEngineApiCreateBitmap {
    public static Bitmap createBitmap(int width, int height, boolean useAshMem) {
        Bitmap bitmap = null;
        if (useAshMem) {
            try {
                byte[] data = EmptyJpegGenerator.generate((short) width, (short) height);
                Options options = new Options();
                options.inInputShareable = true;
                options.inPurgeable = true;
                options.inDither = true;
                options.inMutable = true;
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } catch (Throwable th) {
                return null;
            }
        }
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        }
        bitmap.eraseColor(0);
        return bitmap;
    }
}
