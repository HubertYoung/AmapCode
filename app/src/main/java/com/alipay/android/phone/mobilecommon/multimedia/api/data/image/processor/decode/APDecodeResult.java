package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode;

import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;

public class APDecodeResult {
    public Bitmap bitmap;
    public int code = -1;
    public int extra;
    public APImageInfo srcInfo;

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
        return "APDecodeResult{code=" + this.code + ", bitmap=" + this.bitmap + ", extra=" + this.extra + '}';
    }
}
