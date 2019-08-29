package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;

public class APImageUpRequest {
    public static final int DEFAULT_UP_H = 1280;
    public static final int DEFAULT_UP_W = 1280;
    public static final int FILE_TYPE_DEFAULT = 0;
    public static final int FILE_TYPE_GIF = 1;
    public static final int TYPE_DEFAULT = 4;
    public static final int TYPE_HIGH = 1;
    public static final int TYPE_LOW = 3;
    public static final int TYPE_MIDDLE = 2;
    public static final int TYPE_ORIGINAL = 0;
    public APImageUploadCallback callback;
    public byte[] fileData;
    public int height = 1280;
    public boolean isSync = false;
    private int mFileType = 0;
    private int mTimeout = -1;
    public APImageUploadOption option;
    public String path;
    public Boolean setPublic;
    public int uploadType = 4;
    public int width = 1280;

    public void setTimeout(int timeout) {
        this.mTimeout = timeout;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getFileType() {
        return this.mFileType;
    }

    public void setFileType(int mFileType2) {
        this.mFileType = mFileType2;
    }
}
