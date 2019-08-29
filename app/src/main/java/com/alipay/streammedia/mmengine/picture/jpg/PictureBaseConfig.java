package com.alipay.streammedia.mmengine.picture.jpg;

import android.graphics.Bitmap;

public class PictureBaseConfig {
    public static final int COMPRESS_LEVEL_AR = 2;
    public static final int COMPRESS_LEVEL_HIGH = 1;
    public static final int COMPRESS_LEVEL_MID = 0;
    public int cropMode;
    public int cropX;
    public int cropY;
    public boolean debugLog;
    public int dstHeight;
    public int dstWidth;
    public int maxDimension;
    public int minDimension;
    public boolean needMirror;
    public boolean perfLog;
    public int rotate;
    public int srcHeight;
    public int srcWidth;
    public boolean useAshMem = true;

    public enum CropMode {
        MaxVisibility(0, "最大居中模式，已其中一条边确认另外一边，最大可视"),
        TopAlign(1, "上对齐"),
        DownAlign(2, "下对齐"),
        LeftAlign(3, "左对齐"),
        RightAlign(4, "右对齐"),
        Manual(5, "手动模式，抠图");
        
        private int index;
        private String name;

        private CropMode(int index2, String name2) {
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
            CropMode[] values;
            for (CropMode t : values()) {
                if (t.getIndex() == index2) {
                    return t.getName();
                }
            }
            return "Unknown Mode";
        }
    }

    public static PictureBaseConfig createDefault(Bitmap src) {
        PictureBaseConfig config = new PictureBaseConfig();
        config.srcWidth = src.getWidth();
        config.srcHeight = src.getHeight();
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        config.cropX = 0;
        config.cropY = 0;
        config.cropMode = CropMode.MaxVisibility.getIndex();
        config.rotate = 0;
        config.needMirror = false;
        return config;
    }

    public static PictureBaseConfig createDefault() {
        PictureBaseConfig config = new PictureBaseConfig();
        config.srcWidth = 0;
        config.srcHeight = 0;
        config.dstWidth = 0;
        config.dstHeight = 0;
        config.maxDimension = 0;
        config.minDimension = 0;
        config.cropX = 0;
        config.cropY = 0;
        config.cropMode = CropMode.MaxVisibility.getIndex();
        config.rotate = 0;
        config.needMirror = false;
        config.debugLog = false;
        config.perfLog = false;
        return config;
    }
}
