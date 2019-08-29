package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;

public class APDownloadTaskAdd extends BaseDownloadResponse {
    public APMaterialInfo mMaterialInfo;
    public APMultimediaTaskModel mTaskModel;

    public APDownloadTaskAdd(APMultimediaTaskModel taskModel, APMaterialInfo materialInfo) {
        this.mTaskModel = taskModel;
        this.mMaterialInfo = materialInfo;
    }

    public String toString() {
        return "APDownloadTaskAdd{mTaskModel=" + this.mTaskModel + ", mMaterialInfo=" + this.mMaterialInfo + '}';
    }
}
