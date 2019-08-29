package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor;

import java.io.File;

public class APImagePlaceHolderOptions {
    public int cropMode;
    public int cropX;
    public int cropY;
    public int dstHeight;
    public int dstWidth;
    public File jpgFile;
    public int maxDimension;
    public int minDimension;
    public boolean needMirror;
    public int rotate;
    public int srcHeight;
    public int srcWidth;

    public static APImagePlaceHolderOptions createDefault() {
        APImagePlaceHolderOptions options = new APImagePlaceHolderOptions();
        options.cropMode = 0;
        return options;
    }

    public String toString() {
        return "APImagePlaceHolderOptions{needMirror=" + this.needMirror + ", srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstWidth=" + this.dstWidth + ", dstHeight=" + this.dstHeight + ", cropX=" + this.cropX + ", cropY=" + this.cropY + ", cropMode=" + this.cropMode + ", maxDimension=" + this.maxDimension + ", minDimension=" + this.minDimension + ", rotate=" + this.rotate + '}';
    }
}
