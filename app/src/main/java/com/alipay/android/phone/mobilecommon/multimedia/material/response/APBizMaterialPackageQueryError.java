package com.alipay.android.phone.mobilecommon.multimedia.material.response;

public class APBizMaterialPackageQueryError {
    public int code;
    public String id;
    public String msg;

    public String toString() {
        return "APBizMaterialPackageQueryError{id='" + this.id + '\'' + ", code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }
}
