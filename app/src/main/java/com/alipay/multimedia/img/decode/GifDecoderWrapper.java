package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import com.alipay.streammedia.mmengine.picture.gif.GifDecoder;
import com.alipay.streammedia.mmengine.picture.gif.GifParseResult;
import java.io.File;

public class GifDecoderWrapper {
    private static final String TAG = "GifDecoderWrapper";

    static {
        try {
            GifDecoder.loadLibrariesOnce(new SoLibLoader());
        } catch (Throwable t) {
            LogUtils.e("APMGifView", "load library error", t);
        }
    }

    public static DecodeResult decode(File file, DecodeOptions options) {
        GifParseResult result;
        int i = 0;
        Bitmap bitmap = null;
        if (FileUtils.checkFile(file)) {
            boolean decoder = null;
            try {
                decoder = GifDecoder.generateGifDecoder(file.getAbsolutePath(), 4096, 0);
                int width = decoder.getWidth();
                int height = decoder.getHeight();
                LogUtils.d(TAG, "decode file: " + file + ", width: " + width + ", height: " + height + ", frameCheck: " + options.frameCheck);
                if (width > 0 && height > 0) {
                    bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                    decoder = options.frameCheck;
                    if (!decoder || options.frameIndex == 1) {
                        result = decoder.renderNextFrameByIndex(bitmap, options.frameIndex);
                    } else {
                        result = decoder.renderNextFrameByIndex(bitmap, 1);
                        if (result == null || NativeExceptionCode.ONLY_ONE_FRAME_IN_GIF.getIndex() == result.getCode()) {
                            bitmap = null;
                        } else {
                            result = decoder.renderNextFrameByIndex(bitmap, options.frameIndex);
                        }
                    }
                    if (result == null || !(result.getCode() == 0 || NativeExceptionCode.ONLY_ONE_FRAME_IN_GIF.getIndex() == result.getCode())) {
                        bitmap = null;
                    } else if (bitmap != null) {
                        bitmap.setHasAlpha(true);
                    }
                }
                if (decoder != null) {
                    try {
                        decoder.release();
                    } catch (MMNativeException e) {
                        LogUtils.e(TAG, "decode release code=" + e.getCode(), e);
                    }
                }
            } catch (Exception e2) {
                LogUtils.e(TAG, "decode error, ", e2);
                if (decoder != null) {
                    try {
                        decoder.release();
                    } catch (MMNativeException e3) {
                        LogUtils.e(TAG, "decode release code=" + e3.getCode(), e3);
                    }
                }
            } finally {
                if (decoder != null) {
                    try {
                        decoder.release();
                    } catch (MMNativeException e4) {
                        LogUtils.e(TAG, "decode release code=" + e4.getCode(), e4);
                    }
                }
            }
            if (bitmap == null) {
                try {
                    bitmap = InnerDecoder.decodeFile(file, options, ImageInfo.getImageInfo(file.getAbsolutePath())).bitmap;
                } catch (Throwable t) {
                    LogUtils.e(TAG, "decode, degrade use BitmapFactory decode error, ", t);
                }
            }
        }
        DecodeResult result2 = new DecodeResult();
        if (bitmap == null) {
            i = -1;
        }
        result2.code = i;
        result2.bitmap = bitmap;
        return result2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.multimedia.img.decode.DecodeResult decode(byte[] r9, com.alipay.multimedia.img.decode.DecodeOptions r10) {
        /*
            r2 = 0
            if (r9 == 0) goto L_0x001b
            int r6 = r9.length
            if (r6 <= 0) goto L_0x001b
            r5 = 0
            java.lang.String r6 = "gif_"
            java.lang.String r7 = "apmgif"
            java.io.File r5 = java.io.File.createTempFile(r6, r7)     // Catch:{ Exception -> 0x0023 }
            com.alipay.multimedia.io.FileUtils.safeCopyToFile(r9, r5)     // Catch:{ Exception -> 0x0023 }
            com.alipay.multimedia.img.decode.DecodeResult r2 = decode(r5, r10)     // Catch:{ Exception -> 0x0023 }
            if (r5 == 0) goto L_0x001b
            r5.delete()
        L_0x001b:
            if (r2 != 0) goto L_0x0022
            com.alipay.multimedia.img.decode.DecodeResult r2 = new com.alipay.multimedia.img.decode.DecodeResult
            r2.<init>()
        L_0x0022:
            return r2
        L_0x0023:
            r1 = move-exception
            java.lang.String r6 = "GifDecoderWrapper"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0075 }
            java.lang.String r8 = "decode error, "
            r7.<init>(r8)     // Catch:{ all -> 0x0075 }
            java.lang.StringBuilder r7 = r7.append(r9)     // Catch:{ all -> 0x0075 }
            java.lang.String r8 = ", e: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0075 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0075 }
            com.alipay.multimedia.img.utils.LogUtils.e(r6, r7, r1)     // Catch:{ all -> 0x0075 }
            r0 = 0
            com.alipay.multimedia.img.ImageInfo r6 = com.alipay.multimedia.img.ImageInfo.getImageInfo(r9)     // Catch:{ Throwable -> 0x0069 }
            com.alipay.multimedia.img.decode.DecodeResult r2 = com.alipay.multimedia.img.decode.InnerDecoder.decodeByteArray(r9, r6, r10)     // Catch:{ Throwable -> 0x0069 }
            boolean r6 = r2.isSuccess()     // Catch:{ Throwable -> 0x0069 }
            if (r6 == 0) goto L_0x0056
            android.graphics.Bitmap r6 = r2.bitmap     // Catch:{ Throwable -> 0x0069 }
            if (r6 == 0) goto L_0x0056
            r6 = 0
            android.graphics.Bitmap r0 = com.alipay.multimedia.img.utils.GifUtils.convert2Png(r6)     // Catch:{ Throwable -> 0x0069 }
        L_0x0056:
            r3 = r2
        L_0x0057:
            com.alipay.multimedia.img.decode.DecodeResult r2 = new com.alipay.multimedia.img.decode.DecodeResult     // Catch:{ all -> 0x007c }
            r2.<init>()     // Catch:{ all -> 0x007c }
            if (r0 != 0) goto L_0x0073
            r6 = -1
        L_0x005f:
            r2.code = r6     // Catch:{ all -> 0x0075 }
            r2.bitmap = r0     // Catch:{ all -> 0x0075 }
            if (r5 == 0) goto L_0x001b
            r5.delete()
            goto L_0x001b
        L_0x0069:
            r4 = move-exception
            java.lang.String r6 = "GifDecoderWrapper"
            java.lang.String r7 = "decode bytes, degrade use BitmapFactory decode error, "
            com.alipay.multimedia.img.utils.LogUtils.e(r6, r7, r4)     // Catch:{ all -> 0x0075 }
            r3 = r2
            goto L_0x0057
        L_0x0073:
            r6 = 0
            goto L_0x005f
        L_0x0075:
            r6 = move-exception
        L_0x0076:
            if (r5 == 0) goto L_0x007b
            r5.delete()
        L_0x007b:
            throw r6
        L_0x007c:
            r6 = move-exception
            r2 = r3
            goto L_0x0076
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.img.decode.GifDecoderWrapper.decode(byte[], com.alipay.multimedia.img.decode.DecodeOptions):com.alipay.multimedia.img.decode.DecodeResult");
    }
}
