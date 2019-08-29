package com.alipay.android.phone.mobilecommon.multimedia.material.response;

import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;

public class APPackageQueryComplete {
    public APPackageInfo mPackageInfo;

    public APPackageQueryComplete(APPackageInfo packageInfo) {
        this.mPackageInfo = packageInfo;
    }

    public String toString() {
        return "APPackageQueryComplete{mPackageInfo=" + this.mPackageInfo + '}';
    }
}
