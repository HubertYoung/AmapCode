package com.alipay.multimedia.img.encode;

import com.alipay.multimedia.img.ImageInfo;

public class EncodeResult {
    public int code = -1;
    public byte[] encodeData;
    public String encodeFilePath;
    public int extra;
    public ImageInfo imageInfo;

    public interface CODE {
        public static final int ERROR = -1;
        public static final int SUCCESS = 0;
    }

    public interface EXTRA {
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

    public String toString() {
        return "EncodeResult{code=" + this.code + ", extra=" + this.extra + ", encodeData=" + this.encodeData + ", encodeFilePath='" + this.encodeFilePath + '\'' + ", imageInfo=" + this.imageInfo + '}';
    }
}
