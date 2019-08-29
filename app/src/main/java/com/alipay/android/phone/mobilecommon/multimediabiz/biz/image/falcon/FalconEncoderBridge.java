package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.CompressImagePerf;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import com.alipay.multimedia.img.encode.ImageEncoder;
import com.alipay.multimedia.img.encode.mode.MaxLenMode;
import com.alipay.multimedia.img.encode.mode.NoneScaleMode;
import com.alipay.multimedia.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class FalconEncoderBridge {
    public static final int LEVEL_HIGH = 2;

    public ByteArrayOutputStream compressImage(File imageFile, int quality, int width, int height) {
        long start = System.currentTimeMillis();
        EncodeOptions options = new EncodeOptions();
        options.mode = new MaxLenMode(Math.max(width, height));
        if (quality == 2) {
            options.quality = 1;
        }
        EncodeResult result = ImageEncoder.compress(imageFile, options);
        CompressImagePerf.createFrom(start, imageFile, options, result).submitRemoteAsync();
        if (result.isSuccess()) {
            return a(result.encodeData);
        }
        return null;
    }

    public ByteArrayOutputStream GenerateCompressImage_new(File file, int level) {
        EncodeOptions options = new EncodeOptions();
        options.mode = new MaxLenMode(1280);
        if (level == 2) {
            options.quality = 1;
        }
        EncodeResult result = ImageEncoder.compress(file, options);
        if (result.isSuccess()) {
            return a(result.encodeData);
        }
        return null;
    }

    public static byte[] CompressImageBitmapDefaultNoChange(Bitmap bitmap, int level, int imageType) {
        long start = System.currentTimeMillis();
        EncodeOptions options = new EncodeOptions();
        options.mode = new NoneScaleMode();
        if (level == 2) {
            options.quality = 1;
        }
        EncodeResult result = ImageEncoder.compress(bitmap, options);
        CompressImagePerf.createFrom(start, bitmap, options, result).submitRemoteAsync();
        if (result.isSuccess()) {
            return result.encodeData;
        }
        return null;
    }

    public void setIsUseNewMethod(boolean isuseNewMethod) {
    }

    public ByteArrayOutputStream compressImage(byte[] imageData, int quality, int width, int height) {
        long start = System.currentTimeMillis();
        EncodeOptions options = new EncodeOptions();
        options.mode = new MaxLenMode(Math.max(width, height));
        if (quality == 2) {
            options.quality = 1;
        }
        EncodeResult result = ImageEncoder.compress(imageData, options);
        CompressImagePerf.createFrom(start, imageData, options, result).submitRemoteAsync();
        if (result.isSuccess()) {
            return a(result.encodeData);
        }
        return null;
    }

    public ByteArrayOutputStream compressImage(InputStream in, int quality, int width, int height) {
        return compressImage(IOUtils.getBytes(in), quality, width, height);
    }

    private static ByteArrayOutputStream a(byte[] data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
        baos.write(data);
        return baos;
    }
}
