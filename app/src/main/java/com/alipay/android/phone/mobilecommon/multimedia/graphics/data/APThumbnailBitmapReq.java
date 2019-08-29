package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APThumbnailBitmapReq extends APCacheBitmapReq {
    public Integer minHeight = null;
    public Integer minWidth = null;

    public APThumbnailBitmapReq(String path, int width, int height) {
        super(path, width, height);
    }

    public APThumbnailBitmapReq(String path) {
        super(path, -1, -1);
    }
}
