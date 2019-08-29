package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon;

import android.os.Build.VERSION;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageAssist;
import com.alipay.multimedia.img.utils.ImageAssist.ImagePlaceHolderOptions;
import com.alipay.multimedia.img.utils.ImageAssist.ImagePlaceHolderRect;
import java.io.File;
import java.io.FileDescriptor;

public class FalconUtilsBridge {
    public static boolean isSdkMatch() {
        return VERSION.SDK_INT >= 15 && VERSION.SDK_INT < 21;
    }

    public static boolean calcultDesWidthHeight_new(File file, int[] desLen) {
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        desLen[0] = info.correctWidth;
        desLen[1] = info.correctHeight;
        if (desLen[0] != 0) {
            return true;
        }
        return false;
    }

    public static boolean calcultDesWidthHeight_new(FileDescriptor fd, int[] desLen) {
        ImageInfo info = ImageInfo.getImageInfo(fd);
        desLen[0] = info.correctWidth;
        desLen[1] = info.correctHeight;
        if (desLen[0] != 0) {
            return true;
        }
        return false;
    }

    public static boolean calcultDesWidthHeight_new(File file, int width, int height, int maxLen, float scale, int[] desLen) {
        ImageInfo info;
        ImagePlaceHolderRect rect;
        int minLen = (int) (((float) maxLen) * scale);
        if (file != null) {
            info = ImageInfo.getImageInfo(file.getAbsolutePath());
        } else {
            info = new ImageInfo();
            info.width = width;
            info.height = height;
        }
        if (!a(info, scale)) {
            rect = a(info, maxLen);
        } else {
            int dstWidth = minLen;
            int dstHeight = maxLen;
            if (width > height) {
                dstWidth = maxLen;
                dstHeight = minLen;
            }
            rect = a(info, dstWidth, dstHeight);
        }
        if (rect.retCode != 0) {
            return false;
        }
        desLen[0] = rect.dstWidth;
        desLen[1] = rect.dstHeight;
        return true;
    }

    public static boolean calcultDesWidthHeight_new(File file, int width, int height, int maxLen, int[] desLen) {
        return calcultDesWidthHeight_new(file, width, height, maxLen, 0.5f, desLen);
    }

    public static void calcultDesWidthHeight(int width, int height, int maxLen, int[] desLen) {
        ImagePlaceHolderRect rect;
        if (width > 0 && height > 0 && maxLen > 0) {
            int minLen = maxLen / 2;
            ImageInfo info = new ImageInfo();
            info.width = width;
            info.height = height;
            if (!a(info, 0.5f)) {
                rect = a(info, maxLen);
            } else {
                int dstWidth = minLen;
                int dstHeight = maxLen;
                if (width > height) {
                    dstWidth = maxLen;
                    dstHeight = minLen;
                }
                rect = a(info, dstWidth, dstHeight);
            }
            if (rect.retCode == 0) {
                desLen[0] = rect.dstWidth;
                desLen[1] = rect.dstHeight;
            }
        }
    }

    private static ImagePlaceHolderRect a(ImageInfo info, int width, int height) {
        ImagePlaceHolderOptions opts = new ImagePlaceHolderOptions();
        opts.srcWidth = info.width;
        opts.srcHeight = info.height;
        opts.dstWidth = width;
        opts.dstHeight = height;
        opts.rotate = info.rotation;
        return ImageAssist.calculateImageRect(opts);
    }

    private static ImagePlaceHolderRect a(ImageInfo info, int maxLen) {
        ImagePlaceHolderOptions opts = new ImagePlaceHolderOptions();
        opts.srcWidth = info.width;
        opts.srcHeight = info.height;
        opts.maxDimension = maxLen;
        opts.rotate = info.rotation;
        return ImageAssist.calculateImageRect(opts);
    }

    private static boolean a(ImageInfo info, float scale) {
        return ((float) Math.min(info.width, info.height)) / ((float) Math.max(info.width, info.height)) < scale;
    }
}
