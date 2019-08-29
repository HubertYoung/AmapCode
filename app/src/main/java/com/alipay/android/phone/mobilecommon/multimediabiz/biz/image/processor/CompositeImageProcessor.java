package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import com.alipay.multimedia.img.encode.ImageEncoder;
import com.alipay.multimedia.img.encode.mode.NoneScaleMode;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.filter.CompositeConfig;
import java.io.ByteArrayOutputStream;

public class CompositeImageProcessor {
    public static byte[] compositeImage(Bitmap src, Bitmap overlap, Rect rect, Bundle extra) {
        byte[] result = null;
        CompositeConfig cfg = new CompositeConfig();
        cfg.setSrc(src);
        cfg.setSuperimosed(overlap);
        cfg.setX(rect.left);
        cfg.setY(rect.top);
        cfg.setSuperimosed_w(rect.width());
        cfg.setSuperimosed_h(rect.height());
        try {
            MMNativeEngineApi.composite(cfg);
            result = a(src, extra);
        } catch (Throwable e) {
            Logger.E((String) "CompositeImage", e, (String) "compositeImage error", new Object[0]);
        }
        Logger.D("CompositeImage", "compositeImage src: " + ImageUtils.getBitmapInfo(src) + ", overlap: " + ImageUtils.getBitmapInfo(overlap) + ", rect: " + rect + ", extra: " + extra + ", result: " + result, new Object[0]);
        return result;
    }

    private static byte[] a(Bitmap src, Bundle extra) {
        int compressLevel;
        int i = 0;
        boolean z = true;
        if (extra == null) {
            compressLevel = 1;
        } else {
            compressLevel = extra.getInt(MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, 1);
        }
        int imageType = extra == null ? 1 : extra.getInt("imageType", 1);
        if (imageType == 0) {
            if (CompareUtils.in(Integer.valueOf(compressLevel), Integer.valueOf(0), Integer.valueOf(1))) {
                EncodeOptions options = new EncodeOptions();
                options.requireOutputInfo = false;
                if (compressLevel != 0) {
                    i = 1;
                }
                options.quality = i;
                options.mode = new NoneScaleMode();
                EncodeResult result = ImageEncoder.compress(src, options);
                if (result.isSuccess()) {
                    return result.encodeData;
                }
                return null;
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (imageType != 0) {
            z = false;
        }
        if (ImageUtils.compressBitmap(src, baos, 100, z)) {
            return baos.toByteArray();
        }
        return null;
    }
}
