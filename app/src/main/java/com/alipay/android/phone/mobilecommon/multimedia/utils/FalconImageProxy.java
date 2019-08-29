package com.alipay.android.phone.mobilecommon.multimedia.utils;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APCropOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APMaxLenMode;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APNoneScaleMode;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class FalconImageProxy {
    private static final int LEVEL_HIGH = 2;
    private static MultimediaImageProcessor mImageProcessor;

    private static MultimediaImageProcessor getImageProcessor() {
        if (mImageProcessor == null) {
            mImageProcessor = (MultimediaImageProcessor) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageProcessor.class.getName());
        }
        return mImageProcessor;
    }

    public static int isSuperHeight(int ImgWidth, int ImgHeight, int ScreenWidth, int ScreenHeight) {
        if (ImgHeight <= 0 || ImgWidth <= 0) {
            return 0;
        }
        float scale_ori = ((float) ImgWidth) / ((float) ImgHeight);
        if (scale_ori < 0.5f) {
            return 1;
        }
        if (scale_ori > 2.0f) {
            return 2;
        }
        return 0;
    }

    public static Bitmap cutImage_backgroud(File file, int targetWidth, int targetHeight) {
        APCropOptions options = new APCropOptions();
        options.cutSize = new Size(targetWidth, targetHeight);
        options.scaleType = 1;
        return getImageProcessor().cropBitmap(file, options).bitmap;
    }

    public static Bitmap cutImage_backgroud(File file, int targetWidth, int targetHeight, boolean useThumbnailData) {
        APCropOptions options = new APCropOptions();
        options.canUseJpgThumbnailData = useThumbnailData;
        options.cutSize = new Size(targetWidth, targetHeight);
        options.scaleType = 1;
        return getImageProcessor().cropBitmap(file, options).bitmap;
    }

    public static Bitmap cutImage_backgroud(InputStream in, int targetWidth, int targetHeight) {
        APCropOptions options = new APCropOptions();
        options.cutSize = new Size(targetWidth, targetHeight);
        options.scaleType = 1;
        return getImageProcessor().cropBitmap(in, options).bitmap;
    }

    public static byte[] CompressImageBitmapDefaultNoChange(Bitmap bitmap, int level) {
        APEncodeOptions options = new APEncodeOptions();
        options.mode = new APNoneScaleMode();
        if (level == 2) {
            options.quality = 1;
        }
        APEncodeResult result = getImageProcessor().compress(bitmap, options);
        if (result.isSuccess()) {
            return result.encodeData;
        }
        return null;
    }

    public static ByteArrayOutputStream GenerateCompressImage_new(File file, int level) {
        APEncodeOptions options = new APEncodeOptions();
        options.mode = new APMaxLenMode(1280);
        if (level == 2) {
            options.quality = 1;
        }
        APEncodeResult result = getImageProcessor().compress(file, options);
        if (result.isSuccess()) {
            return byteArray2ByteArrayOutputStream(result.encodeData);
        }
        return null;
    }

    private static ByteArrayOutputStream byteArray2ByteArrayOutputStream(byte[] data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
        baos.write(data);
        return baos;
    }
}
