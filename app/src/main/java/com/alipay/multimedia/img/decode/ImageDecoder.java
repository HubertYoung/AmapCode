package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.StaticOptions;
import java.io.File;
import java.io.InputStream;

public class ImageDecoder {
    public static DecodeResult decodeBitmap(File file, DecodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.decodeBitmap(file, options);
        }
        return SystemImageDecoder.decodeBitmap(file, options);
    }

    public static DecodeResult decodeBitmap(byte[] data, DecodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.decodeBitmap(data, options);
        }
        return SystemImageDecoder.decodeBitmap(data, options);
    }

    public static DecodeResult decodeBitmap(InputStream in, DecodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.decodeBitmap(in, options);
        }
        return SystemImageDecoder.decodeBitmap(in, options);
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, DecodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.processBitmap(bitmap, rotation, options);
        }
        return SystemImageDecoder.processBitmap(bitmap, rotation, options);
    }

    public static DecodeResult processBitmap(byte[] yuv, ImageInfo info, DecodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.processBitmap(yuv, info, options);
        }
        return SystemImageDecoder.processBitmap(yuv, info, options);
    }

    public static DecodeResult cropBitmap(File file, CropOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.cropBitmap(file, options);
        }
        return SystemImageDecoder.cropBitmap(file, options);
    }

    public static DecodeResult cropBitmap(byte[] data, CropOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.cropBitmap(data, options);
        }
        return SystemImageDecoder.cropBitmap(data, options);
    }

    public static DecodeResult cropBitmap(InputStream in, CropOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.cropBitmap(in, options);
        }
        return SystemImageDecoder.cropBitmap(in, options);
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, CropOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.processBitmap(bitmap, rotation, options);
        }
        return SystemImageDecoder.processBitmap(bitmap, rotation, options);
    }

    public static DecodeResult processBitmap(byte[] yuvData, ImageInfo info, CropOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageDecoder.processBitmap(yuvData, info, options);
        }
        return SystemImageDecoder.processBitmap(yuvData, info, options);
    }
}
