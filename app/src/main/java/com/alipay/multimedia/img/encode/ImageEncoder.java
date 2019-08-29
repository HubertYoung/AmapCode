package com.alipay.multimedia.img.encode;

import android.graphics.Bitmap;
import com.alipay.multimedia.img.base.StaticOptions;
import java.io.File;
import java.io.InputStream;

public class ImageEncoder {
    public static EncodeResult compress(File file, EncodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageEncoder.compress(file, options);
        }
        return SystemImageEncoder.compress(file, options);
    }

    public static EncodeResult compress(byte[] data, EncodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageEncoder.compress(data, options);
        }
        return SystemImageEncoder.compress(data, options);
    }

    public static EncodeResult compress(InputStream in, EncodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageEncoder.compress(in, options);
        }
        return SystemImageEncoder.compress(in, options);
    }

    public static EncodeResult compress(Bitmap bitmap, EncodeOptions options) {
        if (StaticOptions.supportNativeProcess) {
            return NeonImageEncoder.compress(bitmap, options);
        }
        return SystemImageEncoder.compress(bitmap, options);
    }
}
