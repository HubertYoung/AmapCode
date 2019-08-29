package com.alipay.streammedia.mmengine;

import android.graphics.Bitmap;
import com.alipay.streammedia.mmengine.audio.AudioBaseResult;
import com.alipay.streammedia.mmengine.filter.CalcColorResult;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.JpgFilePictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureCompressResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureFileConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureHevcFileInfo;
import com.alipay.streammedia.mmengine.picture.scale.ScaleConfig;
import com.alipay.streammedia.mmengine.picture.scale.ScaleResult;
import com.alipay.streammedia.mmengine.video.VideoCompressConfig;
import com.alipay.streammedia.mmengine.video.VideoInfo;

public class MMNativeEngineNativeWrapper {
    static native PictureBaseResult PictureFileDecompress(PictureFileConfig pictureFileConfig);

    static native int audioOpusDecInit(int i, int i2, int i3);

    static native int audioOpusDecUninit();

    static native AudioBaseResult audioOpusDecoder(byte[] bArr, int i);

    static native int audioOpusEncInit(int i, int i2, int i3, boolean z);

    static native int audioOpusEncUninit();

    static native AudioBaseResult audioOpusEncoder(byte[] bArr, int i);

    static native AudioBaseResult audioProcess(byte[] bArr, int i);

    static native int audioProcessInit(int i, int i2, int i3);

    static native int audioProcessUninit();

    static native PictureCompressResult bitmapCompressToByte(BitmapPictureCompressConfig bitmapPictureCompressConfig, Bitmap bitmap);

    static native PictureCompressResult bitmapCompressToByteByProg(BitmapPictureCompressConfig bitmapPictureCompressConfig, Bitmap bitmap, int i);

    static native PictureCompressResult bitmapCompressToByteNoScale(BitmapPictureCompressConfig bitmapPictureCompressConfig, Bitmap bitmap);

    static native PictureCompressResult bitmapCompressToByteNoScaleByProg(BitmapPictureCompressConfig bitmapPictureCompressConfig, Bitmap bitmap, int i);

    static native PictureBaseResult bitmapCrop(BitmapPictureBaseConfig bitmapPictureBaseConfig, Bitmap bitmap);

    static native PictureBaseResult byteArrayCrop(BitmapPictureBaseConfig bitmapPictureBaseConfig, byte[] bArr, int i);

    static native int compositeNative(Bitmap bitmap, Bitmap bitmap2, int i, int i2, int i3, int i4);

    static native PictureBaseResult decompressByByteArray(PictureBaseConfig pictureBaseConfig, byte[] bArr, int i);

    static native VideoInfo dumpVideoInfo(String str);

    static native PictureBaseResult getBestPixelInfo(PictureFileConfig pictureFileConfig);

    static native long getCurCompressPtsNative(long j);

    static native long getOptionFlagsNative();

    static native int hevcDecoderVersion();

    static native PictureBaseResult hevcDecompressByByteArray(PictureBaseConfig pictureBaseConfig, byte[] bArr, int i);

    static native PictureBaseResult hevcFileDecompress(PictureFileConfig pictureFileConfig);

    static native PictureHevcFileInfo hevcFileGetInfo(PictureFileConfig pictureFileConfig);

    static native PictureCompressResult jpgFileCompressToByte(JpgFilePictureCompressConfig jpgFilePictureCompressConfig);

    static native PictureCompressResult jpgFileCompressToByteByProg(JpgFilePictureCompressConfig jpgFilePictureCompressConfig, int i);

    static native PictureCompressResult jpgFileCompressToByteNoScale(JpgFilePictureCompressConfig jpgFilePictureCompressConfig);

    static native PictureCompressResult jpgFileCompressToByteNoScaleByProg(JpgFilePictureCompressConfig jpgFilePictureCompressConfig, int i);

    static native int jpgFileCompressToFile(JpgFilePictureCompressConfig jpgFilePictureCompressConfig, String str);

    static native PictureBaseResult jpgFileDecompress(PictureFileConfig pictureFileConfig);

    static native PictureCompressResult jpgFileDecompressToYuv420(PictureFileConfig pictureFileConfig);

    static native CalcColorResult pictureCalcColor(Bitmap bitmap);

    static native int pictureCalcComplexity(Bitmap bitmap);

    static native byte[] pictureOil(Bitmap bitmap, int i, int i2);

    static native ScaleResult scaleImageByNative(ScaleConfig scaleConfig, Bitmap bitmap);

    static native void setOptionFlagsNative(long j);

    public static native void test1080P(byte[] bArr, int i, int i2);

    public static native void test1080P2(byte[] bArr, int i, int i2);

    static native int videoCompressByCfg(VideoCompressConfig videoCompressConfig);

    static native int videoDumpPts(String str);
}
