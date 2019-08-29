package com.alipay.multimedia.js.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions;
import java.util.concurrent.atomic.AtomicInteger;

public class Base64Utils {
    public Base64Utils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static Bitmap decodeBase64(String data) {
        return ((MultimediaImageProcessor) Utils.getService(MultimediaImageProcessor.class)).decodeBitmap(Base64.decode(extractBase64(data), 2), new APDecodeOptions()).bitmap;
    }

    public static String encodeToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, 2);
    }

    public static boolean hasBase64Prefix(String content, AtomicInteger endIndex) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        String lowContentPre = content;
        if (content.length() > 150) {
            lowContentPre = content.substring(0, 150).toLowerCase();
        }
        int index = -1;
        if (lowContentPre.startsWith("data")) {
            index = lowContentPre.indexOf(";base64,");
            if (endIndex != null) {
                endIndex.set(index + 8);
            }
        }
        if (index >= 0) {
            return true;
        }
        return false;
    }

    public static String extractBase64(String content) {
        AtomicInteger endIndex = new AtomicInteger(-1);
        if (!hasBase64Prefix(content, endIndex) || endIndex.get() <= 0) {
            return content;
        }
        return content.substring(endIndex.get());
    }

    public static byte[] decodeToBytes(String base64) {
        byte[] data = null;
        if (TextUtils.isEmpty(base64)) {
            return data;
        }
        try {
            return Base64.decode(extractBase64(base64), 2);
        } catch (Exception e) {
            Logger.error("Base64Utils", "decodeToBytes error, base64: " + base64, e);
            return data;
        }
    }
}
