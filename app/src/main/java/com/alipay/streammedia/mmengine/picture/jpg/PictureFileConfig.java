package com.alipay.streammedia.mmengine.picture.jpg;

import android.graphics.Bitmap;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;

public class PictureFileConfig extends PictureBaseConfig {
    public String srcFile;

    public static PictureFileConfig createDefault(Bitmap src) {
        PictureFileConfig config = new PictureFileConfig();
        config.srcWidth = src.getWidth();
        config.srcHeight = src.getHeight();
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        config.cropX = 0;
        config.cropY = 0;
        config.rotate = 0;
        config.cropMode = CropMode.MaxVisibility.getIndex();
        config.needMirror = false;
        config.debugLog = false;
        config.perfLog = false;
        return config;
    }

    public static PictureFileConfig createDefault() {
        PictureFileConfig config = new PictureFileConfig();
        config.srcWidth = 0;
        config.srcHeight = 0;
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        config.cropX = 0;
        config.cropY = 0;
        config.rotate = 0;
        config.needMirror = false;
        config.cropMode = CropMode.MaxVisibility.getIndex();
        config.debugLog = false;
        config.perfLog = false;
        return config;
    }
}
