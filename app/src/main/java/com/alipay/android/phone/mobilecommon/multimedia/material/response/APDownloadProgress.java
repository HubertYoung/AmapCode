package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadProgress extends BaseDownloadResponse {
    public APMaterialInfo mMaterialInfo;
    public int percent;
    public long progress;
    public long total;

    public APDownloadProgress() {
    }

    public APDownloadProgress(int percent2, long progress2, long total2) {
        this.percent = percent2;
        this.progress = progress2;
        this.total = total2;
    }

    public String toString() {
        return "APDownloadProgress{, mMaterialInfo=" + this.mMaterialInfo + ", percent=" + this.percent + ", progress=" + this.progress + ", total=" + this.total + '}';
    }
}
