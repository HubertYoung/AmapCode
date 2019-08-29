package com.alipay.android.phone.inside.api.model.aliautologin;

import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.SourceType;

public enum SourceTypeEnum {
    NATIVE(SourceType.NATIVE),
    H5("H5");
    
    private String sourceTypeName;

    private SourceTypeEnum(String str) {
        this.sourceTypeName = str;
    }

    public final String getSourceTypeName() {
        return this.sourceTypeName;
    }
}
