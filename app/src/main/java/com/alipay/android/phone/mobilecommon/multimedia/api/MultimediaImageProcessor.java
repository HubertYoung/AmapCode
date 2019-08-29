package com.alipay.android.phone.mobilecommon.multimedia.api;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderRect;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APCropOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.effect.APCalcColorResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifInfo;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.io.File;
import java.io.InputStream;

public abstract class MultimediaImageProcessor extends ExternalService {
    public static final String COMPOSITE_INT_KEY_COMPRESS_LEVEL = "compress";
    public static final int COMPOSITE_INT_VAL_COMPRESS_LEVEL_HIGH = 1;
    public static final int COMPOSITE_INT_VAL_COMPRESS_LEVEL_NORMAL = 0;
    public static final int COMPOSITE_INT_VAL_COMPRESS_LEVEL_ORIGINAL = 3;

    public abstract APCalcColorResult calcPictureColor(Bitmap bitmap);

    public abstract int calcPictureComplexity(Bitmap bitmap);

    public abstract APImagePlaceHolderRect calculateImageRect(APImagePlaceHolderOptions aPImagePlaceHolderOptions);

    public abstract byte[] compositeImage(Bitmap bitmap, Bitmap bitmap2, Rect rect, Bundle bundle);

    public abstract APEncodeResult compress(Bitmap bitmap, APEncodeOptions aPEncodeOptions);

    public abstract APEncodeResult compress(File file, APEncodeOptions aPEncodeOptions);

    public abstract APEncodeResult compress(InputStream inputStream, APEncodeOptions aPEncodeOptions);

    public abstract APEncodeResult compress(byte[] bArr, APEncodeOptions aPEncodeOptions);

    public abstract APGifInfo compressGif(String str, String str2, Bundle bundle);

    public abstract APEncodeResult compressToTempFile(File file, APEncodeOptions aPEncodeOptions);

    public abstract APDecodeResult cropBitmap(File file, APCropOptions aPCropOptions);

    public abstract APDecodeResult cropBitmap(InputStream inputStream, APCropOptions aPCropOptions);

    public abstract APDecodeResult cropBitmap(byte[] bArr, APCropOptions aPCropOptions);

    public abstract APDecodeResult decodeBitmap(File file, APDecodeOptions aPDecodeOptions);

    public abstract APDecodeResult decodeBitmap(InputStream inputStream, APDecodeOptions aPDecodeOptions);

    public abstract APDecodeResult decodeBitmap(byte[] bArr, APDecodeOptions aPDecodeOptions);

    public abstract Bitmap extractGifFrame(String str, int i);

    public abstract APImageInfo parseGifInfo(String str);

    public abstract APImageInfo parseImageInfo(String str);

    public abstract byte[] pictureOilFilter(Bitmap bitmap);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }
}
