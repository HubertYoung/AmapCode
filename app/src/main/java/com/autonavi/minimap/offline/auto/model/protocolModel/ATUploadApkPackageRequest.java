package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATUploadApkPackageRequest {
    private ATApkPackage apkInfo;

    public ATApkPackage getApkInfo() {
        return this.apkInfo;
    }

    public void setApkInfo(ATApkPackage aTApkPackage) {
        this.apkInfo = aTApkPackage;
    }
}
