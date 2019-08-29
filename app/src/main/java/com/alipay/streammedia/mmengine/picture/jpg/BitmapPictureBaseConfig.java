package com.alipay.streammedia.mmengine.picture.jpg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;

public class BitmapPictureBaseConfig extends PictureBaseConfig {
    public int pixfmtConfig;

    public enum ImageType {
        ARGB_8888(0, "packed ARGB 8:8:8:8, 32bpp, ARGBARGB..."),
        YUV_420(1, "planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)");
        
        private int index;
        private String name;

        private ImageType(int index2, String name2) {
            this.index = index2;
            this.name = name2;
        }

        public final int getIndex() {
            return this.index;
        }

        public final void setIndex(int index2) {
            this.index = index2;
        }

        public final String getName() {
            return this.name;
        }

        public final void setName(String name2) {
            this.name = name2;
        }

        public static String getNameByIndex(int index2) {
            ImageType[] values;
            for (ImageType t : values()) {
                if (t.getIndex() == index2) {
                    return t.getName();
                }
            }
            return "Unknown Type";
        }
    }

    public static BitmapPictureBaseConfig createDefault(Bitmap src) {
        BitmapPictureBaseConfig config = new BitmapPictureBaseConfig();
        config.srcWidth = src.getWidth();
        config.srcHeight = src.getHeight();
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        if (src.getConfig() == Config.ARGB_8888) {
            config.pixfmtConfig = ImageType.ARGB_8888.getIndex();
            config.cropX = 0;
            config.cropY = 0;
            config.cropMode = CropMode.MaxVisibility.getIndex();
            config.needMirror = false;
            config.rotate = 0;
            config.debugLog = false;
            config.perfLog = false;
            return config;
        }
        Log.d("BitmapPictureBaseConfig", "bitmap config no support!");
        return null;
    }
}
