package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APBizMaterialPackage;

public class APBizMaterialPackageQueryComplete {
    public APBizMaterialPackage mBizMaterialPackage;

    public APBizMaterialPackageQueryComplete(APBizMaterialPackage bizMaterialPackage) {
        this.mBizMaterialPackage = bizMaterialPackage;
    }

    public String toString() {
        return "APBizMaterialPackageQueryComplete{mBizMaterialPackage=" + this.mBizMaterialPackage + '}';
    }
}
