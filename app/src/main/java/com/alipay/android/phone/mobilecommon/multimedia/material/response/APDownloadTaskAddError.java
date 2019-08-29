package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadTaskAddError extends BaseDownloadResponse {
    public int code;
    public APMaterialInfo mMaterialInfo;
    public String msg;

    public APDownloadTaskAddError(int code2, String msg2) {
        this.code = code2;
        this.msg = msg2;
    }

    public String toString() {
        return "APDownloadTaskAddError{code=" + this.code + ", msg='" + this.msg + '\'' + ", mMaterialInfo=" + this.mMaterialInfo + '}';
    }
}
