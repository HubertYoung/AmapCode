package com.alipay.streammedia.mmengine.picture.jpg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig.ImageType;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;

public class BitmapPictureCompressConfig extends BitmapPictureBaseConfig {
    public int qualityLevel;

    public static BitmapPictureCompressConfig createDefault(Bitmap src) {
        BitmapPictureCompressConfig config = new BitmapPictureCompressConfig();
        config.srcWidth = src.getWidth();
        config.srcHeight = src.getHeight();
        if (src.getConfig() == Config.ARGB_8888) {
            config.pixfmtConfig = ImageType.ARGB_8888.getIndex();
            config.dstWidth = 0;
            config.dstHeight = 0;
            config.maxDimension = 0;
            config.minDimension = 0;
            config.qualityLevel = 1;
            config.cropX = 0;
            config.cropY = 0;
            config.rotate = 0;
            config.cropMode = CropMode.MaxVisibility.getIndex();
            config.needMirror = false;
            config.debugLog = false;
            config.perfLog = false;
            return config;
        }
        Log.d("BitmapPictureBaseConfig", "bitmap config no support!");
        return null;
    }
}
