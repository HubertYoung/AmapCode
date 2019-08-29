package com.alipay.android.phone.inside.api.model.scan;

import com.alipay.mobile.scansdk.constant.Constants;

public enum CodeTypeEnum {
    QRCODE(Constants.NORMAL_MA_TYPE_QR),
    BARCODE(Constants.NORMAL_MA_TYPE_BAR);
    
    private String codeName;

    private CodeTypeEnum(String str) {
        this.codeName = str;
    }

    public final String getCodeName() {
        return this.codeName;
    }
}
