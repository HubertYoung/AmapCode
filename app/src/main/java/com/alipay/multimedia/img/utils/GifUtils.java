package com.alipay.multimedia.img.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.multimedia.img.IConfig;
import com.alipay.multimedia.io.IOUtils;
import java.io.ByteArrayOutputStream;

public class GifUtils {
    public static final String CONFIG_KEY_FRAME_CHECK = "frameCheck";
    private static final String TAG = "GifUtils";
    private static IConfig sConfig;

    public static Bitmap convert2Png(Bitmap gif) {
        Bitmap png = null;
        if (gif != null) {
            ByteArrayOutputStream baos = null;
            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                try {
                    gif.compress(CompressFormat.PNG, 100, baos2);
                    byte[] pngData = baos2.toByteArray();
                    Options opts = new Options();
                    opts.inDither = true;
                    opts.inPreferQualityOverSpeed = true;
                    if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT < 21) {
                        opts.inPurgeable = true;
                        opts.inInputShareable = true;
                        gif.recycle();
                    }
                    png = BitmapFactory.decodeByteArray(pngData, 0, pngData.length, opts);
                    IOUtils.closeQuietly(baos2);
                } catch (Throwable th) {
                    th = th;
                    baos = baos2;
                    IOUtils.closeQuietly(baos);
                    throw th;
                }
            } catch (Throwable th2) {
                t = th2;
                LogUtils.w(TAG, "convert2Png error, t: " + t);
                IOUtils.closeQuietly(baos);
                return png;
            }
        }
        return png;
    }

    public static void setIConfig(IConfig config) {
        sConfig = config;
    }

    public static boolean getFrameCheckSwitch() {
        if (sConfig != null) {
            String config = sConfig.getConfig(CONFIG_KEY_FRAME_CHECK);
            if (!TextUtils.isEmpty(config)) {
                return "true".equalsIgnoreCase(config);
            }
        }
        return false;
    }
}
