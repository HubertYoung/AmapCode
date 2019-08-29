package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.ResizeImagePerf;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.ImageSize;
import com.alipay.multimedia.img.decode.CropOptions;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.ImageDecoder;
import com.alipay.multimedia.io.IOUtils;
import java.io.File;
import java.io.InputStream;

public class FalconDecoderBridge {
    private boolean a = FalconUtilsBridge.isSdkMatch();
    private boolean b;

    public FalconDecoderBridge() {
        boolean z = true;
        this.b = ConfigManager.getInstance().getCommonConfigItem().imageProcessorConf.systemCropNew != 1 ? false : z;
    }

    public Bitmap cutImage_backgroud(File file, int newWidth, int newHeight) {
        long start = System.currentTimeMillis();
        CropOptions options = new CropOptions();
        options.cutSize = new ImageSize(newWidth, newHeight);
        options.autoUseAshmem = this.a;
        options.systemCropNew = this.b;
        DecodeResult result = ImageDecoder.cropBitmap(file, options);
        ResizeImagePerf.createFrom(start, file, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public Bitmap cutImage_backgroud(InputStream in, int newWidth, int newHeight) {
        long start = System.currentTimeMillis();
        CropOptions options = new CropOptions();
        options.cutSize = new ImageSize(newWidth, newHeight);
        options.autoUseAshmem = this.a;
        options.systemCropNew = this.b;
        DecodeResult result = ImageDecoder.cropBitmap(in, options);
        ResizeImagePerf.createFrom(start, (byte[]) null, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public Bitmap cutImage_new(File file, int maxLen, int minLen, float scale) {
        long start = System.currentTimeMillis();
        CropOptions options = new CropOptions();
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        ImageSize size = a(info.correctWidth, info.correctHeight, maxLen, scale);
        options.cutSize = new ImageSize(size.width, size.height);
        options.autoUseAshmem = this.a;
        options.systemCropNew = this.b;
        DecodeResult result = ImageDecoder.cropBitmap(file, options);
        ResizeImagePerf.createFrom(start, file, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public Bitmap cutImage_new(InputStream in, int maxLen, int minLen, float scale) {
        return cutImage_new(IOUtils.getBytes(in), maxLen, minLen, scale);
    }

    public Bitmap cutImage_new(byte[] data, int maxLen, int minLen, float scale) {
        long start = System.currentTimeMillis();
        CropOptions options = new CropOptions();
        ImageInfo info = ImageInfo.getImageInfo(data);
        ImageSize size = a(info.correctWidth, info.correctHeight, maxLen, scale);
        options.cutSize = new ImageSize(size.width, size.height);
        options.autoUseAshmem = this.a;
        options.systemCropNew = this.b;
        DecodeResult result = ImageDecoder.cropBitmap(data, options);
        ResizeImagePerf.createFrom(start, data, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public Bitmap cutImage_keepRatio(File file, int newWidth, int newHeight) {
        long start = System.currentTimeMillis();
        DecodeOptions options = new DecodeOptions();
        options.autoUseAshmem = this.a;
        options.mode = new MaxLenMode(Integer.valueOf(Math.max(newWidth, newHeight)));
        DecodeResult result = ImageDecoder.decodeBitmap(file, options);
        ResizeImagePerf.createFrom(start, file, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public Bitmap cutImage_keepRatio(InputStream in, int newWidth, int newHeight) {
        return cutImageKeepRatio_new(IOUtils.getBytes(in), newWidth, newHeight);
    }

    public Bitmap cutImageKeepRatio_new(byte[] data, int newWidth, int newHeight) {
        long start = System.currentTimeMillis();
        DecodeOptions options = new DecodeOptions();
        options.autoUseAshmem = this.a;
        options.mode = new MaxLenMode(Integer.valueOf(Math.max(newWidth, newHeight)));
        DecodeResult result = ImageDecoder.decodeBitmap(data, options);
        ResizeImagePerf.createFrom(start, data, options, result).submitRemoteAsync();
        return result.bitmap;
    }

    public void setIsUseNewMethod(boolean useNewMethod) {
        this.a = useNewMethod && FalconUtilsBridge.isSdkMatch();
    }

    private static ImageSize a(int oriWidth, int oriHeight, int maxLen, float scale) {
        int maxSide = maxLen;
        int minSide = (int) (((float) maxLen) * scale);
        ImageSize size = new ImageSize();
        if (oriWidth > oriHeight) {
            size.width = maxSide;
            size.height = minSide;
        } else {
            size.width = minSide;
            size.height = maxSide;
        }
        return size;
    }
}
