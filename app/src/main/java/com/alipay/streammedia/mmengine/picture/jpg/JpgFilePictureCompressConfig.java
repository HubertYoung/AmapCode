package com.alipay.streammedia.mmengine.picture.jpg;

import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;

public class JpgFilePictureCompressConfig extends PictureBaseConfig {
    public int qualityLevel;
    public String srcFile;

    public static JpgFilePictureCompressConfig createDefault() {
        JpgFilePictureCompressConfig config = new JpgFilePictureCompressConfig();
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.qualityLevel = 1;
        config.cropX = 0;
        config.cropY = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        config.needMirror = false;
        config.rotate = 0;
        config.cropMode = CropMode.MaxVisibility.getIndex();
        config.debugLog = false;
        config.perfLog = false;
        return config;
    }
}
