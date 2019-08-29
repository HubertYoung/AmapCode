package com.alipay.multimedia.img.utils;

import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.SoLoader;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureFileConfig;
import java.io.File;

public class ImageAssist {
    private static final String TAG = "ImageAssist";

    public static class ImagePlaceHolderOptions {
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

        public static ImagePlaceHolderOptions createDefault() {
            ImagePlaceHolderOptions options = new ImagePlaceHolderOptions();
            options.cropMode = CropMode.MaxVisibility.getIndex();
            return options;
        }

        public String toString() {
            return "ImagePlaceHolderOptions{needMirror=" + this.needMirror + ", srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstWidth=" + this.dstWidth + ", dstHeight=" + this.dstHeight + ", cropX=" + this.cropX + ", cropY=" + this.cropY + ", cropMode=" + this.cropMode + ", maxDimension=" + this.maxDimension + ", minDimension=" + this.minDimension + ", rotate=" + this.rotate + '}';
        }
    }

    public static class ImagePlaceHolderRect {
        public int cropLeft;
        public int cropTop;
        public int dstHeight;
        public int dstWidth;
        public int retCode;
        public int srcHeight;
        public int srcWidth;

        public String toString() {
            return "ImagePlaceHolderRect{srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstWidth=" + this.dstWidth + ", dstHeight=" + this.dstHeight + ", cropLeft=" + this.cropLeft + ", cropTop=" + this.cropTop + ", retCode=" + this.retCode + '}';
        }
    }

    static {
        SoLoader.loadLibraryOnce();
    }

    public static ImagePlaceHolderRect calculateImageRect(ImagePlaceHolderOptions options) {
        PictureBaseResult result;
        String absolutePath;
        if (StaticOptions.supportNativeProcess) {
            PictureFileConfig cfg = PictureFileConfig.createDefault();
            if (options.jpgFile == null) {
                absolutePath = null;
            } else {
                absolutePath = options.jpgFile.getAbsolutePath();
            }
            cfg.srcFile = absolutePath;
            cfg.srcWidth = options.srcWidth;
            cfg.srcHeight = options.srcHeight;
            cfg.dstWidth = options.dstWidth;
            cfg.dstHeight = options.dstHeight;
            cfg.maxDimension = options.maxDimension;
            cfg.minDimension = options.minDimension;
            cfg.cropX = options.cropX;
            cfg.cropY = options.cropY;
            cfg.cropMode = options.cropMode;
            cfg.rotate = options.rotate;
            cfg.needMirror = options.needMirror;
            try {
                result = MMNativeEngineApi.calculateImageRect(cfg);
            } catch (MMNativeException e) {
                result = new PictureBaseResult();
                result.retCode = -1;
            }
        } else {
            result = calculateImageRectSys(options);
        }
        ImagePlaceHolderRect rect = new ImagePlaceHolderRect();
        rect.cropLeft = result.cropLeft;
        rect.cropTop = result.cropTop;
        rect.dstHeight = result.dstHeight;
        rect.dstWidth = result.dstWidth;
        rect.retCode = result.retCode;
        rect.srcWidth = result.srcWidth;
        rect.srcHeight = result.srcHeight;
        LogUtils.d(TAG, "calculateImageRect opts: " + options + ", result: " + rect);
        return rect;
    }

    private static PictureBaseResult calculateImageRectSys(ImagePlaceHolderOptions options) {
        int dstWidth;
        int dstHeight;
        PictureBaseResult result = new PictureBaseResult();
        int srcWidth = options.srcWidth;
        int srcHeight = options.srcHeight;
        int rotation = options.rotate;
        if (FileUtils.checkFile(options.jpgFile)) {
            ImageInfo info = ImageInfo.getImageInfo(options.jpgFile.getAbsolutePath());
            srcWidth = info.width;
            srcHeight = info.height;
            rotation = info.rotation;
        }
        if (rotation == 90 || rotation == 270) {
            int temp = srcWidth;
            srcWidth = srcHeight;
            srcHeight = temp;
        }
        if (options.dstWidth > 0 && options.dstHeight > 0) {
            double maxRatio = Math.max(((double) options.dstWidth) / ((double) srcWidth), ((double) options.dstHeight) / ((double) srcHeight));
            dstWidth = (int) (((double) srcWidth) * maxRatio);
            dstHeight = (int) (((double) srcHeight) * maxRatio);
            if (dstWidth > options.dstWidth) {
                dstWidth = options.dstWidth;
            }
            if (dstHeight > options.dstHeight) {
                dstHeight = options.dstHeight;
            }
        } else if (options.maxDimension > 0) {
            double ratio = ((double) options.maxDimension) / ((double) Math.max(srcWidth, srcHeight));
            dstWidth = (int) (((double) srcWidth) * ratio);
            dstHeight = (int) (((double) srcHeight) * ratio);
        } else if (options.minDimension > 0) {
            double ratio2 = ((double) options.minDimension) / ((double) Math.min(srcWidth, srcHeight));
            dstWidth = (int) (((double) srcWidth) * ratio2);
            dstHeight = (int) (((double) srcHeight) * ratio2);
        } else {
            dstWidth = srcWidth;
            dstHeight = srcHeight;
        }
        result.cropLeft = 0;
        result.cropTop = 0;
        result.srcWidth = srcWidth;
        result.srcHeight = srcHeight;
        result.dstWidth = dstWidth;
        result.dstHeight = dstHeight;
        LogUtils.d(TAG, "calculateImageRectSys options: " + options + ", result: [sw: " + srcWidth + ", sh: " + srcHeight + ", dw: " + dstWidth + ", dh: " + dstHeight + "]");
        return result;
    }

    public static boolean isSuperPic(int width, int height) {
        if (width <= 0 || height <= 0) {
            return true;
        }
        float scale_ori = ((float) width) / ((float) height);
        if (scale_ori < 0.5f || scale_ori > 2.0f) {
            return true;
        }
        return false;
    }

    public static float getScale(int targetWidth, int targetHeight, float bitmapWidth, float bitmapHeight) {
        if (targetWidth > 0 && targetHeight > 0) {
            return Math.max(((float) targetWidth) / bitmapWidth, ((float) targetHeight) / bitmapHeight);
        }
        if (targetWidth > 0) {
            return ((float) targetWidth) / bitmapWidth;
        }
        if (targetHeight > 0) {
            return ((float) targetHeight) / bitmapHeight;
        }
        return 1.0f;
    }
}
