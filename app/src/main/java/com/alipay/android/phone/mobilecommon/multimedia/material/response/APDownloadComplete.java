package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadComplete extends BaseDownloadResponse {
    public APMaterialInfo mMaterialInfo;

    public String toString() {
        return "APDownloadComplete{, mMaterialInfo=" + this.mMaterialInfo + '}';
    }
}
