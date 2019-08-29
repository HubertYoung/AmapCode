package com.alipay.streammedia.mmengine.picture.scale;

import android.graphics.Bitmap;

public class ScaleConfig {
    public boolean debugLog;
    public int dstHeight;
    public int dstWidth;
    public boolean perfLog;
    public int srcHeight;
    public int srcWidth;
    public boolean useAshMem = true;

    public static ScaleConfig createDefault(Bitmap src, double scaleX) {
        ScaleConfig config = new ScaleConfig();
        config.srcWidth = src.getWidth();
        config.srcHeight = src.getHeight();
        config.dstWidth = (int) (((double) src.getWidth()) * scaleX);
        config.dstHeight = (int) (((double) src.getHeight()) * scaleX);
        config.perfLog = true;
        config.debugLog = true;
        return config;
    }
}
