package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class DecodeResult {
    public Bitmap bitmap;
    public int code = -1;
    public int extra;
    public ImageInfo srcInfo;
    public byte[] yuvData;
    public ImageInfo yuvInfo;

    public interface CODE {
        public static final int ERROR = -1;
        public static final int SUCCESS = 0;
    }

    public interface EXTRA {
        public static final int DEGRADE_RGB565 = 1;
        public static final int DEGRADE_SAMPLE_SIZE = 2;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

    public String toString() {
        return "DecodeResult{code=" + this.code + ", bitmap=" + this.bitmap + ", bitmap.info=" + bitmapInfo() + ", yuvData=" + this.yuvData + ", extra=" + this.extra + ", srcInfo=" + this.srcInfo + ", yuvInfo=" + this.yuvInfo + '}';
    }

    private String bitmapInfo() {
        if (this.bitmap != null) {
            return "[" + this.bitmap.getWidth() + DictionaryKeys.CTRLXY_X + this.bitmap.getHeight() + "," + this.bitmap.getConfig() + "]";
        }
        return "[]";
    }
}
