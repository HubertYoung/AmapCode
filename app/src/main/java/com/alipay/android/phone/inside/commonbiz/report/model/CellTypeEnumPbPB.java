package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;

public enum CellTypeEnumPbPB implements ProtoEnum {
    GSM(0),
    CDMA(1);
    
    private final int value;

    private CellTypeEnumPbPB(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
