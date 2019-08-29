package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadCancel extends BaseDownloadResponse {
    public APMaterialInfo mMaterialInfo;

    public String toString() {
        return "APDownloadCancel{, mMaterialInfo=" + this.mMaterialInfo + '}';
    }
}
