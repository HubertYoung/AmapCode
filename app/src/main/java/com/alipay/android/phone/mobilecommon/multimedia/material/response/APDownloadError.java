package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadError extends BaseDownloadResponse {
    public static final int CODE_ERROR_FALCON_CHECK = 10002;
    public static final int CODE_ERROR_MD5 = 10000;
    public static final int CODE_ERROR_UNZIP = 10001;
    public int errorCode;
    public APMaterialInfo mMaterialInfo;
    public String msg;

    public String toString() {
        return "APDownloadError{, mMaterialInfo=" + this.mMaterialInfo + ", errorCode=" + this.errorCode + ", msg='" + this.msg + '\'' + '}';
    }
}
